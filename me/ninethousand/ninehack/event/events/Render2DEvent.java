/*    */ package me.ninethousand.ninehack.event.events;
/*    */ 
/*    */ import me.ninethousand.ninehack.event.EventStage;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ 
/*    */ public class Render2DEvent extends EventStage {
/*    */   public float partialTicks;
/*    */   public ScaledResolution scaledResolution;
/*    */   
/*    */   public Render2DEvent(float partialTicks, ScaledResolution scaledResolution) {
/* 11 */     this.partialTicks = partialTicks;
/* 12 */     this.scaledResolution = scaledResolution;
/*    */   }
/*    */   
/*    */   public void setPartialTicks(float partialTicks) {
/* 16 */     this.partialTicks = partialTicks;
/*    */   }
/*    */   
/*    */   public void setScaledResolution(ScaledResolution scaledResolution) {
/* 20 */     this.scaledResolution = scaledResolution;
/*    */   }
/*    */   
/*    */   public double getScreenWidth() {
/* 24 */     return this.scaledResolution.func_78327_c();
/*    */   }
/*    */   
/*    */   public double getScreenHeight() {
/* 28 */     return this.scaledResolution.func_78324_d();
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\event\events\Render2DEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */