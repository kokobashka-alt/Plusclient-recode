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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ProtocolVersionResolver {
   private static int a;
   private static long b;
   private static final Pattern c = Pattern.compile("1\\.(\\d+)");
   private static long d;
   private static boolean e;

   private ProtocolVersionResolver() {
   }

   public static boolean a() {
      long longValue0 = System.currentTimeMillis();
      if (longValue0 < d) {
         return e;
      } else {
         e = b();
         d = longValue0 + 1500L;
         return e;
      }
   }

   private static boolean b() {
      try {
         Class class0 = Class.forName("com.viaversion.viafabricplus.ViaFabricPlus");
         Object object1 = class0.getMethod("getImpl").invoke(null);
         if (object1 == null) {
            return false;
         } else {
            Object object2 = a(object1, "getTargetVersion");
            if (object2 == null) {
               return false;
            } else {
               Integer integer3 = a(object2);
               return integer3 != null && integer3 < 759;
            }
         }
      } catch (Throwable throwable4) {
         return false;
      }
   }

   private static Object a(Object object0, String string1) {
      try {
         Method method2 = object0.getClass().getMethod(string1);
         return Modifier.isPublic(method2.getModifiers()) && method2.getParameterCount() == 0 ? method2.invoke(object0) : null;
      } catch (Throwable throwable3) {
         return null;
      }
   }

   private static Integer a(Object object0) {
      try {
         Method method1 = object0.getClass().getMethod("getVersion");
         if (method1.invoke(object0) instanceof Number number15) {
            return number15.intValue();
         }
      } catch (Throwable throwable9) {
      }

      try {
         for (Method method4 : object0.getClass().getMethods()) {
            if (Modifier.isPublic(method4.getModifiers()) && method4.getParameterCount() == 0) {
               Class class5 = method4.getReturnType();
               if (class5 == int.class || class5 == Integer.class) {
                  String string6 = method4.getName().toLowerCase();
                  if ((string6.contains("version") || string6.contains("protocol") || string6.contains("AutoEZ")) && method4.invoke(object0) instanceof Number number8) {
                     return number8.intValue();
                  }
               }
            }
         }
      } catch (Throwable throwable10) {
      }

      Matcher matcher12 = c.matcher(String.valueOf(object0));
      if (matcher12.find()) {
         int intValue14 = Integer.parseInt(matcher12.group(1));
         return intValue14 >= 19 ? 759 : 758;
      } else {
         return null;
      }
   }
}
