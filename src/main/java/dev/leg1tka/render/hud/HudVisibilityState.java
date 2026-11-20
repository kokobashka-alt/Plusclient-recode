package dev.leg1tka.render.hud;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

public class HudVisibilityState {
   private static boolean a = false;
   private static boolean b = false;
   private static boolean c = false;

   public HudVisibilityState() {
   }

   public static void a() {
      a = true;
   }

   public static void b() {
      b = true;
   }

   public static void c() {
      c = true;
   }

   public static boolean d() {
      return a;
   }

   public static boolean e() {
      return b;
   }

   public static boolean f() {
      return c;
   }

   public static void g() {
      a = false;
   }

   public static void h() {
      b = false;
   }

   public static void i() {
      c = false;
   }
}
