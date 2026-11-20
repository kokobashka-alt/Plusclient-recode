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
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

public class NoWeb extends Module {
   public static NoWeb c = new NoWeb();
   public ModeSetting d = new ModeSetting(
      "Мод",
      "Коллизия",
      "Коллизия",
      "Обычный",
      "Grim"
   );

   public NoWeb() {
      super(
         "NoWeb",
         "Убирает замедление от паутины",
         Category.MOVEMENT
      );
      this.addSettings(new Setting[]{this.d});
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         if (this.d.b("Коллизия")) {
            BlockPos blockPos2 = minecraftClient.player.getBlockPos();

            for (int intValue3 = -1; intValue3 <= 1; intValue3++) {
               for (int intValue4 = 0; intValue4 <= 2; intValue4++) {
                  for (int intValue5 = -1; intValue5 <= 1; intValue5++) {
                     BlockPos blockPos6 = blockPos2.add(intValue3, intValue4, intValue5);
                     if (minecraftClient.world.getBlockState(blockPos6).getBlock() == Blocks.COBWEB) {
                        minecraftClient.player.networkHandler.sendPacket(new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, blockPos6, Direction.UP));
                     }
                  }
               }
            }
         }

         if (this.d.b("Обычный") && (!minecraftClient.player.isSneaking() || !minecraftClient.player.isOnGround())) {
            boolean flag7 = false;
            boolean flag8 = false;

            for (double doubleValue21 = -0.295; doubleValue21 <= 0.295; doubleValue21 += 0.05) {
               for (double doubleValue17 = -0.295; doubleValue17 <= 0.295; doubleValue17 += 0.05) {
                  for (double doubleValue9 = (double)minecraftClient.player.getStandingEyeHeight(); doubleValue9 >= 0.0; doubleValue9 -= 0.1) {
                     BlockPos blockPos11 = BlockPos.ofFloored(minecraftClient.player.getX() + doubleValue21, minecraftClient.player.getY() + doubleValue9, minecraftClient.player.getZ() + doubleValue17);
                     if (minecraftClient.world.getBlockState(blockPos11).getBlock() == Blocks.COBWEB) {
                        flag7 = true;
                        break;
                     }
                  }
               }
            }

            if (!flag7) {
               for (double doubleValue22 = -0.295; doubleValue22 <= 0.295; doubleValue22 += 0.05) {
                  for (double doubleValue18 = -0.295; doubleValue18 <= 0.295; doubleValue18 += 0.05) {
                     BlockPos blockPos29 = BlockPos.ofFloored(minecraftClient.player.getX() + doubleValue22, minecraftClient.player.getY(), minecraftClient.player.getZ() + doubleValue18);
                     if (minecraftClient.world.getBlockState(blockPos29).getBlock() == Blocks.COBWEB) {
                        flag8 = true;
                        break;
                     }
                  }
               }
            }

            BlockPos blockPos15 = BlockPos.ofFloored(minecraftClient.player.getX(), minecraftClient.player.getY() + (double)minecraftClient.player.getStandingEyeHeight() + 0.2F, minecraftClient.player.getZ());
            if (!flag7 && !flag8 && minecraftClient.world.getBlockState(blockPos15).getBlock() == Blocks.COBWEB) {
               flag7 = true;
            }

            if (flag7 || flag8) {
               if (minecraftClient.options.jumpKey.isPressed()) {
                  minecraftClient.player.setVelocity(0.0, 0.8, 0.0);
               } else if (minecraftClient.options.sneakKey.isPressed()) {
                  minecraftClient.player.setVelocity(0.0, -0.8, 0.0);
               } else {
                  minecraftClient.player.setVelocity(0.0, 0.0, 0.0);
               }

               dev.leg1tka.api.utils.player.PlayerPositionUtils.a(0.21);
            }
         }

         if (this.d.b("Grim") && minecraftClient.player != null) {
            boolean flag16 = false;
            Box box19 = minecraftClient.player.getBoundingBox();

            for (BlockPos blockPos23 : BlockPos.iterate(
               MathHelper.floor(box19.minX),
               MathHelper.floor(box19.minY),
               MathHelper.floor(box19.minZ),
               MathHelper.floor(box19.maxX),
               MathHelper.floor(box19.maxY),
               MathHelper.floor(box19.maxZ)
            )) {
               if (minecraftClient.world.getBlockState(blockPos23).isOf(Blocks.COBWEB)) {
                  Box box26 = new Box(blockPos23);
                  if (box19.intersects(box26)) {
                     flag16 = true;
                  }
               }
            }

            if (flag16) {
               float floatValue24 = minecraftClient.player.getYaw();
               double doubleValue27 = 0.0;
               double doubleValue28 = 0.0;
               if (minecraftClient.player.input.playerInput.forward()) {
                  doubleValue27++;
               }

               if (minecraftClient.player.input.playerInput.backward()) {
                  doubleValue27--;
               }

               if (minecraftClient.player.input.playerInput.left()) {
                  doubleValue28++;
               }

               if (minecraftClient.player.input.playerInput.right()) {
                  doubleValue28--;
               }

               if (doubleValue27 != 0.0 || doubleValue28 != 0.0) {
                  if (doubleValue27 != 0.0) {
                     if (doubleValue28 > 0.0) {
                        floatValue24 += (float)(doubleValue27 > 0.0 ? -45 : 45);
                     } else if (doubleValue28 < 0.0) {
                        floatValue24 += (float)(doubleValue27 > 0.0 ? 45 : -45);
                     }

                     doubleValue28 = 0.0;
                     if (doubleValue27 > 0.0) {
                        doubleValue27 = 1.0;
                     } else {
                        doubleValue27 = -1.0;
                     }
                  }

                  double doubleValue10 = Math.toDegrees(Math.atan2(doubleValue28, doubleValue27)) + (double)floatValue24;
                  floatValue24 = (float)((doubleValue10 % 360.0 + 360.0) % 360.0);
               }

               double doubleValue30 = minecraftClient.player.getVelocity().y;
               if (!minecraftClient.options.jumpKey.isPressed()) {
                  if (minecraftClient.options.sneakKey.isPressed()) {
                     doubleValue30 = -3.6;
                  } else {
                     doubleValue30 = 0.0;
                  }
               } else {
                  doubleValue30 = doubleValue27 == 0.0 && doubleValue28 == 0.0 ? 1.4 : 1.2;
               }

               float floatValue12 = minecraftClient.player.getYaw() * (float) (Math.PI / 180.0);
               float floatValue13 = (float)doubleValue27 * 0.633F;
               float floatValue14 = (float)doubleValue28 * 0.633F;
               if (floatValue13 == 0.0F && floatValue14 == 0.0F) {
                  minecraftClient.player.setVelocity(0.0, doubleValue30, 0.0);
               } else {
                  minecraftClient.player
                     .setVelocity(
                        (double)(-MathHelper.sin(floatValue12) * floatValue13) + (double)(MathHelper.cos(floatValue12) * floatValue14),
                        doubleValue30,
                        (double)(MathHelper.cos(floatValue12) * floatValue13) + (double)(MathHelper.sin(floatValue12) * floatValue14)
                     );
               }
            }
         }
      }
   }
}
