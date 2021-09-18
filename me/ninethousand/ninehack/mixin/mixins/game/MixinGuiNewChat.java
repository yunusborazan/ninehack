/*    */ package me.ninethousand.ninehack.mixin.mixins.game;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.util.List;
/*    */ import me.ninethousand.ninehack.NineHack;
/*    */ import me.ninethousand.ninehack.feature.features.client.Chat;
/*    */ import me.ninethousand.ninehack.feature.features.client.ClientColor;
/*    */ import net.minecraft.client.gui.ChatLine;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiNewChat;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Redirect;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({GuiNewChat.class})
/*    */ public class MixinGuiNewChat
/*    */   extends Gui
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   public List<ChatLine> field_146253_i;
/*    */   private ChatLine chatLine;
/*    */   
/*    */   @Redirect(method = {"drawChat"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
/*    */   private void drawRectHook(int left, int top, int right, int bottom, int color) {
/* 31 */     Gui.func_73734_a(left, top, right, bottom, (Chat.INSTANCE.isEnabled() && ((Boolean)Chat.clear.getValue()).booleanValue()) ? 0 : color);
/*    */   }
/*    */   
/*    */   @Redirect(method = {"drawChat"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I"))
/*    */   private int drawStringWithShadow(FontRenderer fontRenderer, String text, float x, float y, int color) {
/* 36 */     if (((Boolean)Chat.customFontChat.getValue()).booleanValue()) {
/* 37 */       if (text.contains("§+")) {
/* 38 */         float colorSpeed = (101 - ((Integer)ClientColor.speed.getValue()).intValue());
/* 39 */         NineHack.TEXT_MANAGER.drawRainbowString(text, x, y, Color.HSBtoRGB(ClientColor.hue, 1.0F, 1.0F), 100.0F, true);
/*    */       } else {
/* 41 */         NineHack.TEXT_MANAGER.drawStringWithShadow(text, x, y, color);
/*    */       }
/*    */     
/* 44 */     } else if (text.contains("§+")) {
/* 45 */       float colorSpeed = (101 - ((Integer)ClientColor.speed.getValue()).intValue());
/* 46 */       NineHack.TEXT_MANAGER.drawRainbowStringCustomFont(text, x, y, Color.HSBtoRGB(ClientColor.hue, 1.0F, 1.0F), 100.0F, true, false);
/*    */     } else {
/* 48 */       NineHack.Globals.mc.field_71466_p.func_175063_a(text, x, y, color);
/*    */     } 
/*    */     
/* 51 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\mixin\mixins\game\MixinGuiNewChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */