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
import dev.leg1tka.mixin.SlotAccessor;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

public class LockSlot extends Module {
   public static LockSlot c = new LockSlot();
   private final MultiSetting d = new MultiSetting(
      "Слоты",
      new BooleanSetting("1", false),
      new BooleanSetting("2", false),
      new BooleanSetting("3", false),
      new BooleanSetting("4", false),
      new BooleanSetting("5", false),
      new BooleanSetting("6", false),
      new BooleanSetting("7", false),
      new BooleanSetting("8", false),
      new BooleanSetting("9", false)
   );

   public LockSlot() {
      super(
         "LockSlot",
         "Блокирует выброс предметов из выбранных слотов",
         Category.PLAYER
      );
      this.addSettings(new Setting[]{this.d});
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (minecraftClient.player != null && eventPacket1.getType() == EventPacket.Type.SEND) {
         if (!(minecraftClient.currentScreen instanceof HandledScreen)) {
            if (eventPacket1.getPacket() instanceof PlayerActionC2SPacket playerActionC2SPacket4) {
               if (playerActionC2SPacket4.getAction() == Action.DROP_ITEM || playerActionC2SPacket4.getAction() == Action.DROP_ALL_ITEMS) {
                  if (this.p()) {
                     eventPacket1.cancel();
                     this.d(minecraftClient.player.getInventory().selectedSlot);
                  }
               }
            } else {
               if (eventPacket1.getPacket() instanceof ClickSlotC2SPacket clickSlotC2SPacket2 && clickSlotC2SPacket2.getActionType() == SlotActionType.THROW) {
                  int intValue6 = this.c(clickSlotC2SPacket2.getSlot());
                  if (intValue6 >= 0 && this.b(intValue6)) {
                     eventPacket1.cancel();
                     this.d(intValue6);
                  }
               }
            }
         }
      }
   }

   public boolean p() {
      if (!this.isEnabled() || minecraftClient.player == null || minecraftClient.player.getMainHandStack().isEmpty()) {
         return false;
      } else {
         return minecraftClient.currentScreen instanceof HandledScreen ? false : this.b(minecraftClient.player.getInventory().selectedSlot);
      }
   }

   private boolean b(int intValue1) {
      return intValue1 >= 0 && intValue1 < this.d.e().size() ? this.d.e().get(intValue1).getValue() : false;
   }

   private int c(int intValue1) {
      if (minecraftClient.player != null && intValue1 >= 0 && intValue1 < minecraftClient.player.currentScreenHandler.slots.size()) {
         Slot slot2 = minecraftClient.player.currentScreenHandler.getSlot(intValue1);
         SlotAccessor slotAccessor3 = (SlotAccessor)slot2;
         int intValue4 = slotAccessor3.AspectDLC$getIndex();
         return slotAccessor3.AspectDLC$getInventory() == minecraftClient.player.getInventory() && intValue4 >= 0 && intValue4 <= 8 ? intValue4 : -1;
      } else {
         return -1;
      }
   }

   private void d(int intValue1) {
      dev.leg1tka.api.utils.text.ClientChat.a(
         "Выброс предмета из слота "
            + (intValue1 + 1)
            + " заблокирован"
      );
   }
}
