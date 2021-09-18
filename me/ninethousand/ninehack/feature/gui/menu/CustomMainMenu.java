/*     */ package me.ninethousand.ninehack.feature.gui.menu;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.image.BufferedImage;
/*     */ import me.ninethousand.ninehack.NineHack;
/*     */ import me.ninethousand.ninehack.util.RenderUtil;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiMultiplayer;
/*     */ import net.minecraft.client.gui.GuiOptions;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiWorldSelection;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class CustomMainMenu extends GuiScreen {
/*  18 */   private final ResourceLocation bgLocation = new ResourceLocation("textures/background.png");
/*  19 */   private final ResourceLocation txtLogoLocation = new ResourceLocation("textures/textlogo.png");
/*     */   private int y;
/*     */   private int x;
/*     */   private int singleplayerWidth;
/*     */   private int multiplayerWidth;
/*     */   private int settingsWidth;
/*     */   private int exitWidth;
/*     */   private int textHeight;
/*     */   private float xOffset;
/*     */   private float yOffset;
/*     */   
/*     */   public static void drawCompleteImage(float posX, float posY, float width, float height) {
/*  31 */     GL11.glPushMatrix();
/*  32 */     GL11.glTranslatef(posX, posY, 0.0F);
/*  33 */     GL11.glBegin(7);
/*  34 */     GL11.glTexCoord2f(0.0F, 0.0F);
/*  35 */     GL11.glVertex3f(0.0F, 0.0F, 0.0F);
/*  36 */     GL11.glTexCoord2f(0.0F, 1.0F);
/*  37 */     GL11.glVertex3f(0.0F, height, 0.0F);
/*  38 */     GL11.glTexCoord2f(1.0F, 1.0F);
/*  39 */     GL11.glVertex3f(width, height, 0.0F);
/*  40 */     GL11.glTexCoord2f(1.0F, 0.0F);
/*  41 */     GL11.glVertex3f(width, 0.0F, 0.0F);
/*  42 */     GL11.glEnd();
/*  43 */     GL11.glPopMatrix();
/*     */   }
/*     */   
/*     */   public static boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
/*  47 */     return (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY < y + height);
/*     */   }
/*     */   
/*     */   public void func_73866_w_() {
/*  51 */     this.x = this.field_146294_l / 2;
/*  52 */     this.y = this.field_146295_m / 4 + 48;
/*  53 */     this.field_146292_n.add(new TextButton(0, this.x, this.y + 20, "Singleplayer"));
/*  54 */     this.field_146292_n.add(new TextButton(1, this.x, this.y + 44, "Multiplayer"));
/*  55 */     this.field_146292_n.add(new TextButton(2, this.x, this.y + 66, "Settings"));
/*  56 */     this.field_146292_n.add(new TextButton(2, this.x, this.y + 88, "Exit"));
/*  57 */     GlStateManager.func_179090_x();
/*  58 */     GlStateManager.func_179147_l();
/*  59 */     GlStateManager.func_179118_c();
/*  60 */     GlStateManager.func_179103_j(7425);
/*  61 */     GlStateManager.func_179103_j(7424);
/*  62 */     GlStateManager.func_179084_k();
/*  63 */     GlStateManager.func_179141_d();
/*  64 */     GlStateManager.func_179098_w();
/*     */   }
/*     */   
/*     */   public void func_73876_c() {
/*  68 */     super.func_73876_c();
/*     */   }
/*     */   
/*     */   public void func_73864_a(int mouseX, int mouseY, int mouseButton) {
/*  72 */     if (isHovered(this.x - NineHack.TEXT_MANAGER.getStringWidth("Singleplayer") / 2, this.y + 20, NineHack.TEXT_MANAGER.getStringWidth("Singleplayer"), NineHack.TEXT_MANAGER.getFontHeight(), mouseX, mouseY)) {
/*  73 */       this.field_146297_k.func_147108_a((GuiScreen)new GuiWorldSelection(this));
/*  74 */     } else if (isHovered(this.x - NineHack.TEXT_MANAGER.getStringWidth("Multiplayer") / 2, this.y + 44, NineHack.TEXT_MANAGER.getStringWidth("Multiplayer"), NineHack.TEXT_MANAGER.getFontHeight(), mouseX, mouseY)) {
/*  75 */       this.field_146297_k.func_147108_a((GuiScreen)new GuiMultiplayer(this));
/*  76 */     } else if (isHovered(this.x - NineHack.TEXT_MANAGER.getStringWidth("Settings") / 2, this.y + 66, NineHack.TEXT_MANAGER.getStringWidth("Settings"), NineHack.TEXT_MANAGER.getFontHeight(), mouseX, mouseY)) {
/*  77 */       this.field_146297_k.func_147108_a((GuiScreen)new GuiOptions(this, this.field_146297_k.field_71474_y));
/*  78 */     } else if (isHovered(this.x - NineHack.TEXT_MANAGER.getStringWidth("Exit") / 2, this.y + 88, NineHack.TEXT_MANAGER.getStringWidth("Exit"), NineHack.TEXT_MANAGER.getFontHeight(), mouseX, mouseY)) {
/*  79 */       this.field_146297_k.func_71400_g();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/*  84 */     this.xOffset = -1.0F * (mouseX - this.field_146294_l / 2.0F) / this.field_146294_l / 32.0F;
/*  85 */     this.yOffset = -1.0F * (mouseY - this.field_146295_m / 2.0F) / this.field_146295_m / 18.0F;
/*  86 */     this.x = this.field_146294_l / 2;
/*  87 */     this.y = this.field_146295_m / 4 + 48;
/*  88 */     GlStateManager.func_179098_w();
/*  89 */     GlStateManager.func_179084_k();
/*  90 */     this.field_146297_k.func_110434_K().func_110577_a(this.bgLocation);
/*  91 */     drawCompleteImage(-16.0F + this.xOffset, -9.0F + this.yOffset, (this.field_146294_l + 32), (this.field_146295_m + 18));
/*  92 */     ScaledResolution res = new ScaledResolution(this.field_146297_k);
/*     */     
/*  94 */     NineHack.TEXT_MANAGER.drawStringCustomMenu("NineHack v1.0.1", (this.x - NineHack.TEXT_MANAGER.getStringWidthMenu("NineHack v1.0.1") / 2), (this.y - 23), Color.white.getRGB(), true);
/*  95 */     super.func_73863_a(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public BufferedImage parseBackground(BufferedImage background) {
/* 100 */     int width = 1920;
/* 101 */     int srcWidth = background.getWidth();
/* 102 */     int srcHeight = background.getHeight(); int height;
/* 103 */     for (height = 1080; width < srcWidth || height < srcHeight; ) { width *= 2; height *= 2; }
/*     */     
/* 105 */     BufferedImage imgNew = new BufferedImage(width, height, 2);
/* 106 */     Graphics g = imgNew.getGraphics();
/* 107 */     g.drawImage(background, 0, 0, null);
/* 108 */     g.dispose();
/* 109 */     return imgNew;
/*     */   }
/*     */   
/*     */   private static class TextButton extends GuiButton {
/*     */     public TextButton(int buttonId, int x, int y, String buttonText) {
/* 114 */       super(buttonId, x, y, NineHack.TEXT_MANAGER.getStringWidth(buttonText), NineHack.TEXT_MANAGER.getFontHeight(), buttonText);
/*     */     }
/*     */     
/*     */     public void func_191745_a(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
/* 118 */       if (this.field_146125_m) {
/* 119 */         this.field_146124_l = true;
/* 120 */         this.field_146123_n = (mouseX >= this.field_146128_h - NineHack.TEXT_MANAGER.getStringWidth(this.field_146126_j) / 2.0F && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g);
/* 121 */         NineHack.TEXT_MANAGER.drawStringWithShadow(this.field_146126_j, this.field_146128_h - NineHack.TEXT_MANAGER.getStringWidth(this.field_146126_j) / 2.0F, this.field_146129_i, Color.WHITE.getRGB());
/* 122 */         if (this.field_146123_n) {
/* 123 */           RenderUtil.drawLine((this.field_146128_h - 1) - NineHack.TEXT_MANAGER.getStringWidth(this.field_146126_j) / 2.0F, (this.field_146129_i + 2 + NineHack.TEXT_MANAGER.getFontHeight()), this.field_146128_h + NineHack.TEXT_MANAGER.getStringWidth(this.field_146126_j) / 2.0F + 1.0F, (this.field_146129_i + 2 + NineHack.TEXT_MANAGER.getFontHeight()), 1.0F, Color.WHITE.getRGB());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
/* 129 */       return (this.field_146124_l && this.field_146125_m && mouseX >= this.field_146128_h - NineHack.TEXT_MANAGER.getStringWidth(this.field_146126_j) / 2.0F && mouseY >= this.field_146129_i && mouseX < this.field_146128_h + this.field_146120_f && mouseY < this.field_146129_i + this.field_146121_g);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\gui\menu\CustomMainMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */