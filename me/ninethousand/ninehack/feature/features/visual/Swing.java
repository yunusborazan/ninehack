/*    */ package me.ninethousand.ninehack.feature.features.visual;
/*    */ 
/*    */ import me.ninethousand.ninehack.feature.Category;
/*    */ import me.ninethousand.ninehack.feature.Feature;
/*    */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*    */ import me.ninethousand.ninehack.feature.setting.Setting;
/*    */ import net.minecraft.util.EnumHand;
/*    */ 
/*    */ @NineHackFeature(name = "Swing", description = "Swings with hand", category = Category.Visual)
/*    */ public class Swing
/*    */   extends Feature {
/*    */   public static Feature INSTANCE;
/* 13 */   public static Setting<Hand> hand = new Setting("Hand", Hand.OFFHAND);
/*    */   
/*    */   public Swing() {
/* 16 */     addSettings(new Setting[] { hand });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 21 */     if (mc.field_71441_e == null)
/*    */       return; 
/* 23 */     if (((Hand)hand.getValue()).equals(Hand.OFFHAND)) {
/* 24 */       mc.field_71439_g.field_184622_au = EnumHand.OFF_HAND;
/*    */     }
/* 26 */     if (((Hand)hand.getValue()).equals(Hand.MAINHAND))
/* 27 */       mc.field_71439_g.field_184622_au = EnumHand.MAIN_HAND; 
/*    */   }
/*    */   
/*    */   public enum Hand
/*    */   {
/* 32 */     OFFHAND,
/* 33 */     MAINHAND;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\visual\Swing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */