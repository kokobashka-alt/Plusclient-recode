package dev.leg1tka.api.utils.rpc.utils;

import com.sun.jna.Structure;
import dev.leg1tka.api.utils.rpc.PresenceButton;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DiscordRichPresence extends Structure {
   public String largeImageKey;
   public String largeImageText;
   public String smallImageText;
   public String partyPrivacy;
   public long startTimestamp;
   public int instance;
   public String partyId;
   public int partySize;
   public long endTimestamp;
   public String details;
   public String joinSecret;
   public String spectateSecret;
   public String smallImageKey;
   public String matchSecret;
   public String state;
   public int partyMax;
   public String button_url_1;
   public String button_label_1;
   public String button_url_2;
   public String button_label_2;

   public DiscordRichPresence() {
      this.setStringEncoding("UTF-8");
   }

   public List<String> getFieldOrder() {
      return Arrays.asList(
         "state",
         "details",
         "startTimestamp",
         "endTimestamp",
         "largeImageKey",
         "largeImageText",
         "smallImageKey",
         "smallImageText",
         "partyId",
         "partySize",
         "partyMax",
         "partyPrivacy",
         "matchSecret",
         "joinSecret",
         "spectateSecret",
         "button_label_1",
         "button_url_1",
         "button_label_2",
         "button_url_2",
         "instance"
      );
   }

   public static class a {
      private final DiscordRichPresence a = new DiscordRichPresence();

      public a() {
      }

      public DiscordRichPresence.a a(String string1) {
         return this.b(string1, "");
      }

      public DiscordRichPresence.a b(String string1) {
         if (string1 != null && !string1.isEmpty()) {
            this.a.details = string1.substring(0, Math.min(string1.length(), 128));
         }

         return this;
      }

      public DiscordRichPresence.a a(String string1, String string2) {
         this.a.largeImageKey = string1;
         this.a.largeImageText = string2;
         return this;
      }

      public DiscordRichPresence.a c(String string1) {
         if (string1 != null && !string1.isEmpty()) {
            this.a.state = string1.substring(0, Math.min(string1.length(), 128));
         }

         return this;
      }

      public DiscordRichPresence.a a(boolean flag1) {
         if ((this.a.button_label_1 == null || !this.a.button_label_1.isEmpty()) && (this.a.button_label_2 == null || !this.a.button_label_2.isEmpty())) {
            this.a.instance = flag1 ? 1 : 0;
         }

         return this;
      }

      public DiscordRichPresence.a a(dev.leg1tka.api.utils.rpc.PresenceButton value1) {
         return this.a(Collections.singletonList(value1));
      }

      public DiscordRichPresence.a b(String string1, String string2) {
         this.a.smallImageKey = string1;
         this.a.smallImageText = string2;
         return this;
      }

      public DiscordRichPresence.a a(String string1, int intValue2, int intValue3) {
         if ((this.a.button_label_1 == null || !this.a.button_label_1.isEmpty()) && (this.a.button_label_2 == null || !this.a.button_label_2.isEmpty())) {
            this.a.partyId = string1;
            this.a.partySize = intValue2;
            this.a.partyMax = intValue3;
         }

         return this;
      }

      public DiscordRichPresence.a a(List<dev.leg1tka.api.utils.rpc.PresenceButton> list1) {
         if (list1 != null && !list1.isEmpty()) {
            int intValue2 = Math.min(list1.size(), 2);
            this.a.button_label_1 = ((dev.leg1tka.api.utils.rpc.PresenceButton)list1.get(0)).b();
            this.a.button_url_1 = ((dev.leg1tka.api.utils.rpc.PresenceButton)list1.get(0)).a();
            if (intValue2 == 2) {
               this.a.button_label_2 = ((dev.leg1tka.api.utils.rpc.PresenceButton)list1.get(1)).b();
               this.a.button_url_2 = ((dev.leg1tka.api.utils.rpc.PresenceButton)list1.get(1)).a();
            }
         }

         return this;
      }

      public DiscordRichPresence.a a(OffsetDateTime offsetDateTime1) {
         this.a.startTimestamp = offsetDateTime1.toEpochSecond();
         return this;
      }

      public DiscordRichPresence.a a(String string1, String string2, String string3) {
         if ((this.a.button_label_1 == null || !this.a.button_label_1.isEmpty()) && (this.a.button_label_2 == null || !this.a.button_label_2.isEmpty())) {
            this.a.matchSecret = string1;
            this.a.joinSecret = string2;
            this.a.spectateSecret = string3;
         }

         return this;
      }

      public void a(dev.leg1tka.api.utils.rpc.PresenceButton value1, dev.leg1tka.api.utils.rpc.PresenceButton value2) {
         this.a(Arrays.asList(value1, value2));
      }

      public void a(long longValue1) {
         this.a.startTimestamp = longValue1;
      }

      public DiscordRichPresence.a c(String string1, String string2) {
         if ((this.a.button_label_1 == null || !this.a.button_label_1.isEmpty()) && (this.a.button_label_2 == null || !this.a.button_label_2.isEmpty())) {
            this.a.joinSecret = string1;
            this.a.spectateSecret = string2;
         }

         return this;
      }

      public DiscordRichPresence.a b(long longValue1) {
         this.a.endTimestamp = longValue1;
         return this;
      }

      public DiscordRichPresence.a b(OffsetDateTime offsetDateTime1) {
         this.a.endTimestamp = offsetDateTime1.toEpochSecond();
         return this;
      }

      public DiscordRichPresence.a d(String string1) {
         return this.a(string1, "");
      }

      public DiscordRichPresence a() {
         return this.a;
      }
   }
}
