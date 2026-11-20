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
import lombok.Generated;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.joml.Vector3f;

public final class EntityRaycast implements dev.leg1tka.api.ClientAccess {
   public static HitResult a(double doubleValue0, float floatValue2, float floatValue3, Entity entity4) {
      Vec3d vec3d5 = minecraftClient.player.getEyePos();
      Vec3d vec3d6 = b(floatValue3, floatValue2);
      Vec3d vec3d7 = vec3d5.add(vec3d6.x * doubleValue0, vec3d6.y * doubleValue0, vec3d6.z * doubleValue0);
      return minecraftClient.world.raycast(new RaycastContext(vec3d5, vec3d7, ShapeType.OUTLINE, FluidHandling.NONE, entity4));
   }

   public static BlockHitResult a(Vec3d vec3d0, Vec3d vec3d1, ShapeType shapeType2) {
      return a(vec3d0, vec3d1, shapeType2, minecraftClient.player);
   }

   public static BlockHitResult a(Vec3d vec3d0, Vec3d vec3d1, ShapeType shapeType2, Entity entity3) {
      return minecraftClient.world.raycast(new RaycastContext(vec3d0, vec3d1, shapeType2, FluidHandling.NONE, entity3));
   }

   public static boolean a(Vec3d vec3d0, double doubleValue1, Box box3) {
      Vec3d vec3d4 = Objects.requireNonNull(minecraftClient.player).getEyePos();
      return box3.contains(vec3d4) || box3.raycast(vec3d4, vec3d4.add(vec3d0.multiply(doubleValue1))).isPresent();
   }

   public static boolean a(LivingEntity livingEntity0, float floatValue1, float floatValue2, float floatValue3, boolean flag4) {
      Entity entity5 = minecraftClient.getCameraEntity();
      if (entity5 != null && minecraftClient.world != null) {
         double doubleValue6 = (double)(floatValue3 * floatValue3);
         Vec3d vec3d8 = entity5.getEyePos();
         Vector3f vector3f9 = a(floatValue1, floatValue2);
         vector3f9.mul(floatValue3, floatValue3, floatValue3);
         Vec3d vec3d10 = vec3d8.add((double)vector3f9.x, (double)vector3f9.y, (double)vector3f9.z);
         Box box11 = livingEntity0.getBoundingBox();
         EntityHitResult entityHitResult12 = ProjectileUtil.raycast(entity5, vec3d8, vec3d10, box11, entity1x -> !entity1x.isSpectator() && entity1x.isAlive() && entity1x == livingEntity0, doubleValue6);
         return entityHitResult12 != null;
      } else {
         return false;
      }
   }

   public static Vector3f a(float floatValue0, float floatValue1) {
      float floatValue2 = floatValue1 * (float) (Math.PI / 180.0);
      float floatValue3 = -floatValue0 * (float) (Math.PI / 180.0);
      float floatValue4 = MathHelper.cos(floatValue3);
      float floatValue5 = MathHelper.sin(floatValue3);
      float floatValue6 = MathHelper.cos(floatValue2);
      float floatValue7 = MathHelper.sin(floatValue2);
      return new Vector3f(floatValue5 * floatValue6, -floatValue7, floatValue4 * floatValue6);
   }

   public static Vec3d b(float floatValue0, float floatValue1) {
      float floatValue2 = -floatValue1 * (float) (Math.PI / 180.0) - (float) Math.PI;
      float floatValue3 = -floatValue0 * (float) (Math.PI / 180.0);
      float floatValue4 = MathHelper.cos(floatValue2);
      float floatValue5 = MathHelper.sin(floatValue2);
      float floatValue6 = -MathHelper.cos(floatValue3);
      float floatValue7 = MathHelper.sin(floatValue3);
      return new Vec3d((double)(floatValue5 * floatValue6), (double)floatValue7, (double)(floatValue4 * floatValue6));
   }

   public static boolean a(float floatValue0, float floatValue1, double doubleValue2, Entity entity4) {
      Vec3d vec3d5 = minecraftClient.player.getEyePos();
      Vec3d vec3d6 = minecraftClient.player.getRotationVector(floatValue1, floatValue0);
      Vec3d vec3d7 = vec3d5.add(vec3d6.multiply(doubleValue2));
      Box box8 = entity4.getBoundingBox();
      return box8.contains(vec3d5) || box8.raycast(vec3d5, vec3d7).isPresent();
   }

   @Generated
   private EntityRaycast() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
