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
import dev.leg1tka.api.events.implement.EventPacket;
import java.util.UUID;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket.Status;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;

public class RPSpoofer extends Module {
   public static RPSpoofer c = new RPSpoofer();

   public RPSpoofer() {
      super(
         "RPSpoofer",
         "Убирает ресурс-пак сервера",
         Category.PLAYER
      );
   }

   @EventLink
   public void onReceivePacket(EventPacket eventPacket1) {
      if (eventPacket1.getPacket() instanceof ResourcePackSendS2CPacket resourcePackSendS2CPacket2 && this.isEnabled() && minecraftClient.getNetworkHandler() != null) {
         UUID uUID4 = resourcePackSendS2CPacket2.id();
         minecraftClient.getNetworkHandler().sendPacket(new ResourcePackStatusC2SPacket(uUID4, Status.ACCEPTED));
         minecraftClient.getNetworkHandler().sendPacket(new ResourcePackStatusC2SPacket(uUID4, Status.SUCCESSFULLY_LOADED));
         eventPacket1.setCancelled(true);
      }
   }
}
