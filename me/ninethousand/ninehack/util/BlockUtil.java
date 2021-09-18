/*     */ package me.ninethousand.ninehack.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ import me.ninethousand.ninehack.NineHack;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketAnimation;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockUtil implements NineHack.Globals {
/*  30 */   public static final List<Block> blackList = Arrays.asList(new Block[] { Blocks.field_150477_bB, (Block)Blocks.field_150486_ae, Blocks.field_150447_bR, Blocks.field_150462_ai, Blocks.field_150467_bQ, Blocks.field_150382_bo, (Block)Blocks.field_150438_bZ, Blocks.field_150409_cd, Blocks.field_150367_z, Blocks.field_150415_aT, Blocks.field_150381_bn });
/*  31 */   public static final List<Block> shulkerList = Arrays.asList(new Block[] { Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA });
/*  32 */   public static final List<Block> unSafeBlocks = Arrays.asList(new Block[] { Blocks.field_150343_Z, Blocks.field_150357_h, Blocks.field_150477_bB, Blocks.field_150467_bQ });
/*  33 */   public static List<Block> unSolidBlocks = Arrays.asList(new Block[] { (Block)Blocks.field_150356_k, Blocks.field_150457_bL, Blocks.field_150433_aE, Blocks.field_150404_cg, Blocks.field_185764_cQ, (Block)Blocks.field_150465_bP, Blocks.field_150457_bL, Blocks.field_150473_bD, (Block)Blocks.field_150479_bC, Blocks.field_150471_bO, Blocks.field_150442_at, Blocks.field_150430_aB, Blocks.field_150468_ap, (Block)Blocks.field_150441_bU, (Block)Blocks.field_150455_bV, (Block)Blocks.field_150413_aR, (Block)Blocks.field_150416_aS, Blocks.field_150437_az, Blocks.field_150429_aA, (Block)Blocks.field_150488_af, Blocks.field_150350_a, (Block)Blocks.field_150427_aO, Blocks.field_150384_bq, (Block)Blocks.field_150355_j, (Block)Blocks.field_150358_i, (Block)Blocks.field_150353_l, (Block)Blocks.field_150356_k, Blocks.field_150345_g, (Block)Blocks.field_150328_O, (Block)Blocks.field_150327_N, (Block)Blocks.field_150338_P, (Block)Blocks.field_150337_Q, Blocks.field_150464_aj, Blocks.field_150459_bM, Blocks.field_150469_bN, Blocks.field_185773_cZ, (Block)Blocks.field_150436_aH, Blocks.field_150393_bb, Blocks.field_150394_bc, Blocks.field_150392_bi, Blocks.field_150388_bm, Blocks.field_150375_by, Blocks.field_185766_cS, Blocks.field_185765_cR, (Block)Blocks.field_150329_H, (Block)Blocks.field_150330_I, Blocks.field_150395_bd, (Block)Blocks.field_150480_ab, Blocks.field_150448_aq, Blocks.field_150408_cc, Blocks.field_150319_E, Blocks.field_150318_D, Blocks.field_150478_aa });
/*     */   
/*     */   public static List<BlockPos> getBlockSphere(float breakRange, Class clazz) {
/*  36 */     NonNullList positions = NonNullList.func_191196_a();
/*  37 */     positions.addAll((Collection)getSphere(EntityUtil.getPlayerPos((EntityPlayer)mc.field_71439_g), breakRange, (int)breakRange, false, true, 0).stream().filter(pos -> clazz.isInstance(mc.field_71441_e.func_180495_p(pos).func_177230_c())).collect(Collectors.toList()));
/*  38 */     return (List<BlockPos>)positions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<EnumFacing> getPossibleSides(BlockPos pos) {
/*  57 */     ArrayList<EnumFacing> facings = new ArrayList<>();
/*  58 */     for (EnumFacing side : EnumFacing.values()) {
/*     */       
/*  60 */       BlockPos neighbour = pos.func_177972_a(side); IBlockState blockState;
/*  61 */       if (mc.field_71441_e.func_180495_p(neighbour).func_177230_c().func_176209_a(mc.field_71441_e.func_180495_p(neighbour), false) && !(blockState = mc.field_71441_e.func_180495_p(neighbour)).func_185904_a().func_76222_j())
/*     */       {
/*  63 */         facings.add(side); } 
/*     */     } 
/*  65 */     return facings;
/*     */   }
/*     */   
/*     */   public static EnumFacing getFirstFacing(BlockPos pos) {
/*  69 */     Iterator<EnumFacing> iterator = getPossibleSides(pos).iterator();
/*  70 */     if (iterator.hasNext()) {
/*  71 */       EnumFacing facing = iterator.next();
/*  72 */       return facing;
/*     */     } 
/*  74 */     return null;
/*     */   }
/*     */   
/*     */   public static EnumFacing getRayTraceFacing(BlockPos pos) {
/*  78 */     RayTraceResult result = mc.field_71441_e.func_72933_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d(pos.func_177958_n() + 0.5D, pos.func_177958_n() - 0.5D, pos.func_177958_n() + 0.5D));
/*  79 */     if (result == null || result.field_178784_b == null) {
/*  80 */       return EnumFacing.UP;
/*     */     }
/*  82 */     return result.field_178784_b;
/*     */   }
/*     */   
/*     */   public static int isPositionPlaceable(BlockPos pos, boolean rayTrace) {
/*  86 */     return isPositionPlaceable(pos, rayTrace, true);
/*     */   }
/*     */   
/*     */   public static int isPositionPlaceable(BlockPos pos, boolean rayTrace, boolean entityCheck) {
/*  90 */     Block block = mc.field_71441_e.func_180495_p(pos).func_177230_c();
/*  91 */     if (!(block instanceof net.minecraft.block.BlockAir) && !(block instanceof net.minecraft.block.BlockLiquid) && !(block instanceof net.minecraft.block.BlockTallGrass) && !(block instanceof net.minecraft.block.BlockFire) && !(block instanceof net.minecraft.block.BlockDeadBush) && !(block instanceof net.minecraft.block.BlockSnow)) {
/*  92 */       return 0;
/*     */     }
/*  94 */     if (!rayTracePlaceCheck(pos, rayTrace, 0.0F)) {
/*  95 */       return -1;
/*     */     }
/*  97 */     if (entityCheck) {
/*  98 */       for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(pos))) {
/*  99 */         if (entity instanceof net.minecraft.entity.item.EntityItem || entity instanceof net.minecraft.entity.item.EntityXPOrb)
/* 100 */           continue;  return 1;
/*     */       } 
/*     */     }
/* 103 */     for (EnumFacing side : getPossibleSides(pos)) {
/* 104 */       if (!canBeClicked(pos.func_177972_a(side)))
/* 105 */         continue;  return 3;
/*     */     } 
/* 107 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3d[] getHelpingBlocks(Vec3d vec3d) {
/* 201 */     return new Vec3d[] { new Vec3d(vec3d.field_72450_a, vec3d.field_72448_b - 1.0D, vec3d.field_72449_c), new Vec3d((vec3d.field_72450_a != 0.0D) ? (vec3d.field_72450_a * 2.0D) : vec3d.field_72450_a, vec3d.field_72448_b, (vec3d.field_72450_a != 0.0D) ? vec3d.field_72449_c : (vec3d.field_72449_c * 2.0D)), new Vec3d((vec3d.field_72450_a == 0.0D) ? (vec3d.field_72450_a + 1.0D) : vec3d.field_72450_a, vec3d.field_72448_b, (vec3d.field_72450_a == 0.0D) ? vec3d.field_72449_c : (vec3d.field_72449_c + 1.0D)), new Vec3d((vec3d.field_72450_a == 0.0D) ? (vec3d.field_72450_a - 1.0D) : vec3d.field_72450_a, vec3d.field_72448_b, (vec3d.field_72450_a == 0.0D) ? vec3d.field_72449_c : (vec3d.field_72449_c - 1.0D)), new Vec3d(vec3d.field_72450_a, vec3d.field_72448_b + 1.0D, vec3d.field_72449_c) };
/*     */   }
/*     */   
/*     */   public static List<BlockPos> possiblePlacePositions(float placeRange) {
/* 205 */     NonNullList positions = NonNullList.func_191196_a();
/* 206 */     positions.addAll((Collection)getSphere(EntityUtil.getPlayerPos((EntityPlayer)mc.field_71439_g), placeRange, (int)placeRange, false, true, 0).stream().filter(BlockUtil::canPlaceCrystal).collect(Collectors.toList()));
/* 207 */     return (List<BlockPos>)positions;
/*     */   }
/*     */   
/*     */   public static List<BlockPos> getSphere(BlockPos pos, float r, int h, boolean hollow, boolean sphere, int plus_y) {
/* 211 */     ArrayList<BlockPos> circleblocks = new ArrayList<>();
/* 212 */     int cx = pos.func_177958_n();
/* 213 */     int cy = pos.func_177956_o();
/* 214 */     int cz = pos.func_177952_p();
/* 215 */     int x = cx - (int)r;
/* 216 */     while (x <= cx + r) {
/* 217 */       int z = cz - (int)r;
/* 218 */       while (z <= cz + r) {
/* 219 */         int y = sphere ? (cy - (int)r) : cy;
/*     */         while (true) {
/* 221 */           float f = y;
/* 222 */           float f2 = sphere ? (cy + r) : (cy + h);
/* 223 */           if (f >= f2)
/* 224 */             break;  double dist = ((cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? ((cy - y) * (cy - y)) : 0));
/* 225 */           if (dist < (r * r) && (!hollow || dist >= ((r - 1.0F) * (r - 1.0F)))) {
/* 226 */             BlockPos l = new BlockPos(x, y + plus_y, z);
/* 227 */             circleblocks.add(l);
/*     */           } 
/* 229 */           y++;
/*     */         } 
/* 231 */         z++;
/*     */       } 
/* 233 */       x++;
/*     */     } 
/* 235 */     return circleblocks;
/*     */   }
/*     */   
/*     */   public static boolean canPlaceCrystal(BlockPos blockPos) {
/* 239 */     BlockPos boost = blockPos.func_177982_a(0, 1, 0);
/* 240 */     BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
/*     */     try {
/* 242 */       return ((mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150357_h || mc.field_71441_e.func_180495_p(blockPos).func_177230_c() == Blocks.field_150343_Z) && mc.field_71441_e.func_180495_p(boost).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_180495_p(boost2).func_177230_c() == Blocks.field_150350_a && mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost)).isEmpty() && mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost2)).isEmpty());
/* 243 */     } catch (Exception e) {
/* 244 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<BlockPos> possiblePlacePositions(float placeRange, boolean specialEntityCheck, boolean oneDot15) {
/* 249 */     NonNullList positions = NonNullList.func_191196_a();
/* 250 */     positions.addAll((Collection)getSphere(EntityUtil.getPlayerPos((EntityPlayer)mc.field_71439_g), placeRange, (int)placeRange, false, true, 0).stream().filter(pos -> canPlaceCrystal(pos, specialEntityCheck, oneDot15)).collect(Collectors.toList()));
/* 251 */     return (List<BlockPos>)positions;
/*     */   }
/*     */   
/*     */   public static boolean canPlaceCrystal(BlockPos blockPos, boolean specialEntityCheck, boolean oneDot15) {
/* 255 */     BlockPos boost = blockPos.func_177982_a(0, 1, 0);
/* 256 */     BlockPos boost2 = blockPos.func_177982_a(0, 2, 0);
/*     */     try {
/* 258 */       if (mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150357_h && mc.field_71441_e.func_180495_p(blockPos).func_177230_c() != Blocks.field_150343_Z) {
/* 259 */         return false;
/*     */       }
/* 261 */       if ((!oneDot15 && mc.field_71441_e.func_180495_p(boost2).func_177230_c() != Blocks.field_150350_a) || mc.field_71441_e.func_180495_p(boost).func_177230_c() != Blocks.field_150350_a) {
/* 262 */         return false;
/*     */       }
/* 264 */       for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost))) {
/* 265 */         if (entity.field_70128_L || (specialEntityCheck && entity instanceof net.minecraft.entity.item.EntityEnderCrystal))
/* 266 */           continue;  return false;
/*     */       } 
/* 268 */       if (!oneDot15) {
/* 269 */         for (Entity entity : mc.field_71441_e.func_72872_a(Entity.class, new AxisAlignedBB(boost2))) {
/* 270 */           if (entity.field_70128_L || (specialEntityCheck && entity instanceof net.minecraft.entity.item.EntityEnderCrystal))
/* 271 */             continue;  return false;
/*     */         } 
/*     */       }
/* 274 */     } catch (Exception ignored) {
/* 275 */       return false;
/*     */     } 
/* 277 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean canBeClicked(BlockPos pos) {
/* 281 */     return getBlock(pos).func_176209_a(getState(pos), false);
/*     */   }
/*     */   
/*     */   private static Block getBlock(BlockPos pos) {
/* 285 */     return getState(pos).func_177230_c();
/*     */   }
/*     */   
/*     */   private static IBlockState getState(BlockPos pos) {
/* 289 */     return mc.field_71441_e.func_180495_p(pos);
/*     */   }
/*     */   
/*     */   public static boolean isBlockAboveEntitySolid(Entity entity) {
/* 293 */     if (entity != null) {
/* 294 */       BlockPos pos = new BlockPos(entity.field_70165_t, entity.field_70163_u + 2.0D, entity.field_70161_v);
/* 295 */       return isBlockSolid(pos);
/*     */     } 
/* 297 */     return false;
/*     */   }
/*     */   
/*     */   public static void placeCrystalOnBlock(BlockPos pos, EnumHand hand, boolean swing, boolean exactHand) {
/* 301 */     RayTraceResult result = mc.field_71441_e.func_72933_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d(pos.func_177958_n() + 0.5D, pos.func_177956_o() - 0.5D, pos.func_177952_p() + 0.5D));
/* 302 */     EnumFacing facing = (result == null || result.field_178784_b == null) ? EnumFacing.UP : result.field_178784_b;
/* 303 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItemOnBlock(pos, facing, hand, 0.0F, 0.0F, 0.0F));
/* 304 */     if (swing) {
/* 305 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketAnimation(exactHand ? hand : EnumHand.MAIN_HAND));
/*     */     }
/*     */   }
/*     */   
/*     */   public static BlockPos[] toBlockPos(Vec3d[] vec3ds) {
/* 310 */     BlockPos[] list = new BlockPos[vec3ds.length];
/* 311 */     for (int i = 0; i < vec3ds.length; i++) {
/* 312 */       list[i] = new BlockPos(vec3ds[i]);
/*     */     }
/* 314 */     return list;
/*     */   }
/*     */   
/*     */   public static Vec3d posToVec3d(BlockPos pos) {
/* 318 */     return new Vec3d((Vec3i)pos);
/*     */   }
/*     */   
/*     */   public static BlockPos vec3dToPos(Vec3d vec3d) {
/* 322 */     return new BlockPos(vec3d);
/*     */   }
/*     */   
/*     */   public static Boolean isPosInFov(BlockPos pos) {
/* 326 */     int dirnumber = RotationUtil.getDirection4D();
/* 327 */     if (dirnumber == 0 && pos.func_177952_p() - (mc.field_71439_g.func_174791_d()).field_72449_c < 0.0D) {
/* 328 */       return Boolean.valueOf(false);
/*     */     }
/* 330 */     if (dirnumber == 1 && pos.func_177958_n() - (mc.field_71439_g.func_174791_d()).field_72450_a > 0.0D) {
/* 331 */       return Boolean.valueOf(false);
/*     */     }
/* 333 */     if (dirnumber == 2 && pos.func_177952_p() - (mc.field_71439_g.func_174791_d()).field_72449_c > 0.0D) {
/* 334 */       return Boolean.valueOf(false);
/*     */     }
/* 336 */     return Boolean.valueOf((dirnumber != 3 || pos.func_177958_n() - (mc.field_71439_g.func_174791_d()).field_72450_a >= 0.0D));
/*     */   }
/*     */   
/*     */   public static boolean isBlockBelowEntitySolid(Entity entity) {
/* 340 */     if (entity != null) {
/* 341 */       BlockPos pos = new BlockPos(entity.field_70165_t, entity.field_70163_u - 1.0D, entity.field_70161_v);
/* 342 */       return isBlockSolid(pos);
/*     */     } 
/* 344 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isBlockSolid(BlockPos pos) {
/* 348 */     return !isBlockUnSolid(pos);
/*     */   }
/*     */   
/*     */   public static boolean isBlockUnSolid(BlockPos pos) {
/* 352 */     return isBlockUnSolid(mc.field_71441_e.func_180495_p(pos).func_177230_c());
/*     */   }
/*     */   
/*     */   public static boolean isBlockUnSolid(Block block) {
/* 356 */     return unSolidBlocks.contains(block);
/*     */   }
/*     */   
/*     */   public static boolean isBlockUnSafe(Block block) {
/* 360 */     return unSafeBlocks.contains(block);
/*     */   }
/*     */   
/*     */   public static Vec3d[] convertVec3ds(Vec3d vec3d, Vec3d[] input) {
/* 364 */     Vec3d[] output = new Vec3d[input.length];
/* 365 */     for (int i = 0; i < input.length; i++) {
/* 366 */       output[i] = vec3d.func_178787_e(input[i]);
/*     */     }
/* 368 */     return output;
/*     */   }
/*     */   
/*     */   public static Vec3d[] convertVec3ds(EntityPlayer entity, Vec3d[] input) {
/* 372 */     return convertVec3ds(entity.func_174791_d(), input);
/*     */   }
/*     */   
/*     */   public static boolean canBreak(BlockPos pos) {
/* 376 */     IBlockState blockState = mc.field_71441_e.func_180495_p(pos);
/* 377 */     Block block = blockState.func_177230_c();
/* 378 */     return (block.func_176195_g(blockState, (World)mc.field_71441_e, pos) != -1.0F);
/*     */   }
/*     */   
/*     */   public static boolean isValidBlock(BlockPos pos) {
/* 382 */     Block block = mc.field_71441_e.func_180495_p(pos).func_177230_c();
/* 383 */     return (!(block instanceof net.minecraft.block.BlockLiquid) && block.func_149688_o(null) != Material.field_151579_a);
/*     */   }
/*     */   
/*     */   public static boolean isScaffoldPos(BlockPos pos) {
/* 387 */     return (mc.field_71441_e.func_175623_d(pos) || mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150431_aC || mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150329_H || mc.field_71441_e.func_180495_p(pos).func_177230_c() instanceof net.minecraft.block.BlockLiquid);
/*     */   }
/*     */   
/*     */   public static boolean rayTracePlaceCheck(BlockPos pos, boolean shouldCheck, float height) {
/* 391 */     return (!shouldCheck || mc.field_71441_e.func_147447_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d(pos.func_177958_n(), (pos.func_177956_o() + height), pos.func_177952_p()), false, true, false) == null);
/*     */   }
/*     */   
/*     */   public static boolean rayTracePlaceCheck(BlockPos pos, boolean shouldCheck) {
/* 395 */     return rayTracePlaceCheck(pos, shouldCheck, 1.0F);
/*     */   }
/*     */   
/*     */   public static boolean rayTracePlaceCheck(BlockPos pos) {
/* 399 */     return rayTracePlaceCheck(pos, true);
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\BlockUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */