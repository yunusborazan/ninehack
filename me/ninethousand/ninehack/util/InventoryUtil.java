/*     */ package me.ninethousand.ninehack.util;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import me.ninethousand.ninehack.NineHack;
/*     */ import me.ninethousand.ninehack.mixin.accessors.game.IBlock;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.inventory.Slot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ 
/*     */ public class InventoryUtil implements NineHack.Globals {
/*     */   public static void switchToHotbarSlot(int slot, boolean silent) {
/*  25 */     if (mc.field_71439_g.field_71071_by.field_70461_c == slot || slot < 0) {
/*     */       return;
/*     */     }
/*  28 */     if (silent) {
/*  29 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
/*  30 */       mc.field_71442_b.func_78765_e();
/*     */     } else {
/*  32 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
/*  33 */       mc.field_71439_g.field_71071_by.field_70461_c = slot;
/*  34 */       mc.field_71442_b.func_78765_e();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void switchToHotbarSlot(Class clazz, boolean silent) {
/*  39 */     int slot = findHotbarBlock(clazz);
/*  40 */     if (slot > -1) {
/*  41 */       switchToHotbarSlot(slot, silent);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isNull(ItemStack stack) {
/*  46 */     return (stack == null || stack.func_77973_b() instanceof net.minecraft.item.ItemAir);
/*     */   }
/*     */   
/*     */   public static int findHotbarBlock(Class clazz) {
/*  50 */     for (int i = 0; i < 9; i++) {
/*     */       
/*  52 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/*  53 */       if (stack != ItemStack.field_190927_a) {
/*  54 */         if (clazz.isInstance(stack.func_77973_b()))
/*  55 */           return i; 
/*     */         Block block;
/*  57 */         if (stack.func_77973_b() instanceof ItemBlock && clazz.isInstance(block = ((ItemBlock)stack.func_77973_b()).func_179223_d()))
/*     */         {
/*  59 */           return i; } 
/*     */       } 
/*  61 */     }  return -1;
/*     */   }
/*     */   
/*     */   public static int findHotbarBlock(Block blockIn) {
/*  65 */     for (int i = 0; i < 9; ) {
/*     */       
/*  67 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i); Block block;
/*  68 */       if (stack == ItemStack.field_190927_a || !(stack.func_77973_b() instanceof ItemBlock) || (block = ((ItemBlock)stack.func_77973_b()).func_179223_d()) != blockIn) {
/*     */         i++; continue;
/*  70 */       }  return i;
/*     */     } 
/*  72 */     return -1;
/*     */   }
/*     */   
/*     */   public static int getItemHotbar(Item input) {
/*  76 */     for (int i = 0; i < 9; ) {
/*  77 */       Item item = mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
/*  78 */       if (Item.func_150891_b(item) != Item.func_150891_b(input)) { i++; continue; }
/*  79 */        return i;
/*     */     } 
/*  81 */     return -1;
/*     */   }
/*     */   
/*     */   public static int findStackInventory(Item input) {
/*  85 */     return findStackInventory(input, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int findStackInventory(Item input, boolean withHotbar) {
/*  90 */     int i = withHotbar ? 0 : 9, n = i;
/*  91 */     while (i < 36) {
/*  92 */       Item item = mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
/*  93 */       if (Item.func_150891_b(input) == Item.func_150891_b(item)) {
/*  94 */         return i + ((i < 9) ? 36 : 0);
/*     */       }
/*  96 */       i++;
/*     */     } 
/*  98 */     return -1;
/*     */   }
/*     */   
/*     */   public static int findItemInventorySlot(Item item, boolean offHand) {
/* 102 */     AtomicInteger slot = new AtomicInteger();
/* 103 */     slot.set(-1);
/* 104 */     for (Map.Entry<Integer, ItemStack> entry : getInventoryAndHotbarSlots().entrySet()) {
/* 105 */       if (((ItemStack)entry.getValue()).func_77973_b() != item || (((Integer)entry.getKey()).intValue() == 45 && !offHand))
/* 106 */         continue;  slot.set(((Integer)entry.getKey()).intValue());
/* 107 */       return slot.get();
/*     */     } 
/* 109 */     return slot.get();
/*     */   }
/*     */   public static List<Integer> getItemInventory(Item item) {
/* 112 */     List<Integer> ints = new ArrayList<>();
/* 113 */     for (int i = 9; i < 36; i++) {
/*     */       
/* 115 */       Item target = mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
/*     */       
/* 117 */       if (item instanceof ItemBlock && ((ItemBlock)item).func_179223_d().equals(item)) ints.add(Integer.valueOf(i));
/*     */     
/*     */     } 
/* 120 */     if (ints.size() == 0) ints.add(Integer.valueOf(-1));
/*     */     
/* 122 */     return ints;
/*     */   }
/*     */   public static List<Integer> findEmptySlots(boolean withXCarry) {
/* 125 */     ArrayList<Integer> outPut = new ArrayList<>();
/* 126 */     for (Map.Entry<Integer, ItemStack> entry : getInventoryAndHotbarSlots().entrySet()) {
/* 127 */       boolean isEmpty = ((ItemStack)entry.getValue()).func_190926_b();
/* 128 */       if (!isEmpty && ((ItemStack)entry.getValue()).func_77973_b() != Items.field_190931_a)
/* 129 */         continue;  outPut.add(entry.getKey());
/*     */     } 
/* 131 */     if (withXCarry)
/* 132 */       for (int i = 1; i < 5; i++) {
/* 133 */         Slot craftingSlot = mc.field_71439_g.field_71069_bz.field_75151_b.get(i);
/* 134 */         ItemStack craftingStack = craftingSlot.func_75211_c();
/* 135 */         if (craftingStack.func_190926_b() || craftingStack.func_77973_b() == Items.field_190931_a) {
/* 136 */           outPut.add(Integer.valueOf(i));
/*     */         }
/*     */       }  
/* 139 */     return outPut;
/*     */   }
/*     */   
/*     */   public static int findInventoryBlock(Class clazz, boolean offHand) {
/* 143 */     AtomicInteger slot = new AtomicInteger();
/* 144 */     slot.set(-1);
/* 145 */     for (Map.Entry<Integer, ItemStack> entry : getInventoryAndHotbarSlots().entrySet()) {
/* 146 */       if (!isBlock(((ItemStack)entry.getValue()).func_77973_b(), clazz) || (((Integer)entry.getKey()).intValue() == 45 && !offHand))
/* 147 */         continue;  slot.set(((Integer)entry.getKey()).intValue());
/* 148 */       return slot.get();
/*     */     } 
/* 150 */     return slot.get();
/*     */   }
/*     */   
/*     */   public static boolean isBlock(Item item, Class clazz) {
/* 154 */     if (item instanceof ItemBlock) {
/* 155 */       Block block = ((ItemBlock)item).func_179223_d();
/* 156 */       return clazz.isInstance(block);
/*     */     } 
/* 158 */     return false;
/*     */   }
/*     */   
/*     */   public static void confirmSlot(int slot) {
/* 162 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
/* 163 */     mc.field_71439_g.field_71071_by.field_70461_c = slot;
/* 164 */     mc.field_71442_b.func_78765_e();
/*     */   }
/*     */   
/*     */   public static Map<Integer, ItemStack> getInventoryAndHotbarSlots() {
/* 168 */     return getInventorySlots(9, 44);
/*     */   }
/*     */   
/*     */   private static Map<Integer, ItemStack> getInventorySlots(int currentI, int last) {
/* 172 */     HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<>();
/* 173 */     for (int current = currentI; current <= last; current++) {
/* 174 */       fullInventorySlots.put(Integer.valueOf(current), mc.field_71439_g.field_71069_bz.func_75138_a().get(current));
/*     */     }
/* 176 */     return fullInventorySlots;
/*     */   }
/*     */   
/*     */   public static boolean[] switchItem(boolean back, int lastHotbarSlot, boolean switchedItem, Switch mode, Class clazz) {
/* 180 */     boolean[] switchedItemSwitched = { switchedItem, false };
/* 181 */     switch (mode) {
/*     */       case NORMAL:
/* 183 */         if (!back && !switchedItem) {
/* 184 */           switchToHotbarSlot(findHotbarBlock(clazz), false);
/* 185 */           switchedItemSwitched[0] = true;
/* 186 */         } else if (back && switchedItem) {
/* 187 */           switchToHotbarSlot(lastHotbarSlot, false);
/* 188 */           switchedItemSwitched[0] = false;
/*     */         } 
/* 190 */         switchedItemSwitched[1] = true;
/*     */         break;
/*     */       
/*     */       case SILENT:
/* 194 */         if (!back && !switchedItem) {
/* 195 */           switchToHotbarSlot(findHotbarBlock(clazz), true);
/* 196 */           switchedItemSwitched[0] = true;
/* 197 */         } else if (back && switchedItem) {
/* 198 */           switchedItemSwitched[0] = false;
/* 199 */           NineHack.INVENTORY_MANAGER.recoverSilent(lastHotbarSlot);
/*     */         } 
/* 201 */         switchedItemSwitched[1] = true;
/*     */         break;
/*     */       
/*     */       case NONE:
/* 205 */         switchedItemSwitched[1] = (back || mc.field_71439_g.field_71071_by.field_70461_c == findHotbarBlock(clazz));
/*     */         break;
/*     */     } 
/* 208 */     return switchedItemSwitched;
/*     */   }
/*     */   
/*     */   public static boolean[] switchItemToItem(boolean back, int lastHotbarSlot, boolean switchedItem, Switch mode, Item item) {
/* 212 */     boolean[] switchedItemSwitched = { switchedItem, false };
/* 213 */     switch (mode) {
/*     */       case NORMAL:
/* 215 */         if (!back && !switchedItem) {
/* 216 */           switchToHotbarSlot(getItemHotbar(item), false);
/* 217 */           switchedItemSwitched[0] = true;
/* 218 */         } else if (back && switchedItem) {
/* 219 */           switchToHotbarSlot(lastHotbarSlot, false);
/* 220 */           switchedItemSwitched[0] = false;
/*     */         } 
/* 222 */         switchedItemSwitched[1] = true;
/*     */         break;
/*     */       
/*     */       case SILENT:
/* 226 */         if (!back && !switchedItem) {
/* 227 */           switchToHotbarSlot(getItemHotbar(item), true);
/* 228 */           switchedItemSwitched[0] = true;
/* 229 */         } else if (back && switchedItem) {
/* 230 */           switchedItemSwitched[0] = false;
/* 231 */           NineHack.INVENTORY_MANAGER.recoverSilent(lastHotbarSlot);
/*     */         } 
/* 233 */         switchedItemSwitched[1] = true;
/*     */         break;
/*     */       
/*     */       case NONE:
/* 237 */         switchedItemSwitched[1] = (back || mc.field_71439_g.field_71071_by.field_70461_c == getItemHotbar(item));
/*     */         break;
/*     */     } 
/* 240 */     return switchedItemSwitched;
/*     */   }
/*     */   
/*     */   public static boolean holdingItem(Class clazz) {
/* 244 */     boolean result = false;
/* 245 */     ItemStack stack = mc.field_71439_g.func_184614_ca();
/* 246 */     result = isInstanceOf(stack, clazz);
/* 247 */     if (!result) {
/* 248 */       ItemStack offhand = mc.field_71439_g.func_184592_cb();
/* 249 */       result = isInstanceOf(stack, clazz);
/*     */     } 
/* 251 */     return result;
/*     */   }
/*     */   
/*     */   public static boolean isInstanceOf(ItemStack stack, Class clazz) {
/* 255 */     if (stack == null) {
/* 256 */       return false;
/*     */     }
/* 258 */     Item item = stack.func_77973_b();
/* 259 */     if (clazz.isInstance(item)) {
/* 260 */       return true;
/*     */     }
/* 262 */     if (item instanceof ItemBlock) {
/* 263 */       Block block = Block.func_149634_a(item);
/* 264 */       return clazz.isInstance(block);
/*     */     } 
/* 266 */     return false;
/*     */   }
/*     */   
/*     */   public static int getEmptyXCarry() {
/* 270 */     for (int i = 1; i < 5; ) {
/* 271 */       Slot craftingSlot = mc.field_71439_g.field_71069_bz.field_75151_b.get(i);
/* 272 */       ItemStack craftingStack = craftingSlot.func_75211_c();
/* 273 */       if (!craftingStack.func_190926_b() && craftingStack.func_77973_b() != Items.field_190931_a) { i++; continue; }
/* 274 */        return i;
/*     */     } 
/* 276 */     return -1;
/*     */   }
/*     */   
/*     */   public static boolean isSlotEmpty(int i) {
/* 280 */     Slot slot = mc.field_71439_g.field_71069_bz.field_75151_b.get(i);
/* 281 */     ItemStack stack = slot.func_75211_c();
/* 282 */     return stack.func_190926_b();
/*     */   }
/*     */   
/*     */   public static int convertHotbarToInv(int input) {
/* 286 */     return 36 + input;
/*     */   }
/*     */   
/*     */   public static boolean areStacksCompatible(ItemStack stack1, ItemStack stack2) {
/* 290 */     if (!stack1.func_77973_b().equals(stack2.func_77973_b())) {
/* 291 */       return false;
/*     */     }
/* 293 */     if (stack1.func_77973_b() instanceof ItemBlock && stack2.func_77973_b() instanceof ItemBlock) {
/* 294 */       Block block1 = ((ItemBlock)stack1.func_77973_b()).func_179223_d();
/* 295 */       Block block2 = ((ItemBlock)stack2.func_77973_b()).func_179223_d();
/*     */ 
/*     */ 
/*     */       
/* 299 */       if (!((IBlock)block1).getMaterial().equals(((IBlock)block2).getMaterial())) {
/* 300 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 304 */     if (!stack1.func_82833_r().equals(stack2.func_82833_r())) {
/* 305 */       return false;
/*     */     }
/* 307 */     return (stack1.func_77952_i() == stack2.func_77952_i());
/*     */   }
/*     */   
/*     */   public static EntityEquipmentSlot getEquipmentFromSlot(int slot) {
/* 311 */     if (slot == 5) {
/* 312 */       return EntityEquipmentSlot.HEAD;
/*     */     }
/* 314 */     if (slot == 6) {
/* 315 */       return EntityEquipmentSlot.CHEST;
/*     */     }
/* 317 */     if (slot == 7) {
/* 318 */       return EntityEquipmentSlot.LEGS;
/*     */     }
/* 320 */     return EntityEquipmentSlot.FEET;
/*     */   }
/*     */   
/*     */   public static int findArmorSlot(EntityEquipmentSlot type, boolean binding) {
/* 324 */     int slot = -1;
/* 325 */     float damage = 0.0F;
/* 326 */     for (int i = 9; i < 45; i++) {
/*     */ 
/*     */       
/* 329 */       ItemStack s = (Minecraft.func_71410_x()).field_71439_g.field_71069_bz.func_75139_a(i).func_75211_c(); ItemArmor armor;
/* 330 */       if (s.func_77973_b() != Items.field_190931_a && s.func_77973_b() instanceof ItemArmor && (armor = (ItemArmor)s.func_77973_b()).func_185083_B_() == type) {
/*     */         
/* 332 */         float currentDamage = (armor.field_77879_b + EnchantmentHelper.func_77506_a(Enchantments.field_180310_c, s));
/* 333 */         boolean cursed = (binding && EnchantmentHelper.func_190938_b(s)), bl = cursed;
/* 334 */         if (currentDamage > damage && !cursed)
/* 335 */         { damage = currentDamage;
/* 336 */           slot = i; } 
/*     */       } 
/* 338 */     }  return slot;
/*     */   }
/*     */   
/*     */   public static int findArmorSlot(EntityEquipmentSlot type, boolean binding, boolean withXCarry) {
/* 342 */     int slot = findArmorSlot(type, binding);
/* 343 */     if (slot == -1 && withXCarry) {
/* 344 */       float damage = 0.0F;
/* 345 */       for (int i = 1; i < 5; i++) {
/*     */ 
/*     */         
/* 348 */         Slot craftingSlot = mc.field_71439_g.field_71069_bz.field_75151_b.get(i);
/* 349 */         ItemStack craftingStack = craftingSlot.func_75211_c(); ItemArmor armor;
/* 350 */         if (craftingStack.func_77973_b() != Items.field_190931_a && craftingStack.func_77973_b() instanceof ItemArmor && (armor = (ItemArmor)craftingStack.func_77973_b()).func_185083_B_() == type) {
/*     */           
/* 352 */           float currentDamage = (armor.field_77879_b + EnchantmentHelper.func_77506_a(Enchantments.field_180310_c, craftingStack));
/* 353 */           boolean cursed = (binding && EnchantmentHelper.func_190938_b(craftingStack)), bl = cursed;
/* 354 */           if (currentDamage > damage && !cursed)
/* 355 */           { damage = currentDamage;
/* 356 */             slot = i; } 
/*     */         } 
/*     */       } 
/* 359 */     }  return slot;
/*     */   }
/*     */   
/*     */   public static int findItemInventorySlot(Item item, boolean offHand, boolean withXCarry) {
/* 363 */     int slot = findItemInventorySlot(item, offHand);
/* 364 */     if (slot == -1 && withXCarry)
/* 365 */       for (int i = 1; i < 5; i++) {
/*     */         
/* 367 */         Slot craftingSlot = mc.field_71439_g.field_71069_bz.field_75151_b.get(i);
/* 368 */         ItemStack craftingStack = craftingSlot.func_75211_c(); Item craftingStackItem;
/* 369 */         if (craftingStack.func_77973_b() != Items.field_190931_a && (craftingStackItem = craftingStack.func_77973_b()) == item)
/*     */         {
/* 371 */           slot = i;
/*     */         }
/*     */       }  
/* 374 */     return slot;
/*     */   }
/*     */   
/*     */   public static int findBlockSlotInventory(Class clazz, boolean offHand, boolean withXCarry) {
/* 378 */     int slot = findInventoryBlock(clazz, offHand);
/* 379 */     if (slot == -1 && withXCarry)
/* 380 */       for (int i = 1; i < 5; i++) {
/*     */         
/* 382 */         Slot craftingSlot = mc.field_71439_g.field_71069_bz.field_75151_b.get(i);
/* 383 */         ItemStack craftingStack = craftingSlot.func_75211_c();
/* 384 */         if (craftingStack.func_77973_b() != Items.field_190931_a) {
/* 385 */           Item craftingStackItem = craftingStack.func_77973_b();
/* 386 */           if (clazz.isInstance(craftingStackItem)) {
/* 387 */             slot = i;
/*     */           } else {
/*     */             Block block;
/* 390 */             if (craftingStackItem instanceof ItemBlock && clazz.isInstance(block = ((ItemBlock)craftingStackItem).func_179223_d()))
/*     */             {
/* 392 */               slot = i; } 
/*     */           } 
/*     */         } 
/* 395 */       }   return slot;
/*     */   }
/*     */   
/*     */   public enum Switch {
/* 399 */     NORMAL,
/* 400 */     SILENT,
/* 401 */     NONE;
/*     */   }
/*     */   
/*     */   public static class Task
/*     */   {
/*     */     private final int slot;
/*     */     private final boolean update;
/*     */     private final boolean quickClick;
/*     */     
/*     */     public Task() {
/* 411 */       this.update = true;
/* 412 */       this.slot = -1;
/* 413 */       this.quickClick = false;
/*     */     }
/*     */     
/*     */     public Task(int slot) {
/* 417 */       this.slot = slot;
/* 418 */       this.quickClick = false;
/* 419 */       this.update = false;
/*     */     }
/*     */     
/*     */     public Task(int slot, boolean quickClick) {
/* 423 */       this.slot = slot;
/* 424 */       this.quickClick = quickClick;
/* 425 */       this.update = false;
/*     */     }
/*     */     
/*     */     public void run() {
/* 429 */       if (this.update) {
/* 430 */         NineHack.Globals.mc.field_71442_b.func_78765_e();
/*     */       }
/* 432 */       if (this.slot != -1) {
/* 433 */         NineHack.Globals.mc.field_71442_b.func_187098_a(0, this.slot, 0, this.quickClick ? ClickType.QUICK_MOVE : ClickType.PICKUP, (EntityPlayer)NineHack.Globals.mc.field_71439_g);
/*     */       }
/*     */     }
/*     */     
/*     */     public boolean isSwitching() {
/* 438 */       return !this.update;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\InventoryUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */