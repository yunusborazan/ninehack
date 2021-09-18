/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.lang.model.type.TypeKind;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ import org.spongepowered.asm.mixin.gen.AccessorInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
/*     */ import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.Extensions;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotatedMixinElementHandlerAccessor
/*     */   extends AnnotatedMixinElementHandler
/*     */   implements IMixinContext
/*     */ {
/*     */   static class AnnotatedElementAccessor
/*     */     extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement>
/*     */   {
/*     */     private final boolean shouldRemap;
/*     */     private final TypeMirror returnType;
/*     */     private String targetName;
/*     */     
/*     */     public AnnotatedElementAccessor(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) {
/*  71 */       super(element, annotation);
/*  72 */       this.shouldRemap = shouldRemap;
/*  73 */       this.returnType = getElement().getReturnType();
/*     */     }
/*     */     
/*     */     public boolean shouldRemap() {
/*  77 */       return this.shouldRemap;
/*     */     }
/*     */     
/*     */     public String getAnnotationValue() {
/*  81 */       return (String)getAnnotation().getValue();
/*     */     }
/*     */     
/*     */     public TypeMirror getTargetType() {
/*  85 */       switch (getAccessorType()) {
/*     */         case FIELD_GETTER:
/*  87 */           return this.returnType;
/*     */         case FIELD_SETTER:
/*  89 */           return ((VariableElement)getElement().getParameters().get(0)).asType();
/*     */       } 
/*  91 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTargetTypeName() {
/*  96 */       return TypeUtils.getTypeName(getTargetType());
/*     */     }
/*     */     
/*     */     public String getAccessorDesc() {
/* 100 */       return TypeUtils.getInternalName(getTargetType());
/*     */     }
/*     */     
/*     */     public MemberInfo getContext() {
/* 104 */       return new MemberInfo(getTargetName(), null, getAccessorDesc());
/*     */     }
/*     */     
/*     */     public AccessorInfo.AccessorType getAccessorType() {
/* 108 */       return (this.returnType.getKind() == TypeKind.VOID) ? AccessorInfo.AccessorType.FIELD_SETTER : AccessorInfo.AccessorType.FIELD_GETTER;
/*     */     }
/*     */     
/*     */     public void setTargetName(String targetName) {
/* 112 */       this.targetName = targetName;
/*     */     }
/*     */     
/*     */     public String getTargetName() {
/* 116 */       return this.targetName;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 121 */       return (this.targetName != null) ? this.targetName : "<invalid>";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class AnnotatedElementInvoker
/*     */     extends AnnotatedElementAccessor
/*     */   {
/*     */     public AnnotatedElementInvoker(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) {
/* 131 */       super(element, annotation, shouldRemap);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getAccessorDesc() {
/* 136 */       return TypeUtils.getDescriptor(getElement());
/*     */     }
/*     */ 
/*     */     
/*     */     public AccessorInfo.AccessorType getAccessorType() {
/* 141 */       return AccessorInfo.AccessorType.METHOD_PROXY;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTargetTypeName() {
/* 146 */       return TypeUtils.getJavaSignature(getElement());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public AnnotatedMixinElementHandlerAccessor(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
/* 152 */     super(ap, mixin);
/*     */   }
/*     */ 
/*     */   
/*     */   public ReferenceMapper getReferenceMapper() {
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getClassRef() {
/* 162 */     return this.mixin.getClassRef();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTargetClassRef() {
/* 167 */     throw new UnsupportedOperationException("Target class not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public IMixinInfo getMixin() {
/* 172 */     throw new UnsupportedOperationException("MixinInfo not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public Extensions getExtensions() {
/* 177 */     throw new UnsupportedOperationException("Mixin Extensions not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getOption(MixinEnvironment.Option option) {
/* 182 */     throw new UnsupportedOperationException("Options not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPriority() {
/* 187 */     throw new UnsupportedOperationException("Priority not available at compile time");
/*     */   }
/*     */ 
/*     */   
/*     */   public Target getTargetMethod(MethodNode into) {
/* 192 */     throw new UnsupportedOperationException("Target not available at compile time");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerAccessor(AnnotatedElementAccessor elem) {
/* 201 */     if (elem.getAccessorType() == null) {
/* 202 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unsupported accessor type");
/*     */       
/*     */       return;
/*     */     } 
/* 206 */     String targetName = getAccessorTargetName(elem);
/* 207 */     if (targetName == null) {
/* 208 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Cannot inflect accessor target name");
/*     */       return;
/*     */     } 
/* 211 */     elem.setTargetName(targetName);
/*     */     
/* 213 */     for (TypeHandle target : this.mixin.getTargets()) {
/* 214 */       if (elem.getAccessorType() == AccessorInfo.AccessorType.METHOD_PROXY) {
/* 215 */         registerInvokerForTarget((AnnotatedElementInvoker)elem, target); continue;
/*     */       } 
/* 217 */       registerAccessorForTarget(elem, target);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerAccessorForTarget(AnnotatedElementAccessor elem, TypeHandle target) {
/* 223 */     FieldHandle targetField = target.findField(elem.getTargetName(), elem.getTargetTypeName(), false);
/* 224 */     if (targetField == null) {
/* 225 */       if (!target.isImaginary()) {
/* 226 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Could not locate @Accessor target " + elem + " in target " + target);
/*     */         
/*     */         return;
/*     */       } 
/* 230 */       targetField = new FieldHandle(target.getName(), elem.getTargetName(), elem.getDesc());
/*     */     } 
/*     */     
/* 233 */     if (!elem.shouldRemap()) {
/*     */       return;
/*     */     }
/*     */     
/* 237 */     ObfuscationData<MappingField> obfData = this.obf.getDataProvider().getObfField(targetField.asMapping(false).move(target.getName()));
/* 238 */     if (obfData.isEmpty()) {
/* 239 */       String info = this.mixin.isMultiTarget() ? (" in target " + target) : "";
/* 240 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Accessor target " + elem);
/*     */       
/*     */       return;
/*     */     } 
/* 244 */     obfData = AnnotatedMixinElementHandler.stripOwnerData(obfData);
/*     */     
/*     */     try {
/* 247 */       this.obf.getReferenceManager().addFieldMapping(this.mixin.getClassRef(), elem.getTargetName(), elem.getContext(), obfData);
/* 248 */     } catch (ReferenceConflictException ex) {
/* 249 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Accessor target " + elem + ": " + ex.getNew() + " for target " + target + " conflicts with existing mapping " + ex
/* 250 */           .getOld());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerInvokerForTarget(AnnotatedElementInvoker elem, TypeHandle target) {
/* 255 */     MethodHandle targetMethod = target.findMethod(elem.getTargetName(), elem.getTargetTypeName(), false);
/* 256 */     if (targetMethod == null) {
/* 257 */       if (!target.isImaginary()) {
/* 258 */         elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Could not locate @Invoker target " + elem + " in target " + target);
/*     */         
/*     */         return;
/*     */       } 
/* 262 */       targetMethod = new MethodHandle(target, elem.getTargetName(), elem.getDesc());
/*     */     } 
/*     */     
/* 265 */     if (!elem.shouldRemap()) {
/*     */       return;
/*     */     }
/*     */     
/* 269 */     ObfuscationData<MappingMethod> obfData = this.obf.getDataProvider().getObfMethod(targetMethod.asMapping(false).move(target.getName()));
/* 270 */     if (obfData.isEmpty()) {
/* 271 */       String info = this.mixin.isMultiTarget() ? (" in target " + target) : "";
/* 272 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Accessor target " + elem);
/*     */       
/*     */       return;
/*     */     } 
/* 276 */     obfData = AnnotatedMixinElementHandler.stripOwnerData(obfData);
/*     */     
/*     */     try {
/* 279 */       this.obf.getReferenceManager().addMethodMapping(this.mixin.getClassRef(), elem.getTargetName(), elem.getContext(), obfData);
/* 280 */     } catch (ReferenceConflictException ex) {
/* 281 */       elem.printMessage((Messager)this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Invoker target " + elem + ": " + ex.getNew() + " for target " + target + " conflicts with existing mapping " + ex
/* 282 */           .getOld());
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getAccessorTargetName(AnnotatedElementAccessor elem) {
/* 287 */     String value = elem.getAnnotationValue();
/* 288 */     if (Strings.isNullOrEmpty(value)) {
/* 289 */       return inflectAccessorTarget(elem);
/*     */     }
/* 291 */     return value;
/*     */   }
/*     */   
/*     */   private String inflectAccessorTarget(AnnotatedElementAccessor elem) {
/* 295 */     return AccessorInfo.inflectTarget(elem.getSimpleName(), elem.getAccessorType(), "", this, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\tools\obfuscation\AnnotatedMixinElementHandlerAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */