/*    */ package me.ninethousand.ninehack.feature.command;
/*    */ 
/*    */ import me.ninethousand.ninehack.feature.Feature;
/*    */ import me.ninethousand.ninehack.managers.FeatureManager;
/*    */ import me.yagel15637.venture.command.AbstractCommand;
/*    */ 
/*    */ public final class ToggleCommand
/*    */   extends AbstractCommand
/*    */ {
/*    */   public ToggleCommand() {
/* 11 */     super("Toggles a specified module.", "toggle/t/ [module name]", new String[] { "toggle", "t" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void execute(String[] args) {
/* 16 */     if (args.length == 0)
/*    */       return; 
/* 18 */     for (String string : args) {
/* 19 */       for (Feature module : FeatureManager.getFeatures()) {
/* 20 */         if (module.getName().equalsIgnoreCase(string))
/* 21 */           module.toggle(); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\command\ToggleCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */