/*     */ package me.ninethousand.ninehack.util;
/*     */ 
/*     */ import java.util.Random;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class TextUtil {
/*     */   public static final String SECTIONSIGN = "§";
/*     */   public static final String BLACK = "§0";
/*     */   public static final String DARK_BLUE = "§1";
/*     */   public static final String DARK_GREEN = "§2";
/*     */   public static final String DARK_AQUA = "§3";
/*     */   public static final String DARK_RED = "§4";
/*     */   public static final String DARK_PURPLE = "§5";
/*     */   public static final String GOLD = "§6";
/*     */   public static final String GRAY = "§7";
/*     */   public static final String DARK_GRAY = "§8";
/*     */   public static final String BLUE = "§9";
/*     */   public static final String GREEN = "§a";
/*     */   public static final String AQUA = "§b";
/*     */   public static final String RED = "§c";
/*     */   public static final String LIGHT_PURPLE = "§d";
/*     */   public static final String YELLOW = "§e";
/*     */   public static final String WHITE = "§f";
/*     */   public static final String OBFUSCATED = "§k";
/*     */   public static final String BOLD = "§l";
/*     */   public static final String STRIKE = "§m";
/*     */   public static final String UNDERLINE = "§n";
/*     */   public static final String ITALIC = "§o";
/*     */   public static final String RESET = "§r";
/*     */   public static final String RAINBOW = "§+";
/*     */   public static final String blank = " ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒";
/*     */   public static final String line1 = " ███▒█▒█▒███▒███▒███▒███";
/*     */   public static final String line2 = " █▒█▒█▒█▒█▒█▒█▒█▒█▒█▒█▒▒";
/*     */   public static final String line3 = " ███▒███▒█▒█▒███▒█▒█▒███";
/*     */   public static final String line4 = " █▒▒▒█▒█▒█▒█▒█▒█▒█▒█▒▒▒█";
/*     */   public static final String line5 = " █▒▒▒█▒█▒███▒███▒███▒███";
/*     */   public static final String pword = "  ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒\n ███▒█▒█▒███▒███▒███▒███\n █▒█▒█▒█▒█▒█▒█▒█▒█▒█▒█▒▒\n ███▒███▒█▒█▒███▒█▒█▒███\n █▒▒▒█▒█▒█▒█▒█▒█▒█▒█▒▒▒█\n █▒▒▒█▒█▒███▒███▒███▒███\n ▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒";
/*  38 */   private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
/*  39 */   private static final Random rand = new Random();
/*  40 */   public static String shrug = "¯\\_(ツ)_/¯";
/*     */   
/*     */   public static String stripColor(String input) {
/*  43 */     if (input == null) {
/*  44 */       return null;
/*     */     }
/*  46 */     return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
/*     */   }
/*     */   
/*     */   public static String coloredString(String string, Color color) {
/*  50 */     String coloredString = string;
/*  51 */     switch (color) {
/*     */       case AQUA:
/*  53 */         coloredString = "§b" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case WHITE:
/*  57 */         coloredString = "§f" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case BLACK:
/*  61 */         coloredString = "§0" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case DARK_BLUE:
/*  65 */         coloredString = "§1" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case DARK_GREEN:
/*  69 */         coloredString = "§2" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case DARK_AQUA:
/*  73 */         coloredString = "§3" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case DARK_RED:
/*  77 */         coloredString = "§4" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case DARK_PURPLE:
/*  81 */         coloredString = "§5" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case GOLD:
/*  85 */         coloredString = "§6" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case DARK_GRAY:
/*  89 */         coloredString = "§8" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case GRAY:
/*  93 */         coloredString = "§7" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case BLUE:
/*  97 */         coloredString = "§9" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case RED:
/* 101 */         coloredString = "§c" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case GREEN:
/* 105 */         coloredString = "§a" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case LIGHT_PURPLE:
/* 109 */         coloredString = "§d" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case YELLOW:
/* 113 */         coloredString = "§e" + coloredString + "§r";
/*     */         break;
/*     */       
/*     */       case RAINBOW:
/* 117 */         coloredString = "§+" + coloredString + "§r"; break;
/*     */     } 
/* 119 */     return coloredString;
/*     */   }
/*     */   
/*     */   public static String cropMaxLengthMessage(String s, int i) {
/* 123 */     String output = "";
/* 124 */     if (s.length() >= 256 - i) {
/* 125 */       output = s.substring(0, 256 - i);
/*     */     }
/* 127 */     return output;
/*     */   }
/*     */   
/*     */   public enum Color {
/* 131 */     NONE,
/* 132 */     WHITE,
/* 133 */     BLACK,
/* 134 */     DARK_BLUE,
/* 135 */     DARK_GREEN,
/* 136 */     DARK_AQUA,
/* 137 */     DARK_RED,
/* 138 */     DARK_PURPLE,
/* 139 */     GOLD,
/* 140 */     GRAY,
/* 141 */     DARK_GRAY,
/* 142 */     BLUE,
/* 143 */     GREEN,
/* 144 */     AQUA,
/* 145 */     RED,
/* 146 */     LIGHT_PURPLE,
/* 147 */     YELLOW,
/* 148 */     RAINBOW;
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\me\ninethousand\ninehac\\util\TextUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */