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

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventBinding;
import dev.leg1tka.api.events.implement.EventMoveInput;
import dev.leg1tka.api.events.implement.EventUpdate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.PlayerInput;
public class AutoSwap extends Module {
   public static final AutoSwap c = new AutoSwap();
   private final ModeSetting d = new ModeSetting(
      "Первый предмет",
      "Руна",
      "Руна",
      "Тотем",
      "Шар",
      "Гэпл",
      "Щит"
   );
   private final ModeSetting e = new ModeSetting(
      "Второй предмет",
      "Тотем",
      "Руна",
      "Тотем",
      "Шар",
      "Гэпл",
      "Щит"
   );
   private final dev.leg1tka.settings.KeybindSetting f = new dev.leg1tka.settings.KeybindSetting("Бинд свапа", -98);
   private final BooleanSetting g = new BooleanSetting("Обход Grim", true);
   private final BooleanSetting h = new BooleanSetting("Свапать талисманы", false);
   private int i;
   private boolean j;
   private int k;
   private int l = -1;
   private boolean m = false;

   public AutoSwap() {
      super(
         "AutoSwap",
         "Быстрая смена предметов в офф-хенде",
         Category.COMBAT
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.h, this.g});
   }

   @Override
   public void onEnable() {
      this.m = false;
      this.l = -1;
      this.i = 0;
      this.k = 0;
      super.onEnable();
   }

   @EventLink
   public void onBinding(EventBinding eventBinding1) {
      if (this.f.getKey() == eventBinding1.getKey()) {
         this.performSwap();
      }
   }

   @EventLink
   public void onInput(EventMoveInput eventMoveInput1) {
      if (this.j) {
         eventMoveInput1.setForward(0.0F);
         eventMoveInput1.setStrafe(0.0F);
      }
   }

   public void onUpdate(EventUpdate eventUpdate1) {
   }



   public void performSwap() {
      if (minecraftClient.player == null) return;
      ItemStack offhand = minecraftClient.player.getOffHandStack();
      Item firstItem = this.c(this.d.g());
      Item secondItem = this.c(this.e.g());
      Item targetItem = (offhand.getItem() == firstItem) ? secondItem : firstItem;
      int slot = this.a(targetItem);
      if (slot != -1) {
         if (this.g.getValue()) {
            this.q();
         }
         this.b(slot);
         minecraftClient.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(0));
         if (this.g.getValue()) {
            this.r();
         }
      }
   }

   private void p() {
      if (this.l != -1) {
         this.b(this.l);
         minecraftClient.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(0));
         this.l = -1;
      }
   }

   private void b(int intValue1) {
      minecraftClient.interactionManager.clickSlot(0, intValue1, 40, SlotActionType.SWAP, minecraftClient.player);
   }

   private int a(Item item1) {
      if (!this.h.getValue()) {
         for (int intValue7 = 9; intValue7 < 45; intValue7++) {
            ItemStack itemStack8 = minecraftClient.player.playerScreenHandler.getSlot(intValue7).getStack();
            if (itemStack8.getItem() == item1) {
               return intValue7;
            }
         }

         return -1;
      } else {
         int intValue2 = -1;
         boolean flag3 = false;

         for (int intValue4 = 9; intValue4 < 45; intValue4++) {
            ItemStack itemStack5 = minecraftClient.player.playerScreenHandler.getSlot(intValue4).getStack();
            if (itemStack5.getItem() == item1) {
               if (item1 == Items.PLAYER_HEAD) {
                  AttributeModifiersComponent attributeModifiersComponent6 = (AttributeModifiersComponent)itemStack5.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
                  if (attributeModifiersComponent6 == null || attributeModifiersComponent6.modifiers().isEmpty()) {
                     continue;
                  }
               }

               boolean flag9 = itemStack5.hasEnchantments();
               if (intValue2 == -1 || flag9 && !flag3) {
                  intValue2 = intValue4;
                  flag3 = flag9;
               }
            }
         }

         return intValue2;
      }
   }

   private Item c(String string1) {
      return switch (string1) {
         case "Руна" -> Items.FIREWORK_STAR;
         case "Тотем" -> Items.TOTEM_OF_UNDYING;
         case "Шар" -> Items.PLAYER_HEAD;
         case "Гепл" -> Items.GOLDEN_APPLE;
         case "Гэпл" -> Items.GOLDEN_APPLE;
         case "Щит" -> Items.SHIELD;
         default -> Items.AIR;
      };
   }

   private void q() {
      if (!this.j) {
         this.j = true;
         Sprint.b(1000);
         minecraftClient.player.setSprinting(false);
         minecraftClient.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(new PlayerInput(false, false, false, false, false, false, false)));
         if (AspectDLC.INSTANCE.serverStorage.i()) {
            minecraftClient.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.STOP_SPRINTING));
         }
      }
   }

   private void r() {
      if (this.j) {
         this.j = false;
         Sprint.p();
         minecraftClient.getNetworkHandler().sendPacket(new PlayerInputC2SPacket(minecraftClient.player.input.playerInput));
      }
   }

   @Override
   public void onDisable() {
      this.i = 0;
      this.k = 0;
      this.m = false;
      this.l = -1;
      this.r();
      super.onDisable();
   }



   private static Runnable createRunnable92(AutoSwap autoSwap0) {
      return autoSwap0::p;
   }
   public static void dev_leg1tka_o_gl_initializer() {}
}
