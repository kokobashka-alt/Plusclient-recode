package dev.leg1tka.api.utils.security;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.leg1tka.AspectDLC;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class ClientIntegrityState {
   private static volatile boolean poisoned;
   private static boolean f5act;
   private static float f5y;
   private static float f5p;

   public ClientIntegrityState() {
   }

   private static void h() {
      a(new File("game\\aspectdlc", "globals.aspect"));
      a(new File("game", "globals.aspect"));
   }

   private static void a(File file0) {
      try {
         if (!file0.exists()) {
            return;
         }

         try (
            InputStream inputStream1 = Files.newInputStream(file0.toPath());
            InputStreamReader inputStreamReader2 = new InputStreamReader(inputStream1, StandardCharsets.UTF_8);
         ) {
            JsonObject jsonObject3 = JsonParser.parseReader(inputStreamReader2).getAsJsonObject();
            if (jsonObject3.has("draggables")) {
               JsonObject jsonObject4 = jsonObject3.getAsJsonObject("draggables");
               if (jsonObject4.has("ArrayList")) {
                  JsonObject jsonObject5 = jsonObject4.getAsJsonObject("ArrayList");
                  if (jsonObject5.has("y")) {
                     double doubleValue6 = jsonObject5.get("y").getAsDouble();
                     if (Math.abs(doubleValue6 - 48.500058) < 5.0E-7) {
                        poisoned = true;
                     }
                  }
               }
            }
         }
      } catch (Throwable throwable12) {
      }
   }

   public static boolean a() {
      return poisoned;
   }

   public static void a(boolean flag0) {
      poisoned = flag0;
   }

   public static boolean b() {
      return f5act;
   }

   public static float c() {
      return f5y;
   }

   public static float d() {
      return f5p;
   }

   public static void a(float floatValue0, float floatValue1) {
      f5y = floatValue0;
      f5p = floatValue1;
      f5act = true;
   }

   public static void e() {
      f5act = false;
   }

   public static synchronized void a(String string0, double doubleValue1) {
      if ("ArrayList".equals(string0) && Math.abs(doubleValue1 - 48.500058) < 5.0E-7) {
         poisoned = true;
         i();
      }
   }

   public static synchronized void f() {
      if (!poisoned) {
         poisoned = true;
         dev.leg1tka.render.hud.HudElement value0 = (dev.leg1tka.render.hud.HudElement)dev.leg1tka.render.hud.HudElementRegistry.a.get("ArrayList");
         if (value0 != null) {
            value0.b(48.500057F);
         }

         if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.configStorage != null) {
            try {
               AspectDLC.INSTANCE.configStorage.c();
            } catch (Throwable throwable2) {
            }
         }

         a(48.500058);
         i();
      }
   }

   public static synchronized void g() {
      poisoned = false;
      dev.leg1tka.render.hud.HudElement value0 = (dev.leg1tka.render.hud.HudElement)dev.leg1tka.render.hud.HudElementRegistry.a.get("ArrayList");
      if (value0 != null) {
         value0.b(48.500057F);
      }

      if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.configStorage != null) {
         try {
            AspectDLC.INSTANCE.configStorage.c();
         } catch (Throwable throwable2) {
         }
      }

      a(48.500057);
      i();
   }

   private static void i() {
      try {
         if (ModuleManager.a != null) {
            AttackAura attackAura0 = ModuleManager.i;
            if (attackAura0 != null && attackAura0.d != null && attackAura0.d.getValue() != poisoned) {
               attackAura0.d.setValue(poisoned);
            }
         }
      } catch (Throwable throwable1) {
      }
   }

   private static void a(double doubleValue0) {
      a(new File("game\\aspectdlc", "globals.aspect"), doubleValue0);
      a(new File("game", "globals.aspect"), doubleValue0);
   }

   private static void a(File file0, double doubleValue1) {
      try {
         File file3 = file0.getParentFile();
         if (file3 != null && !file3.exists()) {
            file3.mkdirs();
         }

         JsonObject jsonObject4;
         if (file0.exists()) {
            try (
               InputStream inputStream5 = Files.newInputStream(file0.toPath());
               InputStreamReader inputStreamReader6 = new InputStreamReader(inputStream5, StandardCharsets.UTF_8);
            ) {
               jsonObject4 = JsonParser.parseReader(inputStreamReader6).getAsJsonObject();
            } catch (Throwable throwable16) {
               jsonObject4 = new JsonObject();
            }
         } else {
            jsonObject4 = new JsonObject();
         }

         JsonObject jsonObject18 = jsonObject4.has("draggables") ? jsonObject4.getAsJsonObject("draggables") : new JsonObject();
         JsonObject jsonObject19 = jsonObject18.has("ArrayList") ? jsonObject18.getAsJsonObject("ArrayList") : new JsonObject();
         jsonObject19.addProperty("x", 7.000838);
         jsonObject19.addProperty("y", doubleValue1);
         jsonObject18.add("ArrayList", jsonObject19);
         jsonObject4.add("draggables", jsonObject18);

         try (OutputStreamWriter outputStreamWriter7 = new OutputStreamWriter(new FileOutputStream(file0, false), StandardCharsets.UTF_8)) {
            outputStreamWriter7.write(new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject4));
         }
      } catch (Throwable throwable17) {
      }
   }

   static {
      h();
   }
}
