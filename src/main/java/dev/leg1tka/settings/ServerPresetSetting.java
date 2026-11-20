package dev.leg1tka.settings;

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

public class ServerPresetSetting {
   private dev.leg1tka.settings.ServerPresetSetting.a a;

   public ServerPresetSetting(dev.leg1tka.settings.ServerPresetSetting.a value1) {
      this.a = value1;
   }

   public static enum a {
      RW("RW"),
      HW("HW"),
      FT("FT"),
      SP("SP");

      private final String a;

      @Generated
      private a(String string3) {
         this.a = string3;
      }

      @Generated
      public String a() {
         return this.a;
      }
   }
}
