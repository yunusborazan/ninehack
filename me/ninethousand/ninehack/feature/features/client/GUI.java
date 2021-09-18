/*    */ package me.ninethousand.ninehack.feature.features.client;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.ninethousand.ninehack.feature.Category;
/*    */ import me.ninethousand.ninehack.feature.Feature;
/*    */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*    */ import me.ninethousand.ninehack.feature.gui.clickgui.ClickGUI;
/*    */ import me.ninethousand.ninehack.feature.setting.NumberSetting;
/*    */ import me.ninethousand.ninehack.feature.setting.Setting;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ 
/*    */ @NineHackFeature(name = "Gui", description = "Sexy ClickGUI", category = Category.Client, key = 23)
/*    */ public class GUI
/*    */   extends Feature
/*    */ {
/*    */   public static Feature INSTANCE;
/*    */   public static boolean guiOpen = false;
/* 19 */   public static final NumberSetting<Integer> width = new NumberSetting("Width", Integer.valueOf(80), Integer.valueOf(110), Integer.valueOf(130), 1);
/* 20 */   public static final NumberSetting<Integer> height = new NumberSetting("Height", Integer.valueOf(12), Integer.valueOf(14), Integer.valueOf(20), 1);
/* 21 */   public static final Setting<Boolean> outline = new Setting("Outline", Boolean.valueOf(true));
/* 22 */   public static final Setting<Boolean> gradientFeatures = new Setting("Gradient", Boolean.valueOf(false));
/* 23 */   public static final Setting<Color> headerColor = new Setting("Header", ClickGUI.HEADER_COLOR);
/* 24 */   public static final Setting<Color> accentColor = new Setting("Accent", ClickGUI.ACCENT_COLOR);
/* 25 */   public static final Setting<Color> featureFill = new Setting("Fill", ClickGUI.FEATURE_FILL_COLOR);
/* 26 */   public static final Setting<Color> featureBackground = new Setting("Background", ClickGUI.FEATURE_BACKGROUND_COLOR);
/* 27 */   public static final Setting<Color> fontColor = new Setting("Font", ClickGUI.FONT_COLOR);
/*    */   
/* 29 */   public static final GuiScreen gui = (GuiScreen)new ClickGUI();
/*    */   
/*    */   public GUI() {
/* 32 */     addSettings(new Setting[] { (Setting)width, (Setting)height, outline, gradientFeatures, headerColor, accentColor, featureFill, featureBackground, fontColor });
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
/*    */   public void onEnable() {
/* 47 */     mc.func_147108_a(gui);
/* 48 */     guiOpen = true;
/* 49 */     ClickGUI.WIDTH = ((Integer)width.getValue()).intValue();
/* 50 */     ClickGUI.HEIGHT = ((Integer)height.getValue()).intValue();
/* 51 */     ClickGUI.FEATURE_HEIGHT = ((Integer)height.getValue()).intValue() - 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 56 */     ClickGUI.HEADER_COLOR = (Color)headerColor.getValue();
/* 57 */     ClickGUI.ACCENT_COLOR = (Color)accentColor.getValue();
/* 58 */     ClickGUI.FEATURE_FILL_COLOR = (Color)featureFill.getValue();
/* 59 */     ClickGUI.FEATURE_BACKGROUND_COLOR = (Color)featureBackground.getValue();
/* 60 */     ClickGUI.FONT_COLOR = (Color)fontColor.getValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\client\GUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */