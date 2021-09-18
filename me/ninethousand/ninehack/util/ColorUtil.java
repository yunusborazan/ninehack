/*     */ package me.ninethousand.ninehack.util;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ColorUtil
/*     */ {
/*     */   public static int toRGBA(double r, double g, double b, double a) {
/*  10 */     return toRGBA((float)r, (float)g, (float)b, (float)a);
/*     */   }
/*     */   
/*     */   public static int toRGBA(int r, int g, int b) {
/*  14 */     return toRGBA(r, g, b, 255);
/*     */   }
/*     */   
/*     */   public static int toRGBA(int r, int g, int b, int a) {
/*  18 */     return (r << 16) + (g << 8) + b + (a << 24);
/*     */   }
/*     */   
/*     */   public static int toARGB(int r, int g, int b, int a) {
/*  22 */     return (new Color(r, g, b, a)).getRGB();
/*     */   }
/*     */   
/*     */   public static int toRGBA(float r, float g, float b, float a) {
/*  26 */     return toRGBA((int)(r * 255.0F), (int)(g * 255.0F), (int)(b * 255.0F), (int)(a * 255.0F));
/*     */   }
/*     */   
/*     */   public static int toRGBA(float[] colors) {
/*  30 */     if (colors.length != 4) {
/*  31 */       throw new IllegalArgumentException("colors[] must have a length of 4!");
/*     */     }
/*  33 */     return toRGBA(colors[0], colors[1], colors[2], colors[3]);
/*     */   }
/*     */   
/*     */   public static int toRGBA(double[] colors) {
/*  37 */     if (colors.length != 4) {
/*  38 */       throw new IllegalArgumentException("colors[] must have a length of 4!");
/*     */     }
/*  40 */     return toRGBA((float)colors[0], (float)colors[1], (float)colors[2], (float)colors[3]);
/*     */   }
/*     */   
/*     */   public static int toRGBA(Color color) {
/*  44 */     return toRGBA(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
/*     */   }
/*     */   
/*     */   public static int[] toRGBAArray(int colorBuffer) {
/*  48 */     return new int[] { colorBuffer >> 16 & 0xFF, colorBuffer >> 8 & 0xFF, colorBuffer & 0xFF, colorBuffer >> 24 & 0xFF };
/*     */   }
/*     */   
/*     */   public static int changeAlpha(int origColor, int userInputedAlpha) {
/*  52 */     return userInputedAlpha << 24 | (origColor &= 0xFFFFFF);
/*     */   }
/*     */   
/*     */   private ArrayList<ColorName> initColorList() {
/*  56 */     ArrayList<ColorName> colorList = new ArrayList<>();
/*  57 */     colorList.add(new ColorName("AliceBlue", 240, 248, 255));
/*  58 */     colorList.add(new ColorName("AntiqueWhite", 250, 235, 215));
/*  59 */     colorList.add(new ColorName("Aqua", 0, 255, 255));
/*  60 */     colorList.add(new ColorName("Aquamarine", 127, 255, 212));
/*  61 */     colorList.add(new ColorName("Azure", 240, 255, 255));
/*  62 */     colorList.add(new ColorName("Beige", 245, 245, 220));
/*  63 */     colorList.add(new ColorName("Bisque", 255, 228, 196));
/*  64 */     colorList.add(new ColorName("Black", 0, 0, 0));
/*  65 */     colorList.add(new ColorName("BlanchedAlmond", 255, 235, 205));
/*  66 */     colorList.add(new ColorName("Blue", 0, 0, 255));
/*  67 */     colorList.add(new ColorName("BlueViolet", 138, 43, 226));
/*  68 */     colorList.add(new ColorName("Brown", 165, 42, 42));
/*  69 */     colorList.add(new ColorName("BurlyWood", 222, 184, 135));
/*  70 */     colorList.add(new ColorName("CadetBlue", 95, 158, 160));
/*  71 */     colorList.add(new ColorName("Chartreuse", 127, 255, 0));
/*  72 */     colorList.add(new ColorName("Chocolate", 210, 105, 30));
/*  73 */     colorList.add(new ColorName("Coral", 255, 127, 80));
/*  74 */     colorList.add(new ColorName("CornflowerBlue", 100, 149, 237));
/*  75 */     colorList.add(new ColorName("Cornsilk", 255, 248, 220));
/*  76 */     colorList.add(new ColorName("Crimson", 220, 20, 60));
/*  77 */     colorList.add(new ColorName("Cyan", 0, 255, 255));
/*  78 */     colorList.add(new ColorName("DarkBlue", 0, 0, 139));
/*  79 */     colorList.add(new ColorName("DarkCyan", 0, 139, 139));
/*  80 */     colorList.add(new ColorName("DarkGoldenRod", 184, 134, 11));
/*  81 */     colorList.add(new ColorName("DarkGray", 169, 169, 169));
/*  82 */     colorList.add(new ColorName("DarkGreen", 0, 100, 0));
/*  83 */     colorList.add(new ColorName("DarkKhaki", 189, 183, 107));
/*  84 */     colorList.add(new ColorName("DarkMagenta", 139, 0, 139));
/*  85 */     colorList.add(new ColorName("DarkOliveGreen", 85, 107, 47));
/*  86 */     colorList.add(new ColorName("DarkOrange", 255, 140, 0));
/*  87 */     colorList.add(new ColorName("DarkOrchid", 153, 50, 204));
/*  88 */     colorList.add(new ColorName("DarkRed", 139, 0, 0));
/*  89 */     colorList.add(new ColorName("DarkSalmon", 233, 150, 122));
/*  90 */     colorList.add(new ColorName("DarkSeaGreen", 143, 188, 143));
/*  91 */     colorList.add(new ColorName("DarkSlateBlue", 72, 61, 139));
/*  92 */     colorList.add(new ColorName("DarkSlateGray", 47, 79, 79));
/*  93 */     colorList.add(new ColorName("DarkTurquoise", 0, 206, 209));
/*  94 */     colorList.add(new ColorName("DarkViolet", 148, 0, 211));
/*  95 */     colorList.add(new ColorName("DeepPink", 255, 20, 147));
/*  96 */     colorList.add(new ColorName("DeepSkyBlue", 0, 191, 255));
/*  97 */     colorList.add(new ColorName("DimGray", 105, 105, 105));
/*  98 */     colorList.add(new ColorName("DodgerBlue", 30, 144, 255));
/*  99 */     colorList.add(new ColorName("FireBrick", 178, 34, 34));
/* 100 */     colorList.add(new ColorName("FloralWhite", 255, 250, 240));
/* 101 */     colorList.add(new ColorName("ForestGreen", 34, 139, 34));
/* 102 */     colorList.add(new ColorName("Fuchsia", 255, 0, 255));
/* 103 */     colorList.add(new ColorName("Gainsboro", 220, 220, 220));
/* 104 */     colorList.add(new ColorName("GhostWhite", 248, 248, 255));
/* 105 */     colorList.add(new ColorName("Gold", 255, 215, 0));
/* 106 */     colorList.add(new ColorName("GoldenRod", 218, 165, 32));
/* 107 */     colorList.add(new ColorName("Gray", 128, 128, 128));
/* 108 */     colorList.add(new ColorName("Green", 0, 128, 0));
/* 109 */     colorList.add(new ColorName("GreenYellow", 173, 255, 47));
/* 110 */     colorList.add(new ColorName("HoneyDew", 240, 255, 240));
/* 111 */     colorList.add(new ColorName("HotPink", 255, 105, 180));
/* 112 */     colorList.add(new ColorName("IndianRed", 205, 92, 92));
/* 113 */     colorList.add(new ColorName("Indigo", 75, 0, 130));
/* 114 */     colorList.add(new ColorName("Ivory", 255, 255, 240));
/* 115 */     colorList.add(new ColorName("Khaki", 240, 230, 140));
/* 116 */     colorList.add(new ColorName("Lavender", 230, 230, 250));
/* 117 */     colorList.add(new ColorName("LavenderBlush", 255, 240, 245));
/* 118 */     colorList.add(new ColorName("LawnGreen", 124, 252, 0));
/* 119 */     colorList.add(new ColorName("LemonChiffon", 255, 250, 205));
/* 120 */     colorList.add(new ColorName("LightBlue", 173, 216, 230));
/* 121 */     colorList.add(new ColorName("LightCoral", 240, 128, 128));
/* 122 */     colorList.add(new ColorName("LightCyan", 224, 255, 255));
/* 123 */     colorList.add(new ColorName("LightGoldenRodYellow", 250, 250, 210));
/* 124 */     colorList.add(new ColorName("LightGray", 211, 211, 211));
/* 125 */     colorList.add(new ColorName("LightGreen", 144, 238, 144));
/* 126 */     colorList.add(new ColorName("LightPink", 255, 182, 193));
/* 127 */     colorList.add(new ColorName("LightSalmon", 255, 160, 122));
/* 128 */     colorList.add(new ColorName("LightSeaGreen", 32, 178, 170));
/* 129 */     colorList.add(new ColorName("LightSkyBlue", 135, 206, 250));
/* 130 */     colorList.add(new ColorName("LightSlateGray", 119, 136, 153));
/* 131 */     colorList.add(new ColorName("LightSteelBlue", 176, 196, 222));
/* 132 */     colorList.add(new ColorName("LightYellow", 255, 255, 224));
/* 133 */     colorList.add(new ColorName("Lime", 0, 255, 0));
/* 134 */     colorList.add(new ColorName("LimeGreen", 50, 205, 50));
/* 135 */     colorList.add(new ColorName("Linen", 250, 240, 230));
/* 136 */     colorList.add(new ColorName("Magenta", 255, 0, 255));
/* 137 */     colorList.add(new ColorName("Maroon", 128, 0, 0));
/* 138 */     colorList.add(new ColorName("MediumAquaMarine", 102, 205, 170));
/* 139 */     colorList.add(new ColorName("MediumBlue", 0, 0, 205));
/* 140 */     colorList.add(new ColorName("MediumOrchid", 186, 85, 211));
/* 141 */     colorList.add(new ColorName("MediumPurple", 147, 112, 219));
/* 142 */     colorList.add(new ColorName("MediumSeaGreen", 60, 179, 113));
/* 143 */     colorList.add(new ColorName("MediumSlateBlue", 123, 104, 238));
/* 144 */     colorList.add(new ColorName("MediumSpringGreen", 0, 250, 154));
/* 145 */     colorList.add(new ColorName("MediumTurquoise", 72, 209, 204));
/* 146 */     colorList.add(new ColorName("MediumVioletRed", 199, 21, 133));
/* 147 */     colorList.add(new ColorName("MidnightBlue", 25, 25, 112));
/* 148 */     colorList.add(new ColorName("MintCream", 245, 255, 250));
/* 149 */     colorList.add(new ColorName("MistyRose", 255, 228, 225));
/* 150 */     colorList.add(new ColorName("Moccasin", 255, 228, 181));
/* 151 */     colorList.add(new ColorName("NavajoWhite", 255, 222, 173));
/* 152 */     colorList.add(new ColorName("Navy", 0, 0, 128));
/* 153 */     colorList.add(new ColorName("OldLace", 253, 245, 230));
/* 154 */     colorList.add(new ColorName("Olive", 128, 128, 0));
/* 155 */     colorList.add(new ColorName("OliveDrab", 107, 142, 35));
/* 156 */     colorList.add(new ColorName("Orange", 255, 165, 0));
/* 157 */     colorList.add(new ColorName("OrangeRed", 255, 69, 0));
/* 158 */     colorList.add(new ColorName("Orchid", 218, 112, 214));
/* 159 */     colorList.add(new ColorName("PaleGoldenRod", 238, 232, 170));
/* 160 */     colorList.add(new ColorName("PaleGreen", 152, 251, 152));
/* 161 */     colorList.add(new ColorName("PaleTurquoise", 175, 238, 238));
/* 162 */     colorList.add(new ColorName("PaleVioletRed", 219, 112, 147));
/* 163 */     colorList.add(new ColorName("PapayaWhip", 255, 239, 213));
/* 164 */     colorList.add(new ColorName("PeachPuff", 255, 218, 185));
/* 165 */     colorList.add(new ColorName("Peru", 205, 133, 63));
/* 166 */     colorList.add(new ColorName("Pink", 255, 192, 203));
/* 167 */     colorList.add(new ColorName("Plum", 221, 160, 221));
/* 168 */     colorList.add(new ColorName("PowderBlue", 176, 224, 230));
/* 169 */     colorList.add(new ColorName("Purple", 128, 0, 128));
/* 170 */     colorList.add(new ColorName("Red", 255, 0, 0));
/* 171 */     colorList.add(new ColorName("RosyBrown", 188, 143, 143));
/* 172 */     colorList.add(new ColorName("RoyalBlue", 65, 105, 225));
/* 173 */     colorList.add(new ColorName("SaddleBrown", 139, 69, 19));
/* 174 */     colorList.add(new ColorName("Salmon", 250, 128, 114));
/* 175 */     colorList.add(new ColorName("SandyBrown", 244, 164, 96));
/* 176 */     colorList.add(new ColorName("SeaGreen", 46, 139, 87));
/* 177 */     colorList.add(new ColorName("SeaShell", 255, 245, 238));
/* 178 */     colorList.add(new ColorName("Sienna", 160, 82, 45));
/* 179 */     colorList.add(new ColorName("Silver", 192, 192, 192));
/* 180 */     colorList.add(new ColorName("SkyBlue", 135, 206, 235));
/* 181 */     colorList.add(new ColorName("SlateBlue", 106, 90, 205));
/* 182 */     colorList.add(new ColorName("SlateGray", 112, 128, 144));
/* 183 */     colorList.add(new ColorName("Snow", 255, 250, 250));
/* 184 */     colorList.add(new ColorName("SpringGreen", 0, 255, 127));
/* 185 */     colorList.add(new ColorName("SteelBlue", 70, 130, 180));
/* 186 */     colorList.add(new ColorName("Tan", 210, 180, 140));
/* 187 */     colorList.add(new ColorName("Teal", 0, 128, 128));
/* 188 */     colorList.add(new ColorName("Thistle", 216, 191, 216));
/* 189 */     colorList.add(new ColorName("Tomato", 255, 99, 71));
/* 190 */     colorList.add(new ColorName("Turquoise", 64, 224, 208));
/* 191 */     colorList.add(new ColorName("Violet", 238, 130, 238));
/* 192 */     colorList.add(new ColorName("Wheat", 245, 222, 179));
/* 193 */     colorList.add(new ColorName("White", 255, 255, 255));
/* 194 */     colorList.add(new ColorName("WhiteSmoke", 245, 245, 245));
/* 195 */     colorList.add(new ColorName("Yellow", 255, 255, 0));
/* 196 */     colorList.add(new ColorName("YellowGreen", 154, 205, 50));
/* 197 */     return colorList;
/*     */   }
/*     */   
/*     */   public String getColorNameFromRgb(int r, int g, int b) {
/* 201 */     ArrayList<ColorName> colorList = initColorList();
/* 202 */     ColorName closestMatch = null;
/* 203 */     int minMSE = Integer.MAX_VALUE;
/* 204 */     for (ColorName c : colorList) {
/* 205 */       int mse = c.computeMSE(r, g, b);
/* 206 */       if (mse >= minMSE)
/* 207 */         continue;  minMSE = mse;
/* 208 */       closestMatch = c;
/*     */     } 
/* 210 */     if (closestMatch != null) {
/* 211 */       return closestMatch.getName();
/*     */     }
/* 213 */     return "No matched color name.";
/*     */   }
/*     */   
/*     */   public String getColorNameFromHex(int hexColor) {
/* 217 */     int r = (hexColor & 0xFF0000) >> 16;
/* 218 */     int g = (hexColor & 0xFF00) >> 8;
/* 219 */     int b = hexColor & 0xFF;
/* 220 */     return getColorNameFromRgb(r, g, b);
/*     */   }
/*     */   
/*     */   public int colorToHex(Color c) {
/* 224 */     return Integer.decode("0x" + Integer.toHexString(c.getRGB()).substring(2)).intValue();
/*     */   }
/*     */   
/*     */   public String getColorNameFromColor(Color color) {
/* 228 */     return getColorNameFromRgb(color.getRed(), color.getGreen(), color.getBlue());
/*     */   }
/*     */   
/*     */   public static class HueCycler {
/* 232 */     public int index = 0;
/*     */     public int[] cycles;
/*     */     
/*     */     public HueCycler(int cycles) {
/* 236 */       if (cycles <= 0) {
/* 237 */         throw new IllegalArgumentException("cycles <= 0");
/*     */       }
/* 239 */       this.cycles = new int[cycles];
/* 240 */       double hue = 0.0D;
/* 241 */       double add = 1.0D / cycles;
/* 242 */       for (int i = 0; i < cycles; i++) {
/* 243 */         this.cycles[i] = Color.HSBtoRGB((float)hue, 1.0F, 1.0F);
/* 244 */         hue += add;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void reset() {
/* 249 */       this.index = 0;
/*     */     }
/*     */     
/*     */     public void reset(int index) {
/* 253 */       this.index = index;
/*     */     }
/*     */     
/*     */     public int next() {
/* 257 */       int a = this.cycles[this.index];
/* 258 */       this.index++;
/* 259 */       if (this.index >= this.cycles.length) {
/* 260 */         this.index = 0;
/*     */       }
/* 262 */       return a;
/*     */     }
/*     */     
/*     */     public void setNext() {
/* 266 */       int rgb = next();
/*     */     }
/*     */     
/*     */     public void set() {
/* 270 */       int rgb = this.cycles[this.index];
/* 271 */       float red = (rgb >> 16 & 0xFF) / 255.0F;
/* 272 */       float green = (rgb >> 8 & 0xFF) / 255.0F;
/* 273 */       float blue = (rgb & 0xFF) / 255.0F;
/* 274 */       GL11.glColor3f(red, green, blue);
/*     */     }
/*     */     
/*     */     public void setNext(float alpha) {
/* 278 */       int rgb = next();
/* 279 */       float red = (rgb >> 16 & 0xFF) / 255.0F;
/* 280 */       float green = (rgb >> 8 & 0xFF) / 255.0F;
/* 281 */       float blue = (rgb & 0xFF) / 255.0F;
/* 282 */       GL11.glColor4f(red, green, blue, alpha);
/*     */     }
/*     */     
/*     */     public int current() {
/* 286 */       return this.cycles[this.index];
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Colors {
/* 291 */     public static final int WHITE = ColorUtil.toRGBA(255, 255, 255, 255);
/* 292 */     public static final int BLACK = ColorUtil.toRGBA(0, 0, 0, 255);
/* 293 */     public static final int RED = ColorUtil.toRGBA(255, 0, 0, 255);
/* 294 */     public static final int GREEN = ColorUtil.toRGBA(0, 255, 0, 255);
/* 295 */     public static final int BLUE = ColorUtil.toRGBA(0, 0, 255, 255);
/* 296 */     public static final int ORANGE = ColorUtil.toRGBA(255, 128, 0, 255);
/* 297 */     public static final int PURPLE = ColorUtil.toRGBA(163, 73, 163, 255);
/* 298 */     public static final int GRAY = ColorUtil.toRGBA(127, 127, 127, 255);
/* 299 */     public static final int DARK_RED = ColorUtil.toRGBA(64, 0, 0, 255);
/* 300 */     public static final int YELLOW = ColorUtil.toRGBA(255, 255, 0, 255);
/*     */     public static final int RAINBOW = -2147483648;
/*     */   }
/*     */   
/*     */   public static class ColorName {
/*     */     public int r;
/*     */     public int g;
/*     */     public int b;
/*     */     public String name;
/*     */     
/*     */     public ColorName(String name, int r, int g, int b) {
/* 311 */       this.r = r;
/* 312 */       this.g = g;
/* 313 */       this.b = b;
/* 314 */       this.name = name;
/*     */     }
/*     */     
/*     */     public int computeMSE(int pixR, int pixG, int pixB) {
/* 318 */       return ((pixR - this.r) * (pixR - this.r) + (pixG - this.g) * (pixG - this.g) + (pixB - this.b) * (pixB - this.b)) / 3;
/*     */     }
/*     */     
/*     */     public int getR() {
/* 322 */       return this.r;
/*     */     }
/*     */     
/*     */     public int getG() {
/* 326 */       return this.g;
/*     */     }
/*     */     
/*     */     public int getB() {
/* 330 */       return this.b;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 334 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\ColorUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */