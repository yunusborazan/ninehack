/*    */ package me.yagel15637.venture.manager;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import me.yagel15637.venture.command.ICommand;
/*    */ import me.yagel15637.venture.exceptions.VentureException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CommandManager
/*    */ {
/* 16 */   private static final ArrayList<ICommand> commands = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean ignoresCases = true;
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean debug = false;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ArrayList<ICommand> getCommands() {
/* 31 */     return commands;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static void addCommands(ICommand... commands) {
/* 37 */     CommandManager.commands.addAll(Arrays.asList(commands));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void parseCommand(String... lines) {
/* 44 */     for (String line : lines) {
/* 45 */       String[] split = line.split(" ");
/* 46 */       String cmdName = split[0];
/* 47 */       String[] args = Arrays.<String>copyOfRange(split, 1, split.length);
/*    */       
/* 49 */       for (ICommand command : commands) {
/* 50 */         for (String alias : command.getAliases()) {
/* 51 */           if (ignoresCases ? cmdName.equalsIgnoreCase(alias) : cmdName.equals(alias))
/*    */             try {
/* 53 */               command.execute(args);
/* 54 */             } catch (VentureException e) {
/* 55 */               if (debug) e.printStackTrace(); 
/*    */             }  
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\yagel15637\venture\manager\CommandManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */