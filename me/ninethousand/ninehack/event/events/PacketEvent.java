/*    */ package me.ninethousand.ninehack.event.events;
/*    */ 
/*    */ import me.ninethousand.ninehack.event.EventStage;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ 
/*    */ public class PacketEvent extends EventStage {
/*    */   private final Packet<?> packet;
/*    */   
/*    */   public PacketEvent(int stage, Packet<?> packet) {
/* 11 */     super(stage);
/* 12 */     this.packet = packet;
/*    */   }
/*    */   
/*    */   public <T extends Packet<?>> T getPacket() {
/* 16 */     return (T)this.packet;
/*    */   }
/*    */   
/*    */   @Cancelable
/*    */   public static class Send
/*    */     extends PacketEvent {
/*    */     public Send(int stage, Packet<?> packet) {
/* 23 */       super(stage, packet);
/*    */     }
/*    */   }
/*    */   
/*    */   @Cancelable
/*    */   public static class Receive
/*    */     extends PacketEvent {
/*    */     public Receive(int stage, Packet<?> packet) {
/* 31 */       super(stage, packet);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\event\events\PacketEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */