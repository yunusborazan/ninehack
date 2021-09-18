/*      */ package org.spongepowered.asm.mixin;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.Sets;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.apache.logging.log4j.core.Appender;
/*      */ import org.apache.logging.log4j.core.Filter;
/*      */ import org.apache.logging.log4j.core.Layout;
/*      */ import org.apache.logging.log4j.core.LogEvent;
/*      */ import org.apache.logging.log4j.core.Logger;
/*      */ import org.apache.logging.log4j.core.appender.AbstractAppender;
/*      */ import org.spongepowered.asm.launch.GlobalProperties;
/*      */ import org.spongepowered.asm.mixin.extensibility.IEnvironmentTokenProvider;
/*      */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*      */ import org.spongepowered.asm.mixin.transformer.MixinTransformer;
/*      */ import org.spongepowered.asm.obfuscation.RemapperChain;
/*      */ import org.spongepowered.asm.service.ILegacyClassTransformer;
/*      */ import org.spongepowered.asm.service.IMixinService;
/*      */ import org.spongepowered.asm.service.ITransformer;
/*      */ import org.spongepowered.asm.service.MixinService;
/*      */ import org.spongepowered.asm.util.ITokenProvider;
/*      */ import org.spongepowered.asm.util.JavaVersion;
/*      */ import org.spongepowered.asm.util.PrettyPrinter;
/*      */ import org.spongepowered.asm.util.perf.Profiler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class MixinEnvironment
/*      */   implements ITokenProvider
/*      */ {
/*      */   public static final class Phase
/*      */   {
/*   78 */     static final Phase NOT_INITIALISED = new Phase(-1, "NOT_INITIALISED");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   84 */     public static final Phase PREINIT = new Phase(0, "PREINIT");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   89 */     public static final Phase INIT = new Phase(1, "INIT");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   94 */     public static final Phase DEFAULT = new Phase(2, "DEFAULT");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   99 */     static final List<Phase> phases = (List<Phase>)ImmutableList.of(PREINIT, INIT, DEFAULT);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final int ordinal;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     final String name;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private MixinEnvironment environment;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Phase(int ordinal, String name) {
/*  121 */       this.ordinal = ordinal;
/*  122 */       this.name = name;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  127 */       return this.name;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public static Phase forName(String name) {
/*  138 */       for (Phase phase : phases) {
/*  139 */         if (phase.name.equals(name)) {
/*  140 */           return phase;
/*      */         }
/*      */       } 
/*  143 */       return null;
/*      */     }
/*      */     
/*      */     MixinEnvironment getEnvironment() {
/*  147 */       if (this.ordinal < 0) {
/*  148 */         throw new IllegalArgumentException("Cannot access the NOT_INITIALISED environment");
/*      */       }
/*      */       
/*  151 */       if (this.environment == null) {
/*  152 */         this.environment = new MixinEnvironment(this);
/*      */       }
/*      */       
/*  155 */       return this.environment;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Side
/*      */   {
/*  167 */     UNKNOWN
/*      */     {
/*      */       protected boolean detect() {
/*  170 */         return false;
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  177 */     CLIENT
/*      */     {
/*      */       protected boolean detect() {
/*  180 */         String sideName = MixinService.getService().getSideName();
/*  181 */         return "CLIENT".equals(sideName);
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  188 */     SERVER
/*      */     {
/*      */       protected boolean detect() {
/*  191 */         String sideName = MixinService.getService().getSideName();
/*  192 */         return ("SERVER".equals(sideName) || "DEDICATEDSERVER".equals(sideName));
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected abstract boolean detect();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Option
/*      */   {
/*  207 */     DEBUG_ALL("debug"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  214 */     DEBUG_EXPORT((String)DEBUG_ALL, "export"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  229 */     DEBUG_EXPORT_FILTER((String)DEBUG_EXPORT, "filter", false),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  234 */     DEBUG_EXPORT_DECOMPILE((String)DEBUG_EXPORT, Inherit.ALLOW_OVERRIDE, (Option)"decompile"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  242 */     DEBUG_EXPORT_DECOMPILE_THREADED((String)DEBUG_EXPORT_DECOMPILE, Inherit.ALLOW_OVERRIDE, (Option)"async"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  249 */     DEBUG_VERIFY((String)DEBUG_ALL, "verify"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  255 */     DEBUG_VERBOSE((String)DEBUG_ALL, "verbose"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  261 */     DEBUG_INJECTORS((String)DEBUG_ALL, "countInjections"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  266 */     DEBUG_STRICT((String)DEBUG_ALL, Inherit.INDEPENDENT, (Option)"strict"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  273 */     DEBUG_UNIQUE((String)DEBUG_STRICT, "unique"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  278 */     DEBUG_TARGETS((String)DEBUG_STRICT, "targets"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  284 */     DEBUG_PROFILER((String)DEBUG_ALL, Inherit.ALLOW_OVERRIDE, (Option)"profiler"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  290 */     DUMP_TARGET_ON_FAILURE("dumpTargetOnFailure"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  295 */     CHECK_ALL("checks"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  301 */     CHECK_IMPLEMENTS((String)CHECK_ALL, "interfaces"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  309 */     CHECK_IMPLEMENTS_STRICT((String)CHECK_IMPLEMENTS, Inherit.ALLOW_OVERRIDE, (Option)"strict"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  314 */     IGNORE_CONSTRAINTS("ignoreConstraints"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  319 */     HOT_SWAP("hotSwap"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  324 */     ENVIRONMENT((String)Inherit.ALWAYS_FALSE, "env"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  329 */     OBFUSCATION_TYPE((String)ENVIRONMENT, Inherit.ALWAYS_FALSE, (Option)"obf"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  334 */     DISABLE_REFMAP((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"disableRefMap"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  344 */     REFMAP_REMAP((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"remapRefMap"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  354 */     REFMAP_REMAP_RESOURCE((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"refMapRemappingFile", (Inherit)""),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  361 */     REFMAP_REMAP_SOURCE_ENV((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"refMapRemappingEnv", (Inherit)"searge"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  366 */     IGNORE_REQUIRED((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"ignoreRequired"),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  371 */     DEFAULT_COMPATIBILITY_LEVEL((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"compatLevel"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  391 */     SHIFT_BY_VIOLATION_BEHAVIOUR((String)ENVIRONMENT, Inherit.INDEPENDENT, (Option)"shiftByViolation", (Inherit)"warn"),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  397 */     INITIALISER_INJECTION_MODE("initialiserInjectionMode", "default");
/*      */     private static final String PREFIX = "mixin";
/*      */     final Option parent;
/*      */     final Inherit inheritance;
/*      */     final String property;
/*      */     final String defaultValue;
/*      */     final boolean isFlag;
/*      */     final int depth;
/*      */     
/*      */     private enum Inherit {
/*  407 */       INHERIT,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  414 */       ALLOW_OVERRIDE,
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  420 */       INDEPENDENT,
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  425 */       ALWAYS_FALSE;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Option(Option parent, Inherit inheritance, String property, boolean isFlag, String defaultStringValue) {
/*  506 */       this.parent = parent;
/*  507 */       this.inheritance = inheritance;
/*  508 */       this.property = ((parent != null) ? parent.property : "mixin") + "." + property;
/*  509 */       this.defaultValue = defaultStringValue;
/*  510 */       this.isFlag = isFlag;
/*  511 */       int depth = 0;
/*  512 */       for (; parent != null; depth++) {
/*  513 */         parent = parent.parent;
/*      */       }
/*  515 */       this.depth = depth;
/*      */     }
/*      */     
/*      */     Option getParent() {
/*  519 */       return this.parent;
/*      */     }
/*      */     
/*      */     String getProperty() {
/*  523 */       return this.property;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  528 */       return this.isFlag ? String.valueOf(getBooleanValue()) : getStringValue();
/*      */     }
/*      */     
/*      */     private boolean getLocalBooleanValue(boolean defaultValue) {
/*  532 */       return Boolean.parseBoolean(System.getProperty(this.property, Boolean.toString(defaultValue)));
/*      */     }
/*      */     
/*      */     private boolean getInheritedBooleanValue() {
/*  536 */       return (this.parent != null && this.parent.getBooleanValue());
/*      */     }
/*      */     
/*      */     final boolean getBooleanValue() {
/*  540 */       if (this.inheritance == Inherit.ALWAYS_FALSE) {
/*  541 */         return false;
/*      */       }
/*      */       
/*  544 */       boolean local = getLocalBooleanValue(false);
/*  545 */       if (this.inheritance == Inherit.INDEPENDENT) {
/*  546 */         return local;
/*      */       }
/*      */       
/*  549 */       boolean inherited = (local || getInheritedBooleanValue());
/*  550 */       return (this.inheritance == Inherit.INHERIT) ? inherited : getLocalBooleanValue(inherited);
/*      */     }
/*      */     
/*      */     final String getStringValue() {
/*  554 */       return (this.parent == null || this.parent.getBooleanValue()) ? System.getProperty(this.property, this.defaultValue) : this.defaultValue;
/*      */     }
/*      */ 
/*      */     
/*      */     <E extends Enum<E>> E getEnumValue(E defaultValue) {
/*  559 */       String value = System.getProperty(this.property, defaultValue.name());
/*      */       try {
/*  561 */         return Enum.valueOf((Class)defaultValue.getClass(), value.toUpperCase());
/*  562 */       } catch (IllegalArgumentException ex) {
/*  563 */         return defaultValue;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum CompatibilityLevel
/*      */   {
/*  576 */     JAVA_6(6, 50, false),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  581 */     JAVA_7(7, 51, false)
/*      */     {
/*      */       boolean isSupported()
/*      */       {
/*  585 */         return (JavaVersion.current() >= 1.7D);
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  593 */     JAVA_8(8, 52, true)
/*      */     {
/*      */       boolean isSupported()
/*      */       {
/*  597 */         return (JavaVersion.current() >= 1.8D);
/*      */       }
/*      */     },
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  605 */     JAVA_9(9, 53, true)
/*      */     {
/*      */       boolean isSupported()
/*      */       {
/*  609 */         return false;
/*      */       }
/*      */     };
/*      */ 
/*      */     
/*      */     private static final int CLASS_V1_9 = 53;
/*      */     
/*      */     private final int ver;
/*      */     
/*      */     private final int classVersion;
/*      */     
/*      */     private final boolean supportsMethodsInInterfaces;
/*      */     
/*      */     private CompatibilityLevel maxCompatibleLevel;
/*      */ 
/*      */     
/*      */     CompatibilityLevel(int ver, int classVersion, boolean resolveMethodsInInterfaces) {
/*  626 */       this.ver = ver;
/*  627 */       this.classVersion = classVersion;
/*  628 */       this.supportsMethodsInInterfaces = resolveMethodsInInterfaces;
/*      */     }
/*      */ 
/*      */     
/*      */     private void setMaxCompatibleLevel(CompatibilityLevel maxCompatibleLevel) {
/*  633 */       this.maxCompatibleLevel = maxCompatibleLevel;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isSupported() {
/*  641 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int classVersion() {
/*  648 */       return this.classVersion;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean supportsMethodsInInterfaces() {
/*  656 */       return this.supportsMethodsInInterfaces;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isAtLeast(CompatibilityLevel level) {
/*  667 */       return (level == null || this.ver >= level.ver);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean canElevateTo(CompatibilityLevel level) {
/*  677 */       if (level == null || this.maxCompatibleLevel == null) {
/*  678 */         return true;
/*      */       }
/*  680 */       return (level.ver <= this.maxCompatibleLevel.ver);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean canSupport(CompatibilityLevel level) {
/*  690 */       if (level == null) {
/*  691 */         return true;
/*      */       }
/*      */       
/*  694 */       return level.canElevateTo(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class TokenProviderWrapper
/*      */     implements Comparable<TokenProviderWrapper>
/*      */   {
/*  704 */     private static int nextOrder = 0;
/*      */     
/*      */     private final int priority;
/*      */     
/*      */     private final int order;
/*      */     private final IEnvironmentTokenProvider provider;
/*      */     private final MixinEnvironment environment;
/*      */     
/*      */     public TokenProviderWrapper(IEnvironmentTokenProvider provider, MixinEnvironment environment) {
/*  713 */       this.provider = provider;
/*  714 */       this.environment = environment;
/*  715 */       this.order = nextOrder++;
/*  716 */       this.priority = provider.getPriority();
/*      */     }
/*      */ 
/*      */     
/*      */     public int compareTo(TokenProviderWrapper other) {
/*  721 */       if (other == null) {
/*  722 */         return 0;
/*      */       }
/*  724 */       if (other.priority == this.priority) {
/*  725 */         return other.order - this.order;
/*      */       }
/*  727 */       return other.priority - this.priority;
/*      */     }
/*      */     
/*      */     public IEnvironmentTokenProvider getProvider() {
/*  731 */       return this.provider;
/*      */     }
/*      */     
/*      */     Integer getToken(String token) {
/*  735 */       return this.provider.getToken(token, this.environment);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class MixinLogger
/*      */   {
/*  745 */     static MixinAppender appender = new MixinAppender("MixinLogger", null, null);
/*      */     
/*      */     public MixinLogger() {
/*  748 */       Logger log = (Logger)LogManager.getLogger("FML");
/*  749 */       appender.start();
/*  750 */       log.addAppender((Appender)appender);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     static class MixinAppender
/*      */       extends AbstractAppender
/*      */     {
/*      */       protected MixinAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
/*  759 */         super(name, filter, layout);
/*      */       }
/*      */       
/*      */       public void append(LogEvent event)
/*      */       {
/*  764 */         if (event.getLevel() == Level.DEBUG && "Validating minecraft".equals(event.getMessage().getFormat()))
/*      */         {
/*  766 */           MixinEnvironment.gotoPhase(MixinEnvironment.Phase.INIT); }  } } } static class MixinAppender extends AbstractAppender { public void append(LogEvent event) { if (event.getLevel() == Level.DEBUG && "Validating minecraft".equals(event.getMessage().getFormat())) MixinEnvironment.gotoPhase(MixinEnvironment.Phase.INIT);
/*      */        }
/*      */ 
/*      */ 
/*      */     
/*      */     protected MixinAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
/*      */       super(name, filter, layout);
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*  777 */   private static final Set<String> excludeTransformers = Sets.newHashSet((Object[])new String[] { "net.minecraftforge.fml.common.asm.transformers.EventSubscriptionTransformer", "cpw.mods.fml.common.asm.transformers.EventSubscriptionTransformer", "net.minecraftforge.fml.common.asm.transformers.TerminalTransformer", "cpw.mods.fml.common.asm.transformers.TerminalTransformer" });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static MixinEnvironment currentEnvironment;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  793 */   private static Phase currentPhase = Phase.NOT_INITIALISED;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  798 */   private static CompatibilityLevel compatibility = Option.DEFAULT_COMPATIBILITY_LEVEL.<CompatibilityLevel>getEnumValue(CompatibilityLevel.JAVA_6);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean showHeader = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  808 */   private static final Logger logger = LogManager.getLogger("mixin");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  813 */   private static final Profiler profiler = new Profiler();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final IMixinService service;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Phase phase;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String configsKey;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean[] options;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  838 */   private final Set<String> tokenProviderClasses = new HashSet<String>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  843 */   private final List<TokenProviderWrapper> tokenProviders = new ArrayList<TokenProviderWrapper>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  848 */   private final Map<String, Integer> internalTokens = new HashMap<String, Integer>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  853 */   private final RemapperChain remappers = new RemapperChain();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Side side;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<ILegacyClassTransformer> transformers;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  871 */   private String obfuscationContext = null;
/*      */   
/*      */   MixinEnvironment(Phase phase) {
/*  874 */     this.service = MixinService.getService();
/*  875 */     this.phase = phase;
/*  876 */     this.configsKey = "mixin.configs." + this.phase.name.toLowerCase();
/*      */ 
/*      */     
/*  879 */     Object version = getVersion();
/*  880 */     if (version == null || !"0.7.4".equals(version)) {
/*  881 */       throw new MixinException("Environment conflict, mismatched versions or you didn't call MixinBootstrap.init()");
/*      */     }
/*      */ 
/*      */     
/*  885 */     this.service.checkEnv(this);
/*      */     
/*  887 */     this.options = new boolean[(Option.values()).length];
/*  888 */     for (Option option : Option.values()) {
/*  889 */       this.options[option.ordinal()] = option.getBooleanValue();
/*      */     }
/*      */     
/*  892 */     if (showHeader) {
/*  893 */       showHeader = false;
/*  894 */       printHeader(version);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void printHeader(Object version) {
/*  899 */     String codeSource = getCodeSource();
/*  900 */     String serviceName = this.service.getName();
/*  901 */     Side side = getSide();
/*  902 */     logger.info("SpongePowered MIXIN Subsystem Version={} Source={} Service={} Env={}", new Object[] { version, codeSource, serviceName, side });
/*      */     
/*  904 */     boolean verbose = getOption(Option.DEBUG_VERBOSE);
/*  905 */     if (verbose || getOption(Option.DEBUG_EXPORT) || getOption(Option.DEBUG_PROFILER)) {
/*  906 */       PrettyPrinter printer = new PrettyPrinter(32);
/*  907 */       printer.add("SpongePowered MIXIN%s", new Object[] { verbose ? " (Verbose debugging enabled)" : "" }).centre().hr();
/*  908 */       printer.kv("Code source", codeSource);
/*  909 */       printer.kv("Internal Version", version);
/*  910 */       printer.kv("Java 8 Supported", Boolean.valueOf(CompatibilityLevel.JAVA_8.isSupported())).hr();
/*  911 */       printer.kv("Service Name", serviceName);
/*  912 */       printer.kv("Service Class", this.service.getClass().getName()).hr();
/*  913 */       for (Option option : Option.values()) {
/*  914 */         StringBuilder indent = new StringBuilder();
/*  915 */         for (int i = 0; i < option.depth; i++) {
/*  916 */           indent.append("- ");
/*      */         }
/*  918 */         printer.kv(option.property, "%s<%s>", new Object[] { indent, option });
/*      */       } 
/*  920 */       printer.hr().kv("Detected Side", side);
/*  921 */       printer.print(System.err);
/*      */     } 
/*      */   }
/*      */   
/*      */   private String getCodeSource() {
/*      */     try {
/*  927 */       return getClass().getProtectionDomain().getCodeSource().getLocation().toString();
/*  928 */     } catch (Throwable th) {
/*  929 */       return "Unknown";
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Phase getPhase() {
/*  939 */     return this.phase;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public List<String> getMixinConfigs() {
/*  950 */     List<String> mixinConfigs = (List<String>)GlobalProperties.get(this.configsKey);
/*  951 */     if (mixinConfigs == null) {
/*  952 */       mixinConfigs = new ArrayList<String>();
/*  953 */       GlobalProperties.put(this.configsKey, mixinConfigs);
/*      */     } 
/*  955 */     return mixinConfigs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public MixinEnvironment addConfiguration(String config) {
/*  967 */     logger.warn("MixinEnvironment::addConfiguration is deprecated and will be removed. Use Mixins::addConfiguration instead!");
/*  968 */     Mixins.addConfiguration(config, this);
/*  969 */     return this;
/*      */   }
/*      */   
/*      */   void registerConfig(String config) {
/*  973 */     List<String> configs = getMixinConfigs();
/*  974 */     if (!configs.contains(config)) {
/*  975 */       configs.add(config);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public MixinEnvironment registerErrorHandlerClass(String handlerName) {
/*  988 */     Mixins.registerErrorHandlerClass(handlerName);
/*  989 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment registerTokenProviderClass(String providerName) {
/*  999 */     if (!this.tokenProviderClasses.contains(providerName)) {
/*      */       
/*      */       try {
/*      */         
/* 1003 */         Class<? extends IEnvironmentTokenProvider> providerClass = this.service.getClassProvider().findClass(providerName, true);
/* 1004 */         IEnvironmentTokenProvider provider = providerClass.newInstance();
/* 1005 */         registerTokenProvider(provider);
/* 1006 */       } catch (Throwable th) {
/* 1007 */         logger.error("Error instantiating " + providerName, th);
/*      */       } 
/*      */     }
/* 1010 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment registerTokenProvider(IEnvironmentTokenProvider provider) {
/* 1020 */     if (provider != null && !this.tokenProviderClasses.contains(provider.getClass().getName())) {
/* 1021 */       String providerName = provider.getClass().getName();
/* 1022 */       TokenProviderWrapper wrapper = new TokenProviderWrapper(provider, this);
/* 1023 */       logger.info("Adding new token provider {} to {}", new Object[] { providerName, this });
/* 1024 */       this.tokenProviders.add(wrapper);
/* 1025 */       this.tokenProviderClasses.add(providerName);
/* 1026 */       Collections.sort(this.tokenProviders);
/*      */     } 
/*      */     
/* 1029 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Integer getToken(String token) {
/* 1041 */     token = token.toUpperCase();
/*      */     
/* 1043 */     for (TokenProviderWrapper provider : this.tokenProviders) {
/* 1044 */       Integer value = provider.getToken(token);
/* 1045 */       if (value != null) {
/* 1046 */         return value;
/*      */       }
/*      */     } 
/*      */     
/* 1050 */     return this.internalTokens.get(token);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public Set<String> getErrorHandlerClasses() {
/* 1061 */     return Mixins.getErrorHandlerClasses();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getActiveTransformer() {
/* 1070 */     return GlobalProperties.get("mixin.transformer");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setActiveTransformer(ITransformer transformer) {
/* 1079 */     if (transformer != null) {
/* 1080 */       GlobalProperties.put("mixin.transformer", transformer);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MixinEnvironment setSide(Side side) {
/* 1091 */     if (side != null && getSide() == Side.UNKNOWN && side != Side.UNKNOWN) {
/* 1092 */       this.side = side;
/*      */     }
/* 1094 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Side getSide() {
/* 1103 */     if (this.side == null) {
/* 1104 */       for (Side side : Side.values()) {
/* 1105 */         if (side.detect()) {
/* 1106 */           this.side = side;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 1112 */     return (this.side != null) ? this.side : Side.UNKNOWN;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getVersion() {
/* 1121 */     return (String)GlobalProperties.get("mixin.initialised");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOption(Option option) {
/* 1131 */     return this.options[option.ordinal()];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOption(Option option, boolean value) {
/* 1141 */     this.options[option.ordinal()] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOptionValue(Option option) {
/* 1151 */     return option.getStringValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <E extends Enum<E>> E getOption(Option option, E defaultValue) {
/* 1163 */     return option.getEnumValue(defaultValue);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setObfuscationContext(String context) {
/* 1172 */     this.obfuscationContext = context;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getObfuscationContext() {
/* 1179 */     return this.obfuscationContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRefmapObfuscationContext() {
/* 1186 */     String overrideObfuscationType = Option.OBFUSCATION_TYPE.getStringValue();
/* 1187 */     if (overrideObfuscationType != null) {
/* 1188 */       return overrideObfuscationType;
/*      */     }
/* 1190 */     return this.obfuscationContext;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RemapperChain getRemappers() {
/* 1197 */     return this.remappers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void audit() {
/* 1204 */     Object activeTransformer = getActiveTransformer();
/* 1205 */     if (activeTransformer instanceof MixinTransformer) {
/* 1206 */       MixinTransformer transformer = (MixinTransformer)activeTransformer;
/* 1207 */       transformer.audit(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public List<ILegacyClassTransformer> getTransformers() {
/* 1218 */     if (this.transformers == null) {
/* 1219 */       buildTransformerDelegationList();
/*      */     }
/*      */     
/* 1222 */     return Collections.unmodifiableList(this.transformers);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTransformerExclusion(String name) {
/* 1231 */     excludeTransformers.add(name);
/*      */ 
/*      */     
/* 1234 */     this.transformers = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void buildTransformerDelegationList() {
/* 1244 */     logger.debug("Rebuilding transformer delegation list:");
/* 1245 */     this.transformers = new ArrayList<ILegacyClassTransformer>();
/* 1246 */     for (ITransformer transformer : this.service.getTransformers()) {
/* 1247 */       if (!(transformer instanceof ILegacyClassTransformer)) {
/*      */         continue;
/*      */       }
/*      */       
/* 1251 */       ILegacyClassTransformer legacyTransformer = (ILegacyClassTransformer)transformer;
/* 1252 */       String transformerName = legacyTransformer.getName();
/* 1253 */       boolean include = true;
/* 1254 */       for (String excludeClass : excludeTransformers) {
/* 1255 */         if (transformerName.contains(excludeClass)) {
/* 1256 */           include = false;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1260 */       if (include && !legacyTransformer.isDelegationExcluded()) {
/* 1261 */         logger.debug("  Adding:    {}", new Object[] { transformerName });
/* 1262 */         this.transformers.add(legacyTransformer); continue;
/*      */       } 
/* 1264 */       logger.debug("  Excluding: {}", new Object[] { transformerName });
/*      */     } 
/*      */ 
/*      */     
/* 1268 */     logger.debug("Transformer delegation list created with {} entries", new Object[] { Integer.valueOf(this.transformers.size()) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1276 */     return String.format("%s[%s]", new Object[] { getClass().getSimpleName(), this.phase });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Phase getCurrentPhase() {
/* 1283 */     if (currentPhase == Phase.NOT_INITIALISED) {
/* 1284 */       init(Phase.PREINIT);
/*      */     }
/*      */     
/* 1287 */     return currentPhase;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void init(Phase phase) {
/* 1296 */     if (currentPhase == Phase.NOT_INITIALISED) {
/* 1297 */       currentPhase = phase;
/* 1298 */       MixinEnvironment env = getEnvironment(phase);
/* 1299 */       getProfiler().setActive(env.getOption(Option.DEBUG_PROFILER));
/*      */ 
/*      */       
/* 1302 */       MixinLogger mixinLogger = new MixinLogger();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MixinEnvironment getEnvironment(Phase phase) {
/* 1313 */     if (phase == null) {
/* 1314 */       return Phase.DEFAULT.getEnvironment();
/*      */     }
/* 1316 */     return phase.getEnvironment();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MixinEnvironment getDefaultEnvironment() {
/* 1325 */     return getEnvironment(Phase.DEFAULT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MixinEnvironment getCurrentEnvironment() {
/* 1334 */     if (currentEnvironment == null) {
/* 1335 */       currentEnvironment = getEnvironment(getCurrentPhase());
/*      */     }
/*      */     
/* 1338 */     return currentEnvironment;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CompatibilityLevel getCompatibilityLevel() {
/* 1345 */     return compatibility;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static void setCompatibilityLevel(CompatibilityLevel level) throws IllegalArgumentException {
/* 1357 */     StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
/* 1358 */     if (!"org.spongepowered.asm.mixin.transformer.MixinConfig".equals(stackTrace[2].getClassName())) {
/* 1359 */       logger.warn("MixinEnvironment::setCompatibilityLevel is deprecated and will be removed. Set level via config instead!");
/*      */     }
/*      */     
/* 1362 */     if (level != compatibility && level.isAtLeast(compatibility)) {
/* 1363 */       if (!level.isSupported()) {
/* 1364 */         throw new IllegalArgumentException("The requested compatibility level " + level + " could not be set. Level is not supported");
/*      */       }
/*      */       
/* 1367 */       compatibility = level;
/* 1368 */       logger.info("Compatibility level set to {}", new Object[] { level });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Profiler getProfiler() {
/* 1378 */     return profiler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void gotoPhase(Phase phase) {
/* 1387 */     if (phase == null || phase.ordinal < 0) {
/* 1388 */       throw new IllegalArgumentException("Cannot go to the specified phase, phase is null or invalid");
/*      */     }
/*      */     
/* 1391 */     if (phase.ordinal > (getCurrentPhase()).ordinal) {
/* 1392 */       MixinService.getService().beginPhase();
/*      */     }
/*      */     
/* 1395 */     if (phase == Phase.DEFAULT) {
/*      */       
/* 1397 */       Logger log = (Logger)LogManager.getLogger("FML");
/* 1398 */       log.removeAppender((Appender)MixinLogger.appender);
/*      */     } 
/*      */     
/* 1401 */     currentPhase = phase;
/* 1402 */     currentEnvironment = getEnvironment(getCurrentPhase());
/*      */   }
/*      */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\MixinEnvironment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */