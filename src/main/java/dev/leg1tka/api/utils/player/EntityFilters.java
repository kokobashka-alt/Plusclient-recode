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
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class EntityFilters implements dev.leg1tka.api.ClientAccess {
   private static double c;
   private static float d;

   public static Vec3d a(Entity entity0, double doubleValue1, float floatValue3, double doubleValue4, float floatValue6) {
      Vec3d vec3d7 = entity0.getPos();
      Vec3d vec3d8 = entity0.getVelocity();
      if (Math.hypot(entity0.prevX - entity0.getX(), entity0.prevZ - entity0.getZ()) * 20.0 <= 20.0) {
         return vec3d7.add(0.0, (double)entity0.getHeight(), 0.0);
      } else {
         float floatValue9 = entity0.prevYaw;
         float floatValue10 = entity0.getYaw();
         float floatValue11 = Math.abs(floatValue10 - floatValue9);
         float floatValue12 = Math.min(floatValue11 / 25.0F, 3.0F);
         double doubleValue13 = 1.0 - (double)floatValue12 * 0.9;

         for (int intValue15 = 0; intValue15 < (int)doubleValue1; intValue15++) {
            float floatValue16 = (float)intValue15 / (float)doubleValue1;
            double doubleValue17 = 1.0 - (1.0 - doubleValue4) * (1.0 - (double)floatValue16);
            vec3d8 = a(vec3d8, floatValue9, floatValue10, (float)((double)floatValue6 * doubleValue13));
            vec3d7 = vec3d7.add(vec3d8);
            vec3d8 = vec3d8.multiply(doubleValue17);
         }

         double doubleValue23 = doubleValue1 - (double)((int)doubleValue1);
         if (doubleValue23 > 0.0) {
            float floatValue24 = (float)(1.0 - doubleValue23);
            double doubleValue18 = 1.0 - (1.0 - doubleValue4) * (1.0 - (double)floatValue24);
            vec3d8 = a(vec3d8, floatValue9, floatValue10, (float)((double)floatValue6 * doubleValue13));
            vec3d7 = vec3d7.add(vec3d8.multiply(doubleValue23 * doubleValue18));
         }

         vec3d7 = a(entity0, vec3d7, doubleValue1);
         return vec3d7.add(0.0, (double)entity0.getHeight(), 0.0);
      }
   }

   private static Vec3d a(Entity entity0, Vec3d vec3d1, double doubleValue2) {
      if (minecraftClient.player != null && entity0 != null && minecraftClient.player.isGliding() && entity0 instanceof LivingEntity livingEntity4 && livingEntity4.isGliding()) {
         Vec3d vec3d5 = minecraftClient.player.getVelocity();
         if (vec3d5.lengthSquared() < 0.01) {
            vec3d5 = minecraftClient.player.getRotationVector();
         }

         if (vec3d5.lengthSquared() < 0.01) {
            return vec3d1;
         }

         Vec3d vec3d6 = new Vec3d(vec3d5.x, 0.0, vec3d5.z).normalize();
         Vec3d vec3d7 = new Vec3d(-vec3d6.z, 0.0, vec3d6.x);
         Vec3d vec3d8 = entity0.getPos().subtract(minecraftClient.player.getPos());
         double doubleValue9 = vec3d8.dotProduct(vec3d7);
         double doubleValue11 = vec3d8.dotProduct(vec3d6);
         Vec3d vec3d13 = entity0.getVelocity();
         double doubleValue14 = vec3d13.dotProduct(vec3d7);
         double doubleValue16 = Math.max(-1.35, Math.min(1.35, doubleValue9 * 0.32));
         double doubleValue18 = Math.max(-1.1, Math.min(1.1, doubleValue14 * (0.9 + doubleValue2 * 0.12)));
         double doubleValue20 = doubleValue11 > 0.0 ? Math.min(doubleValue11 * 0.045, 0.45) : 0.0;
         return vec3d1.add(vec3d7.multiply(doubleValue16 + doubleValue18)).add(vec3d6.multiply(doubleValue20));
      }

      return vec3d1;
   }

   private static Vec3d a(Vec3d vec3d0, float floatValue1, float floatValue2, float floatValue3) {
      float floatValue4 = (floatValue2 - floatValue1) * floatValue3;
      if (Math.abs(floatValue4) < 0.001F) {
         return vec3d0;
      } else {
         double doubleValue5 = Math.toRadians((double)floatValue4);
         double doubleValue7 = Math.cos(doubleValue5);
         double doubleValue9 = Math.sin(doubleValue5);
         double doubleValue11 = vec3d0.x * doubleValue7 - vec3d0.z * doubleValue9;
         double doubleValue13 = vec3d0.x * doubleValue9 + vec3d0.z * doubleValue7;
         return new Vec3d(doubleValue11, vec3d0.y, doubleValue13);
      }
   }

   public static Vec3d a(Entity entity0, double doubleValue1) {
      return a(entity0, doubleValue1, 0.0F, 0.98, 1.5F);
   }

   public static Vec3d a(Entity entity0, double doubleValue1, double doubleValue3, double doubleValue5) {
      return a(entity0, doubleValue1, 0.0F, 0.98, 1.5F, doubleValue3, doubleValue5);
   }

   public static Vec3d a(Entity entity0, double doubleValue1, float floatValue3) {
      return a(entity0, doubleValue1, floatValue3, 0.98, 1.5F);
   }

   public static Vec3d a(Entity entity0, double doubleValue1, float floatValue3, double doubleValue4, double doubleValue6) {
      return a(entity0, doubleValue1, floatValue3, 0.98, 1.5F, doubleValue4, doubleValue6);
   }

   public static Vec3d a(LivingEntity livingEntity0, double doubleValue1) {
      return a((Entity)livingEntity0, doubleValue1);
   }

   private static Vec3d a(Entity entity0, double doubleValue1, float floatValue3, double doubleValue4, float floatValue6, double doubleValue7, double doubleValue9) {
      Vec3d vec3d11 = a(entity0, doubleValue1, floatValue3, doubleValue4, floatValue6);
      return a(entity0, vec3d11, doubleValue1, doubleValue7, doubleValue9);
   }

   private static Vec3d a(Entity entity0, Vec3d vec3d1, double doubleValue2, double doubleValue4, double doubleValue6) {
      if (minecraftClient.player != null && entity0 != null && minecraftClient.player.isGliding() && entity0 instanceof LivingEntity livingEntity8 && livingEntity8.isGliding()) {
         Vec3d vec3d9 = minecraftClient.player.getVelocity();
         if (vec3d9.lengthSquared() < 0.01) {
            vec3d9 = minecraftClient.player.getRotationVector();
         }

         if (vec3d9.lengthSquared() < 0.01) {
            return vec3d1;
         }

         Vec3d vec3d10 = new Vec3d(vec3d9.x, 0.0, vec3d9.z).normalize();
         Vec3d vec3d11 = new Vec3d(-vec3d10.z, 0.0, vec3d10.x);
         Vec3d vec3d12 = entity0.getPos().subtract(minecraftClient.player.getPos());
         double doubleValue13 = vec3d12.dotProduct(vec3d11);
         double doubleValue15 = vec3d12.dotProduct(vec3d10);
         Vec3d vec3d17 = entity0.getVelocity();
         Vec3d vec3d18 = new Vec3d(vec3d17.x, 0.0, vec3d17.z);
         double doubleValue19 = vec3d17.dotProduct(vec3d11);
         double doubleValue21 = vec3d17.dotProduct(vec3d10);
         double doubleValue23 = vec3d9.dotProduct(vec3d10);
         double doubleValue25 = doubleValue21 - doubleValue23;
         float floatValue27 = MathHelper.wrapDegrees(entity0.getYaw() - entity0.prevYaw);
         double doubleValue28 = Math.max(-3.4, Math.min(3.4, doubleValue13 * 0.58 * doubleValue4));
         double doubleValue30 = Math.max(-2.8, Math.min(2.8, doubleValue19 * (1.25 + doubleValue2 * 0.3) * doubleValue4));
         double doubleValue32 = doubleValue15 > 0.0 ? Math.min(doubleValue15 * 0.22 * doubleValue6, 4.1 * doubleValue6) : 0.0;
         double doubleValue34 = Math.max(0.0, Math.min(doubleValue21 * 0.52 * doubleValue6, 2.4 * doubleValue6));
         double doubleValue36 = doubleValue25 > 0.0 ? Math.min(doubleValue25 * (1.55 + doubleValue2 * 0.24) * doubleValue6, 3.0 * doubleValue6) : 0.0;
         Vec3d vec3d38 = Vec3d.ZERO;
         if (vec3d18.lengthSquared() > 0.01 && Math.abs(floatValue27) > 2.0F) {
            Vec3d vec3d39 = vec3d18.normalize();
            Vec3d vec3d40 = new Vec3d(-vec3d39.z, 0.0, vec3d39.x).multiply((double)Math.signum(floatValue27));
            double doubleValue41 = Math.min((double)Math.abs(floatValue27) / 16.0, 2.6) * (0.7 + doubleValue2 * 0.14);
            vec3d38 = vec3d40.multiply(doubleValue41 * doubleValue4).add(vec3d39.multiply(doubleValue41 * 1.15 * doubleValue6));
         }

         return vec3d1.add(vec3d11.multiply(doubleValue28 + doubleValue30)).add(vec3d10.multiply(doubleValue32 + doubleValue34 + doubleValue36)).add(vec3d38);
      }

      return vec3d1;
   }

   public static Vec3d a(LivingEntity livingEntity0) {
      return a(livingEntity0, livingEntity0.getPos());
   }

   public static Vec3d a(LivingEntity livingEntity0, Vec3d vec3d1, int intValue2) {
      return a(livingEntity0, (double)intValue2);
   }

   public static boolean b(LivingEntity livingEntity0) {
      if (livingEntity0 != null && minecraftClient.player != null && minecraftClient.player.isGliding() && livingEntity0.isGliding()) {
         Vec3d vec3d1 = livingEntity0.getVelocity();
         Vec3d vec3d2 = vec3d1.subtract(minecraftClient.player.getVelocity());
         return vec3d1.length() < 0.38 || vec3d2.length() < 0.2;
      } else {
         return false;
      }
   }

   public static boolean c(LivingEntity livingEntity0) {
      if (livingEntity0 != null && minecraftClient.player != null && minecraftClient.player.isGliding() && livingEntity0.isGliding()) {
         Vec3d vec3d1 = livingEntity0.getVelocity().subtract(minecraftClient.player.getVelocity());
         Vec3d vec3d2 = livingEntity0.getVelocity();
         Vec3d vec3d3 = minecraftClient.player.getVelocity();
         if (vec3d1.length() < 0.01 || vec3d2.length() < 0.04) {
            return false;
         } else if (b(livingEntity0)) {
            return false;
         } else {
            Vec3d vec3d4 = vec3d1.normalize();
            double doubleValue5 = vec3d4.dotProduct(vec3d2.normalize());
            double doubleValue7 = vec3d2.subtract(vec3d3).normalize().dotProduct(vec3d4);
            double doubleValue9 = (double)minecraftClient.player.distanceTo(livingEntity0);
            return doubleValue5 > 0.72 && doubleValue7 > 0.18 && vec3d2.y > 0.55 && doubleValue9 > 2.2;
         }
      } else {
         return false;
      }
   }

   public static boolean d(LivingEntity livingEntity0) {
      if (livingEntity0 != null && minecraftClient.player != null && minecraftClient.player.isGliding() && livingEntity0.isGliding()) {
         Vec3d vec3d1 = livingEntity0.getVelocity().subtract(minecraftClient.player.getVelocity());
         Vec3d vec3d2 = livingEntity0.getVelocity();
         Vec3d vec3d3 = minecraftClient.player.getVelocity();
         if (vec3d1.length() < 0.01 || vec3d2.length() < 0.09) {
            return false;
         } else if (b(livingEntity0)) {
            return false;
         } else {
            Vec3d vec3d4 = vec3d1.normalize();
            double doubleValue5 = vec3d4.dotProduct(vec3d2.normalize());
            double doubleValue7 = vec3d2.subtract(vec3d3).normalize().dotProduct(vec3d4);
            double doubleValue9 = (double)minecraftClient.player.distanceTo(livingEntity0);
            double doubleValue11 = vec3d2.length();
            double doubleValue13 = vec3d3.length();
            return doubleValue5 > 0.84 && doubleValue7 > 0.42 && doubleValue11 > 1.05 && doubleValue11 - doubleValue13 > 0.14 && doubleValue9 > 2.7;
         }
      } else {
         return false;
      }
   }

   public static boolean e(LivingEntity livingEntity0) {
      return livingEntity0 != null && minecraftClient.player != null && minecraftClient.player.isGliding() && livingEntity0.isGliding() ? d(livingEntity0) || c(livingEntity0) : false;
   }

   public static Vec3d a(LivingEntity livingEntity0, Vec3d vec3d1) {
      if (livingEntity0 != null && livingEntity0.isGliding() && ModuleManager.O != null) {
         double doubleValue2 = ModuleManager.O.e.e().doubleValue();
         boolean flag4 = ModuleManager.O.g.getValue();
         double doubleValue5 = ModuleManager.O.i.e().doubleValue();
         double doubleValue7 = ModuleManager.O.h.e().doubleValue();
         if (d(livingEntity0)) {
            double doubleValue9 = 0.0;
            if (livingEntity0 instanceof PlayerEntity) {
               PlayerListEntry playerListEntry11 = minecraftClient.getNetworkHandler().getPlayerListEntry(livingEntity0.getUuid());
               if (playerListEntry11 != null) {
                  doubleValue9 = Math.min((double)playerListEntry11.getLatency() / 50.0, 5.0);
               }
            }

            double doubleValue18 = Math.min(1.3 + doubleValue9 * 0.45, 2.6);
            double doubleValue14 = Math.max(doubleValue2 - 0.5, 1.0);
            Vec3d vec3d13;
            if (flag4) {
               vec3d13 = a(livingEntity0, doubleValue14, doubleValue5, doubleValue7);
            } else {
               vec3d13 = a((Entity)livingEntity0, doubleValue14);
            }

            Vec3d vec3d16 = livingEntity0.getPos().add(0.0, (double)livingEntity0.getHeight() / 2.0, 0.0);
            Vec3d vec3d17 = vec3d16.add(livingEntity0.getVelocity().multiply(doubleValue18));
            return vec3d13.multiply(0.65).add(vec3d17.multiply(0.35));
         } else {
            if (c(livingEntity0)) {
               doubleValue2 += 2.5;
            }

            return flag4 ? a(livingEntity0, doubleValue2, doubleValue5, doubleValue7) : a((Entity)livingEntity0, doubleValue2);
         }
      } else {
         return livingEntity0 != null ? livingEntity0.getEyePos() : Vec3d.ZERO;
      }
   }

   public static Vec3d a(LivingEntity livingEntity0, float floatValue1) {
      if (livingEntity0 != null && livingEntity0.isGliding() && ModuleManager.O != null) {
         double doubleValue2 = ModuleManager.O.e.e().doubleValue();
         boolean flag4 = ModuleManager.O.g.getValue();
         double doubleValue5 = ModuleManager.O.i.e().doubleValue();
         double doubleValue7 = ModuleManager.O.h.e().doubleValue();
         if (d(livingEntity0)) {
            double doubleValue9 = 0.0;
            if (livingEntity0 instanceof PlayerEntity) {
               PlayerListEntry playerListEntry11 = minecraftClient.getNetworkHandler().getPlayerListEntry(livingEntity0.getUuid());
               if (playerListEntry11 != null) {
                  doubleValue9 = Math.min((double)playerListEntry11.getLatency() / 50.0, 5.0);
               }
            }

            double doubleValue18 = Math.min(1.3 + doubleValue9 * 0.45, 2.6);
            double doubleValue14 = Math.max(doubleValue2 - 0.5, 1.0);
            Vec3d vec3d13;
            if (flag4) {
               vec3d13 = a(livingEntity0, doubleValue14, floatValue1, doubleValue5, doubleValue7);
            } else {
               vec3d13 = a(livingEntity0, doubleValue14, floatValue1);
            }

            Vec3d vec3d16 = livingEntity0.getPos().add(0.0, (double)livingEntity0.getHeight() / 2.0, 0.0);
            Vec3d vec3d17 = vec3d16.add(livingEntity0.getVelocity().multiply(doubleValue18));
            return vec3d13.multiply(0.65).add(vec3d17.multiply(0.35));
         } else {
            if (c(livingEntity0)) {
               doubleValue2 += 2.5;
            }

            return flag4 ? a(livingEntity0, doubleValue2, floatValue1, doubleValue5, doubleValue7) : a(livingEntity0, doubleValue2, floatValue1);
         }
      } else {
         return livingEntity0 != null ? livingEntity0.getEyePos() : Vec3d.ZERO;
      }
   }

   @Generated
   private EntityFilters() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
