package dev.leg1tka.rotation;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.util.Random;

public final class RandomAimOffsets {
   private float a;
   private float b;
   private float c;
   private float d;
   private final Random e = new Random();
   private static float f;
   private static float g;
   private static float h;
   private static float i;
   private static float j;
   private static float k;

   public RandomAimOffsets() {
   }

   public void a(float floatValue1, float floatValue2) {
      this.a = floatValue1;
      this.b = floatValue2;
      this.c = 0.0F;
      this.d = 0.0F;
   }

   public void a(float floatValue1, float floatValue2, float floatValue3) {
      float floatValue4 = (floatValue1 - 1.6F * this.c) / 0.022F;
      float floatValue5 = (floatValue2 - 2.7F * this.d) / 0.038F;
      this.c += floatValue4 * floatValue3;
      this.d += floatValue5 * floatValue3;
      float floatValue6 = (float)Math.sqrt((double)floatValue3);
      float floatValue7 = (0.16F + 0.04F * Math.abs(this.c)) * floatValue6;
      float floatValue8 = (0.024F + 0.004F * Math.abs(this.d)) * 0.2F * floatValue6;
      this.c = this.c + (float)this.e.nextGaussian() * floatValue7;
      this.d = this.d + (float)this.e.nextGaussian() * floatValue8;
      this.a = this.a + this.c * floatValue3;
      this.b = this.b + this.d * floatValue3;
      if (this.b < -89.0F) {
         this.b = -89.0F;
         this.d = Math.max(this.d, 0.0F);
      }

      if (this.b > 89.0F) {
         this.b = 89.0F;
         this.d = Math.min(this.d, 0.0F);
      }
   }

   public float a() {
      return this.a;
   }

   public float b() {
      return this.b;
   }

   public float c() {
      return this.c;
   }

   public float d() {
      return this.d;
   }
}
