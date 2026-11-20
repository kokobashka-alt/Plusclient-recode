package dev.leg1tka.modules.movement;


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
import net.minecraft.block.Blocks;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.gui.screen.ingame.ShulkerBoxScreen;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.BlockPos;

public class HighJump extends Module {
   public static HighJump c = new HighJump();
   private final ModeSetting d = new ModeSetting("Режим", "Shulker", "Shulker", "Slime", "Boat");
   private final NumberSetting e = new NumberSetting("Множитель", 2.0F, 1.1F, 5.0F, 0.1F);
   private boolean f;
   private double g;
   private int h;

   public HighJump() {
      super(
         "HighJump",
         "Высокий прыжок от различных источников",
         Category.MOVEMENT
      );
      this.addSettings(new Setting[]{this.d, this.e});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.f = false;
      this.g = 0.0;
      this.h = 0;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.f = false;
      this.g = 0.0;
      this.h = 0;
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         if (this.h > 0) {
            this.h--;
         }

         if (this.d.b("Shulker")) {
            this.p();
         }

         if (this.d.b("Slime")) {
            this.q();
         }

         if (this.d.b("Boat")) {
            this.r();
         }
      }
   }

   private void p() {
      if (minecraftClient.currentScreen instanceof ShulkerBoxScreen) {
         BlockPos blockPos1 = minecraftClient.player.getBlockPos();
         BlockPos[] blockPosArray2 = new BlockPos[]{blockPos1.down(), blockPos1, blockPos1.north(), blockPos1.south(), blockPos1.east(), blockPos1.west()};
         boolean flag3 = false;

         for (BlockPos blockPos7 : blockPosArray2) {
            BlockState blockState8 = minecraftClient.world.getBlockState(blockPos7);
            if (blockState8.getBlock() instanceof ShulkerBoxBlock) {
               flag3 = true;
               break;
            }
         }

         if (flag3) {
            minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().x, 2.0, minecraftClient.player.getVelocity().z);
            minecraftClient.player.closeHandledScreen();
         }
      }
   }

   private void q() {
      double doubleValue1 = minecraftClient.player.getVelocity().y;
      BlockPos blockPos3 = minecraftClient.player.getBlockPos().down();
      BlockPos blockPos4 = minecraftClient.player.getBlockPos().down(2);
      boolean flag5 = minecraftClient.world.getBlockState(blockPos3).isOf(Blocks.SLIME_BLOCK) || minecraftClient.world.getBlockState(blockPos4).isOf(Blocks.SLIME_BLOCK);
      if (this.g < -0.1 && doubleValue1 > 0.1 && flag5 && this.h == 0) {
         double doubleValue6 = doubleValue1 * (double)this.e.f();
         minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().x, doubleValue6, minecraftClient.player.getVelocity().z);
         this.h = 5;
      }

      this.g = doubleValue1;
   }

   private void r() {
      boolean flag1 = minecraftClient.player.getVehicle() instanceof BoatEntity;
      if (this.f && !flag1 && this.h == 0) {
         minecraftClient.player.setVelocity(minecraftClient.player.getVelocity().x, 1.5, minecraftClient.player.getVelocity().z);
         this.h = 20;
      }

      this.f = flag1;
   }
}
