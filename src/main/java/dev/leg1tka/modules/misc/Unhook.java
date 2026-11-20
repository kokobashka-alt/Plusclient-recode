package dev.leg1tka.modules.misc;


import dev.leg1tka.modules.Module;
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
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.lwjgl.glfw.GLFW;

public class Unhook extends Module {
   public static Unhook c = new Unhook();
   private final dev.leg1tka.settings.KeybindSetting d = new dev.leg1tka.settings.KeybindSetting("Unhook Key", -1);
   private final BooleanSetting e = new BooleanSetting("Allow Baritone Commands", true);
   private final BooleanSetting f = new BooleanSetting("Очистить GPS", true);
   private final BooleanSetting g = new BooleanSetting("Очистить логи", true);
   private final BooleanSetting h = new BooleanSetting("Удалить Baritone папку", true);
   private final BooleanSetting i = new BooleanSetting("Очистить Recent файлы", true);
   private final BooleanSetting j = new BooleanSetting("Блокировать Baritone папку", true);
   private final BooleanSetting k = new BooleanSetting(
      "Глубокая очистка следов", true
   );
   private static boolean l = false;
   private static final Map<Module, Boolean> m = new HashMap<>();
   private static boolean n = false;
   private static long o = 0L;
   private static long p;

   public Unhook() {
      super(
         "Unhook",
         "Скрывает все функции читов по нажатию кнопки",
         Category.MISC
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (this.j.getValue()) {
         dev.leg1tka.api.utils.logging.ClientLogFilter.b(minecraftClient.runDirectory);
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      dev.leg1tka.api.utils.logging.ClientLogFilter.c(minecraftClient.runDirectory);
      if (l) {
         u();
      }
   }

   public static boolean p() {
      return c != null && c.e.getValue();
   }

   public static void q() {
      if (c != null && c.isEnabled()) {
         int intValue0 = c.d.getKey();
         if (intValue0 != -1) {
            long longValue1 = System.currentTimeMillis();
            if (longValue1 - o >= 200L) {
               if (b(intValue0)) {
                  o = longValue1;
                  s();
               }
            }
         }
      }
   }

   private static void s() {
      if (l) {
         u();
      } else {
         t();
      }
   }

   private static void t() {
      if (!l) {
         l = true;
         m.clear();
         if (c.f.getValue()) {
            x();
         }

         if (c.g.getValue()) {
            w();
         }

         if (c.h.getValue()) {
            y();
         }

         if (c.i.getValue()) {
            z();
         }

         if (c.k.getValue()) {
            dev.leg1tka.api.utils.logging.ClientLogFilter.f();
         }

         ObjectListIterator objectListIterator0 = ModuleManager.a.a().iterator();

         while (objectListIterator0.hasNext()) {
            Module module1 = (Module)objectListIterator0.next();
            if (module1 != c) {
               m.put(module1, module1.isEnabled());
               if (module1.isEnabled()) {
                  module1.setEnabled(false);
               }
            }
         }

         if (minecraftClient != null && minecraftClient.currentScreen instanceof ClickGuiScreen) {
            n = true;
            minecraftClient.setScreen(null);
         }

         v();
      }
   }

   private static void u() {
      if (l) {
         l = false;

         for (Map.Entry<Module, Boolean> entry1 : m.entrySet()) {
            Module module2 = (Module)entry1.getKey();
            boolean flag3 = (Boolean)entry1.getValue();
            if (flag3 && !module2.isEnabled()) {
               module2.setEnabled(true);
            }
         }

         m.clear();
      }
   }

   public static boolean r() {
      return l;
   }

   private static void v() {
      try {
         if (minecraftClient != null && minecraftClient.inGameHud != null && minecraftClient.inGameHud.getChatHud() != null) {
            minecraftClient.inGameHud.getChatHud().clear(false);
         }
      } catch (Exception exception1) {
      }
   }

   private static void w() {
      try {
         File file0 = new File(minecraftClient.runDirectory, "logs");
         if (file0.exists() && file0.isDirectory()) {
            dev.leg1tka.api.utils.logging.ClientLogFilter.a(file0.toPath());
            file0.mkdirs();
         }

         File file1 = new File(System.getProperty("java.io.tmpdir"));
         if (file1.exists()) {
            File[] fileArray2 = file1.listFiles(
               (file0x, string1x) -> string1x.toLowerCase().contains("minecraft") || string1x.toLowerCase().contains("fabric") || string1x.toLowerCase().contains("java")
            );
            if (fileArray2 != null) {
               for (File file6 : fileArray2) {
                  try {
                     if (file6.isFile()) {
                        file6.delete();
                     }
                  } catch (Exception exception8) {
                  }
               }
            }
         }

         System.gc();
      } catch (Exception exception9) {
      }
   }

   private static void x() {
      try {
         if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.commandStorage != null) {
            String string0 = AspectDLC.INSTANCE.commandStorage.d();
            minecraftClient.player.networkHandler.sendChatMessage(string0 + "gps remove");
         }

         File file3 = new File(minecraftClient.runDirectory, "waypoints.json");
         if (file3.exists()) {
            file3.delete();
         }

         File file1 = new File(minecraftClient.runDirectory, "gps.dat");
         if (file1.exists()) {
            file1.delete();
         }
      } catch (Exception exception2) {
      }
   }

   private static void y() {
      try {
         File file0 = new File(minecraftClient.runDirectory, "baritone");
         if (file0.exists() && file0.isDirectory()) {
            dev.leg1tka.api.utils.logging.ClientLogFilter.a(file0.toPath());
         }
      } catch (Exception exception1) {
      }
   }

   private static void z() {
      try {
         String string0 = System.getProperty("os.name").toLowerCase();
         if (string0.contains("win")) {
            String string1 = System.getProperty("user.home");
            Path path2 = Paths.get(string1, "AppData", "Roaming", "Microsoft", "Windows", "Recent");
            if (Files.exists(path2)) {
               Files.walk(path2).filter(path0x -> Files.isRegularFile(path0x)).forEach(path0x -> {
                  try {
                     Files.delete(path0x);
                  } catch (IOException iOException2x) {
                  }
               });
            }

            try {
               Path path3 = Paths.get("C:\\Windows\\Prefetch");
               if (Files.exists(path3)) {
                  Files.walk(path3)
                     .filter(path0x -> Files.isRegularFile(path0x))
                     .filter(path0x -> path0x.toString().toLowerCase().contains("java") || path0x.toString().toLowerCase().contains("minecraft"))
                     .forEach(path0x -> {
                        try {
                           Files.delete(path0x);
                        } catch (IOException iOException2x) {
                        }
                     });
               }
            } catch (Exception exception4) {
            }
         }
      } catch (Exception exception5) {
      }
   }

   private static void a(File file0) {
      dev.leg1tka.api.utils.logging.ClientLogFilter.a(file0.toPath());
   }

   private static boolean b(int intValue0) {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         try {
            if (intValue0 >= 1000) {
               int intValue1 = intValue0 - 1000;
               return GLFW.glfwGetMouseButton(minecraftClient.getWindow().getHandle(), intValue1) == 1;
            } else {
               return GLFW.glfwGetKey(minecraftClient.getWindow().getHandle(), intValue0) == 1;
            }
         } catch (Exception exception2) {
            return false;
         }
      } else {
         return false;
      }
   }
}
