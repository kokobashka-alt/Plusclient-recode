package dev.leg1tka.rotation.neural;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import it.unimi.dsi.fastutil.doubles.Double2IntOpenHashMap;
import it.unimi.dsi.fastutil.doubles.Double2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayDeque;

public final class RotationDeltaHistory {
   private static int c;
   private static int d;
   private static double e;
   private static double f;
   private static double g;
   private static final double h = (double)((float)Math.pow(0.2F, 3.0) * 8.0F) * 0.15 - 0.001;
   public double a;
   public double b;
   private double i;
   private double j;
   private final dev.leg1tka.rotation.neural.RotationDeltaHistory.a k = new dev.leg1tka.rotation.neural.RotationDeltaHistory.a(80);
   private final dev.leg1tka.rotation.neural.RotationDeltaHistory.a l = new dev.leg1tka.rotation.neural.RotationDeltaHistory.a(80);
   private double m;
   private double n;
   private boolean o;

   public RotationDeltaHistory() {
   }

   public boolean a() {
      return this.k.c > 15;
   }

   public boolean b() {
      return this.l.c > 15;
   }

   public void a(float floatValue1, float floatValue2, float floatValue3, float floatValue4) {
      double doubleValue5 = (double)Math.abs(floatValue3 - floatValue1);
      double doubleValue7 = (double)Math.abs(floatValue4 - floatValue2);
      double doubleValue9 = a(doubleValue5, this.m);
      if (doubleValue5 > 0.0 && doubleValue5 < 5.0 && doubleValue9 > h) {
         double doubleValue11 = (double)Math.ulp(Math.max(Math.abs(floatValue1), Math.abs(floatValue3)));
         if (doubleValue11 < doubleValue9 * 0.0625) {
            if (this.m != 0.0) {
               this.k.a(doubleValue9, Math.max(0.001, doubleValue11 * 2.0));
            }

            this.m = doubleValue5;
         }
      }

      double doubleValue13 = a(doubleValue7, this.n);
      if (doubleValue7 > 0.0 && doubleValue7 < 5.0 && doubleValue13 > h) {
         if (this.n != 0.0) {
            this.l.a(doubleValue13, 0.001);
         }

         this.n = doubleValue7;
      }

      if (this.k.a() > 15) {
         this.k.c();
         if (this.k.c > 15) {
            this.a = this.k.b;
         }
      }

      if (this.l.a() > 15) {
         this.l.c();
         if (this.l.c > 15) {
            this.b = this.l.b;
         }
      }

      if (this.a > 0.0) {
         this.i = doubleValue5 / this.a;
      }

      if (this.b > 0.0) {
         this.j = doubleValue7 / this.b;
      }

      this.o = true;
   }

   public float c() {
      return this.o ? (float)this.i : 0.0F;
   }

   public float d() {
      float floatValue1 = this.o ? (float)this.j : 0.0F;
      this.o = false;
      return floatValue1;
   }

   public void e() {
      this.k.b();
      this.l.b();
      this.m = this.n = 0.0;
      this.a = this.b = 0.0;
      this.i = this.j = 0.0;
      this.o = false;
   }

   static double a(double doubleValue0, double doubleValue2) {
      if (doubleValue0 == 0.0) {
         return 0.0;
      } else {
         double doubleValue4 = doubleValue0;
         double doubleValue6 = doubleValue2;
         if (doubleValue0 < doubleValue2) {
            doubleValue4 = doubleValue2;
            doubleValue6 = doubleValue0;
         }

         while (doubleValue6 > h) {
            double doubleValue8 = doubleValue4 - Math.floor(doubleValue4 / doubleValue6) * doubleValue6;
            doubleValue4 = doubleValue6;
            doubleValue6 = doubleValue8;
         }

         return doubleValue4;
      }
   }

   static class a {
      static double a;
      private final int d;
      private final ArrayDeque e;
      private final Double2IntOpenHashMap f = new Double2IntOpenHashMap();
      double b;
      int c;

      a(int intValue1) {
         this.d = intValue1;
         this.e = new ArrayDeque(intValue1);
      }

      int a() {
         return this.e.size();
      }

      void a(double doubleValue1, double doubleValue3) {
         if (this.e.size() >= this.d) {
            this.d();
         }

         ObjectIterator objectIterator5 = this.f.double2IntEntrySet().iterator();

         while (objectIterator5.hasNext()) {
            Entry entry6 = (Entry)objectIterator5.next();
            if (Math.abs(entry6.getDoubleKey() - doubleValue1) < doubleValue3) {
               entry6.setValue(entry6.getIntValue() + 1);
               this.e.add(entry6.getDoubleKey());
               return;
            }
         }

         this.f.put(doubleValue1, 1);
         this.e.add(doubleValue1);
      }

      private void d() {
         Double double1 = (Double)this.e.poll();
         if (double1 != null) {
            int intValue2 = this.f.get((double)double1);
            if (intValue2 == 1) {
               this.f.remove((Object)double1);
            } else {
               this.f.put((double)double1, intValue2 - 1);
            }
         }
      }

      void b() {
         this.e.clear();
         this.f.clear();
         this.b = 0.0;
         this.c = 0;
      }

      void c() {
         int intValue1 = 0;
         double doubleValue2 = 0.0;
         ObjectIterator objectIterator4 = this.f.double2IntEntrySet().iterator();

         while (objectIterator4.hasNext()) {
            Entry entry5 = (Entry)objectIterator4.next();
            if (entry5.getIntValue() > intValue1) {
               intValue1 = entry5.getIntValue();
               doubleValue2 = entry5.getDoubleKey();
            }
         }

         this.b = doubleValue2;
         this.c = intValue1;
      }
   }
}
