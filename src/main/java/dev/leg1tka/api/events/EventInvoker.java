package dev.leg1tka.api.events;

import dev.leg1tka.network.bots.BotController;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

public class EventInvoker {
   private static final ConcurrentHashMap classRegistry = new ConcurrentHashMap();
   private static final ConcurrentHashMap invocationCache = new ConcurrentHashMap();
   private static final ConcurrentHashMap slowHandlerWarnings = new ConcurrentHashMap();
   private static final ConcurrentHashMap slowEventWarnings = new ConcurrentHashMap();
   private static final boolean PERF_DEBUG = Boolean.parseBoolean(System.getProperty("AspectDLC.perf.debug", "false"));
   private static final long SLOW_HANDLER_NANOS = Long.getLong("AspectDLC.perf.handlerMs", 8L) * 1000000L;
   private static final long SLOW_EVENT_NANOS = Long.getLong("AspectDLC.perf.eventMs", 18L) * 1000000L;
   private static final long WARN_COOLDOWN_NANOS = Long.getLong("AspectDLC.perf.cooldownMs", 1000L) * 1000000L;
   private static volatile boolean cacheDirty = true;

   public EventInvoker() {
   }

   public static void register(Object object0) {
      classRegistry.putIfAbsent(object0.getClass(), object0);
      cacheDirty = true;
   }

   public static void unregister(Object object0) {
      classRegistry.remove(object0.getClass());
      cacheDirty = true;
   }

   public static void batchUnregister(Collection<?> collection0) {
      for (Object object2 : collection0) {
         classRegistry.remove(object2.getClass());
      }

      cacheDirty = true;
   }

   public static void batchRegister(Collection<?> collection0) {
      for (Object object2 : collection0) {
         classRegistry.putIfAbsent(object2.getClass(), object2);
      }

      cacheDirty = true;
   }

   public static void clean() {
      classRegistry.clear();
      invocationCache.clear();
      cacheDirty = false;
   }

   public static void invoke(Event event0) throws IllegalAccessException, InvocationTargetException, InstantiationException {
      if (!dev.leg1tka.network.bots.BotController.g()) {
         long longValue1 = PERF_DEBUG ? System.nanoTime() : 0L;
         if (cacheDirty) {
            rebuildCache();
         }

         List list3 = (List)invocationCache.get(event0.getClass());
         if (list3 != null && !list3.isEmpty()) {
            for (EventInvoker.Invocation invocation5 : (List<EventInvoker.Invocation>) list3) {
               if (classRegistry.containsKey(invocation5.listener().getClass())) {
                  Method method6 = invocation5.method();
                  method6.setAccessible(true);
                  long longValue7 = PERF_DEBUG ? System.nanoTime() : 0L;

                  try {
                     method6.invoke(invocation5.listener(), event0);
                  } finally {
                     if (PERF_DEBUG) {
                        long longValue12 = System.nanoTime() - longValue7;
                        if (longValue12 >= SLOW_HANDLER_NANOS) {
                           logSlowHandler(event0, invocation5, longValue12);
                        }
                     }
                  }
               }
            }

            if (PERF_DEBUG) {
               long longValue16 = System.nanoTime() - longValue1;
               if (longValue16 >= SLOW_EVENT_NANOS) {
                  logSlowEvent(event0, longValue16, list3.size());
               }
            }
         }
      }
   }

   public static boolean hasListeners(Class<? extends Event> class0) {
      if (cacheDirty) {
         rebuildCache();
      }

      List list1 = (List)invocationCache.get(class0);
      return list1 != null && !list1.isEmpty();
   }

   private static synchronized void rebuildCache() {
      if (cacheDirty) {
         ConcurrentHashMap<Class<?>, List<EventInvoker.Invocation>> concurrentHashMap0 = new ConcurrentHashMap<>();

         for (Object object2 : classRegistry.values()) {
            for (Method method6 : object2.getClass().getDeclaredMethods()) {
               if (method6.isAnnotationPresent(EventLink.class) && dev.leg1tka.recovery.NativeComponents.canDispatch(method6)) {
                  Class[] classArray7 = method6.getParameterTypes();
                  if (classArray7.length == 1 && Event.class.isAssignableFrom(classArray7[0])) {
                     Class class8 = classArray7[0];
                     method6.setAccessible(true);
                     concurrentHashMap0.computeIfAbsent(class8, class0x -> new ArrayList<>())
                        .add(new EventInvoker.Invocation(object2, method6, method6.getAnnotation(EventLink.class).priority()));
                  }
               }
            }
         }

         for (List<EventInvoker.Invocation> list10 : concurrentHashMap0.values()) {
            list10.sort((invocation0x, invocation1) -> {
               int intValue2x = Integer.compare(invocation1.priority(), invocation0x.priority());
               if (intValue2x != 0) {
                  return intValue2x;
               } else {
                  int intValue3 = dev.leg1tka.recovery.NativeComponents.originalName(invocation0x.listener().getClass()).compareTo(dev.leg1tka.recovery.NativeComponents.originalName(invocation1.listener().getClass()));
                  return intValue3 != 0 ? intValue3 : invocation0x.method().getName().compareTo(invocation1.method().getName());
               }
            });
         }

         invocationCache.clear();
         invocationCache.putAll(concurrentHashMap0);
         cacheDirty = false;
      }
   }

   private static void logSlowHandler(Event event0, EventInvoker.Invocation invocation1, long longValue2) {
      String string4 = invocation1.listener().getClass().getSimpleName();
      String string5 = invocation1.method().getName();
      String string6 = event0.getClass().getSimpleName();
      String string7 = "handler:" + string6 + ":" + string4 + "#" + string5;
      if (canWarn(slowHandlerWarnings, string7)) {
         System.out.println(String.format(Locale.ROOT, "[PerfDebug] Slow handler: %s -> %s#%s took %.2f ms", string6, string4, string5, (double)longValue2 / 1000000.0));
      }
   }

   private static void logSlowEvent(Event event0, long longValue1, int intValue3) {
      String string4 = event0.getClass().getSimpleName();
      String string5 = "event:" + string4;
      if (canWarn(slowEventWarnings, string5)) {
         System.out.println(String.format(Locale.ROOT, "[PerfDebug] Slow event: %s took %.2f ms for %d handlers", string4, (double)longValue1 / 1000000.0, intValue3));
      }
   }

   private static boolean canWarn(ConcurrentHashMap<String, Long> concurrentHashMap0, String string1) {
      long longValue2 = System.nanoTime();
      Long long4 = (Long)concurrentHashMap0.get(string1);
      if (long4 != null && longValue2 - long4 < WARN_COOLDOWN_NANOS) {
         return false;
      } else {
         concurrentHashMap0.put(string1, longValue2);
         return true;
      }
   }

   static record Invocation(Object listener, Method method, int priority)  {


}
}
