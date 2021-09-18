/*    */ package me.ninethousand.ninehack.feature.setting;
/*    */ 
/*    */ public final class NumberSetting<T extends Number>
/*    */   extends Setting<T> {
/*    */   private final T min;
/*    */   private final T max;
/*    */   private final int scale;
/*    */   
/*    */   public NumberSetting(String name, T min, T value, T max, int scale) {
/* 10 */     super(name, value);
/*    */     
/* 12 */     this.min = min;
/* 13 */     this.max = max;
/* 14 */     this.scale = scale;
/*    */   }
/*    */   
/*    */   public NumberSetting(Setting<?> parent, String name, T min, T value, T max, int scale) {
/* 18 */     super(parent, name, value);
/*    */     
/* 20 */     this.min = min;
/* 21 */     this.max = max;
/* 22 */     this.scale = scale;
/*    */   }
/*    */   
/*    */   public T getMin() {
/* 26 */     return this.min;
/*    */   }
/*    */   
/*    */   public T getMax() {
/* 30 */     return this.max;
/*    */   }
/*    */   
/*    */   public int getScale() {
/* 34 */     return this.scale;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\setting\NumberSetting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */