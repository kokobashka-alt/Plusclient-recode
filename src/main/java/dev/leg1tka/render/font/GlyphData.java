package dev.leg1tka.render.font;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

public class GlyphData {
   private final int a;
   private final float b;
   private final dev.leg1tka.render.font.GlyphData.a c;
   private final dev.leg1tka.render.font.GlyphData.a d;

   public GlyphData(int intValue1, float floatValue2, dev.leg1tka.render.font.GlyphData.a value3, dev.leg1tka.render.font.GlyphData.a value4) {
      this.a = intValue1;
      this.b = floatValue2;
      this.c = value3;
      this.d = value4;
   }

   public int a() {
      return this.a;
   }

   public float b() {
      return this.b;
   }

   public dev.leg1tka.render.font.GlyphData.a c() {
      return this.c;
   }

   public dev.leg1tka.render.font.GlyphData.a d() {
      return this.d;
   }

   public boolean e() {
      return this.c != null && this.d != null;
   }

   public char f() {
      return (char)this.a;
   }

   @Override
   public String toString() {
      return "GlyphData{unicode=" + this.a + " ('" + (char)this.a + "'), advance=" + this.b + ", planeBounds=" + this.c + ", atlasBounds=" + this.d + "}";
   }

   public static class a {
      private final float a;
      private final float b;
      private final float c;
      private final float d;

      public a(float floatValue1, float floatValue2, float floatValue3, float floatValue4) {
         this.a = floatValue1;
         this.b = floatValue2;
         this.c = floatValue3;
         this.d = floatValue4;
      }

      public float a() {
         return this.a;
      }

      public float b() {
         return this.b;
      }

      public float c() {
         return this.c;
      }

      public float d() {
         return this.d;
      }

      public float e() {
         return this.c - this.a;
      }

      public float f() {
         return this.d - this.b;
      }

      @Override
      public String toString() {
         return "Bounds{left=" + this.a + ", bottom=" + this.b + ", right=" + this.c + ", top=" + this.d + ", width=" + this.e() + ", height=" + this.f() + "}";
      }
   }
}
