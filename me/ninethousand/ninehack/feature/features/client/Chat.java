/*     */ package me.ninethousand.ninehack.feature.features.client;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.util.HashMap;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import me.ninethousand.ninehack.event.events.DeathEvent;
/*     */ import me.ninethousand.ninehack.event.events.PacketEvent;
/*     */ import me.ninethousand.ninehack.event.events.Render2DEvent;
/*     */ import me.ninethousand.ninehack.feature.Category;
/*     */ import me.ninethousand.ninehack.feature.Feature;
/*     */ import me.ninethousand.ninehack.feature.annotation.AlwaysEnabled;
/*     */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*     */ import me.ninethousand.ninehack.feature.setting.Setting;
/*     */ import me.ninethousand.ninehack.managers.NotificationManager;
/*     */ import me.ninethousand.ninehack.util.ChatUtil;
/*     */ import me.ninethousand.ninehack.util.TextUtil;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.event.entity.player.AttackEntityEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ 
/*     */ @AlwaysEnabled
/*     */ @NineHackFeature(name = "Chat", description = "Notify you when things happen", category = Category.Client)
/*     */ public class Chat
/*     */   extends Feature
/*     */ {
/*     */   public static Chat INSTANCE;
/*  31 */   public static final Setting<String> prefixString = new Setting("Prefix String", "NineHack");
/*  32 */   public static final Setting<TextUtil.Color> prefixColor = new Setting("Prefix Color", TextUtil.Color.RAINBOW);
/*  33 */   public static final Setting<PrefixBracket> prefixBracket = new Setting("Prefix Bracket", PrefixBracket.Arrow);
/*     */   
/*  35 */   public static final Setting<Boolean> customFontChat = new Setting("Custom Chat", Boolean.valueOf(false));
/*  36 */   public static final Setting<Boolean> clear = new Setting("Clear Chatbox", Boolean.valueOf(false));
/*     */   
/*  38 */   public static final Setting<Boolean> totemPop = new Setting("Totem Pop", Boolean.valueOf(false));
/*  39 */   public static final Setting<Boolean> autoEz = new Setting("AutoEZ", Boolean.valueOf(false));
/*     */   
/*  41 */   public static final Setting<ModuleToggleMode> moduleToggle = new Setting("Module Toggle", ModuleToggleMode.Chat);
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
/*     */   public Chat() {
/*  91 */     this.targets = new ConcurrentHashMap<>();
/*     */     addSettings(new Setting[] { prefixString, prefixColor, prefixBracket, customFontChat, clear, totemPop, moduleToggle, autoEz });
/*     */   }
/*     */   public static HashMap<String, Integer> popMap = new HashMap<>(); private int delayCount;
/*  95 */   public void onEnable() { this.delayCount = 0; }
/*     */   public final ConcurrentHashMap<String, Integer> targets;
/*     */   public void onDeath(EntityPlayer player) { if (((Boolean)totemPop.getValue()).booleanValue() && popMap.containsKey(player.func_70005_c_())) { int l_Count = ((Integer)popMap.get(player.func_70005_c_())).intValue(); popMap.remove(player.func_70005_c_()); if (l_Count == 1) { ChatUtil.sendClientMessageSimple(ChatFormatting.RED + player.func_70005_c_() + " died after popping " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " totem"); } else { ChatUtil.sendClientMessageSimple(ChatFormatting.RED + player.func_70005_c_() + " died after popping " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " totems"); }  }  } public void onTotemPop(EntityPlayer player) { if (nullCheck())
/*     */       return;  if (((Boolean)totemPop.getValue()).booleanValue()) { int l_Count = 1; if (popMap.containsKey(player.func_70005_c_())) { l_Count = ((Integer)popMap.get(player.func_70005_c_())).intValue(); popMap.put(player.func_70005_c_(), Integer.valueOf(++l_Count)); } else { popMap.put(player.func_70005_c_(), Integer.valueOf(l_Count)); }  if (l_Count == 1) { ChatUtil.sendClientMessageSimple(ChatFormatting.RED + player.func_70005_c_() + " popped " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " totem"); } else { ChatUtil.sendClientMessageSimple(ChatFormatting.RED + player.func_70005_c_() + " popped " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " totems."); }
/*     */        }
/* 100 */      } public void onUpdate() { this.delayCount++;
/* 101 */     for (Entity entity : mc.field_71441_e.func_72910_y()) {
/* 102 */       if (entity instanceof EntityPlayer) {
/* 103 */         EntityPlayer player = (EntityPlayer)entity;
/* 104 */         if (player.func_110143_aJ() <= 0.0F && 
/* 105 */           this.targets.containsKey(player.func_70005_c_())) {
/* 106 */           announceDeath(player.func_70005_c_());
/* 107 */           this.targets.remove(player.func_70005_c_());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     this.targets.forEach((name, timeout) -> {
/*     */           if (timeout.intValue() <= 0) {
/*     */             this.targets.remove(name);
/*     */           } else {
/*     */             this.targets.put(name, Integer.valueOf(timeout.intValue() - 1));
/*     */           } 
/*     */         }); }
/*     */ 
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onAttackEntity(AttackEntityEvent event) {
/* 124 */     if (event.getTarget() instanceof EntityPlayer) {
/* 125 */       this.targets.put(event.getTarget().func_70005_c_(), Integer.valueOf(20));
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onSendAttackPacket(PacketEvent.Send event) {
/*     */     CPacketUseEntity packet;
/* 132 */     if (event.getPacket() instanceof CPacketUseEntity && (packet = (CPacketUseEntity)event.getPacket()).func_149565_c() == CPacketUseEntity.Action.ATTACK && packet.func_149564_a((World)mc.field_71441_e) instanceof EntityPlayer) {
/* 133 */       this.targets.put(((Entity)Objects.<Entity>requireNonNull(packet.func_149564_a((World)mc.field_71441_e))).func_70005_c_(), Integer.valueOf(20));
/*     */     }
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onEntityDeath(DeathEvent event) {
/* 139 */     if (this.targets.containsKey(event.player.func_70005_c_())) {
/* 140 */       if (((Boolean)autoEz.getValue()).booleanValue()) {
/* 141 */         announceDeath(event.player.func_70005_c_());
/*     */       }
/* 143 */       this.targets.remove(event.player.func_70005_c_());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void announceDeath(String name) {
/* 148 */     if (this.delayCount < 150)
/* 149 */       return;  this.delayCount = 0;
/* 150 */     mc.field_71439_g.func_71165_d("hahaha so ez " + name + "! next time use ninehack");
/*     */   }
/*     */ 
/*     */   
/*     */   public void on2DRenderEvent(Render2DEvent event) {
/* 155 */     NotificationManager.render();
/*     */   }
/*     */   
/*     */   public enum ModuleToggleMode {
/* 159 */     None,
/* 160 */     Chat,
/* 161 */     HUD;
/*     */   }
/*     */   
/*     */   public enum PrefixString {
/* 165 */     NineHack,
/* 166 */     Dev,
/* 167 */     CurryWare,
/* 168 */     Rwahack,
/* 169 */     Reapsense,
/* 170 */     BoboHack,
/* 171 */     x8Hack,
/* 172 */     PedroHack,
/* 173 */     MomHack,
/* 174 */     xcc7Ware,
/* 175 */     WhaleHack,
/* 176 */     Skylight,
/* 177 */     Jimboware,
/* 178 */     JoeWare; }
/*     */   
/*     */   public enum PrefixBracket {
/* 181 */     Square,
/* 182 */     Round,
/* 183 */     Curly,
/* 184 */     Arrow,
/* 185 */     Double,
/* 186 */     None;
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\client\Chat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */