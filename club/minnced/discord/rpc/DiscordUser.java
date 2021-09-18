/*    */ package club.minnced.discord.rpc;
/*    */ 
/*    */ import com.sun.jna.Structure;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DiscordUser
/*    */   extends Structure
/*    */ {
/* 40 */   private static final List<String> FIELD_ORDER = Collections.unmodifiableList(Arrays.asList(new String[] { "userId", "username", "discriminator", "avatar" }));
/*    */   
/*    */   public String userId;
/*    */   
/*    */   public String username;
/*    */   public String discriminator;
/*    */   public String avatar;
/*    */   
/*    */   public DiscordUser(String encoding) {
/* 49 */     setStringEncoding(encoding);
/*    */   }
/*    */   
/*    */   public DiscordUser() {
/* 53 */     this("UTF-8");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 79 */     if (this == o)
/* 80 */       return true; 
/* 81 */     if (!(o instanceof DiscordUser))
/* 82 */       return false; 
/* 83 */     DiscordUser that = (DiscordUser)o;
/* 84 */     return (Objects.equals(this.userId, that.userId) && 
/* 85 */       Objects.equals(this.username, that.username) && 
/* 86 */       Objects.equals(this.discriminator, that.discriminator) && 
/* 87 */       Objects.equals(this.avatar, that.avatar));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 93 */     return Objects.hash(new Object[] { this.userId, this.username, this.discriminator, this.avatar });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected List<String> getFieldOrder() {
/* 99 */     return FIELD_ORDER;
/*    */   }
/*    */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\club\minnced\discord\rpc\DiscordUser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */