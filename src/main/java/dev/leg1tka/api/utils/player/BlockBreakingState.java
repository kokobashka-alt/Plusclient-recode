package dev.leg1tka.api.utils.player;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import net.minecraft.client.network.ClientPlayerEntity;

public final class BlockBreakingState implements dev.leg1tka.api.ClientAccess {
   private static int c = Integer.MIN_VALUE;
   private static ClientPlayerEntity d;

   private BlockBreakingState() {
   }

   public static void a() {
      if (minecraftClient.player != null) {
         d = minecraftClient.player;
         c = minecraftClient.player.age;
      }
   }

   public static boolean b() {
      if (minecraftClient.player != null && minecraftClient.player == d) {
         int intValue0 = minecraftClient.player.age - c;
         return intValue0 >= 0 && intValue0 <= 1;
      } else {
         return false;
      }
   }
}
