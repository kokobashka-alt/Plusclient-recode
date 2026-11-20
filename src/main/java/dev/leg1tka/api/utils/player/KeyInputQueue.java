package dev.leg1tka.api.utils.player;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventGameUpdate;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class KeyInputQueue implements dev.leg1tka.api.ClientAccess {
   private final KeyBinding[] d = new KeyBinding[]{
      minecraftClient.options.forwardKey, minecraftClient.options.leftKey, minecraftClient.options.rightKey, minecraftClient.options.backKey, minecraftClient.options.jumpKey, minecraftClient.options.sprintKey, minecraftClient.options.sneakKey
   };
   private final List<dev.leg1tka.api.utils.player.KeyInputQueue.Task> e = new ArrayList<>();
   private final List<dev.leg1tka.api.utils.player.KeyInputQueue.TickTask> f = new ArrayList<>();
   private dev.leg1tka.api.utils.player.KeyInputQueue.Task g = null;
   public static final dev.leg1tka.api.utils.player.KeyInputQueue c = new dev.leg1tka.api.utils.player.KeyInputQueue();

   private KeyInputQueue() {
      EventInvoker.register(this);
   }

   @EventLink
   public void onGameUpdate(EventGameUpdate eventGameUpdate1) {
      this.a();
      this.a(false);
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      this.a(true);
   }

   public static void a(dev.leg1tka.api.utils.player.KeyInputQueue.Task task0) {
      for (dev.leg1tka.api.utils.player.KeyInputQueue.Task task2 : c.e) {
         if (task2.d != null && task0.d != null && task2.d.equals(task0.d)) {
            return;
         }
      }

      c.g = task0;
      c.e.add(task0);
   }

   public static void a(dev.leg1tka.api.utils.player.KeyInputQueue.TickTask tickTask0) {
      c.f.add(tickTask0);
   }

   public void a() {
      long longValue1 = System.currentTimeMillis();
      if (this.g != null) {
         if (longValue1 - this.g.b < this.g.a) {
            for (KeyBinding keyBinding6 : new KeyBinding[]{
               minecraftClient.options.forwardKey, minecraftClient.options.leftKey, minecraftClient.options.rightKey, minecraftClient.options.backKey, minecraftClient.options.jumpKey, minecraftClient.options.sprintKey
            }) {
               keyBinding6.setPressed(false);
            }
         } else {
            for (KeyBinding keyBinding15 : this.d) {
               if (minecraftClient.currentScreen == null) {
                  keyBinding15.setPressed(InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), keyBinding15.getDefaultKey().getCode()));
               }
            }
         }
      }

      for (dev.leg1tka.api.utils.player.KeyInputQueue.Task task13 : new ArrayList<>(this.e)) {
         if (longValue1 - task13.b > task13.a - task13.c && task13.d != null) {
            task13.d.run();
            task13.d = null;
         }
      }

      Iterator<dev.leg1tka.api.utils.player.KeyInputQueue.Task> iterator11 = this.e.iterator();

      while (iterator11.hasNext()) {
         dev.leg1tka.api.utils.player.KeyInputQueue.Task task14 = iterator11.next();
         if (longValue1 - task14.b > task14.a || !task14.e) {
            iterator11.remove();
         }
      }
   }

   public void a(boolean flag1) {
      long longValue2 = System.currentTimeMillis();

      for (dev.leg1tka.api.utils.player.KeyInputQueue.TickTask tickTask5 : new ArrayList<>(this.f)) {
         if (flag1) {
            tickTask5.c++;
         }

         if (tickTask5.d != null && (tickTask5.f == dev.leg1tka.api.utils.player.KeyInputQueue.TickTask.Unit.TICKS ? tickTask5.c >= tickTask5.b : longValue2 - tickTask5.b > tickTask5.a)) {
            tickTask5.d.run();
            tickTask5.d = null;
         }
      }

      Iterator<dev.leg1tka.api.utils.player.KeyInputQueue.TickTask> iterator6 = this.f.iterator();

      while (iterator6.hasNext()) {
         dev.leg1tka.api.utils.player.KeyInputQueue.TickTask tickTask7 = iterator6.next();
         if ((tickTask7.f == dev.leg1tka.api.utils.player.KeyInputQueue.TickTask.Unit.TICKS ? tickTask7.c >= tickTask7.b : longValue2 - tickTask7.b > tickTask7.a) || !tickTask7.e) {
            iterator6.remove();
         }
      }
   }

   public static boolean b() {
      return c.e.isEmpty();
   }

   public static boolean c() {
      return c.f.isEmpty();
   }

   public static class Task {
      public long a;
      public long b;
      public long c;
      public Runnable d;
      public boolean e;

      public Task(long longValue1, long longValue3, Runnable runnable5) {
         this.a = longValue1;
         this.c = longValue3;
         this.d = runnable5;
         this.b = System.currentTimeMillis();
         this.e = true;
      }

      public Task(long longValue1, long longValue3, Runnable runnable5, boolean flag6) {
         this.a = longValue1;
         this.c = longValue3;
         this.d = runnable5;
         this.b = System.currentTimeMillis();
         this.e = flag6;
      }

      public Task(long longValue1, Runnable runnable3) {
         this.a = longValue1;
         this.c = 20L;
         this.d = runnable3;
         this.b = System.currentTimeMillis();
         this.e = true;
      }

      public Task(long longValue1, Runnable runnable3, boolean flag4) {
         this.a = longValue1;
         this.c = 20L;
         this.d = runnable3;
         this.b = System.currentTimeMillis();
         this.e = flag4;
      }
   }

   public static class TickTask {
      public long a;
      public long b;
      public long c;
      public Runnable d;
      public boolean e;
      public Unit f;

      public TickTask(long longValue1, Runnable runnable3, boolean flag4) {
         this.f = Unit.TIME;
         this.a = longValue1;
         this.d = runnable3;
         this.b = System.currentTimeMillis();
         this.e = flag4;
      }

      public TickTask(Unit unit1, int intValue2, Runnable runnable3) {
         this(unit1, intValue2, runnable3, true);
      }

      public TickTask(Unit unit1, int intValue2, Runnable runnable3, boolean flag4) {
         this.f = unit1;
         this.d = runnable3;
         this.b = (long)intValue2;
         this.e = flag4;
      }

      public static enum Unit {
         TIME,
         TICKS;

         private Unit() {
         }
      }
   }
}
