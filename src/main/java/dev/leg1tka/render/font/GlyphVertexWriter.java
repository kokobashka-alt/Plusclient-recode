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

import net.minecraft.client.render.VertexConsumer;
import org.joml.Matrix4f;

public final class GlyphVertexWriter {
   private final int a;
   private final float b;
   private final float c;
   private final float d;
   private final float e;
   private final float f;
   private final float g;
   private final float h;
   private final float i;
   private final float j;

   public GlyphVertexWriter(int intValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, float floatValue9, float floatValue10, float floatValue11, float floatValue12) {
      this.a = intValue1;
      this.f = floatValue2;
      if (floatValue7 == 0.0F && floatValue9 == 0.0F && floatValue8 == 0.0F && floatValue10 == 0.0F) {
         this.b = 0.0F;
         this.c = 0.0F;
         this.d = 0.0F;
         this.e = 0.0F;
      } else {
         this.b = floatValue7 / floatValue11;
         this.c = floatValue9 / floatValue11;
         this.d = 1.0F - floatValue8 / floatValue12;
         this.e = 1.0F - floatValue10 / floatValue12;
      }

      if (floatValue3 == 0.0F && floatValue5 == 0.0F && floatValue4 == 0.0F && floatValue6 == 0.0F) {
         this.h = 0.0F;
         this.i = 0.0F;
         this.g = 0.0F;
         this.j = 0.0F;
      } else {
         this.h = floatValue5 - floatValue3;
         this.i = floatValue4 - floatValue6;
         this.g = floatValue4;
         this.j = floatValue3;
      }
   }

   public float a(Matrix4f matrix4f1, VertexConsumer vertexConsumer2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8, int intValue9, int intValue10) {
      return this.a(matrix4f1, vertexConsumer2, floatValue3, floatValue4, floatValue5, floatValue6, intValue7, intValue8, intValue9, intValue10, false);
   }

   public float a(Matrix4f matrix4f1, VertexConsumer vertexConsumer2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8, int intValue9, int intValue10, boolean flag11) {
      if (flag11) {
         floatValue4 += this.j * floatValue3;
      }

      floatValue5 -= this.g * floatValue3;
      floatValue5--;
      float floatValue12 = this.h * floatValue3;
      float floatValue13 = this.i * floatValue3;
      vertexConsumer2.vertex(matrix4f1, floatValue4, floatValue5, floatValue6).color(intValue7, intValue8, intValue9, intValue10).texture(this.b, this.d);
      vertexConsumer2.vertex(matrix4f1, floatValue4, floatValue5 + floatValue13, floatValue6).color(intValue7, intValue8, intValue9, intValue10).texture(this.b, this.e);
      vertexConsumer2.vertex(matrix4f1, floatValue4 + floatValue12, floatValue5 + floatValue13, floatValue6).color(intValue7, intValue8, intValue9, intValue10).texture(this.c, this.e);
      vertexConsumer2.vertex(matrix4f1, floatValue4 + floatValue12, floatValue5, floatValue6).color(intValue7, intValue8, intValue9, intValue10).texture(this.c, this.d);
      return this.f * floatValue3;
   }

   public float a(float floatValue1) {
      return this.f * floatValue1;
   }

   public boolean a() {
      return this.h == 0.0F || this.i == 0.0F;
   }

   public float b() {
      return this.j;
   }

   public float c() {
      return this.g;
   }

   public float d() {
      return this.g - this.i;
   }

   public float e() {
      return this.h;
   }

   public int f() {
      return this.a;
   }
}
