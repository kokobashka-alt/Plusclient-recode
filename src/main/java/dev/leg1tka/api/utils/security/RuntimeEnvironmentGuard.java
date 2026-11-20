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

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public final class RuntimeEnvironmentGuard {
   private static final AtomicBoolean a = new AtomicBoolean(false);
   private static final AtomicInteger b = new AtomicInteger(0);
   private static final Random c = new Random(System.nanoTime());
   private static volatile boolean d = false;
   private static volatile boolean e = false;
   private static volatile boolean f = false;

   private RuntimeEnvironmentGuard() {
   }

   public static void a() {
      if (a.compareAndSet(false, true)) {
         dev.leg1tka.api.utils.security.RuntimeIntegrityChecks.a(() -> {
            if (dev.leg1tka.api.utils.security.RuntimeIntegrityChecks.a()) {
               d();
            }
         });
      }
   }

   private static void d() {
      Thread thread0 = new Thread(dev.leg1tka.api.utils.security.RuntimeEnvironmentGuard::e, "ٴ");
      Thread thread1 = new Thread(dev.leg1tka.api.utils.security.RuntimeEnvironmentGuard::f, "ٵ");
      Thread thread2 = new Thread(dev.leg1tka.api.utils.security.RuntimeEnvironmentGuard::g, "ٶ");
      Thread thread3 = new Thread(dev.leg1tka.api.utils.security.RuntimeEnvironmentGuard::h, "ٷ");
      thread0.setDaemon(true);
      thread1.setDaemon(true);
      thread2.setDaemon(true);
      thread3.setDaemon(true);
      thread0.start();
      thread1.start();
      thread2.start();
      thread3.start();
   }

   private static void e() {
      try {
         RuntimeMXBean runtimeMXBean0 = ManagementFactory.getRuntimeMXBean();

         for (String string3 : runtimeMXBean0.getInputArguments()) {
            if (string3.contains("-agentlib:jdwp") || string3.contains("-Xrunjdwp") || string3.contains("-Xdebug") || string3.contains("-Xnoagent")) {
               d = true;
               a("Debugger detected via JVM flags");
               return;
            }
         }

         ThreadGroup threadGroup10 = Thread.currentThread().getThreadGroup();

         while (threadGroup10.getParent() != null) {
            threadGroup10 = threadGroup10.getParent();
         }

         Thread[] threadArray11 = new Thread[threadGroup10.activeCount() * 2];
         int intValue4 = threadGroup10.enumerate(threadArray11);

         for (int intValue5 = 0; intValue5 < intValue4; intValue5++) {
            if (threadArray11[intValue5] != null) {
               String string6 = threadArray11[intValue5].getName().toLowerCase();
               if (string6.contains("jdwp") || string6.contains("debug") || string6.contains("attach") || string6.contains("instrument")) {
                  d = true;
                  a("Debugger thread detected: " + string6);
                  return;
               }
            }
         }

         long longValue12 = System.nanoTime();
         dev.leg1tka.api.utils.security.RuntimeIntegrityChecks.c();
         long longValue7 = System.nanoTime() - longValue12;
         if (longValue7 > 10000000L) {
            d = true;
            a("Timing anomaly detected (possible debugger)");
         }
      } catch (Exception exception9) {
         a("Exception during debugger check");
      }
   }

   private static void f() {
      try {
         String[] stringArray0 = new String[]{
            "C:\\windows\\system32\\drivers\\vmmouse.sys",
            "C:\\windows\\system32\\drivers\\vmhgfs.sys",
            "/usr/bin/vmware-toolbox-cmd",
            "/usr/bin/VBoxControl",
            "C:\\Program Files\\VMware\\",
            "C:\\Program Files\\Oracle\\VirtualBox\\"
         };

         for (String string4 : stringArray0) {
            if (new File(string4).exists()) {
               e = true;
               a("VM artifact detected: " + string4);
               return;
            }
         }

         int intValue6 = Runtime.getRuntime().availableProcessors();
         if (intValue6 < 2) {
            e = true;
            a("Suspicious CPU count: " + intValue6);
         }

         String string7 = System.getProperty("java.vendor", "").toLowerCase();
         String string8 = System.getProperty("java.vm.name", "").toLowerCase();
         if (string7.contains("android") || string8.contains("dalvik")) {
            e = true;
            a("Android/Dalvik VM detected");
         }
      } catch (Exception exception5) {
         a("Exception during VM check");
      }
   }

   private static void g() {
      try {
         StackTraceElement[] stackTraceElementArray0 = Thread.currentThread().getStackTrace();

         for (StackTraceElement stackTraceElement4 : stackTraceElementArray0) {
            String string5 = stackTraceElement4.getClassName().toLowerCase();
            if (string5.contains("HighJump.gui")
               || string5.contains("fernflower")
               || string5.contains("procyon")
               || string5.contains("cfr")
               || string5.contains("jadx")
               || string5.contains("jdec")
               || string5.contains("luyten")) {
               f = true;
               a("Decompiler detected: " + string5);
               return;
            }
         }

         String string8 = System.getProperty("java.class.path", "");
         if (string8.contains("HighJump-gui") || string8.contains("fernflower") || string8.contains("procyon") || string8.contains("cfr")) {
            f = true;
            a("Decompiler NameProtect classpath");
         }

         try {
            Class.forName("org.benf.cfr.reader.Main");
            f = true;
            a("CFR decompiler loaded");
         } catch (ClassNotFoundException classNotFoundException6) {
         }
      } catch (Exception exception7) {
         a("Exception during decompiler check");
      }
   }

   private static void h() {
      try {
         String string0 = System.getProperty("user.dir", "");
         File file1 = new File(string0);
         if (file1.exists() && file1.isDirectory()) {
            File[] fileArray2 = file1.listFiles((file0x, string1x) -> string1x.endsWith(".hprof") || string1x.endsWith(".dump") || string1x.contains("heapdump"));
            if (fileArray2 != null && fileArray2.length > 0) {
               a("Heap dump file detected");
            }
         }
      } catch (Exception exception3) {
      }
   }

   private static void a(String string0) {
      int intValue1 = b.incrementAndGet();
      if (dev.leg1tka.api.utils.security.RuntimeIntegrityChecks.a()) {
         if (intValue1 >= 3) {
            i();
         } else {
            j();
         }
      }
   }

   private static void i() {
      if (c.nextBoolean()) {
         throw new dev.leg1tka.api.utils.security.RuntimeEnvironmentGuard.a();
      } else {
         if (c.nextBoolean()) {
            while (dev.leg1tka.api.utils.security.RuntimeIntegrityChecks.a()) {
               Thread.yield();
            }
         }

         System.exit(0);
      }
   }

   private static void j() {
      try {
         Thread.sleep((long)(c.nextInt(1000) + 500));
      } catch (InterruptedException interruptedException1) {
      }

      dev.leg1tka.api.utils.security.RuntimeIntegrityChecks.a = dev.leg1tka.api.utils.security.RuntimeIntegrityChecks.a + c.nextInt(1000);
   }

   public static void b() {
      Thread thread0 = new Thread(() -> {
         while (dev.leg1tka.api.utils.security.RuntimeIntegrityChecks.a()) {
            try {
               Thread.sleep((long)(5000 + c.nextInt(5000)));
               if (d || e || f) {
                  a("Continuous monitoring alert");
               }

               e();
               f();
               g();
            } catch (Exception exception1) {
            }
         }
      }, "ٸ");
      thread0.setDaemon(true);
      thread0.setPriority(1);
      thread0.start();
   }

   public static boolean c() {
      return !d && !e && !f;
   }

   public static void a(Runnable runnable0) {
      if (dev.leg1tka.api.utils.security.RuntimeIntegrityChecks.a()) {
         if (c()) {
            runnable0.run();
         } else {
            a("Attempted execution NameProtect unsafe environment");
         }
      }
   }

   static class a extends RuntimeException {
      public a() {
         super(dev.leg1tka.api.utils.security.LayeredStringCrypto.a(32));
      }
   }
}
