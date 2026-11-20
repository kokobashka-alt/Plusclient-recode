package dev.leg1tka.network.bots;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.world.ClientWorld;

public final class BotTaskScheduler implements dev.leg1tka.network.bots.BotTask {
   private static final dev.leg1tka.network.bots.BotTaskScheduler a = new dev.leg1tka.network.bots.BotTaskScheduler();
   private static int b;
   private static int c;
   private static final Map<String, dev.leg1tka.network.bots.BotLootTracker> d = new LinkedHashMap<>();
   private static final Map<String, String> e = new HashMap<>();
   private static final Map<String, Integer> f = new HashMap<>();
   private static final ArrayDeque<String> g = new ArrayDeque<>();
   private static String h;
   private static int i;
   private static int j;

   private BotTaskScheduler() {
   }

   public static void a() {
      dev.leg1tka.network.bots.BotController.a(a);
   }

   public static String b() {
      return h;
   }

   public static boolean a(String string0) {
      return string0 != null && d.containsKey(g(string0));
   }

   public static String b(String string0) {
      if (string0 != null && !string0.isBlank()) {
         String string1 = g(string0);
         if (d.containsKey(string1)) {
            return "уже запущен";
         } else {
            MinecraftClient minecraftClient2 = MinecraftClient.getInstance();
            boolean flag3 = minecraftClient2.player != null && string0.equalsIgnoreCase(dev.leg1tka.network.bots.BotController.c());
            String string5 = string0;
            Object object4;
            if (flag3) {
               object4 = new dev.leg1tka.network.bots.BaritoneBotNavigator();
            } else {
               dev.leg1tka.network.bots.BotSession value6 = dev.leg1tka.network.bots.BotController.d(string0);
               if (value6 == null) {
                  return "нет такого аккаунта";
               }

               if (!BotManager.c.i.getValue()) {
                  return "включи «Физика в фоне» в BotManager";
               }

               object4 = new dev.leg1tka.network.bots.GridBotNavigator(value6);
               string5 = value6.a();
            }

            d.put(string1, new dev.leg1tka.network.bots.BotLootTracker(string5, (dev.leg1tka.network.bots.BotNavigator)object4, dev.leg1tka.network.bots.BotTaskScheduler::f));
            f("§7[WardenBot §f" + string5 + "§7] §aзапущен");
            return null;
         }
      } else {
         return "пустой ник";
      }
   }

   public static boolean c(String string0) {
      dev.leg1tka.network.bots.BotLootTracker value1 = string0 == null ? null : (dev.leg1tka.network.bots.BotLootTracker)d.remove(g(string0));
      if (value1 == null) {
         return false;
      } else {
         value1.d();
         f("§7[WardenBot §f" + value1.a + "§7] §cостановлен");
         return true;
      }
   }

   public static void c() {
      for (dev.leg1tka.network.bots.BotLootTracker value1 : d.values()) {
         value1.d();
      }

      d.clear();
      e.clear();
      f.clear();
   }

   public static List<String> d() {
      ArrayList arrayList0 = new ArrayList();

      for (dev.leg1tka.network.bots.BotLootTracker value2 : d.values()) {
         arrayList0.add(value2.a);
      }

      return arrayList0;
   }

   public static dev.leg1tka.network.bots.BotLootTracker d(String string0) {
      return string0 == null ? null : (dev.leg1tka.network.bots.BotLootTracker)d.get(g(string0));
   }

   public static void e() {
      MinecraftClient minecraftClient0 = MinecraftClient.getInstance();
      ClientPlayerEntity clientPlayerEntity1 = minecraftClient0.player;
      if (clientPlayerEntity1 != null && minecraftClient0.world != null && minecraftClient0.interactionManager != null) {
         long longValue2 = System.currentTimeMillis();
         if (h == null || --i <= 0) {
            i = 10;
            ServerInfo serverInfo4 = minecraftClient0.getCurrentServerEntry();
            h = dev.leg1tka.network.bots.BotMessageParser.a(serverInfo4 != null ? serverInfo4.address : null, minecraftClient0.world, clientPlayerEntity1);
            dev.leg1tka.network.bots.BotMessageParser.a(h, minecraftClient0.world, clientPlayerEntity1, longValue2);
         }

         String string6 = dev.leg1tka.network.bots.BotController.c();
         dev.leg1tka.network.bots.BotLootTracker value5 = (dev.leg1tka.network.bots.BotLootTracker)d.get(g(string6));
         if (value5 != null) {
            if (!(value5.a() instanceof dev.leg1tka.network.bots.BaritoneBotNavigator)) {
               value5.a(new dev.leg1tka.network.bots.BaritoneBotNavigator());
            }

            value5.a(h, clientPlayerEntity1, minecraftClient0.world, minecraftClient0.interactionManager, longValue2);
            if (value5.b()) {
               d.remove(g(string6));
            }
         }

         if (++j >= 20) {
            j = 0;
            e(string6);
         }
      }

      f();
   }

   @Override
   public boolean a(dev.leg1tka.network.bots.BotSession value1, ClientPlayerEntity clientPlayerEntity2, ClientWorld clientWorld3) {
      if (!WardenHelper.c.isEnabled()) {
         return false;
      } else {
         MinecraftClient minecraftClient4 = MinecraftClient.getInstance();
         long longValue5 = System.currentTimeMillis();
         String string7 = g(value1.a());
         String string8 = (String)e.get(string7);
         int intValue9 = f.getOrDefault(string7, 0) - 1;
         if (string8 == null || intValue9 <= 0) {
            intValue9 = 10;
            string8 = dev.leg1tka.network.bots.BotMessageParser.a(value1.b(), clientWorld3, clientPlayerEntity2);
            e.put(string7, string8);
            dev.leg1tka.network.bots.BotMessageParser.a(string8, clientWorld3, clientPlayerEntity2, longValue5);
         }

         f.put(string7, intValue9);
         dev.leg1tka.network.bots.BotLootTracker value10 = (dev.leg1tka.network.bots.BotLootTracker)d.get(string7);
         if (value10 == null) {
            return false;
         } else if (!BotManager.c.i.getValue()) {
            d.remove(string7);
            value10.d();
            f(
               "§7[WardenBot §f"
                  + value10.a
                  + "§7] §cостановлен: выключена «Физика в фоне»"
            );
            return false;
         } else {
            if (!(value10.a() instanceof dev.leg1tka.network.bots.GridBotNavigator value11) || !value11.a(value1)) {
               value10.a(new dev.leg1tka.network.bots.GridBotNavigator(value1));
            }

            if (minecraftClient4.interactionManager == null) {
               return true;
            } else {
               value10.a(string8, clientPlayerEntity2, clientWorld3, minecraftClient4.interactionManager, longValue5);
               if (value10.b()) {
                  d.remove(string7);
                  value1.e().a();
                  return false;
               } else {
                  return true;
               }
            }
         }
      }
   }

   @Override
   public boolean a(dev.leg1tka.network.bots.BotSession value1) {
      dev.leg1tka.network.bots.BotLootTracker value2 = (dev.leg1tka.network.bots.BotLootTracker)d.get(g(value1.a()));
      return value2 != null && value2.c();
   }

   @Override
   public void b(dev.leg1tka.network.bots.BotSession value1) {
      String string2 = g(value1.a());
      e.remove(string2);
      f.remove(string2);
      dev.leg1tka.network.bots.BotLootTracker value3 = (dev.leg1tka.network.bots.BotLootTracker)d.remove(string2);
      if (value3 != null) {
         value3.d();
         f(
            "§7[WardenBot §f"
               + value3.a
               + "§7] §cостановлен: аккаунт отключён"
         );
      }
   }

   private static void e(String string0) {
      Iterator iterator1 = d.entrySet().iterator();

      while (iterator1.hasNext()) {
         Entry entry2 = (Entry)iterator1.next();
         if (!((String)entry2.getKey()).equals(g(string0)) && dev.leg1tka.network.bots.BotController.d((String)entry2.getKey()) == null) {
            ((dev.leg1tka.network.bots.BotLootTracker)entry2.getValue()).d();
            iterator1.remove();
            f(
               "§7[WardenBot §f"
                  + ((dev.leg1tka.network.bots.BotLootTracker)entry2.getValue()).a
                  + "§7] §cостановлен: аккаунта нет"
            );
         }
      }
   }

   private static void f(String string0) {
      if (g.size() >= 64) {
         g.pollFirst();
      }

      g.addLast(string0);
   }

   private static void f() {
      if (!dev.leg1tka.network.bots.BotController.f() && MinecraftClient.getInstance().player != null) {
         String string0;
         while ((string0 = (String)g.pollFirst()) != null) {
            dev.leg1tka.api.utils.text.ClientChat.a(string0);
         }
      }
   }

   private static String g(String string0) {
      return string0 == null ? "" : string0.toLowerCase(Locale.ROOT);
   }
}
