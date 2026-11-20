package dev.leg1tka.modules.movement;


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
import net.minecraft.entity.effect.StatusEffects;

public class Sprint extends Module {
   public static Sprint c = new Sprint();
   private static long d = 0L;

   public Sprint() {
      super("Sprint", "Автоматический бег", Category.MOVEMENT);
   }

   @EventLink
   public void onEvent(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null) {
         if (System.currentTimeMillis() >= d) {
            minecraftClient.options.sprintKey.setPressed(false);
            if (!AttackAura.c.E.i()) {
               boolean flag2 = FreeCamera.c != null && FreeCamera.c.isEnabled();
               minecraftClient.player
                  .setSprinting(
                     !flag2
                        && (!minecraftClient.player.isTouchingWater() || minecraftClient.player.isSubmergedInWater())
                        && !minecraftClient.player.isGliding()
                        && ((float)minecraftClient.player.getHungerManager().getFoodLevel() > 6.0F || minecraftClient.player.getAbilities().allowFlying)
                        && !minecraftClient.player.hasStatusEffect(StatusEffects.BLINDNESS)
                        && (!minecraftClient.player.isUsingItem() || NoSlow.c != null && NoSlow.c.p())
                        && (!minecraftClient.player.hasVehicle() || minecraftClient.player.getVehicle().canSprintAsVehicle() && minecraftClient.player.getVehicle().isLogicalSideForUpdatingMovement())
                        && (!minecraftClient.player.shouldSlowDown() || minecraftClient.player.isSubmergedInWater())
                        && minecraftClient.player.input.hasForwardMovement()
                        && !minecraftClient.player.horizontalCollision
                        && !minecraftClient.player.collidedSoftly
                  );
            }
         }
      }
   }

   @Override
   public void onDisable() {
      if (minecraftClient.player != null) {
         minecraftClient.player.setSprinting(false);
         minecraftClient.options.sprintKey.setPressed(false);
      }

      super.onDisable();
   }

   public static void a(long longValue0) {
      d = System.currentTimeMillis() + longValue0;
   }

   public static void b(int intValue0) {
      d = System.currentTimeMillis() + (long)intValue0;
   }

   public static void p() {
      d = 0L;
   }

   public static boolean q() {
      return minecraftClient.player != null && minecraftClient.player.isSprinting();
   }

   public boolean r() {
      return false;
   }
}
