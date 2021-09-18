/*      */ package org.spongepowered.asm.mixin.transformer;
/*      */ 
/*      */ import com.google.common.collect.BiMap;
/*      */ import com.google.common.collect.HashBiMap;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Deque;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.spongepowered.asm.lib.Handle;
/*      */ import org.spongepowered.asm.lib.Type;
/*      */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*      */ import org.spongepowered.asm.lib.tree.ClassNode;
/*      */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.FieldNode;
/*      */ import org.spongepowered.asm.lib.tree.InvokeDynamicInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.LdcInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.LocalVariableNode;
/*      */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.MethodNode;
/*      */ import org.spongepowered.asm.lib.tree.TypeInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*      */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*      */ import org.spongepowered.asm.mixin.SoftOverride;
/*      */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*      */ import org.spongepowered.asm.mixin.gen.AccessorInfo;
/*      */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*      */ import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
/*      */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*      */ import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
/*      */ import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
/*      */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*      */ import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
/*      */ import org.spongepowered.asm.mixin.struct.MemberRef;
/*      */ import org.spongepowered.asm.mixin.struct.SourceMap;
/*      */ import org.spongepowered.asm.mixin.transformer.ext.Extensions;
/*      */ import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
/*      */ import org.spongepowered.asm.obfuscation.RemapperChain;
/*      */ import org.spongepowered.asm.util.Annotations;
/*      */ import org.spongepowered.asm.util.Bytecode;
/*      */ import org.spongepowered.asm.util.ClassSignature;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MixinTargetContext
/*      */   extends ClassContext
/*      */   implements IMixinContext
/*      */ {
/*   93 */   private static final Logger logger = LogManager.getLogger("mixin");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final MixinInfo mixin;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final ClassNode classNode;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final TargetClassContext targetClass;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String sessionId;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final ClassInfo targetClassInfo;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  123 */   private final BiMap<String, String> innerClasses = (BiMap<String, String>)HashBiMap.create();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  128 */   private final List<MethodNode> shadowMethods = new ArrayList<MethodNode>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  133 */   private final Map<FieldNode, ClassInfo.Field> shadowFields = new LinkedHashMap<FieldNode, ClassInfo.Field>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  138 */   private final List<MethodNode> mergedMethods = new ArrayList<MethodNode>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  143 */   private final InjectorGroupInfo.Map injectorGroups = new InjectorGroupInfo.Map();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  148 */   private final List<InjectionInfo> injectors = new ArrayList<InjectionInfo>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  153 */   private final List<AccessorInfo> accessors = new ArrayList<AccessorInfo>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean inheritsFromMixin;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean detachedSuper;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final SourceMap.File stratum;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  174 */   private int minRequiredClassVersion = MixinEnvironment.CompatibilityLevel.JAVA_6.classVersion();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MixinTargetContext(MixinInfo mixin, ClassNode classNode, TargetClassContext context) {
/*  184 */     this.mixin = mixin;
/*  185 */     this.classNode = classNode;
/*  186 */     this.targetClass = context;
/*  187 */     this.targetClassInfo = ClassInfo.forName(getTarget().getClassRef());
/*  188 */     this.stratum = context.getSourceMap().addFile(this.classNode);
/*  189 */     this.inheritsFromMixin = (mixin.getClassInfo().hasMixinInHierarchy() || this.targetClassInfo.hasMixinTargetInHierarchy());
/*  190 */     this.detachedSuper = !this.classNode.superName.equals((getTarget().getClassNode()).superName);
/*  191 */     this.sessionId = context.getSessionId();
/*  192 */     requireVersion(classNode.version);
/*      */     
/*  194 */     InnerClassGenerator icg = (InnerClassGenerator)context.getExtensions().getGenerator(InnerClassGenerator.class);
/*  195 */     for (String innerClass : this.mixin.getInnerClasses()) {
/*  196 */       this.innerClasses.put(innerClass, icg.registerInnerClass(this.mixin, innerClass, this));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addShadowMethod(MethodNode method) {
/*  206 */     this.shadowMethods.add(method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addShadowField(FieldNode fieldNode, ClassInfo.Field fieldInfo) {
/*  216 */     this.shadowFields.put(fieldNode, fieldInfo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addAccessorMethod(MethodNode method, Class<? extends Annotation> type) {
/*  226 */     this.accessors.add(AccessorInfo.of(this, method, type));
/*      */   }
/*      */   
/*      */   void addMixinMethod(MethodNode method) {
/*  230 */     Annotations.setVisible(method, MixinMerged.class, new Object[] { "mixin", getClassName() });
/*  231 */     getTarget().addMixinMethod(method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void methodMerged(MethodNode method) {
/*  240 */     this.mergedMethods.add(method);
/*  241 */     this.targetClassInfo.addMethod(method);
/*  242 */     getTarget().methodMerged(method);
/*      */     
/*  244 */     Annotations.setVisible(method, MixinMerged.class, new Object[] { "mixin", 
/*  245 */           getClassName(), "priority", 
/*  246 */           Integer.valueOf(getPriority()), "sessionId", this.sessionId });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  255 */     return this.mixin.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment getEnvironment() {
/*  264 */     return this.mixin.getParent().getEnvironment();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOption(MixinEnvironment.Option option) {
/*  273 */     return getEnvironment().getOption(option);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassNode getClassNode() {
/*  283 */     return this.classNode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClassName() {
/*  292 */     return this.mixin.getClassName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClassRef() {
/*  301 */     return this.mixin.getClassRef();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TargetClassContext getTarget() {
/*  310 */     return this.targetClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTargetClassRef() {
/*  321 */     return getTarget().getClassRef();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassNode getTargetClassNode() {
/*  330 */     return getTarget().getClassNode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfo getTargetClassInfo() {
/*  339 */     return this.targetClassInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ClassInfo getClassInfo() {
/*  349 */     return this.mixin.getClassInfo();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassSignature getSignature() {
/*  358 */     return getClassInfo().getSignature();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SourceMap.File getStratum() {
/*  367 */     return this.stratum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinRequiredClassVersion() {
/*  374 */     return this.minRequiredClassVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDefaultRequiredInjections() {
/*  384 */     return this.mixin.getParent().getDefaultRequiredInjections();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultInjectorGroup() {
/*  393 */     return this.mixin.getParent().getDefaultInjectorGroup();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxShiftByValue() {
/*  402 */     return this.mixin.getParent().getMaxShiftByValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InjectorGroupInfo.Map getInjectorGroups() {
/*  411 */     return this.injectorGroups;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean requireOverwriteAnnotations() {
/*  420 */     return this.mixin.getParent().requireOverwriteAnnotations();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfo findRealType(ClassInfo mixin) {
/*  431 */     if (mixin == getClassInfo()) {
/*  432 */       return this.targetClassInfo;
/*      */     }
/*      */     
/*  435 */     ClassInfo type = this.targetClassInfo.findCorrespondingType(mixin);
/*  436 */     if (type == null) {
/*  437 */       throw new InvalidMixinException(this, "Resolution error: unable to find corresponding type for " + mixin + " in hierarchy of " + this.targetClassInfo);
/*      */     }
/*      */ 
/*      */     
/*  441 */     return type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transformMethod(MethodNode method) {
/*  453 */     validateMethod(method);
/*  454 */     transformDescriptor(method);
/*  455 */     transformLVT(method);
/*      */ 
/*      */     
/*  458 */     this.stratum.applyOffset(method);
/*      */     
/*  460 */     AbstractInsnNode lastInsn = null;
/*  461 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/*  462 */       AbstractInsnNode insn = iter.next();
/*      */       
/*  464 */       if (insn instanceof MethodInsnNode) {
/*  465 */         transformMethodRef(method, iter, (MemberRef)new MemberRef.Method((MethodInsnNode)insn));
/*  466 */       } else if (insn instanceof FieldInsnNode) {
/*  467 */         transformFieldRef(method, iter, (MemberRef)new MemberRef.Field((FieldInsnNode)insn));
/*  468 */         checkFinal(method, iter, (FieldInsnNode)insn);
/*  469 */       } else if (insn instanceof TypeInsnNode) {
/*  470 */         transformTypeNode(method, iter, (TypeInsnNode)insn, lastInsn);
/*  471 */       } else if (insn instanceof LdcInsnNode) {
/*  472 */         transformConstantNode(method, iter, (LdcInsnNode)insn);
/*  473 */       } else if (insn instanceof InvokeDynamicInsnNode) {
/*  474 */         transformInvokeDynamicNode(method, iter, (InvokeDynamicInsnNode)insn);
/*      */       } 
/*      */       
/*  477 */       lastInsn = insn;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void validateMethod(MethodNode method) {
/*  489 */     if (Annotations.getInvisible(method, SoftOverride.class) != null) {
/*  490 */       ClassInfo.Method superMethod = this.targetClassInfo.findMethodInHierarchy(method.name, method.desc, ClassInfo.SearchType.SUPER_CLASSES_ONLY, ClassInfo.Traversal.SUPER);
/*      */       
/*  492 */       if (superMethod == null || !superMethod.isInjected()) {
/*  493 */         throw new InvalidMixinException(this, "Mixin method " + method.name + method.desc + " is tagged with @SoftOverride but no valid method was found in superclasses of " + 
/*  494 */             getTarget().getClassName());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transformLVT(MethodNode method) {
/*  505 */     if (method.localVariables == null) {
/*      */       return;
/*      */     }
/*      */     
/*  509 */     for (LocalVariableNode local : method.localVariables) {
/*  510 */       if (local == null || local.desc == null) {
/*      */         continue;
/*      */       }
/*      */       
/*  514 */       local.desc = transformSingleDescriptor(Type.getType(local.desc));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transformMethodRef(MethodNode method, Iterator<AbstractInsnNode> iter, MemberRef methodRef) {
/*  527 */     transformDescriptor(methodRef);
/*      */     
/*  529 */     if (methodRef.getOwner().equals(getClassRef())) {
/*  530 */       methodRef.setOwner(getTarget().getClassRef());
/*  531 */       ClassInfo.Method md = getClassInfo().findMethod(methodRef.getName(), methodRef.getDesc(), 10);
/*  532 */       if (md != null && md.isRenamed() && md.getOriginalName().equals(methodRef.getName()) && md.isSynthetic()) {
/*  533 */         methodRef.setName(md.getName());
/*      */       }
/*  535 */       upgradeMethodRef(method, methodRef, md);
/*  536 */     } else if (this.innerClasses.containsKey(methodRef.getOwner())) {
/*  537 */       methodRef.setOwner((String)this.innerClasses.get(methodRef.getOwner()));
/*  538 */       methodRef.setDesc(transformMethodDescriptor(methodRef.getDesc()));
/*  539 */     } else if (this.detachedSuper || this.inheritsFromMixin) {
/*  540 */       if (methodRef.getOpcode() == 183) {
/*  541 */         updateStaticBinding(method, methodRef);
/*  542 */       } else if (methodRef.getOpcode() == 182 && ClassInfo.forName(methodRef.getOwner()).isMixin()) {
/*  543 */         updateDynamicBinding(method, methodRef);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transformFieldRef(MethodNode method, Iterator<AbstractInsnNode> iter, MemberRef fieldRef) {
/*  560 */     if ("super$".equals(fieldRef.getName())) {
/*  561 */       if (fieldRef instanceof MemberRef.Field) {
/*  562 */         processImaginarySuper(method, ((MemberRef.Field)fieldRef).insn);
/*  563 */         iter.remove();
/*      */       } else {
/*  565 */         throw new InvalidMixinException(this.mixin, "Cannot call imaginary super from method handle.");
/*      */       } 
/*      */     }
/*      */     
/*  569 */     transformDescriptor(fieldRef);
/*      */     
/*  571 */     if (fieldRef.getOwner().equals(getClassRef())) {
/*  572 */       fieldRef.setOwner(getTarget().getClassRef());
/*      */       
/*  574 */       ClassInfo.Field field = getClassInfo().findField(fieldRef.getName(), fieldRef.getDesc(), 10);
/*      */       
/*  576 */       if (field != null && field.isRenamed() && field.getOriginalName().equals(fieldRef.getName()) && field.isStatic()) {
/*  577 */         fieldRef.setName(field.getName());
/*      */       }
/*      */     } else {
/*  580 */       ClassInfo fieldOwner = ClassInfo.forName(fieldRef.getOwner());
/*  581 */       if (fieldOwner.isMixin()) {
/*  582 */         ClassInfo actualOwner = this.targetClassInfo.findCorrespondingType(fieldOwner);
/*  583 */         fieldRef.setOwner((actualOwner != null) ? actualOwner.getName() : getTarget().getClassRef());
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void checkFinal(MethodNode method, Iterator<AbstractInsnNode> iter, FieldInsnNode fieldNode) {
/*  589 */     if (!fieldNode.owner.equals(getTarget().getClassRef())) {
/*      */       return;
/*      */     }
/*      */     
/*  593 */     int opcode = fieldNode.getOpcode();
/*  594 */     if (opcode == 180 || opcode == 178) {
/*      */       return;
/*      */     }
/*      */     
/*  598 */     for (Map.Entry<FieldNode, ClassInfo.Field> shadow : this.shadowFields.entrySet()) {
/*  599 */       FieldNode shadowFieldNode = shadow.getKey();
/*  600 */       if (!shadowFieldNode.desc.equals(fieldNode.desc) || !shadowFieldNode.name.equals(fieldNode.name)) {
/*      */         continue;
/*      */       }
/*  603 */       ClassInfo.Field shadowField = shadow.getValue();
/*  604 */       if (shadowField.isDecoratedFinal()) {
/*  605 */         if (shadowField.isDecoratedMutable()) {
/*  606 */           if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/*  607 */             logger.warn("Write access to @Mutable @Final field {} in {}::{}", new Object[] { shadowField, this.mixin, method.name });
/*      */           }
/*      */         }
/*  610 */         else if ("<init>".equals(method.name) || "<clinit>".equals(method.name)) {
/*  611 */           logger.warn("@Final field {} in {} should be final", new Object[] { shadowField, this.mixin });
/*      */         } else {
/*  613 */           logger.error("Write access detected to @Final field {} in {}::{}", new Object[] { shadowField, this.mixin, method.name });
/*  614 */           if (this.mixin.getParent().getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
/*  615 */             throw new InvalidMixinException(this.mixin, "Write access detected to @Final field " + shadowField + " in " + this.mixin + "::" + method.name);
/*      */           }
/*      */         } 
/*      */       }
/*      */       return;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transformTypeNode(MethodNode method, Iterator<AbstractInsnNode> iter, TypeInsnNode typeInsn, AbstractInsnNode lastNode) {
/*  636 */     if (typeInsn.getOpcode() == 192 && typeInsn.desc
/*  637 */       .equals(getTarget().getClassRef()) && lastNode
/*  638 */       .getOpcode() == 25 && ((VarInsnNode)lastNode).var == 0) {
/*      */       
/*  640 */       iter.remove();
/*      */       
/*      */       return;
/*      */     } 
/*  644 */     if (typeInsn.desc.equals(getClassRef())) {
/*  645 */       typeInsn.desc = getTarget().getClassRef();
/*      */     } else {
/*  647 */       String newName = (String)this.innerClasses.get(typeInsn.desc);
/*  648 */       if (newName != null) {
/*  649 */         typeInsn.desc = newName;
/*      */       }
/*      */     } 
/*      */     
/*  653 */     transformDescriptor(typeInsn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transformConstantNode(MethodNode method, Iterator<AbstractInsnNode> iter, LdcInsnNode ldcInsn) {
/*  665 */     ldcInsn.cst = transformConstant(method, iter, ldcInsn.cst);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void transformInvokeDynamicNode(MethodNode method, Iterator<AbstractInsnNode> iter, InvokeDynamicInsnNode dynInsn) {
/*  676 */     requireVersion(51);
/*  677 */     dynInsn.desc = transformMethodDescriptor(dynInsn.desc);
/*  678 */     dynInsn.bsm = transformHandle(method, iter, dynInsn.bsm);
/*  679 */     for (int i = 0; i < dynInsn.bsmArgs.length; i++) {
/*  680 */       dynInsn.bsmArgs[i] = transformConstant(method, iter, dynInsn.bsmArgs[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Object transformConstant(MethodNode method, Iterator<AbstractInsnNode> iter, Object constant) {
/*  693 */     if (constant instanceof Type) {
/*  694 */       Type type = (Type)constant;
/*  695 */       String desc = transformDescriptor(type);
/*  696 */       if (!type.toString().equals(desc)) {
/*  697 */         return Type.getType(desc);
/*      */       }
/*  699 */       return constant;
/*  700 */     }  if (constant instanceof Handle) {
/*  701 */       return transformHandle(method, iter, (Handle)constant);
/*      */     }
/*  703 */     return constant;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Handle transformHandle(MethodNode method, Iterator<AbstractInsnNode> iter, Handle handle) {
/*  715 */     MemberRef.Handle memberRef = new MemberRef.Handle(handle);
/*  716 */     if (memberRef.isField()) {
/*  717 */       transformFieldRef(method, iter, (MemberRef)memberRef);
/*      */     } else {
/*  719 */       transformMethodRef(method, iter, (MemberRef)memberRef);
/*      */     } 
/*  721 */     return memberRef.getMethodHandle();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void processImaginarySuper(MethodNode method, FieldInsnNode fieldInsn) {
/*  737 */     if (fieldInsn.getOpcode() != 180) {
/*  738 */       if ("<init>".equals(method.name)) {
/*  739 */         throw new InvalidMixinException(this, "Illegal imaginary super declaration: field " + fieldInsn.name + " must not specify an initialiser");
/*      */       }
/*      */ 
/*      */       
/*  743 */       throw new InvalidMixinException(this, "Illegal imaginary super access: found " + Bytecode.getOpcodeName(fieldInsn.getOpcode()) + " opcode in " + method.name + method.desc);
/*      */     } 
/*      */ 
/*      */     
/*  747 */     if ((method.access & 0x2) != 0 || (method.access & 0x8) != 0) {
/*  748 */       throw new InvalidMixinException(this, "Illegal imaginary super access: method " + method.name + method.desc + " is private or static");
/*      */     }
/*      */ 
/*      */     
/*  752 */     if (Annotations.getInvisible(method, SoftOverride.class) == null) {
/*  753 */       throw new InvalidMixinException(this, "Illegal imaginary super access: method " + method.name + method.desc + " is not decorated with @SoftOverride");
/*      */     }
/*      */ 
/*      */     
/*  757 */     for (Iterator<AbstractInsnNode> methodIter = method.instructions.iterator(method.instructions.indexOf((AbstractInsnNode)fieldInsn)); methodIter.hasNext(); ) {
/*  758 */       AbstractInsnNode insn = methodIter.next();
/*  759 */       if (insn instanceof MethodInsnNode) {
/*  760 */         MethodInsnNode methodNode = (MethodInsnNode)insn;
/*  761 */         if (methodNode.owner.equals(getClassRef()) && methodNode.name.equals(method.name) && methodNode.desc.equals(method.desc)) {
/*  762 */           methodNode.setOpcode(183);
/*  763 */           updateStaticBinding(method, (MemberRef)new MemberRef.Method(methodNode));
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*  769 */     throw new InvalidMixinException(this, "Illegal imaginary super access: could not find INVOKE for " + method.name + method.desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateStaticBinding(MethodNode method, MemberRef methodRef) {
/*  780 */     updateBinding(method, methodRef, ClassInfo.Traversal.SUPER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateDynamicBinding(MethodNode method, MemberRef methodRef) {
/*  791 */     updateBinding(method, methodRef, ClassInfo.Traversal.ALL);
/*      */   }
/*      */   
/*      */   private void updateBinding(MethodNode method, MemberRef methodRef, ClassInfo.Traversal traversal) {
/*  795 */     if ("<init>".equals(method.name) || methodRef
/*  796 */       .getOwner().equals(getTarget().getClassRef()) || 
/*  797 */       getTarget().getClassRef().startsWith("<")) {
/*      */       return;
/*      */     }
/*      */     
/*  801 */     ClassInfo.Method superMethod = this.targetClassInfo.findMethodInHierarchy(methodRef.getName(), methodRef.getDesc(), traversal
/*  802 */         .getSearchType(), traversal);
/*  803 */     if (superMethod != null) {
/*  804 */       if (superMethod.getOwner().isMixin()) {
/*  805 */         throw new InvalidMixinException(this, "Invalid " + methodRef + " in " + this + " resolved " + superMethod.getOwner() + " but is mixin.");
/*      */       }
/*      */       
/*  808 */       methodRef.setOwner(superMethod.getImplementor().getName());
/*  809 */     } else if (ClassInfo.forName(methodRef.getOwner()).isMixin()) {
/*  810 */       throw new MixinTransformerError("Error resolving " + methodRef + " in " + this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transformDescriptor(FieldNode field) {
/*  820 */     if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
/*      */       return;
/*      */     }
/*  823 */     field.desc = transformSingleDescriptor(field.desc, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transformDescriptor(MethodNode method) {
/*  832 */     if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
/*      */       return;
/*      */     }
/*  835 */     method.desc = transformMethodDescriptor(method.desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transformDescriptor(MemberRef member) {
/*  845 */     if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
/*      */       return;
/*      */     }
/*  848 */     if (member.isField()) {
/*  849 */       member.setDesc(transformSingleDescriptor(member.getDesc(), false));
/*      */     } else {
/*  851 */       member.setDesc(transformMethodDescriptor(member.getDesc()));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transformDescriptor(TypeInsnNode typeInsn) {
/*  861 */     if (!this.inheritsFromMixin && this.innerClasses.size() == 0) {
/*      */       return;
/*      */     }
/*  864 */     typeInsn.desc = transformSingleDescriptor(typeInsn.desc, true);
/*      */   }
/*      */   
/*      */   private String transformDescriptor(Type type) {
/*  868 */     if (type.getSort() == 11) {
/*  869 */       return transformMethodDescriptor(type.getDescriptor());
/*      */     }
/*  871 */     return transformSingleDescriptor(type);
/*      */   }
/*      */   
/*      */   private String transformSingleDescriptor(Type type) {
/*  875 */     if (type.getSort() < 9) {
/*  876 */       return type.toString();
/*      */     }
/*      */     
/*  879 */     return transformSingleDescriptor(type.toString(), false);
/*      */   }
/*      */   
/*      */   private String transformSingleDescriptor(String desc, boolean isObject) {
/*  883 */     String type = desc;
/*  884 */     while (type.startsWith("[") || type.startsWith("L")) {
/*  885 */       if (type.startsWith("[")) {
/*  886 */         type = type.substring(1);
/*      */         continue;
/*      */       } 
/*  889 */       type = type.substring(1, type.indexOf(";"));
/*  890 */       isObject = true;
/*      */     } 
/*      */     
/*  893 */     if (!isObject) {
/*  894 */       return desc;
/*      */     }
/*      */     
/*  897 */     String innerClassName = (String)this.innerClasses.get(type);
/*  898 */     if (innerClassName != null) {
/*  899 */       return desc.replace(type, innerClassName);
/*      */     }
/*      */     
/*  902 */     if (this.innerClasses.inverse().containsKey(type)) {
/*  903 */       return desc;
/*      */     }
/*      */     
/*  906 */     ClassInfo typeInfo = ClassInfo.forName(type);
/*      */     
/*  908 */     if (!typeInfo.isMixin()) {
/*  909 */       return desc;
/*      */     }
/*      */     
/*  912 */     return desc.replace(type, findRealType(typeInfo).toString());
/*      */   }
/*      */   
/*      */   private String transformMethodDescriptor(String desc) {
/*  916 */     StringBuilder newDesc = new StringBuilder();
/*  917 */     newDesc.append('(');
/*  918 */     for (Type arg : Type.getArgumentTypes(desc)) {
/*  919 */       newDesc.append(transformSingleDescriptor(arg));
/*      */     }
/*  921 */     return newDesc.append(')').append(transformSingleDescriptor(Type.getReturnType(desc))).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Target getTargetMethod(MethodNode method) {
/*  932 */     return getTarget().getTargetMethod(method);
/*      */   }
/*      */   
/*      */   MethodNode findMethod(MethodNode method, AnnotationNode annotation) {
/*  936 */     Deque<String> aliases = new LinkedList<String>();
/*  937 */     aliases.add(method.name);
/*  938 */     if (annotation != null) {
/*  939 */       List<String> aka = (List<String>)Annotations.getValue(annotation, "aliases");
/*  940 */       if (aka != null) {
/*  941 */         aliases.addAll(aka);
/*      */       }
/*      */     } 
/*      */     
/*  945 */     return getTarget().findMethod(aliases, method.desc);
/*      */   }
/*      */   
/*      */   MethodNode findRemappedMethod(MethodNode method) {
/*  949 */     RemapperChain remapperChain = getEnvironment().getRemappers();
/*  950 */     String remappedName = remapperChain.mapMethodName(getTarget().getClassRef(), method.name, method.desc);
/*  951 */     if (remappedName.equals(method.name)) {
/*  952 */       return null;
/*      */     }
/*      */     
/*  955 */     Deque<String> aliases = new LinkedList<String>();
/*  956 */     aliases.add(remappedName);
/*      */     
/*  958 */     return getTarget().findAliasedMethod(aliases, method.desc);
/*      */   }
/*      */   
/*      */   FieldNode findField(FieldNode field, AnnotationNode shadow) {
/*  962 */     Deque<String> aliases = new LinkedList<String>();
/*  963 */     aliases.add(field.name);
/*  964 */     if (shadow != null) {
/*  965 */       List<String> aka = (List<String>)Annotations.getValue(shadow, "aliases");
/*  966 */       if (aka != null) {
/*  967 */         aliases.addAll(aka);
/*      */       }
/*      */     } 
/*      */     
/*  971 */     return getTarget().findAliasedField(aliases, field.desc);
/*      */   }
/*      */   
/*      */   FieldNode findRemappedField(FieldNode field) {
/*  975 */     RemapperChain remapperChain = getEnvironment().getRemappers();
/*  976 */     String remappedName = remapperChain.mapFieldName(getTarget().getClassRef(), field.name, field.desc);
/*  977 */     if (remappedName.equals(field.name)) {
/*  978 */       return null;
/*      */     }
/*      */     
/*  981 */     Deque<String> aliases = new LinkedList<String>();
/*  982 */     aliases.add(remappedName);
/*  983 */     return getTarget().findAliasedField(aliases, field.desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void requireVersion(int version) {
/*  993 */     this.minRequiredClassVersion = Math.max(this.minRequiredClassVersion, version);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  998 */     if (version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
/*  999 */       throw new InvalidMixinException(this, "Unsupported mixin class version " + version);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Extensions getExtensions() {
/* 1008 */     return this.targetClass.getExtensions();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IMixinInfo getMixin() {
/* 1016 */     return this.mixin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MixinInfo getInfo() {
/* 1023 */     return this.mixin;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPriority() {
/* 1033 */     return this.mixin.getPriority();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<String> getInterfaces() {
/* 1042 */     return this.mixin.getInterfaces();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection<MethodNode> getShadowMethods() {
/* 1051 */     return this.shadowMethods;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<MethodNode> getMethods() {
/* 1060 */     return this.classNode.methods;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set<Map.Entry<FieldNode, ClassInfo.Field>> getShadowFields() {
/* 1069 */     return this.shadowFields.entrySet();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<FieldNode> getFields() {
/* 1078 */     return this.classNode.fields;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Level getLoggingLevel() {
/* 1087 */     return this.mixin.getLoggingLevel();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldSetSourceFile() {
/* 1097 */     return this.mixin.getParent().shouldSetSourceFile();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSourceFile() {
/* 1106 */     return this.classNode.sourceFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IReferenceMapper getReferenceMapper() {
/* 1115 */     return this.mixin.getParent().getReferenceMapper();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preApply(String transformedName, ClassNode targetClass) {
/* 1125 */     this.mixin.preApply(transformedName, targetClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void postApply(String transformedName, ClassNode targetClass) {
/*      */     try {
/* 1136 */       this.injectorGroups.validateAll();
/* 1137 */     } catch (InjectionValidationException ex) {
/* 1138 */       InjectorGroupInfo group = ex.getGroup();
/* 1139 */       throw new InjectionError(
/* 1140 */           String.format("Critical injection failure: Callback group %s in %s failed injection check: %s", new Object[] {
/* 1141 */               group, this.mixin, ex.getMessage()
/*      */             }));
/*      */     } 
/* 1144 */     this.mixin.postApply(transformedName, targetClass);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUniqueName(MethodNode method, boolean preservePrefix) {
/* 1157 */     return getTarget().getUniqueName(method, preservePrefix);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUniqueName(FieldNode field) {
/* 1168 */     return getTarget().getUniqueName(field);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void prepareInjections() {
/* 1176 */     this.injectors.clear();
/*      */     
/* 1178 */     for (MethodNode method : this.mergedMethods) {
/* 1179 */       InjectionInfo injectInfo = InjectionInfo.parse(this, method);
/* 1180 */       if (injectInfo == null) {
/*      */         continue;
/*      */       }
/*      */       
/* 1184 */       if (injectInfo.isValid()) {
/* 1185 */         injectInfo.prepare();
/* 1186 */         this.injectors.add(injectInfo);
/*      */       } 
/*      */       
/* 1189 */       method.visibleAnnotations.remove(injectInfo.getAnnotation());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyInjections() {
/* 1197 */     for (InjectionInfo injectInfo : this.injectors) {
/* 1198 */       injectInfo.inject();
/*      */     }
/*      */     
/* 1201 */     for (InjectionInfo injectInfo : this.injectors) {
/* 1202 */       injectInfo.postInject();
/*      */     }
/*      */     
/* 1205 */     this.injectors.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<MethodNode> generateAccessors() {
/* 1213 */     for (AccessorInfo accessor : this.accessors) {
/* 1214 */       accessor.locate();
/*      */     }
/*      */     
/* 1217 */     List<MethodNode> methods = new ArrayList<MethodNode>();
/*      */     
/* 1219 */     for (AccessorInfo accessor : this.accessors) {
/* 1220 */       MethodNode generated = accessor.generate();
/* 1221 */       getTarget().addMixinMethod(generated);
/* 1222 */       methods.add(generated);
/*      */     } 
/*      */     
/* 1225 */     return methods;
/*      */   }
/*      */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\transformer\MixinTargetContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */