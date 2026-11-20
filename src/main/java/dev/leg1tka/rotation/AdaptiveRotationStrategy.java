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

import java.util.Random;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class AdaptiveRotationStrategy extends dev.leg1tka.rotation.RotationStrategy implements dev.leg1tka.api.ClientAccess {
   private LivingEntity c;
   private float d;
   private float f;
   private float g;
   private boolean h;
   private boolean i;
   private float j;
   private Vec3d k;
   private LivingEntity l;
   private int m;
   private float n;
   private float o;
   private final Random p = new Random();

   public AdaptiveRotationStrategy() {
   }

   public void b() {
      this.c = null;
      this.d = 0.0F;
      this.f = 0.0F;
      this.g = 0.0F;
      this.h = false;
      this.i = false;
      this.j = 1.0F;
      this.k = null;
      this.l = null;
      this.m = 0;
      this.n = 0.0F;
      this.o = 0.0F;
      if (minecraftClient.player != null) {
         this.d = minecraftClient.player.getYaw();
         this.f = minecraftClient.player.getPitch();
      }

      dev.leg1tka.rotation.RotationSensitivity.c.e();
   }

   public void c() {
      this.i = true;
      this.h = true;
      this.g = -0.04F;
   }

   private float a(float floatValue1) {
      return MathHelper.clamp(floatValue1, 0.0F, 90.0F);
   }

   private Vec3d e(LivingEntity livingEntity1) {
      if (livingEntity1.isGliding() && ModuleManager.O != null && ModuleManager.O.isEnabled()) {
         return livingEntity1.getBoundingBox().getCenter();
      } else {
         byte byteValue2 = 1;
         if (this.l == livingEntity1 && this.k != null && this.m < byteValue2) {
            this.m++;
            return this.k;
         } else {
            Vec3d vec3d3 = dev.leg1tka.rotation.BacktrackAimGeometry.c(livingEntity1);
            if (vec3d3 == null) {
               vec3d3 = livingEntity1.getPos().add(0.0, (double)livingEntity1.getHeight() * 0.72, 0.0);
            }

            this.l = livingEntity1;
            this.k = vec3d3;
            this.m = 0;
            return vec3d3 != null ? vec3d3 : livingEntity1.getBoundingBox().getCenter();
         }
      }
   }

   private float a(Box box1, Vec3d vec3d2) {
      boolean flag3 = box1.expand(-0.3).contains(vec3d2) || box1.expand(0.3).contains(vec3d2);
      float floatValue4 = flag3 ? 0.05F : 1.0F;
      float floatValue5 = flag3 ? 1.0F : 0.01F;
      this.j = this.j + (floatValue4 - this.j) * floatValue5;
      this.j = MathHelper.clamp(this.j, 0.05F, 1.0F);
      return this.j;
   }

   @Override
   public void a(LivingEntity livingEntity1) {
      if (minecraftClient.player != null && livingEntity1 != null) {
         if (!dev.leg1tka.rotation.RotationSensitivity.c.d()) {
            if (this.c != livingEntity1) {
               this.c = livingEntity1;
               this.d = minecraftClient.player.getYaw();
               this.f = minecraftClient.player.getPitch();
               this.g = 0.0F;
               this.h = false;
               this.i = false;
               this.j = 1.0F;
               this.k = null;
               this.l = null;
               this.m = 0;
               this.n = 0.0F;
               this.o = 0.0F;
            }

            Vec3d vec3d2 = this.e(livingEntity1);
            Vec3d vec3d3 = minecraftClient.player.getEyePos();
            Vec3d vec3d4 = vec3d2.subtract(vec3d3);
            float floatValue5 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d4.z, vec3d4.x)) - 90.0);
            float floatValue6 = (float)(-Math.toDegrees(Math.atan2(vec3d4.y, vec3d4.horizontalLength())));
            floatValue6 = this.a(floatValue6);
            Box box7 = livingEntity1.getBoundingBox();
            boolean flag8 = dev.leg1tka.api.utils.player.EntityRaycast.a(minecraftClient.player.getRotationVector(this.f, this.d), 1950.0, box7.expand(-0.3));
            float floatValue9 = MathHelper.wrapDegrees(floatValue5 - this.d);
            float floatValue10 = floatValue6 - this.f;
            float floatValue11 = this.d;
            float floatValue12 = this.f;
            float floatValue13 = minecraftClient.player.getAttackCooldownProgress(0.5F);
            boolean flag14 = floatValue13 >= 0.65F || !minecraftClient.player.isOnGround() && minecraftClient.player.getVelocity().y < 0.0 && minecraftClient.player.fallDistance > 0.0F;
            if (this.i && flag14) {
               this.i = false;
               this.h = false;
               this.g = 0.0F;
            }

            if (this.i && !flag14) {
               floatValue11 = this.d;
               floatValue12 = this.f;
               this.g = Math.min(this.g, 0.0F);
            } else if (flag8) {
               floatValue11 = this.d;
               floatValue12 = this.f;
            } else {
               if (this.h) {
                  float floatValue15 = -0.1F;
                  float floatValue16 = Math.abs(MathHelper.wrapDegrees(floatValue5 - this.d)) > 30.0F ? 0.01F : 0.046F;
                  if (this.g >= floatValue15) {
                     this.g -= floatValue16;
                  }

                  if (this.g <= floatValue15) {
                     this.h = false;
                  }
               } else {
                  this.g += 0.003F;
                  if (this.g >= 0.25F || flag8) {
                     this.h = true;
                  }
               }

               float floatValue23 = Math.max(this.g, 0.0F) * this.a(box7, vec3d3);
               floatValue11 = this.d + floatValue9 * Math.min(Math.max(floatValue23 * 1.8F, 0.0F), 1.0F);
               floatValue12 = this.f + floatValue10 * Math.min(Math.max(floatValue23 * 0.4F, 0.0F), 1.0F);
            }

            if (Math.abs(floatValue9) > 18.0F && Math.abs(this.g) > 0.08F) {
               this.n = Math.signum(floatValue9) * Math.min(Math.abs(floatValue9) * 0.06F, 1.2F) * 0.9F;
               this.o = Math.signum(floatValue10) * Math.min(Math.abs(floatValue10) * 0.04F, 0.8F) * 0.9F;
            } else {
               this.n *= 0.65F;
               this.o *= 0.65F;
            }

            float floatValue24 = 30.0F;
            float floatValue25 = 9.0F;
            byte byteValue17 = 1;
            float floatValue20;
            float floatValue22;
            dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(floatValue20 = floatValue11 + this.n, floatValue22 = floatValue12 + this.o), 360.0F, 360.0F, floatValue24, floatValue25, byteValue17, 1, AttackAura.I.getValue());
            this.d = floatValue20;
            this.f = floatValue22;
            AttackAura.c.aM = new Vec2f(this.d, this.f);
            AttackAura.c.aN = new Vec2f(floatValue20, floatValue22);
         }
      }
   }

   public void d(LivingEntity livingEntity1) {
      if (minecraftClient.player != null && livingEntity1 != null) {
         if (this.c != livingEntity1) {
            this.c = livingEntity1;
            this.d = minecraftClient.player.getYaw();
            this.f = minecraftClient.player.getPitch();
            this.g = 0.0F;
            this.h = false;
            this.i = false;
            this.j = 1.0F;
            this.k = null;
            this.l = null;
            this.m = 0;
            this.n = 0.0F;
            this.o = 0.0F;
         }

         Vec3d vec3d2 = this.e(livingEntity1);
         Vec3d vec3d3 = minecraftClient.player.getEyePos();
         Vec3d vec3d4 = vec3d2.subtract(vec3d3);
         float floatValue5 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d4.z, vec3d4.x)) - 90.0);
         float floatValue6 = (float)(-Math.toDegrees(Math.atan2(vec3d4.y, vec3d4.horizontalLength())));
         floatValue6 = this.a(floatValue6);
         Box box7 = livingEntity1.getBoundingBox();
         boolean flag8 = dev.leg1tka.api.utils.player.EntityRaycast.a(minecraftClient.player.getRotationVector(this.f, this.d), 1950.0, box7.expand(-0.3));
         float floatValue9 = MathHelper.wrapDegrees(floatValue5 - this.d);
         float floatValue10 = floatValue6 - this.f;
         float floatValue11 = this.d;
         float floatValue12 = this.f;
         float floatValue13 = minecraftClient.player.getAttackCooldownProgress(0.5F);
         boolean flag14 = floatValue13 >= 0.65F || !minecraftClient.player.isOnGround() && minecraftClient.player.getVelocity().y < 0.0 && minecraftClient.player.fallDistance > 0.0F;
         if (this.i && flag14) {
            this.i = false;
            this.h = false;
            this.g = 0.0F;
         }

         if (this.i && !flag14) {
            floatValue11 = this.d;
            floatValue12 = this.f;
            this.g = Math.min(this.g, 0.0F);
         } else if (flag8) {
            floatValue11 = this.d;
            floatValue12 = this.f;
         } else {
            if (this.h) {
               float floatValue15 = -0.1F;
               float floatValue16 = Math.abs(MathHelper.wrapDegrees(floatValue5 - this.d)) > 30.0F ? 0.01F : 0.046F;
               if (this.g >= floatValue15) {
                  this.g -= floatValue16;
               }

               if (this.g <= floatValue15) {
                  this.h = false;
               }
            } else {
               this.g += 0.003F;
               if (this.g >= 0.25F || flag8) {
                  this.h = true;
               }
            }

            float floatValue22 = Math.max(this.g, 0.0F) * this.a(box7, vec3d3);
            floatValue11 = this.d + floatValue9 * Math.min(Math.max(floatValue22 * 1.8F, 0.0F), 1.0F);
            floatValue12 = this.f + floatValue10 * Math.min(Math.max(floatValue22 * 0.4F, 0.0F), 1.0F);
         }

         if (Math.abs(floatValue9) > 18.0F && Math.abs(this.g) > 0.08F) {
            this.n = Math.signum(floatValue9) * Math.min(Math.abs(floatValue9) * 0.06F, 1.2F) * 0.9F;
            this.o = Math.signum(floatValue10) * Math.min(Math.abs(floatValue10) * 0.04F, 0.8F) * 0.9F;
         } else {
            this.n *= 0.65F;
            this.o *= 0.65F;
         }

         floatValue11 += this.n;
         floatValue12 += this.o;
         this.d = floatValue11;
         this.f = floatValue12;
         dev.leg1tka.api.utils.security.ClientIntegrityState.a(floatValue11, floatValue12);
      } else {
         dev.leg1tka.api.utils.security.ClientIntegrityState.e();
      }
   }
}
