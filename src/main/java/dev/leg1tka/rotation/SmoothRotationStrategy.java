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

public class SmoothRotationStrategy extends dev.leg1tka.rotation.RotationStrategy implements dev.leg1tka.api.ClientAccess {
   private LivingEntity c;
   private float d;
   private float f;
   private float g;
   private boolean h;
   private float i;
   private float j;
   private float k;
   private float l;
   private float m;
   private float n;
   private float o;
   private Vec3d p;
   private LivingEntity q;
   private int r;
   private final Random s = new Random();
   private float t;
   private float u;
   private int v;
   private float w;
   private float x;
   private long y;
   private Vec3d z;
   private Vec3d A;
   private float B;
   private float C;
   private Vec3d D;
   private float E;
   private int F;
   private int G = 4;
   private int H = 9;

   public SmoothRotationStrategy() {
   }

   public void b() {
      this.c = null;
      this.d = 0.0F;
      this.f = 0.0F;
      this.g = 0.0F;
      this.h = false;
      this.i = 1.0F;
      this.j = 0.0F;
      this.k = 0.0F;
      this.l = 0.0F;
      this.m = 0.0F;
      this.n = 0.0F;
      this.o = 0.0F;
      this.p = null;
      this.q = null;
      this.r = 0;
      this.t = 0.0F;
      this.u = 0.0F;
      this.v = 0;
      this.w = 0.0F;
      this.x = 0.0F;
      this.y = 0L;
      this.z = null;
      this.A = null;
      this.B = 0.0F;
      this.C = 0.0F;
      this.D = null;
      this.E = 0.0F;
      this.F = 0;
      this.G = 3 + this.s.nextInt(4);
      this.H = this.G + 4 + this.s.nextInt(5);
      if (minecraftClient.player != null) {
         this.d = MathHelper.wrapDegrees(minecraftClient.player.getYaw());
         this.f = minecraftClient.player.getPitch();
      }

      dev.leg1tka.rotation.RotationSensitivity.c.e();
   }

   public void c() {
      this.h = true;
      this.g = Math.min(this.g, 0.03F);
      this.C = 0.08F + this.s.nextFloat() * 0.08F;
      this.B = (this.s.nextFloat() - 0.5F) * 0.08F;
      this.F++;
      if (this.F > this.H + 5 + this.s.nextInt(5)) {
         this.F = 0;
         this.G = 3 + this.s.nextInt(4);
         this.H = this.G + 4 + this.s.nextInt(5);
      }
   }

   public float d() {
      if (this.F <= this.G) {
         return 0.92F + this.s.nextFloat() * 0.02F;
      } else {
         return this.F <= this.H ? 0.94F + this.s.nextFloat() * 0.03F : 0.95F + this.s.nextFloat() * 0.03F;
      }
   }

   public float e() {
      if (this.c != null && minecraftClient.player != null) {
         double doubleValue1 = (double)minecraftClient.player.distanceTo(this.c);
         if (doubleValue1 > 3.0) {
            return 0.02F + this.s.nextFloat() * 0.03F;
         }
      }

      if (this.F <= this.G) {
         return 0.03F + this.s.nextFloat() * 0.04F;
      } else {
         return this.F <= this.H ? 0.05F + this.s.nextFloat() * 0.05F : 0.07F + this.s.nextFloat() * 0.05F;
      }
   }

   public long f() {
      if (this.F <= this.G) {
         return (long)this.s.nextInt(10);
      } else {
         return this.F <= this.H ? 8L + (long)this.s.nextInt(15) : 15L + (long)this.s.nextInt(20);
      }
   }

   public boolean d(LivingEntity livingEntity1) {
      if (minecraftClient.player != null && livingEntity1 != null) {
         float floatValue2 = minecraftClient.player.getAttackCooldownProgress(0.5F);
         if (floatValue2 < this.d()) {
            return false;
         } else {
            float floatValue3 = this.e();
            boolean flag4 = !minecraftClient.player.isOnGround() && minecraftClient.player.getVelocity().y < 0.0 && minecraftClient.player.fallDistance >= floatValue3;
            if (!flag4 && !minecraftClient.player.isOnGround()) {
               return false;
            } else {
               Box box5 = livingEntity1.getBoundingBox().expand(0.1);
               Vec3d vec3d6 = minecraftClient.player.getEyePos();
               Vec3d vec3d7 = minecraftClient.player.getRotationVector(this.f, this.d);
               return dev.leg1tka.api.utils.player.EntityRaycast.a(vec3d7, 4.0, box5);
            }
         }
      } else {
         return false;
      }
   }

   private float a(float floatValue1) {
      float floatValue2 = 90.0F;
      float floatValue3 = 90.0F;
      return MathHelper.clamp(floatValue1, -floatValue3, floatValue2);
   }

   private Vec3d a(LivingEntity livingEntity1, float floatValue2) {
      Box box3 = livingEntity1.getBoundingBox();
      double doubleValue4 = box3.getLengthX();
      double doubleValue6 = box3.getLengthY();
      double doubleValue8 = box3.getLengthZ();
      double doubleValue10 = box3.minX + doubleValue4 * 0.5;
      double doubleValue12 = box3.minY + doubleValue6 * 0.7;
      double doubleValue14 = box3.minZ + doubleValue8 * 0.5;
      double doubleValue16 = (double)System.currentTimeMillis() / 50.0 * (double)floatValue2;
      int intValue18 = livingEntity1.getId();
      double doubleValue19 = Math.sin(doubleValue16 + (double)intValue18) * doubleValue4 * 0.45;
      double doubleValue21 = Math.cos(doubleValue16 * 0.8 + (double)intValue18) * doubleValue6 * 0.1;
      double doubleValue23 = Math.cos(doubleValue16 * 1.2 + (double)intValue18) * doubleValue8 * 0.45;
      return new Vec3d(doubleValue10 + doubleValue19, doubleValue12 + doubleValue21, doubleValue14 + doubleValue23);
   }

   private Vec3d b(LivingEntity livingEntity1, float floatValue2) {
      Box box3 = livingEntity1.getBoundingBox();
      double doubleValue4 = box3.getLengthX();
      double doubleValue6 = box3.getLengthY();
      double doubleValue8 = box3.getLengthZ();
      double doubleValue10 = box3.minX + doubleValue4 * 0.5;
      double doubleValue12 = box3.minY + doubleValue6 * 0.65;
      double doubleValue14 = box3.minZ + doubleValue8 * 0.5;
      double doubleValue16 = (double)System.currentTimeMillis() / 65.0 * (double)floatValue2;
      int intValue18 = livingEntity1.getId();
      double doubleValue19 = Math.sin(doubleValue16 + (double)intValue18) * doubleValue4 * 0.7;
      double doubleValue21 = Math.cos(doubleValue16 * 0.8 + (double)intValue18) * doubleValue6 * 0.4;
      double doubleValue23 = Math.cos(doubleValue16 * 1.2 + (double)intValue18) * doubleValue8 * 0.7;
      return new Vec3d(doubleValue10 + doubleValue19, doubleValue12 + doubleValue21, doubleValue14 + doubleValue23);
   }

   private Vec3d c(LivingEntity livingEntity1, float floatValue2) {
      Box box3 = livingEntity1.getBoundingBox();
      double doubleValue4 = box3.minX + box3.getLengthX() * 0.5;
      double doubleValue6 = box3.minZ + box3.getLengthZ() * 0.5;
      double doubleValue8 = box3.getLengthY();
      double doubleValue10 = (double)System.currentTimeMillis() / 400.0 * (double)floatValue2;
      double doubleValue12 = Math.abs(Math.sin(doubleValue10 + (double)livingEntity1.getId()));
      double doubleValue14;
      if (livingEntity1.isSneaking()) {
         doubleValue14 = 0.55 + doubleValue12 * 0.25;
      } else if (!(livingEntity1.fallDistance > 0.5F) && livingEntity1.isOnGround()) {
         doubleValue14 = 0.6 + doubleValue12 * 0.32;
      } else {
         doubleValue14 = 0.45 + doubleValue12 * 0.35;
      }

      return new Vec3d(doubleValue4, box3.minY + doubleValue8 * doubleValue14, doubleValue6);
   }

   private Vec3d d(LivingEntity livingEntity1, float floatValue2) {
      Box box3 = livingEntity1.getBoundingBox();
      double doubleValue4 = box3.getLengthX();
      double doubleValue6 = box3.getLengthY();
      double doubleValue8 = box3.getLengthZ();
      double doubleValue10 = box3.minX + doubleValue4 * 0.5;
      double doubleValue12 = box3.minY + doubleValue6 * 0.65;
      double doubleValue14 = box3.minZ + doubleValue8 * 0.5;
      double doubleValue16 = (double)System.currentTimeMillis() / 150.0 * (double)floatValue2;
      double doubleValue18 = Math.max(1.0E-6, Math.abs((Math.sin(doubleValue16 * 1.1 + (double)livingEntity1.getId()) + 1.0) * 0.5));
      double doubleValue20 = (Math.cos(doubleValue16 * 0.9 + (double)(livingEntity1.getId() * 2)) + 1.0) * 0.5;
      double doubleValue22 = Math.sqrt(-2.0 * Math.log(doubleValue18));
      double doubleValue24 = (Math.PI * 2) * doubleValue20;
      double doubleValue26 = MathHelper.clamp(doubleValue22 * Math.cos(doubleValue24) * 0.18 * doubleValue4, -doubleValue4 * 0.4, doubleValue4 * 0.4);
      double doubleValue28 = MathHelper.clamp(doubleValue22 * Math.sin(doubleValue24) * 0.18 * doubleValue8, -doubleValue8 * 0.4, doubleValue8 * 0.4);
      double doubleValue30 = MathHelper.clamp(Math.sin(doubleValue16 * 1.3) * 0.2 * doubleValue6, -doubleValue6 * 0.25, doubleValue6 * 0.25);
      return new Vec3d(doubleValue10 + doubleValue26, doubleValue12 + doubleValue30, doubleValue14 + doubleValue28);
   }

   private Vec3d e(LivingEntity livingEntity1, float floatValue2) {
      Box box3 = livingEntity1.getBoundingBox();
      double doubleValue4 = box3.getLengthX();
      double doubleValue6 = box3.getLengthY();
      double doubleValue8 = box3.getLengthZ();
      if (this.z == null || this.A == null) {
         this.z = new Vec3d(0.0, doubleValue6 * 0.6, 0.0);
         this.A = new Vec3d((double)(this.s.nextFloat() - 0.5F) * 0.02, (double)(this.s.nextFloat() - 0.5F) * 0.01, (double)(this.s.nextFloat() - 0.5F) * 0.02);
      }

      double doubleValue10 = (doubleValue4 - 0.08) * 0.5;
      double doubleValue12 = (doubleValue8 - 0.08) * 0.5;
      double doubleValue14 = 0.015 * (double)floatValue2;
      double doubleValue16 = this.A.x + (double)(this.s.nextFloat() - 0.5F) * doubleValue14;
      double doubleValue18 = this.A.y + (double)(this.s.nextFloat() - 0.5F) * doubleValue14 * 0.6;
      double doubleValue20 = this.A.z + (double)(this.s.nextFloat() - 0.5F) * doubleValue14;
      doubleValue16 = MathHelper.clamp(doubleValue16, -doubleValue14 * 2.0, doubleValue14 * 2.0);
      doubleValue18 = MathHelper.clamp(doubleValue18, -doubleValue14 * 1.5, doubleValue14 * 1.5);
      doubleValue20 = MathHelper.clamp(doubleValue20, -doubleValue14 * 2.0, doubleValue14 * 2.0);
      double doubleValue22 = this.z.x + doubleValue16;
      double doubleValue24 = this.z.y + doubleValue18;
      double doubleValue26 = this.z.z + doubleValue20;
      if (doubleValue22 > doubleValue10 || doubleValue22 < -doubleValue10) {
         doubleValue16 = -doubleValue16;
         doubleValue22 = MathHelper.clamp(doubleValue22, -doubleValue10, doubleValue10);
      }

      if (doubleValue24 > doubleValue6 * 0.85 || doubleValue24 < doubleValue6 * 0.35) {
         doubleValue18 = -doubleValue18;
         doubleValue24 = MathHelper.clamp(doubleValue24, doubleValue6 * 0.35, doubleValue6 * 0.85);
      }

      if (doubleValue26 > doubleValue12 || doubleValue26 < -doubleValue12) {
         doubleValue20 = -doubleValue20;
         doubleValue26 = MathHelper.clamp(doubleValue26, -doubleValue12, doubleValue12);
      }

      this.A = new Vec3d(doubleValue16, doubleValue18, doubleValue20);
      this.z = new Vec3d(doubleValue22, doubleValue24, doubleValue26);
      return new Vec3d(box3.minX + doubleValue4 * 0.5 + doubleValue22, box3.minY + doubleValue24, box3.minZ + doubleValue8 * 0.5 + doubleValue26);
   }

   private Vec3d f(LivingEntity livingEntity1, float floatValue2) {
      Box box3 = livingEntity1.getBoundingBox();
      double doubleValue4 = box3.getLengthX() * 0.42;
      double doubleValue6 = box3.getLengthY();
      double doubleValue8 = box3.minX + box3.getLengthX() * 0.5;
      double doubleValue10 = box3.minZ + box3.getLengthZ() * 0.5;
      double doubleValue12 = (double)System.currentTimeMillis() / 120.0 * (double)floatValue2;
      double doubleValue14 = doubleValue12 + (double)livingEntity1.getId();
      double doubleValue16 = Math.cos(doubleValue14) * doubleValue4;
      double doubleValue18 = Math.sin(doubleValue14) * doubleValue4;
      double doubleValue20 = (Math.sin(doubleValue12 * 0.4) * 0.5 + 0.5) * doubleValue6 * 0.5 + doubleValue6 * 0.35;
      return new Vec3d(doubleValue8 + doubleValue16, box3.minY + doubleValue20, doubleValue10 + doubleValue18);
   }

   private Vec3d g(LivingEntity livingEntity1, float floatValue2) {
      Box box3 = livingEntity1.getBoundingBox();
      double doubleValue4 = box3.getLengthX() * 0.46;
      double doubleValue6 = box3.getLengthZ() * 0.46;
      double doubleValue8 = box3.getLengthY();
      double doubleValue10 = box3.minX + box3.getLengthX() * 0.5;
      double doubleValue12 = box3.minZ + box3.getLengthZ() * 0.5;
      double doubleValue14 = (double)System.currentTimeMillis() / 200.0 * (double)floatValue2 + (double)livingEntity1.getId();
      double doubleValue16 = Math.abs(doubleValue14 % 4.0);
      double doubleValue18;
      double doubleValue20;
      if (doubleValue16 < 1.0) {
         doubleValue18 = MathHelper.lerp(doubleValue16, -doubleValue4, doubleValue4);
         doubleValue20 = -doubleValue6;
      } else if (doubleValue16 < 2.0) {
         doubleValue18 = doubleValue4;
         doubleValue20 = MathHelper.lerp(doubleValue16 - 1.0, -doubleValue6, doubleValue6);
      } else if (doubleValue16 < 3.0) {
         doubleValue18 = MathHelper.lerp(doubleValue16 - 2.0, doubleValue4, -doubleValue4);
         doubleValue20 = doubleValue6;
      } else {
         doubleValue18 = -doubleValue4;
         doubleValue20 = MathHelper.lerp(doubleValue16 - 3.0, doubleValue6, -doubleValue6);
      }

      double doubleValue22 = (Math.cos(doubleValue14 * 0.5) * 0.15 + 0.65) * doubleValue8;
      return new Vec3d(doubleValue10 + doubleValue18, box3.minY + doubleValue22, doubleValue12 + doubleValue20);
   }

   private Vec3d h(LivingEntity livingEntity1, float floatValue2) {
      Box box3 = livingEntity1.getBoundingBox();
      double doubleValue4 = box3.minX + box3.getLengthX() * 0.5;
      double doubleValue6 = box3.minZ + box3.getLengthZ() * 0.5;
      double doubleValue8 = box3.minY + box3.getLengthY() * 0.68;
      Vec3d vec3d10 = minecraftClient.player != null ? minecraftClient.player.getVelocity() : Vec3d.ZERO;
      double doubleValue11 = livingEntity1.getX() - livingEntity1.prevX;
      double doubleValue13 = livingEntity1.getZ() - livingEntity1.prevZ;
      double doubleValue15 = (doubleValue11 - vec3d10.x) * 2.5 * (double)floatValue2;
      double doubleValue17 = (doubleValue13 - vec3d10.z) * 2.5 * (double)floatValue2;
      double doubleValue19 = box3.getLengthX() * 0.45;
      doubleValue15 = MathHelper.clamp(doubleValue15, -doubleValue19, doubleValue19);
      doubleValue17 = MathHelper.clamp(doubleValue17, -doubleValue19, doubleValue19);
      return new Vec3d(doubleValue4 + doubleValue15, doubleValue8, doubleValue6 + doubleValue17);
   }

   private Vec3d e(LivingEntity livingEntity1) {
      if (livingEntity1.isGliding() && ModuleManager.O != null && ModuleManager.O.isEnabled()) {
         return livingEntity1.getBoundingBox().getCenter();
      } else {
         int intValue2 = Math.max(1, 1);
         Vec3d vec3d3 = livingEntity1.getPos().add(0.0, (double)livingEntity1.getHeight() * 0.72, 0.0);
         float floatValue4 = 0.7F;
         vec3d3 = this.e(livingEntity1, floatValue4);
         return vec3d3 != null ? vec3d3 : livingEntity1.getBoundingBox().getCenter();
      }
   }

   private float b(float floatValue1) {
      return 1.0F;
   }

   private float a(Box box1, Vec3d vec3d2) {
      boolean flag3 = box1.expand(-0.3F).contains(vec3d2) || box1.expand(0.3).contains(vec3d2);
      float floatValue4 = flag3 ? 1.0F : 1.0F;
      float floatValue5 = flag3 ? 1.0F : 0.01F;
      this.i = this.i + (floatValue4 - this.i) * floatValue5;
      this.i = MathHelper.clamp(this.i, 1.0F, 1.0F);
      return this.i;
   }

   private void b(float floatValue1, float floatValue2) {
      this.j = 0.0F;
      this.k = 0.0F;
   }

   private void c(float floatValue1, float floatValue2) {
      this.n = 0.0F;
      this.o = 0.0F;
   }

   @Override
   public void a(LivingEntity livingEntity1) {
      if (minecraftClient.player != null && livingEntity1 != null) {
         if (!dev.leg1tka.rotation.RotationSensitivity.c.d()) {
            if (this.c != livingEntity1) {
               this.c = livingEntity1;
               this.d = MathHelper.wrapDegrees(minecraftClient.player.getYaw());
               this.f = minecraftClient.player.getPitch();
               this.g = 0.0F;
               this.h = false;
               this.i = 1.0F;
               this.j = 0.0F;
               this.k = 0.0F;
               this.n = 0.0F;
               this.o = 0.0F;
               this.p = null;
               this.q = null;
               this.r = 0;
               this.t = 0.0F;
               this.u = 0.0F;
               this.v = 0;
               this.z = null;
               this.A = null;
               this.B = 0.0F;
               this.C = 0.0F;
               this.D = null;
               this.E = 0.0F;
               this.w = 0.0F;
               this.x = 0.0F;
               this.y = 0L;
            }

            if (System.currentTimeMillis() >= this.y) {
               Vec3d vec3d2 = this.e(livingEntity1);
               Vec3d vec3d3 = minecraftClient.player.getEyePos();
               Vec3d vec3d4 = vec3d2.subtract(vec3d3);
               float floatValue5 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d4.z, vec3d4.x)) - 90.0);
               float floatValue6 = (float)(-Math.toDegrees(Math.atan2(vec3d4.y, vec3d4.horizontalLength())));
               floatValue6 = this.a(floatValue6);
               Box box7 = livingEntity1.getBoundingBox();
               boolean flag8 = dev.leg1tka.api.utils.player.EntityRaycast.a(minecraftClient.player.getRotationVector(this.f, this.d), 100.0, box7.expand(-0.3F));
               float floatValue9 = MathHelper.wrapDegrees(floatValue5 - this.d);
               float floatValue10 = floatValue6 - this.f;
               float floatValue11 = this.d;
               float floatValue12 = this.f;
               if (flag8 && Math.abs(floatValue9) < 5.0F && Math.abs(floatValue10) < 4.0F) {
                  floatValue11 = this.d;
                  floatValue12 = this.f;
               } else {
                  if (minecraftClient.player.getAttackCooldownProgress(1.5F) < 0.85F) {
                     this.h = true;
                  }

                  if (this.h) {
                     float floatValue13 = -0.08F;
                     float floatValue14 = Math.abs(MathHelper.wrapDegrees(floatValue5 - this.d)) > 180.0F ? 0.01F : 0.001F;
                     if (this.g >= floatValue13) {
                        this.g -= floatValue14;
                     }

                     if (this.g <= floatValue13) {
                        this.h = false;
                     }
                  } else {
                     this.g += 0.012F;
                     if (this.g >= 0.38F || flag8) {
                        this.h = true;
                     }
                  }

                  float floatValue21 = this.b(floatValue9);
                  floatValue9 *= floatValue21;
                  floatValue10 *= 1.0F - (1.0F - floatValue21) * 0.5F;
                  float floatValue24 = Math.max(this.g, 0.0F) * this.a(box7, vec3d3);
                  floatValue11 = this.d + floatValue9 * Math.min(Math.max(floatValue24 * 1.9F, 0.0F), 1.0F);
                  floatValue12 = this.f + floatValue10 * Math.min(Math.max(floatValue24 * 1.5F, 0.0F), 1.0F);
                  if (minecraftClient.player.getAttackCooldownProgress(1.5F) < 0.85F) {
                     floatValue11 = this.d;
                     floatValue12 = this.f;
                  }
               }

               if (this.v == 0 && Math.abs(floatValue9) < 8.0F && Math.abs(floatValue10) < 6.0F && this.g > 0.04F) {
                  float floatValue22 = 2.0F;
                  this.t = Math.signum(floatValue9) * (0.6F + this.s.nextFloat() * 0.5F) * floatValue22;
                  this.u = Math.signum(floatValue10) * (0.3F + this.s.nextFloat() * 0.3F) * floatValue22;
                  this.v = 3;
               }

               if (this.v > 0) {
                  floatValue11 += this.t;
                  floatValue12 += this.u;
                  this.t *= 0.5F;
                  this.u *= 0.5F;
                  this.v--;
                  if (this.v == 0) {
                     this.t = 0.0F;
                     this.u = 0.0F;
                  }
               }

               this.D = null;
               this.E = 0.0F;
               if (!flag8) {
               }

               if (this.B != 0.0F || this.C != 0.0F) {
                  floatValue11 += this.B;
                  floatValue12 += this.C;
                  this.B *= 0.4F;
                  this.C *= 0.4F;
                  if (Math.abs(this.B) < 0.005F) {
                     this.B = 0.0F;
                  }

                  if (Math.abs(this.C) < 0.005F) {
                     this.C = 0.0F;
                  }
               }

               floatValue11 = MathHelper.wrapDegrees(floatValue11);
               floatValue12 = this.a(floatValue12);
               float floatValue23 = 180.0F;
               float floatValue25 = 180.0F;
               byte byteValue15 = 1;
               dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(floatValue11, floatValue12), 360.0F, 360.0F, floatValue23, floatValue25, byteValue15, 1, AttackAura.I.getValue());
               this.d = floatValue11;
               this.f = floatValue12;
               AttackAura.c.aM = new Vec2f(this.d, this.f);
               AttackAura.c.aN = new Vec2f(floatValue11, floatValue12);
            }
         }
      }
   }
}
