/*    */ package me.ninethousand.ninehack.feature.features.player;
/*    */ 
/*    */ import me.ninethousand.ninehack.feature.Category;
/*    */ import me.ninethousand.ninehack.feature.Feature;
/*    */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*    */ import me.ninethousand.ninehack.util.InventoryUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemEnderPearl;
/*    */ import net.minecraft.util.EnumHand;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ @NineHackFeature(name = "Pearl Bind", description = "Throw pearl with keybind", category = Category.Player)
/*    */ public class PearlBind
/*    */   extends Feature
/*    */ {
/*    */   public static Feature INSTANCE;
/*    */   public static boolean clicked = false;
/*    */   
/*    */   public void onEnable() {
/* 23 */     if (nullCheck()) disable();
/*    */     
/* 25 */     throwPearl();
/* 26 */     disable();
/*    */   }
/*    */ 
/*    */   
/*    */   private void throwPearl() {
/* 31 */     int pearlSlot = InventoryUtil.findHotbarBlock(ItemEnderPearl.class);
/* 32 */     boolean offhand = (mc.field_71439_g.func_184592_cb().func_77973_b() == Items.field_151079_bi), bl = offhand;
/* 33 */     if (pearlSlot != -1 || offhand) {
/* 34 */       int oldslot = mc.field_71439_g.field_71071_by.field_70461_c;
/* 35 */       if (!offhand) {
/* 36 */         InventoryUtil.switchToHotbarSlot(pearlSlot, false);
/*    */       }
/* 38 */       mc.field_71442_b.func_187101_a((EntityPlayer)mc.field_71439_g, (World)mc.field_71441_e, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
/* 39 */       if (!offhand)
/* 40 */         InventoryUtil.switchToHotbarSlot(oldslot, false); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\player\PearlBind.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */