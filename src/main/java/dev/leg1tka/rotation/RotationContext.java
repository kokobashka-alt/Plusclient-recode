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

public class RotationContext implements dev.leg1tka.api.ClientAccess {
   public RotationContext() {
   }

   public static float a(float floatValue0) {
      double doubleValue1 = minecraftClient != null && minecraftClient.options != null ? (Double)minecraftClient.options.getMouseSensitivity().getValue() : 0.5;
      double doubleValue3 = doubleValue1 * 0.6F + 0.2F;
      double doubleValue5 = doubleValue3 * doubleValue3 * doubleValue3 * 8.0;
      double doubleValue7 = doubleValue5 * 0.15;
      if (doubleValue7 < 1.0E-6) {
         return floatValue0;
      } else {
         int intValue9 = (int)Math.round((double)floatValue0 / doubleValue7);
         return (float)((double)intValue9 * doubleValue5) * 0.15F;
      }
   }

   public static float a(int intValue0) {
      double doubleValue1 = minecraftClient != null && minecraftClient.options != null ? (Double)minecraftClient.options.getMouseSensitivity().getValue() : 0.5;
      double doubleValue3 = doubleValue1 * 0.6F + 0.2F;
      double doubleValue5 = doubleValue3 * doubleValue3 * doubleValue3 * 8.0;
      return (float)((double)intValue0 * doubleValue5) * 0.15F;
   }

   public static int b(float floatValue0) {
      double doubleValue1 = a();
      return doubleValue1 < 1.0E-6 ? 0 : (int)Math.round((double)floatValue0 / doubleValue1);
   }

   public static double a() {
      double doubleValue0 = minecraftClient != null && minecraftClient.options != null ? (Double)minecraftClient.options.getMouseSensitivity().getValue() : 0.5;
      double doubleValue2 = doubleValue0 * 0.6F + 0.2F;
      double doubleValue4 = doubleValue2 * doubleValue2 * doubleValue2 * 8.0;
      return doubleValue4 * 0.15;
   }

   public static float b() {
      return (float)a();
   }

   public static float c() {
      double doubleValue0 = minecraftClient != null && minecraftClient.options != null ? (Double)minecraftClient.options.getMouseSensitivity().getValue() : 0.5;
      double doubleValue2 = doubleValue0 * 0.6F + 0.2F;
      return (float)(doubleValue2 * doubleValue2 * doubleValue2 * 8.0);
   }

   public static float c(float floatValue0) {
      double doubleValue1 = a();
      return doubleValue1 < 1.0E-6 ? 0.0F : (float)Math.round((double)floatValue0 / doubleValue1);
   }

   public static float a(float floatValue0, float floatValue1) {
      return floatValue1 + a(floatValue0 - floatValue1);
   }
}
