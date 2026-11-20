package dev.leg1tka.api.utils.math;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import net.minecraft.util.math.MathHelper;

public final class ParticleMotionState {
   public final float[] a = new float[3];
   public final float[] b = new float[3];
   public final float[] c = new float[3];
   public final float[] d = new float[3];
   public final float[] e = new float[3];
   public final float[] f = new float[3];
   public final float[] g = new float[3];
   public final float[] h = new float[4];
   public final float[] i = new float[4];
   public float j;
   public float k;
   private static final float[][] l = new float[][]{
      {
            0.4F,
            0.56F,
            0.8F,
            0.8F,
            0.87F,
            0.95F,
            1.0F,
            0.96F,
            0.88F,
            0.86F,
            0.93F,
            1.08F,
            1.02F,
            1.02F,
            1.0F,
            0.01F,
            0.016F,
            0.03F,
            0.97F,
            0.99F,
            1.0F,
            0.86F,
            1.05F,
            1.0F,
            1.0F,
            0.022F,
            0.22F,
            0.0028F,
            0.55F
      },
      {
            0.3F,
            0.34F,
            0.6F,
            1.0F,
            0.7F,
            0.6F,
            1.0F,
            0.62F,
            0.38F,
            0.82F,
            0.86F,
            1.1F,
            1.08F,
            0.98F,
            0.9F,
            0.012F,
            0.012F,
            0.03F,
            1.0F,
            0.88F,
            0.84F,
            0.95F,
            1.06F,
            1.0F,
            1.0F,
            0.026F,
            0.22F,
            0.0034F,
            0.6F
      },
      {
            0.012F,
            0.022F,
            0.058F,
            0.055F,
            0.075F,
            0.145F,
            0.55F,
            0.65F,
            0.9F,
            0.8F,
            0.88F,
            1.16F,
            0.92F,
            0.97F,
            1.08F,
            0.006F,
            0.01F,
            0.026F,
            0.55F,
            0.62F,
            0.85F,
            0.72F,
            1.04F,
            1.0F,
            1.05F,
            0.02F,
            0.22F,
            0.003F,
            0.5F
      }
   };
   private static final float[][] m = new float[][]{
      {
            0.5F,
            0.62F,
            0.95F,
            1.0F,
            0.84F,
            0.88F,
            1.0F,
            0.88F,
            0.78F,
            0.98F,
            0.88F,
            1.06F,
            1.05F,
            0.99F,
            0.96F,
            0.02F,
            0.01F,
            0.026F,
            1.0F,
            0.97F,
            0.98F,
            1.06F,
            0.97F,
            1.0F,
            1.0F,
            0.018F,
            0.28F,
            0.0034F,
            0.5F
      },
      {
            0.42F,
            0.36F,
            0.7F,
            1.0F,
            0.66F,
            0.62F,
            1.0F,
            0.6F,
            0.45F,
            0.95F,
            0.8F,
            1.05F,
            1.1F,
            0.96F,
            0.88F,
            0.02F,
            0.008F,
            0.028F,
            1.0F,
            0.86F,
            0.84F,
            1.1F,
            1.0F,
            1.0F,
            1.0F,
            0.022F,
            0.28F,
            0.004F,
            0.55F
      },
      {
            0.04F,
            0.03F,
            0.09F,
            0.14F,
            0.09F,
            0.17F,
            0.75F,
            0.7F,
            0.95F,
            0.92F,
            0.82F,
            1.1F,
            0.98F,
            0.92F,
            1.04F,
            0.01F,
            0.006F,
            0.02F,
            0.7F,
            0.62F,
            0.8F,
            0.85F,
            1.02F,
            1.0F,
            1.04F,
            0.018F,
            0.28F,
            0.003F,
            0.45F
      }
   };

   public ParticleMotionState() {
   }

   public void a(boolean flag1, float floatValue2) {
      float floatValue3 = floatValue2 * (float) (Math.PI * 2);
      float floatValue4 = MathHelper.cos(floatValue3);
      float floatValue5 = MathHelper.clamp((floatValue4 - 0.06F) / 0.26F, 0.0F, 1.0F);
      float floatValue6 = MathHelper.clamp((-floatValue4 - 0.04F) / 0.2F, 0.0F, 1.0F);
      float floatValue7 = Math.max(0.0F, 1.0F - Math.abs(floatValue4 - 0.01F) / 0.3F);
      float floatValue8 = floatValue5 + floatValue6 + floatValue7;
      if (floatValue8 < 1.0E-4F) {
         floatValue5 = 1.0F;
         floatValue8 = 1.0F;
      }

      floatValue5 /= floatValue8;
      floatValue6 /= floatValue8;
      floatValue7 /= floatValue8;
      float[][] floatValueArrayArray9 = flag1 ? l : m;
      float[] floatValueArray10 = new float[floatValueArrayArray9[0].length];

      for (int intValue11 = 0; intValue11 < floatValueArray10.length; intValue11++) {
         floatValueArray10[intValue11] = floatValueArrayArray9[0][intValue11] * floatValue5 + floatValueArrayArray9[1][intValue11] * floatValue7 + floatValueArrayArray9[2][intValue11] * floatValue6;
      }

      int intValue15 = 0;
      intValue15 = a(floatValueArray10, intValue15, this.a);
      intValue15 = a(floatValueArray10, intValue15, this.b);
      intValue15 = a(floatValueArray10, intValue15, this.c);
      intValue15 = a(floatValueArray10, intValue15, this.d);
      intValue15 = a(floatValueArray10, intValue15, this.e);
      intValue15 = a(floatValueArray10, intValue15, this.f);
      intValue15 = a(floatValueArray10, intValue15, this.g);
      intValue15 = a(floatValueArray10, intValue15, this.h);
      a(floatValueArray10, intValue15, this.i);
      this.j = MathHelper.clamp(floatValue4 * 4.0F + 0.3F, 0.0F, 1.0F);
      this.k = MathHelper.clamp((0.02F - floatValue4) / 0.18F, 0.0F, 1.0F);
   }

   private static int a(float[] floatValueArray0, int intValue1, float[] floatValueArray2) {
      System.arraycopy(floatValueArray0, intValue1, floatValueArray2, 0, floatValueArray2.length);
      return intValue1 + floatValueArray2.length;
   }
}
