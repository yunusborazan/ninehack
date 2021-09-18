/*     */ package org.spongepowered.asm.mixin.injection.points;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.ListIterator;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @AtCode("RETURN")
/*     */ public class BeforeReturn
/*     */   extends InjectionPoint
/*     */ {
/*     */   private final int ordinal;
/*     */   
/*     */   public BeforeReturn(InjectionPointData data) {
/*  77 */     super(data);
/*     */     
/*  79 */     this.ordinal = data.getOrdinal();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/*  84 */     boolean found = false;
/*     */ 
/*     */     
/*  87 */     int returnOpcode = Type.getReturnType(desc).getOpcode(172);
/*  88 */     int ordinal = 0;
/*     */     
/*  90 */     ListIterator<AbstractInsnNode> iter = insns.iterator();
/*  91 */     while (iter.hasNext()) {
/*  92 */       AbstractInsnNode insn = iter.next();
/*     */       
/*  94 */       if (insn instanceof org.spongepowered.asm.lib.tree.InsnNode && insn.getOpcode() == returnOpcode) {
/*  95 */         if (this.ordinal == -1 || this.ordinal == ordinal) {
/*  96 */           nodes.add(insn);
/*  97 */           found = true;
/*     */         } 
/*     */         
/* 100 */         ordinal++;
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     return found;
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\injection\points\BeforeReturn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */