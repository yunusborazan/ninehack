/*    */ package me.ninethousand.ninehack.event.events;
/*    */ 
/*    */ import me.ninethousand.ninehack.event.EventStage;
/*    */ import net.minecraft.entity.MoverType;
/*    */ import net.minecraftforge.fml.common.eventhandler.Cancelable;
/*    */ 
/*    */ @Cancelable
/*    */ public class MoveEvent extends EventStage {
/*    */   private MoverType type;
/*    */   private double x;
/*    */   private double y;
/*    */   private double z;
/*    */   
/*    */   public MoveEvent(int stage, MoverType type, double x, double y, double z) {
/* 15 */     super(stage);
/* 16 */     this.type = type;
/* 17 */     this.x = x;
/* 18 */     this.y = y;
/* 19 */     this.z = z;
/*    */   }
/*    */   
/*    */   public MoverType getType() {
/* 23 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setType(MoverType type) {
/* 27 */     this.type = type;
/*    */   }
/*    */   
/*    */   public double getX() {
/* 31 */     return this.x;
/*    */   }
/*    */   
/*    */   public void setX(double x) {
/* 35 */     this.x = x;
/*    */   }
/*    */   
/*    */   public double getY() {
/* 39 */     return this.y;
/*    */   }
/*    */   
/*    */   public void setY(double y) {
/* 43 */     this.y = y;
/*    */   }
/*    */   
/*    */   public double getZ() {
/* 47 */     return this.z;
/*    */   }
/*    */   
/*    */   public void setZ(double z) {
/* 51 */     this.z = z;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\event\events\MoveEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */