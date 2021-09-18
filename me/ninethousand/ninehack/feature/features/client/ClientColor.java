/*     */ package me.ninethousand.ninehack.feature.features.client;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import me.ninethousand.ninehack.feature.Category;
/*     */ import me.ninethousand.ninehack.feature.Feature;
/*     */ import me.ninethousand.ninehack.feature.annotation.AlwaysEnabled;
/*     */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*     */ import me.ninethousand.ninehack.feature.setting.NumberSetting;
/*     */ import me.ninethousand.ninehack.feature.setting.Setting;
/*     */ import me.ninethousand.ninehack.util.ColorUtil;
/*     */ 
/*     */ @AlwaysEnabled
/*     */ @NineHackFeature(name = "Colors", description = "Modify the client colors", category = Category.Client)
/*     */ public class ClientColor
/*     */   extends Feature
/*     */ {
/*     */   public static Feature INSTANCE;
/*  20 */   public static final Setting<Boolean> colorMode = new Setting("HSB Mode", Boolean.valueOf(false));
/*  21 */   public static final Setting<Color> GLOBAL_COLOR = new Setting("Global Color", new Color(214, 214, 214, 255));
/*     */   
/*  23 */   public static final Setting<Boolean> rainbow = new Setting("Rainbow", Boolean.valueOf(true));
/*  24 */   public static final NumberSetting<Integer> saturation = new NumberSetting("Saturation", Integer.valueOf(0), Integer.valueOf(255), Integer.valueOf(255), 1);
/*  25 */   public static final NumberSetting<Integer> brightness = new NumberSetting("Brightness", Integer.valueOf(0), Integer.valueOf(255), Integer.valueOf(255), 1);
/*  26 */   public static final NumberSetting<Integer> speed = new NumberSetting("Speed", Integer.valueOf(0), Integer.valueOf(20), Integer.valueOf(100), 1);
/*  27 */   public static final NumberSetting<Integer> step = new NumberSetting("Step", Integer.valueOf(0), Integer.valueOf(14), Integer.valueOf(30), 1);
/*     */   
/*     */   public static float hue;
/*  30 */   public static Map<Integer, Integer> colorHeightMap = new HashMap<>();
/*     */   
/*     */   public ClientColor() {
/*  33 */     addSettings(new Setting[] { colorMode, GLOBAL_COLOR, rainbow, (Setting)saturation, (Setting)brightness, (Setting)speed, (Setting)step });
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
/*     */   public void onUpdate() {
/*  46 */     if (((Boolean)rainbow.getValue()).booleanValue()) {
/*  47 */       GLOBAL_COLOR.setValue(getCurrentColor());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  58 */     int colorSpeed = 101 - ((Integer)speed.getValue()).intValue();
/*  59 */     float tempHue = hue = (float)(System.currentTimeMillis() % (360 * colorSpeed)) / 360.0F * colorSpeed;
/*  60 */     for (int i = 0; i <= 4080; i++) {
/*  61 */       this; colorHeightMap.put(Integer.valueOf(i), Integer.valueOf(Color.HSBtoRGB(tempHue, ((Integer)saturation.getValue()).intValue() / 255.0F, ((Integer)brightness.getValue()).intValue() / 255.0F)));
/*  62 */       if (tempHue + 0.0013071896F < 1.0F) {
/*  63 */         tempHue += 0.0013071896F;
/*     */       } else {
/*     */         
/*  66 */         tempHue = 0.0F;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getCurrentColorHex() {
/*  72 */     this; if (((Boolean)rainbow.getValue()).booleanValue()) {
/*  73 */       return Color.HSBtoRGB(hue, ((Integer)saturation.getValue()).intValue() / 255.0F, ((Integer)brightness.getValue()).intValue() / 255.0F);
/*     */     }
/*  75 */     return ColorUtil.toARGB(((Color)GLOBAL_COLOR.getValue()).getRed(), ((Color)GLOBAL_COLOR.getValue()).getGreen(), ((Color)GLOBAL_COLOR.getValue()).getBlue(), ((Color)GLOBAL_COLOR.getValue()).getAlpha());
/*     */   }
/*     */   
/*     */   public Color getCurrentColor() {
/*  79 */     this; if (((Boolean)rainbow.getValue()).booleanValue()) {
/*  80 */       return Color.getHSBColor(hue, ((Integer)saturation.getValue()).intValue() / 255.0F, ((Integer)brightness.getValue()).intValue() / 255.0F);
/*     */     }
/*  82 */     return new Color(((Color)GLOBAL_COLOR.getValue()).getRed(), ((Color)GLOBAL_COLOR.getValue()).getGreen(), ((Color)GLOBAL_COLOR.getValue()).getBlue(), ((Color)GLOBAL_COLOR.getValue()).getAlpha());
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
/*     */   public Color staticRainbow() {
/*  96 */     float hue = (float)(System.currentTimeMillis() % 11520L) / 12000.0F;
/*  97 */     int rgb = Color.HSBtoRGB(hue, 1.0F, 1.0F);
/*  98 */     int red = rgb >> 16 & 0xFF;
/*  99 */     int green = rgb >> 8 & 0xFF;
/* 100 */     int blue = rgb & 0xFF;
/* 101 */     return new Color(red, green, blue);
/*     */   }
/*     */   
/*     */   public static int toRGBA(int r, int g, int b, int a) {
/* 105 */     return (r << 16) + (g << 8) + b + (a << 24);
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\client\ClientColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */