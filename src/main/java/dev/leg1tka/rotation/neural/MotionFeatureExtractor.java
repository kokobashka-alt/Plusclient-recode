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

public final class MotionFeatureExtractor {
   private static double o;
   private static double p;
   private static double q;
   private static int r;
   private static int s;
   private static double t;
   private static double u;
   private static double v;
   private static double w;
   public final int a;
   public final boolean b;
   public final boolean c;
   public final boolean d;
   public final boolean e;
   public final boolean f;
   public final boolean g;
   public double h;
   public double i;
   public double j;
   public boolean k;
   public float l = 0.6F;
   public float m = 1.8F;
   boolean n;
   private dev.leg1tka.rotation.neural.MotionFeatureExtractor.a x;
   private dev.leg1tka.rotation.neural.MotionFeatureExtractor.a y;
   final double[] z = new double[3];
   private boolean A;
   private double B;
   private double C;
   private double D;
   double[] E;

   public MotionFeatureExtractor(int intValue1, boolean flag2, boolean flag3, boolean flag4, boolean flag5, boolean flag6, boolean flag7, double doubleValue8, double doubleValue10, double doubleValue12) {
      this.a = intValue1;
      this.b = flag2;
      this.c = flag3;
      this.d = flag4;
      this.e = flag5;
      this.f = flag6;
      this.g = flag7;
      this.h = doubleValue8;
      this.i = doubleValue10;
      this.j = doubleValue12;
   }

   public boolean a() {
      return !this.k;
   }

   int b() {
      if (this.d) {
         return 3;
      } else if (this.e) {
         return 5;
      } else if (this.f) {
         return 1;
      } else {
         return this.c ? 3 : 1;
      }
   }

   void a(boolean flag1, boolean flag2, long longValue3, long longValue5, long longValue7, double doubleValue9, double doubleValue11, double doubleValue13, boolean flag15) {
      double doubleValue16 = this.h;
      double doubleValue18 = this.i;
      double doubleValue20 = this.j;
      dev.leg1tka.rotation.neural.MotionFeatureExtractor.a value22 = this.x;
      dev.leg1tka.rotation.neural.MotionFeatureVector value23 = value22 != null ? value22.a() : dev.leg1tka.rotation.neural.MotionFeatureVector.a(this.h, this.i, this.j);
      if (flag2) {
         if (flag1) {
            this.a(longValue3, longValue5, longValue7);
         } else {
            this.h = doubleValue9;
            this.i = doubleValue11;
            this.j = doubleValue13;
         }

         this.a(flag1, doubleValue16, doubleValue18, doubleValue20);
      }

      this.y = value22;
      this.x = new dev.leg1tka.rotation.neural.MotionFeatureExtractor.a(value23, this, flag15);
      this.n = true;
   }

   void c() {
      this.y = null;
      this.n = false;
   }

   void a(boolean flag1) {
      dev.leg1tka.rotation.neural.MotionFeatureExtractor.a value2 = this.x;
      if (value2 != null) {
         value2.a(this.y == null, flag1);
         dev.leg1tka.rotation.neural.MotionFeatureExtractor.a value3 = this.y;
         if (value3 != null) {
            value3.a(true, flag1);
            value2.a(value3.a());
         }
      }
   }

   public dev.leg1tka.rotation.neural.MotionFeatureVector d() {
      dev.leg1tka.rotation.neural.MotionFeatureVector value1 = this.x != null ? this.x.b() : null;
      dev.leg1tka.rotation.neural.MotionFeatureVector value2 = this.y != null ? this.y.b() : null;
      if (value1 != null && value2 != null) {
         return dev.leg1tka.rotation.neural.MotionFeatureVector.a(value1, value2);
      } else if (value1 != null) {
         return value1;
      } else {
         return value2 != null ? value2 : this.e();
      }
   }

   public dev.leg1tka.rotation.neural.MotionFeatureVector e() {
      return dev.leg1tka.rotation.neural.MotionFeatureVector.a(this.h, this.i, this.j, this.l, this.m);
   }

   private void a(long longValue1, long longValue3, long longValue5) {
      if (longValue1 != 0L || longValue3 != 0L || longValue5 != 0L) {
         if (longValue1 != 0L) {
            this.h = (double)(Math.round(this.h * 4096.0) + longValue1) / 4096.0;
         }

         if (longValue3 != 0L) {
            this.i = (double)(Math.round(this.i * 4096.0) + longValue3) / 4096.0;
         }

         if (longValue5 != 0L) {
            this.j = (double)(Math.round(this.j * 4096.0) + longValue5) / 4096.0;
         }
      }
   }

   private void a(boolean flag1, double doubleValue2, double doubleValue4, double doubleValue6) {
      if (this.A) {
         int intValue8 = this.b();
         double doubleValue9 = intValue8 <= 1 ? 0.0 : ((double)intValue8 - 1.0) / (double)intValue8;
         this.z[0] = a(this.z[0] * doubleValue9, Math.abs(this.h - this.B));
         this.z[1] = a(this.z[1] * doubleValue9, Math.abs(this.i - this.C));
         this.z[2] = a(this.z[2] * doubleValue9, Math.abs(this.j - this.D));
      }

      this.A = true;
      this.B = this.h;
      this.C = this.i;
      this.D = this.j;
      double doubleValue15 = Math.abs(this.h - doubleValue2);
      double doubleValue10 = Math.abs(this.i - doubleValue4);
      double doubleValue12 = Math.abs(this.j - doubleValue6);
      boolean flag14 = doubleValue15 < 64.0 && doubleValue10 < 64.0 && doubleValue12 < 64.0;
      this.E = !flag1 && flag14 ? new double[]{doubleValue15, doubleValue10, doubleValue12} : null;
   }

   private static double a(double doubleValue0, double doubleValue2) {
      return doubleValue2 < 8.0 ? Math.max(doubleValue0, doubleValue2) : doubleValue0;
   }

   static class a {
      private dev.leg1tka.rotation.neural.MotionFeatureVector a;
      private final dev.leg1tka.rotation.neural.MotionFeatureVector b;
      private final dev.leg1tka.rotation.neural.MotionFeatureExtractor c;
      private int d;
      private int e;
      private final int f;
      private double g;
      private double h;
      private double i;
      private boolean j;

      a(dev.leg1tka.rotation.neural.MotionFeatureVector value1, dev.leg1tka.rotation.neural.MotionFeatureExtractor value2, boolean flag3) {
         this.a = value1;
         this.b = dev.leg1tka.rotation.neural.MotionFeatureVector.a(value2.h, value2.i, value2.j);
         this.c = value2;
         this.f = value2.b();
         if (flag3) {
            this.e = this.f;
         }

         this.c();
         this.d();
      }

      dev.leg1tka.rotation.neural.MotionFeatureVector a() {
         double doubleValue1 = (double)this.f;
         double doubleValue3 = (this.b.a - this.a.a) / doubleValue1;
         double doubleValue5 = (this.b.d - this.a.d) / doubleValue1;
         double doubleValue7 = (this.b.b - this.a.b) / doubleValue1;
         double doubleValue9 = (this.b.e - this.a.e) / doubleValue1;
         double doubleValue11 = (this.b.c - this.a.c) / doubleValue1;
         double doubleValue13 = (this.b.f - this.a.f) / doubleValue1;
         dev.leg1tka.rotation.neural.MotionFeatureVector value15 = this.a(this.d, doubleValue3, doubleValue5, doubleValue7, doubleValue9, doubleValue11, doubleValue13);

         for (int intValue16 = this.d + 1; intValue16 <= this.e; intValue16++) {
            value15 = dev.leg1tka.rotation.neural.MotionFeatureVector.a(value15, this.a(intValue16, doubleValue3, doubleValue5, doubleValue7, doubleValue9, doubleValue11, doubleValue13));
         }

         return value15;
      }

      dev.leg1tka.rotation.neural.MotionFeatureVector b() {
         dev.leg1tka.rotation.neural.MotionFeatureVector value1 = this.a();
         value1.a(this.c.l, this.c.m);
         return value1;
      }

      void a(boolean flag1, boolean flag2) {
         if (!flag2) {
            this.e = this.f;
         }

         if (flag1) {
            this.d = Math.min(this.d + 1, this.f);
         }

         this.e = Math.min(this.e + 1, this.f);
      }

      void a(dev.leg1tka.rotation.neural.MotionFeatureVector value1) {
         this.a = dev.leg1tka.rotation.neural.MotionFeatureVector.a(this.a, value1);
         this.d();
      }

      private dev.leg1tka.rotation.neural.MotionFeatureVector a(int intValue1, double doubleValue2, double doubleValue4, double doubleValue6, double doubleValue8, double doubleValue10, double doubleValue12) {
         return new dev.leg1tka.rotation.neural.MotionFeatureVector(
            this.a.a + (double)intValue1 * doubleValue2,
            this.a.b + (double)intValue1 * doubleValue6,
            this.a.c + (double)intValue1 * doubleValue10,
            this.a.d + (double)intValue1 * doubleValue4,
            this.a.e + (double)intValue1 * doubleValue8,
            this.a.f + (double)intValue1 * doubleValue12
         );
      }

      private void c() {
         double doubleValue1 = (double)this.f * Math.max(this.c.z[0], 0.05) * 1.1 + 0.001;
         double doubleValue3 = (double)this.f * Math.max(this.c.z[1], 0.08) * 1.1 + 0.001;
         double doubleValue5 = (double)this.f * Math.max(this.c.z[2], 0.05) * 1.1 + 0.001;
         double[] doubleValueArray7 = this.c.E;
         if (doubleValueArray7 != null) {
            doubleValue1 = Math.max(doubleValue1, doubleValueArray7[0] + 0.001);
            doubleValue3 = Math.max(doubleValue3, doubleValueArray7[1] + 0.001);
            doubleValue5 = Math.max(doubleValue5, doubleValueArray7[2] + 0.001);
            this.j = true;
         }

         this.c.E = null;
         this.g = doubleValue1;
         this.h = doubleValue3;
         this.i = doubleValue5;
      }

      private void d() {
         dev.leg1tka.rotation.neural.MotionFeatureVector value1 = this.a;
         double doubleValue2 = this.b.b();
         double doubleValue4 = this.b.c();
         double doubleValue6 = this.b.d();
         double doubleValue8 = Math.max(value1.a, doubleValue2 - this.g);
         double doubleValue10 = Math.min(value1.d, doubleValue2 + this.g);
         double doubleValue12 = Math.max(value1.b, doubleValue4 - this.h);
         double doubleValue14 = Math.min(value1.e, doubleValue4 + this.h);
         double doubleValue16 = Math.max(value1.c, doubleValue6 - this.i);
         double doubleValue18 = Math.min(value1.f, doubleValue6 + this.i);
         if (doubleValue8 > doubleValue10) {
            doubleValue8 = this.j ? value1.a : doubleValue2;
            doubleValue10 = this.j ? value1.d : doubleValue2;
         }

         if (doubleValue12 > doubleValue14) {
            doubleValue12 = this.j ? value1.b : doubleValue4;
            doubleValue14 = this.j ? value1.e : doubleValue4;
         }

         if (doubleValue16 > doubleValue18) {
            doubleValue16 = this.j ? value1.c : doubleValue6;
            doubleValue18 = this.j ? value1.f : doubleValue6;
         }

         value1.a = doubleValue8;
         value1.b = doubleValue12;
         value1.c = doubleValue16;
         value1.d = doubleValue10;
         value1.e = doubleValue14;
         value1.f = doubleValue18;
      }
   }
}
