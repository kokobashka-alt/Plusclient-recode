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
import dev.leg1tka.api.events.implement.EventBlockCollide;
public class NoControllerWeb extends Module {
   public static final NoControllerWeb c = new NoControllerWeb();

   public NoControllerWeb() {
      super(
         "NoControllerWeb",
         "Позволяет ломать и бить сквозь паутину",
         Category.COMBAT
      );
   }

   @EventLink
   public void onBlockCollide(EventBlockCollide eventBlockCollide1) {
      if (minecraftClient.world != null && eventBlockCollide1.getPos() != null && minecraftClient.world.getBlockState(eventBlockCollide1.getPos()).isOf(net.minecraft.block.Blocks.COBWEB)) {
         eventBlockCollide1.cancel();
      }
   }

   public static void dev_leg1tka_o_gx_initializer() {}
}
