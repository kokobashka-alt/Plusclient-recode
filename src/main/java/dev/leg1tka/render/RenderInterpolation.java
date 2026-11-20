package dev.leg1tka.render;

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

public final class RenderInterpolation implements dev.leg1tka.api.ClientAccess {
   private static float c;
   private static float d;
   private static double e;
   private static double f;
   private static double g;
   private static boolean h;
   private static float i;
   private static float j;
   private static float k;

   private RenderInterpolation() {
   }

   public static void a() {
      if (minecraftClient != null && minecraftClient.player != null) {
         float floatValue0 = minecraftClient.player.getYaw();
         float floatValue1 = minecraftClient.player.getPitch();
         double doubleValue2 = minecraftClient.player.getX();
         double doubleValue4 = minecraftClient.player.getY();
         double doubleValue6 = minecraftClient.player.getZ();
         if (!h) {
            c = floatValue0;
            d = floatValue1;
            e = doubleValue2;
            f = doubleValue4;
            g = doubleValue6;
            h = true;
         } else {
            float floatValue8 = MathHelper.wrapDegrees(floatValue0 - c);
            float floatValue9 = floatValue1 - d;
            double doubleValue10 = doubleValue2 - e;
            double doubleValue12 = doubleValue4 - f;
            double doubleValue14 = doubleValue6 - g;
            float floatValue16 = (float)Math.sqrt((double)(floatValue8 * floatValue8 + floatValue9 * floatValue9));
            float floatValue17 = (float)Math.sqrt(doubleValue10 * doubleValue10 + doubleValue12 * doubleValue12 + doubleValue14 * doubleValue14) * 20.0F;
            float floatValue18 = MathHelper.clamp(floatValue16 * 0.055F + floatValue17 * 0.42F, 0.0F, 1.35F);
            i = i * 0.78F + floatValue18 * 0.22F;
            i = MathHelper.clamp(i, 0.0F, 1.0F);
            float floatValue19 = floatValue8 * 4.2E-4F + (float)doubleValue10 * 0.0018F;
            float floatValue20 = -floatValue9 * 4.2E-4F + (float)doubleValue12 * 0.0024F - (float)doubleValue14 * 6.0E-4F;
            j = j * 0.72F + floatValue19 * 0.28F;
            k = k * 0.72F + floatValue20 * 0.28F;
            c = floatValue0;
            d = floatValue1;
            e = doubleValue2;
            f = doubleValue4;
            g = doubleValue6;
         }
      } else {
         i *= 0.9F;
         j *= 0.85F;
         k *= 0.85F;
      }
   }

   public static float b() {
      return i;
   }

   public static float c() {
      return j;
   }

   public static float d() {
      return k;
   }

   public static float e() {
      return 1.0F + i * 3.2F;
   }
}
