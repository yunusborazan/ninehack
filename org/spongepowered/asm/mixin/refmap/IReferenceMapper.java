package org.spongepowered.asm.mixin.refmap;

public interface IReferenceMapper {
  boolean isDefault();
  
  String getResourceName();
  
  String getStatus();
  
  String getContext();
  
  void setContext(String paramString);
  
  String remap(String paramString1, String paramString2);
  
  String remapWithContext(String paramString1, String paramString2, String paramString3);
}


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\refmap\IReferenceMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */