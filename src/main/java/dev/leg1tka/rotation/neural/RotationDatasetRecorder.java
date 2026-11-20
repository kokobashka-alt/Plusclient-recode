package dev.leg1tka.rotation.neural;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RotationDatasetRecorder implements dev.leg1tka.api.ClientAccess {
   public static final List c = List.of("dyaw", "dpitch", "ex", "ey", "nx", "ny", "tdx", "tdy");
   public static final List d = List.of(
      "dist", "box_w", "box_h", "ground", "jump", "fwd", "strafe", "speed_fwd", "speed_side", "cooldown", "since_attack", "on_box", "t_ground"
   );
   public static boolean e;
   private static dev.leg1tka.rotation.neural.RotationDatasetLoader f;
   private static String g = "model";
   private static String h;
   private static long i;
   private static long j;

   private RotationDatasetRecorder() {
   }

   public static File a() {
      File file0 = new File(minecraftClient.runDirectory, "aspectdlc/neuro/models");
      file0.mkdirs();
      return file0;
   }

   public static File b() {
      return new File(a(), g + ".json");
   }

   public static dev.leg1tka.rotation.neural.RotationDatasetLoader c() {
      long longValue0 = System.currentTimeMillis();
      if (longValue0 - i > 1000L) {
         i = longValue0;
         File file2 = b();
         if (file2.exists() && file2.lastModified() != j) {
            String string3 = d();
            if (minecraftClient.player != null && f != null) {
               dev.leg1tka.api.utils.text.ClientChat.a("[Neuro] " + string3);
            }
         }
      }

      return f;
   }

   public static String d() {
      File file0 = b();
      if (!file0.exists()) {
         f = null;
         h = "нет файла " + file0.getName();
         return "§cНет модели: §f" + file0.getAbsolutePath();
      } else {
         try {
            dev.leg1tka.rotation.neural.RotationDatasetLoader value1 = dev.leg1tka.rotation.neural.RotationDatasetLoader.a(file0);
            if (value1.h.equals(c) && value1.i.equals(d)) {
               f = value1;
               j = file0.lastModified();
               h = null;
               return "§aМодель загружена: §f"
                  + file0.getName()
                  + (value1.j >= 0 ? " §7(эпоха " + value1.j + ")" : "");
            } else {
               throw new IllegalStateException(
                  "признаки модели не совпадают с клиентом (переобучи текущим train.py)"
               );
            }
         } catch (Exception exception2) {
            j = file0.lastModified();
            h = exception2.getMessage();
            return "§cОшибка загрузки "
               + file0.getName()
               + ": §f"
               + exception2.getMessage();
         }
      }
   }

   public static String a(String string0) {
      g = string0.endsWith(".json") ? string0.substring(0, string0.length() - 5) : string0;
      f = null;
      h = null;
      return d();
   }

   public static List<String> e() {
      File[] fileArray0 = a().listFiles((file0x, string1x) -> string1x.toLowerCase().endsWith(".json"));
      ArrayList arrayList1 = new ArrayList();
      if (fileArray0 != null) {
         Arrays.sort((Object[])fileArray0);

         for (File file5 : fileArray0) {
            arrayList1.add(file5.getName().substring(0, file5.getName().length() - 5));
         }
      }

      return arrayList1;
   }

   public static String f() {
      return g;
   }

   public static String g() {
      return h;
   }

   public static dev.leg1tka.rotation.neural.RotationDatasetLoader h() {
      return f;
   }
}
