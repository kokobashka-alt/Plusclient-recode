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

import dev.leg1tka.api.events.Event;
import dev.leg1tka.api.events.EventLink;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
public class PacketMace extends Module {
   public static final PacketMace c = new PacketMace();
   private final BooleanSetting d = new BooleanSetting("Только на земле", true);
   private final NumberSetting e = new NumberSetting(
      "Задержка переключения", 50.0F, 0.0F, 200.0F, 1.0F
   );
   private boolean f = false;
   private long g = 0L;

   public PacketMace() {
      super(
         "PacketMace",
         "Автоматически бьет булавой и возвращает предмет",
         Category.COMBAT
      );
      this.addSettings(new Setting[]{this.d, this.e});
   }

   @EventLink(
      priority = 100
   )
   public void onEvent(dev.leg1tka.api.events.Event event1) {
      if (event1 instanceof dev.leg1tka.api.events.implement.EventAttackEntity attackEntity) {
         this.onAttack(attackEntity);
      }
   }

   public void onAttack(dev.leg1tka.api.events.implement.EventAttackEntity eventAttackEntity1) {
      if (minecraftClient.player == null) return;
      if (this.d.getValue() && !minecraftClient.player.isOnGround()) return;
      if (minecraftClient.player.getMainHandStack().isOf(net.minecraft.item.Items.MACE)) return;
      int maceSlot = this.a(net.minecraft.item.Items.MACE);
      if (maceSlot != -1 && eventAttackEntity1.getTarget() != null) {
         int prevSlot = minecraftClient.player.getInventory().selectedSlot;
         minecraftClient.player.getInventory().selectedSlot = maceSlot;
         minecraftClient.interactionManager.attackEntity(minecraftClient.player, eventAttackEntity1.getTarget());
         minecraftClient.player.swingHand(net.minecraft.util.Hand.MAIN_HAND);
         minecraftClient.player.getInventory().selectedSlot = prevSlot;
      }
   }

   private int a(Item item1) {
      for (int intValue2 = 0; intValue2 < 9; intValue2++) {
         ItemStack itemStack3 = minecraftClient.player.getInventory().getStack(intValue2);
         if (itemStack3.getItem() == item1) {
            return intValue2;
         }
      }

      return -1;
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.f = false;
      this.g = 0L;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.f = false;
   }



      public static void dev_leg1tka_o_ha_initializer() {}
}
