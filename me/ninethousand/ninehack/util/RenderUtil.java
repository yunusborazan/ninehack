/*     */ package me.ninethousand.ninehack.util;
/*     */ import java.awt.Color;
/*     */ import java.util.Objects;
/*     */ import me.ninethousand.ninehack.NineHack;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class RenderUtil implements NineHack.Globals {
/*  22 */   public static RenderItem itemRender = mc.func_175599_af();
/*     */   
/*     */   public static ICamera camera;
/*     */   
/*     */   public static void drawRect(double left, double top, double right, double bottom, Color color) {
/*  27 */     if (left < right) {
/*     */       
/*  29 */       double i = left;
/*  30 */       left = right;
/*  31 */       right = i;
/*     */     } 
/*     */     
/*  34 */     if (top < bottom) {
/*     */       
/*  36 */       double j = top;
/*  37 */       top = bottom;
/*  38 */       bottom = j;
/*     */     } 
/*     */     
/*  41 */     float f3 = (color.getRGB() >> 24 & 0xFF) / 255.0F;
/*  42 */     float f = (color.getRGB() >> 16 & 0xFF) / 255.0F;
/*  43 */     float f1 = (color.getRGB() >> 8 & 0xFF) / 255.0F;
/*  44 */     float f2 = (color.getRGB() & 0xFF) / 255.0F;
/*  45 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  46 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*  47 */     GlStateManager.func_179147_l();
/*  48 */     GlStateManager.func_179090_x();
/*  49 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  50 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/*  51 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/*  52 */     bufferbuilder.func_181662_b(left, bottom, 0.0D).func_181675_d();
/*  53 */     bufferbuilder.func_181662_b(right, bottom, 0.0D).func_181675_d();
/*  54 */     bufferbuilder.func_181662_b(right, top, 0.0D).func_181675_d();
/*  55 */     bufferbuilder.func_181662_b(left, top, 0.0D).func_181675_d();
/*  56 */     tessellator.func_78381_a();
/*  57 */     GlStateManager.func_179098_w();
/*  58 */     GlStateManager.func_179084_k();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawRect(int left, int top, int right, int bottom, Color color) {
/*  63 */     if (left < right) {
/*     */       
/*  65 */       int i = left;
/*  66 */       left = right;
/*  67 */       right = i;
/*     */     } 
/*     */     
/*  70 */     if (top < bottom) {
/*     */       
/*  72 */       int j = top;
/*  73 */       top = bottom;
/*  74 */       bottom = j;
/*     */     } 
/*     */     
/*  77 */     float f3 = (color.getRGB() >> 24 & 0xFF) / 255.0F;
/*  78 */     float f = (color.getRGB() >> 16 & 0xFF) / 255.0F;
/*  79 */     float f1 = (color.getRGB() >> 8 & 0xFF) / 255.0F;
/*  80 */     float f2 = (color.getRGB() & 0xFF) / 255.0F;
/*  81 */     Tessellator tessellator = Tessellator.func_178181_a();
/*  82 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/*  83 */     GlStateManager.func_179147_l();
/*  84 */     GlStateManager.func_179090_x();
/*  85 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  86 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/*  87 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/*  88 */     bufferbuilder.func_181662_b(left, bottom, 0.0D).func_181675_d();
/*  89 */     bufferbuilder.func_181662_b(right, bottom, 0.0D).func_181675_d();
/*  90 */     bufferbuilder.func_181662_b(right, top, 0.0D).func_181675_d();
/*  91 */     bufferbuilder.func_181662_b(left, top, 0.0D).func_181675_d();
/*  92 */     tessellator.func_78381_a();
/*  93 */     GlStateManager.func_179098_w();
/*  94 */     GlStateManager.func_179084_k();
/*     */   }
/*     */   
/*     */   public static void drawOutlineRect(double left, double top, double right, double bottom, Color color, float lineWidth) {
/*  98 */     if (left < right) {
/*  99 */       double i = left;
/* 100 */       left = right;
/* 101 */       right = i;
/*     */     } 
/*     */     
/* 104 */     if (top < bottom) {
/* 105 */       double j = top;
/* 106 */       top = bottom;
/* 107 */       bottom = j;
/*     */     } 
/*     */     
/* 110 */     float f3 = (color.getRGB() >> 24 & 0xFF) / 255.0F;
/* 111 */     float f = (color.getRGB() >> 16 & 0xFF) / 255.0F;
/* 112 */     float f1 = (color.getRGB() >> 8 & 0xFF) / 255.0F;
/* 113 */     float f2 = (color.getRGB() & 0xFF) / 255.0F;
/* 114 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 115 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 116 */     GlStateManager.func_179147_l();
/* 117 */     GL11.glPolygonMode(1032, 6913);
/* 118 */     GL11.glLineWidth(lineWidth);
/* 119 */     GlStateManager.func_179090_x();
/* 120 */     GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 121 */     GlStateManager.func_179131_c(f, f1, f2, f3);
/* 122 */     bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181705_e);
/* 123 */     bufferbuilder.func_181662_b(left, bottom, 0.0D).func_181675_d();
/* 124 */     bufferbuilder.func_181662_b(right, bottom, 0.0D).func_181675_d();
/* 125 */     bufferbuilder.func_181662_b(right, top, 0.0D).func_181675_d();
/* 126 */     bufferbuilder.func_181662_b(left, top, 0.0D).func_181675_d();
/* 127 */     tessellator.func_78381_a();
/* 128 */     GlStateManager.func_179098_w();
/* 129 */     GlStateManager.func_179084_k();
/* 130 */     GL11.glPolygonMode(1032, 6914);
/*     */   }
/*     */   
/*     */   public static void drawLine(float x, float y, float x1, float y1, float thickness, int hex) {
/* 134 */     float red = (hex >> 16 & 0xFF) / 255.0F;
/* 135 */     float green = (hex >> 8 & 0xFF) / 255.0F;
/* 136 */     float blue = (hex & 0xFF) / 255.0F;
/* 137 */     float alpha = (hex >> 24 & 0xFF) / 255.0F;
/* 138 */     GlStateManager.func_179094_E();
/* 139 */     GlStateManager.func_179090_x();
/* 140 */     GlStateManager.func_179147_l();
/* 141 */     GlStateManager.func_179118_c();
/* 142 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/* 143 */     GlStateManager.func_179103_j(7425);
/* 144 */     GL11.glLineWidth(thickness);
/* 145 */     GL11.glEnable(2848);
/* 146 */     GL11.glHint(3154, 4354);
/* 147 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 148 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 149 */     bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
/* 150 */     bufferbuilder.func_181662_b(x, y, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 151 */     bufferbuilder.func_181662_b(x1, y1, 0.0D).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 152 */     tessellator.func_78381_a();
/* 153 */     GlStateManager.func_179103_j(7424);
/* 154 */     GL11.glDisable(2848);
/* 155 */     GlStateManager.func_179084_k();
/* 156 */     GlStateManager.func_179141_d();
/* 157 */     GlStateManager.func_179098_w();
/* 158 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawParticles() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawBoxESP(BlockPos pos, Color color, boolean secondC, Color secondColor, float lineWidth, boolean outline, boolean box, int boxAlpha, boolean air) {
/* 170 */     if (box) {
/* 171 */       drawBox(pos, new Color(color.getRed(), color.getGreen(), color.getBlue(), boxAlpha));
/*     */     }
/* 173 */     if (outline) {
/* 174 */       drawBlockOutline(pos, secondC ? secondColor : color, lineWidth, air);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawBlockOutline(BlockPos pos, Color color, float linewidth, boolean air) {
/* 179 */     IBlockState iblockstate = mc.field_71441_e.func_180495_p(pos);
/* 180 */     if ((air || iblockstate.func_185904_a() != Material.field_151579_a) && mc.field_71441_e.func_175723_af().func_177746_a(pos)) {
/* 181 */       Vec3d interp = EntityUtil.interpolateEntity((Entity)mc.field_71439_g, mc.func_184121_ak());
/* 182 */       drawBlockOutline(iblockstate.func_185918_c((World)mc.field_71441_e, pos).func_186662_g(0.0020000000949949026D).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c), color, linewidth);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawBoxESP(BlockPos pos, Color color, boolean secondC, Color secondColor, float lineWidth, boolean outline, boolean box, int boxAlpha, boolean air, double height, boolean gradientBox, boolean gradientOutline, boolean invertGradientBox, boolean invertGradientOutline, int gradientAlpha) {
/* 187 */     if (box) {
/* 188 */       drawBox(pos, new Color(color.getRed(), color.getGreen(), color.getBlue(), boxAlpha), height, gradientBox, invertGradientBox, gradientAlpha);
/*     */     }
/* 190 */     if (outline) {
/* 191 */       drawBlockOutline(pos, secondC ? secondColor : color, lineWidth, air, height, gradientOutline, invertGradientOutline, gradientAlpha);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void drawBox(BlockPos pos, Color color) {
/* 196 */     AxisAlignedBB bb = new AxisAlignedBB(pos.func_177958_n() - (mc.func_175598_ae()).field_78730_l, pos.func_177956_o() - (mc.func_175598_ae()).field_78731_m, pos.func_177952_p() - (mc.func_175598_ae()).field_78728_n, (pos.func_177958_n() + 1) - (mc.func_175598_ae()).field_78730_l, (pos.func_177956_o() + 1) - (mc.func_175598_ae()).field_78731_m, (pos.func_177952_p() + 1) - (mc.func_175598_ae()).field_78728_n);
/* 197 */     camera.func_78547_a(((Entity)Objects.requireNonNull((T)mc.func_175606_aa())).field_70165_t, (mc.func_175606_aa()).field_70163_u, (mc.func_175606_aa()).field_70161_v);
/* 198 */     if (camera.func_78546_a(new AxisAlignedBB(bb.field_72340_a + (mc.func_175598_ae()).field_78730_l, bb.field_72338_b + (mc.func_175598_ae()).field_78731_m, bb.field_72339_c + (mc.func_175598_ae()).field_78728_n, bb.field_72336_d + (mc.func_175598_ae()).field_78730_l, bb.field_72337_e + (mc.func_175598_ae()).field_78731_m, bb.field_72334_f + (mc.func_175598_ae()).field_78728_n))) {
/* 199 */       GlStateManager.func_179094_E();
/* 200 */       GlStateManager.func_179147_l();
/* 201 */       GlStateManager.func_179097_i();
/* 202 */       GlStateManager.func_179120_a(770, 771, 0, 1);
/* 203 */       GlStateManager.func_179090_x();
/* 204 */       GlStateManager.func_179132_a(false);
/* 205 */       GL11.glEnable(2848);
/* 206 */       GL11.glHint(3154, 4354);
/* 207 */       RenderGlobal.func_189696_b(bb, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/* 208 */       GL11.glDisable(2848);
/* 209 */       GlStateManager.func_179132_a(true);
/* 210 */       GlStateManager.func_179126_j();
/* 211 */       GlStateManager.func_179098_w();
/* 212 */       GlStateManager.func_179084_k();
/* 213 */       GlStateManager.func_179121_F();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawBox(BlockPos pos, Color color, double height, boolean gradient, boolean invert, int alpha) {
/* 218 */     if (gradient) {
/* 219 */       Color endColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
/* 220 */       drawOpenGradientBox(pos, invert ? endColor : color, invert ? color : endColor, height);
/*     */       return;
/*     */     } 
/* 223 */     AxisAlignedBB bb = new AxisAlignedBB(pos.func_177958_n() - (mc.func_175598_ae()).field_78730_l, pos.func_177956_o() - (mc.func_175598_ae()).field_78731_m, pos.func_177952_p() - (mc.func_175598_ae()).field_78728_n, (pos.func_177958_n() + 1) - (mc.func_175598_ae()).field_78730_l, (pos.func_177956_o() + 1) - (mc.func_175598_ae()).field_78731_m + height, (pos.func_177952_p() + 1) - (mc.func_175598_ae()).field_78728_n);
/*     */     
/* 225 */     GlStateManager.func_179094_E();
/* 226 */     GlStateManager.func_179147_l();
/* 227 */     GlStateManager.func_179097_i();
/* 228 */     GlStateManager.func_179120_a(770, 771, 0, 1);
/* 229 */     GlStateManager.func_179090_x();
/* 230 */     GlStateManager.func_179132_a(false);
/* 231 */     GL11.glEnable(2848);
/* 232 */     GL11.glHint(3154, 4354);
/* 233 */     RenderGlobal.func_189696_b(bb, color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/* 234 */     GL11.glDisable(2848);
/* 235 */     GlStateManager.func_179132_a(true);
/* 236 */     GlStateManager.func_179126_j();
/* 237 */     GlStateManager.func_179098_w();
/* 238 */     GlStateManager.func_179084_k();
/* 239 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawBlockOutline(BlockPos pos, Color color, float linewidth, boolean air, double height, boolean gradient, boolean invert, int alpha) {
/* 247 */     if (gradient) {
/* 248 */       Color endColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
/* 249 */       drawGradientBlockOutline(pos, invert ? endColor : color, invert ? color : endColor, linewidth, height);
/*     */       return;
/*     */     } 
/* 252 */     IBlockState iblockstate = mc.field_71441_e.func_180495_p(pos);
/* 253 */     if ((air || iblockstate.func_185904_a() != Material.field_151579_a) && mc.field_71441_e.func_175723_af().func_177746_a(pos)) {
/* 254 */       AxisAlignedBB blockAxis = new AxisAlignedBB(pos.func_177958_n() - (mc.func_175598_ae()).field_78730_l, pos.func_177956_o() - (mc.func_175598_ae()).field_78731_m, pos.func_177952_p() - (mc.func_175598_ae()).field_78728_n, (pos.func_177958_n() + 1) - (mc.func_175598_ae()).field_78730_l, (pos.func_177956_o() + 1) - (mc.func_175598_ae()).field_78731_m + height, (pos.func_177952_p() + 1) - (mc.func_175598_ae()).field_78728_n);
/* 255 */       drawBlockOutline(blockAxis.func_186662_g(0.0020000000949949026D), color, linewidth);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void drawGradientBlockOutline(BlockPos pos, Color startColor, Color endColor, float linewidth, double height) {
/* 260 */     IBlockState iblockstate = mc.field_71441_e.func_180495_p(pos);
/* 261 */     Vec3d interp = EntityUtil.interpolateEntity((Entity)mc.field_71439_g, mc.func_184121_ak());
/* 262 */     drawGradientBlockOutline(iblockstate.func_185918_c((World)mc.field_71441_e, pos).func_186662_g(0.0020000000949949026D).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c).func_72321_a(0.0D, height, 0.0D), startColor, endColor, linewidth);
/*     */   }
/*     */   
/*     */   public static void drawProperGradientBlockOutline(AxisAlignedBB bb, Color startColor, Color midColor, Color endColor, float linewidth) {
/* 266 */     float red = endColor.getRed() / 255.0F;
/* 267 */     float green = endColor.getGreen() / 255.0F;
/* 268 */     float blue = endColor.getBlue() / 255.0F;
/* 269 */     float alpha = endColor.getAlpha() / 255.0F;
/* 270 */     float red2 = midColor.getRed() / 255.0F;
/* 271 */     float green2 = midColor.getGreen() / 255.0F;
/* 272 */     float blue2 = midColor.getBlue() / 255.0F;
/* 273 */     float alpha2 = midColor.getAlpha() / 255.0F;
/* 274 */     float red3 = startColor.getRed() / 255.0F;
/* 275 */     float green3 = startColor.getGreen() / 255.0F;
/* 276 */     float blue3 = startColor.getBlue() / 255.0F;
/* 277 */     float alpha3 = startColor.getAlpha() / 255.0F;
/* 278 */     double dif = (bb.field_72337_e - bb.field_72338_b) / 2.0D;
/* 279 */     GlStateManager.func_179094_E();
/* 280 */     GlStateManager.func_179147_l();
/* 281 */     GlStateManager.func_179097_i();
/* 282 */     GlStateManager.func_179120_a(770, 771, 0, 1);
/* 283 */     GlStateManager.func_179090_x();
/* 284 */     GlStateManager.func_179132_a(false);
/* 285 */     GL11.glEnable(2848);
/* 286 */     GL11.glHint(3154, 4354);
/* 287 */     GL11.glLineWidth(linewidth);
/* 288 */     GL11.glBegin(1);
/* 289 */     GL11.glColor4d(red, green, blue, alpha);
/* 290 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
/* 291 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
/* 292 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
/* 293 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
/* 294 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
/* 295 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
/* 296 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
/* 297 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
/* 298 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c);
/* 299 */     GL11.glColor4d(red2, green2, blue2, alpha2);
/* 300 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b + dif, bb.field_72339_c);
/* 301 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b + dif, bb.field_72339_c);
/* 302 */     GL11.glColor4f(red3, green3, blue3, alpha3);
/* 303 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
/* 304 */     GL11.glColor4d(red, green, blue, alpha);
/* 305 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f);
/* 306 */     GL11.glColor4d(red2, green2, blue2, alpha2);
/* 307 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b + dif, bb.field_72334_f);
/* 308 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72338_b + dif, bb.field_72334_f);
/* 309 */     GL11.glColor4d(red3, green3, blue3, alpha3);
/* 310 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
/* 311 */     GL11.glColor4d(red, green, blue, alpha);
/* 312 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f);
/* 313 */     GL11.glColor4d(red2, green2, blue2, alpha2);
/* 314 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b + dif, bb.field_72334_f);
/* 315 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b + dif, bb.field_72334_f);
/* 316 */     GL11.glColor4d(red3, green3, blue3, alpha3);
/* 317 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
/* 318 */     GL11.glColor4d(red, green, blue, alpha);
/* 319 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c);
/* 320 */     GL11.glColor4d(red2, green2, blue2, alpha2);
/* 321 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b + dif, bb.field_72339_c);
/* 322 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72338_b + dif, bb.field_72339_c);
/* 323 */     GL11.glColor4d(red3, green3, blue3, alpha3);
/* 324 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
/* 325 */     GL11.glColor4d(red3, green3, blue3, alpha3);
/* 326 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
/* 327 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
/* 328 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c);
/* 329 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
/* 330 */     GL11.glVertex3d(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f);
/* 331 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
/* 332 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f);
/* 333 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
/* 334 */     GL11.glVertex3d(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c);
/* 335 */     GL11.glEnd();
/* 336 */     GL11.glDisable(2848);
/* 337 */     GlStateManager.func_179132_a(true);
/* 338 */     GlStateManager.func_179126_j();
/* 339 */     GlStateManager.func_179098_w();
/* 340 */     GlStateManager.func_179084_k();
/* 341 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public static void drawGradientBlockOutline(AxisAlignedBB bb, Color startColor, Color endColor, float linewidth) {
/* 345 */     float red = startColor.getRed() / 255.0F;
/* 346 */     float green = startColor.getGreen() / 255.0F;
/* 347 */     float blue = startColor.getBlue() / 255.0F;
/* 348 */     float alpha = startColor.getAlpha() / 255.0F;
/* 349 */     float red2 = endColor.getRed() / 255.0F;
/* 350 */     float green2 = endColor.getGreen() / 255.0F;
/* 351 */     float blue2 = endColor.getBlue() / 255.0F;
/* 352 */     float alpha2 = endColor.getAlpha() / 255.0F;
/* 353 */     GlStateManager.func_179094_E();
/* 354 */     GlStateManager.func_179147_l();
/* 355 */     GlStateManager.func_179097_i();
/* 356 */     GlStateManager.func_179120_a(770, 771, 0, 1);
/* 357 */     GlStateManager.func_179090_x();
/* 358 */     GlStateManager.func_179132_a(false);
/* 359 */     GL11.glEnable(2848);
/* 360 */     GL11.glHint(3154, 4354);
/* 361 */     GL11.glLineWidth(linewidth);
/* 362 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 363 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 364 */     bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
/* 365 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 366 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 367 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 368 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 369 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 370 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 371 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 372 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 373 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 374 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 375 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 376 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 377 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 378 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 379 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 380 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 381 */     tessellator.func_78381_a();
/* 382 */     GL11.glDisable(2848);
/* 383 */     GlStateManager.func_179132_a(true);
/* 384 */     GlStateManager.func_179126_j();
/* 385 */     GlStateManager.func_179098_w();
/* 386 */     GlStateManager.func_179084_k();
/* 387 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public static void drawBlockOutline(AxisAlignedBB bb, Color color, float linewidth) {
/* 391 */     float red = color.getRed() / 255.0F;
/* 392 */     float green = color.getGreen() / 255.0F;
/* 393 */     float blue = color.getBlue() / 255.0F;
/* 394 */     float alpha = color.getAlpha() / 255.0F;
/* 395 */     GlStateManager.func_179094_E();
/* 396 */     GlStateManager.func_179147_l();
/* 397 */     GlStateManager.func_179097_i();
/* 398 */     GlStateManager.func_179120_a(770, 771, 0, 1);
/* 399 */     GlStateManager.func_179090_x();
/* 400 */     GlStateManager.func_179132_a(false);
/* 401 */     GL11.glEnable(2848);
/* 402 */     GL11.glHint(3154, 4354);
/* 403 */     GL11.glLineWidth(linewidth);
/* 404 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 405 */     BufferBuilder bufferbuilder = tessellator.func_178180_c();
/* 406 */     bufferbuilder.func_181668_a(3, DefaultVertexFormats.field_181706_f);
/* 407 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 408 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 409 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 410 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 411 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 412 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 413 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 414 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 415 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 416 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 417 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 418 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72334_f).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 419 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 420 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72338_b, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 421 */     bufferbuilder.func_181662_b(bb.field_72336_d, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 422 */     bufferbuilder.func_181662_b(bb.field_72340_a, bb.field_72337_e, bb.field_72339_c).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 423 */     tessellator.func_78381_a();
/* 424 */     GL11.glDisable(2848);
/* 425 */     GlStateManager.func_179132_a(true);
/* 426 */     GlStateManager.func_179126_j();
/* 427 */     GlStateManager.func_179098_w();
/* 428 */     GlStateManager.func_179084_k();
/* 429 */     GlStateManager.func_179121_F();
/*     */   }
/*     */   
/*     */   public static void drawOpenGradientBox(BlockPos pos, Color startColor, Color endColor, double height) {
/* 433 */     for (EnumFacing face : EnumFacing.values()) {
/* 434 */       if (face != EnumFacing.UP) {
/* 435 */         drawGradientPlane(pos, face, startColor, endColor, height);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawGradientPlane(BlockPos pos, EnumFacing face, Color startColor, Color endColor, double height) {
/* 442 */     Tessellator tessellator = Tessellator.func_178181_a();
/* 443 */     BufferBuilder builder = tessellator.func_178180_c();
/* 444 */     IBlockState iblockstate = mc.field_71441_e.func_180495_p(pos);
/* 445 */     Vec3d interp = EntityUtil.interpolateEntity((Entity)mc.field_71439_g, mc.func_184121_ak());
/* 446 */     AxisAlignedBB bb = iblockstate.func_185918_c((World)mc.field_71441_e, pos).func_186662_g(0.0020000000949949026D).func_72317_d(-interp.field_72450_a, -interp.field_72448_b, -interp.field_72449_c).func_72321_a(0.0D, height, 0.0D);
/* 447 */     float red = startColor.getRed() / 255.0F;
/* 448 */     float green = startColor.getGreen() / 255.0F;
/* 449 */     float blue = startColor.getBlue() / 255.0F;
/* 450 */     float alpha = startColor.getAlpha() / 255.0F;
/* 451 */     float red2 = endColor.getRed() / 255.0F;
/* 452 */     float green2 = endColor.getGreen() / 255.0F;
/* 453 */     float blue2 = endColor.getBlue() / 255.0F;
/* 454 */     float alpha2 = endColor.getAlpha() / 255.0F;
/* 455 */     double x1 = 0.0D;
/* 456 */     double y1 = 0.0D;
/* 457 */     double z1 = 0.0D;
/* 458 */     double x2 = 0.0D;
/* 459 */     double y2 = 0.0D;
/* 460 */     double z2 = 0.0D;
/* 461 */     if (face == EnumFacing.DOWN) {
/* 462 */       x1 = bb.field_72340_a;
/* 463 */       x2 = bb.field_72336_d;
/* 464 */       y1 = bb.field_72338_b;
/* 465 */       y2 = bb.field_72338_b;
/* 466 */       z1 = bb.field_72339_c;
/* 467 */       z2 = bb.field_72334_f;
/*     */     }
/* 469 */     else if (face == EnumFacing.UP) {
/* 470 */       x1 = bb.field_72340_a;
/* 471 */       x2 = bb.field_72336_d;
/* 472 */       y1 = bb.field_72337_e;
/* 473 */       y2 = bb.field_72337_e;
/* 474 */       z1 = bb.field_72339_c;
/* 475 */       z2 = bb.field_72334_f;
/*     */     }
/* 477 */     else if (face == EnumFacing.EAST) {
/* 478 */       x1 = bb.field_72336_d;
/* 479 */       x2 = bb.field_72336_d;
/* 480 */       y1 = bb.field_72338_b;
/* 481 */       y2 = bb.field_72337_e;
/* 482 */       z1 = bb.field_72339_c;
/* 483 */       z2 = bb.field_72334_f;
/*     */     }
/* 485 */     else if (face == EnumFacing.WEST) {
/* 486 */       x1 = bb.field_72340_a;
/* 487 */       x2 = bb.field_72340_a;
/* 488 */       y1 = bb.field_72338_b;
/* 489 */       y2 = bb.field_72337_e;
/* 490 */       z1 = bb.field_72339_c;
/* 491 */       z2 = bb.field_72334_f;
/*     */     }
/* 493 */     else if (face == EnumFacing.SOUTH) {
/* 494 */       x1 = bb.field_72340_a;
/* 495 */       x2 = bb.field_72336_d;
/* 496 */       y1 = bb.field_72338_b;
/* 497 */       y2 = bb.field_72337_e;
/* 498 */       z1 = bb.field_72334_f;
/* 499 */       z2 = bb.field_72334_f;
/*     */     }
/* 501 */     else if (face == EnumFacing.NORTH) {
/* 502 */       x1 = bb.field_72340_a;
/* 503 */       x2 = bb.field_72336_d;
/* 504 */       y1 = bb.field_72338_b;
/* 505 */       y2 = bb.field_72337_e;
/* 506 */       z1 = bb.field_72339_c;
/* 507 */       z2 = bb.field_72339_c;
/*     */     } 
/* 509 */     GlStateManager.func_179094_E();
/* 510 */     GlStateManager.func_179097_i();
/* 511 */     GlStateManager.func_179090_x();
/* 512 */     GlStateManager.func_179147_l();
/* 513 */     GlStateManager.func_179118_c();
/* 514 */     GlStateManager.func_179132_a(false);
/* 515 */     builder.func_181668_a(5, DefaultVertexFormats.field_181706_f);
/* 516 */     if (face == EnumFacing.EAST || face == EnumFacing.WEST || face == EnumFacing.NORTH || face == EnumFacing.SOUTH) {
/* 517 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 518 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 519 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 520 */       builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 521 */       builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 522 */       builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 523 */       builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 524 */       builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 525 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 526 */       builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 527 */       builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 528 */       builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 529 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 530 */       builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 531 */       builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 532 */       builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 533 */       builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 534 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 535 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 536 */       builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 537 */       builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 538 */       builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 539 */       builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 540 */       builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 541 */       builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 542 */       builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 543 */       builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 544 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 545 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 546 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/*     */     }
/* 548 */     else if (face == EnumFacing.UP) {
/* 549 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 550 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 551 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 552 */       builder.func_181662_b(x1, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 553 */       builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 554 */       builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 555 */       builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 556 */       builder.func_181662_b(x1, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 557 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 558 */       builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 559 */       builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 560 */       builder.func_181662_b(x2, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 561 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 562 */       builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 563 */       builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 564 */       builder.func_181662_b(x2, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 565 */       builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 566 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 567 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 568 */       builder.func_181662_b(x2, y1, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 569 */       builder.func_181662_b(x1, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 570 */       builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 571 */       builder.func_181662_b(x2, y1, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 572 */       builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 573 */       builder.func_181662_b(x1, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 574 */       builder.func_181662_b(x1, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 575 */       builder.func_181662_b(x2, y2, z1).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 576 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 577 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/* 578 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red2, green2, blue2, alpha2).func_181675_d();
/*     */     }
/* 580 */     else if (face == EnumFacing.DOWN) {
/* 581 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 582 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 583 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 584 */       builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 585 */       builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 586 */       builder.func_181662_b(x1, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 587 */       builder.func_181662_b(x1, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 588 */       builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 589 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 590 */       builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 591 */       builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 592 */       builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 593 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 594 */       builder.func_181662_b(x2, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 595 */       builder.func_181662_b(x2, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 596 */       builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 597 */       builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 598 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 599 */       builder.func_181662_b(x1, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 600 */       builder.func_181662_b(x2, y1, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 601 */       builder.func_181662_b(x1, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 602 */       builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 603 */       builder.func_181662_b(x2, y1, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 604 */       builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 605 */       builder.func_181662_b(x1, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 606 */       builder.func_181662_b(x1, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 607 */       builder.func_181662_b(x2, y2, z1).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 608 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 609 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/* 610 */       builder.func_181662_b(x2, y2, z2).func_181666_a(red, green, blue, alpha).func_181675_d();
/*     */     } 
/* 612 */     tessellator.func_78381_a();
/* 613 */     GlStateManager.func_179132_a(true);
/* 614 */     GlStateManager.func_179084_k();
/* 615 */     GlStateManager.func_179141_d();
/* 616 */     GlStateManager.func_179098_w();
/* 617 */     GlStateManager.func_179126_j();
/* 618 */     GlStateManager.func_179121_F();
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\RenderUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */