/*    */ package me.ninethousand.ninehack.event.events;
/*    */ 
/*    */ import me.ninethousand.ninehack.event.EventStage;
/*    */ 
/*    */ public class Render3DEvent extends EventStage {
/*    */   private float partialTicks;
/*    */   
/*    */   public Render3DEvent(float partialTicks) {
/*  9 */     this.partialTicks = partialTicks;
/*    */   }
/*    */   
/*    */   public float getPartialTicks() {
/* 13 */     return this.partialTicks;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\event\events\Render3DEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */