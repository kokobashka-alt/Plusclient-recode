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

public class TimedAnimation {
   private long a;
   private float b;
   private dev.leg1tka.api.animation.TimedEasing c;
   private long d;
   private float e;
   private float f;
   private boolean g;
   private boolean h;

   public TimedAnimation(long longValue1, float floatValue3, dev.leg1tka.api.animation.TimedEasing value4) {
      this.a = longValue1;
      this.c = value4;
      this.b = floatValue3;
      this.e = floatValue3;
      this.f = floatValue3;
      this.g = true;
   }

   public TimedAnimation(dev.leg1tka.api.animation.TimedEasing value1, long longValue2) {
      this(longValue2, 0.0F, value1);
   }

   public void a(boolean flag1) {
      this.a(flag1 ? 1.0F : 0.0F);
   }

   public float a(float floatValue1) {
      long longValue2 = System.currentTimeMillis();
      if (floatValue1 != this.f) {
         this.e = this.b;
         this.f = floatValue1;
         this.d = longValue2;
         this.g = false;
      }

      long longValue4 = longValue2 - this.d;
      if (longValue4 >= this.a) {
         this.b = this.f;
         this.g = true;
         return this.b;
      } else {
         float floatValue6 = (float)longValue4 / (float)this.a;
         float floatValue7 = this.c.ease(floatValue6, 0.0F, 1.0F, 1.0F);
         this.b = this.e + (this.f - this.e) * floatValue7;
         return this.b;
      }
   }

   public void b(float floatValue1) {
      this.b = floatValue1;
      this.e = floatValue1;
      this.f = floatValue1;
      this.g = true;
   }

   public void c(float floatValue1) {
      this.b = floatValue1;
      this.e = floatValue1;
      this.f = floatValue1;
      this.g = true;
   }

   public void a() {
      this.c(0.0F);
   }

   public void d(float floatValue1) {
      if (floatValue1 != this.f) {
         this.e = this.b;
         this.f = floatValue1;
         this.d = System.currentTimeMillis();
         this.g = false;
      }
   }

   public float b() {
      return this.a(this.f);
   }

   @Generated
   public long c() {
      return this.a;
   }

   @Generated
   public float d() {
      return this.b;
   }

   @Generated
   public dev.leg1tka.api.animation.TimedEasing e() {
      return this.c;
   }

   @Generated
   public long f() {
      return this.d;
   }

   @Generated
   public float g() {
      return this.e;
   }

   @Generated
   public float h() {
      return this.f;
   }

   @Generated
   public boolean i() {
      return this.g;
   }

   @Generated
   public boolean j() {
      return this.h;
   }

   @Generated
   public void a(long longValue1) {
      this.a = longValue1;
   }

   @Generated
   public void a(dev.leg1tka.api.animation.TimedEasing value1) {
      this.c = value1;
   }

   @Generated
   public void b(long longValue1) {
      this.d = longValue1;
   }

   @Generated
   public void e(float floatValue1) {
      this.e = floatValue1;
   }

   @Generated
   public void f(float floatValue1) {
      this.f = floatValue1;
   }

   @Generated
   public void b(boolean flag1) {
      this.g = flag1;
   }

   @Generated
   public void c(boolean flag1) {
      this.h = flag1;
   }

   @Generated
   @Override
   public boolean equals(Object object1) {
      if (object1 == this) {
         return true;
      } else if (!(object1 instanceof dev.leg1tka.api.animation.TimedAnimation value2)) {
         return false;
      } else if (!value2.a(this)) {
         return false;
      } else if (this.c() != value2.c()) {
         return false;
      } else if (Float.compare(this.d(), value2.d()) != 0) {
         return false;
      } else if (this.f() != value2.f()) {
         return false;
      } else if (Float.compare(this.g(), value2.g()) != 0) {
         return false;
      } else if (Float.compare(this.h(), value2.h()) != 0) {
         return false;
      } else if (this.i() != value2.i()) {
         return false;
      } else if (this.j() != value2.j()) {
         return false;
      } else {
         dev.leg1tka.api.animation.TimedEasing value3 = this.e();
         dev.leg1tka.api.animation.TimedEasing value4 = value2.e();
         return value3 == null ? value4 == null : value3.equals(value4);
      }
   }

   @Generated
   protected boolean a(Object object1) {
      return object1 instanceof dev.leg1tka.api.animation.TimedAnimation;
   }

   @Generated
   @Override
   public int hashCode() {
      byte byteValue1 = 59;
      int intValue2 = 1;
      long longValue3 = this.c();
      intValue2 = intValue2 * 59 + (int)(longValue3 >>> 32 ^ longValue3);
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.d());
      long longValue5 = this.f();
      intValue2 = intValue2 * 59 + (int)(longValue5 >>> 32 ^ longValue5);
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.g());
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.h());
      intValue2 = intValue2 * 59 + (this.i() ? 79 : 97);
      intValue2 = intValue2 * 59 + (this.j() ? 79 : 97);
      dev.leg1tka.api.animation.TimedEasing value7 = this.e();
      return intValue2 * 59 + (value7 == null ? 43 : value7.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "GuiAnimation(duration="
         + this.c()
         + ", value="
         + this.d()
         + ", easing="
         + this.e()
         + ", startTime="
         + this.f()
         + ", startValue="
         + this.g()
         + ", targetValue="
         + this.h()
         + ", done="
         + this.i()
         + ", direction="
         + this.j()
         + ")";
   }
}
