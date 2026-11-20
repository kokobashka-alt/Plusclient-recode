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
import java.util.concurrent.ThreadLocalRandom;
import lombok.Generated;
import net.minecraft.util.math.MathHelper;

public final class NumericFormats {
   public static float a(float floatValue0) {
      return (float)a(0.0, 1.0, (double)floatValue0);
   }

   public static double a(double doubleValue0, double doubleValue2) {
      if (doubleValue0 == doubleValue2) {
         return doubleValue0;
      } else {
         if (doubleValue0 > doubleValue2) {
            double doubleValue4 = doubleValue0;
            doubleValue0 = doubleValue2;
            doubleValue2 = doubleValue4;
         }

         return ThreadLocalRandom.current().nextDouble() * (doubleValue2 - doubleValue0) + doubleValue0;
      }
   }

   public static float a(float floatValue0, float floatValue1) {
      return floatValue0 - floatValue1;
   }

   public static double a(double doubleValue0, int intValue2) {
      double doubleValue3 = Math.pow(10.0, (double)intValue2);
      return (double)Math.round(doubleValue0 * doubleValue3) / doubleValue3;
   }

   public static Number b(double doubleValue0, double doubleValue2) {
      if (doubleValue2 <= 0.0) {
         throw new IllegalArgumentException("Increment must be greater than zero");
      } else {
         double doubleValue4 = (double)Math.round(doubleValue0 / doubleValue2) * doubleValue2;
         BigDecimal bigDecimal6 = BigDecimal.valueOf(doubleValue4);
         bigDecimal6 = bigDecimal6.setScale(2, RoundingMode.HALF_UP);
         return bigDecimal6.doubleValue();
      }
   }

   public static String a(long longValue0) {
      long longValue2 = longValue0 / 3600000L;
      long longValue4 = longValue0 % 3600000L / 60000L;
      long longValue6 = longValue0 % 360000L % 60000L / 1000L;
      return String.format("%02d:%02d:%02d", longValue2, longValue4, longValue6);
   }

   public static float a(float floatValue0, float floatValue1, float floatValue2) {
      floatValue2 = Math.max(0.0F, Math.min(1.0F, floatValue2));
      float floatValue3 = (float)Math.toRadians((double)floatValue0);
      float floatValue4 = (float)Math.toRadians((double)floatValue1);
      float floatValue5 = (float)Math.cos((double)floatValue3) * (float)Math.cos((double)floatValue4) + (float)Math.sin((double)floatValue3) * (float)Math.sin((double)floatValue4);
      float floatValue6 = (float)Math.acos((double)floatValue5);
      if (Math.abs(floatValue6) < 0.001F) {
         return floatValue0;
      } else {
         float floatValue7 = (float)(Math.sin((double)((1.0F - floatValue2) * floatValue6)) / Math.sin((double)floatValue6));
         float floatValue8 = (float)(Math.sin((double)(floatValue2 * floatValue6)) / Math.sin((double)floatValue6));
         float floatValue9 = floatValue0 * floatValue7 + floatValue1 * floatValue8;
         return (float)MathHelper.clamp(MathHelper.wrapDegrees(Math.toDegrees((double)floatValue9)), (double)floatValue0, (double)floatValue1);
      }
   }

   public static double a(double doubleValue0, int intValue2, double doubleValue3) {
      double doubleValue5 = doubleValue3 / 2.0;
      double doubleValue7 = Math.floor(doubleValue0 / doubleValue3) * doubleValue3;
      return doubleValue0 >= doubleValue7 + doubleValue5
         ? new BigDecimal(Math.ceil(doubleValue0 / doubleValue3) * doubleValue3).setScale(intValue2, RoundingMode.HALF_UP).doubleValue()
         : new BigDecimal(doubleValue7).setScale(intValue2, RoundingMode.HALF_UP).doubleValue();
   }

   public static double c(double doubleValue0, double doubleValue2) {
      double doubleValue4 = (double)Math.round(doubleValue0 / doubleValue2) * doubleValue2;
      doubleValue4 *= 1000.0;
      doubleValue4 = (double)((int)doubleValue4);
      return doubleValue4 / 1000.0;
   }

   public static double a(double doubleValue0, double doubleValue2, double doubleValue4, double doubleValue6, double doubleValue8, double doubleValue10) {
      double doubleValue12 = doubleValue6 - doubleValue0;
      double doubleValue14 = doubleValue8 - doubleValue2;
      double doubleValue16 = doubleValue10 - doubleValue4;
      return (double)MathHelper.sqrt((float)(doubleValue12 * doubleValue12 + doubleValue14 * doubleValue14 + doubleValue16 * doubleValue16));
   }

   public static double a(double doubleValue0, double doubleValue2, double doubleValue4) {
      return Math.max(doubleValue0, Math.min(doubleValue2, doubleValue4));
   }

   public static int a(int intValue0, int intValue1, int intValue2) {
      return Math.max(intValue0, Math.min(intValue1, intValue2));
   }

   public static float b(float floatValue0, float floatValue1, float floatValue2) {
      return (floatValue0 - floatValue1) / (floatValue2 - floatValue1);
   }

   public static double b(double doubleValue0, double doubleValue2, double doubleValue4) {
      return doubleValue2 + doubleValue0 * (doubleValue4 - doubleValue2);
   }

   public static float c(float floatValue0, float floatValue1, float floatValue2) {
      return floatValue0 + (floatValue1 - floatValue0) * floatValue2;
   }

   public static float b(float floatValue0) {
      return floatValue0 == 1.0F ? 1.0F : (float)(1.0 - Math.pow(2.0, (double)(-10.0F * floatValue0)));
   }

   @Generated
   private NumericFormats() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
