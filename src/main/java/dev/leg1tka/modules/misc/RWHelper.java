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

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventBinding;
import dev.leg1tka.api.events.implement.EventUpdate;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.PlayerInput;

public final class RWHelper extends Module {
   public static final RWHelper c = new RWHelper();
   private final BooleanSetting d = new BooleanSetting("Обходить Grim", true);
   private final dev.leg1tka.settings.KeybindSetting e = new dev.leg1tka.settings.KeybindSetting("Анти-Полёт", -1);
   private final dev.leg1tka.settings.KeybindSetting f = new dev.leg1tka.settings.KeybindSetting("Ловушка", -1);
   private int g = 0;
   private Item h;
   private SlotLocation i = SlotLocation.NONE;
   private int j = -1;
   private int k = -1;
   private boolean l;
   private boolean m;

   public RWHelper() {
      super("RWHelper", "Помощник для ReallyWorld", Category.MISC);
      this.addSettings(new Setting[]{this.d, this.e, this.f});
      EventInvoker.register(this);
   }

   @EventLink
   public void onKey(EventBinding eventBinding1) {
      if (minecraftClient.currentScreen == null) {
         if (eventBinding1.getKey() == this.e.getKey()) {
            if (this.d.getValue()) {
               dev.leg1tka.api.utils.player.SprintInputController.a(this::p);
            } else {
               this.p();
            }
         } else if (eventBinding1.getKey() == this.f.getKey()) {
            if (this.d.getValue()) {
               dev.leg1tka.api.utils.player.SprintInputController.a(() -> this.a(Items.HEART_OF_THE_SEA));
            } else {
               this.a(Items.HEART_OF_THE_SEA);
            }
         }
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      this.q();
   }

   private void p() {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         Slot slot1 = null;

         for (int intValue2 = 9; intValue2 <= 44; intValue2++) {
            if (minecraftClient.player.currentScreenHandler.getSlot(intValue2).getStack().isOf(Items.FIREWORK_STAR)) {
               slot1 = minecraftClient.player.currentScreenHandler.getSlot(intValue2);
               break;
            }
         }

         if (minecraftClient.player.getOffHandStack().isOf(Items.FIREWORK_STAR)) {
            minecraftClient.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.PRESS_SHIFT_KEY));
            minecraftClient.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.RELEASE_SHIFT_KEY));
         } else if (slot1 != null) {
            boolean flag4 = false;
            if (minecraftClient.player.isSprinting()) {
               minecraftClient.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(new PlayerInput(false, false, false, false, false, false, false)));
               minecraftClient.player.setSprinting(false);
               minecraftClient.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.STOP_SPRINTING));
               if (!Sprint.c.isEnabled()) {
                  minecraftClient.options.sprintKey.setPressed(false);
               }

               flag4 = true;
            }

            int intValue3 = minecraftClient.player.currentScreenHandler.syncId;
            minecraftClient.interactionManager.clickSlot(intValue3, slot1.id, 40, SlotActionType.SWAP, minecraftClient.player);
            minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(intValue3));
            minecraftClient.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.PRESS_SHIFT_KEY));
            minecraftClient.interactionManager.clickSlot(intValue3, slot1.id, 40, SlotActionType.SWAP, minecraftClient.player);
            minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(intValue3));
            minecraftClient.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.RELEASE_SHIFT_KEY));
            if (flag4) {
               minecraftClient.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(minecraftClient.player.input.playerInput));
            }
         }
      }
   }

   private boolean a(Item item1) {
      if (this.g == 0 && minecraftClient.player != null) {
         RWHelper.a value2 = this.b(item1);
         if (value2 == null) {
            return false;
         } else {
            this.h = item1;
            this.i = value2.type();
            this.j = value2.slot();
            this.k = minecraftClient.player.getInventory().selectedSlot;
            this.m = false;
            this.g = 1;
            return true;
         }
      } else {
         return false;
      }
   }

   private RWHelper.a b(Item item1) {
      if (minecraftClient.player.getMainHandStack().isOf(item1)) {
         int intValue4 = minecraftClient.player.getInventory().selectedSlot;
         return new RWHelper.a(SlotLocation.HOTBAR, intValue4 + 36);
      } else if (minecraftClient.player.getOffHandStack().isOf(item1)) {
         return new RWHelper.a(SlotLocation.OFFHAND, 45);
      } else {
         for (int intValue2 = 36; intValue2 <= 44; intValue2++) {
            if (minecraftClient.player.currentScreenHandler.getSlot(intValue2).getStack().isOf(item1)) {
               return new RWHelper.a(SlotLocation.HOTBAR, intValue2);
            }
         }

         for (int intValue3 = 9; intValue3 <= 35; intValue3++) {
            if (minecraftClient.player.currentScreenHandler.getSlot(intValue3).getStack().isOf(item1)) {
               return new RWHelper.a(SlotLocation.INVENTORY, intValue3);
            }
         }

         return null;
      }
   }

   private void q() {
      if (this.g != 0 && minecraftClient.player != null) {
         if (this.g == 1) {
            this.r();
            this.g = 2;
         } else if (this.g == 2) {
            this.r();
            this.s();
            this.g = 3;
         } else {
            if (this.g == 3) {
               this.t();
               this.u();
            }
         }
      }
   }

   private void r() {
      minecraftClient.options.sprintKey.setPressed(false);
      minecraftClient.player.setSprinting(false);
      if (!this.l) {
         this.l = true;
         minecraftClient.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.STOP_SPRINTING));
         if (Sprint.c.isEnabled()) {
            minecraftClient.options.sprintKey.setPressed(false);
         }
      }
   }

   private void s() {
      if (this.h != null && minecraftClient.player.currentScreenHandler != null) {
         if (this.i == SlotLocation.OFFHAND) {
            minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.OFF_HAND);
            this.m = true;
         } else if (this.i == SlotLocation.HOTBAR) {
            int intValue2 = this.j - 36;
            minecraftClient.player.getInventory().selectedSlot = intValue2;
            minecraftClient.interactionManager.syncSelectedSlot();
            minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
            minecraftClient.player.getInventory().selectedSlot = this.k;
            minecraftClient.interactionManager.syncSelectedSlot();
            this.m = true;
         } else {
            if (this.i == SlotLocation.INVENTORY) {
               int intValue1 = minecraftClient.player.currentScreenHandler.syncId;
               minecraftClient.interactionManager.clickSlot(intValue1, this.j, this.k, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(intValue1));
               minecraftClient.player.getInventory().selectedSlot = this.k;
               minecraftClient.interactionManager.syncSelectedSlot();
               minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
               minecraftClient.interactionManager.clickSlot(intValue1, this.j, this.k, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(intValue1));
               this.m = true;
            }
         }
      }
   }

   private void t() {
      this.l = false;
      minecraftClient.player.getInventory().selectedSlot = this.k;
      minecraftClient.interactionManager.syncSelectedSlot();
      minecraftClient.options.sprintKey.setPressed(InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), minecraftClient.options.sprintKey.getDefaultKey().getCode()));
   }

   private void u() {
      this.g = 0;
      this.h = null;
      this.i = SlotLocation.NONE;
      this.j = -1;
      this.k = -1;
      this.l = false;
      this.m = false;
   }

   @Override
   public void onDisable() {
      if (minecraftClient.player != null && this.m) {
         minecraftClient.player.getInventory().selectedSlot = this.k;
         minecraftClient.interactionManager.syncSelectedSlot();
      }

      this.u();
      if (minecraftClient.player != null) {
         minecraftClient.options.sprintKey.setPressed(InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), minecraftClient.options.sprintKey.getDefaultKey().getCode()));
      }

      super.onDisable();
   }

   static record a(SlotLocation type, int slot)  {

}

   static enum SlotLocation {
      NONE,
      HOTBAR,
      INVENTORY,
      OFFHAND;

      private SlotLocation() {
      }
   }
}
