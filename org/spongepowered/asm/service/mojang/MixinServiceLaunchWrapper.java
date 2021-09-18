/*     */ package org.spongepowered.asm.service.mojang;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.launchwrapper.IClassNameTransformer;
/*     */ import net.minecraft.launchwrapper.IClassTransformer;
/*     */ import net.minecraft.launchwrapper.ITweaker;
/*     */ import net.minecraft.launchwrapper.Launch;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.launch.GlobalProperties;
/*     */ import org.spongepowered.asm.lib.ClassReader;
/*     */ import org.spongepowered.asm.lib.ClassVisitor;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*     */ import org.spongepowered.asm.service.IClassBytecodeProvider;
/*     */ import org.spongepowered.asm.service.IClassProvider;
/*     */ import org.spongepowered.asm.service.ILegacyClassTransformer;
/*     */ import org.spongepowered.asm.service.IMixinService;
/*     */ import org.spongepowered.asm.service.ITransformer;
/*     */ import org.spongepowered.asm.util.ReEntranceLock;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MixinServiceLaunchWrapper
/*     */   implements IMixinService, IClassProvider, IClassBytecodeProvider
/*     */ {
/*     */   public static final String BLACKBOARD_KEY_TWEAKCLASSES = "TweakClasses";
/*     */   public static final String BLACKBOARD_KEY_TWEAKS = "Tweaks";
/*     */   private static final String LAUNCH_PACKAGE = "org.spongepowered.asm.launch.";
/*     */   private static final String MIXIN_PACKAGE = "org.spongepowered.asm.mixin.";
/*     */   private static final String STATE_TWEAKER = "org.spongepowered.asm.mixin.EnvironmentStateTweaker";
/*     */   private static final String TRANSFORMER_PROXY_CLASS = "org.spongepowered.asm.mixin.transformer.Proxy";
/*  79 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   private final LaunchClassLoaderUtil classLoaderUtil = new LaunchClassLoaderUtil(Launch.classLoader);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private final ReEntranceLock lock = new ReEntranceLock(1);
/*     */ 
/*     */ 
/*     */   
/*     */   private IClassNameTransformer nameTransformer;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  99 */     return "LaunchWrapper";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*     */     try {
/* 109 */       Launch.classLoader.hashCode();
/* 110 */     } catch (Throwable ex) {
/* 111 */       return false;
/*     */     } 
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepare() {
/* 122 */     Launch.classLoader.addClassLoaderExclusion("org.spongepowered.asm.launch.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MixinEnvironment.Phase getInitialPhase() {
/* 130 */     if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") > 132) {
/* 131 */       return MixinEnvironment.Phase.DEFAULT;
/*     */     }
/* 133 */     return MixinEnvironment.Phase.PREINIT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/* 141 */     if (findInStackTrace("net.minecraft.launchwrapper.Launch", "launch") < 4) {
/* 142 */       logger.error("MixinBootstrap.doInit() called during a tweak constructor!");
/*     */     }
/*     */     
/* 145 */     List<String> tweakClasses = (List<String>)GlobalProperties.get("TweakClasses");
/* 146 */     if (tweakClasses != null) {
/* 147 */       tweakClasses.add("org.spongepowered.asm.mixin.EnvironmentStateTweaker");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock getReEntranceLock() {
/* 156 */     return this.lock;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getPlatformAgents() {
/* 164 */     return (Collection<String>)ImmutableList.of("org.spongepowered.asm.launch.platform.MixinPlatformAgentFML");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IClassProvider getClassProvider() {
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IClassBytecodeProvider getBytecodeProvider() {
/* 182 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findClass(String name) throws ClassNotFoundException {
/* 191 */     return Launch.classLoader.findClass(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findClass(String name, boolean initialize) throws ClassNotFoundException {
/* 200 */     return Class.forName(name, initialize, (ClassLoader)Launch.classLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> findAgentClass(String name, boolean initialize) throws ClassNotFoundException {
/* 209 */     return Class.forName(name, initialize, Launch.class.getClassLoader());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginPhase() {
/* 217 */     Launch.classLoader.registerTransformer("org.spongepowered.asm.mixin.transformer.Proxy");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkEnv(Object bootSource) {
/* 226 */     if (bootSource.getClass().getClassLoader() != Launch.class.getClassLoader()) {
/* 227 */       throw new MixinException("Attempted to init the mixin environment in the wrong classloader");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputStream getResourceAsStream(String name) {
/* 237 */     return Launch.classLoader.getResourceAsStream(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerInvalidClass(String className) {
/* 246 */     this.classLoaderUtil.registerInvalidClass(className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClassLoaded(String className) {
/* 255 */     return this.classLoaderUtil.isClassLoaded(className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URL[] getClassPath() {
/* 263 */     return (URL[])Launch.classLoader.getSources().toArray((Object[])new URL[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<ITransformer> getTransformers() {
/* 271 */     List<IClassTransformer> transformers = Launch.classLoader.getTransformers();
/* 272 */     List<ITransformer> wrapped = new ArrayList<ITransformer>(transformers.size());
/* 273 */     for (IClassTransformer transformer : transformers) {
/* 274 */       if (transformer instanceof ITransformer) {
/* 275 */         wrapped.add((ITransformer)transformer);
/*     */       } else {
/* 277 */         wrapped.add(new LegacyTransformerHandle(transformer));
/*     */       } 
/*     */       
/* 280 */       if (transformer instanceof IClassNameTransformer) {
/* 281 */         logger.debug("Found name transformer: {}", new Object[] { transformer.getClass().getName() });
/* 282 */         this.nameTransformer = (IClassNameTransformer)transformer;
/*     */       } 
/*     */     } 
/*     */     
/* 286 */     return wrapped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getClassBytes(String name, String transformedName) throws IOException {
/* 295 */     byte[] classBytes = Launch.classLoader.getClassBytes(name);
/* 296 */     if (classBytes != null) {
/* 297 */       return classBytes;
/*     */     }
/*     */     
/* 300 */     URLClassLoader appClassLoader = (URLClassLoader)Launch.class.getClassLoader();
/*     */     
/* 302 */     InputStream classStream = null;
/*     */     try {
/* 304 */       String resourcePath = transformedName.replace('.', '/').concat(".class");
/* 305 */       classStream = appClassLoader.getResourceAsStream(resourcePath);
/* 306 */       return IOUtils.toByteArray(classStream);
/* 307 */     } catch (Exception ex) {
/* 308 */       return null;
/*     */     } finally {
/* 310 */       IOUtils.closeQuietly(classStream);
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
/*     */   public byte[] getClassBytes(String className, boolean runTransformers) throws ClassNotFoundException, IOException {
/* 326 */     String transformedName = className.replace('/', '.');
/* 327 */     String name = unmapClassName(transformedName);
/*     */     
/* 329 */     Profiler profiler = MixinEnvironment.getProfiler();
/* 330 */     Profiler.Section loadTime = profiler.begin(1, "class.load");
/* 331 */     byte[] classBytes = getClassBytes(name, transformedName);
/* 332 */     loadTime.end();
/*     */     
/* 334 */     if (runTransformers) {
/* 335 */       Profiler.Section transformTime = profiler.begin(1, "class.transform");
/* 336 */       classBytes = applyTransformers(name, transformedName, classBytes, profiler);
/* 337 */       transformTime.end();
/*     */     } 
/*     */     
/* 340 */     if (classBytes == null) {
/* 341 */       throw new ClassNotFoundException(String.format("The specified class '%s' was not found", new Object[] { transformedName }));
/*     */     }
/*     */     
/* 344 */     return classBytes;
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
/*     */   private byte[] applyTransformers(String name, String transformedName, byte[] basicClass, Profiler profiler) {
/* 358 */     if (this.classLoaderUtil.isClassExcluded(name, transformedName)) {
/* 359 */       return basicClass;
/*     */     }
/*     */     
/* 362 */     MixinEnvironment environment = MixinEnvironment.getCurrentEnvironment();
/*     */     
/* 364 */     for (ILegacyClassTransformer transformer : environment.getTransformers()) {
/*     */       
/* 366 */       this.lock.clear();
/*     */       
/* 368 */       int pos = transformer.getName().lastIndexOf('.');
/* 369 */       String simpleName = transformer.getName().substring(pos + 1);
/* 370 */       Profiler.Section transformTime = profiler.begin(2, simpleName.toLowerCase());
/* 371 */       transformTime.setInfo(transformer.getName());
/* 372 */       basicClass = transformer.transformClassBytes(name, transformedName, basicClass);
/* 373 */       transformTime.end();
/*     */       
/* 375 */       if (this.lock.isSet()) {
/*     */         
/* 377 */         environment.addTransformerExclusion(transformer.getName());
/*     */         
/* 379 */         this.lock.clear();
/* 380 */         logger.info("A re-entrant transformer '{}' was detected and will no longer process meta class data", new Object[] { transformer
/* 381 */               .getName() });
/*     */       } 
/*     */     } 
/*     */     
/* 385 */     return basicClass;
/*     */   }
/*     */   
/*     */   private String unmapClassName(String className) {
/* 389 */     if (this.nameTransformer == null) {
/* 390 */       findNameTransformer();
/*     */     }
/*     */     
/* 393 */     if (this.nameTransformer != null) {
/* 394 */       return this.nameTransformer.unmapClassName(className);
/*     */     }
/*     */     
/* 397 */     return className;
/*     */   }
/*     */   
/*     */   private void findNameTransformer() {
/* 401 */     List<IClassTransformer> transformers = Launch.classLoader.getTransformers();
/* 402 */     for (IClassTransformer transformer : transformers) {
/* 403 */       if (transformer instanceof IClassNameTransformer) {
/* 404 */         logger.debug("Found name transformer: {}", new Object[] { transformer.getClass().getName() });
/* 405 */         this.nameTransformer = (IClassNameTransformer)transformer;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassNode getClassNode(String className) throws ClassNotFoundException, IOException {
/* 416 */     return getClassNode(getClassBytes(className, true), 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassNode getClassNode(byte[] classBytes, int flags) {
/* 427 */     ClassNode classNode = new ClassNode();
/* 428 */     ClassReader classReader = new ClassReader(classBytes);
/* 429 */     classReader.accept((ClassVisitor)classNode, flags);
/* 430 */     return classNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getSideName() {
/* 440 */     for (ITweaker tweaker : GlobalProperties.get("Tweaks")) {
/* 441 */       if (tweaker.getClass().getName().endsWith(".common.launcher.FMLServerTweaker"))
/* 442 */         return "SERVER"; 
/* 443 */       if (tweaker.getClass().getName().endsWith(".common.launcher.FMLTweaker")) {
/* 444 */         return "CLIENT";
/*     */       }
/*     */     } 
/*     */     
/* 448 */     String name = getSideName("net.minecraftforge.fml.relauncher.FMLLaunchHandler", "side");
/* 449 */     if (name != null) {
/* 450 */       return name;
/*     */     }
/*     */     
/* 453 */     name = getSideName("cpw.mods.fml.relauncher.FMLLaunchHandler", "side");
/* 454 */     if (name != null) {
/* 455 */       return name;
/*     */     }
/*     */     
/* 458 */     name = getSideName("com.mumfrey.liteloader.launch.LiteLoaderTweaker", "getEnvironmentType");
/* 459 */     if (name != null) {
/* 460 */       return name;
/*     */     }
/*     */     
/* 463 */     return "UNKNOWN";
/*     */   }
/*     */   
/*     */   private String getSideName(String className, String methodName) {
/*     */     try {
/* 468 */       Class<?> clazz = Class.forName(className, false, (ClassLoader)Launch.classLoader);
/* 469 */       Method method = clazz.getDeclaredMethod(methodName, new Class[0]);
/* 470 */       return ((Enum)method.invoke(null, new Object[0])).name();
/* 471 */     } catch (Exception ex) {
/* 472 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int findInStackTrace(String className, String methodName) {
/* 477 */     Thread currentThread = Thread.currentThread();
/*     */     
/* 479 */     if (!"main".equals(currentThread.getName())) {
/* 480 */       return 0;
/*     */     }
/*     */     
/* 483 */     StackTraceElement[] stackTrace = currentThread.getStackTrace();
/* 484 */     for (StackTraceElement s : stackTrace) {
/* 485 */       if (className.equals(s.getClassName()) && methodName.equals(s.getMethodName())) {
/* 486 */         return s.getLineNumber();
/*     */       }
/*     */     } 
/*     */     
/* 490 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\service\mojang\MixinServiceLaunchWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */