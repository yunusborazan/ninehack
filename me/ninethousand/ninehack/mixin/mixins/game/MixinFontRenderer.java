/*    */ package me.ninethousand.ninehack.mixin.mixins.game;
/*    */ 
/*    */ import me.ninethousand.ninehack.NineHack;
/*    */ import me.ninethousand.ninehack.feature.features.client.CustomFont;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ @Mixin({FontRenderer.class})
/*    */ public final class MixinFontRenderer {
/* 14 */   private final Minecraft mc = Minecraft.func_71410_x();
/*    */   
/*    */   @Inject(method = {"drawString(Ljava/lang/String;FFIZ)I"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void renderStringHook(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Integer> info) {
/* 18 */     if (this.mc.field_71441_e != null && CustomFont.INSTANCE.isEnabled() && ((Boolean)CustomFont.override.getValue()).booleanValue() && NineHack.TEXT_MANAGER != null) {
/* 19 */       float result = NineHack.TEXT_MANAGER.drawString(text, x, y, color, dropShadow);
/* 20 */       info.setReturnValue(Integer.valueOf((int)result));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\mixin\mixins\game\MixinFontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */