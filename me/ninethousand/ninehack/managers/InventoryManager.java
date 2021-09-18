/*    */ package me.ninethousand.ninehack.managers;
/*    */ 
/*    */ import me.ninethousand.ninehack.NineHack;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*    */ 
/*    */ public class InventoryManager implements NineHack.Globals {
/*  8 */   private int recoverySlot = -1; public int currentPlayerItem;
/*    */   
/*    */   public void update() {
/* 11 */     if (this.recoverySlot != -1) {
/* 12 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange((this.recoverySlot == 8) ? 7 : (this.recoverySlot + 1)));
/* 13 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.recoverySlot));
/* 14 */       mc.field_71439_g.field_71071_by.field_70461_c = this.recoverySlot;
/* 15 */       int i = mc.field_71439_g.field_71071_by.field_70461_c;
/* 16 */       if (i != this.currentPlayerItem) {
/* 17 */         this.currentPlayerItem = i;
/* 18 */         mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.currentPlayerItem));
/*    */       } 
/* 20 */       this.recoverySlot = -1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void recoverSilent(int slot) {
/* 25 */     this.recoverySlot = slot;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\managers\InventoryManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */