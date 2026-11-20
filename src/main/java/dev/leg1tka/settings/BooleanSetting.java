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

public class BooleanSetting extends Setting {
   private boolean value;
   private int keyBind = -1;

   public BooleanSetting(String string1, boolean flag2) {
      super(string1);
      this.value = flag2;
   }

   public static BooleanSetting a(String string0, boolean flag1) {
      return new BooleanSetting(string0, flag1);
   }

   public BooleanSetting visibleWhen(Supplier<Boolean> supplier1) {
      this.visibleCondition = supplier1;
      return this;
   }

   @Generated
   public boolean getValue() {
      return this.value;
   }

   @Generated
   public int getKeyBind() {
      return this.keyBind;
   }

   @Generated
   public void setValue(boolean flag1) {
      this.value = flag1;
   }

   @Generated
   public void setKeyBind(int intValue1) {
      this.keyBind = intValue1;
   }
}
