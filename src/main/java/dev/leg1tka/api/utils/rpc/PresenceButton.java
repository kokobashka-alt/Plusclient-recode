package dev.leg1tka.api.utils.rpc;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.io.Serializable;

public class PresenceButton implements Serializable {
   private final String a;
   private final String b;

   public static dev.leg1tka.api.utils.rpc.PresenceButton a(String string0, String string1) {
      string0 = string0.substring(0, Math.min(string0.length(), 31));
      return new dev.leg1tka.api.utils.rpc.PresenceButton(string0, string1);
   }

   protected PresenceButton(String string1, String string2) {
      this.b = string1;
      this.a = string2;
   }

   public String a() {
      return this.a;
   }

   public String b() {
      return this.b;
   }
}
