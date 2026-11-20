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
import dev.leg1tka.api.events.implement.EventMoveInput;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventTickPre;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.OnGroundOnly;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;

public final class NoFall extends Module {
   public static final NoFall c = new NoFall();
   private final ModeSetting f = new ModeSetting("Режим", "SpookyTime", "SpookyTime");
   public int d;
   public boolean e;
   private boolean g;
   private final dev.leg1tka.api.utils.time.Stopwatch h = new dev.leg1tka.api.utils.time.Stopwatch();

   public NoFall() {
      super(
         "NoFall",
         "Предотвращает получение урона от падения",
         Category.MOVEMENT
      );
      this.addSettings(new Setting[]{this.f});
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.g = false;
      this.e = false;
      this.d = 0;
      dev.leg1tka.api.utils.math.RenderTickTiming.a();
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (this.isEnabled() && minecraftClient.player != null) {
         if (!(eventPacket1.getPacket() instanceof GameJoinS2CPacket)
            && !(eventPacket1.getPacket() instanceof DisconnectS2CPacket)
            && !(eventPacket1.getPacket() instanceof PlayerRespawnS2CPacket)) {
            if (this.f.b("SpookyTime")) {
               if (eventPacket1.getType() == EventPacket.Type.SEND
                  && eventPacket1.getPacket() instanceof PlayerMoveC2SPacket playerMoveC2SPacket2
                  && this.e
                  && playerMoveC2SPacket2.isOnGround()
                  && this.d == 0) {
                  dev.leg1tka.network.PacketSender.a(new OnGroundOnly(true, false));
                  this.h.reset();
                  this.g = true;
               }

               if (eventPacket1.getPacket() instanceof PlayerPositionLookS2CPacket && this.g) {
                  this.d = 2;
                  this.g = false;
               }
            }
         } else {
            this.p();
         }
      }
   }

   @EventLink
   public void onTick(EventTickPre eventTickPre1) {
      if (this.isEnabled() && minecraftClient.player != null) {
         if (this.f.b("SpookyTime")) {
            this.e = minecraftClient.player.fallDistance > 3.0F;
            if (this.d == 2) {
               dev.leg1tka.api.utils.math.RenderTickTiming.a(0.5F);
            }

            if (this.d <= 1) {
               dev.leg1tka.api.utils.math.RenderTickTiming.a(1.0F);
            }

            if (this.d > 0) {
               this.d--;
            }

            if (this.h.hasElapsedStrict(300L)) {
               this.g = false;
               this.d = 0;
            }
         }
      }
   }

   @EventLink
   public void onMoveInput(EventMoveInput eventMoveInput1) {
      if (this.isEnabled() && minecraftClient.player != null) {
         if (this.f.b("SpookyTime")) {
            if (this.g) {
               eventMoveInput1.setJump(false);
            }

            if (this.d == 2) {
               eventMoveInput1.setJump(false);
            }

            if (this.d == 1) {
               eventMoveInput1.setJump(true);
            }
         }
      }
   }

   public void p() {
      this.d = 0;
      this.e = false;
      this.g = false;
      dev.leg1tka.api.utils.math.RenderTickTiming.a();
   }
}
