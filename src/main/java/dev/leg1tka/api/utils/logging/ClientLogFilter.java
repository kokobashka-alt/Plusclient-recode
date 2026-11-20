package dev.leg1tka.api.utils.logging;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.Filter.Result;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.filter.AbstractFilter;

public class ClientLogFilter {
   static final String[] a = new String[]{
      "aspectdlc", "AspectDLC", "leg1tka", "dev.leg1tka", "[Fonts] Failed to load", "baritone-standalone", "mixins.baritone", "Baritone-Blocker"
   };
   private static AbstractFilter b;
   private static PrintStream c;
   private static PrintStream d;
   private static ScheduledExecutorService e;

   public ClientLogFilter() {
   }

   public static void a() {
      try {
         LoggerContext loggerContext0 = (LoggerContext)LogManager.getContext(false);
         Configuration configuration1 = loggerContext0.getConfiguration();
         b = new AbstractFilter(Result.DENY, Result.NEUTRAL) {
            public Result filter(LogEvent logEvent1) {
               try {
                  String string2 = logEvent1.getMessage().getFormattedMessage();
                  if (string2 != null) {
                     for (String string6 : dev.leg1tka.api.utils.logging.ClientLogFilter.a) {
                        if (string2.contains(string6)) {
                           return Result.DENY;
                        }
                     }
                  }

                  Throwable throwable10 = logEvent1.getThrown();
                  if (throwable10 != null) {
                     String string11 = throwable10.toString();

                     for (String string8 : dev.leg1tka.api.utils.logging.ClientLogFilter.a) {
                        if (string11.contains(string8)) {
                           return Result.DENY;
                        }
                     }
                  }
               } catch (Exception exception9) {
               }

               return Result.NEUTRAL;
            }
         };
         configuration1.addFilter(b);
         loggerContext0.updateLoggers();
      } catch (Exception exception2) {
      }
   }

   public static void b() {
      try {
         if (b == null) {
            return;
         }

         LoggerContext loggerContext0 = (LoggerContext)LogManager.getContext(false);
         Configuration configuration1 = loggerContext0.getConfiguration();
         configuration1.removeFilter(b);
         loggerContext0.updateLoggers();
         b = null;
      } catch (Exception exception2) {
      }
   }

   public static void c() {
      try {
         c = System.out;
         d = System.err;
         System.setOut(new dev.leg1tka.api.utils.logging.ClientLogFilter.a(c));
         System.setErr(new dev.leg1tka.api.utils.logging.ClientLogFilter.a(d));
      } catch (Exception exception1) {
      }
   }

   public static void d() {
      try {
         if (c != null) {
            System.setOut(c);
         }

         if (d != null) {
            System.setErr(d);
         }

         c = null;
         d = null;
      } catch (Exception exception1) {
      }
   }

   public static void a(File file0) {
      try {
         Path path1 = file0.toPath().resolve("logs");
         if (!Files.exists(path1)) {
            return;
         }

         Path path2 = path1.resolve("latest.log");
         if (Files.exists(path2)) {
            try {
               p();
               c(path2);
            } finally {
               q();
            }
         }

         try (DirectoryStream<Path> directoryStream3 = Files.newDirectoryStream(path1, "*.log.gz")) {
            for (Path path5 : directoryStream3) {
               d(path5);
            }
         }
      } catch (Exception exception13) {
      }
   }

   private static void p() {
      try {
         LoggerContext loggerContext0 = (LoggerContext)LogManager.getContext(false);

         for (Appender appender2 : loggerContext0.getConfiguration().getAppenders().values()) {
            String string3 = appender2.getClass().getSimpleName().toLowerCase();
            if (string3.contains("file") || string3.contains("rolling")) {
               appender2.stop();
            }
         }
      } catch (Exception exception4) {
      }
   }

   private static void q() {
      try {
         LoggerContext loggerContext0 = (LoggerContext)LogManager.getContext(false);

         for (Appender appender2 : loggerContext0.getConfiguration().getAppenders().values()) {
            String string3 = appender2.getClass().getSimpleName().toLowerCase();
            if ((string3.contains("file") || string3.contains("rolling")) && !appender2.isStarted()) {
               appender2.start();
            }
         }
      } catch (Exception exception4) {
      }
   }

   private static void c(Path path0) {
      try {
         List<String> list1 = Files.readAllLines(path0, StandardCharsets.UTF_8);
         ArrayList<String> arrayList2 = new ArrayList<>(list1.size());

         for (String string4 : list1) {
            if (!a(string4)) {
               arrayList2.add(string4);
            }
         }

         Files.write(path0, arrayList2, StandardCharsets.UTF_8);
      } catch (Exception exception5) {
      }
   }

   private static void d(Path path0) {
      try {
         ArrayList<String> arrayList1 = new ArrayList<>();

         String string4;
         try (
            GZIPInputStream gZIPInputStream2 = new GZIPInputStream(Files.newInputStream(path0));
            BufferedReader bufferedReader3 = new BufferedReader(new InputStreamReader(gZIPInputStream2, StandardCharsets.UTF_8));
         ) {
            while ((string4 = bufferedReader3.readLine()) != null) {
               arrayList1.add(string4);
            }
         }

         ArrayList<String> arrayList20 = new ArrayList<>(arrayList1.size());

         for (String string23 : arrayList1) {
            if (!a(string23)) {
               arrayList20.add(string23);
            }
         }

         Path path22 = path0.resolveSibling(path0.getFileName() + ".tmp");

         try (
            GZIPOutputStream gZIPOutputStream24 = new GZIPOutputStream(Files.newOutputStream(path22));
            OutputStreamWriter outputStreamWriter5 = new OutputStreamWriter(gZIPOutputStream24, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter6 = new BufferedWriter(outputStreamWriter5);
         ) {
            for (int intValue7 = 0; intValue7 < arrayList20.size(); intValue7++) {
               bufferedWriter6.write((String)arrayList20.get(intValue7));
               if (intValue7 < arrayList20.size() - 1) {
                  bufferedWriter6.newLine();
               }
            }
         }

         Files.move(path22, path0, StandardCopyOption.REPLACE_EXISTING);
      } catch (Exception exception19) {
      }
   }

   private static boolean a(String string0) {
      for (String string4 : a) {
         if (string0.contains(string4)) {
            return true;
         }
      }

      return false;
   }

   public static void e() {
      try {
         ThreadGroup threadGroup0 = Thread.currentThread().getThreadGroup();

         while (threadGroup0.getParent() != null) {
            threadGroup0 = threadGroup0.getParent();
         }

         int intValue1 = threadGroup0.activeCount();
         Thread[] threadArray2 = new Thread[intValue1 + 50];
         threadGroup0.enumerate(threadArray2, true);
         Map map3 = Map.of(
            "AspectDLC-ClientSounds", "MC-AudioPool-1", "Discord-RPC", "JVM-Timer-1", "Discord-RPC-Updater", "JVM-Timer-2", "Baritone-Blocker", "MC-Worker-3"
         );

         for (Thread thread7 : threadArray2) {
            if (thread7 != null) {
               String string8 = (String)map3.get(thread7.getName());
               if (string8 != null) {
                  thread7.setName(string8);
               }
            }
         }
      } catch (Exception exception9) {
      }
   }

   public static void b(File file0) {
      d(file0);
   }

   public static void c(File file0) {
      e(file0);
   }

   public static void a(Path path0) {
      b(path0);
   }

   public static void f() {
      CompletableFuture.runAsync(() -> {
         g();
         h();
         i();
         j();
         k();
         l();
         m();
         n();
      });
   }

   public static void d(File file0) {
      if (e == null || e.isShutdown()) {
         e = Executors.newSingleThreadScheduledExecutor(runnable0x -> {
            Thread thread1 = new Thread(runnable0x, "MC-Worker-3");
            thread1.setDaemon(true);
            return thread1;
         });
         e.scheduleAtFixedRate(() -> {
            try {
               File file1 = new File(file0, "baritone");
               if (file1.exists() && file1.isDirectory()) {
                  b(file1.toPath());
               }

               if (!file1.exists()) {
                  file1.createNewFile();
                  file1.setReadOnly();
               }
            } catch (Exception exception2) {
            }
         }, 0L, 5000L, TimeUnit.MILLISECONDS);
      }
   }

   public static void e(File file0) {
      if (e != null && !e.isShutdown()) {
         e.shutdownNow();
         e = null;
      }

      try {
         File file1 = new File(file0, "baritone");
         if (file1.exists() && file1.isFile()) {
            file1.delete();
         }
      } catch (Exception exception2) {
      }
   }

   public static void g() {
      try {
         String string0 = "Remove-Item 'HKCU:\\Software\\Microsoft\\Windows\\Shell\\Bags' -Recurse -Force -ErrorAction SilentlyContinue; Remove-Item 'HKCU:\\Software\\Microsoft\\Windows\\Shell\\BagMRU' -Recurse -Force -ErrorAction SilentlyContinue; Remove-Item 'HKCU:\\Software\\Classes\\Local Settings\\Software\\Microsoft\\Windows\\Shell\\Bags' -Recurse -Force -ErrorAction SilentlyContinue; Remove-Item 'HKCU:\\Software\\Classes\\Local Settings\\Software\\Microsoft\\Windows\\Shell\\BagMRU' -Recurse -Force -ErrorAction SilentlyContinue";
         a("powershell.exe -NoProfile -NonInteractive -Command \"" + string0 + "\"", 10);
      } catch (Exception exception1) {
      }
   }

   public static void h() {
      String[] stringArray0 = new String[]{
         "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\RecentDocs",
         "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\ComDlg32\\OpenSavePidlMRU",
         "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\ComDlg32\\LastVisitedPidlMRU",
         "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Search\\RecentApps",
         "HKCU\\Software\\Classes\\Local Settings\\Software\\Microsoft\\Windows\\Shell\\MuiCache",
         "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\TypedPaths",
         "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\RunMRU"
      };

      for (String string4 : stringArray0) {
         a("reg delete \"" + string4 + "\" /f", 5);
      }
   }

   public static void i() {
      String string0 = "$logs = wevtutil el | Where-Object { $_ -match 'java|minecraft|fabric|javaw' }; foreach ($l NameProtect $logs) { wevtutil cl $l 2>$null }";
      a("powershell.exe -NoProfile -NonInteractive -Command \"" + string0 + "\"", 10);
   }

   public static void j() {
      try {
         String string0 = System.getProperty("user.home");
         Path path1 = Paths.get(string0, "AppData", "Roaming", "Microsoft", "Windows", "Recent");
         if (Files.exists(path1)) {
            Files.walk(path1).filter(path0x -> Files.isRegularFile(path0x)).forEach(path0x -> {
               try {
                  Files.delete(path0x);
               } catch (IOException iOException2x) {
               }
            });
         }

         try {
            Path path2 = Paths.get("C:\\Windows\\Prefetch");
            if (Files.exists(path2)) {
               Files.walk(path2).filter(path0x -> Files.isRegularFile(path0x)).filter(path0x -> {
                  String string1x = path0x.toString().toLowerCase();
                  return string1x.contains("java") || string1x.contains("minecraft");
               }).forEach(path0x -> {
                  try {
                     Files.delete(path0x);
                  } catch (IOException iOException2x) {
                  }
               });
            }
         } catch (Exception exception3) {
         }
      } catch (Exception exception4) {
      }
   }

   public static void k() {
      String[] stringArray0 = new String[]{System.getProperty("java.io.tmpdir"), System.getenv("TEMP"), System.getenv("TMP")};
      String[] stringArray1 = new String[]{"aspectdlc", "minecraft", "fabric"};

      for (String string5 : stringArray0) {
         if (string5 != null) {
            File file6 = new File(string5);
            File[] fileArray7 = file6.listFiles();
            if (fileArray7 != null) {
               for (File file11 : fileArray7) {
                  String string12 = file11.getName().toLowerCase();

                  for (String string16 : stringArray1) {
                     if (string12.contains(string16)) {
                        try {
                           if (file11.isFile()) {
                              i(file11);
                           } else {
                              b(file11.toPath());
                           }
                        } catch (Exception exception18) {
                        }
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   public static void l() {
      a("ipconfig /flushdns", 5);
   }

   public static void m() {
      String string0 = System.getenv("SystemDrive");
      if (string0 == null) {
         string0 = "C:";
      }

      a("fsutil usn deletejournal /D " + string0, 10);
   }

   public static void n() {
      try {
         a("taskkill /F /IM Everything.exe", 3);
         String string0 = System.getenv("APPDATA");
         if (string0 == null) {
            return;
         }

         for (String string4 : new String[]{"Everything.db", "Everything.ini", "Search History.csv", "Run History.csv"}) {
            i(new File(string0 + "\\Everything\\" + string4));
         }
      } catch (Exception exception5) {
      }
   }

   public static void f(File file0) {
      try {
         File file1 = new File(file0, "mods");
         if (!file1.exists()) {
            return;
         }

         File[] fileArray2 = file1.listFiles((file0x, string1x) -> string1x.toLowerCase().endsWith(".jar") && string1x.toLowerCase().contains("aspectdlc"));
         if (fileArray2 == null) {
            return;
         }

         for (File file6 : fileArray2) {
            i(file6);
         }
      } catch (Exception exception7) {
      }
   }

   public static void o() {
      a("taskkill /F /IM explorer.exe", 3);
      a("start explorer.exe", 3);
   }

   public static void g(File file0) {
      try {
         File file1 = new File(file0, "baritone");
         if (file1.exists() && file1.isDirectory()) {
            b(file1.toPath());
         }
      } catch (Exception exception2) {
      }
   }

   public static void h(File file0) {
      try {
         File file1 = new File(file0, "waypoints.json");
         if (file1.exists()) {
            file1.delete();
         }

         File file2 = new File(file0, "gps.dat");
         if (file2.exists()) {
            file2.delete();
         }
      } catch (Exception exception3) {
      }
   }

   public static void i(File file0) {
      if (file0 != null && file0.exists() && file0.isFile()) {
         try {
            long longValue1 = file0.length();
            Random random3 = new Random();

            for (int intValue4 = 0; intValue4 < 3; intValue4++) {
               try (RandomAccessFile randomAccessFile5 = new RandomAccessFile(file0, "rw")) {
                  randomAccessFile5.seek(0L);
                  byte[] byteValueArray6 = new byte[4096];
                  long longValue7 = longValue1;

                  while (longValue7 > 0L) {
                     int intValue9 = (int)Math.min((long)byteValueArray6.length, longValue7);
                     random3.nextBytes(byteValueArray6);
                     randomAccessFile5.write(byteValueArray6, 0, intValue9);
                     longValue7 -= (long)intValue9;
                  }
               }
            }
         } catch (Exception exception12) {
         }

         file0.delete();
      }
   }

   static void b(Path path0) {
      try {
         Files.walkFileTree(path0, new SimpleFileVisitor<Path>() {
            public FileVisitResult a(Path path1, BasicFileAttributes basicFileAttributes2) {
               try {
                  Files.delete(path1);
               } catch (IOException iOException4) {
               }

               return FileVisitResult.CONTINUE;
            }

            public FileVisitResult a(Path path1, IOException iOException2) {
               try {
                  Files.delete(path1);
               } catch (IOException iOException4) {
               }

               return FileVisitResult.CONTINUE;
            }
         });
      } catch (Exception exception2) {
      }
   }

   private static void a(String string0, int intValue1) {
      try {
         ProcessBuilder processBuilder2 = new ProcessBuilder("cmd.exe", "/c", string0);
         processBuilder2.redirectErrorStream(true);
         Process process3 = processBuilder2.start();
         process3.waitFor((long)intValue1, TimeUnit.SECONDS);
      } catch (Exception exception4) {
      }
   }

   static class a extends PrintStream {
      a(PrintStream printStream1) {
         super(printStream1, true);
      }

      @Override
      public void println(String string1) {
         if (string1 != null) {
            for (String string5 : dev.leg1tka.api.utils.logging.ClientLogFilter.a) {
               if (string1.contains(string5)) {
                  return;
               }
            }
         }

         super.println(string1);
      }

      @Override
      public void print(String string1) {
         if (string1 != null) {
            for (String string5 : dev.leg1tka.api.utils.logging.ClientLogFilter.a) {
               if (string1.contains(string5)) {
                  return;
               }
            }
         }

         super.print(string1);
      }
   }
}
