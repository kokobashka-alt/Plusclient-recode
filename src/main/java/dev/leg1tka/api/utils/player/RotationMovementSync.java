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

import lombok.Generated;
import net.minecraft.util.math.MathHelper;

public final class RotationMovementSync implements dev.leg1tka.api.ClientAccess {
   private static int c;

   public static void a() {
      int intValue0 = minecraftClient.getCurrentFps();
      c = MathHelper.lerp(0.5F, intValue0, c);
   }

   @Generated
   private RotationMovementSync() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }

   @Generated
   public static int b() {
      return c;
   }
}
