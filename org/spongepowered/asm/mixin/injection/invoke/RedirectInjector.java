/*     */ package org.spongepowered.asm.mixin.injection.invoke;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ObjectArrays;
/*     */ import com.google.common.primitives.Ints;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.InsnNode;
/*     */ import org.spongepowered.asm.lib.tree.JumpInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.LabelNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.TypeInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeNew;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.util.Annotations;
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
/*     */ public class RedirectInjector
/*     */   extends InvokeInjector
/*     */ {
/*     */   private static final String KEY_NOMINATORS = "nominators";
/*     */   private static final String KEY_WILD = "wildcard";
/*     */   private static final String KEY_FUZZ = "fuzz";
/*     */   private static final String KEY_OPCODE = "opcode";
/*     */   protected Meta meta;
/*     */   
/*     */   class Meta
/*     */   {
/*     */     public static final String KEY = "redirector";
/*     */     final int priority;
/*     */     final boolean isFinal;
/*     */     final String name;
/*     */     final String desc;
/*     */     
/*     */     public Meta(int priority, boolean isFinal, String name, String desc) {
/* 117 */       this.priority = priority;
/* 118 */       this.isFinal = isFinal;
/* 119 */       this.name = name;
/* 120 */       this.desc = desc;
/*     */     }
/*     */     
/*     */     RedirectInjector getOwner() {
/* 124 */       return RedirectInjector.this;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class ConstructorRedirectData
/*     */   {
/*     */     public static final String KEY = "ctor";
/*     */ 
/*     */     
/* 136 */     public int injected = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 142 */   private Map<BeforeNew, ConstructorRedirectData> ctorRedirectors = new HashMap<BeforeNew, ConstructorRedirectData>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RedirectInjector(InjectionInfo info) {
/* 148 */     this(info, "@Redirect");
/*     */   }
/*     */   
/*     */   protected RedirectInjector(InjectionInfo info, String annotationType) {
/* 152 */     super(info, annotationType);
/*     */     
/* 154 */     int priority = info.getContext().getPriority();
/* 155 */     boolean isFinal = (Annotations.getVisible(this.methodNode, Final.class) != null);
/* 156 */     this.meta = new Meta(priority, isFinal, this.info.toString(), this.methodNode.desc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkTarget(Target target) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addTargetNode(Target target, List<InjectionNodes.InjectionNode> myNodes, AbstractInsnNode insn, Set<InjectionPoint> nominators) {
/* 170 */     InjectionNodes.InjectionNode node = target.getInjectionNode(insn);
/* 171 */     ConstructorRedirectData ctorData = null;
/* 172 */     int fuzz = 8;
/* 173 */     int opcode = 0;
/*     */     
/* 175 */     if (node != null) {
/* 176 */       Meta other = (Meta)node.getDecoration("redirector");
/*     */       
/* 178 */       if (other != null && other.getOwner() != this) {
/* 179 */         if (other.priority >= this.meta.priority) {
/* 180 */           Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[] { this.annotationType, this.info, 
/* 181 */                 Integer.valueOf(this.meta.priority), other.name, Integer.valueOf(other.priority) }); return;
/*     */         } 
/* 183 */         if (other.isFinal) {
/* 184 */           throw new InvalidInjectionException(this.info, this.annotationType + " conflict: " + this + " failed because target was already remapped by " + other.name);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 190 */     for (InjectionPoint ip : nominators) {
/* 191 */       if (ip instanceof BeforeNew && !((BeforeNew)ip).hasDescriptor()) {
/* 192 */         ctorData = getCtorRedirect((BeforeNew)ip); continue;
/* 193 */       }  if (ip instanceof BeforeFieldAccess) {
/* 194 */         BeforeFieldAccess bfa = (BeforeFieldAccess)ip;
/* 195 */         fuzz = bfa.getFuzzFactor();
/* 196 */         opcode = bfa.getArrayOpcode();
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     InjectionNodes.InjectionNode targetNode = target.addInjectionNode(insn);
/* 201 */     targetNode.decorate("redirector", this.meta);
/* 202 */     targetNode.decorate("nominators", nominators);
/* 203 */     if (insn instanceof TypeInsnNode && insn.getOpcode() == 187) {
/* 204 */       targetNode.decorate("wildcard", Boolean.valueOf((ctorData != null)));
/* 205 */       targetNode.decorate("ctor", ctorData);
/*     */     } else {
/* 207 */       targetNode.decorate("fuzz", Integer.valueOf(fuzz));
/* 208 */       targetNode.decorate("opcode", Integer.valueOf(opcode));
/*     */     } 
/* 210 */     myNodes.add(targetNode);
/*     */   }
/*     */   
/*     */   private ConstructorRedirectData getCtorRedirect(BeforeNew ip) {
/* 214 */     ConstructorRedirectData ctorRedirect = this.ctorRedirectors.get(ip);
/* 215 */     if (ctorRedirect == null) {
/* 216 */       ctorRedirect = new ConstructorRedirectData();
/* 217 */       this.ctorRedirectors.put(ip, ctorRedirect);
/*     */     } 
/* 219 */     return ctorRedirect;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inject(Target target, InjectionNodes.InjectionNode node) {
/* 224 */     if (!preInject(node)) {
/*     */       return;
/*     */     }
/*     */     
/* 228 */     if (node.isReplaced()) {
/* 229 */       throw new UnsupportedOperationException("Redirector target failure for " + this.info);
/*     */     }
/*     */     
/* 232 */     if (node.getCurrentTarget() instanceof MethodInsnNode) {
/* 233 */       checkTargetForNode(target, node);
/* 234 */       injectAtInvoke(target, node);
/*     */       
/*     */       return;
/*     */     } 
/* 238 */     if (node.getCurrentTarget() instanceof FieldInsnNode) {
/* 239 */       checkTargetForNode(target, node);
/* 240 */       injectAtFieldAccess(target, node);
/*     */       
/*     */       return;
/*     */     } 
/* 244 */     if (node.getCurrentTarget() instanceof TypeInsnNode && node.getCurrentTarget().getOpcode() == 187) {
/* 245 */       if (!this.isStatic && target.isStatic) {
/* 246 */         throw new InvalidInjectionException(this.info, "non-static callback method " + this + " has a static target which is not supported");
/*     */       }
/* 248 */       injectAtConstructor(target, node);
/*     */       
/*     */       return;
/*     */     } 
/* 252 */     throw new InvalidInjectionException(this.info, this.annotationType + " annotation on is targetting an invalid insn in " + target + " in " + this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean preInject(InjectionNodes.InjectionNode node) {
/* 257 */     Meta other = (Meta)node.getDecoration("redirector");
/* 258 */     if (other.getOwner() != this) {
/* 259 */       Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[] { this.annotationType, this.info, 
/* 260 */             Integer.valueOf(this.meta.priority), other.name, Integer.valueOf(other.priority) });
/* 261 */       return false;
/*     */     } 
/* 263 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void postInject(Target target, InjectionNodes.InjectionNode node) {
/* 268 */     super.postInject(target, node);
/* 269 */     if (node.getOriginalTarget() instanceof TypeInsnNode && node.getOriginalTarget().getOpcode() == 187) {
/* 270 */       ConstructorRedirectData meta = (ConstructorRedirectData)node.getDecoration("ctor");
/* 271 */       boolean wildcard = ((Boolean)node.getDecoration("wildcard")).booleanValue();
/* 272 */       if (wildcard && meta.injected == 0) {
/* 273 */         throw new InvalidInjectionException(this.info, this.annotationType + " ctor invocation was not found in " + target);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void injectAtInvoke(Target target, InjectionNodes.InjectionNode node) {
/* 283 */     MethodInsnNode methodNode = (MethodInsnNode)node.getCurrentTarget();
/* 284 */     boolean targetIsStatic = (methodNode.getOpcode() == 184);
/* 285 */     Type ownerType = Type.getType("L" + methodNode.owner + ";");
/* 286 */     Type returnType = Type.getReturnType(methodNode.desc);
/* 287 */     Type[] args = Type.getArgumentTypes(methodNode.desc);
/* 288 */     Type[] stackVars = targetIsStatic ? args : (Type[])ObjectArrays.concat(ownerType, (Object[])args);
/* 289 */     boolean injectTargetParams = false;
/*     */     
/* 291 */     String desc = Bytecode.getDescriptor(stackVars, returnType);
/* 292 */     if (!desc.equals(this.methodNode.desc)) {
/* 293 */       String alternateDesc = Bytecode.getDescriptor((Type[])ObjectArrays.concat((Object[])stackVars, (Object[])target.arguments, Type.class), returnType);
/* 294 */       if (alternateDesc.equals(this.methodNode.desc)) {
/* 295 */         injectTargetParams = true;
/*     */       } else {
/* 297 */         throw new InvalidInjectionException(this.info, this.annotationType + " handler method " + this + " has an invalid signature, expected " + desc + " found " + this.methodNode.desc);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 302 */     InsnList insns = new InsnList();
/* 303 */     int extraLocals = Bytecode.getArgsSize(stackVars) + 1;
/* 304 */     int extraStack = 1;
/* 305 */     int[] argMap = storeArgs(target, stackVars, insns, 0);
/* 306 */     if (injectTargetParams) {
/* 307 */       int argSize = Bytecode.getArgsSize(target.arguments);
/* 308 */       extraLocals += argSize;
/* 309 */       extraStack += argSize;
/* 310 */       argMap = Ints.concat(new int[][] { argMap, target.getArgIndices() });
/*     */     } 
/* 312 */     AbstractInsnNode insn = invokeHandlerWithArgs(this.methodArgs, insns, argMap);
/* 313 */     target.replaceNode((AbstractInsnNode)methodNode, insn, insns);
/* 314 */     target.addToLocals(extraLocals);
/* 315 */     target.addToStack(extraStack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectAtFieldAccess(Target target, InjectionNodes.InjectionNode node) {
/* 322 */     FieldInsnNode fieldNode = (FieldInsnNode)node.getCurrentTarget();
/* 323 */     int opCode = fieldNode.getOpcode();
/* 324 */     Type ownerType = Type.getType("L" + fieldNode.owner + ";");
/* 325 */     Type fieldType = Type.getType(fieldNode.desc);
/*     */     
/* 327 */     int targetDimensions = (fieldType.getSort() == 9) ? fieldType.getDimensions() : 0;
/* 328 */     int handlerDimensions = (this.returnType.getSort() == 9) ? this.returnType.getDimensions() : 0;
/*     */     
/* 330 */     if (handlerDimensions > targetDimensions)
/* 331 */       throw new InvalidInjectionException(this.info, "Dimensionality of handler method is greater than target array on " + this); 
/* 332 */     if (handlerDimensions == 0 && targetDimensions > 0) {
/* 333 */       int fuzz = ((Integer)node.getDecoration("fuzz")).intValue();
/* 334 */       int opcode = ((Integer)node.getDecoration("opcode")).intValue();
/* 335 */       injectAtArrayField(target, fieldNode, opCode, ownerType, fieldType, fuzz, opcode);
/*     */     } else {
/* 337 */       injectAtScalarField(target, fieldNode, opCode, ownerType, fieldType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectAtArrayField(Target target, FieldInsnNode fieldNode, int opCode, Type ownerType, Type fieldType, int fuzz, int opcode) {
/* 345 */     Type elementType = fieldType.getElementType();
/* 346 */     if (opCode != 178 && opCode != 180)
/* 347 */       throw new InvalidInjectionException(this.info, "Unspported opcode " + Bytecode.getOpcodeName(opCode) + " for array access " + this.info); 
/* 348 */     if (this.returnType.getSort() != 0) {
/* 349 */       if (opcode != 190) {
/* 350 */         opcode = elementType.getOpcode(46);
/*     */       }
/* 352 */       AbstractInsnNode varNode = BeforeFieldAccess.findArrayNode(target.insns, fieldNode, opcode, fuzz);
/* 353 */       injectAtGetArray(target, fieldNode, varNode, ownerType, fieldType);
/*     */     } else {
/* 355 */       AbstractInsnNode varNode = BeforeFieldAccess.findArrayNode(target.insns, fieldNode, elementType.getOpcode(79), fuzz);
/* 356 */       injectAtSetArray(target, fieldNode, varNode, ownerType, fieldType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectAtGetArray(Target target, FieldInsnNode fieldNode, AbstractInsnNode varNode, Type ownerType, Type fieldType) {
/* 364 */     String handlerDesc = getGetArrayHandlerDescriptor(varNode, this.returnType, fieldType);
/* 365 */     boolean withArgs = checkDescriptor(handlerDesc, target, "array getter");
/* 366 */     injectArrayRedirect(target, fieldNode, varNode, withArgs, "array getter");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void injectAtSetArray(Target target, FieldInsnNode fieldNode, AbstractInsnNode varNode, Type ownerType, Type fieldType) {
/* 373 */     String handlerDesc = Bytecode.generateDescriptor(null, (Object[])getArrayArgs(fieldType, 1, new Type[] { fieldType.getElementType() }));
/* 374 */     boolean withArgs = checkDescriptor(handlerDesc, target, "array setter");
/* 375 */     injectArrayRedirect(target, fieldNode, varNode, withArgs, "array setter");
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void injectArrayRedirect(Target target, FieldInsnNode fieldNode, AbstractInsnNode varNode, boolean withArgs, String type) {
/* 392 */     if (varNode == null) {
/* 393 */       String advice = "";
/* 394 */       throw new InvalidInjectionException(this.info, "Array element " + this.annotationType + " on " + this + " could not locate a matching " + type + " instruction in " + target + ". " + advice);
/*     */     } 
/*     */ 
/*     */     
/* 398 */     if (!this.isStatic) {
/* 399 */       target.insns.insertBefore((AbstractInsnNode)fieldNode, (AbstractInsnNode)new VarInsnNode(25, 0));
/* 400 */       target.addToStack(1);
/*     */     } 
/*     */     
/* 403 */     InsnList invokeInsns = new InsnList();
/* 404 */     if (withArgs) {
/* 405 */       pushArgs(target.arguments, invokeInsns, target.getArgIndices(), 0, target.arguments.length);
/* 406 */       target.addToStack(Bytecode.getArgsSize(target.arguments));
/*     */     } 
/* 408 */     target.replaceNode(varNode, invokeHandler(invokeInsns), invokeInsns);
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
/*     */   public void injectAtScalarField(Target target, FieldInsnNode fieldNode, int opCode, Type ownerType, Type fieldType) {
/* 421 */     AbstractInsnNode invoke = null;
/* 422 */     InsnList insns = new InsnList();
/* 423 */     if (opCode == 178 || opCode == 180) {
/* 424 */       invoke = injectAtGetField(insns, target, fieldNode, (opCode == 178), ownerType, fieldType);
/* 425 */     } else if (opCode == 179 || opCode == 181) {
/* 426 */       invoke = injectAtPutField(insns, target, fieldNode, (opCode == 179), ownerType, fieldType);
/*     */     } else {
/* 428 */       throw new InvalidInjectionException(this.info, "Unspported opcode " + Bytecode.getOpcodeName(opCode) + " for " + this.info);
/*     */     } 
/*     */     
/* 431 */     target.replaceNode((AbstractInsnNode)fieldNode, invoke, insns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AbstractInsnNode injectAtGetField(InsnList insns, Target target, FieldInsnNode node, boolean staticField, Type owner, Type fieldType) {
/* 441 */     String handlerDesc = staticField ? Bytecode.generateDescriptor(fieldType, new Object[0]) : Bytecode.generateDescriptor(fieldType, new Object[] { owner });
/* 442 */     boolean withArgs = checkDescriptor(handlerDesc, target, "getter");
/*     */     
/* 444 */     if (!this.isStatic) {
/* 445 */       insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 446 */       if (!staticField) {
/* 447 */         insns.add((AbstractInsnNode)new InsnNode(95));
/*     */       }
/*     */     } 
/*     */     
/* 451 */     if (withArgs) {
/* 452 */       pushArgs(target.arguments, insns, target.getArgIndices(), 0, target.arguments.length);
/* 453 */       target.addToStack(Bytecode.getArgsSize(target.arguments));
/*     */     } 
/*     */     
/* 456 */     target.addToStack(this.isStatic ? 0 : 1);
/* 457 */     return invokeHandler(insns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AbstractInsnNode injectAtPutField(InsnList insns, Target target, FieldInsnNode node, boolean staticField, Type owner, Type fieldType) {
/* 467 */     String handlerDesc = staticField ? Bytecode.generateDescriptor(null, new Object[] { fieldType }) : Bytecode.generateDescriptor(null, new Object[] { owner, fieldType });
/* 468 */     boolean withArgs = checkDescriptor(handlerDesc, target, "setter");
/*     */     
/* 470 */     if (!this.isStatic) {
/* 471 */       if (staticField) {
/* 472 */         insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 473 */         insns.add((AbstractInsnNode)new InsnNode(95));
/*     */       } else {
/* 475 */         int marshallVar = target.allocateLocals(fieldType.getSize());
/* 476 */         insns.add((AbstractInsnNode)new VarInsnNode(fieldType.getOpcode(54), marshallVar));
/* 477 */         insns.add((AbstractInsnNode)new VarInsnNode(25, 0));
/* 478 */         insns.add((AbstractInsnNode)new InsnNode(95));
/* 479 */         insns.add((AbstractInsnNode)new VarInsnNode(fieldType.getOpcode(21), marshallVar));
/*     */       } 
/*     */     }
/*     */     
/* 483 */     if (withArgs) {
/* 484 */       pushArgs(target.arguments, insns, target.getArgIndices(), 0, target.arguments.length);
/* 485 */       target.addToStack(Bytecode.getArgsSize(target.arguments));
/*     */     } 
/*     */     
/* 488 */     target.addToStack((!this.isStatic && !staticField) ? 1 : 0);
/* 489 */     return invokeHandler(insns);
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
/*     */   
/*     */   protected boolean checkDescriptor(String desc, Target target, String type) {
/* 503 */     if (this.methodNode.desc.equals(desc)) {
/* 504 */       return false;
/*     */     }
/*     */     
/* 507 */     int pos = desc.indexOf(')');
/* 508 */     String alternateDesc = String.format("%s%s%s", new Object[] { desc.substring(0, pos), Joiner.on("").join((Object[])target.arguments), desc.substring(pos) });
/* 509 */     if (this.methodNode.desc.equals(alternateDesc)) {
/* 510 */       return true;
/*     */     }
/*     */     
/* 513 */     throw new InvalidInjectionException(this.info, this.annotationType + " method " + type + " " + this + " has an invalid signature. Expected " + desc + " but found " + this.methodNode.desc);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void injectAtConstructor(Target target, InjectionNodes.InjectionNode node) {
/* 518 */     ConstructorRedirectData meta = (ConstructorRedirectData)node.getDecoration("ctor");
/* 519 */     boolean wildcard = ((Boolean)node.getDecoration("wildcard")).booleanValue();
/*     */     
/* 521 */     TypeInsnNode newNode = (TypeInsnNode)node.getCurrentTarget();
/* 522 */     AbstractInsnNode dupNode = target.get(target.indexOf((AbstractInsnNode)newNode) + 1);
/* 523 */     MethodInsnNode initNode = target.findInitNodeFor(newNode);
/*     */     
/* 525 */     if (initNode == null) {
/* 526 */       if (!wildcard) {
/* 527 */         throw new InvalidInjectionException(this.info, this.annotationType + " ctor invocation was not found in " + target);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 533 */     boolean isAssigned = (dupNode.getOpcode() == 89);
/* 534 */     String desc = initNode.desc.replace(")V", ")L" + newNode.desc + ";");
/* 535 */     boolean withArgs = false;
/*     */     try {
/* 537 */       withArgs = checkDescriptor(desc, target, "constructor");
/* 538 */     } catch (InvalidInjectionException ex) {
/* 539 */       if (!wildcard) {
/* 540 */         throw ex;
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 545 */     if (isAssigned) {
/* 546 */       target.removeNode(dupNode);
/*     */     }
/*     */     
/* 549 */     if (this.isStatic) {
/* 550 */       target.removeNode((AbstractInsnNode)newNode);
/*     */     } else {
/* 552 */       target.replaceNode((AbstractInsnNode)newNode, (AbstractInsnNode)new VarInsnNode(25, 0));
/*     */     } 
/*     */     
/* 555 */     InsnList insns = new InsnList();
/* 556 */     if (withArgs) {
/* 557 */       pushArgs(target.arguments, insns, target.getArgIndices(), 0, target.arguments.length);
/* 558 */       target.addToStack(Bytecode.getArgsSize(target.arguments));
/*     */     } 
/*     */     
/* 561 */     invokeHandler(insns);
/*     */     
/* 563 */     if (isAssigned) {
/*     */ 
/*     */ 
/*     */       
/* 567 */       LabelNode nullCheckSucceeded = new LabelNode();
/* 568 */       insns.add((AbstractInsnNode)new InsnNode(89));
/* 569 */       insns.add((AbstractInsnNode)new JumpInsnNode(199, nullCheckSucceeded));
/* 570 */       throwException(insns, "java/lang/NullPointerException", this.annotationType + " constructor handler " + this + " returned null for " + newNode.desc
/* 571 */           .replace('/', '.'));
/* 572 */       insns.add((AbstractInsnNode)nullCheckSucceeded);
/* 573 */       target.addToStack(1);
/*     */     } else {
/*     */       
/* 576 */       insns.add((AbstractInsnNode)new InsnNode(87));
/*     */     } 
/*     */     
/* 579 */     target.replaceNode((AbstractInsnNode)initNode, insns);
/* 580 */     meta.injected++;
/*     */   }
/*     */   
/*     */   private static String getGetArrayHandlerDescriptor(AbstractInsnNode varNode, Type returnType, Type fieldType) {
/* 584 */     if (varNode != null && varNode.getOpcode() == 190) {
/* 585 */       return Bytecode.generateDescriptor(Type.INT_TYPE, (Object[])getArrayArgs(fieldType, 0, new Type[0]));
/*     */     }
/* 587 */     return Bytecode.generateDescriptor(returnType, (Object[])getArrayArgs(fieldType, 1, new Type[0]));
/*     */   }
/*     */   
/*     */   private static Type[] getArrayArgs(Type fieldType, int extraDimensions, Type... extra) {
/* 591 */     int dimensions = fieldType.getDimensions() + extraDimensions;
/* 592 */     Type[] args = new Type[dimensions + extra.length];
/* 593 */     for (int i = 0; i < args.length; i++) {
/* 594 */       args[i] = (i == 0) ? fieldType : ((i < dimensions) ? Type.INT_TYPE : extra[dimensions - i]);
/*     */     }
/* 596 */     return args;
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\injection\invoke\RedirectInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */