/*    */ package me.ninethousand.ninehack.util;
/*    */ 
/*    */ import me.ninethousand.ninehack.NineHack;
/*    */ import me.ninethousand.ninehack.mixin.accessors.game.IMinecraft;
/*    */ import me.ninethousand.ninehack.mixin.accessors.util.ITimer;
/*    */ 
/*    */ public class WorldUtil
/*    */   implements NineHack.Globals {
/*    */   public static void setTickLength(float tickLength) {
/* 10 */     ((ITimer)((IMinecraft)mc).getTimer()).setTickLength(tickLength);
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\WorldUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */