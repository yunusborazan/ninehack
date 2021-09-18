/*     */ package me.ninethousand.ninehack.feature.gui.clickgui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.RoundingMode;
/*     */ import java.util.ArrayList;
/*     */ import me.ninethousand.ninehack.NineHack;
/*     */ import me.ninethousand.ninehack.feature.Category;
/*     */ import me.ninethousand.ninehack.feature.Feature;
/*     */ import me.ninethousand.ninehack.feature.features.client.ClientColor;
/*     */ import me.ninethousand.ninehack.feature.features.client.GUI;
/*     */ import me.ninethousand.ninehack.feature.setting.NumberSetting;
/*     */ import me.ninethousand.ninehack.feature.setting.Setting;
/*     */ import me.ninethousand.ninehack.managers.FeatureManager;
/*     */ import me.ninethousand.ninehack.util.RenderUtil;
/*     */ import me.ninethousand.ninehack.util.Timer;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import org.apache.commons.lang3.text.WordUtils;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClickGUI
/*     */   extends GuiScreen
/*     */ {
/*  27 */   public static int EDGE_SPACING_X = 2;
/*  28 */   public static int EDGE_SPACING_Y = 20;
/*  29 */   public static int FEATURE_SPACING = 1;
/*  30 */   public static int WIDTH = 110;
/*  31 */   public static int HEIGHT = 16;
/*  32 */   public static int FEATURE_HEIGHT = HEIGHT - 2;
/*  33 */   public static int FEATURE_WIDTH = HEIGHT - 2;
/*     */   
/*  35 */   public static int cHeight = 0;
/*     */   
/*  37 */   public static final Timer timer = new Timer();
/*     */   
/*  39 */   public static Color HEADER_COLOR = new Color(61, 34, 194, 161);
/*  40 */   public static Color ACCENT_COLOR = new Color(80, 80, 174, 255);
/*  41 */   public static Color FEATURE_FILL_COLOR = new Color(-12105913, true);
/*  42 */   public static Color FEATURE_BACKGROUND_COLOR = new Color(1377310744, true);
/*  43 */   public static Color FONT_COLOR = new Color(16777215);
/*     */   public static boolean leftClicked = false;
/*     */   public static boolean leftDown = false;
/*  46 */   public static int keyDown = 0; public static boolean rightClicked = false; public static boolean rightDown = false;
/*  47 */   public static char typed = 't';
/*     */ 
/*     */   
/*     */   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
/*  51 */     drawGUI(0, 0, mouseX, mouseY);
/*     */     
/*  53 */     leftClicked = false;
/*  54 */     rightClicked = false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
/*  59 */     if (mouseButton == 0) {
/*  60 */       leftClicked = true;
/*  61 */       leftDown = true;
/*     */     } 
/*     */     
/*  64 */     if (mouseButton == 1) {
/*  65 */       rightClicked = true;
/*  66 */       rightDown = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_146286_b(int mouseX, int mouseY, int state) {
/*  72 */     if (state == 0) {
/*  73 */       leftClicked = false;
/*  74 */       leftDown = false;
/*     */     } 
/*     */     
/*  77 */     if (state == 1) {
/*  78 */       rightClicked = false;
/*  79 */       rightDown = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_73869_a(char typedChar, int keyCode) throws IOException {
/*  85 */     super.func_73869_a(typedChar, keyCode);
/*     */     
/*  87 */     keyDown = keyCode;
/*  88 */     typed = typedChar;
/*     */   }
/*     */   
/*     */   protected static int getKey() {
/*  92 */     int temp = keyDown;
/*  93 */     keyDown = 0;
/*  94 */     return temp;
/*     */   }
/*     */   
/*     */   protected static char getChar() {
/*  98 */     char temp = typed;
/*  99 */     typed = Character.MIN_VALUE;
/* 100 */     return temp;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146281_b() {
/* 105 */     GUI.guiOpen = false;
/* 106 */     GUI.INSTANCE.setEnabled(false);
/*     */   }
/*     */   
/*     */   public static void drawGUI(int posX, int posY, int mouseX, int mouseY) {
/* 110 */     int x = posX + EDGE_SPACING_X + 20;
/*     */ 
/*     */     
/* 113 */     int totalY = 0;
/*     */     
/* 115 */     for (Category category : Category.values()) {
/* 116 */       cHeight = 0;
/*     */       
/* 118 */       int y = posY + EDGE_SPACING_Y;
/* 119 */       totalY = y;
/*     */ 
/*     */       
/* 122 */       totalY += drawCategoryHeader(category, x, y, mouseX, mouseY);
/*     */ 
/*     */       
/* 125 */       if (category.isOpenInGui()) {
/* 126 */         totalY += drawCategoryFeatures(category, x, totalY, mouseX, mouseY);
/*     */       }
/*     */ 
/*     */       
/* 130 */       if (((Boolean)GUI.outline.getValue()).booleanValue()) {
/* 131 */         if (category.isOpenInGui() && FeatureManager.getFeaturesInCategory(category).size() > 0) totalY++; 
/* 132 */         RenderUtil.drawOutlineRect(x + 0.3D, y, (x + WIDTH), (y + totalY - EDGE_SPACING_Y), HEADER_COLOR, 2.0F);
/*     */       } 
/*     */ 
/*     */       
/* 136 */       x += EDGE_SPACING_X + WIDTH;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int drawCategoryHeader(Category category, int x, int y, int mouseX, int mouseY) {
/* 141 */     if (mouseHovering(x, y, x + WIDTH, y + HEIGHT, mouseX, mouseY) && rightClicked) {
/* 142 */       category.setOpenInGui(!category.isOpenInGui());
/*     */     }
/* 144 */     RenderUtil.drawRect(x, y, x + WIDTH, y + HEIGHT, HEADER_COLOR);
/* 145 */     NineHack.TEXT_MANAGER.drawStringWithShadow(category.name(), (x + WIDTH / 2 - NineHack.TEXT_MANAGER.getStringWidth(category.name()) / 2), (y + HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2 - 1), FONT_COLOR.getRGB());
/*     */     
/* 147 */     return HEIGHT;
/*     */   }
/*     */   
/*     */   private static int drawCategoryFeatures(Category category, int x, int y, int mouseX, int mouseY) {
/* 151 */     ArrayList<Feature> features = new ArrayList<>();
/* 152 */     int settingY = 1;
/*     */     
/* 154 */     for (Feature feature : FeatureManager.getFeaturesInCategory(category)) {
/* 155 */       features.add(feature);
/*     */       
/* 157 */       if (feature.isOpened()) {
/* 158 */         settingY += getSettingsHeight(feature);
/* 159 */         settingY += FEATURE_HEIGHT;
/*     */       } 
/*     */     } 
/*     */     
/* 163 */     int endY = features.size() * FEATURE_HEIGHT;
/*     */     
/* 165 */     RenderUtil.drawRect(x, y, x + WIDTH, y + endY + settingY, FEATURE_BACKGROUND_COLOR);
/*     */     
/* 167 */     for (Feature feature : features) {
/* 168 */       if (mouseHovering(x + 2, y, x + WIDTH - 2, y + HEIGHT - 2, mouseX, mouseY)) {
/* 169 */         if (leftClicked) feature.setEnabled(!feature.isEnabled()); 
/* 170 */         if (rightClicked) feature.setOpened(!feature.isOpened());
/*     */       
/*     */       } 
/* 173 */       cHeight += ((Integer)ClientColor.step.getValue()).intValue();
/* 174 */       Color featC = ((Boolean)GUI.gradientFeatures.getValue()).booleanValue() ? new Color(((Integer)ClientColor.colorHeightMap.get(Integer.valueOf(cHeight))).intValue()) : ACCENT_COLOR;
/*     */       
/* 176 */       RenderUtil.drawRect(x + 2, y + FEATURE_SPACING, x + WIDTH - 2, y + FEATURE_HEIGHT, feature.isEnabled() ? featC : FEATURE_FILL_COLOR);
/* 177 */       NineHack.TEXT_MANAGER.drawStringWithShadow(feature.getName(), (x + 3), (y + (HEIGHT - 2) / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
/*     */       
/* 179 */       y += HEIGHT - 2;
/*     */       
/* 181 */       if (feature.isOpened()) {
/* 182 */         int boostY = drawFeatureSettings(feature, x, y, mouseX, mouseY);
/* 183 */         y += boostY;
/* 184 */         endY += boostY;
/*     */       } 
/*     */     } 
/*     */     
/* 188 */     return endY;
/*     */   }
/*     */   
/*     */   private static int drawFeatureSettings(Feature feature, int x, int y, int mouseX, int mouseY) {
/* 192 */     int boostY = 0;
/*     */     
/* 194 */     for (Setting<?> setting : (Iterable<Setting<?>>)feature.getSettings()) {
/* 195 */       int settingHeight = 0;
/* 196 */       if (setting.getValue() instanceof Boolean) {
/* 197 */         Setting<Boolean> booleanSetting = (Setting)setting;
/* 198 */         settingHeight = drawBooleanSetting(booleanSetting, x, y, mouseX, mouseY);
/* 199 */         y += settingHeight;
/* 200 */         boostY += settingHeight;
/*     */       } 
/* 202 */       if (setting.getValue() instanceof String) {
/* 203 */         Setting<String> stringSetting = (Setting)setting;
/* 204 */         settingHeight = drawStringSetting(stringSetting, x, y, mouseX, mouseY);
/* 205 */         y += settingHeight;
/* 206 */         boostY += settingHeight;
/*     */       } 
/* 208 */       if (setting.getValue() instanceof Enum) {
/* 209 */         Setting<Enum> enumSetting = (Setting)setting;
/* 210 */         settingHeight = drawEnumSetting(enumSetting, x, y, mouseX, mouseY);
/* 211 */         y += settingHeight;
/* 212 */         boostY += settingHeight;
/*     */       } 
/* 214 */       if (setting.getValue() instanceof Color) {
/* 215 */         Setting<Color> colorSetting = (Setting)setting;
/* 216 */         settingHeight = drawColorSetting(colorSetting, x, y, mouseX, mouseY);
/* 217 */         y += settingHeight;
/* 218 */         boostY += settingHeight;
/*     */       } 
/* 220 */       if (setting.getValue() instanceof Integer) {
/* 221 */         NumberSetting<Integer> integerSetting = (NumberSetting)setting;
/* 222 */         settingHeight = drawIntegerSetting(integerSetting, x, y, mouseX, mouseY);
/* 223 */         y += settingHeight;
/* 224 */         boostY += settingHeight;
/*     */       } 
/* 226 */       if (setting.getValue() instanceof Double) {
/* 227 */         NumberSetting<Double> doubleSetting = (NumberSetting)setting;
/* 228 */         settingHeight = drawDoubleSetting(doubleSetting, x, y, mouseX, mouseY);
/* 229 */         y += settingHeight;
/* 230 */         boostY += settingHeight;
/*     */       } 
/* 232 */       if (setting.getValue() instanceof Float) {
/* 233 */         NumberSetting<Float> floatSetting = (NumberSetting)setting;
/* 234 */         settingHeight = drawFloatSetting(floatSetting, x, y, mouseX, mouseY);
/* 235 */         y += settingHeight;
/* 236 */         boostY += settingHeight;
/*     */       } 
/* 238 */       y++;
/* 239 */       boostY++;
/*     */     } 
/*     */     
/* 242 */     boostY += drawBind(feature, keyDown, x, y, mouseX, mouseY);
/*     */     
/* 244 */     return boostY;
/*     */   }
/*     */   
/*     */   private static int drawBooleanSetting(Setting<Boolean> setting, int x, int y, int mouseX, int mouseY) {
/* 248 */     if (mouseHovering(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, mouseX, mouseY)) {
/* 249 */       if (leftClicked) setting.setValue(Boolean.valueOf(!((Boolean)setting.getValue()).booleanValue())); 
/* 250 */       if (rightClicked) setting.setOpened(!setting.isOpened());
/*     */     
/*     */     } 
/* 253 */     char[] delimiters = { ' ', '_' };
/*     */     
/* 255 */     RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
/* 256 */     NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", (x + 6), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
/* 257 */     NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(((Boolean)setting.getValue()).toString()), (x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());
/* 258 */     return FEATURE_HEIGHT;
/*     */   }
/*     */   
/*     */   private static int drawStringSetting(Setting<String> setting, int x, int y, int mouseX, int mouseY) {
/* 262 */     if (mouseHovering(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, mouseX, mouseY)) {
/* 263 */       if (leftClicked) setting.setTyping(!setting.isTyping()); 
/* 264 */       if (rightClicked) setting.setOpened(!setting.isOpened());
/*     */     
/*     */     } 
/* 267 */     int key = getKey();
/* 268 */     char current = getChar();
/* 269 */     String currentS = String.valueOf(current);
/*     */     
/* 271 */     if (setting.isTyping()) {
/* 272 */       if (key == 28) {
/* 273 */         setting.setTyping(false);
/*     */       }
/* 275 */       else if (key != 0) {
/*     */ 
/*     */         
/* 278 */         if ((key == 211 || key == 14) && ((String)setting.getValue()).length() > 0) {
/* 279 */           setting.setValue(((String)setting.getValue()).substring(0, ((String)setting.getValue()).length() - 1));
/*     */         }
/* 281 */         else if (Character.isDigit(currentS.charAt(0)) || Character.isLetter(currentS.charAt(0)) || key == 57) {
/*     */           
/* 283 */           setting.setValue((String)setting.getValue() + (Keyboard.isKeyDown(42) ? String.valueOf(current).toUpperCase() : String.valueOf(current).toLowerCase()));
/*     */         } 
/*     */       } 
/*     */     }
/* 287 */     RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
/* 288 */     NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", (x + 6), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
/* 289 */     NineHack.TEXT_MANAGER.drawStringWithShadow((String)setting.getValue(), (x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());
/*     */     
/* 291 */     return FEATURE_HEIGHT;
/*     */   }
/*     */   
/*     */   private static <E extends Enum<E>> int drawEnumSetting(Setting<Enum> setting, int x, int y, int mouseX, int mouseY) {
/* 295 */     if (mouseHovering(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, mouseX, mouseY)) {
/* 296 */       Enum[] arrayOfEnum = ((Enum<Enum>)setting.getValue()).getDeclaringClass().getEnumConstants();
/* 297 */       int ordinal = ((Enum)setting.getValue()).ordinal();
/*     */       
/* 299 */       if (leftClicked) { setting.setValue(arrayOfEnum[(ordinal + 1 >= arrayOfEnum.length) ? 0 : (ordinal + 1)]); }
/* 300 */       else if (rightClicked) { setting.setValue(arrayOfEnum[(ordinal - 1 < 0) ? (arrayOfEnum.length - 1) : (ordinal - 1)]); }
/*     */     
/*     */     } 
/* 303 */     RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
/* 304 */     NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", (x + 6), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
/* 305 */     NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(((Enum)setting.getValue()).toString()), (x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());
/*     */     
/* 307 */     return FEATURE_HEIGHT;
/*     */   }
/*     */   
/*     */   private static int drawColorSetting(Setting<Color> setting, int x, int y, int mouseX, int mouseY) {
/* 311 */     if (mouseHovering(x, y, x + WIDTH, y + HEIGHT, mouseX, mouseY) && rightClicked) {
/* 312 */       setting.setOpened(!setting.isOpened());
/*     */     }
/* 314 */     RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
/* 315 */     NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName(), (x + 6), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
/* 316 */     RenderUtil.drawRect(x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName()) + 2, y + FEATURE_HEIGHT / 4 - 1, x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName()) + 9, y + 3 * FEATURE_HEIGHT / 4, (Color)setting.getValue());
/* 317 */     RenderUtil.drawOutlineRect((x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName()) + 2), (y + FEATURE_HEIGHT / 4 - 1), (x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName()) + 9), (y + 3 * FEATURE_HEIGHT / 4), FONT_COLOR, 1.0F);
/*     */ 
/*     */     
/* 320 */     if (setting.isOpened()) {
/*     */       Color newColor;
/* 322 */       NineHack.TEXT_MANAGER.drawStringWithShadow("..", (x + WIDTH - 10), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2 - 2), FONT_COLOR.getRGB());
/* 323 */       if (((Boolean)ClientColor.colorMode.getValue()).booleanValue()) {
/* 324 */         float[] hsb = new float[3];
/* 325 */         hsb = Color.RGBtoHSB(((Color)setting.getValue()).getRed(), ((Color)setting.getValue()).getGreen(), ((Color)setting.getValue()).getBlue(), hsb);
/*     */         
/* 327 */         float hueN = hsb[0];
/*     */ 
/*     */         
/* 330 */         y += FEATURE_HEIGHT;
/* 331 */         int hue = drawColorSlider("Hue", (int)(hsb[0] * 255.0F), x, y, mouseX, mouseY);
/* 332 */         y += FEATURE_HEIGHT;
/* 333 */         int saturation = drawColorSlider("Saturation", (int)(hsb[1] * 255.0F), x, y, mouseX, mouseY);
/* 334 */         y += FEATURE_HEIGHT;
/* 335 */         int brightness = drawColorSlider("Brightness", (int)(hsb[2] * 255.0F), x, y, mouseX, mouseY);
/* 336 */         y += FEATURE_HEIGHT;
/* 337 */         int alpha = drawColorSlider("Alpha", ((Color)setting.getValue()).getAlpha(), x, y, mouseX, mouseY);
/* 338 */         y += FEATURE_HEIGHT;
/* 339 */         drawRainbowButton(setting, x, y, mouseX, mouseY);
/* 340 */         y += FEATURE_HEIGHT;
/*     */         
/* 342 */         Color col = new Color(Color.HSBtoRGB(hue / 255.0F, saturation / 255.0F, brightness / 255.0F));
/* 343 */         int r = col.getRed();
/* 344 */         int g = col.getGreen();
/* 345 */         int b = col.getBlue();
/*     */ 
/*     */ 
/*     */         
/* 349 */         newColor = new Color(r, g, b, alpha);
/*     */       } else {
/*     */         
/* 352 */         y += FEATURE_HEIGHT;
/* 353 */         int red = drawColorSlider("Red", ((Color)setting.getValue()).getRed(), x, y, mouseX, mouseY);
/* 354 */         y += FEATURE_HEIGHT;
/* 355 */         int green = drawColorSlider("Green", ((Color)setting.getValue()).getGreen(), x, y, mouseX, mouseY);
/* 356 */         y += FEATURE_HEIGHT;
/* 357 */         int blue = drawColorSlider("Blue", ((Color)setting.getValue()).getBlue(), x, y, mouseX, mouseY);
/* 358 */         y += FEATURE_HEIGHT;
/* 359 */         int alpha = drawColorSlider("Alpha", ((Color)setting.getValue()).getAlpha(), x, y, mouseX, mouseY);
/* 360 */         y += FEATURE_HEIGHT;
/* 361 */         drawRainbowButton(setting, x, y, mouseX, mouseY);
/* 362 */         y += FEATURE_HEIGHT;
/* 363 */         newColor = new Color(red, green, blue, alpha);
/*     */       } 
/*     */       
/* 366 */       RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
/* 367 */       RenderUtil.drawRect(x + WIDTH - 10, y + FEATURE_HEIGHT / 4, x + WIDTH - 4, y + 3 * FEATURE_HEIGHT / 4, (Color)ClientColor.GLOBAL_COLOR.getValue());
/* 368 */       RenderUtil.drawOutlineRect((x + WIDTH - 10), (y + FEATURE_HEIGHT / 4), (x + WIDTH - 4), (y + 3 * FEATURE_HEIGHT / 4), FONT_COLOR, 1.0F);
/* 369 */       NineHack.TEXT_MANAGER.drawStringWithShadow("Color Sync", (x + 6), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
/*     */       
/* 371 */       if (leftDown && mouseHovering(x, y, x + WIDTH - 2, y + FEATURE_HEIGHT, mouseX, mouseY)) {
/* 372 */         setting.setValue(ClientColor.GLOBAL_COLOR.getValue());
/*     */       } else {
/*     */         
/* 375 */         setting.setValue(newColor);
/*     */       } 
/* 377 */       return FEATURE_HEIGHT * 7;
/*     */     } 
/*     */     
/* 380 */     NineHack.TEXT_MANAGER.drawStringWithShadow("...", (x + WIDTH - NineHack.TEXT_MANAGER.getStringWidth("...") - 4), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2 - 2), FONT_COLOR.getRGB());
/* 381 */     return FEATURE_HEIGHT;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int drawColorSlider(String colorType, int colorValue, int x, int y, int mouseX, int mouseY) {
/* 386 */     int settingWidth = WIDTH - 2;
/*     */     
/* 388 */     int min = 0;
/* 389 */     int max = 255;
/*     */     
/* 391 */     if (leftDown && mouseHovering(x, y, x + settingWidth, y + FEATURE_HEIGHT, mouseX, mouseY)) {
/* 392 */       double percentError = (mouseX - x) * 100.0D / (x + settingWidth - x);
/* 393 */       double newVal = percentError * ((max - min) / 100.0F) + min;
/* 394 */       colorValue = (new BigDecimal(newVal)).setScale(1, (newVal > ((max - min) / 2)) ? RoundingMode.UP : RoundingMode.DOWN).intValue();
/*     */     } 
/*     */     
/* 397 */     int progress = (new BigDecimal((WIDTH - 2) * (colorValue - min) / (max - min))).setScale(2, RoundingMode.DOWN).intValue();
/*     */     
/* 399 */     if (mouseHovering(x, y, x + 3, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
/* 400 */       colorValue = min;
/* 401 */       progress = 0;
/*     */     } 
/* 403 */     if (mouseHovering(x + settingWidth - 1, y, x + settingWidth + 1, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
/* 404 */       colorValue = max;
/* 405 */       progress = settingWidth;
/*     */     } 
/*     */     
/* 408 */     RenderUtil.drawRect(x + 2, y - 1, x + settingWidth, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
/*     */     
/* 410 */     cHeight += ((Integer)ClientColor.step.getValue()).intValue();
/* 411 */     Color featC = ((Boolean)GUI.gradientFeatures.getValue()).booleanValue() ? new Color(((Integer)ClientColor.colorHeightMap.get(Integer.valueOf(cHeight))).intValue()) : ACCENT_COLOR;
/*     */     
/* 413 */     if (progress > 0) {
/* 414 */       RenderUtil.drawRect(x + 2, y - 1, x + progress, y + FEATURE_HEIGHT, featC);
/*     */     }
/*     */     
/* 417 */     NineHack.TEXT_MANAGER.drawStringWithShadow(colorType + ":", (x + 6), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
/* 418 */     NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(String.valueOf(colorValue)), (x + 6 + NineHack.TEXT_MANAGER.getStringWidth(colorType + ":") + 2), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());
/*     */     
/* 420 */     return colorValue;
/*     */   }
/*     */   
/*     */   private static void drawRainbowButton(Setting<Color> setting, int x, int y, int mouseX, int mouseY) {
/* 424 */     if (mouseHovering(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, mouseX, mouseY) && 
/* 425 */       leftClicked) setting.setRainbow(!setting.isRainbow());
/*     */ 
/*     */     
/* 428 */     char[] delimiters = { ' ', '_' };
/*     */     
/* 430 */     RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
/* 431 */     NineHack.TEXT_MANAGER.drawStringWithShadow("Rainbow:", (x + 6), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
/* 432 */     NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(String.valueOf(setting.isRainbow())), (x + 6 + NineHack.TEXT_MANAGER.getStringWidth("Rainbow:") + 2), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());
/*     */   }
/*     */   
/*     */   private static int drawIntegerSetting(NumberSetting<Integer> setting, int x, int y, int mouseX, int mouseY) {
/* 436 */     int settingWidth = WIDTH - 2;
/*     */     
/* 438 */     int min = ((Integer)setting.getMin()).intValue();
/* 439 */     int max = ((Integer)setting.getMax()).intValue();
/*     */     
/* 441 */     if (leftDown && mouseHovering(x, y, x + settingWidth, y + FEATURE_HEIGHT, mouseX, mouseY)) {
/* 442 */       double percentError = (mouseX - x) * 100.0D / (x + settingWidth - x);
/* 443 */       double newVal = percentError * ((max - min) / 100.0F) + min;
/* 444 */       setting.setValue(Integer.valueOf((new BigDecimal(newVal)).setScale(1, (newVal > ((max - min) / 2)) ? RoundingMode.UP : RoundingMode.DOWN).intValue()));
/*     */     } 
/*     */     
/* 447 */     int progress = (new BigDecimal((WIDTH - 2) * (((Integer)setting.getValue()).intValue() - ((Integer)setting.getMin()).intValue()) / (((Integer)setting.getMax()).intValue() - ((Integer)setting.getMin()).intValue()))).setScale(2, RoundingMode.DOWN).intValue();
/*     */     
/* 449 */     if (mouseHovering(x, y, x + 3, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
/* 450 */       setting.setValue(Integer.valueOf(min));
/* 451 */       progress = 0;
/*     */     } 
/* 453 */     if (mouseHovering(x + settingWidth - 1, y, x + settingWidth + 1, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
/* 454 */       setting.setValue(Integer.valueOf(max));
/* 455 */       progress = settingWidth;
/*     */     } 
/*     */     
/* 458 */     RenderUtil.drawRect(x + 2, y - 1, x + settingWidth, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
/*     */     
/* 460 */     cHeight += ((Integer)ClientColor.step.getValue()).intValue();
/* 461 */     Color featC = ((Boolean)GUI.gradientFeatures.getValue()).booleanValue() ? new Color(((Integer)ClientColor.colorHeightMap.get(Integer.valueOf(cHeight))).intValue()) : ACCENT_COLOR;
/*     */     
/* 463 */     if (progress > 0) {
/* 464 */       RenderUtil.drawRect(x + 2, y - 1, x + progress, y + FEATURE_HEIGHT, featC);
/*     */     }
/*     */     
/* 467 */     NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", (x + 6), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
/* 468 */     NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(((Integer)setting.getValue()).toString()), (x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());
/*     */     
/* 470 */     return FEATURE_HEIGHT;
/*     */   }
/*     */   
/*     */   private static int drawDoubleSetting(NumberSetting<Double> setting, int x, int y, int mouseX, int mouseY) {
/* 474 */     int settingWidth = WIDTH - 2;
/*     */     
/* 476 */     double min = ((Double)setting.getMin()).doubleValue();
/* 477 */     double max = ((Double)setting.getMax()).doubleValue();
/*     */     
/* 479 */     if (leftDown && mouseHovering(x, y, x + settingWidth, y + FEATURE_HEIGHT, mouseX, mouseY)) {
/* 480 */       double percentError = (mouseX - x) * 100.0D / (x + settingWidth - x);
/* 481 */       double newVal = percentError * (max - min) / 100.0D + min;
/* 482 */       setting.setValue(Double.valueOf((new BigDecimal(newVal)).setScale(1, (newVal > (max - min) / 2.0D) ? RoundingMode.UP : RoundingMode.DOWN).doubleValue()));
/*     */     } 
/*     */     
/* 485 */     int progress = (new BigDecimal((WIDTH - 2) * (((Double)setting.getValue()).doubleValue() - ((Double)setting.getMin()).doubleValue()) / (((Double)setting.getMax()).doubleValue() - ((Double)setting.getMin()).doubleValue()))).setScale(2, RoundingMode.DOWN).intValue();
/*     */     
/* 487 */     if (mouseHovering(x, y, x + 3, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
/* 488 */       setting.setValue(Double.valueOf(min));
/* 489 */       progress = 0;
/*     */     } 
/* 491 */     if (mouseHovering(x + settingWidth - 1, y, x + settingWidth + 1, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
/* 492 */       setting.setValue(Double.valueOf(max));
/* 493 */       progress = settingWidth;
/*     */     } 
/*     */     
/* 496 */     RenderUtil.drawRect(x + 2, y - 1, x + settingWidth, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
/*     */     
/* 498 */     cHeight += ((Integer)ClientColor.step.getValue()).intValue();
/* 499 */     Color featC = ((Boolean)GUI.gradientFeatures.getValue()).booleanValue() ? new Color(((Integer)ClientColor.colorHeightMap.get(Integer.valueOf(cHeight))).intValue()) : ACCENT_COLOR;
/*     */     
/* 501 */     if (progress > 0) {
/* 502 */       RenderUtil.drawRect(x + 2, y - 1, x + progress, y + FEATURE_HEIGHT, featC);
/*     */     }
/*     */     
/* 505 */     NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", (x + 6), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
/* 506 */     NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(((Double)setting.getValue()).toString()), (x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());
/*     */     
/* 508 */     return FEATURE_HEIGHT;
/*     */   }
/*     */   
/*     */   private static int drawFloatSetting(NumberSetting<Float> setting, int x, int y, int mouseX, int mouseY) {
/* 512 */     int settingWidth = WIDTH - 2;
/*     */     
/* 514 */     float min = ((Float)setting.getMin()).floatValue();
/* 515 */     float max = ((Float)setting.getMax()).floatValue();
/*     */     
/* 517 */     if (leftDown && mouseHovering(x, y, x + settingWidth, y + FEATURE_HEIGHT, mouseX, mouseY)) {
/* 518 */       double percentError = (mouseX - x) * 100.0D / (x + settingWidth - x);
/* 519 */       double newVal = percentError * ((max - min) / 100.0F) + min;
/* 520 */       setting.setValue(Float.valueOf((new BigDecimal(newVal)).setScale(1, (newVal > ((max - min) / 2.0F)) ? RoundingMode.UP : RoundingMode.DOWN).floatValue()));
/*     */     } 
/*     */     
/* 523 */     int progress = (new BigDecimal(((WIDTH - 2) * (((Float)setting.getValue()).floatValue() - ((Float)setting.getMin()).floatValue()) / (((Float)setting.getMax()).floatValue() - ((Float)setting.getMin()).floatValue())))).setScale(2, RoundingMode.DOWN).intValue();
/*     */     
/* 525 */     if (mouseHovering(x, y, x + 3, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
/* 526 */       setting.setValue(Float.valueOf(min));
/* 527 */       progress = 0;
/*     */     } 
/* 529 */     if (mouseHovering(x + settingWidth - 1, y, x + settingWidth + 1, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
/* 530 */       setting.setValue(Float.valueOf(max));
/* 531 */       progress = settingWidth;
/*     */     } 
/*     */     
/* 534 */     RenderUtil.drawRect(x + 2, y - 1, x + settingWidth, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
/*     */     
/* 536 */     cHeight += ((Integer)ClientColor.step.getValue()).intValue();
/* 537 */     Color featC = ((Boolean)GUI.gradientFeatures.getValue()).booleanValue() ? new Color(((Integer)ClientColor.colorHeightMap.get(Integer.valueOf(cHeight))).intValue()) : ACCENT_COLOR;
/*     */     
/* 539 */     if (progress > 0) {
/* 540 */       RenderUtil.drawRect(x + 2, y - 1, x + progress, y + FEATURE_HEIGHT, featC);
/*     */     }
/*     */     
/* 543 */     NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", (x + 6), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
/* 544 */     NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(((Float)setting.getValue()).toString()), (x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());
/*     */     
/* 546 */     return FEATURE_HEIGHT;
/*     */   }
/*     */   
/*     */   private static int drawBind(Feature feature, int key, int x, int y, int mouseX, int mouseY) {
/* 550 */     if (leftClicked && mouseHovering(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, mouseX, mouseY)) {
/* 551 */       feature.setBinding(!feature.isBinding());
/*     */     }
/* 553 */     if (feature.isBinding() && (key == 211 || key == 14)) {
/* 554 */       feature.setKey(0);
/* 555 */       feature.setBinding(false);
/*     */     } 
/*     */     
/* 558 */     if (feature.isBinding() && key == 1) {
/* 559 */       feature.setBinding(false);
/*     */     }
/*     */     
/* 562 */     if (feature.isBinding() && key != 0 && key != 211) {
/* 563 */       feature.setKey(key);
/* 564 */       feature.setBinding(false);
/*     */     } 
/* 566 */     RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
/*     */     
/* 568 */     String keyName = (feature.isBinding() == true) ? "..." : Keyboard.getKeyName(feature.getKey());
/* 569 */     NineHack.TEXT_MANAGER.drawStringWithShadow("Bind:", (x + 6), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
/* 570 */     NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(keyName), (x + 6 + NineHack.TEXT_MANAGER.getStringWidth("Bind:") + 2), (y + FEATURE_HEIGHT / 2 - NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());
/*     */ 
/*     */ 
/*     */     
/* 574 */     return FEATURE_HEIGHT;
/*     */   }
/*     */ 
/*     */   
/*     */   private static final boolean mouseHovering(int left, int top, int right, int bottom, int mouseX, int mouseY) {
/* 579 */     return (mouseX > left && mouseX < right && mouseY > top && mouseY < bottom);
/*     */   }
/*     */   
/*     */   private static int getSettingsHeight(Feature feature) {
/* 583 */     int boostY = 0;
/* 584 */     for (Setting<?> setting : (Iterable<Setting<?>>)feature.getSettings()) {
/* 585 */       if (setting.getValue() instanceof Color) {
/* 586 */         if (setting.isOpened()) {
/* 587 */           boostY += FEATURE_HEIGHT * 7;
/*     */         } else {
/*     */           
/* 590 */           boostY += FEATURE_HEIGHT;
/*     */         } 
/*     */       } else {
/*     */         
/* 594 */         boostY += FEATURE_HEIGHT;
/*     */       } 
/*     */       
/* 597 */       boostY++;
/*     */     } 
/* 599 */     return boostY;
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\gui\clickgui\ClickGUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */