package dev.leg1tka.gui.themes;

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

public final class ThemeKey {
   private final String a;
   private final String b;
   private final String c;
   private final String d;
   private final String e;

   @Override
   public String toString() {
      return this.a + "_" + this.b + "_" + this.c + "_" + this.d + "_" + this.e;
   }

   @Generated
   public ThemeKey(String string1, String string2, String string3, String string4, String string5) {
      this.a = string1;
      this.b = string2;
      this.c = string3;
      this.d = string4;
      this.e = string5;
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
   public String c() {
      return this.c;
   }

   @Generated
   public String d() {
      return this.d;
   }

   @Generated
   public String e() {
      return this.e;
   }

   @Generated
   @Override
   public boolean equals(Object object1) {
      if (object1 == this) {
         return true;
      } else if (!(object1 instanceof dev.leg1tka.gui.themes.ThemeKey value2)) {
         return false;
      } else {
         String string3 = this.a();
         String string4 = value2.a();
         if (string3 == null ? string4 == null : string3.equals(string4)) {
            String string5 = this.b();
            String string6 = value2.b();
            if (string5 == null ? string6 == null : string5.equals(string6)) {
               String string7 = this.c();
               String string8 = value2.c();
               if (string7 == null ? string8 == null : string7.equals(string8)) {
                  String string9 = this.d();
                  String string10 = value2.d();
                  if (string9 == null ? string10 == null : string9.equals(string10)) {
                     String string11 = this.e();
                     String string12 = value2.e();
                     return string11 == null ? string12 == null : string11.equals(string12);
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   @Generated
   @Override
   public int hashCode() {
      byte byteValue1 = 59;
      int intValue2 = 1;
      String string3 = this.a();
      intValue2 = intValue2 * 59 + (string3 == null ? 43 : string3.hashCode());
      String string4 = this.b();
      intValue2 = intValue2 * 59 + (string4 == null ? 43 : string4.hashCode());
      String string5 = this.c();
      intValue2 = intValue2 * 59 + (string5 == null ? 43 : string5.hashCode());
      String string6 = this.d();
      intValue2 = intValue2 * 59 + (string6 == null ? 43 : string6.hashCode());
      String string7 = this.e();
      return intValue2 * 59 + (string7 == null ? 43 : string7.hashCode());
   }
}
