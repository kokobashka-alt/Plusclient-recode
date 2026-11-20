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

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public final class ItemCustomData {
   private ItemCustomData() {
   }

   public static PotionEntity a(World world0, BlockPos blockPos1) {
      PotionEntity potionEntity2 = null;
      double doubleValue3 = Double.MAX_VALUE;
      Vec3d vec3d5 = blockPos1.toCenterPos();

      for (PotionEntity potionEntity7 : world0.getEntitiesByClass(PotionEntity.class, new Box(blockPos1).expand(3.0), potionEntity0x -> true)) {
         double doubleValue8 = potionEntity7.squaredDistanceTo(vec3d5);
         if (doubleValue8 < doubleValue3) {
            doubleValue3 = doubleValue8;
            potionEntity2 = potionEntity7;
         }
      }

      return potionEntity2;
   }

   public static dev.leg1tka.api.utils.player.ItemCustomData.b a(PotionEntity potionEntity0, int intValue1) {
      dev.leg1tka.api.utils.player.ItemCustomData.b value2 = potionEntity0 != null ? dev.leg1tka.api.utils.player.ItemCustomData.b.a(potionEntity0.getStack()) : null;
      return value2 != null ? value2 : dev.leg1tka.api.utils.player.ItemCustomData.b.a(intValue1);
   }

   public static String a(ItemStack itemStack0) {
      NbtComponent nbtComponent1 = (NbtComponent)itemStack0.get(DataComponentTypes.CUSTOM_DATA);
      if (nbtComponent1 == null) {
         return null;
      } else {
         NbtCompound nbtCompound2 = nbtComponent1.getNbt();
         if (nbtCompound2 == null) {
            return null;
         } else {
            if (nbtCompound2.contains("PublicBukkitValues")) {
               NbtCompound nbtCompound3 = nbtCompound2.getCompound("PublicBukkitValues");
               if (nbtCompound3.contains("minecraft:ftid")) {
                  return nbtCompound3.getString("minecraft:ftid");
               }

               if (nbtCompound3.contains("spookyitems:spooky-item")) {
                  return nbtCompound3.getString("spookyitems:spooky-item");
               }
            }

            return nbtCompound2.contains("spooky-item") ? nbtCompound2.getString("spooky-item") : null;
         }
      }
   }

   public static String b(ItemStack itemStack0) {
      String string1 = Formatting.strip(itemStack0.getName().getString());
      return string1 == null ? "" : string1.toLowerCase(Locale.ROOT);
   }

   public static boolean c(ItemStack itemStack0) {
      PotionContentsComponent potionContentsComponent1 = (PotionContentsComponent)itemStack0.get(DataComponentTypes.POTION_CONTENTS);
      if (potionContentsComponent1 == null) {
         return false;
      } else {
         for (StatusEffectInstance statusEffectInstance3 : potionContentsComponent1.getEffects()) {
            if (statusEffectInstance3.getEffectType().equals(StatusEffects.INSTANT_HEALTH)) {
               return true;
            }
         }

         return false;
      }
   }

   public static enum a {
      TOTEM("Тотем", Items.TOTEM_OF_UNDYING, 60),
      ENCHANTED_APPLE("Чарка", Items.ENCHANTED_GOLDEN_APPLE, 150),
      GOLDEN_APPLE("Гэпл", Items.GOLDEN_APPLE, 30),
      CHORUS("Хорус", Items.CHORUS_FRUIT, 20),
      HEALING("Исцеление", Items.POTION, 15),
      PEARL("Жемчуг", Items.ENDER_PEARL, 15),
      SNOWBALL("Снежок", Items.SNOWBALL, 20),
      WIND_CHARGE("Заряд ветра", Items.WIND_CHARGE, 30),
      FIRE_CHARGE("Огненный смерч", Items.FIRE_CHARGE, 60),
      DEZORIENTATION("Дезориентация", Items.ENDER_EYE, 60),
      TRAP("Трапка", Items.NETHERITE_SCRAP, 10),
      DRAGON_TRAP("Драконья трапка", Items.NETHERITE_SCRAP, 15),
      PLAST("Пласт", Items.DRIED_KELP, 10),
      DRAGON_PLAST("Драконий пласт", Items.DRIED_KELP, 15);

      public final String a;
      public final Item b;
      public final int c;
      public final Identifier d;
      private final ItemStack e;
      private int f;

      private a(String string3, Item item4, int intValue5) {
         this.a = string3;
         this.b = item4;
         this.c = intValue5;
         this.d = Registries.ITEM.getId(item4);
         this.e = item4 == Items.POTION ? PotionContentsComponent.createStack(Items.POTION, Potions.HEALING) : new ItemStack(item4);
      }

      public ItemStack a() {
         return this.e;
      }

      public long b() {
         return this.f > 0 ? (long)this.f * 50L : (long)this.c * 1000L;
      }

      public static void a(Identifier identifier0, int intValue1) {
         if (intValue1 > 20) {
            for (dev.leg1tka.api.utils.player.ItemCustomData.a value5 : values()) {
               if (value5.d.equals(identifier0) && value5 != DRAGON_TRAP && value5 != DRAGON_PLAST) {
                  value5.f = intValue1;
               }
            }
         }
      }
   }

   public static enum b {
      CRACKER(
         "Хлопушка",
         "хлопушк",
         null,
         16738740,
         new int[]{16738740},
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.SLOWNESS, 200, 9),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.SPEED, 300, 4),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.BLINDNESS, 100, 9),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.GLOWING, 3600, 0)
      ),
      RADIATION(
         "Зелье Радиации",
         "радиац",
         null,
         9699134,
         new int[]{3329330},
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.POISON, 1200, 1),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.WITHER, 1200, 1),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.SLOWNESS, 1800, 2),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.HUNGER, 1200, 4),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.GLOWING, 2400, 0)
      ),
      SLEEPING(
         "Снотворное",
         "снотворн",
         "potion-drowsiness",
         6918889,
         new int[]{254, 255, 4737096},
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.WEAKNESS, 1800, 1),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.MINING_FATIGUE, 200, 1),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.WITHER, 1800, 2),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.BLINDNESS, 200, 0)
      ),
      WRATH(
         "Зелье Гнева",
         "гнев",
         null,
         16747520,
         new int[]{10040115},
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.STRENGTH, 600, 4),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.SLOWNESS, 600, 3)
      ),
      ASSASSIN(
         "Зелье Ассасина",
         "ассасин",
         "potion-assassin",
         11545130,
         new int[]{3355443},
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.STRENGTH, 1200, 3),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.SPEED, 6000, 2),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.HASTE, 1200, 0),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.INSTANT_DAMAGE, 0, 1)
      ),
      PALADIN(
         "Зелье Палладина",
         "паллад",
         "potion-paladin",
         13762395,
         new int[]{65535},
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.RESISTANCE, 12000, 0),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.FIRE_RESISTANCE, 12000, 0),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.HEALTH_BOOST, 1200, 2),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.INVISIBILITY, 18000, 2)
      ),
      HOLY_WATER(
         "Святая вода",
         "святая вод",
         "potion-holy-water",
         16776830,
         new int[]{16777215},
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.REGENERATION, 900, 1),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.INVISIBILITY, 12000, 1),
         new dev.leg1tka.api.utils.player.ItemCustomData.c(StatusEffects.INSTANT_HEALTH, 0, 2)
      );

      public final String a;
      public final String b;
      public final String c;
      public final int d;
      public final int[] e;
      public final List<dev.leg1tka.api.utils.player.ItemCustomData.c> f;
      private final ItemStack g;

      private b(String string3, String string4, String string5, int intValue6, int[] intValueArray7, dev.leg1tka.api.utils.player.ItemCustomData.c... valueArray8) {
         this.a = string3;
         this.b = string4;
         this.c = string5;
         this.d = intValue6;
         this.e = intValueArray7;
         this.f = List.of(valueArray8);
         this.g = new ItemStack(Items.SPLASH_POTION);
         this.g.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(Optional.empty(), Optional.of(intValue6), List.of(), Optional.empty()));
      }

      public ItemStack a() {
         return this.g;
      }

      public int a(double doubleValue1) {
         if (doubleValue1 <= 0.0) {
            return 0;
         } else {
            int intValue3 = 0;

            for (dev.leg1tka.api.utils.player.ItemCustomData.c value5 : this.f) {
               int intValue6 = (int)((double)value5.ticks() * doubleValue1 + 0.5);
               if (intValue6 > 20) {
                  intValue3 = Math.max(intValue3, intValue6);
               }
            }

            return intValue3;
         }
      }

      public static dev.leg1tka.api.utils.player.ItemCustomData.b a(int intValue0) {
         int intValue1 = intValue0 & 16777215;

         for (dev.leg1tka.api.utils.player.ItemCustomData.b value5 : values()) {
            for (int intValue9 : value5.e) {
               if ((intValue9 & 16777215) == intValue1) {
                  return value5;
               }
            }
         }

         int intValue10 = intValue1 >> 16 & 0xFF;
         int intValue11 = intValue1 >> 8 & 0xFF;
         int intValue12 = intValue1 & 0xFF;
         boolean flag13 = Math.abs(intValue10 - intValue11) <= 10 && Math.abs(intValue11 - intValue12) <= 10 && Math.abs(intValue10 - intValue12) <= 10 && intValue10 >= 14 && intValue10 <= 88;
         return flag13 ? ASSASSIN : null;
      }

      public static dev.leg1tka.api.utils.player.ItemCustomData.b a(ItemStack itemStack0) {
         if (itemStack0 != null && !itemStack0.isEmpty()) {
            String string1 = dev.leg1tka.api.utils.player.ItemCustomData.a(itemStack0);
            if (string1 != null) {
               for (dev.leg1tka.api.utils.player.ItemCustomData.b value5 : values()) {
                  if (string1.equals(value5.c)) {
                     return value5;
                  }
               }
            }

            String string7 = dev.leg1tka.api.utils.player.ItemCustomData.b(itemStack0);

            for (dev.leg1tka.api.utils.player.ItemCustomData.b value6 : values()) {
               if (string7.contains(value6.b)) {
                  return value6;
               }
            }

            return null;
         } else {
            return null;
         }
      }

      public static dev.leg1tka.api.utils.player.ItemCustomData.b a(Iterable<StatusEffectInstance> iterable0) {
         for (dev.leg1tka.api.utils.player.ItemCustomData.b value4 : values()) {
            if (value4.b(iterable0) > 0) {
               return value4;
            }
         }

         return null;
      }

      public int b(Iterable<StatusEffectInstance> iterable1) {
         int intValue2 = 0;
         int intValue3 = 0;

         for (dev.leg1tka.api.utils.player.ItemCustomData.c value5 : this.f) {
            if (value5.ticks() > 20) {
               intValue3++;
               StatusEffectInstance statusEffectInstance6 = null;

               for (StatusEffectInstance statusEffectInstance8 : iterable1) {
                  if (statusEffectInstance8.getEffectType().equals(value5.type()) && Math.abs(statusEffectInstance8.getAmplifier() - value5.amplifier()) <= 1) {
                     statusEffectInstance6 = statusEffectInstance8;
                     break;
                  }
               }

               if (statusEffectInstance6 == null) {
                  return 0;
               }

               intValue2 = Math.max(intValue2, statusEffectInstance6.getDuration());
            }
         }

         return intValue3 >= 2 ? intValue2 : 0;
      }

      public boolean a(StatusEffectInstance statusEffectInstance1) {
         for (dev.leg1tka.api.utils.player.ItemCustomData.c value3 : this.f) {
            if (value3.ticks() > 20 && statusEffectInstance1.getEffectType().equals(value3.type()) && Math.abs(statusEffectInstance1.getAmplifier() - value3.amplifier()) <= 1) {
               return true;
            }
         }

         return false;
      }
   }

   public static record c(RegistryEntry<StatusEffect> type, int ticks, int amplifier)  {


}
}
