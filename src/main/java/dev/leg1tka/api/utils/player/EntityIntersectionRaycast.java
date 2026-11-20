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

import java.util.function.Predicate;
import lombok.Generated;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public final class EntityIntersectionRaycast implements dev.leg1tka.api.ClientAccess {
   private static boolean a(Vec3d vec3d0, Vec3d vec3d1, BlockHitResult blockHitResult2) {
      if (blockHitResult2 == null || blockHitResult2.getType() != Type.BLOCK) {
         return false;
      } else if (minecraftClient.world == null) {
         return false;
      } else {
         BlockState blockState3 = minecraftClient.world.getBlockState(blockHitResult2.getBlockPos());
         if (blockState3 == null) {
            return false;
         } else if (!blockState3.getCollisionShape(minecraftClient.world, blockHitResult2.getBlockPos()).isEmpty()) {
            Vec3d vec3d4 = blockHitResult2.getPos();
            return vec3d4.squaredDistanceTo(vec3d0) < vec3d1.squaredDistanceTo(vec3d0);
         } else {
            return false;
         }
      }
   }

   public static BlockHitResult a(dev.leg1tka.rotation.Rotation value0, double doubleValue1, boolean flag3, float floatValue4) {
      return a(doubleValue1, flag3, minecraftClient.player.getCameraPosVec(floatValue4), value0.asDirectionVector(), minecraftClient.cameraEntity);
   }

   public static BlockHitResult a(double doubleValue0, boolean flag2, Vec3d vec3d3, Vec3d vec3d4, Entity entity5) {
      Vec3d vec3d6 = vec3d3.add(vec3d4.x * doubleValue0, vec3d4.y * doubleValue0, vec3d4.z * doubleValue0);
      return minecraftClient.world.raycast(new RaycastContext(vec3d3, vec3d6, ShapeType.OUTLINE, flag2 ? FluidHandling.ANY : FluidHandling.NONE, entity5));
   }

   public static EntityHitResult a(double doubleValue0, dev.leg1tka.rotation.Rotation value2, boolean flag3) {
      return a(doubleValue0, value2, flag3, entity0x -> true, 0.0F);
   }

   public static EntityHitResult a(double doubleValue0, dev.leg1tka.rotation.Rotation value2, boolean flag3, float floatValue4) {
      return a(doubleValue0, value2, flag3, entity0x -> true, floatValue4);
   }

   public static EntityHitResult a(double doubleValue0, dev.leg1tka.rotation.Rotation value2, boolean flag3, Predicate<Entity> predicate4, float floatValue5) {
      Entity entity6 = minecraftClient.getCameraEntity();
      if (entity6 == null) {
         return null;
      } else {
         Vec3d vec3d7 = entity6.getEyePos();
         Vec3d vec3d8 = value2.asDirectionVector();
         Vec3d vec3d9 = vec3d7.add(vec3d8.x * doubleValue0, vec3d8.y * doubleValue0, vec3d8.z * doubleValue0);
         Box box10 = entity6.getBoundingBox().stretch(vec3d8.multiply(doubleValue0)).expand(1.0, 1.0, 1.0);
         EntityHitResult entityHitResult11 = ProjectileUtil.raycast(
            entity6, vec3d7, vec3d9, box10, entity1 -> entity1.canHit() && entity1.isAlive() && !entity1.isSpectator() && predicate4.test(entity1), doubleValue0 * doubleValue0
         );
         if (entityHitResult11 == null) {
            return null;
         } else if (flag3) {
            return entityHitResult11;
         } else {
            Vec3d vec3d12 = entityHitResult11.getEntity().getBoundingBox().raycast(vec3d7, vec3d9).orElse(entityHitResult11.getEntity().getPos());
            BlockHitResult blockHitResult13 = minecraftClient.world != null ? minecraftClient.world.raycast(new RaycastContext(vec3d7, vec3d12, ShapeType.COLLIDER, FluidHandling.ANY, minecraftClient.player)) : null;
            return a(vec3d7, vec3d12, blockHitResult13) ? null : entityHitResult11;
         }
      }
   }

   @Generated
   private EntityIntersectionRaycast() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
