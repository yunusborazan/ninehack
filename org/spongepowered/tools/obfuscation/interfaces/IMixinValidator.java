/*    */ package org.spongepowered.tools.obfuscation.interfaces;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import javax.lang.model.element.TypeElement;
/*    */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*    */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
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
/*    */ public interface IMixinValidator
/*    */ {
/*    */   boolean validate(ValidationPass paramValidationPass, TypeElement paramTypeElement, AnnotationHandle paramAnnotationHandle, Collection<TypeHandle> paramCollection);
/*    */   
/*    */   public enum ValidationPass
/*    */   {
/* 44 */     EARLY,
/* 45 */     LATE,
/* 46 */     FINAL;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\tools\obfuscation\interfaces\IMixinValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */