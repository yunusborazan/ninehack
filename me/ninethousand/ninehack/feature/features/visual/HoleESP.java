/*    */ package me.ninethousand.ninehack.feature.features.visual;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.ninethousand.ninehack.event.events.Render3DEvent;
/*    */ import me.ninethousand.ninehack.feature.Category;
/*    */ import me.ninethousand.ninehack.feature.Feature;
/*    */ import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
/*    */ import me.ninethousand.ninehack.feature.setting.NumberSetting;
/*    */ import me.ninethousand.ninehack.feature.setting.Setting;
/*    */ import me.ninethousand.ninehack.util.BlockUtil;
/*    */ import me.ninethousand.ninehack.util.RenderUtil;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ 
/*    */ @NineHackFeature(name = "HoleESP", description = "Based Hoe", category = Category.Visual)
/*    */ public class HoleESP
/*    */   extends Feature
/*    */ {
/*    */   public static Feature INSTANCE;
/* 21 */   public static final Setting<Boolean> own = new Setting("Own Hole", Boolean.valueOf(true));
/* 22 */   public static final Setting<Boolean> fov = new Setting("In FOV", Boolean.valueOf(true));
/* 23 */   public static final NumberSetting<Integer> range = new NumberSetting("Range", Integer.valueOf(0), Integer.valueOf(5), Integer.valueOf(10), 1);
/* 24 */   public static final NumberSetting<Integer> rangeY = new NumberSetting("Vert Range", Integer.valueOf(0), Integer.valueOf(5), Integer.valueOf(10), 1);
/* 25 */   public static final Setting<Boolean> box = new Setting("Box", Boolean.valueOf(true));
/* 26 */   public static final Setting<Boolean> gradient = new Setting("Gradient", Boolean.valueOf(true));
/* 27 */   public static final Setting<Boolean> invert = new Setting("Invert", Boolean.valueOf(true));
/* 28 */   public static final Setting<Boolean> outline = new Setting("Outline", Boolean.valueOf(true));
/* 29 */   public static final Setting<Boolean> gradientOutline = new Setting("Gradient Outline", Boolean.valueOf(true));
/* 30 */   public static final Setting<Boolean> invertOutline = new Setting("Invert Outline", Boolean.valueOf(true));
/* 31 */   public static final NumberSetting<Float> height = new NumberSetting("Height", Float.valueOf(-2.0F), Float.valueOf(0.0F), Float.valueOf(2.0F), 1);
/* 32 */   public static final NumberSetting<Float> outlineWidth = new NumberSetting("Outline Width", Float.valueOf(0.0F), Float.valueOf(1.0F), Float.valueOf(4.0F), 1);
/* 33 */   public static final NumberSetting<Integer> alpha = new NumberSetting("Alpha", Integer.valueOf(0), Integer.valueOf(155), Integer.valueOf(255), 1);
/* 34 */   public static final Setting<Color> bedrockColor = new Setting("Bedrock Color", new Color(-1843542947, true));
/* 35 */   public static final Setting<Color> bedrockOColor = new Setting("Bedrock Line Color", new Color(-149580476, true));
/* 36 */   public static final Setting<Color> obsidianColor = new Setting("Obby Color", new Color(-1832639715, true));
/* 37 */   public static final Setting<Color> obsidianOColor = new Setting("Obby Color", new Color(-797239278, true));
/*    */   
/* 39 */   private int currentAlpha = 0;
/*    */   
/*    */   public HoleESP() {
/* 42 */     addSettings(new Setting[] { own, fov, (Setting)range, (Setting)rangeY, box, gradient, invert, outline, gradientOutline, invertOutline, (Setting)height, (Setting)outlineWidth, (Setting)alpha, bedrockColor, bedrockOColor, obsidianColor, obsidianOColor });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRender3D(Render3DEvent event) {
/* 65 */     assert mc.func_175606_aa() != null;
/* 66 */     Vec3i playerPos = new Vec3i((mc.func_175606_aa()).field_70165_t, (mc.func_175606_aa()).field_70163_u, (mc.func_175606_aa()).field_70161_v);
/* 67 */     for (int x = playerPos.func_177958_n() - ((Integer)range.getValue()).intValue(); x < playerPos.func_177958_n() + ((Integer)range.getValue()).intValue(); x++) {
/* 68 */       for (int z = playerPos.func_177952_p() - ((Integer)range.getValue()).intValue(); z < playerPos.func_177952_p() + ((Integer)range.getValue()).intValue(); z++) {
/* 69 */         for (int y = playerPos.func_177956_o() + ((Integer)rangeY.getValue()).intValue(); y > playerPos.func_177956_o() - ((Integer)rangeY.getValue()).intValue(); y--) {
/* 70 */           BlockPos pos = new BlockPos(x, y, z);
/* 71 */           if (mc.field_71441_e.func_180495_p(pos).func_177230_c().equals(Blocks.field_150350_a) && mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 1, 0)).func_177230_c().equals(Blocks.field_150350_a) && mc.field_71441_e.func_180495_p(pos.func_177982_a(0, 2, 0)).func_177230_c().equals(Blocks.field_150350_a) && (!pos.equals(new BlockPos(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u, mc.field_71439_g.field_70161_v)) || ((Boolean)own.getValue()).booleanValue()) && (BlockUtil.isPosInFov(pos).booleanValue() || !((Boolean)fov.getValue()).booleanValue()))
/*    */           {
/* 73 */             if (mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c() == Blocks.field_150357_h && mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c() == Blocks.field_150357_h) {
/* 74 */               RenderUtil.drawBoxESP(pos, (Color)bedrockColor.getValue(), ((Boolean)outline.getValue()).booleanValue(), (Color)bedrockOColor.getValue(), ((Float)outlineWidth.getValue()).floatValue(), ((Boolean)outline.getValue()).booleanValue(), ((Boolean)box.getValue()).booleanValue(), ((Integer)alpha.getValue()).intValue(), true, ((Float)height.getValue()).floatValue(), ((Boolean)gradient.getValue()).booleanValue(), ((Boolean)gradientOutline.getValue()).booleanValue(), ((Boolean)invert.getValue()).booleanValue(), ((Boolean)invertOutline.getValue()).booleanValue(), this.currentAlpha);
/*    */             
/*    */             }
/* 77 */             else if (BlockUtil.isBlockUnSafe(mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c()) && BlockUtil.isBlockUnSafe(mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c()) && BlockUtil.isBlockUnSafe(mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c()) && BlockUtil.isBlockUnSafe(mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c()) && BlockUtil.isBlockUnSafe(mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c())) {
/*    */               
/* 79 */               RenderUtil.drawBoxESP(pos, (Color)obsidianColor.getValue(), ((Boolean)outline.getValue()).booleanValue(), (Color)obsidianOColor.getValue(), ((Float)outlineWidth.getValue()).floatValue(), ((Boolean)outline.getValue()).booleanValue(), ((Boolean)box.getValue()).booleanValue(), ((Integer)alpha.getValue()).intValue(), true, ((Float)height.getValue()).floatValue(), ((Boolean)gradient.getValue()).booleanValue(), ((Boolean)gradientOutline.getValue()).booleanValue(), ((Boolean)invert.getValue()).booleanValue(), ((Boolean)invertOutline.getValue()).booleanValue(), this.currentAlpha);
/*    */             } 
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\features\visual\HoleESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */