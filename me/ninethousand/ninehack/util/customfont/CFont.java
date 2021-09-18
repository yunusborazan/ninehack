/*     */ package me.ninethousand.ninehack.util.customfont;
/*     */ import java.awt.Font;
/*     */ import java.awt.FontMetrics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class CFont {
/*  11 */   protected CharData[] charData = new CharData[256];
/*     */   protected Font font;
/*     */   protected boolean antiAlias;
/*     */   protected boolean fractionalMetrics;
/*  15 */   protected int fontHeight = -1;
/*  16 */   protected int charOffset = 0;
/*     */   protected DynamicTexture tex;
/*  18 */   private final float imgSize = 512.0F;
/*     */   
/*     */   public CFont(Font font, boolean antiAlias, boolean fractionalMetrics) {
/*  21 */     this.font = font;
/*  22 */     this.antiAlias = antiAlias;
/*  23 */     this.fractionalMetrics = fractionalMetrics;
/*  24 */     this.tex = setupTexture(font, antiAlias, fractionalMetrics, this.charData);
/*     */   }
/*     */   
/*     */   protected DynamicTexture setupTexture(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
/*  28 */     BufferedImage img = generateFontImage(font, antiAlias, fractionalMetrics, chars);
/*     */     try {
/*  30 */       return new DynamicTexture(img);
/*  31 */     } catch (Exception e) {
/*  32 */       e.printStackTrace();
/*  33 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected BufferedImage generateFontImage(Font font, boolean antiAlias, boolean fractionalMetrics, CharData[] chars) {
/*  38 */     getClass(); int imgSize = 512;
/*  39 */     BufferedImage bufferedImage = new BufferedImage(imgSize, imgSize, 2);
/*  40 */     Graphics2D g = (Graphics2D)bufferedImage.getGraphics();
/*  41 */     g.setFont(font);
/*  42 */     g.setColor(new Color(255, 255, 255, 0));
/*  43 */     g.fillRect(0, 0, imgSize, imgSize);
/*  44 */     g.setColor(Color.WHITE);
/*  45 */     g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, fractionalMetrics ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
/*  46 */     g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
/*  47 */     g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
/*  48 */     FontMetrics fontMetrics = g.getFontMetrics();
/*  49 */     int charHeight = 0;
/*  50 */     int positionX = 0;
/*  51 */     int positionY = 1;
/*  52 */     for (int i = 0; i < chars.length; i++) {
/*  53 */       char ch = (char)i;
/*  54 */       CharData charData = new CharData();
/*  55 */       Rectangle2D dimensions = fontMetrics.getStringBounds(String.valueOf(ch), g);
/*  56 */       charData.width = (dimensions.getBounds()).width + 8;
/*  57 */       charData.height = (dimensions.getBounds()).height;
/*  58 */       if (positionX + charData.width >= imgSize) {
/*  59 */         positionX = 0;
/*  60 */         positionY += charHeight;
/*  61 */         charHeight = 0;
/*     */       } 
/*  63 */       if (charData.height > charHeight) {
/*  64 */         charHeight = charData.height;
/*     */       }
/*  66 */       charData.storedX = positionX;
/*  67 */       charData.storedY = positionY;
/*  68 */       if (charData.height > this.fontHeight) {
/*  69 */         this.fontHeight = charData.height;
/*     */       }
/*  71 */       chars[i] = charData;
/*  72 */       g.drawString(String.valueOf(ch), positionX + 2, positionY + fontMetrics.getAscent());
/*  73 */       positionX += charData.width;
/*     */     } 
/*  75 */     return bufferedImage;
/*     */   }
/*     */   
/*     */   public void drawChar(CharData[] chars, char c, float x, float y) throws ArrayIndexOutOfBoundsException {
/*     */     try {
/*  80 */       drawQuad(x, y, (chars[c]).width, (chars[c]).height, (chars[c]).storedX, (chars[c]).storedY, (chars[c]).width, (chars[c]).height);
/*  81 */     } catch (Exception e) {
/*  82 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void drawQuad(float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
/*  87 */     getClass(); float renderSRCX = srcX / 512.0F;
/*  88 */     getClass(); float renderSRCY = srcY / 512.0F;
/*  89 */     getClass(); float renderSRCWidth = srcWidth / 512.0F;
/*  90 */     getClass(); float renderSRCHeight = srcHeight / 512.0F;
/*  91 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
/*  92 */     GL11.glVertex2d((x + width), y);
/*  93 */     GL11.glTexCoord2f(renderSRCX, renderSRCY);
/*  94 */     GL11.glVertex2d(x, y);
/*  95 */     GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
/*  96 */     GL11.glVertex2d(x, (y + height));
/*  97 */     GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
/*  98 */     GL11.glVertex2d(x, (y + height));
/*  99 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
/* 100 */     GL11.glVertex2d((x + width), (y + height));
/* 101 */     GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
/* 102 */     GL11.glVertex2d((x + width), y);
/*     */   }
/*     */   
/*     */   public int getStringHeight(String text) {
/* 106 */     return getHeight();
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 110 */     return (this.fontHeight - 8) / 2;
/*     */   }
/*     */   
/*     */   public int getStringWidth(String text) {
/* 114 */     int width = 0;
/* 115 */     for (char c : text.toCharArray()) {
/* 116 */       if (c < this.charData.length && c >= '\000')
/* 117 */         width += (this.charData[c]).width - 8 + this.charOffset; 
/*     */     } 
/* 119 */     return width / 2;
/*     */   }
/*     */   
/*     */   public boolean isAntiAlias() {
/* 123 */     return this.antiAlias;
/*     */   }
/*     */   
/*     */   public void setAntiAlias(boolean antiAlias) {
/* 127 */     if (this.antiAlias != antiAlias) {
/* 128 */       this.antiAlias = antiAlias;
/* 129 */       this.tex = setupTexture(this.font, antiAlias, this.fractionalMetrics, this.charData);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isFractionalMetrics() {
/* 134 */     return this.fractionalMetrics;
/*     */   }
/*     */   
/*     */   public void setFractionalMetrics(boolean fractionalMetrics) {
/* 138 */     if (this.fractionalMetrics != fractionalMetrics) {
/* 139 */       this.fractionalMetrics = fractionalMetrics;
/* 140 */       this.tex = setupTexture(this.font, this.antiAlias, fractionalMetrics, this.charData);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Font getFont() {
/* 145 */     return this.font;
/*     */   }
/*     */   
/*     */   public void setFont(Font font) {
/* 149 */     this.font = font;
/* 150 */     this.tex = setupTexture(font, this.antiAlias, this.fractionalMetrics, this.charData);
/*     */   }
/*     */   
/*     */   protected static class CharData {
/*     */     public int width;
/*     */     public int height;
/*     */     public int storedX;
/*     */     public int storedY;
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\customfont\CFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */