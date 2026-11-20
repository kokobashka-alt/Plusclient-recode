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
import net.minecraft.util.math.Vec3d;

public final class VectorInterpolation {
   public static double a;
   public static double b;
   public static int c;

   private VectorInterpolation() {
   }

   public static Vec3d a(float floatValue0, float floatValue1, Vec3d vec3d2) {
      float floatValue3 = -MathHelper.sin(floatValue0 * (float) (Math.PI / 180.0)) * MathHelper.cos(floatValue1 * (float) (Math.PI / 180.0));
      float floatValue4 = -MathHelper.sin(floatValue1 * (float) (Math.PI / 180.0));
      float floatValue5 = MathHelper.cos(floatValue0 * (float) (Math.PI / 180.0)) * MathHelper.cos(floatValue1 * (float) (Math.PI / 180.0));
      return new Vec3d((double)floatValue3, (double)floatValue4, (double)floatValue5).normalize().multiply(1.5).add(vec3d2);
   }

   public static dev.leg1tka.api.utils.math.VectorInterpolation.a a(dev.leg1tka.api.utils.math.VectorInterpolation.c value0, Vec3d vec3d1, Vec3d vec3d2, boolean flag3) {
      double doubleValue4 = value0.a() - 64.0;

      for (int intValue6 = 1; intValue6 <= 300; intValue6++) {
         double doubleValue7 = value0.a(vec3d1) ? 0.8 : 0.99;
         vec3d2 = new Vec3d(vec3d2.x, vec3d2.y - 0.03, vec3d2.z).multiply(doubleValue7);
         Vec3d vec3d9 = vec3d1.add(vec3d2);
         if (value0.a(vec3d1, vec3d9)) {
            return new dev.leg1tka.api.utils.math.VectorInterpolation.a(vec3d1, intValue6, false);
         }

         if (flag3 && value0.b(vec3d1, vec3d9)) {
            return new dev.leg1tka.api.utils.math.VectorInterpolation.a(vec3d1, intValue6, true);
         }

         if (vec3d9.y < doubleValue4) {
            return null;
         }

         vec3d1 = vec3d9;
      }

      return null;
   }

   public static dev.leg1tka.api.utils.math.VectorInterpolation.b a(dev.leg1tka.api.utils.math.VectorInterpolation.c value0, Vec3d vec3d1, Vec3d vec3d2, Vec3d vec3d3, float floatValue4, float floatValue5, boolean flag6) {
      floatValue5 = MathHelper.clamp(floatValue5, -90.0F, 90.0F);
      dev.leg1tka.api.utils.math.VectorInterpolation.a value7 = a(value0, vec3d1, a(floatValue4, floatValue5, vec3d2), flag6);
      double doubleValue8 = value7 != null && !value7.entity() ? a(value7.land(), vec3d3) : Double.MAX_VALUE;
      return new dev.leg1tka.api.utils.math.VectorInterpolation.b(floatValue4, floatValue5, doubleValue8, value7);
   }

   public static double a(Vec3d vec3d0, Vec3d vec3d1) {
      double doubleValue2 = vec3d0.x - vec3d1.x;
      double doubleValue4 = (vec3d0.y - vec3d1.y) * 0.3;
      double doubleValue6 = vec3d0.z - vec3d1.z;
      return Math.sqrt(doubleValue2 * doubleValue2 + doubleValue4 * doubleValue4 + doubleValue6 * doubleValue6);
   }

   public static dev.leg1tka.api.utils.math.VectorInterpolation.b a(dev.leg1tka.api.utils.math.VectorInterpolation.c value0, Vec3d vec3d1, Vec3d vec3d2, Vec3d vec3d3) {
      float floatValue4 = (float)(Math.toDegrees(Math.atan2(vec3d3.z - vec3d1.z, vec3d3.x - vec3d1.x)) - 90.0);
      short value5 = 179;
      dev.leg1tka.api.utils.math.VectorInterpolation.b[] valueArray6 = new dev.leg1tka.api.utils.math.VectorInterpolation.b[value5];

      for (int intValue7 = 0; intValue7 < value5; intValue7++) {
         valueArray6[intValue7] = a(value0, vec3d1, vec3d2, vec3d3, floatValue4, -89.0F + (float)intValue7, false);
      }

      dev.leg1tka.api.utils.math.VectorInterpolation.b[] valueArray14 = new dev.leg1tka.api.utils.math.VectorInterpolation.b[3];

      for (int intValue8 = 0; intValue8 < value5; intValue8++) {
         double doubleValue9 = valueArray6[intValue8].err();
         if (doubleValue9 != Double.MAX_VALUE && (intValue8 <= 0 || !(valueArray6[intValue8 - 1].err() < doubleValue9)) && (intValue8 >= value5 - 1 || !(valueArray6[intValue8 + 1].err() < doubleValue9))) {
            for (int intValue11 = 0; intValue11 < valueArray14.length; intValue11++) {
               if (valueArray14[intValue11] == null || doubleValue9 < valueArray14[intValue11].err()) {
                  System.arraycopy(valueArray14, intValue11, valueArray14, intValue11 + 1, valueArray14.length - intValue11 - 1);
                  valueArray14[intValue11] = valueArray6[intValue8];
                  break;
               }
            }
         }
      }

      dev.leg1tka.api.utils.math.VectorInterpolation.b value15 = null;

      for (dev.leg1tka.api.utils.math.VectorInterpolation.b value12 : valueArray14) {
         if (value12 != null) {
            dev.leg1tka.api.utils.math.VectorInterpolation.b value13 = a(value0, vec3d1, vec3d2, vec3d3, value12.yaw(), value12.pitch(), 0.5);
            if (value15 == null || a(value13, value15)) {
               value15 = value13;
            }
         }
      }

      return value15;
   }

   public static dev.leg1tka.api.utils.math.VectorInterpolation.b a(dev.leg1tka.api.utils.math.VectorInterpolation.c value0, Vec3d vec3d1, Vec3d vec3d2, Vec3d vec3d3, float floatValue4, float floatValue5, double doubleValue6) {
      dev.leg1tka.api.utils.math.VectorInterpolation.b value8 = a(value0, vec3d1, vec3d2, vec3d3, floatValue4, floatValue5, false);
      int intValue9 = 0;

      while (doubleValue6 >= 0.01 && intValue9++ < 300) {
         boolean flag10 = false;
         float[][] floatValueArrayArray11 = new float[][]{{(float)doubleValue6, 0.0F}, {(float)(-doubleValue6), 0.0F}, {0.0F, (float)doubleValue6}, {0.0F, (float)(-doubleValue6)}};
         float[][] floatValueArrayArray12 = floatValueArrayArray11;
         int intValue13 = floatValueArrayArray11.length;
         int intValue14 = 0;

         while (true) {
            if (intValue14 < intValue13) {
               float[] floatValueArray15 = floatValueArrayArray12[intValue14];
               dev.leg1tka.api.utils.math.VectorInterpolation.b value16 = a(value0, vec3d1, vec3d2, vec3d3, value8.yaw() + floatValueArray15[0], value8.pitch() + floatValueArray15[1], false);
               if (!(value16.err() < value8.err() - 1.0E-6)) {
                  intValue14++;
                  continue;
               }

               value8 = value16;
               flag10 = true;
            }

            if (!flag10) {
               doubleValue6 *= 0.5;
            }
            break;
         }
      }

      return value8;
   }

   public static dev.leg1tka.api.utils.math.VectorInterpolation.b a(dev.leg1tka.api.utils.math.VectorInterpolation.c value0, Vec3d vec3d1, Vec3d vec3d2, Vec3d vec3d3, dev.leg1tka.api.utils.math.VectorInterpolation.b value4, float floatValue5, float floatValue6, double doubleValue7) {
      if (doubleValue7 <= 1.0E-6) {
         return a(value0, vec3d1, vec3d2, vec3d3, value4.yaw(), value4.pitch(), true);
      } else {
         long longValue9 = (long)Math.floor((double)(value4.yaw() - floatValue5) / doubleValue7);
         long longValue11 = (long)Math.floor((double)(value4.pitch() - floatValue6) / doubleValue7);
         dev.leg1tka.api.utils.math.VectorInterpolation.b value13 = null;

         for (long longValue14 = longValue9 - 1L; longValue14 <= longValue9 + 2L; longValue14++) {
            for (long longValue16 = longValue11 - 1L; longValue16 <= longValue11 + 2L; longValue16++) {
               float floatValue18 = (float)((double)floatValue5 + (double)longValue14 * doubleValue7);
               float floatValue19 = (float)((double)floatValue6 + (double)longValue16 * doubleValue7);
               if (!(floatValue19 < -90.0F) && !(floatValue19 > 90.0F)) {
                  dev.leg1tka.api.utils.math.VectorInterpolation.b value20 = a(value0, vec3d1, vec3d2, vec3d3, floatValue18, floatValue19, true);
                  if (value13 == null || value20.err() < value13.err()) {
                     value13 = value20;
                  }
               }
            }
         }

         return value13;
      }
   }

   private static boolean a(dev.leg1tka.api.utils.math.VectorInterpolation.b value0, dev.leg1tka.api.utils.math.VectorInterpolation.b value1) {
      return Math.abs(value0.err() - value1.err()) <= 0.25 && value0.flight() != null && value1.flight() != null ? value0.flight().ticks() < value1.flight().ticks() : value0.err() < value1.err();
   }

   public static record a(Vec3d land, int ticks, boolean entity)  {


}

   public static record b(float yaw, float pitch, double err, dev.leg1tka.api.utils.math.VectorInterpolation.a flight)  {



}

   public interface c {
      boolean a(Vec3d vec3d1, Vec3d vec3d2);

      boolean a(Vec3d vec3d1);

      boolean b(Vec3d vec3d1, Vec3d vec3d2);

      double a();
   }
}
