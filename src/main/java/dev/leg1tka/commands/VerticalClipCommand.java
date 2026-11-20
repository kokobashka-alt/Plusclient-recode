package dev.leg1tka.commands;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.shape.VoxelShape;

public class VerticalClipCommand extends dev.leg1tka.commands.ClientCommand {
   public VerticalClipCommand() {
      super("vclip");
   }

   @Override
   public void a(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder1) {
      literalArgumentBuilder1.then(this.a("Y", IntegerArgumentType.integer()).executes(commandContext0 -> {
         int intValue1x = (Integer)commandContext0.getArgument("Y", Integer.class);
         minecraftClient.player.setPosition(minecraftClient.player.getX(), minecraftClient.player.getY() + (double)intValue1x, minecraftClient.player.getZ());
         return 1;
      }));
      literalArgumentBuilder1.then(this.a("up").executes(commandContext1x -> {
         this.a(true);
         return 1;
      }));
      literalArgumentBuilder1.then(this.a("down").executes(commandContext1x -> {
         this.a(false);
         return 1;
      }));
   }

   private void a(boolean flag1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         int intValue2 = minecraftClient.player.getBlockY();
         int intValue3 = minecraftClient.world.getBottomY();
         int intValue4 = minecraftClient.world.getTopYInclusive() - 2;
         int intValue5 = flag1 ? 1 : -1;
         int intValue6 = flag1 ? intValue2 + 1 : intValue2 - 1;
         int intValue7 = flag1 ? intValue4 : intValue3;

         for (int intValue8 = intValue6; flag1 ? intValue8 <= intValue7 : intValue8 >= intValue7; intValue8 += intValue5) {
            if (this.a(intValue8)) {
               VoxelShape voxelShape9 = minecraftClient.world
                  .getBlockState(new BlockPos(minecraftClient.player.getBlockX(), intValue8 - 1, minecraftClient.player.getBlockZ()))
                  .getCollisionShape(minecraftClient.world, new BlockPos(minecraftClient.player.getBlockX(), intValue8 - 1, minecraftClient.player.getBlockZ()));
               double doubleValue10 = voxelShape9.isEmpty() ? 0.0 : voxelShape9.getMax(Axis.Y);
               minecraftClient.player.setPosition(minecraftClient.player.getX(), (double)intValue8 + doubleValue10, minecraftClient.player.getZ());
               return;
            }
         }
      }
   }

   private boolean a(int intValue1) {
      BlockPos blockPos2 = new BlockPos(minecraftClient.player.getBlockX(), intValue1 - 1, minecraftClient.player.getBlockZ());
      BlockPos blockPos3 = blockPos2.up();
      BlockPos blockPos4 = blockPos3.up();
      BlockState blockState5 = minecraftClient.world.getBlockState(blockPos2);
      return blockState5.getCollisionShape(minecraftClient.world, blockPos2).isEmpty()
         ? false
         : minecraftClient.world.getBlockState(blockPos3).getCollisionShape(minecraftClient.world, blockPos3).isEmpty() && minecraftClient.world.getBlockState(blockPos4).getCollisionShape(minecraftClient.world, blockPos4).isEmpty();
   }
}
