/*    */ package me.ninethousand.ninehack.feature.gui.notifications;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.ninethousand.ninehack.NineHack;
/*    */ import me.ninethousand.ninehack.util.RenderUtil;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ 
/*    */ public class Notification
/*    */   implements NineHack.Globals
/*    */ {
/*    */   private NotificationType type;
/*    */   private String title;
/*    */   private String messsage;
/*    */   private long start;
/*    */   private long fadedIn;
/*    */   private long fadeOut;
/*    */   private long end;
/*    */   
/*    */   public Notification(NotificationType type, String title, String messsage, int length) {
/* 20 */     this.type = type;
/* 21 */     this.title = title;
/* 22 */     this.messsage = messsage;
/*    */     
/* 24 */     this.fadedIn = (200 * length);
/* 25 */     this.fadeOut = this.fadedIn + (500 * length);
/* 26 */     this.end = this.fadeOut + this.fadedIn;
/*    */   }
/*    */   
/*    */   public void show() {
/* 30 */     this.start = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public boolean isShown() {
/* 34 */     return (getTime() <= this.end);
/*    */   }
/*    */   
/*    */   private long getTime() {
/* 38 */     return System.currentTimeMillis() - this.start;
/*    */   }
/*    */   public void render() {
/*    */     Color color1;
/* 42 */     ScaledResolution res = new ScaledResolution(mc);
/* 43 */     double offset = 0.0D;
/* 44 */     int width = 120;
/* 45 */     int height = 30;
/* 46 */     long time = getTime();
/*    */     
/* 48 */     if (time < this.fadedIn) {
/* 49 */       offset = Math.tanh(time / this.fadedIn * 3.0D) * width;
/* 50 */     } else if (time > this.fadeOut) {
/* 51 */       offset = Math.tanh(3.0D - (time - this.fadeOut) / (this.end - this.fadeOut) * 3.0D) * width;
/*    */     } else {
/* 53 */       offset = width;
/*    */     } 
/*    */     
/* 56 */     Color color = new Color(0, 0, 0, 220);
/*    */ 
/*    */     
/* 59 */     if (this.type == NotificationType.INFO) {
/* 60 */       color1 = new Color(0, 26, 169);
/* 61 */     } else if (this.type == NotificationType.WARNING) {
/* 62 */       color1 = new Color(204, 193, 0);
/*    */     } else {
/* 64 */       color1 = new Color(204, 0, 18);
/* 65 */       int i = Math.max(0, Math.min(255, (int)(Math.sin(time / 100.0D) * 255.0D / 2.0D + 127.5D)));
/* 66 */       color = new Color(i, 0, 0, 220);
/*    */     } 
/*    */     
/* 69 */     RenderUtil.drawRect(res.func_78326_a() - offset, (res.func_78328_b() - 5 - height), res.func_78326_a(), (res.func_78328_b() - 5), color);
/* 70 */     RenderUtil.drawRect(res.func_78326_a() - offset, (res.func_78328_b() - 5 - height), res.func_78326_a() - offset + 4.0D, (res.func_78328_b() - 5), color1);
/*    */     
/* 72 */     NineHack.TEXT_MANAGER.drawStringWithShadow(this.title, (float)(res.func_78326_a() - offset + 8.0D), (res.func_78328_b() - 2 - height), -1);
/* 73 */     NineHack.TEXT_MANAGER.drawStringWithShadow(this.messsage, (float)(res.func_78326_a() - offset + 8.0D), (res.func_78328_b() - 15), -1);
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\feature\gui\notifications\Notification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */