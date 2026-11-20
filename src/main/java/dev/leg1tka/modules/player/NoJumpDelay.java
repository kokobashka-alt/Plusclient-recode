package dev.leg1tka.modules.player;


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
import dev.leg1tka.api.events.implement.EventUpdate;
import dev.leg1tka.mixin.ILivingEntity;

public class NoJumpDelay extends Module {
   public static NoJumpDelay c = new NoJumpDelay();

   public NoJumpDelay() {
      super(
         "NoJumpDelay",
         "Убирает задержку на прыжок",
         Category.PLAYER
      );
   }

   @EventLink
   public void onEvent(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         ((ILivingEntity)minecraftClient.player).setJumpingCooldown(0);
      }
   }
}
