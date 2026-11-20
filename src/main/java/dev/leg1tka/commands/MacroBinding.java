package dev.leg1tka.commands;

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

public class MacroBinding {
   private String a;
   private String b;
   private dev.leg1tka.settings.KeybindSetting c;

   @Generated
   public MacroBinding(String string1, String string2, dev.leg1tka.settings.KeybindSetting value3) {
      this.a = string1;
      this.b = string2;
      this.c = value3;
   }

   @Generated
   public String a() {
      return this.a;
   }

   @Generated
   public String b() {
      return this.b;
   }

   @Generated
   public dev.leg1tka.settings.KeybindSetting c() {
      return this.c;
   }
}
