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

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ButtonBlock;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeverBlock;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

public final class BlockInteractions implements dev.leg1tka.api.ClientAccess {
   private static final double[][] c = new double[][]{{0.5, 0.5}, {0.25, 0.5}, {0.75, 0.5}, {0.5, 0.25}, {0.5, 0.75}};

   private BlockInteractions() {
   }

   public static boolean a(BlockPos blockPos0) {
      if (minecraftClient.world == null) {
         return false;
      } else {
         BlockState blockState1 = minecraftClient.world.getBlockState(blockPos0);
         return blockState1.isOf(Blocks.OBSIDIAN) || blockState1.isOf(Blocks.BEDROCK);
      }
   }

   public static dev.leg1tka.api.utils.player.BlockInteractionTarget a(BlockPos blockPos0, Vec3d vec3d1, double doubleValue2) {
      if (!a(blockPos0)) {
         return null;
      } else {
         dev.leg1tka.api.utils.player.BlockInteractionTarget value4 = a(blockPos0, Direction.UP, vec3d1, a(doubleValue2));
         return value4 != null ? value4 : b(blockPos0, vec3d1, doubleValue2);
      }
   }

   public static dev.leg1tka.api.utils.player.BlockInteractionTarget a(BlockPos blockPos0, Vec3d vec3d1, double doubleValue2, boolean flag4) {
      if (minecraftClient.world != null && minecraftClient.player != null && minecraftClient.world.getBlockState(blockPos0).isReplaceable()) {
         dev.leg1tka.api.utils.player.BlockInteractionTarget value5 = null;
         double doubleValue6 = Double.MAX_VALUE;
         ItemStack itemStack8 = (flag4 ? Items.COBWEB : Items.OBSIDIAN).getDefaultStack();

         for (Direction direction12 : Direction.values()) {
            BlockPos blockPos13 = blockPos0.offset(direction12.getOpposite());
            BlockState blockState14 = minecraftClient.world.getBlockState(blockPos13);
            if (!blockState14.isReplaceable() && !blockState14.isAir() && (flag4 || !blockState14.isOf(Blocks.COBWEB)) && !d(blockPos13)) {
               dev.leg1tka.api.utils.player.BlockInteractionTarget value15 = a(blockPos13, direction12, vec3d1, a(doubleValue2));
               if (value15 != null && a(blockPos0, value15, itemStack8)) {
                  double doubleValue16 = vec3d1.squaredDistanceTo(value15.hitVec());
                  if (doubleValue16 < doubleValue6) {
                     value5 = value15;
                     doubleValue6 = doubleValue16;
                  }
               }
            }
         }

         return value5;
      } else {
         return null;
      }
   }

   public static dev.leg1tka.api.utils.player.BlockInteractionTarget b(BlockPos blockPos0, Vec3d vec3d1, double doubleValue2) {
      if (minecraftClient.world != null && minecraftClient.player != null) {
         dev.leg1tka.api.utils.player.BlockInteractionTarget value4 = null;
         double doubleValue5 = Double.MAX_VALUE;

         for (Direction direction10 : Direction.values()) {
            dev.leg1tka.api.utils.player.BlockInteractionTarget value11 = a(blockPos0, direction10, vec3d1, a(doubleValue2));
            if (value11 != null) {
               double doubleValue12 = vec3d1.squaredDistanceTo(value11.hitVec());
               if (doubleValue12 < doubleValue5) {
                  value4 = value11;
                  doubleValue5 = doubleValue12;
               }
            }
         }

         return value4;
      } else {
         return null;
      }
   }

   public static boolean a(BlockPos blockPos0, BlockHitResult blockHitResult1, ItemStack itemStack2) {
      if (blockHitResult1 != null && minecraftClient.player != null && minecraftClient.world != null && !itemStack2.isEmpty()) {
         ItemPlacementContext itemPlacementContext3 = new ItemPlacementContext(minecraftClient.player, Hand.MAIN_HAND, itemStack2, blockHitResult1);
         return itemPlacementContext3.getBlockPos().equals(blockPos0) && itemPlacementContext3.canPlace();
      } else {
         return false;
      }
   }

   public static boolean a(BlockPos blockPos0, dev.leg1tka.api.utils.player.BlockInteractionTarget value1, ItemStack itemStack2) {
      return value1 != null && a(blockPos0, new BlockHitResult(value1.hitVec(), value1.side(), value1.neighbor(), value1.inside()), itemStack2);
   }

   public static BlockHitResult a(dev.leg1tka.api.utils.player.BlockInteractionTarget value0, double doubleValue1, Vec3d vec3d3, float floatValue4, float floatValue5) {
      if (value0 != null && minecraftClient.player != null && minecraftClient.world != null && !b(value0.neighbor())) {
         double doubleValue6 = a(doubleValue1);
         if (doubleValue6 <= 0.0) {
            return null;
         } else {
            Vec3d vec3d8 = Vec3d.fromPolar(floatValue5, floatValue4);
            BlockPos blockPos9 = value0.neighbor();
            BlockHitResult blockHitResult10 = minecraftClient.world.getBlockState(blockPos9).getOutlineShape(minecraftClient.world, blockPos9).raycast(vec3d3, vec3d3.add(vec3d8.multiply(doubleValue6)), blockPos9);
            boolean flag11 = blockHitResult10 != null && blockHitResult10.getType() == Type.BLOCK && blockHitResult10.getBlockPos().equals(blockPos9) && blockHitResult10.getSide() == value0.side();
            if (flag11 && dev.leg1tka.api.utils.player.BlockPlacementGeometry.a() && !blockHitResult10.isInsideBlock() && !dev.leg1tka.api.utils.player.BlockPlacementGeometry.a(vec3d3, blockHitResult10.getPos(), blockPos9, false)) {
               return null;
            } else {
               return flag11 && vec3d3.squaredDistanceTo(blockHitResult10.getPos()) <= doubleValue6 * doubleValue6 ? blockHitResult10 : null;
            }
         }
      } else {
         return null;
      }
   }

   public static double a(double doubleValue0) {
      return minecraftClient.player == null ? 0.0 : Math.max(0.0, Math.min(doubleValue0, minecraftClient.player.getBlockInteractionRange()));
   }

   public static int a() {
      if (minecraftClient.player != null && minecraftClient.getNetworkHandler() != null) {
         PlayerListEntry playerListEntry0 = minecraftClient.getNetworkHandler().getPlayerListEntry(minecraftClient.player.getUuid());
         int intValue1 = playerListEntry0 == null ? 0 : Math.max(0, playerListEntry0.getLatency());
         return MathHelper.clamp(12 + (intValue1 + 49) / 50 * 2, 12, 60);
      } else {
         return 20;
      }
   }

   public static int b() {
      if (minecraftClient.player != null && minecraftClient.getNetworkHandler() != null) {
         PlayerListEntry playerListEntry0 = minecraftClient.getNetworkHandler().getPlayerListEntry(minecraftClient.player.getUuid());
         int intValue1 = playerListEntry0 == null ? 0 : Math.max(0, playerListEntry0.getLatency());
         return (intValue1 + 49) / 50;
      } else {
         return 2;
      }
   }

   public static boolean b(BlockPos blockPos0) {
      return minecraftClient.world != null && minecraftClient.world.getPendingUpdateManager().blockPosToPendingUpdate.containsKey(blockPos0.asLong());
   }

   public static boolean c(BlockPos blockPos0) {
      return minecraftClient.player == null
         || minecraftClient.world == null
         || minecraftClient.player.getBoundingBox().intersects(new Box(blockPos0))
         || !minecraftClient.world.canPlace(Blocks.OBSIDIAN.getDefaultState(), blockPos0, ShapeContext.absent());
   }

   private static dev.leg1tka.api.utils.player.BlockInteractionTarget a(BlockPos blockPos0, Direction direction1, Vec3d vec3d2, double doubleValue3) {
      return b(blockPos0) ? null : a(minecraftClient.world.getBlockState(blockPos0).getOutlineShape(minecraftClient.world, blockPos0), blockPos0, direction1, vec3d2, doubleValue3);
   }

   private static dev.leg1tka.api.utils.player.BlockInteractionTarget a(VoxelShape voxelShape0, BlockPos blockPos1, Direction direction2, Vec3d vec3d3, double doubleValue4) {
      if (voxelShape0.isEmpty()) {
         return null;
      } else {
         Box box6 = voxelShape0.getBoundingBox().offset(blockPos1);
         if (box6.contains(vec3d3)) {
            Vec3d vec3d7 = vec3d3.add((double)(-direction2.getOffsetX()) * 0.1, (double)(-direction2.getOffsetY()) * 0.1, (double)(-direction2.getOffsetZ()) * 0.1);
            BlockHitResult blockHitResult8 = voxelShape0.raycast(vec3d3, vec3d7, blockPos1);
            if (blockHitResult8 != null && blockHitResult8.isInsideBlock() && blockHitResult8.getSide() == direction2 && vec3d3.squaredDistanceTo(blockHitResult8.getPos()) <= doubleValue4 * doubleValue4) {
               return new dev.leg1tka.api.utils.player.BlockInteractionTarget(blockPos1.toImmutable(), direction2, blockHitResult8.getPos(), true);
            }
         }
         boolean flag20 = switch (direction2) {
            case UP -> vec3d3.y >= box6.maxY - 1.0E-7;
            case DOWN -> vec3d3.y <= box6.minY + 1.0E-7;
            case EAST -> vec3d3.x >= box6.maxX - 1.0E-7;
            case WEST -> vec3d3.x <= box6.minX + 1.0E-7;
            case SOUTH -> vec3d3.z >= box6.maxZ - 1.0E-7;
            case NORTH -> vec3d3.z <= box6.minZ + 1.0E-7;
            default -> throw new MatchException(null, null);
         };
         if (!box6.contains(vec3d3) && !flag20) {
            return null;
         } else {
            for (Box box9 : voxelShape0.getBoundingBoxes()) {
               Box box10 = box9.offset(blockPos1);

               for (double[] doubleValueArray14 : c) {
                  Vec3d vec3d15 = a(box10, direction2, doubleValueArray14[0], doubleValueArray14[1]);
                  Vec3d vec3d16 = vec3d15.subtract(vec3d3);
                  double doubleValue17 = vec3d16.lengthSquared();
                  if (!(doubleValue17 < 1.0E-10) && !(doubleValue17 > doubleValue4 * doubleValue4)) {
                     BlockHitResult blockHitResult19 = voxelShape0.raycast(vec3d3, vec3d15.add(vec3d16.normalize().multiply(0.001)), blockPos1);
                     if (blockHitResult19 != null
                        && blockHitResult19.getSide() == direction2
                        && vec3d3.squaredDistanceTo(blockHitResult19.getPos()) <= doubleValue4 * doubleValue4
                        && (blockHitResult19.isInsideBlock() || blockHitResult19.getPos().squaredDistanceTo(vec3d15) <= 1.0E-6)
                        && (!dev.leg1tka.api.utils.player.BlockPlacementGeometry.a() || blockHitResult19.isInsideBlock() || dev.leg1tka.api.utils.player.BlockPlacementGeometry.a(vec3d3, blockHitResult19.getPos(), blockPos1, false))) {
                        return new dev.leg1tka.api.utils.player.BlockInteractionTarget(blockPos1.toImmutable(), direction2, blockHitResult19.getPos(), blockHitResult19.isInsideBlock());
                     }
                  }
               }
            }

            return null;
         }
      }
   }

   private static Vec3d a(Box box0, Direction direction1, double doubleValue2, double doubleValue4) {
      return switch (direction1) {
         case UP -> new Vec3d(MathHelper.lerp(doubleValue2, box0.minX, box0.maxX), box0.maxY, MathHelper.lerp(doubleValue4, box0.minZ, box0.maxZ));
         case DOWN -> new Vec3d(MathHelper.lerp(doubleValue2, box0.minX, box0.maxX), box0.minY, MathHelper.lerp(doubleValue4, box0.minZ, box0.maxZ));
         case EAST -> new Vec3d(box0.maxX, MathHelper.lerp(doubleValue4, box0.minY, box0.maxY), MathHelper.lerp(doubleValue2, box0.minZ, box0.maxZ));
         case WEST -> new Vec3d(box0.minX, MathHelper.lerp(doubleValue4, box0.minY, box0.maxY), MathHelper.lerp(doubleValue2, box0.minZ, box0.maxZ));
         case SOUTH -> new Vec3d(MathHelper.lerp(doubleValue2, box0.minX, box0.maxX), MathHelper.lerp(doubleValue4, box0.minY, box0.maxY), box0.maxZ);
         case NORTH -> new Vec3d(MathHelper.lerp(doubleValue2, box0.minX, box0.maxX), MathHelper.lerp(doubleValue4, box0.minY, box0.maxY), box0.minZ);
         default -> throw new MatchException(null, null);
      };
   }

   private static boolean d(BlockPos blockPos0) {
      if (minecraftClient.player.shouldCancelInteraction()) {
         return false;
      } else {
         BlockState blockState1 = minecraftClient.world.getBlockState(blockPos0);
         Block block2 = blockState1.getBlock();
         return blockState1.createScreenHandlerFactory(minecraftClient.world, blockPos0) != null
            || block2 instanceof DoorBlock
            || block2 instanceof TrapdoorBlock
            || block2 instanceof FenceGateBlock
            || block2 instanceof ButtonBlock
            || block2 instanceof BedBlock
            || block2 instanceof LeverBlock
            || block2 instanceof CakeBlock
            || block2 instanceof NoteBlock
            || block2 instanceof RespawnAnchorBlock
            || blockState1.isOf(Blocks.ENDER_CHEST)
            || blockState1.isOf(Blocks.JUKEBOX);
      }
   }
}
