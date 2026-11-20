package dev.leg1tka.gui.menu;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

public final class MenuLayout {
   private static float a = 0.5F;
   private static float b = 2.0F;

   private MenuLayout() {
   }

   public static void a() {
      Window window0 = MinecraftClient.getInstance().getWindow();
      if (window0 != null) {
         b = (float)Math.max(1.0, window0.getScaleFactor());
         float floatValue1 = (float)window0.getFramebufferHeight() / 1000.0F;
         float floatValue2 = (float)window0.getFramebufferWidth() / 1400.0F;
         float floatValue3 = Math.max(0.72F, Math.min(2.2F, Math.min(floatValue1, floatValue2)));
         a = floatValue3 / b;
      }
   }

   public static float a(float floatValue0) {
      return floatValue0 * a;
   }

   public static float b() {
      return a;
   }

   public static float b(float floatValue0) {
      return (float)Math.round(floatValue0 * b) / b;
   }

   public static float c() {
      return 1.0F / b;
   }
}
