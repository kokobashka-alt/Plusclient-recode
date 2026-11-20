package dev.leg1tka.gui.menu;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import net.fabricmc.loader.api.FabricLoader;

public final class MenuPreferences {
   private static final File a = new File(FabricLoader.getInstance().getConfigDir().toFile(), "aspect_menu.txt");
   private static boolean b = true;
   private static boolean c = false;

   private MenuPreferences() {
   }

   public static boolean a() {
      b();
      return b;
   }

   public static void a(boolean flag0) {
      b();
      b = flag0;
      c();
   }

   private static void b() {
      if (!c) {
         c = true;

         try {
            if (!a.exists()) {
               return;
            }

            for (String string1 : Files.readAllLines(a.toPath(), StandardCharsets.UTF_8)) {
               String[] stringArray2 = string1.split("=", 2);
               if (stringArray2.length == 2 && stringArray2[0].trim().equals("nunito_buttons")) {
                  b = Boolean.parseBoolean(stringArray2[1].trim());
               }
            }
         } catch (Exception exception3) {
         }
      }
   }

   private static void c() {
      try {
         Files.writeString(a.toPath(), "nunito_buttons=" + b + "\n", StandardCharsets.UTF_8);
      } catch (Exception exception1) {
      }
   }
}
