/*     */ package org.spongepowered.asm.mixin.injection.code;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.InsnNode;
/*     */ import org.spongepowered.asm.lib.tree.LdcInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.lib.tree.TypeInsnNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
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
/*     */ public abstract class Injector
/*     */ {
/*     */   public static final class TargetNode
/*     */   {
/*     */     final AbstractInsnNode insn;
/*  69 */     final Set<InjectionPoint> nominators = new HashSet<InjectionPoint>();
/*     */     
/*     */     TargetNode(AbstractInsnNode insn) {
/*  72 */       this.insn = insn;
/*     */     }
/*     */     
/*     */     public AbstractInsnNode getNode() {
/*  76 */       return this.insn;
/*     */     }
/*     */     
/*     */     public Set<InjectionPoint> getNominators() {
/*  80 */       return Collections.unmodifiableSet(this.nominators);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj) {
/*  85 */       if (obj == null || obj.getClass() != TargetNode.class) {
/*  86 */         return false;
/*     */       }
/*     */       
/*  89 */       return (((TargetNode)obj).insn == this.insn);
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/*  94 */       return this.insn.hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   protected static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected InjectionInfo info;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final ClassNode classNode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final MethodNode methodNode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Type[] methodArgs;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Type returnType;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean isStatic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Injector(InjectionInfo info) {
/* 140 */     this(info.getClassNode(), info.getMethod());
/* 141 */     this.info = info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Injector(ClassNode classNode, MethodNode methodNode) {
/* 151 */     this.classNode = classNode;
/* 152 */     this.methodNode = methodNode;
/* 153 */     this.methodArgs = Type.getArgumentTypes(this.methodNode.desc);
/* 154 */     this.returnType = Type.getReturnType(this.methodNode.desc);
/* 155 */     this.isStatic = Bytecode.methodIsStatic(this.methodNode);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 160 */     return String.format("%s::%s", new Object[] { this.classNode.name, this.methodNode.name });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final List<InjectionNodes.InjectionNode> find(InjectorTarget injectorTarget, List<InjectionPoint> injectionPoints) {
/* 172 */     sanityCheck(injectorTarget.getTarget(), injectionPoints);
/*     */     
/* 174 */     List<InjectionNodes.InjectionNode> myNodes = new ArrayList<InjectionNodes.InjectionNode>();
/* 175 */     for (TargetNode node : findTargetNodes(injectorTarget, injectionPoints)) {
/* 176 */       addTargetNode(injectorTarget.getTarget(), myNodes, node.insn, node.nominators);
/*     */     }
/* 178 */     return myNodes;
/*     */   }
/*     */   
/*     */   protected void addTargetNode(Target target, List<InjectionNodes.InjectionNode> myNodes, AbstractInsnNode node, Set<InjectionPoint> nominators) {
/* 182 */     myNodes.add(target.addInjectionNode(node));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void inject(Target target, List<InjectionNodes.InjectionNode> nodes) {
/* 192 */     for (InjectionNodes.InjectionNode node : nodes) {
/* 193 */       if (node.isRemoved()) {
/* 194 */         if (this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/* 195 */           logger.warn("Target node for {} was removed by a previous injector in {}", new Object[] { this.info, target });
/*     */         }
/*     */         continue;
/*     */       } 
/* 199 */       inject(target, node);
/*     */     } 
/*     */     
/* 202 */     for (InjectionNodes.InjectionNode node : nodes) {
/* 203 */       postInject(target, node);
/*     */     }
/*     */   }
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
/*     */   private Collection<TargetNode> findTargetNodes(InjectorTarget injectorTarget, List<InjectionPoint> injectionPoints) {
/* 217 */     MethodNode method = injectorTarget.getMethod();
/* 218 */     Map<Integer, TargetNode> targetNodes = new TreeMap<Integer, TargetNode>();
/* 219 */     Collection<AbstractInsnNode> nodes = new ArrayList<AbstractInsnNode>(32);
/*     */     
/* 221 */     for (InjectionPoint injectionPoint : injectionPoints) {
/* 222 */       nodes.clear();
/*     */       
/* 224 */       if (findTargetNodes(method, injectionPoint, injectorTarget.getSlice(injectionPoint), nodes)) {
/* 225 */         for (AbstractInsnNode insn : nodes) {
/* 226 */           Integer key = Integer.valueOf(method.instructions.indexOf(insn));
/* 227 */           TargetNode targetNode = targetNodes.get(key);
/* 228 */           if (targetNode == null) {
/* 229 */             targetNode = new TargetNode(insn);
/* 230 */             targetNodes.put(key, targetNode);
/*     */           } 
/* 232 */           targetNode.nominators.add(injectionPoint);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 237 */     return targetNodes.values();
/*     */   }
/*     */   
/*     */   protected boolean findTargetNodes(MethodNode into, InjectionPoint injectionPoint, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 241 */     return injectionPoint.find(into.desc, insns, nodes);
/*     */   }
/*     */   
/*     */   protected void sanityCheck(Target target, List<InjectionPoint> injectionPoints) {
/* 245 */     if (target.classNode != this.classNode) {
/* 246 */       throw new InvalidInjectionException(this.info, "Target class does not match injector class in " + this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void inject(Target paramTarget, InjectionNodes.InjectionNode paramInjectionNode);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void postInject(Target target, InjectionNodes.InjectionNode node) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractInsnNode invokeHandler(InsnList insns) {
/* 263 */     return invokeHandler(insns, this.methodNode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractInsnNode invokeHandler(InsnList insns, MethodNode handler) {
/* 275 */     boolean isPrivate = ((handler.access & 0x2) != 0);
/* 276 */     int invokeOpcode = this.isStatic ? 184 : (isPrivate ? 183 : 182);
/* 277 */     MethodInsnNode insn = new MethodInsnNode(invokeOpcode, this.classNode.name, handler.name, handler.desc, false);
/* 278 */     insns.add((AbstractInsnNode)insn);
/* 279 */     this.info.addCallbackInvocation(handler);
/* 280 */     return (AbstractInsnNode)insn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void throwException(InsnList insns, String exceptionType, String message) {
/* 292 */     insns.add((AbstractInsnNode)new TypeInsnNode(187, exceptionType));
/* 293 */     insns.add((AbstractInsnNode)new InsnNode(89));
/* 294 */     insns.add((AbstractInsnNode)new LdcInsnNode(message));
/* 295 */     insns.add((AbstractInsnNode)new MethodInsnNode(183, exceptionType, "<init>", "(Ljava/lang/String;)V", false));
/* 296 */     insns.add((AbstractInsnNode)new InsnNode(191));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canCoerce(Type from, Type to) {
/* 308 */     if (from.getSort() == 10 && to.getSort() == 10) {
/* 309 */       return canCoerce(ClassInfo.forType(from), ClassInfo.forType(to));
/*     */     }
/*     */     
/* 312 */     return canCoerce(from.getDescriptor(), to.getDescriptor());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canCoerce(String from, String to) {
/* 324 */     if (from.length() > 1 || to.length() > 1) {
/* 325 */       return false;
/*     */     }
/*     */     
/* 328 */     return canCoerce(from.charAt(0), to.charAt(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canCoerce(char from, char to) {
/* 340 */     return (to == 'I' && "IBSCZ".indexOf(from) > -1);
/*     */   }
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
/*     */   private static boolean canCoerce(ClassInfo from, ClassInfo to) {
/* 353 */     return (from != null && to != null && (to == from || to.hasSuperClass(from)));
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\injection\code\Injector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */