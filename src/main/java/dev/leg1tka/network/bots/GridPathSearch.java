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

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.util.shape.VoxelShape;

final class GridPathSearch {
   private static int a;
   private static int b;
   private static final int[][] c = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
   private static double d;
   private final ClientWorld e;
   private final ClientPlayerEntity f;
   private final Mutable g = new Mutable();

   GridPathSearch(ClientWorld clientWorld1, ClientPlayerEntity clientPlayerEntity2) {
      this.e = clientWorld1;
      this.f = clientPlayerEntity2;
   }

   List<Vec3d> a(BlockPos blockPos1, double doubleValue2) {
      BlockPos blockPos4 = this.f.getBlockPos();
      dev.leg1tka.network.bots.GridPathSearch.a value5 = null;

      for (int intValue6 = 0; intValue6 >= -1 && value5 == null; intValue6--) {
         double doubleValue7 = this.a(blockPos4.getX(), blockPos4.getY() + intValue6, blockPos4.getZ());
         if (!Double.isNaN(doubleValue7)) {
            value5 = new dev.leg1tka.network.bots.GridPathSearch.a(blockPos4.getX(), blockPos4.getY() + intValue6, blockPos4.getZ(), doubleValue7);
         }
      }

      if (value5 == null) {
         double doubleValue29 = this.a(blockPos4.getX(), blockPos4.getY() + 1, blockPos4.getZ());
         if (Double.isNaN(doubleValue29)) {
            return null;
         }

         value5 = new dev.leg1tka.network.bots.GridPathSearch.a(blockPos4.getX(), blockPos4.getY() + 1, blockPos4.getZ(), doubleValue29);
      }

      Vec3d vec3d30 = Vec3d.ofCenter(blockPos1);
      Long2ObjectOpenHashMap long2ObjectOpenHashMap31 = new Long2ObjectOpenHashMap();
      PriorityQueue priorityQueue8 = new PriorityQueue();
      value5.e = 0.0;
      value5.f = a(value5, vec3d30);
      long2ObjectOpenHashMap31.put(BlockPos.asLong(value5.a, value5.b, value5.c), value5);
      priorityQueue8.add(value5);
      int intValue9 = 0;

      while (!priorityQueue8.isEmpty() && intValue9++ < 20000) {
         dev.leg1tka.network.bots.GridPathSearch.a value10 = (dev.leg1tka.network.bots.GridPathSearch.a)priorityQueue8.poll();
         if (!value10.h) {
            value10.h = true;
            if (this.a(value10, blockPos1, vec3d30, doubleValue2)) {
               return this.a(this.a(value10));
            }

            for (int[] intValueArray14 : c) {
               boolean flag15 = intValueArray14[0] != 0 && intValueArray14[1] != 0;
               int intValue16 = value10.a + intValueArray14[0];
               int intValue17 = value10.c + intValueArray14[1];
               if (Math.abs(intValue16 - value5.a) <= 96 && Math.abs(intValue17 - value5.c) <= 96) {
                  for (int intValue18 = 1; intValue18 >= -3; intValue18--) {
                     int intValue19 = value10.b + intValue18;
                     double doubleValue20 = this.a(intValue16, intValue19, intValue17);
                     if (!Double.isNaN(doubleValue20)) {
                        double doubleValue22 = this.a(value10, intValue16, intValue19, intValue17, doubleValue20, flag15);
                        if (!Double.isNaN(doubleValue22)) {
                           long longValue24 = BlockPos.asLong(intValue16, intValue19, intValue17);
                           dev.leg1tka.network.bots.GridPathSearch.a value26 = (dev.leg1tka.network.bots.GridPathSearch.a)long2ObjectOpenHashMap31.get(longValue24);
                           if (value26 == null) {
                              value26 = new dev.leg1tka.network.bots.GridPathSearch.a(intValue16, intValue19, intValue17, doubleValue20);
                              long2ObjectOpenHashMap31.put(longValue24, value26);
                           }

                           if (!value26.h) {
                              double doubleValue27 = value10.e + doubleValue22;
                              if (doubleValue27 < value26.e) {
                                 value26.e = doubleValue27;
                                 value26.f = doubleValue27 + a(value26, vec3d30);
                                 value26.g = value10;
                                 priorityQueue8.add(value26);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   boolean a(Vec3d vec3d1, Vec3d vec3d2) {
      if (Math.abs(vec3d1.y - vec3d2.y) > 0.001) {
         return false;
      } else {
         double doubleValue3 = vec3d2.x - vec3d1.x;
         double doubleValue5 = vec3d2.z - vec3d1.z;
         double doubleValue7 = Math.sqrt(doubleValue3 * doubleValue3 + doubleValue5 * doubleValue5);
         if (doubleValue7 < 0.001) {
            return true;
         } else {
            double doubleValue9 = -doubleValue5 / doubleValue7 * 0.31;
            double doubleValue11 = doubleValue3 / doubleValue7 * 0.31;
            int intValue13 = MathHelper.floor(vec3d1.y + 0.5);
            int intValue14 = MathHelper.ceil(doubleValue7 / 0.25);

            for (int intValue15 = 0; intValue15 <= intValue14; intValue15++) {
               double doubleValue16 = (double)intValue15 / (double)intValue14;
               double doubleValue18 = vec3d1.x + doubleValue3 * doubleValue16;
               double doubleValue20 = vec3d1.z + doubleValue5 * doubleValue16;
               if (!this.a(doubleValue18 + doubleValue9, doubleValue20 + doubleValue11, intValue13, vec3d1.y) || !this.a(doubleValue18 - doubleValue9, doubleValue20 - doubleValue11, intValue13, vec3d1.y)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   double a(int intValue1, int intValue2, int intValue3) {
      if (!this.e.getChunkManager().isChunkLoaded(intValue1 >> 4, intValue3 >> 4)) {
         return Double.NaN;
      } else {
         BlockState blockState4 = this.b(intValue1, intValue2, intValue3);
         BlockState blockState5 = this.b(intValue1, intValue2 + 1, intValue3);
         if (!a(blockState4) && !a(blockState5) && blockState4.getFluidState().isEmpty() && blockState5.getFluidState().isEmpty()) {
            double doubleValue6 = this.a(blockState4, intValue1, intValue2, intValue3);
            double doubleValue8;
            if (Double.isNaN(doubleValue6)) {
               BlockState blockState10 = this.b(intValue1, intValue2 - 1, intValue3);
               if (a(blockState10)) {
                  return Double.NaN;
               }

               double doubleValue11 = this.a(blockState10, intValue1, intValue2 - 1, intValue3);
               if (Double.isNaN(doubleValue11) || doubleValue11 <= 0.5 || doubleValue11 > 1.0) {
                  return Double.NaN;
               }

               doubleValue8 = (double)(intValue2 - 1) + doubleValue11;
            } else {
               if (doubleValue6 > 0.5) {
                  return Double.NaN;
               }

               doubleValue8 = (double)intValue2 + doubleValue6;
            }

            if (!this.c(intValue1, intValue2 + 1, intValue3)) {
               return Double.NaN;
            } else {
               return doubleValue8 + 1.8 > (double)(intValue2 + 2) && !this.c(intValue1, intValue2 + 2, intValue3) ? Double.NaN : doubleValue8;
            }
         } else {
            return Double.NaN;
         }
      }
   }

   private boolean a(double doubleValue1, double doubleValue3, int intValue5, double doubleValue6) {
      int intValue8 = MathHelper.floor(doubleValue1);
      int intValue9 = MathHelper.floor(doubleValue3);
      double doubleValue10 = this.a(intValue8, intValue5, intValue9);
      if (!Double.isNaN(doubleValue10) && Math.abs(doubleValue10 - doubleValue6) < 0.001) {
         return true;
      } else {
         double doubleValue12 = this.a(intValue8, intValue5 - 1, intValue9);
         return !Double.isNaN(doubleValue12) && Math.abs(doubleValue12 - doubleValue6) < 0.001;
      }
   }

   private double a(dev.leg1tka.network.bots.GridPathSearch.a value1, int intValue2, int intValue3, int intValue4, double doubleValue5, boolean flag7) {
      double doubleValue8 = doubleValue5 - value1.d;
      if (Math.abs(doubleValue8) <= 0.6) {
         if (!flag7) {
            return 1.0 + Math.max(0.0, doubleValue8) * 0.3;
         } else if (Math.abs(doubleValue8) > 0.001) {
            return Double.NaN;
         } else {
            return this.a(intValue2, value1.b, value1.c, value1.d) && this.a(value1.a, value1.b, intValue4, value1.d) ? Math.sqrt(2.0) : Double.NaN;
         }
      } else if (flag7) {
         return Double.NaN;
      } else if (doubleValue8 > 0.6) {
         if (doubleValue8 > 1.25) {
            return Double.NaN;
         } else {
            int intValue12 = MathHelper.floor(value1.d + 1.8 + doubleValue8);

            for (int intValue13 = value1.b + 2; intValue13 <= intValue12; intValue13++) {
               if (!this.c(value1.a, intValue13, value1.c)) {
                  return Double.NaN;
               }
            }

            return 1.8;
         }
      } else if (doubleValue8 < -3.2) {
         return Double.NaN;
      } else {
         int intValue10 = MathHelper.floor(value1.d + 1.79);

         for (int intValue11 = intValue3 + 2; intValue11 <= intValue10; intValue11++) {
            if (!this.c(intValue2, intValue11, intValue4)) {
               return Double.NaN;
            }
         }

         return 1.2 - doubleValue8 * 0.3;
      }
   }

   private boolean a(int intValue1, int intValue2, int intValue3, double doubleValue4) {
      double doubleValue6 = this.a(intValue1, intValue2, intValue3);
      if (!Double.isNaN(doubleValue6) && Math.abs(doubleValue6 - doubleValue4) < 0.001) {
         return true;
      } else {
         double doubleValue8 = this.a(intValue1, intValue2 - 1, intValue3);
         return !Double.isNaN(doubleValue8) && Math.abs(doubleValue8 - doubleValue4) < 0.001;
      }
   }

   private boolean a(dev.leg1tka.network.bots.GridPathSearch.a value1, BlockPos blockPos2, Vec3d vec3d3, double doubleValue4) {
      double doubleValue6 = (double)value1.a + 0.5;
      double doubleValue8 = value1.d + 1.62;
      double doubleValue10 = (double)value1.c + 0.5;
      double doubleValue12 = vec3d3.x - doubleValue6;
      double doubleValue14 = vec3d3.y - doubleValue8;
      double doubleValue16 = vec3d3.z - doubleValue10;
      return doubleValue12 * doubleValue12 + doubleValue14 * doubleValue14 + doubleValue16 * doubleValue16 > doubleValue4 * doubleValue4 ? false : dev.leg1tka.network.bots.BotBlockActions.a(this.e, this.f, new Vec3d(doubleValue6, doubleValue8, doubleValue10), blockPos2, doubleValue4) != null;
   }

   private static double a(dev.leg1tka.network.bots.GridPathSearch.a value0, Vec3d vec3d1) {
      double doubleValue2 = vec3d1.x - ((double)value0.a + 0.5);
      double doubleValue4 = vec3d1.z - ((double)value0.c + 0.5);
      return Math.max(0.0, Math.sqrt(doubleValue2 * doubleValue2 + doubleValue4 * doubleValue4) - 3.0) + Math.abs(vec3d1.y - value0.d) * 0.5;
   }

   private List<Vec3d> a(dev.leg1tka.network.bots.GridPathSearch.a value1) {
      ArrayList arrayList2 = new ArrayList();

      for (dev.leg1tka.network.bots.GridPathSearch.a value3 = value1; value3 != null; value3 = value3.g) {
         arrayList2.add(new Vec3d((double)value3.a + 0.5, value3.d, (double)value3.c + 0.5));
      }

      Collections.reverse(arrayList2);
      return arrayList2;
   }

   private List<Vec3d> a(List<Vec3d> list1) {
      if (list1.size() <= 2) {
         return list1;
      } else {
         ArrayList arrayList2 = new ArrayList();
         int intValue3 = 0;
         arrayList2.add((Vec3d)list1.get(0));

         while (intValue3 < list1.size() - 1) {
            int intValue4 = intValue3 + 1;

            while (intValue4 + 1 < list1.size() && this.a((Vec3d)list1.get(intValue3), (Vec3d)list1.get(intValue4 + 1))) {
               intValue4++;
            }

            arrayList2.add((Vec3d)list1.get(intValue4));
            intValue3 = intValue4;
         }

         return arrayList2;
      }
   }

   private BlockState b(int intValue1, int intValue2, int intValue3) {
      return this.e.getBlockState(this.g.set(intValue1, intValue2, intValue3));
   }

   private double a(BlockState blockState1, int intValue2, int intValue3, int intValue4) {
      VoxelShape voxelShape5 = blockState1.getCollisionShape(this.e, this.g.set(intValue2, intValue3, intValue4));
      return voxelShape5.isEmpty() ? Double.NaN : voxelShape5.getMax(Axis.Y);
   }

   private boolean c(int intValue1, int intValue2, int intValue3) {
      BlockState blockState4 = this.b(intValue1, intValue2, intValue3);
      return !a(blockState4) && blockState4.getFluidState().isEmpty() && blockState4.getCollisionShape(this.e, this.g.set(intValue1, intValue2, intValue3)).isEmpty();
   }

   private static boolean a(BlockState blockState0) {
      return blockState0.isOf(Blocks.LAVA)
         || blockState0.isOf(Blocks.FIRE)
         || blockState0.isOf(Blocks.SOUL_FIRE)
         || blockState0.isOf(Blocks.MAGMA_BLOCK)
         || blockState0.isOf(Blocks.CACTUS)
         || blockState0.isOf(Blocks.SWEET_BERRY_BUSH)
         || blockState0.isOf(Blocks.COBWEB)
         || blockState0.isOf(Blocks.POWDER_SNOW)
         || blockState0.isOf(Blocks.CAMPFIRE)
         || blockState0.isOf(Blocks.SOUL_CAMPFIRE)
         || blockState0.isOf(Blocks.WITHER_ROSE)
         || blockState0.isOf(Blocks.POINTED_DRIPSTONE)
         || blockState0.isOf(Blocks.NETHER_PORTAL)
         || blockState0.isOf(Blocks.END_PORTAL);
   }

   static class a implements Comparable<dev.leg1tka.network.bots.GridPathSearch.a> {
      final int a;
      final int b;
      final int c;
      final double d;
      double e = Double.MAX_VALUE;
      double f;
      dev.leg1tka.network.bots.GridPathSearch.a g;
      boolean h;

      a(int intValue1, int intValue2, int intValue3, double doubleValue4) {
         this.a = intValue1;
         this.b = intValue2;
         this.c = intValue3;
         this.d = doubleValue4;
      }

      @Override
      public int compareTo(dev.leg1tka.network.bots.GridPathSearch.a value1) {
         return Double.compare(this.f, value1.f);
      }

      public int a(dev.leg1tka.network.bots.GridPathSearch.a value1) {
         return this.compareTo(value1);
      }
   }
}
