package me.ninethousand.ninehack.mixin.accessors.game;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({Minecraft.class})
public interface IMinecraft {
  @Accessor("rightClickDelayTimer")
  int getRightClickDelayTimer();
  
  @Accessor("rightClickDelayTimer")
  void setRightClickDelayTimer(int paramInt);
  
  @Accessor("timer")
  Timer getTimer();
  
  @Accessor("integratedServerIsRunning")
  boolean getIntegratedServerIsRunning();
}


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\mixin\accessors\game\IMinecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */