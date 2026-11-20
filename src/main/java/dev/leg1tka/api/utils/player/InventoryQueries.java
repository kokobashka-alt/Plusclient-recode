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

import dev.leg1tka.AspectDLC;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public final class InventoryQueries implements dev.leg1tka.api.ClientAccess {
   private static double c;
   private static double d;
   private static double e;
   private static double f;
   private static int g;
   private static int h;
   private static double i;
   private static double j;
   private static float k;
   private static final Map<java.util.UUID, Double> l = new HashMap<>();
   private static double m;
   private static long n;
   private static final Map<java.util.UUID, Long> o = new HashMap<>();
   private static final Set<java.util.UUID> p = new HashSet<>();
   private static final Mutable q = new Mutable();
   private static final Mutable r = new Mutable();
   private static ClientWorld s;

   private InventoryQueries() {
   }

   public static float a() {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         float floatValue0 = 0.0F;
         Box box1 = minecraftClient.player.getBoundingBox();

         for (Entity entity3 : minecraftClient.world.getEntities()) {
            if (entity3 instanceof EndCrystalEntity) {
               EndCrystalEntity endCrystalEntity4 = (EndCrystalEntity)entity3;
               if (!endCrystalEntity4.isRemoved() && !(minecraftClient.player.squaredDistanceTo(endCrystalEntity4) > 144.0)) {
                  floatValue0 = Math.max(floatValue0, a(endCrystalEntity4.getPos(), box1, null));
               }
            }
         }

         return floatValue0;
      } else {
         return 0.0F;
      }
   }

   public static float a(Vec3d vec3d0) {
      return minecraftClient.player == null ? 0.0F : a(vec3d0, minecraftClient.player.getBoundingBox(), null);
   }

   private static float a(Vec3d vec3d0, Box box1, Box box2) {
      return dev.leg1tka.api.utils.player.ItemAttributes.a(vec3d0, minecraftClient.player, box1, box2, List.of(), 6.0F, true);
   }

   public static boolean b() {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         for (PlayerEntity playerEntity1 : minecraftClient.world.getPlayers()) {
            if (b(playerEntity1) && minecraftClient.player.squaredDistanceTo(playerEntity1) <= 64.0 && a(playerEntity1, Items.END_CRYSTAL)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static float a(float floatValue0) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         PlayerEntity playerEntity1 = null;
         PlayerEntity playerEntity2 = null;

         for (PlayerEntity playerEntity4 : minecraftClient.world.getPlayers()) {
            if (b(playerEntity4) && !(minecraftClient.player.squaredDistanceTo(playerEntity4) > 64.0)) {
               if (playerEntity1 == null && a(playerEntity4, Items.END_CRYSTAL)) {
                  playerEntity1 = playerEntity4;
               }

               if (playerEntity2 == null && a(playerEntity4, Items.OBSIDIAN)) {
                  playerEntity2 = playerEntity4;
               }
            }
         }

         if (playerEntity1 == null && playerEntity2 == null) {
            return 0.0F;
         } else {
            Box box29 = minecraftClient.player.getBoundingBox();
            Vec3d vec3d30 = minecraftClient.player.getPos();
            BlockPos blockPos5 = minecraftClient.player.getBlockPos();
            float floatValue6 = 0.0F;
            int intValue7 = 0;
            dev.leg1tka.api.utils.player.ItemAttributes.a();

            float floatValue25;
            try {
               int intValue8 = -3;

               label184:
               while (true) {
                  if (intValue8 > 2 || intValue7 >= 64) {
                     return floatValue6;
                  }

                  int intValue9 = -5;

                  while (true) {
                     if (intValue9 > 5 || intValue7 >= 64) {
                        break label184;
                     }

                     for (int intValue10 = -5; intValue10 <= 5 && intValue7 < 64; intValue10++) {
                        q.set(blockPos5.getX() + intValue9, blockPos5.getY() + intValue8, blockPos5.getZ() + intValue10);
                        double doubleValue11 = (double)q.getX() + 0.5;
                        double doubleValue13 = (double)q.getY() + 1.0;
                        double doubleValue15 = (double)q.getZ() + 0.5;
                        double doubleValue17 = doubleValue11 - vec3d30.x;
                        double doubleValue19 = doubleValue13 - vec3d30.y;
                        double doubleValue21 = doubleValue15 - vec3d30.z;
                        if (!(doubleValue17 * doubleValue17 + doubleValue19 * doubleValue19 + doubleValue21 * doubleValue21 > 36.0)) {
                           Box box23 = null;
                           PlayerEntity playerEntity24;
                           if (playerEntity1 != null && dev.leg1tka.api.utils.player.BlockInteractions.a(q)) {
                              playerEntity24 = playerEntity1;
                           } else {
                              if (playerEntity2 == null || Math.abs(intValue9) > 3 || Math.abs(intValue10) > 3 || intValue8 < -1 || intValue8 > 1 || !a(q)) {
                                 continue;
                              }

                              playerEntity24 = playerEntity2;
                              box23 = new Box(q).contract(1.0E-4);
                           }

                           if (b(q) && !(dev.leg1tka.api.utils.player.InventoryActions.a(playerEntity24.getEyePos(), q) > 25.0)) {
                              intValue7++;
                              floatValue6 = Math.max(floatValue6, a(new Vec3d(doubleValue11, doubleValue13, doubleValue15), box29, box23));
                              if (floatValue6 >= floatValue0) {
                                 break label184;
                              }
                           }
                        }
                     }

                     intValue9++;
                     intValue8++;
                     break;
                  }
               }

               floatValue25 = floatValue6;
            } finally {
               dev.leg1tka.api.utils.player.ItemAttributes.b();
            }

            return floatValue25;
         }
      } else {
         return 0.0F;
      }
   }

   public static float b(float floatValue0) {
      if (minecraftClient.player != null && minecraftClient.world != null && !RespawnAnchorBlock.isNether(minecraftClient.world)) {
         BlockPos blockPos1 = minecraftClient.player.getBlockPos();
         Box box2 = minecraftClient.player.getBoundingBox();
         float floatValue3 = 0.0F;

         for (int intValue4 = -6; intValue4 <= 6; intValue4++) {
            for (int intValue5 = -6; intValue5 <= 6; intValue5++) {
               for (int intValue6 = -6; intValue6 <= 6; intValue6++) {
                  q.set(blockPos1.getX() + intValue4, blockPos1.getY() + intValue5, blockPos1.getZ() + intValue6);
                  BlockState blockState7 = minecraftClient.world.getBlockState(q);
                  if (blockState7.isOf(Blocks.RESPAWN_ANCHOR) && (Integer)blockState7.get(RespawnAnchorBlock.CHARGES) > 0) {
                     Vec3d vec3d8 = Vec3d.ofCenter(q);
                     if (a(vec3d8, 36.0)) {
                        BlockPos blockPos9 = q.toImmutable();
                        floatValue3 = Math.max(floatValue3, dev.leg1tka.api.utils.player.ItemAttributes.a(vec3d8, minecraftClient.player, box2, null, List.of(blockPos9), 5.0F, true));
                        if (floatValue3 >= floatValue0) {
                           return floatValue3;
                        }
                     }
                  }
               }
            }
         }

         return floatValue3;
      } else {
         return 0.0F;
      }
   }

   private static void e() {
      if (s != minecraftClient.world) {
         l.clear();
         o.clear();
         p.clear();
         s = minecraftClient.world;
      }

      if (l.size() > 128) {
         l.clear();
      }

      if (o.size() > 128) {
         o.clear();
      }

      if (p.size() > 128) {
         p.clear();
      }
   }

   private static double a(PlayerEntity playerEntity0) {
      double doubleValue1 = !playerEntity0.isOnGround() && !playerEntity0.isGliding() && !playerEntity0.isTouchingWater()
         ? Math.max(l.getOrDefault(playerEntity0.getUuid(), playerEntity0.getY()), playerEntity0.getY())
         : playerEntity0.getY();
      l.put(playerEntity0.getUuid(), doubleValue1);
      return doubleValue1;
   }

   public static boolean c() {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         e();
         dev.leg1tka.api.utils.player.EquipmentUtils.a();
         long longValue0 = System.currentTimeMillis();
         boolean flag2 = false;

         for (PlayerEntity playerEntity4 : minecraftClient.world.getPlayers()) {
            if (playerEntity4 != minecraftClient.player) {
               double doubleValue5 = a(playerEntity4);
               UUID uUID7 = playerEntity4.getUuid();
               if (b(playerEntity4) && playerEntity4.getMainHandStack().isOf(Items.MACE)) {
                  double doubleValue8 = minecraftClient.player.squaredDistanceTo(playerEntity4);
                  if (dev.leg1tka.api.utils.player.EquipmentUtils.a(playerEntity4)) {
                     if (doubleValue8 <= 25.0) {
                        flag2 = true;
                     }
                  } else if (!playerEntity4.isOnGround() && !playerEntity4.isGliding() && !playerEntity4.isTouchingWater()) {
                     if (playerEntity4.getY() - playerEntity4.prevY < 0.0 && doubleValue5 - playerEntity4.getY() > 1.5) {
                        p.add(uUID7);
                        o.remove(uUID7);
                     }

                     if (p.contains(uUID7) && doubleValue8 <= 36.0) {
                        flag2 = true;
                     }
                  } else {
                     if (p.remove(uUID7)) {
                        o.put(uUID7, longValue0);
                     }

                     Long long10 = (Long)o.get(uUID7);
                     if (long10 != null) {
                        if (longValue0 - long10 > 2000L) {
                           o.remove(uUID7);
                        } else if (doubleValue8 <= 36.0) {
                           flag2 = true;
                        }
                     }
                  }
               } else {
                  p.remove(uUID7);
                  o.remove(uUID7);
               }
            }
         }

         return flag2;
      } else {
         return false;
      }
   }

   public static float d() {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         e();
         float floatValue0 = 0.0F;

         for (PlayerEntity playerEntity2 : minecraftClient.world.getPlayers()) {
            if (playerEntity2 != minecraftClient.player) {
               double doubleValue3 = a(playerEntity2);
               if (b(playerEntity2) && !(minecraftClient.player.squaredDistanceTo(playerEntity2) > 36.0)) {
                  ItemStack itemStack5 = playerEntity2.getMainHandStack();
                  if (itemStack5.isOf(Items.MACE) && !playerEntity2.isGliding() && !(playerEntity2.getY() - playerEntity2.prevY >= 0.0)) {
                     float floatValue6 = (float)(doubleValue3 - playerEntity2.getY());
                     if (!(floatValue6 <= 1.5F)) {
                        int intValue7 = dev.leg1tka.api.utils.player.ItemAttributes.a(Enchantments.DENSITY, itemStack5);
                        int intValue8 = dev.leg1tka.api.utils.player.ItemAttributes.a(Enchantments.BREACH, itemStack5);
                        float floatValue9 = 9.0F + d(floatValue6) + 0.5F * (float)intValue7 * floatValue6;
                        floatValue0 = Math.max(floatValue0, dev.leg1tka.api.utils.player.ItemAttributes.a(minecraftClient.player, floatValue9, false, intValue8));
                     }
                  }
               }
            }
         }

         return floatValue0;
      } else {
         return 0.0F;
      }
   }

   private static float d(float floatValue0) {
      if (floatValue0 <= 3.0F) {
         return 4.0F * floatValue0;
      } else {
         return floatValue0 <= 8.0F ? 12.0F + 2.0F * (floatValue0 - 3.0F) : 22.0F + floatValue0 - 8.0F;
      }
   }

   public static boolean c(float floatValue0) {
      if (minecraftClient.player == null || minecraftClient.world == null) {
         return false;
      } else if (minecraftClient.player.fallDistance < floatValue0 || minecraftClient.player.isGliding() || minecraftClient.player.isTouchingWater() || minecraftClient.player.isClimbing()) {
         return false;
      } else if (!minecraftClient.player.hasStatusEffect(StatusEffects.SLOW_FALLING) && !minecraftClient.player.hasStatusEffect(StatusEffects.LEVITATION)) {
         Vec3d vec3d1 = minecraftClient.player.getPos();
         BlockHitResult blockHitResult2 = minecraftClient.world.raycast(new RaycastContext(vec3d1, vec3d1.add(0.0, -64.0, 0.0), ShapeType.OUTLINE, FluidHandling.ANY, minecraftClient.player));
         if (blockHitResult2.getType() != Type.BLOCK) {
            return true;
         } else {
            BlockPos blockPos3 = blockHitResult2.getBlockPos();
            if (!minecraftClient.world.getFluidState(blockPos3).isEmpty()) {
               return false;
            } else {
               BlockState blockState4 = minecraftClient.world.getBlockState(blockPos3);
               return !blockState4.isOf(Blocks.SLIME_BLOCK) && !blockState4.isOf(Blocks.COBWEB) && !blockState4.isOf(Blocks.POWDER_SNOW) && !blockState4.isOf(Blocks.SCAFFOLDING);
            }
         }
      } else {
         return false;
      }
   }

   private static boolean a(BlockPos blockPos0) {
      BlockState blockState1 = minecraftClient.world.getBlockState(blockPos0);
      if (!blockState1.isReplaceable()) {
         return false;
      } else {
         return minecraftClient.player.getBoundingBox().intersects(new Box(blockPos0))
            ? false
            : !minecraftClient.world.getBlockState(r.set(blockPos0.getX(), blockPos0.getY() - 1, blockPos0.getZ())).isReplaceable()
               || !minecraftClient.world.getBlockState(r.set(blockPos0.getX() + 1, blockPos0.getY(), blockPos0.getZ())).isReplaceable()
               || !minecraftClient.world.getBlockState(r.set(blockPos0.getX() - 1, blockPos0.getY(), blockPos0.getZ())).isReplaceable()
               || !minecraftClient.world.getBlockState(r.set(blockPos0.getX(), blockPos0.getY(), blockPos0.getZ() + 1)).isReplaceable()
               || !minecraftClient.world.getBlockState(r.set(blockPos0.getX(), blockPos0.getY(), blockPos0.getZ() - 1)).isReplaceable();
      }
   }

   private static boolean b(BlockPos blockPos0) {
      if (!minecraftClient.world.getBlockState(r.set(blockPos0.getX(), blockPos0.getY() + 1, blockPos0.getZ())).isAir()) {
         return false;
      } else {
         Box box1 = new Box(
            (double)blockPos0.getX(),
            (double)(blockPos0.getY() + 1),
            (double)blockPos0.getZ(),
            (double)(blockPos0.getX() + 1),
            (double)(blockPos0.getY() + 3),
            (double)(blockPos0.getZ() + 1)
         );
         return minecraftClient.world.getOtherEntities(null, box1, entity0x -> entity0x.isAlive() && !entity0x.isSpectator() && entity0x != minecraftClient.player).isEmpty()
            && !minecraftClient.player.getBoundingBox().intersects(box1);
      }
   }

   private static boolean a(Vec3d vec3d0, double doubleValue1) {
      for (PlayerEntity playerEntity4 : minecraftClient.world.getPlayers()) {
         if (b(playerEntity4) && playerEntity4.squaredDistanceTo(vec3d0) <= doubleValue1) {
            return true;
         }
      }

      return false;
   }

   private static boolean a(PlayerEntity playerEntity0, Item item1) {
      return playerEntity0.getMainHandStack().isOf(item1) || playerEntity0.getOffHandStack().isOf(item1);
   }

   private static boolean b(PlayerEntity playerEntity0) {
      return playerEntity0 != minecraftClient.player && playerEntity0.isAlive() && !playerEntity0.isSpectator() && !AspectDLC.INSTANCE.friendStorage.c(playerEntity0.getGameProfile().getName());
   }
}
