/*     */ package me.ninethousand.ninehack.mixin.mixins.game;
/*     */ import java.awt.Color;
/*     */ import me.ninethousand.ninehack.feature.features.visual.WireESP;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraftforge.client.event.RenderLivingEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Overwrite;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ 
/*     */ @Mixin({RenderLivingBase.class})
/*     */ public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T> {
/*     */   @Shadow
/*  26 */   private static final Logger field_147923_a = LogManager.getLogger();
/*     */   
/*     */   @Shadow
/*     */   protected ModelBase field_77045_g;
/*     */   
/*     */   @Shadow
/*     */   protected boolean field_188323_j;
/*     */   
/*     */   float red;
/*     */   float green;
/*     */   float blue;
/*     */   
/*     */   protected MixinRenderLivingBase(RenderManager renderManager) {
/*  39 */     super(renderManager);
/*  40 */     this.red = 0.0F;
/*  41 */     this.green = 0.0F;
/*  42 */     this.blue = 0.0F;
/*     */   }
/*     */   
/*     */   @Overwrite
/*     */   public void func_76986_a(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  47 */     if (!MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Pre((EntityLivingBase)entity, RenderLivingBase.class.cast(this), partialTicks, x, y, z))) {
/*  48 */       GlStateManager.func_179094_E();
/*  49 */       GlStateManager.func_179129_p();
/*  50 */       this.field_77045_g.field_78095_p = func_77040_d(entity, partialTicks);
/*  51 */       boolean shouldSit = (entity.func_184218_aH() && entity.func_184187_bx() != null && entity.func_184187_bx().shouldRiderSit());
/*  52 */       this.field_77045_g.field_78093_q = shouldSit;
/*  53 */       this.field_77045_g.field_78091_s = entity.func_70631_g_();
/*     */       try {
/*  55 */         float f = func_77034_a(((EntityLivingBase)entity).field_70760_ar, ((EntityLivingBase)entity).field_70761_aq, partialTicks);
/*  56 */         float f1 = func_77034_a(((EntityLivingBase)entity).field_70758_at, ((EntityLivingBase)entity).field_70759_as, partialTicks);
/*  57 */         float f2 = f1 - f;
/*  58 */         if (shouldSit && entity.func_184187_bx() instanceof EntityLivingBase) {
/*  59 */           EntityLivingBase entitylivingbase = (EntityLivingBase)entity.func_184187_bx();
/*  60 */           f = func_77034_a(entitylivingbase.field_70760_ar, entitylivingbase.field_70761_aq, partialTicks);
/*  61 */           f2 = f1 - f;
/*  62 */           float f3 = MathHelper.func_76142_g(f2);
/*  63 */           if (f3 < -85.0F)
/*  64 */             f3 = -85.0F; 
/*  65 */           if (f3 >= 85.0F)
/*  66 */             f3 = 85.0F; 
/*  67 */           f = f1 - f3;
/*  68 */           if (f3 * f3 > 2500.0F)
/*  69 */             f += f3 * 0.2F; 
/*  70 */           f2 = f1 - f;
/*     */         } 
/*  72 */         float f7 = ((EntityLivingBase)entity).field_70127_C + (((EntityLivingBase)entity).field_70125_A - ((EntityLivingBase)entity).field_70127_C) * partialTicks;
/*  73 */         func_77039_a(entity, x, y, z);
/*  74 */         float f8 = func_77044_a(entity, partialTicks);
/*  75 */         func_77043_a(entity, f8, f, partialTicks);
/*  76 */         float f4 = func_188322_c(entity, partialTicks);
/*  77 */         float f5 = 0.0F;
/*  78 */         float f6 = 0.0F;
/*  79 */         if (!entity.func_184218_aH()) {
/*  80 */           f5 = ((EntityLivingBase)entity).field_184618_aE + (((EntityLivingBase)entity).field_70721_aZ - ((EntityLivingBase)entity).field_184618_aE) * partialTicks;
/*  81 */           f6 = ((EntityLivingBase)entity).field_184619_aG - ((EntityLivingBase)entity).field_70721_aZ * (1.0F - partialTicks);
/*  82 */           if (entity.func_70631_g_())
/*  83 */             f6 *= 3.0F; 
/*  84 */           if (f5 > 1.0F)
/*  85 */             f5 = 1.0F; 
/*  86 */           f2 = f1 - f;
/*     */         } 
/*  88 */         GlStateManager.func_179141_d();
/*  89 */         this.field_77045_g.func_78086_a((EntityLivingBase)entity, f6, f5, partialTicks);
/*  90 */         this.field_77045_g.func_78087_a(f6, f5, f8, f2, f7, f4, (Entity)entity);
/*  91 */         if (this.field_188301_f) {
/*  92 */           boolean flag1 = func_177088_c(entity);
/*  93 */           GlStateManager.func_179142_g();
/*  94 */           GlStateManager.func_187431_e(func_188298_c((Entity)entity));
/*  95 */           if (!this.field_188323_j)
/*  96 */             func_77036_a(entity, f6, f5, f8, f2, f7, f4); 
/*  97 */           if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).func_175149_v())
/*  98 */             func_177093_a(entity, f6, f5, partialTicks, f8, f2, f7, f4); 
/*  99 */           GlStateManager.func_187417_n();
/* 100 */           GlStateManager.func_179119_h();
/* 101 */           if (flag1)
/* 102 */             func_180565_e(); 
/*     */         } else {
/* 104 */           if (WireESP.INSTANCE.isEnabled() && ((Boolean)WireESP.players.getValue()).booleanValue() && entity instanceof EntityPlayer && WireESP.pMode.getValue() == WireESP.RenderMode.Solid) {
/* 105 */             this.red = ((Color)WireESP.pColor.getValue()).getRed() / 255.0F;
/* 106 */             this.green = ((Color)WireESP.pColor.getValue()).getGreen() / 255.0F;
/* 107 */             this.blue = ((Color)WireESP.pColor.getValue()).getBlue() / 255.0F;
/* 108 */             float alpha = ((Color)WireESP.pColor.getValue()).getAlpha() / 255.0F;
/* 109 */             GlStateManager.func_179094_E();
/* 110 */             GL11.glPushAttrib(1048575);
/* 111 */             GL11.glDisable(3553);
/* 112 */             GL11.glDisable(2896);
/* 113 */             GL11.glEnable(2848);
/* 114 */             GL11.glEnable(3042);
/* 115 */             GL11.glBlendFunc(770, 771);
/* 116 */             GL11.glDisable(2929);
/* 117 */             GL11.glDepthMask(false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 123 */             GL11.glColor4f(this.red, this.green, this.blue, alpha);
/* 124 */             func_77036_a(entity, f6, f5, f8, f2, f7, f4);
/* 125 */             GL11.glDisable(2896);
/* 126 */             GL11.glEnable(2929);
/* 127 */             GL11.glDepthMask(true);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 133 */             GL11.glColor4f(this.red, this.green, this.blue, alpha);
/* 134 */             func_77036_a(entity, f6, f5, f8, f2, f7, f4);
/* 135 */             GL11.glEnable(2896);
/* 136 */             GlStateManager.func_179099_b();
/* 137 */             GlStateManager.func_179121_F();
/*     */           } 
/* 139 */           boolean flag1 = func_177090_c(entity, partialTicks);
/* 140 */           if (!(entity instanceof EntityPlayer) || (WireESP.INSTANCE.isEnabled() && WireESP.pMode.getValue() == WireESP.RenderMode.Wire && ((Boolean)WireESP.pModel.getValue()).booleanValue()) || !WireESP.INSTANCE.isEnabled())
/* 141 */             func_77036_a(entity, f6, f5, f8, f2, f7, f4); 
/* 142 */           if (flag1)
/* 143 */             func_177091_f(); 
/* 144 */           GlStateManager.func_179132_a(true);
/* 145 */           if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).func_175149_v())
/* 146 */             func_177093_a(entity, f6, f5, partialTicks, f8, f2, f7, f4); 
/* 147 */           if (WireESP.INSTANCE.isEnabled() && ((Boolean)WireESP.players.getValue()).booleanValue() && entity instanceof EntityPlayer && WireESP.pMode.getValue() == WireESP.RenderMode.Wire) {
/* 148 */             GlStateManager.func_179094_E();
/* 149 */             GL11.glPushAttrib(1048575);
/* 150 */             GL11.glPolygonMode(1032, 6913);
/* 151 */             GL11.glDisable(3553);
/* 152 */             GL11.glDisable(2896);
/* 153 */             GL11.glDisable(2929);
/* 154 */             GL11.glEnable(2848);
/* 155 */             GL11.glEnable(3042);
/* 156 */             GL11.glBlendFunc(770, 771);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 162 */             this.red = ((Color)WireESP.pColor.getValue()).getRed() / 255.0F;
/* 163 */             this.green = ((Color)WireESP.pColor.getValue()).getGreen() / 255.0F;
/* 164 */             this.blue = ((Color)WireESP.pColor.getValue()).getBlue() / 255.0F;
/* 165 */             float alpha = ((Color)WireESP.pColor.getValue()).getAlpha() / 255.0F;
/* 166 */             GL11.glColor4f(this.red, this.green, this.blue, alpha);
/* 167 */             GL11.glLineWidth(((Float)WireESP.pWidth.getValue()).floatValue());
/* 168 */             func_77036_a(entity, f6, f5, f8, f2, f7, f4);
/* 169 */             GL11.glEnable(2896);
/* 170 */             GlStateManager.func_179099_b();
/* 171 */             GlStateManager.func_179121_F();
/*     */           } 
/*     */         } 
/* 174 */         GlStateManager.func_179101_C();
/* 175 */       } catch (Exception var20) {
/* 176 */         field_147923_a.error("Couldn't render entity", var20);
/*     */       } 
/* 178 */       GlStateManager.func_179138_g(OpenGlHelper.field_77476_b);
/* 179 */       GlStateManager.func_179098_w();
/* 180 */       GlStateManager.func_179138_g(OpenGlHelper.field_77478_a);
/* 181 */       GlStateManager.func_179089_o();
/* 182 */       GlStateManager.func_179121_F();
/* 183 */       super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
/* 184 */       MinecraftForge.EVENT_BUS.post((Event)new RenderLivingEvent.Post((EntityLivingBase)entity, RenderLivingBase.class.cast(this), partialTicks, x, y, z));
/*     */     } 
/*     */   }
/*     */   
/*     */   @Shadow
/*     */   protected abstract boolean func_193115_c(EntityLivingBase paramEntityLivingBase);
/*     */   
/*     */   @Shadow
/*     */   protected abstract float func_77040_d(T paramT, float paramFloat);
/*     */   
/*     */   @Shadow
/*     */   protected abstract float func_77034_a(float paramFloat1, float paramFloat2, float paramFloat3);
/*     */   
/*     */   @Shadow
/*     */   protected abstract float func_77044_a(T paramT, float paramFloat);
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_77043_a(T paramT, float paramFloat1, float paramFloat2, float paramFloat3);
/*     */   
/*     */   @Shadow
/*     */   public abstract float func_188322_c(T paramT, float paramFloat);
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_180565_e();
/*     */   
/*     */   @Shadow
/*     */   protected abstract boolean func_177088_c(T paramT);
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_77039_a(T paramT, double paramDouble1, double paramDouble2, double paramDouble3);
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_177091_f();
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_77036_a(T paramT, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_177093_a(T paramT, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7);
/*     */   
/*     */   @Shadow
/*     */   protected abstract boolean func_177090_c(T paramT, float paramFloat);
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\mixin\mixins\game\MixinRenderLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */