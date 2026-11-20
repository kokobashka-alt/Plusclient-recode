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

import java.util.ArrayList;
import net.minecraft.item.BundleItem;
import net.minecraft.network.packet.c2s.play.BundleItemSelectedC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

final class BundleInventoryActions implements dev.leg1tka.api.ClientAccess {
   private static int c;
   private static int d;
   private static int e;
   private final ArrayList f = new ArrayList();
   private final dev.leg1tka.api.utils.player.SelectedSlotState g;
   private boolean h;
   private boolean i;

   BundleInventoryActions(dev.leg1tka.api.utils.player.SelectedSlotState value1) {
      this.g = value1;
   }

   boolean a() {
      return !this.f.isEmpty() || this.i;
   }

   void a(int intValue1, int intValue2, int intValue3, boolean flag4) {
      this.a(new dev.leg1tka.api.utils.player.BundleInventoryActions.a(0, intValue1, intValue2, -1, -1, intValue3, flag4));
   }

   void a(int intValue1, int intValue2, int intValue3, int intValue4, int intValue5, boolean flag6) {
      this.a(new dev.leg1tka.api.utils.player.BundleInventoryActions.a(1, intValue1, intValue2, intValue3, intValue4, intValue5, flag6));
   }

   void b(int intValue1, int intValue2, int intValue3, boolean flag4) {
      this.a(new dev.leg1tka.api.utils.player.BundleInventoryActions.a(2, intValue1, intValue2, -1, -1, intValue3, flag4));
   }

   void b() {
      this.f.clear();
      this.i = false;
   }

   private void a(dev.leg1tka.api.utils.player.BundleInventoryActions.a value1) {
      if (this.f.isEmpty() && !this.i && value1.pauseTicks > 0) {
         this.g.a(value1.pauseTicks);
      }

      this.f.add(value1);
   }

   void c() {
      if (minecraftClient.player != null && minecraftClient.interactionManager != null) {
         if (this.i) {
            this.i = false;
            this.e();
            this.d();
         } else if (!this.f.isEmpty() && this.g.a() < 1) {
            dev.leg1tka.api.utils.player.BundleInventoryActions.a value1 = (dev.leg1tka.api.utils.player.BundleInventoryActions.a)this.f.remove(0);
            this.h = false;

            boolean flag2 = switch (value1.type) {
               case 1 -> this.c(value1);
               case 2 -> this.d(value1);
               default -> this.b(value1);
            };
            if (!flag2) {
               this.d();
            } else {
               if (value1.deferClose) {
                  this.i = true;
               } else {
                  this.e();
                  this.d();
               }
            }
         }
      }
   }

   private void d() {
      if (!this.f.isEmpty()) {
         this.g.a(((dev.leg1tka.api.utils.player.BundleInventoryActions.a)this.f.get(0)).pauseTicks);
      }
   }

   private boolean b(dev.leg1tka.api.utils.player.BundleInventoryActions.a value1) {
      if (value1.from != -1 && value1.from != value1.to) {
         int intValue2 = a(value1.from);
         int intValue3 = a(value1.to);
         int intValue4 = a(value1.to, intValue3);
         if (intValue4 != -1) {
            this.a(intValue2, intValue4, SlotActionType.SWAP);
            return true;
         } else {
            int intValue5 = a(value1.from, intValue2);
            if (intValue5 != -1) {
               this.a(intValue3, intValue5, SlotActionType.SWAP);
            } else if (intValue2 != intValue3) {
               this.a(intValue2, 0, SlotActionType.SWAP);
               this.a(intValue3, 0, SlotActionType.SWAP);
               this.a(intValue2, 0, SlotActionType.SWAP);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private boolean c(dev.leg1tka.api.utils.player.BundleInventoryActions.a value1) {
      if (value1.from == value1.to) {
         return false;
      } else {
         int intValue2 = a(value1.from);
         int intValue3 = a(value1.empty);
         int intValue4 = a(value1.to, a(value1.to));
         PlayerScreenHandler playerScreenHandler5 = minecraftClient.player.playerScreenHandler;
         if (minecraftClient.player.getInventory().getStack(value1.empty).isEmpty() && playerScreenHandler5.getCursorStack().isEmpty()) {
            if (BundleItem.getSelectedStackIndex(playerScreenHandler5.getSlot(intValue2).getStack()) != value1.index) {
               playerScreenHandler5.selectBundleStack(intValue2, value1.index);
               minecraftClient.player.networkHandler.sendPacket(new BundleItemSelectedC2SPacket(intValue2, value1.index));
            }

            this.a(intValue2, 1, SlotActionType.PICKUP);
            this.a(intValue3, 0, SlotActionType.PICKUP);
            this.a(intValue3, intValue4, SlotActionType.SWAP);
         }

         return true;
      }
   }

   private boolean d(dev.leg1tka.api.utils.player.BundleInventoryActions.a value1) {
      this.a(a(value1.from), a(value1.to, a(value1.to)), SlotActionType.SWAP);
      return true;
   }

   private void a(int intValue1, int intValue2, SlotActionType slotActionType3) {
      if (intValue1 != -1 && intValue2 != -1) {
         if (this.h) {
            minecraftClient.player.playerScreenHandler.nextRevision();
         }

         this.h = true;
         minecraftClient.interactionManager.clickSlot(minecraftClient.player.playerScreenHandler.syncId, intValue1, intValue2, slotActionType3, minecraftClient.player);
      }
   }

   private void e() {
      if (minecraftClient.player != null && minecraftClient.player.networkHandler != null) {
         minecraftClient.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(minecraftClient.player.currentScreenHandler.syncId));
      }
   }

   private static int a(int intValue0) {
      if (intValue0 == 40) {
         return 45;
      } else {
         return intValue0 >= 0 && intValue0 <= 8 ? intValue0 + 36 : intValue0;
      }
   }

   private static int a(int intValue0, int intValue1) {
      if (intValue0 != 40 && intValue1 != 45) {
         return intValue1 >= 36 && intValue1 <= 44 ? intValue1 - 36 : -1;
      } else {
         return 40;
      }
   }

   static record a(int type, int from, int to, int empty, int index, int pauseTicks, boolean deferClose)  {






}
}
