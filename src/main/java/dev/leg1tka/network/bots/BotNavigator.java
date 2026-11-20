package dev.leg1tka.network.bots;

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
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;

public interface BotNavigator {
   void a(BlockPos blockPos1);

   dev.leg1tka.network.bots.BotNavigator.a a(ClientPlayerEntity clientPlayerEntity1, ClientWorld clientWorld2);

   void a(ClientPlayerEntity clientPlayerEntity1, float floatValue2, float floatValue3);

   void a();

   void b();

   String c();

   public static enum a {
      WALKING,
      ARRIVED,
      FAILED;

      private a() {
      }
   }
}
