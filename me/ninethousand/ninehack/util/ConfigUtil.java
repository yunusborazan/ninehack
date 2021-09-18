/*     */ package me.ninethousand.ninehack.util;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import java.awt.Color;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.ArrayList;
/*     */ import me.ninethousand.ninehack.feature.Feature;
/*     */ import me.ninethousand.ninehack.feature.setting.NumberSetting;
/*     */ import me.ninethousand.ninehack.feature.setting.Setting;
/*     */ 
/*     */ public final class ConfigUtil {
/*  19 */   private static final Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/*     */   
/*     */   public static void createDirectory() throws IOException {
/*  22 */     if (!Files.exists(Paths.get("ninehack/", new String[0]), new java.nio.file.LinkOption[0])) {
/*  23 */       Files.createDirectories(Paths.get("ninehack/", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
/*     */     }
/*     */     
/*  26 */     if (!Files.exists(Paths.get("ninehack/modules", new String[0]), new java.nio.file.LinkOption[0])) {
/*  27 */       Files.createDirectories(Paths.get("ninehack/modules", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
/*     */     }
/*     */     
/*  30 */     if (!Files.exists(Paths.get("ninehack/components", new String[0]), new java.nio.file.LinkOption[0])) {
/*  31 */       Files.createDirectories(Paths.get("ninehack/components", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
/*     */     }
/*     */     
/*  34 */     if (!Files.exists(Paths.get("ninehack/gui", new String[0]), new java.nio.file.LinkOption[0])) {
/*  35 */       Files.createDirectories(Paths.get("ninehack/gui", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
/*     */     }
/*     */     
/*  38 */     if (!Files.exists(Paths.get("ninehack/social", new String[0]), new java.nio.file.LinkOption[0])) {
/*  39 */       Files.createDirectories(Paths.get("ninehack/social", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void registerFiles(String name, String path) throws IOException {
/*  44 */     if (Files.exists(Paths.get("ninehack/" + path + "/" + name + ".json", new String[0]), new java.nio.file.LinkOption[0])) {
/*  45 */       File file = new File("ninehack/" + path + "/" + name + ".json");
/*  46 */       file.delete();
/*     */     } 
/*     */     
/*  49 */     Files.createFile(Paths.get("ninehack/" + path + "/" + name + ".json", new String[0]), (FileAttribute<?>[])new FileAttribute[0]);
/*     */   }
/*     */   
/*     */   public static void saveConfig() {
/*     */     try {
/*  54 */       saveModules();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  60 */       savePrefix();
/*  61 */     } catch (IOException exception) {
/*  62 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void loadConfig() {
/*     */     try {
/*  68 */       createDirectory();
/*  69 */       loadModules();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  75 */       loadPrefix();
/*  76 */     } catch (IOException exception) {
/*  77 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void saveModules() throws IOException {
/*  82 */     for (Feature feature : FeatureManager.getFeatures()) {
/*  83 */       registerFiles(feature.getName(), "modules");
/*  84 */       OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("ninehack/modules/" + feature.getName() + ".json"), StandardCharsets.UTF_8);
/*     */       
/*  86 */       JsonObject moduleObject = new JsonObject();
/*  87 */       JsonObject settingObject = new JsonObject();
/*  88 */       JsonObject subSettingObject = new JsonObject();
/*     */       
/*  90 */       moduleObject.add("Name", (JsonElement)new JsonPrimitive(feature.getName()));
/*  91 */       moduleObject.add("Enabled", (JsonElement)new JsonPrimitive(Boolean.valueOf(feature.isEnabled())));
/*  92 */       moduleObject.add("Drawn", (JsonElement)new JsonPrimitive(Boolean.valueOf(feature.isDrawn())));
/*  93 */       moduleObject.add("Bind", (JsonElement)new JsonPrimitive(Integer.valueOf(feature.getKey())));
/*     */       
/*  95 */       for (Setting<?> setting : (Iterable<Setting<?>>)feature.getSettings()) {
/*  96 */         if (setting.getValue() instanceof Boolean) {
/*  97 */           Setting<Boolean> booleanSetting = (Setting)setting;
/*  98 */           settingObject.add(booleanSetting.getName(), (JsonElement)new JsonPrimitive((Boolean)booleanSetting.getValue()));
/*     */           
/* 100 */           if (booleanSetting.hasSubSettings()) {
/* 101 */             for (Setting<?> subSetting : (Iterable<Setting<?>>)booleanSetting.getSubSettings()) {
/* 102 */               if (subSetting.getValue() instanceof Boolean) {
/* 103 */                 Setting<Boolean> subBooleanSetting = (Setting)subSetting;
/* 104 */                 subSettingObject.add(subBooleanSetting.getName(), (JsonElement)new JsonPrimitive((Boolean)subBooleanSetting.getValue()));
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 110 */         if (setting.getValue() instanceof Enum) {
/* 111 */           Setting<Enum<?>> enumSetting = (Setting)setting;
/* 112 */           settingObject.add(enumSetting.getName(), (JsonElement)new JsonPrimitive(String.valueOf(enumSetting.getValue())));
/*     */           
/* 114 */           if (enumSetting.hasSubSettings()) {
/* 115 */             for (Setting<?> subSetting : (Iterable<Setting<?>>)enumSetting.getSubSettings()) {
/* 116 */               if (subSetting.getValue() instanceof Boolean) {
/* 117 */                 Setting<Boolean> subBooleanSetting = (Setting)subSetting;
/* 118 */                 subSettingObject.add(subBooleanSetting.getName(), (JsonElement)new JsonPrimitive((Boolean)subBooleanSetting.getValue()));
/*     */               } 
/*     */               
/* 121 */               if (subSetting.getValue() instanceof Enum) {
/* 122 */                 Setting<Enum<?>> subEnumSetting = (Setting)subSetting;
/* 123 */                 subSettingObject.add(subEnumSetting.getName(), (JsonElement)new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
/*     */               } 
/*     */               
/* 126 */               if (subSetting.getValue() instanceof Color) {
/* 127 */                 Setting<Color> subColourSetting = (Setting)subSetting;
/*     */                 
/* 129 */                 JsonObject subColourObject = new JsonObject();
/*     */                 
/* 131 */                 subColourObject.add("Red", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getRed())));
/* 132 */                 subColourObject.add("Green", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getGreen())));
/* 133 */                 subColourObject.add("Blue", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getBlue())));
/* 134 */                 subColourObject.add("Alpha", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getAlpha())));
/*     */                 
/* 136 */                 subSettingObject.add(subColourSetting.getName(), (JsonElement)subColourObject);
/*     */               } 
/*     */               
/* 139 */               if (subSetting.getValue() instanceof Integer) {
/* 140 */                 NumberSetting<Integer> subIntegerSetting = (NumberSetting)subSetting;
/* 141 */                 subSettingObject.add(subIntegerSetting.getName(), (JsonElement)new JsonPrimitive((Number)subIntegerSetting.getValue()));
/*     */               } 
/*     */               
/* 144 */               if (subSetting.getValue() instanceof Double) {
/* 145 */                 NumberSetting<Double> subDoubleSetting = (NumberSetting)subSetting;
/* 146 */                 subSettingObject.add(subDoubleSetting.getName(), (JsonElement)new JsonPrimitive((Number)subDoubleSetting.getValue()));
/*     */               } 
/*     */               
/* 149 */               if (subSetting.getValue() instanceof Float) {
/* 150 */                 NumberSetting<Float> subFloatSetting = (NumberSetting)subSetting;
/* 151 */                 subSettingObject.add(subFloatSetting.getName(), (JsonElement)new JsonPrimitive((Number)subFloatSetting.getValue()));
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 157 */         if (setting.getValue() instanceof Integer) {
/* 158 */           NumberSetting<Integer> integerSetting = (NumberSetting)setting;
/* 159 */           settingObject.add(integerSetting.getName(), (JsonElement)new JsonPrimitive((Number)integerSetting.getValue()));
/*     */           
/* 161 */           if (integerSetting.hasSubSettings()) {
/* 162 */             for (Setting<?> subSetting : (Iterable<Setting<?>>)integerSetting.getSubSettings()) {
/* 163 */               if (subSetting.getValue() instanceof Boolean) {
/* 164 */                 Setting<Boolean> subBooleanSetting = (Setting)subSetting;
/* 165 */                 subSettingObject.add(subBooleanSetting.getName(), (JsonElement)new JsonPrimitive((Boolean)subBooleanSetting.getValue()));
/*     */               } 
/*     */               
/* 168 */               if (subSetting.getValue() instanceof Enum) {
/* 169 */                 Setting<Enum<?>> subEnumSetting = (Setting)subSetting;
/* 170 */                 subSettingObject.add(subEnumSetting.getName(), (JsonElement)new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
/*     */               } 
/*     */               
/* 173 */               if (subSetting.getValue() instanceof Color) {
/* 174 */                 Setting<Color> subColourSetting = (Setting)subSetting;
/*     */                 
/* 176 */                 JsonObject subColourObject = new JsonObject();
/*     */                 
/* 178 */                 subColourObject.add("Red", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getRed())));
/* 179 */                 subColourObject.add("Green", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getGreen())));
/* 180 */                 subColourObject.add("Blue", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getBlue())));
/* 181 */                 subColourObject.add("Alpha", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getAlpha())));
/*     */                 
/* 183 */                 subSettingObject.add(subColourSetting.getName(), (JsonElement)subColourObject);
/*     */               } 
/*     */               
/* 186 */               if (subSetting.getValue() instanceof Integer) {
/* 187 */                 NumberSetting<Integer> subIntegerSetting = (NumberSetting)subSetting;
/* 188 */                 subSettingObject.add(subIntegerSetting.getName(), (JsonElement)new JsonPrimitive((Number)subIntegerSetting.getValue()));
/*     */               } 
/*     */               
/* 191 */               if (subSetting.getValue() instanceof Double) {
/* 192 */                 NumberSetting<Double> subDoubleSetting = (NumberSetting)subSetting;
/* 193 */                 subSettingObject.add(subDoubleSetting.getName(), (JsonElement)new JsonPrimitive((Number)subDoubleSetting.getValue()));
/*     */               } 
/*     */               
/* 196 */               if (subSetting.getValue() instanceof Float) {
/* 197 */                 NumberSetting<Float> subFloatSetting = (NumberSetting)subSetting;
/* 198 */                 subSettingObject.add(subFloatSetting.getName(), (JsonElement)new JsonPrimitive((Number)subFloatSetting.getValue()));
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 204 */         if (setting.getValue() instanceof Double) {
/* 205 */           NumberSetting<Double> doubleSetting = (NumberSetting)setting;
/* 206 */           settingObject.add(doubleSetting.getName(), (JsonElement)new JsonPrimitive((Number)doubleSetting.getValue()));
/*     */           
/* 208 */           if (doubleSetting.hasSubSettings()) {
/* 209 */             for (Setting<?> subSetting : (Iterable<Setting<?>>)doubleSetting.getSubSettings()) {
/* 210 */               if (subSetting.getValue() instanceof Boolean) {
/* 211 */                 Setting<Boolean> subBooleanSetting = (Setting)subSetting;
/* 212 */                 subSettingObject.add(subBooleanSetting.getName(), (JsonElement)new JsonPrimitive((Boolean)subBooleanSetting.getValue()));
/*     */               } 
/*     */               
/* 215 */               if (subSetting.getValue() instanceof Enum) {
/* 216 */                 Setting<Enum<?>> subEnumSetting = (Setting)subSetting;
/* 217 */                 subSettingObject.add(subEnumSetting.getName(), (JsonElement)new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
/*     */               } 
/*     */               
/* 220 */               if (subSetting.getValue() instanceof Color) {
/* 221 */                 Setting<Color> subColourSetting = (Setting)subSetting;
/*     */                 
/* 223 */                 JsonObject subColourObject = new JsonObject();
/*     */                 
/* 225 */                 subColourObject.add("Red", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getRed())));
/* 226 */                 subColourObject.add("Green", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getGreen())));
/* 227 */                 subColourObject.add("Blue", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getBlue())));
/* 228 */                 subColourObject.add("Alpha", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getAlpha())));
/*     */                 
/* 230 */                 subSettingObject.add(subColourSetting.getName(), (JsonElement)subColourObject);
/*     */               } 
/*     */               
/* 233 */               if (subSetting.getValue() instanceof Integer) {
/* 234 */                 NumberSetting<Integer> subIntegerSetting = (NumberSetting)subSetting;
/* 235 */                 subSettingObject.add(subIntegerSetting.getName(), (JsonElement)new JsonPrimitive((Number)subIntegerSetting.getValue()));
/*     */               } 
/*     */               
/* 238 */               if (subSetting.getValue() instanceof Double) {
/* 239 */                 NumberSetting<Double> subDoubleSetting = (NumberSetting)subSetting;
/* 240 */                 subSettingObject.add(subDoubleSetting.getName(), (JsonElement)new JsonPrimitive((Number)subDoubleSetting.getValue()));
/*     */               } 
/*     */               
/* 243 */               if (subSetting.getValue() instanceof Float) {
/* 244 */                 NumberSetting<Float> subFloatSetting = (NumberSetting)subSetting;
/* 245 */                 subSettingObject.add(subFloatSetting.getName(), (JsonElement)new JsonPrimitive((Number)subFloatSetting.getValue()));
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 251 */         if (setting.getValue() instanceof Float) {
/* 252 */           NumberSetting<Float> floatSetting = (NumberSetting)setting;
/* 253 */           settingObject.add(floatSetting.getName(), (JsonElement)new JsonPrimitive((Number)floatSetting.getValue()));
/*     */           
/* 255 */           if (floatSetting.hasSubSettings()) {
/* 256 */             for (Setting<?> subSetting : (Iterable<Setting<?>>)floatSetting.getSubSettings()) {
/* 257 */               if (subSetting.getValue() instanceof Boolean) {
/* 258 */                 Setting<Boolean> subBooleanSetting = (Setting)subSetting;
/* 259 */                 subSettingObject.add(subBooleanSetting.getName(), (JsonElement)new JsonPrimitive((Boolean)subBooleanSetting.getValue()));
/*     */               } 
/*     */               
/* 262 */               if (subSetting.getValue() instanceof Enum) {
/* 263 */                 Setting<Enum<?>> subEnumSetting = (Setting)subSetting;
/* 264 */                 subSettingObject.add(subEnumSetting.getName(), (JsonElement)new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
/*     */               } 
/*     */               
/* 267 */               if (subSetting.getValue() instanceof Color) {
/* 268 */                 Setting<Color> subColourSetting = (Setting)subSetting;
/*     */                 
/* 270 */                 JsonObject subColourObject = new JsonObject();
/*     */                 
/* 272 */                 subColourObject.add("Red", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getRed())));
/* 273 */                 subColourObject.add("Green", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getGreen())));
/* 274 */                 subColourObject.add("Blue", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getBlue())));
/* 275 */                 subColourObject.add("Alpha", (JsonElement)new JsonPrimitive(Integer.valueOf(((Color)subColourSetting.getValue()).getAlpha())));
/*     */                 
/* 277 */                 subSettingObject.add(subColourSetting.getName(), (JsonElement)subColourObject);
/*     */               } 
/*     */               
/* 280 */               if (subSetting.getValue() instanceof Integer) {
/* 281 */                 NumberSetting<Integer> subIntegerSetting = (NumberSetting)subSetting;
/* 282 */                 subSettingObject.add(subIntegerSetting.getName(), (JsonElement)new JsonPrimitive((Number)subIntegerSetting.getValue()));
/*     */               } 
/*     */               
/* 285 */               if (subSetting.getValue() instanceof Double) {
/* 286 */                 NumberSetting<Double> subDoubleSetting = (NumberSetting)subSetting;
/* 287 */                 subSettingObject.add(subDoubleSetting.getName(), (JsonElement)new JsonPrimitive((Number)subDoubleSetting.getValue()));
/*     */               } 
/*     */               
/* 290 */               if (subSetting.getValue() instanceof Float) {
/* 291 */                 NumberSetting<Float> subFloatSetting = (NumberSetting)subSetting;
/* 292 */                 subSettingObject.add(subFloatSetting.getName(), (JsonElement)new JsonPrimitive((Number)subFloatSetting.getValue()));
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 298 */         if (setting.getValue() instanceof Color) {
/* 299 */           Setting<Color> colorSetting = (Setting)setting;
/* 300 */           ArrayList<Integer> arr = new ArrayList<>();
/* 301 */           arr.addAll(Arrays.asList(new Integer[] {
/* 302 */                   Integer.valueOf(((Color)colorSetting.getValue()).getRed()), 
/* 303 */                   Integer.valueOf(((Color)colorSetting.getValue()).getGreen()), 
/* 304 */                   Integer.valueOf(((Color)colorSetting.getValue()).getBlue()), 
/* 305 */                   Integer.valueOf(((Color)colorSetting.getValue()).getAlpha())
/*     */                 }));
/*     */           
/* 308 */           settingObject.add(colorSetting.getName(), (JsonElement)new JsonPrimitive(String.valueOf(arr)));
/*     */         } 
/*     */       } 
/*     */       
/* 312 */       moduleObject.add("Settings", (JsonElement)settingObject);
/* 313 */       settingObject.add("SubSettings", (JsonElement)subSettingObject);
/*     */       
/* 315 */       String jsonString = gson.toJson((new JsonParser()).parse(moduleObject.toString()));
/*     */       
/* 317 */       fileOutputStreamWriter.write(jsonString);
/* 318 */       fileOutputStreamWriter.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void loadModules() throws IOException {
/* 323 */     for (Feature feature : FeatureManager.getFeatures()) {
/* 324 */       if (!Files.exists(Paths.get("ninehack/modules/" + feature.getName() + ".json", new String[0]), new java.nio.file.LinkOption[0]))
/*     */         continue; 
/* 326 */       InputStream inputStream = Files.newInputStream(Paths.get("ninehack/modules/" + feature.getName() + ".json", new String[0]), new java.nio.file.OpenOption[0]);
/* 327 */       JsonObject moduleObject = (new JsonParser()).parse(new InputStreamReader(inputStream)).getAsJsonObject();
/*     */       
/* 329 */       if (moduleObject.get("Name") == null || moduleObject.get("Enabled") == null || moduleObject.get("Drawn") == null || moduleObject.get("Bind") == null)
/*     */         continue; 
/* 331 */       JsonObject settingObject = moduleObject.get("Settings").getAsJsonObject();
/* 332 */       JsonObject subSettingObject = settingObject.get("SubSettings").getAsJsonObject();
/*     */       
/* 334 */       for (Setting<?> setting : (Iterable<Setting<?>>)feature.getSettings()) {
/* 335 */         JsonElement settingValueObject = null;
/*     */         
/* 337 */         if (setting.getValue() instanceof Boolean) {
/* 338 */           Setting<Boolean> booleanSetting = (Setting)setting;
/* 339 */           settingValueObject = settingObject.get(booleanSetting.getName());
/*     */         } 
/*     */ 
/*     */         
/* 343 */         if (setting.getValue() instanceof Enum) {
/* 344 */           Setting<Enum<?>> enumSetting = (Setting)setting;
/* 345 */           settingValueObject = settingObject.get(enumSetting.getName());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 350 */         if (setting.getValue() instanceof Integer) {
/* 351 */           NumberSetting<Integer> integerSetting = (NumberSetting)setting;
/* 352 */           settingValueObject = settingObject.get(integerSetting.getName());
/*     */         } 
/*     */ 
/*     */         
/* 356 */         if (setting.getValue() instanceof Double) {
/* 357 */           NumberSetting<Double> doubleSetting = (NumberSetting)setting;
/* 358 */           settingValueObject = settingObject.get(doubleSetting.getName());
/*     */         } 
/*     */         
/* 361 */         if (setting.getValue() instanceof Float) {
/* 362 */           NumberSetting<Float> floatSetting = (NumberSetting)setting;
/* 363 */           settingValueObject = settingObject.get(floatSetting.getName());
/*     */         } 
/*     */         
/* 366 */         if (setting.getValue() instanceof Color) {
/* 367 */           Setting<Color> colorSetting = (Setting)setting;
/* 368 */           settingValueObject = settingObject.get(colorSetting.getName());
/*     */         } 
/*     */         
/* 371 */         if (settingValueObject != null) {
/* 372 */           if (setting.getValue() instanceof Boolean) {
/* 373 */             Setting<Boolean> booleanSetting = (Setting)setting;
/* 374 */             booleanSetting.setValue(Boolean.valueOf(settingValueObject.getAsBoolean()));
/*     */           } 
/*     */           
/* 377 */           if (setting.getValue() instanceof Enum) {
/* 378 */             Setting<Enum<?>> enumSetting = (Setting)setting;
/* 379 */             EnumUtil.setEnumValue(enumSetting, settingValueObject.getAsString());
/*     */           } 
/*     */           
/* 382 */           if (setting.getValue() instanceof Integer) {
/* 383 */             NumberSetting<Integer> integerSetting = (NumberSetting)setting;
/* 384 */             integerSetting.setValue(Integer.valueOf(settingValueObject.getAsInt()));
/*     */           } 
/*     */           
/* 387 */           if (setting.getValue() instanceof Double) {
/* 388 */             NumberSetting<Double> doubleSetting = (NumberSetting)setting;
/* 389 */             doubleSetting.setValue(Double.valueOf(settingValueObject.getAsDouble()));
/*     */           } 
/*     */           
/* 392 */           if (setting.getValue() instanceof Float) {
/* 393 */             NumberSetting<Float> floatSetting = (NumberSetting)setting;
/* 394 */             floatSetting.setValue(Float.valueOf(settingValueObject.getAsFloat()));
/*     */           } 
/*     */           
/* 397 */           if (setting.getValue() instanceof Color) {
/* 398 */             Setting<Color> colorSetting = (Setting)setting;
/* 399 */             String value = settingValueObject.getAsString().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "");
/* 400 */             String[] values = value.split(",");
/* 401 */             colorSetting.setValue(new Color(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3])));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 407 */       feature.setEnabled(moduleObject.get("Enabled").getAsBoolean());
/* 408 */       feature.setDrawn(moduleObject.get("Drawn").getAsBoolean());
/* 409 */       feature.setKey(moduleObject.get("Bind").getAsInt());
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
/*     */   public static void savePrefix() throws IOException {
/* 608 */     File prefixFile = new File("ninehack/Prefix.txt");
/*     */     
/* 610 */     if (!prefixFile.exists()) {
/* 611 */       prefixFile.createNewFile();
/*     */     }
/*     */     
/* 614 */     OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(prefixFile), StandardCharsets.UTF_8);
/*     */     
/* 616 */     fileOutputStreamWriter.write(NineHack.CHAT_PREFIX);
/* 617 */     fileOutputStreamWriter.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadPrefix() throws IOException {
/* 622 */     if (!Files.exists(Paths.get("ninehack/Prefix.txt", new String[0]), new java.nio.file.LinkOption[0]))
/*     */       return; 
/* 624 */     BufferedReader reader = new BufferedReader(new FileReader("ninehack/Prefix.txt"));
/*     */     
/* 626 */     NineHack.CHAT_PREFIX = reader.readLine();
/* 627 */     reader.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\ConfigUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */