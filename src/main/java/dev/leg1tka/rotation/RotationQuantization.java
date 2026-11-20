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

import lombok.Generated;
import net.minecraft.util.math.MathHelper;

public final class RotationQuantization implements dev.leg1tka.api.ClientAccess {
   public static double a() {
      int[] intValueArray1 = new int[]{-45, 45, 135, -135};
      int[] intValueArray2 = new int[]{-90, 90, 180, -180, 0};
      int[] intValueArray3 = new int[]{-45, 45};
      float floatValue4 = minecraftClient.player.prevYaw;
      float floatValue5 = minecraftClient.player.prevPitch;
      int intValue6 = a(floatValue4, intValueArray1);
      float floatValue7 = Math.abs(MathHelper.wrapDegrees(floatValue4) - (float)intValueArray1[intValue6]);
      int intValue8 = a(floatValue4, intValueArray2);
      float floatValue9 = Math.abs(MathHelper.wrapDegrees(floatValue4) - (float)intValueArray2[intValue8]);
      float floatValue0 = intValue6 == -1 ? 1.5F : 1.95F - floatValue7 * 0.56F / 45.0F;
      if (floatValue9 < 10.0F) {
         floatValue0 += 0.1F - 0.1F * floatValue9 / 10.0F;
      }

      int intValue10 = a(floatValue5, intValueArray3);
      float floatValue11 = Math.abs(Math.abs(floatValue5) - (float)Math.abs(intValueArray3[intValue10]));
      if (floatValue11 < 26.0F) {
         floatValue0 = Math.max(1.94F, floatValue0);
         floatValue0 += 0.05F - floatValue11 * 0.05F / 26.0F;
      }

      floatValue0 = Math.min(2.045F, floatValue0);
      if (minecraftClient.player.prevPitch > -55.0F && minecraftClient.player.prevPitch < -19.0F) {
         floatValue0 = 1.91F;
      } else if (minecraftClient.player.prevPitch < -55.0F) {
         floatValue0 = 1.54F;
      }

      if (minecraftClient.player.prevPitch > 19.0F && minecraftClient.player.prevPitch < 55.0F) {
         floatValue0 = 1.8F;
      } else if (minecraftClient.player.prevPitch > 55.0F) {
         floatValue0 = 1.54F;
      }

      return (double)floatValue0;
   }

   private static int a(float floatValue0, int[] intValueArray1) {
      int intValue2 = 0;
      int intValue3 = -1;
      float floatValue4 = Float.MAX_VALUE;

      for (int intValue8 : intValueArray1) {
         float floatValue9 = Math.abs(MathHelper.wrapDegrees(floatValue0) - (float)intValue8);
         if (floatValue9 < floatValue4) {
            floatValue4 = floatValue9;
            intValue3 = intValue2;
         }

         intValue2++;
      }

      return intValue3;
   }

   @Generated
   private RotationQuantization() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
