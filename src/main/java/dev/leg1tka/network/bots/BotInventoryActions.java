package dev.leg1tka.network.bots;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.util.function.Predicate;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

public final class BotInventoryActions {
   public static int a;
   private static int e;
   private static int f;
   private static int g;
   public static final Predicate<ItemStack> b = (ItemStack itemStack0) -> a(itemStack0);
   public static final Predicate<ItemStack> c = (ItemStack itemStack0) -> b(itemStack0);
   public static final Predicate<ItemStack> d = (ItemStack itemStack0) -> itemStack0.isOf(Items.GOLDEN_CARROT);

   private BotInventoryActions() {
   }

   public static boolean a(ItemStack itemStack0) {
      return itemStack0.isOf(Items.PHANTOM_MEMBRANE);
   }

   public static boolean b(ItemStack itemStack0) {
      if (!itemStack0.isOf(Items.POTION)) {
         return false;
      } else {
         PotionContentsComponent potionContentsComponent1 = (PotionContentsComponent)itemStack0.get(DataComponentTypes.POTION_CONTENTS);
         if (potionContentsComponent1 == null) {
            return false;
         } else {
            for (StatusEffectInstance statusEffectInstance3 : potionContentsComponent1.getEffects()) {
               if (statusEffectInstance3.getEffectType().value() == StatusEffects.SPEED.value()) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public static boolean c(ItemStack itemStack0) {
      return a(itemStack0) || b(itemStack0) || d.test(itemStack0);
   }

   public static int a(PlayerInventory playerInventory0) {
      int intValue1 = 0;

      for (int intValue2 = 1; intValue2 < 36; intValue2++) {
         if (playerInventory0.getStack(intValue2).isEmpty()) {
            intValue1++;
         }
      }

      return intValue1;
   }

   public static int a(PlayerInventory playerInventory0, Predicate<ItemStack> predicate1) {
      for (int intValue2 = 1; intValue2 < 9; intValue2++) {
         if (predicate1.test(playerInventory0.getStack(intValue2))) {
            return intValue2;
         }
      }

      return -1;
   }

   public static int b(PlayerInventory playerInventory0, Predicate<ItemStack> predicate1) {
      for (int intValue2 = 9; intValue2 < 36; intValue2++) {
         if (predicate1.test(playerInventory0.getStack(intValue2))) {
            return intValue2;
         }
      }

      return -1;
   }

   public static int c(PlayerInventory playerInventory0, Predicate<ItemStack> predicate1) {
      int intValue2 = 0;

      for (int intValue3 = 0; intValue3 < 9; intValue3++) {
         ItemStack itemStack4 = playerInventory0.getStack(intValue3);
         if (predicate1.test(itemStack4)) {
            intValue2 += itemStack4.getCount();
         }
      }

      return intValue2;
   }

   public static int d(PlayerInventory playerInventory0, Predicate<ItemStack> predicate1) {
      int intValue2 = 0;

      for (int intValue3 = 0; intValue3 < 36; intValue3++) {
         ItemStack itemStack4 = playerInventory0.getStack(intValue3);
         if (predicate1.test(itemStack4)) {
            intValue2 += itemStack4.getCount();
         }
      }

      return intValue2;
   }

   public static int b(PlayerInventory playerInventory0) {
      for (int intValue1 = 1; intValue1 < 9; intValue1++) {
         if (playerInventory0.getStack(intValue1).isEmpty()) {
            return intValue1;
         }
      }

      for (int intValue2 = 1; intValue2 < 9; intValue2++) {
         if (!c(playerInventory0.getStack(intValue2))) {
            return intValue2;
         }
      }

      return -1;
   }

   public static void a(ClientPlayerEntity clientPlayerEntity0, ClientPlayerInteractionManager clientPlayerInteractionManager1, int intValue2) {
      PlayerInventory playerInventory3 = clientPlayerEntity0.getInventory();
      if (playerInventory3.selectedSlot != intValue2) {
         playerInventory3.selectedSlot = intValue2;
      }

      clientPlayerInteractionManager1.syncSelectedSlot();
   }

   public static void a(ClientPlayerEntity clientPlayerEntity0, ClientPlayerInteractionManager clientPlayerInteractionManager1, int intValue2, int intValue3) {
      clientPlayerInteractionManager1.clickSlot(clientPlayerEntity0.playerScreenHandler.syncId, intValue2, intValue3, SlotActionType.SWAP, clientPlayerEntity0);
   }

   public static void a(ClientPlayerEntity clientPlayerEntity0, ClientPlayerInteractionManager clientPlayerInteractionManager1) {
      clientPlayerInteractionManager1.clickSlot(clientPlayerEntity0.playerScreenHandler.syncId, 36, 0, SlotActionType.QUICK_MOVE, clientPlayerEntity0);
   }

   public static void b(ClientPlayerEntity clientPlayerEntity0, ClientPlayerInteractionManager clientPlayerInteractionManager1) {
      clientPlayerInteractionManager1.clickSlot(clientPlayerEntity0.playerScreenHandler.syncId, 36, 1, SlotActionType.THROW, clientPlayerEntity0);
   }

   public static int a(int intValue0, int intValue1) {
      return intValue1 < 9 ? intValue0 + 27 + intValue1 : intValue0 + intValue1 - 9;
   }

   public static int b(int intValue0, int intValue1) {
      int intValue2 = intValue1 - intValue0;
      return intValue2 >= 27 ? intValue2 - 27 : intValue2 + 9;
   }

   public static int a(GenericContainerScreenHandler genericContainerScreenHandler0, int intValue1, ItemStack itemStack2, boolean flag3) {
      if (flag3) {
         int intValue4;
         if ((intValue4 = a(genericContainerScreenHandler0, intValue1, itemStack2, 1, 9)) >= 0) {
            return intValue4;
         }

         if ((intValue4 = a(genericContainerScreenHandler0, intValue1, 1, 9)) >= 0) {
            return intValue4;
         }
      }

      int intValue6;
      if ((intValue6 = a(genericContainerScreenHandler0, intValue1, itemStack2, 9, 36)) >= 0) {
         return intValue6;
      } else if ((intValue6 = a(genericContainerScreenHandler0, intValue1, 9, 36)) >= 0) {
         return intValue6;
      } else {
         if (!flag3) {
            if ((intValue6 = a(genericContainerScreenHandler0, intValue1, itemStack2, 1, 9)) >= 0) {
               return intValue6;
            }

            if ((intValue6 = a(genericContainerScreenHandler0, intValue1, 1, 9)) >= 0) {
               return intValue6;
            }
         }

         return -1;
      }
   }

   public static int a(GenericContainerScreenHandler genericContainerScreenHandler0, int intValue1, ItemStack itemStack2) {
      for (int intValue3 = 0; intValue3 < intValue1; intValue3++) {
         ItemStack itemStack4 = genericContainerScreenHandler0.getSlot(intValue3).getStack();
         if (!itemStack4.isEmpty() && itemStack4.getCount() < itemStack4.getMaxCount() && ItemStack.areItemsAndComponentsEqual(itemStack4, itemStack2)) {
            return intValue3;
         }
      }

      for (int intValue5 = 0; intValue5 < intValue1; intValue5++) {
         if (genericContainerScreenHandler0.getSlot(intValue5).getStack().isEmpty()) {
            return intValue5;
         }
      }

      return -1;
   }

   private static int a(GenericContainerScreenHandler genericContainerScreenHandler0, int intValue1, ItemStack itemStack2, int intValue3, int intValue4) {
      for (int intValue5 = intValue3; intValue5 < intValue4; intValue5++) {
         ItemStack itemStack6 = genericContainerScreenHandler0.getSlot(a(intValue1, intValue5)).getStack();
         if (!itemStack6.isEmpty() && itemStack6.getCount() < itemStack6.getMaxCount() && ItemStack.areItemsAndComponentsEqual(itemStack6, itemStack2)) {
            return a(intValue1, intValue5);
         }
      }

      return -1;
   }

   private static int a(GenericContainerScreenHandler genericContainerScreenHandler0, int intValue1, int intValue2, int intValue3) {
      for (int intValue4 = intValue2; intValue4 < intValue3; intValue4++) {
         if (genericContainerScreenHandler0.getSlot(a(intValue1, intValue4)).getStack().isEmpty()) {
            return a(intValue1, intValue4);
         }
      }

      return -1;
   }
}
