package dev.leg1tka.api.animation;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import lombok.Generated;

public final class Easings {
   public static double a;
   public static double b;
   public static double c;
   public static double d;
   public static double e;
   public static final dev.leg1tka.api.animation.EasingFunction f = doubleValue0 -> doubleValue0;
   public static final dev.leg1tka.api.animation.EasingFunction g = a(2);
   public static final dev.leg1tka.api.animation.EasingFunction h = b(2);
   public static final dev.leg1tka.api.animation.EasingFunction i = c(2.0);
   public static final dev.leg1tka.api.animation.EasingFunction j = a(3);
   public static final dev.leg1tka.api.animation.EasingFunction k = b(3);
   public static final dev.leg1tka.api.animation.EasingFunction l = c(3.0);
   public static final dev.leg1tka.api.animation.EasingFunction m = a(4);
   public static final dev.leg1tka.api.animation.EasingFunction n = b(4);
   public static final dev.leg1tka.api.animation.EasingFunction o = c(4.0);
   public static final dev.leg1tka.api.animation.EasingFunction p = a(5);
   public static final dev.leg1tka.api.animation.EasingFunction q = b(5);
   public static final dev.leg1tka.api.animation.EasingFunction r = c(5.0);
   public static final dev.leg1tka.api.animation.EasingFunction s = doubleValue0 -> 1.0 - Math.cos(doubleValue0 * Math.PI / 2.0);
   public static final dev.leg1tka.api.animation.EasingFunction t = doubleValue0 -> Math.sin(doubleValue0 * Math.PI / 2.0);
   public static final dev.leg1tka.api.animation.EasingFunction u = doubleValue0 -> -(Math.cos(Math.PI * doubleValue0) - 1.0) / 2.0;
   public static final dev.leg1tka.api.animation.EasingFunction v = doubleValue0 -> 1.0 - Math.sqrt(1.0 - Math.pow(doubleValue0, 2.0));
   public static final dev.leg1tka.api.animation.EasingFunction w = doubleValue0 -> Math.sqrt(1.0 - Math.pow(doubleValue0 - 1.0, 2.0));
   public static final dev.leg1tka.api.animation.EasingFunction x = doubleValue0 -> doubleValue0 < 0.5
         ? (1.0 - Math.sqrt(1.0 - Math.pow(2.0 * doubleValue0, 2.0))) / 2.0
         : (Math.sqrt(1.0 - Math.pow(-2.0 * doubleValue0 + 2.0, 2.0)) + 1.0) / 2.0;
   public static final dev.leg1tka.api.animation.EasingFunction y = doubleValue0 -> doubleValue0 != 0.0 && doubleValue0 != 1.0
         ? Math.pow(-2.0, 10.0 * doubleValue0 - 10.0) * Math.sin((doubleValue0 * 10.0 - 10.75) * (Math.PI * 2.0 / 3.0))
         : doubleValue0;
   public static final dev.leg1tka.api.animation.EasingFunction z = doubleValue0 -> doubleValue0 != 0.0 && doubleValue0 != 1.0
         ? Math.pow(2.0, -10.0 * doubleValue0) * Math.sin((doubleValue0 * 10.0 - 0.75) * (Math.PI * 2.0 / 3.0)) + 1.0
         : doubleValue0;
   public static final dev.leg1tka.api.animation.EasingFunction A = doubleValue0 -> {
      if (doubleValue0 != 0.0 && doubleValue0 != 1.0) {
         return doubleValue0 < 0.5
            ? -(Math.pow(2.0, 20.0 * doubleValue0 - 10.0) * Math.sin((20.0 * doubleValue0 - 11.125) * (Math.PI * 4.0 / 9.0))) / 2.0
            : Math.pow(2.0, -20.0 * doubleValue0 + 10.0) * Math.sin((20.0 * doubleValue0 - 11.125) * (Math.PI * 4.0 / 9.0)) / 2.0 + 1.0;
      } else {
         return doubleValue0;
      }
   };
   public static final dev.leg1tka.api.animation.EasingFunction B = doubleValue0 -> doubleValue0 != 0.0 ? Math.pow(2.0, 10.0 * doubleValue0 - 10.0) : doubleValue0;
   public static final dev.leg1tka.api.animation.EasingFunction C = doubleValue0 -> doubleValue0 != 1.0 ? 1.0 - Math.pow(2.0, -10.0 * doubleValue0) : doubleValue0;
   public static final dev.leg1tka.api.animation.EasingFunction D = doubleValue0 -> {
      if (doubleValue0 != 0.0 && doubleValue0 != 1.0) {
         return doubleValue0 < 0.5 ? Math.pow(2.0, 20.0 * doubleValue0 - 10.0) / 2.0 : (2.0 - Math.pow(2.0, -20.0 * doubleValue0 + 10.0)) / 2.0;
      } else {
         return doubleValue0;
      }
   };
   public static final dev.leg1tka.api.animation.EasingFunction E = doubleValue0 -> 2.70158 * Math.pow(doubleValue0, 3.0) - 1.70158 * Math.pow(doubleValue0, 2.0);
   public static final dev.leg1tka.api.animation.EasingFunction F = doubleValue0 -> 1.0 + 2.70158 * Math.pow(doubleValue0 - 1.0, 3.0) + 1.70158 * Math.pow(doubleValue0 - 1.0, 2.0);
   public static final dev.leg1tka.api.animation.EasingFunction G = doubleValue0 -> doubleValue0 < 0.5
         ? Math.pow(2.0 * doubleValue0, 2.0) * (7.189819 * doubleValue0 - 2.5949095) / 2.0
         : (Math.pow(2.0 * doubleValue0 - 2.0, 2.0) * (3.5949095 * (doubleValue0 * 2.0 - 2.0) + 2.5949095) + 2.0) / 2.0;
   public static final dev.leg1tka.api.animation.EasingFunction H = doubleValue0 -> {
      double doubleValue2 = 7.5625;
      double doubleValue4 = 2.75;
      if (doubleValue0 < 1.0 / doubleValue4) {
         return doubleValue2 * Math.pow(doubleValue0, 2.0);
      } else if (doubleValue0 < 2.0 / doubleValue4) {
         return doubleValue2 * Math.pow(doubleValue0 - 1.5 / doubleValue4, 2.0) + 0.75;
      } else {
         return doubleValue0 < 2.5 / doubleValue4 ? doubleValue2 * Math.pow(doubleValue0 - 2.25 / doubleValue4, 2.0) + 0.9375 : doubleValue2 * Math.pow(doubleValue0 - 2.625 / doubleValue4, 2.0) + 0.984375;
      }
   };
   public static final dev.leg1tka.api.animation.EasingFunction I = doubleValue0 -> 1.0 - H.ease(1.0 - doubleValue0);
   public static final dev.leg1tka.api.animation.EasingFunction J = doubleValue0 -> doubleValue0 < 0.5 ? (1.0 - H.ease(1.0 - 2.0 * doubleValue0)) / 2.0 : (1.0 + H.ease(2.0 * doubleValue0 - 1.0)) / 2.0;

   public static dev.leg1tka.api.animation.EasingFunction a(double doubleValue0) {
      return doubleValue2 -> Math.pow(doubleValue2, doubleValue0);
   }

   public static dev.leg1tka.api.animation.EasingFunction a(int intValue0) {
      return a((double)intValue0);
   }

   public static dev.leg1tka.api.animation.EasingFunction b(double doubleValue0) {
      return doubleValue2 -> 1.0 - Math.pow(1.0 - doubleValue2, doubleValue0);
   }

   public static dev.leg1tka.api.animation.EasingFunction b(int intValue0) {
      return b((double)intValue0);
   }

   public static dev.leg1tka.api.animation.EasingFunction c(double doubleValue0) {
      return doubleValue2 -> doubleValue2 < 0.5 ? Math.pow(2.0, doubleValue0 - 1.0) * Math.pow(doubleValue2, doubleValue0) : 1.0 - Math.pow(-2.0 * doubleValue2 + 2.0, doubleValue0) / 2.0;
   }

   @Generated
   private Easings() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
