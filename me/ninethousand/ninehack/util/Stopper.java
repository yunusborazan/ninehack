/*    */ package me.ninethousand.ninehack.util;
/*    */ 
/*    */ public final class Stopper {
/*  4 */   private long startTime = System.currentTimeMillis();
/*    */   
/*    */   public boolean hasTimePassed(long ms) {
/*  7 */     return (System.currentTimeMillis() - this.startTime > ms);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 11 */     this.startTime = System.currentTimeMillis();
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\Stopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */