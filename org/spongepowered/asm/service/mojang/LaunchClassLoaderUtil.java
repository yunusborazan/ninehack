/*     */ package org.spongepowered.asm.service.mojang;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.launchwrapper.LaunchClassLoader;
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
/*     */ final class LaunchClassLoaderUtil
/*     */ {
/*     */   private static final String CACHED_CLASSES_FIELD = "cachedClasses";
/*     */   private static final String INVALID_CLASSES_FIELD = "invalidClasses";
/*     */   private static final String CLASS_LOADER_EXCEPTIONS_FIELD = "classLoaderExceptions";
/*     */   private static final String TRANSFORMER_EXCEPTIONS_FIELD = "transformerExceptions";
/*     */   private final LaunchClassLoader classLoader;
/*     */   private final Map<String, Class<?>> cachedClasses;
/*     */   private final Set<String> invalidClasses;
/*     */   private final Set<String> classLoaderExceptions;
/*     */   private final Set<String> transformerExceptions;
/*     */   
/*     */   LaunchClassLoaderUtil(LaunchClassLoader classLoader) {
/*  64 */     this.classLoader = classLoader;
/*  65 */     this.cachedClasses = getField(classLoader, "cachedClasses");
/*  66 */     this.invalidClasses = getField(classLoader, "invalidClasses");
/*  67 */     this.classLoaderExceptions = getField(classLoader, "classLoaderExceptions");
/*  68 */     this.transformerExceptions = getField(classLoader, "transformerExceptions");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   LaunchClassLoader getClassLoader() {
/*  75 */     return this.classLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isClassLoaded(String name) {
/*  86 */     return this.cachedClasses.containsKey(name);
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
/*     */   boolean isClassExcluded(String name, String transformedName) {
/*  98 */     for (String exception : getClassLoaderExceptions()) {
/*  99 */       if (transformedName.startsWith(exception) || name.startsWith(exception)) {
/* 100 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 104 */     for (String exception : getTransformerExceptions()) {
/* 105 */       if (transformedName.startsWith(exception) || name.startsWith(exception)) {
/* 106 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void registerInvalidClass(String name) {
/* 121 */     if (this.invalidClasses != null) {
/* 122 */       this.invalidClasses.add(name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Set<String> getClassLoaderExceptions() {
/* 130 */     if (this.classLoaderExceptions != null) {
/* 131 */       return this.classLoaderExceptions;
/*     */     }
/* 133 */     return Collections.emptySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Set<String> getTransformerExceptions() {
/* 140 */     if (this.transformerExceptions != null) {
/* 141 */       return this.transformerExceptions;
/*     */     }
/* 143 */     return Collections.emptySet();
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T getField(LaunchClassLoader classLoader, String fieldName) {
/*     */     try {
/* 149 */       Field field = LaunchClassLoader.class.getDeclaredField(fieldName);
/* 150 */       field.setAccessible(true);
/* 151 */       return (T)field.get(classLoader);
/* 152 */     } catch (Exception ex) {
/* 153 */       ex.printStackTrace();
/*     */       
/* 155 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\service\mojang\LaunchClassLoaderUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */