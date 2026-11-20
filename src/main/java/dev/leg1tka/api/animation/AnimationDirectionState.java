package dev.leg1tka.api.animation;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

public class AnimationDirectionState {
   private static boolean a = true;
   private static float b = 0.0F;
   private static boolean c = false;

   public AnimationDirectionState() {
   }

   public static void a(float floatValue0) {
      boolean flag1 = floatValue0 > 0.01F;
      if (flag1 && !c) {
         a = !a;
      }

      c = flag1;
      b = floatValue0;
   }

   public static boolean a() {
      return a;
   }

   public static void b() {
      a = true;
      b = 0.0F;
      c = false;
   }
}
