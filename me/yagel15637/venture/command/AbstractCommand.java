/*    */ package me.yagel15637.venture.command;
/*    */ 
/*    */ 
/*    */ public abstract class AbstractCommand
/*    */   implements ICommand
/*    */ {
/*    */   private final String[] aliases;
/*    */   private final String description;
/*    */   private final String usage;
/*    */   
/*    */   public AbstractCommand(String description, String usage, String... aliases) {
/* 12 */     this.aliases = aliases;
/* 13 */     this.description = description;
/* 14 */     this.usage = usage;
/*    */   }
/*    */   
/* 17 */   public String[] getAliases() { return this.aliases; }
/* 18 */   public String getDescription() { return this.description; } public String getUsage() {
/* 19 */     return this.usage;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\yagel15637\venture\command\AbstractCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */