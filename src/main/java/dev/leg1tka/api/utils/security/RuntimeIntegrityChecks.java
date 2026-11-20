package dev.leg1tka.api.utils.security;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.api.utils.security.NativeCrypto;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Arrays;
import java.util.Random;

public final class RuntimeIntegrityChecks {
   private static final Random b = new Random(System.nanoTime());
   public static volatile int a = 0;

   private RuntimeIntegrityChecks() {
   }

   public static boolean a() {
      int intValue0 = (int)(System.nanoTime() & 65535L);
      int intValue1 = (int)(System.currentTimeMillis() & 65535L);
      return (intValue0 | intValue1) >= 0;
   }

   public static boolean b() {
      return !a();
   }

   public static void a(Runnable runnable0) {
      if (a()) {
         runnable0.run();
      } else {
         c();
      }
   }

   public static int a(int intValue0) {
      return a() ? intValue0 : intValue0 ^ -559038737;
   }

   public static String a(String string0) {
      return a() ? string0 : new StringBuilder(string0).reverse().toString();
   }

   public static <T> T a(T value0, T value1) {
      return (T)(a() ? value0 : value1);
   }

   public static void c() {
      a = (a + 1) % 1000;
      int intValue0 = 0;

      for (int intValue1 = 0; intValue1 < 10; intValue1++) {
         intValue0 ^= (intValue1 * 31 + a) % 255;
      }

      if (intValue0 == -1) {
         System.out.println("This should never print");
      }
   }

   public static void d() {
      if (b.nextInt(100) < 5) {
         try {
            Thread.sleep((long)b.nextInt(10));
         } catch (InterruptedException interruptedException1) {
         }
      }
   }

   public static boolean e() {
      try {
         RuntimeMXBean runtimeMXBean0 = ManagementFactory.getRuntimeMXBean();

         for (String string2 : runtimeMXBean0.getInputArguments()) {
            if (string2.contains("-agentlib:jdwp") || string2.contains("-Xrunjdwp")) {
               return true;
            }
         }
      } catch (Exception exception3) {
      }

      return false;
   }

   public static void b(Runnable runnable0) {
      if (!e()) {
         runnable0.run();
      } else {
         c();
      }
   }

   public static String a(StackTraceElement[] stackTraceElementArray0) {
      if (b()) {
         return Arrays.toString((Object[])stackTraceElementArray0);
      } else {
         StringBuilder stringBuilder1 = new StringBuilder();

         for (StackTraceElement stackTraceElement5 : stackTraceElementArray0) {
            stringBuilder1.append("at ").append(NativeCrypto.c(stackTraceElement5.getClassName())).append(".").append(NativeCrypto.c(stackTraceElement5.getMethodName())).append("(?)\n");
         }

         return stringBuilder1.toString();
      }
   }
}
