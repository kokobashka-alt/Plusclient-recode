package dev.leg1tka.network.bots;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.Settings;
import baritone.api.Settings.Setting;
import baritone.api.event.events.PathEvent;
import baritone.api.event.listener.AbstractGameEventListener;
import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.utils.Rotation;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public final class BaritoneBotNavigator implements dev.leg1tka.network.bots.BotNavigator {
   private static int a;
   private static int b;
   private static int c;
   private static int d;
   private static boolean e;
   static volatile PathEvent f;
   private static List<dev.leg1tka.network.bots.BaritoneBotNavigator.a<?>> g;
   private BlockPos h;
   private int i;
   private int j;
   private int k;
   private int l;
   private double m;
   private Vec3d n;
   private int o;
   private String p = "";

   public BaritoneBotNavigator() {
   }

   @Override
   public void a(BlockPos blockPos1) {
      IBaritone iBaritone2 = dev.leg1tka.api.utils.resources.BaritoneAccess.b();
      if (iBaritone2 == null) {
         this.h = null;
         this.p = "Baritone не загружен";
      } else {
         b(iBaritone2);
         d();
         this.h = blockPos1;
         this.i = 0;
         this.k = 0;
         this.m = Double.MAX_VALUE;
         this.l = 0;
         this.n = null;
         this.p = "";
         this.a(iBaritone2);
      }
   }

   @Override
   public dev.leg1tka.network.bots.BotNavigator.a a(ClientPlayerEntity clientPlayerEntity1, ClientWorld clientWorld2) {
      if (this.h == null) {
         return dev.leg1tka.network.bots.BotNavigator.a.FAILED;
      } else {
         IBaritone iBaritone3 = dev.leg1tka.api.utils.resources.BaritoneAccess.b();
         if (iBaritone3 == null) {
            this.p = "Baritone не загружен";
            return dev.leg1tka.network.bots.BotNavigator.a.FAILED;
         } else if (clientPlayerEntity1.isOnGround() && dev.leg1tka.network.bots.BotBlockActions.a(clientWorld2, clientPlayerEntity1, clientPlayerEntity1.getEyePos(), this.h, dev.leg1tka.network.bots.BotBlockActions.a(clientPlayerEntity1)) != null) {
            iBaritone3.getPathingBehavior().cancelEverything();
            return dev.leg1tka.network.bots.BotNavigator.a.ARRIVED;
         } else {
            this.i++;
            double doubleValue4 = Math.sqrt(clientPlayerEntity1.getPos().squaredDistanceTo(Vec3d.ofCenter(this.h)));
            if (doubleValue4 < this.m - 0.5) {
               this.m = doubleValue4;
               this.l = 0;
            } else {
               this.l++;
            }

            if (this.n == null || this.i - this.o >= 40) {
               boolean flag6 = this.n != null && clientPlayerEntity1.getPos().squaredDistanceTo(this.n) < 0.6400000000000001 && this.l >= 40;
               this.n = clientPlayerEntity1.getPos();
               this.o = this.i;
               if (flag6) {
                  return this.a(iBaritone3, "застрял на месте");
               }
            }

            PathEvent pathEvent7 = f;
            if (pathEvent7 != PathEvent.CALC_FAILED && pathEvent7 != PathEvent.NEXT_CALC_FAILED) {
               if (this.i - this.j > 20 && !iBaritone3.getCustomGoalProcess().isActive()) {
                  return this.a(iBaritone3, "Baritone остановился");
               } else if (this.l > 300) {
                  return this.a(iBaritone3, "нет прогресса 15с");
               } else if (this.i > 900) {
                  this.p = "таймаут 45с";
                  iBaritone3.getPathingBehavior().cancelEverything();
                  return dev.leg1tka.network.bots.BotNavigator.a.FAILED;
               } else {
                  return dev.leg1tka.network.bots.BotNavigator.a.WALKING;
               }
            } else {
               return this.a(iBaritone3, "путь не найден");
            }
         }
      }
   }

   @Override
   public void a(ClientPlayerEntity clientPlayerEntity1, float floatValue2, float floatValue3) {
      IBaritone iBaritone4 = dev.leg1tka.api.utils.resources.BaritoneAccess.b();
      if (iBaritone4 != null) {
         iBaritone4.getLookBehavior().updateTarget(new Rotation(floatValue2, floatValue3), true);
      }
   }

   @Override
   public void a() {
      dev.leg1tka.api.utils.resources.BaritoneAccess.c();
   }

   @Override
   public void b() {
      if (this.h != null) {
         dev.leg1tka.api.utils.resources.BaritoneAccess.c();
      }

      this.h = null;
      e();
   }

   @Override
   public String c() {
      return this.p;
   }

   private dev.leg1tka.network.bots.BotNavigator.a a(IBaritone iBaritone1, String string2) {
      iBaritone1.getPathingBehavior().cancelEverything();
      if (++this.k > 4) {
         this.p = string2;
         return dev.leg1tka.network.bots.BotNavigator.a.FAILED;
      } else {
         this.l = 0;
         this.m = Double.MAX_VALUE;
         this.n = null;
         this.a(iBaritone1);
         return dev.leg1tka.network.bots.BotNavigator.a.WALKING;
      }
   }

   private void a(IBaritone iBaritone1) {
      f = null;
      this.j = this.i;
      iBaritone1.getCustomGoalProcess().setGoalAndPath(new GoalGetToBlock(this.h));
   }

   private static void b(IBaritone iBaritone0) {
      if (!e) {
         e = true;
         iBaritone0.getGameEventHandler().registerEventListener(new AbstractGameEventListener() {
            public void onPathEvent(PathEvent pathEvent1) {
               dev.leg1tka.network.bots.BaritoneBotNavigator.f = pathEvent1;
            }
         });
      }
   }

   private static void d() {
      if (g == null) {
         Settings settings0 = BaritoneAPI.getSettings();
         g = new ArrayList<>();
         a(settings0.allowBreak, false);
         a(settings0.allowPlace, false);
         a(settings0.allowParkour, false);
         a(settings0.allowParkourPlace, false);
         a(settings0.allowWaterBucketFall, false);
         a(settings0.allowInventory, false);
         a(settings0.autoTool, false);
         a(settings0.allowSprint, true);
         a(settings0.randomLooking, 0.0);
         a(settings0.randomLooking113, 0.0);
         a(settings0.antiCheatCompatibility, true);
      }
   }

   private static void e() {
      if (g != null) {
         for (dev.leg1tka.network.bots.BaritoneBotNavigator.a<?> value1 : g) {
            value1.a();
         }

         g = null;
      }
   }

   private static <T> void a(Setting<T> setting0, T value1) {
      g.add(new dev.leg1tka.network.bots.BaritoneBotNavigator.a<>(setting0, setting0.value));
      setting0.value = value1;
   }

   static record a<T>(Setting<T> setting, T value) {



      void a() {
         this.setting.value = this.value;
      }

      public Setting<T> b() {
         return this.setting;
      }

      public T c() {
         return (T)this.value;
      }
   }
}
