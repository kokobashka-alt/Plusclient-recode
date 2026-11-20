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

import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public final class BotBlockActions {
   private static double a;
   private static final double[][] b = new double[][]{{0.5, 0.5}, {0.25, 0.25}, {0.75, 0.25}, {0.25, 0.75}, {0.75, 0.75}};

   private BotBlockActions() {
   }

   public static double a(ClientPlayerEntity clientPlayerEntity0) {
      return clientPlayerEntity0.getBlockInteractionRange() - 0.3;
   }

   public static Box a(ClientWorld clientWorld0, BlockPos blockPos1) {
      BlockState blockState2 = clientWorld0.getBlockState(blockPos1);
      VoxelShape voxelShape3 = blockState2.getOutlineShape(clientWorld0, blockPos1);
      Box box4 = voxelShape3.isEmpty() ? new Box(0.0, 0.0, 0.0, 1.0, 1.0, 1.0) : voxelShape3.getBoundingBox();
      return box4.offset(blockPos1);
   }

   public static BlockHitResult a(ClientWorld clientWorld0, ClientPlayerEntity clientPlayerEntity1, Vec3d vec3d2, BlockPos blockPos3, double doubleValue4) {
      Box box6 = a(clientWorld0, blockPos3);
      Vec3d vec3d7 = box6.getCenter();
      if (vec3d2.squaredDistanceTo(vec3d7) > (doubleValue4 + 1.0) * (doubleValue4 + 1.0)) {
         return null;
      } else {
         BlockHitResult blockHitResult8 = a(clientWorld0, clientPlayerEntity1, vec3d2, blockPos3, vec3d7, doubleValue4);
         if (blockHitResult8 != null) {
            return blockHitResult8;
         } else {
            for (int intValue9 = 0; intValue9 < 3; intValue9++) {
               for (int intValue10 = 0; intValue10 < 2; intValue10++) {
                  for (double[] doubleValueArray14 : b) {
                     Vec3d vec3d15 = a(box6, intValue9, intValue10, doubleValueArray14[0], doubleValueArray14[1]);
                     blockHitResult8 = a(clientWorld0, clientPlayerEntity1, vec3d2, blockPos3, vec3d15, doubleValue4);
                     if (blockHitResult8 != null) {
                        return blockHitResult8;
                     }
                  }
               }
            }

            return null;
         }
      }
   }

   public static BlockHitResult a(ClientWorld clientWorld0, ClientPlayerEntity clientPlayerEntity1, Vec3d vec3d2, float floatValue3, float floatValue4, BlockPos blockPos5, double doubleValue6) {
      Vec3d vec3d8 = vec3d2.add(Vec3d.fromPolar(floatValue4, floatValue3).multiply(doubleValue6));
      BlockHitResult blockHitResult9 = clientWorld0.raycast(new RaycastContext(vec3d2, vec3d8, ShapeType.OUTLINE, FluidHandling.NONE, clientPlayerEntity1));
      return blockHitResult9.getType() == Type.BLOCK && blockHitResult9.getBlockPos().equals(blockPos5) ? blockHitResult9 : null;
   }

   public static float a(Vec3d vec3d0, Vec3d vec3d1) {
      return (float)(MathHelper.atan2(vec3d1.z - vec3d0.z, vec3d1.x - vec3d0.x) * 180.0F / (float)Math.PI) - 90.0F;
   }

   public static float b(Vec3d vec3d0, Vec3d vec3d1) {
      double doubleValue2 = vec3d1.x - vec3d0.x;
      double doubleValue4 = vec3d1.z - vec3d0.z;
      return (float)(-(MathHelper.atan2(vec3d1.y - vec3d0.y, Math.sqrt(doubleValue2 * doubleValue2 + doubleValue4 * doubleValue4)) * 180.0F / (float)Math.PI));
   }

   private static Vec3d a(Box box0, int intValue1, int intValue2, double doubleValue3, double doubleValue5) {
      double doubleValue7 = MathHelper.lerp(intValue1 == 0 ? (intValue2 == 0 ? 0.0 : 1.0) : doubleValue3, box0.minX, box0.maxX);
      double doubleValue9 = MathHelper.lerp(intValue1 == 1 ? (intValue2 == 0 ? 0.0 : 1.0) : (intValue1 == 0 ? doubleValue3 : doubleValue5), box0.minY, box0.maxY);
      double doubleValue11 = MathHelper.lerp(intValue1 == 2 ? (intValue2 == 0 ? 0.0 : 1.0) : doubleValue5, box0.minZ, box0.maxZ);
      Vec3d vec3d13 = box0.getCenter();
      return new Vec3d(doubleValue7 + Math.signum(vec3d13.x - doubleValue7) * 0.03, doubleValue9 + Math.signum(vec3d13.y - doubleValue9) * 0.03, doubleValue11 + Math.signum(vec3d13.z - doubleValue11) * 0.03);
   }

   private static BlockHitResult a(ClientWorld clientWorld0, ClientPlayerEntity clientPlayerEntity1, Vec3d vec3d2, BlockPos blockPos3, Vec3d vec3d4, double doubleValue5) {
      BlockHitResult blockHitResult7 = clientWorld0.raycast(new RaycastContext(vec3d2, vec3d4, ShapeType.OUTLINE, FluidHandling.NONE, clientPlayerEntity1));
      if (blockHitResult7.getType() == Type.BLOCK && blockHitResult7.getBlockPos().equals(blockPos3)) {
         return blockHitResult7.getPos().squaredDistanceTo(vec3d2) <= doubleValue5 * doubleValue5 ? blockHitResult7 : null;
      } else {
         return null;
      }
   }
}
