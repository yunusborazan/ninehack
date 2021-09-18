/*    */ package me.ninethousand.ninehack.feature;
/*    */ 
/*    */ public enum Category {
/*  4 */   Combat,
/*  5 */   Movement,
/*  6 */   Player,
/*  7 */   Visual,
/*  8 */   Other,
/*  9 */   Client;
/*    */   Category() {
/* 11 */     this.openInGui = true;
/*    */   }
/*    */   public boolean isOpenInGui() {
/* 14 */     return this.openInGui;
/*    */   }
/*    */   private boolean openInGui;
/*    */   public void setOpenInGui(boolean openInGui) {
/* 18 */     this.openInGui = openInGui;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\Category.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */