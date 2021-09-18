/*     */ package me.ninethousand.ninehack.mixin.mixins.game;
/*     */ import java.awt.Color;
/*     */ import javax.annotation.Nullable;
/*     */ import me.ninethousand.ninehack.feature.features.visual.WireESP;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelEnderCrystal;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderDragon;
/*     */ import net.minecraft.client.renderer.entity.RenderEnderCrystal;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityEnderCrystal;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Overwrite;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ 
/*     */ @Mixin({RenderEnderCrystal.class})
/*     */ public class MixinRenderEnderCrystal extends Render<EntityEnderCrystal> {
/*     */   @Shadow
/*  25 */   private static final ResourceLocation field_110787_a = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
/*     */   @Shadow
/*  27 */   private final ModelBase field_76995_b = (ModelBase)new ModelEnderCrystal(0.0F, true);
/*     */   
/*     */   @Shadow
/*  30 */   private final ModelBase field_188316_g = (ModelBase)new ModelEnderCrystal(0.0F, false);
/*     */ 
/*     */   
/*     */   protected MixinRenderEnderCrystal(RenderManager renderManager) {
/*  34 */     super(renderManager);
/*     */   }
/*     */   
/*     */   @Overwrite
/*     */   public void func_76986_a(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  39 */     float f = entity.field_70261_a + partialTicks;
/*  40 */     GlStateManager.func_179094_E();
/*  41 */     GlStateManager.func_179109_b((float)x, (float)y, (float)z);
/*  42 */     func_110776_a(field_110787_a);
/*  43 */     float f1 = MathHelper.func_76126_a(f * 0.2F) / 2.0F + 0.5F;
/*  44 */     f1 += f1 * f1;
/*  45 */     if (this.field_188301_f) {
/*  46 */       GlStateManager.func_179142_g();
/*  47 */       GlStateManager.func_187431_e(func_188298_c((Entity)entity));
/*     */     } 
/*  49 */     if (WireESP.INSTANCE.isEnabled() && ((Boolean)WireESP.crystals.getValue()).booleanValue()) {
/*  50 */       float red = ((Color)WireESP.cColor.getValue()).getRed() / 255.0F;
/*  51 */       float green = ((Color)WireESP.cColor.getValue()).getGreen() / 255.0F;
/*  52 */       float blue = ((Color)WireESP.cColor.getValue()).getBlue() / 255.0F;
/*  53 */       float alpha = ((Color)WireESP.cColor.getValue()).getAlpha() / 255.0F;
/*  54 */       if (WireESP.cMode.getValue() == WireESP.RenderMode.Wire && ((Boolean)WireESP.cModel.getValue()).booleanValue())
/*  55 */         this.field_188316_g.func_78088_a((Entity)entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F); 
/*  56 */       GlStateManager.func_179094_E();
/*  57 */       GL11.glPushAttrib(1048575);
/*  58 */       if (WireESP.cMode.getValue() == WireESP.RenderMode.Wire)
/*  59 */         GL11.glPolygonMode(1032, 6913); 
/*  60 */       GL11.glDisable(3553);
/*  61 */       GL11.glDisable(2896);
/*  62 */       if (WireESP.cMode.getValue() == WireESP.RenderMode.Wire)
/*  63 */         GL11.glEnable(2848); 
/*  64 */       GL11.glEnable(3042);
/*  65 */       GL11.glBlendFunc(770, 771);
/*  66 */       GL11.glDisable(2929);
/*  67 */       GL11.glDepthMask(false);
/*  68 */       GL11.glColor4f(red, green, blue, alpha);
/*  69 */       if (WireESP.cMode.getValue() == WireESP.RenderMode.Wire)
/*  70 */         GL11.glLineWidth(((Float)WireESP.cWidth.getValue()).floatValue()); 
/*  71 */       this.field_188316_g.func_78088_a((Entity)entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
/*  72 */       GL11.glDisable(2896);
/*  73 */       GL11.glEnable(2929);
/*  74 */       GL11.glDepthMask(true);
/*  75 */       GL11.glColor4f(red, green, blue, alpha);
/*  76 */       if (WireESP.cMode.getValue() == WireESP.RenderMode.Wire)
/*  77 */         GL11.glLineWidth(((Float)WireESP.cWidth.getValue()).floatValue()); 
/*  78 */       this.field_188316_g.func_78088_a((Entity)entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
/*  79 */       GlStateManager.func_179126_j();
/*  80 */       GlStateManager.func_179099_b();
/*  81 */       GlStateManager.func_179121_F();
/*     */     } else {
/*  83 */       this.field_188316_g.func_78088_a((Entity)entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
/*     */     } 
/*  85 */     if (this.field_188301_f) {
/*  86 */       GlStateManager.func_187417_n();
/*  87 */       GlStateManager.func_179119_h();
/*     */     } 
/*  89 */     GlStateManager.func_179121_F();
/*  90 */     BlockPos blockpos = entity.func_184518_j();
/*  91 */     if (blockpos != null) {
/*  92 */       func_110776_a(RenderDragon.field_110843_g);
/*  93 */       float f2 = blockpos.func_177958_n() + 0.5F;
/*  94 */       float f3 = blockpos.func_177956_o() + 0.5F;
/*  95 */       float f4 = blockpos.func_177952_p() + 0.5F;
/*  96 */       double d0 = f2 - entity.field_70165_t;
/*  97 */       double d1 = f3 - entity.field_70163_u;
/*  98 */       double d2 = f4 - entity.field_70161_v;
/*  99 */       RenderDragon.func_188325_a(x + d0, y - 0.3D + (f1 * 0.4F) + d1, z + d2, partialTicks, f2, f3, f4, entity.field_70261_a, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
/*     */     } 
/* 101 */     super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getEntityTexture(EntityEnderCrystal entityEnderCrystal) {
/* 106 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\mixin\mixins\game\MixinRenderEnderCrystal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */