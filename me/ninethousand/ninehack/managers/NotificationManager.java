/*    */ package me.ninethousand.ninehack.managers;
/*    */ 
/*    */ import java.util.concurrent.LinkedBlockingQueue;
/*    */ import me.ninethousand.ninehack.feature.gui.notifications.Notification;
/*    */ 
/*    */ public class NotificationManager
/*    */ {
/*  8 */   private static LinkedBlockingQueue<Notification> pendingNotifications = new LinkedBlockingQueue<>();
/*  9 */   private static Notification currentNotification = null;
/*    */   
/*    */   public static void show(Notification notification) {
/* 12 */     pendingNotifications.add(notification);
/*    */   }
/*    */   
/*    */   public static void update() {
/* 16 */     if (currentNotification != null && !currentNotification.isShown()) {
/* 17 */       currentNotification = null;
/*    */     }
/*    */     
/* 20 */     if (currentNotification == null && !pendingNotifications.isEmpty()) {
/* 21 */       currentNotification = pendingNotifications.poll();
/* 22 */       currentNotification.show();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static void render() {
/* 28 */     update();
/*    */     
/* 30 */     if (currentNotification != null)
/* 31 */       currentNotification.render(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehack\managers\NotificationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */