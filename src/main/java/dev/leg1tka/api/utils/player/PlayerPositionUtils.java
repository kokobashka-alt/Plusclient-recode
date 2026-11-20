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

import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PlayerPositionUtils {
   private static final MinecraftClient a = MinecraftClient.getInstance();

   public PlayerPositionUtils() {
   }

   public static void a(double doubleValue0) {
      if (a.player != null) {
         double doubleValue2 = (double)a.player.input.movementForward;
         double doubleValue4 = (double)a.player.input.movementSideways;
         float floatValue6 = a.player.getYaw();
         if (doubleValue2 == 0.0 && doubleValue4 == 0.0) {
            a.player.setVelocity(0.0, a.player.getVelocity().y, 0.0);
         } else {
            if (doubleValue2 != 0.0) {
               if (doubleValue4 > 0.0) {
                  floatValue6 += (float)(doubleValue2 > 0.0 ? -45 : 45);
               } else if (doubleValue4 < 0.0) {
                  floatValue6 += (float)(doubleValue2 > 0.0 ? 45 : -45);
               }

               doubleValue4 = 0.0;
               if (doubleValue2 > 0.0) {
                  doubleValue2 = 1.0;
               } else if (doubleValue2 < 0.0) {
                  doubleValue2 = -1.0;
               }
            }

            double doubleValue7 = doubleValue2 * doubleValue0 * (double)MathHelper.cos((float)Math.toRadians((double)(floatValue6 + 90.0F)))
               + doubleValue4 * doubleValue0 * (double)MathHelper.sin((float)Math.toRadians((double)(floatValue6 + 90.0F)));
            double doubleValue9 = doubleValue2 * doubleValue0 * (double)MathHelper.sin((float)Math.toRadians((double)(floatValue6 + 90.0F)))
               - doubleValue4 * doubleValue0 * (double)MathHelper.cos((float)Math.toRadians((double)(floatValue6 + 90.0F)));
            a.player.setVelocity(doubleValue7, a.player.getVelocity().y, doubleValue9);
         }
      }
   }

   public static double a() {
      if (a.player == null) {
         return 0.0;
      } else {
         Vec3d vec3d0 = a.player.getVelocity();
         return Math.sqrt(vec3d0.x * vec3d0.x + vec3d0.z * vec3d0.z);
      }
   }

   public static void b(double doubleValue0) {
      double[] doubleValueArray2 = dev.leg1tka.api.utils.player.MovementGeometry.a(doubleValue0);
      Objects.requireNonNull(a.player).setVelocity(doubleValueArray2[0], a.player.getVelocity().getY(), doubleValueArray2[1]);
   }

   public static void a(double doubleValue0, double doubleValue2) {
      double[] doubleValueArray4 = dev.leg1tka.api.utils.player.MovementGeometry.a(doubleValue0);
      Objects.requireNonNull(a.player).setVelocity(doubleValueArray4[0], doubleValue2, doubleValueArray4[1]);
   }

   public static void b() {
      c(a());
   }

   public static void c(double doubleValue0) {
      if (a.player != null) {
         float floatValue2 = a.player.getYaw();
         double doubleValue3 = (double)a.player.input.movementForward;
         double doubleValue5 = (double)a.player.input.movementSideways;
         if (doubleValue3 == 0.0 && doubleValue5 == 0.0) {
            a.player.setVelocity(0.0, a.player.getVelocity().y, 0.0);
         } else {
            if (doubleValue3 != 0.0) {
               if (doubleValue5 > 0.0) {
                  floatValue2 += doubleValue3 > 0.0 ? -45.0F : 45.0F;
               } else if (doubleValue5 < 0.0) {
                  floatValue2 += doubleValue3 > 0.0 ? 45.0F : -45.0F;
               }

               doubleValue5 = 0.0;
               doubleValue3 = doubleValue3 > 0.0 ? 1.0 : -1.0;
            }

            double doubleValue7 = Math.toRadians((double)(floatValue2 + 90.0F));
            double doubleValue9 = doubleValue3 * doubleValue0 * Math.cos(doubleValue7) + doubleValue5 * doubleValue0 * Math.sin(doubleValue7);
            double doubleValue11 = doubleValue3 * doubleValue0 * Math.sin(doubleValue7) - doubleValue5 * doubleValue0 * Math.cos(doubleValue7);
            a.player.setVelocity(doubleValue9, a.player.getVelocity().y, doubleValue11);
         }
      }
   }

   public static boolean c() {
      return a.player == null ? false : a.player.input.movementForward != 0.0F || a.player.input.movementSideways != 0.0F;
   }
}
