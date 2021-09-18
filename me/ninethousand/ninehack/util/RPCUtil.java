/*    */ package me.ninethousand.ninehack.util;
/*    */ 
/*    */ import club.minnced.discord.rpc.DiscordEventHandlers;
/*    */ import club.minnced.discord.rpc.DiscordRPC;
/*    */ import club.minnced.discord.rpc.DiscordRichPresence;
/*    */ import java.util.Objects;
/*    */ import me.ninethousand.ninehack.NineHack;
/*    */ import me.ninethousand.ninehack.feature.features.client.RPC;
/*    */ import net.minecraft.server.integrated.IntegratedServer;
/*    */ 
/*    */ public class RPCUtil
/*    */   implements NineHack.Globals
/*    */ {
/* 14 */   private static final DiscordRPC discordRPC = DiscordRPC.INSTANCE;
/* 15 */   private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();
/*    */   
/*    */   private static String details;
/*    */   
/*    */   private static String state;
/* 20 */   public static String id = "847411619914711061";
/*    */   
/*    */   public static final void update() {
/* 23 */     if (RPC.rpcMode.getValue() == RPC.RPCMode.NineHack) {
/* 24 */       id = "847411619914711061";
/*    */       
/* 26 */       discordRichPresence.largeImageKey = "swag";
/* 27 */       discordRichPresence.largeImageText = "Version 1.0.1";
/*    */     }
/* 29 */     else if (RPC.rpcMode.getValue() == RPC.RPCMode.TuxHack) {
/* 30 */       id = "851171769850134579";
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void startup() {
/* 35 */     NineHack.log("Discord RPC Started.");
/*    */     
/* 37 */     update();
/*    */     
/* 39 */     DiscordEventHandlers handlers = new DiscordEventHandlers();
/*    */     
/* 41 */     discordRPC.Discord_Initialize(id, handlers, true, "");
/*    */     
/* 43 */     discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
/*    */     
/* 45 */     discordRPC.Discord_UpdatePresence(discordRichPresence);
/*    */     
/* 47 */     (new Thread(() -> {
/*    */           while (!Thread.currentThread().isInterrupted()) {
/*    */             try {
/*    */               if (RPC.rpcMode.getValue() == RPC.RPCMode.NineHack) {
/*    */                 details = "NineHack v1.0.1";
/*    */                 
/*    */                 state = "Main Menu";
/*    */                 
/*    */                 if (mc.func_71387_A()) {
/*    */                   state = "Singleplayer | " + ((IntegratedServer)Objects.<IntegratedServer>requireNonNull(mc.func_71401_C())).func_71221_J();
/*    */                 } else if (mc.field_71462_r instanceof net.minecraft.client.gui.GuiMultiplayer) {
/*    */                   state = "Multiplayer Menu";
/*    */                 } else if (mc.field_71462_r instanceof net.minecraft.client.gui.GuiWorldSelection) {
/*    */                   state = "Singleplayer Menu";
/*    */                 } else if (mc.func_147104_D() != null) {
/*    */                   state = "Server | " + (mc.func_147104_D()).field_78845_b.toLowerCase();
/*    */                 } 
/*    */               } else if (RPC.rpcMode.getValue() == RPC.RPCMode.TuxHack) {
/*    */                 details = state = "pvping noobs @ " + (mc.func_147104_D()).field_78845_b.toLowerCase();
/*    */                 
/*    */                 state = "TuxIsCool is based";
/*    */               } 
/*    */               
/*    */               discordRichPresence.details = details;
/*    */               
/*    */               discordRichPresence.state = state;
/*    */               discordRPC.Discord_UpdatePresence(discordRichPresence);
/* 74 */             } catch (Exception exception) {
/*    */               exception.printStackTrace();
/*    */             }  try {
/*    */               Thread.sleep(5000L);
/* 78 */             } catch (InterruptedException exception) {
/*    */               exception.printStackTrace();
/*    */             } 
/*    */           } 
/* 82 */         }"RPC-Callback-Handler")).start();
/*    */   }
/*    */   
/*    */   public static void shutdown() {
/* 86 */     NineHack.log("Discord RPC is shutting down!");
/*    */     
/* 88 */     discordRPC.Discord_Shutdown();
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\RPCUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */