package dev.leg1tka.modules.misc;


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
public class AutoAccept extends Module {
   public static final AutoAccept c = new AutoAccept();
   private final BooleanSetting d = new BooleanSetting("Только друзья", false);

   public AutoAccept() {
      super(
         "AutoAccept",
         "Автоматически принимает телепорт",
         Category.MISC
      );
      this.addSettings(new Setting[]{this.d});
   }

   @EventLink
   public void onEvent(EventPacket eventPacket1) {
      if (minecraftClient.player == null) return;
      if (eventPacket1.getType() == EventPacket.Type.RECEIVE) {
         if (eventPacket1.getPacket() instanceof net.minecraft.network.packet.s2c.play.GameMessageS2CPacket packet) {
            String text = packet.content().getString().toLowerCase();
            if (text.contains("/tpaccept") || text.contains("телепорт") || text.contains("tpaccept")) {
               minecraftClient.player.networkHandler.sendCommand("tpaccept");
            }
         }
      }
   }

   public static void dev_leg1tka_o_ia_initializer() {}
}
