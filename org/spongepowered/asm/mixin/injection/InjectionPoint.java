/*     */ package org.spongepowered.asm.mixin.injection;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.lang.annotation.ElementType;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.lang.annotation.Target;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.modify.AfterStoreLocal;
/*     */ import org.spongepowered.asm.mixin.injection.modify.BeforeLoadLocal;
/*     */ import org.spongepowered.asm.mixin.injection.points.AfterInvoke;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeFieldAccess;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeFinalReturn;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeInvoke;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeNew;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeReturn;
/*     */ import org.spongepowered.asm.mixin.injection.points.BeforeStringInvoke;
/*     */ import org.spongepowered.asm.mixin.injection.points.JumpInsnPoint;
/*     */ import org.spongepowered.asm.mixin.injection.points.MethodHead;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
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
/*     */ public abstract class InjectionPoint
/*     */ {
/*     */   public static final int DEFAULT_ALLOWED_SHIFT_BY = 0;
/*     */   public static final int MAX_ALLOWED_SHIFT_BY = 0;
/*     */   
/*     */   @Retention(RetentionPolicy.RUNTIME)
/*     */   @Target({ElementType.TYPE})
/*     */   public static @interface AtCode
/*     */   {
/*     */     String value();
/*     */   }
/*     */   
/*     */   public enum Selector
/*     */   {
/* 112 */     FIRST,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 117 */     LAST,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 124 */     ONE;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     public static final Selector DEFAULT = FIRST;
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   enum ShiftByViolationBehaviour
/*     */   {
/* 142 */     IGNORE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     WARN,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     ERROR;
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
/*     */ 
/*     */ 
/*     */   
/* 171 */   private static Map<String, Class<? extends InjectionPoint>> types = new HashMap<String, Class<? extends InjectionPoint>>();
/*     */   private final String slice;
/*     */   
/*     */   static {
/* 175 */     register((Class)BeforeFieldAccess.class);
/* 176 */     register((Class)BeforeInvoke.class);
/* 177 */     register((Class)BeforeNew.class);
/* 178 */     register((Class)BeforeReturn.class);
/* 179 */     register((Class)BeforeStringInvoke.class);
/* 180 */     register((Class)JumpInsnPoint.class);
/* 181 */     register((Class)MethodHead.class);
/* 182 */     register((Class)AfterInvoke.class);
/* 183 */     register((Class)BeforeLoadLocal.class);
/* 184 */     register((Class)AfterStoreLocal.class);
/* 185 */     register((Class)BeforeFinalReturn.class);
/* 186 */     register((Class)BeforeConstant.class);
/*     */   }
/*     */ 
/*     */   
/*     */   private final Selector selector;
/*     */   private final String id;
/*     */   
/*     */   protected InjectionPoint() {
/* 194 */     this("", Selector.DEFAULT, null);
/*     */   }
/*     */   
/*     */   protected InjectionPoint(InjectionPointData data) {
/* 198 */     this(data.getSlice(), data.getSelector(), data.getId());
/*     */   }
/*     */   
/*     */   public InjectionPoint(String slice, Selector selector, String id) {
/* 202 */     this.slice = slice;
/* 203 */     this.selector = selector;
/* 204 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getSlice() {
/* 208 */     return this.slice;
/*     */   }
/*     */   
/*     */   public Selector getSelector() {
/* 212 */     return this.selector;
/*     */   }
/*     */   
/*     */   public String getId() {
/* 216 */     return this.id;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 238 */     return String.format("@At(\"%s\")", new Object[] { getAtCode() });
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
/*     */   protected static AbstractInsnNode nextNode(InsnList insns, AbstractInsnNode insn) {
/* 250 */     int index = insns.indexOf(insn) + 1;
/* 251 */     if (index > 0 && index < insns.size()) {
/* 252 */       return insns.get(index);
/*     */     }
/* 254 */     return insn;
/*     */   }
/*     */ 
/*     */   
/*     */   static abstract class CompositeInjectionPoint
/*     */     extends InjectionPoint
/*     */   {
/*     */     protected final InjectionPoint[] components;
/*     */ 
/*     */     
/*     */     protected CompositeInjectionPoint(InjectionPoint... components) {
/* 265 */       if (components == null || components.length < 2) {
/* 266 */         throw new IllegalArgumentException("Must supply two or more component injection points for composite point!");
/*     */       }
/*     */       
/* 269 */       this.components = components;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 277 */       return "CompositeInjectionPoint(" + getClass().getSimpleName() + ")[" + Joiner.on(',').join((Object[])this.components) + "]";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Intersection
/*     */     extends CompositeInjectionPoint
/*     */   {
/*     */     public Intersection(InjectionPoint... points) {
/* 288 */       super(points);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 294 */       boolean found = false;
/*     */       
/* 296 */       ArrayList[] arrayOfArrayList = (ArrayList[])Array.newInstance(ArrayList.class, this.components.length);
/*     */       
/* 298 */       for (int i = 0; i < this.components.length; i++) {
/* 299 */         arrayOfArrayList[i] = new ArrayList();
/* 300 */         this.components[i].find(desc, insns, arrayOfArrayList[i]);
/*     */       } 
/*     */       
/* 303 */       ArrayList<AbstractInsnNode> alpha = arrayOfArrayList[0];
/* 304 */       for (int nodeIndex = 0; nodeIndex < alpha.size(); nodeIndex++) {
/* 305 */         AbstractInsnNode node = alpha.get(nodeIndex);
/* 306 */         boolean in = true;
/*     */         
/* 308 */         for (int b = 1; b < arrayOfArrayList.length && 
/* 309 */           arrayOfArrayList[b].contains(node); b++);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 314 */         if (in) {
/*     */ 
/*     */ 
/*     */           
/* 318 */           nodes.add(node);
/* 319 */           found = true;
/*     */         } 
/*     */       } 
/* 322 */       return found;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Union
/*     */     extends CompositeInjectionPoint
/*     */   {
/*     */     public Union(InjectionPoint... points) {
/* 333 */       super(points);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 338 */       LinkedHashSet<AbstractInsnNode> allNodes = new LinkedHashSet<AbstractInsnNode>();
/*     */       
/* 340 */       for (int i = 0; i < this.components.length; i++) {
/* 341 */         this.components[i].find(desc, insns, allNodes);
/*     */       }
/*     */       
/* 344 */       nodes.addAll(allNodes);
/*     */       
/* 346 */       return (allNodes.size() > 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static final class Shift
/*     */     extends InjectionPoint
/*     */   {
/*     */     private final InjectionPoint input;
/*     */     
/*     */     private final int shift;
/*     */ 
/*     */     
/*     */     public Shift(InjectionPoint input, int shift) {
/* 360 */       if (input == null) {
/* 361 */         throw new IllegalArgumentException("Must supply an input injection point for SHIFT");
/*     */       }
/*     */       
/* 364 */       this.input = input;
/* 365 */       this.shift = shift;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 373 */       return "InjectionPoint(" + getClass().getSimpleName() + ")[" + this.input + "]";
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 378 */       List<AbstractInsnNode> list = (nodes instanceof List) ? (List<AbstractInsnNode>)nodes : new ArrayList<AbstractInsnNode>(nodes);
/*     */       
/* 380 */       this.input.find(desc, insns, nodes);
/*     */       
/* 382 */       for (int i = 0; i < list.size(); i++) {
/* 383 */         list.set(i, insns.get(insns.indexOf(list.get(i)) + this.shift));
/*     */       }
/*     */       
/* 386 */       if (nodes != list) {
/* 387 */         nodes.clear();
/* 388 */         nodes.addAll(list);
/*     */       } 
/*     */       
/* 391 */       return (nodes.size() > 0);
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
/*     */   public static InjectionPoint and(InjectionPoint... operands) {
/* 403 */     return new Intersection(operands);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InjectionPoint or(InjectionPoint... operands) {
/* 414 */     return new Union(operands);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InjectionPoint after(InjectionPoint point) {
/* 425 */     return new Shift(point, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InjectionPoint before(InjectionPoint point) {
/* 436 */     return new Shift(point, -1);
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
/*     */   public static InjectionPoint shift(InjectionPoint point, int count) {
/* 448 */     return new Shift(point, count);
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
/*     */   public static List<InjectionPoint> parse(IInjectionPointContext owner, List<AnnotationNode> ats) {
/* 462 */     return parse(owner.getContext(), owner.getMethod(), owner.getAnnotation(), ats);
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
/*     */   public static List<InjectionPoint> parse(IMixinContext context, MethodNode method, AnnotationNode parent, List<AnnotationNode> ats) {
/* 478 */     ImmutableList.Builder<InjectionPoint> injectionPoints = ImmutableList.builder();
/* 479 */     for (AnnotationNode at : ats) {
/* 480 */       InjectionPoint injectionPoint = parse(context, method, parent, at);
/* 481 */       if (injectionPoint != null) {
/* 482 */         injectionPoints.add(injectionPoint);
/*     */       }
/*     */     } 
/* 485 */     return (List<InjectionPoint>)injectionPoints.build();
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
/*     */   public static InjectionPoint parse(IInjectionPointContext owner, At at) {
/* 498 */     return parse(owner.getContext(), owner.getMethod(), owner.getAnnotation(), at.value(), at.shift(), at.by(), 
/* 499 */         Arrays.asList(at.args()), at.target(), at.slice(), at.ordinal(), at.opcode(), at.id());
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
/*     */   public static InjectionPoint parse(IMixinContext context, MethodNode method, AnnotationNode parent, At at) {
/* 514 */     return parse(context, method, parent, at.value(), at.shift(), at.by(), Arrays.asList(at.args()), at.target(), at.slice(), at
/* 515 */         .ordinal(), at.opcode(), at.id());
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
/*     */   public static InjectionPoint parse(IInjectionPointContext owner, AnnotationNode node) {
/* 529 */     return parse(owner.getContext(), owner.getMethod(), owner.getAnnotation(), node);
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
/*     */   public static InjectionPoint parse(IMixinContext context, MethodNode method, AnnotationNode parent, AnnotationNode node) {
/*     */     ImmutableList immutableList;
/* 545 */     String at = (String)Annotations.getValue(node, "value");
/* 546 */     List<String> args = (List<String>)Annotations.getValue(node, "args");
/* 547 */     String target = (String)Annotations.getValue(node, "target", "");
/* 548 */     String slice = (String)Annotations.getValue(node, "slice", "");
/* 549 */     At.Shift shift = (At.Shift)Annotations.getValue(node, "shift", At.Shift.class, At.Shift.NONE);
/* 550 */     int by = ((Integer)Annotations.getValue(node, "by", Integer.valueOf(0))).intValue();
/* 551 */     int ordinal = ((Integer)Annotations.getValue(node, "ordinal", Integer.valueOf(-1))).intValue();
/* 552 */     int opcode = ((Integer)Annotations.getValue(node, "opcode", Integer.valueOf(0))).intValue();
/* 553 */     String id = (String)Annotations.getValue(node, "id");
/*     */     
/* 555 */     if (args == null) {
/* 556 */       immutableList = ImmutableList.of();
/*     */     }
/*     */     
/* 559 */     return parse(context, method, parent, at, shift, by, (List<String>)immutableList, target, slice, ordinal, opcode, id);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static InjectionPoint parse(IMixinContext context, MethodNode method, AnnotationNode parent, String at, At.Shift shift, int by, List<String> args, String target, String slice, int ordinal, int opcode, String id) {
/* 584 */     InjectionPointData data = new InjectionPointData(context, method, parent, at, args, target, slice, ordinal, opcode, id);
/* 585 */     Class<? extends InjectionPoint> ipClass = findClass(context, data);
/* 586 */     InjectionPoint point = create(context, data, ipClass);
/* 587 */     return shift(context, method, parent, point, shift, by);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Class<? extends InjectionPoint> findClass(IMixinContext context, InjectionPointData data) {
/* 592 */     String type = data.getType();
/* 593 */     Class<? extends InjectionPoint> ipClass = types.get(type);
/* 594 */     if (ipClass == null) {
/* 595 */       if (type.matches("^([A-Za-z_][A-Za-z0-9_]*\\.)+[A-Za-z_][A-Za-z0-9_]*$")) {
/*     */         try {
/* 597 */           ipClass = (Class)Class.forName(type);
/* 598 */           types.put(type, ipClass);
/* 599 */         } catch (Exception ex) {
/* 600 */           throw new InvalidInjectionException(context, data + " could not be loaded or is not a valid InjectionPoint", ex);
/*     */         } 
/*     */       } else {
/* 603 */         throw new InvalidInjectionException(context, data + " is not a valid injection point specifier");
/*     */       } 
/*     */     }
/* 606 */     return ipClass;
/*     */   }
/*     */   
/*     */   private static InjectionPoint create(IMixinContext context, InjectionPointData data, Class<? extends InjectionPoint> ipClass) {
/* 610 */     Constructor<? extends InjectionPoint> ipCtor = null;
/*     */     try {
/* 612 */       ipCtor = ipClass.getDeclaredConstructor(new Class[] { InjectionPointData.class });
/* 613 */       ipCtor.setAccessible(true);
/* 614 */     } catch (NoSuchMethodException ex) {
/* 615 */       throw new InvalidInjectionException(context, ipClass.getName() + " must contain a constructor which accepts an InjectionPointData", ex);
/*     */     } 
/*     */     
/* 618 */     InjectionPoint point = null;
/*     */     try {
/* 620 */       point = ipCtor.newInstance(new Object[] { data });
/* 621 */     } catch (Exception ex) {
/* 622 */       throw new InvalidInjectionException(context, "Error whilst instancing injection point " + ipClass.getName() + " for " + data.getAt(), ex);
/*     */     } 
/*     */     
/* 625 */     return point;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static InjectionPoint shift(IMixinContext context, MethodNode method, AnnotationNode parent, InjectionPoint point, At.Shift shift, int by) {
/* 631 */     if (point != null) {
/* 632 */       if (shift == At.Shift.BEFORE)
/* 633 */         return before(point); 
/* 634 */       if (shift == At.Shift.AFTER)
/* 635 */         return after(point); 
/* 636 */       if (shift == At.Shift.BY) {
/* 637 */         validateByValue(context, method, parent, point, by);
/* 638 */         return shift(point, by);
/*     */       } 
/*     */     } 
/*     */     
/* 642 */     return point;
/*     */   }
/*     */   
/*     */   private static void validateByValue(IMixinContext context, MethodNode method, AnnotationNode parent, InjectionPoint point, int by) {
/* 646 */     MixinEnvironment env = context.getMixin().getConfig().getEnvironment();
/* 647 */     ShiftByViolationBehaviour err = (ShiftByViolationBehaviour)env.getOption(MixinEnvironment.Option.SHIFT_BY_VIOLATION_BEHAVIOUR, ShiftByViolationBehaviour.WARN);
/* 648 */     if (err == ShiftByViolationBehaviour.IGNORE) {
/*     */       return;
/*     */     }
/*     */     
/* 652 */     int allowed = 0;
/* 653 */     if (context instanceof MixinTargetContext) {
/* 654 */       allowed = ((MixinTargetContext)context).getMaxShiftByValue();
/*     */     }
/*     */     
/* 657 */     if (by <= allowed) {
/*     */       return;
/*     */     }
/*     */     
/* 661 */     String message = String.format("@%s(%s) Shift.BY=%d on %s::%s exceeds the maximum allowed value %d.", new Object[] { Bytecode.getSimpleName(parent), point, 
/* 662 */           Integer.valueOf(by), context, method.name, Integer.valueOf(allowed) });
/*     */     
/* 664 */     if (err == ShiftByViolationBehaviour.WARN) {
/* 665 */       LogManager.getLogger("mixin").warn("{} Increase the value of maxShiftBy to suppress this warning.", new Object[] { message });
/*     */       
/*     */       return;
/*     */     } 
/* 669 */     throw new InvalidInjectionException(context, message);
/*     */   }
/*     */   
/*     */   protected String getAtCode() {
/* 673 */     AtCode code = getClass().<AtCode>getAnnotation(AtCode.class);
/* 674 */     return (code == null) ? getClass().getName() : code.value();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(Class<? extends InjectionPoint> type) {
/* 684 */     AtCode code = type.<AtCode>getAnnotation(AtCode.class);
/* 685 */     if (code == null) {
/* 686 */       throw new IllegalArgumentException("Injection point class " + type + " is not annotated with @AtCode");
/*     */     }
/*     */     
/* 689 */     Class<? extends InjectionPoint> existing = types.get(code.value());
/* 690 */     if (existing != null && !existing.equals(type)) {
/* 691 */       LogManager.getLogger("mixin").debug("Overriding InjectionPoint {} with {} (previously {})", new Object[] { code.value(), type.getName(), existing
/* 692 */             .getName() });
/*     */     }
/*     */     
/* 695 */     types.put(code.value(), type);
/*     */   }
/*     */   
/*     */   public abstract boolean find(String paramString, InsnList paramInsnList, Collection<AbstractInsnNode> paramCollection);
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\injection\InjectionPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */