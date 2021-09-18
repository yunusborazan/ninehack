/*    */ package me.ninethousand.ninehack.mixin;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
/*    */ import org.spongepowered.asm.launch.MixinBootstrap;
/*    */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*    */ import org.spongepowered.asm.mixin.Mixins;
/*    */ 
/*    */ public final class MixinLoader
/*    */   implements IFMLLoadingPlugin {
/*    */   private static boolean isObfuscatedEnvironment = false;
/*    */   
/*    */   public MixinLoader() {
/* 14 */     MixinBootstrap.init();
/* 15 */     Mixins.addConfiguration("mixins.ninehack.json");
/* 16 */     MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getASMTransformerClass() {
/* 21 */     return new String[0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getModContainerClass() {
/* 26 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSetupClass() {
/* 31 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void injectData(Map<String, Object> data) {
/* 36 */     isObfuscatedEnvironment = ((Boolean)data.get("runtimeDeobfuscationEnabled")).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getAccessTransformerClass() {
/* 41 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\mixin\MixinLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */