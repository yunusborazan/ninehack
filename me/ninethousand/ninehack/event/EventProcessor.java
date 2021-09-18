/*     */ package me.ninethousand.ninehack.event;
/*     */ import java.awt.Color;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import me.ninethousand.ninehack.NineHack;
/*     */ import me.ninethousand.ninehack.event.events.PacketEvent;
/*     */ import me.ninethousand.ninehack.event.events.Render2DEvent;
/*     */ import me.ninethousand.ninehack.event.events.Render3DEvent;
/*     */ import me.ninethousand.ninehack.event.events.TotemPopEvent;
/*     */ import me.ninethousand.ninehack.feature.Feature;
/*     */ import me.ninethousand.ninehack.feature.features.client.Chat;
/*     */ import me.ninethousand.ninehack.feature.setting.Setting;
/*     */ import me.ninethousand.ninehack.managers.FeatureManager;
/*     */ import me.ninethousand.ninehack.util.Timer;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraftforge.client.event.RenderGameOverlayEvent;
/*     */ import net.minecraftforge.client.event.RenderWorldLastEvent;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.entity.living.LivingEvent;
/*     */ import net.minecraftforge.fml.common.eventhandler.Event;
/*     */ import net.minecraftforge.fml.common.eventhandler.EventPriority;
/*     */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*     */ import net.minecraftforge.fml.common.gameevent.TickEvent;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class EventProcessor implements NineHack.Globals {
/*  30 */   private final Timer logoutTimer = new Timer();
/*     */   private final AtomicBoolean tickOngoing;
/*     */   
/*     */   public EventProcessor() {
/*  34 */     this.tickOngoing = new AtomicBoolean(false);
/*     */   }
/*     */   
/*     */   public void init() {
/*  38 */     MinecraftForge.EVENT_BUS.register(this);
/*     */   }
/*     */   
/*     */   public boolean ticksOngoing() {
/*  42 */     return this.tickOngoing.get();
/*     */   }
/*     */   
/*     */   public void onUnload() {
/*  46 */     MinecraftForge.EVENT_BUS.unregister(this);
/*     */   }
/*     */   
/*     */   public boolean nullCheck() {
/*  50 */     return (mc.field_71439_g == null || mc.field_71441_e == null);
/*     */   }
/*     */ 
/*     */   
/*     */   @SubscribeEvent
/*     */   public void onUpdate(LivingEvent.LivingUpdateEvent event) {
/*  56 */     if (!nullCheck() && (event.getEntity().func_130014_f_()).field_72995_K && event.getEntityLiving().equals(mc.field_71439_g)) {
/*  57 */       NineHack.INVENTORY_MANAGER.update();
/*  58 */       for (Feature module : FeatureManager.getFeatures()) {
/*  59 */         if (module.isEnabled()) {
/*  60 */           module.onUpdate();
/*     */         }
/*     */       } 
/*     */     } 
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
/*     */   @SubscribeEvent
/*     */   public void onTick(TickEvent.ClientTickEvent event) {
/*  79 */     if (nullCheck())
/*  80 */       return;  for (Feature module : FeatureManager.getFeatures()) {
/*  81 */       if (module.isEnabled()) {
/*  82 */         module.onUpdate();
/*     */       }
/*  84 */       module.onTick();
/*  85 */       for (Setting<?> setting : (Iterable<Setting<?>>)module.getSettings()) {
/*  86 */         if (setting.getValue() instanceof Color) {
/*  87 */           Setting<Color> colorSetting = (Setting)setting;
/*  88 */           if (setting.isRainbow()) {
/*  89 */             colorSetting.setValue(new Color(((Integer)ClientColor.colorHeightMap.get(Integer.valueOf(0))).intValue()));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     for (EntityPlayer player : mc.field_71441_e.field_73010_i) {
/*  96 */       if (player == null || player.func_110143_aJ() > 0.0F)
/*     */         continue; 
/*  98 */       MinecraftForge.EVENT_BUS.post((Event)new DeathEvent(player));
/*  99 */       Chat.INSTANCE.onDeath(player);
/*     */     } 
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
/*     */   @SubscribeEvent
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/* 120 */     if (event.getStage() != 0)
/*     */       return; 
/* 122 */     if (event.getPacket() instanceof SPacketEntityStatus) {
/* 123 */       SPacketEntityStatus packet = (SPacketEntityStatus)event.getPacket();
/* 124 */       if (packet.func_149160_c() == 35 && packet.func_149161_a((World)mc.field_71441_e) instanceof EntityPlayer) {
/* 125 */         EntityPlayer player = (EntityPlayer)packet.func_149161_a((World)mc.field_71441_e);
/* 126 */         MinecraftForge.EVENT_BUS.post((Event)new TotemPopEvent(player));
/* 127 */         Chat.INSTANCE.onTotemPop(player);
/*     */       } 
/*     */     } 
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
/*     */   @SubscribeEvent
/*     */   public void onWorldRender(RenderWorldLastEvent event) {
/* 162 */     if (event.isCanceled())
/*     */       return; 
/* 164 */     mc.field_71424_I.func_76320_a("ninehack");
/* 165 */     GlStateManager.func_179090_x();
/* 166 */     GlStateManager.func_179147_l();
/* 167 */     GlStateManager.func_179118_c();
/* 168 */     GlStateManager.func_179120_a(770, 771, 1, 0);
/* 169 */     GlStateManager.func_179103_j(7425);
/* 170 */     GlStateManager.func_179097_i();
/* 171 */     GlStateManager.func_187441_d(1.0F);
/* 172 */     Render3DEvent render3dEvent = new Render3DEvent(event.getPartialTicks());
/*     */     
/* 174 */     for (Feature feature : FeatureManager.getFeatures()) {
/* 175 */       if (feature.isEnabled()) {
/* 176 */         feature.onRender3D(render3dEvent);
/*     */       }
/*     */     } 
/*     */     
/* 180 */     GlStateManager.func_187441_d(1.0F);
/* 181 */     GlStateManager.func_179103_j(7424);
/* 182 */     GlStateManager.func_179084_k();
/* 183 */     GlStateManager.func_179141_d();
/* 184 */     GlStateManager.func_179098_w();
/* 185 */     GlStateManager.func_179126_j();
/* 186 */     GlStateManager.func_179089_o();
/* 187 */     GlStateManager.func_179089_o();
/* 188 */     GlStateManager.func_179132_a(true);
/* 189 */     GlStateManager.func_179098_w();
/* 190 */     GlStateManager.func_179147_l();
/* 191 */     GlStateManager.func_179126_j();
/* 192 */     mc.field_71424_I.func_76319_b();
/*     */   }
/*     */   
/*     */   @SubscribeEvent
/*     */   public void renderHUD(RenderGameOverlayEvent.Post event) {
/* 197 */     if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
/* 198 */       NineHack.TEXT_MANAGER.updateResolution(); 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.LOW)
/*     */   public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Text event) {
/* 203 */     if (event.getType().equals(RenderGameOverlayEvent.ElementType.TEXT)) {
/* 204 */       ScaledResolution resolution = new ScaledResolution(mc);
/* 205 */       Render2DEvent render2DEvent = new Render2DEvent(event.getPartialTicks(), resolution);
/* 206 */       for (Feature feature : FeatureManager.getFeatures()) {
/* 207 */         if (feature.isEnabled())
/* 208 */           feature.on2DRenderEvent(render2DEvent); 
/*     */       } 
/* 210 */       GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
/*     */   public void onKeyInput(InputEvent.KeyInputEvent event) {
/* 216 */     if (Keyboard.getEventKeyState() && 
/* 217 */       Keyboard.getEventKey() != 0) {
/* 218 */       for (Feature feature : FeatureManager.getFeatures()) {
/* 219 */         if (feature.getKey() == Keyboard.getEventKey()) {
/* 220 */           feature.toggle();
/*     */         }
/*     */       } 
/*     */       
/* 224 */       String keyName = Keyboard.getKeyName(Keyboard.getEventKey());
/*     */ 
/*     */ 
/*     */       
/* 228 */       if (keyName.equalsIgnoreCase(NineHack.CHAT_PREFIX))
/* 229 */         mc.func_147108_a((GuiScreen)new GuiChat("HAHA Faggot!")); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\event\EventProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */