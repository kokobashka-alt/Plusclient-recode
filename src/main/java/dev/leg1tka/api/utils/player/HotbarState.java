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

public class HotbarState implements dev.leg1tka.api.ClientAccess {
   public HotbarState() {
   }

   public static void a(float floatValue0) {
      if (minecraftClient != null && minecraftClient.player != null) {
         minecraftClient.player.speed = floatValue0;
      }
   }

   public static void a() {
      a(1.0F);
   }
}
