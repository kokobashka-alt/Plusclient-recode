package dev.leg1tka.modules.movement;


import dev.leg1tka.modules.Module;
import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventGameUpdate;
import dev.leg1tka.api.events.implement.EventMoveInput;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventTickPre;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.MathHelper;

public class Timer extends Module {
   public static Timer c = new Timer();
   private static float d;
   private static float e;
   private final NumberSetting f = new NumberSetting("Скорость буста", 1.5F, 1.2F, 2.2F, 0.05F);
   private final NumberSetting g = new NumberSetting("Время накопления", 1.7F, 0.4F, 4.0F, 0.05F);
   private final NumberSetting h = new NumberSetting("Время буста", 3.6F, 1.0F, 8.0F, 0.1F);
   private final NumberSetting i = new NumberSetting("Время разгона", 0.7F, 0.1F, 2.0F, 0.05F);
   private final NumberSetting j = new NumberSetting("Задержка hop", 0.32F, 0.0F, 1.5F, 0.02F);
   private final NumberSetting k = new NumberSetting("Прыжки", 8.0F, 1.0F, 20.0F, 1.0F);
   private final NumberSetting l = new NumberSetting("Тики земли hop", 3.0F, 1.0F, 8.0F, 1.0F);
   private final NumberSetting m = new NumberSetting("Кулдаун setback", 1.2F, 0.2F, 4.0F, 0.1F);
   private TimerState n = TimerState.CHARGING;
   private boolean o;
   private boolean p;
   private int q;
   private int r;
   private long s;
   private long t;
   private final Queue u = new ConcurrentLinkedQueue();

   public Timer() {
      super(
         "Timer",
         "Замедляется, накапливает инерцию и плавно разгоняет (настройки в GUI)",
         Category.MOVEMENT
      );
      this.addSettings(new Setting[]{this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m});
   }

   private static long a(float floatValue0) {
      float floatValue1 = MathHelper.clamp(floatValue0, 0.0F, 30.0F);
      return (long)(floatValue1 * 1.0E9F);
   }

   private int p() {
      return MathHelper.clamp(Math.round(this.k.f()), 1, 20);
   }

   private int q() {
      return MathHelper.clamp(Math.round(this.l.f()), 1, 8);
   }

   @Override
   public void onEnable() {
      this.t();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.t();
      super.onDisable();
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && this.o && this.n == TimerState.BOOSTING) {
         if (minecraftClient.player.verticalCollision) {
            if (this.p) {
               this.p = false;
               this.q++;
            }
         } else {
            this.p = true;
            if (this.q >= this.p() && minecraftClient.player.getVelocity().y <= 0.0) {
               this.r();
            }
         }
      }
   }

   @EventLink
   public void onGameUpdate(EventGameUpdate eventGameUpdate1) {
      if (minecraftClient.player != null && dev.leg1tka.api.utils.player.PlayerPositionUtils.c()) {
         long longValue2 = System.nanoTime();
         if (!this.o) {
            this.o = true;
            this.r();
         } else {
            long longValue4 = a(this.g.f());
            long longValue6 = a(this.h.f());
            if (this.n == TimerState.CHARGING) {
               if (longValue2 < this.t) {
                  dev.leg1tka.api.utils.math.RenderTickTiming.a(0.05F);
                  return;
               }

               if (longValue2 - this.s >= longValue4) {
                  this.s();
               } else {
                  dev.leg1tka.api.utils.math.RenderTickTiming.a(0.05F);
               }
            } else if (this.n == TimerState.BOOSTING) {
               if (longValue2 - this.s >= longValue6) {
                  this.r();
               } else {
                  this.a(longValue2);
               }
            }
         }
      } else {
         this.t();
      }
   }

   private void a(long longValue1) {
      long longValue3 = longValue1 - this.s;
      float floatValue5 = MathHelper.clamp(this.f.f(), 1.2F, 2.2F);
      long longValue6 = Math.max(1L, a(this.i.f()));
      float floatValue8 = MathHelper.clamp((float)longValue3 / (float)longValue6, 0.0F, 1.0F);
      floatValue8 = 1.0F - (1.0F - floatValue8) * (1.0F - floatValue8);
      float floatValue9 = 1.0F + (floatValue5 - 1.0F) * floatValue8;
      dev.leg1tka.api.utils.math.RenderTickTiming.a(floatValue9);
   }

   @EventLink
   public void onTickPre(EventTickPre eventTickPre1) {
      if (minecraftClient.player == null || minecraftClient.world == null) {
         this.t();
      }
   }

   @EventLink
   public void onMoveInput(EventMoveInput eventMoveInput1) {
      if (minecraftClient.player != null && this.n == TimerState.BOOSTING && this.a(eventMoveInput1)) {
         long longValue2 = System.nanoTime() - this.s;
         long longValue4 = a(this.j.f());
         if (longValue2 < longValue4) {
            this.r = 0;
            if (minecraftClient.options != null) {
               minecraftClient.options.sprintKey.setPressed(true);
            }
         } else {
            this.r = minecraftClient.player.verticalCollision ? this.r + 1 : 0;
            if (minecraftClient.options != null) {
               minecraftClient.options.sprintKey.setPressed(true);
            }

            if (this.r >= this.q()) {
               eventMoveInput1.setJump(true);
            }
         }
      } else {
         this.r = 0;
      }
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (eventPacket1.getType() == EventPacket.Type.SEND && this.o && eventPacket1.getPacket() instanceof CommonPongC2SPacket commonPongC2SPacket2) {
         this.u.add(commonPongC2SPacket2);
         eventPacket1.cancel();
      } else {
         if (eventPacket1.getType() == EventPacket.Type.RECEIVE && this.o && eventPacket1.getPacket() instanceof PlayerPositionLookS2CPacket) {
            this.t = System.nanoTime() + a(this.m.f());
            this.r();
         }
      }
   }

   private boolean a(EventMoveInput eventMoveInput1) {
      return eventMoveInput1.getForward() != 0.0F || eventMoveInput1.getStrafe() != 0.0F;
   }

   private void r() {
      this.u();
      this.n = TimerState.CHARGING;
      this.p = false;
      this.q = 0;
      this.r = 0;
      this.s = System.nanoTime();
      dev.leg1tka.api.utils.math.RenderTickTiming.a(0.05F);
   }

   private void s() {
      this.n = TimerState.BOOSTING;
      this.p = false;
      this.q = 0;
      this.r = 0;
      this.s = System.nanoTime();
      dev.leg1tka.api.utils.math.RenderTickTiming.a(1.0F);
   }

   private void t() {
      this.u();
      this.o = false;
      this.n = TimerState.CHARGING;
      this.p = false;
      this.q = 0;
      this.r = 0;
      this.s = 0L;
      this.t = 0L;
      dev.leg1tka.api.utils.math.RenderTickTiming.a();
   }

   private void u() {
      if (minecraftClient != null && minecraftClient.getNetworkHandler() != null && minecraftClient.player != null) {
         CommonPongC2SPacket commonPongC2SPacket1;
         while ((commonPongC2SPacket1 = (CommonPongC2SPacket)this.u.poll()) != null) {
            dev.leg1tka.network.PacketSender.a(commonPongC2SPacket1);
         }
      } else {
         this.u.clear();
      }
   }

   static enum TimerState {
      CHARGING,
      BOOSTING;

      private TimerState() {
      }
   }
}
