/*     */ package org.spongepowered.asm.mixin.injection.code;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InjectorTarget
/*     */ {
/*     */   private final ISliceContext context;
/*  49 */   private final Map<String, ReadOnlyInsnList> cache = new HashMap<String, ReadOnlyInsnList>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Target target;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectorTarget(ISliceContext context, Target target) {
/*  63 */     this.context = context;
/*  64 */     this.target = target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Target getTarget() {
/*  71 */     return this.target;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodNode getMethod() {
/*  78 */     return this.target.method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InsnList getSlice(String id) {
/*  88 */     ReadOnlyInsnList slice = this.cache.get(id);
/*  89 */     if (slice == null) {
/*  90 */       MethodSlice sliceInfo = this.context.getSlice(id);
/*  91 */       if (sliceInfo != null) {
/*  92 */         slice = sliceInfo.getSlice(this.target.method);
/*     */       } else {
/*     */         
/*  95 */         slice = new ReadOnlyInsnList(this.target.method.instructions);
/*     */       } 
/*  97 */       this.cache.put(id, slice);
/*     */     } 
/*     */     
/* 100 */     return slice;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InsnList getSlice(InjectionPoint injectionPoint) {
/* 110 */     return getSlice(injectionPoint.getSlice());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 117 */     for (ReadOnlyInsnList insns : this.cache.values()) {
/* 118 */       insns.dispose();
/*     */     }
/*     */     
/* 121 */     this.cache.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\injection\code\InjectorTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */