package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface Group {
  String name() default "";
  
  int min() default -1;
  
  int max() default -1;
}


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\injection\Group.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */