/*     */ package org.spongepowered.asm.mixin.injection.code;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.Deque;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.Slice;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
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
/*     */ public final class MethodSlice
/*     */ {
/*     */   private final ISliceContext owner;
/*     */   private final String id;
/*     */   private final InjectionPoint from;
/*     */   private final InjectionPoint to;
/*     */   private final String name;
/*     */   
/*     */   static final class InsnListSlice
/*     */     extends ReadOnlyInsnList
/*     */   {
/*     */     private final int start;
/*     */     private final int end;
/*     */     
/*     */     static class SliceIterator
/*     */       implements ListIterator<AbstractInsnNode>
/*     */     {
/*     */       private final ListIterator<AbstractInsnNode> iter;
/*     */       private int start;
/*     */       private int end;
/*     */       private int index;
/*     */       
/*     */       public SliceIterator(ListIterator<AbstractInsnNode> iter, int start, int end, int index) {
/*  86 */         this.iter = iter;
/*  87 */         this.start = start;
/*  88 */         this.end = end;
/*  89 */         this.index = index;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/*  97 */         return (this.index <= this.end && this.iter.hasNext());
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public AbstractInsnNode next() {
/* 105 */         if (this.index > this.end) {
/* 106 */           throw new NoSuchElementException();
/*     */         }
/* 108 */         this.index++;
/* 109 */         return this.iter.next();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean hasPrevious() {
/* 117 */         return (this.index > this.start);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public AbstractInsnNode previous() {
/* 125 */         if (this.index <= this.start) {
/* 126 */           throw new NoSuchElementException();
/*     */         }
/* 128 */         this.index--;
/* 129 */         return this.iter.previous();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public int nextIndex() {
/* 137 */         return this.index - this.start;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public int previousIndex() {
/* 145 */         return this.index - this.start - 1;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void remove() {
/* 153 */         throw new UnsupportedOperationException("Cannot remove insn from slice");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void set(AbstractInsnNode e) {
/* 161 */         throw new UnsupportedOperationException("Cannot set insn using slice");
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void add(AbstractInsnNode e) {
/* 169 */         throw new UnsupportedOperationException("Cannot add insn using slice");
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected InsnListSlice(InsnList inner, int start, int end) {
/* 179 */       super(inner);
/*     */ 
/*     */       
/* 182 */       this.start = start;
/* 183 */       this.end = end;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ListIterator<AbstractInsnNode> iterator() {
/* 192 */       return iterator(0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ListIterator<AbstractInsnNode> iterator(int index) {
/* 202 */       return new SliceIterator(super.iterator(this.start + index), this.start, this.end, this.start + index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AbstractInsnNode[] toArray() {
/* 211 */       AbstractInsnNode[] all = super.toArray();
/* 212 */       AbstractInsnNode[] subset = new AbstractInsnNode[size()];
/* 213 */       System.arraycopy(all, this.start, subset, 0, subset.length);
/* 214 */       return subset;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 223 */       return this.end - this.start + 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AbstractInsnNode getFirst() {
/* 232 */       return super.get(this.start);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AbstractInsnNode getLast() {
/* 241 */       return super.get(this.end);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AbstractInsnNode get(int index) {
/* 250 */       return super.get(this.start + index);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(AbstractInsnNode insn) {
/* 259 */       for (AbstractInsnNode node : toArray()) {
/* 260 */         if (node == insn) {
/* 261 */           return true;
/*     */         }
/*     */       } 
/* 264 */       return false;
/*     */     }
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
/*     */     public int indexOf(AbstractInsnNode insn) {
/* 277 */       int index = super.indexOf(insn);
/* 278 */       return (index >= this.start && index <= this.end) ? (index - this.start) : -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int realIndexOf(AbstractInsnNode insn) {
/* 288 */       return super.indexOf(insn);
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
/*     */   private MethodSlice(ISliceContext owner, String id, InjectionPoint from, InjectionPoint to) {
/* 330 */     if (from == null && to == null) {
/* 331 */       throw new InvalidSliceException(owner, String.format("%s is redundant. No 'from' or 'to' value specified", new Object[] { this }));
/*     */     }
/*     */     
/* 334 */     this.owner = owner;
/* 335 */     this.id = Strings.nullToEmpty(id);
/* 336 */     this.from = from;
/* 337 */     this.to = to;
/* 338 */     this.name = getSliceName(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getId() {
/* 345 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReadOnlyInsnList getSlice(MethodNode method) {
/* 355 */     int max = method.instructions.size() - 1;
/* 356 */     int start = find(method, this.from, 0, this.name + "(from)");
/* 357 */     int end = find(method, this.to, max, this.name + "(to)");
/*     */     
/* 359 */     if (start > end) {
/* 360 */       throw new InvalidSliceException(this.owner, String.format("%s is negative size. Range(%d -> %d)", new Object[] { describe(), Integer.valueOf(start), Integer.valueOf(end) }));
/*     */     }
/*     */     
/* 363 */     if (start < 0 || end < 0 || start > max || end > max) {
/* 364 */       throw new InjectionError("Unexpected critical error in " + this + ": out of bounds start=" + start + " end=" + end + " lim=" + max);
/*     */     }
/*     */     
/* 367 */     if (start == 0 && end == max) {
/* 368 */       return new ReadOnlyInsnList(method.instructions);
/*     */     }
/*     */     
/* 371 */     return new InsnListSlice(method.instructions, start, end);
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
/*     */   private int find(MethodNode method, InjectionPoint injectionPoint, int defaultValue, String description) {
/* 386 */     if (injectionPoint == null) {
/* 387 */       return defaultValue;
/*     */     }
/*     */     
/* 390 */     Deque<AbstractInsnNode> nodes = new LinkedList<AbstractInsnNode>();
/* 391 */     ReadOnlyInsnList insns = new ReadOnlyInsnList(method.instructions);
/* 392 */     boolean result = injectionPoint.find(method.desc, insns, nodes);
/* 393 */     InjectionPoint.Selector select = injectionPoint.getSelector();
/* 394 */     if (nodes.size() != 1 && select == InjectionPoint.Selector.ONE) {
/* 395 */       throw new InvalidSliceException(this.owner, String.format("%s requires 1 result but found %d", new Object[] { describe(description), Integer.valueOf(nodes.size()) }));
/*     */     }
/*     */     
/* 398 */     if (!result) {
/* 399 */       return defaultValue;
/*     */     }
/*     */     
/* 402 */     return method.instructions.indexOf((select == InjectionPoint.Selector.FIRST) ? nodes.getFirst() : nodes.getLast());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 410 */     return describe();
/*     */   }
/*     */   
/*     */   private String describe() {
/* 414 */     return describe(this.name);
/*     */   }
/*     */   
/*     */   private String describe(String description) {
/* 418 */     return describeSlice(description, this.owner);
/*     */   }
/*     */   
/*     */   private static String describeSlice(String description, ISliceContext owner) {
/* 422 */     String annotation = Bytecode.getSimpleName(owner.getAnnotation());
/* 423 */     MethodNode method = owner.getMethod();
/* 424 */     return String.format("%s->%s(%s)::%s%s", new Object[] { owner.getContext(), annotation, description, method.name, method.desc });
/*     */   }
/*     */   
/*     */   private static String getSliceName(String id) {
/* 428 */     return String.format("@Slice[%s]", new Object[] { Strings.nullToEmpty(id) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MethodSlice parse(ISliceContext owner, Slice slice) {
/* 439 */     String id = slice.id();
/*     */     
/* 441 */     At from = slice.from();
/* 442 */     At to = slice.to();
/*     */     
/* 444 */     InjectionPoint fromPoint = (from != null) ? InjectionPoint.parse(owner, from) : null;
/* 445 */     InjectionPoint toPoint = (to != null) ? InjectionPoint.parse(owner, to) : null;
/*     */     
/* 447 */     return new MethodSlice(owner, id, fromPoint, toPoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MethodSlice parse(ISliceContext info, AnnotationNode node) {
/* 458 */     String id = (String)Annotations.getValue(node, "id");
/*     */     
/* 460 */     AnnotationNode from = (AnnotationNode)Annotations.getValue(node, "from");
/* 461 */     AnnotationNode to = (AnnotationNode)Annotations.getValue(node, "to");
/*     */     
/* 463 */     InjectionPoint fromPoint = (from != null) ? InjectionPoint.parse(info, from) : null;
/* 464 */     InjectionPoint toPoint = (to != null) ? InjectionPoint.parse(info, to) : null;
/*     */     
/* 466 */     return new MethodSlice(info, id, fromPoint, toPoint);
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\injection\code\MethodSlice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */