package org.spongepowered.asm.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.CLASS)
public @interface Intrinsic {
  boolean displace() default false;
}


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\Intrinsic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */