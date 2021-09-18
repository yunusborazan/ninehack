/*    */ package me.ninethousand.ninehack.mixin.mixins.game;
/*    */ 
/*    */ import me.ninethousand.ninehack.mixin.accessors.game.IBlock;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ @Mixin({Block.class})
/*    */ public class MixinBlock
/*    */   implements IBlock {
/*    */   @Shadow
/*    */   protected Material field_149764_J;
/*    */   
/*    */   public Material getMaterial() {
/* 16 */     return this.field_149764_J;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\mixin\mixins\game\MixinBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */