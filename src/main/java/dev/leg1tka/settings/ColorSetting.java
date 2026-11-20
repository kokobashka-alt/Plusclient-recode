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

public class ColorSetting extends Setting {
   private int value;

   public ColorSetting(String string1, int intValue2) {
      super(string1);
      this.value = intValue2;
   }

   public ColorSetting visibleWhen(Supplier<Boolean> supplier1) {
      this.visibleCondition = supplier1;
      return this;
   }

   @Generated
   public int getColor() {
      return this.value;
   }

   @Generated
   public void setColor(int intValue1) {
      this.value = intValue1;
   }
}
