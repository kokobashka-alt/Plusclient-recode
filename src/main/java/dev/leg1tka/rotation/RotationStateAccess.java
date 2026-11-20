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

public final class RotationStateAccess implements dev.leg1tka.api.ClientAccess {
   private float c;
   private float d;

   public RotationStateAccess() {
   }

   public void a() {
      this.c = 0.0F;
      this.d = 0.0F;
   }

   public float a(float floatValue1) {
      float floatValue2 = b();
      if (floatValue2 < 1.0E-6F) {
         return floatValue1;
      } else {
         float floatValue3 = floatValue1 + this.c;
         int intValue4 = Math.round(floatValue3 / floatValue2);
         float floatValue5 = (float)intValue4 * floatValue2;
         this.c = floatValue3 - floatValue5;
         return floatValue5;
      }
   }

   public float b(float floatValue1) {
      float floatValue2 = b();
      if (floatValue2 < 1.0E-6F) {
         return floatValue1;
      } else {
         float floatValue3 = floatValue1 + this.d;
         int intValue4 = Math.round(floatValue3 / floatValue2);
         float floatValue5 = (float)intValue4 * floatValue2;
         this.d = floatValue3 - floatValue5;
         return floatValue5;
      }
   }

   private static float b() {
      double doubleValue0 = minecraftClient != null && minecraftClient.options != null ? (Double)minecraftClient.options.getMouseSensitivity().getValue() : 0.5;
      double doubleValue2 = doubleValue0 * 0.6F + 0.2F;
      return (float)(doubleValue2 * doubleValue2 * doubleValue2 * 8.0 * 0.15);
   }
}
