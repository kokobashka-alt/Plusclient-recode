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

import dev.leg1tka.AspectDLC;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import lombok.Generated;

public class ModeSetting extends Setting {
   private List c;
   private String f;
   private int g;

   public ModeSetting(String string1, String string2, String... stringArray3) {
      super(string1);
      this.c = Arrays.asList(stringArray3);
      this.g = this.c.indexOf(string2);
      if (this.g < 0) {
         this.g = 0;
      }

      this.f = (String)this.c.get(this.g);
   }

   public void a(String string1) {
      int intValue2 = this.c.indexOf(string1);
      if (intValue2 >= 0) {
         this.f = string1;
         this.g = intValue2;
      }
   }

   public boolean b(String string1) {
      return this.f.equals(string1);
   }

   public String c(String string1) {
      return AspectDLC.INSTANCE.localizationStorage == null ? string1 : AspectDLC.INSTANCE.localizationStorage.a(string1);
   }

   public String e() {
      return this.c(this.f);
   }

   public ModeSetting a(Supplier<Boolean> supplier1) {
      this.visibleCondition = supplier1;
      return this;
   }

   @Generated
   public List<String> f() {
      return this.c;
   }

   @Generated
   public String g() {
      return this.f;
   }

   @Generated
   public int h() {
      return this.g;
   }

   @Generated
   public void a(List<String> list1) {
      this.c = list1;
   }

   @Generated
   public void d(String string1) {
      this.f = string1;
   }

   @Generated
   public void a(int intValue1) {
      this.g = intValue1;
   }
}
