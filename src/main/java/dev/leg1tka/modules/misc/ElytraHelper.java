package dev.leg1tka.modules.misc;


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
import dev.leg1tka.api.events.implement.EventBinding;
import dev.leg1tka.api.events.implement.EventUpdate;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Formatting;
public class ElytraHelper extends Module {
   public static final ElytraHelper c = new ElytraHelper();
   private final ModeSetting d = new ModeSetting("Мод", "Vanilla", "Vanilla", "Grim", "Polar");
   private final dev.leg1tka.settings.KeybindSetting e = new dev.leg1tka.settings.KeybindSetting("Кнопка свапа", -1);
   private final dev.leg1tka.settings.KeybindSetting f = new dev.leg1tka.settings.KeybindSetting("Кнопка феерверка", -1);
   private final ModeSetting g = new ModeSetting(
      "Мод пуска феера",
      "Обычный",
      "Обычный",
      "Легитный",
      "WellMine"
   );
   private final BooleanSetting h = new BooleanSetting("Автовзлёт", true);
   private boolean i;
   private boolean j;
   private int k;

   public ElytraHelper() {
      super("ElytraHelper", "Elytra Helper", Category.MISC);
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.k = 0;
      this.j = false;
      this.i = false;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.k = 0;
      this.j = false;
      this.i = false;
   }

   @EventLink
   public void onKey(EventBinding eventBinding1) {
      if (minecraftClient.player == null) return;
      if (eventBinding1.getKey() == this.e.getKey()) {
         boolean wearingChestplate = minecraftClient.player.getEquippedStack(net.minecraft.entity.EquipmentSlot.CHEST).isOf(net.minecraft.item.Items.ELYTRA);
         this.a(this.d, wearingChestplate);
      } else if (eventBinding1.getKey() == this.f.getKey()) {
         dev.leg1tka.api.utils.player.InventorySearch.c(net.minecraft.item.Items.FIREWORK_ROCKET);
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player == null) return;
      if (this.h.getValue() && minecraftClient.player.getEquippedStack(net.minecraft.entity.EquipmentSlot.CHEST).isOf(net.minecraft.item.Items.ELYTRA)) {
         if (!minecraftClient.player.isOnGround() && !minecraftClient.player.isGliding() && minecraftClient.options.jumpKey.isPressed()) {
            minecraftClient.player.networkHandler.sendPacket(new net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket(minecraftClient.player, net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode.START_FALL_FLYING));
         }
      }
   }

   public void a(ModeSetting modeSetting1, boolean flag2) {
      String string3 = modeSetting1.g();
      switch (string3) {
         case "Vanilla":
            this.f(flag2);
            break;
         case "Grim":
            this.g(flag2);
            break;
         case "Polar":
            this.h(flag2);
      }
   }

   private void f(boolean flag1) {
      int intValue2 = flag1 ? dev.leg1tka.api.utils.player.InventorySearch.b() : dev.leg1tka.api.utils.player.InventorySearch.a();
      if (intValue2 == -1) {
         String string3 = createString159(String.valueOf(Formatting.GRAY));
         if (flag1) {
            string3 = createString160(String.valueOf(Formatting.GRAY));
         }

         dev.leg1tka.api.utils.text.ClientChat.a(string3);
      } else {
         if (intValue2 >= 0 && intValue2 <= 8) {
            minecraftClient.interactionManager.clickSlot(0, 6, intValue2, SlotActionType.SWAP, minecraftClient.player);
         } else if (intValue2 >= 8 && intValue2 <= 45) {
            minecraftClient.interactionManager.clickSlot(0, intValue2, 8, SlotActionType.SWAP, minecraftClient.player);
            minecraftClient.interactionManager.clickSlot(0, 6, 8, SlotActionType.SWAP, minecraftClient.player);
            minecraftClient.interactionManager.clickSlot(0, intValue2, 8, SlotActionType.SWAP, minecraftClient.player);
         }
      }
   }

   public void e(boolean flag1) {
      this.g(flag1);
   }

   private void g(boolean flag1) {
      int intValue2 = flag1 ? dev.leg1tka.api.utils.player.InventorySearch.b() : dev.leg1tka.api.utils.player.InventorySearch.a();
      if (intValue2 == -1) {
         String string3 = createString161(String.valueOf(Formatting.GRAY));
         if (flag1) {
            string3 = createString162(String.valueOf(Formatting.GRAY));
         }

         dev.leg1tka.api.utils.text.ClientChat.a(string3);
      } else {
         if (intValue2 >= 0 && intValue2 <= 8) {
            dev.leg1tka.api.utils.player.InventorySearch.a(createRunnable163(intValue2));
         } else if (intValue2 >= 8 && intValue2 <= 45) {
            dev.leg1tka.api.utils.player.InventorySearch.a(createRunnable164(intValue2));
         }
      }
   }

   private void h(boolean flag1) {
      int intValue2 = flag1 ? dev.leg1tka.api.utils.player.InventorySearch.b() : dev.leg1tka.api.utils.player.InventorySearch.a();
      if (intValue2 == -1) {
         String string3 = createString165(String.valueOf(Formatting.GRAY));
         if (flag1) {
            string3 = createString166(String.valueOf(Formatting.GRAY));
         }

         dev.leg1tka.api.utils.text.ClientChat.a(string3);
      } else {
         if (intValue2 >= 0 && intValue2 <= 8) {
            dev.leg1tka.api.utils.player.InventorySearch.b(createRunnable167(intValue2));
         } else if (intValue2 >= 8 && intValue2 <= 45) {
            dev.leg1tka.api.utils.player.InventorySearch.b(createRunnable168(intValue2));
         }
      }
   }



   private static String createString159(String string0) {
      return "Элитра"
         + string0
         + " не найдена в инвентаре";
   }

   private static String createString160(String string0) {
      return "Нагрудник"
         + string0
         + " не найден в инвентаре";
   }

   private static String createString161(String string0) {
      return "Элитра"
         + string0
         + " не найдена в инвентаре";
   }

   private static String createString162(String string0) {
      return "Нагрудник"
         + string0
         + " не найден в инвентаре";
   }

   private static Runnable createRunnable163(int intValue0) {
      return () -> minecraftClient.interactionManager.clickSlot(0, 6, intValue0, SlotActionType.SWAP, minecraftClient.player);
   }

   private static Runnable createRunnable164(int intValue0) {
      return () -> {
         minecraftClient.interactionManager.clickSlot(0, intValue0, 8, SlotActionType.SWAP, minecraftClient.player);
         minecraftClient.interactionManager.clickSlot(0, 6, 8, SlotActionType.SWAP, minecraftClient.player);
         minecraftClient.interactionManager.clickSlot(0, intValue0, 8, SlotActionType.SWAP, minecraftClient.player);
      };
   }

   private static String createString165(String string0) {
      return "Элитра"
         + string0
         + " не найдена в инвентаре";
   }

   private static String createString166(String string0) {
      return "Нагрудник"
         + string0
         + " не найден в инвентаре";
   }

   private static Runnable createRunnable167(int intValue0) {
      return () -> minecraftClient.interactionManager.clickSlot(0, 6, intValue0, SlotActionType.SWAP, minecraftClient.player);
   }

   private static Runnable createRunnable168(int intValue0) {
      return () -> {
         minecraftClient.interactionManager.clickSlot(0, intValue0, 8, SlotActionType.SWAP, minecraftClient.player);
         minecraftClient.interactionManager.clickSlot(0, 6, 8, SlotActionType.SWAP, minecraftClient.player);
         minecraftClient.interactionManager.clickSlot(0, intValue0, 8, SlotActionType.SWAP, minecraftClient.player);
      };
   }
   public static void dev_leg1tka_o_ij_initializer() {}
}
