package dev.leg1tka.network;

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
import java.util.function.IntConsumer;

final class SequenceCallbackQueue {
   static int a;
   static int b;
   static int c;
   private static int d;
   private final ArrayDeque<dev.leg1tka.network.SequenceCallbackQueue.a> e = new ArrayDeque<>();
   private final IntConsumer f;
   private boolean g;

   SequenceCallbackQueue(IntConsumer intConsumer1) {
      this.f = intConsumer1;
   }

   synchronized dev.leg1tka.network.SequenceCallbackQueue.a a(int intValue1) {
      dev.leg1tka.network.SequenceCallbackQueue.a value2 = new dev.leg1tka.network.SequenceCallbackQueue.a(intValue1);
      this.e.addLast(value2);

      while (this.e.size() > 2048) {
         this.e.pollFirst();
      }

      return value2;
   }

   synchronized void a(dev.leg1tka.network.SequenceCallbackQueue.a value1) {
      value1.b = false;
      this.b();
   }

   synchronized int a(boolean flag1, int intValue2) {
      dev.leg1tka.network.SequenceCallbackQueue.a value3 = null;

      for (dev.leg1tka.network.SequenceCallbackQueue.a value5 : this.e) {
         if (value5.a == intValue2 && (flag1 ? !value5.d : !value5.c)) {
            value3 = value5;
            break;
         }
      }

      if (value3 == null) {
         return flag1 ? 1 : 0;
      } else {
         if (flag1) {
            value3.d = true;
         } else {
            value3.c = true;
         }

         boolean flag6 = value3.e;
         this.b();
         return !flag1 && !flag6 && value3.e ? 2 : 1;
      }
   }

   synchronized void a(boolean flag1) {
      this.g = flag1;
      this.b();
   }

   synchronized void a() {
      this.e.clear();
      this.g = false;
   }

   private void b() {
      for (dev.leg1tka.network.SequenceCallbackQueue.a value2 : this.e) {
         if (!value2.e) {
            if (this.g && value2.b ? !value2.d : !value2.c) {
               break;
            }

            value2.e = true;
            this.f.accept(value2.a);
         }
      }

      while (!this.e.isEmpty() && ((dev.leg1tka.network.SequenceCallbackQueue.a)this.e.peekFirst()).e && ((dev.leg1tka.network.SequenceCallbackQueue.a)this.e.peekFirst()).c) {
         this.e.pollFirst();
      }
   }

   static class a {
      final int a;
      boolean b = true;
      boolean c;
      boolean d;
      boolean e;

      a(int intValue1) {
         this.a = intValue1;
      }
   }
}
