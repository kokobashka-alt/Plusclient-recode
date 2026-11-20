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
import dev.leg1tka.mixin.IMinecraftClientAccessor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class FastExp extends Module {
   public static FastExp c = new FastExp();

   public FastExp() {
      super(
         "FastExp",
         "Позволяет бросать пузырьки опыта без задержки",
         Category.PLAYER
      );
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null) {
         ItemStack itemStack2 = minecraftClient.player.getMainHandStack();
         if (itemStack2.isOf(Items.EXPERIENCE_BOTTLE)) {
            ((IMinecraftClientAccessor)minecraftClient).setItemUseCooldown(0);
         }
      }
   }
}
