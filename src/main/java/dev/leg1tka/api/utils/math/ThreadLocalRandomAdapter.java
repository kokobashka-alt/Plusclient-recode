package dev.leg1tka.api.utils.math;

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
import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomAdapter extends Random {
   private final ThreadLocalRandom a = ThreadLocalRandom.current();
   private Random b = null;
   private volatile boolean c;
   private volatile boolean d;
   private volatile long e;

   public ThreadLocalRandomAdapter() {
   }

   private void a() {
      if (this.b == null) {
         this.b = new Random(this.e);
         this.d = false;
      } else if (this.d) {
         this.b.setSeed(this.e);
         this.d = false;
      }
   }

   public static long a(long longValue0, long longValue2) {
      longValue0 *= longValue0 * 6364136223846793005L + 1442695040888963407L;
      return longValue0 + longValue2;
   }

   @Override
   public void setSeed(long longValue1) {
      this.e = longValue1;
      this.c = true;
      this.d = true;
   }

   @Override
   public void nextBytes(byte[] byteValueArray1) {
      if (this.c) {
         this.a();
         this.b.nextBytes(byteValueArray1);
      } else {
         this.a.nextBytes(byteValueArray1);
      }
   }

   @Override
   public int nextInt() {
      if (this.c) {
         this.a();
         return this.b.nextInt();
      } else {
         return this.a.nextInt();
      }
   }

   @Override
   public int nextInt(int intValue1) {
      if (this.c) {
         this.a();
         return this.b.nextInt(intValue1);
      } else {
         return this.a.nextInt(intValue1);
      }
   }

   @Override
   public long nextLong() {
      if (this.c) {
         this.a();
         return this.b.nextLong();
      } else {
         return this.a.nextLong();
      }
   }

   @Override
   public boolean nextBoolean() {
      if (this.c) {
         this.a();
         return this.b.nextBoolean();
      } else {
         return this.a.nextBoolean();
      }
   }

   @Override
   public float nextFloat() {
      if (this.c) {
         this.a();
         return this.b.nextFloat();
      } else {
         return this.a.nextFloat();
      }
   }

   @Override
   public double nextDouble() {
      if (this.c) {
         this.a();
         return this.b.nextDouble();
      } else {
         return this.a.nextDouble();
      }
   }

   @Override
   public double nextGaussian() {
      if (this.c) {
         this.a();
         return this.b.nextGaussian();
      } else {
         return this.a.nextGaussian();
      }
   }
}
