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

import java.util.function.Supplier;
import lombok.Generated;

public class KeybindSetting extends Setting {
   private int keyCode;

   public KeybindSetting(String string1, int intValue2) {
      super(string1);
      this.keyCode = intValue2;
   }

   public dev.leg1tka.settings.KeybindSetting visibleWhen(Supplier<Boolean> supplier1) {
      this.visibleCondition = supplier1;
      return this;
   }

   @Generated
   public int getKey() {
      return this.keyCode;
   }

   @Generated
   public void setKey(int intValue1) {
      this.keyCode = intValue1;
   }
}
