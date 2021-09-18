/*     */ package me.ninethousand.ninehack.feature.setting;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Setting<T>
/*     */ {
/*     */   private final String name;
/*     */   private T value;
/*     */   private boolean isOpened;
/*     */   private boolean isTyping;
/*     */   private boolean rainbow;
/*  18 */   private float alpha = 0.2F;
/*     */   
/*  20 */   private final ArrayList<Setting<?>> subSettings = new ArrayList<>();
/*     */   
/*     */   public Setting(String name, T value) {
/*  23 */     this.name = name;
/*  24 */     this.value = value;
/*     */   }
/*     */   
/*     */   public Setting(Setting<?> parent, String name, T value) {
/*  28 */     this.name = name;
/*  29 */     this.value = value;
/*     */     
/*  31 */     if (parent.getValue() instanceof Boolean) {
/*  32 */       Setting<Boolean> booleanSetting = (Setting)parent;
/*  33 */       booleanSetting.addSubSetting(this);
/*     */     } 
/*     */     
/*  36 */     if (parent.getValue() instanceof Enum) {
/*  37 */       Setting<Enum<?>> enumSetting = (Setting)parent;
/*  38 */       enumSetting.addSubSetting(this);
/*     */     } 
/*     */     
/*  41 */     if (parent.getValue() instanceof Color) {
/*  42 */       Setting<Color> colorSetting = (Setting)parent;
/*  43 */       colorSetting.addSubSetting(this);
/*     */     } 
/*     */     
/*  46 */     if (parent.getValue() instanceof Integer) {
/*  47 */       NumberSetting<Integer> integerNumberSetting = (NumberSetting)parent;
/*  48 */       integerNumberSetting.addSubSetting(this);
/*     */     } 
/*     */     
/*  51 */     if (parent.getValue() instanceof Double) {
/*  52 */       NumberSetting<Double> doubleNumberSetting = (NumberSetting)parent;
/*  53 */       doubleNumberSetting.addSubSetting(this);
/*     */     } 
/*     */     
/*  56 */     if (parent.getValue() instanceof Float) {
/*  57 */       NumberSetting<Float> floatNumberSetting = (NumberSetting)parent;
/*  58 */       floatNumberSetting.addSubSetting(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ArrayList<Setting<?>> getSubSettings() {
/*  63 */     return this.subSettings;
/*     */   }
/*     */   
/*     */   public boolean hasSubSettings() {
/*  67 */     return (this.subSettings.size() > 0);
/*     */   }
/*     */   
/*     */   public void addSubSetting(Setting<?> subSetting) {
/*  71 */     this.subSettings.add(subSetting);
/*     */   }
/*     */   
/*     */   public String getName() {
/*  75 */     return this.name;
/*     */   }
/*     */   
/*     */   public T getValue() {
/*  79 */     return this.value;
/*     */   }
/*     */   
/*     */   public boolean isOpened() {
/*  83 */     return this.isOpened;
/*     */   }
/*     */   
/*     */   public boolean isTyping() {
/*  87 */     return this.isTyping;
/*     */   }
/*     */   
/*     */   public void setTyping(boolean typing) {
/*  91 */     this.isTyping = typing;
/*     */   }
/*     */   
/*     */   public void setValue(T value) {
/*  95 */     this.value = value;
/*     */   }
/*     */   
/*     */   public void setOpened(boolean opened) {
/*  99 */     this.isOpened = opened;
/*     */   }
/*     */   
/*     */   public float getAlpha() {
/* 103 */     return this.alpha;
/*     */   }
/*     */   
/*     */   public void setAlpha(float alpha) {
/* 107 */     this.alpha = alpha;
/*     */   }
/*     */   
/*     */   public boolean isRainbow() {
/* 111 */     return this.rainbow;
/*     */   }
/*     */   
/*     */   public void setRainbow(boolean rainbow) {
/* 115 */     this.rainbow = rainbow;
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\setting\Setting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */