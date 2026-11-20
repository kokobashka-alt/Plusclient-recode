package dev.leg1tka.gui.components;

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

public final class MenuTextRenderer {
   public static String a;
   public static String b;
   private static final HashMap c = new HashMap();

   private MenuTextRenderer() {
   }

   private static dev.leg1tka.gui.components.MenuTextRenderer.a a(String string0, float floatValue1) {
      float floatValue2 = dev.leg1tka.gui.menu.MenuLayout.a(floatValue1);
      int intValue3 = string0.equals("nunito") ? 2 : 0;
      int intValue4 = Math.round(floatValue2 * 2.0F);
      int intValue5 = Math.max(8, Math.min(99 - intValue3, intValue4 - intValue3));
      dev.leg1tka.render.font.MsdfTextRenderer value6 = dev.leg1tka.render.font.FontCache.a(string0, intValue5);
      if (value6 == null) {
         return null;
      } else {
         float floatValue7 = value6.d() * 0.5F;
         return new dev.leg1tka.gui.components.MenuTextRenderer.a(value6, floatValue2 / Math.max(0.01F, floatValue7));
      }
   }

   private static float a(dev.leg1tka.render.font.MsdfFont value0, int intValue1) {
      Float float2 = (Float)c.get(value0);
      if (float2 != null && intValue1 == 72) {
         return float2;
      } else {
         dev.leg1tka.render.font.GlyphVertexWriter value3 = value0.a(intValue1);
         float floatValue4 = value3 != null ? (value3.c() + value3.d()) * 0.5F : 0.36F;
         float floatValue5 = value0.g() - floatValue4;
         if (intValue1 == 72) {
            c.put(value0, floatValue5);
         }

         return floatValue5;
      }
   }

   public static float a(float floatValue0, String string1) {
      return a(floatValue0, string1, 0.0F);
   }

   public static float a(float floatValue0, String string1, float floatValue2) {
      return a("nunito", floatValue0, string1, floatValue2);
   }

   public static float a(String string0, float floatValue1, String string2, float floatValue3) {
      dev.leg1tka.gui.components.MenuTextRenderer.a value4 = a(string0, floatValue1);
      if (value4 != null && string2 != null) {
         float floatValue5 = value4.fnt.a(string2) * value4.sc;
         if (floatValue3 != 0.0F && string2.length() > 1) {
            floatValue5 += dev.leg1tka.gui.menu.MenuLayout.a(floatValue3) * (float)(string2.length() - 1);
         }

         return floatValue5;
      } else {
         return 0.0F;
      }
   }

   public static void a(MatrixStack matrixStack0, String string1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      a(matrixStack0, string1, floatValue2, floatValue3, floatValue4, 0.0F, intValue5, intValue5);
   }

   public static void b(MatrixStack matrixStack0, String string1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      a(matrixStack0, string1, floatValue2, floatValue3 - a(floatValue2, string1) * 0.5F, floatValue4, 0.0F, intValue5, intValue5);
   }

   public static void c(MatrixStack matrixStack0, String string1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      a(matrixStack0, string1, floatValue2, floatValue3 - a(floatValue2, string1), floatValue4, 0.0F, intValue5, intValue5);
   }

   public static void d(MatrixStack matrixStack0, String string1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      float floatValue6 = (float)(intValue5 >>> 24 & 0xFF) / 255.0F;
      float floatValue7 = dev.leg1tka.gui.menu.MenuLayout.a(0.8F + floatValue2 * 0.03F);
      b(matrixStack0, string1, floatValue2, floatValue3, floatValue4 + floatValue7 * 2.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.12F * floatValue6, 0));
      b(matrixStack0, string1, floatValue2, floatValue3, floatValue4 + floatValue7, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.3F * floatValue6, 0));
      b(matrixStack0, string1, floatValue2, floatValue3, floatValue4, intValue5);
   }

   public static void e(MatrixStack matrixStack0, String string1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      float floatValue6 = (float)(intValue5 >>> 24 & 0xFF) / 255.0F;
      float floatValue7 = dev.leg1tka.gui.menu.MenuLayout.a(0.8F + floatValue2 * 0.03F);
      a(matrixStack0, string1, floatValue2, floatValue3, floatValue4 + floatValue7 * 2.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.12F * floatValue6, 0));
      a(matrixStack0, string1, floatValue2, floatValue3, floatValue4 + floatValue7, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.3F * floatValue6, 0));
      a(matrixStack0, string1, floatValue2, floatValue3, floatValue4, intValue5);
   }

   public static void a(MatrixStack matrixStack0, String string1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      a(matrixStack0, string1, floatValue2, floatValue3, floatValue4, floatValue5, intValue6, intValue6);
   }

   public static void a(MatrixStack matrixStack0, String string1, float floatValue2, float floatValue3, float floatValue4, int intValue5, int intValue6) {
      a(matrixStack0, string1, floatValue2, floatValue3, floatValue4, 0.0F, intValue5, intValue6);
   }

   public static void a(MatrixStack matrixStack0, String string1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, int intValue7) {
      a(matrixStack0, "nunito", string1, floatValue2, floatValue3, floatValue4, floatValue5, intValue6, intValue7);
   }

   public static void a(MatrixStack matrixStack0, String string1, String string2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8) {
      if (string2 != null && !string2.isEmpty()) {
         dev.leg1tka.gui.components.MenuTextRenderer.a value9 = a(string1, floatValue3);
         if (value9 != null) {
            dev.leg1tka.render.font.MsdfTextRenderer value10 = value9.fnt;
            float floatValue11 = value10.d() * 0.5F;
            float floatValue12 = 2.5F - a(value10.c(), 72) * floatValue11;
            float floatValue13 = floatValue6 != 0.0F ? dev.leg1tka.gui.menu.MenuLayout.a(floatValue6) / value9.sc : 0.0F;
            matrixStack0.push();
            matrixStack0.translate(dev.leg1tka.gui.menu.MenuLayout.b(floatValue4), dev.leg1tka.gui.menu.MenuLayout.b(floatValue5), 0.0F);
            matrixStack0.scale(value9.sc, value9.sc, 1.0F);
            if (intValue7 == intValue8 && floatValue13 == 0.0F) {
               value10.e(matrixStack0, string2, 0.0F, floatValue12, intValue7);
            } else {
               float floatValue14 = value10.a(string2) + floatValue13 * (float)(string2.length() - 1);
               float floatValue15 = 0.0F;

               for (int intValue16 = 0; intValue16 < string2.length(); intValue16++) {
                  String string17 = String.valueOf(string2.charAt(intValue16));
                  int intValue18 = intValue7 == intValue8 ? intValue7 : dev.leg1tka.render.font.MsdfTextRenderer.a(intValue7, intValue8, floatValue14 > 0.0F ? floatValue15 / floatValue14 : 0.0F);
                  value10.e(matrixStack0, string17, floatValue15, floatValue12, intValue18);
                  floatValue15 += value10.a(string17) + floatValue13;
               }
            }

            matrixStack0.pop();
         }
      }
   }

   public static void f(MatrixStack matrixStack0, String string1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      a(matrixStack0, "icon", string1, floatValue2, floatValue3, floatValue4, intValue5);
   }

   public static void a(MatrixStack matrixStack0, String string1, String string2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      dev.leg1tka.render.font.MsdfTextRenderer value7 = dev.leg1tka.render.font.FontCache.a(string1, 64);
      if (value7 != null && string2 != null && !string2.isEmpty()) {
         dev.leg1tka.render.font.GlyphVertexWriter value8 = value7.c().a(string2.charAt(0));
         float floatValue9 = value7.d() * 0.5F;
         float floatValue10 = value8 != null ? value8.e() : 1.0F;
         float floatValue11 = floatValue3 / Math.max(0.01F, floatValue9);
         float floatValue12 = 2.5F - a(value7.c(), string2.charAt(0)) * floatValue9;
         matrixStack0.push();
         matrixStack0.translate(floatValue4, floatValue5, 0.0F);
         matrixStack0.scale(floatValue11, floatValue11, 1.0F);
         value7.a(matrixStack0, string2, -floatValue10 * floatValue9 * 0.5F, floatValue12, intValue6);
         matrixStack0.pop();
      }
   }

   static record a(dev.leg1tka.render.font.MsdfTextRenderer fnt, float sc)  {

}
}
