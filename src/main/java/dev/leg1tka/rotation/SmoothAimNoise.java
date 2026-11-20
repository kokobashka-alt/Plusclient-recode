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
import net.minecraft.util.math.MathHelper;

public final class SmoothAimNoise {
   private float a;
   private float b;
   private float c;
   private float d;
   private boolean e;
   private long f;
   private long g;
   private boolean h;
   private final Random i = new Random();
   private final float[] j = new float[2];
   private static float k;
   private static float l;
   private static float m;
   private static float n;
   private static long o;
   private static long p;
   private static float q;
   private static float r;
   private static float s;
   private static float t;
   private static float u;

   public SmoothAimNoise() {
   }

   public void a() {
      this.e = false;
      this.a = 0.0F;
      this.b = 0.0F;
      this.c = 0.08F;
      this.d = 0.0F;
      this.f = 0L;
      this.g = 0L;
      this.h = false;
   }

   public void b() {
      this.h = true;
   }

   public float[] a(float floatValue1, float floatValue2, float floatValue3, long longValue4, boolean flag6) {
      float floatValue7 = 0.0F;
      float floatValue8 = 0.0F;
      if (this.e) {
         this.d += floatValue3;
         float floatValue9 = this.d / this.c;
         if (floatValue9 >= 1.0F) {
            this.e = false;
            this.h = false;
         } else {
            float floatValue10 = floatValue9 * floatValue9;
            float floatValue11 = (1.0F - floatValue9) * (1.0F - floatValue9);
            float floatValue12 = 30.0F * floatValue10 * floatValue11;
            floatValue7 = this.a * floatValue12;
            floatValue8 = this.b * floatValue12;
            if (floatValue9 < 0.55F) {
               this.j[0] = floatValue7;
               this.j[1] = floatValue8;
               return this.j;
            }
         }
      }

      float floatValue15 = (float)Math.hypot((double)floatValue1, (double)floatValue2);
      float floatValue16 = flag6 ? 1.6F : 3.0F;
      float floatValue17 = this.h ? 0.22F : (flag6 ? 0.16F : 0.14F);
      if (floatValue15 > floatValue17 && longValue4 >= this.g) {
         this.c = 0.028F + 0.016F * (float)(Math.log(1.0 + (double)(floatValue15 / 0.45F)) / Math.log(2.0));
         this.c = MathHelper.clamp(this.c, 0.035F, 0.2F);
         float floatValue18 = flag6 ? 1.0F : 0.985F + (this.i.nextFloat() - 0.5F) * 0.02F;
         float floatValue13 = floatValue16 * floatValue18 / this.c;
         float floatValue14 = 1.0F + 0.022F * (float)this.i.nextGaussian();
         this.a = floatValue1 * floatValue13 * floatValue14;
         this.b = floatValue2 * floatValue13 * floatValue14 * 0.78F;
         this.d = 0.0F;
         this.e = true;
         this.f = longValue4;
         this.g = longValue4 + 70L + (long)(this.i.nextFloat() * 45.0F);
         this.j[0] = floatValue7;
         this.j[1] = floatValue8;
         return this.j;
      } else {
         this.j[0] = floatValue7;
         this.j[1] = floatValue8;
         return this.j;
      }
   }
}
