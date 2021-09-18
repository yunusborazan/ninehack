/*     */ package club.minnced.discord.rpc;
/*     */ 
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
/*     */ public class DiscordRichPresence
/*     */   extends Structure
/*     */ {
/*  51 */   private static final List<String> FIELD_ORDER = Collections.unmodifiableList(Arrays.asList(new String[] { "state", "details", "startTimestamp", "endTimestamp", "largeImageKey", "largeImageText", "smallImageKey", "smallImageText", "partyId", "partySize", "partyMax", "matchSecret", "joinSecret", "spectateSecret", "instance" }));
/*     */   
/*     */   public String state;
/*     */   
/*     */   public String details;
/*     */   public long startTimestamp;
/*     */   public long endTimestamp;
/*     */   public String largeImageKey;
/*     */   public String largeImageText;
/*     */   public String smallImageKey;
/*     */   public String smallImageText;
/*     */   public String partyId;
/*     */   public int partySize;
/*     */   public int partyMax;
/*     */   public String matchSecret;
/*     */   public String joinSecret;
/*     */   public String spectateSecret;
/*     */   public byte instance;
/*     */   
/*     */   public DiscordRichPresence(String encoding) {
/*  71 */     setStringEncoding(encoding);
/*     */   }
/*     */   
/*     */   public DiscordRichPresence() {
/*  75 */     this("UTF-8");
/*     */   }
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
/*     */   public boolean equals(Object o) {
/* 195 */     if (this == o)
/* 196 */       return true; 
/* 197 */     if (!(o instanceof DiscordRichPresence))
/* 198 */       return false; 
/* 199 */     DiscordRichPresence presence = (DiscordRichPresence)o;
/* 200 */     return (this.startTimestamp == presence.startTimestamp && this.endTimestamp == presence.endTimestamp && this.partySize == presence.partySize && this.partyMax == presence.partyMax && this.instance == presence.instance && 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 205 */       Objects.equals(this.state, presence.state) && 
/* 206 */       Objects.equals(this.details, presence.details) && 
/* 207 */       Objects.equals(this.largeImageKey, presence.largeImageKey) && 
/* 208 */       Objects.equals(this.largeImageText, presence.largeImageText) && 
/* 209 */       Objects.equals(this.smallImageKey, presence.smallImageKey) && 
/* 210 */       Objects.equals(this.smallImageText, presence.smallImageText) && 
/* 211 */       Objects.equals(this.partyId, presence.partyId) && 
/* 212 */       Objects.equals(this.matchSecret, presence.matchSecret) && 
/* 213 */       Objects.equals(this.joinSecret, presence.joinSecret) && 
/* 214 */       Objects.equals(this.spectateSecret, presence.spectateSecret));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 220 */     return Objects.hash(new Object[] { this.state, this.details, Long.valueOf(this.startTimestamp), Long.valueOf(this.endTimestamp), this.largeImageKey, this.largeImageText, this.smallImageKey, this.smallImageText, this.partyId, 
/* 221 */           Integer.valueOf(this.partySize), Integer.valueOf(this.partyMax), this.matchSecret, this.joinSecret, this.spectateSecret, Byte.valueOf(this.instance) });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> getFieldOrder() {
/* 227 */     return FIELD_ORDER;
/*     */   }
/*     */ }


/* Location:              C:\Users\tarka\Downloads\ninehack-1.0.1-release.jar!\club\minnced\discord\rpc\DiscordRichPresence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */