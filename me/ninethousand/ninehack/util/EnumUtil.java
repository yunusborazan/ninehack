/*    */ package me.ninethousand.ninehack.util;
/*    */ 
/*    */ import me.ninethousand.ninehack.feature.setting.Setting;
/*    */ 
/*    */ public final class EnumUtil {
/*    */   public static String getNextEnumValue(Setting<Enum<?>> setting, boolean reverse) {
/*  7 */     Enum<?> currentValue = (Enum)setting.getValue();
/*    */     
/*  9 */     int i = 0;
/*    */     
/* 11 */     for (; i < ((Enum[])((Enum)setting.getValue()).getClass().getEnumConstants()).length; i++) {
/* 12 */       Enum<?> e = ((Enum[])((Enum)setting.getValue()).getClass().getEnumConstants())[i];
/*    */       
/* 14 */       if (e.name().equalsIgnoreCase(currentValue.name()))
/*    */         break; 
/*    */     } 
/* 17 */     return ((Enum[])((Enum)setting.getValue()).getClass().getEnumConstants())[(reverse ? ((i != 0) ? (i - 1) : (((Enum[])((Enum)setting.getValue()).getClass().getEnumConstants()).length - 1)) : (i + 1)) % ((Enum[])((Enum)setting.getValue()).getClass().getEnumConstants()).length].toString();
/*    */   }
/*    */   
/*    */   public static void setEnumValue(Setting<Enum<?>> setting, String value) {
/* 21 */     for (Enum<?> e : (Enum[])((Enum)setting.getValue()).getClass().getEnumConstants()) {
/* 22 */       if (e.name().equalsIgnoreCase(value)) {
/* 23 */         setting.setValue(e);
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\EnumUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */