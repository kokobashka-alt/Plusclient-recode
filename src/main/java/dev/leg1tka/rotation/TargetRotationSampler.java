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
import net.minecraft.util.math.Vec3d;

public final class TargetRotationSampler implements dev.leg1tka.api.ClientAccess {
   private final Random h = new Random();
   private float[][] i;
   private int j;
   private float[] k;
   private LivingEntity l;
   private float m;
   private float n;
   private float o;
   private float p;
   private float q;
   private float r;
   private Vec3d s;
   private double t;
   private double u;
   private int v = 1000;
   private boolean w;
   private float x;
   public float c;
   public float d;
   public float e;
   public float f;
   public long g;

   public TargetRotationSampler() {
   }

   public void a() {
      this.l = null;
      this.j = 0;
      this.t = this.u = 0.0;
      this.o = this.p = 0.0F;
      this.w = false;
      this.x = 0.0F;
      this.s = null;
   }

   public void b() {
      this.k = null;
   }

   public void c() {
      this.v = 0;
   }

   public float[] a(dev.leg1tka.rotation.neural.RotationDatasetLoader value1, LivingEntity livingEntity2, float floatValue3, float floatValue4, dev.leg1tka.rotation.TargetRotationSampler.a value5) {
      if (value1 != null && minecraftClient.player != null) {
         long longValue6 = System.nanoTime();
         if (this.v < 1000) {
            this.v++;
         }

         Vec3d vec3d8 = minecraftClient.player.getEyePos();
         Box box9 = livingEntity2.getBoundingBox();
         Vec3d vec3d10 = box9.getCenter();
         float[] floatValueArray11 = a(vec3d8, vec3d10);
         Vec3d vec3d12 = vec3d10.subtract(vec3d8);
         double doubleValue13 = vec3d12.length();
         double doubleValue15 = Math.max(0.05, Math.sqrt(vec3d12.x * vec3d12.x + vec3d12.z * vec3d12.z));
         float floatValue17 = (float)Math.toDegrees(Math.atan2(box9.getLengthX() * 0.5, doubleValue15));
         float floatValue18 = (float)Math.toDegrees(Math.atan2(box9.getLengthY() * 0.5, Math.max(0.05, doubleValue13)));
         float floatValue19 = MathHelper.wrapDegrees(floatValueArray11[0] - floatValue3);
         float floatValue20 = floatValueArray11[1] - floatValue4;
         float floatValue21 = floatValue19 - MathHelper.clamp(floatValue19, -floatValue17, floatValue17);
         float floatValue22 = floatValue20 - MathHelper.clamp(floatValue20, -floatValue18, floatValue18);
         boolean flag23 = livingEntity2 != this.l || this.i == null || this.i.length != value1.c;
         if (flag23) {
            if (this.k == null || value5.c || this.i == null) {
               this.k = this.a(value1.e, value5.b);
            }

            this.i = new float[value1.c][];
            this.j = 0;
            this.l = livingEntity2;
            this.m = floatValueArray11[0];
            this.n = floatValueArray11[1];
            this.o = this.p = 0.0F;
            this.t = this.u = 0.0;
            this.w = false;
            this.x = 0.0F;
         }

         if (!flag23) {
            this.o = floatValue3 - this.q;
            this.p = floatValue4 - this.r;
         }

         this.q = floatValue3;
         this.r = floatValue4;
         float floatValue24 = MathHelper.wrapDegrees(floatValueArray11[0] - this.m);
         float floatValue25 = floatValueArray11[1] - this.n;
         this.m = floatValueArray11[0];
         this.n = floatValueArray11[1];
         float[] floatValueArray26 = new float[]{this.o, this.p, floatValue19, floatValue20, floatValue21, floatValue22, floatValue24, floatValue25};
         if (this.j == 0) {
            float[] floatValueArray27 = new float[]{0.0F, 0.0F, floatValue19, floatValue20, floatValue21, floatValue22, 0.0F, 0.0F};

            for (int intValue28 = 0; intValue28 < value1.c; intValue28++) {
               this.i[intValue28] = (float[])floatValueArray27.clone();
            }

            this.j = value1.c;
         }

         System.arraycopy(this.i, 1, this.i, 0, value1.c - 1);
         this.i[value1.c - 1] = floatValueArray26;
         Vec3d vec3d54 = minecraftClient.player.getPos();
         Vec3d vec3d55 = this.s == null ? Vec3d.ZERO : vec3d54.subtract(this.s);
         this.s = vec3d54;
         double doubleValue29 = Math.toRadians((double)floatValue3);
         double doubleValue31 = -Math.sin(doubleValue29);
         double doubleValue33 = Math.cos(doubleValue29);
         float floatValue35 = (float)((vec3d55.x * doubleValue31 + vec3d55.z * doubleValue33) * 20.0);
         float floatValue36 = (float)((vec3d55.x * doubleValue33 - vec3d55.z * doubleValue31) * 20.0);
         float[] floatValueArray37 = new float[]{
            (float)doubleValue13,
            floatValue17 * 2.0F,
            floatValue18 * 2.0F,
            a(minecraftClient.player.isOnGround()),
            a(minecraftClient.options.jumpKey.isPressed()),
            a(minecraftClient.options.forwardKey.isPressed()) - a(minecraftClient.options.backKey.isPressed()),
            a(minecraftClient.options.leftKey.isPressed()) - a(minecraftClient.options.rightKey.isPressed()),
            floatValue35,
            floatValue36,
            minecraftClient.player.getAttackCooldownProgress(0.0F),
            (float)Math.min(this.v, 40) / 40.0F,
            floatValue21 == 0.0F && floatValue22 == 0.0F ? 1.0F : 0.0F,
            a(livingEntity2.isOnGround())
         };
         float[] floatValueArray38 = new float[value1.f];

         for (int intValue39 = 0; intValue39 < floatValueArray38.length; intValue39++) {
            floatValueArray38[intValue39] = (float)this.h.nextGaussian();
         }

         if (this.k == null || this.k.length != value1.e) {
            this.k = this.a(value1.e, value5.b);
         }

         float[][] floatValueArrayArray56 = value1.a(this.i, floatValueArray37, this.k, floatValueArray38);
         float floatValue40 = floatValueArrayArray56[0][0];
         float floatValue41 = floatValueArrayArray56[0][1];
         if (value1.a() && this.h.nextFloat() < value1.a(this.i, floatValueArray37)) {
            floatValue40 = 0.0F;
            floatValue41 = 0.0F;
         }

         float floatValue42 = (float)Math.hypot((double)floatValue21, (double)floatValue22);
         if (value5.a) {
            if (floatValue42 > 25.0F) {
               this.w = true;
            } else if (floatValue42 < 10.0F) {
               this.w = false;
            }

            this.x = this.x + ((this.w ? 1.0F : 0.0F) - this.x) * (this.w ? 0.25F : 0.08F);
            if (this.x > 0.01F) {
               float floatValue43 = 25.0F;
               float floatValue44 = MathHelper.clamp(floatValue21 * 0.5F, -floatValue43, floatValue43);
               float floatValue45 = MathHelper.clamp(floatValue22 * 0.5F, -floatValue43, floatValue43);
               floatValue40 += (floatValue44 - floatValue40) * this.x;
               floatValue41 += (floatValue45 - floatValue41) * this.x;
            }
         } else {
            this.x = 0.0F;
         }

         double doubleValue57 = dev.leg1tka.rotation.RotationContext.a();
         double doubleValue58 = (double)floatValue40 + this.t;
         double doubleValue47 = (double)floatValue41 + this.u;
         double doubleValue49 = doubleValue57 > 1.0E-6 ? (double)Math.round(doubleValue58 / doubleValue57) * doubleValue57 : doubleValue58;
         double doubleValue51 = doubleValue57 > 1.0E-6 ? (double)Math.round(doubleValue47 / doubleValue57) * doubleValue57 : doubleValue47;
         this.t = doubleValue58 - doubleValue49;
         this.u = doubleValue47 - doubleValue51;
         float floatValue53 = MathHelper.clamp((float)((double)floatValue4 + doubleValue51), -90.0F, 90.0F);
         if (Math.abs((double)floatValue4 + doubleValue51) > 90.0) {
            this.u = 0.0;
         }

         this.c = floatValue42;
         this.d = this.x;
         this.e = (float)doubleValue49;
         this.f = floatValue53 - floatValue4;
         this.g = System.nanoTime() - longValue6;
         return new float[]{(float)((double)floatValue3 + doubleValue49), floatValue53};
      } else {
         return null;
      }
   }

   private float[] a(int intValue1, float floatValue2) {
      float[] floatValueArray3 = new float[intValue1];

      for (int intValue4 = 0; intValue4 < intValue1; intValue4++) {
         floatValueArray3[intValue4] = (float)this.h.nextGaussian() * floatValue2;
      }

      return floatValueArray3;
   }

   private static float a(boolean flag0) {
      return flag0 ? 1.0F : 0.0F;
   }

   private static float[] a(Vec3d vec3d0, Vec3d vec3d1) {
      double doubleValue2 = vec3d1.x - vec3d0.x;
      double doubleValue4 = vec3d1.y - vec3d0.y;
      double doubleValue6 = vec3d1.z - vec3d0.z;
      double doubleValue8 = Math.sqrt(doubleValue2 * doubleValue2 + doubleValue6 * doubleValue6);
      return new float[]{(float)(Math.toDegrees(Math.atan2(doubleValue6, doubleValue2)) - 90.0), (float)(-Math.toDegrees(Math.atan2(doubleValue4, doubleValue8)))};
   }

   public static final class a {
      public boolean a = true;
      public float b = 1.0F;
      public boolean c = true;

      public a() {
      }
   }
}
