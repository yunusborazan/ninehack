/*    */ package me.ninethousand.ninehack.mixin.mixins.game;
/*    */ 
/*    */ import me.ninethousand.ninehack.feature.features.visual.Menu;
/*    */ import me.ninethousand.ninehack.feature.gui.menu.CustomMainMenu;
/*    */ import me.ninethousand.ninehack.mixin.accessors.game.IMinecraft;
/*    */ import me.ninethousand.ninehack.util.Stopper;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.util.Timer;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({Minecraft.class})
/*    */ public final class MixinMinecraft
/*    */   implements IMinecraft
/*    */ {
/*    */   @Shadow
/*    */   private int field_71467_ac;
/*    */   @Shadow
/*    */   private boolean field_71455_al;
/*    */   @Shadow
/*    */   @Final
/*    */   private Timer field_71428_T;
/*    */   @Shadow
/*    */   public EntityPlayerSP field_71439_g;
/*    */   
/*    */   public int getRightClickDelayTimer() {
/* 36 */     return this.field_71467_ac;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRightClickDelayTimer(int rightClickDelayTimer) {
/* 41 */     this.field_71467_ac = rightClickDelayTimer;
/*    */   }
/*    */ 
/*    */   
/*    */   public Timer getTimer() {
/* 46 */     return this.field_71428_T;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getIntegratedServerIsRunning() {
/* 51 */     return this.field_71455_al;
/*    */   }
/*    */   
/* 54 */   private final Stopper stopper = new Stopper();
/* 55 */   private int progression = 0;
/*    */   private boolean progressionState = true;
/*    */   
/*    */   @Inject(method = {"runTick()V"}, at = {@At("RETURN")})
/*    */   private void runTick(CallbackInfo callbackInfo) {
/* 60 */     if ((Minecraft.func_71410_x()).field_71462_r instanceof net.minecraft.client.gui.GuiMainMenu && Menu.INSTANCE.isEnabled()) {
/* 61 */       Minecraft.func_71410_x().func_147108_a((GuiScreen)new CustomMainMenu());
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"displayGuiScreen"}, at = {@At("HEAD")})
/*    */   private void displayGuiScreen(GuiScreen screen, CallbackInfo ci) {
/* 67 */     if (screen instanceof net.minecraft.client.gui.GuiMainMenu && Menu.INSTANCE.isEnabled()) {
/* 68 */       displayGuiScreen(new CustomMainMenu());
/*    */     }
/*    */   }
/*    */   
/*    */   private void displayGuiScreen(CustomMainMenu customMainMenu) {
/* 73 */     customMainMenu.func_73866_w_();
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\mixin\mixins\game\MixinMinecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */