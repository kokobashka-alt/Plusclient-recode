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

import lombok.Generated;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.jetbrains.annotations.NotNull;

public final class RotationRaycast implements dev.leg1tka.api.ClientAccess {
   public static HitResult a(double doubleValue0, float floatValue2, float floatValue3) {
      Vec3d vec3d4 = minecraftClient.player.getCameraPosVec(1.0F);
      Vec3d vec3d5 = a(floatValue3, floatValue2);
      Vec3d vec3d6 = vec3d4.add(vec3d5.x * doubleValue0, vec3d5.y * doubleValue0, vec3d5.z * doubleValue0);
      return minecraftClient.world.raycast(new RaycastContext(vec3d4, vec3d6, ShapeType.OUTLINE, FluidHandling.NONE, minecraftClient.player));
   }

   static Vec3d a(Entity entity0) {
      Vec3d vec3d1 = minecraftClient.player.getEyePos();
      Box box2 = entity0.getBoundingBox();
      double doubleValue3 = 0.1;
      Vec3d vec3d5 = null;
      double doubleValue6 = Double.MAX_VALUE;

      for (double doubleValue8 = box2.minX; doubleValue8 <= box2.maxX; doubleValue8 += doubleValue3) {
         for (double doubleValue10 = box2.minY; doubleValue10 <= box2.maxY; doubleValue10 += doubleValue3) {
            for (double doubleValue12 = box2.minZ; doubleValue12 <= box2.maxZ; doubleValue12 += doubleValue3) {
               Vec3d vec3d14 = new Vec3d(doubleValue8, doubleValue10, doubleValue12);
               double doubleValue15 = vec3d1.distanceTo(vec3d14);
               if (doubleValue15 < doubleValue6) {
                  doubleValue6 = doubleValue15;
                  vec3d5 = vec3d14;
               }
            }
         }
      }

      return vec3d5;
   }

   public static dev.leg1tka.rotation.Rotation a(Vec3d vec3d0) {
      return new dev.leg1tka.rotation.Rotation(
         (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d0.z, vec3d0.x)) - 90.0),
         (float)MathHelper.wrapDegrees(Math.toDegrees(-Math.atan2(vec3d0.y, Math.hypot(vec3d0.x, vec3d0.z))))
      );
   }

   @NotNull
   public static Vec3d a(float floatValue0, float floatValue1) {
      return new Vec3d(
         (double)(MathHelper.sin(-floatValue1 * (float) (Math.PI / 180.0)) * MathHelper.cos(floatValue0 * (float) (Math.PI / 180.0))),
         (double)(-MathHelper.sin(floatValue0 * (float) (Math.PI / 180.0))),
         (double)(MathHelper.cos(-floatValue1 * (float) (Math.PI / 180.0)) * MathHelper.cos(floatValue0 * (float) (Math.PI / 180.0)))
      );
   }

   public static Vec2f b(Entity entity0) {
      return a(entity0.getX(), entity0.getY(), entity0.getZ());
   }

   public static Vec2f b(Vec3d vec3d0) {
      return a(vec3d0.x, vec3d0.y, vec3d0.z);
   }

   public static Vec2f a(double doubleValue0, double doubleValue2, double doubleValue4) {
      double doubleValue6 = doubleValue0 - minecraftClient.player.getX();
      double doubleValue8 = doubleValue2 - minecraftClient.player.getEyeY();
      double doubleValue10 = doubleValue4 - minecraftClient.player.getZ();
      double doubleValue12 = (double)MathHelper.sqrt((float)(doubleValue6 * doubleValue6 + doubleValue10 * doubleValue10));
      float floatValue14 = (float)(MathHelper.atan2(doubleValue10, doubleValue6) * (180.0 / Math.PI) - 90.0);
      float floatValue15 = (float)(-MathHelper.atan2(doubleValue8, doubleValue12) * (180.0 / Math.PI));
      return new Vec2f(floatValue14, floatValue15);
   }

   public static float[] a(Direction direction0) {
      return switch (direction0) {
         case DOWN -> new float[]{minecraftClient.player.getYaw(), 90.0F};
         case UP -> new float[]{minecraftClient.player.getYaw(), -90.0F};
         case NORTH -> new float[]{180.0F, minecraftClient.player.getPitch()};
         case SOUTH -> new float[]{0.0F, minecraftClient.player.getPitch()};
         case WEST -> new float[]{90.0F, minecraftClient.player.getPitch()};
         case EAST -> new float[]{-90.0F, minecraftClient.player.getPitch()};
         default -> throw new MatchException(null, null);
      };
   }

   public static float[] a(float[] floatValueArray0) {
      floatValueArray0[0] -= floatValueArray0[0] % dev.leg1tka.rotation.RotationContext.b();
      floatValueArray0[1] -= floatValueArray0[1] % dev.leg1tka.rotation.RotationContext.b();
      return new float[]{floatValueArray0[0], floatValueArray0[1]};
   }

   public static float a(float floatValue0) {
      return b(floatValue0) * dev.leg1tka.rotation.RotationContext.b();
   }

   public static float b(float floatValue0) {
      return (float)Math.round(floatValue0 / dev.leg1tka.rotation.RotationContext.b());
   }

   @Generated
   private RotationRaycast() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
