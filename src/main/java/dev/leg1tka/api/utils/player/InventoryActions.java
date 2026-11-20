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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public final class InventoryActions implements dev.leg1tka.api.ClientAccess {
   private static int d;
   private static float e;
   private static float f;
   private static float g;
   private static float h;
   private static final Comparator<dev.leg1tka.api.utils.player.InventoryActions.PlacementOption> i = Comparator.comparing(dev.leg1tka.api.utils.player.InventoryActions.PlacementOption::cost).thenComparingLong(placementOption0 -> placementOption0.base().asLong());
   public static String c = "";

   private InventoryActions() {
   }

   public static boolean a(BlockPos blockPos0, Vec3d vec3d1, float floatValue2) {
      return minecraftClient.world != null && minecraftClient.world.getBlockState(blockPos0).isReplaceable() && !dev.leg1tka.api.utils.player.BlockInteractions.c(blockPos0) && dev.leg1tka.api.utils.player.BlockInteractions.a(blockPos0, vec3d1, (double)floatValue2, false) != null;
   }

   @Nullable
   public static dev.leg1tka.api.utils.player.InventoryActions.d a(List<PlayerEntity> list0, Vec3d vec3d1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, List<PlayerEntity> list6, boolean flag7) {
      if (minecraftClient.world != null && minecraftClient.player != null && !list0.isEmpty()) {
         dev.leg1tka.api.utils.player.InventoryActions.d value8 = null;
         float floatValue9 = -Float.MAX_VALUE;

         for (Entity entity11 : minecraftClient.world.getEntities()) {
            if (entity11 instanceof EndCrystalEntity) {
               EndCrystalEntity endCrystalEntity12 = (EndCrystalEntity)entity11;
               if (a(endCrystalEntity12, vec3d1, (double)floatValue2) != null) {
                  float floatValue13 = a(endCrystalEntity12.getPos(), list0);
                  boolean flag14 = flag7 && b(endCrystalEntity12.getPos(), list0);
                  if (!(floatValue13 < floatValue4) || flag14) {
                     float floatValue15 = dev.leg1tka.api.utils.player.ItemAttributes.a(endCrystalEntity12.getPos(), minecraftClient.player);
                     if (!(floatValue15 >= floatValue5) && !(floatValue15 > floatValue3)) {
                        float floatValue16 = a(endCrystalEntity12.getPos(), list6, null, List.of(), floatValue3);
                        if (!(floatValue16 < 0.0F)) {
                           float floatValue17 = floatValue13 - floatValue15 * 2.0F - floatValue16 * 2.0F + (flag14 ? 10.0F : 0.0F);
                           if (floatValue17 > floatValue9) {
                              floatValue9 = floatValue17;
                              value8 = new dev.leg1tka.api.utils.player.InventoryActions.d(endCrystalEntity12, floatValue13, floatValue15);
                           }
                        }
                     }
                  }
               }
            }
         }

         return value8;
      } else {
         return null;
      }
   }

   public static boolean a(EndCrystalEntity endCrystalEntity0, List<PlayerEntity> list1, float floatValue2, float floatValue3, float floatValue4, List<PlayerEntity> list5, boolean flag6) {
      if (endCrystalEntity0 != null && endCrystalEntity0.isAlive() && minecraftClient.player != null && minecraftClient.world != null) {
         if (!(a(endCrystalEntity0.getPos(), list1) < floatValue3) || flag6 && b(endCrystalEntity0.getPos(), list1)) {
            float floatValue7 = dev.leg1tka.api.utils.player.ItemAttributes.a(endCrystalEntity0.getPos(), minecraftClient.player);
            return floatValue7 < floatValue4 && floatValue7 <= floatValue2 && a(endCrystalEntity0.getPos(), list5, null, List.of(), floatValue2) >= 0.0F;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Nullable
   public static dev.leg1tka.api.utils.player.InventoryActions.c a(
      List<dev.leg1tka.api.utils.player.InventoryActions.e> list0,
      Vec3d vec3d1,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      boolean flag6,
      boolean flag7,
      float floatValue8,
      List<PlayerEntity> list9,
      @Nullable BlockPos blockPos10,
      float floatValue11,
      @Nullable BlockPos blockPos12,
      Collection<BlockPos> collection13,
      boolean flag14
   ) {
      if (minecraftClient.world != null && minecraftClient.player != null && !list0.isEmpty()) {
         Collection<BlockPos> collection15 = collection13 == null ? List.of() : collection13;
         Map<BlockPos, dev.leg1tka.api.utils.player.InventoryActions.PlacementOption> map16 = new HashMap<>();
         dev.leg1tka.api.utils.player.ItemAttributes.a();

         dev.leg1tka.api.utils.player.InventoryActions.c value40;
         try {
            for (dev.leg1tka.api.utils.player.InventoryActions.e value18 : list0) {
               BlockPos blockPos19 = BlockPos.ofFloored(value18.center());

               for (int intValue20 = -3; intValue20 <= 3; intValue20++) {
                  for (int intValue21 = -3; intValue21 <= 3; intValue21++) {
                     for (int intValue22 = -4; intValue22 <= 1; intValue22++) {
                        BlockPos blockPos23 = blockPos19.add(intValue20, intValue22, intValue21);
                        if (!map16.containsKey(blockPos23) && !collection15.contains(blockPos23)) {
                           dev.leg1tka.api.utils.player.InventoryActions.PlacementOption placementOption24 = a(blockPos23, blockPos19, vec3d1, floatValue2, floatValue3, flag6, flag7, blockPos10, floatValue11, blockPos12, collection15);
                           if (placementOption24 != null) {
                              map16.put(blockPos23, placementOption24);
                           }
                        }
                     }
                  }
               }
            }

            List<dev.leg1tka.api.utils.player.InventoryActions.PlacementOption> list35 = new ArrayList<>(map16.values());
            list35.sort(i);
            dev.leg1tka.api.utils.player.InventoryActions.c value36 = null;
            dev.leg1tka.api.utils.player.InventoryActions.b value37 = null;
            int intValue38 = 0;

            for (dev.leg1tka.api.utils.player.InventoryActions.PlacementOption placementOption41 : list35) {
               if (intValue38++ >= 150 || value37 != null && value37.blocks() == 0 && !placementOption41.digs().isEmpty()) {
                  break;
               }

               Vec3d vec3d42 = c(placementOption41.base());
               Box box43 = placementOption41.obsidian() ? new Box(placementOption41.base()).contract(1.0E-4) : null;
               float floatValue25 = 0.0F;
               boolean flag26 = false;

               for (dev.leg1tka.api.utils.player.InventoryActions.e value28 : list0) {
                  float floatValue29 = dev.leg1tka.api.utils.player.ItemAttributes.a(vec3d42, value28.player(), value28.box(), box43, placementOption41.digs());
                  if (value28.box() != value28.player().getBoundingBox()) {
                     floatValue29 = Math.min(floatValue29, dev.leg1tka.api.utils.player.ItemAttributes.a(vec3d42, value28.player(), value28.player().getBoundingBox(), box43, placementOption41.digs()));
                  }

                  floatValue25 = Math.max(floatValue25, floatValue29);
                  if (floatValue29 >= value28.player().getHealth() + value28.player().getAbsorptionAmount()) {
                     flag26 = true;
                  }
               }

               boolean flag44 = flag14 && flag26;
               if (!(floatValue25 < floatValue5) || flag44) {
                  float floatValue45 = dev.leg1tka.api.utils.player.ItemAttributes.a(vec3d42, minecraftClient.player, minecraftClient.player.getBoundingBox(), box43, placementOption41.digs());
                  if (!(floatValue45 >= floatValue8) && !(floatValue45 > floatValue4)) {
                     float floatValue46 = a(vec3d42, list9, box43, placementOption41.digs(), floatValue4);
                     if (!(floatValue46 < 0.0F)) {
                        float floatValue30 = floatValue25 - floatValue45 * 2.0F - floatValue46 * 2.0F - (placementOption41.obsidian() ? 3.0F : 0.0F) + (flag44 ? 10.0F : 0.0F);
                        dev.leg1tka.api.utils.player.InventoryActions.b value31 = new dev.leg1tka.api.utils.player.InventoryActions.b(placementOption41.digs().size(), placementOption41.cost().ticks(), placementOption41.cost().current(), placementOption41.cost().preferred(), floatValue30);
                        if ((value37 == null || value31.a(value37) < 0) && (!dev.leg1tka.api.utils.player.BlockPlacementGeometry.a() || !placementOption41.digs().isEmpty() || dev.leg1tka.api.utils.player.BlockPlacementGeometry.a(vec3d42, a(vec3d42), vec3d1, (double)floatValue3) != null)) {
                           value37 = value31;
                           value36 = new dev.leg1tka.api.utils.player.InventoryActions.c(placementOption41.base(), vec3d42, placementOption41.obsidian(), placementOption41.digs().isEmpty() ? null : placementOption41.digs().getFirst(), floatValue25, floatValue45);
                        }
                     }
                  }
               }
            }

            c = list35.size() + " " + Math.min(intValue38, 150) + " " + (value36 == null ? "none" : value36.pos().toShortString() + " " + value36.digPos() + " " + value37.ticks());
            value40 = value36;
         } finally {
            dev.leg1tka.api.utils.player.ItemAttributes.b();
         }

         return value40;
      } else {
         return null;
      }
   }

   @Nullable
   private static dev.leg1tka.api.utils.player.InventoryActions.PlacementOption a(
      BlockPos blockPos0,
      BlockPos blockPos1,
      Vec3d vec3d2,
      float floatValue3,
      float floatValue4,
      boolean flag5,
      boolean flag6,
      @Nullable BlockPos blockPos7,
      float floatValue8,
      @Nullable BlockPos blockPos9,
      Collection<BlockPos> collection10
   ) {
      BlockPos blockPos11 = blockPos0.up();
      if (a(vec3d2, blockPos0) > (double)(floatValue3 * floatValue3)) {
         return null;
      } else {
         Vec3d vec3d12 = c(blockPos0);
         Box box13 = new Box(vec3d12.x - 1.0, vec3d12.y, vec3d12.z - 1.0, vec3d12.x + 1.0, vec3d12.y + 2.0, vec3d12.z + 1.0);
         if (a(vec3d2, box13) > (double)(floatValue4 * floatValue4)) {
            return null;
         } else {
            BlockState blockState14 = minecraftClient.world.getBlockState(blockPos0);
            BlockState blockState15 = minecraftClient.world.getBlockState(blockPos11);
            ArrayList<BlockPos> arrayList16 = new ArrayList<>(2);
            if (!blockState15.isAir()) {
               if (!flag6 || !a(blockState15, blockPos11)) {
                  return null;
               }

               arrayList16.add(blockPos11);
            }

            boolean flag17 = !dev.leg1tka.api.utils.player.BlockInteractions.a(blockPos0);
            if (flag17) {
               if (!flag5) {
                  return null;
               }

               if (!blockState14.isReplaceable()) {
                  if (!flag6 || !a(blockState14, blockPos0)) {
                     return null;
                  }

                  arrayList16.add(blockPos0);
               }
            }

            if (!b(blockPos11) && (!flag17 || a(blockPos0, arrayList16))) {
               float floatValue18 = 0.0F;

               for (BlockPos blockPos20 : arrayList16) {
                  if (collection10.contains(blockPos20) || blockPos20.getY() > blockPos1.getY() + 1 || blockPos20.getY() < blockPos1.getY() - 4 || a(vec3d2, blockPos20) > (double)(floatValue3 * floatValue3)) {
                     return null;
                  }

                  float floatValue21 = dev.leg1tka.api.utils.player.ItemAttributes.a(minecraftClient.world.getBlockState(blockPos20), blockPos20);
                  if (blockPos20.equals(blockPos7)) {
                     floatValue21 *= 1.0F - MathHelper.clamp(floatValue8, 0.0F, 1.0F);
                  }

                  floatValue18 += floatValue21;
               }

               if (floatValue18 > 480.0F) {
                  return null;
               } else {
                  if (arrayList16.isEmpty()) {
                     if (flag17 ? !a(blockPos0, vec3d2, floatValue3) : dev.leg1tka.api.utils.player.BlockInteractions.a(blockPos0, vec3d2, (double)floatValue3) == null) {
                        return null;
                     }
                  } else if (dev.leg1tka.api.utils.player.BlockInteractions.b((BlockPos)arrayList16.getFirst(), vec3d2, (double)floatValue3) == null) {
                     if (arrayList16.size() != 2 || dev.leg1tka.api.utils.player.BlockInteractions.b((BlockPos)arrayList16.getLast(), vec3d2, (double)floatValue3) == null) {
                        return null;
                     }

                     arrayList16.addFirst((BlockPos)arrayList16.removeLast());
                  }

                  dev.leg1tka.api.utils.player.InventoryActions.b value22 = new dev.leg1tka.api.utils.player.InventoryActions.b(arrayList16.size(), floatValue18, arrayList16.contains(blockPos7), blockPos0.equals(blockPos9), -((float)vec3d2.squaredDistanceTo(vec3d12)));
                  return new dev.leg1tka.api.utils.player.InventoryActions.PlacementOption(blockPos0.toImmutable(), flag17, List.copyOf(arrayList16), value22);
               }
            } else {
               return null;
            }
         }
      }
   }

   private static float a(Vec3d vec3d0, List<PlayerEntity> list1) {
      float floatValue2 = 0.0F;

      for (PlayerEntity playerEntity4 : list1) {
         if (playerEntity4.isAlive()) {
            floatValue2 = Math.max(floatValue2, dev.leg1tka.api.utils.player.ItemAttributes.a(vec3d0, playerEntity4));
         }
      }

      return floatValue2;
   }

   private static boolean b(Vec3d vec3d0, List<PlayerEntity> list1) {
      for (PlayerEntity playerEntity3 : list1) {
         if (playerEntity3.isAlive() && dev.leg1tka.api.utils.player.ItemAttributes.a(vec3d0, playerEntity3) >= playerEntity3.getHealth() + playerEntity3.getAbsorptionAmount()) {
            return true;
         }
      }

      return false;
   }

   private static float a(Vec3d vec3d0, List<PlayerEntity> list1, @Nullable Box box2, List<BlockPos> list3, float floatValue4) {
      float floatValue5 = 0.0F;
      if (list1 == null) {
         return floatValue5;
      } else {
         for (PlayerEntity playerEntity7 : list1) {
            if (playerEntity7 != minecraftClient.player && playerEntity7.isAlive()) {
               float floatValue8 = dev.leg1tka.api.utils.player.ItemAttributes.a(vec3d0, playerEntity7, playerEntity7.getBoundingBox(), box2, list3);
               if (floatValue8 > floatValue4 || floatValue8 > 0.0F && floatValue8 >= playerEntity7.getHealth() + playerEntity7.getAbsorptionAmount() - 1.0F) {
                  return -1.0F;
               }

               floatValue5 = Math.max(floatValue5, floatValue8);
            }
         }

         return floatValue5;
      }
   }

   private static boolean a(BlockPos blockPos0, List<BlockPos> list1) {
      for (Direction direction5 : Direction.values()) {
         BlockPos blockPos6 = blockPos0.offset(direction5);
         BlockState blockState7 = minecraftClient.world.getBlockState(blockPos6);
         if (!list1.contains(blockPos6) && !blockState7.isReplaceable() && !blockState7.isOf(Blocks.COBWEB) && !blockState7.getOutlineShape(minecraftClient.world, blockPos6).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   public static boolean a(BlockState blockState0) {
      return blockState0 != null && minecraftClient.world != null && !blockState0.isAir() && !(blockState0.getBlock() instanceof FluidBlock) && blockState0.getHardness(minecraftClient.world, BlockPos.ORIGIN) >= 0.0F;
   }

   public static boolean b(BlockState blockState0) {
      return a(blockState0, BlockPos.ORIGIN);
   }

   private static boolean a(BlockState blockState0, BlockPos blockPos1) {
      if (a(blockState0) && minecraftClient.player != null && !blockState0.isOf(Blocks.COBWEB) && !blockState0.isOf(Blocks.SWEET_BERRY_BUSH)) {
         float floatValue2 = dev.leg1tka.api.utils.player.ItemAttributes.a(blockState0, blockPos1);
         boolean flag3 = blockState0.isOf(Blocks.OBSIDIAN) || blockState0.isOf(Blocks.CRYING_OBSIDIAN);
         boolean flag4 = !minecraftClient.player.isOnGround();
         boolean flag5 = flag3 && flag4 && dev.leg1tka.api.utils.player.WorldBlockQueries.a(minecraftClient.player);
         return floatValue2 <= 240.0F && (!flag3 || !flag4 || !flag5 || floatValue2 <= 30.0F);
      } else {
         return false;
      }
   }

   public static double a(Vec3d vec3d0, Box box1) {
      double doubleValue2 = MathHelper.clamp(vec3d0.x, box1.minX, box1.maxX) - vec3d0.x;
      double doubleValue4 = MathHelper.clamp(vec3d0.y, box1.minY, box1.maxY) - vec3d0.y;
      double doubleValue6 = MathHelper.clamp(vec3d0.z, box1.minZ, box1.maxZ) - vec3d0.z;
      return doubleValue2 * doubleValue2 + doubleValue4 * doubleValue4 + doubleValue6 * doubleValue6;
   }

   public static double a(Vec3d vec3d0, BlockPos blockPos1) {
      double doubleValue2 = MathHelper.clamp(vec3d0.x, (double)blockPos1.getX(), (double)blockPos1.getX() + 1.0) - vec3d0.x;
      double doubleValue4 = MathHelper.clamp(vec3d0.y, (double)blockPos1.getY(), (double)blockPos1.getY() + 1.0) - vec3d0.y;
      double doubleValue6 = MathHelper.clamp(vec3d0.z, (double)blockPos1.getZ(), (double)blockPos1.getZ() + 1.0) - vec3d0.z;
      return doubleValue2 * doubleValue2 + doubleValue4 * doubleValue4 + doubleValue6 * doubleValue6;
   }

   private static boolean b(BlockPos blockPos0) {
      Box box1 = new Box(
         (double)blockPos0.getX(), (double)blockPos0.getY(), (double)blockPos0.getZ(), (double)(blockPos0.getX() + 1), (double)(blockPos0.getY() + 2), (double)(blockPos0.getZ() + 1)
      );
      return !minecraftClient.world.getOtherEntities(null, box1, entity0x -> entity0x.isAlive() && !entity0x.isSpectator()).isEmpty();
   }

   public static boolean a(BlockPos blockPos0) {
      return dev.leg1tka.api.utils.player.BlockInteractions.a(blockPos0) && minecraftClient.world.getBlockState(blockPos0.up()).isAir() && !b(blockPos0.up());
   }

   @Nullable
   public static Vec3d a(EndCrystalEntity endCrystalEntity0, Vec3d vec3d1, double doubleValue2) {
      if (endCrystalEntity0 != null && endCrystalEntity0.isAlive()) {
         Box box4 = endCrystalEntity0.getBoundingBox();
         if (dev.leg1tka.api.utils.player.BlockPlacementGeometry.a()) {
            return dev.leg1tka.api.utils.player.BlockPlacementGeometry.a(endCrystalEntity0.getPos(), box4, vec3d1, doubleValue2);
         } else {
            Vec3d vec3d5 = new Vec3d(
               MathHelper.clamp(vec3d1.x, box4.minX + 0.02, box4.maxX - 0.02),
               MathHelper.clamp(vec3d1.y, box4.minY + 0.02, box4.maxY - 0.02),
               MathHelper.clamp(vec3d1.z, box4.minZ + 0.02, box4.maxZ - 0.02)
            );
            return a(vec3d1, box4) <= doubleValue2 * doubleValue2 ? vec3d5 : null;
         }
      } else {
         return null;
      }
   }

   private static Box a(Vec3d vec3d0) {
      return new Box(vec3d0.x - 1.0, vec3d0.y, vec3d0.z - 1.0, vec3d0.x + 1.0, vec3d0.y + 2.0, vec3d0.z + 1.0);
   }

   private static Vec3d c(BlockPos blockPos0) {
      return new Vec3d((double)blockPos0.getX() + 0.5, (double)(blockPos0.getY() + 1), (double)blockPos0.getZ() + 0.5);
   }

   static record PlacementOption(BlockPos base, boolean obsidian, List<BlockPos> digs, dev.leg1tka.api.utils.player.InventoryActions.b cost)  {
   }

   public static record b(int blocks, float ticks, boolean current, boolean preferred, float score) implements Comparable<dev.leg1tka.api.utils.player.InventoryActions.b>  {
      public int a(dev.leg1tka.api.utils.player.InventoryActions.b value1) {
         return this.compareTo(value1);
      }

      @Override
      public int compareTo(dev.leg1tka.api.utils.player.InventoryActions.b value1) {
         if (this.blocks == 0 != (value1.blocks == 0)) {
            return this.blocks == 0 ? -1 : 1;
         } else {
            if (this.blocks > 0) {
               if (Float.compare(this.ticks, value1.ticks) != 0) {
                  return Float.compare(this.ticks, value1.ticks);
               }

               if (this.blocks != value1.blocks) {
                  return Integer.compare(this.blocks, value1.blocks);
               }

               if (this.current != value1.current) {
                  return this.current ? -1 : 1;
               }
            }

            if (this.preferred != value1.preferred) {
               return this.preferred ? -1 : 1;
            } else {
               return Float.compare(value1.score, this.score);
            }
         }
      }
   }

   public static record c(BlockPos pos, Vec3d crystalPos, boolean needsObsidian, @Nullable BlockPos digPos, float enemyDamage, float selfDamage)  {





}

   public static record d(EndCrystalEntity crystal, float enemyDamage, float selfDamage)  {


}

   public static record e(PlayerEntity player, Box box, Vec3d center)  {



      public e(PlayerEntity playerEntity1, Box box2) {
         this(playerEntity1, box2, new Vec3d((box2.minX + box2.maxX) * 0.5, box2.minY, (box2.minZ + box2.maxZ) * 0.5));
      }
   }
}
