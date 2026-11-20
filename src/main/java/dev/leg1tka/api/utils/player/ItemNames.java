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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Generated;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BedItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;

public final class ItemNames implements dev.leg1tka.api.ClientAccess {
   private static int c = -1;

   public static int a(Item item0) {
      if (minecraftClient.player == null) {
         return 0;
      } else {
         int intValue1 = 0;

         for (int intValue2 = 0; intValue2 < minecraftClient.player.getInventory().size(); intValue2++) {
            ItemStack itemStack3 = minecraftClient.player.getInventory().getStack(intValue2);
            if (itemStack3.isOf(item0)) {
               intValue1 += itemStack3.getCount();
            }
         }

         return intValue1;
      }
   }

   public static dev.leg1tka.api.utils.player.FindItemResult a() {
      return a(itemStack0 -> itemStack0.getItem() instanceof AxeItem, false);
   }

   public static dev.leg1tka.api.utils.player.FindItemResult b() {
      return a(itemStack0 -> itemStack0.getItem() instanceof AxeItem, true);
   }

   public static dev.leg1tka.api.utils.player.FindItemResult c() {
      return a(itemStack0 -> itemStack0.getItem() instanceof PickaxeItem, false);
   }

   public static dev.leg1tka.api.utils.player.FindItemResult d() {
      return e();
   }

   public static dev.leg1tka.api.utils.player.FindItemResult e() {
      return a(itemStack0 -> itemStack0.getItem() instanceof PickaxeItem, true);
   }

   public static dev.leg1tka.api.utils.player.FindItemResult f() {
      return a(itemStack0 -> itemStack0.getItem() instanceof SwordItem, false);
   }

   public static dev.leg1tka.api.utils.player.FindItemResult g() {
      return a(itemStack0 -> itemStack0.getItem() instanceof SwordItem, true);
   }

   public static dev.leg1tka.api.utils.player.FindItemResult h() {
      return a(
         (dev.leg1tka.api.utils.player.ItemNames.a)(itemStack0 -> itemStack0.isOf(Items.SKELETON_SKULL)
               || itemStack0.isOf(Items.WITHER_SKELETON_SKULL)
               || itemStack0.isOf(Items.CREEPER_HEAD)
               || itemStack0.isOf(Items.PLAYER_HEAD)
               || itemStack0.isOf(Items.ZOMBIE_HEAD))
      );
   }

   public static int i() {
      if (minecraftClient.player == null) {
         return -1;
      } else {
         for (ItemStack itemStack1 : minecraftClient.player.getInventory().armor) {
            if (itemStack1.isOf(Items.ELYTRA) && itemStack1.getDamage() < itemStack1.getMaxDamage() - 1) {
               return -2;
            }
         }

         for (int intValue2 = 0; intValue2 < 36; intValue2++) {
            ItemStack itemStack3 = minecraftClient.player.getInventory().getStack(intValue2);
            if (itemStack3.isOf(Items.ELYTRA) && itemStack3.getDamage() < itemStack3.getMaxDamage() - 1) {
               return intValue2 < 9 ? intValue2 + 36 : intValue2;
            }
         }

         return -1;
      }
   }

   public static dev.leg1tka.api.utils.player.FindItemResult a(dev.leg1tka.api.utils.player.ItemNames.a value0) {
      if (minecraftClient.player != null) {
         if (value0.isValid(minecraftClient.player.getOffHandStack())) {
            return dev.leg1tka.api.utils.player.FindItemResult.a(minecraftClient.player.getOffHandStack());
         }

         for (int intValue1 = 0; intValue1 < 9; intValue1++) {
            ItemStack itemStack2 = minecraftClient.player.getInventory().getStack(intValue1);
            if (value0.isValid(itemStack2)) {
               return new dev.leg1tka.api.utils.player.FindItemResult(intValue1, true, itemStack2);
            }
         }
      }

      return dev.leg1tka.api.utils.player.FindItemResult.a();
   }

   public static dev.leg1tka.api.utils.player.FindItemResult a(List<Item> list0) {
      return a((dev.leg1tka.api.utils.player.ItemNames.a)(itemStack1 -> list0.contains(itemStack1.getItem())));
   }

   public static dev.leg1tka.api.utils.player.FindItemResult a(Item... itemArray0) {
      return a(Arrays.asList(itemArray0));
   }

   public static dev.leg1tka.api.utils.player.FindItemResult b(dev.leg1tka.api.utils.player.ItemNames.a value0) {
      if (minecraftClient.player != null) {
         for (int intValue1 = 35; intValue1 >= 0; intValue1--) {
            ItemStack itemStack2 = minecraftClient.player.getInventory().getStack(intValue1);
            if (value0.isValid(itemStack2)) {
               return new dev.leg1tka.api.utils.player.FindItemResult(intValue1, true, itemStack2);
            }
         }
      }

      return dev.leg1tka.api.utils.player.FindItemResult.a();
   }

   public static dev.leg1tka.api.utils.player.FindItemResult b(List<Item> list0) {
      return b((dev.leg1tka.api.utils.player.ItemNames.a)(itemStack1 -> list0.contains(itemStack1.getItem())));
   }

   public static dev.leg1tka.api.utils.player.FindItemResult b(Item... itemArray0) {
      return b(Arrays.asList(itemArray0));
   }

   public static dev.leg1tka.api.utils.player.FindItemResult c(@NotNull List<Block> list0) {
      return a(list0.stream().<Item>map(Block::asItem).toList());
   }

   public static dev.leg1tka.api.utils.player.FindItemResult a(Block... blockArray0) {
      return a(Arrays.stream(blockArray0).<Item>map(Block::asItem).toList());
   }

   public static dev.leg1tka.api.utils.player.FindItemResult d(@NotNull List<Block> list0) {
      return b(list0.stream().<Item>map(Block::asItem).toList());
   }

   public static dev.leg1tka.api.utils.player.FindItemResult b(Block... blockArray0) {
      return b(Arrays.stream(blockArray0).<Item>map(Block::asItem).toList());
   }

   public static void j() {
      if (minecraftClient.player != null) {
         c = minecraftClient.player.getInventory().selectedSlot;
      }
   }

   public static void k() {
      if (c != -1) {
         b(c);
      }

      c = -1;
   }

   public static void a(int intValue0) {
      j();
      b(intValue0);
   }

   public static void b(int intValue0) {
      if (minecraftClient.player != null && minecraftClient.getNetworkHandler() != null && intValue0 >= 0 && intValue0 <= 8) {
         if (minecraftClient.player.getInventory().selectedSlot != intValue0) {
            minecraftClient.player.getInventory().selectedSlot = intValue0;
            minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue0));
         }
      }
   }

   public static void c(int intValue0) {
      if (minecraftClient.player != null && minecraftClient.getNetworkHandler() != null && intValue0 >= 0 && intValue0 <= 8) {
         minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue0));
      }
   }

   public static dev.leg1tka.api.utils.player.FindItemResult l() {
      if (minecraftClient.player == null) {
         return dev.leg1tka.api.utils.player.FindItemResult.a();
      } else {
         Item item0 = minecraftClient.player.getMainHandStack().getItem();
         return !(item0 instanceof SwordItem) && !(item0 instanceof PickaxeItem) && !(item0 instanceof AxeItem) && !(item0 instanceof ShovelItem)
            ? a(
               (dev.leg1tka.api.utils.player.ItemNames.a)(itemStack0x -> itemStack0x.getItem() instanceof SwordItem
                     || itemStack0x.getItem() instanceof PickaxeItem
                     || itemStack0x.getItem() instanceof AxeItem
                     || itemStack0x.getItem() instanceof ShovelItem)
            )
            : new dev.leg1tka.api.utils.player.FindItemResult(minecraftClient.player.getInventory().selectedSlot, true, minecraftClient.player.getMainHandStack());
      }
   }

   public static float a(@NotNull ItemStack itemStack0, PlayerEntity playerEntity1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         float floatValue2 = a(itemStack0);
         if (minecraftClient.player.fallDistance > 0.0F) {
            floatValue2 += floatValue2 / 2.0F;
         }

         if (minecraftClient.player.hasStatusEffect(StatusEffects.STRENGTH)) {
            int intValue3 = Objects.requireNonNull(minecraftClient.player.getStatusEffect(StatusEffects.STRENGTH)).getAmplifier() + 1;
            floatValue2 += 3.0F * (float)intValue3;
         }

         return DamageUtil.getDamageLeft(
            playerEntity1, floatValue2, minecraftClient.world.getDamageSources().generic(), (float)playerEntity1.getArmor(), (float)playerEntity1.getAttributeValue(EntityAttributes.ARMOR_TOUGHNESS)
         );
      } else {
         return 0.0F;
      }
   }

   public static dev.leg1tka.api.utils.player.FindItemResult m() {
      return a((dev.leg1tka.api.utils.player.ItemNames.a)(itemStack0 -> itemStack0.getItem() instanceof BedItem));
   }

   public static dev.leg1tka.api.utils.player.FindItemResult n() {
      return b((dev.leg1tka.api.utils.player.ItemNames.a)(itemStack0 -> itemStack0.getItem() instanceof BedItem));
   }

   public static Item a(String string0) {
      if (string0 == null) {
         return Items.AIR;
      } else {
         String string1 = string0.toLowerCase();

         for (Block block3 : Registries.BLOCK) {
            if (block3.getTranslationKey().replace("block.minecraft.", "").equals(string1)) {
               return Item.fromBlock(block3);
            }
         }

         for (Item item5 : Registries.ITEM) {
            if (item5.getTranslationKey().replace("item.minecraft.", "").equals(string1)) {
               return item5;
            }
         }

         return Items.DIRT;
      }
   }

   public static int o() {
      if (minecraftClient.player == null) {
         return 0;
      } else {
         int intValue0 = 0;

         for (int intValue1 = 0; intValue1 < minecraftClient.player.getInventory().size(); intValue1++) {
            ItemStack itemStack2 = minecraftClient.player.getInventory().getStack(intValue1);
            if (itemStack2.getItem() instanceof BedItem) {
               intValue0 += itemStack2.getCount();
            }
         }

         return intValue0;
      }
   }

   private static dev.leg1tka.api.utils.player.FindItemResult a(dev.leg1tka.api.utils.player.ItemNames.a value0, boolean flag1) {
      if (minecraftClient.player == null) {
         return dev.leg1tka.api.utils.player.FindItemResult.a();
      } else {
         int intValue2 = -1;
         float floatValue3 = 0.0F;
         int intValue4 = flag1 ? 8 : 35;

         for (int intValue5 = 0; intValue5 <= intValue4; intValue5++) {
            ItemStack itemStack6 = minecraftClient.player.getInventory().getStack(intValue5);
            if (value0.isValid(itemStack6)) {
               float floatValue7 = a(itemStack6);
               if (floatValue7 > floatValue3) {
                  floatValue3 = floatValue7;
                  intValue2 = intValue5;
               }
            }
         }

         return intValue2 == -1 ? dev.leg1tka.api.utils.player.FindItemResult.a() : new dev.leg1tka.api.utils.player.FindItemResult(intValue2, true, minecraftClient.player.getInventory().getStack(intValue2));
      }
   }

   private static float a(ItemStack itemStack0) {
      AttributeModifiersComponent attributeModifiersComponent1 = (AttributeModifiersComponent)itemStack0.getOrDefault(
         DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT
      );
      double doubleValue2 = 1.0;

      for (Entry entry5 : attributeModifiersComponent1.modifiers()) {
         if (entry5.attribute().equals(EntityAttributes.ATTACK_DAMAGE)) {
            doubleValue2 += entry5.modifier().value();
         }
      }

      return (float)doubleValue2;
   }

   public static boolean b(Item item0) {
      return minecraftClient.player != null && (minecraftClient.player.getMainHandStack().isOf(item0) || minecraftClient.player.getOffHandStack().isOf(item0));
   }

   public static Hand c(Item item0) {
      if (minecraftClient.player == null) {
         return null;
      } else if (minecraftClient.player.getOffHandStack().isOf(item0)) {
         return Hand.OFF_HAND;
      } else {
         return minecraftClient.player.getMainHandStack().isOf(item0) ? Hand.MAIN_HAND : null;
      }
   }

   @Generated
   private ItemNames() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }

   public interface a {
      boolean isValid(ItemStack itemStack1);
   }
}
