/*     */ package me.ninethousand.ninehack.managers;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import me.ninethousand.ninehack.NineHack;
/*     */ import me.ninethousand.ninehack.feature.features.client.CustomFont;
/*     */ import me.ninethousand.ninehack.util.MathsUtil;
/*     */ import me.ninethousand.ninehack.util.Timer;
/*     */ import me.ninethousand.ninehack.util.customfont.CustomFont;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class TextManager implements NineHack.Globals {
/*  12 */   private final Timer idleTimer = new Timer();
/*     */   public int scaledWidth;
/*     */   public int scaledHeight;
/*     */   public int scaleFactor;
/*  16 */   public CustomFont menuFont = new CustomFont(new Font("Impact", 0, 40), true, true);
/*  17 */   public CustomFont customFont = new CustomFont(new Font("Verdana", ((CustomFont.FontStyle)CustomFont.style.getValue()).ordinal(), ((Integer)CustomFont.size.getValue()).intValue()), true, true);
/*     */   private boolean idling;
/*     */   
/*     */   public TextManager() {
/*  21 */     updateResolution();
/*     */   }
/*     */   
/*     */   public void init() {
/*  25 */     this.customFont = new CustomFont(new Font((String)CustomFont.fontName.getValue(), ((CustomFont.FontStyle)CustomFont.style.getValue()).ordinal(), ((Integer)CustomFont.size.getValue()).intValue()), true, true);
/*     */   }
/*     */   
/*     */   public void drawStringWithShadow(String text, float x, float y, int color) {
/*  29 */     drawString(text, x, y, color, true);
/*     */   }
/*     */   
/*     */   public float drawStringCustomMenu(String text, float x, float y, int color, boolean shadow) {
/*  33 */     if (shadow) {
/*  34 */       return this.menuFont.drawStringWithShadow(text, x, y, color);
/*     */     }
/*  36 */     return this.menuFont.drawString(text, x, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public float drawString(String text, float x, float y, int color, boolean shadow) {
/*  41 */     if (CustomFont.INSTANCE.isEnabled()) {
/*  42 */       if (shadow) {
/*  43 */         return this.customFont.drawStringWithShadow(text, x, y, color);
/*     */       }
/*  45 */       return this.customFont.drawString(text, x, y, color);
/*     */     } 
/*  47 */     return mc.field_71466_p.func_175065_a(text, x, y, color, shadow);
/*     */   }
/*     */   
/*     */   public float drawStringCustomFont(String text, float x, float y, int color, boolean shadow, boolean custom) {
/*  51 */     if (custom) {
/*  52 */       if (shadow) {
/*  53 */         return this.customFont.drawStringWithShadow(text, x, y, color);
/*     */       }
/*  55 */       return this.customFont.drawString(text, x, y, color);
/*     */     } 
/*  57 */     return mc.field_71466_p.func_175065_a(text, x, y, color, shadow);
/*     */   }
/*     */   
/*     */   public void drawRainbowString(String text, float x, float y, int startColor, float factor, boolean shadow) {
/*  61 */     Color currentColor = new Color(startColor);
/*  62 */     float hueIncrement = 1.0F / factor;
/*  63 */     String[] rainbowStrings = text.split("§.");
/*  64 */     float currentHue = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[0];
/*  65 */     float saturation = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[1];
/*  66 */     float brightness = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[2];
/*  67 */     int currentWidth = 0;
/*  68 */     boolean shouldRainbow = true;
/*  69 */     boolean shouldContinue = false;
/*  70 */     for (int i = 0; i < text.length(); i++) {
/*  71 */       char currentChar = text.charAt(i);
/*  72 */       char nextChar = text.charAt(MathsUtil.clamp(i + 1, 0, text.length() - 1));
/*  73 */       if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
/*  74 */         shouldRainbow = false;
/*  75 */       } else if ((String.valueOf(currentChar) + nextChar).equals("§+")) {
/*  76 */         shouldRainbow = true;
/*     */       } 
/*  78 */       if (shouldContinue) {
/*  79 */         shouldContinue = false;
/*     */       } else {
/*     */         
/*  82 */         if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
/*  83 */           String escapeString = text.substring(i);
/*  84 */           drawString(escapeString, x + currentWidth, y, Color.WHITE.getRGB(), shadow);
/*     */           break;
/*     */         } 
/*  87 */         drawString(String.valueOf(currentChar).equals("§") ? "" : String.valueOf(currentChar), x + currentWidth, y, shouldRainbow ? currentColor.getRGB() : Color.WHITE.getRGB(), shadow);
/*  88 */         if (String.valueOf(currentChar).equals("§")) {
/*  89 */           shouldContinue = true;
/*     */         }
/*  91 */         currentWidth += getStringWidth(String.valueOf(currentChar));
/*  92 */         if (!String.valueOf(currentChar).equals(" ")) {
/*  93 */           currentColor = new Color(Color.HSBtoRGB(currentHue, saturation, brightness));
/*  94 */           currentHue += hueIncrement;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } public void drawRainbowStringCustomFont(String text, float x, float y, int startColor, float factor, boolean shadow, boolean custom) {
/*  99 */     Color currentColor = new Color(startColor);
/* 100 */     float hueIncrement = 1.0F / factor;
/* 101 */     String[] rainbowStrings = text.split("§.");
/* 102 */     float currentHue = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[0];
/* 103 */     float saturation = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[1];
/* 104 */     float brightness = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[2];
/* 105 */     int currentWidth = 0;
/* 106 */     boolean shouldRainbow = true;
/* 107 */     boolean shouldContinue = false;
/* 108 */     for (int i = 0; i < text.length(); i++) {
/* 109 */       char currentChar = text.charAt(i);
/* 110 */       char nextChar = text.charAt(MathsUtil.clamp(i + 1, 0, text.length() - 1));
/* 111 */       if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
/* 112 */         shouldRainbow = false;
/* 113 */       } else if ((String.valueOf(currentChar) + nextChar).equals("§+")) {
/* 114 */         shouldRainbow = true;
/*     */       } 
/* 116 */       if (shouldContinue) {
/* 117 */         shouldContinue = false;
/*     */       } else {
/*     */         
/* 120 */         if ((String.valueOf(currentChar) + nextChar).equals("§r")) {
/* 121 */           String escapeString = text.substring(i);
/* 122 */           drawString(escapeString, x + currentWidth, y, Color.WHITE.getRGB(), shadow);
/*     */           break;
/*     */         } 
/* 125 */         drawStringCustomFont(String.valueOf(currentChar).equals("§") ? "" : String.valueOf(currentChar), x + currentWidth, y, shouldRainbow ? currentColor.getRGB() : Color.WHITE.getRGB(), shadow, custom);
/* 126 */         if (String.valueOf(currentChar).equals("§")) {
/* 127 */           shouldContinue = true;
/*     */         }
/* 129 */         currentWidth += getStringWidth(String.valueOf(currentChar));
/* 130 */         if (!String.valueOf(currentChar).equals(" ")) {
/* 131 */           currentColor = new Color(Color.HSBtoRGB(currentHue, saturation, brightness));
/* 132 */           currentHue += hueIncrement;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } public int getStringWidth(String text) {
/* 137 */     if (CustomFont.INSTANCE.isEnabled()) {
/* 138 */       return this.customFont.getStringWidth(text);
/*     */     }
/* 140 */     return mc.field_71466_p.func_78256_a(text);
/*     */   }
/*     */   
/*     */   public int getStringWidthMenu(String text) {
/* 144 */     return this.menuFont.getStringWidth(text);
/*     */   }
/*     */   
/*     */   public int getFontHeight() {
/* 148 */     if (CustomFont.INSTANCE.isEnabled()) {
/* 149 */       String text = "A";
/* 150 */       return this.customFont.getStringHeight(text);
/*     */     } 
/* 152 */     return mc.field_71466_p.field_78288_b;
/*     */   }
/*     */   
/*     */   public int getFontHeightMenu() {
/* 156 */     String text = "A";
/* 157 */     return this.customFont.getStringHeight(text);
/*     */   }
/*     */   
/*     */   public void setFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
/* 161 */     this.customFont = new CustomFont(font, antiAlias, fractionalMetrics);
/*     */   }
/*     */   
/*     */   public Font getCurrentFont() {
/* 165 */     return this.customFont.getFont();
/*     */   }
/*     */   
/*     */   public void updateResolution() {
/* 169 */     this.scaledWidth = mc.field_71443_c;
/* 170 */     this.scaledHeight = mc.field_71440_d;
/* 171 */     this.scaleFactor = 1;
/* 172 */     boolean flag = mc.func_152349_b();
/* 173 */     int i = mc.field_71474_y.field_74335_Z;
/* 174 */     if (i == 0) {
/* 175 */       i = 1000;
/*     */     }
/* 177 */     while (this.scaleFactor < i && this.scaledWidth / (this.scaleFactor + 1) >= 320 && this.scaledHeight / (this.scaleFactor + 1) >= 240) {
/* 178 */       this.scaleFactor++;
/*     */     }
/* 180 */     if (flag && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
/* 181 */       this.scaleFactor--;
/*     */     }
/* 183 */     double scaledWidthD = this.scaledWidth / this.scaleFactor;
/* 184 */     double scaledHeightD = this.scaledHeight / this.scaleFactor;
/* 185 */     this.scaledWidth = MathHelper.func_76143_f(scaledWidthD);
/* 186 */     this.scaledHeight = MathHelper.func_76143_f(scaledHeightD);
/*     */   }
/*     */   
/*     */   public String getIdleSign() {
/* 190 */     if (this.idleTimer.passedMs(500L)) {
/* 191 */       this.idling = !this.idling;
/* 192 */       this.idleTimer.reset();
/*     */     } 
/* 194 */     if (this.idling) {
/* 195 */       return "_";
/*     */     }
/* 197 */     return "";
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\managers\TextManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */