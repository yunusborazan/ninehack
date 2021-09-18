package me.ninethousand.ninehack.feature.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import me.ninethousand.ninehack.feature.Category;

@Retention(RetentionPolicy.RUNTIME)
public @interface NineHackFeature {
  String name();
  
  String description() default "No description";
  
  Category category();
  
  int key() default 0;
}


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\annotation\NineHackFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */