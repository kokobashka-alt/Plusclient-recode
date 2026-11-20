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
import dev.leg1tka.api.events.implement.EventUpdate;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.util.hit.BlockHitResult;

public class AutoTool extends Module {
   public static AutoTool c = new AutoTool();
   private final BooleanSetting d = new BooleanSetting("Пакетный", false);
   private final BooleanSetting e = new BooleanSetting(
      "Видно только для других людей",
      false
   );
   private int f = -1;

   public AutoTool() {
      super(
         "AutoTool",
         "При копании берет лучший предмет",
         Category.PLAYER
      );
      this.addSettings(new Setting[]{this.d, this.e});
   }

   @EventLink
   public void onEvent(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null && minecraftClient.interactionManager != null && !minecraftClient.player.isCreative()) {
         if (minecraftClient.interactionManager.isBreakingBlock()) {
            if (this.f == -1) {
               this.f = minecraftClient.player.getInventory().selectedSlot;
            }

            int intValue2 = this.p();
            if (intValue2 != -1) {
               this.b(intValue2);
            }
         } else if (this.f != -1) {
            this.b(this.f);
            this.f = -1;
         }
      } else {
         this.f = -1;
      }
   }

   private void b(int intValue1) {
      if (intValue1 >= 0 && intValue1 <= 8) {
         if (minecraftClient.player.getInventory().selectedSlot != intValue1) {
            if (this.e.getValue()) {
               minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue1));
            } else if (this.d.getValue()) {
               minecraftClient.player.getInventory().selectedSlot = intValue1;
               minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue1));
            } else {
               minecraftClient.player.getInventory().selectedSlot = intValue1;
            }
         }
      }
   }

   private int p() {
      if (minecraftClient.crosshairTarget instanceof BlockHitResult blockHitResult2) {
         BlockState blockState3 = minecraftClient.world.getBlockState(blockHitResult2.getBlockPos());
         return this.a(blockState3);
      } else {
         return -1;
      }
   }

   private int a(BlockState blockState1) {
      int intValue2 = -1;
      float floatValue3 = 1.0F;

      for (int intValue4 = 0; intValue4 < 9; intValue4++) {
         float floatValue5 = minecraftClient.player.getInventory().getStack(intValue4).getMiningSpeedMultiplier(blockState1);
         if (floatValue5 > floatValue3) {
            floatValue3 = floatValue5;
            intValue2 = intValue4;
         }
      }

      return intValue2;
   }

   @Override
   public void onDisable() {
      this.f = -1;
      super.onDisable();
   }
}
