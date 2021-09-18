/*     */ package org.spongepowered.asm.mixin.transformer.ext.extensions;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IDecompiler;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IExtension;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
/*     */ import org.spongepowered.asm.util.Constants;
/*     */ import org.spongepowered.asm.util.perf.Profiler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtensionClassExporter
/*     */   implements IExtension
/*     */ {
/*     */   private static final String DECOMPILER_CLASS = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler";
/*     */   private static final String EXPORT_CLASS_DIR = "class";
/*     */   private static final String EXPORT_JAVA_DIR = "java";
/*  56 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private final File classExportDir = new File(Constants.DEBUG_OUTPUT_DIR, "class");
/*     */ 
/*     */   
/*     */   private final IDecompiler decompiler;
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtensionClassExporter(MixinEnvironment env) {
/*  69 */     this.decompiler = initDecompiler(env, new File(Constants.DEBUG_OUTPUT_DIR, "java"));
/*     */     
/*     */     try {
/*  72 */       FileUtils.deleteDirectory(this.classExportDir);
/*  73 */     } catch (IOException ex) {
/*  74 */       logger.warn("Error cleaning class output directory: {}", new Object[] { ex.getMessage() });
/*     */     } 
/*     */   }
/*     */   
/*     */   private IDecompiler initDecompiler(MixinEnvironment env, File outputPath) {
/*  79 */     if (!env.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE)) {
/*  80 */       return null;
/*     */     }
/*     */     
/*     */     try {
/*  84 */       boolean as = env.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_THREADED);
/*  85 */       logger.info("Attempting to load Fernflower decompiler{}", new Object[] { as ? " (Threaded mode)" : "" });
/*  86 */       String className = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler" + (as ? "Async" : "");
/*     */       
/*  88 */       Class<? extends IDecompiler> clazz = (Class)Class.forName(className);
/*  89 */       Constructor<? extends IDecompiler> ctor = clazz.getDeclaredConstructor(new Class[] { File.class });
/*  90 */       IDecompiler decompiler = ctor.newInstance(new Object[] { outputPath });
/*  91 */       logger.info("Fernflower decompiler was successfully initialised, exported classes will be decompiled{}", new Object[] { as ? " in a separate thread" : "" });
/*     */       
/*  93 */       return decompiler;
/*  94 */     } catch (Throwable th) {
/*  95 */       logger.info("Fernflower could not be loaded, exported classes will not be decompiled. {}: {}", new Object[] { th
/*  96 */             .getClass().getSimpleName(), th.getMessage() });
/*     */       
/*  98 */       return null;
/*     */     } 
/*     */   }
/*     */   private String prepareFilter(String filter) {
/* 102 */     filter = "^\\Q" + filter.replace("**", "").replace("*", "").replace("?", "") + "\\E$";
/* 103 */     return filter.replace("", "\\E.*\\Q").replace("", "\\E[^\\.]+\\Q").replace("", "\\E.\\Q").replace("\\Q\\E", "");
/*     */   }
/*     */   
/*     */   private boolean applyFilter(String filter, String subject) {
/* 107 */     return Pattern.compile(prepareFilter(filter), 2).matcher(subject).matches();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkActive(MixinEnvironment environment) {
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void preApply(ITargetClassContext context) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void postApply(ITargetClassContext context) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void export(MixinEnvironment env, String name, boolean force, byte[] bytes) {
/* 126 */     if (force || env.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
/* 127 */       String filter = env.getOptionValue(MixinEnvironment.Option.DEBUG_EXPORT_FILTER);
/* 128 */       if (force || filter == null || applyFilter(filter, name)) {
/* 129 */         Profiler.Section exportTimer = MixinEnvironment.getProfiler().begin("debug.export");
/* 130 */         File outputFile = dumpClass(name.replace('.', '/'), bytes);
/* 131 */         if (this.decompiler != null) {
/* 132 */           this.decompiler.decompile(outputFile);
/*     */         }
/* 134 */         exportTimer.end();
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
/*     */   public File dumpClass(String fileName, byte[] bytes) {
/* 147 */     File outputFile = new File(this.classExportDir, fileName + ".class");
/*     */     try {
/* 149 */       FileUtils.writeByteArrayToFile(outputFile, bytes);
/* 150 */     } catch (IOException iOException) {}
/*     */ 
/*     */     
/* 153 */     return outputFile;
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\transformer\ext\extensions\ExtensionClassExporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */