package dev.leg1tka.api.utils.player;

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
import dev.leg1tka.api.events.implement.EventUpdate;
import net.minecraft.item.Item;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

public class InventoryPackets implements dev.leg1tka.api.ClientAccess {
   public static final dev.leg1tka.api.utils.player.InventoryPackets c = new dev.leg1tka.api.utils.player.InventoryPackets();
   private static int d;
   private SwapState e = SwapState.IDLE;
   private Item f;
   private int g = -1;
   private int h = -1;
   private int i = -1;
   private int j = 0;

   private InventoryPackets() {
      EventInvoker.register(this);
   }

   public static boolean a(Item item0) {
      return c.d(item0);
   }

   public static boolean b(Item item0) {
      return c(item0) != -1;
   }

   public static boolean a() {
      return c.e != SwapState.IDLE;
   }

   public static int c(Item item0) {
      return c.a(item0, 0, 35);
   }

   private boolean d(Item item1) {
      if (minecraftClient.player != null && this.e == SwapState.IDLE) {
         if (minecraftClient.player.getMainHandStack().isOf(item1)) {
            this.d();
            return true;
         } else if (minecraftClient.player.getOffHandStack().isOf(item1)) {
            minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.OFF_HAND);
            return true;
         } else {
            int intValue2 = this.a(item1, 0, 8);
            if (intValue2 != -1) {
               this.g = minecraftClient.player.getInventory().selectedSlot;
               minecraftClient.player.getInventory().selectedSlot = intValue2;
               minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue2));
               this.d();
               minecraftClient.player.getInventory().selectedSlot = this.g;
               minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(this.g));
               return true;
            } else {
               int intValue3 = this.a(item1, 9, 35);
               if (intValue3 == -1) {
                  return false;
               } else {
                  this.f = item1;
                  this.g = minecraftClient.player.getInventory().selectedSlot;
                  this.i = intValue3;
                  this.j = 0;
                  this.h = this.e();
                  minecraftClient.interactionManager.clickSlot(0, intValue3, this.h, SlotActionType.SWAP, minecraftClient.player);
                  minecraftClient.player.getInventory().selectedSlot = this.h;
                  minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(this.h));
                  this.e = SwapState.SWAPTO;
                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (this.e != SwapState.IDLE && minecraftClient.player != null) {
         this.j++;
         if (this.j > 5) {
            this.f();
         } else {
            if (this.e == SwapState.SWAPTO) {
               if (this.b()) {
                  this.j = 0;
                  this.e = SwapState.USEANDBACK;
               }
            } else if (this.e == SwapState.USEANDBACK) {
               this.d();
               minecraftClient.player.getInventory().selectedSlot = this.g;
               minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(this.g));
               minecraftClient.interactionManager.clickSlot(0, this.i, this.h, SlotActionType.SWAP, minecraftClient.player);
               this.j = 0;
               this.e = SwapState.SWAPBACK;
            } else if (this.e == SwapState.SWAPBACK && this.c()) {
               this.g();
            }
         }
      }
   }

   private boolean b() {
      return this.h >= 0 && this.f != null && minecraftClient.player.getInventory().selectedSlot == this.h && minecraftClient.player.getMainHandStack().isOf(this.f);
   }

   private boolean c() {
      return this.h >= 0 && this.i >= 0 && this.f != null && minecraftClient.player.getInventory().selectedSlot == this.g && !minecraftClient.player.getMainHandStack().isOf(this.f);
   }

   private void d() {
      minecraftClient.interactionManager.sendSequencedPacket(minecraftClient.world, intValue0 -> new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, intValue0, minecraftClient.player.getYaw(), minecraftClient.player.getPitch()));
      minecraftClient.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
      minecraftClient.player.swingHand(Hand.MAIN_HAND);
   }

   private int a(Item item1, int intValue2, int intValue3) {
      if (minecraftClient.player == null) {
         return -1;
      } else {
         for (int intValue4 = intValue3; intValue4 >= intValue2; intValue4--) {
            if (intValue4 < minecraftClient.player.getInventory().size() && minecraftClient.player.getInventory().getStack(intValue4).isOf(item1)) {
               return intValue4;
            }
         }

         return -1;
      }
   }

   private int e() {
      for (int intValue1 = 0; intValue1 < 9; intValue1++) {
         if (minecraftClient.player.getInventory().getStack(intValue1).isEmpty()) {
            return intValue1;
         }
      }

      return 8;
   }

   private void f() {
      if (minecraftClient.player == null) {
         this.g();
      } else {
         if (this.e == SwapState.SWAPBACK && this.i >= 0 && this.h >= 0 && this.f != null && minecraftClient.player.getInventory().getStack(this.h).isOf(this.f)) {
            minecraftClient.player.getInventory().selectedSlot = this.g;
            minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(this.g));
            minecraftClient.interactionManager.clickSlot(0, this.i, this.h, SlotActionType.SWAP, minecraftClient.player);
         } else if (this.g >= 0) {
            minecraftClient.player.getInventory().selectedSlot = this.g;
            minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(this.g));
         }

         this.g();
      }
   }

   private void g() {
      this.e = SwapState.IDLE;
      this.f = null;
      this.g = -1;
      this.i = -1;
      this.h = -1;
      this.j = 0;
   }

   static enum SwapState {
      IDLE,
      SWAPTO,
      USEANDBACK,
      SWAPBACK;

      private SwapState() {
      }
   }
}
