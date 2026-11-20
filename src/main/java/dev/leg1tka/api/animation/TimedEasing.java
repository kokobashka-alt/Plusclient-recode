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

public interface TimedEasing {
   dev.leg1tka.api.animation.TimedEasing a = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4 = floatValue2 * floatValue0 / floatValue3 + floatValue1;
      return (float)(-2.0 * Math.pow((double)floatValue4, 3.0) + 3.0 * Math.pow((double)floatValue4, 2.0));
   };
   dev.leg1tka.api.animation.TimedEasing b = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4 = floatValue2 * floatValue0 / floatValue3 + floatValue1;
      return (double)floatValue4 < 0.5 ? 4.0F * floatValue4 * floatValue4 * floatValue4 : (float)(1.0 - Math.pow((double)(-2.0F * floatValue4 + 2.0F), 3.0) / 2.0);
   };
   dev.leg1tka.api.animation.TimedEasing c = (floatValue0, floatValue1, floatValue2, floatValue3) -> floatValue2 * floatValue0 / floatValue3 + floatValue1;
   dev.leg1tka.api.animation.TimedEasing d = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      return floatValue2 * (floatValue4 = floatValue0 / floatValue3) * floatValue4 + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing e = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      return -floatValue2 * (floatValue4 = floatValue0 / floatValue3) * (floatValue4 - 2.0F) + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing f = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      return (floatValue4 = floatValue0 / (floatValue3 / 2.0F)) < 1.0F ? floatValue2 / 2.0F * floatValue4 * floatValue4 + floatValue1 : -floatValue2 / 2.0F * (--floatValue4 * (floatValue4 - 2.0F) - 1.0F) + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing g = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      return floatValue2 * (floatValue4 = floatValue0 / floatValue3) * floatValue4 * floatValue4 + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing h = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      return floatValue2 * ((floatValue4 = floatValue0 / floatValue3 - 1.0F) * floatValue4 * floatValue4 + 1.0F) + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing i = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      float floatValue5;
      return (floatValue4 = floatValue0 / (floatValue3 / 2.0F)) < 1.0F ? floatValue2 / 2.0F * floatValue4 * floatValue4 * floatValue4 + floatValue1 : floatValue2 / 2.0F * ((floatValue5 = floatValue4 - 2.0F) * floatValue5 * floatValue5 + 2.0F) + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing j = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      return floatValue2 * (floatValue4 = floatValue0 / floatValue3) * floatValue4 * floatValue4 * floatValue4 + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing k = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      return -floatValue2 * ((floatValue4 = floatValue0 / floatValue3 - 1.0F) * floatValue4 * floatValue4 * floatValue4 - 1.0F) + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing l = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      float floatValue5;
      return (floatValue4 = floatValue0 / (floatValue3 / 2.0F)) < 1.0F
         ? floatValue2 / 2.0F * floatValue4 * floatValue4 * floatValue4 * floatValue4 + floatValue1
         : -floatValue2 / 2.0F * ((floatValue5 = floatValue4 - 2.0F) * floatValue5 * floatValue5 * floatValue5 - 2.0F) + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing m = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      return floatValue2 * (floatValue4 = floatValue0 / floatValue3) * floatValue4 * floatValue4 * floatValue4 * floatValue4 + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing n = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      return floatValue2 * ((floatValue4 = floatValue0 / floatValue3 - 1.0F) * floatValue4 * floatValue4 * floatValue4 * floatValue4 + 1.0F) + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing o = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      float floatValue5;
      return (floatValue4 = floatValue0 / (floatValue3 / 2.0F)) < 1.0F
         ? floatValue2 / 2.0F * floatValue4 * floatValue4 * floatValue4 * floatValue4 * floatValue4 + floatValue1
         : floatValue2 / 2.0F * ((floatValue5 = floatValue4 - 2.0F) * floatValue5 * floatValue5 * floatValue5 * floatValue5 + 2.0F) + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing p = (floatValue0, floatValue1, floatValue2, floatValue3) -> -floatValue2 * (float)Math.cos((double)(floatValue0 / floatValue3) * (Math.PI / 2)) + floatValue2 + floatValue1;
   dev.leg1tka.api.animation.TimedEasing q = (floatValue0, floatValue1, floatValue2, floatValue3) -> floatValue2 * (float)Math.sin((double)(floatValue0 / floatValue3) * (Math.PI / 2)) + floatValue1;
   dev.leg1tka.api.animation.TimedEasing r = (floatValue0, floatValue1, floatValue2, floatValue3) -> -floatValue2 / 2.0F * ((float)Math.cos(Math.PI * (double)floatValue0 / (double)floatValue3) - 1.0F) + floatValue1;
   dev.leg1tka.api.animation.TimedEasing s = (floatValue0, floatValue1, floatValue2, floatValue3) -> floatValue0 == 0.0F ? floatValue1 : floatValue2 * (float)Math.pow(2.0, (double)(10.0F * (floatValue0 / floatValue3 - 1.0F))) + floatValue1;
   dev.leg1tka.api.animation.TimedEasing t = (floatValue0, floatValue1, floatValue2, floatValue3) -> floatValue0 == floatValue3 ? floatValue1 + floatValue2 : floatValue2 * (-((float)Math.pow(2.0, (double)(-10.0F * floatValue0 / floatValue3))) + 1.0F) + floatValue1;
   dev.leg1tka.api.animation.TimedEasing u = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      if (floatValue0 == 0.0F) {
         return floatValue1;
      } else if (floatValue0 == floatValue3) {
         return floatValue1 + floatValue2;
      } else {
         float floatValue4;
         return (floatValue4 = floatValue0 / (floatValue3 / 2.0F)) < 1.0F
            ? floatValue2 / 2.0F * (float)Math.pow(2.0, (double)(10.0F * (floatValue4 - 1.0F))) + floatValue1
            : floatValue2 / 2.0F * (-((float)Math.pow(2.0, (double)(-10.0F * --floatValue4))) + 2.0F) + floatValue1;
      }
   };
   dev.leg1tka.api.animation.TimedEasing v = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      return -floatValue2 * ((float)Math.sqrt((double)(1.0F - (floatValue4 = floatValue0 / floatValue3) * floatValue4)) - 1.0F) + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing w = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      return floatValue2 * (float)Math.sqrt((double)(1.0F - (floatValue4 = floatValue0 / floatValue3 - 1.0F) * floatValue4)) + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing x = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      float floatValue4;
      float floatValue5;
      return (floatValue4 = floatValue0 / (floatValue3 / 2.0F)) < 1.0F
         ? -floatValue2 / 2.0F * ((float)Math.sqrt((double)(1.0F - floatValue4 * floatValue4)) - 1.0F) + floatValue1
         : floatValue2 / 2.0F * ((float)Math.sqrt((double)(1.0F - (floatValue5 = floatValue4 - 2.0F) * floatValue5)) + 1.0F) + floatValue1;
   };
   dev.leg1tka.api.animation.TimedEasing.e y = new dev.leg1tka.api.animation.TimedEasing.f();
   dev.leg1tka.api.animation.TimedEasing.e z = new dev.leg1tka.api.animation.TimedEasing.h();
   dev.leg1tka.api.animation.TimedEasing.e A = new dev.leg1tka.api.animation.TimedEasing.g();
   dev.leg1tka.api.animation.TimedEasing.a B = new dev.leg1tka.api.animation.TimedEasing.b();
   dev.leg1tka.api.animation.TimedEasing.a C = new dev.leg1tka.api.animation.TimedEasing.d();
   dev.leg1tka.api.animation.TimedEasing.a D = new dev.leg1tka.api.animation.TimedEasing.d(5.70158F);
   dev.leg1tka.api.animation.TimedEasing.a E = new dev.leg1tka.api.animation.TimedEasing.c();
   dev.leg1tka.api.animation.TimedEasing F = (floatValue0, floatValue1, floatValue2, floatValue3) -> {
      if ((floatValue0 = floatValue0 / floatValue3) < 0.36363637F) {
         return floatValue2 * 7.5625F * floatValue0 * floatValue0 + floatValue1;
      } else if (floatValue0 < 0.72727275F) {
         float floatValue7;
         return floatValue2 * (7.5625F * (floatValue7 = floatValue0 - 0.54545456F) * floatValue7 + 0.75F) + floatValue1;
      } else {
         float floatValue5;
         float floatValue6;
         return floatValue0 < 0.90909094F
            ? floatValue2 * (7.5625F * (floatValue5 = floatValue0 - 0.8181818F) * floatValue5 + 0.9375F) + floatValue1
            : floatValue2 * (7.5625F * (floatValue6 = floatValue0 - 0.95454544F) * floatValue6 + 0.984375F) + floatValue1;
      }
   };
   dev.leg1tka.api.animation.TimedEasing G = (floatValue0, floatValue1, floatValue2, floatValue3) -> floatValue2 - F.ease(floatValue3 - floatValue0, 0.0F, floatValue2, floatValue3) + floatValue1;
   dev.leg1tka.api.animation.TimedEasing H = (floatValue0, floatValue1, floatValue2, floatValue3) -> floatValue0 < floatValue3 / 2.0F
         ? G.ease(floatValue0 * 2.0F, 0.0F, floatValue2, floatValue3) * 0.5F + floatValue1
         : F.ease(floatValue0 * 2.0F - floatValue3, 0.0F, floatValue2, floatValue3) * 0.5F + floatValue2 * 0.5F + floatValue1;

   float ease(float floatValue1, float floatValue2, float floatValue3, float floatValue4);

   public abstract static class a implements dev.leg1tka.api.animation.TimedEasing {
      public static float I;
      private float J;

      public a() {
         this(1.70158F);
      }

      public a(float floatValue1) {
         this.J = floatValue1;
      }

      @Generated
      public void a(float floatValue1) {
         this.J = floatValue1;
      }

      @Generated
      public float a() {
         return this.J;
      }
   }

   public static class b extends dev.leg1tka.api.animation.TimedEasing.a {
      public b() {
      }

      public b(float floatValue1) {
         super(floatValue1);
      }

      @Override
      public float ease(float floatValue1, float floatValue2, float floatValue3, float floatValue4) {
         float floatValue5 = this.a();
         float floatValue6;
         return floatValue3 * (floatValue6 = floatValue1 / floatValue4) * floatValue6 * ((floatValue5 + 1.0F) * floatValue6 - floatValue5) + floatValue2;
      }
   }

   public static class c extends dev.leg1tka.api.animation.TimedEasing.a {
      public c() {
      }

      public c(float floatValue1) {
         super(floatValue1);
      }

      @Override
      public float ease(float floatValue1, float floatValue2, float floatValue3, float floatValue4) {
         float floatValue5 = this.a();
         float floatValue6;
         float floatValue7;
         float floatValue8;
         float floatValue9;
         return (floatValue6 = floatValue1 / (floatValue4 / 2.0F)) < 1.0F
            ? floatValue3 / 2.0F * floatValue6 * floatValue6 * (((floatValue8 = floatValue5 * 1.525F) + 1.0F) * floatValue6 - floatValue8) + floatValue2
            : floatValue3 / 2.0F * ((floatValue7 = floatValue6 - 2.0F) * floatValue7 * (((floatValue9 = floatValue5 * 1.525F) + 1.0F) * floatValue7 + floatValue9) + 2.0F) + floatValue2;
      }
   }

   public static class d extends dev.leg1tka.api.animation.TimedEasing.a {
      public d() {
      }

      public d(float floatValue1) {
         super(floatValue1);
      }

      @Override
      public float ease(float floatValue1, float floatValue2, float floatValue3, float floatValue4) {
         float floatValue5 = this.a();
         float floatValue6;
         return floatValue3 * ((floatValue6 = floatValue1 / floatValue4 - 1.0F) * floatValue6 * ((floatValue5 + 1.0F) * floatValue6 + floatValue5) + 1.0F) + floatValue2;
      }
   }

   public abstract static class e implements dev.leg1tka.api.animation.TimedEasing {
      private float I;
      private float J;

      public e(float floatValue1, float floatValue2) {
         this.I = floatValue1;
         this.J = floatValue2;
      }

      public e() {
         this(-1.0F, 0.0F);
      }

      @Generated
      public void a(float floatValue1) {
         this.I = floatValue1;
      }

      @Generated
      public void b(float floatValue1) {
         this.J = floatValue1;
      }

      @Generated
      public float a() {
         return this.I;
      }

      @Generated
      public float b() {
         return this.J;
      }
   }

   public static class f extends dev.leg1tka.api.animation.TimedEasing.e {
      public f(float floatValue1, float floatValue2) {
         super(floatValue1, floatValue2);
      }

      public f() {
      }

      @Override
      public float ease(float floatValue1, float floatValue2, float floatValue3, float floatValue4) {
         float floatValue5 = this.a();
         float floatValue6 = this.b();
         if (floatValue1 == 0.0F) {
            return floatValue2;
         } else if ((floatValue1 = floatValue1 / floatValue4) == 1.0F) {
            return floatValue2 + floatValue3;
         } else {
            if (floatValue6 == 0.0F) {
               floatValue6 = floatValue4 * 0.3F;
            }

            float floatValue7;
            if (floatValue5 < Math.abs(floatValue3)) {
               floatValue5 = floatValue3;
               floatValue7 = floatValue6 / 4.0F;
            } else {
               floatValue7 = floatValue6 / (float) (Math.PI * 2) * (float)Math.asin((double)(floatValue3 / floatValue5));
            }

            return -(floatValue5 * (float)Math.pow(2.0, (double)(10.0F * --floatValue1)) * (float)Math.sin((double)(floatValue1 * floatValue4 - floatValue7) * (Math.PI * 2) / (double)floatValue6))
               + floatValue2;
         }
      }
   }

   public static class g extends dev.leg1tka.api.animation.TimedEasing.e {
      public g(float floatValue1, float floatValue2) {
         super(floatValue1, floatValue2);
      }

      public g() {
      }

      @Override
      public float ease(float floatValue1, float floatValue2, float floatValue3, float floatValue4) {
         float floatValue5 = this.a();
         float floatValue6 = this.b();
         if (floatValue1 == 0.0F) {
            return floatValue2;
         } else if ((floatValue1 = floatValue1 / (floatValue4 / 2.0F)) == 2.0F) {
            return floatValue2 + floatValue3;
         } else {
            if (floatValue6 == 0.0F) {
               floatValue6 = floatValue4 * 0.45000002F;
            }

            float floatValue7;
            if (floatValue5 < Math.abs(floatValue3)) {
               floatValue5 = floatValue3;
               floatValue7 = floatValue6 / 4.0F;
            } else {
               floatValue7 = floatValue6 / (float) (Math.PI * 2) * (float)Math.asin((double)(floatValue3 / floatValue5));
            }

            return floatValue1 < 1.0F
               ? -0.5F * floatValue5 * (float)Math.pow(2.0, (double)(10.0F * --floatValue1)) * (float)Math.sin((double)(floatValue1 * floatValue4 - floatValue7) * (Math.PI * 2) / (double)floatValue6)
                  + floatValue2
               : floatValue5 * (float)Math.pow(2.0, (double)(-10.0F * --floatValue1)) * (float)Math.sin((double)(floatValue1 * floatValue4 - floatValue7) * (Math.PI * 2) / (double)floatValue6) * 0.5F
                  + floatValue3
                  + floatValue2;
         }
      }
   }

   public static class h extends dev.leg1tka.api.animation.TimedEasing.e {
      public h(float floatValue1, float floatValue2) {
         super(floatValue1, floatValue2);
      }

      public h() {
      }

      @Override
      public float ease(float floatValue1, float floatValue2, float floatValue3, float floatValue4) {
         float floatValue5 = this.a();
         float floatValue6 = this.b();
         if (floatValue1 == 0.0F) {
            return floatValue2;
         } else if ((floatValue1 = floatValue1 / floatValue4) == 1.0F) {
            return floatValue2 + floatValue3;
         } else {
            if (floatValue6 == 0.0F) {
               floatValue6 = floatValue4 * 0.3F;
            }

            float floatValue7;
            if (floatValue5 < Math.abs(floatValue3)) {
               floatValue5 = floatValue3;
               floatValue7 = floatValue6 / 4.0F;
            } else {
               floatValue7 = floatValue6 / (float) (Math.PI * 2) * (float)Math.asin((double)(floatValue3 / floatValue5));
            }

            return floatValue5 * (float)Math.pow(2.0, (double)(-10.0F * floatValue1)) * (float)Math.sin((double)(floatValue1 * floatValue4 - floatValue7) * (Math.PI * 2) / (double)floatValue6)
               + floatValue3
               + floatValue2;
         }
      }
   }
}
