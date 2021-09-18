package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
  String id() default "";
  
  String[] method();
  
  Slice[] slice() default {};
  
  At[] at();
  
  boolean cancellable() default false;
  
  LocalCapture locals() default LocalCapture.NO_CAPTURE;
  
  boolean remap() default true;
  
  int require() default -1;
  
  int expect() default 1;
  
  int allow() default -1;
  
  String constraints() default "";
}


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\injection\Inject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */