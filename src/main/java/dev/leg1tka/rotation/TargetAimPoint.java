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

import dev.leg1tka.api.events.implement.EventLook;
import java.util.List;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class TargetAimPoint implements dev.leg1tka.api.ClientAccess {
   private static float c;
   private static float d;
   private static float e;
   private static float f;
   private static float g;
   private static float h;
   private static float i;
   private static float j;
   private static float k;
   private static float l;
   private static float m;
   private static float n;
   private static float o;
   private static float p;
   private static float q;
   private static long r;
   private long s;
   private float t;
   private float u;
   private float v;
   private float w;
   private float x;
   private float y;
   private float z;
   private float A;
   private float B;
   private float C;
   private LivingEntity D;
   private double E;
   private double F;
   private LivingEntity G;
   private long H;

   public TargetAimPoint() {
   }

   public void a() {
      this.s = 0L;
      this.t = this.u = 0.0F;
      this.v = 0.0F;
      this.w = 0.0F;
      this.x = this.y = 0.0F;
      this.z = this.A = 0.0F;
      this.D = null;
      this.E = this.F = 0.0;
      this.G = null;
   }

   public void a(EventLook eventLook1, LivingEntity livingEntity2, dev.leg1tka.rotation.TargetAimPoint.a value3) {
      long longValue4 = System.nanoTime();
      float floatValue6 = this.s == 0L ? 0.0F : (float)(longValue4 - this.s) / 1.0E9F;
      this.s = longValue4;
      if (!(floatValue6 <= 0.0F) && !(floatValue6 > 0.1F)) {
         floatValue6 = Math.min(floatValue6, 0.05F);
         float floatValue7 = (float)(eventLook1.getYaw() * 0.15);
         float floatValue8 = (float)(eventLook1.getPitch() * 0.15);
         this.t = this.t + (floatValue7 / floatValue6 - this.t) * a(floatValue6, 0.04F);
         this.u = this.u + (floatValue8 / floatValue6 - this.u) * a(floatValue6, 0.04F);
         if (livingEntity2 != null && minecraftClient.player != null) {
            float floatValue9 = minecraftClient.getRenderTickCounter().getTickDelta(true);
            Vec3d vec3d10 = minecraftClient.player.getCameraPosVec(floatValue9);
            Box box11 = a(livingEntity2, floatValue9).offset(value3.j);
            float floatValue12 = minecraftClient.player.getYaw();
            float floatValue13 = minecraftClient.player.getPitch();
            Vec3d vec3d14 = Vec3d.fromPolar(floatValue13, floatValue12);
            float floatValue15 = Math.max(0.25F, MathHelper.cos(floatValue13 * (float) (Math.PI / 180.0)));
            Box box16 = box11.expand((double)value3.d);
            float floatValue17 = a(vec3d10, vec3d14, a(box16, vec3d10, vec3d14), floatValue12, floatValue13, floatValue15, null);
            float[] floatValueArray18 = new float[2];
            float floatValue19 = a(vec3d10, vec3d14, a(a(box11), vec3d10, vec3d14), floatValue12, floatValue13, floatValue15, floatValueArray18);
            float[] floatValueArray20 = new float[2];
            float floatValue21 = a(vec3d10, vec3d14, box11.getCenter(), floatValue12, floatValue13, floatValue15, floatValueArray20);
            if (box11.contains(vec3d10)) {
               floatValue17 = 0.0F;
               floatValue19 = 0.0F;
            } else if (box16.raycast(vec3d10, vec3d10.add(vec3d14.multiply(vec3d10.distanceTo(box11.getCenter()) + 4.0))).isPresent()) {
               floatValue17 = 0.0F;
            }

            float floatValue22 = 1.0F - a(0.0F, 1.5F, floatValue17);
            float floatValue23 = 1.0F - a(0.0F, 2.5F, floatValue17);
            float floatValue24 = floatValue12 + floatValueArray20[0] / floatValue15;
            float floatValue25 = floatValue13 + floatValueArray20[1];
            if (this.D == livingEntity2) {
               float floatValue26 = MathHelper.wrapDegrees(floatValue24 - this.B) / floatValue6;
               float floatValue27 = (floatValue25 - this.C) / floatValue6;
               this.z = this.z + (floatValue26 - this.z) * a(floatValue6, 0.05F);
               this.A = this.A + (floatValue27 - this.A) * a(floatValue6, 0.05F);
            } else {
               this.z = this.A = 0.0F;
               this.D = livingEntity2;
            }

            this.B = floatValue24;
            this.C = floatValue25;
            float floatValue52 = this.t * floatValue15;
            float floatValue53 = this.u;
            float floatValue28 = (float)Math.sqrt((double)(floatValue52 * floatValue52 + floatValue53 * floatValue53));
            float floatValue29 = a(2.0F, 15.0F, floatValue28);
            float floatValue30 = 0.0F;
            if (floatValue28 > 0.001F && floatValue21 > 0.001F) {
               floatValue30 = (floatValue52 * floatValueArray20[0] + floatValue53 * floatValueArray20[1]) / (floatValue28 * floatValue21);
            }

            float floatValue31 = floatValue29 * (floatValue30 >= 0.0F ? 0.6F + 0.5F * floatValue30 : 0.6F + 0.4F * floatValue30);
            if (value3.i) {
               floatValue31 = Math.max(floatValue31, 1.0F);
            }

            this.v = this.v + (floatValue31 - this.v) * a(floatValue6, floatValue31 > this.v ? 0.03F : 0.15F);
            float floatValue32 = value3.a * 0.5F;
            float floatValue33 = this.v * (1.0F - a(floatValue32 * 0.6F, floatValue32, floatValue21));
            float floatValue34 = 0.0F;
            float floatValue35 = 0.0F;
            float floatValue36 = (float)Math.sqrt((double)(1800.0F * value3.f * floatValue19));
            if (floatValue19 > 1.0E-4F) {
               float floatValue37 = floatValueArray18[0] / floatValue19;
               float floatValue38 = floatValueArray18[1] / floatValue19;
               float floatValue39 = floatValue37 * this.x + floatValue38 * this.y;
               if (floatValue39 < 1.0F) {
                  this.w = this.w * MathHelper.clamp(floatValue39, 0.0F, 1.0F);
               }

               this.x = floatValue37;
               this.y = floatValue38;
               float floatValue40 = MathHelper.lerp(floatValue22, value3.b, value3.c);
               float floatValue41 = Math.min(floatValue40, floatValue36) * floatValue33;
               floatValue41 *= 1.0F - a(150.0F, 320.0F, floatValue28);
               float floatValue42 = floatValue41 > this.w ? Math.max(0.005F, value3.e / 1000.0F) : 0.015F;
               this.w = this.w + (floatValue41 - this.w) * a(floatValue6, floatValue42);
               this.w = Math.min(this.w, floatValue36);
               float floatValue43 = Math.min(this.w * floatValue6, floatValue19);
               floatValue34 = floatValue37 * floatValue43 / floatValue15;
               floatValue35 = floatValue38 * floatValue43;
            } else {
               this.w = this.w + -this.w * a(floatValue6, 0.015F);
            }

            float floatValue56 = value3.h * floatValue22 * floatValue33 * floatValue6;
            floatValue34 += this.z * floatValue56;
            floatValue35 += this.A * floatValue56;
            float floatValue57 = value3.g * floatValue23 * (1.0F - a(40.0F, 160.0F, floatValue28));
            double doubleValue58 = dev.leg1tka.rotation.RotationContext.a();
            double doubleValue60 = (double)dev.leg1tka.rotation.RotationContext.c();
            if (!(doubleValue58 < 1.0E-6)) {
               double doubleValue61 = (double)(floatValue7 * (1.0F - floatValue57) + floatValue34) + this.E;
               double doubleValue45 = (double)(floatValue8 * (1.0F - floatValue57) + floatValue35) + this.F;
               long longValue47 = Math.round(doubleValue61 / doubleValue58);
               long longValue49 = Math.round(doubleValue45 / doubleValue58);
               this.E = doubleValue61 - (double)longValue47 * doubleValue58;
               this.F = doubleValue45 - (double)longValue49 * doubleValue58;
               if (Math.abs((double)floatValue13 + (double)longValue49 * doubleValue58) >= 90.0) {
                  this.F = 0.0;
               }

               eventLook1.setYaw((double)longValue47 * doubleValue60);
               eventLook1.setPitch((double)longValue49 * doubleValue60);
            }
         } else {
            this.v = this.v + -this.v * a(floatValue6, 0.15F);
            this.w = 0.0F;
            this.D = null;
            this.E = this.F = 0.0;
         }
      } else {
         this.t = this.u = 0.0F;
         this.v = 0.0F;
         this.w = 0.0F;
         this.D = null;
         this.E = this.F = 0.0;
      }
   }

   public LivingEntity a(List<LivingEntity> list1, LivingEntity livingEntity2, float floatValue3) {
      if (minecraftClient.player != null && !list1.isEmpty()) {
         Vec3d vec3d4 = minecraftClient.player.getEyePos();
         float floatValue5 = minecraftClient.player.getYaw();
         float floatValue6 = minecraftClient.player.getPitch();
         Vec3d vec3d7 = Vec3d.fromPolar(floatValue6, floatValue5);
         float floatValue8 = Math.max(0.25F, MathHelper.cos(floatValue6 * (float) (Math.PI / 180.0)));
         float floatValue9 = floatValue3 * 0.5F;
         LivingEntity livingEntity10 = null;
         float floatValue11 = Float.MAX_VALUE;
         float floatValue12 = Float.MAX_VALUE;

         for (LivingEntity livingEntity14 : list1) {
            Box box15 = livingEntity14.getBoundingBox();
            float floatValue16 = box15.contains(vec3d4) ? 0.0F : a(vec3d4, vec3d7, a(box15, vec3d4, vec3d7), floatValue5, floatValue6, floatValue8, null);
            if (livingEntity14 == livingEntity2) {
               floatValue12 = floatValue16;
            }

            if (floatValue16 < floatValue11) {
               floatValue11 = floatValue16;
               livingEntity10 = livingEntity14;
            }
         }

         boolean flag17 = livingEntity2 != null && floatValue12 <= floatValue9 * 1.15F;
         if (livingEntity10 == null || floatValue11 > floatValue9) {
            this.G = null;
            return flag17 ? livingEntity2 : null;
         } else if (flag17 && livingEntity10 != livingEntity2) {
            if (floatValue11 < floatValue12 * 0.7F - 1.0F) {
               long longValue18 = System.currentTimeMillis();
               if (this.G != livingEntity10) {
                  this.G = livingEntity10;
                  this.H = longValue18;
               } else if (longValue18 - this.H >= 150L) {
                  this.G = null;
                  return livingEntity10;
               }
            } else {
               this.G = null;
            }

            return livingEntity2;
         } else {
            this.G = null;
            return livingEntity10;
         }
      } else {
         this.G = null;
         return null;
      }
   }

   private static Box a(LivingEntity livingEntity0, float floatValue1) {
      Vec3d vec3d2 = livingEntity0.getLerpedPos(floatValue1).subtract(livingEntity0.getPos());
      return livingEntity0.getBoundingBox().offset(vec3d2);
   }

   private static Box a(Box box0) {
      Vec3d vec3d1 = box0.getCenter();
      double doubleValue2 = box0.getLengthX() * 0.5 * 0.45F;
      double doubleValue4 = box0.getLengthZ() * 0.5 * 0.45F;
      double doubleValue6 = box0.getLengthY();
      return new Box(vec3d1.x - doubleValue2, box0.minY + doubleValue6 * 0.3, vec3d1.z - doubleValue4, vec3d1.x + doubleValue2, box0.minY + doubleValue6 * 0.85, vec3d1.z + doubleValue4);
   }

   private static Vec3d a(Box box0, Vec3d vec3d1, Vec3d vec3d2) {
      Vec3d vec3d3 = box0.getCenter();

      for (int intValue4 = 0; intValue4 < 3; intValue4++) {
         double doubleValue5 = Math.max(0.0, vec3d3.subtract(vec3d1).dotProduct(vec3d2));
         Vec3d vec3d7 = vec3d1.add(vec3d2.multiply(doubleValue5));
         vec3d3 = new Vec3d(
            MathHelper.clamp(vec3d7.x, box0.minX, box0.maxX), MathHelper.clamp(vec3d7.y, box0.minY, box0.maxY), MathHelper.clamp(vec3d7.z, box0.minZ, box0.maxZ)
         );
      }

      return vec3d3;
   }

   private static float a(Vec3d vec3d0, Vec3d vec3d1, Vec3d vec3d2, float floatValue3, float floatValue4, float floatValue5, float[] floatValueArray6) {
      double doubleValue7 = vec3d2.x - vec3d0.x;
      double doubleValue9 = vec3d2.y - vec3d0.y;
      double doubleValue11 = vec3d2.z - vec3d0.z;
      double doubleValue13 = Math.sqrt(doubleValue7 * doubleValue7 + doubleValue11 * doubleValue11);
      float floatValue15 = (float)(Math.toDegrees(Math.atan2(doubleValue11, doubleValue7)) - 90.0);
      float floatValue16 = (float)(-Math.toDegrees(Math.atan2(doubleValue9, doubleValue13)));
      float floatValue17 = MathHelper.wrapDegrees(floatValue15 - floatValue3) * floatValue5;
      float floatValue18 = floatValue16 - floatValue4;
      if (floatValueArray6 != null) {
         floatValueArray6[0] = floatValue17;
         floatValueArray6[1] = floatValue18;
      }

      return (float)Math.sqrt((double)(floatValue17 * floatValue17 + floatValue18 * floatValue18));
   }

   private static float a(float floatValue0, float floatValue1) {
      return 1.0F - (float)Math.exp((double)(-floatValue0 / floatValue1));
   }

   private static float a(float floatValue0, float floatValue1, float floatValue2) {
      float floatValue3 = MathHelper.clamp((floatValue2 - floatValue0) / (floatValue1 - floatValue0), 0.0F, 1.0F);
      return floatValue3 * floatValue3 * (3.0F - 2.0F * floatValue3);
   }

   public static class a {
      public float a;
      public float b;
      public float c;
      public float d;
      public float e;
      public float f;
      public float g;
      public float h;
      public boolean i;
      public Vec3d j = Vec3d.ZERO;

      public a() {
      }
   }
}
