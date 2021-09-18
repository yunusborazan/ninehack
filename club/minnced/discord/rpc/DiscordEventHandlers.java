/*     */ package club.minnced.discord.rpc;
/*     */ 
/*     */ import com.sun.jna.Callback;
/*     */ import com.sun.jna.Structure;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
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
/*     */ public class DiscordEventHandlers
/*     */   extends Structure
/*     */ {
/*  75 */   private static final List<String> FIELD_ORDER = Collections.unmodifiableList(Arrays.asList(new String[] { "ready", "disconnected", "errored", "joinGame", "spectateGame", "joinRequest" }));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OnReady ready;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OnStatus disconnected;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OnStatus errored;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OnGameUpdate joinGame;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OnGameUpdate spectateGame;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OnJoinRequest joinRequest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 112 */     if (this == o)
/* 113 */       return true; 
/* 114 */     if (!(o instanceof DiscordEventHandlers))
/* 115 */       return false; 
/* 116 */     DiscordEventHandlers that = (DiscordEventHandlers)o;
/* 117 */     return (Objects.equals(this.ready, that.ready) && 
/* 118 */       Objects.equals(this.disconnected, that.disconnected) && 
/* 119 */       Objects.equals(this.errored, that.errored) && 
/* 120 */       Objects.equals(this.joinGame, that.joinGame) && 
/* 121 */       Objects.equals(this.spectateGame, that.spectateGame) && 
/* 122 */       Objects.equals(this.joinRequest, that.joinRequest));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 128 */     return Objects.hash(new Object[] { this.ready, this.disconnected, this.errored, this.joinGame, this.spectateGame, this.joinRequest });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> getFieldOrder() {
/* 134 */     return FIELD_ORDER;
/*     */   }
/*     */   
/*     */   public static interface OnJoinRequest extends Callback {
/*     */     void accept(DiscordUser param1DiscordUser);
/*     */   }
/*     */   
/*     */   public static interface OnGameUpdate extends Callback {
/*     */     void accept(String param1String);
/*     */   }
/*     */   
/*     */   public static interface OnStatus extends Callback {
/*     */     void accept(int param1Int, String param1String);
/*     */   }
/*     */   
/*     */   public static interface OnReady extends Callback {
/*     */     void accept(DiscordUser param1DiscordUser);
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\club\minnced\discord\rpc\DiscordEventHandlers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */