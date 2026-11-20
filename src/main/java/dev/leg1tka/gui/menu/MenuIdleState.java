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

public final class MenuIdleState {
   public static final dev.leg1tka.gui.menu.MenuParticleRenderer a = new dev.leg1tka.gui.menu.MenuParticleRenderer();
   public static long b;
   public static long c;
   private static long e = 0L;
   private static boolean f = false;
   private static float g = Float.NaN;
   private static float h = Float.NaN;
   private static float i = Float.NaN;
   public static float d;

   private MenuIdleState() {
   }

   public static void a() {
      if (e == 0L) {
         e = System.currentTimeMillis();
         f = true;
      }
   }

   public static void b() {
      e = System.currentTimeMillis();
      f = false;
   }

   public static boolean c() {
      return e != 0L && System.currentTimeMillis() - e < 6000L;
   }

   public static boolean d() {
      return f && c();
   }

   public static boolean e() {
      return c() && f() < 3000L;
   }

   public static long f() {
      return e == 0L ? Long.MAX_VALUE : System.currentTimeMillis() - e;
   }

   public static float a(long longValue0, long longValue2) {
      if (!c()) {
         return 1.0F;
      } else {
         float floatValue4 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a((float)(f() - longValue0) / (float)longValue2);
         return floatValue4 < 0.5F ? 4.0F * floatValue4 * floatValue4 * floatValue4 : 1.0F - (float)Math.pow((double)(-2.0F * floatValue4 + 2.0F), 3.0) / 2.0F;
      }
   }

   public static float g() {
      if (!d()) {
         return 1.0F;
      } else {
         float floatValue0 = dev.leg1tka.gui.menu.MenuBackdropRenderer.b((float)f() / 2800.0F);
         return 1.0F + 0.07000005F * (1.0F - floatValue0);
      }
   }

   public static float h() {
      if (!c()) {
         return 0.0F;
      } else {
         long longValue0 = f();
         if (longValue0 < 900L) {
            return 1.0F;
         } else if (longValue0 < 2000L) {
            return b(1.0F, 0.55F, a(longValue0, 900L, 2000L));
         } else if (longValue0 < 2400L) {
            return b(0.55F, 0.9F, a(longValue0, 2000L, 2400L));
         } else if (longValue0 < 3500L) {
            return b(0.9F, 0.12F, a(longValue0, 2400L, 3500L));
         } else if (longValue0 < 3750L) {
            return b(0.12F, 0.8F, a(longValue0, 3500L, 3750L));
         } else {
            return longValue0 < 4100L ? b(0.8F, 0.0F, a(longValue0, 3750L, 4100L)) : 0.0F;
         }
      }
   }

   private static float a(long longValue0, long longValue2, long longValue4) {
      float floatValue6 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a((float)(longValue0 - longValue2) / (float)(longValue4 - longValue2));
      return floatValue6 * floatValue6 * (3.0F - 2.0F * floatValue6);
   }

   private static float b(float floatValue0, float floatValue1, float floatValue2) {
      return floatValue0 + (floatValue1 - floatValue0) * floatValue2;
   }

   public static void a(float floatValue0, float floatValue1, float floatValue2) {
      g = floatValue0;
      h = floatValue1;
      i = floatValue2;
   }

   public static boolean i() {
      return !Float.isNaN(g);
   }

   public static float j() {
      return g;
   }

   public static float k() {
      return h;
   }

   public static float l() {
      return i;
   }
}
