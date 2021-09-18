/*     */ package org.spongepowered.asm.mixin.injection.invoke.arg;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.ClassVisitor;
/*     */ import org.spongepowered.asm.lib.ClassWriter;
/*     */ import org.spongepowered.asm.lib.Label;
/*     */ import org.spongepowered.asm.lib.MethodVisitor;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.util.CheckClassAdapter;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IClassGenerator;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ import org.spongepowered.asm.util.SignaturePrinter;
/*     */ import org.spongepowered.asm.util.asm.MethodVisitorEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ArgsClassGenerator
/*     */   implements IClassGenerator
/*     */ {
/*  57 */   public static final String ARGS_NAME = Args.class.getName();
/*  58 */   public static final String ARGS_REF = ARGS_NAME.replace('.', '/');
/*     */   
/*     */   public static final String GETTER_PREFIX = "$";
/*     */   
/*     */   private static final String CLASS_NAME_BASE = "org.spongepowered.asm.synthetic.args.Args$";
/*     */   
/*     */   private static final String OBJECT = "java/lang/Object";
/*     */   
/*     */   private static final String OBJECT_ARRAY = "[Ljava/lang/Object;";
/*     */   
/*     */   private static final String VALUES_FIELD = "values";
/*     */   
/*     */   private static final String CTOR_DESC = "([Ljava/lang/Object;)V";
/*     */   
/*     */   private static final String SET = "set";
/*     */   
/*     */   private static final String SET_DESC = "(ILjava/lang/Object;)V";
/*     */   
/*     */   private static final String SETALL = "setAll";
/*     */   
/*     */   private static final String SETALL_DESC = "([Ljava/lang/Object;)V";
/*     */   
/*     */   private static final String NPE = "java/lang/NullPointerException";
/*     */   
/*     */   private static final String NPE_CTOR_DESC = "(Ljava/lang/String;)V";
/*     */   
/*     */   private static final String AIOOBE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException";
/*     */   
/*     */   private static final String AIOOBE_CTOR_DESC = "(I)V";
/*     */   
/*     */   private static final String ACE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException";
/*     */   private static final String ACE_CTOR_DESC = "(IILjava/lang/String;)V";
/*  90 */   private int nextIndex = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private final BiMap<String, String> classNames = (BiMap<String, String>)HashBiMap.create();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   private final Map<String, byte[]> classBytes = (Map)new HashMap<String, byte>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassName(String desc) {
/* 115 */     if (!desc.endsWith(")V")) {
/* 116 */       throw new IllegalArgumentException("Invalid @ModifyArgs method descriptor");
/*     */     }
/*     */     
/* 119 */     String name = (String)this.classNames.get(desc);
/* 120 */     if (name == null) {
/* 121 */       name = String.format("%s%d", new Object[] { "org.spongepowered.asm.synthetic.args.Args$", Integer.valueOf(this.nextIndex++) });
/* 122 */       this.classNames.put(desc, name);
/*     */     } 
/* 124 */     return name;
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
/*     */   public String getClassRef(String desc) {
/* 137 */     return getClassName(desc).replace('.', '/');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] generate(String name) {
/* 146 */     return getBytes(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getBytes(String name) {
/* 157 */     byte[] bytes = this.classBytes.get(name);
/* 158 */     if (bytes == null) {
/* 159 */       String desc = (String)this.classNames.inverse().get(name);
/* 160 */       if (desc == null) {
/* 161 */         return null;
/*     */       }
/* 163 */       bytes = generateClass(name, desc);
/* 164 */       this.classBytes.put(name, bytes);
/*     */     } 
/* 166 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] generateClass(String name, String desc) {
/*     */     CheckClassAdapter checkClassAdapter;
/* 177 */     String ref = name.replace('.', '/');
/* 178 */     Type[] args = Type.getArgumentTypes(desc);
/*     */     
/* 180 */     ClassWriter writer = new ClassWriter(2);
/* 181 */     ClassWriter classWriter1 = writer;
/* 182 */     if (MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
/* 183 */       checkClassAdapter = new CheckClassAdapter((ClassVisitor)writer);
/*     */     }
/*     */     
/* 186 */     checkClassAdapter.visit(50, 4129, ref, null, ARGS_REF, null);
/* 187 */     checkClassAdapter.visitSource(name.substring(name.lastIndexOf('.') + 1) + ".java", null);
/*     */     
/* 189 */     generateCtor(ref, desc, args, (ClassVisitor)checkClassAdapter);
/* 190 */     generateToString(ref, desc, args, (ClassVisitor)checkClassAdapter);
/* 191 */     generateFactory(ref, desc, args, (ClassVisitor)checkClassAdapter);
/* 192 */     generateSetters(ref, desc, args, (ClassVisitor)checkClassAdapter);
/* 193 */     generateGetters(ref, desc, args, (ClassVisitor)checkClassAdapter);
/*     */     
/* 195 */     checkClassAdapter.visitEnd();
/*     */     
/* 197 */     return writer.toByteArray();
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
/*     */   private void generateCtor(String ref, String desc, Type[] args, ClassVisitor writer) {
/* 210 */     MethodVisitor ctor = writer.visitMethod(2, "<init>", "([Ljava/lang/Object;)V", null, null);
/* 211 */     ctor.visitCode();
/* 212 */     ctor.visitVarInsn(25, 0);
/* 213 */     ctor.visitVarInsn(25, 1);
/* 214 */     ctor.visitMethodInsn(183, ARGS_REF, "<init>", "([Ljava/lang/Object;)V", false);
/* 215 */     ctor.visitInsn(177);
/* 216 */     ctor.visitMaxs(2, 2);
/* 217 */     ctor.visitEnd();
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
/*     */   private void generateToString(String ref, String desc, Type[] args, ClassVisitor writer) {
/* 229 */     MethodVisitor toString = writer.visitMethod(1, "toString", "()Ljava/lang/String;", null, null);
/* 230 */     toString.visitCode();
/* 231 */     toString.visitLdcInsn("Args" + getSignature(args));
/* 232 */     toString.visitInsn(176);
/* 233 */     toString.visitMaxs(1, 1);
/* 234 */     toString.visitEnd();
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
/*     */   private void generateFactory(String ref, String desc, Type[] args, ClassVisitor writer) {
/* 249 */     String factoryDesc = Bytecode.changeDescriptorReturnType(desc, "L" + ref + ";");
/* 250 */     MethodVisitorEx of = new MethodVisitorEx(writer.visitMethod(9, "of", factoryDesc, null, null));
/* 251 */     of.visitCode();
/*     */ 
/*     */     
/* 254 */     of.visitTypeInsn(187, ref);
/* 255 */     of.visitInsn(89);
/*     */ 
/*     */     
/* 258 */     of.visitConstant((byte)args.length);
/* 259 */     of.visitTypeInsn(189, "java/lang/Object");
/*     */ 
/*     */     
/* 262 */     byte argIndex = 0;
/* 263 */     for (Type arg : args) {
/* 264 */       of.visitInsn(89);
/* 265 */       of.visitConstant(argIndex);
/* 266 */       argIndex = (byte)(argIndex + 1); of.visitVarInsn(arg.getOpcode(21), argIndex);
/* 267 */       box((MethodVisitor)of, arg);
/* 268 */       of.visitInsn(83);
/*     */     } 
/*     */ 
/*     */     
/* 272 */     of.visitMethodInsn(183, ref, "<init>", "([Ljava/lang/Object;)V", false);
/*     */ 
/*     */     
/* 275 */     of.visitInsn(176);
/*     */     
/* 277 */     of.visitMaxs(6, Bytecode.getArgsSize(args));
/* 278 */     of.visitEnd();
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
/*     */   private void generateGetters(String ref, String desc, Type[] args, ClassVisitor writer) {
/* 293 */     byte argIndex = 0;
/* 294 */     for (Type arg : args) {
/* 295 */       String name = "$" + argIndex;
/* 296 */       String sig = "()" + arg.getDescriptor();
/* 297 */       MethodVisitorEx get = new MethodVisitorEx(writer.visitMethod(1, name, sig, null, null));
/* 298 */       get.visitCode();
/*     */ 
/*     */       
/* 301 */       get.visitVarInsn(25, 0);
/* 302 */       get.visitFieldInsn(180, ref, "values", "[Ljava/lang/Object;");
/* 303 */       get.visitConstant(argIndex);
/* 304 */       get.visitInsn(50);
/*     */ 
/*     */       
/* 307 */       unbox((MethodVisitor)get, arg);
/*     */ 
/*     */       
/* 310 */       get.visitInsn(arg.getOpcode(172));
/*     */       
/* 312 */       get.visitMaxs(2, 1);
/* 313 */       get.visitEnd();
/* 314 */       argIndex = (byte)(argIndex + 1);
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
/*     */   private void generateSetters(String ref, String desc, Type[] args, ClassVisitor writer) {
/* 328 */     generateIndexedSetter(ref, desc, args, writer);
/* 329 */     generateMultiSetter(ref, desc, args, writer);
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
/*     */   private void generateIndexedSetter(String ref, String desc, Type[] args, ClassVisitor writer) {
/* 344 */     MethodVisitorEx set = new MethodVisitorEx(writer.visitMethod(1, "set", "(ILjava/lang/Object;)V", null, null));
/*     */     
/* 346 */     set.visitCode();
/*     */     
/* 348 */     Label store = new Label(), checkNull = new Label();
/* 349 */     Label[] labels = new Label[args.length];
/* 350 */     for (int label = 0; label < labels.length; label++) {
/* 351 */       labels[label] = new Label();
/*     */     }
/*     */ 
/*     */     
/* 355 */     set.visitVarInsn(25, 0);
/* 356 */     set.visitFieldInsn(180, ref, "values", "[Ljava/lang/Object;");
/*     */     
/*     */     byte b;
/* 359 */     for (b = 0; b < args.length; b = (byte)(b + 1)) {
/* 360 */       set.visitVarInsn(21, 1);
/* 361 */       set.visitConstant(b);
/* 362 */       set.visitJumpInsn(159, labels[b]);
/*     */     } 
/*     */ 
/*     */     
/* 366 */     throwAIOOBE(set, 1);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 371 */     for (int index = 0; index < args.length; index++) {
/* 372 */       String boxingType = Bytecode.getBoxingType(args[index]);
/* 373 */       set.visitLabel(labels[index]);
/* 374 */       set.visitVarInsn(21, 1);
/* 375 */       set.visitVarInsn(25, 2);
/* 376 */       set.visitTypeInsn(192, (boxingType != null) ? boxingType : args[index].getInternalName());
/* 377 */       set.visitJumpInsn(167, (boxingType != null) ? checkNull : store);
/*     */     } 
/*     */ 
/*     */     
/* 381 */     set.visitLabel(checkNull);
/* 382 */     set.visitInsn(89);
/* 383 */     set.visitJumpInsn(199, store);
/*     */ 
/*     */     
/* 386 */     throwNPE(set, "Argument with primitive type cannot be set to NULL");
/*     */ 
/*     */     
/* 389 */     set.visitLabel(store);
/* 390 */     set.visitInsn(83);
/* 391 */     set.visitInsn(177);
/* 392 */     set.visitMaxs(6, 3);
/* 393 */     set.visitEnd();
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
/*     */   private void generateMultiSetter(String ref, String desc, Type[] args, ClassVisitor writer) {
/* 407 */     MethodVisitorEx set = new MethodVisitorEx(writer.visitMethod(1, "setAll", "([Ljava/lang/Object;)V", null, null));
/*     */     
/* 409 */     set.visitCode();
/*     */     
/* 411 */     Label lengthOk = new Label(), nullPrimitive = new Label();
/* 412 */     int maxStack = 6;
/*     */ 
/*     */     
/* 415 */     set.visitVarInsn(25, 1);
/* 416 */     set.visitInsn(190);
/* 417 */     set.visitInsn(89);
/* 418 */     set.visitConstant((byte)args.length);
/*     */ 
/*     */     
/* 421 */     set.visitJumpInsn(159, lengthOk);
/*     */     
/* 423 */     set.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException");
/* 424 */     set.visitInsn(89);
/* 425 */     set.visitInsn(93);
/* 426 */     set.visitInsn(88);
/* 427 */     set.visitConstant((byte)args.length);
/* 428 */     set.visitLdcInsn(getSignature(args));
/*     */     
/* 430 */     set.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException", "<init>", "(IILjava/lang/String;)V", false);
/* 431 */     set.visitInsn(191);
/*     */     
/* 433 */     set.visitLabel(lengthOk);
/* 434 */     set.visitInsn(87);
/*     */ 
/*     */     
/* 437 */     set.visitVarInsn(25, 0);
/* 438 */     set.visitFieldInsn(180, ref, "values", "[Ljava/lang/Object;");
/*     */     byte index;
/* 440 */     for (index = 0; index < args.length; index = (byte)(index + 1)) {
/*     */       
/* 442 */       set.visitInsn(89);
/* 443 */       set.visitConstant(index);
/*     */ 
/*     */       
/* 446 */       set.visitVarInsn(25, 1);
/* 447 */       set.visitConstant(index);
/* 448 */       set.visitInsn(50);
/*     */ 
/*     */       
/* 451 */       String boxingType = Bytecode.getBoxingType(args[index]);
/* 452 */       set.visitTypeInsn(192, (boxingType != null) ? boxingType : args[index].getInternalName());
/*     */ 
/*     */       
/* 455 */       if (boxingType != null) {
/* 456 */         set.visitInsn(89);
/* 457 */         set.visitJumpInsn(198, nullPrimitive);
/* 458 */         maxStack = 7;
/*     */       } 
/*     */ 
/*     */       
/* 462 */       set.visitInsn(83);
/*     */     } 
/*     */     
/* 465 */     set.visitInsn(177);
/*     */     
/* 467 */     set.visitLabel(nullPrimitive);
/* 468 */     throwNPE(set, "Argument with primitive type cannot be set to NULL");
/* 469 */     set.visitInsn(177);
/*     */     
/* 471 */     set.visitMaxs(maxStack, 2);
/* 472 */     set.visitEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void throwNPE(MethodVisitorEx method, String message) {
/* 479 */     method.visitTypeInsn(187, "java/lang/NullPointerException");
/* 480 */     method.visitInsn(89);
/* 481 */     method.visitLdcInsn(message);
/* 482 */     method.visitMethodInsn(183, "java/lang/NullPointerException", "<init>", "(Ljava/lang/String;)V", false);
/* 483 */     method.visitInsn(191);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void throwAIOOBE(MethodVisitorEx method, int arg) {
/* 491 */     method.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException");
/* 492 */     method.visitInsn(89);
/* 493 */     method.visitVarInsn(21, arg);
/* 494 */     method.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException", "<init>", "(I)V", false);
/* 495 */     method.visitInsn(191);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void box(MethodVisitor method, Type var) {
/* 506 */     String boxingType = Bytecode.getBoxingType(var);
/* 507 */     if (boxingType != null) {
/* 508 */       String desc = String.format("(%s)L%s;", new Object[] { var.getDescriptor(), boxingType });
/* 509 */       method.visitMethodInsn(184, boxingType, "valueOf", desc, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void unbox(MethodVisitor method, Type var) {
/* 520 */     String boxingType = Bytecode.getBoxingType(var);
/* 521 */     if (boxingType != null) {
/* 522 */       String unboxingMethod = Bytecode.getUnboxingMethod(var);
/* 523 */       String desc = "()" + var.getDescriptor();
/* 524 */       method.visitTypeInsn(192, boxingType);
/* 525 */       method.visitMethodInsn(182, boxingType, unboxingMethod, desc, false);
/*     */     } else {
/* 527 */       method.visitTypeInsn(192, var.getInternalName());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getSignature(Type[] args) {
/* 532 */     return (new SignaturePrinter("", null, args)).setFullyQualified(true).getFormattedArgs();
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\injection\invoke\arg\ArgsClassGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */