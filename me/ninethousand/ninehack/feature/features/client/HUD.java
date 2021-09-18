/*     */ package me.ninethousand.ninehack.feature.features.client;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.Objects;
/*     */ import me.ninethousand.ninehack.NineHack;
/*     */ import me.ninethousand.ninehack.event.events.Render2DEvent;
/*     */ import me.ninethousand.ninehack.feature.Category;
/*     */ import me.ninethousand.ninehack.feature.Feature;
/*     */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*     */ import me.ninethousand.ninehack.feature.setting.NumberSetting;
/*     */ import me.ninethousand.ninehack.feature.setting.Setting;
/*     */ import me.ninethousand.ninehack.mixin.accessors.game.IMinecraft;
/*     */ import me.ninethousand.ninehack.util.ColorUtil;
/*     */ import me.ninethousand.ninehack.util.RenderUtil;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ 
/*     */ @NineHackFeature(name = "HUD", description = "Based HUD", category = Category.Client)
/*     */ public class HUD
/*     */   extends Feature {
/*     */   public static Feature INSTANCE;
/*  26 */   private static ScaledResolution res = new ScaledResolution(mc);
/*  27 */   private static final ItemStack totem = new ItemStack(Items.field_190929_cY);
/*     */   
/*  29 */   public static final Setting<Boolean> rainbow = new Setting("Rainbow", Boolean.valueOf(true));
/*  30 */   public static final Setting<Boolean> rolling = new Setting("Rolling", Boolean.valueOf(true));
/*  31 */   public static final NumberSetting<Integer> factor = new NumberSetting("Rolling Factor", Integer.valueOf(0), Integer.valueOf(10), Integer.valueOf(100), 1);
/*  32 */   public static final NumberSetting<Integer> hue = new NumberSetting("Hue", Integer.valueOf(0), Integer.valueOf(255), Integer.valueOf(255), 1);
/*  33 */   public static final NumberSetting<Integer> saturation = new NumberSetting("Saturation", Integer.valueOf(0), Integer.valueOf(255), Integer.valueOf(255), 1);
/*  34 */   public static final NumberSetting<Integer> brightness = new NumberSetting("Brightness", Integer.valueOf(0), Integer.valueOf(255), Integer.valueOf(255), 1);
/*     */   
/*  36 */   public static final Setting<Boolean> watermark = new Setting("Watermark", Boolean.valueOf(true));
/*  37 */   public static final Setting<WatermarkMode> watermarkMode = new Setting("Watermark Mode", WatermarkMode.Default);
/*  38 */   public static final NumberSetting<Integer> watermarkX = new NumberSetting("Watermark X", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(960), 1);
/*  39 */   public static final NumberSetting<Integer> watermarkY = new NumberSetting("Watermark Y", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(530), 1);
/*     */   
/*  41 */   public static final Setting<Boolean> ping = new Setting("Ping", Boolean.valueOf(true));
/*  42 */   public static final NumberSetting<Integer> pingX = new NumberSetting("Ping X", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(960), 1);
/*  43 */   public static final NumberSetting<Integer> pingY = new NumberSetting("Ping Y", Integer.valueOf(1), Integer.valueOf(10), Integer.valueOf(530), 1);
/*     */   
/*  45 */   public static final Setting<Boolean> welcomer = new Setting("Welcomer", Boolean.valueOf(true));
/*  46 */   public static final Setting<WelcomeMode> welcomerMode = new Setting("Welcomer Mode", WelcomeMode.Default);
/*  47 */   public static final NumberSetting<Integer> welcomerX = new NumberSetting("Welcomer X", Integer.valueOf(1), Integer.valueOf(480), Integer.valueOf(960), 1);
/*  48 */   public static final NumberSetting<Integer> welcomerY = new NumberSetting("Welcomer Y", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(530), 1);
/*     */   
/*  50 */   public static final Setting<Boolean> coords = new Setting("Coords", Boolean.valueOf(true));
/*  51 */   public static final NumberSetting<Integer> coordX = new NumberSetting("Coords X", Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(960), 1);
/*  52 */   public static final NumberSetting<Integer> coordY = new NumberSetting("Coords Y", Integer.valueOf(1), Integer.valueOf(530), Integer.valueOf(530), 1);
/*     */   
/*  54 */   public static final Setting<Boolean> totems = new Setting("Totem", Boolean.valueOf(true));
/*     */   
/*  56 */   public static final Setting<Boolean> armor = new Setting("Armor", Boolean.valueOf(true));
/*  57 */   public static final Setting<Boolean> percent = new Setting("Armor %", Boolean.valueOf(true));
/*     */   
/*     */   public HUD() {
/*  60 */     addSettings(new Setting[] { rainbow, rolling, (Setting)factor, (Setting)hue, (Setting)saturation, (Setting)brightness, watermark, watermarkMode, (Setting)watermarkX, (Setting)watermarkY, ping, (Setting)pingX, (Setting)pingY, welcomer, welcomerMode, (Setting)welcomerX, (Setting)welcomerY, coords, (Setting)coordX, (Setting)coordY, totems, armor, percent });
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  89 */     if (((Boolean)rainbow.getValue()).booleanValue()) {
/*  90 */       if (((Integer)hue.getValue()).intValue() < 255) {
/*  91 */         hue.setValue(Integer.valueOf(((Integer)hue.getValue()).intValue() + 1));
/*     */       } else {
/*     */         
/*  94 */         hue.setValue(Integer.valueOf(0));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void on2DRenderEvent(Render2DEvent event) {
/* 101 */     if (((Boolean)watermark.getValue()).booleanValue()) drawWatermark(((Integer)watermarkX.getValue()).intValue(), ((Integer)watermarkY.getValue()).intValue()); 
/* 102 */     if (((Boolean)ping.getValue()).booleanValue()) drawPing(((Integer)pingX.getValue()).intValue(), ((Integer)pingY.getValue()).intValue()); 
/* 103 */     if (((Boolean)welcomer.getValue()).booleanValue()) drawWelcomer(((Integer)welcomerX.getValue()).intValue(), ((Integer)welcomerY.getValue()).intValue()); 
/* 104 */     if (((Boolean)coords.getValue()).booleanValue()) drawCoords(((Integer)coordX.getValue()).intValue(), ((Integer)coordY.getValue()).intValue()); 
/* 105 */     if (((Boolean)totems.getValue()).booleanValue()) drawTotem(); 
/* 106 */     if (((Boolean)armor.getValue()).booleanValue()) drawArmor(((Boolean)percent.getValue()).booleanValue());
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawWatermark(int x, int y) {
/* 112 */     String watermark = "Ninehack";
/*     */     
/* 114 */     if (watermarkMode.getValue() == WatermarkMode.Default) {
/* 115 */       watermark = "NineHack v1.0.1";
/*     */     }
/* 117 */     else if (watermarkMode.getValue() == WatermarkMode.Tesco) {
/* 118 */       if (((IMinecraft)mc).getIntegratedServerIsRunning()) {
/* 119 */         watermark = "NineHack v1.0.1 : " + mc.field_71439_g.func_70005_c_() + " : Singleplayer";
/*     */       } else {
/*     */         
/* 122 */         watermark = "NineHack v1.0.1 : " + mc.field_71439_g.func_70005_c_() + " : " + ((ServerData)Objects.requireNonNull((T)mc.func_147104_D())).field_78845_b;
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     drawText(watermark, x, y);
/*     */   }
/*     */   
/*     */   public void drawPing(int x, int y) {
/* 130 */     String text = "Ping " + TextFormatting.WHITE + (!mc.func_71356_B() ? mc.func_147114_u().func_175102_a(mc.field_71439_g.func_110124_au()).func_178853_c() : -1) + " ms";
/* 131 */     drawText(text, x, y);
/*     */   }
/*     */   
/*     */   public void drawWelcomer(int x, int y) {
/* 135 */     String text = "Hello";
/*     */     
/* 137 */     switch ((WelcomeMode)welcomerMode.getValue()) {
/*     */       case Default:
/* 139 */         text = "Welcome, " + mc.field_71439_g.func_70005_c_() + " :^)";
/*     */         break;
/*     */     } 
/* 142 */     drawText(text, x, y);
/*     */   }
/*     */   
/*     */   public void drawCoords(int x, int y) {
/* 146 */     boolean inHell = mc.field_71441_e.func_180494_b(mc.field_71439_g.func_180425_c()).func_185359_l().equals("Hell");
/* 147 */     int posX = (int)mc.field_71439_g.field_70165_t;
/* 148 */     int posY = (int)mc.field_71439_g.field_70163_u;
/* 149 */     int posZ = (int)mc.field_71439_g.field_70161_v;
/* 150 */     float nether = inHell ? 8.0F : 0.125F;
/* 151 */     int hposX = (int)(mc.field_71439_g.field_70165_t * nether);
/* 152 */     int hposZ = (int)(mc.field_71439_g.field_70161_v * nether);
/*     */     
/* 154 */     String coordinates = "XYZ " + posX + ", " + posY + ", " + posZ + " [" + hposX + ", " + hposZ + "]";
/*     */     
/* 156 */     drawText(coordinates, x, y);
/*     */   }
/*     */   
/*     */   public void drawText(String text, int x, int y) {
/* 160 */     if (((Boolean)rolling.getValue()).booleanValue()) { NineHack.TEXT_MANAGER.drawRainbowString(text, x, y, Color.HSBtoRGB(((Integer)hue.getValue()).intValue() / 255.0F, ((Integer)saturation.getValue()).intValue() / 255.0F, ((Integer)brightness.getValue()).intValue() / 255.0F), ((Integer)factor.getValue()).intValue(), ((Boolean)CustomFont.shadow.getValue()).booleanValue()); }
/* 161 */     else { NineHack.TEXT_MANAGER.drawStringWithShadow(text, x, y, Color.HSBtoRGB(((Integer)hue.getValue()).intValue() / 255.0F, ((Integer)saturation.getValue()).intValue() / 255.0F, ((Integer)brightness.getValue()).intValue() / 255.0F)); }
/*     */   
/*     */   }
/*     */   public void drawTotem() {
/* 165 */     int width = NineHack.TEXT_MANAGER.scaledWidth;
/* 166 */     int height = NineHack.TEXT_MANAGER.scaledHeight;
/* 167 */     int totems = mc.field_71439_g.field_71071_by.field_70462_a.stream().filter(itemStack -> (itemStack.func_77973_b() == Items.field_190929_cY)).mapToInt(ItemStack::func_190916_E).sum();
/* 168 */     if (mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_190929_cY) {
/* 169 */       totems += mc.field_71439_g.func_184592_cb().func_190916_E();
/*     */     }
/* 171 */     if (totems > 0) {
/* 172 */       GlStateManager.func_179098_w();
/* 173 */       int i = width / 2;
/* 174 */       int iteration = 0;
/* 175 */       int y = height - 55 - ((mc.field_71439_g.func_70090_H() && mc.field_71442_b.func_78763_f()) ? 10 : 0);
/* 176 */       int x = i - 189 + 180 + 2;
/* 177 */       GlStateManager.func_179126_j();
/* 178 */       RenderUtil.itemRender.field_77023_b = 200.0F;
/* 179 */       RenderUtil.itemRender.func_180450_b(totem, x, y);
/* 180 */       RenderUtil.itemRender.func_180453_a(mc.field_71466_p, totem, x, y, "");
/* 181 */       RenderUtil.itemRender.field_77023_b = 0.0F;
/* 182 */       GlStateManager.func_179098_w();
/* 183 */       GlStateManager.func_179140_f();
/* 184 */       GlStateManager.func_179097_i();
/* 185 */       drawText(totems + "", x + 19 - NineHack.TEXT_MANAGER.getStringWidth(totems + ""), y + 9);
/* 186 */       GlStateManager.func_179126_j();
/* 187 */       GlStateManager.func_179140_f();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawArmor(boolean percent) {
/* 192 */     int width = NineHack.TEXT_MANAGER.scaledWidth;
/* 193 */     int height = NineHack.TEXT_MANAGER.scaledHeight;
/* 194 */     GlStateManager.func_179098_w();
/* 195 */     int i = width / 2;
/* 196 */     int iteration = 0;
/* 197 */     int y = height - 55 - ((mc.field_71439_g.func_70090_H() && mc.field_71442_b.func_78763_f()) ? 10 : 0);
/* 198 */     for (ItemStack is : mc.field_71439_g.field_71071_by.field_70460_b) {
/* 199 */       iteration++;
/* 200 */       if (is.func_190926_b()) {
/*     */         continue;
/*     */       }
/* 203 */       int x = i - 90 + (9 - iteration) * 20 + 2;
/* 204 */       GlStateManager.func_179126_j();
/* 205 */       RenderUtil.itemRender.field_77023_b = 200.0F;
/* 206 */       RenderUtil.itemRender.func_180450_b(is, x, y);
/* 207 */       RenderUtil.itemRender.func_180453_a(mc.field_71466_p, is, x, y, "");
/* 208 */       RenderUtil.itemRender.field_77023_b = 0.0F;
/* 209 */       GlStateManager.func_179098_w();
/* 210 */       GlStateManager.func_179140_f();
/* 211 */       GlStateManager.func_179097_i();
/* 212 */       String s = (is.func_190916_E() > 1) ? (is.func_190916_E() + "") : "";
/* 213 */       NineHack.TEXT_MANAGER.drawStringWithShadow(s, (x + 19 - 2 - NineHack.TEXT_MANAGER.getStringWidth(s)), (y + 9), 16777215);
/* 214 */       if (!percent) {
/*     */         continue;
/*     */       }
/* 217 */       int dmg = 0;
/* 218 */       int itemDurability = is.func_77958_k() - is.func_77952_i();
/* 219 */       float green = (is.func_77958_k() - is.func_77952_i()) / is.func_77958_k();
/* 220 */       float red = 1.0F - green;
/* 221 */       if (percent) {
/* 222 */         dmg = 100 - (int)(red * 100.0F);
/*     */       } else {
/* 224 */         dmg = itemDurability;
/*     */       } 
/* 226 */       NineHack.TEXT_MANAGER.drawStringWithShadow(dmg + "", (x + 8 - NineHack.TEXT_MANAGER.getStringWidth(dmg + "") / 2), (y - 11), ColorUtil.toRGBA((int)(red * 255.0F), (int)(green * 255.0F), 0));
/*     */     } 
/* 228 */     GlStateManager.func_179126_j();
/* 229 */     GlStateManager.func_179140_f();
/*     */   }
/*     */   
/*     */   public enum WatermarkMode {
/* 233 */     Default,
/* 234 */     Tesco;
/*     */   }
/*     */   
/*     */   public enum WelcomeMode {
/* 238 */     Default;
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\client\HUD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */