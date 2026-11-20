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

public class StringSetting extends Setting {
   private String c;
   private final int f;

   public StringSetting(String string1, String string2) {
      this(string1, string2, 32);
   }

   public StringSetting(String string1, String string2, int intValue3) {
      super(string1);
      this.f = Math.max(1, intValue3);
      this.a(string2);
   }

   public void a(String string1) {
      if (string1 == null) {
         this.c = "";
      } else {
         StringBuilder stringBuilder2 = new StringBuilder();

         for (int intValue3 = 0; intValue3 < string1.length() && stringBuilder2.length() < this.f; intValue3++) {
            char character4 = string1.charAt(intValue3);
            if (!Character.isISOControl(character4)) {
               stringBuilder2.append(character4);
            }
         }

         this.c = stringBuilder2.toString();
      }
   }

   public String e() {
      return this.c;
   }

   public dev.leg1tka.settings.StringSetting a(Supplier<Boolean> supplier1) {
      this.visibleCondition = supplier1;
      return this;
   }

   @Generated
   public String f() {
      return this.c;
   }

   @Generated
   public int g() {
      return this.f;
   }
}
