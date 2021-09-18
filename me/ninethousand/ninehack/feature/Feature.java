/*     */ package me.ninethousand.ninehack.feature;
/*     */ 
/*     */ import com.mojang.realmsclient.gui.ChatFormatting;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import me.ninethousand.ninehack.NineHack;
/*     */ import me.ninethousand.ninehack.event.events.Render2DEvent;
/*     */ import me.ninethousand.ninehack.event.events.Render3DEvent;
/*     */ import me.ninethousand.ninehack.feature.annotation.AlwaysEnabled;
/*     */ import me.ninethousand.ninehack.feature.annotation.EnabledByDefault;
/*     */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*     */ import me.ninethousand.ninehack.feature.features.client.Chat;
/*     */ import me.ninethousand.ninehack.feature.features.client.GUI;
/*     */ import me.ninethousand.ninehack.feature.gui.notifications.Notification;
/*     */ import me.ninethousand.ninehack.feature.gui.notifications.NotificationType;
/*     */ import me.ninethousand.ninehack.feature.setting.Setting;
/*     */ import me.ninethousand.ninehack.managers.NotificationManager;
/*     */ import me.ninethousand.ninehack.util.ChatUtil;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ 
/*     */ public abstract class Feature
/*     */   implements NineHack.Globals {
/*  23 */   private final String name = getAnnotation().name();
/*  24 */   private final String description = getAnnotation().description();
/*  25 */   private final Category category = getAnnotation().category();
/*     */   
/*  27 */   private int key = getAnnotation().key();
/*     */   
/*  29 */   public final boolean alwaysEnabled = getClass().isAnnotationPresent((Class)AlwaysEnabled.class);
/*  30 */   public final boolean enabledByDefault = getClass().isAnnotationPresent((Class)EnabledByDefault.class);
/*     */   
/*  32 */   private boolean enabled = (this.alwaysEnabled || this.enabledByDefault);
/*     */   
/*     */   private boolean opened = false;
/*     */   private boolean binding = false;
/*     */   private boolean drawn = true;
/*  37 */   private final ArrayList<Setting<?>> settings = new ArrayList<>();
/*     */   
/*     */   public Feature() {
/*  40 */     if (this.alwaysEnabled);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private NineHackFeature getAnnotation() {
/*  46 */     if (getClass().isAnnotationPresent((Class)NineHackFeature.class)) {
/*  47 */       return getClass().<NineHackFeature>getAnnotation(NineHackFeature.class);
/*     */     }
/*     */     
/*  50 */     throw new IllegalStateException("Annotation 'NineWareFeature' not found!");
/*     */   }
/*     */   
/*     */   public boolean nullCheck() {
/*  54 */     return (mc.field_71439_g == null || mc.field_71441_e == null);
/*     */   }
/*     */   
/*     */   public void enable() {
/*  58 */     this.enabled = true;
/*     */     
/*  60 */     MinecraftForge.EVENT_BUS.register(this);
/*     */     
/*  62 */     onEnable();
/*     */     
/*  64 */     handleNotifications(true);
/*     */   }
/*     */   
/*     */   public void disable() {
/*  68 */     this.enabled = this.alwaysEnabled;
/*     */     
/*  70 */     MinecraftForge.EVENT_BUS.register(this);
/*     */     
/*  72 */     onDisable();
/*     */     
/*  74 */     handleNotifications(false);
/*     */   }
/*     */   
/*     */   private void handleNotifications(boolean enable) {
/*  78 */     if (nullCheck())
/*     */       return; 
/*  80 */     if (Chat.moduleToggle.getValue() != Chat.ModuleToggleMode.None)
/*     */     {
/*  82 */       if (getClass() != GUI.class)
/*  83 */         if (Chat.moduleToggle.getValue() == Chat.ModuleToggleMode.Chat) {
/*  84 */           String message; if (enable) {
/*  85 */             message = ChatFormatting.GREEN + this.name + " enabled.";
/*     */           } else {
/*  87 */             message = ChatFormatting.RED + this.name + " disabled.";
/*     */           } 
/*  89 */           ChatUtil.sendClientMessage(message);
/*     */         }
/*  91 */         else if (Chat.moduleToggle.getValue() == Chat.ModuleToggleMode.HUD) {
/*  92 */           String message; if (enable) {
/*  93 */             message = this.name + " enabled.";
/*     */           } else {
/*  95 */             message = this.name + " disabled.";
/*     */           } 
/*     */           
/*  98 */           NotificationManager.show(new Notification(NotificationType.INFO, "Module Toggle", message, 1));
/*     */         }  
/*     */     }
/*     */   }
/*     */   
/*     */   public void toggle() {
/*     */     try {
/* 105 */       if (this.enabled) {
/* 106 */         disable();
/*     */       } else {
/* 108 */         enable();
/*     */       } 
/* 110 */     } catch (Exception exception) {}
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 114 */     if (enabled) {
/* 115 */       enable();
/*     */     } else {
/* 117 */       disable();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setOpened(boolean opened) {
/* 122 */     this.opened = opened;
/*     */   }
/*     */   
/*     */   public void setBinding(boolean binding) {
/* 126 */     this.binding = binding;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 130 */     return this.name;
/*     */   }
/*     */   
/*     */   public Category getCategory() {
/* 134 */     return this.category;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 138 */     return this.description;
/*     */   }
/*     */   
/*     */   public int getKey() {
/* 142 */     return this.key;
/*     */   }
/*     */   
/*     */   public boolean isOpened() {
/* 146 */     return this.opened;
/*     */   }
/*     */   
/*     */   public boolean isBinding() {
/* 150 */     return this.binding;
/*     */   }
/*     */   
/*     */   public void setKey(int key) {
/* 154 */     this.key = key;
/*     */   }
/*     */   
/*     */   public void addSettings(Setting<?>... settings) {
/* 158 */     this.settings.addAll(Arrays.asList(settings));
/*     */   }
/*     */   
/*     */   public ArrayList<Setting<?>> getSettings() {
/* 162 */     return this.settings;
/*     */   }
/*     */   
/*     */   public boolean hasSettings() {
/* 166 */     return (this.settings.size() > 0);
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/* 170 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public boolean isDrawn() {
/* 174 */     return this.drawn;
/*     */   }
/*     */   
/*     */   public void setDrawn(boolean drawn) {
/* 178 */     this.drawn = drawn;
/*     */   }
/*     */   
/*     */   public String getArraylistInfo() {
/* 182 */     return "";
/*     */   }
/*     */   
/*     */   public void onEnable() {}
/*     */   
/*     */   public void onDisable() {}
/*     */   
/*     */   public void onUpdate() {}
/*     */   
/*     */   public void onTick() {}
/*     */   
/*     */   public void on2DRenderEvent(Render2DEvent event) {}
/*     */   
/*     */   public void onRender3D(Render3DEvent event) {}
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\Feature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */