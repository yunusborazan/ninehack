/*    */ package me.ninethousand.ninehack.event.events;
/*    */ 
/*    */ import me.ninethousand.ninehack.event.EventStage;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class DeathEvent extends EventStage {
/*    */   public EntityPlayer player;
/*    */   
/*    */   public DeathEvent(EntityPlayer player) {
/* 10 */     this.player = player;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\event\events\DeathEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */