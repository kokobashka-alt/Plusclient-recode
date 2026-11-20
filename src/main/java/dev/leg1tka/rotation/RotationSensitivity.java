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

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class RotationSensitivity implements dev.leg1tka.api.ClientAccess {
   public static final dev.leg1tka.rotation.RotationSensitivity c = new dev.leg1tka.rotation.RotationSensitivity();
   private SmoothMode d = SmoothMode.DISABLED;
   private boolean e = false;
   private boolean f = false;
   private float g = 0.0F;
   private float h = 0.0F;
   private float i = 0.0F;
   private float j = 0.0F;
   private float k = 0.0F;
   private float l = 0.0F;
   private long m = 0L;
   private long n = 0L;
   private static long o;
   private boolean p = false;
   private int q = 0;
   private long r = 0L;
   private long s = 0L;
   private float t = 0.0F;
   private float u = 0.0F;
   private float v = 0.0F;
   private float w = 0.0F;
   private float x = 0.0F;
   private float y = 0.0F;
   private static long z;
   private static long A;
   private double B = 0.0;

   public RotationSensitivity() {
   }

   public void a(String string1) {
      this.d = switch (string1) {
         case "Плавная" -> SmoothMode.SMOOTH;
         case "Испытательная" -> SmoothMode.TEST;
         default -> SmoothMode.DISABLED;
      };
   }

   public void a(boolean flag1, boolean flag2) {
      SmoothMode smoothMode3;
      if (flag2) {
         smoothMode3 = SmoothMode.TEST;
      } else if (flag1) {
         smoothMode3 = SmoothMode.SMOOTH;
      } else {
         smoothMode3 = SmoothMode.DISABLED;
      }

      this.d = smoothMode3;
   }

   public SmoothMode a() {
      return this.d;
   }

   public void b() {
      if (minecraftClient.player != null && this.d != SmoothMode.DISABLED) {
         this.k = minecraftClient.player.getYaw();
         this.l = minecraftClient.player.getPitch();
         this.g = this.k;
         this.h = this.l;
         this.i = this.k;
         this.j = this.l;
         this.m = System.currentTimeMillis();
         this.n = System.currentTimeMillis();
         this.f = true;
         this.e = false;
      }
   }

   public void c() {
      if (minecraftClient.player != null && this.d != SmoothMode.DISABLED) {
         this.g = minecraftClient.player.getYaw();
         this.h = minecraftClient.player.getPitch();
         this.i = this.k + (float)((Math.random() - 0.5) * 2.0);
         this.j = this.l + (float)((Math.random() - 0.5) * 1.2);
         this.i = MathHelper.wrapDegrees(this.i);
         this.j = MathHelper.clamp(this.j, -90.0F, 90.0F);
         this.m = System.currentTimeMillis();
         this.n = System.currentTimeMillis();
         this.e = true;
         this.f = false;
      }
   }

   public void a(float floatValue1, float floatValue2) {
      if (!this.p) {
         this.t = (ThreadLocalRandom.current().nextFloat() - 0.5F) * 10.0F;
         this.u = (ThreadLocalRandom.current().nextFloat() - 0.5F) * 5.0F;
         this.v = floatValue1;
         this.w = floatValue2;
         this.x = floatValue1 + this.t;
         this.y = MathHelper.clamp(floatValue2 + this.u, -90.0F, 90.0F);
         this.r = System.currentTimeMillis();
         this.q = 1;
         this.p = true;
      }
   }

   public boolean d() {
      if (this.d == SmoothMode.DISABLED) {
         return false;
      } else if (this.f) {
         this.a(true);
         return true;
      } else if (this.e) {
         this.a(false);
         return true;
      } else {
         return false;
      }
   }

   public float[] a(float floatValue1, float floatValue2, LivingEntity livingEntity3, String string4) {
      if (minecraftClient.player == null) {
         return new float[]{floatValue1, floatValue2};
      } else {
         float floatValue5 = minecraftClient.player.getYaw();
         float floatValue6 = minecraftClient.player.getPitch();
         long longValue7 = System.currentTimeMillis();
         if (this.f) {
            this.i = floatValue1;
            this.j = floatValue2;
         }

         if (this.d == SmoothMode.DISABLED) {
            return new float[]{floatValue1, floatValue2};
         } else {
            if (this.d == SmoothMode.TEST) {
               if (!this.p && longValue7 >= this.s) {
                  float floatValue9 = (float)Math.hypot((double)MathHelper.wrapDegrees(floatValue1 - floatValue5), (double)(floatValue2 - floatValue6));
                  if (floatValue9 < 12.0F) {
                     this.a(floatValue5, floatValue6);
                     this.h();
                  }
               }

               if (this.p) {
                  float[] floatValueArray10 = this.a(floatValue1, floatValue2, floatValue5, floatValue6, longValue7);
                  if (floatValueArray10 != null) {
                     floatValue1 = floatValueArray10[0];
                     floatValue2 = floatValueArray10[1];
                  }
               }
            }

            return this.a(floatValue1, floatValue2, string4);
         }
      }
   }

   public void e() {
      this.f = false;
      this.e = false;
      this.p = false;
      this.q = 0;
      this.B = 0.0;
      this.h();
   }

   public boolean f() {
      return this.f || this.e;
   }

   public boolean g() {
      return this.p;
   }

   private void a(boolean flag1) {
      if (minecraftClient.player != null) {
         long longValue2 = System.currentTimeMillis();
         long longValue4 = longValue2 - this.m;
         if (longValue4 >= 1200L) {
            if (!flag1) {
               dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(this.i, this.j), 180.0F, 180.0F, 0.0F, 0, 1);
            }

            this.e = false;
            this.f = false;
         } else {
            double doubleValue6 = (double)longValue4 / 1200.0;
            doubleValue6 = doubleValue6 < 0.5 ? 8.0 * doubleValue6 * doubleValue6 * doubleValue6 * doubleValue6 : 1.0 - Math.pow(-2.0 * doubleValue6 + 2.0, 4.0) / 2.0;
            float floatValue8 = (float)((double)this.g + (double)MathHelper.wrapDegrees(this.i - this.g) * doubleValue6);
            float floatValue9 = (float)((double)this.h + (double)(this.j - this.h) * doubleValue6);
            float floatValue10 = (float)(1.0 - doubleValue6 * 0.7);
            float floatValue11 = dev.leg1tka.api.utils.math.InterpolationMath.a((float)((double)this.n * 6.0E-4 + (double)longValue4 * 0.0012));
            float floatValue12 = dev.leg1tka.api.utils.math.InterpolationMath.a((float)((double)this.n * 9.0E-4 + (double)longValue4 * 0.0018));
            floatValue8 += floatValue11 * 0.8F * floatValue10;
            floatValue9 += floatValue12 * 0.6F * floatValue10;
            floatValue8 = MathHelper.wrapDegrees(floatValue8);
            floatValue9 = MathHelper.clamp(floatValue9, -90.0F, 90.0F);
            float floatValue13 = Math.abs(MathHelper.wrapDegrees(floatValue8 - (flag1 ? this.k : this.i)));
            float floatValue14 = Math.abs(floatValue9 - (flag1 ? this.l : this.j));
            float floatValue15 = Math.min(90.0F, floatValue13 * 0.8F + 15.0F);
            float floatValue16 = Math.min(90.0F, floatValue14 * 0.6F + 10.0F);
            dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(floatValue8, floatValue9), floatValue15, floatValue16, floatValue15, floatValue16, 0, 1, false);
         }
      }
   }

   private float[] a(float floatValue1, float floatValue2, float floatValue3, float floatValue4, long longValue5) {
      long longValue7 = longValue5 - this.r;
      if (this.q == 1) {
         if (longValue7 < 120L) {
            float floatValue12 = a((float)longValue7 / 120.0F);
            float floatValue13 = MathHelper.lerp(floatValue12, this.v, this.x);
            float floatValue14 = MathHelper.lerp(floatValue12, this.w, this.y);
            return new float[]{floatValue13, MathHelper.clamp(floatValue14, -90.0F, 90.0F)};
         }

         this.q = 2;
         this.r = longValue5;
         longValue7 = 0L;
      }

      if (this.q == 2) {
         if (longValue7 < 180L) {
            float floatValue9 = a((float)longValue7 / 180.0F);
            float floatValue10 = MathHelper.lerp(floatValue9, this.x, floatValue1);
            float floatValue11 = MathHelper.lerp(floatValue9, this.y, floatValue2);
            return new float[]{floatValue10, MathHelper.clamp(floatValue11, -90.0F, 90.0F)};
         }

         this.p = false;
         this.q = 0;
      }

      return null;
   }

   private void h() {
      this.s = System.currentTimeMillis() + 4000L + (long)(ThreadLocalRandom.current().nextDouble() * 5000.0);
   }

   private float[] a(float floatValue1, float floatValue2, String string3) {
      this.B = this.B + 0.08 + ThreadLocalRandom.current().nextDouble() * 0.04;
      String string5 = string3.toLowerCase();

      float floatValue4 = switch (string5) {
         case "shardac", "shardac/test", "shardac-Storage", "suckytime" -> 0.06F;
         case "legit" -> 0.15F;
         case "wellmine" -> 0.25F;
         default -> 0.2F;
      };
      float floatValue7 = dev.leg1tka.api.utils.math.InterpolationMath.a((float)(this.B * 1.3)) * 0.18F + dev.leg1tka.api.utils.math.InterpolationMath.a((float)(this.B * 2.7)) * 0.09F;
      float floatValue8 = dev.leg1tka.api.utils.math.InterpolationMath.a((float)(this.B * 1.1 + 5.0)) * 0.12F + dev.leg1tka.api.utils.math.InterpolationMath.a((float)(this.B * 2.3 + 3.0)) * 0.06F;
      return new float[]{floatValue1 + floatValue7 * floatValue4, MathHelper.clamp(floatValue2 + floatValue8 * floatValue4 * 0.6F, -90.0F, 90.0F)};
   }

   private static float a(float floatValue0) {
      floatValue0 = MathHelper.clamp(floatValue0, 0.0F, 1.0F);
      return floatValue0 * floatValue0 * (3.0F - 2.0F * floatValue0);
   }

   public static enum SmoothMode {
      DISABLED,
      SMOOTH,
      TEST;

      private SmoothMode() {
      }
   }
}
