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

public class ShardRotationStrategy extends dev.leg1tka.rotation.RotationStrategy implements dev.leg1tka.api.ClientAccess {
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

   public ShardRotationStrategy() {
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
   }

   private float a(float floatValue1) {
      float floatValue2 = 90.0F * (AttackAura.c.af.f() / 100.0F);
      float floatValue3 = 90.0F * (AttackAura.c.ae.f() / 100.0F);
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

   private Vec3d d(LivingEntity livingEntity1) {
      if (livingEntity1.isGliding() && ModuleManager.O != null && ModuleManager.O.isEnabled()) {
         return livingEntity1.getBoundingBox().getCenter();
      } else {
         int intValue2 = Math.max(1, (int)AttackAura.c.ab.f());
         if (AttackAura.c.aa.getValue() && this.q == livingEntity1 && this.p != null && this.r < intValue2) {
            this.r++;
            return this.p;
         } else {
            String string3 = AttackAura.c.W.g();

            Vec3d vec3d4 = switch (string3) {
               case "Грудь" -> livingEntity1.getPos().add(0.0, (double)livingEntity1.getHeight() * 0.72, 0.0);
               case "Голова" -> livingEntity1.getPos().add(0.0, (double)livingEntity1.getHeight() * 0.92, 0.0);
               default -> {
                  Vec3d vec3d7 = dev.leg1tka.rotation.BacktrackAimGeometry.b(livingEntity1);
                  yield dev.leg1tka.rotation.BacktrackAimGeometry.a(livingEntity1, vec3d7 != null ? vec3d7 : livingEntity1.getBoundingBox().getCenter(), (double)AttackAura.c.t.f());
               }
            };
            if (AttackAura.c.X.getValue()) {
               float floatValue5 = AttackAura.c.Z.f();
               String string9 = AttackAura.c.Y.g();

               vec3d4 = switch (string9) {
                  case "Агрессивный" -> this.b(livingEntity1, floatValue5);
                  case "Скелетный" -> this.c(livingEntity1, floatValue5);
                  case "Гауссов" -> this.d(livingEntity1, floatValue5);
                  case "Органический" -> this.e(livingEntity1, floatValue5);
                  case "Спиральный" -> this.f(livingEntity1, floatValue5);
                  case "Периметр" -> this.g(livingEntity1, floatValue5);
                  case "Кинетический" -> this.h(livingEntity1, floatValue5);
                  default -> this.a(livingEntity1, floatValue5);
               };
            }

            if (AttackAura.c.aa.getValue()) {
               this.q = livingEntity1;
               this.p = vec3d4;
               this.r = 0;
            }

            return vec3d4 != null ? vec3d4 : livingEntity1.getBoundingBox().getCenter();
         }
      }
   }

   private float b(float floatValue1) {
      if (!AttackAura.c.Storage.getValue()) {
         return 1.0F;
      } else {
         float floatValue2 = Math.abs(floatValue1);
         float floatValue3 = AttackAura.c.ModuleManager.f();
         if (floatValue2 <= floatValue3) {
            return 1.0F;
         } else {
            float floatValue4 = MathHelper.clamp((floatValue2 - floatValue3) / Math.max(1.0F, 180.0F - floatValue3), 0.0F, 1.0F);
            return MathHelper.clamp(1.0F - floatValue4 * AttackAura.c.ModuleRegistry.f(), 0.1F, 1.0F);
         }
      }
   }

   private float a(Box box1, Vec3d vec3d2) {
      boolean flag3 = box1.expand((double)AttackAura.c.V.f()).contains(vec3d2) || box1.expand(0.3).contains(vec3d2);
      float floatValue4 = flag3 ? AttackAura.c.ag.f() : 1.0F;
      float floatValue5 = flag3 ? 1.0F : AttackAura.c.ah.f();
      this.i = this.i + (floatValue4 - this.i) * floatValue5;
      this.i = MathHelper.clamp(this.i, AttackAura.c.ag.f(), 1.0F);
      return this.i;
   }

   private void b(float floatValue1, float floatValue2) {
      if (!AttackAura.c.as.getValue()) {
         this.j = 0.0F;
      } else {
         float floatValue3 = AttackAura.c.au.f();
         float floatValue4 = Math.min(AttackAura.c.at.f() * 0.1F, 0.5F);
         float floatValue5 = MathHelper.clamp(Math.max(Math.abs(floatValue1) / 25.0F, Math.abs(floatValue2) / 20.0F), 0.12F, 1.0F);
         this.l += floatValue4;
         float floatValue6 = (float)Math.sin((double)this.l) * floatValue3;
         float floatValue7 = 1.0F + (this.s.nextFloat() - 0.5F) * AttackAura.c.ax.f() * 0.2F;
         this.j = floatValue6 * floatValue7;
         this.j = this.j * (1.0F - AttackAura.c.av.f() * 0.5F);
         if (AttackAura.c.aw.f() > 0.0F && Math.abs(floatValue6) > floatValue3 * 0.7F) {
            this.l = this.l + (float)(Math.PI * (double)AttackAura.c.aw.f() * 0.1F);
         }

         this.j = MathHelper.clamp(this.j * floatValue5, -floatValue3, floatValue3);
      }

      if (!AttackAura.c.ay.getValue()) {
         this.k = 0.0F;
      } else {
         float floatValue8 = AttackAura.c.aA.f();
         float floatValue9 = Math.min(AttackAura.c.az.f() * 0.1F, 0.5F);
         float floatValue10 = MathHelper.clamp(Math.max(Math.abs(floatValue1) / 30.0F, Math.abs(floatValue2) / 18.0F), 0.1F, 1.0F);
         this.m += floatValue9;
         float floatValue11 = (float)Math.sin((double)this.m) * floatValue8;
         float floatValue12 = 1.0F + (this.s.nextFloat() - 0.5F) * AttackAura.c.aD.f() * 0.2F;
         this.k = floatValue11 * floatValue12;
         this.k = this.k * (1.0F - AttackAura.c.aB.f() * 0.5F);
         if (AttackAura.c.aC.f() > 0.0F && Math.abs(floatValue11) > floatValue8 * 0.7F) {
            this.m = this.m + (float)(Math.PI * (double)AttackAura.c.aC.f() * 0.1F);
         }

         this.k = MathHelper.clamp(this.k * floatValue10, -floatValue8, floatValue8);
      }
   }

   private void c(float floatValue1, float floatValue2) {
      if (!AttackAura.c.aE.getValue()) {
         this.n = 0.0F;
         this.o = 0.0F;
      } else {
         boolean flag3 = Math.abs(floatValue1) <= 8.0F && Math.abs(floatValue2) <= 6.0F;
         if (AttackAura.c.aH.getValue() && !flag3) {
            this.n = 0.0F;
            this.o = 0.0F;
         } else if (this.s.nextFloat() * 100.0F <= AttackAura.c.aF.f()) {
            float floatValue4 = AttackAura.c.aG.f();
            this.n = (this.s.nextFloat() - 0.5F) * 2.0F * floatValue4;
            this.o = AttackAura.c.aI.getValue() ? 0.0F : (this.s.nextFloat() - 0.5F) * floatValue4;
         } else {
            this.n *= 0.2F;
            this.o *= 0.2F;
         }
      }
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
               if (AttackAura.c.ap.getValue()) {
                  this.y = System.currentTimeMillis() + (long)AttackAura.c.aq.f();
               } else {
                  this.y = 0L;
               }
            }

            if (System.currentTimeMillis() >= this.y) {
               Vec3d vec3d2 = this.d(livingEntity1);
               Vec3d vec3d3 = minecraftClient.player.getEyePos();
               if (AttackAura.c.K.getValue()) {
                  Vec3d vec3d4 = minecraftClient.player.getVelocity();
                  double doubleValue5 = livingEntity1.getX() - livingEntity1.prevX;
                  double doubleValue7 = livingEntity1.getZ() - livingEntity1.prevZ;
                  double doubleValue9 = livingEntity1.getY() - livingEntity1.prevY;
                  double doubleValue11 = Math.max(1.0, vec3d3.distanceTo(vec3d2));
                  double doubleValue13 = Math.min(doubleValue11 * 0.12, 0.35);
                  vec3d2 = vec3d2.add((doubleValue5 - vec3d4.x) * doubleValue13, (doubleValue9 - vec3d4.y) * doubleValue13 * 0.5, (doubleValue7 - vec3d4.z) * doubleValue13);
               }

               Vec3d vec3d22 = vec3d2.subtract(vec3d3);
               float floatValue23 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d22.z, vec3d22.x)) - 90.0);
               float floatValue6 = (float)(-Math.toDegrees(Math.atan2(vec3d22.y, vec3d22.horizontalLength())));
               floatValue6 = this.a(floatValue6);
               Box box25 = livingEntity1.getBoundingBox();
               boolean flag8 = dev.leg1tka.api.utils.player.EntityRaycast.a(minecraftClient.player.getRotationVector(this.f, this.d), (double)AttackAura.c.U.f(), box25.expand((double)AttackAura.c.V.f()));
               float floatValue26 = MathHelper.wrapDegrees(floatValue23 - this.d);
               float floatValue10 = floatValue6 - this.f;
               float floatValue27 = this.d;
               float floatValue12 = this.f;
               if (AttackAura.c.N.getValue() && flag8 && Math.abs(floatValue26) < 5.0F && Math.abs(floatValue10) < 4.0F) {
                  floatValue27 = this.d;
                  floatValue12 = this.f;
               } else if (AttackAura.c.J.b("Постоянная")) {
                  float floatValue32 = Math.min(Math.abs(floatValue26), AttackAura.c.L.f()) * Math.signum(floatValue26);
                  float floatValue14 = Math.min(Math.abs(floatValue10), AttackAura.c.M.f()) * Math.signum(floatValue10);
                  floatValue27 = this.d + floatValue32 * AttackAura.c.ac.f();
                  floatValue12 = this.f + floatValue14 * AttackAura.c.ad.f();
               } else if (AttackAura.c.J.b("Адаптивная")) {
                  float floatValue33 = MathHelper.clamp((float)Math.sqrt((double)(floatValue26 * floatValue26 + floatValue10 * floatValue10)) / 45.0F, 0.15F, 1.0F);
                  float floatValue41 = floatValue26 * floatValue33 * AttackAura.c.ac.f();
                  float floatValue15 = floatValue10 * floatValue33 * AttackAura.c.ad.f();
                  floatValue27 = this.d + floatValue41;
                  floatValue12 = this.f + floatValue15;
               } else {
                  if (AttackAura.c.al.getValue() && minecraftClient.player.getAttackCooldownProgress(1.5F) < 0.85F) {
                     this.h = true;
                  }

                  if (this.h) {
                     float floatValue34 = AttackAura.c.S.f();
                     float floatValue42 = Math.abs(MathHelper.wrapDegrees(floatValue23 - this.d)) > AttackAura.c.T.f() ? AttackAura.c.Q.f() : AttackAura.c.P.f();
                     if (this.g >= floatValue34) {
                        this.g -= floatValue42;
                     }

                     if (this.g <= floatValue34) {
                        this.h = false;
                     }
                  } else {
                     this.g = this.g + AttackAura.c.O.f();
                     if (this.g >= AttackAura.c.R.f() || flag8) {
                        this.h = true;
                     }
                  }

                  float floatValue35 = this.b(floatValue26);
                  floatValue26 *= floatValue35;
                  floatValue10 *= 1.0F - (1.0F - floatValue35) * 0.5F;
                  float floatValue43 = Math.max(this.g, 0.0F) * this.a(box25, vec3d3);
                  floatValue27 = this.d + floatValue26 * Math.min(Math.max(floatValue43 * AttackAura.c.ac.f(), 0.0F), 1.0F);
                  floatValue12 = this.f + floatValue10 * Math.min(Math.max(floatValue43 * AttackAura.c.ad.f(), 0.0F), 1.0F);
                  if (AttackAura.c.al.getValue() && minecraftClient.player.getAttackCooldownProgress(1.5F) < 0.85F) {
                     floatValue27 = this.d;
                     floatValue12 = this.f;
                  }
               }

               if (AttackAura.c.an.getValue() && this.v == 0 && Math.abs(floatValue26) < 8.0F && Math.abs(floatValue10) < 6.0F && this.g > 0.04F) {
                  float floatValue36 = AttackAura.c.ao.f();
                  this.t = Math.signum(floatValue26) * (0.6F + this.s.nextFloat() * 0.5F) * floatValue36;
                  this.u = Math.signum(floatValue10) * (0.3F + this.s.nextFloat() * 0.3F) * floatValue36;
                  this.v = 3;
               }

               if (this.v > 0) {
                  floatValue27 += this.t;
                  floatValue12 += this.u;
                  this.t *= 0.5F;
                  this.u *= 0.5F;
                  this.v--;
                  if (this.v == 0) {
                     this.t = 0.0F;
                     this.u = 0.0F;
                  }
               }

               if (AttackAura.c.ar.getValue() && !flag8) {
                  if (this.D == null || this.E >= 1.0F) {
                     float floatValue37 = (this.s.nextFloat() - 0.5F) * 6.0F;
                     this.D = new Vec3d((double)(this.d + floatValue26 * 0.5F + floatValue37), (double)(this.f + floatValue10 * 0.5F + (this.s.nextFloat() - 0.5F) * 3.0F), 0.0);
                     this.E = 0.0F;
                  }

                  this.E = Math.min(1.0F, this.E + 0.25F);
                  float floatValue38 = 1.0F - this.E;
                  floatValue27 = floatValue38 * floatValue38 * this.d + 2.0F * floatValue38 * this.E * (float)this.D.x + this.E * this.E * floatValue27;
                  floatValue12 = floatValue38 * floatValue38 * this.f + 2.0F * floatValue38 * this.E * (float)this.D.y + this.E * this.E * floatValue12;
               } else {
                  this.D = null;
                  this.E = 0.0F;
               }

               if (!AttackAura.c.N.getValue() || !flag8) {
                  if (AttackAura.c.as.getValue() || AttackAura.c.ay.getValue()) {
                     this.b(floatValue26, floatValue10);
                     floatValue27 += this.j;
                     floatValue12 += this.k;
                  }

                  if (AttackAura.c.aE.getValue()) {
                     this.c(floatValue26, floatValue10);
                     floatValue27 += this.n;
                     floatValue12 += this.o;
                  }
               }

               if (this.B != 0.0F || this.C != 0.0F) {
                  floatValue27 += this.B;
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

               if (AttackAura.c.am.getValue()) {
                  float floatValue39 = MathHelper.wrapDegrees(floatValue27 - this.d) + this.w;
                  float floatValue44 = floatValue12 - this.f + this.x;
                  int intValue46 = dev.leg1tka.rotation.RotationContext.b(floatValue39);
                  int intValue16 = dev.leg1tka.rotation.RotationContext.b(floatValue44);
                  float floatValue17 = dev.leg1tka.rotation.RotationContext.a(intValue46);
                  float floatValue18 = dev.leg1tka.rotation.RotationContext.a(intValue16);
                  double doubleValue19 = dev.leg1tka.rotation.RotationContext.a();
                  float floatValue21 = (float)(doubleValue19 * 0.5);
                  this.w = MathHelper.clamp(floatValue39 - floatValue17, -floatValue21, floatValue21);
                  this.x = MathHelper.clamp(floatValue44 - floatValue18, -floatValue21, floatValue21);
                  floatValue27 = this.d + floatValue17;
                  floatValue12 = this.f + floatValue18;
               }

               floatValue27 = MathHelper.wrapDegrees(floatValue27);
               floatValue12 = this.a(floatValue12);
               float floatValue40 = AttackAura.c.aJ.getValue() ? AttackAura.c.aK.f() : 360.0F;
               float floatValue45 = AttackAura.c.aJ.getValue() ? AttackAura.c.aL.f() : 360.0F;
               int intValue47 = AttackAura.c.aJ.getValue() ? 1 : 0;
               dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(floatValue27, floatValue12), 360.0F, 360.0F, floatValue40, floatValue45, intValue47, 1, AttackAura.I.getValue());
               this.d = floatValue27;
               this.f = floatValue12;
               AttackAura.c.aM = new Vec2f(this.d, this.f);
               AttackAura.c.aN = new Vec2f(floatValue27, floatValue12);
            }
         }
      }
   }
}
