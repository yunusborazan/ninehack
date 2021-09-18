/*    */ package me.ninethousand.ninehack.event;
/*    */ 
/*    */ import net.minecraftforge.fml.common.eventhandler.Event;
/*    */ 
/*    */ public class EventStage
/*    */   extends Event {
/*    */   private int stage;
/*    */   
/*    */   public EventStage() {}
/*    */   
/*    */   public EventStage(int stage) {
/* 12 */     this.stage = stage;
/*    */   }
/*    */   
/*    */   public int getStage() {
/* 16 */     return this.stage;
/*    */   }
/*    */   
/*    */   public void setStage(int stage) {
/* 20 */     this.stage = stage;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\event\EventStage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */