package dev.leg1tka.managers;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

public class UserInfo {
   private final String a;
   private final int b;
   private final String c;
   private final String d;

   public UserInfo(String string1, int intValue2, String string3, String string4) {
      this.a = string1;
      this.b = intValue2;
      this.c = string3;
      this.d = string4;
   }

   public static dev.leg1tka.managers.UserInfo a() {
      return new dev.leg1tka.managers.UserInfo("Unknown", 0, "Unknown", "");
   }

   public String b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public String d() {
      return this.c;
   }

   public String e() {
      return this.d;
   }
}
