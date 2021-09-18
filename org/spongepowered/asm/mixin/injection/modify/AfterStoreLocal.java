/*    */ package org.spongepowered.asm.mixin.injection.modify;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*    */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @AtCode("STORE")
/*    */ public class AfterStoreLocal
/*    */   extends BeforeLoadLocal
/*    */ {
/*    */   public AfterStoreLocal(InjectionPointData data) {
/* 75 */     super(data, 54, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\injection\modify\AfterStoreLocal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */