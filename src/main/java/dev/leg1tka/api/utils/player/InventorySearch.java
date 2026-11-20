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

import com.google.common.collect.Lists;
import com.mojang.text2speech.Narrator;
import dev.leg1tka.AspectDLC;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntry.Reference;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.collection.DefaultedList;

public class InventorySearch implements dev.leg1tka.api.ClientAccess {
   public static final dev.leg1tka.api.utils.player.InventorySearch c = new dev.leg1tka.api.utils.player.InventorySearch();

   public InventorySearch() {
   }

   public static int a(Item item0) {
      for (int intValue1 = 0; intValue1 < minecraftClient.player.getInventory().getChangeCount(); intValue1++) {
         if (minecraftClient.player.getInventory().getStack(intValue1).getItem().equals(item0)) {
            return intValue1;
         }
      }

      return -1;
   }

   public static int a(Item item0, int intValue1, int intValue2) {
      for (int intValue3 = intValue1; intValue3 < intValue2; intValue3++) {
         if (minecraftClient.player.getInventory().getStack(intValue3).getItem().equals(item0)) {
            return intValue3;
         }
      }

      return -1;
   }

   public static int a(List<Item> list0) {
      for (int intValue1 = 0; intValue1 < minecraftClient.player.getInventory().getChangeCount(); intValue1++) {
         for (Item item3 : list0) {
            if (minecraftClient.player.getInventory().getStack(intValue1).getItem().equals(item3)) {
               return intValue1;
            }
         }
      }

      return -1;
   }

   public static int b(Item item0) {
      for (int intValue1 = 0; intValue1 < 9; intValue1++) {
         if (minecraftClient.player.getInventory().getStack(intValue1).getItem().equals(item0)) {
            return intValue1;
         }
      }

      return -1;
   }

   public static int b(List<Item> list0) {
      for (int intValue1 = 0; intValue1 < 9; intValue1++) {
         for (Item item3 : list0) {
            if (minecraftClient.player.getInventory().getStack(intValue1).getItem().equals(item3)) {
               return intValue1;
            }
         }
      }

      return -1;
   }

   public static int a(Predicate<ItemStack> predicate0) {
      for (int intValue1 = 0; intValue1 < 45; intValue1++) {
         ItemStack itemStack2 = minecraftClient.player.getInventory().getStack(intValue1);
         if (!itemStack2.isEmpty() && predicate0.test(itemStack2)) {
            return intValue1;
         }
      }

      return -1;
   }

   public static int b(Predicate<ItemStack> predicate0) {
      for (int intValue1 = 0; intValue1 < 9; intValue1++) {
         ItemStack itemStack2 = minecraftClient.player.getInventory().getStack(intValue1);
         if (!itemStack2.isEmpty() && predicate0.test(itemStack2)) {
            return intValue1;
         }
      }

      return -1;
   }

   public static void a(Runnable runnable0) {
      if (minecraftClient.player != null && minecraftClient.getNetworkHandler() != null) {
         dev.leg1tka.api.utils.player.SprintInputController.a(() -> {
            try {
               runnable0.run();
               dev.leg1tka.network.PacketSender.a(new CloseHandledScreenC2SPacket(0));
            } catch (Exception exception2) {
               exception2.printStackTrace();
            }
         });
      }
   }

   public static void b(Runnable runnable0) {
      a(runnable0, 100L);
   }

   public static void a(Runnable runnable0, long longValue1) {
      if (minecraftClient.player != null && minecraftClient.getNetworkHandler() != null) {
         try {
            dev.leg1tka.api.utils.player.KeyInputQueue.a(new dev.leg1tka.api.utils.player.KeyInputQueue.Task(longValue1, () -> {
               if (minecraftClient.player.isUsingItem()) {
                  minecraftClient.interactionManager.stopUsingItem(minecraftClient.player);
               }

               runnable0.run();
               dev.leg1tka.network.PacketSender.a(new CloseHandledScreenC2SPacket(0));
            }));
         } catch (Exception exception4) {
            exception4.printStackTrace();
         }
      }
   }

   public static void c(Item item0) {
      if (!minecraftClient.player.getItemCooldownManager().isCoolingDown(new ItemStack(item0))) {
         int intValue1 = a(item0, 9, 45);
         int intValue2 = a(item0, 0, 9);
         int intValue3 = minecraftClient.player.getInventory().selectedSlot;
         boolean flag4 = minecraftClient.player.isUsingItem();
         Hand hand5 = minecraftClient.player.getActiveHand();
         if (minecraftClient.player.getMainHandStack().getItem() == item0) {
            if (!flag4) {
               minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
            }
         } else if (minecraftClient.player.getOffHandStack().getItem() == item0) {
            minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.OFF_HAND);
         } else if (flag4 && hand5 == Hand.MAIN_HAND) {
            if (intValue2 != -1) {
               minecraftClient.interactionManager.clickSlot(0, 36 + intValue2, 40, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
               minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.OFF_HAND);
               minecraftClient.interactionManager.clickSlot(0, 36 + intValue2, 40, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
            } else if (intValue1 != -1) {
               minecraftClient.interactionManager.clickSlot(0, intValue1, 40, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
               minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.OFF_HAND);
               minecraftClient.interactionManager.clickSlot(0, intValue1, 40, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
            }
         } else if (intValue2 != -1) {
            minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue2));
            minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
            minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue3));
         } else {
            if (intValue1 != -1) {
               int intValue6 = -1;

               for (int intValue7 = 0; intValue7 < 8; intValue7++) {
                  ItemStack itemStack8 = minecraftClient.player.getInventory().getStack(intValue7);
                  if (itemStack8.isEmpty()) {
                     intValue6 = intValue7;
                  }

                  if (itemStack8.getUseAction() == UseAction.NONE) {
                     intValue6 = intValue7;
                  }
               }

               int intValue9 = intValue6 == -1 ? 8 : intValue6;
               dev.leg1tka.api.utils.player.SprintInputController.a(() -> {
                  minecraftClient.interactionManager.clickSlot(0, intValue1, intValue9, SlotActionType.SWAP, minecraftClient.player);
                  minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
                  minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue9));
                  minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
                  minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue3));
                  minecraftClient.interactionManager.clickSlot(0, intValue1, intValue9, SlotActionType.SWAP, minecraftClient.player);
                  minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
               });
            }
         }
      }
   }

   public static void d(Item item0) {
      if (!minecraftClient.player.getItemCooldownManager().isCoolingDown(new ItemStack(item0))) {
         int intValue1 = a(item0, 9, 45);
         int intValue2 = a(item0, 0, 9);
         int intValue3 = minecraftClient.player.getInventory().selectedSlot;
         boolean flag4 = minecraftClient.player.isUsingItem();
         Hand hand5 = minecraftClient.player.getActiveHand();
         if (minecraftClient.player.getMainHandStack().getItem() == item0) {
            if (!flag4) {
               minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
            }
         } else if (minecraftClient.player.getOffHandStack().getItem() == item0) {
            minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.OFF_HAND);
         } else if (flag4 && hand5 == Hand.MAIN_HAND) {
            if (intValue2 != -1) {
               minecraftClient.interactionManager.clickSlot(0, 36 + intValue2, 40, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
               minecraftClient.interactionManager
                  .sendSequencedPacket(minecraftClient.world, intValue0x -> new PlayerInteractItemC2SPacket(Hand.OFF_HAND, intValue0x, minecraftClient.player.getYaw(), minecraftClient.player.getPitch()));
               minecraftClient.interactionManager.clickSlot(0, 36 + intValue2, 40, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
            } else if (intValue1 != -1) {
               minecraftClient.interactionManager.clickSlot(0, intValue1, 40, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
               minecraftClient.interactionManager
                  .sendSequencedPacket(minecraftClient.world, intValue0x -> new PlayerInteractItemC2SPacket(Hand.OFF_HAND, intValue0x, minecraftClient.player.getYaw(), minecraftClient.player.getPitch()));
               minecraftClient.interactionManager.clickSlot(0, intValue1, 40, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
            }
         } else if (intValue2 != -1) {
            minecraftClient.player.getInventory().selectedSlot = intValue2;
            minecraftClient.interactionManager.syncSelectedSlot();
            minecraftClient.interactionManager
               .sendSequencedPacket(minecraftClient.world, intValue0x -> new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, intValue0x, minecraftClient.player.getYaw(), minecraftClient.player.getPitch()));
            minecraftClient.player.getInventory().selectedSlot = intValue3;
            minecraftClient.interactionManager.syncSelectedSlot();
         } else {
            if (intValue1 != -1) {
               int intValue6 = -1;

               for (int intValue7 = 0; intValue7 < 8; intValue7++) {
                  ItemStack itemStack8 = minecraftClient.player.getInventory().getStack(intValue7);
                  if (itemStack8.isEmpty()) {
                     intValue6 = intValue7;
                  }

                  if (itemStack8.getUseAction() == UseAction.NONE) {
                     intValue6 = intValue7;
                  }
               }

               int intValue9 = intValue6 == -1 ? 8 : intValue6;
               minecraftClient.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(new PlayerInput(false, false, false, false, false, false, false)));
               minecraftClient.interactionManager.clickSlot(0, intValue1, intValue9, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
               minecraftClient.player.getInventory().selectedSlot = intValue9;
               minecraftClient.interactionManager.syncSelectedSlot();
               minecraftClient.interactionManager
                  .sendSequencedPacket(minecraftClient.world, intValue0x -> new PlayerInteractItemC2SPacket(Hand.MAIN_HAND, intValue0x, minecraftClient.player.getYaw(), minecraftClient.player.getPitch()));
               minecraftClient.player.getInventory().selectedSlot = intValue3;
               minecraftClient.interactionManager.syncSelectedSlot();
               minecraftClient.interactionManager.clickSlot(0, intValue1, intValue9, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
               minecraftClient.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(minecraftClient.player.input.playerInput));
            }
         }
      }
   }

   public static void e(Item item0) {
      if (!minecraftClient.player.getItemCooldownManager().isCoolingDown(new ItemStack(item0))) {
         int intValue1 = a(item0, 9, 45);
         int intValue2 = a(item0, 0, 9);
         int intValue3 = minecraftClient.player.getInventory().selectedSlot;
         boolean flag4 = minecraftClient.player.isUsingItem();
         Hand hand5 = minecraftClient.player.getActiveHand();
         if (minecraftClient.player.getMainHandStack().getItem() == item0) {
            if (!flag4) {
               minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
            }
         } else if (minecraftClient.player.getOffHandStack().getItem() == item0) {
            minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.OFF_HAND);
         } else if (flag4 && hand5 == Hand.MAIN_HAND) {
            if (intValue2 != -1) {
               minecraftClient.interactionManager.clickSlot(0, 36 + intValue2, 40, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
               dev.leg1tka.api.utils.player.KeyInputQueue.a(new dev.leg1tka.api.utils.player.KeyInputQueue.TickTask(dev.leg1tka.api.utils.player.KeyInputQueue.TickTask.Unit.TICKS, 1, () -> {
                  minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.OFF_HAND);
                  minecraftClient.player.swingHand(Hand.OFF_HAND);
                  dev.leg1tka.api.utils.player.KeyInputQueue.a(new dev.leg1tka.api.utils.player.KeyInputQueue.TickTask(dev.leg1tka.api.utils.player.KeyInputQueue.TickTask.Unit.TICKS, 5, () -> {
                     minecraftClient.interactionManager.clickSlot(0, 36 + intValue2, 40, SlotActionType.SWAP, minecraftClient.player);
                     minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
                  }, true));
               }, true));
            } else if (intValue1 != -1) {
               minecraftClient.interactionManager.clickSlot(0, intValue1, 40, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
               dev.leg1tka.api.utils.player.KeyInputQueue.a(new dev.leg1tka.api.utils.player.KeyInputQueue.TickTask(dev.leg1tka.api.utils.player.KeyInputQueue.TickTask.Unit.TICKS, 1, () -> {
                  minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.OFF_HAND);
                  minecraftClient.player.swingHand(Hand.OFF_HAND);
                  dev.leg1tka.api.utils.player.KeyInputQueue.a(new dev.leg1tka.api.utils.player.KeyInputQueue.TickTask(dev.leg1tka.api.utils.player.KeyInputQueue.TickTask.Unit.TICKS, 5, () -> {
                     minecraftClient.interactionManager.clickSlot(0, intValue1, 40, SlotActionType.SWAP, minecraftClient.player);
                     minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
                  }, true));
               }, true));
            }
         } else if (intValue2 != -1) {
            minecraftClient.player.getInventory().selectedSlot = intValue2;
            minecraftClient.interactionManager.syncSelectedSlot();
            dev.leg1tka.api.utils.player.KeyInputQueue.a(new dev.leg1tka.api.utils.player.KeyInputQueue.TickTask(dev.leg1tka.api.utils.player.KeyInputQueue.TickTask.Unit.TICKS, 1, () -> {
               minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
               minecraftClient.player.swingHand(Hand.MAIN_HAND);
               dev.leg1tka.api.utils.player.KeyInputQueue.a(new dev.leg1tka.api.utils.player.KeyInputQueue.TickTask(dev.leg1tka.api.utils.player.KeyInputQueue.TickTask.Unit.TICKS, 5, () -> {
                  minecraftClient.player.getInventory().selectedSlot = intValue3;
                  minecraftClient.interactionManager.syncSelectedSlot();
               }, true));
            }, true));
         } else {
            if (intValue1 != -1) {
               int intValue6 = -1;

               for (int intValue7 = 0; intValue7 < 8; intValue7++) {
                  ItemStack itemStack8 = minecraftClient.player.getInventory().getStack(intValue7);
                  if (itemStack8.isEmpty()) {
                     intValue6 = intValue7;
                  }

                  if (itemStack8.getUseAction() == UseAction.NONE) {
                     intValue6 = intValue7;
                  }
               }

               int intValue9 = intValue6 == -1 ? 8 : intValue6;
               minecraftClient.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(new PlayerInput(false, false, false, false, false, false, false)));
               if (AspectDLC.INSTANCE.serverStorage.i()) {
                  minecraftClient.player.setSprinting(false);
                  minecraftClient.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.STOP_SPRINTING));
                  if (!Sprint.c.isEnabled()) {
                     minecraftClient.options.sprintKey.setPressed(false);
                  }
               }

               minecraftClient.interactionManager.clickSlot(0, intValue1, intValue9, SlotActionType.SWAP, minecraftClient.player);
               minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
               minecraftClient.player.getInventory().selectedSlot = intValue9;
               minecraftClient.interactionManager.syncSelectedSlot();
               dev.leg1tka.api.utils.player.KeyInputQueue.a(new dev.leg1tka.api.utils.player.KeyInputQueue.TickTask(dev.leg1tka.api.utils.player.KeyInputQueue.TickTask.Unit.TICKS, 1, () -> {
                  minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
                  minecraftClient.player.swingHand(Hand.MAIN_HAND);
                  dev.leg1tka.api.utils.player.KeyInputQueue.a(new dev.leg1tka.api.utils.player.KeyInputQueue.TickTask(dev.leg1tka.api.utils.player.KeyInputQueue.TickTask.Unit.TICKS, 5, () -> {
                     minecraftClient.player.getInventory().selectedSlot = intValue3;
                     minecraftClient.interactionManager.syncSelectedSlot();
                     minecraftClient.interactionManager.clickSlot(0, intValue1, intValue9, SlotActionType.SWAP, minecraftClient.player);
                     minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
                     minecraftClient.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(minecraftClient.player.input.playerInput));
                  }, true));
               }, true));
            }
         }
      }
   }

   public static void a(int intValue0, int intValue1, int intValue2, SlotActionType slotActionType3, PlayerEntity playerEntity4) {
      ScreenHandler screenHandler5 = playerEntity4.currentScreenHandler;
      if (intValue0 != screenHandler5.syncId) {
         Narrator.LOGGER.warn("Ignoring click NameProtect mismatching container. Click NameProtect {}, player has {}.", intValue0, screenHandler5.syncId);
      } else {
         DefaultedList<Slot> defaultedList6 = screenHandler5.slots;
         int intValue7 = defaultedList6.size();
         ArrayList arrayList8 = Lists.newArrayListWithCapacity(intValue7);

         for (Slot slot10 : defaultedList6) {
            arrayList8.add(slot10.getStack().copy());
         }

         screenHandler5.onSlotClick(intValue1, intValue2, slotActionType3, playerEntity4);
         Int2ObjectOpenHashMap int2ObjectOpenHashMap14 = new Int2ObjectOpenHashMap();

         for (int intValue11 = 0; intValue11 < intValue7; intValue11++) {
            ItemStack itemStack12 = (ItemStack)arrayList8.get(intValue11);
            ItemStack itemStack13 = ((Slot)defaultedList6.get(intValue11)).getStack();
            if (!ItemStack.areEqual(itemStack12, itemStack13)) {
               int2ObjectOpenHashMap14.put(intValue11, itemStack13.copy());
            }
         }

         dev.leg1tka.network.PacketSender.a(new ClickSlotC2SPacket(intValue0, screenHandler5.getRevision(), intValue1, intValue2, slotActionType3, screenHandler5.getCursorStack().copy(), int2ObjectOpenHashMap14));
      }
   }

   public static int a() {
      int intValue0 = -1;
      double doubleValue1 = -1.0;
      RegistryEntry registryEntry3 = (RegistryEntry)((Registry)MinecraftClient.getInstance().world.getRegistryManager().getOptional(RegistryKeys.ENCHANTMENT).get())
         .getEntry(Enchantments.PROTECTION.getValue())
         .orElseThrow();
      RegistryEntry registryEntry4 = (RegistryEntry)((Registry)MinecraftClient.getInstance().world.getRegistryManager().getOptional(RegistryKeys.ENCHANTMENT).get())
         .getEntry(Enchantments.UNBREAKING.getValue())
         .orElseThrow();
      RegistryEntry registryEntry5 = (RegistryEntry)((Registry)MinecraftClient.getInstance().world.getRegistryManager().getOptional(RegistryKeys.ENCHANTMENT).get())
         .getEntry(Enchantments.MENDING.getValue())
         .orElseThrow();

      for (int intValue6 = 0; intValue6 < 36; intValue6++) {
         ItemStack itemStack7 = minecraftClient.player.getInventory().getStack(intValue6);
         if (itemStack7.isOf(Items.ELYTRA)) {
            int intValue8 = EnchantmentHelper.getLevel(registryEntry3, itemStack7);
            int intValue9 = EnchantmentHelper.getLevel(registryEntry4, itemStack7);
            int intValue10 = EnchantmentHelper.getLevel(registryEntry5, itemStack7);
            int intValue11 = itemStack7.getMaxDamage();
            int intValue12 = itemStack7.getDamage();
            double doubleValue13 = (double)(intValue11 - intValue12) / (double)intValue11;
            double doubleValue15 = (double)(intValue8 * 100 + intValue9 * 10 + (intValue10 > 0 ? 1 : 0)) + doubleValue13 * 10.0;
            if (doubleValue15 > doubleValue1) {
               doubleValue1 = doubleValue15;
               intValue0 = intValue6;
            }
         }
      }

      return intValue0;
   }

   public static int b() {
      int intValue0 = -1;
      double doubleValue1 = -1.0;
      Reference reference3 = (Reference)((Registry)minecraftClient.world.getRegistryManager().getOptional(RegistryKeys.ENCHANTMENT).get())
         .getEntry(Enchantments.PROTECTION.getValue())
         .orElseThrow();
      Reference reference4 = (Reference)((Registry)minecraftClient.world.getRegistryManager().getOptional(RegistryKeys.ENCHANTMENT).get())
         .getEntry(Enchantments.UNBREAKING.getValue())
         .orElseThrow();
      Reference reference5 = (Reference)((Registry)minecraftClient.world.getRegistryManager().getOptional(RegistryKeys.ENCHANTMENT).get())
         .getEntry(Enchantments.MENDING.getValue())
         .orElseThrow();

      for (int intValue6 = 0; intValue6 < 36; intValue6++) {
         ItemStack itemStack7 = minecraftClient.player.getInventory().getStack(intValue6);
         Item item9 = itemStack7.getItem();
         if (item9 instanceof ArmorItem) {
            ArmorItem armorItem8 = (ArmorItem)item9;
            int intValue19 = EnchantmentHelper.getLevel(reference3, itemStack7);
            int intValue10 = EnchantmentHelper.getLevel(reference4, itemStack7);
            int intValue11 = EnchantmentHelper.getLevel(reference5, itemStack7);
            int intValue12 = h(armorItem8);
            int intValue13 = itemStack7.getMaxDamage();
            int intValue14 = itemStack7.getDamage();
            double doubleValue15 = (double)(intValue13 - intValue14) / (double)intValue13;
            double doubleValue17 = (double)(intValue12 * 10000 + intValue19 * 100 + intValue10 * 10 + (intValue11 > 0 ? 1 : 0)) + doubleValue15 * 10.0;
            if (doubleValue17 > doubleValue1) {
               doubleValue1 = doubleValue17;
               intValue0 = intValue6;
            }
         }
      }

      return intValue0;
   }

   private static int g(Item item0) {
      if (item0 == Items.NETHERITE_HELMET || item0 == Items.NETHERITE_CHESTPLATE || item0 == Items.NETHERITE_LEGGINGS || item0 == Items.NETHERITE_BOOTS) {
         return 6;
      } else if (item0 == Items.DIAMOND_HELMET || item0 == Items.DIAMOND_CHESTPLATE || item0 == Items.DIAMOND_LEGGINGS || item0 == Items.DIAMOND_BOOTS) {
         return 5;
      } else if (item0 == Items.IRON_HELMET || item0 == Items.IRON_CHESTPLATE || item0 == Items.IRON_LEGGINGS || item0 == Items.IRON_BOOTS) {
         return 4;
      } else if (item0 == Items.CHAINMAIL_HELMET || item0 == Items.CHAINMAIL_CHESTPLATE || item0 == Items.CHAINMAIL_LEGGINGS || item0 == Items.CHAINMAIL_BOOTS) {
         return 3;
      } else if (item0 == Items.GOLDEN_HELMET || item0 == Items.GOLDEN_CHESTPLATE || item0 == Items.GOLDEN_LEGGINGS || item0 == Items.GOLDEN_BOOTS) {
         return 2;
      } else {
         return item0 != Items.LEATHER_HELMET && item0 != Items.LEATHER_CHESTPLATE && item0 != Items.LEATHER_LEGGINGS && item0 != Items.LEATHER_BOOTS ? 0 : 1;
      }
   }

   private static int h(Item item0) {
      if (item0 == Items.NETHERITE_CHESTPLATE) {
         return 6;
      } else if (item0 == Items.DIAMOND_CHESTPLATE) {
         return 5;
      } else if (item0 == Items.IRON_CHESTPLATE) {
         return 4;
      } else if (item0 == Items.CHAINMAIL_CHESTPLATE) {
         return 3;
      } else if (item0 == Items.GOLDEN_CHESTPLATE) {
         return 2;
      } else {
         return item0 == Items.LEATHER_CHESTPLATE ? 1 : 0;
      }
   }

   public static int a(EquipmentSlot equipmentSlot0) {
      int intValue1 = -1;
      double doubleValue2 = -1.0;

      for (int intValue4 = 0; intValue4 < 36; intValue4++) {
         ItemStack itemStack5 = minecraftClient.player.getInventory().getStack(intValue4);
         if (itemStack5.getItem() instanceof ArmorItem && b(itemStack5) == equipmentSlot0) {
            double doubleValue6 = a(itemStack5);
            if (doubleValue6 > doubleValue2) {
               doubleValue2 = doubleValue6;
               intValue1 = intValue4;
            }
         }
      }

      return intValue1;
   }

   private static EquipmentSlot b(ItemStack itemStack0) {
      Item item1 = itemStack0.getItem();
      if (item1 instanceof ArmorItem) {
         if (item1 == Items.NETHERITE_HELMET
            || item1 == Items.DIAMOND_HELMET
            || item1 == Items.IRON_HELMET
            || item1 == Items.GOLDEN_HELMET
            || item1 == Items.CHAINMAIL_HELMET
            || item1 == Items.LEATHER_HELMET) {
            return EquipmentSlot.HEAD;
         }

         if (item1 == Items.NETHERITE_CHESTPLATE
            || item1 == Items.DIAMOND_CHESTPLATE
            || item1 == Items.IRON_CHESTPLATE
            || item1 == Items.GOLDEN_CHESTPLATE
            || item1 == Items.CHAINMAIL_CHESTPLATE
            || item1 == Items.LEATHER_CHESTPLATE) {
            return EquipmentSlot.CHEST;
         }

         if (item1 == Items.NETHERITE_LEGGINGS
            || item1 == Items.DIAMOND_LEGGINGS
            || item1 == Items.IRON_LEGGINGS
            || item1 == Items.GOLDEN_LEGGINGS
            || item1 == Items.CHAINMAIL_LEGGINGS
            || item1 == Items.LEATHER_LEGGINGS) {
            return EquipmentSlot.LEGS;
         }

         if (item1 == Items.NETHERITE_BOOTS
            || item1 == Items.DIAMOND_BOOTS
            || item1 == Items.IRON_BOOTS
            || item1 == Items.GOLDEN_BOOTS
            || item1 == Items.CHAINMAIL_BOOTS
            || item1 == Items.LEATHER_BOOTS) {
            return EquipmentSlot.FEET;
         }
      }

      return null;
   }

   public static double a(ItemStack itemStack0) {
      double doubleValue1 = 0.0;
      doubleValue1 += (double)g(itemStack0.getItem());
      Reference reference3 = (Reference)((Registry)minecraftClient.world.getRegistryManager().getOptional(RegistryKeys.ENCHANTMENT).get())
         .getEntry(Enchantments.PROTECTION.getValue())
         .orElseThrow();
      Reference reference4 = (Reference)((Registry)minecraftClient.world.getRegistryManager().getOptional(RegistryKeys.ENCHANTMENT).get())
         .getEntry(Enchantments.UNBREAKING.getValue())
         .orElseThrow();
      Reference reference5 = (Reference)((Registry)minecraftClient.world.getRegistryManager().getOptional(RegistryKeys.ENCHANTMENT).get())
         .getEntry(Enchantments.MENDING.getValue())
         .orElseThrow();
      doubleValue1 += (double)EnchantmentHelper.getLevel(reference3, itemStack0) * 0.75;
      doubleValue1 += (double)EnchantmentHelper.getLevel(reference4, itemStack0) * 0.3;
      doubleValue1 += (double)EnchantmentHelper.getLevel(reference5, itemStack0) * 0.1;
      double doubleValue6 = 1.0;
      if (itemStack0.isDamageable()) {
         int intValue8 = itemStack0.getMaxDamage();
         int intValue9 = intValue8 - itemStack0.getDamage();
         doubleValue6 = Math.max(0.05, (double)intValue9 / (double)intValue8);
      }

      return doubleValue1 * doubleValue6;
   }

   public static int f(Item item0) {
      for (int intValue1 = 0; intValue1 < 36; intValue1++) {
         if (minecraftClient.player.getInventory().getStack(intValue1).getItem() == item0) {
            return intValue1;
         }
      }

      return -1;
   }

   public static int b(Item item0, int intValue1, int intValue2) {
      for (int intValue3 = intValue1; intValue3 <= intValue2; intValue3++) {
         if (minecraftClient.player.getInventory().getStack(intValue3).getItem() == item0) {
            return intValue3;
         }
      }

      return -1;
   }

   public static int a(ItemStack itemStack0, RegistryKey<Enchantment> registryKey1) {
      Optional optional2 = minecraftClient.world.getRegistryManager().getOptional(RegistryKeys.ENCHANTMENT);
      if (optional2.isEmpty()) {
         return 0;
      } else {
         Reference reference3 = (Reference)((Registry)optional2.get()).getEntry(registryKey1.getValue()).orElse(null);
         return reference3 != null ? EnchantmentHelper.getLevel(reference3, itemStack0) : 0;
      }
   }

   @Generated
   public static dev.leg1tka.api.utils.player.InventorySearch c() {
      return c;
   }
}
