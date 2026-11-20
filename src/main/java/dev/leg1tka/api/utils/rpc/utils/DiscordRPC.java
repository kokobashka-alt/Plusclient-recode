package dev.leg1tka.api.utils.rpc.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface DiscordRPC extends Library {
   DiscordRPC a = (DiscordRPC)Native.loadLibrary("discord-rpc", DiscordRPC.class);

   void Discord_UpdateHandlers(DiscordEventHandlers discordEventHandlers1);

   void Discord_UpdatePresence(DiscordRichPresence discordRichPresence1);

   void Discord_Respond(String string1, int intValue2);

   void Discord_Register(String string1, String string2);

   void Discord_Shutdown();

   void Discord_UpdateConnection();

   void Discord_RegisterSteamGame(String string1, String string2);

   void Discord_RunCallbacks();

   void Discord_Initialize(String string1, DiscordEventHandlers discordEventHandlers2, boolean flag3, String string4);

   void Discord_ClearPresence();

   public static enum a {
      NO(0),
      IGNORE(2),
      YES(1);

      public final int a;

      private a(int intValue3) {
         this.a = intValue3;
      }

      private static DiscordRPC.a[] a() {
         return new DiscordRPC.a[]{NO, YES, IGNORE};
      }
   }
}
