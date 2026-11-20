package dev.leg1tka.modules.combat;


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
import dev.leg1tka.api.events.implement.EventUpdate;
import dev.leg1tka.api.events.implement.EventUpdatePost;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.MathHelper;
public class Velocity extends Module {
   public static final Velocity c = new Velocity();
   private static double d;
   private final ModeSetting e = new ModeSetting("Мод", "Vanilla", "Vanilla", "Grim", "Jump Reset");
   private final BooleanSetting f = new BooleanSetting("Взрывы", true);
   private volatile boolean g;
   private volatile double h;
   private volatile double i;
   private boolean j;
   private int k;

   public Velocity() {
      super(
         "Velocity",
         "Отключает отдачу от урона",
         Category.COMBAT
      );
      this.addSettings(new Setting[]{this.e, this.f});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.q();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.q();
   }

   private void q() {
      this.g = false;
      this.j = false;
      this.k = 0;
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (minecraftClient.player == null) return;
      if (eventPacket1.getType() == EventPacket.Type.RECEIVE) {
         if (eventPacket1.getPacket() instanceof net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket packet) {
            if (packet.getEntityId() == minecraftClient.player.getId()) {
               if (this.e.b("Vanilla") || this.e.b("Grim")) {
                  eventPacket1.cancel();
               } else if (this.e.b("Jump Reset")) {
                  this.h = (double)packet.getVelocityX() / 8000.0;
                  this.i = (double)packet.getVelocityZ() / 8000.0;
                  this.j = true;
               }
            }
         } else if (this.f.getValue() && eventPacket1.getPacket() instanceof net.minecraft.network.packet.s2c.play.ExplosionS2CPacket packet) {
            if (this.e.b("Vanilla") || this.e.b("Grim")) {
               eventPacket1.cancel();
            } else if (this.e.b("Jump Reset")) {
               if (packet.playerKnockback().isPresent()) {
                  net.minecraft.util.math.Vec3d vec = packet.playerKnockback().get();
                  this.h = vec.x;
                  this.i = vec.z;
                  this.j = true;
               }
            }
         }
      }
   }

   @EventLink(
      priority = 200
   )
   public void onUpdate(EventUpdate eventUpdate1) {
      if (this.j && this.k > 0) {
         this.j = false;
         this.k = 0;
      }
   }

   @EventLink
   public void onUpdatePost(EventUpdatePost eventUpdatePost1) {
      this.j = false;
      this.k = 0;
   }

   public boolean p() {
      if (this.isEnabled() && this.j && this.e.b("Jump Reset")) {
         if (this.k == 0) {
            this.k = this.r() ? 1 : -1;
         }

         return this.k > 0;
      } else {
         return false;
      }
   }

   private boolean r() {
      ClientPlayerEntity clientPlayerEntity1 = minecraftClient.player;
      if (clientPlayerEntity1 == null || AttackAura.c == null || !AttackAura.c.isEnabled()) {
         return false;
      } else if (!minecraftClient.options.forwardKey.isPressed()) {
         return false;
      } else if (clientPlayerEntity1.isOnGround()
         && !clientPlayerEntity1.isTouchingWater()
         && !clientPlayerEntity1.isInLava()
         && !clientPlayerEntity1.isClimbing()
         && !clientPlayerEntity1.hasVehicle()
         && !clientPlayerEntity1.isGliding()
         && !clientPlayerEntity1.getAbilities().flying) {
         double doubleValue2 = Math.sqrt(this.h * this.h + this.i * this.i);
         if (doubleValue2 < 1.0E-4) {
            return false;
         } else {
            float floatValue4 = clientPlayerEntity1.getYaw() * (float) (Math.PI / 180.0);
            double doubleValue5 = ((double)MathHelper.sin(floatValue4) * this.h - (double)MathHelper.cos(floatValue4) * this.i) / doubleValue2;
            return doubleValue5 >= 0.34;
         }
      } else {
         return false;
      }
   }

   @EventLink(
      priority = -300
   )
   public void onMoveInput(EventMoveInput eventMoveInput1) {
      if (this.p() && !(eventMoveInput1.getForward() <= 0.0F)) {
         eventMoveInput1.setJump(true);
      }
   }



      public static void dev_leg1tka_o_gy_initializer() {}
}
