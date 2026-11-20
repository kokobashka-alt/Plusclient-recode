package dev.leg1tka.modules.render;


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

import java.util.ArrayList;
import java.util.List;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class TalismanIndication extends Module {
   public static TalismanIndication c = new TalismanIndication();
   private static final Identifier i = Identifier.of("aspectdlc", "crusher_helmet");
   private static final Identifier j = Identifier.of("aspectdlc", "crusher_chestplate");
   private static final Identifier k = Identifier.of("aspectdlc", "crusher_leggings");
   private static final Identifier l = Identifier.of("aspectdlc", "crusher_boots");
   private static final Identifier m = Identifier.of("aspectdlc", "thorns_armor");
   private static final List<TalismanEntry> n = List.of(
      new TalismanEntry(
         Identifier.of("aspectdlc", "tal_karatel0"),
         Identifier.of("aspectdlc", "tal_karatel0_simple"),
         List.of(
            new TalismanIndication.a(EntityAttributes.ATTACK_DAMAGE, Operation.ADD_VALUE, 7.0),
            new TalismanIndication.a(EntityAttributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.1),
            new TalismanIndication.a(EntityAttributes.MAX_HEALTH, Operation.ADD_VALUE, -4.0)
         )
      ),
      new TalismanEntry(
         Identifier.of("aspectdlc", "tal_demon"),
         Identifier.of("aspectdlc", "tal_demon_simple"),
         List.of(
            new TalismanIndication.a(EntityAttributes.ATTACK_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.1), new TalismanIndication.a(EntityAttributes.ATTACK_DAMAGE, Operation.ADD_VALUE, 2.5)
         )
      ),
      new TalismanEntry(
         Identifier.of("aspectdlc", "tal_mraka"),
         Identifier.of("aspectdlc", "tal_mraka_simple"),
         List.of(new TalismanIndication.a(EntityAttributes.MAX_HEALTH, Operation.ADD_VALUE, 1.5), new TalismanIndication.a(EntityAttributes.ARMOR, Operation.ADD_VALUE, 1.5))
      ),
      new TalismanEntry(
         Identifier.of("aspectdlc", "tal_vihrya"),
         Identifier.of("aspectdlc", "tal_vihrya_simple"),
         List.of(
            new TalismanIndication.a(EntityAttributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.15),
            new TalismanIndication.a(EntityAttributes.MAX_HEALTH, Operation.ADD_VALUE, 2.0),
            new TalismanIndication.a(EntityAttributes.ATTACK_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.15)
         )
      ),
      new TalismanEntry(
         Identifier.of("aspectdlc", "tal_razdora"),
         Identifier.of("aspectdlc", "tal_razdora_simple"),
         List.of(
            new TalismanIndication.a(EntityAttributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.1),
            new TalismanIndication.a(EntityAttributes.MAX_HEALTH, Operation.ADD_VALUE, 2.0),
            new TalismanIndication.a(EntityAttributes.ATTACK_SPEED, Operation.ADD_MULTIPLIED_BASE, 0.1),
            new TalismanIndication.a(EntityAttributes.ARMOR, Operation.ADD_VALUE, -3.0),
            new TalismanIndication.a(EntityAttributes.ATTACK_DAMAGE, Operation.ADD_VALUE, 4.0)
         )
      ),
      new TalismanEntry(
         Identifier.of("aspectdlc", "tal_tirana"),
         Identifier.of("aspectdlc", "tal_tirana_simple"),
         List.of(
            new TalismanIndication.a(EntityAttributes.MAX_HEALTH, Operation.ADD_VALUE, -4.0),
            new TalismanIndication.a(EntityAttributes.ARMOR, Operation.ADD_VALUE, 2.0),
            new TalismanIndication.a(EntityAttributes.ATTACK_DAMAGE, Operation.ADD_VALUE, 2.0)
         )
      ),
      new TalismanEntry(
         Identifier.of("aspectdlc", "tal_yarosti"),
         Identifier.of("aspectdlc", "tal_yarosti_simple"),
         List.of(new TalismanIndication.a(EntityAttributes.MAX_HEALTH, Operation.ADD_VALUE, -4.0), new TalismanIndication.a(EntityAttributes.ATTACK_DAMAGE, Operation.ADD_VALUE, 5.0))
      ),
      new TalismanEntry(
         Identifier.of("aspectdlc", "tal_krush0"),
         Identifier.of("aspectdlc", "tal_krush0_simple"),
         List.of(
            new TalismanIndication.a(EntityAttributes.ARMOR_TOUGHNESS, Operation.ADD_VALUE, 2.0),
            new TalismanIndication.a(EntityAttributes.ARMOR, Operation.ADD_VALUE, 2.0),
            new TalismanIndication.a(EntityAttributes.MAX_HEALTH, Operation.ADD_VALUE, 4.0),
            new TalismanIndication.a(EntityAttributes.ATTACK_DAMAGE, Operation.ADD_VALUE, 3.0)
         )
      )
   );
   public final ModeSetting d = new ModeSetting("Мод", "SpookyTime", "SpookyTime", "ArtyGrief");
   public final BooleanSetting e = new BooleanSetting("Индикация талисманов", true);
   public final ModeSetting f = new ModeSetting(
         "Выбор индикации",
         "Красивый",
         "Красивый",
         "Простая"
      )
      .a(this.e::getValue);
   public final BooleanSetting g = new BooleanSetting("Индикация брони круша", true);
   public final BooleanSetting h = new BooleanSetting("Индикация шипов", true);

   public TalismanIndication() {
      super(
         "TalismanIndication",
         "Подменяет талисманы по атрибутам",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h});
   }

   public ItemStack a(ItemStack itemStack1) {
      if (this.isEnabled() && !itemStack1.isEmpty()) {
         ItemStack itemStack2 = this.b(itemStack1);
         if (itemStack2 != itemStack1) {
            return itemStack2;
         } else {
            ItemStack itemStack3 = this.c(itemStack1);
            if (itemStack3 != itemStack1) {
               return itemStack3;
            } else if (this.e.getValue() && itemStack1.isOf(Items.TOTEM_OF_UNDYING)) {
               TalismanEntry talismanEntry4 = this.e(itemStack1);
               if (talismanEntry4 == null) {
                  return itemStack1;
               } else {
                  Identifier identifier5 = talismanEntry4.a(this.f.b("Простая"));
                  return this.a(itemStack1, identifier5);
               }
            } else {
               return itemStack1;
            }
         }
      } else {
         return itemStack1;
      }
   }

   private ItemStack b(ItemStack itemStack1) {
      return this.h.getValue() && this.g(itemStack1) ? this.a(itemStack1, m) : itemStack1;
   }

   private ItemStack c(ItemStack itemStack1) {
      if (this.g.getValue() && this.f(itemStack1)) {
         Identifier identifier2 = this.d(itemStack1);
         return identifier2 == null ? itemStack1 : this.a(itemStack1, identifier2);
      } else {
         return itemStack1;
      }
   }

   private ItemStack a(ItemStack itemStack1, Identifier identifier2) {
      Identifier identifier3 = (Identifier)itemStack1.get(DataComponentTypes.ITEM_MODEL);
      if (identifier2.equals(identifier3)) {
         return itemStack1;
      } else {
         ItemStack itemStack4 = itemStack1.copy();
         itemStack4.set(DataComponentTypes.ITEM_MODEL, identifier2);
         return itemStack4;
      }
   }

   private Identifier d(ItemStack itemStack1) {
      EquippableComponent equippableComponent2 = (EquippableComponent)itemStack1.get(DataComponentTypes.EQUIPPABLE);
      if (equippableComponent2 != null && equippableComponent2.slot() != null) {
         EquipmentSlot equipmentSlot3 = equippableComponent2.slot();
         if (equipmentSlot3 == EquipmentSlot.HEAD) {
            return i;
         } else if (equipmentSlot3 == EquipmentSlot.CHEST) {
            return j;
         } else if (equipmentSlot3 == EquipmentSlot.LEGS) {
            return k;
         } else {
            return equipmentSlot3 == EquipmentSlot.FEET ? l : null;
         }
      } else {
         return null;
      }
   }

   private TalismanEntry e(ItemStack itemStack1) {
      List list2 = this.i(itemStack1);

      for (TalismanEntry talismanEntry4 : n) {
         if (talismanEntry4.a(list2)) {
            return talismanEntry4;
         }
      }

      return null;
   }

   private boolean f(ItemStack itemStack1) {
      if (minecraftClient.world != null && itemStack1.get(DataComponentTypes.EQUIPPABLE) != null) {
         Registry registry2 = minecraftClient.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
         return a(registry2, Enchantments.PROTECTION, itemStack1) >= 5 && a(registry2, Enchantments.MENDING, itemStack1) > 0;
      } else {
         return false;
      }
   }

   private boolean g(ItemStack itemStack1) {
      if (minecraftClient.world != null && this.h(itemStack1)) {
         Registry registry2 = minecraftClient.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
         return a(registry2, Enchantments.PROTECTION, itemStack1) >= 5 && a(registry2, Enchantments.MENDING, itemStack1) > 0 && a(registry2, Enchantments.THORNS, itemStack1) > 0;
      } else {
         return false;
      }
   }

   private static int a(Registry<Enchantment> registry0, RegistryKey<Enchantment> registryKey1, ItemStack itemStack2) {
      return registry0.getOptional(registryKey1).map(reference1x -> EnchantmentHelper.getLevel(reference1x, itemStack2)).orElse(0);
   }

   private boolean h(ItemStack itemStack1) {
      return itemStack1.isOf(Items.NETHERITE_HELMET)
         || itemStack1.isOf(Items.NETHERITE_CHESTPLATE)
         || itemStack1.isOf(Items.NETHERITE_LEGGINGS)
         || itemStack1.isOf(Items.NETHERITE_BOOTS);
   }

   private List<TalismanIndication.a> i(ItemStack itemStack1) {
      AttributeModifiersComponent attributeModifiersComponent2 = (AttributeModifiersComponent)itemStack1.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
      if (attributeModifiersComponent2 == null) {
         return List.of();
      } else {
         ArrayList arrayList3 = new ArrayList();

         for (Entry entry5 : attributeModifiersComponent2.modifiers()) {
            if (entry5.slot().matches(EquipmentSlot.OFFHAND)) {
               arrayList3.add(new TalismanIndication.a(entry5.attribute(), this.a(entry5), entry5.modifier().value()));
            }
         }

         return arrayList3;
      }
   }

   private Operation a(Entry entry1) {
      return (entry1.attribute().equals(EntityAttributes.MOVEMENT_SPEED) || entry1.attribute().equals(EntityAttributes.ATTACK_SPEED))
            && entry1.modifier().operation() == Operation.ADD_MULTIPLIED_TOTAL
         ? Operation.ADD_MULTIPLIED_BASE
         : entry1.modifier().operation();
   }

   static record a(RegistryEntry<EntityAttribute> attribute, Operation operation, double value)  {



      @Override
      public boolean equals(Object object1) {
         if (this == object1) {
            return true;
         } else {
            return !(object1 instanceof TalismanIndication.a value2) ? false : this.attribute.equals(value2.attribute) && this.operation == value2.operation && Math.abs(this.value - value2.value) < 1.0E-6;
         }
      }

      @Override
      public int hashCode() {
         long longValue1 = Math.round(this.value * 1000000.0);
         int intValue3 = this.attribute.hashCode();
         intValue3 = 31 * intValue3 + this.operation.hashCode();
         return 31 * intValue3 + Long.hashCode(longValue1);
      }
   }

   static record TalismanEntry(Identifier prettyModel, Identifier simpleModel, List<TalismanIndication.a> attributes)  {



      Identifier a(boolean flag1) {
         return flag1 ? this.simpleModel : this.prettyModel;
      }

      boolean a(List<TalismanIndication.a> list1) {
         if (this.attributes.size() != list1.size()) {
            return false;
         } else {
            ArrayList arrayList2 = new ArrayList(list1);

            for (TalismanIndication.a value4 : this.attributes) {
               int intValue5 = arrayList2.indexOf(value4);
               if (intValue5 < 0) {
                  return false;
               }

               arrayList2.remove(intValue5);
            }

            return arrayList2.isEmpty();
         }
      }
   }
}
