/*    */ package me.ninethousand.ninehack.managers;
/*    */ import java.util.ArrayList;
/*    */ import me.ninethousand.ninehack.feature.Category;
/*    */ import me.ninethousand.ninehack.feature.Feature;
/*    */ import me.ninethousand.ninehack.feature.features.client.Chat;
/*    */ import me.ninethousand.ninehack.feature.features.client.ClientColor;
/*    */ import me.ninethousand.ninehack.feature.features.client.CustomFont;
/*    */ import me.ninethousand.ninehack.feature.features.client.RPC;
/*    */ import me.ninethousand.ninehack.feature.features.combat.Burrow;
/*    */ import me.ninethousand.ninehack.feature.features.movement.AntiWeb;
/*    */ import me.ninethousand.ninehack.feature.features.player.Ghost;
/*    */ import me.ninethousand.ninehack.feature.features.player.PearlBind;
/*    */ import me.ninethousand.ninehack.feature.features.visual.HoleESP;
/*    */ import me.ninethousand.ninehack.feature.features.visual.Menu;
/*    */ 
/*    */ public final class FeatureManager {
/* 17 */   private static final ArrayList<Feature> features = new ArrayList<>();
/*    */   
/*    */   public static void init() {
/* 20 */     features.addAll(Arrays.asList(new Feature[] { Burrow.INSTANCE = (Feature)new Burrow(), Sprint.INSTANCE = (Feature)new Sprint(), AntiWeb.INSTANCE = (Feature)new AntiWeb(), PearlBind.INSTANCE = (Feature)new PearlBind(), Ghost.INSTANCE = (Feature)new Ghost(), Blur.INSTANCE = (Feature)new Blur(), FOV.INSTANCE = (Feature)new FOV(), Swing.INSTANCE = (Feature)new Swing(), WireESP.INSTANCE = (Feature)new WireESP(), Menu.INSTANCE = (Feature)new Menu(), HoleESP.INSTANCE = (Feature)new HoleESP(), GUI.INSTANCE = (Feature)new GUI(), HUD.INSTANCE = (Feature)new HUD(), CustomFont.INSTANCE = (Feature)new CustomFont(), ClientColor.INSTANCE = (Feature)new ClientColor(), RPC.INSTANCE = (Feature)new RPC(), (Feature)(Chat.INSTANCE = new Chat()) }));
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 52 */     features.sort(FeatureManager::alphabetize);
/*    */   }
/*    */   
/*    */   private static int alphabetize(Feature feature1, Feature feature2) {
/* 56 */     return feature1.getName().compareTo(feature2.getName());
/*    */   }
/*    */   
/*    */   public static ArrayList<Feature> getFeatures() {
/* 60 */     return features;
/*    */   }
/*    */   
/*    */   public static ArrayList<Feature> getFeaturesInCategory(Category category) {
/* 64 */     ArrayList<Feature> featuresInCategory = new ArrayList<>();
/*    */     
/* 66 */     for (Feature feature : features) {
/* 67 */       if (feature.getCategory().equals(category)) {
/* 68 */         featuresInCategory.add(feature);
/*    */       }
/*    */     } 
/*    */     
/* 72 */     return featuresInCategory;
/*    */   }
/*    */   
/*    */   public static Feature getFeatureByName(String name) {
/* 76 */     return features.stream()
/* 77 */       .filter(feature -> feature.getName().equalsIgnoreCase(name))
/* 78 */       .findFirst()
/* 79 */       .orElse(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\managers\FeatureManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */