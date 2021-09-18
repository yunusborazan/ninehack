/*    */ package me.ninethousand.ninehack.feature.features.client;
/*    */ 
/*    */ import me.ninethousand.ninehack.feature.Category;
/*    */ import me.ninethousand.ninehack.feature.Feature;
/*    */ import me.ninethousand.ninehack.feature.annotation.EnabledByDefault;
/*    */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*    */ import me.ninethousand.ninehack.feature.setting.Setting;
/*    */ import me.ninethousand.ninehack.util.RPCUtil;
/*    */ 
/*    */ @EnabledByDefault
/*    */ @NineHackFeature(name = "RPC", description = "Discord RPC", category = Category.Client)
/*    */ public class RPC
/*    */   extends Feature {
/*    */   public static Feature INSTANCE;
/* 15 */   public static final Setting<RPCMode> rpcMode = new Setting("Mode", RPCMode.NineHack);
/*    */   
/*    */   public RPC() {
/* 18 */     addSettings(new Setting[] { rpcMode });
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 23 */     if (nullCheck())
/*    */       return; 
/* 25 */     RPCUtil.startup();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 30 */     if (nullCheck())
/*    */       return; 
/* 32 */     RPCUtil.shutdown();
/*    */   }
/*    */   
/*    */   public enum RPCMode {
/* 36 */     NineHack,
/* 37 */     TuxHack;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\client\RPC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */