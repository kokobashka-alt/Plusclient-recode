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

public abstract class Interpolators {
   private static double f;
   public static final dev.leg1tka.api.animation.Interpolators a = new dev.leg1tka.api.animation.Interpolators() {
      @Override
      protected double a(double doubleValue1) {
         return Math.abs(doubleValue1 - 1.0) < 1.0E-12 ? 1.0 : 0.0;
      }

      @Override
      public String toString() {
         return "Interpolator.DISCRETE";
      }
   };
   public static final dev.leg1tka.api.animation.Interpolators b = new dev.leg1tka.api.animation.Interpolators() {
      @Override
      protected double a(double doubleValue1) {
         return doubleValue1;
      }

      @Override
      public String toString() {
         return "Interpolator.LINEAR";
      }
   };
   public static final dev.leg1tka.api.animation.Interpolators c = new dev.leg1tka.api.animation.Interpolators() {
      @Override
      protected double a(double doubleValue1) {
         return dev.leg1tka.api.animation.Interpolators.b(doubleValue1 < 0.2 ? 3.125 * doubleValue1 * doubleValue1 : (doubleValue1 > 0.8 ? -3.125 * doubleValue1 * doubleValue1 + 6.25 * doubleValue1 - 2.125 : 1.25 * doubleValue1 - 0.125));
      }

      @Override
      public String toString() {
         return "Interpolator.EASE_BOTH";
      }
   };
   public static final dev.leg1tka.api.animation.Interpolators d = new dev.leg1tka.api.animation.Interpolators() {
      private static double f;
      private static double g;
      private static double h;

      @Override
      protected double a(double doubleValue1) {
         return dev.leg1tka.api.animation.Interpolators.b(doubleValue1 < 0.2 ? 2.7777777777777777 * doubleValue1 * doubleValue1 : 1.1111111111111112 * doubleValue1 - 0.1111111111111111);
      }

      @Override
      public String toString() {
         return "Interpolator.EASE_IN";
      }
   };
   public static final dev.leg1tka.api.animation.Interpolators e = new dev.leg1tka.api.animation.Interpolators() {
      private static double f;
      private static double g;
      private static double h;
      private static double i;

      @Override
      protected double a(double doubleValue1) {
         return dev.leg1tka.api.animation.Interpolators.b(doubleValue1 > 0.8 ? -2.7777777777777777 * doubleValue1 * doubleValue1 + 5.555555555555555 * doubleValue1 + -1.7777777777777777 : 1.1111111111111112 * doubleValue1);
      }

      @Override
      public String toString() {
         return "Interpolator.EASE_OUT";
      }
   };

   protected Interpolators() {
   }

   public boolean a(boolean flag1, boolean flag2, double doubleValue3) {
      return Math.abs(this.a(doubleValue3) - 1.0) < 1.0E-12 ? flag2 : flag1;
   }

   public double a(double doubleValue1, double doubleValue3, double doubleValue5) {
      return doubleValue1 + (doubleValue3 - doubleValue1) * this.a(doubleValue5);
   }

   public int a(int intValue1, int intValue2, double doubleValue3) {
      return intValue1 + (int)Math.round((double)(intValue2 - intValue1) * this.a(doubleValue3));
   }

   public long a(long longValue1, long longValue3, double doubleValue5) {
      return longValue1 + Math.round((double)(longValue3 - longValue1) * this.a(doubleValue5));
   }

   static double b(double doubleValue0) {
      return doubleValue0 < 0.0 ? 0.0 : (doubleValue0 > 1.0 ? 1.0 : doubleValue0);
   }

   protected abstract double a(double doubleValue1);
}
