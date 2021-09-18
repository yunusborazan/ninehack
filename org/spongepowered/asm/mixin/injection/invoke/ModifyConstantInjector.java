/*     */ package org.spongepowered.asm.mixin.injection.invoke;
/*     */ 
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.InsnNode;
/*     */ import org.spongepowered.asm.lib.tree.JumpInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModifyConstantInjector
/*     */   extends RedirectInjector
/*     */ {
/*     */   private static final int OPCODE_OFFSET = 6;
/*     */   
/*     */   public ModifyConstantInjector(InjectionInfo info) {
/*  55 */     super(info, "@ModifyConstant");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/*  60 */     if (!preInject(node)) {
/*     */       return;
/*     */     }
/*     */     
/*  64 */     if (node.isReplaced()) {
/*  65 */       throw new UnsupportedOperationException("Target failure for " + this.info);
/*     */     }
/*     */     
/*  68 */     AbstractInsnNode targetNode = node.getCurrentTarget();
/*  69 */     if (targetNode instanceof JumpInsnNode) {
/*  70 */       checkTargetModifiers(target, false);
/*  71 */       injectExpandedConstantModifier(target, (JumpInsnNode)targetNode);
/*     */       
/*     */       return;
/*     */     } 
/*  75 */     if (Bytecode.isConstant(targetNode)) {
/*  76 */       checkTargetModifiers(target, false);
/*  77 */       injectConstantModifier(target, targetNode);
/*     */       
/*     */       return;
/*     */     } 
/*  81 */     throw new InvalidInjectionException(this.info, this.annotationType + " annotation is targetting an invalid insn in " + target + " in " + this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectExpandedConstantModifier(Target target, JumpInsnNode jumpNode) {
/*  92 */     int opcode = jumpNode.getOpcode();
/*  93 */     if (opcode < 155 || opcode > 158) {
/*  94 */       throw new InvalidInjectionException(this.info, this.annotationType + " annotation selected an invalid opcode " + 
/*  95 */           Bytecode.getOpcodeName(opcode) + " in " + target + " in " + this);
/*     */     }
/*     */     
/*  98 */     InsnList insns = new InsnList();
/*  99 */     insns.add((AbstractInsnNode)new InsnNode(3));
/* 100 */     AbstractInsnNode invoke = invokeConstantHandler(Type.getType("I"), target, insns, insns);
/* 101 */     insns.add((AbstractInsnNode)new JumpInsnNode(opcode + 6, jumpNode.label));
/* 102 */     target.replaceNode((AbstractInsnNode)jumpNode, invoke, insns);
/* 103 */     target.addToStack(1);
/*     */   }
/*     */   
/*     */   private void injectConstantModifier(Target target, AbstractInsnNode constNode) {
/* 107 */     Type constantType = Bytecode.getConstantType(constNode);
/* 108 */     InsnList before = new InsnList();
/* 109 */     InsnList after = new InsnList();
/* 110 */     AbstractInsnNode invoke = invokeConstantHandler(constantType, target, before, after);
/* 111 */     target.wrapNode(constNode, invoke, before, after);
/*     */   }
/*     */   
/*     */   private AbstractInsnNode invokeConstantHandler(Type constantType, Target target, InsnList before, InsnList after) {
/* 115 */     String handlerDesc = Bytecode.generateDescriptor(constantType, new Object[] { constantType });
/* 116 */     boolean withArgs = checkDescriptor(handlerDesc, target, "getter");
/*     */     
/* 118 */     if (!this.isStatic) {
/* 119 */       before.insert((AbstractInsnNode)new VarInsnNode(25, 0));
/* 120 */       target.addToStack(1);
/*     */     } 
/*     */     
/* 123 */     if (withArgs) {
/* 124 */       pushArgs(target.arguments, after, target.getArgIndices(), 0, target.arguments.length);
/* 125 */       target.addToStack(Bytecode.getArgsSize(target.arguments));
/*     */     } 
/*     */     
/* 128 */     return invokeHandler(after);
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\injection\invoke\ModifyConstantInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */