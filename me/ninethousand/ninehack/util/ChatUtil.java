/*    */ package me.ninethousand.ninehack.util;
/*    */ 
/*    */ import com.mojang.realmsclient.gui.ChatFormatting;
/*    */ import me.ninethousand.ninehack.NineHack;
/*    */ import me.ninethousand.ninehack.feature.features.client.Chat;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ import net.minecraft.util.text.Style;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ import net.minecraft.util.text.event.HoverEvent;
/*    */ 
/*    */ public class ChatUtil
/*    */   implements NineHack.Globals {
/*    */   public static void sendClientMessageSimple(String message) {
/* 14 */     mc.field_71439_g.func_145747_a((ITextComponent)new TextComponentString(getPrefix() + " " + ChatFormatting.WHITE + message));
/*    */   }
/*    */   
/*    */   public static void sendClientMessage(String message) {
/* 18 */     if (mc.field_71439_g != null) {
/* 19 */       ITextComponent itc = (new TextComponentString(getPrefix() + " " + message)).func_150255_a((new Style()).func_150209_a(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (ITextComponent)new TextComponentString("ninehack owns all!"))));
/* 20 */       mc.field_71456_v.func_146158_b().func_146234_a(itc, 5936);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void sendChatMessage(String message) {
/* 25 */     mc.field_71439_g.func_71165_d(message);
/*    */   }
/*    */   
/*    */   public static String getPrefix() {
/* 29 */     String prefix = (String)Chat.prefixString.getValue();
/*    */     
/* 31 */     switch ((Chat.PrefixBracket)Chat.prefixBracket.getValue()) {
/*    */       case Arrow:
/* 33 */         return TextUtil.coloredString("<" + prefix + ">", (TextUtil.Color)Chat.prefixColor.getValue());
/*    */       case Double:
/* 35 */         return TextUtil.coloredString(prefix + " >>", (TextUtil.Color)Chat.prefixColor.getValue());
/*    */       case Square:
/* 37 */         return TextUtil.coloredString("[" + prefix + "]", (TextUtil.Color)Chat.prefixColor.getValue());
/*    */       case Round:
/* 39 */         return TextUtil.coloredString("(" + prefix + ")", (TextUtil.Color)Chat.prefixColor.getValue());
/*    */       case Curly:
/* 41 */         return TextUtil.coloredString("{" + prefix + "}", (TextUtil.Color)Chat.prefixColor.getValue());
/*    */     } 
/* 43 */     return TextUtil.coloredString(prefix, (TextUtil.Color)Chat.prefixColor.getValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\ChatUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */