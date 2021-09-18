/*     */ package org.spongepowered.asm.mixin.injection.points;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.ListIterator;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @AtCode("INVOKE")
/*     */ public class BeforeInvoke
/*     */   extends InjectionPoint
/*     */ {
/*     */   protected final MemberInfo target;
/*     */   protected final MemberInfo permissiveTarget;
/*     */   protected final int ordinal;
/*     */   protected final String className;
/*     */   private boolean log = false;
/*  90 */   private final Logger logger = LogManager.getLogger("mixin");
/*     */   
/*     */   public BeforeInvoke(InjectionPointData data) {
/*  93 */     super(data);
/*     */     
/*  95 */     this.target = data.getTarget();
/*  96 */     this.ordinal = data.getOrdinal();
/*  97 */     this.log = data.get("log", false);
/*  98 */     this.className = getClassName();
/*  99 */     this.permissiveTarget = data.getContext().getOption(MixinEnvironment.Option.REFMAP_REMAP) ? this.target.transform(null) : null;
/*     */   }
/*     */   
/*     */   private String getClassName() {
/* 103 */     InjectionPoint.AtCode atCode = getClass().<InjectionPoint.AtCode>getAnnotation(InjectionPoint.AtCode.class);
/* 104 */     return String.format("@At(%s)", new Object[] { (atCode != null) ? atCode.value() : getClass().getSimpleName().toUpperCase() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BeforeInvoke setLogging(boolean logging) {
/* 114 */     this.log = logging;
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 125 */     log("{} is searching for an injection point in method with descriptor {}", new Object[] { this.className, desc });
/*     */     
/* 127 */     if (!find(desc, insns, nodes, this.target)) {
/* 128 */       return find(desc, insns, nodes, this.permissiveTarget);
/*     */     }
/* 130 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes, MemberInfo target) {
/* 134 */     if (target == null) {
/* 135 */       return false;
/*     */     }
/*     */     
/* 138 */     int ordinal = 0;
/* 139 */     boolean found = false;
/*     */     
/* 141 */     ListIterator<AbstractInsnNode> iter = insns.iterator();
/* 142 */     while (iter.hasNext()) {
/* 143 */       AbstractInsnNode insn = iter.next();
/*     */       
/* 145 */       if (matchesInsn(insn)) {
/* 146 */         MemberInfo nodeInfo = new MemberInfo(insn);
/* 147 */         log("{} is considering insn {}", new Object[] { this.className, nodeInfo });
/*     */         
/* 149 */         if (target.matches(nodeInfo.owner, nodeInfo.name, nodeInfo.desc)) {
/* 150 */           log("{} > found a matching insn, checking preconditions...", new Object[] { this.className });
/*     */           
/* 152 */           if (matchesInsn(nodeInfo, ordinal)) {
/* 153 */             log("{} > > > found a matching insn at ordinal {}", new Object[] { this.className, Integer.valueOf(ordinal) });
/*     */             
/* 155 */             found |= addInsn(insns, nodes, insn);
/*     */             
/* 157 */             if (this.ordinal == ordinal) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */           
/* 162 */           ordinal++;
/*     */         } 
/*     */       } 
/*     */       
/* 166 */       inspectInsn(desc, insns, insn);
/*     */     } 
/*     */     
/* 169 */     return found;
/*     */   }
/*     */   
/*     */   protected boolean addInsn(InsnList insns, Collection<AbstractInsnNode> nodes, AbstractInsnNode insn) {
/* 173 */     nodes.add(insn);
/* 174 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean matchesInsn(AbstractInsnNode insn) {
/* 178 */     return insn instanceof org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void inspectInsn(String desc, InsnList insns, AbstractInsnNode insn) {}
/*     */ 
/*     */   
/*     */   protected boolean matchesInsn(MemberInfo nodeInfo, int ordinal) {
/* 186 */     log("{} > > comparing target ordinal {} with current ordinal {}", new Object[] { this.className, Integer.valueOf(this.ordinal), Integer.valueOf(ordinal) });
/* 187 */     return (this.ordinal == -1 || this.ordinal == ordinal);
/*     */   }
/*     */   
/*     */   protected void log(String message, Object... params) {
/* 191 */     if (this.log)
/* 192 */       this.logger.info(message, params); 
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\org\spongepowered\asm\mixin\injection\points\BeforeInvoke.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */