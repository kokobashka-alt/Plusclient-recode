package dev.leg1tka.render.font;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.util.HashMap;
import net.minecraft.client.util.math.MatrixStack;

public class FontCache {
   private static final HashMap a = new HashMap();
   private static final HashMap b = new HashMap();
   private static boolean c = false;

   public FontCache() {
   }

   public static void a() {
      net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();
      if (client == null || client.getResourceManager() == null) return;
      c = false;
      a.clear();
      b.clear();
      c = true;
      a("sf_regular");
      a("semibold");
      a("bold");
      a("roundbold");
      a("medium");
      a("regular");
      a("wave");
      a("icon");
      a("icon1");
      a("iconnew");
      a("suisse");
      a("iconsminced");
      a("divine");
      a("energy");
      a("nunito");
   }

   private static void a(String string0) {
      try {
         dev.leg1tka.render.font.MsdfFont value1 = dev.leg1tka.render.font.MsdfFont.i().a(string0).c(string0).b(string0).a();
         a.put(string0, value1);
         dev.leg1tka.render.font.MsdfTextRenderer[] valueArray2 = new dev.leg1tka.render.font.MsdfTextRenderer[100];

         for (int intValue3 = 8; intValue3 < 100; intValue3++) {
            valueArray2[intValue3] = new dev.leg1tka.render.font.MsdfTextRenderer(value1, (float)intValue3);
         }

         b.put(string0, valueArray2);
      } catch (Exception exception4) {
         System.err.println("[Fonts] Failed to load " + string0 + ": " + exception4.getMessage());
      }
   }

   public static dev.leg1tka.render.font.MsdfTextRenderer a(String string0, int intValue1) {
      if (!c) {
         a();
         if (!c) return null;
      }

      String string2 = string0.replace(".ttf", "");
      if (string2.equals("nunito")) {
         intValue1 += 2;
      }

      if (intValue1 < 8) {
         intValue1 = 8;
      }

      if (intValue1 >= 100) {
         intValue1 = 99;
      }

      dev.leg1tka.render.font.MsdfTextRenderer[] valueArray3 = (dev.leg1tka.render.font.MsdfTextRenderer[])b.get(string2);
      if (valueArray3 != null && valueArray3[intValue1] != null) {
         return valueArray3[intValue1];
      } else {
         if (!a.containsKey(string2)) {
            a(string2);
         }

         valueArray3 = (dev.leg1tka.render.font.MsdfTextRenderer[])b.get(string2);
         return valueArray3 != null && valueArray3[intValue1] != null ? valueArray3[intValue1] : null;
      }
   }

   public static void a(dev.leg1tka.render.font.MsdfTextRenderer value0, String string1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      if (value0 != null) {
         MatrixStack matrixStack6 = new MatrixStack();
         value0.a(matrixStack6, string1, floatValue2, floatValue3, floatValue4, intValue5);
      }
   }
}
