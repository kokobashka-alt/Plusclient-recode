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

import dev.leg1tka.api.events.implement.EventMoveInput;
import java.util.Objects;
import lombok.Generated;
import net.minecraft.entity.Entity;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class MovementGeometry implements dev.leg1tka.api.ClientAccess {
   public static boolean a() {
      return minecraftClient.player.input.movementForward != 0.0F || minecraftClient.player.input.movementSideways != 0.0F;
   }

   public static double[] a(double doubleValue0) {
      float floatValue2 = minecraftClient.player.input.movementForward;
      float floatValue3 = minecraftClient.player.input.movementSideways;
      float floatValue4 = minecraftClient.player.getYaw();
      if (floatValue2 != 0.0F) {
         if (floatValue3 > 0.0F) {
            floatValue4 += floatValue2 > 0.0F ? -45.0F : 45.0F;
         } else if (floatValue3 < 0.0F) {
            floatValue4 += floatValue2 > 0.0F ? 45.0F : -45.0F;
         }

         floatValue3 = 0.0F;
         floatValue2 = floatValue2 > 0.0F ? 1.0F : -1.0F;
      }

      double doubleValue5 = Math.sin(Math.toRadians((double)(floatValue4 + 90.0F)));
      double doubleValue7 = Math.cos(Math.toRadians((double)(floatValue4 + 90.0F)));
      double doubleValue9 = (double)floatValue2 * doubleValue0 * doubleValue7 + (double)floatValue3 * doubleValue0 * doubleValue5;
      double doubleValue11 = (double)floatValue2 * doubleValue0 * doubleValue5 - (double)floatValue3 * doubleValue0 * doubleValue7;
      return new double[]{doubleValue9, doubleValue11};
   }

   public static double a(Entity entity0) {
      double doubleValue1 = entity0.getX() - entity0.prevX;
      double doubleValue3 = entity0.getY() - entity0.prevY;
      double doubleValue5 = entity0.getZ() - entity0.prevZ;
      return Math.sqrt(doubleValue1 * doubleValue1 + doubleValue5 * doubleValue5 + doubleValue3 * doubleValue3);
   }

   public static void b(double doubleValue0) {
      double[] doubleValueArray2 = a(doubleValue0);
      Objects.requireNonNull(minecraftClient.player).setVelocity(doubleValueArray2[0], minecraftClient.player.getVelocity().getY(), doubleValueArray2[1]);
   }

   public static void a(double doubleValue0, double doubleValue2) {
      double[] doubleValueArray4 = a(doubleValue0);
      Objects.requireNonNull(minecraftClient.player).setVelocity(doubleValueArray4[0], doubleValue2, doubleValueArray4[1]);
   }

   public static double a(Vec3d vec3d0, float floatValue1) {
      float floatValue2 = (float)Math.atan2(-vec3d0.x, vec3d0.z);
      double doubleValue3 = Math.toRadians((double)MathHelper.wrapDegrees(floatValue1));
      return Math.toDegrees(MathHelper.wrapDegrees((double)floatValue2 - doubleValue3));
   }

   public static PlayerInput a(PlayerInput playerInput0, double doubleValue1, float floatValue3) {
      boolean flag4 = playerInput0.forward();
      boolean flag5 = playerInput0.backward();
      boolean flag6 = playerInput0.left();
      boolean flag7 = playerInput0.right();
      if (doubleValue1 >= (double)(-90.0F + floatValue3) && doubleValue1 <= (double)(90.0F - floatValue3)) {
         flag4 = true;
      } else if (doubleValue1 < (double)(-90.0F - floatValue3) || doubleValue1 > (double)(90.0F + floatValue3)) {
         flag5 = true;
      }

      if (doubleValue1 >= (double)(0.0F + floatValue3) && doubleValue1 <= (double)(180.0F - floatValue3)) {
         flag7 = true;
      } else if (doubleValue1 >= (double)(-180.0F + floatValue3) && doubleValue1 <= (double)(0.0F - floatValue3)) {
         flag6 = true;
      }

      return new PlayerInput(flag4, flag5, flag6, flag7, playerInput0.jump(), playerInput0.sneak(), playerInput0.sprint());
   }

   public static void a(EventMoveInput eventMoveInput0, float floatValue1) {
      float floatValue2 = eventMoveInput0.getForward();
      float floatValue3 = eventMoveInput0.getStrafe();
      if (floatValue2 != 0.0F || floatValue3 != 0.0F) {
         double doubleValue4 = MathHelper.wrapDegrees(Math.toDegrees(a(floatValue1, floatValue2, floatValue3)));
         float floatValue6 = 0.0F;
         float floatValue7 = 0.0F;
         float floatValue8 = Float.MAX_VALUE;

         for (float floatValue9 = -1.0F; floatValue9 <= 1.0F; floatValue9++) {
            for (float floatValue10 = -1.0F; floatValue10 <= 1.0F; floatValue10++) {
               if (floatValue9 != 0.0F || floatValue10 != 0.0F) {
                  double doubleValue11 = MathHelper.wrapDegrees(Math.toDegrees(a(floatValue1, floatValue9, floatValue10)));
                  float floatValue13 = Math.abs(MathHelper.wrapDegrees((float)(doubleValue4 - doubleValue11)));
                  if (floatValue13 < floatValue8) {
                     floatValue8 = floatValue13;
                     floatValue6 = floatValue9;
                     floatValue7 = floatValue10;
                  }
               }
            }
         }

         eventMoveInput0.setForward(floatValue6);
         eventMoveInput0.setStrafe(floatValue7);
      }
   }

   public static void a(EventMoveInput eventMoveInput0) {
      float floatValue1 = eventMoveInput0.getForward();
      float floatValue2 = eventMoveInput0.getStrafe();
      double doubleValue3 = MathHelper.wrapDegrees(Math.toDegrees(a(minecraftClient.player.isGliding() ? minecraftClient.player.getYaw() : dev.leg1tka.rotation.FreeLookStorage.b(), floatValue1, floatValue2)));
      if (floatValue1 != 0.0F || floatValue2 != 0.0F) {
         float floatValue5 = 0.0F;
         float floatValue6 = 0.0F;
         float floatValue7 = Float.MAX_VALUE;

         for (float floatValue8 = -1.0F; floatValue8 <= 1.0F; floatValue8++) {
            for (float floatValue9 = -1.0F; floatValue9 <= 1.0F; floatValue9++) {
               if (floatValue9 != 0.0F || floatValue8 != 0.0F) {
                  double doubleValue10 = MathHelper.wrapDegrees(Math.toDegrees(a(minecraftClient.player.getYaw(), floatValue8, floatValue9)));
                  double doubleValue12 = Math.abs(MathHelper.wrapDegrees(doubleValue3 - doubleValue10));
                  if (doubleValue12 < (double)floatValue7) {
                     floatValue7 = (float)doubleValue12;
                     floatValue5 = floatValue8;
                     floatValue6 = floatValue9;
                  }
               }
            }
         }

         eventMoveInput0.setForward(floatValue5);
         eventMoveInput0.setStrafe(floatValue6);
      }
   }

   public static double a(float floatValue0, float floatValue1, float floatValue2) {
      if (floatValue1 < 0.0F) {
         floatValue0 += 180.0F;
      }

      float floatValue3 = 1.0F;
      if (floatValue1 < 0.0F) {
         floatValue3 = -0.5F;
      }

      if (floatValue1 > 0.0F) {
         floatValue3 = 0.5F;
      }

      if (floatValue2 > 0.0F) {
         floatValue0 -= 90.0F * floatValue3;
      }

      if (floatValue2 < 0.0F) {
         floatValue0 += 90.0F * floatValue3;
      }

      return Math.toRadians((double)floatValue0);
   }

   public static PlayerInput a(PlayerInput playerInput0, double doubleValue1) {
      return a(playerInput0, doubleValue1, 20.0F);
   }

   @Generated
   private MovementGeometry() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
