/*    */ package me.ninethousand.ninehack.mixin.mixins.game;
/*    */ 
/*    */ import me.ninethousand.ninehack.mixin.accessors.game.IRenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ @Mixin({RenderManager.class})
/*    */ public class MixinRenderManager
/*    */   implements IRenderManager
/*    */ {
/*    */   @Shadow
/*    */   private double field_78725_b;
/*    */   @Shadow
/*    */   private double field_78726_c;
/*    */   @Shadow
/*    */   private double field_78723_d;
/*    */   
/*    */   public double getRenderPosX() {
/* 21 */     return this.field_78725_b;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getRenderPosY() {
/* 26 */     return this.field_78726_c;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getRenderPosZ() {
/* 31 */     return this.field_78723_d;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\mixin\mixins\game\MixinRenderManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */