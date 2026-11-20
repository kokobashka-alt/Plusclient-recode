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
import dev.leg1tka.api.events.implement.EventUpdate;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;

public class AutoArmor extends Module {
   public static AutoArmor c = new AutoArmor();
   private final NumberSetting d = new NumberSetting("Задержка", 25.0F, 1.0F, 1000.0F, 1.0F);
   private long e = 0L;

   public AutoArmor() {
      super(
         "AutoArmor",
         "Автоматически одевает броню",
         Category.PLAYER
      );
      this.addSettings(new Setting[]{this.d});
   }

   @EventLink
   public void onEvent(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         if (!this.p()) {
            long longValue2 = System.currentTimeMillis();
            if (!((float)(longValue2 - this.e) < this.d.f())) {
               for (int intValue4 = 0; intValue4 < 4; intValue4++) {
                  ItemStack itemStack5 = minecraftClient.player.getInventory().getArmorStack(intValue4);
                  ItemStack itemStack6 = null;
                  int intValue7 = -1;
                  float floatValue8 = this.a(itemStack5);

                  for (int intValue9 = 0; intValue9 < 36; intValue9++) {
                     ItemStack itemStack10 = minecraftClient.player.getInventory().getStack(intValue9);
                     if (!itemStack10.isEmpty()) {
                        Item item12 = itemStack10.getItem();
                        if (item12 instanceof ArmorItem) {
                           ArmorItem armorItem11 = (ArmorItem)item12;
                           if (this.b(armorItem11) == intValue4) {
                              float floatValue15 = this.a(itemStack10);
                              if ((itemStack5.isEmpty() || floatValue15 > floatValue8) && (itemStack6 == null || floatValue15 > this.a(itemStack6))) {
                                 itemStack6 = itemStack10;
                                 intValue7 = intValue9;
                              }
                           }
                        }
                     }
                  }

                  if (intValue7 != -1) {
                     int intValue13 = intValue7 < 9 ? intValue7 + 36 : intValue7;
                     if (!itemStack5.isEmpty()) {
                        int intValue14 = 8 - intValue4;
                        minecraftClient.interactionManager.clickSlot(0, intValue14, 0, SlotActionType.QUICK_MOVE, minecraftClient.player);
                        this.e = longValue2;
                        return;
                     }

                     minecraftClient.interactionManager.clickSlot(0, intValue13, 0, SlotActionType.QUICK_MOVE, minecraftClient.player);
                     this.e = longValue2;
                     return;
                  }
               }
            }
         }
      }
   }

   private float a(ItemStack itemStack1) {
      if (!itemStack1.isEmpty() && itemStack1.getItem() instanceof ArmorItem armorItem2) {
         float floatValue5 = this.a(armorItem2);
         int intValue4 = dev.leg1tka.api.utils.player.InventorySearch.a(itemStack1, Enchantments.PROTECTION);
         return floatValue5 + (float)intValue4 * 5.0F;
      } else {
         return 0.0F;
      }
   }

   private float a(ArmorItem armorItem1) {
      String string2 = armorItem1.toString().toLowerCase();
      if (string2.contains("netherite")) {
         return 50.0F;
      } else if (string2.contains("diamond")) {
         return 40.0F;
      } else if (string2.contains("iron")) {
         return 30.0F;
      } else if (string2.contains("golden") || string2.contains("gold")) {
         return 20.0F;
      } else if (string2.contains("chainmail")) {
         return 20.0F;
      } else {
         return string2.contains("leather") ? 10.0F : 0.0F;
      }
   }

   private boolean p() {
      return minecraftClient.player.input.movementForward != 0.0F || minecraftClient.player.input.movementSideways != 0.0F;
   }

   private int b(ArmorItem armorItem1) {
      String string2 = armorItem1.toString().toLowerCase();
      if (string2.contains("helmet") || string2.contains("skull")) {
         return 3;
      } else if (string2.contains("chestplate") || string2.contains("tunic")) {
         return 2;
      } else if (string2.contains("leggings") || string2.contains("pants")) {
         return 1;
      } else {
         return !string2.contains("boots") && !string2.contains("shoes") ? 0 : 0;
      }
   }
}
