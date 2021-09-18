/*    */ package org.spongepowered.asm.mixin.injection.points;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.ListIterator;
/*    */ import org.spongepowered.asm.lib.Type;
/*    */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*    */ import org.spongepowered.asm.lib.tree.InsnList;
/*    */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*    */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*    */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*    */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*    */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @AtCode("TAIL")
/*    */ public class BeforeFinalReturn
/*    */   extends InjectionPoint
/*    */ {
/*    */   private final IMixinContext context;
/*    */   
/*    */   public BeforeFinalReturn(InjectionPointData data) {
/* 64 */     super(data);
/*    */     
/* 66 */     this.context = data.getContext();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 71 */     AbstractInsnNode ret = null;
/*    */ 
/*    */     
/* 74 */     int returnOpcode = Type.getReturnType(desc).getOpcode(172);
/*    */     
/* 76 */     ListIterator<AbstractInsnNode> iter = insns.iterator();
/* 77 */     while (iter.hasNext()) {
/* 78 */       AbstractInsnNode insn = iter.next();
/* 79 */       if (insn instanceof org.spongepowered.asm.lib.tree.InsnNode && insn.getOpcode() == returnOpcode) {
/* 80 */         ret = insn;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 85 */     if (ret == null) {
/* 86 */       throw new InvalidInjectionException(this.context, "TAIL could not locate a valid RETURN in the target method!");
/*    */     }
/*    */     
/* 89 */     nodes.add(ret);
/* 90 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\injection\points\BeforeFinalReturn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */