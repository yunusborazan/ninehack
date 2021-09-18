/*    */ package me.ninethousand.ninehack.mixin.mixins.game;
/*    */ 
/*    */ import me.ninethousand.ninehack.mixin.accessors.game.IEntity;
/*    */ import net.minecraft.entity.Entity;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ @Mixin({Entity.class})
/*    */ public class MixinEntity
/*    */   implements IEntity {
/*    */   @Shadow
/*    */   protected boolean field_70134_J;
/*    */   
/*    */   public boolean getIsInWeb() {
/* 15 */     return this.field_70134_J;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setIsInWeb(boolean isInWeb) {
/* 20 */     this.field_70134_J = isInWeb;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\mixin\mixins\game\MixinEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */