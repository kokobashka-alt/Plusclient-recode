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

public final class MotionFeatureVector {
   public double a;
   public double b;
   public double c;
   public double d;
   public double e;
   public double f;

   public MotionFeatureVector(double doubleValue1, double doubleValue3, double doubleValue5, double doubleValue7, double doubleValue9, double doubleValue11) {
      this.a = doubleValue1;
      this.b = doubleValue3;
      this.c = doubleValue5;
      this.d = doubleValue7;
      this.e = doubleValue9;
      this.f = doubleValue11;
   }

   public static dev.leg1tka.rotation.neural.MotionFeatureVector a(double doubleValue0, double doubleValue2, double doubleValue4) {
      return new dev.leg1tka.rotation.neural.MotionFeatureVector(doubleValue0, doubleValue2, doubleValue4, doubleValue0, doubleValue2, doubleValue4);
   }

   public static dev.leg1tka.rotation.neural.MotionFeatureVector a(double doubleValue0, double doubleValue2, double doubleValue4, float floatValue6, float floatValue7) {
      double doubleValue8 = (double)floatValue6 / 2.0;
      double doubleValue10 = doubleValue0 - doubleValue8;
      double doubleValue12 = doubleValue0 + doubleValue8;
      double doubleValue14 = doubleValue2 + (double)floatValue7;
      double doubleValue16 = doubleValue4 - doubleValue8;
      double doubleValue18 = doubleValue4 + doubleValue8;
      return new dev.leg1tka.rotation.neural.MotionFeatureVector(
         Math.min(doubleValue10, doubleValue12), Math.min(doubleValue2, doubleValue14), Math.min(doubleValue16, doubleValue18), Math.max(doubleValue10, doubleValue12), Math.max(doubleValue2, doubleValue14), Math.max(doubleValue16, doubleValue18)
      );
   }

   public static dev.leg1tka.rotation.neural.MotionFeatureVector a(dev.leg1tka.rotation.neural.MotionFeatureVector value0, dev.leg1tka.rotation.neural.MotionFeatureVector value1) {
      return new dev.leg1tka.rotation.neural.MotionFeatureVector(
         Math.min(value0.a, value1.a),
         Math.min(value0.b, value1.b),
         Math.min(value0.c, value1.c),
         Math.max(value0.d, value1.d),
         Math.max(value0.e, value1.e),
         Math.max(value0.f, value1.f)
      );
   }

   public dev.leg1tka.rotation.neural.MotionFeatureVector a() {
      return new dev.leg1tka.rotation.neural.MotionFeatureVector(this.a, this.b, this.c, this.d, this.e, this.f);
   }

   public void b(double doubleValue1, double doubleValue3, double doubleValue5) {
      this.a -= doubleValue1;
      this.b -= doubleValue3;
      this.c -= doubleValue5;
      this.d += doubleValue1;
      this.e += doubleValue3;
      this.f += doubleValue5;
   }

   public void a(float floatValue1, float floatValue2) {
      double doubleValue3 = (double)floatValue1 / 2.0;
      double doubleValue5 = this.a - doubleValue3;
      double doubleValue7 = this.c - doubleValue3;
      double doubleValue9 = this.d + doubleValue3;
      double doubleValue11 = this.e + (double)floatValue2;
      double doubleValue13 = this.f + doubleValue3;
      double doubleValue15 = this.b;
      this.a = Math.min(doubleValue5, doubleValue9);
      this.b = Math.min(doubleValue15, doubleValue11);
      this.c = Math.min(doubleValue7, doubleValue13);
      this.d = Math.max(doubleValue5, doubleValue9);
      this.e = Math.max(doubleValue15, doubleValue11);
      this.f = Math.max(doubleValue7, doubleValue13);
   }

   public boolean c(double doubleValue1, double doubleValue3, double doubleValue5) {
      return doubleValue1 > this.a && doubleValue1 < this.d && doubleValue3 > this.b && doubleValue3 < this.e && doubleValue5 > this.c && doubleValue5 < this.f;
   }

   public double b() {
      return (this.a + this.d) * 0.5;
   }

   public double c() {
      return (this.b + this.e) * 0.5;
   }

   public double d() {
      return (this.c + this.f) * 0.5;
   }
}
