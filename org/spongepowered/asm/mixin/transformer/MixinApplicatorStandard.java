/*      */ package org.spongepowered.asm.mixin.transformer;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Deque;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.SortedSet;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.spongepowered.asm.lib.Label;
/*      */ import org.spongepowered.asm.lib.Type;
/*      */ import org.spongepowered.asm.lib.signature.SignatureReader;
/*      */ import org.spongepowered.asm.lib.signature.SignatureVisitor;
/*      */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*      */ import org.spongepowered.asm.lib.tree.ClassNode;
/*      */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.FieldNode;
/*      */ import org.spongepowered.asm.lib.tree.LabelNode;
/*      */ import org.spongepowered.asm.lib.tree.LineNumberNode;
/*      */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.MethodNode;
/*      */ import org.spongepowered.asm.mixin.Final;
/*      */ import org.spongepowered.asm.mixin.Intrinsic;
/*      */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*      */ import org.spongepowered.asm.mixin.Overwrite;
/*      */ import org.spongepowered.asm.mixin.injection.Inject;
/*      */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*      */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*      */ import org.spongepowered.asm.mixin.injection.ModifyConstant;
/*      */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*      */ import org.spongepowered.asm.mixin.injection.Redirect;
/*      */ import org.spongepowered.asm.mixin.transformer.meta.MixinMerged;
/*      */ import org.spongepowered.asm.mixin.transformer.meta.MixinRenamed;
/*      */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;
/*      */ import org.spongepowered.asm.util.Annotations;
/*      */ import org.spongepowered.asm.util.Bytecode;
/*      */ import org.spongepowered.asm.util.ConstraintParser;
/*      */ import org.spongepowered.asm.util.ITokenProvider;
/*      */ import org.spongepowered.asm.util.perf.Profiler;
/*      */ import org.spongepowered.asm.util.throwables.ConstraintViolationException;
/*      */ import org.spongepowered.asm.util.throwables.InvalidConstraintException;
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
/*      */ class MixinApplicatorStandard
/*      */ {
/*   82 */   protected static final List<Class<? extends Annotation>> CONSTRAINED_ANNOTATIONS = (List<Class<? extends Annotation>>)ImmutableList.of(Overwrite.class, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
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
/*      */   enum ApplicatorPass
/*      */   {
/*   99 */     MAIN,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  104 */     PREINJECT,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  109 */     INJECT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   enum InitialiserInjectionMode
/*      */   {
/*  120 */     DEFAULT,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  126 */     SAFE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class Range
/*      */   {
/*      */     final int start;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int end;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int marker;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Range(int start, int end, int marker) {
/*  156 */       this.start = start;
/*  157 */       this.end = end;
/*  158 */       this.marker = marker;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isValid() {
/*  168 */       return (this.start != 0 && this.end != 0 && this.end >= this.start);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean contains(int value) {
/*  178 */       return (value >= this.start && value <= this.end);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean excludes(int value) {
/*  187 */       return (value < this.start || value > this.end);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String toString() {
/*  195 */       return String.format("Range[%d-%d,%d,valid=%s)", new Object[] { Integer.valueOf(this.start), Integer.valueOf(this.end), Integer.valueOf(this.marker), Boolean.valueOf(isValid()) });
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
/*  208 */   protected static final int[] INITIALISER_OPCODE_BLACKLIST = new int[] { 177, 21, 22, 23, 24, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 79, 80, 81, 82, 83, 84, 85, 86 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  218 */   protected final Logger logger = LogManager.getLogger("mixin");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final TargetClassContext context;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String targetName;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final ClassNode targetClass;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  238 */   protected final Profiler profiler = MixinEnvironment.getProfiler();
/*      */   
/*      */   MixinApplicatorStandard(TargetClassContext context) {
/*  241 */     this.context = context;
/*  242 */     this.targetName = context.getClassName();
/*  243 */     this.targetClass = context.getClassNode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void apply(SortedSet<MixinInfo> mixins) {
/*  250 */     List<MixinTargetContext> mixinContexts = new ArrayList<MixinTargetContext>();
/*      */     
/*  252 */     for (MixinInfo mixin : mixins) {
/*  253 */       this.logger.log(mixin.getLoggingLevel(), "Mixing {} from {} into {}", new Object[] { mixin.getName(), mixin.getParent(), this.targetName });
/*  254 */       mixinContexts.add(mixin.createContextFor(this.context));
/*      */     } 
/*      */     
/*  257 */     MixinTargetContext current = null;
/*      */     
/*      */     try {
/*  260 */       for (MixinTargetContext context : mixinContexts) {
/*  261 */         (current = context).preApply(this.targetName, this.targetClass);
/*      */       }
/*      */       
/*  264 */       for (ApplicatorPass pass : ApplicatorPass.values()) {
/*  265 */         Profiler.Section timer = this.profiler.begin(new String[] { "pass", pass.name().toLowerCase() });
/*  266 */         for (MixinTargetContext context : mixinContexts) {
/*  267 */           applyMixin(current = context, pass);
/*      */         }
/*  269 */         timer.end();
/*      */       } 
/*      */       
/*  272 */       for (MixinTargetContext context : mixinContexts) {
/*  273 */         (current = context).postApply(this.targetName, this.targetClass);
/*      */       }
/*  275 */     } catch (InvalidMixinException ex) {
/*  276 */       throw ex;
/*  277 */     } catch (Exception ex) {
/*  278 */       throw new InvalidMixinException(current, "Unexpecteded " + ex.getClass().getSimpleName() + " whilst applying the mixin class: " + ex
/*  279 */           .getMessage(), ex);
/*      */     } 
/*      */     
/*  282 */     applySourceMap(this.context);
/*  283 */     this.context.processDebugTasks();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void applyMixin(MixinTargetContext mixin, ApplicatorPass pass) {
/*  292 */     switch (pass) {
/*      */       case MAIN:
/*  294 */         applySignature(mixin);
/*  295 */         applyInterfaces(mixin);
/*  296 */         applyAttributes(mixin);
/*  297 */         applyAnnotations(mixin);
/*  298 */         applyFields(mixin);
/*  299 */         applyMethods(mixin);
/*  300 */         applyInitialisers(mixin);
/*      */         return;
/*      */       
/*      */       case PREINJECT:
/*  304 */         prepareInjections(mixin);
/*      */         return;
/*      */       
/*      */       case INJECT:
/*  308 */         applyAccessors(mixin);
/*  309 */         applyInjections(mixin);
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  314 */     throw new IllegalStateException("Invalid pass specified " + pass);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applySignature(MixinTargetContext mixin) {
/*  319 */     this.context.mergeSignature(mixin.getSignature());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyInterfaces(MixinTargetContext mixin) {
/*  328 */     for (String interfaceName : mixin.getInterfaces()) {
/*  329 */       if (!this.targetClass.interfaces.contains(interfaceName)) {
/*  330 */         this.targetClass.interfaces.add(interfaceName);
/*  331 */         mixin.getTargetClassInfo().addInterface(interfaceName);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyAttributes(MixinTargetContext mixin) {
/*  342 */     if (mixin.shouldSetSourceFile()) {
/*  343 */       this.targetClass.sourceFile = mixin.getSourceFile();
/*      */     }
/*  345 */     this.targetClass.version = Math.max(this.targetClass.version, mixin.getMinRequiredClassVersion());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyAnnotations(MixinTargetContext mixin) {
/*  354 */     ClassNode sourceClass = mixin.getClassNode();
/*  355 */     Bytecode.mergeAnnotations(sourceClass, this.targetClass);
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
/*      */   protected void applyFields(MixinTargetContext mixin) {
/*  367 */     mergeShadowFields(mixin);
/*  368 */     mergeNewFields(mixin);
/*      */   }
/*      */   
/*      */   protected void mergeShadowFields(MixinTargetContext mixin) {
/*  372 */     for (Map.Entry<FieldNode, ClassInfo.Field> entry : mixin.getShadowFields()) {
/*  373 */       FieldNode shadow = entry.getKey();
/*  374 */       FieldNode target = findTargetField(shadow);
/*  375 */       if (target != null) {
/*  376 */         Bytecode.mergeAnnotations(shadow, target);
/*      */ 
/*      */         
/*  379 */         if (((ClassInfo.Field)entry.getValue()).isDecoratedMutable() && !Bytecode.hasFlag(target, 2)) {
/*  380 */           target.access &= 0xFFFFFFEF;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void mergeNewFields(MixinTargetContext mixin) {
/*  387 */     for (FieldNode field : mixin.getFields()) {
/*  388 */       FieldNode target = findTargetField(field);
/*  389 */       if (target == null)
/*      */       {
/*  391 */         this.targetClass.fields.add(field);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyMethods(MixinTargetContext mixin) {
/*  402 */     for (MethodNode shadow : mixin.getShadowMethods()) {
/*  403 */       applyShadowMethod(mixin, shadow);
/*      */     }
/*      */     
/*  406 */     for (MethodNode mixinMethod : mixin.getMethods()) {
/*  407 */       applyNormalMethod(mixin, mixinMethod);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void applyShadowMethod(MixinTargetContext mixin, MethodNode shadow) {
/*  412 */     MethodNode target = findTargetMethod(shadow);
/*  413 */     if (target != null) {
/*  414 */       Bytecode.mergeAnnotations(shadow, target);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyNormalMethod(MixinTargetContext mixin, MethodNode mixinMethod) {
/*  420 */     mixin.transformMethod(mixinMethod);
/*      */     
/*  422 */     if (!mixinMethod.name.startsWith("<")) {
/*  423 */       checkMethodVisibility(mixin, mixinMethod);
/*  424 */       checkMethodConstraints(mixin, mixinMethod);
/*  425 */       mergeMethod(mixin, mixinMethod);
/*  426 */     } else if ("<clinit>".equals(mixinMethod.name)) {
/*      */       
/*  428 */       appendInsns(mixin, mixinMethod);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void mergeMethod(MixinTargetContext mixin, MethodNode method) {
/*  439 */     boolean isOverwrite = (Annotations.getVisible(method, Overwrite.class) != null);
/*  440 */     MethodNode target = findTargetMethod(method);
/*      */     
/*  442 */     if (target != null) {
/*  443 */       if (isAlreadyMerged(mixin, method, isOverwrite, target)) {
/*      */         return;
/*      */       }
/*      */       
/*  447 */       AnnotationNode intrinsic = Annotations.getInvisible(method, Intrinsic.class);
/*  448 */       if (intrinsic != null) {
/*  449 */         if (mergeIntrinsic(mixin, method, isOverwrite, target, intrinsic)) {
/*  450 */           mixin.getTarget().methodMerged(method);
/*      */           return;
/*      */         } 
/*      */       } else {
/*  454 */         if (mixin.requireOverwriteAnnotations() && !isOverwrite) {
/*  455 */           throw new InvalidMixinException(mixin, 
/*  456 */               String.format("%s%s in %s cannot overwrite method in %s because @Overwrite is required by the parent configuration", new Object[] {
/*  457 */                   method.name, method.desc, mixin, mixin.getTarget().getClassName()
/*      */                 }));
/*      */         }
/*  460 */         this.targetClass.methods.remove(target);
/*      */       } 
/*  462 */     } else if (isOverwrite) {
/*  463 */       throw new InvalidMixinException(mixin, String.format("Overwrite target \"%s\" was not located in target class %s", new Object[] { method.name, mixin
/*  464 */               .getTargetClassRef() }));
/*      */     } 
/*      */     
/*  467 */     this.targetClass.methods.add(method);
/*  468 */     mixin.methodMerged(method);
/*      */     
/*  470 */     if (method.signature != null) {
/*  471 */       SignatureVisitor sv = mixin.getSignature().getRemapper();
/*  472 */       (new SignatureReader(method.signature)).accept(sv);
/*  473 */       method.signature = sv.toString();
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
/*      */   protected boolean isAlreadyMerged(MixinTargetContext mixin, MethodNode method, boolean isOverwrite, MethodNode target) {
/*  489 */     AnnotationNode merged = Annotations.getVisible(target, MixinMerged.class);
/*  490 */     if (merged == null) {
/*  491 */       if (Annotations.getVisible(target, Final.class) != null) {
/*  492 */         this.logger.warn("Overwrite prohibited for @Final method {} in {}. Skipping method.", new Object[] { method.name, mixin });
/*  493 */         return true;
/*      */       } 
/*  495 */       return false;
/*      */     } 
/*      */     
/*  498 */     String sessionId = (String)Annotations.getValue(merged, "sessionId");
/*      */     
/*  500 */     if (!this.context.getSessionId().equals(sessionId)) {
/*  501 */       throw new ClassFormatError("Invalid @MixinMerged annotation found in" + mixin + " at " + method.name + " in " + this.targetClass.name);
/*      */     }
/*      */     
/*  504 */     if (Bytecode.hasFlag(target, 4160) && 
/*  505 */       Bytecode.hasFlag(method, 4160)) {
/*  506 */       if (mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/*  507 */         this.logger.warn("Synthetic bridge method clash for {} in {}", new Object[] { method.name, mixin });
/*      */       }
/*  509 */       return true;
/*      */     } 
/*      */     
/*  512 */     String owner = (String)Annotations.getValue(merged, "mixin");
/*  513 */     int priority = ((Integer)Annotations.getValue(merged, "priority")).intValue();
/*      */     
/*  515 */     if (priority >= mixin.getPriority() && !owner.equals(mixin.getClassName())) {
/*  516 */       this.logger.warn("Method overwrite conflict for {} in {}, previously written by {}. Skipping method.", new Object[] { method.name, mixin, owner });
/*  517 */       return true;
/*      */     } 
/*      */     
/*  520 */     if (Annotations.getVisible(target, Final.class) != null) {
/*  521 */       this.logger.warn("Method overwrite conflict for @Final method {} in {} declared by {}. Skipping method.", new Object[] { method.name, mixin, owner });
/*  522 */       return true;
/*      */     } 
/*      */     
/*  525 */     return false;
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean mergeIntrinsic(MixinTargetContext mixin, MethodNode method, boolean isOverwrite, MethodNode target, AnnotationNode intrinsic) {
/*  544 */     if (isOverwrite) {
/*  545 */       throw new InvalidMixinException(mixin, "@Intrinsic is not compatible with @Overwrite, remove one of these annotations on " + method.name + " in " + mixin);
/*      */     }
/*      */ 
/*      */     
/*  549 */     String methodName = method.name + method.desc;
/*  550 */     if (Bytecode.hasFlag(method, 8)) {
/*  551 */       throw new InvalidMixinException(mixin, "@Intrinsic method cannot be static, found " + methodName + " in " + mixin);
/*      */     }
/*      */     
/*  554 */     if (!Bytecode.hasFlag(method, 4096)) {
/*  555 */       AnnotationNode renamed = Annotations.getVisible(method, MixinRenamed.class);
/*  556 */       if (renamed == null || !((Boolean)Annotations.getValue(renamed, "isInterfaceMember", Boolean.FALSE)).booleanValue()) {
/*  557 */         throw new InvalidMixinException(mixin, "@Intrinsic method must be prefixed interface method, no rename encountered on " + methodName + " in " + mixin);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  562 */     if (!((Boolean)Annotations.getValue(intrinsic, "displace", Boolean.FALSE)).booleanValue()) {
/*  563 */       this.logger.log(mixin.getLoggingLevel(), "Skipping Intrinsic mixin method {} for {}", new Object[] { methodName, mixin.getTargetClassRef() });
/*  564 */       return true;
/*      */     } 
/*      */     
/*  567 */     displaceIntrinsic(mixin, method, target);
/*  568 */     return false;
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
/*      */   protected void displaceIntrinsic(MixinTargetContext mixin, MethodNode method, MethodNode target) {
/*  581 */     String proxyName = "proxy+" + target.name;
/*      */     
/*  583 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/*  584 */       AbstractInsnNode insn = iter.next();
/*  585 */       if (insn instanceof MethodInsnNode && insn.getOpcode() != 184) {
/*  586 */         MethodInsnNode methodNode = (MethodInsnNode)insn;
/*  587 */         if (methodNode.owner.equals(this.targetClass.name) && methodNode.name.equals(target.name) && methodNode.desc.equals(target.desc)) {
/*  588 */           methodNode.name = proxyName;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  593 */     target.name = proxyName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void appendInsns(MixinTargetContext mixin, MethodNode method) {
/*  604 */     if (Type.getReturnType(method.desc) != Type.VOID_TYPE) {
/*  605 */       throw new IllegalArgumentException("Attempted to merge insns from a method which does not return void");
/*      */     }
/*      */     
/*  608 */     MethodNode target = findTargetMethod(method);
/*      */     
/*  610 */     if (target != null) {
/*  611 */       AbstractInsnNode returnNode = Bytecode.findInsn(target, 177);
/*      */       
/*  613 */       if (returnNode != null) {
/*  614 */         Iterator<AbstractInsnNode> injectIter = method.instructions.iterator();
/*  615 */         while (injectIter.hasNext()) {
/*  616 */           AbstractInsnNode insn = injectIter.next();
/*  617 */           if (!(insn instanceof LineNumberNode) && insn.getOpcode() != 177) {
/*  618 */             target.instructions.insertBefore(returnNode, insn);
/*      */           }
/*      */         } 
/*      */         
/*  622 */         target.maxLocals = Math.max(target.maxLocals, method.maxLocals);
/*  623 */         target.maxStack = Math.max(target.maxStack, method.maxStack);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  629 */     this.targetClass.methods.add(method);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyInitialisers(MixinTargetContext mixin) {
/*  640 */     MethodNode ctor = getConstructor(mixin);
/*  641 */     if (ctor == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  646 */     Deque<AbstractInsnNode> initialiser = getInitialiser(mixin, ctor);
/*  647 */     if (initialiser == null || initialiser.size() == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  652 */     for (MethodNode method : this.targetClass.methods) {
/*  653 */       if ("<init>".equals(method.name)) {
/*  654 */         method.maxStack = Math.max(method.maxStack, ctor.maxStack);
/*  655 */         injectInitialiser(mixin, method, initialiser);
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
/*      */   protected MethodNode getConstructor(MixinTargetContext mixin) {
/*  667 */     MethodNode ctor = null;
/*      */     
/*  669 */     for (MethodNode mixinMethod : mixin.getMethods()) {
/*  670 */       if ("<init>".equals(mixinMethod.name) && Bytecode.methodHasLineNumbers(mixinMethod)) {
/*  671 */         if (ctor == null) {
/*  672 */           ctor = mixinMethod;
/*      */           continue;
/*      */         } 
/*  675 */         this.logger.warn(String.format("Mixin %s has multiple constructors, %s was selected\n", new Object[] { mixin, ctor.desc }));
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  680 */     return ctor;
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
/*      */   private Range getConstructorRange(MethodNode ctor) {
/*  692 */     boolean lineNumberIsValid = false;
/*  693 */     AbstractInsnNode endReturn = null;
/*      */     
/*  695 */     int line = 0, start = 0, end = 0, superIndex = -1;
/*  696 */     for (Iterator<AbstractInsnNode> iter = ctor.instructions.iterator(); iter.hasNext(); ) {
/*  697 */       AbstractInsnNode insn = iter.next();
/*  698 */       if (insn instanceof LineNumberNode) {
/*  699 */         line = ((LineNumberNode)insn).line;
/*  700 */         lineNumberIsValid = true; continue;
/*  701 */       }  if (insn instanceof MethodInsnNode) {
/*  702 */         if (insn.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)insn).name) && superIndex == -1) {
/*  703 */           superIndex = ctor.instructions.indexOf(insn);
/*  704 */           start = line;
/*      */         }  continue;
/*  706 */       }  if (insn.getOpcode() == 181) {
/*  707 */         lineNumberIsValid = false; continue;
/*  708 */       }  if (insn.getOpcode() == 177) {
/*  709 */         if (lineNumberIsValid) {
/*  710 */           end = line; continue;
/*      */         } 
/*  712 */         end = start;
/*  713 */         endReturn = insn;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  718 */     if (endReturn != null) {
/*  719 */       LabelNode label = new LabelNode(new Label());
/*  720 */       ctor.instructions.insertBefore(endReturn, (AbstractInsnNode)label);
/*  721 */       ctor.instructions.insertBefore(endReturn, (AbstractInsnNode)new LineNumberNode(start, label));
/*      */     } 
/*      */     
/*  724 */     return new Range(start, end, superIndex);
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
/*      */ 
/*      */   
/*      */   protected final Deque<AbstractInsnNode> getInitialiser(MixinTargetContext mixin, MethodNode ctor) {
/*  742 */     Range init = getConstructorRange(ctor);
/*  743 */     if (!init.isValid()) {
/*  744 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  748 */     int line = 0;
/*  749 */     Deque<AbstractInsnNode> initialiser = new ArrayDeque<AbstractInsnNode>();
/*  750 */     boolean gatherNodes = false;
/*  751 */     int trimAtOpcode = -1;
/*  752 */     LabelNode optionalInsn = null;
/*  753 */     for (Iterator<AbstractInsnNode> iter = ctor.instructions.iterator(init.marker); iter.hasNext(); ) {
/*  754 */       AbstractInsnNode insn = iter.next();
/*  755 */       if (insn instanceof LineNumberNode) {
/*  756 */         line = ((LineNumberNode)insn).line;
/*  757 */         AbstractInsnNode next = ctor.instructions.get(ctor.instructions.indexOf(insn) + 1);
/*  758 */         if (line == init.end && next.getOpcode() != 177) {
/*  759 */           gatherNodes = true;
/*  760 */           trimAtOpcode = 177; continue;
/*      */         } 
/*  762 */         gatherNodes = init.excludes(line);
/*  763 */         trimAtOpcode = -1; continue;
/*      */       } 
/*  765 */       if (gatherNodes) {
/*  766 */         if (optionalInsn != null) {
/*  767 */           initialiser.add(optionalInsn);
/*  768 */           optionalInsn = null;
/*      */         } 
/*      */         
/*  771 */         if (insn instanceof LabelNode) {
/*  772 */           optionalInsn = (LabelNode)insn; continue;
/*      */         } 
/*  774 */         int opcode = insn.getOpcode();
/*  775 */         if (opcode == trimAtOpcode) {
/*  776 */           trimAtOpcode = -1;
/*      */           continue;
/*      */         } 
/*  779 */         for (int ivalidOp : INITIALISER_OPCODE_BLACKLIST) {
/*  780 */           if (opcode == ivalidOp)
/*      */           {
/*      */             
/*  783 */             throw new InvalidMixinException(mixin, "Cannot handle " + Bytecode.getOpcodeName(opcode) + " opcode (0x" + 
/*  784 */                 Integer.toHexString(opcode).toUpperCase() + ") in class initialiser");
/*      */           }
/*      */         } 
/*      */         
/*  788 */         initialiser.add(insn);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  794 */     AbstractInsnNode last = initialiser.peekLast();
/*  795 */     if (last != null && 
/*  796 */       last.getOpcode() != 181) {
/*  797 */       throw new InvalidMixinException(mixin, "Could not parse initialiser, expected 0xB5, found 0x" + 
/*  798 */           Integer.toHexString(last.getOpcode()) + " in " + mixin);
/*      */     }
/*      */ 
/*      */     
/*  802 */     return initialiser;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void injectInitialiser(MixinTargetContext mixin, MethodNode ctor, Deque<AbstractInsnNode> initialiser) {
/*  813 */     Map<LabelNode, LabelNode> labels = Bytecode.cloneLabels(ctor.instructions);
/*      */     
/*  815 */     AbstractInsnNode insn = findInitialiserInjectionPoint(mixin, ctor, initialiser);
/*  816 */     if (insn == null) {
/*  817 */       this.logger.warn("Failed to locate initialiser injection point in <init>{}, initialiser was not mixed in.", new Object[] { ctor.desc });
/*      */       
/*      */       return;
/*      */     } 
/*  821 */     for (AbstractInsnNode node : initialiser) {
/*  822 */       if (node instanceof LabelNode) {
/*      */         continue;
/*      */       }
/*  825 */       if (node instanceof org.spongepowered.asm.lib.tree.JumpInsnNode) {
/*  826 */         throw new InvalidMixinException(mixin, "Unsupported JUMP opcode in initialiser in " + mixin);
/*      */       }
/*  828 */       AbstractInsnNode imACloneNow = node.clone(labels);
/*  829 */       ctor.instructions.insert(insn, imACloneNow);
/*  830 */       insn = imACloneNow;
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
/*      */   protected AbstractInsnNode findInitialiserInjectionPoint(MixinTargetContext mixin, MethodNode ctor, Deque<AbstractInsnNode> initialiser) {
/*  844 */     Set<String> initialisedFields = new HashSet<String>();
/*  845 */     for (AbstractInsnNode initialiserInsn : initialiser) {
/*  846 */       if (initialiserInsn.getOpcode() == 181) {
/*  847 */         initialisedFields.add(fieldKey((FieldInsnNode)initialiserInsn));
/*      */       }
/*      */     } 
/*      */     
/*  851 */     InitialiserInjectionMode mode = getInitialiserInjectionMode(mixin.getEnvironment());
/*  852 */     String targetName = mixin.getTargetClassInfo().getName();
/*  853 */     String targetSuperName = mixin.getTargetClassInfo().getSuperName();
/*  854 */     AbstractInsnNode targetInsn = null;
/*      */     
/*  856 */     for (Iterator<AbstractInsnNode> iter = ctor.instructions.iterator(); iter.hasNext(); ) {
/*  857 */       AbstractInsnNode insn = iter.next();
/*  858 */       if (insn.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)insn).name)) {
/*  859 */         String owner = ((MethodInsnNode)insn).owner;
/*  860 */         if (owner.equals(targetName) || owner.equals(targetSuperName)) {
/*  861 */           targetInsn = insn;
/*  862 */           if (mode == InitialiserInjectionMode.SAFE)
/*      */             break; 
/*      */         }  continue;
/*      */       } 
/*  866 */       if (insn.getOpcode() == 181 && mode == InitialiserInjectionMode.DEFAULT) {
/*  867 */         String key = fieldKey((FieldInsnNode)insn);
/*  868 */         if (initialisedFields.contains(key)) {
/*  869 */           targetInsn = insn;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  874 */     return targetInsn;
/*      */   }
/*      */   
/*      */   private InitialiserInjectionMode getInitialiserInjectionMode(MixinEnvironment environment) {
/*  878 */     String strMode = environment.getOptionValue(MixinEnvironment.Option.INITIALISER_INJECTION_MODE);
/*  879 */     if (strMode == null) {
/*  880 */       return InitialiserInjectionMode.DEFAULT;
/*      */     }
/*      */     try {
/*  883 */       return InitialiserInjectionMode.valueOf(strMode.toUpperCase());
/*  884 */     } catch (Exception ex) {
/*  885 */       this.logger.warn("Could not parse unexpected value \"{}\" for mixin.initialiserInjectionMode, reverting to DEFAULT", new Object[] { strMode });
/*  886 */       return InitialiserInjectionMode.DEFAULT;
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String fieldKey(FieldInsnNode fieldNode) {
/*  891 */     return String.format("%s:%s", new Object[] { fieldNode.desc, fieldNode.name });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void prepareInjections(MixinTargetContext mixin) {
/*  900 */     mixin.prepareInjections();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyInjections(MixinTargetContext mixin) {
/*  909 */     mixin.applyInjections();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyAccessors(MixinTargetContext mixin) {
/*  918 */     List<MethodNode> accessorMethods = mixin.generateAccessors();
/*  919 */     for (MethodNode method : accessorMethods) {
/*  920 */       if (!method.name.startsWith("<")) {
/*  921 */         mergeMethod(mixin, method);
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
/*      */   protected void checkMethodVisibility(MixinTargetContext mixin, MethodNode mixinMethod) {
/*  933 */     if (Bytecode.hasFlag(mixinMethod, 8) && 
/*  934 */       !Bytecode.hasFlag(mixinMethod, 2) && 
/*  935 */       !Bytecode.hasFlag(mixinMethod, 4096) && 
/*  936 */       Annotations.getVisible(mixinMethod, Overwrite.class) == null) {
/*  937 */       throw new InvalidMixinException(mixin, 
/*  938 */           String.format("Mixin %s contains non-private static method %s", new Object[] { mixin, mixinMethod }));
/*      */     }
/*      */   }
/*      */   
/*      */   protected void applySourceMap(TargetClassContext context) {
/*  943 */     this.targetClass.sourceDebug = context.getSourceMap().toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkMethodConstraints(MixinTargetContext mixin, MethodNode method) {
/*  953 */     for (Class<? extends Annotation> annotationType : CONSTRAINED_ANNOTATIONS) {
/*  954 */       AnnotationNode annotation = Annotations.getVisible(method, annotationType);
/*  955 */       if (annotation != null) {
/*  956 */         checkConstraints(mixin, method, annotation);
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
/*      */   protected final void checkConstraints(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/*      */     try {
/*  971 */       ConstraintParser.Constraint constraint = ConstraintParser.parse(annotation);
/*      */       try {
/*  973 */         constraint.check((ITokenProvider)mixin.getEnvironment());
/*  974 */       } catch (ConstraintViolationException ex) {
/*  975 */         String message = String.format("Constraint violation: %s on %s in %s", new Object[] { ex.getMessage(), method, mixin });
/*  976 */         this.logger.warn(message);
/*  977 */         if (!mixin.getEnvironment().getOption(MixinEnvironment.Option.IGNORE_CONSTRAINTS)) {
/*  978 */           throw new InvalidMixinException(mixin, message, ex);
/*      */         }
/*      */       } 
/*  981 */     } catch (InvalidConstraintException ex) {
/*  982 */       throw new InvalidMixinException(mixin, ex.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final MethodNode findTargetMethod(MethodNode searchFor) {
/*  993 */     for (MethodNode target : this.targetClass.methods) {
/*  994 */       if (target.name.equals(searchFor.name) && target.desc.equals(searchFor.desc)) {
/*  995 */         return target;
/*      */       }
/*      */     } 
/*      */     
/*  999 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final FieldNode findTargetField(FieldNode searchFor) {
/* 1009 */     for (FieldNode target : this.targetClass.fields) {
/* 1010 */       if (target.name.equals(searchFor.name)) {
/* 1011 */         return target;
/*      */       }
/*      */     } 
/*      */     
/* 1015 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\transformer\MixinApplicatorStandard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */