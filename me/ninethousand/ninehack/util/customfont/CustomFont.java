/*     */ package me.ninethousand.ninehack.util.customfont;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class CustomFont
/*     */   extends CFont {
/*  12 */   private final int[] colorCode = new int[32];
/*  13 */   protected CFont.CharData[] boldChars = new CFont.CharData[256];
/*  14 */   protected CFont.CharData[] italicChars = new CFont.CharData[256];
/*  15 */   protected CFont.CharData[] boldItalicChars = new CFont.CharData[256];
/*     */   protected DynamicTexture texBold;
/*     */   protected DynamicTexture texItalic;
/*     */   protected DynamicTexture texItalicBold;
/*     */   
/*     */   public CustomFont(Font font, boolean antiAlias, boolean fractionalMetrics) {
/*  21 */     super(font, antiAlias, fractionalMetrics);
/*  22 */     setupMinecraftColorcodes();
/*  23 */     setupBoldItalicIDs();
/*     */   }
/*     */   
/*     */   public float drawStringWithShadow(String text, double x, double y, int color) {
/*  27 */     float shadowWidth = drawString(text, x + 1.0D, y + 1.0D, color, true);
/*  28 */     return Math.max(shadowWidth, drawString(text, x, y, color, false));
/*     */   }
/*     */   
/*     */   public float drawString(String text, float x, float y, int color) {
/*  32 */     return drawString(text, x, y, color, false);
/*     */   }
/*     */   
/*     */   public float drawCenteredString(String text, float x, float y, int color) {
/*  36 */     return drawString(text, x - (getStringWidth(text) / 2), y, color);
/*     */   }
/*     */   
/*     */   public float drawCenteredStringWithShadow(String text, float x, float y, int color) {
/*  40 */     float shadowWidth = drawString(text, (x - (getStringWidth(text) / 2)) + 1.0D, y + 1.0D, color, true);
/*  41 */     return drawString(text, x - (getStringWidth(text) / 2), y, color);
/*     */   }
/*     */   
/*     */   public float drawString(String textIn, double xI, double yI, int color, boolean shadow) {
/*  45 */     String text = textIn;
/*  46 */     double x = xI;
/*  47 */     double y = yI;
/*  48 */     if (me.ninethousand.ninehack.feature.features.client.CustomFont.INSTANCE.isEnabled() && !((Boolean)me.ninethousand.ninehack.feature.features.client.CustomFont.shadow.getValue()).booleanValue() && shadow) {
/*  49 */       x -= 0.5D;
/*  50 */       y -= 0.5D;
/*     */     } 
/*  52 */     x--;
/*  53 */     if (text == null) {
/*  54 */       return 0.0F;
/*     */     }
/*  56 */     if (color == 553648127) {
/*  57 */       color = 16777215;
/*     */     }
/*  59 */     if ((color & 0xFC000000) == 0) {
/*  60 */       color |= 0xFF000000;
/*     */     }
/*  62 */     if (shadow) {
/*  63 */       color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
/*     */     }
/*  65 */     CFont.CharData[] currentData = this.charData;
/*  66 */     float alpha = (color >> 24 & 0xFF) / 255.0F;
/*  67 */     boolean bold = false;
/*  68 */     boolean italic = false;
/*  69 */     boolean strikethrough = false;
/*  70 */     boolean underline = false;
/*  71 */     boolean render = true;
/*  72 */     x *= 2.0D;
/*  73 */     y = (y - 3.0D) * 2.0D;
/*  74 */     if (render) {
/*  75 */       GL11.glPushMatrix();
/*  76 */       GlStateManager.func_179139_a(0.5D, 0.5D, 0.5D);
/*  77 */       GlStateManager.func_179147_l();
/*  78 */       GlStateManager.func_179112_b(770, 771);
/*  79 */       GlStateManager.func_179131_c((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/*  80 */       int size = text.length();
/*  81 */       GlStateManager.func_179098_w();
/*  82 */       GlStateManager.func_179144_i(this.tex.func_110552_b());
/*  83 */       GL11.glBindTexture(3553, this.tex.func_110552_b());
/*  84 */       for (int i = 0; i < size; i++) {
/*  85 */         char character = text.charAt(i);
/*  86 */         if (character == '§' && i < size) {
/*  87 */           int colorIndex = 21;
/*     */           try {
/*  89 */             colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
/*  90 */           } catch (Exception e) {
/*  91 */             e.printStackTrace();
/*     */           } 
/*  93 */           if (colorIndex < 16) {
/*  94 */             bold = false;
/*  95 */             italic = false;
/*  96 */             underline = false;
/*  97 */             strikethrough = false;
/*  98 */             GlStateManager.func_179144_i(this.tex.func_110552_b());
/*  99 */             currentData = this.charData;
/* 100 */             if (colorIndex < 0 || colorIndex > 15) {
/* 101 */               colorIndex = 15;
/*     */             }
/* 103 */             if (shadow) {
/* 104 */               colorIndex += 16;
/*     */             }
/* 106 */             int colorcode = this.colorCode[colorIndex];
/* 107 */             GlStateManager.func_179131_c((colorcode >> 16 & 0xFF) / 255.0F, (colorcode >> 8 & 0xFF) / 255.0F, (colorcode & 0xFF) / 255.0F, alpha);
/* 108 */           } else if (colorIndex == 17) {
/* 109 */             bold = true;
/* 110 */             if (italic) {
/* 111 */               GlStateManager.func_179144_i(this.texItalicBold.func_110552_b());
/* 112 */               currentData = this.boldItalicChars;
/*     */             } else {
/* 114 */               GlStateManager.func_179144_i(this.texBold.func_110552_b());
/* 115 */               currentData = this.boldChars;
/*     */             } 
/* 117 */           } else if (colorIndex == 18) {
/* 118 */             strikethrough = true;
/* 119 */           } else if (colorIndex == 19) {
/* 120 */             underline = true;
/* 121 */           } else if (colorIndex == 20) {
/* 122 */             italic = true;
/* 123 */             if (bold) {
/* 124 */               GlStateManager.func_179144_i(this.texItalicBold.func_110552_b());
/* 125 */               currentData = this.boldItalicChars;
/*     */             } else {
/* 127 */               GlStateManager.func_179144_i(this.texItalic.func_110552_b());
/* 128 */               currentData = this.italicChars;
/*     */             } 
/* 130 */           } else if (colorIndex == 21) {
/* 131 */             bold = false;
/* 132 */             italic = false;
/* 133 */             underline = false;
/* 134 */             strikethrough = false;
/* 135 */             GlStateManager.func_179131_c((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha);
/* 136 */             GlStateManager.func_179144_i(this.tex.func_110552_b());
/* 137 */             currentData = this.charData;
/*     */           } 
/* 139 */           i++;
/*     */         
/*     */         }
/* 142 */         else if (character < currentData.length && character >= '\000') {
/* 143 */           GL11.glBegin(4);
/* 144 */           drawChar(currentData, character, (float)x, (float)y);
/* 145 */           GL11.glEnd();
/* 146 */           if (strikethrough) {
/* 147 */             drawLine(x, y + ((currentData[character]).height / 2), x + (currentData[character]).width - 8.0D, y + ((currentData[character]).height / 2), 1.0F);
/*     */           }
/* 149 */           if (underline) {
/* 150 */             drawLine(x, y + (currentData[character]).height - 2.0D, x + (currentData[character]).width - 8.0D, y + (currentData[character]).height - 2.0D, 1.0F);
/*     */           }
/* 152 */           x += ((currentData[character]).width - 8 + this.charOffset);
/*     */         } 
/* 154 */       }  GL11.glHint(3155, 4352);
/* 155 */       GL11.glPopMatrix();
/*     */     } 
/* 157 */     return (float)x / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStringWidth(String text) {
/* 162 */     if (text == null) {
/* 163 */       return 0;
/*     */     }
/* 165 */     int width = 0;
/* 166 */     CFont.CharData[] currentData = this.charData;
/* 167 */     boolean bold = false;
/* 168 */     boolean italic = false;
/* 169 */     int size = text.length();
/* 170 */     for (int i = 0; i < size; i++) {
/* 171 */       char character = text.charAt(i);
/* 172 */       if (character == '§' && i < size) {
/* 173 */         int colorIndex = "0123456789abcdefklmnor".indexOf(character);
/* 174 */         if (colorIndex < 16) {
/* 175 */           bold = false;
/* 176 */           italic = false;
/* 177 */         } else if (colorIndex == 17) {
/* 178 */           bold = true;
/* 179 */           currentData = italic ? this.boldItalicChars : this.boldChars;
/* 180 */         } else if (colorIndex == 20) {
/* 181 */           italic = true;
/* 182 */           currentData = bold ? this.boldItalicChars : this.italicChars;
/* 183 */         } else if (colorIndex == 21) {
/* 184 */           bold = false;
/* 185 */           italic = false;
/* 186 */           currentData = this.charData;
/*     */         } 
/* 188 */         i++;
/*     */       
/*     */       }
/* 191 */       else if (character < currentData.length && character >= '\000') {
/* 192 */         width += (currentData[character]).width - 8 + this.charOffset;
/*     */       } 
/* 194 */     }  return width / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFont(Font font) {
/* 199 */     super.setFont(font);
/* 200 */     setupBoldItalicIDs();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAntiAlias(boolean antiAlias) {
/* 205 */     super.setAntiAlias(antiAlias);
/* 206 */     setupBoldItalicIDs();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFractionalMetrics(boolean fractionalMetrics) {
/* 211 */     super.setFractionalMetrics(fractionalMetrics);
/* 212 */     setupBoldItalicIDs();
/*     */   }
/*     */   
/*     */   private void setupBoldItalicIDs() {
/* 216 */     this.texBold = setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
/* 217 */     this.texItalic = setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
/* 218 */     this.texItalicBold = setupTexture(this.font.deriveFont(3), this.antiAlias, this.fractionalMetrics, this.boldItalicChars);
/*     */   }
/*     */   
/*     */   private void drawLine(double x, double y, double x1, double y1, float width) {
/* 222 */     GL11.glDisable(3553);
/* 223 */     GL11.glLineWidth(width);
/* 224 */     GL11.glBegin(1);
/* 225 */     GL11.glVertex2d(x, y);
/* 226 */     GL11.glVertex2d(x1, y1);
/* 227 */     GL11.glEnd();
/* 228 */     GL11.glEnable(3553);
/*     */   }
/*     */   
/*     */   public List<String> wrapWords(String text, double width) {
/* 232 */     ArrayList<String> finalWords = new ArrayList<>();
/* 233 */     if (getStringWidth(text) > width) {
/* 234 */       String[] words = text.split(" ");
/* 235 */       String currentWord = "";
/* 236 */       char lastColorCode = Character.MAX_VALUE;
/* 237 */       for (String word : words) {
/* 238 */         for (int i = 0; i < (word.toCharArray()).length; i++) {
/* 239 */           char c = word.toCharArray()[i];
/* 240 */           if (c == '§' && i < (word.toCharArray()).length - 1)
/* 241 */             lastColorCode = word.toCharArray()[i + 1]; 
/*     */         } 
/* 243 */         StringBuilder stringBuilder = new StringBuilder();
/* 244 */         if (getStringWidth(stringBuilder.append(currentWord).append(word).append(" ").toString()) < width) {
/* 245 */           currentWord = currentWord + word + " ";
/*     */         } else {
/*     */           
/* 248 */           finalWords.add(currentWord);
/* 249 */           currentWord = "§" + lastColorCode + word + " ";
/*     */         } 
/* 251 */       }  if (currentWord.length() > 0) {
/* 252 */         if (getStringWidth(currentWord) < width) {
/* 253 */           finalWords.add("§" + lastColorCode + currentWord + " ");
/* 254 */           currentWord = "";
/*     */         } else {
/* 256 */           for (String s : formatString(currentWord, width)) {
/* 257 */             finalWords.add(s);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } else {
/* 262 */       finalWords.add(text);
/*     */     } 
/* 264 */     return finalWords;
/*     */   }
/*     */   
/*     */   public List<String> formatString(String string, double width) {
/* 268 */     ArrayList<String> finalWords = new ArrayList<>();
/* 269 */     String currentWord = "";
/* 270 */     char lastColorCode = Character.MAX_VALUE;
/* 271 */     char[] chars = string.toCharArray();
/* 272 */     for (int i = 0; i < chars.length; i++) {
/* 273 */       char c = chars[i];
/* 274 */       if (c == '§' && i < chars.length - 1) {
/* 275 */         lastColorCode = chars[i + 1];
/*     */       }
/* 277 */       StringBuilder stringBuilder = new StringBuilder();
/* 278 */       if (getStringWidth(stringBuilder.append(currentWord).append(c).toString()) < width) {
/* 279 */         currentWord = currentWord + c;
/*     */       } else {
/*     */         
/* 282 */         finalWords.add(currentWord);
/* 283 */         currentWord = "§" + lastColorCode + c;
/*     */       } 
/* 285 */     }  if (currentWord.length() > 0) {
/* 286 */       finalWords.add(currentWord);
/*     */     }
/* 288 */     return finalWords;
/*     */   }
/*     */   
/*     */   private void setupMinecraftColorcodes() {
/* 292 */     for (int index = 0; index < 32; index++) {
/* 293 */       int noClue = (index >> 3 & 0x1) * 85;
/* 294 */       int red = (index >> 2 & 0x1) * 170 + noClue;
/* 295 */       int green = (index >> 1 & 0x1) * 170 + noClue;
/* 296 */       int blue = (index >> 0 & 0x1) * 170 + noClue;
/* 297 */       if (index == 6) {
/* 298 */         red += 85;
/*     */       }
/* 300 */       if (index >= 16) {
/* 301 */         red /= 4;
/* 302 */         green /= 4;
/* 303 */         blue /= 4;
/*     */       } 
/* 305 */       this.colorCode[index] = (red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\customfont\CustomFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */