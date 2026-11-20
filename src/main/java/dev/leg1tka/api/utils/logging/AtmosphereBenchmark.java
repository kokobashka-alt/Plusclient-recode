package dev.leg1tka.api.utils.logging;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.mojang.blaze3d.platform.GlDebugInfo;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents.End;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.GameRules.BooleanRule;

public final class AtmosphereBenchmark {
   private static final String[] a = new String[]{
      "Частицы",
      "Покрытие мира",
      "Объёмный туман",
      "Лучи света",
      "Небо",
      "Дымка",
      "Цветокоррекция"
   };
   private static final dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a[] b = new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a[]{
      new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a("ВЫКЛ", "", 6000L, "полдень"),
      new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a("Зима", "Высокое", 6000L, "полдень"),
      new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a("Зима", "Высокое", 12650L, "закат"),
      new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a("Зима", "Высокое", 18000L, "ночь"),
      new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a("Зима", "Среднее", 6000L, "полдень"),
      new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a("Зима", "Низкое", 6000L, "полдень"),
      new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a(
         "Зима",
         "Высокое",
         12650L,
         "закат",
         "Объёмный туман"
      ),
      new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a(
         "Зима",
         "Высокое",
         12650L,
         "закат",
         "Лучи света"
      ),
      new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a(
         "Зима",
         "Высокое",
         12650L,
         "закат",
         "Покрытие мира"
      ),
      new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a("Сакура", "Высокое", 6000L, "полдень"),
      new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a("Сакура", "Высокое", 12650L, "закат"),
      new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a("Сакура", "Высокое", 18000L, "ночь"),
      new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a("Сакура", "Низкое", 6000L, "полдень"),
      new dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a("ВЫКЛ", "", 6000L, "полдень")
   };
   private static long c;
   private static int d = 0;
   private static int e = -1;
   private static long f = 0L;
   private static long g = 0L;
   private static double h = 0.0;
   private static int i = 0;
   private static final List<Double> j = new ArrayList<>();
   private static final StringBuilder k = new StringBuilder();
   private static boolean l = false;
   private static boolean m = false;
   private static boolean n = false;
   private static float o = Float.NaN;

   private AtmosphereBenchmark() {
   }

   public static void a() {
      Atmosphere.g = true;
      ClientTickEvents.END_CLIENT_TICK.register(dev.leg1tka.api.utils.logging.AtmosphereBenchmark::a);
      WorldRenderEvents.END.register((End)worldRenderContext0 -> b());
      k.append("Atmosphere bench\n");
   }

   private static void a(MinecraftClient minecraftClient0) {
      if (!l && minecraftClient0.world != null && minecraftClient0.player != null) {
         d++;
         if (d >= 200) {
            if (Float.isNaN(o)) {
               o = minecraftClient0.player.getYaw();
               Interface interface1 = ModuleManager.U;
               if (interface1 != null && interface1.isEnabled()) {
                  interface1.setEnabled(false);
               }
            }

            minecraftClient0.options.hudHidden = true;
            long longValue7 = System.currentTimeMillis();
            if (e < 0 || longValue7 - f >= 10000L) {
               if (e >= 0) {
                  c();
               }

               e++;
               if (e >= b.length) {
                  l = true;
                  b(minecraftClient0);
                  minecraftClient0.scheduleStop();
                  return;
               }

               a(minecraftClient0, longValue7);
            }

            long longValue3 = longValue7 - f;
            dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a value5 = b[e];
            boolean flag6 = longValue3 >= 5000L;
            minecraftClient0.player.setYaw(!flag6 && value5.label.equals("закат") ? 90.0F : o);
            minecraftClient0.player.setPitch(flag6 ? 38.0F : -6.0F);
            if (!m && longValue3 > 4600L) {
               m = true;
               a(minecraftClient0, "atmo_" + e + "_a.png");
            }

            if (!n && longValue3 > 9600L) {
               n = true;
               a(minecraftClient0, "atmo_" + e + "_b.png");
            }
         }
      }
   }

   private static void a(MinecraftClient minecraftClient0, String string1) {
      ScreenshotRecorder.saveScreenshot(FabricLoader.getInstance().getGameDir().toFile(), string1, minecraftClient0.getFramebuffer(), text0x -> {
      });
   }

   private static void a(MinecraftClient minecraftClient0, long longValue1) {
      dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a value3 = b[e];
      Atmosphere atmosphere4 = Atmosphere.c;
      IntegratedServer integratedServer5 = minecraftClient0.getServer();
      if (integratedServer5 != null) {
         long longValue6 = value3.time;
         integratedServer5.execute(() -> {
            integratedServer5.getOverworld().setTimeOfDay(longValue6);
            ((BooleanRule)integratedServer5.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE)).set(false, integratedServer5);
            integratedServer5.getOverworld().setWeather(12000, 0, false, false);
         });
      }

      if (value3.type.equals("ВЫКЛ")) {
         if (atmosphere4.isEnabled()) {
            atmosphere4.setEnabled(false);
         }
      } else {
         atmosphere4.p().a(value3.type);
         atmosphere4.q().a(value3.q);

         for (String string9 : a) {
            atmosphere4.r().a(string9, !string9.equals(value3.off));
         }

         if (!atmosphere4.isEnabled()) {
            atmosphere4.setEnabled(true);
         }
      }

      f = longValue1;
      n = false;
      m = false;
      h = 0.0;
      i = 0;
      j.clear();
      Atmosphere.i = 0.0;
      Atmosphere.j = 0;
   }

   private static void b() {
      long longValue0 = System.nanoTime();
      long longValue2 = System.currentTimeMillis() - f;
      boolean flag4 = e >= 0 && (longValue2 > 2000L && longValue2 < 4500L || longValue2 > 7000L && longValue2 < 9500L);
      if (g != 0L && flag4) {
         double doubleValue5 = (double)(longValue0 - g) / 1000000.0;
         h += doubleValue5;
         i++;
         j.add(doubleValue5);
      }

      g = longValue0;
   }

   private static void c() {
      dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a value0 = b[e];
      double doubleValue1 = i > 0 ? h / (double)i : 0.0;
      j.sort(Double::compare);
      double doubleValue3 = j.isEmpty() ? 0.0 : (Double)j.get(Math.min(j.size() - 1, (int)((double)j.size() * 0.99)));
      double doubleValue5 = Atmosphere.j > 0 ? Atmosphere.i / (double)Atmosphere.j / 1000000.0 : 0.0;
      String string7 = value0.type + (value0.q.isEmpty() ? "" : " / " + value0.q) + " / " + value0.label + (value0.off.isEmpty() ? "" : " без: " + value0.off);
      k.append(
         String.format(
            Locale.ROOT,
            "%2d %-52s кадр %.2f мс (%.0f FPS), 1%% худших %.2f мс, атмосфера на GPU %.3f мс%n",
            e,
            string7,
            doubleValue1,
            doubleValue1 > 0.0 ? 1000.0 / doubleValue1 : 0.0,
            doubleValue3,
            doubleValue5
         )
      );
      System.out.println("[AtmoBench] phase " + e + " gpu " + String.format(Locale.ROOT, "%.3f", doubleValue5));
   }

   private static void b(MinecraftClient minecraftClient0) {
      k.append(
         String.format(
            Locale.ROOT,
            "окно %dx%d, %s%n",
            minecraftClient0.getWindow().getFramebufferWidth(),
            minecraftClient0.getWindow().getFramebufferHeight(),
            GlDebugInfo.getRenderer()
         )
      );
      Path path1 = FabricLoader.getInstance().getGameDir().resolve("atmo_bench.txt");

      try {
         Files.writeString(path1, k.toString(), StandardCharsets.UTF_8);
      } catch (IOException iOException3) {
      }
   }

   static record a(String type, String q, long time, String label, String off)  {





      a(String string1, String string2, long longValue3, String string5) {
         this(string1, string2, longValue3, string5, "");
      }
   }
}
