/*    */ package me.ninethousand.ninehack.feature.features.movement;
/*    */ 
/*    */ import me.ninethousand.ninehack.feature.Category;
/*    */ import me.ninethousand.ninehack.feature.Feature;
/*    */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*    */ import me.ninethousand.ninehack.feature.setting.NumberSetting;
/*    */ import me.ninethousand.ninehack.feature.setting.Setting;
/*    */ import me.ninethousand.ninehack.mixin.accessors.game.IEntity;
/*    */ import me.ninethousand.ninehack.util.WorldUtil;
/*    */ 
/*    */ @NineHackFeature(name = "AntiWeb", description = "Fall through webs fast", category = Category.Movement)
/*    */ public class AntiWeb
/*    */   extends Feature {
/*    */   public static Feature INSTANCE;
/* 15 */   public static final Setting<WebMode> mode = new Setting("Mode", WebMode.Timer);
/* 16 */   public static final NumberSetting<Float> factor = new NumberSetting("Size", Float.valueOf(0.3F), Float.valueOf(1.0F), Float.valueOf(5.0F), 0);
/*    */   
/*    */   public AntiWeb() {
/* 19 */     addSettings(new Setting[] { mode, (Setting)factor });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 24 */     if (((IEntity)mc.field_71439_g).getIsInWeb())
/* 25 */       switch ((WebMode)mode.getValue()) {
/*    */         case Timer:
/* 27 */           if (!mc.field_71439_g.field_70122_E && mc.field_71439_g.field_70181_x <= 0.0D) {
/* 28 */             WorldUtil.setTickLength(((Float)factor.getValue()).floatValue()); break;
/*    */           } 
/* 30 */           WorldUtil.setTickLength(50.0F);
/*    */           break;
/*    */       }  
/*    */   }
/*    */   
/*    */   public enum WebMode
/*    */   {
/* 37 */     Vanilla,
/* 38 */     Normal,
/* 39 */     Timer;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\movement\AntiWeb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */