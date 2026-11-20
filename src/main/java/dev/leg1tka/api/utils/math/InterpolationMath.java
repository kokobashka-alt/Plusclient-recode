package dev.leg1tka.api.utils.math;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import net.minecraft.util.math.MathHelper;

public final class InterpolationMath {
   private InterpolationMath() {
   }

   public static float a(float floatValue0) {
      int intValue1 = MathHelper.floor(floatValue0);
      int intValue2 = intValue1 + 1;
      float floatValue3 = floatValue0 - (float)intValue1;
      float floatValue4 = floatValue3 * floatValue3 * (3.0F - 2.0F * floatValue3);
      return MathHelper.lerp(floatValue4, a(intValue1), a(intValue2));
   }

   private static float a(int intValue0) {
      int intValue1 = intValue0 * 1103515245 + 12345;
      return (float)(intValue1 >> 16 & 0xFF) / 127.5F - 1.0F;
   }
}
