/*     */ package me.ninethousand.ninehack.util;
/*     */ import me.ninethousand.ninehack.NineHack;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.CPacketPlayer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ 
/*     */ public class RotationUtil implements NineHack.Globals {
/*     */   public static Vec3d getEyesPos() {
/*  14 */     return new Vec3d(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + mc.field_71439_g.func_70047_e(), mc.field_71439_g.field_70161_v);
/*     */   }
/*     */   
/*     */   public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
/*  18 */     double dirx = me.field_70165_t - px;
/*  19 */     double diry = me.field_70163_u - py;
/*  20 */     double dirz = me.field_70161_v - pz;
/*  21 */     double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
/*  22 */     double pitch = Math.asin(diry /= len);
/*  23 */     double yaw = Math.atan2(dirz /= len, dirx /= len);
/*  24 */     pitch = pitch * 180.0D / Math.PI;
/*  25 */     yaw = yaw * 180.0D / Math.PI;
/*  26 */     return new double[] { yaw += 90.0D, pitch };
/*     */   }
/*     */   
/*     */   public static float[] getLegitRotations(Vec3d vec) {
/*  30 */     Vec3d eyesPos = getEyesPos();
/*  31 */     double diffX = vec.field_72450_a - eyesPos.field_72450_a;
/*  32 */     double diffY = vec.field_72448_b - eyesPos.field_72448_b;
/*  33 */     double diffZ = vec.field_72449_c - eyesPos.field_72449_c;
/*  34 */     double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
/*  35 */     float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
/*  36 */     float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
/*  37 */     return new float[] { mc.field_71439_g.field_70177_z + MathHelper.func_76142_g(yaw - mc.field_71439_g.field_70177_z), mc.field_71439_g.field_70125_A + MathHelper.func_76142_g(pitch - mc.field_71439_g.field_70125_A) };
/*     */   }
/*     */   
/*     */   public static void faceYawAndPitch(float yaw, float pitch) {
/*  41 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(yaw, pitch, mc.field_71439_g.field_70122_E));
/*     */   }
/*     */   
/*     */   public static void faceVector(Vec3d vec, boolean normalizeAngle) {
/*  45 */     float[] rotations = getLegitRotations(vec);
/*  46 */     mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayer.Rotation(rotations[0], normalizeAngle ? MathHelper.func_180184_b((int)rotations[1], 360) : rotations[1], mc.field_71439_g.field_70122_E));
/*     */   }
/*     */   
/*     */   public static void faceEntity(Entity entity) {
/*  50 */     float[] angle = MathsUtil.calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), entity.func_174824_e(mc.func_184121_ak()));
/*  51 */     faceYawAndPitch(angle[0], angle[1]);
/*     */   }
/*     */   
/*     */   public static float[] getAngle(Entity entity) {
/*  55 */     return MathsUtil.calcAngle(mc.field_71439_g.func_174824_e(mc.func_184121_ak()), entity.func_174824_e(mc.func_184121_ak()));
/*     */   }
/*     */   
/*     */   public static int getDirection4D() {
/*  59 */     return MathHelper.func_76128_c((mc.field_71439_g.field_70177_z * 4.0F / 360.0F) + 0.5D) & 0x3;
/*     */   }
/*     */   
/*     */   public static boolean isInFov(BlockPos pos) {
/*  63 */     return (pos != null && (mc.field_71439_g.func_174818_b(pos) < 4.0D || isInFov(new Vec3d((Vec3i)pos), mc.field_71439_g.func_174791_d())));
/*     */   }
/*     */   
/*     */   public static boolean isInFov(Entity entity) {
/*  67 */     return (entity != null && (mc.field_71439_g.func_70068_e(entity) < 4.0D || isInFov(entity.func_174791_d(), mc.field_71439_g.func_174791_d())));
/*     */   }
/*     */   
/*     */   public static boolean isInFov(Vec3d vec3d, Vec3d other) {
/*  71 */     if (mc.field_71439_g.field_70125_A > 30.0F) {
/*  72 */       if (other.field_72448_b > mc.field_71439_g.field_70163_u) {
/*  73 */         return true;
/*     */       }
/*     */     }
/*  76 */     else if (mc.field_71439_g.field_70125_A < -30.0F && other.field_72448_b < mc.field_71439_g.field_70163_u) {
/*  77 */       return true;
/*     */     } 
/*  79 */     float angle = MathsUtil.calcAngleNoY(vec3d, other)[0] - transformYaw();
/*  80 */     if (angle < -270.0F) {
/*  81 */       return true;
/*     */     }
/*  83 */     float fov = mc.field_71474_y.field_74334_X / 2.0F;
/*  84 */     return (angle < fov + 10.0F && angle > -fov - 10.0F);
/*     */   }
/*     */   public static float transformYaw() {
/*  87 */     float yaw = mc.field_71439_g.field_70177_z % 360.0F;
/*  88 */     if (mc.field_71439_g.field_70177_z > 0.0F && 
/*  89 */       yaw > 180.0F) {
/*  90 */       yaw = -180.0F + yaw - 180.0F;
/*     */     }
/*     */     
/*  93 */     return yaw;
/*     */   }
/*     */   public static String getDirection4D(boolean northRed) {
/*  96 */     int dirnumber = getDirection4D();
/*  97 */     if (dirnumber == 0) {
/*  98 */       return "South (+Z)";
/*     */     }
/* 100 */     if (dirnumber == 1) {
/* 101 */       return "West (-X)";
/*     */     }
/* 103 */     if (dirnumber == 2) {
/* 104 */       return (northRed ? "Â§c" : "") + "North (-Z)";
/*     */     }
/* 106 */     if (dirnumber == 3) {
/* 107 */       return "East (+X)";
/*     */     }
/* 109 */     return "Loading...";
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\RotationUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */