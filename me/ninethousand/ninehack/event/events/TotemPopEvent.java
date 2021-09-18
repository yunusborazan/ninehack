/*    */ package me.ninethousand.ninehack.event.events;
/*    */ 
/*    */ import me.ninethousand.ninehack.event.EventStage;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class TotemPopEvent extends EventStage {
/*    */   private final EntityPlayer entity;
/*    */   
/*    */   public TotemPopEvent(EntityPlayer entity) {
/* 10 */     this.entity = entity;
/*    */   }
/*    */   
/*    */   public EntityPlayer getEntity() {
/* 14 */     return this.entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\event\events\TotemPopEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */