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
import java.util.List;
import java.util.Locale;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.PlayerInput;

public class ServerHelper extends Module {
   public static ServerHelper c = new ServerHelper();
   private final ModeSetting d = new ModeSetting("Режим", "Lony", "Lony", "Spooky");
   private final BooleanSetting e = new BooleanSetting("Обходить Grim", true);
   private final dev.leg1tka.settings.KeybindSetting f = new dev.leg1tka.settings.KeybindSetting("Перышко", -1).visibleWhen(() -> this.d.b("Lony"));
   private final dev.leg1tka.settings.KeybindSetting g = new dev.leg1tka.settings.KeybindSetting("Ливалка", -1).visibleWhen(() -> this.d.b("Lony"));
   private final dev.leg1tka.settings.KeybindSetting h = new dev.leg1tka.settings.KeybindSetting("Трапка", -1).visibleWhen(() -> this.d.b("Lony"));
   private final dev.leg1tka.settings.KeybindSetting i = new dev.leg1tka.settings.KeybindSetting("Ливалка с платформой", -1)
      .visibleWhen(() -> this.d.b("Lony"));
   private final dev.leg1tka.settings.KeybindSetting j = new dev.leg1tka.settings.KeybindSetting("Дезориентация", -1).visibleWhen(() -> this.d.b("Spooky"));
   private final dev.leg1tka.settings.KeybindSetting k = new dev.leg1tka.settings.KeybindSetting("Трапка", -1).visibleWhen(() -> this.d.b("Spooky"));
   private final dev.leg1tka.settings.KeybindSetting l = new dev.leg1tka.settings.KeybindSetting("Пласт", -1).visibleWhen(() -> this.d.b("Spooky"));
   private final dev.leg1tka.settings.KeybindSetting m = new dev.leg1tka.settings.KeybindSetting("Явная пыль", -1).visibleWhen(() -> this.d.b("Spooky"));
   private final dev.leg1tka.settings.KeybindSetting n = new dev.leg1tka.settings.KeybindSetting("Снег заморозки", -1).visibleWhen(() -> this.d.b("Spooky"));
   private final dev.leg1tka.settings.KeybindSetting o = new dev.leg1tka.settings.KeybindSetting("Божья аура", -1).visibleWhen(() -> this.d.b("Spooky"));
   private Item p;
   private ServerAction q;

   public ServerHelper() {
      super("ServerHelper", "Помощник для серверов", Category.MISC);
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o});
      this.d(true);
   }

   public boolean p() {
      return this.d.b("Spooky");
   }

   public boolean q() {
      return this.d.b("Lony");
   }

   public List<HelperItem> r() {
      return List.of(
         new HelperItem("Перышко", Items.FEATHER, this.f),
         new HelperItem("Ливалка", Items.MAGMA_CREAM, this.g),
         new HelperItem("Трапка", Items.CRYING_OBSIDIAN, this.h),
         new HelperItem("Ливалка с платформой", Items.CLAY, this.i)
      );
   }

   public List<HelperItem> s() {
      return List.of(
         new HelperItem("Дезориентация", Items.ENDER_EYE, this.j),
         new HelperItem("Трапка", Items.NETHERITE_SCRAP, this.k),
         new HelperItem("Пласт", Items.DRIED_KELP, this.l),
         new HelperItem("Явная пыль", Items.SUGAR, this.m),
         new HelperItem("Снег заморозки", Items.SNOWBALL, this.n),
         new HelperItem("Божья аура", Items.PHANTOM_MEMBRANE, this.o)
      );
   }

   @Override
   public void onEnable() {
      this.p = null;
      this.q = null;
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.p = null;
      this.q = null;
      super.onDisable();
   }

   @EventLink
   public void onBinding(EventBinding eventBinding1) {
      if (minecraftClient.currentScreen == null) {
         if (this.d.b("Lony")) {
            if (eventBinding1.getKey() == this.f.getKey()) {
               this.p = Items.FEATHER;
            } else if (eventBinding1.getKey() == this.g.getKey()) {
               this.p = Items.MAGMA_CREAM;
            } else if (eventBinding1.getKey() == this.h.getKey()) {
               this.p = Items.CRYING_OBSIDIAN;
            } else if (eventBinding1.getKey() == this.i.getKey()) {
               this.p = Items.CLAY_BALL;
            }
         } else {
            if (eventBinding1.getKey() == this.j.getKey()) {
               this.q = ServerAction.DISORIENTATION;
            } else if (eventBinding1.getKey() == this.k.getKey()) {
               this.q = ServerAction.TRAP;
            } else if (eventBinding1.getKey() == this.l.getKey()) {
               this.q = ServerAction.PLAST;
            } else if (eventBinding1.getKey() == this.m.getKey()) {
               this.q = ServerAction.DUST;
            } else if (eventBinding1.getKey() == this.n.getKey()) {
               this.q = ServerAction.FREEZE_SNOW;
            } else if (eventBinding1.getKey() == this.o.getKey()) {
               this.q = ServerAction.AURA;
            }
         }
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player == null || minecraftClient.world == null) {
         this.p = null;
         this.q = null;
      } else if (this.d.b("Lony")) {
         if (this.p != null) {
            Item item3 = this.p;
            if (this.e.getValue()) {
               dev.leg1tka.api.utils.player.SprintInputController.a(() -> dev.leg1tka.api.utils.player.InventorySearch.c(item3));
            } else {
               dev.leg1tka.api.utils.player.InventorySearch.c(item3);
            }

            this.p = null;
         }
      } else if (this.q != null && minecraftClient.interactionManager != null) {
         ServerAction serverAction2 = this.q;
         if (this.e.getValue()) {
            dev.leg1tka.api.utils.player.SprintInputController.a(() -> this.a(serverAction2));
         } else {
            this.a(serverAction2);
         }

         this.q = null;
      }
   }

   private void a(ServerAction serverAction1) {
      if (minecraftClient.player.getItemCooldownManager().isCoolingDown(new ItemStack(serverAction1.c))) {
         dev.leg1tka.api.utils.text.ClientChat.a("У предмета " + serverAction1.b + " есть кд");
      } else if (this.a(minecraftClient.player.getMainHandStack(), serverAction1)) {
         minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
         minecraftClient.player.swingHand(Hand.MAIN_HAND);
         dev.leg1tka.api.utils.text.ClientChat.a(serverAction1.d);
      } else if (this.a(minecraftClient.player.getOffHandStack(), serverAction1)) {
         minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.OFF_HAND);
         minecraftClient.player.swingHand(Hand.OFF_HAND);
         dev.leg1tka.api.utils.text.ClientChat.a(serverAction1.d);
      } else {
         int intValue2 = this.a(serverAction1, 0, 8);
         if (intValue2 != -1) {
            int intValue4 = minecraftClient.player.getInventory().selectedSlot;
            minecraftClient.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(intValue2));
            minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
            minecraftClient.player.swingHand(Hand.MAIN_HAND);
            minecraftClient.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(intValue4));
            dev.leg1tka.api.utils.text.ClientChat.a(serverAction1.d);
         } else {
            int intValue3 = this.a(serverAction1, 9, 35);
            if (intValue3 != -1) {
               this.b(intValue3);
               dev.leg1tka.api.utils.text.ClientChat.a(serverAction1.d);
            } else {
               dev.leg1tka.api.utils.text.ClientChat.a(serverAction1.e);
            }
         }
      }
   }

   private void b(int intValue1) {
      int intValue2 = minecraftClient.player.getInventory().selectedSlot;
      int intValue3 = this.t();
      boolean flag4 = false;
      if (minecraftClient.player.isSprinting()) {
         minecraftClient.player.networkHandler.sendPacket(new PlayerInputC2SPacket(new PlayerInput(false, false, false, false, false, false, false)));
         minecraftClient.player.setSprinting(false);
         minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.STOP_SPRINTING));
         if (!ModuleManager.au.isEnabled()) {
            minecraftClient.options.sprintKey.setPressed(false);
         }

         flag4 = true;
      }

      minecraftClient.interactionManager.clickSlot(0, intValue1, intValue3, SlotActionType.SWAP, minecraftClient.player);
      minecraftClient.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(0));
      minecraftClient.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(intValue3));
      minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.MAIN_HAND);
      minecraftClient.player.swingHand(Hand.MAIN_HAND);
      minecraftClient.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(intValue2));
      minecraftClient.interactionManager.clickSlot(0, intValue1, intValue3, SlotActionType.SWAP, minecraftClient.player);
      minecraftClient.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(0));
      if (flag4) {
         minecraftClient.player.networkHandler.sendPacket(new PlayerInputC2SPacket(minecraftClient.player.input.playerInput));
      }
   }

   private int t() {
      int intValue1 = 8;

      for (int intValue2 = 0; intValue2 < 9; intValue2++) {
         if (intValue2 != minecraftClient.player.getInventory().selectedSlot) {
            ItemStack itemStack3 = minecraftClient.player.getInventory().getStack(intValue2);
            if (itemStack3.isEmpty()) {
               return intValue2;
            }

            if (itemStack3.getUseAction() == UseAction.NONE) {
               intValue1 = intValue2;
            }
         }
      }

      return intValue1;
   }

   private int a(ServerAction serverAction1, int intValue2, int intValue3) {
      for (int intValue4 = intValue2; intValue4 <= intValue3; intValue4++) {
         if (this.a(minecraftClient.player.getInventory().getStack(intValue4), serverAction1)) {
            return intValue4;
         }
      }

      return -1;
   }

   private boolean a(ItemStack itemStack1, ServerAction serverAction2) {
      return itemStack1 != null && !itemStack1.isEmpty() && itemStack1.getItem() == serverAction2.c ? itemStack1.getName().getString().toLowerCase(Locale.ROOT).contains(serverAction2.a) : false;
   }

   static enum ServerAction {
      DISORIENTATION(
         "дезориентация",
         "дезориентации",
         Items.ENDER_EYE,
         "Использовал дезориентацию!",
         "Дезориентация не найдена!"
      ),
      TRAP(
         "трапка",
         "трапки",
         Items.NETHERITE_SCRAP,
         "Использовал трапку!",
         "Трапка не найдена!"
      ),
      PLAST(
         "пласт",
         "пласта",
         Items.DRIED_KELP,
         "Использовал пласт!",
         "Пласт не найден!"
      ),
      DUST(
         "явная пыль",
         "пыли",
         Items.SUGAR,
         "Использовал пыль!",
         "Пыль не найдена!"
      ),
      FREEZE_SNOW(
         "заморозка",
         "снега",
         Items.SNOWBALL,
         "Использовал снег!",
         "Снег не найден!"
      ),
      AURA(
         "божья",
         "ауры",
         Items.PHANTOM_MEMBRANE,
         "Использовал ауру!",
         "Аура не найдена!"
      );

      final String a;
      final String b;
      final Item c;
      final String d;
      final String e;

      private ServerAction(String string3, String string4, Item item5, String string6, String string7) {
         this.a = string3;
         this.b = string4;
         this.c = item5;
         this.d = string6;
         this.e = string7;
      }
   }

   public static record HelperItem(String name, Item item, dev.leg1tka.settings.KeybindSetting bind)  {


}
}
