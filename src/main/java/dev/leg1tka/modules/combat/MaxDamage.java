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

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventUpdate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
public class MaxDamage extends Module {
   public static final MaxDamage c = new MaxDamage();
   public final ModeSetting d = new ModeSetting(
      "Режим",
      "Пакетный",
      "Пакетный",
      "Легитный"
   );
   public int e = -1;
   public boolean f = false;
   public boolean g = false;
   private Entity h;

   public MaxDamage() {
      super("MaxDamage", "Максимальный урон", Category.COMBAT);
      this.addSettings(new Setting[]{this.d});
      EventInvoker.register(this);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.p();
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      this.p();
   }

   private void p() {
      if (this.e != -1) {
         if (this.g) {
            int intValue1 = this.e;
            Entity entity2 = this.h;
            this.e = -1;
            this.h = null;
            if (entity2 != null && minecraftClient.player != null) {
               minecraftClient.interactionManager.attackEntity(minecraftClient.player, entity2);
               minecraftClient.player.swingHand(Hand.MAIN_HAND);
            }

            this.b(intValue1);
            this.r();
         } else {
            this.b(this.e);
            this.r();
            this.e = -1;
         }
      }
   }

   public boolean a(Entity entity1) {
      if (!this.isEnabled() || minecraftClient.player == null || minecraftClient.interactionManager == null) {
         return false;
      } else if (this.e != -1) {
         return false;
      } else {
         int intValue2 = this.q();
         if (intValue2 == -1) {
            return false;
         } else {
            if (this.d.b("Пакетный")) {
               this.a(entity1, intValue2);
            } else {
               this.b(entity1, intValue2);
            }

            return true;
         }
      }
   }

   private void a(Entity entity1, int intValue2) {
      dev.leg1tka.api.utils.player.SprintInputController.b(createRunnable151(this, intValue2, entity1));
   }

   private void b(Entity entity1, int intValue2) {
      this.h = entity1;
      dev.leg1tka.api.utils.player.SprintInputController.b(createRunnable152(this, intValue2));
   }

   private int q() {
      if (minecraftClient.player == null) {
         return -1;
      } else {
         double doubleValue1 = this.a(minecraftClient.player.getOffHandStack());
         double doubleValue3 = doubleValue1;
         int intValue5 = -1;

         for (int intValue6 = 9; intValue6 < 45; intValue6++) {
            ItemStack itemStack7 = minecraftClient.player.playerScreenHandler.getSlot(intValue6).getStack();
            if (!itemStack7.isEmpty() && (itemStack7.getItem() == Items.PLAYER_HEAD || itemStack7.getItem() == Items.TOTEM_OF_UNDYING)) {
               double doubleValue8 = this.a(itemStack7);
               if (doubleValue8 > doubleValue3) {
                  doubleValue3 = doubleValue8;
                  intValue5 = intValue6;
               }
            }
         }

         return intValue5;
      }
   }

   private double a(ItemStack itemStack1) {
      if (itemStack1 != null && !itemStack1.isEmpty()) {
         AttributeModifiersComponent attributeModifiersComponent2 = (AttributeModifiersComponent)itemStack1.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
         if (attributeModifiersComponent2 == null) {
            return 0.0;
         } else {
            double doubleValue3 = 0.0;

            for (Entry entry6 : attributeModifiersComponent2.modifiers()) {
               RegistryEntry registryEntry7 = entry6.attribute();
               if (registryEntry7 != null && registryEntry7.matches(EntityAttributes.ATTACK_DAMAGE)) {
                  doubleValue3 += entry6.modifier().value();
               }
            }

            return doubleValue3;
         }
      } else {
         return 0.0;
      }
   }

   private void b(int intValue1) {
      minecraftClient.interactionManager.clickSlot(0, intValue1, 40, SlotActionType.SWAP, minecraftClient.player);
   }

   private void r() {
      minecraftClient.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(0));
   }



   private static Runnable createRunnable151(MaxDamage maxDamage0, int intValue1, Entity entity2) {
      return () -> {
         maxDamage0.b(intValue1);
         minecraftClient.interactionManager.attackEntity(minecraftClient.player, entity2);
         minecraftClient.player.swingHand(Hand.MAIN_HAND);
         maxDamage0.e = intValue1;
         maxDamage0.g = false;
      };
   }

   private static Runnable createRunnable152(MaxDamage maxDamage0, int intValue1) {
      return () -> {
         maxDamage0.b(intValue1);
         maxDamage0.e = intValue1;
         maxDamage0.g = true;
      };
   }
   public static void dev_leg1tka_o_gw_initializer() {}
}
