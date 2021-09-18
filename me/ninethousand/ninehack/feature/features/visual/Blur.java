/*    */ package me.ninethousand.ninehack.feature.features.visual;
/*    */ 
/*    */ import me.ninethousand.ninehack.feature.Category;
/*    */ import me.ninethousand.ninehack.feature.Feature;
/*    */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @NineHackFeature(name = "Blur", description = "Blurs the gui", category = Category.Visual)
/*    */ public class Blur
/*    */   extends Feature
/*    */ {
/*    */   public static Feature INSTANCE;
/*    */   
/*    */   public void onDisable() {
/* 21 */     this; if (mc.field_71441_e != null)
/* 22 */       mc.field_71460_t.func_147706_e().func_148021_a(); 
/*    */   }
/*    */   
/*    */   public void onUpdate() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: pop
/*    */     //   2: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   5: getfield field_71441_e : Lnet/minecraft/client/multiplayer/WorldClient;
/*    */     //   8: ifnull -> 316
/*    */     //   11: getstatic me/ninethousand/ninehack/feature/features/client/GUI.guiOpen : Z
/*    */     //   14: ifne -> 185
/*    */     //   17: aload_0
/*    */     //   18: pop
/*    */     //   19: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   22: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*    */     //   25: instanceof net/minecraft/client/gui/inventory/GuiContainer
/*    */     //   28: ifne -> 185
/*    */     //   31: aload_0
/*    */     //   32: pop
/*    */     //   33: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   36: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*    */     //   39: instanceof net/minecraft/client/gui/GuiChat
/*    */     //   42: ifne -> 185
/*    */     //   45: aload_0
/*    */     //   46: pop
/*    */     //   47: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   50: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*    */     //   53: instanceof net/minecraft/client/gui/GuiConfirmOpenLink
/*    */     //   56: ifne -> 185
/*    */     //   59: aload_0
/*    */     //   60: pop
/*    */     //   61: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   64: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*    */     //   67: instanceof net/minecraft/client/gui/inventory/GuiEditSign
/*    */     //   70: ifne -> 185
/*    */     //   73: aload_0
/*    */     //   74: pop
/*    */     //   75: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   78: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*    */     //   81: instanceof net/minecraft/client/gui/GuiGameOver
/*    */     //   84: ifne -> 185
/*    */     //   87: aload_0
/*    */     //   88: pop
/*    */     //   89: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   92: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*    */     //   95: instanceof net/minecraft/client/gui/GuiOptions
/*    */     //   98: ifne -> 185
/*    */     //   101: aload_0
/*    */     //   102: pop
/*    */     //   103: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   106: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*    */     //   109: instanceof net/minecraft/client/gui/GuiIngameMenu
/*    */     //   112: ifne -> 185
/*    */     //   115: aload_0
/*    */     //   116: pop
/*    */     //   117: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   120: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*    */     //   123: instanceof net/minecraft/client/gui/GuiVideoSettings
/*    */     //   126: ifne -> 185
/*    */     //   129: aload_0
/*    */     //   130: pop
/*    */     //   131: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   134: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*    */     //   137: instanceof net/minecraft/client/gui/GuiScreenOptionsSounds
/*    */     //   140: ifne -> 185
/*    */     //   143: aload_0
/*    */     //   144: pop
/*    */     //   145: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   148: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*    */     //   151: instanceof net/minecraft/client/gui/GuiControls
/*    */     //   154: ifne -> 185
/*    */     //   157: aload_0
/*    */     //   158: pop
/*    */     //   159: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   162: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*    */     //   165: instanceof net/minecraft/client/gui/GuiCustomizeSkin
/*    */     //   168: ifne -> 185
/*    */     //   171: aload_0
/*    */     //   172: pop
/*    */     //   173: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   176: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*    */     //   179: instanceof net/minecraftforge/fml/client/GuiModList
/*    */     //   182: ifeq -> 292
/*    */     //   185: getstatic net/minecraft/client/renderer/OpenGlHelper.field_148824_g : Z
/*    */     //   188: ifeq -> 256
/*    */     //   191: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   194: invokevirtual func_175606_aa : ()Lnet/minecraft/entity/Entity;
/*    */     //   197: instanceof net/minecraft/entity/player/EntityPlayer
/*    */     //   200: ifeq -> 256
/*    */     //   203: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   206: getfield field_71460_t : Lnet/minecraft/client/renderer/EntityRenderer;
/*    */     //   209: invokevirtual func_147706_e : ()Lnet/minecraft/client/shader/ShaderGroup;
/*    */     //   212: ifnull -> 227
/*    */     //   215: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   218: getfield field_71460_t : Lnet/minecraft/client/renderer/EntityRenderer;
/*    */     //   221: invokevirtual func_147706_e : ()Lnet/minecraft/client/shader/ShaderGroup;
/*    */     //   224: invokevirtual func_148021_a : ()V
/*    */     //   227: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   230: getfield field_71460_t : Lnet/minecraft/client/renderer/EntityRenderer;
/*    */     //   233: new net/minecraft/util/ResourceLocation
/*    */     //   236: dup
/*    */     //   237: ldc 'shaders/post/blur.json'
/*    */     //   239: invokespecial <init> : (Ljava/lang/String;)V
/*    */     //   242: invokevirtual func_175069_a : (Lnet/minecraft/util/ResourceLocation;)V
/*    */     //   245: goto -> 316
/*    */     //   248: astore_1
/*    */     //   249: aload_1
/*    */     //   250: invokevirtual printStackTrace : ()V
/*    */     //   253: goto -> 316
/*    */     //   256: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   259: getfield field_71460_t : Lnet/minecraft/client/renderer/EntityRenderer;
/*    */     //   262: invokevirtual func_147706_e : ()Lnet/minecraft/client/shader/ShaderGroup;
/*    */     //   265: ifnull -> 316
/*    */     //   268: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   271: getfield field_71462_r : Lnet/minecraft/client/gui/GuiScreen;
/*    */     //   274: ifnonnull -> 316
/*    */     //   277: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   280: getfield field_71460_t : Lnet/minecraft/client/renderer/EntityRenderer;
/*    */     //   283: invokevirtual func_147706_e : ()Lnet/minecraft/client/shader/ShaderGroup;
/*    */     //   286: invokevirtual func_148021_a : ()V
/*    */     //   289: goto -> 316
/*    */     //   292: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   295: getfield field_71460_t : Lnet/minecraft/client/renderer/EntityRenderer;
/*    */     //   298: invokevirtual func_147706_e : ()Lnet/minecraft/client/shader/ShaderGroup;
/*    */     //   301: ifnull -> 316
/*    */     //   304: getstatic me/ninethousand/ninehack/feature/features/visual/Blur.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   307: getfield field_71460_t : Lnet/minecraft/client/renderer/EntityRenderer;
/*    */     //   310: invokevirtual func_147706_e : ()Lnet/minecraft/client/shader/ShaderGroup;
/*    */     //   313: invokevirtual func_148021_a : ()V
/*    */     //   316: return
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #28	-> 0
/*    */     //   #29	-> 11
/*    */     //   #32	-> 185
/*    */     //   #33	-> 203
/*    */     //   #34	-> 215
/*    */     //   #37	-> 227
/*    */     //   #41	-> 245
/*    */     //   #39	-> 248
/*    */     //   #40	-> 249
/*    */     //   #41	-> 253
/*    */     //   #43	-> 256
/*    */     //   #44	-> 277
/*    */     //   #47	-> 292
/*    */     //   #48	-> 304
/*    */     //   #51	-> 316
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   249	4	1	e	Ljava/lang/Exception;
/*    */     //   0	317	0	this	Lme/ninethousand/ninehack/feature/features/visual/Blur;
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   227	245	248	java/lang/Exception
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\visual\Blur.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */