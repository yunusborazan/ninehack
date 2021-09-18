package org.spongepowered.asm.launch.platform;

public interface IMixinPlatformAgent {
  String getPhaseProvider();
  
  void prepare();
  
  void initPrimaryContainer();
  
  void inject();
  
  String getLaunchTarget();
}


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\launch\platform\IMixinPlatformAgent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */