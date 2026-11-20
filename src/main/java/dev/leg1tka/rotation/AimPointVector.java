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

import net.minecraft.util.math.Vec3d;

public final class AimPointVector {
   private final Vec3d[] a;
   private final long[] b;
   private int c;
   private int d;
   private long e;

   public AimPointVector(int intValue1) {
      this.a = new Vec3d[intValue1];
      this.b = new long[intValue1];
   }

   public void a() {
      this.c = 0;
      this.d = 0;
      this.e = 0L;
   }

   public void a(Vec3d vec3d1, long longValue2) {
      if (longValue2 - this.e >= 15L || this.c <= 0) {
         this.e = longValue2;
         this.a[this.d] = vec3d1;
         this.b[this.d] = longValue2;
         this.d = (this.d + 1) % this.a.length;
         if (this.c < this.a.length) {
            this.c++;
         }
      }
   }

   public Vec3d a(long longValue1, long longValue3) {
      if (this.c == 0) {
         return null;
      } else {
         long longValue5 = longValue1 - longValue3;
         Vec3d vec3d7 = null;
         long longValue8 = Long.MAX_VALUE;

         for (int intValue10 = 0; intValue10 < this.c; intValue10++) {
            int intValue11 = (this.d - 1 - intValue10 + this.a.length) % this.a.length;
            long longValue12 = Math.abs(this.b[intValue11] - longValue5);
            if (longValue12 < longValue8) {
               longValue8 = longValue12;
               vec3d7 = this.a[intValue11];
            }
         }

         return vec3d7;
      }
   }
}
