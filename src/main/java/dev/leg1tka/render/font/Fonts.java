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

import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import net.minecraft.util.Identifier;

public class Fonts {
   private static final String m = dev.leg1tka.api.utils.resources.NamespaceResolver.a();
   private static final Map n = new HashMap();
   private static final Map o = new HashMap();
   public static dev.leg1tka.render.font.AtlasFontRenderer a;
   public static dev.leg1tka.render.font.AtlasFontRenderer b;
   public static dev.leg1tka.render.font.AtlasFontRenderer c;
   public static dev.leg1tka.render.font.OutlineFontRenderer d;
   public static dev.leg1tka.render.font.AtlasFontRenderer e;
   public static dev.leg1tka.render.font.AtlasFontRenderer f;
   public static dev.leg1tka.render.font.AtlasFontRenderer g;
   public static dev.leg1tka.render.font.OutlineFontRenderer h;
   public static dev.leg1tka.render.font.AtlasFontRenderer i;
   public static dev.leg1tka.render.font.AtlasFontRenderer j;
   public static dev.leg1tka.render.font.AtlasFontRenderer k;
   public static dev.leg1tka.render.font.OutlineFontRenderer l;
   private static boolean p = false;

   public Fonts() {
   }

   public static void a() {
      if (!p) {
         a = a("comfortaa.ttf", 16.0F);
         b = a("comfortaa.ttf", 18.0F);
         c = a("comfortaa.ttf", 20.0F);
         d = b("comfortaa.ttf", 18.0F);
         e = a("roboto.ttf", 16.0F);
         f = a("roboto.ttf", 18.0F);
         g = a("roboto.ttf", 20.0F);
         h = b("roboto.ttf", 18.0F);
         i = a("montserrat.ttf", 16.0F);
         j = a("montserrat.ttf", 18.0F);
         k = a("montserrat.ttf", 20.0F);
         l = b("montserrat.ttf", 18.0F);
         p = true;
      }
   }

   public static dev.leg1tka.render.font.AtlasFontRenderer a(String string0, float floatValue1) {
      n.computeIfAbsent(string0, object0x -> new HashMap());
      Map map2 = (Map)n.get(string0);
      if (map2.containsKey(floatValue1)) {
         return (dev.leg1tka.render.font.AtlasFontRenderer)map2.get(floatValue1);
      } else {
         Font font3 = dev.leg1tka.render.font.FontResources.a(Identifier.of(m, "fonts/ttf/" + string0), floatValue1, 0);
         if (font3 == null) {
            font3 = new Font("Arial", 0, (int)floatValue1);
         }

         dev.leg1tka.render.font.AtlasFontRenderer value4 = new dev.leg1tka.render.font.AtlasFontRenderer(font3, true, true);
         map2.put(floatValue1, value4);
         return value4;
      }
   }

   public static dev.leg1tka.render.font.OutlineFontRenderer b(String string0, float floatValue1) {
      o.computeIfAbsent(string0, object0x -> new HashMap());
      Map map2 = (Map)o.get(string0);
      if (map2.containsKey(floatValue1)) {
         return (dev.leg1tka.render.font.OutlineFontRenderer)map2.get(floatValue1);
      } else {
         Font font3 = dev.leg1tka.render.font.FontResources.a(Identifier.of(m, "fonts/" + string0), floatValue1, 0);
         if (font3 == null) {
            font3 = new Font("Arial", 0, (int)floatValue1);
         }

         dev.leg1tka.render.font.OutlineFontRenderer value4 = new dev.leg1tka.render.font.OutlineFontRenderer(font3, true, true);
         map2.put(floatValue1, value4);
         return value4;
      }
   }

   public static void a(dev.leg1tka.render.font.AtlasFontRenderer value0, String string1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      if (string1 != null && !string1.isEmpty() && !(floatValue4 <= 0.0F)) {
         float floatValue6 = floatValue2;
         float floatValue7 = Math.min(22.0F, Math.max(8.0F, floatValue4 * 0.35F));
         float floatValue8 = floatValue2 + floatValue4 - floatValue7;
         int intValue9 = intValue5 >> 24 & 0xFF;

         for (int intValue10 = 0; intValue10 < string1.length(); intValue10++) {
            String string11 = String.valueOf(string1.charAt(intValue10));
            float floatValue12 = (float)value0.b(string11);
            if (floatValue6 > floatValue2 + floatValue4 && intValue10 > 0) {
               break;
            }

            int intValue13 = intValue5;
            if (floatValue6 > floatValue8) {
               float floatValue14 = (floatValue6 - floatValue8) / floatValue7;
               floatValue14 = Math.max(0.0F, Math.min(1.0F, floatValue14));
               float floatValue15 = (float)Math.cos((double)floatValue14 * Math.PI / 2.0);
               int intValue16 = (int)((float)intValue9 * floatValue15);
               intValue13 = intValue5 & 16777215 | intValue16 << 24;
            }

            if ((intValue13 >> 24 & 0xFF) > 4) {
               value0.a(string11, floatValue6, floatValue3, intValue13);
            }

            floatValue6 += floatValue12;
         }
      }
   }

   public static dev.leg1tka.render.font.AtlasFontRenderer c(String string0, float floatValue1) {
      String string2 = "system_" + string0;
      n.computeIfAbsent(string2, object0x -> new HashMap());
      Map map3 = (Map)n.get(string2);
      if (map3.containsKey(floatValue1)) {
         return (dev.leg1tka.render.font.AtlasFontRenderer)map3.get(floatValue1);
      } else {
         Font font4 = new Font(string0, 0, (int)floatValue1);
         dev.leg1tka.render.font.AtlasFontRenderer value5 = new dev.leg1tka.render.font.AtlasFontRenderer(font4, true, true);
         map3.put(floatValue1, value5);
         return value5;
      }
   }

   public static dev.leg1tka.render.font.AtlasFontRenderer a(String string0, float floatValue1, int intValue2) {
      String string3 = "system_" + string0 + "_" + intValue2;
      n.computeIfAbsent(string3, object0x -> new HashMap());
      Map map4 = (Map)n.get(string3);
      if (map4.containsKey(floatValue1)) {
         return (dev.leg1tka.render.font.AtlasFontRenderer)map4.get(floatValue1);
      } else {
         Font font5 = new Font(string0, intValue2, (int)floatValue1);
         dev.leg1tka.render.font.AtlasFontRenderer value6 = new dev.leg1tka.render.font.AtlasFontRenderer(font5, true, true);
         map4.put(floatValue1, value6);
         return value6;
      }
   }

   public static void b() {
      n.clear();
      o.clear();
      p = false;
   }

   public static void a(String string0) {
      n.remove(string0);
      o.remove(string0);
   }

   @Generated
   public static boolean c() {
      return p;
   }
}
