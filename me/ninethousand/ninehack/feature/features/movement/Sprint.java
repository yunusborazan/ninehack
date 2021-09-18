/*    */ package me.ninethousand.ninehack.feature.features.movement;
/*    */ 
/*    */ import me.ninethousand.ninehack.NineHack;
/*    */ import me.ninethousand.ninehack.feature.Category;
/*    */ import me.ninethousand.ninehack.feature.Feature;
/*    */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*    */ import me.ninethousand.ninehack.feature.setting.Setting;
/*    */ 
/*    */ @NineHackFeature(name = "Sprint", description = "Be fast af", category = Category.Movement)
/*    */ public class Sprint
/*    */   extends Feature
/*    */ {
/*    */   public static Feature INSTANCE;
/* 14 */   public static final Setting<SprintMode> mode = new Setting("Mode", SprintMode.Rage);
/*    */ 
/*    */ 
/*    */   
/*    */   public Sprint() {
/* 19 */     addSettings(new Setting[] { mode });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 24 */     if (nullCheck()) {
/*    */       return;
/*    */     }
/* 27 */     if (!mc.field_71439_g.func_70051_ag()) {
/* 28 */       mc.field_71439_g.func_70031_b((mode.getValue() == SprintMode.Rage || mc.field_71474_y.field_74351_w.func_151470_d()));
/*    */     }
/* 30 */     for (Setting<?> setting : (Iterable<Setting<?>>)getSettings())
/* 31 */       NineHack.log(setting.getName() + " setting name."); 
/*    */   }
/*    */   
/*    */   public enum SprintMode
/*    */   {
/* 36 */     Rage,
/* 37 */     Normal;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\movement\Sprint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */