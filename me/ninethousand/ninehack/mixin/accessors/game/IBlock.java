package me.ninethousand.ninehack.mixin.accessors.game;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({Block.class})
public interface IBlock {
  @Accessor("material")
  Material getMaterial();
}


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\mixin\accessors\game\IBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */