/*    */ package me.ninethousand.ninehack.feature.features.combat;
/*    */ 
/*    */ import me.ninethousand.ninehack.feature.Category;
/*    */ import me.ninethousand.ninehack.feature.Feature;
/*    */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*    */ import me.ninethousand.ninehack.feature.setting.Setting;
/*    */ 
/*    */ @NineHackFeature(name = "Offhand", description = "Puts item in offhand", category = Category.Combat)
/*    */ public class Offhand extends Feature {
/* 10 */   public static final Setting<Boolean> searchHotbar = new Setting("Search Hotbar", Boolean.valueOf(true));
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\combat\Offhand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */