/*    */ package me.ninethousand.ninehack.util;
/*    */ public class Timer {
/*  3 */   private long time = -1L;
/*    */   
/*    */   public boolean passedS(double s) {
/*  6 */     return passedMs((long)s * 1000L);
/*    */   }
/*    */   
/*    */   public boolean passedDms(double dms) {
/* 10 */     return passedMs((long)dms * 10L);
/*    */   }
/*    */   
/*    */   public boolean passedDs(double ds) {
/* 14 */     return passedMs((long)ds * 100L);
/*    */   }
/*    */   
/*    */   public boolean passedMs(long ms) {
/* 18 */     return passedNS(convertToNS(ms));
/*    */   }
/*    */   
/*    */   public void setMs(long ms) {
/* 22 */     this.time = System.nanoTime() - convertToNS(ms);
/*    */   }
/*    */   
/*    */   public boolean passedNS(long ns) {
/* 26 */     return (System.nanoTime() - this.time >= ns);
/*    */   }
/*    */   
/*    */   public long getPassedTimeMs() {
/* 30 */     return getMs(System.nanoTime() - this.time);
/*    */   }
/*    */   
/*    */   public Timer reset() {
/* 34 */     this.time = System.nanoTime();
/* 35 */     return this;
/*    */   }
/*    */   
/*    */   public long getMs(long time) {
/* 39 */     return time / 1000000L;
/*    */   }
/*    */   
/*    */   public long convertToNS(long time) {
/* 43 */     return time * 1000000L;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\Timer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */