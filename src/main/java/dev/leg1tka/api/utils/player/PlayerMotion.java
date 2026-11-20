package dev.leg1tka.api.utils.player;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class PlayerMotion {
   private static final MinecraftClient a = MinecraftClient.getInstance();
   private static float b;
   private static float c;
   private static float d;
   private static float e;
   private static float f;
   private static float g;
   private static final float[] h = new float[]{
      1.61F,
      1.61F,
      1.61F,
      1.61F,
      1.61F,
      1.61F,
      1.62F,
      1.62F,
      1.62F,
      1.63F,
      1.63F,
      1.64F,
      1.65F,
      1.65F,
      1.66F,
      1.67F,
      1.68F,
      1.69F,
      1.7F,
      1.71F,
      1.72F,
      1.73F,
      1.73F,
      1.75F,
      1.76F,
      1.78F,
      1.79F,
      1.81F,
      1.83F,
      1.85F,
      1.87F,
      1.89F,
      1.91F,
      1.93F,
      1.95F,
      1.98F,
      2.01F,
      2.03F,
      2.06F,
      2.09F,
      2.12F,
      2.16F,
      2.19F,
      2.23F,
      2.27F,
      2.31F,
      2.35F,
      2.31F,
      2.27F,
      2.23F,
      2.19F,
      2.16F,
      2.12F,
      2.09F,
      2.06F,
      2.03F,
      2.01F,
      1.98F,
      1.95F,
      1.93F,
      1.89F,
      1.87F,
      1.85F,
      1.83F,
      1.81F,
      1.79F,
      1.78F,
      1.76F,
      1.75F,
      1.73F,
      1.72F,
      1.71F,
      1.7F,
      1.69F,
      1.68F,
      1.67F,
      1.66F,
      1.65F,
      1.64F,
      1.63F,
      1.63F,
      1.63F,
      1.62F,
      1.62F,
      1.62F,
      1.61F,
      1.61F,
      1.61F,
      1.61F,
      1.61F,
      1.61F
   };
   private static final float[] i = new float[]{
      1.61F,
      1.61F,
      1.61F,
      1.62F,
      1.62F,
      1.62F,
      1.63F,
      1.63F,
      1.64F,
      1.65F,
      1.65F,
      1.66F,
      1.67F,
      1.68F,
      1.69F,
      1.7F,
      1.71F,
      1.72F,
      1.73F,
      1.73F,
      1.75F,
      1.76F,
      1.78F,
      1.79F,
      1.81F,
      1.83F,
      1.85F,
      1.87F,
      1.89F,
      1.91F,
      1.93F,
      1.95F,
      1.98F,
      2.01F,
      2.03F,
      2.06F,
      2.09F,
      2.12F,
      2.16F,
      2.19F,
      2.23F,
      2.24F,
      2.21F,
      2.21F,
      2.21F,
      2.23F,
      2.23F,
      2.19F,
      2.16F,
      2.12F,
      2.09F,
      2.06F,
      2.03F,
      2.01F,
      1.98F,
      1.95F,
      1.93F,
      1.89F,
      1.87F,
      1.85F,
      1.83F,
      1.81F,
      1.79F,
      1.78F,
      1.76F,
      1.75F,
      1.73F,
      1.72F,
      1.71F,
      1.7F,
      1.69F,
      1.68F,
      1.67F,
      1.66F,
      1.65F,
      1.64F,
      1.63F,
      1.63F,
      1.63F,
      1.62F,
      1.62F,
      1.62F,
      1.61F,
      1.61F,
      1.61F,
      1.61F,
      1.61F,
      1.61F,
      1.61F,
      1.61F,
      1.61F
   };

   public static Vec3d a(LivingEntity livingEntity0) {
      return d(livingEntity0, 50.0F);
   }

   public static Vec3d b(LivingEntity livingEntity0) {
      return d(livingEntity0, 100.0F);
   }

   private static Vec3d d(LivingEntity livingEntity0, float floatValue1) {
      float floatValue2 = h(livingEntity0);
      if (g(livingEntity0)) {
         float floatValue3 = floatValue1 / 20.0F;
         if (floatValue1 > 50.0F) {
            float floatValue4 = floatValue1 / 50.0F;
            floatValue2 = Math.min(floatValue2 * floatValue4, floatValue3);
         } else {
            floatValue2 = Math.min(floatValue2, floatValue3);
         }
      } else {
         floatValue2 = Math.min(floatValue2, 3.0F);
      }

      Vec3d vec3d20 = livingEntity0.getRotationVector();
      Vec3d vec3d21 = Vec3d.fromPolar(livingEntity0.getPitch(), livingEntity0.getYaw()).multiply((double)floatValue2);
      float floatValue5 = livingEntity0.getPitch() * (float) (Math.PI / 180.0);
      double doubleValue6 = Math.sqrt(vec3d20.x * vec3d20.x + vec3d20.z * vec3d20.z);
      double doubleValue8 = vec3d21.horizontalLength();
      boolean flag10 = livingEntity0.getVelocity().y <= 0.0;
      double doubleValue11 = flag10 && livingEntity0.hasStatusEffect(StatusEffects.SLOW_FALLING) ? Math.min(livingEntity0.getFinalGravity(), 0.01) : livingEntity0.getFinalGravity();
      double doubleValue13 = MathHelper.square(Math.cos((double)floatValue5));
      vec3d21 = vec3d21.add(0.0, doubleValue11 * (-1.0 + doubleValue13 * 0.75), 0.0);
      if (vec3d21.y < 0.0 && doubleValue6 > 0.0) {
         double doubleValue15 = vec3d21.y * -0.1 * doubleValue13;
         vec3d21 = vec3d21.add(vec3d20.x * doubleValue15 / doubleValue6, doubleValue15, vec3d20.z * doubleValue15 / doubleValue6);
      }

      if (floatValue5 < 0.0F && doubleValue6 > 0.0) {
         double doubleValue23 = doubleValue8 * (double)(-MathHelper.sin(floatValue5)) * 0.04;
         vec3d21 = vec3d21.add(-vec3d20.x * doubleValue23 / doubleValue6, doubleValue23 * 3.2, -vec3d20.z * doubleValue23 / doubleValue6);
      }

      if (doubleValue6 > 0.0) {
         vec3d21 = vec3d21.add((vec3d20.x / doubleValue6 * doubleValue8 - vec3d21.x) * 0.1, 0.0, (vec3d20.z / doubleValue6 * doubleValue8 - vec3d21.z) * 0.1);
      }

      double doubleValue17 = vec3d21.length();
      return new Vec3d(doubleValue17, doubleValue17, doubleValue17).multiply(0.99, 0.98, 0.99);
   }

   private static boolean g(LivingEntity livingEntity0) {
      float floatValue1 = Math.abs((livingEntity0.getYaw() - 360.0F) % 360.0F);
      float[] floatValueArray2 = new float[]{45.0F, 135.0F, 225.0F, 315.0F};
      float floatValue3 = Float.MAX_VALUE;

      for (float floatValue7 : floatValueArray2) {
         floatValue3 = Math.min(floatValue3, Math.abs(floatValue1 - floatValue7));
      }

      return floatValue3 <= 22.5F;
   }

   private static float h(LivingEntity livingEntity0) {
      float floatValue1 = Math.abs(MathHelper.wrapDegrees(livingEntity0.getYaw()));
      float floatValue2 = a(floatValue1);
      float floatValue3 = Math.abs(b(livingEntity0.getPitch()));
      if (floatValue3 >= 70.0F && floatValue3 <= 90.0F) {
         return 1.615F;
      } else {
         float floatValue4 = h[Math.min((int)Math.ceil((double)floatValue2), 90)];
         int intValue5 = Math.min((int)Math.ceil((double)floatValue3), i.length - 1);
         float floatValue6 = i[intValue5];
         float floatValue7 = floatValue3 >= 75.0F ? floatValue6 : Math.max(floatValue4, floatValue6);
         return Math.max(floatValue7, floatValue3 >= 75.0F ? 1.5F : 1.61F);
      }
   }

   private static float a(float floatValue0) {
      float floatValue1 = floatValue0 > 180.0F ? 360.0F - floatValue0 : floatValue0;
      return floatValue1 > 90.0F ? 180.0F - floatValue1 : floatValue1;
   }

   private static float b(float floatValue0) {
      return Math.max(-90.0F, Math.min(90.0F, floatValue0));
   }

   public static Vec3d a(LivingEntity livingEntity0, float floatValue1) {
      float floatValue2 = Math.abs((livingEntity0.getYaw() - 360.0F) % 360.0F);
      float floatValue3 = livingEntity0.getPitch();
      float floatValue4 = Math.abs(floatValue3);
      float floatValue6 = 0.0F;
      if (floatValue4 >= 30.0F && floatValue4 <= 50.0F) {
         floatValue6 = 0.15F;
      } else if (floatValue4 >= 25.0F && floatValue4 <= 55.0F) {
         floatValue6 = 0.1F;
      } else if (floatValue4 >= 20.0F && floatValue4 <= 60.0F) {
         floatValue6 = 0.05F;
      }

      float floatValue7 = floatValue1 + floatValue6;
      float[] floatValueArray8 = new float[]{45.0F, 135.0F, 225.0F, 315.0F};
      float floatValue9 = 9999.0F;

      for (float floatValue13 : floatValueArray8) {
         float floatValue14 = Math.abs(floatValue2 - floatValue13);
         if (floatValue14 < floatValue9) {
            floatValue9 = floatValue14;
         }
      }

      if (floatValue9 < 15.0F) {
         floatValue7 += 0.1F;
      } else if (floatValue9 < 25.0F) {
         floatValue7 += 0.05F;
      }

      floatValue7 = Math.min(floatValue7, 2.8F);
      return new Vec3d((double)floatValue7, (double)floatValue7, (double)floatValue7);
   }

   public static Vec3d c(LivingEntity livingEntity0) {
      float floatValue1 = Math.abs((livingEntity0.getYaw() - 360.0F) % 360.0F);
      float floatValue2 = livingEntity0.getPitch();
      float floatValue3 = Math.abs(floatValue2);
      float floatValue4 = 2.5F;
      float floatValue5 = 2.3F;
      if (floatValue3 >= 35.0F && floatValue3 <= 50.0F) {
         floatValue4 = 2.7F;
         floatValue5 = 2.5F;
      } else if (floatValue3 >= 30.0F && floatValue3 <= 55.0F) {
         floatValue4 = 2.6F;
         floatValue5 = 2.4F;
      }

      float[] floatValueArray6 = new float[]{45.0F, 135.0F, 225.0F, 315.0F};
      float floatValue7 = 9999.0F;

      for (float floatValue11 : floatValueArray6) {
         float floatValue12 = Math.abs(floatValue1 - floatValue11);
         if (floatValue12 < floatValue7) {
            floatValue7 = floatValue12;
         }
      }

      if (floatValue7 < 20.0F) {
         floatValue4 += 0.15F;
      }

      return new Vec3d((double)floatValue4, (double)floatValue5, (double)floatValue4);
   }

   public static Vec3d a(LivingEntity livingEntity0, float floatValue1, float floatValue2, float floatValue3) {
      float floatValue4 = Math.abs(floatValue1);
      float floatValue5 = floatValue3;
      float floatValue6;
      if (floatValue4 >= 38.0F && floatValue4 <= 52.0F) {
         floatValue5 = Math.min(floatValue3 + 0.2F, 2.7F);
         floatValue6 = Math.min(floatValue3 + 0.15F, 2.5F);
      } else if (floatValue4 >= 30.0F && floatValue4 <= 60.0F) {
         floatValue5 = Math.min(floatValue3 + 0.1F, 2.6F);
         floatValue6 = Math.min(floatValue3 + 0.1F, 2.4F);
      } else if (floatValue4 >= 25.0F && floatValue4 <= 65.0F) {
         floatValue6 = floatValue3 - 0.05F;
      } else {
         floatValue5 = floatValue3 - 0.1F;
         floatValue6 = floatValue3 - 0.15F;
      }

      return new Vec3d((double)floatValue5, (double)floatValue6, (double)floatValue5);
   }

   public static Vec3d d(LivingEntity livingEntity0) {
      return b(livingEntity0, 42.0F);
   }

   public static Vec3d e(LivingEntity livingEntity0) {
      return b(livingEntity0, 39.0F);
   }

   public static Vec3d f(LivingEntity livingEntity0) {
      return b(livingEntity0, 33.2F);
   }

   public static Vec3d b(LivingEntity livingEntity0, float floatValue1) {
      float floatValue2 = floatValue1 / 20.0F;
      float floatValue3 = Math.abs((livingEntity0.getYaw() - 360.0F) % 360.0F);
      float floatValue4 = livingEntity0.getPitch();
      float floatValue5 = Math.min(floatValue2 * 0.7F, 1.67F);
      float[] floatValueArray6 = new float[]{45.0F, 135.0F, 225.0F, 315.0F};
      float floatValue7 = 9999.0F;

      for (float floatValue11 : floatValueArray6) {
         float floatValue12 = Math.abs(floatValue3 - floatValue11);
         if (floatValue12 < floatValue7) {
            floatValue7 = floatValue12;
         }
      }

      float floatValue28 = 1.0F - floatValue7 / 45.0F;
      floatValue28 = Math.max(0.0F, Math.min(1.0F, floatValue28));
      float floatValue30 = c(floatValue4);
      float floatValue31 = floatValue28 * floatValue30;
      float floatValue32 = floatValue5 + (floatValue2 - floatValue5) * floatValue31;
      Vec3d vec3d33 = livingEntity0.getRotationVector();
      Vec3d vec3d13 = Vec3d.fromPolar(floatValue4, livingEntity0.getYaw()).multiply((double)floatValue32);
      float floatValue14 = floatValue4 * (float) (Math.PI / 180.0);
      double doubleValue15 = Math.sqrt(vec3d33.x * vec3d33.x + vec3d33.z * vec3d33.z);
      double doubleValue17 = vec3d13.horizontalLength();
      boolean flag19 = livingEntity0.getVelocity().y <= 0.0;
      double doubleValue20 = flag19 && livingEntity0.hasStatusEffect(StatusEffects.SLOW_FALLING) ? Math.min(livingEntity0.getFinalGravity(), 0.01) : livingEntity0.getFinalGravity();
      double doubleValue22 = MathHelper.square(Math.cos((double)floatValue14));
      vec3d13 = vec3d13.add(0.0, doubleValue20 * (-1.0 + doubleValue22 * 0.75), 0.0);
      if (vec3d13.y < 0.0 && doubleValue15 > 0.0) {
         double doubleValue24 = vec3d13.y * -0.1 * doubleValue22;
         vec3d13 = vec3d13.add(vec3d33.x * doubleValue24 / doubleValue15, doubleValue24, vec3d33.z * doubleValue24 / doubleValue15);
      }

      if (floatValue14 < 0.0F && doubleValue15 > 0.0) {
         double doubleValue35 = doubleValue17 * (double)(-MathHelper.sin(floatValue14)) * 0.04;
         vec3d13 = vec3d13.add(-vec3d33.x * doubleValue35 / doubleValue15, doubleValue35 * 3.2, -vec3d33.z * doubleValue35 / doubleValue15);
      }

      if (doubleValue15 > 0.0) {
         vec3d13 = vec3d13.add((vec3d33.x / doubleValue15 * doubleValue17 - vec3d13.x) * 0.1, 0.0, (vec3d33.z / doubleValue15 * doubleValue17 - vec3d13.z) * 0.1);
      }

      double doubleValue26 = vec3d13.length();
      return new Vec3d(doubleValue26, doubleValue26, doubleValue26).multiply(0.99, 0.98, 0.99);
   }

   public static Vec3d c(LivingEntity livingEntity0, float floatValue1) {
      float floatValue2 = floatValue1 / 20.0F;
      return new Vec3d((double)floatValue2, (double)floatValue2, (double)floatValue2).multiply(0.99, 0.98, 0.99);
   }

   private static float c(float floatValue0) {
      float floatValue1 = Math.abs(floatValue0);
      if (floatValue1 <= 5.0F) {
         return 1.0F;
      } else if (floatValue1 <= 15.0F) {
         return 0.95F;
      } else if (floatValue1 <= 25.0F) {
         return 0.85F;
      } else if (floatValue1 <= 35.0F) {
         return 0.75F;
      } else if (floatValue1 <= 45.0F) {
         return 0.65F;
      } else if (floatValue1 <= 55.0F) {
         return 0.55F;
      } else if (floatValue1 <= 65.0F) {
         return 0.45F;
      } else {
         return floatValue1 <= 75.0F ? 0.35F : 0.25F;
      }
   }

   private PlayerMotion() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
