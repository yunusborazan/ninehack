/*     */ package me.ninethousand.ninehack.util;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import me.ninethousand.ninehack.NineHack;
/*     */ import me.ninethousand.ninehack.mixin.accessors.game.IRenderManager;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntityIronGolem;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketEntityAction;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.MovementInput;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
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
/*     */ public class EntityUtil
/*     */   implements NineHack.Globals
/*     */ {
/*  47 */   public static final Vec3d[] antiDropOffsetList = new Vec3d[] { new Vec3d(0.0D, -2.0D, 0.0D) };
/*  48 */   public static final Vec3d[] platformOffsetList = new Vec3d[] { new Vec3d(0.0D, -1.0D, 0.0D), new Vec3d(0.0D, -1.0D, -1.0D), new Vec3d(0.0D, -1.0D, 1.0D), new Vec3d(-1.0D, -1.0D, 0.0D), new Vec3d(1.0D, -1.0D, 0.0D) };
/*  49 */   public static final Vec3d[] legOffsetList = new Vec3d[] { new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(0.0D, 0.0D, 1.0D) };
/*  50 */   public static final Vec3d[] OffsetList = new Vec3d[] { new Vec3d(1.0D, 1.0D, 0.0D), new Vec3d(-1.0D, 1.0D, 0.0D), new Vec3d(0.0D, 1.0D, 1.0D), new Vec3d(0.0D, 1.0D, -1.0D), new Vec3d(0.0D, 2.0D, 0.0D) };
/*  51 */   public static final Vec3d[] antiStepOffsetList = new Vec3d[] { new Vec3d(-1.0D, 2.0D, 0.0D), new Vec3d(1.0D, 2.0D, 0.0D), new Vec3d(0.0D, 2.0D, 1.0D), new Vec3d(0.0D, 2.0D, -1.0D) };
/*  52 */   public static final Vec3d[] antiScaffoldOffsetList = new Vec3d[] { new Vec3d(0.0D, 3.0D, 0.0D) };
/*  53 */   public static final Vec3d[] doubleLegOffsetList = new Vec3d[] { new Vec3d(-1.0D, 0.0D, 0.0D), new Vec3d(1.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -1.0D), new Vec3d(0.0D, 0.0D, 1.0D), new Vec3d(-2.0D, 0.0D, 0.0D), new Vec3d(2.0D, 0.0D, 0.0D), new Vec3d(0.0D, 0.0D, -2.0D), new Vec3d(0.0D, 0.0D, 2.0D) };
/*     */   
/*     */   public static void attackEntity(Entity entity, boolean packet, boolean swingArm) {
/*  56 */     if (packet) {
/*  57 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketUseEntity(entity));
/*     */     } else {
/*  59 */       mc.field_71442_b.func_78764_a((EntityPlayer)mc.field_71439_g, entity);
/*     */     } 
/*  61 */     if (swingArm) {
/*  62 */       mc.field_71439_g.func_184609_a(EnumHand.MAIN_HAND);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Vec3d interpolateEntity(Entity entity, float time) {
/*  67 */     return new Vec3d(entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * time, entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * time, entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * time);
/*     */   }
/*     */   
/*     */   public static Vec3d getInterpolatedPos(Entity entity, float partialTicks) {
/*  71 */     return (new Vec3d(entity.field_70142_S, entity.field_70137_T, entity.field_70136_U)).func_178787_e(getInterpolatedAmount(entity, partialTicks));
/*     */   }
/*     */   
/*     */   public static Vec3d getInterpolatedRenderPos(Entity entity, float partialTicks) {
/*  75 */     return getInterpolatedPos(entity, partialTicks).func_178786_a(((IRenderManager)mc.func_175598_ae()).getRenderPosX(), ((IRenderManager)mc.func_175598_ae()).getRenderPosY(), ((IRenderManager)mc.func_175598_ae()).getRenderPosZ());
/*     */   }
/*     */   
/*     */   public static Vec3d getInterpolatedRenderPos(Vec3d vec) {
/*  79 */     return (new Vec3d(vec.field_72450_a, vec.field_72448_b, vec.field_72449_c)).func_178786_a(((IRenderManager)mc.func_175598_ae()).getRenderPosX(), ((IRenderManager)mc.func_175598_ae()).getRenderPosY(), ((IRenderManager)mc.func_175598_ae()).getRenderPosZ());
/*     */   }
/*     */   
/*     */   public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
/*  83 */     return new Vec3d((entity.field_70165_t - entity.field_70142_S) * x, (entity.field_70163_u - entity.field_70137_T) * y, (entity.field_70161_v - entity.field_70136_U) * z);
/*     */   }
/*     */   
/*     */   public static Vec3d getInterpolatedAmount(Entity entity, Vec3d vec) {
/*  87 */     return getInterpolatedAmount(entity, vec.field_72450_a, vec.field_72448_b, vec.field_72449_c);
/*     */   }
/*     */   
/*     */   public static Vec3d getInterpolatedAmount(Entity entity, float partialTicks) {
/*  91 */     return getInterpolatedAmount(entity, partialTicks, partialTicks, partialTicks);
/*     */   }
/*     */   
/*     */   public static double getBaseMoveSpeed() {
/*  95 */     double baseSpeed = 0.2873D;
/*  96 */     if (mc.field_71439_g != null && mc.field_71439_g.func_70644_a(Potion.func_188412_a(1))) {
/*  97 */       int amplifier = mc.field_71439_g.func_70660_b(Potion.func_188412_a(1)).func_76458_c();
/*  98 */       baseSpeed *= 1.0D + 0.2D * (amplifier + 1);
/*     */     } 
/* 100 */     return baseSpeed;
/*     */   }
/*     */   
/*     */   public static boolean isPassive(Entity entity) {
/* 104 */     if (entity instanceof EntityWolf && ((EntityWolf)entity).func_70919_bu()) {
/* 105 */       return false;
/*     */     }
/* 107 */     if (entity instanceof net.minecraft.entity.EntityAgeable || entity instanceof net.minecraft.entity.passive.EntityAmbientCreature || entity instanceof net.minecraft.entity.passive.EntitySquid) {
/* 108 */       return true;
/*     */     }
/* 110 */     return (entity instanceof EntityIronGolem && ((EntityIronGolem)entity).func_70643_av() == null);
/*     */   }
/*     */   
/*     */   public static boolean isSafe(Entity entity, int height, boolean floor, boolean face) {
/* 114 */     return (getUnsafeBlocks(entity, height, floor).size() == 0);
/*     */   }
/*     */   
/*     */   public static boolean stopSneaking(boolean isSneaking) {
/* 118 */     if (isSneaking && mc.field_71439_g != null) {
/* 119 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketEntityAction((Entity)mc.field_71439_g, CPacketEntityAction.Action.STOP_SNEAKING));
/*     */     }
/* 121 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isSafe(Entity entity) {
/* 125 */     return isSafe(entity, 0, false, true);
/*     */   }
/*     */   
/*     */   public static BlockPos getPlayerPos(EntityPlayer player) {
/* 129 */     return new BlockPos(Math.floor(player.field_70165_t), Math.floor(player.field_70163_u), Math.floor(player.field_70161_v));
/*     */   }
/*     */   
/*     */   public static List<Vec3d> getUnsafeBlocks(Entity entity, int height, boolean floor) {
/* 133 */     return getUnsafeBlocksFromVec3d(entity.func_174791_d(), height, floor);
/*     */   }
/*     */   
/*     */   public static boolean isMobAggressive(Entity entity) {
/* 137 */     if (entity instanceof EntityPigZombie) {
/* 138 */       if (((EntityPigZombie)entity).func_184734_db() || ((EntityPigZombie)entity).func_175457_ck()) {
/* 139 */         return true;
/*     */       }
/*     */     } else {
/* 142 */       if (entity instanceof EntityWolf) {
/* 143 */         return (((EntityWolf)entity).func_70919_bu() && !mc.field_71439_g.equals(((EntityWolf)entity).func_70902_q()));
/*     */       }
/* 145 */       if (entity instanceof EntityEnderman) {
/* 146 */         return ((EntityEnderman)entity).func_70823_r();
/*     */       }
/*     */     } 
/* 149 */     return isHostileMob(entity);
/*     */   }
/*     */   
/*     */   public static boolean isNeutralMob(Entity entity) {
/* 153 */     return (entity instanceof EntityPigZombie || entity instanceof EntityWolf || entity instanceof EntityEnderman);
/*     */   }
/*     */   
/*     */   public static boolean isProjectile(Entity entity) {
/* 157 */     return (entity instanceof net.minecraft.entity.projectile.EntityShulkerBullet || entity instanceof net.minecraft.entity.projectile.EntityFireball);
/*     */   }
/*     */   
/*     */   public static boolean isVehicle(Entity entity) {
/* 161 */     return (entity instanceof net.minecraft.entity.item.EntityBoat || entity instanceof net.minecraft.entity.item.EntityMinecart);
/*     */   }
/*     */   
/*     */   public static boolean isFriendlyMob(Entity entity) {
/* 165 */     return ((entity.isCreatureType(EnumCreatureType.CREATURE, false) && !isNeutralMob(entity)) || entity.isCreatureType(EnumCreatureType.AMBIENT, false) || entity instanceof net.minecraft.entity.passive.EntityVillager || entity instanceof EntityIronGolem || (isNeutralMob(entity) && !isMobAggressive(entity)));
/*     */   }
/*     */   
/*     */   public static boolean isHostileMob(Entity entity) {
/* 169 */     return (entity.isCreatureType(EnumCreatureType.MONSTER, false) && !isNeutralMob(entity));
/*     */   }
/*     */   
/*     */   public static List<Vec3d> getUnsafeBlocksFromVec3d(Vec3d pos, int height, boolean floor) {
/* 173 */     ArrayList<Vec3d> vec3ds = new ArrayList<>();
/* 174 */     for (Vec3d vector : getOffsets(height, floor)) {
/* 175 */       BlockPos targetPos = (new BlockPos(pos)).func_177963_a(vector.field_72450_a, vector.field_72448_b, vector.field_72449_c);
/* 176 */       Block block = mc.field_71441_e.func_180495_p(targetPos).func_177230_c();
/* 177 */       if (block instanceof net.minecraft.block.BlockAir || block instanceof net.minecraft.block.BlockLiquid || block instanceof net.minecraft.block.BlockTallGrass || block instanceof net.minecraft.block.BlockFire || block instanceof net.minecraft.block.BlockDeadBush || block instanceof net.minecraft.block.BlockSnow)
/*     */       {
/* 179 */         vec3ds.add(vector); } 
/*     */     } 
/* 181 */     return vec3ds;
/*     */   }
/*     */   
/*     */   public static boolean isInHole(Entity entity) {
/* 185 */     return isBlockValid(new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v));
/*     */   }
/*     */   
/*     */   public static boolean isBlockValid(BlockPos blockPos) {
/* 189 */     return (isBedrockHole(blockPos) || isObbyHole(blockPos) || isBothHole(blockPos));
/*     */   } public static boolean isObbyHole(BlockPos blockPos) { BlockPos[] touchingBlocks;
/*     */     BlockPos[] arrayOfBlockPos1;
/*     */     int i;
/*     */     byte b;
/* 194 */     for (arrayOfBlockPos1 = touchingBlocks = new BlockPos[] { blockPos.func_177978_c(), blockPos.func_177968_d(), blockPos.func_177974_f(), blockPos.func_177976_e(), blockPos.func_177977_b() }, i = arrayOfBlockPos1.length, b = 0; b < i; ) { BlockPos pos = arrayOfBlockPos1[b];
/* 195 */       IBlockState touchingState = mc.field_71441_e.func_180495_p(pos);
/* 196 */       if (touchingState.func_177230_c() != Blocks.field_150350_a && touchingState.func_177230_c() == Blocks.field_150343_Z) { b++; continue; }
/* 197 */        return false; }
/*     */     
/* 199 */     return true; }
/*     */   public static boolean isBedrockHole(BlockPos blockPos) { BlockPos[] touchingBlocks;
/*     */     BlockPos[] arrayOfBlockPos1;
/*     */     int i;
/*     */     byte b;
/* 204 */     for (arrayOfBlockPos1 = touchingBlocks = new BlockPos[] { blockPos.func_177978_c(), blockPos.func_177968_d(), blockPos.func_177974_f(), blockPos.func_177976_e(), blockPos.func_177977_b() }, i = arrayOfBlockPos1.length, b = 0; b < i; ) { BlockPos pos = arrayOfBlockPos1[b];
/* 205 */       IBlockState touchingState = mc.field_71441_e.func_180495_p(pos);
/* 206 */       if (touchingState.func_177230_c() != Blocks.field_150350_a && touchingState.func_177230_c() == Blocks.field_150357_h) { b++; continue; }
/* 207 */        return false; }
/*     */     
/* 209 */     return true; } public static boolean isBothHole(BlockPos blockPos) {
/*     */     BlockPos[] touchingBlocks;
/*     */     BlockPos[] arrayOfBlockPos1;
/*     */     int i;
/*     */     byte b;
/* 214 */     for (arrayOfBlockPos1 = touchingBlocks = new BlockPos[] { blockPos.func_177978_c(), blockPos.func_177968_d(), blockPos.func_177974_f(), blockPos.func_177976_e(), blockPos.func_177977_b() }, i = arrayOfBlockPos1.length, b = 0; b < i; ) { BlockPos pos = arrayOfBlockPos1[b];
/* 215 */       IBlockState touchingState = mc.field_71441_e.func_180495_p(pos);
/* 216 */       if (touchingState.func_177230_c() != Blocks.field_150350_a && (touchingState.func_177230_c() == Blocks.field_150357_h || touchingState.func_177230_c() == Blocks.field_150343_Z)) {
/*     */         b++; continue;
/* 218 */       }  return false; }
/*     */     
/* 220 */     return true;
/*     */   }
/*     */   
/*     */   public static Vec3d[] getUnsafeBlockArray(Entity entity, int height, boolean floor) {
/* 224 */     List<Vec3d> list = getUnsafeBlocks(entity, height, floor);
/* 225 */     Vec3d[] array = new Vec3d[list.size()];
/* 226 */     return list.<Vec3d>toArray(array);
/*     */   }
/*     */   
/*     */   public static Vec3d[] getUnsafeBlockArrayFromVec3d(Vec3d pos, int height, boolean floor) {
/* 230 */     List<Vec3d> list = getUnsafeBlocksFromVec3d(pos, height, floor);
/* 231 */     Vec3d[] array = new Vec3d[list.size()];
/* 232 */     return list.<Vec3d>toArray(array);
/*     */   }
/*     */   
/*     */   public static double getDst(Vec3d vec) {
/* 236 */     return mc.field_71439_g.func_174791_d().func_72438_d(vec);
/*     */   }
/*     */   
/*     */   public static boolean isTrapped(EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
/* 240 */     return (getUntrappedBlocks(player, antiScaffold, antiStep, legs, platform, antiDrop).size() == 0);
/*     */   }
/*     */   
/*     */   public static boolean isTrappedExtended(int extension, EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop, boolean raytrace) {
/* 244 */     return (getUntrappedBlocksExtended(extension, player, antiScaffold, antiStep, legs, platform, antiDrop, raytrace).size() == 0);
/*     */   }
/*     */   
/*     */   public static List<Vec3d> getUntrappedBlocks(EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
/* 248 */     ArrayList<Vec3d> vec3ds = new ArrayList<>();
/* 249 */     if (!antiStep && getUnsafeBlocks((Entity)player, 2, false).size() == 4) {
/* 250 */       vec3ds.addAll(getUnsafeBlocks((Entity)player, 2, false));
/*     */     }
/* 252 */     for (int i = 0; i < (getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop)).length; i++) {
/* 253 */       Vec3d vector = getTrapOffsets(antiScaffold, antiStep, legs, platform, antiDrop)[i];
/* 254 */       BlockPos targetPos = (new BlockPos(player.func_174791_d())).func_177963_a(vector.field_72450_a, vector.field_72448_b, vector.field_72449_c);
/* 255 */       Block block = mc.field_71441_e.func_180495_p(targetPos).func_177230_c();
/* 256 */       if (block instanceof net.minecraft.block.BlockAir || block instanceof net.minecraft.block.BlockLiquid || block instanceof net.minecraft.block.BlockTallGrass || block instanceof net.minecraft.block.BlockFire || block instanceof net.minecraft.block.BlockDeadBush || block instanceof net.minecraft.block.BlockSnow)
/*     */       {
/* 258 */         vec3ds.add(vector); } 
/*     */     } 
/* 260 */     return vec3ds;
/*     */   }
/*     */   
/*     */   public static boolean isInWater(Entity entity) {
/* 264 */     if (entity == null) {
/* 265 */       return false;
/*     */     }
/* 267 */     double y = entity.field_70163_u + 0.01D;
/* 268 */     for (int x = MathHelper.func_76128_c(entity.field_70165_t); x < MathHelper.func_76143_f(entity.field_70165_t); x++) {
/* 269 */       for (int z = MathHelper.func_76128_c(entity.field_70161_v); z < MathHelper.func_76143_f(entity.field_70161_v); ) {
/* 270 */         BlockPos pos = new BlockPos(x, (int)y, z);
/* 271 */         if (!(mc.field_71441_e.func_180495_p(pos).func_177230_c() instanceof net.minecraft.block.BlockLiquid)) { z++; continue; }
/* 272 */          return true;
/*     */       } 
/*     */     } 
/* 275 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isDrivenByPlayer(Entity entityIn) {
/* 279 */     return (mc.field_71439_g != null && entityIn != null && entityIn.equals(mc.field_71439_g.func_184187_bx()));
/*     */   }
/*     */   
/*     */   public static boolean isPlayer(Entity entity) {
/* 283 */     return entity instanceof EntityPlayer;
/*     */   }
/*     */   
/*     */   public static boolean isAboveWater(Entity entity) {
/* 287 */     return isAboveWater(entity, false);
/*     */   }
/*     */   
/*     */   public static boolean isAboveWater(Entity entity, boolean packet) {
/* 291 */     if (entity == null) {
/* 292 */       return false;
/*     */     }
/* 294 */     double y = entity.field_70163_u - (packet ? 0.03D : (isPlayer(entity) ? 0.2D : 0.5D));
/* 295 */     for (int x = MathHelper.func_76128_c(entity.field_70165_t); x < MathHelper.func_76143_f(entity.field_70165_t); x++) {
/* 296 */       for (int z = MathHelper.func_76128_c(entity.field_70161_v); z < MathHelper.func_76143_f(entity.field_70161_v); ) {
/* 297 */         BlockPos pos = new BlockPos(x, MathHelper.func_76128_c(y), z);
/* 298 */         if (!(mc.field_71441_e.func_180495_p(pos).func_177230_c() instanceof net.minecraft.block.BlockLiquid)) { z++; continue; }
/* 299 */          return true;
/*     */       } 
/*     */     } 
/* 302 */     return false;
/*     */   }
/*     */   public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
/* 305 */     double dirx = me.field_70165_t - px;
/* 306 */     double diry = me.field_70163_u - py;
/* 307 */     double dirz = me.field_70161_v - pz;
/* 308 */     double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
/* 309 */     dirx /= len;
/* 310 */     diry /= len;
/* 311 */     dirz /= len;
/* 312 */     double pitch = Math.asin(diry);
/* 313 */     double yaw = Math.atan2(dirz, dirx);
/* 314 */     pitch = pitch * 180.0D / Math.PI;
/* 315 */     yaw = yaw * 180.0D / Math.PI;
/* 316 */     yaw += 90.0D;
/* 317 */     return new double[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static List<Vec3d> getUntrappedBlocksExtended(int extension, EntityPlayer player, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop, boolean raytrace) {
/* 321 */     ArrayList<Vec3d> placeTargets = new ArrayList<>();
/* 322 */     if (extension == 1) {
/* 323 */       placeTargets.addAll(targets(player.func_174791_d(), antiScaffold, antiStep, legs, platform, antiDrop, raytrace));
/*     */     } else {
/* 325 */       int extend = 1;
/* 326 */       for (Vec3d vec3d : MathsUtil.getBlockBlocks((Entity)player)) {
/* 327 */         if (extend > extension)
/* 328 */           break;  placeTargets.addAll(targets(vec3d, antiScaffold, antiStep, legs, platform, antiDrop, raytrace));
/* 329 */         extend++;
/*     */       } 
/*     */     } 
/* 332 */     ArrayList<Vec3d> removeList = new ArrayList<>();
/* 333 */     for (Vec3d vec3d : placeTargets) {
/* 334 */       BlockPos pos = new BlockPos(vec3d);
/* 335 */       if (BlockUtil.isPositionPlaceable(pos, raytrace) != -1)
/* 336 */         continue;  removeList.add(vec3d);
/*     */     } 
/* 338 */     for (Vec3d vec3d : removeList) {
/* 339 */       placeTargets.remove(vec3d);
/*     */     }
/* 341 */     return placeTargets;
/*     */   }
/*     */   
/*     */   public static List<Vec3d> targets(Vec3d vec3d, boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop, boolean raytrace) {
/* 345 */     ArrayList<Vec3d> placeTargets = new ArrayList<>();
/* 346 */     if (antiDrop) {
/* 347 */       Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiDropOffsetList));
/*     */     }
/* 349 */     if (platform) {
/* 350 */       Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, platformOffsetList));
/*     */     }
/* 352 */     if (legs) {
/* 353 */       Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, legOffsetList));
/*     */     }
/* 355 */     Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, OffsetList));
/* 356 */     if (antiStep) {
/* 357 */       Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiStepOffsetList));
/*     */     } else {
/* 359 */       List<Vec3d> vec3ds = getUnsafeBlocksFromVec3d(vec3d, 2, false);
/* 360 */       if (vec3ds.size() == 4)
/*     */       {
/* 362 */         for (Vec3d vector : vec3ds) {
/* 363 */           BlockPos position = (new BlockPos(vec3d)).func_177963_a(vector.field_72450_a, vector.field_72448_b, vector.field_72449_c);
/* 364 */           switch (BlockUtil.isPositionPlaceable(position, raytrace)) {
/*     */             case 0:
/*     */               break;
/*     */             
/*     */             case -1:
/*     */             case 1:
/*     */             case 2:
/*     */               continue;
/*     */             
/*     */             case 3:
/* 374 */               placeTargets.add(vec3d.func_178787_e(vector)); break;
/*     */             default:
/*     */               // Byte code: goto -> 234
/*     */           } 
/* 378 */           if (antiScaffold) {
/* 379 */             Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiScaffoldOffsetList));
/*     */           }
/* 381 */           return placeTargets;
/*     */         } 
/*     */       }
/*     */     } 
/* 385 */     if (antiScaffold) {
/* 386 */       Collections.addAll(placeTargets, BlockUtil.convertVec3ds(vec3d, antiScaffoldOffsetList));
/*     */     }
/* 388 */     return placeTargets;
/*     */   }
/*     */   
/*     */   public static List<Vec3d> getOffsetList(int y, boolean floor) {
/* 392 */     ArrayList<Vec3d> offsets = new ArrayList<>();
/* 393 */     offsets.add(new Vec3d(-1.0D, y, 0.0D));
/* 394 */     offsets.add(new Vec3d(1.0D, y, 0.0D));
/* 395 */     offsets.add(new Vec3d(0.0D, y, -1.0D));
/* 396 */     offsets.add(new Vec3d(0.0D, y, 1.0D));
/* 397 */     if (floor) {
/* 398 */       offsets.add(new Vec3d(0.0D, (y - 1), 0.0D));
/*     */     }
/* 400 */     return offsets;
/*     */   }
/*     */   
/*     */   public static Vec3d[] getOffsets(int y, boolean floor) {
/* 404 */     List<Vec3d> offsets = getOffsetList(y, floor);
/* 405 */     Vec3d[] array = new Vec3d[offsets.size()];
/* 406 */     return offsets.<Vec3d>toArray(array);
/*     */   }
/*     */   
/*     */   public static Vec3d[] getTrapOffsets(boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
/* 410 */     List<Vec3d> offsets = getTrapOffsetsList(antiScaffold, antiStep, legs, platform, antiDrop);
/* 411 */     Vec3d[] array = new Vec3d[offsets.size()];
/* 412 */     return offsets.<Vec3d>toArray(array);
/*     */   }
/*     */   
/*     */   public static List<Vec3d> getTrapOffsetsList(boolean antiScaffold, boolean antiStep, boolean legs, boolean platform, boolean antiDrop) {
/* 416 */     ArrayList<Vec3d> offsets = new ArrayList<>(getOffsetList(1, false));
/* 417 */     offsets.add(new Vec3d(0.0D, 2.0D, 0.0D));
/* 418 */     if (antiScaffold) {
/* 419 */       offsets.add(new Vec3d(0.0D, 3.0D, 0.0D));
/*     */     }
/* 421 */     if (antiStep) {
/* 422 */       offsets.addAll(getOffsetList(2, false));
/*     */     }
/* 424 */     if (legs) {
/* 425 */       offsets.addAll(getOffsetList(0, false));
/*     */     }
/* 427 */     if (platform) {
/* 428 */       offsets.addAll(getOffsetList(-1, false));
/* 429 */       offsets.add(new Vec3d(0.0D, -1.0D, 0.0D));
/*     */     } 
/* 431 */     if (antiDrop) {
/* 432 */       offsets.add(new Vec3d(0.0D, -2.0D, 0.0D));
/*     */     }
/* 434 */     return offsets;
/*     */   }
/*     */   
/*     */   public static Vec3d[] getHeightOffsets(int min, int max) {
/* 438 */     ArrayList<Vec3d> offsets = new ArrayList<>();
/* 439 */     for (int i = min; i <= max; i++) {
/* 440 */       offsets.add(new Vec3d(0.0D, i, 0.0D));
/*     */     }
/* 442 */     Vec3d[] array = new Vec3d[offsets.size()];
/* 443 */     return offsets.<Vec3d>toArray(array);
/*     */   }
/*     */   
/*     */   public static BlockPos getRoundedBlockPos(Entity entity) {
/* 447 */     return new BlockPos(MathsUtil.roundVec(entity.func_174791_d(), 0));
/*     */   }
/*     */   
/*     */   public static boolean isLiving(Entity entity) {
/* 451 */     return entity instanceof EntityLivingBase;
/*     */   }
/*     */   
/*     */   public static boolean isAlive(Entity entity) {
/* 455 */     return (isLiving(entity) && !entity.field_70128_L && ((EntityLivingBase)entity).func_110143_aJ() > 0.0F);
/*     */   }
/*     */   
/*     */   public static boolean isDead(Entity entity) {
/* 459 */     return !isAlive(entity);
/*     */   }
/*     */   
/*     */   public static float getHealth(Entity entity) {
/* 463 */     if (isLiving(entity)) {
/* 464 */       EntityLivingBase livingBase = (EntityLivingBase)entity;
/* 465 */       return livingBase.func_110143_aJ() + livingBase.func_110139_bj();
/*     */     } 
/* 467 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public static float getHealth(Entity entity, boolean absorption) {
/* 471 */     if (isLiving(entity)) {
/* 472 */       EntityLivingBase livingBase = (EntityLivingBase)entity;
/* 473 */       return livingBase.func_110143_aJ() + (absorption ? livingBase.func_110139_bj() : 0.0F);
/*     */     } 
/* 475 */     return 0.0F;
/*     */   }
/*     */   
/*     */   public static boolean canEntityFeetBeSeen(Entity entityIn) {
/* 479 */     return (mc.field_71441_e.func_147447_a(new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70165_t + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v), new Vec3d(entityIn.field_70165_t, entityIn.field_70163_u, entityIn.field_70161_v), false, true, false) == null);
/*     */   }
/*     */   
/*     */   public static boolean isntValid(Entity entity, double range) {
/* 483 */     return (entity == null || isDead(entity) || entity.equals(mc.field_71439_g) || mc.field_71439_g.func_70068_e(entity) > MathsUtil.square(range));
/*     */   }
/*     */   
/*     */   public static boolean isValid(Entity entity, double range) {
/* 487 */     return !isntValid(entity, range);
/*     */   }
/*     */   
/*     */   public static boolean holdingWeapon(EntityPlayer player) {
/* 491 */     return (player.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemSword || player.func_184614_ca().func_77973_b() instanceof net.minecraft.item.ItemAxe);
/*     */   }
/*     */   
/*     */   public static double getMaxSpeed() {
/* 495 */     double maxModifier = 0.2873D;
/* 496 */     if (mc.field_71439_g.func_70644_a(Objects.<Potion>requireNonNull(Potion.func_188412_a(1)))) {
/* 497 */       maxModifier *= 1.0D + 0.2D * (((PotionEffect)Objects.<PotionEffect>requireNonNull(mc.field_71439_g.func_70660_b(Objects.<Potion>requireNonNull(Potion.func_188412_a(1))))).func_76458_c() + 1);
/*     */     }
/* 499 */     return maxModifier;
/*     */   }
/*     */   
/*     */   public static void mutliplyEntitySpeed(Entity entity, double multiplier) {
/* 503 */     if (entity != null) {
/* 504 */       entity.field_70159_w *= multiplier;
/* 505 */       entity.field_70179_y *= multiplier;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isEntityMoving(Entity entity) {
/* 510 */     if (entity == null) {
/* 511 */       return false;
/*     */     }
/* 513 */     if (entity instanceof EntityPlayer) {
/* 514 */       return (mc.field_71474_y.field_74351_w.func_151470_d() || mc.field_71474_y.field_74368_y.func_151470_d() || mc.field_71474_y.field_74370_x.func_151470_d() || mc.field_71474_y.field_74366_z.func_151470_d());
/*     */     }
/* 516 */     return (entity.field_70159_w != 0.0D || entity.field_70181_x != 0.0D || entity.field_70179_y != 0.0D);
/*     */   }
/*     */   
/*     */   public static double getEntitySpeed(Entity entity) {
/* 520 */     if (entity != null) {
/* 521 */       double distTraveledLastTickX = entity.field_70165_t - entity.field_70169_q;
/* 522 */       double distTraveledLastTickZ = entity.field_70161_v - entity.field_70166_s;
/* 523 */       double speed = MathHelper.func_76133_a(distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ);
/* 524 */       return speed * 20.0D;
/*     */     } 
/* 526 */     return 0.0D;
/*     */   }
/*     */   
/*     */   public static boolean is32k(ItemStack stack) {
/* 530 */     return (EnchantmentHelper.func_77506_a(Enchantments.field_185302_k, stack) >= 1000);
/*     */   }
/*     */   
/*     */   public static void moveEntityStrafe(double speed, Entity entity) {
/* 534 */     if (entity != null) {
/* 535 */       MovementInput movementInput = mc.field_71439_g.field_71158_b;
/* 536 */       double forward = movementInput.field_192832_b;
/* 537 */       double strafe = movementInput.field_78902_a;
/* 538 */       float yaw = mc.field_71439_g.field_70177_z;
/* 539 */       if (forward == 0.0D && strafe == 0.0D) {
/* 540 */         entity.field_70159_w = 0.0D;
/* 541 */         entity.field_70179_y = 0.0D;
/*     */       } else {
/* 543 */         if (forward != 0.0D) {
/* 544 */           if (strafe > 0.0D) {
/* 545 */             yaw += ((forward > 0.0D) ? -45 : 45);
/* 546 */           } else if (strafe < 0.0D) {
/* 547 */             yaw += ((forward > 0.0D) ? 45 : -45);
/*     */           } 
/* 549 */           strafe = 0.0D;
/* 550 */           if (forward > 0.0D) {
/* 551 */             forward = 1.0D;
/* 552 */           } else if (forward < 0.0D) {
/* 553 */             forward = -1.0D;
/*     */           } 
/*     */         } 
/* 556 */         entity.field_70159_w = forward * speed * Math.cos(Math.toRadians((yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((yaw + 90.0F)));
/* 557 */         entity.field_70179_y = forward * speed * Math.sin(Math.toRadians((yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((yaw + 90.0F)));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean rayTraceHitCheck(Entity entity, boolean shouldCheck) {
/* 563 */     return (!shouldCheck || mc.field_71439_g.func_70685_l(entity));
/*     */   }
/*     */   
/*     */   public static Color getColor(Entity entity, int red, int green, int blue, int alpha, boolean colorFriends) {
/* 567 */     Color color = new Color(red / 255.0F, green / 255.0F, blue / 255.0F, alpha / 255.0F);
/* 568 */     if (entity instanceof EntityPlayer && colorFriends) {
/* 569 */       color = new Color(0.33333334F, 1.0F, 1.0F, alpha / 255.0F);
/*     */     }
/* 571 */     return color;
/*     */   }
/*     */   
/*     */   public static boolean isMoving() {
/* 575 */     return (mc.field_71439_g.field_191988_bg != 0.0D || mc.field_71439_g.field_70702_br != 0.0D);
/*     */   }
/*     */   
/*     */   public static boolean isMoving(EntityLivingBase entity) {
/* 579 */     return (entity.field_191988_bg != 0.0F || entity.field_70702_br != 0.0F);
/*     */   }
/*     */   
/*     */   public static EntityPlayer getClosestEnemy(double distance) {
/* 583 */     EntityPlayer closest = null;
/* 584 */     for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/* 585 */       if (isntValid((Entity)player, distance))
/* 586 */         continue;  if (closest == null) {
/* 587 */         closest = player;
/*     */         continue;
/*     */       } 
/* 590 */       if (mc.field_71439_g.func_70068_e((Entity)player) >= mc.field_71439_g.func_70068_e((Entity)closest))
/*     */         continue; 
/* 592 */       closest = player;
/*     */     } 
/* 594 */     return closest;
/*     */   }
/*     */   
/*     */   public static boolean checkCollide() {
/* 598 */     if (mc.field_71439_g.func_70093_af()) {
/* 599 */       return false;
/*     */     }
/* 601 */     if (mc.field_71439_g.func_184187_bx() != null && (mc.field_71439_g.func_184187_bx()).field_70143_R >= 3.0F) {
/* 602 */       return false;
/*     */     }
/* 604 */     return (mc.field_71439_g.field_70143_R < 3.0F);
/*     */   }
/*     */   
/*     */   public static BlockPos getPlayerPosWithEntity() {
/* 608 */     return new BlockPos((mc.field_71439_g.func_184187_bx() != null) ? (mc.field_71439_g.func_184187_bx()).field_70165_t : mc.field_71439_g.field_70165_t, (mc.field_71439_g.func_184187_bx() != null) ? (mc.field_71439_g.func_184187_bx()).field_70163_u : mc.field_71439_g.field_70163_u, (mc.field_71439_g.func_184187_bx() != null) ? (mc.field_71439_g.func_184187_bx()).field_70161_v : mc.field_71439_g.field_70161_v);
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
/*     */   public static double[] forward(double speed) {
/* 629 */     float forward = mc.field_71439_g.field_71158_b.field_192832_b;
/* 630 */     float side = mc.field_71439_g.field_71158_b.field_78902_a;
/* 631 */     float yaw = mc.field_71439_g.field_70126_B + (mc.field_71439_g.field_70177_z - mc.field_71439_g.field_70126_B) * mc.func_184121_ak();
/* 632 */     if (forward != 0.0F) {
/* 633 */       if (side > 0.0F) {
/* 634 */         yaw += ((forward > 0.0F) ? -45 : 45);
/* 635 */       } else if (side < 0.0F) {
/* 636 */         yaw += ((forward > 0.0F) ? 45 : -45);
/*     */       } 
/* 638 */       side = 0.0F;
/* 639 */       if (forward > 0.0F) {
/* 640 */         forward = 1.0F;
/* 641 */       } else if (forward < 0.0F) {
/* 642 */         forward = -1.0F;
/*     */       } 
/*     */     } 
/* 645 */     double sin = Math.sin(Math.toRadians((yaw + 90.0F)));
/* 646 */     double cos = Math.cos(Math.toRadians((yaw + 90.0F)));
/* 647 */     double posX = forward * speed * cos + side * speed * sin;
/* 648 */     double posZ = forward * speed * sin - side * speed * cos;
/* 649 */     return new double[] { posX, posZ };
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
/*     */   public static boolean isAboveBlock(Entity entity, BlockPos blockPos) {
/* 697 */     return (entity.field_70163_u >= blockPos.func_177956_o());
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\EntityUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */