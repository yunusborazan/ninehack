/*    */ package me.ninethousand.ninehack;
/*    */ import me.ninethousand.ninehack.feature.command.ToggleCommand;
/*    */ import me.ninethousand.ninehack.feature.features.client.RPC;
/*    */ import me.ninethousand.ninehack.managers.FeatureManager;
/*    */ import me.ninethousand.ninehack.util.RPCUtil;
/*    */ import me.yagel15637.venture.command.ICommand;
/*    */ import me.yagel15637.venture.manager.CommandManager;
/*    */ 
/*    */ public class StartUp {
/*    */   public static void start() {
/* 11 */     NineHack.EVENT_PROCESSOR.init();
/* 12 */     NineHack.log("Events Initialised");
/*    */     
/* 14 */     FeatureManager.init();
/* 15 */     NineHack.log("Features Initialised");
/*    */     
/* 17 */     initCommandManager();
/* 18 */     NineHack.log("Commands Initialised");
/*    */     
/* 20 */     NineHack.CUSTOM_MAIN_MENU.func_73866_w_();
/*    */     
/* 22 */     if (RPC.INSTANCE.isEnabled()) {
/* 23 */       RPCUtil.startup();
/*    */     }
/*    */   }
/*    */   
/*    */   private static void initCommandManager() {
/* 28 */     CommandManager.addCommands(new ICommand[] { (ICommand)new ToggleCommand() });
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\StartUp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */