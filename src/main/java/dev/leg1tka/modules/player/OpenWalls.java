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

import dev.leg1tka.mixin.IMinecraftClientAccessor;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BeaconBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.block.CartographyTableBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.DecoratedPotBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.block.GrindstoneBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.LoomBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SmithingTableBlock;
import net.minecraft.block.StonecutterBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class OpenWalls extends Module {
   public static OpenWalls c = new OpenWalls();
   private final NumberSetting d = new NumberSetting("Дистанция", 4.5F, 3.0F, 6.0F, 0.1F);

   public OpenWalls() {
      super(
         "OpenWalls",
         "Открывает сундуки и контейнеры сквозь блоки",
         Category.PLAYER
      );
      this.addSettings(new Setting[]{this.d});
   }

   public boolean p() {
      if (!this.isEnabled() || minecraftClient.player == null || minecraftClient.world == null || minecraftClient.interactionManager == null) {
         return false;
      } else if (minecraftClient.currentScreen == null && !minecraftClient.player.isSpectator()) {
         HitResult hitResult1 = minecraftClient.crosshairTarget;
         if (hitResult1 instanceof BlockHitResult blockHitResult2 && hitResult1.getType() == Type.BLOCK) {
            BlockPos blockPos3 = blockHitResult2.getBlockPos();
            if (this.a(minecraftClient.world.getBlockState(blockPos3), blockPos3)) {
               return false;
            }
         }

         BlockHitResult blockHitResult6 = this.r();
         if (blockHitResult6 == null) {
            return false;
         } else {
            ActionResult actionResult7 = minecraftClient.interactionManager.interactBlock(minecraftClient.player, Hand.MAIN_HAND, blockHitResult6);
            if (actionResult7.isAccepted()) {
               minecraftClient.player.swingHand(Hand.MAIN_HAND);
               if (minecraftClient instanceof IMinecraftClientAccessor iMinecraftClientAccessor9) {
                  iMinecraftClientAccessor9.setItemUseCooldown(4);
               }

               return true;
            } else {
               actionResult7 = minecraftClient.interactionManager.interactBlock(minecraftClient.player, Hand.OFF_HAND, blockHitResult6);
               if (actionResult7.isAccepted()) {
                  minecraftClient.player.swingHand(Hand.OFF_HAND);
                  if (minecraftClient instanceof IMinecraftClientAccessor iMinecraftClientAccessor4) {
                     iMinecraftClientAccessor4.setItemUseCooldown(4);
                  }

                  return true;
               } else {
                  return false;
               }
            }
         }
      } else {
         return false;
      }
   }

   private BlockHitResult r() {
      double doubleValue1 = (double)this.d.f();
      Vec3d vec3d3 = minecraftClient.player.getCameraPosVec(1.0F);
      Vec3d vec3d4 = minecraftClient.player.getRotationVec(1.0F);
      BlockPos blockPos5 = null;
      double doubleValue6 = 0.05;

      for (double doubleValue8 = 0.0; doubleValue8 <= doubleValue1; doubleValue8 += doubleValue6) {
         Vec3d vec3d10 = vec3d3.add(vec3d4.multiply(doubleValue8));
         BlockPos blockPos11 = BlockPos.ofFloored(vec3d10);
         if (!blockPos11.equals(blockPos5)) {
            blockPos5 = blockPos11;
            if (minecraftClient.world.isChunkLoaded(blockPos11)) {
               BlockState blockState12 = minecraftClient.world.getBlockState(blockPos11);
               if (!blockState12.isAir() && this.a(blockState12, blockPos11)) {
                  Direction direction13 = this.a(blockPos11, vec3d3);
                  Vec3d vec3d14 = this.a(blockPos11, direction13);
                  return new BlockHitResult(vec3d14, direction13, blockPos11, false);
               }
            }
         }
      }

      return null;
   }

   private Direction a(BlockPos blockPos1, Vec3d vec3d2) {
      double doubleValue3 = (double)blockPos1.getX() + 0.5;
      double doubleValue5 = (double)blockPos1.getY() + 0.5;
      double doubleValue7 = (double)blockPos1.getZ() + 0.5;
      double doubleValue9 = vec3d2.x - doubleValue3;
      double doubleValue11 = vec3d2.y - doubleValue5;
      double doubleValue13 = vec3d2.z - doubleValue7;
      double doubleValue15 = Math.abs(doubleValue9);
      double doubleValue17 = Math.abs(doubleValue11);
      double doubleValue19 = Math.abs(doubleValue13);
      if (doubleValue15 > doubleValue17 && doubleValue15 > doubleValue19) {
         return doubleValue9 > 0.0 ? Direction.EAST : Direction.WEST;
      } else if (doubleValue17 > doubleValue15 && doubleValue17 > doubleValue19) {
         return doubleValue11 > 0.0 ? Direction.UP : Direction.DOWN;
      } else {
         return doubleValue13 > 0.0 ? Direction.SOUTH : Direction.NORTH;
      }
   }

   private Vec3d a(BlockPos blockPos1, Direction direction2) {
      double doubleValue3 = (double)blockPos1.getX() + 0.5 + (double)direction2.getOffsetX() * 0.5;
      double doubleValue5 = (double)blockPos1.getY() + 0.5 + (double)direction2.getOffsetY() * 0.5;
      double doubleValue7 = (double)blockPos1.getZ() + 0.5 + (double)direction2.getOffsetZ() * 0.5;
      doubleValue3 -= (double)direction2.getOffsetX() * 0.01;
      doubleValue5 -= (double)direction2.getOffsetY() * 0.01;
      doubleValue7 -= (double)direction2.getOffsetZ() * 0.01;
      return new Vec3d(doubleValue3, doubleValue5, doubleValue7);
   }

   private boolean a(BlockState blockState1, BlockPos blockPos2) {
      if (blockState1 != null && !blockState1.isAir()) {
         Block block3 = blockState1.getBlock();
         if (!(block3 instanceof ChestBlock)
            && !(block3 instanceof AbstractChestBlock)
            && !(block3 instanceof EnderChestBlock)
            && !(block3 instanceof BarrelBlock)
            && !(block3 instanceof ShulkerBoxBlock)
            && !(block3 instanceof HopperBlock)
            && !(block3 instanceof AbstractFurnaceBlock)
            && !(block3 instanceof BrewingStandBlock)
            && !(block3 instanceof DispenserBlock)
            && !(block3 instanceof DropperBlock)
            && !(block3 instanceof CraftingTableBlock)
            && !(block3 instanceof AnvilBlock)
            && !(block3 instanceof EnchantingTableBlock)
            && !(block3 instanceof BeaconBlock)
            && !(block3 instanceof GrindstoneBlock)
            && !(block3 instanceof CartographyTableBlock)
            && !(block3 instanceof LoomBlock)
            && !(block3 instanceof StonecutterBlock)
            && !(block3 instanceof SmithingTableBlock)
            && !(block3 instanceof LecternBlock)
            && !(block3 instanceof DecoratedPotBlock)) {
            String string4 = Registries.BLOCK.getId(block3).getPath();
            if (!string4.contains("crafter") && !string4.contains("chest") && !string4.contains("barrel") && !string4.contains("shulker") && !string4.contains("hopper")) {
               BlockEntity blockEntity5 = minecraftClient.world.getBlockEntity(blockPos2);
               return blockEntity5 instanceof LockableContainerBlockEntity || blockEntity5 instanceof Inventory;
            } else {
               return true;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public float q() {
      return this.d.f();
   }
}
