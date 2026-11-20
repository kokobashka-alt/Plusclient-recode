package dev.leg1tka.accounts;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.AspectDLC;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;

public final class ServerReconnectTracker {
   private static ServerAddress a = null;
   private static ServerInfo b = null;

   private ServerReconnectTracker() {
   }

   public static void a(ServerAddress serverAddress0, ServerInfo serverInfo1) {
      a = serverAddress0;
      b = serverInfo1;
   }

   public static ServerAddress a() {
      return a;
   }

   public static ServerInfo b() {
      return b;
   }

   public static void a(MinecraftClient minecraftClient0) {
      if (minecraftClient0 != null && AspectDLC.INSTANCE.altStorage != null) {
         ServerInfo serverInfo1 = minecraftClient0.getCurrentServerEntry();
         if (serverInfo1 != null && serverInfo1.address != null && !serverInfo1.address.isBlank()) {
            String string2 = minecraftClient0.getSession().getUsername();

            for (dev.leg1tka.accounts.AltAccount value4 : AspectDLC.INSTANCE.altStorage.c()) {
               if (value4.c().equalsIgnoreCase(string2)) {
                  value4.e(serverInfo1.address.trim());
                  value4.a(value4.l() + 1);
                  AspectDLC.INSTANCE.altStorage.a();
                  return;
               }
            }
         }
      }
   }

   public static String a(String string0) {
      if (string0 != null && !string0.isBlank()) {
         String string1 = string0.trim().toLowerCase(Locale.ROOT);
         int intValue2 = string1.lastIndexOf(58);
         if (intValue2 > 0 && string1.indexOf(58) == intValue2) {
            string1 = string1.substring(0, intValue2);
         }

         for (String string6 : new String[]{"mc.", "play.", "join.", "www.", "pvp."}) {
            if (string1.startsWith(string6) && string1.length() > string6.length() + 3) {
               string1 = string1.substring(string6.length());
               break;
            }
         }

         return string1;
      } else {
         return "";
      }
   }

   public static List<String> c() {
      ArrayList arrayList0 = new ArrayList();
      if (AspectDLC.INSTANCE.altStorage == null) {
         return arrayList0;
      } else {
         boolean flag1 = false;

         for (dev.leg1tka.accounts.AltAccount value3 : AspectDLC.INSTANCE.altStorage.c()) {
            if (value3.b()) {
               Iterator iterator4 = value3.a().iterator();

               while (iterator4.hasNext()) {
                  dev.leg1tka.accounts.AccountBan value5 = (dev.leg1tka.accounts.AccountBan)iterator4.next();
                  if (value5.b()) {
                     iterator4.remove();
                     arrayList0.add(value3.c() + " на " + a(value5.e()));
                     flag1 = true;
                  }
               }
            }
         }

         if (flag1) {
            AspectDLC.INSTANCE.altStorage.a();
         }

         return arrayList0;
      }
   }

   public static String a(long longValue0) {
      long longValue2 = longValue0 / 1000L;
      long longValue4 = longValue2 / 86400L;
      long longValue6 = longValue2 % 86400L / 3600L;
      long longValue8 = longValue2 % 3600L / 60L;
      long longValue10 = longValue2 % 60L;
      if (longValue4 > 0L) {
         return longValue4 + "д " + longValue6 + "ч";
      } else {
         return longValue6 > 0L ? longValue6 + "ч " + longValue8 + "м" : String.format(Locale.ROOT, "%02d:%02d", longValue8, longValue10);
      }
   }
}
