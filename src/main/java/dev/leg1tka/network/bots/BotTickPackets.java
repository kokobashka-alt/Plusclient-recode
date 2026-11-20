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

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientTickEndC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.PlayerInput;

public final class BotTickPackets {
   public volatile double a;
   public volatile double b;
   public volatile double c;
   public volatile float d;
   public volatile float e;
   public volatile boolean f;
   public volatile boolean g;
   public volatile boolean h;
   public volatile boolean i;
   public volatile PlayerInput j = PlayerInput.DEFAULT;
   public volatile int k = -1;
   public volatile int l;
   public volatile double m;
   public volatile double n;
   public volatile double o;
   public volatile boolean p;
   public volatile boolean q;
   private boolean r;
   private double s;
   private double t;
   private double u;
   private double v;
   private double w;
   private double x;
   private int y;

   public BotTickPackets() {
   }

   public void a(ClientPlayerEntity clientPlayerEntity1, int intValue2) {
      this.a = clientPlayerEntity1.lastX;
      this.b = clientPlayerEntity1.lastBaseY;
      this.c = clientPlayerEntity1.lastZ;
      this.d = clientPlayerEntity1.lastYaw;
      this.e = clientPlayerEntity1.lastPitch;
      this.f = clientPlayerEntity1.lastOnGround;
      this.g = clientPlayerEntity1.lastHorizontalCollision;
      this.h = clientPlayerEntity1.lastSprinting;
      this.i = clientPlayerEntity1.lastSneaking;
      this.j = clientPlayerEntity1.lastPlayerInput;
      this.k = intValue2;
      this.l = clientPlayerEntity1.ticksSinceLastPositionPacketSent;
      this.s = this.v = this.a;
      this.t = this.w = this.b;
      this.u = this.x = this.c;
      this.y = 0;
      this.p = true;
   }

   public void a(Packet<?> packet1) {
      if (packet1 instanceof PlayerMoveC2SPacket playerMoveC2SPacket2) {
         if (playerMoveC2SPacket2.changesPosition()) {
            this.a = playerMoveC2SPacket2.getX(this.a);
            this.b = playerMoveC2SPacket2.getY(this.b);
            this.c = playerMoveC2SPacket2.getZ(this.c);
            this.l = 0;
            this.r = true;
            this.q = true;
         }

         if (playerMoveC2SPacket2.changesLook()) {
            this.d = playerMoveC2SPacket2.getYaw(this.d);
            this.e = playerMoveC2SPacket2.getPitch(this.e);
         }

         this.f = playerMoveC2SPacket2.isOnGround();
         this.g = playerMoveC2SPacket2.horizontalCollision();
      } else if (packet1 instanceof ClientCommandC2SPacket clientCommandC2SPacket3) {
         switch (clientCommandC2SPacket3.getMode()) {
            case START_SPRINTING:
               this.h = true;
               break;
            case STOP_SPRINTING:
               this.h = false;
               break;
            case PRESS_SHIFT_KEY:
               this.i = true;
               break;
            case RELEASE_SHIFT_KEY:
               this.i = false;
         }
      } else if (packet1 instanceof PlayerInputC2SPacket playerInputC2SPacket4) {
         this.j = playerInputC2SPacket4.input();
      } else if (packet1 instanceof UpdateSelectedSlotC2SPacket updateSelectedSlotC2SPacket5) {
         this.k = updateSelectedSlotC2SPacket5.getSelectedSlot();
      } else if (packet1 instanceof ClientTickEndC2SPacket) {
         if (!this.r) {
            this.l++;
         }

         this.r = false;
         this.v = this.s;
         this.w = this.t;
         this.x = this.u;
         this.s = this.a;
         this.t = this.b;
         this.u = this.c;
         this.y++;
      }
   }

   public void a() {
      double doubleValue1 = this.y > 1 ? this.s - this.v : 0.0;
      double doubleValue3 = this.y > 1 ? this.t - this.w : 0.0;
      double doubleValue5 = this.y > 1 ? this.u - this.x : 0.0;
      double doubleValue7 = this.f ? 0.54600006F : 0.91F;
      this.m = doubleValue1 * doubleValue7;
      this.o = doubleValue5 * doubleValue7;
      this.n = this.f ? -0.0784000015258789 : (doubleValue3 - 0.08) * 0.98F;
   }
}
