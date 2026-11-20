package dev.leg1tka.modules.combat;


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
import java.util.function.Supplier;
public class ItemRelease extends Module {
   public static final ItemRelease c = new ItemRelease();
   private final MultiSetting d = new MultiSetting(
      "Предметы",
      new BooleanSetting("Лук", true),
      new BooleanSetting("Трезубец", false),
      new BooleanSetting("Арбалет", true)
   );
   private final NumberSetting e = new NumberSetting("Задержка выстрела", 2.5F, 2.0F, 5.0F, 0.05F)
      .a(createSupplier147(this));

   public ItemRelease() {
      super(
         "ItemRelease",
         "Автоматически выпускает предмет когда он полностью натянут",
         Category.COMBAT
      );
      this.addSettings(new Setting[]{this.d, this.e});
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player == null) return;
      if (minecraftClient.player.isUsingItem()) {
         net.minecraft.item.ItemStack stack = minecraftClient.player.getActiveItem();
         int useTime = minecraftClient.player.getItemUseTime();
         if (stack.isOf(net.minecraft.item.Items.BOW) && this.d.a("Лук")) {
            if (useTime >= (int)(this.e.e().floatValue() * 10)) {
               minecraftClient.interactionManager.stopUsingItem(minecraftClient.player);
            }
         } else if (stack.isOf(net.minecraft.item.Items.CROSSBOW) && this.d.a("Арбалет")) {
            if (useTime >= 25) {
               minecraftClient.interactionManager.stopUsingItem(minecraftClient.player);
            }
         } else if (stack.isOf(net.minecraft.item.Items.TRIDENT) && this.d.a("Трезубец")) {
            if (useTime >= 10) {
               minecraftClient.interactionManager.stopUsingItem(minecraftClient.player);
            }
         }
      }
   }

   private static Supplier createSupplier147(ItemRelease itemRelease0) {
      return () -> itemRelease0.d.a("Лук");
   }
   public static void dev_leg1tka_o_gt_initializer() {}
}
