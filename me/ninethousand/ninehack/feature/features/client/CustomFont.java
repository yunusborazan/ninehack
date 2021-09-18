/*    */ package me.ninethousand.ninehack.feature.features.client;
/*    */ 
/*    */ import java.awt.GraphicsEnvironment;
/*    */ import me.ninethousand.ninehack.NineHack;
/*    */ import me.ninethousand.ninehack.feature.Category;
/*    */ import me.ninethousand.ninehack.feature.Feature;
/*    */ import me.ninethousand.ninehack.feature.annotation.EnabledByDefault;
/*    */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*    */ import me.ninethousand.ninehack.feature.setting.NumberSetting;
/*    */ import me.ninethousand.ninehack.feature.setting.Setting;
/*    */ import me.ninethousand.ninehack.util.ChatUtil;
/*    */ 
/*    */ @EnabledByDefault
/*    */ @NineHackFeature(name = "Font", description = "Modify the client fonts", category = Category.Client)
/*    */ public class CustomFont
/*    */   extends Feature
/*    */ {
/*    */   public static Feature INSTANCE;
/* 19 */   public static final Setting<String> fontName = new Setting("Name", "Consolas");
/* 20 */   public static final Setting<Boolean> shadow = new Setting("Shadow", Boolean.valueOf(true));
/* 21 */   public static final NumberSetting<Integer> size = new NumberSetting("Size", Integer.valueOf(10), Integer.valueOf(17), Integer.valueOf(30), 0);
/* 22 */   public static final Setting<FontStyle> style = new Setting("Style", FontStyle.Normal);
/* 23 */   public static final Setting<Boolean> override = new Setting("Override", Boolean.valueOf(true));
/*    */   
/*    */   public CustomFont() {
/* 26 */     addSettings(new Setting[] { fontName, shadow, (Setting)size, style, override });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean checkFont(String font, boolean message) {
/* 36 */     String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
/* 37 */     for (String s : fonts) {
/* 38 */       if (!message && s.equals(font)) {
/* 39 */         return true;
/*    */       }
/* 41 */       if (message)
/* 42 */         ChatUtil.sendClientMessageSimple(s); 
/*    */     } 
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 49 */     if (nullCheck())
/*    */       return; 
/* 51 */     NineHack.TEXT_MANAGER.init();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum FontStyle
/*    */   {
/* 62 */     Normal(0),
/* 63 */     Bold(1),
/* 64 */     Italic(2);
/*    */     
/*    */     private final int style;
/*    */     
/*    */     FontStyle(int style) {
/* 69 */       this.style = style;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\client\CustomFont.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */