/*    */ package me.ninethousand.ninehack.feature.features.visual;
/*    */ 
/*    */ import me.ninethousand.ninehack.feature.Category;
/*    */ import me.ninethousand.ninehack.feature.Feature;
/*    */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*    */ import me.ninethousand.ninehack.feature.setting.NumberSetting;
/*    */ import me.ninethousand.ninehack.feature.setting.Setting;
/*    */ 
/*    */ @NineHackFeature(name = "Custom FOV", description = "Change default FOV", category = Category.Visual)
/*    */ public class FOV extends Feature {
/* 11 */   NumberSetting<Integer> fov = new NumberSetting("FOV", Integer.valueOf(30), Integer.valueOf(120), Integer.valueOf(150), 1); public static Feature INSTANCE;
/*    */   
/*    */   public FOV() {
/* 14 */     addSettings(new Setting[] { (Setting)this.fov });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 19 */     mc.field_71474_y.field_74334_X = ((Integer)this.fov.getValue()).intValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\visual\FOV.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */