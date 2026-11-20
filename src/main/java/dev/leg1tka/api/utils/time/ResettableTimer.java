package dev.leg1tka.api.utils.time;

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

public class ResettableTimer implements dev.leg1tka.api.ClientAccess {
   private long c = System.currentTimeMillis();
   private long d;

   public ResettableTimer() {
      this.b();
   }

   public static dev.leg1tka.api.utils.time.ResettableTimer a() {
      return new dev.leg1tka.api.utils.time.ResettableTimer();
   }

   public boolean a(long longValue1) {
      return System.currentTimeMillis() - longValue1 >= this.d;
   }

   public void b() {
      this.d = System.currentTimeMillis();
   }

   public long c() {
      return System.currentTimeMillis() - this.d;
   }

   public double d() {
      return minecraftClient.getCurrentFps() > 0 ? 1.0 / (double)minecraftClient.getCurrentFps() : 1.0;
   }

   public boolean b(long longValue1) {
      boolean flag3 = this.d(System.nanoTime() - this.d) >= longValue1;
      if (flag3) {
         this.b();
      }

      return flag3;
   }

   public boolean c(long longValue1) {
      return System.currentTimeMillis() - this.c > longValue1;
   }

   public long d(long longValue1) {
      return longValue1 / 1000000L;
   }

   public long e() {
      return System.currentTimeMillis() - this.c;
   }

   public void e(long longValue1) {
      this.c = longValue1;
   }

   @Generated
   public long f() {
      return this.c;
   }

   @Generated
   public long g() {
      return this.d;
   }

   @Generated
   public void f(long longValue1) {
      this.c = longValue1;
   }

   @Generated
   public void g(long longValue1) {
      this.d = longValue1;
   }
}
