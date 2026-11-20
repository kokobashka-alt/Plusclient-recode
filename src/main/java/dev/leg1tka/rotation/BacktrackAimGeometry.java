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
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext.ShapeType;

public final class BacktrackAimGeometry implements dev.leg1tka.api.ClientAccess {
   private static Vec3d c = Vec3d.ZERO;
   private static Vec3d d = Vec3d.ZERO;

   public static Vec3d a() {
      return c;
   }

   public static Box a(Entity entity0) {
      return BackTrackOld.c.a(entity0);
   }

   public static Vec3d b(Entity entity0) {
      Box box1 = a(entity0);
      double doubleValue2 = 0.1;
      Vec3d vec3d4 = null;
      double doubleValue5 = Double.MAX_VALUE;

      for (double doubleValue7 = box1.minX; doubleValue7 <= box1.maxX; doubleValue7 += doubleValue2) {
         for (double doubleValue9 = box1.minY; doubleValue9 <= box1.maxY; doubleValue9 += doubleValue2) {
            for (double doubleValue11 = box1.minZ; doubleValue11 <= box1.maxZ; doubleValue11 += doubleValue2) {
               Vec3d vec3d13 = new Vec3d(doubleValue7, doubleValue9, doubleValue11);
               double doubleValue14 = minecraftClient.player.getEyePos().distanceTo(vec3d13);
               if (doubleValue14 < doubleValue5) {
                  doubleValue5 = doubleValue14;
                  vec3d4 = vec3d13;
               }
            }
         }
      }

      return vec3d4;
   }

   public static Vec3d c(Entity entity0) {
      Box box1 = a(entity0);
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

   public static Vec3d d(Entity entity0) {
      Box box1 = a(entity0);
      double doubleValue2 = box1.maxX - box1.minX;
      double doubleValue4 = box1.maxY - box1.minY;
      double doubleValue6 = box1.maxZ - box1.minZ;
      double doubleValue8 = box1.minX + doubleValue2 / 2.0;
      double doubleValue10 = box1.minY + doubleValue4 * 0.65;
      double doubleValue12 = box1.minZ + doubleValue6 / 2.0;
      double doubleValue14 = (double)System.currentTimeMillis() / 65.0;
      int intValue16 = entity0.getId();
      double doubleValue17 = Math.sin(doubleValue14 + (double)intValue16) * doubleValue2 * 0.7;
      double doubleValue19 = Math.cos(doubleValue14 * 0.8 + (double)intValue16) * doubleValue4 * 0.4;
      double doubleValue21 = Math.cos(doubleValue14 * 1.2 + (double)intValue16) * doubleValue6 * 0.7;
      return new Vec3d(doubleValue8 + doubleValue17, doubleValue10 + doubleValue19, doubleValue12 + doubleValue21);
   }

   public static Vec3d a(Entity entity0, Vec3d vec3d1, double doubleValue2) {
      if (vec3d1 != null && minecraftClient.player != null && minecraftClient.world != null) {
         if (b(entity0, vec3d1, doubleValue2)) {
            return vec3d1;
         } else {
            Box box4 = a(entity0);
            double doubleValue5 = 0.12;
            Vec3d vec3d7 = null;
            double doubleValue8 = Double.MAX_VALUE;

            for (double doubleValue10 = box4.minX; doubleValue10 <= box4.maxX; doubleValue10 += doubleValue5) {
               for (double doubleValue12 = box4.minY; doubleValue12 <= box4.maxY; doubleValue12 += doubleValue5) {
                  for (double doubleValue14 = box4.minZ; doubleValue14 <= box4.maxZ; doubleValue14 += doubleValue5) {
                     Vec3d vec3d16 = new Vec3d(doubleValue10, doubleValue12, doubleValue14);
                     if (b(entity0, vec3d16, doubleValue2)) {
                        double doubleValue17 = vec3d16.squaredDistanceTo(vec3d1);
                        if (doubleValue17 < doubleValue8) {
                           doubleValue8 = doubleValue17;
                           vec3d7 = vec3d16;
                        }
                     }
                  }
               }
            }

            return vec3d7 != null ? vec3d7 : vec3d1;
         }
      } else {
         return vec3d1;
      }
   }

   private static boolean b(Entity entity0, Vec3d vec3d1, double doubleValue2) {
      Vec3d vec3d4 = minecraftClient.player.getEyePos();
      double doubleValue5 = vec3d4.distanceTo(vec3d1);
      if (doubleValue5 > doubleValue2) {
         return false;
      } else {
         Vec3d vec3d7 = vec3d1.subtract(vec3d4).normalize();
         if (!dev.leg1tka.api.utils.player.EntityRaycast.a(vec3d7, doubleValue5 + 0.2, a(entity0))) {
            return false;
         } else {
            BlockHitResult blockHitResult8 = dev.leg1tka.api.utils.player.EntityRaycast.a(vec3d4, vec3d1, ShapeType.COLLIDER, minecraftClient.player);
            return blockHitResult8.getType() == Type.MISS || vec3d4.squaredDistanceTo(blockHitResult8.getPos()) >= vec3d4.squaredDistanceTo(vec3d1) - 1.0E-4;
         }
      }
   }

   public static Vec3d a(Entity entity0, double doubleValue1) {
      float floatValue3 = 0.005F;
      float floatValue4 = 0.015F;
      float floatValue5 = 0.0015F;
      float floatValue6 = 0.015F;
      Box box7 = a(entity0);
      double doubleValue8 = box7.getLengthX();
      double doubleValue10 = box7.getLengthY();
      double doubleValue12 = box7.getLengthZ();
      if (d.equals(Vec3d.ZERO)) {
         d = new Vec3d(dev.leg1tka.api.utils.math.MathOperations.b(-0.02F, 0.02F), dev.leg1tka.api.utils.math.MathOperations.b(-0.02F, 0.02F), dev.leg1tka.api.utils.math.MathOperations.b(-0.02F, 0.02F));
      }

      if (c.equals(Vec3d.ZERO)) {
         c = new Vec3d(0.0, doubleValue10 * 0.5, 0.0);
      }

      c = c.add(d);
      double doubleValue14 = (doubleValue8 - 0.1) / 2.0;
      double doubleValue16 = (doubleValue12 - 0.1) / 2.0;
      if (c.x >= doubleValue14) {
         d = new Vec3d(-dev.leg1tka.api.utils.math.MathOperations.b((double)floatValue3, (double)floatValue4), d.getY(), d.getZ());
      } else if (c.x <= -doubleValue14) {
         d = new Vec3d(dev.leg1tka.api.utils.math.MathOperations.b((double)floatValue3, (double)floatValue4), d.getY(), d.getZ());
      }

      if (c.y >= doubleValue10 * 0.75) {
         d = new Vec3d(d.getX(), -dev.leg1tka.api.utils.math.MathOperations.b((double)floatValue5, (double)floatValue6), d.getZ());
      } else if (c.y <= doubleValue10 * 0.3) {
         d = new Vec3d(d.getX(), dev.leg1tka.api.utils.math.MathOperations.b((double)floatValue5, (double)floatValue6), d.getZ());
      }

      if (c.z >= doubleValue16) {
         d = new Vec3d(d.getX(), d.getY(), -dev.leg1tka.api.utils.math.MathOperations.b((double)floatValue3, (double)floatValue4));
      } else if (c.z <= -doubleValue16) {
         d = new Vec3d(d.getX(), d.getY(), dev.leg1tka.api.utils.math.MathOperations.b((double)floatValue3, (double)floatValue4));
      }

      c.add(dev.leg1tka.api.utils.math.MathOperations.b(-0.05F, 0.05F), 0.0, dev.leg1tka.api.utils.math.MathOperations.b(-0.05F, 0.05F));
      if (!dev.leg1tka.api.utils.player.EntityRaycast.a(minecraftClient.player.getRotationVector(), doubleValue1, box7)) {
         float floatValue19 = (float)(doubleValue8 / 2.0) * 0.8F;

         for (float floatValue20 = -floatValue19; floatValue20 <= floatValue19; floatValue20 += 0.1F) {
            for (float floatValue21 = -floatValue19; floatValue21 <= floatValue19; floatValue21 += 0.1F) {
               for (float floatValue22 = (float)(doubleValue10 * 0.9); (double)floatValue22 >= doubleValue10 * 0.3; floatValue22 -= 0.1F) {
                  Vec3d vec3d23 = new Vec3d(box7.getCenter().x + (double)floatValue20, box7.minY + (double)floatValue22, box7.getCenter().z + (double)floatValue21);
                  dev.leg1tka.rotation.Rotation value18 = dev.leg1tka.api.utils.player.RotationRaycast.a(vec3d23);
                  if (dev.leg1tka.api.utils.player.EntityRaycast.a(value18.toDirectionVector(), doubleValue1, box7)) {
                     c = new Vec3d((double)floatValue20, (double)floatValue22, (double)floatValue21);
                     return new Vec3d(box7.getCenter().x + (double)floatValue20, box7.minY + (double)floatValue22, box7.getCenter().z + (double)floatValue21);
                  }
               }
            }
         }
      }

      return new Vec3d(box7.getCenter().x + c.x, box7.minY + c.y, box7.getCenter().z + c.z);
   }

   @Generated
   private BacktrackAimGeometry() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
