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

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;

public final class MenuNavigation {
   private static long a = 0L;
   private static Class b = null;
   private static boolean c = true;
   private static float d = 0.0F;
   private static long e = 300L;

   private MenuNavigation() {
   }

   public static void a(Screen screen0) {
      if (screen0 == null) {
         b = null;
         a = 0L;
      } else {
         Class class1 = screen0.getClass();
         if (b != class1) {
            boolean flag2 = screen0 instanceof TitleScreen;
            boolean flag3 = b == null || flag2 != c;
            b = class1;
            c = flag2;
            a = -1L;
            d = flag3 ? 0.0F : 0.55F;
            e = flag3 ? 480L : 260L;
         }
      }
   }

   public static float a() {
      if (a == 0L) {
         return 1.0F;
      } else {
         if (a == -1L) {
            a = System.currentTimeMillis();
         }

         long longValue0 = System.currentTimeMillis() - a;
         if (longValue0 >= e) {
            return 1.0F;
         } else {
            float floatValue2 = (float)longValue0 / (float)e;
            float floatValue3 = floatValue2 < 0.5F ? 4.0F * floatValue2 * floatValue2 * floatValue2 : 1.0F - (float)Math.pow((double)(-2.0F * floatValue2 + 2.0F), 3.0) / 2.0F;
            return d + (1.0F - d) * floatValue3;
         }
      }
   }

   public static float b() {
      return a();
   }

   public static float c() {
      return c ? 0.0F : -45.0F;
   }
}
