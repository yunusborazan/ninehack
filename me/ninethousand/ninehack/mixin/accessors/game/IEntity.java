package me.ninethousand.ninehack.mixin.accessors.game;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({Entity.class})
public interface IEntity {
  @Accessor("isInWeb")
  boolean getIsInWeb();
  
  @Accessor("isInWeb")
  void setIsInWeb(boolean paramBoolean);
}


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\mixin\accessors\game\IEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */