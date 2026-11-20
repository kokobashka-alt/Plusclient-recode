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

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.OnGroundOnly;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.BlockPos;

public final class InventoryValidation {
   private static final MinecraftClient a = MinecraftClient.getInstance();

   private InventoryValidation() {
   }

   public static void a(double doubleValue0) {
      if (a.player != null && a.world != null) {
         if (doubleValue0 == 0.0) {
            dev.leg1tka.api.utils.text.ClientChat.a("Дистанция не должна быть 0");
         } else {
            int intValue2 = c();
            boolean flag3 = intValue2 != -1 && intValue2 != -2;
            if (flag3) {
               a(intValue2, true);
            }

            a(doubleValue0, flag3);
            if (flag3) {
               a(intValue2, false);
            }
         }
      }
   }

   public static void a() {
      if (a.player != null && a.world != null) {
         float floatValue0 = a(a.player.getBlockPos(), true);
         if (floatValue0 == 0.0F) {
            dev.leg1tka.api.utils.text.ClientChat.a(
               "Не удалось выполнить телепортацию"
            );
         } else {
            a((double)floatValue0);
         }
      }
   }

   public static void b() {
      if (a.player != null && a.world != null) {
         float floatValue0 = a(a.player.getBlockPos(), false);
         if (floatValue0 == 0.0F) {
            dev.leg1tka.api.utils.text.ClientChat.a(
               "Не удалось выполнить телепортацию"
            );
         } else {
            a((double)floatValue0);
         }
      }
   }

   private static void a(double doubleValue0, boolean flag2) {
      if (a.player != null && a.getNetworkHandler() != null) {
         double doubleValue3 = a.player.getX();
         double doubleValue5 = a.player.getY();
         double doubleValue7 = a.player.getZ();
         if (flag2) {
            for (int intValue9 = 0; intValue9 < 2; intValue9++) {
               a.getNetworkHandler().sendPacket(new PositionAndOnGround(doubleValue3, doubleValue5, doubleValue7, false, false));
            }

            a.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(a.player, Mode.START_FALL_FLYING));
            a.getNetworkHandler().sendPacket(new PositionAndOnGround(doubleValue3, doubleValue5 + doubleValue0, doubleValue7, false, false));
            a.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(a.player, Mode.START_FALL_FLYING));
         } else {
            int intValue11 = Math.max((int)(Math.abs(doubleValue0) / 1000.0), 3);

            for (int intValue10 = 0; intValue10 < intValue11; intValue10++) {
               a.getNetworkHandler().sendPacket(new OnGroundOnly(a.player.isOnGround(), false));
            }

            a.getNetworkHandler().sendPacket(new PositionAndOnGround(doubleValue3, doubleValue5 + doubleValue0, doubleValue7, false, false));
         }

         a.player.setPosition(doubleValue3, doubleValue5 + doubleValue0, doubleValue7);
         a.player.fallDistance = 0.0F;
         dev.leg1tka.api.utils.text.ClientChat.a(
            String.format(
               "Телепорт на %.1f по вертикали", doubleValue0
            )
         );
      }
   }

   private static float a(BlockPos blockPos0, boolean flag1) {
      if (a.world == null) {
         return 0.0F;
      } else {
         int intValue2 = flag1 ? 3 : -1;
         int intValue3 = flag1 ? 320 : -320;
         int intValue4 = flag1 ? 1 : -1;

         for (int intValue5 = intValue2; intValue5 != intValue3; intValue5 += intValue4) {
            BlockPos blockPos6 = blockPos0.add(0, intValue5, 0);
            if (a.world.getBlockState(blockPos6).isAir()) {
               return (float)(intValue5 + (flag1 ? 1 : -1));
            }

            if (!flag1 && a.world.getBlockState(blockPos6).getBlock() == Blocks.BEDROCK) {
               dev.leg1tka.api.utils.text.ClientChat.a(
                  "Тут нельзя телепортироваться под землю"
               );
               return 0.0F;
            }
         }

         return 0.0F;
      }
   }

   private static void a(int intValue0, boolean flag1) {
      if (a.interactionManager != null && a.player != null) {
         byte byteValue2 = 6;
         if (flag1) {
            a.interactionManager.clickSlot(0, intValue0, 0, SlotActionType.PICKUP, a.player);
            a.interactionManager.clickSlot(0, byteValue2, 0, SlotActionType.PICKUP, a.player);
         } else {
            a.interactionManager.clickSlot(0, byteValue2, 0, SlotActionType.PICKUP, a.player);
            a.interactionManager.clickSlot(0, intValue0, 0, SlotActionType.PICKUP, a.player);
         }
      }
   }

   private static int c() {
      if (a.player == null) {
         return -1;
      } else {
         ItemStack itemStack0 = a.player.getEquippedStack(EquipmentSlot.CHEST);
         if (itemStack0.getItem() == Items.ELYTRA) {
            return -2;
         } else {
            int intValue1 = -1;

            for (int intValue2 = 0; intValue2 < 36; intValue2++) {
               ItemStack itemStack3 = a.player.getInventory().getStack(intValue2);
               if (itemStack3.getItem() == Items.ELYTRA) {
                  intValue1 = intValue2;
                  break;
               }
            }

            if (intValue1 >= 0 && intValue1 < 9) {
               intValue1 += 36;
            }

            return intValue1;
         }
      }
   }
}
