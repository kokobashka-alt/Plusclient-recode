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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.HopperScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;

public class ChestStealer extends Module {
   public static ChestStealer c = new ChestStealer();
   private final NumberSetting d = new NumberSetting("Задержка", 100.0F, 0.0F, 1000.0F, 1.0F);
   private final BooleanSetting e = new BooleanSetting("Рандомизация", false);
   private long f = 0L;

   public ChestStealer() {
      super(
         "ChestStealer",
         "Автоматически открывает сундуки и забирает из них предметы",
         Category.PLAYER
      );
      this.addSettings(new Setting[]{this.d, this.e});
   }

   @EventLink
   private void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.interactionManager != null) {
         ScreenHandler screenHandler2 = minecraftClient.player.currentScreenHandler;
         if (screenHandler2 != null && screenHandler2 != minecraftClient.player.playerScreenHandler) {
            if (screenHandler2 instanceof GenericContainerScreenHandler || screenHandler2 instanceof HopperScreenHandler) {
               long longValue3 = System.currentTimeMillis();
               long longValue5 = (long)this.d.f();
               if (longValue3 - this.f >= longValue5) {
                  DefaultedList<Slot> defaultedList7 = screenHandler2.slots;
                  this.a(defaultedList7, screenHandler2).ifPresent(slot4 -> {
                     if (minecraftClient.player.currentScreenHandler == screenHandler2) {
                        minecraftClient.interactionManager.clickSlot(screenHandler2.syncId, slot4.id, 0, SlotActionType.QUICK_MOVE, minecraftClient.player);
                        this.f = longValue3;
                     }
                  });
               }
            }
         }
      }
   }

   private Optional<Slot> a(List<Slot> list1, ScreenHandler screenHandler2) {
      int intValue3 = this.a(screenHandler2);
      if (intValue3 > 0 && intValue3 <= list1.size()) {
         List<Slot> list4 = list1.subList(0, intValue3);
         ArrayList<Slot> arrayList5 = new ArrayList<>();

         for (Slot slot7 : list4) {
            if (slot7.hasStack() && !slot7.getStack().isEmpty() && !minecraftClient.player.getItemCooldownManager().isCoolingDown(slot7.getStack())) {
               arrayList5.add(slot7);
            }
         }

         if (arrayList5.isEmpty()) {
            return Optional.empty();
         } else if (this.e.getValue()) {
            int intValue8 = ThreadLocalRandom.current().nextInt(arrayList5.size());
            return Optional.of((Slot)arrayList5.get(intValue8));
         } else {
            return Optional.of((Slot)arrayList5.get(0));
         }
      } else {
         return Optional.empty();
      }
   }

   private int a(ScreenHandler screenHandler1) {
      if (screenHandler1 instanceof GenericContainerScreenHandler genericContainerScreenHandler2) {
         Inventory inventory3 = genericContainerScreenHandler2.getInventory();
         return inventory3.size();
      } else {
         return screenHandler1 instanceof HopperScreenHandler ? 5 : 0;
      }
   }

   @Override
   public void onDisable() {
      this.f = 0L;
      super.onDisable();
   }
}
