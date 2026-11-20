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

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventMoveInput;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.LinkedList;
import java.util.Queue;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;

public class SprintInputController implements dev.leg1tka.api.ClientAccess {
   public static final dev.leg1tka.api.utils.player.SprintInputController c = new dev.leg1tka.api.utils.player.SprintInputController();
   private StopState d = StopState.IDLE;
   private final Queue e = new LinkedList();
   private final Queue f = new LinkedList();

   private SprintInputController() {
      EventInvoker.register(this);
   }

   public static void a(Runnable runnable0) {
      c.e.add(runnable0);
   }

   public static void b(Runnable runnable0) {
      c.f.add(runnable0);
      if (c.d == StopState.IDLE) {
         c.d = StopState.FAST_STOP;
      }
   }

   public static void c(Runnable runnable0) {
      if (minecraftClient.player != null) {
         c.a(c.b());
         c.d();
         runnable0.run();
         if (c.d == StopState.IDLE || c.d == StopState.RELEASE) {
            c.d = StopState.HOLD;
         }
      }
   }

   private KeyBinding[] b() {
      return new KeyBinding[]{minecraftClient.options.forwardKey, minecraftClient.options.backKey, minecraftClient.options.leftKey, minecraftClient.options.rightKey, minecraftClient.options.jumpKey, minecraftClient.options.sprintKey};
   }

   public static boolean a() {
      return c.d != StopState.IDLE || !c.e.isEmpty() || !c.f.isEmpty();
   }

   @EventLink(
      priority = 100
   )
   public void onMoveInput(EventMoveInput eventMoveInput1) {
      if (this.d == StopState.STOP || this.d == StopState.FAST_STOP || this.d == StopState.SEND || this.d == StopState.HOLD) {
         eventMoveInput1.setForward(0.0F);
         eventMoveInput1.setStrafe(0.0F);
         eventMoveInput1.setJump(false);
         eventMoveInput1.setSneak(false);
      }
   }

   @EventLink(
      priority = 100
   )
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player == null) {
         this.d = StopState.IDLE;
         this.e.clear();
         this.f.clear();
      } else {
         KeyBinding[] keyBindingArray2 = new KeyBinding[]{
            minecraftClient.options.forwardKey, minecraftClient.options.backKey, minecraftClient.options.leftKey, minecraftClient.options.rightKey, minecraftClient.options.jumpKey, minecraftClient.options.sprintKey
         };
         if (this.d == StopState.FAST_STOP) {
            this.a(keyBindingArray2);
            this.d();

            while (!this.f.isEmpty()) {
               Runnable runnable5 = (Runnable)this.f.poll();
               if (runnable5 != null) {
                  runnable5.run();
               }
            }

            this.d = StopState.RELEASE;
         } else if (this.d == StopState.STOP) {
            this.a(keyBindingArray2);
            this.d();

            while (!this.e.isEmpty()) {
               Runnable runnable4 = (Runnable)this.e.poll();
               if (runnable4 != null) {
                  runnable4.run();
               }
            }

            this.d = StopState.RELEASE;
         } else if (this.d == StopState.SEND) {
            this.a(keyBindingArray2);

            while (!this.e.isEmpty()) {
               Runnable runnable3 = (Runnable)this.e.poll();
               if (runnable3 != null) {
                  runnable3.run();
               }
            }

            this.d = StopState.RELEASE;
         } else if (this.d == StopState.HOLD) {
            this.a(keyBindingArray2);
            this.d = StopState.RELEASE;
         } else if (this.d == StopState.RELEASE) {
            this.b(keyBindingArray2);
            this.c();
            this.d = StopState.IDLE;
         } else {
            if (!this.f.isEmpty()) {
               this.d = StopState.FAST_STOP;
               this.a(keyBindingArray2);
            } else if (!this.e.isEmpty()) {
               this.d = StopState.STOP;
               this.a(keyBindingArray2);
            }
         }
      }
   }

   private void a(KeyBinding[] keyBindingArray1) {
      for (KeyBinding keyBinding5 : keyBindingArray1) {
         keyBinding5.setPressed(false);
      }
   }

   private void b(KeyBinding[] keyBindingArray1) {
      for (KeyBinding keyBinding5 : keyBindingArray1) {
         keyBinding5.setPressed(InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), keyBinding5.getDefaultKey().getCode()));
      }
   }

   private void c() {
      minecraftClient.options.sprintKey.setPressed(InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), minecraftClient.options.sprintKey.getDefaultKey().getCode()));
   }

   private void d() {
      minecraftClient.options.sprintKey.setPressed(false);
      if (minecraftClient.player != null && AspectDLC.INSTANCE.serverStorage.i()) {
         minecraftClient.player.setSprinting(false);
         minecraftClient.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.STOP_SPRINTING));
      }
   }

   static enum StopState {
      IDLE,
      FAST_STOP,
      STOP,
      SEND,
      HOLD,
      RELEASE;

      private StopState() {
      }
   }
}
