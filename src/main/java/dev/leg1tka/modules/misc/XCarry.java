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
import dev.leg1tka.api.events.implement.EventUpdate;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;

public class XCarry extends Module {
   public static XCarry c = new XCarry();
   public BooleanSetting d = new BooleanSetting("Авто выкл", true);
   private boolean e = false;

   public XCarry() {
      super("XCarry", "Дополнительные слоты", Category.MISC);
      this.addSettings(new Setting[]{this.d});
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         if (eventPacket1.getPacket() instanceof CloseHandledScreenC2SPacket && minecraftClient.currentScreen instanceof InventoryScreen) {
            eventPacket1.cancel();
            this.e = true;
         }
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         if (this.e && minecraftClient.currentScreen == null) {
            if (this.d.getValue()) {
               this.toggle();
            }

            this.e = false;
         }
      }
   }
}
