package dev.leg1tka.gui.themes;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

public final class ThemeAccentResolver {
   private ThemeAccentResolver() {
   }

   public static int a() {
      try {
         return dev.leg1tka.api.utils.text.ColorUtils.a();
      } catch (Throwable throwable1) {
         return dev.leg1tka.api.utils.text.ColorUtils.a(190, 143, 255, 255);
      }
   }

   public static int a(int intValue0, float floatValue1) {
      int intValue2 = intValue0 >>> 24 & 0xFF;
      int intValue3 = Math.max(1, (int)((float)intValue2 * Math.max(0.0F, Math.min(1.0F, floatValue1))));
      return intValue0 & 16777215 | intValue3 << 24;
   }

   public static int a(float floatValue0) {
      return Math.max(1, Math.min(255, (int)floatValue0));
   }
}
