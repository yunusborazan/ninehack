/*    */ package me.ninethousand.ninehack.feature.features.visual;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.ninethousand.ninehack.feature.Category;
/*    */ import me.ninethousand.ninehack.feature.Feature;
/*    */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*    */ import me.ninethousand.ninehack.feature.setting.NumberSetting;
/*    */ import me.ninethousand.ninehack.feature.setting.Setting;
/*    */ import net.minecraftforge.client.event.RenderPlayerEvent;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ @NineHackFeature(name = "Chams", description = "Pretty Chams", category = Category.Visual)
/*    */ public class WireESP
/*    */   extends Feature
/*    */ {
/*    */   public static Feature INSTANCE;
/* 19 */   public static final Setting<Color> pColor = new Setting("Player", new Color(168, 0, 232, 255));
/* 20 */   public static final Setting<Color> cColor = new Setting("Crystal", new Color(168, 0, 232, 255));
/* 21 */   public static final NumberSetting<Float> pWidth = new NumberSetting("Player Width", Float.valueOf(0.1F), Float.valueOf(1.0F), Float.valueOf(3.0F), 1);
/* 22 */   public static final NumberSetting<Float> cWidth = new NumberSetting("Crystal Width", Float.valueOf(0.1F), Float.valueOf(1.0F), Float.valueOf(3.0F), 1);
/* 23 */   public static final Setting<RenderMode> pMode = new Setting("Player Mode", RenderMode.Solid);
/* 24 */   public static final Setting<RenderMode> cMode = new Setting("Crystal Mode", RenderMode.Solid);
/* 25 */   public static final Setting<Boolean> players = new Setting("Players", Boolean.valueOf(true));
/* 26 */   public static final Setting<Boolean> pModel = new Setting("Player Model", Boolean.valueOf(true));
/* 27 */   public static final Setting<Boolean> crystals = new Setting("Crystals", Boolean.valueOf(true));
/* 28 */   public static final Setting<Boolean> cModel = new Setting("Crystal Model", Boolean.valueOf(true));
/*    */   
/*    */   public WireESP() {
/* 31 */     addSettings(new Setting[] { pColor, cColor, (Setting)pWidth, (Setting)cWidth, pMode, cMode, players, pModel, crystals, cModel });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @SubscribeEvent
/*    */   public void onRenderPlayerEvent(RenderPlayerEvent.Pre event) {
/* 47 */     (event.getEntityPlayer()).field_70737_aN = 0;
/*    */   }
/*    */   
/*    */   public enum RenderMode {
/* 51 */     Solid,
/* 52 */     Wire;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\visual\WireESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */