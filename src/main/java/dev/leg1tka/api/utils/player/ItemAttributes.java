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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.jetbrains.annotations.Nullable;

public final class ItemAttributes implements dev.leg1tka.api.ClientAccess {
   private static double c;
   private static final EquipmentSlot[] d = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
   private static final Map e = new HashMap();
   private static final Map f = new HashMap();
   private static boolean g;

   private ItemAttributes() {
   }

   public static void a() {
      e.clear();
      f.clear();
      g = true;
   }

   public static void b() {
      g = false;
   }

   public static float a(Vec3d vec3d0, Entity entity1) {
      return entity1 instanceof LivingEntity livingEntity2 ? a(vec3d0, livingEntity2, entity1.getBoundingBox(), null, List.of()) : 0.0F;
   }

   public static float a(Vec3d vec3d0, LivingEntity livingEntity1, Box box2, @Nullable Box box3, List<BlockPos> list4) {
      return a(vec3d0, livingEntity1, box2, box3, list4, 6.0F, true);
   }

   public static float a(Vec3d vec3d0, LivingEntity livingEntity1, Box box2, @Nullable Box box3, List<BlockPos> list4, float floatValue5, boolean flag6) {
      if (vec3d0 != null && livingEntity1 != null && box2 != null && minecraftClient.world != null && livingEntity1.isAlive()) {
         double doubleValue7 = (double)floatValue5 * 2.0;
         double doubleValue9 = (box2.minX + box2.maxX) * 0.5;
         double doubleValue11 = (box2.minZ + box2.maxZ) * 0.5;
         double doubleValue13 = doubleValue9 - vec3d0.x;
         double doubleValue15 = box2.minY - vec3d0.y;
         double doubleValue17 = doubleValue11 - vec3d0.z;
         double doubleValue19 = Math.sqrt(doubleValue13 * doubleValue13 + doubleValue15 * doubleValue15 + doubleValue17 * doubleValue17);
         if (doubleValue19 > doubleValue7) {
            return 0.0F;
         } else {
            float floatValue21 = floatValue5 == 6.0F ? a(vec3d0, box2, livingEntity1, box3, list4) : b(vec3d0, box2, livingEntity1, box3, list4);
            double doubleValue22 = (1.0 - doubleValue19 / doubleValue7) * (double)floatValue21;
            float floatValue24 = (float)((doubleValue22 * doubleValue22 + doubleValue22) * 0.5 * 7.0 * doubleValue7 + 1.0);
            if (flag6 && livingEntity1 instanceof PlayerEntity) {
               floatValue24 = a(floatValue24);
            }

            return a(livingEntity1, floatValue24, true, 0);
         }
      } else {
         return 0.0F;
      }
   }

   public static float a(Vec3d vec3d0, LivingEntity livingEntity1, Box box2, @Nullable Box box3, List<BlockPos> list4, float floatValue5) {
      if (vec3d0 != null && livingEntity1 != null && box2 != null && minecraftClient.world != null && livingEntity1.isAlive()) {
         double doubleValue6 = (double)floatValue5 * 2.0;
         double doubleValue8 = (box2.minX + box2.maxX) * 0.5 - vec3d0.x;
         double doubleValue10 = box2.minY - vec3d0.y;
         double doubleValue12 = (box2.minZ + box2.maxZ) * 0.5 - vec3d0.z;
         double doubleValue14 = Math.sqrt(doubleValue8 * doubleValue8 + doubleValue10 * doubleValue10 + doubleValue12 * doubleValue12);
         if (doubleValue14 > doubleValue6) {
            return 0.0F;
         } else {
            float floatValue16 = floatValue5 == 6.0F ? a(vec3d0, box2, livingEntity1, box3, list4) : b(vec3d0, box2, livingEntity1, box3, list4);
            double doubleValue17 = (1.0 - doubleValue14 / doubleValue6) * (double)floatValue16;
            float floatValue19 = (float)((doubleValue17 * doubleValue17 + doubleValue17) * 0.5 * 7.0 * doubleValue6 + 1.0);
            return livingEntity1 instanceof PlayerEntity ? a(floatValue19) : floatValue19;
         }
      } else {
         return 0.0F;
      }
   }

   public static float a(float floatValue0) {
      if (minecraftClient.world == null) {
         return floatValue0;
      } else {
         return switch (minecraftClient.world.getDifficulty()) {
            case PEACEFUL -> 0.0F;
            case EASY -> Math.min(floatValue0 / 2.0F + 1.0F, floatValue0);
            case NORMAL -> floatValue0;
            case HARD -> floatValue0 * 1.5F;
            default -> throw new MatchException(null, null);
         };
      }
   }

   public static float b(Vec3d vec3d0, Entity entity1) {
      return minecraftClient.world == null ? 0.0F : b(vec3d0, entity1.getBoundingBox(), entity1, null, List.of());
   }

   public static float a(LivingEntity livingEntity0, float floatValue1, boolean flag2, int intValue3) {
      floatValue1 = a(livingEntity0, floatValue1, intValue3);
      floatValue1 = a(livingEntity0, floatValue1, flag2);
      floatValue1 = a(livingEntity0, floatValue1);
      return Math.max(0.0F, floatValue1);
   }

   private static float a(Vec3d vec3d0, Box box1, Entity entity2, @Nullable Box box3, List<BlockPos> list4) {
      boolean flag5 = box3 != null || !list4.isEmpty();
      if (!flag5 && g) {
         dev.leg1tka.api.utils.player.ItemAttributes.a value6 = new dev.leg1tka.api.utils.player.ItemAttributes.a(entity2.getId(), vec3d0, box1);
         Float float7 = (Float)e.get(value6);
         if (float7 != null) {
            return float7;
         } else {
            float floatValue8 = b(vec3d0, box1, entity2, null, List.of());
            e.put(value6, floatValue8);
            return floatValue8;
         }
      } else {
         return b(vec3d0, box1, entity2, box3, list4);
      }
   }

   private static float b(Vec3d vec3d0, Box box1, Entity entity2, @Nullable Box box3, List<BlockPos> list4) {
      double doubleValue5 = 1.0 / ((box1.maxX - box1.minX) * 2.0 + 1.0);
      double doubleValue7 = 1.0 / ((box1.maxY - box1.minY) * 2.0 + 1.0);
      double doubleValue9 = 1.0 / ((box1.maxZ - box1.minZ) * 2.0 + 1.0);
      if (!(doubleValue5 < 0.0) && !(doubleValue7 < 0.0) && !(doubleValue9 < 0.0)) {
         double doubleValue11 = (1.0 - Math.floor(1.0 / doubleValue5) * doubleValue5) * 0.5;
         double doubleValue13 = (1.0 - Math.floor(1.0 / doubleValue9) * doubleValue9) * 0.5;
         int intValue15 = 0;
         int intValue16 = 0;

         for (double doubleValue17 = 0.0; doubleValue17 <= 1.0; doubleValue17 += doubleValue5) {
            for (double doubleValue19 = 0.0; doubleValue19 <= 1.0; doubleValue19 += doubleValue7) {
               for (double doubleValue21 = 0.0; doubleValue21 <= 1.0; doubleValue21 += doubleValue9) {
                  Vec3d vec3d23 = new Vec3d(
                     MathHelper.lerp(doubleValue17, box1.minX, box1.maxX) + doubleValue11,
                     MathHelper.lerp(doubleValue19, box1.minY, box1.maxY),
                     MathHelper.lerp(doubleValue21, box1.minZ, box1.maxZ) + doubleValue13
                  );
                  if (a(vec3d23, vec3d0, entity2, box3, list4)) {
                     intValue15++;
                  }

                  intValue16++;
               }
            }
         }

         return intValue16 == 0 ? 0.0F : (float)intValue15 / (float)intValue16;
      } else {
         return 0.0F;
      }
   }

   private static boolean a(Vec3d vec3d0, Vec3d vec3d1, Entity entity2, @Nullable Box box3, List<BlockPos> list4) {
      if (box3 != null && box3.raycast(vec3d0, vec3d1).isPresent()) {
         return false;
      } else {
         Vec3d vec3d5 = vec3d0;
         int intValue6 = list4.size();

         for (int intValue7 = 0; intValue7 <= intValue6; intValue7++) {
            BlockHitResult blockHitResult8 = minecraftClient.world.raycast(new RaycastContext(vec3d5, vec3d1, ShapeType.COLLIDER, FluidHandling.NONE, entity2));
            if (blockHitResult8.getType() == Type.MISS) {
               return true;
            }

            if (!list4.contains(blockHitResult8.getBlockPos())) {
               return false;
            }

            Vec3d vec3d9 = vec3d1.subtract(vec3d0).normalize();
            Vec3d vec3d10 = a(blockHitResult8.getPos(), vec3d9, blockHitResult8.getBlockPos());
            if (vec3d1.subtract(vec3d10).dotProduct(vec3d9) <= 0.0) {
               return true;
            }

            vec3d5 = vec3d10;
         }

         return false;
      }
   }

   private static Vec3d a(Vec3d vec3d0, Vec3d vec3d1, BlockPos blockPos2) {
      double doubleValue3 = Double.POSITIVE_INFINITY;
      if (vec3d1.x > 1.0E-8) {
         doubleValue3 = Math.min(doubleValue3, ((double)(blockPos2.getX() + 1) - vec3d0.x) / vec3d1.x);
      } else if (vec3d1.x < -1.0E-8) {
         doubleValue3 = Math.min(doubleValue3, ((double)blockPos2.getX() - vec3d0.x) / vec3d1.x);
      }

      if (vec3d1.y > 1.0E-8) {
         doubleValue3 = Math.min(doubleValue3, ((double)(blockPos2.getY() + 1) - vec3d0.y) / vec3d1.y);
      } else if (vec3d1.y < -1.0E-8) {
         doubleValue3 = Math.min(doubleValue3, ((double)blockPos2.getY() - vec3d0.y) / vec3d1.y);
      }

      if (vec3d1.z > 1.0E-8) {
         doubleValue3 = Math.min(doubleValue3, ((double)(blockPos2.getZ() + 1) - vec3d0.z) / vec3d1.z);
      } else if (vec3d1.z < -1.0E-8) {
         doubleValue3 = Math.min(doubleValue3, ((double)blockPos2.getZ() - vec3d0.z) / vec3d1.z);
      }

      return Double.isFinite(doubleValue3) && doubleValue3 >= 0.0 ? vec3d0.add(vec3d1.multiply(doubleValue3 + 1.0E-4)) : vec3d0.add(vec3d1.multiply(1.0E-4));
   }

   private static float a(LivingEntity livingEntity0, float floatValue1, int intValue2) {
      float floatValue3 = (float)livingEntity0.getArmor();
      if (floatValue3 <= 0.0F) {
         return floatValue1;
      } else {
         float floatValue4 = (float)livingEntity0.getAttributeValue(EntityAttributes.ARMOR_TOUGHNESS);
         float floatValue5 = MathHelper.clamp(floatValue3 - floatValue1 / (2.0F + floatValue4 / 4.0F), floatValue3 * 0.2F, 20.0F);
         float floatValue6 = MathHelper.clamp(1.0F - 0.15F * (float)intValue2, 0.0F, 1.0F);
         return floatValue1 * (1.0F - floatValue5 / 25.0F * floatValue6);
      }
   }

   private static float a(LivingEntity livingEntity0, float floatValue1, boolean flag2) {
      if (minecraftClient.world == null) {
         return floatValue1;
      } else {
         int intValue3 = 0;

         for (EquipmentSlot equipmentSlot7 : d) {
            ItemStack itemStack8 = livingEntity0.getEquippedStack(equipmentSlot7);
            if (!itemStack8.isEmpty()) {
               intValue3 += a(Enchantments.PROTECTION, itemStack8);
               if (flag2) {
                  intValue3 += a(Enchantments.BLAST_PROTECTION, itemStack8) * 2;
               }
            }
         }

         intValue3 = MathHelper.clamp(intValue3, 0, 20);
         return floatValue1 * (1.0F - (float)intValue3 / 25.0F);
      }
   }

   private static float a(LivingEntity livingEntity0, float floatValue1) {
      StatusEffectInstance statusEffectInstance2 = livingEntity0.getStatusEffect(StatusEffects.RESISTANCE);
      return statusEffectInstance2 == null ? floatValue1 : floatValue1 * (1.0F - MathHelper.clamp((float)(statusEffectInstance2.getAmplifier() + 1) * 0.2F, 0.0F, 1.0F));
   }

   public static float a(BlockState blockState0, BlockPos blockPos1) {
      if (blockState0 != null && blockPos1 != null && minecraftClient.player != null && minecraftClient.world != null) {
         Float float2 = g ? (Float)f.get(blockState0) : null;
         if (float2 != null) {
            return float2;
         } else {
            int intValue3 = a(blockState0);
            int intValue4 = minecraftClient.player.getInventory().selectedSlot;

            float floatValue5;
            try {
               if (intValue3 >= 0) {
                  minecraftClient.player.getInventory().selectedSlot = intValue3;
               }

               floatValue5 = blockState0.calcBlockBreakingDelta(minecraftClient.player, minecraftClient.world, blockPos1);
            } finally {
               minecraftClient.player.getInventory().selectedSlot = intValue4;
            }

            float floatValue6 = floatValue5 > 0.0F ? (float)Math.ceil((double)(1.0F / floatValue5)) : Float.POSITIVE_INFINITY;
            if (g) {
               f.put(blockState0, floatValue6);
            }

            return floatValue6;
         }
      } else {
         return Float.POSITIVE_INFINITY;
      }
   }

   public static int a(BlockState blockState0) {
      return a(blockState0, 9);
   }

   public static int b(BlockState blockState0) {
      return a(blockState0, 36);
   }

   private static int a(BlockState blockState0, int intValue1) {
      if (blockState0 != null && minecraftClient.player != null) {
         int intValue2 = -1;
         ItemStack itemStack3 = ItemStack.EMPTY;
         float floatValue4 = a(itemStack3, blockState0);

         for (int intValue5 = 0; intValue5 < intValue1; intValue5++) {
            ItemStack itemStack6 = minecraftClient.player.getInventory().getStack(intValue5);
            float floatValue7 = a(itemStack6, blockState0);
            if (a(itemStack6, itemStack3, floatValue7, floatValue4, blockState0)) {
               intValue2 = intValue5;
               itemStack3 = itemStack6;
               floatValue4 = floatValue7;
            }
         }

         return intValue2;
      } else {
         return -1;
      }
   }

   private static float a(ItemStack itemStack0, BlockState blockState1) {
      if (itemStack0 != null && !itemStack0.isEmpty()) {
         float floatValue2 = itemStack0.getMiningSpeedMultiplier(blockState1);
         int intValue3 = b(itemStack0);
         if (floatValue2 > 1.0F && intValue3 > 0) {
            floatValue2 += (float)(intValue3 * intValue3) + 1.0F;
         }

         return floatValue2;
      } else {
         return 1.0F;
      }
   }

   private static boolean a(ItemStack itemStack0, ItemStack itemStack1, float floatValue2, float floatValue3, BlockState blockState4) {
      if (floatValue2 > floatValue3 + 1.0E-4F) {
         return true;
      } else if (floatValue2 + 1.0E-4F < floatValue3) {
         return false;
      } else {
         boolean flag5 = b(itemStack0, blockState4);
         boolean flag6 = b(itemStack1, blockState4);
         if (flag5 != flag6) {
            return flag5;
         } else {
            int intValue7 = b(itemStack0);
            int intValue8 = b(itemStack1);
            if (intValue7 != intValue8) {
               return intValue7 > intValue8;
            } else {
               boolean flag9 = a(itemStack0);
               return flag9 != a(itemStack1) && !flag9;
            }
         }
      }
   }

   private static boolean b(ItemStack itemStack0, BlockState blockState1) {
      return !blockState1.isToolRequired() || itemStack0 != null && !itemStack0.isEmpty() && itemStack0.isSuitableFor(blockState1);
   }

   private static boolean a(ItemStack itemStack0) {
      return itemStack0 != null && !itemStack0.isEmpty() && itemStack0.isOf(Items.SHEARS) && b(itemStack0) == 0;
   }

   private static int b(ItemStack itemStack0) {
      return itemStack0 != null && !itemStack0.isEmpty() ? a(Enchantments.EFFICIENCY, itemStack0) : 0;
   }

   public static int a(RegistryKey<Enchantment> registryKey0, ItemStack itemStack1) {
      if (minecraftClient.world == null) {
         return 0;
      } else {
         Registry<Enchantment> registry2 = minecraftClient.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
         return registry2.getOptional(registryKey0).map(reference1x -> EnchantmentHelper.getLevel(reference1x, itemStack1)).orElse(0);
      }
   }

   static record a(int entityId, Vec3d explosion, Box box)  {


}
}
