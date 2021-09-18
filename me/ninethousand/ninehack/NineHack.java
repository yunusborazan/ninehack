/*    */ package me.ninethousand.ninehack;
/*    */ 
/*    */ import me.ninethousand.ninehack.event.EventProcessor;
/*    */ import me.ninethousand.ninehack.feature.gui.menu.CustomMainMenu;
/*    */ import me.ninethousand.ninehack.managers.InventoryManager;
/*    */ import me.ninethousand.ninehack.managers.TextManager;
/*    */ import me.ninethousand.ninehack.util.ConfigUtil;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraftforge.fml.common.Mod;
/*    */ import net.minecraftforge.fml.common.Mod.EventHandler;
/*    */ import net.minecraftforge.fml.common.event.FMLInitializationEvent;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.lwjgl.opengl.Display;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mod(modid = "ninehack", name = "NineHack", version = "1.0.1")
/*    */ public class NineHack
/*    */ {
/*    */   public static final String MOD_ID = "ninehack";
/*    */   public static final String MOD_NAME = "NineHack";
/*    */   public static final String MOD_VERSION = "1.0.1";
/*    */   public static final String MOD_EDITION = "Pre-Release";
/*    */   public static final String NAME_VERSION = "NineHack v1.0.1";
/* 31 */   public static String CHAT_PREFIX = ",";
/*    */   
/*    */   public static final int DEFAULT_GUI_KEY = 23;
/* 34 */   public static final EventProcessor EVENT_PROCESSOR = new EventProcessor();
/* 35 */   public static final Logger LOGGER = LogManager.getLogger("ninehack");
/* 36 */   public static final TextManager TEXT_MANAGER = new TextManager();
/* 37 */   public static final CustomMainMenu CUSTOM_MAIN_MENU = new CustomMainMenu();
/* 38 */   public static final InventoryManager INVENTORY_MANAGER = new InventoryManager();
/*    */   
/*    */   @EventHandler
/*    */   public void init(FMLInitializationEvent event) {
/* 42 */     Display.setTitle("ninethousand.dev");
/*    */     
/* 44 */     StartUp.start();
/*    */     
/* 46 */     Runtime.getRuntime().addShutdownHook(new Thread(() -> {
/*    */             ConfigUtil.saveConfig();
/*    */             
/*    */             log("Config Saved!");
/*    */           }));
/* 51 */     ConfigUtil.loadConfig();
/* 52 */     log("Config Loaded!");
/*    */   }
/*    */   
/*    */   public static void log(String message) {
/* 56 */     LOGGER.info(message);
/*    */   }
/*    */   
/*    */   public static interface Globals {
/* 60 */     public static final Minecraft mc = Minecraft.func_71410_x();
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\NineHack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */