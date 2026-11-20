package dev.leg1tka.modules.player;


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

import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventPopTotem;
import dev.leg1tka.api.events.implement.EventPotion;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class UzeTracker extends Module {
   public static final UzeTracker c = new UzeTracker();
   private final MultiSetting d = new MultiSetting(
      "Отслеживать использования",
      new BooleanSetting("Тотема", true),
      new BooleanSetting("Зелья", true),
      new BooleanSetting("Предмета", true)
   );
   private final MultiSetting e = new MultiSetting(
      "Уведомления",
      new BooleanSetting("Окончание силки", true),
      new BooleanSetting("Ломка брони", true)
   );
   private final BooleanSetting f = new BooleanSetting("Звук уведомлений", true);
   private static String g;
   private static final int[] h = new int[]{20, 10, 5};
   private static final int[] i = new int[]{30, 20, 10, 5};
   private static final EquipmentSlot[] j = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
   private static final String[] k = new String[]{
      "Шлем",
      "Нагрудник",
      "Поножи",
      "Ботинки"
   };
   private final Map l = new ConcurrentHashMap();
   private int m = -1;
   private final int[] n = new int[]{-1, -1, -1, -1};
   private final ItemStack[] o = new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
   private ItemStack p;

   public UzeTracker() {
      super(
         "Uze Tracker",
         "Отслеживает выбранные использования и уведомляет о них",
         Category.PLAYER
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f});
   }

   @Override
   public void onDisable() {
      this.l.clear();
      this.m = -1;

      for (int intValue1 = 0; intValue1 < 4; intValue1++) {
         this.n[intValue1] = -1;
         this.o[intValue1] = ItemStack.EMPTY;
      }

      super.onDisable();
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.world != null && minecraftClient.player != null) {
         if (this.e.a("Окончание силки")) {
            this.p();
         } else {
            this.m = -1;
         }

         if (this.e.a("Ломка брони")) {
            this.q();
         } else {
            Arrays.fill(this.n, -1);
         }

         if (this.d.a("Предмета")) {
            for (PlayerEntity playerEntity3 : minecraftClient.world.getPlayers()) {
               if (playerEntity3 != null && playerEntity3 != minecraftClient.player && playerEntity3.isAlive()) {
                  ItemStack itemStack4 = playerEntity3.getActiveItem();
                  if (!itemStack4.isEmpty()
                     && (itemStack4.getItem() instanceof PotionItem || itemStack4.get(DataComponentTypes.FOOD) != null || itemStack4.getItem() == Items.MILK_BUCKET)
                     && playerEntity3.getItemUseTimeLeft() == 1) {
                     long longValue5 = System.currentTimeMillis();
                     Long long7 = (Long)this.l.get(playerEntity3.getUuid());
                     if (long7 == null || longValue5 - long7 >= 1500L) {
                        this.l.put(playerEntity3.getUuid(), longValue5);
                        String string8 = itemStack4.getItem() instanceof PotionItem ? "§a" : "§c";
                        String string9 = itemStack4.getItem().getName().getString();
                        if (itemStack4.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
                           string9 = "Чарка";
                        }

                        dev.leg1tka.api.utils.text.ClientChat.a(
                           "UzeTracker",
                           playerEntity3.getName().getString() + " использовал \"" + string8 + string9 + "§7\""
                        );
                        dev.leg1tka.render.hud.NotificationQueue.a(playerEntity3.getName().getString() + " использовал " + string9, itemStack4.copy());
                     }
                  }
               }
            }
         }
      }
   }

   @EventLink
   public void onPotion(EventPotion eventPotion1) {
      if (minecraftClient.world != null && minecraftClient.player != null) {
         if (this.d.a("Зелья") && eventPotion1.getType() == EventPotion.Type.PARTICLES) {
            PotionEntity potionEntity2 = dev.leg1tka.api.utils.player.ItemCustomData.a(minecraftClient.world, eventPotion1.getPos());
            dev.leg1tka.api.utils.player.ItemCustomData.b value3 = dev.leg1tka.api.utils.player.ItemCustomData.a(potionEntity2, eventPotion1.getData());
            if (value3 != null) {
               Vec3d vec3d4 = potionEntity2 != null ? potionEntity2.getPos() : eventPotion1.getPos().toCenterPos();

               for (PlayerEntity playerEntity6 : minecraftClient.world.getPlayers()) {
                  if (playerEntity6 != minecraftClient.player && playerEntity6.isAlive()) {
                     double doubleValue7 = playerEntity6.squaredDistanceTo(vec3d4);
                     if (!(doubleValue7 >= 16.0)) {
                        double doubleValue9 = 1.0 - Math.sqrt(doubleValue7) / 4.0;
                        if (value3.a(doubleValue9) > 0) {
                           int intValue11 = (int)(doubleValue9 * 100.0);
                           String string12 = playerEntity6.getName().getString();
                           dev.leg1tka.api.utils.text.ClientChat.a(
                              "UzeTracker",
                              string12 + " получил эффекты от \"" + value3.a + "\""
                           );
                           dev.leg1tka.api.utils.text.ClientChat.a("UzeTracker", "- Успешность: §a" + intValue11 + "%");
                           dev.leg1tka.render.hud.NotificationQueue.a(string12 + " получил " + value3.a + " " + intValue11 + "%", value3.a());

                           for (dev.leg1tka.api.utils.player.ItemCustomData.c value14 : value3.f) {
                              int intValue15 = MathHelper.floor((double)value14.ticks() * doubleValue9 + 0.5);
                              if (intValue15 > 20) {
                                 int intValue16 = intValue15 / 20;
                                 int intValue17 = value14.amplifier();
                                 String string18 = intValue17 == 0 ? "I" : (intValue17 == 1 ? "II" : (intValue17 == 2 ? "III" : (intValue17 == 3 ? "IV" : String.valueOf(intValue17 + 1))));
                                 dev.leg1tka.api.utils.text.ClientChat.a(
                                    "UzeTracker",
                                    "- §c"
                                       + ((StatusEffect)value14.type().value()).getName().getString()
                                       + " "
                                       + string18
                                       + " §7("
                                       + intValue16 / 60
                                       + ":"
                                       + String.format("%02d", intValue16 % 60)
                                       + ")"
                                 );
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @EventLink
   public void onPopTotem(EventPopTotem eventPopTotem1) {
      if (minecraftClient.player != null) {
         if (this.d.a("Тотема")) {
            PlayerEntity playerEntity2 = eventPopTotem1.getPlayer();
            if (playerEntity2 != null) {
               ItemStack itemStack3 = playerEntity2.getMainHandStack().isOf(Items.TOTEM_OF_UNDYING)
                  ? playerEntity2.getMainHandStack()
                  : (playerEntity2.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING) ? playerEntity2.getOffHandStack() : null);
               ItemStack itemStack4 = itemStack3 != null ? itemStack3.copy() : new ItemStack(Items.TOTEM_OF_UNDYING);
               boolean flag5 = playerEntity2 == minecraftClient.player;
               String string6 = playerEntity2.getName().getString();
               minecraftClient.execute(
                  () -> {
                     String string3x = itemStack4.getName().getString();
                     boolean flag4x = a(itemStack4, string3x);
                     dev.leg1tka.api.utils.text.ClientChat.a(
                        "UzeTracker",
                        flag5
                           ? "Вы потеряли тотем"
                           : string6 + " потерял тотем"
                     );
                     dev.leg1tka.api.utils.text.ClientChat.a("UzeTracker", "- Снесено: §e" + string3x);
                     if (!flag5) {
                        dev.leg1tka.render.hud.NotificationQueue.a(
                           string6
                              + " потерял тотем | Зачарован: "
                              + (flag4x ? "да" : "нет"),
                           itemStack4
                        );
                     }
                  }
               );
            }
         }
      }
   }

   private static boolean a(ItemStack itemStack0, String string1) {
      ItemEnchantmentsComponent itemEnchantmentsComponent2 = (ItemEnchantmentsComponent)itemStack0.get(DataComponentTypes.ENCHANTMENTS);
      return itemEnchantmentsComponent2 != null && !itemEnchantmentsComponent2.isEmpty() || itemStack0.hasGlint() || string1.startsWith("Талисман");
   }

   private void p() {
      StatusEffectInstance statusEffectInstance1 = minecraftClient.player.getStatusEffect(StatusEffects.STRENGTH);
      if (statusEffectInstance1 != null && !statusEffectInstance1.isInfinite()) {
         int intValue2 = statusEffectInstance1.getDuration();
         int intValue3 = 0;

         while (intValue3 < h.length && intValue2 <= h[intValue3] * 20) {
            intValue3++;
         }

         if (this.m >= 0 && intValue3 > this.m) {
            if (this.p == null) {
               this.p = PotionContentsComponent.createStack(Items.POTION, Potions.STRENGTH);
            }

            this.a(
               "Сила закончится через "
                  + h[intValue3 - 1]
                  + " сек",
               this.p
            );
         }

         this.m = intValue3;
      } else {
         this.m = -1;
      }
   }

   private void q() {
      for (int intValue1 = 0; intValue1 < j.length; intValue1++) {
         ItemStack itemStack2 = minecraftClient.player.getEquippedStack(j[intValue1]);
         if (!itemStack2.isEmpty() && itemStack2.isDamageable()) {
            int intValue3 = itemStack2.getMaxDamage();
            float floatValue4 = (float)(intValue3 - itemStack2.getDamage()) * 100.0F / (float)intValue3;
            int intValue5 = 0;

            while (intValue5 < i.length && floatValue4 <= (float)i[intValue5]) {
               intValue5++;
            }

            boolean flag6 = this.n[intValue1] >= 0 && this.o[intValue1].getItem() == itemStack2.getItem();
            if (flag6 && intValue5 > this.n[intValue1]) {
               this.a(k[intValue1] + ": " + i[intValue5 - 1] + "% прочности", itemStack2.copy());
            }

            this.n[intValue1] = intValue5;
            this.o[intValue1] = itemStack2;
         } else {
            this.n[intValue1] = -1;
            this.o[intValue1] = ItemStack.EMPTY;
         }
      }
   }

   private void a(String string1, ItemStack itemStack2) {
      dev.leg1tka.api.utils.text.ClientChat.a("UzeTracker", string1);
      dev.leg1tka.render.hud.NotificationQueue.a(string1, itemStack2);
      if (this.f.getValue()) {
         dev.leg1tka.api.utils.sound.ClientSounds.a("enablealarm.wav", 1.0, 1.0F);
      }
   }
}
