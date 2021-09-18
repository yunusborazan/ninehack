/*    */ package me.yagel15637.venture.exceptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VentureException
/*    */   extends Exception
/*    */ {
/*    */   private final String message;
/*    */   
/*    */   public VentureException() {
/* 13 */     this("");
/*    */   }
/*    */   
/*    */   public VentureException(String message) {
/* 17 */     this.message = message;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final String getMessage() {
/* 23 */     return this.message;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\yagel15637\venture\exceptions\VentureException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */