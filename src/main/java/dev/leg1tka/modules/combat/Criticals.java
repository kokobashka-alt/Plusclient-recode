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
import dev.leg1tka.api.events.implement.EventAttackEntity;
public class Criticals extends Module {
   public static final Criticals c = new Criticals();

   public Criticals() {
      super(
         "Criticals",
         "Бьет критами под эффект плавного падения / в паутине",
         Category.COMBAT
      );
   }

   @EventLink
   public void onAttack(EventAttackEntity eventAttackEntity1) {
      if (minecraftClient.player == null) return;
      if (minecraftClient.player.getStatusEffects().stream().anyMatch(e -> e.getEffectType() == net.minecraft.entity.effect.StatusEffects.SLOW_FALLING)) {
         minecraftClient.player.networkHandler.sendPacket(new net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround(
            minecraftClient.player.getX(), minecraftClient.player.getY() + 0.0625, minecraftClient.player.getZ(), false, false
         ));
         minecraftClient.player.networkHandler.sendPacket(new net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround(
            minecraftClient.player.getX(), minecraftClient.player.getY(), minecraftClient.player.getZ(), false, false
         ));
      }
   }

   public static void dev_leg1tka_o_gz_initializer() {}
}
