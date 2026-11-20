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

import java.util.ArrayDeque;
import java.util.Iterator;

public final class TargetMotionHistory {
   static double a;
   static double b;
   private final ArrayDeque c = new ArrayDeque();
   private dev.leg1tka.rotation.TargetMotionHistory.c d;

   public TargetMotionHistory() {
   }

   public void a(long longValue1, dev.leg1tka.rotation.TargetMotionHistory.a value3) {
      dev.leg1tka.rotation.TargetMotionHistory.c value4 = (dev.leg1tka.rotation.TargetMotionHistory.c)this.c.peekLast();
      if (value4 != null && (longValue1 < value4.time || longValue1 - value4.time > 250L || value3.a(value4.box) > 9.0)) {
         this.a();
         value4 = null;
      }

      if (value4 != null && longValue1 == value4.time) {
         this.c.removeLast();
      }

      this.c.addLast(new dev.leg1tka.rotation.TargetMotionHistory.c(longValue1, value3));

      while (this.c.size() > 64 || !this.c.isEmpty() && longValue1 - ((dev.leg1tka.rotation.TargetMotionHistory.c)this.c.peekFirst()).time > 400L) {
         this.c.removeFirst();
      }
   }

   public dev.leg1tka.rotation.TargetMotionHistory.d a(long longValue1, dev.leg1tka.rotation.TargetMotionHistory.a value3, double doubleValue4, double doubleValue6, double doubleValue8, int intValue10, double doubleValue11, dev.leg1tka.rotation.TargetMotionHistory.b value13, double doubleValue14, long longValue16) {
      long longValue18 = Math.min(a(intValue10, doubleValue11), longValue16);
      dev.leg1tka.rotation.TargetMotionHistory.c value20 = (dev.leg1tka.rotation.TargetMotionHistory.c)this.c.peekLast();
      if (value20 != null && longValue1 >= value20.time && longValue1 - value20.time <= 100L) {
         dev.leg1tka.rotation.TargetMotionHistory.a value21 = value20.box;
         dev.leg1tka.rotation.TargetMotionHistory.c value22 = value20;
         Iterator iterator23 = this.c.descendingIterator();

         while (iterator23.hasNext()) {
            dev.leg1tka.rotation.TargetMotionHistory.c value24 = (dev.leg1tka.rotation.TargetMotionHistory.c)iterator23.next();
            value22 = value24;
            if (value20.time - value24.time >= 100L) {
               break;
            }
         }

         long longValue40 = value20.time - value22.time;
         double doubleValue26 = longValue40 < 50L ? 0.0 : value21.b(value22.box) * 1000.0 / (double)longValue40;
         double doubleValue28 = Math.min(a(doubleValue26), Math.min(doubleValue14, 4.2));
         double doubleValue30 = value21.a(doubleValue4, doubleValue6, doubleValue8);
         if (doubleValue30 <= 3.0) {
            dev.leg1tka.rotation.TargetMotionHistory.a value41 = value3.a(doubleValue4, doubleValue6, doubleValue8) <= 3.0 ? value3 : value21;
            return !value41.a(doubleValue4, doubleValue6, doubleValue8, value13, doubleValue28) && value21.a(doubleValue4, doubleValue6, doubleValue8, value13, doubleValue28)
               ? this.a(value21, longValue18, doubleValue28, doubleValue26, "remote_in_reach")
               : this.a(value41, longValue18, doubleValue28, doubleValue26, "live_in_reach");
         } else if (doubleValue30 > 4.2) {
            return this.a(value21, longValue18, doubleValue28, doubleValue26, "outside_max_reach");
         } else {
            dev.leg1tka.rotation.TargetMotionHistory.a value32 = doubleValue30 <= doubleValue28 && value3.a(doubleValue4, doubleValue6, doubleValue8) <= doubleValue28 ? value3 : value21;
            boolean flag33 = doubleValue30 <= doubleValue28 && value32.a(doubleValue4, doubleValue6, doubleValue8) <= doubleValue28;
            if (flag33 && value32.a(doubleValue4, doubleValue6, doubleValue8, value13, doubleValue28)) {
               return this.a(value32, longValue18, doubleValue28, doubleValue26, "moving_in_reach");
            } else if (flag33 && value21.a(doubleValue4, doubleValue6, doubleValue8, value13, doubleValue28)) {
               return this.a(value21, longValue18, doubleValue28, doubleValue26, "remote_in_reach");
            } else {
               double doubleValue34 = value21.a(doubleValue4, doubleValue6, doubleValue8) - value22.box.a(doubleValue4, doubleValue6, doubleValue8);
               if (!(doubleValue26 <= 0.15) && (flag33 || !(doubleValue34 <= 0.03))) {
                  if (a(this.d, value20, longValue1, longValue18, doubleValue4, doubleValue6, doubleValue8, value13, doubleValue28)) {
                     return new dev.leg1tka.rotation.TargetMotionHistory.d(this.d.box, true, longValue1 - this.d.time, longValue18, doubleValue28, doubleValue26, "held_history");
                  } else {
                     iterator23 = this.c.descendingIterator();

                     while (iterator23.hasNext()) {
                        dev.leg1tka.rotation.TargetMotionHistory.c value36 = (dev.leg1tka.rotation.TargetMotionHistory.c)iterator23.next();
                        long longValue37 = longValue1 - value36.time;
                        if (longValue37 > longValue18) {
                           break;
                        }

                        if (a(value36, value20, longValue1, longValue18, doubleValue4, doubleValue6, doubleValue8, value13, doubleValue28)) {
                           this.d = value36;
                           return new dev.leg1tka.rotation.TargetMotionHistory.d(value36.box, true, longValue37, longValue18, doubleValue28, doubleValue26, flag33 ? "ray_history" : "recent_reachable_history");
                        }
                     }

                     return this.a(value32, longValue18, doubleValue28, doubleValue26, "no_reachable_history");
                  }
               } else {
                  return this.a(value32, longValue18, doubleValue28, doubleValue26, "no_history_benefit");
               }
            }
         }
      } else {
         return this.a(value3, longValue18, 3.0, 0.0, "no_fresh_history");
      }
   }

   private dev.leg1tka.rotation.TargetMotionHistory.d a(dev.leg1tka.rotation.TargetMotionHistory.a value1, long longValue2, double doubleValue4, double doubleValue6, String string8) {
      this.d = null;
      return new dev.leg1tka.rotation.TargetMotionHistory.d(value1, false, 0L, longValue2, doubleValue4, doubleValue6, string8);
   }

   private static boolean a(dev.leg1tka.rotation.TargetMotionHistory.c value0, dev.leg1tka.rotation.TargetMotionHistory.c value1, long longValue2, long longValue4, double doubleValue6, double doubleValue8, double doubleValue10, dev.leg1tka.rotation.TargetMotionHistory.b value12, double doubleValue13) {
      return value0 != null
         && value0.time < value1.time
         && longValue2 - value0.time >= 0L
         && longValue2 - value0.time <= longValue4
         && value0.box.a(value1.box) > 1.0E-8
         && value0.box.a(doubleValue6, doubleValue8, doubleValue10) <= doubleValue13
         && value0.box.a(doubleValue6, doubleValue8, doubleValue10, value12, doubleValue13);
   }

   static double a(double doubleValue0) {
      if (Double.isFinite(doubleValue0) && !(doubleValue0 <= 0.15)) {
         double doubleValue2 = Math.min(1.0, (doubleValue0 - 0.15) / 4.85);
         return Math.min(4.2, 3.0 + 4.0 * doubleValue2);
      } else {
         return 3.0;
      }
   }

   static long a(int intValue0, double doubleValue1) {
      double doubleValue3 = 1000.0 / Math.max(10.0, Math.min(20.0, Double.isFinite(doubleValue1) ? doubleValue1 : 20.0));
      return Math.max(100L, Math.min(200L, Math.round((double)Math.max(0, intValue0) * 0.5 + 2.0 * doubleValue3)));
   }

   public dev.leg1tka.rotation.TargetMotionHistory.a a(long longValue1, long longValue3) {
      dev.leg1tka.rotation.TargetMotionHistory.a value5 = null;
      Iterator iterator6 = this.c.descendingIterator();

      while (iterator6.hasNext()) {
         dev.leg1tka.rotation.TargetMotionHistory.c value7 = (dev.leg1tka.rotation.TargetMotionHistory.c)iterator6.next();
         if (longValue1 - value7.time > longValue3) {
            break;
         }

         value5 = value7.box;
      }

      return value5;
   }

   public void a() {
      this.c.clear();
      this.d = null;
   }

   public static record a(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)  {






      double a(double doubleValue1, double doubleValue3, double doubleValue5) {
         double doubleValue7 = Math.max(Math.max(this.minX - doubleValue1, doubleValue1 - this.maxX), 0.0);
         double doubleValue9 = Math.max(Math.max(this.minY - doubleValue3, doubleValue3 - this.maxY), 0.0);
         double doubleValue11 = Math.max(Math.max(this.minZ - doubleValue5, doubleValue5 - this.maxZ), 0.0);
         return Math.sqrt(doubleValue7 * doubleValue7 + doubleValue9 * doubleValue9 + doubleValue11 * doubleValue11);
      }

      double a(dev.leg1tka.rotation.TargetMotionHistory.a value1) {
         double doubleValue2 = (this.minX + this.maxX - value1.minX - value1.maxX) * 0.5;
         double doubleValue4 = this.minY - value1.minY;
         double doubleValue6 = (this.minZ + this.maxZ - value1.minZ - value1.maxZ) * 0.5;
         return doubleValue2 * doubleValue2 + doubleValue4 * doubleValue4 + doubleValue6 * doubleValue6;
      }

      double b(dev.leg1tka.rotation.TargetMotionHistory.a value1) {
         double doubleValue2 = (this.minX + this.maxX - value1.minX - value1.maxX) * 0.5;
         double doubleValue4 = (this.minZ + this.maxZ - value1.minZ - value1.maxZ) * 0.5;
         return Math.hypot(doubleValue2, doubleValue4);
      }

      boolean a(double doubleValue1, double doubleValue3, double doubleValue5, dev.leg1tka.rotation.TargetMotionHistory.b value7, double doubleValue8) {
         if (value7 == null) {
            return true;
         } else {
            double doubleValue10 = 0.0;
            double doubleValue12 = doubleValue8;
            double doubleValue14 = Math.max(0.0, value7.margin);

            for (int intValue16 = 0; intValue16 < 3; intValue16++) {
               double doubleValue17 = intValue16 == 0 ? doubleValue1 : (intValue16 == 1 ? doubleValue3 : doubleValue5);
               double doubleValue19 = intValue16 == 0 ? value7.x : (intValue16 == 1 ? value7.y : value7.z);
               double doubleValue21 = (intValue16 == 0 ? this.minX : (intValue16 == 1 ? this.minY : this.minZ)) - doubleValue14;
               double doubleValue23 = (intValue16 == 0 ? this.maxX : (intValue16 == 1 ? this.maxY : this.maxZ)) + doubleValue14;
               if (Math.abs(doubleValue19) < 1.0E-9) {
                  if (doubleValue17 < doubleValue21 || doubleValue17 > doubleValue23) {
                     return false;
                  }
               } else {
                  double doubleValue25 = (doubleValue21 - doubleValue17) / doubleValue19;
                  double doubleValue27 = (doubleValue23 - doubleValue17) / doubleValue19;
                  doubleValue10 = Math.max(doubleValue10, Math.min(doubleValue25, doubleValue27));
                  doubleValue12 = Math.min(doubleValue12, Math.max(doubleValue25, doubleValue27));
                  if (doubleValue10 > doubleValue12) {
                     return false;
                  }
               }
            }

            return true;
         }
      }
   }

   public static record b(double x, double y, double z, double margin)  {



}

   static record c(long time, dev.leg1tka.rotation.TargetMotionHistory.a box)  {

}

   public static record d(dev.leg1tka.rotation.TargetMotionHistory.a box, boolean historical, long ageMs, long windowMs, double reach, double speed, String reason)  {






}
}
