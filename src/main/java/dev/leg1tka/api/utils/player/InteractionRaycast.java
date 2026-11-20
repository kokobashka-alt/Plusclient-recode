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

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public final class InteractionRaycast implements dev.leg1tka.api.ClientAccess {
   private static Vec3d c = Vec3d.ZERO;
   private static Vec3d d = Vec3d.ZERO;

   public InteractionRaycast() {
   }

   public static Vec3d a(Entity entity0) {
      Box box1 = entity0.getBoundingBox();
      double doubleValue2 = box1.maxX - box1.minX;
      double doubleValue4 = box1.maxY - box1.minY;
      double doubleValue6 = box1.maxZ - box1.minZ;
      double doubleValue8 = box1.minX + doubleValue2 / 2.0;
      double doubleValue10 = box1.minY + doubleValue4 * 0.7;
      double doubleValue12 = box1.minZ + doubleValue6 / 2.0;
      double doubleValue14 = (double)System.currentTimeMillis() / 50.0;
      int intValue16 = entity0.getId();
      double doubleValue17 = Math.sin(doubleValue14 + (double)intValue16) * doubleValue2 * 0.45;
      double doubleValue19 = Math.cos(doubleValue14 * 0.8 + (double)intValue16) * doubleValue4 * 0.1;
      double doubleValue21 = Math.cos(doubleValue14 * 1.2 + (double)intValue16) * doubleValue6 * 0.45;
      return new Vec3d(doubleValue8 + doubleValue17, doubleValue10 + doubleValue19, doubleValue12 + doubleValue21);
   }

   public static Vec3d a(Entity entity0, double doubleValue1) {
      float floatValue3 = 0.005F;
      float floatValue4 = 0.015F;
      float floatValue5 = 0.0015F;
      float floatValue6 = 0.015F;
      double doubleValue7 = entity0.getBoundingBox().getLengthX();
      double doubleValue9 = entity0.getBoundingBox().getLengthY();
      double doubleValue11 = entity0.getBoundingBox().getLengthZ();
      if (d.equals(Vec3d.ZERO)) {
         d = new Vec3d(a(-0.02, 0.02), a(-0.02, 0.02), a(-0.02, 0.02));
      }

      if (c.equals(Vec3d.ZERO)) {
         c = new Vec3d(0.0, doubleValue9 * 0.5, 0.0);
      }

      c = c.add(d);
      double doubleValue13 = (doubleValue7 - 0.1) / 2.0;
      double doubleValue15 = (doubleValue11 - 0.1) / 2.0;
      if (c.x >= doubleValue13) {
         d = new Vec3d(-a((double)floatValue3, (double)floatValue4), d.getY(), d.getZ());
      } else if (c.x <= -doubleValue13) {
         d = new Vec3d(a((double)floatValue3, (double)floatValue4), d.getY(), d.getZ());
      }

      if (c.y >= doubleValue9 * 0.75) {
         d = new Vec3d(d.getX(), -a((double)floatValue5, (double)floatValue6), d.getZ());
      } else if (c.y <= doubleValue9 * 0.3) {
         d = new Vec3d(d.getX(), a((double)floatValue5, (double)floatValue6), d.getZ());
      }

      if (c.z >= doubleValue15) {
         d = new Vec3d(d.getX(), d.getY(), -a((double)floatValue3, (double)floatValue4));
      } else if (c.z <= -doubleValue15) {
         d = new Vec3d(d.getX(), d.getY(), a((double)floatValue3, (double)floatValue4));
      }

      c = c.add(a(-0.05, 0.05), 0.0, a(-0.05, 0.05));
      Vec3d vec3d17 = entity0.getPos().add(c);
      MinecraftClient minecraftClient18 = MinecraftClient.getInstance();
      if (minecraftClient18.player != null && minecraftClient18.world != null) {
         Vec3d vec3d19 = minecraftClient18.player.getEyePos();
         if (!a(vec3d19, vec3d17, entity0)) {
            Vec3d vec3d20 = a(entity0, vec3d19, doubleValue7, doubleValue9, doubleValue1);
            if (vec3d20 != null) {
               c = vec3d20.subtract(entity0.getPos());
               return vec3d20;
            }
         }
      }

      return vec3d17;
   }

   private static boolean a(Vec3d vec3d0, Vec3d vec3d1, Entity entity2) {
      MinecraftClient minecraftClient3 = MinecraftClient.getInstance();
      if (minecraftClient3.world == null) {
         return true;
      } else {
         BlockHitResult blockHitResult4 = minecraftClient3.world.raycast(new RaycastContext(vec3d0, vec3d1, ShapeType.COLLIDER, FluidHandling.NONE, minecraftClient3.player));
         return blockHitResult4.getType() == Type.MISS
            || entity2.getBoundingBox().contains(blockHitResult4.getPos())
            || vec3d0.squaredDistanceTo(blockHitResult4.getPos()) >= vec3d0.squaredDistanceTo(vec3d1) - 0.01;
      }
   }

   private static Vec3d a(Entity entity0, Vec3d vec3d1, double doubleValue2, double doubleValue4, double doubleValue6) {
      float floatValue8 = (float)(doubleValue2 / 2.0) * 0.8F;

      for (float floatValue9 = -floatValue8; floatValue9 <= floatValue8; floatValue9 += 0.1F) {
         for (float floatValue10 = -floatValue8; floatValue10 <= floatValue8; floatValue10 += 0.1F) {
            for (float floatValue11 = (float)(doubleValue4 * 0.9); (double)floatValue11 >= doubleValue4 * 0.3; floatValue11 -= 0.1F) {
               Vec3d vec3d12 = new Vec3d(entity0.getX() + (double)floatValue9, entity0.getY() + (double)floatValue11, entity0.getZ() + (double)floatValue10);
               if (a(vec3d1, vec3d12, entity0)) {
                  return vec3d12;
               }
            }
         }
      }

      return null;
   }

   private static double a(double doubleValue0, double doubleValue2) {
      return ThreadLocalRandom.current().nextDouble() * (doubleValue2 - doubleValue0) + doubleValue0;
   }
}
