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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class MathOperations implements dev.leg1tka.api.ClientAccess {
   public static dev.leg1tka.api.utils.math.ThreadLocalRandomAdapter c = new dev.leg1tka.api.utils.math.ThreadLocalRandomAdapter();

   public MathOperations() {
   }

   public static double a(float floatValue0, double doubleValue1, double doubleValue3) {
      if (doubleValue1 < 0.0) {
         floatValue0 += 180.0F;
      }

      float floatValue5 = 1.0F;
      if (doubleValue1 < 0.0) {
         floatValue5 = -0.5F;
      } else if (doubleValue1 > 0.0) {
         floatValue5 = 0.5F;
      }

      if (doubleValue3 > 0.0) {
         floatValue0 -= 90.0F * floatValue5;
      }

      if (doubleValue3 < 0.0) {
         floatValue0 += 90.0F * floatValue5;
      }

      return Math.toRadians((double)floatValue0);
   }

   public static float a(double doubleValue0, double doubleValue2) {
      return doubleValue0 > doubleValue2 ? (float)((double)c.nextFloat() * (doubleValue0 - doubleValue2) + doubleValue2) : (float)((double)c.nextFloat() * (doubleValue2 - doubleValue0) + doubleValue0);
   }

   public static double a(Entity entity0) {
      double doubleValue1 = entity0.getX() - entity0.prevX;
      double doubleValue3 = entity0.getY() - entity0.prevY;
      double doubleValue5 = entity0.getZ() - entity0.prevZ;
      double doubleValue7 = Math.sqrt(doubleValue1 * doubleValue1 + doubleValue3 * doubleValue3 + doubleValue5 * doubleValue5);
      return doubleValue7 * 20.0;
   }

   public static float a() {
      if (minecraftClient.player == null) {
         return 0.0F;
      } else {
         double doubleValue0 = minecraftClient.player.getX() - minecraftClient.player.prevX;
         double doubleValue2 = minecraftClient.player.getY() - minecraftClient.player.prevY;
         double doubleValue4 = minecraftClient.player.getZ() - minecraftClient.player.prevZ;
         double doubleValue6 = Math.sqrt(doubleValue0 * doubleValue0 + doubleValue2 * doubleValue2 + doubleValue4 * doubleValue4);
         float floatValue8 = 1.0F;
         float floatValue9 = (float)(doubleValue6 * (double)floatValue8 * 20.0);
         return (float)Math.round(floatValue9 * 10.0F) / 10.0F;
      }
   }

   public static double b(Entity entity0) {
      double doubleValue1 = 1.5;
      if (entity0 == null) {
         return 1.5;
      } else {
         double doubleValue3 = (double)a();
         double doubleValue5 = 0.00342;
         double doubleValue7 = doubleValue3 * 0.00342;
         return 1.5 + doubleValue7;
      }
   }

   public static float a(float floatValue0, float floatValue1) {
      SecureRandom secureRandom2 = new SecureRandom();
      double doubleValue3 = secureRandom2.nextDouble();
      double doubleValue5 = secureRandom2.nextDouble();
      double doubleValue7 = secureRandom2.nextGaussian() * 0.02F;
      double doubleValue9 = Math.pow(doubleValue3, 1.0 + secureRandom2.nextDouble() * 0.7);
      double doubleValue11 = (doubleValue5 * 0.8 + 0.1) * (Math.log1p(doubleValue3 * 3.0) * 0.5 + 0.5);
      return (float)((double)floatValue0 + (double)(floatValue1 - floatValue0) * doubleValue9 * doubleValue11 + doubleValue7);
   }

   public static float b(float floatValue0, float floatValue1) {
      return (float)ThreadLocalRandom.current().nextDouble((double)floatValue0, (double)floatValue1);
   }

   public static int a(int intValue0, int intValue1) {
      return ThreadLocalRandom.current().nextInt(intValue0, intValue1 + 1);
   }

   public static double b(double doubleValue0, double doubleValue2) {
      return ThreadLocalRandom.current().nextDouble() * (doubleValue2 - doubleValue0) + doubleValue0;
   }

   public static boolean a(double doubleValue0, double doubleValue2, double doubleValue4, double doubleValue6, double doubleValue8, double doubleValue10) {
      return doubleValue8 >= doubleValue0 && doubleValue10 >= doubleValue2 && doubleValue8 < doubleValue0 + doubleValue4 && doubleValue10 < doubleValue2 + doubleValue6;
   }

   public static float a(float floatValue0, float floatValue1, float floatValue2) {
      return floatValue0 + (floatValue1 - floatValue0) * floatValue2;
   }

   public static Vec3d a(Vec3d vec3d0, Vec3d vec3d1, float floatValue2) {
      return new Vec3d(a(vec3d0.getX(), vec3d1.getX(), (double)floatValue2), a(vec3d0.getY(), vec3d1.getY(), (double)floatValue2), a(vec3d0.getZ(), vec3d1.getZ(), (double)floatValue2));
   }

   public static Vec3d a(Entity entity0, float floatValue1) {
      double doubleValue2 = MathHelper.lerp((double)floatValue1, entity0.prevX, entity0.getX());
      double doubleValue4 = MathHelper.lerp((double)floatValue1, entity0.prevY, entity0.getY());
      double doubleValue6 = MathHelper.lerp((double)floatValue1, entity0.prevZ, entity0.getZ());
      return new Vec3d(doubleValue2, doubleValue4, doubleValue6);
   }

   public static double a(double doubleValue0, double doubleValue2, double doubleValue4) {
      return doubleValue2 + (doubleValue0 - doubleValue2) * doubleValue4;
   }

   public static float a(float floatValue0) {
      return (float)Math.round(floatValue0 * 10.0F) / 10.0F;
   }

   public static double c(double doubleValue0, double doubleValue2) {
      double doubleValue4 = (double)Math.round(doubleValue0 / doubleValue2) * doubleValue2;
      BigDecimal bigDecimal6 = new BigDecimal(doubleValue4);
      bigDecimal6 = bigDecimal6.setScale(2, RoundingMode.HALF_UP);
      return bigDecimal6.doubleValue();
   }

   public static float b(float floatValue0, float floatValue1, float floatValue2) {
      return floatValue0 + (floatValue1 - floatValue0) * c(floatValue2, 0.0F, 1.0F);
   }

   public static float c(float floatValue0, float floatValue1, float floatValue2) {
      return floatValue0 <= floatValue1 ? floatValue1 : Math.min(floatValue0, floatValue2);
   }

   public static double b(double doubleValue0, double doubleValue2, double doubleValue4) {
      return Math.max(doubleValue0, Math.min(doubleValue2, doubleValue4));
   }

   public static <T extends Number> T a(T value0, T value1, double doubleValue2) {
      double doubleValue4 = value0.doubleValue();
      double doubleValue6 = value1.doubleValue();
      double doubleValue8 = doubleValue4 + doubleValue2 * (doubleValue6 - doubleValue4);
      if (value0 instanceof Integer) {
         return (T)(Number)(int)Math.round(doubleValue8);
      } else if (value0 instanceof Double) {
         return (T)(Number)doubleValue8;
      } else if (value0 instanceof Float) {
         return (T)(Number)(float)doubleValue8;
      } else if (value0 instanceof Long) {
         return (T)(Number)Math.round(doubleValue8);
      } else if (value0 instanceof Short) {
         return (T)(Number)(short)((int)Math.round(doubleValue8));
      } else if (value0 instanceof Byte) {
         return (T)(Number)(byte)((int)Math.round(doubleValue8));
      } else {
         throw new IllegalArgumentException("Unsupported type: " + value0.getClass().getSimpleName());
      }
   }
}
