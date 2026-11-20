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

import java.util.List;
import java.util.function.Supplier;
import lombok.Generated;

public class MultiSetting extends Setting {
   public List<BooleanSetting> c;

   public MultiSetting(String string1, BooleanSetting... booleanSettingArray2) {
      super(string1);
      this.c = List.of(booleanSettingArray2);
   }

   public MultiSetting a(String string1, BooleanSetting... booleanSettingArray2) {
      return new MultiSetting(string1, booleanSettingArray2);
   }

   public boolean a(String string1) {
      return this.b(string1).getValue();
   }

   public void a(String string1, boolean flag2) {
      this.b(string1).setValue(flag2);
   }

   public MultiSetting a(Supplier<Boolean> supplier1) {
      this.visibleCondition = supplier1;
      return this;
   }

   private BooleanSetting b(String string1) {
      for (BooleanSetting booleanSetting3 : this.c) {
         if (booleanSetting3.getName().equalsIgnoreCase(string1)) {
            return booleanSetting3;
         }
      }

      throw new NullPointerException("Unknown list setting entry: " + string1);
   }

   @Generated
   public List<BooleanSetting> e() {
      return this.c;
   }

   @Generated
   public void a(List<BooleanSetting> list1) {
      this.c = list1;
   }
}
