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

import java.util.Optional;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.jetbrains.annotations.Nullable;

public final class BlockPlacementGeometry implements dev.leg1tka.api.ClientAccess {
   private static final double[] c = new double[]{0.2, 0.5, 0.8};
   private static final double[][] d = new double[][]{
      {0.5, 0.5}, {0.25, 0.25}, {0.75, 0.25}, {0.25, 0.75}, {0.75, 0.75}, {0.5, 0.15}, {0.5, 0.85}, {0.15, 0.5}, {0.85, 0.5}
   };
   private static boolean e;

   private BlockPlacementGeometry() {
   }

   public static boolean a(boolean flag0) {
      boolean flag1 = e;
      e = flag0;
      return flag1;
   }

   public static void b(boolean flag0) {
      e = flag0;
   }

   public static boolean a() {
      return e;
   }

   public static boolean a(Vec3d vec3d0, Vec3d vec3d1) {
      return minecraftClient.world != null && minecraftClient.player != null
         ? minecraftClient.world.raycast(new RaycastContext(vec3d0, vec3d1, ShapeType.OUTLINE, FluidHandling.NONE, minecraftClient.player)).getType() == Type.MISS
         : false;
   }

   public static boolean a(Vec3d vec3d0, Vec3d vec3d1, BlockPos blockPos2, boolean flag3) {
      if (minecraftClient.world != null && minecraftClient.player != null) {
         Vec3d vec3d4 = vec3d1.subtract(vec3d0);
         if (vec3d4.lengthSquared() < 1.0E-10) {
            return true;
         } else {
            Vec3d vec3d5 = vec3d1.add(vec3d4.normalize().multiply(0.02));
            BlockHitResult blockHitResult6 = minecraftClient.world.raycast(new RaycastContext(vec3d0, vec3d5, ShapeType.OUTLINE, FluidHandling.NONE, minecraftClient.player));
            return blockHitResult6.getType() == Type.MISS ? flag3 : blockHitResult6.getBlockPos().equals(blockPos2);
         }
      } else {
         return false;
      }
   }

   @Nullable
   public static Vec3d a(Box box0, Vec3d vec3d1, double doubleValue2) {
      double doubleValue4 = doubleValue2 * doubleValue2;
      Vec3d vec3d6 = new Vec3d(
         MathHelper.clamp(vec3d1.x, box0.minX + 0.02, box0.maxX - 0.02),
         MathHelper.clamp(vec3d1.y, box0.minY + 0.02, box0.maxY - 0.02),
         MathHelper.clamp(vec3d1.z, box0.minZ + 0.02, box0.maxZ - 0.02)
      );
      if (dev.leg1tka.api.utils.player.InventoryActions.a(vec3d1, box0) > doubleValue4) {
         return null;
      } else if (vec3d1.squaredDistanceTo(vec3d6) <= doubleValue4 && a(vec3d1, vec3d6)) {
         return vec3d6;
      } else {
         Vec3d vec3d7 = minecraftClient.player == null ? Vec3d.ZERO : minecraftClient.player.getRotationVec(1.0F);
         Vec3d vec3d8 = null;
         double doubleValue9 = -2.0;

         for (double doubleValue14 : c) {
            for (double doubleValue19 : c) {
               for (double doubleValue24 : c) {
                  Vec3d vec3d26 = new Vec3d(
                     MathHelper.lerp(doubleValue14, box0.minX, box0.maxX), MathHelper.lerp(doubleValue19, box0.minY, box0.maxY), MathHelper.lerp(doubleValue24, box0.minZ, box0.maxZ)
                  );
                  if (!(vec3d1.squaredDistanceTo(vec3d26) > doubleValue4) && a(vec3d1, vec3d26)) {
                     double doubleValue27 = vec3d26.subtract(vec3d1).normalize().dotProduct(vec3d7);
                     if (doubleValue27 > doubleValue9) {
                        doubleValue9 = doubleValue27;
                        vec3d8 = vec3d26;
                     }
                  }
               }
            }
         }

         return vec3d8;
      }
   }

   @Nullable
   public static Vec3d a(Vec3d vec3d0, Box box1, Vec3d vec3d2, double doubleValue3) {
      if (dev.leg1tka.api.utils.player.InventoryActions.a(vec3d2, box1) > doubleValue3 * doubleValue3) {
         return null;
      } else {
         Vec3d vec3d5 = new Vec3d(vec3d0.x, vec3d0.y + 0.05, vec3d0.z);
         return vec3d2.y > vec3d5.y && vec3d2.squaredDistanceTo(vec3d5) <= doubleValue3 * doubleValue3 && a(vec3d2, vec3d5) ? vec3d5 : a(box1, vec3d2, doubleValue3);
      }
   }

   public static boolean a(Vec3d vec3d0, float floatValue1, float floatValue2, Box box3, double doubleValue4) {
      if (box3.contains(vec3d0)) {
         return true;
      } else {
         Vec3d vec3d6 = vec3d0.add(Vec3d.fromPolar(floatValue2, floatValue1).multiply(doubleValue4 + 1.0));
         Optional optional7 = box3.raycast(vec3d0, vec3d6);
         return optional7.isPresent() && vec3d0.squaredDistanceTo((Vec3d)optional7.get()) <= (doubleValue4 + 0.5) * (doubleValue4 + 0.5) && a(vec3d0, (Vec3d)optional7.get());
      }
   }

   public static boolean a(Vec3d vec3d0, float floatValue1, float floatValue2, BlockPos blockPos3, double doubleValue4) {
      if (minecraftClient.world != null && minecraftClient.player != null) {
         Vec3d vec3d6 = vec3d0.add(Vec3d.fromPolar(floatValue2, floatValue1).multiply(doubleValue4));
         BlockHitResult blockHitResult7 = minecraftClient.world.raycast(new RaycastContext(vec3d0, vec3d6, ShapeType.OUTLINE, FluidHandling.NONE, minecraftClient.player));
         return blockHitResult7.getType() == Type.BLOCK && blockHitResult7.getBlockPos().equals(blockPos3);
      } else {
         return false;
      }
   }

   @Nullable
   public static Vec3d a(BlockPos blockPos0, Vec3d vec3d1, double doubleValue2, boolean flag4) {
      double doubleValue5 = doubleValue2 * doubleValue2;
      Vec3d vec3d7 = null;
      double doubleValue8 = Double.MAX_VALUE;

      for (Direction direction13 : Direction.values()) {
         boolean flag14 = switch (direction13) {
            case UP -> vec3d1.y >= (double)blockPos0.getY() + 1.0;
            case DOWN -> vec3d1.y <= (double)blockPos0.getY();
            case EAST -> vec3d1.x >= (double)blockPos0.getX() + 1.0;
            case WEST -> vec3d1.x <= (double)blockPos0.getX();
            case SOUTH -> vec3d1.z >= (double)blockPos0.getZ() + 1.0;
            case NORTH -> vec3d1.z <= (double)blockPos0.getZ();
            default -> throw new MatchException(null, null);
         };
         if (flag14) {
            for (double[] doubleValueArray18 : d) {
               Vec3d vec3d19 = a(blockPos0, direction13, doubleValueArray18[0], doubleValueArray18[1]);
               double doubleValue20 = vec3d1.squaredDistanceTo(vec3d19);
               if (!(doubleValue20 > doubleValue5) && !(doubleValue20 >= doubleValue8) && a(vec3d1, vec3d19, blockPos0, flag4)) {
                  vec3d7 = vec3d19;
                  doubleValue8 = doubleValue20;
                  break;
               }
            }
         }
      }

      return vec3d7;
   }

   private static Vec3d a(BlockPos blockPos0, Direction direction1, double doubleValue2, double doubleValue4) {
      double doubleValue6 = (double)blockPos0.getX();
      double doubleValue8 = (double)blockPos0.getY();
      double doubleValue10 = (double)blockPos0.getZ();

      return switch (direction1) {
         case UP -> new Vec3d(doubleValue6 + doubleValue2, doubleValue8 + 1.0, doubleValue10 + doubleValue4);
         case DOWN -> new Vec3d(doubleValue6 + doubleValue2, doubleValue8, doubleValue10 + doubleValue4);
         case EAST -> new Vec3d(doubleValue6 + 1.0, doubleValue8 + doubleValue4, doubleValue10 + doubleValue2);
         case WEST -> new Vec3d(doubleValue6, doubleValue8 + doubleValue4, doubleValue10 + doubleValue2);
         case SOUTH -> new Vec3d(doubleValue6 + doubleValue2, doubleValue8 + doubleValue4, doubleValue10 + 1.0);
         case NORTH -> new Vec3d(doubleValue6 + doubleValue2, doubleValue8 + doubleValue4, doubleValue10);
         default -> throw new MatchException(null, null);
      };
   }
}
