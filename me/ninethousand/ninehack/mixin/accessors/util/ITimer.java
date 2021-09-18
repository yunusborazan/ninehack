package me.ninethousand.ninehack.mixin.accessors.util;

import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({Timer.class})
public interface ITimer {
  @Accessor("tickLength")
  void setTickLength(float paramFloat);
}


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\mixin\accessor\\util\ITimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */