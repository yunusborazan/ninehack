package org.spongepowered.asm.mixin.transformer.ext;

import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

public interface ITargetClassContext {
  ClassInfo getClassInfo();
  
  ClassNode getClassNode();
}


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\transformer\ext\ITargetClassContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */