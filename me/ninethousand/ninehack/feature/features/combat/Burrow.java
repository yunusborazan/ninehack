/*     */ package me.ninethousand.ninehack.feature.features.combat;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import me.ninethousand.ninehack.feature.Category;
/*     */ import me.ninethousand.ninehack.feature.Feature;
/*     */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*     */ import me.ninethousand.ninehack.feature.setting.NumberSetting;
/*     */ import me.ninethousand.ninehack.feature.setting.Setting;
/*     */ import me.ninethousand.ninehack.mixin.accessors.game.IMinecraft;
/*     */ import me.ninethousand.ninehack.util.ChatUtil;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockAnvil;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.block.BlockEnderChest;
/*     */ import net.minecraft.block.BlockObsidian;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ @NineHackFeature(name = "Burrow", description = "Glitch into a block", category = Category.Combat)
/*     */ public class Burrow extends Feature {
/*  38 */   public static final Setting<BlockMode> mode = new Setting("Mode", BlockMode.Obsidian); public static Feature INSTANCE;
/*  39 */   public static final NumberSetting<Double> offset = new NumberSetting("Offset", Double.valueOf(7.0D), Double.valueOf(-20.0D), Double.valueOf(20.0D), 1);
/*  40 */   public static final Setting<Boolean> rotate = new Setting("Rotate", Boolean.valueOf(false));
/*     */   
/*     */   private BlockPos originalPos;
/*  43 */   private int oldSlot = -1;
/*     */   
/*     */   public Burrow() {
/*  46 */     addSettings(new Setting[] { mode, (Setting)offset, rotate });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  57 */     this.originalPos = new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v);
/*     */     
/*  59 */     if (mc.field_71441_e.func_180495_p(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)).func_177230_c().equals(Blocks.field_150343_Z) || 
/*  60 */       intersectsWithEntity(this.originalPos)) {
/*  61 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  66 */     this.oldSlot = mc.field_71439_g.field_71071_by.field_70461_c;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  72 */     if (findHotbarBlock(((BlockMode)mode.getValue()).clazz) == -1) {
/*  73 */       ChatUtil.sendClientMessageSimple("Can't find " + ((BlockMode)mode.getValue()).clazz.getSimpleName().replaceFirst("Block", "") + " in hotbar!");
/*  74 */       disable();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  79 */     switchToSlot(findHotbarBlock(((BlockMode)mode.getValue()).clazz));
/*     */ 
/*     */     
/*  82 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.41999998688698D, mc.field_71439_g.field_70161_v, true));
/*  83 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 0.7531999805211997D, mc.field_71439_g.field_70161_v, true));
/*  84 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.00133597911214D, mc.field_71439_g.field_70161_v, true));
/*  85 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + 1.16610926093821D, mc.field_71439_g.field_70161_v, true));
/*     */ 
/*     */     
/*  88 */     placeBlock(this.originalPos, ((Boolean)rotate.getValue()).booleanValue());
/*     */ 
/*     */     
/*  91 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Position(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + ((Double)offset.getValue()).doubleValue(), mc.field_71439_g.field_70161_v, false));
/*     */ 
/*     */     
/*  94 */     switchToSlot(this.oldSlot);
/*     */ 
/*     */     
/*  97 */     disable();
/*     */   }
/*     */   
/*     */   private boolean intersectsWithEntity(BlockPos pos) {
/* 101 */     for (Entity entity : mc.field_71441_e.field_72996_f) {
/* 102 */       if (!entity.equals(mc.field_71439_g) && 
/* 103 */         !(entity instanceof net.minecraft.entity.item.EntityItem) && (
/* 104 */         new AxisAlignedBB(pos)).func_72326_a(entity.func_174813_aQ())) return true; 
/*     */     } 
/* 106 */     return false;
/*     */   }
/*     */   
/*     */   private void placeBlock(BlockPos pos, boolean rotate) {
/* 110 */     boolean sneaking = false;
/* 111 */     EnumFacing side = getFirstFacing(pos);
/* 112 */     if (side == null)
/*     */       return; 
/* 114 */     BlockPos neighbour = pos.func_177972_a(side);
/* 115 */     EnumFacing opposite = side.func_176734_d();
/*     */     
/* 117 */     Vec3d hitVec = (new Vec3d((Vec3i)neighbour)).func_72441_c(0.5D, 0.5D, 0.5D).func_178787_e((new Vec3d(opposite.func_176730_m())).func_186678_a(0.5D));
/*     */     
/* 119 */     if (!mc.field_71439_g.func_70093_af()) {
/* 120 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.START_SNEAKING));
/* 121 */       mc.field_71439_g.func_70095_a(true);
/* 122 */       sneaking = true;
/*     */     } 
/*     */     
/* 125 */     if (rotate) faceVector(hitVec, true);
/*     */     
/* 127 */     rightClickBlock(neighbour, hitVec, EnumHand.MAIN_HAND, opposite, true);
/* 128 */     mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*     */     
/* 130 */     if (sneaking) mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING)); 
/*     */   }
/*     */   
/*     */   public static List<EnumFacing> getPossibleSides(BlockPos pos) {
/* 134 */     List<EnumFacing> facings = new ArrayList<>();
/* 135 */     for (EnumFacing side : EnumFacing.values()) {
/* 136 */       BlockPos neighbour = pos.func_177972_a(side);
/* 137 */       if (mc.field_71441_e.func_180495_p(neighbour).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(neighbour), false)) {
/* 138 */         IBlockState blockState = mc.field_71441_e.func_180495_p(neighbour);
/* 139 */         if (!blockState.func_185904_a().func_76222_j()) {
/* 140 */           facings.add(side);
/*     */         }
/*     */       } 
/*     */     } 
/* 144 */     return facings;
/*     */   }
/*     */   
/*     */   public static EnumFacing getFirstFacing(BlockPos pos) {
/* 148 */     Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator(); if (iterator.hasNext()) { EnumFacing facing = iterator.next();
/* 149 */       return facing; }
/*     */     
/* 151 */     return null;
/*     */   }
/*     */   
/*     */   public static Vec3d getEyesPos() {
/* 155 */     return new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
/*     */   }
/*     */   
/*     */   public static float[] getLegitRotations(Vec3d vec) {
/* 159 */     Vec3d eyesPos = getEyesPos();
/* 160 */     double diffX = vec.field_72450_a - eyesPos.field_72450_a;
/* 161 */     double diffY = vec.field_72448_b - eyesPos.field_72448_b;
/* 162 */     double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
/* 163 */     double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
/*     */     
/* 165 */     float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
/* 166 */     float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
/*     */     
/* 168 */     return new float[] { mc.field_71439_g.field_70177_z + 
/* 169 */         MathHelper.func_76142_g(yaw - mc.field_71439_g.field_70177_z), mc.field_71439_g.field_70125_A + 
/* 170 */         MathHelper.func_76142_g(pitch - mc.field_71439_g.field_70125_A) };
/*     */   }
/*     */ 
/*     */   
/*     */   public static void faceVector(Vec3d vec, boolean normalizeAngle) {
/* 175 */     float[] rotations = getLegitRotations(vec);
/* 176 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(rotations[0], normalizeAngle ? MathHelper.func_180184_b((int)rotations[1], 360) : rotations[1], mc.field_71439_g.field_70122_E));
/*     */   }
/*     */   
/*     */   public static void rightClickBlock(BlockPos pos, Vec3d vec, EnumHand hand, EnumFacing direction, boolean packet) {
/* 180 */     if (packet) {
/* 181 */       float f = (float)(vec.field_72450_a - pos.func_177958_n());
/* 182 */       float f1 = (float)(vec.field_72448_b - pos.func_177956_o());
/* 183 */       float f2 = (float)(vec.field_72449_c - pos.func_177952_p());
/* 184 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, f, f1, f2));
/*     */     } else {
/* 186 */       mc.field_71442_b.func_187099_a(mc.field_71439_g, mc.field_71441_e, pos, direction, vec, hand);
/*     */     } 
/* 188 */     mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/* 189 */     ((IMinecraft)mc).setRightClickDelayTimer(4);
/*     */   }
/*     */   
/*     */   private int findHotbarBlock(Class<? extends Block> clazz) {
/* 193 */     for (int i = 0; i < 9; i++) {
/* 194 */       ItemStack stack = mc.field_71439_g.field_71071_by.func_70301_a(i);
/* 195 */       if (stack != ItemStack.field_190927_a) {
/*     */ 
/*     */ 
/*     */         
/* 199 */         if (clazz.isInstance(stack.func_77973_b())) {
/* 200 */           return i;
/*     */         }
/*     */         
/* 203 */         if (stack.func_77973_b() instanceof ItemBlock) {
/* 204 */           Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
/* 205 */           if (clazz.isInstance(block))
/* 206 */             return i; 
/*     */         } 
/*     */       } 
/*     */     } 
/* 210 */     return -1;
/*     */   }
/*     */   
/*     */   private void switchToSlot(int slot) {
/* 214 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
/* 215 */     mc.field_71439_g.field_71071_by.field_70461_c = slot;
/* 216 */     mc.field_71442_b.func_78765_e();
/*     */   }
/*     */   
/*     */   public enum BlockMode
/*     */   {
/* 221 */     Obsidian((String)BlockObsidian.class),
/* 222 */     EChest((String)BlockEnderChest.class),
/* 223 */     Chest((String)BlockChest.class),
/* 224 */     Anvil((String)BlockAnvil.class);
/*     */     
/*     */     private final Class<? extends Block> clazz;
/*     */     
/*     */     BlockMode(Class<? extends Block> clazz) {
/* 229 */       this.clazz = clazz;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\combat\Burrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */