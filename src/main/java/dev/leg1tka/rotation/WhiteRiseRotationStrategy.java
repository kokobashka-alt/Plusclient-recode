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

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class WhiteRiseRotationStrategy extends dev.leg1tka.rotation.RotationStrategy implements dev.leg1tka.api.ClientAccess {
   private final AttackAura c;
   private LivingEntity d;
   private float f;
   private float g;
   private float h;
   private boolean i;
   private boolean j;
   private float k;
   private int l;

   public WhiteRiseRotationStrategy(AttackAura attackAura1) {
      this.c = attackAura1;
   }

   public void b() {
      this.d = null;
      this.h = 0.0F;
      this.i = false;
      this.k = 0.0F;
      this.l = 0;
      this.j = minecraftClient.player != null;
      dev.leg1tka.rotation.RotationSensitivity.c.e();
      if (minecraftClient.player != null) {
         this.f = minecraftClient.player.getYaw();
         this.g = minecraftClient.player.getPitch();
      } else {
         this.f = 0.0F;
         this.g = 0.0F;
      }
   }

   public void c() {
   }

   @Override
   public void a(LivingEntity livingEntity1) {
      if (minecraftClient.player != null && livingEntity1 != null) {
         if (!dev.leg1tka.rotation.RotationSensitivity.c.d()) {
            if (minecraftClient.player.isBlocking()) {
               this.e = new Vec2f(minecraftClient.player.getYaw(), minecraftClient.player.getPitch());
               this.f = this.e.x;
               this.g = this.e.y;
            } else {
               if (!this.j) {
                  this.f = minecraftClient.player.getYaw();
                  this.g = minecraftClient.player.getPitch();
                  this.j = true;
               }

               if (this.d != livingEntity1) {
                  this.d = livingEntity1;
                  this.h = 0.0F;
                  this.i = false;
                  this.l = 0;
               }

               this.l++;
               this.k = (float)((Math.sin((double)this.l * 0.17) * 0.12 + (Math.random() * 0.08 - 0.04)) * 0.7F);
               Vec3d vec3d2 = dev.leg1tka.rotation.BacktrackAimGeometry.a(livingEntity1, 128.0);
               Vec2f vec2f3 = dev.leg1tka.api.utils.player.RotationRaycast.b(vec3d2);
               float floatValue4 = vec2f3.x;
               float floatValue5 = vec2f3.y;
               float floatValue6 = Math.abs(MathHelper.wrapDegrees(floatValue4 - this.f));
               boolean flag7 = minecraftClient.player.getAttackCooldownProgress(1.0F) > 0.9F && this.c.x() <= 1;
               if (!this.i) {
                  float floatValue8 = 0.0055F;
                  if (floatValue6 > 60.0F) {
                     floatValue8 += 0.028800001F;
                  } else if (floatValue6 > 30.0F) {
                     floatValue8 += 0.014400001F;
                  } else {
                     floatValue8 += 0.0072000003F;
                  }

                  if (flag7) {
                     floatValue8 += 0.012857143F;
                  }

                  this.h = this.h + floatValue8 * (1.6F + this.k);
                  if (this.h >= 0.22F) {
                     this.i = true;
                  }
               } else {
                  float floatValue22 = flag7 ? 0.045F : 0.008F;
                  this.h = this.h - floatValue22 * (2.1F + this.k);
                  if (this.h <= -0.04F) {
                     this.i = false;
                  }
               }

               float floatValue23 = MathHelper.clamp(this.h, 0.0F, minecraftClient.player.isGliding() ? 0.38F : 0.26F);
               if (flag7) {
                  floatValue23 = Math.min(floatValue23 + 0.1F, minecraftClient.player.isGliding() ? 0.46F : 0.34F);
               }

               floatValue23 += this.k * 0.5F;
               if (this.l % 7 == 0) {
                  floatValue23 += 0.03F;
               }

               float floatValue9 = MathHelper.wrapDegrees(floatValue4 - this.f);
               float floatValue10 = floatValue5 - this.g;
               float floatValue11 = minecraftClient.player.isGliding() ? 42.0F : (flag7 ? 28.0F : 20.0F);
               float floatValue12 = minecraftClient.player.isGliding() ? 12.0F : (flag7 ? 4.5F : 2.8F);
               floatValue9 = MathHelper.clamp(floatValue9, -floatValue11, floatValue11);
               floatValue10 = MathHelper.clamp(floatValue10, -floatValue12, floatValue12);
               float floatValue13 = floatValue23 * 0.28F;
               float floatValue14 = floatValue23 * (0.85F + this.k * 0.4F);
               float floatValue15 = this.f + floatValue9 * floatValue14;
               float floatValue16 = this.g + floatValue10 * floatValue13;
               float floatValue17 = dev.leg1tka.rotation.RotationContext.b();
               if (floatValue17 > 0.0F) {
                  floatValue15 = this.f + (float)Math.round((floatValue15 - this.f) / floatValue17) * floatValue17;
                  floatValue16 = this.g + (float)Math.round((floatValue16 - this.g) / floatValue17) * floatValue17;
               }

               floatValue16 = MathHelper.clamp(floatValue16, -89.0F, 89.0F);
               float[] floatValueArray18 = dev.leg1tka.rotation.RotationSensitivity.c.a(floatValue15, floatValue16, livingEntity1, "whiterise");
               dev.leg1tka.rotation.Rotation value19 = new dev.leg1tka.rotation.Rotation(floatValueArray18[0], floatValueArray18[1]);
               float floatValue20 = minecraftClient.player.isGliding() && livingEntity1.isGliding() ? 360.0F : 45.0F;
               dev.leg1tka.rotation.RotationStorage.a(value19, floatValue20, floatValue20, floatValue20, floatValue20, 0, 1, AttackAura.I.getValue());
               this.e = new Vec2f(value19.getYaw(), value19.getPitch());
               this.f = value19.getYaw();
               this.g = value19.getPitch();
            }
         }
      }
   }

   private Vec3d d(LivingEntity livingEntity1) {
      Vec3d vec3d2 = dev.leg1tka.rotation.BacktrackAimGeometry.c(livingEntity1);
      if (vec3d2 == null) {
         vec3d2 = livingEntity1.getBoundingBox().getCenter();
      }

      return this.b(livingEntity1) ? a(livingEntity1, vec3d2) : vec3d2;
   }
}
