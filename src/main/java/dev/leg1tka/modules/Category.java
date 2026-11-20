package dev.leg1tka.modules;

import lombok.Generated;

public enum Category {
   THEMES("Темы", "i"),
   COMBAT("Combat", "b"),
   MOVEMENT("Movement", "c"),
   RENDER("Render", "d"),
   PLAYER("Player", "e"),
   MISC("Misc", "w");

   private final String a;
   private final String b;

   public boolean a() {
      return this == THEMES;
   }

   @Generated
   private Category(String string3, String string4) {
      this.a = string3;
      this.b = string4;
   }

   @Generated
   public String b() {
      return this.a;
   }

   @Generated
   public String c() {
      return this.b;
   }
}
