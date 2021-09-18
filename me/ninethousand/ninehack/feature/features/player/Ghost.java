/*     */ package me.ninethousand.ninehack.feature.features.player;
/*     */ 
/*     */ import me.ninethousand.ninehack.event.events.MoveEvent;
/*     */ import me.ninethousand.ninehack.event.events.PacketEvent;
/*     */ import me.ninethousand.ninehack.feature.Category;
/*     */ import me.ninethousand.ninehack.feature.Feature;
/*     */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*     */ import me.ninethousand.ninehack.feature.setting.NumberSetting;
/*     */ import me.ninethousand.ninehack.feature.setting.Setting;
/*     */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ @NineHackFeature(name = "Ghostcam", description = "Become caspar (freecam)", category = Category.Player)
/*     */ public class Ghost
/*     */   extends Feature {
/*     */   public static Feature INSTANCE;
/*  21 */   public NumberSetting<Double> speed = new NumberSetting("Speed", Double.valueOf(0.1D), Double.valueOf(1.0D), Double.valueOf(3.0D), 1);
/*  22 */   public Setting<Boolean> packet = new Setting("Cancel Packet", Boolean.valueOf(false));
/*     */   private double posX;
/*     */   private double posY;
/*     */   private double posZ;
/*     */   private float pitch;
/*     */   private float yaw;
/*     */   private EntityOtherPlayerMP clonedPlayer;
/*     */   private boolean isRidingEntity;
/*     */   private Entity ridingEntity;
/*     */   
/*     */   public Ghost() {
/*  33 */     addSettings(new Setting[] { this.packet, (Setting)this.speed });
/*     */   }
/*     */   
/*     */   private void setInstance() {
/*  37 */     INSTANCE = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  42 */     if (mc.field_71439_g != null) {
/*  43 */       this.isRidingEntity = (mc.field_71439_g.func_184187_bx() != null);
/*     */       
/*  45 */       if (mc.field_71439_g.func_184187_bx() == null) {
/*  46 */         this.posX = mc.field_71439_g.field_70165_t;
/*  47 */         this.posY = mc.field_71439_g.field_70163_u;
/*  48 */         this.posZ = mc.field_71439_g.field_70161_v;
/*     */       } else {
/*  50 */         this.ridingEntity = mc.field_71439_g.func_184187_bx();
/*  51 */         mc.field_71439_g.func_184210_p();
/*     */       } 
/*     */       
/*  54 */       this.pitch = mc.field_71439_g.field_70125_A;
/*  55 */       this.yaw = mc.field_71439_g.field_70177_z;
/*     */       
/*  57 */       this.clonedPlayer = new EntityOtherPlayerMP((World)mc.field_71441_e, mc.func_110432_I().func_148256_e());
/*  58 */       this.clonedPlayer.func_82149_j((Entity)mc.field_71439_g);
/*  59 */       this.clonedPlayer.field_70759_as = mc.field_71439_g.field_70759_as;
/*  60 */       mc.field_71441_e.func_73027_a(-100, (Entity)this.clonedPlayer);
/*  61 */       mc.field_71439_g.field_71075_bZ.field_75100_b = true;
/*  62 */       mc.field_71439_g.field_71075_bZ.func_75092_a((float)(((Double)this.speed.getValue()).doubleValue() / 100.0D));
/*  63 */       mc.field_71439_g.field_70145_X = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  69 */     EntityPlayerSP entityPlayerSP = mc.field_71439_g;
/*     */     
/*  71 */     mc.field_71439_g.func_70080_a(this.posX, this.posY, this.posZ, this.yaw, this.pitch);
/*  72 */     mc.field_71441_e.func_73028_b(-100);
/*  73 */     this.clonedPlayer = null;
/*  74 */     this.posX = this.posY = this.posZ = 0.0D;
/*  75 */     this.pitch = this.yaw = 0.0F;
/*  76 */     mc.field_71439_g.field_71075_bZ.field_75100_b = false;
/*  77 */     mc.field_71439_g.field_71075_bZ.func_75092_a(0.05F);
/*  78 */     mc.field_71439_g.field_70145_X = false;
/*  79 */     mc.field_71439_g.field_70159_w = mc.field_71439_g.field_70181_x = mc.field_71439_g.field_70179_y = 0.0D;
/*     */     
/*  81 */     if (entityPlayerSP != null && this.isRidingEntity) {
/*  82 */       mc.field_71439_g.func_184205_a(this.ridingEntity, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  89 */     mc.field_71439_g.field_71075_bZ.field_75100_b = true;
/*  90 */     mc.field_71439_g.field_71075_bZ.func_75092_a((float)(((Double)this.speed.getValue()).doubleValue() / 100.0D));
/*  91 */     mc.field_71439_g.field_70145_X = true;
/*  92 */     mc.field_71439_g.field_70122_E = false;
/*  93 */     mc.field_71439_g.field_70143_R = 0.0F;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onMove(MoveEvent event) {
/*  98 */     mc.field_71439_g.field_70145_X = true;
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPlayerPushOutOfBlock(PlayerSPPushOutOfBlocksEvent event) {
/* 103 */     event.setCanceled(true);
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onPacketSend(PacketEvent event) {
/* 108 */     if ((event.getPacket() instanceof net.minecraft.network.play.client.CPacketPlayer || event.getPacket() instanceof net.minecraft.network.play.client.CPacketInput) && ((Boolean)this.packet.getValue()).booleanValue())
/* 109 */       event.setCanceled(true); 
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\player\Ghost.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */