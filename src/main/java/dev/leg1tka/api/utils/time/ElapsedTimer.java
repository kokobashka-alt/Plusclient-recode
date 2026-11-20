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

public class ElapsedTimer {
   public long a = System.currentTimeMillis();

   public ElapsedTimer() {
   }

   public void a() {
      this.a = System.currentTimeMillis();
   }

   public boolean a(long longValue1) {
      return System.currentTimeMillis() - this.a > longValue1;
   }

   public void b(long longValue1) {
      this.a = System.currentTimeMillis() + longValue1;
   }

   public void c(long longValue1) {
      this.a = longValue1;
   }

   public long b() {
      return System.currentTimeMillis() - this.a;
   }

   public boolean c() {
      return System.currentTimeMillis() - this.a <= 0L;
   }

   public boolean d() {
      return this.a < System.currentTimeMillis();
   }

   public boolean d(long longValue1) {
      return System.currentTimeMillis() - this.a >= longValue1;
   }

   @Generated
   public long e() {
      return this.a;
   }
}
