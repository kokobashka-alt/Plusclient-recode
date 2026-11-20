package dev.leg1tka.api.utils.text;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.awt.Color;
import java.nio.ByteBuffer;
import java.util.Objects;
import lombok.Generated;
import net.minecraft.util.math.MathHelper;

public class ColorValue {
   public static final dev.leg1tka.api.utils.text.ColorValue a = new dev.leg1tka.api.utils.text.ColorValue(255, 255, 255);
   public static final dev.leg1tka.api.utils.text.ColorValue b = new dev.leg1tka.api.utils.text.ColorValue(0, 0, 0);
   public static final dev.leg1tka.api.utils.text.ColorValue c = new dev.leg1tka.api.utils.text.ColorValue(0, 255, 0);
   public static final dev.leg1tka.api.utils.text.ColorValue d = new dev.leg1tka.api.utils.text.ColorValue(255, 0, 0);
   public static final dev.leg1tka.api.utils.text.ColorValue e = new dev.leg1tka.api.utils.text.ColorValue(0, 0, 255);
   public static final dev.leg1tka.api.utils.text.ColorValue f = new dev.leg1tka.api.utils.text.ColorValue(255, 255, 0);
   public static final dev.leg1tka.api.utils.text.ColorValue g = new dev.leg1tka.api.utils.text.ColorValue(88, 87, 93);
   public static final dev.leg1tka.api.utils.text.ColorValue h = new dev.leg1tka.api.utils.text.ColorValue(0, 0, 0, 0);
   private transient float[] i;
   private final int j;
   private final int k;
   private final int l;
   private final int m;
   private static final ByteBuffer n = ByteBuffer.allocateDirect(4);

   public ColorValue(int intValue1) {
      this(dev.leg1tka.api.utils.text.ColorUtils.a(intValue1), dev.leg1tka.api.utils.text.ColorUtils.b(intValue1), dev.leg1tka.api.utils.text.ColorUtils.l(intValue1), dev.leg1tka.api.utils.text.ColorUtils.m(intValue1));
   }

   public ColorValue(Color color1) {
      this(color1.getRed(), color1.getGreen(), color1.getBlue(), color1.getAlpha());
   }

   public ColorValue(int intValue1, int intValue2, int intValue3) {
      this(intValue1, intValue2, intValue3, 255);
   }

   public ColorValue(int intValue1, int intValue2, int intValue3, int intValue4) {
      intValue1 = MathHelper.clamp(intValue1, 0, 255);
      intValue2 = MathHelper.clamp(intValue2, 0, 255);
      intValue3 = MathHelper.clamp(intValue3, 0, 255);
      intValue4 = MathHelper.clamp(intValue4, 0, 255);
      this.j = intValue1;
      this.k = intValue2;
      this.l = intValue3;
      this.m = intValue4;
   }

   public ColorValue(int intValue1, int intValue2, int intValue3, float floatValue4) {
      intValue1 = MathHelper.clamp(intValue1, 0, 255);
      intValue2 = MathHelper.clamp(intValue2, 0, 255);
      intValue3 = MathHelper.clamp(intValue3, 0, 255);
      floatValue4 = MathHelper.clamp(floatValue4, 0.0F, 255.0F);
      this.j = intValue1;
      this.k = intValue2;
      this.l = intValue3;
      this.m = (int)floatValue4;
   }

   public int a() {
      int intValue1 = Math.round((float)this.e((float)this.m));
      int intValue2 = Math.round((float)this.e((float)this.j));
      int intValue3 = Math.round((float)this.e((float)this.k));
      int intValue4 = Math.round((float)this.e((float)this.l));
      return (intValue1 & 0xFF) << 24 | (intValue2 & 0xFF) << 16 | (intValue3 & 0xFF) << 8 | intValue4 & 0xFF;
   }

   private int e(float floatValue1) {
      return (int)Math.max(0.0F, Math.min(255.0F, floatValue1));
   }

   public static dev.leg1tka.api.utils.text.ColorValue a(String string0) {
      String string1 = string0.startsWith("#") ? string0.substring(1) : string0;
      if (string1.length() != 6 && string1.length() != 8) {
         throw new IllegalArgumentException("Hex color must be NameProtect the format #RRGGBB or #RRGGBBAA");
      } else {
         int intValue2 = Integer.parseInt(string1.substring(0, 2), 16);
         int intValue3 = Integer.parseInt(string1.substring(2, 4), 16);
         int intValue4 = Integer.parseInt(string1.substring(4, 6), 16);
         int intValue5 = string1.length() == 8 ? Integer.parseInt(string1.substring(6, 8), 16) : 255;
         return new dev.leg1tka.api.utils.text.ColorValue(intValue2, intValue3, intValue4, intValue5);
      }
   }

   public static dev.leg1tka.api.utils.text.ColorValue a(dev.leg1tka.api.utils.text.ColorValue value0, dev.leg1tka.api.utils.text.ColorValue value1, float floatValue2) {
      float floatValue3 = Math.max(0.0F, Math.min(1.0F, floatValue2));
      int intValue4 = (int)((float)value0.e() + (float)(value1.e() - value0.e()) * floatValue3);
      int intValue5 = (int)((float)value0.f() + (float)(value1.f() - value0.f()) * floatValue3);
      int intValue6 = (int)((float)value0.g() + (float)(value1.g() - value0.g()) * floatValue3);
      int intValue7 = (int)((float)value0.h() + (float)(value1.h() - value0.h()) * floatValue3);
      return new dev.leg1tka.api.utils.text.ColorValue(intValue4, intValue5, intValue6, intValue7);
   }

   public static dev.leg1tka.api.utils.text.ColorValue a(int intValue0) {
      int intValue1 = intValue0 >> 24 & 0xFF;
      int intValue2 = intValue0 >> 16 & 0xFF;
      int intValue3 = intValue0 >> 8 & 0xFF;
      int intValue4 = intValue0 & 0xFF;
      return new dev.leg1tka.api.utils.text.ColorValue(intValue2, intValue3, intValue4, intValue1);
   }

   public dev.leg1tka.api.utils.text.ColorValue a(float floatValue1) {
      return new dev.leg1tka.api.utils.text.ColorValue(this.j, this.k, this.l, (int)floatValue1);
   }

   public dev.leg1tka.api.utils.text.ColorValue b(int intValue1) {
      return new dev.leg1tka.api.utils.text.ColorValue(this.j, this.k, this.l, intValue1);
   }

   public dev.leg1tka.api.utils.text.ColorValue b(float floatValue1) {
      return this.b((int)((float)this.m * floatValue1));
   }

   public dev.leg1tka.api.utils.text.ColorValue a(dev.leg1tka.api.utils.text.ColorValue value1, float floatValue2) {
      floatValue2 = Math.min(1.0F, Math.max(0.0F, floatValue2));
      return new dev.leg1tka.api.utils.text.ColorValue(
         (int)dev.leg1tka.api.utils.math.MathOperations.a((double)this.e(), (double)value1.e(), (double)floatValue2),
         (int)dev.leg1tka.api.utils.math.MathOperations.a((double)this.f(), (double)value1.f(), (double)floatValue2),
         (int)dev.leg1tka.api.utils.math.MathOperations.a((double)this.g(), (double)value1.g(), (double)floatValue2),
         (int)dev.leg1tka.api.utils.math.MathOperations.a((double)this.h(), (double)value1.h(), (double)floatValue2)
      );
   }

   public dev.leg1tka.api.utils.text.ColorValue c(float floatValue1) {
      floatValue1 = MathHelper.clamp(floatValue1, 0.0F, 1.0F);
      return new dev.leg1tka.api.utils.text.ColorValue((int)((float)this.j * (1.0F - floatValue1)), (int)((float)this.k * (1.0F - floatValue1)), (int)((float)this.l * (1.0F - floatValue1)), this.m);
   }

   public static dev.leg1tka.api.utils.text.ColorValue a(float floatValue0, float floatValue1, float floatValue2) {
      if (floatValue1 == 0.0F) {
         int intValue11 = (int)(floatValue2 * 255.0F + 0.5F);
         return new dev.leg1tka.api.utils.text.ColorValue(intValue11, intValue11, intValue11);
      } else {
         float floatValue3 = (floatValue0 - (float)Math.floor((double)floatValue0)) * 6.0F;
         float floatValue4 = floatValue3 - (float)Math.floor((double)floatValue3);
         float floatValue5 = floatValue2 * (1.0F - floatValue1);
         float floatValue6 = floatValue2 * (1.0F - floatValue1 * floatValue4);
         float floatValue7 = floatValue2 * (1.0F - floatValue1 * (1.0F - floatValue4));
         float floatValue8 = 0.0F;
         float floatValue9 = 0.0F;
         float floatValue10 = 0.0F;
         switch ((int)floatValue3) {
            case 0:
               floatValue8 = floatValue2;
               floatValue9 = floatValue7;
               floatValue10 = floatValue5;
               break;
            case 1:
               floatValue8 = floatValue6;
               floatValue9 = floatValue2;
               floatValue10 = floatValue5;
               break;
            case 2:
               floatValue8 = floatValue5;
               floatValue9 = floatValue2;
               floatValue10 = floatValue7;
               break;
            case 3:
               floatValue8 = floatValue5;
               floatValue9 = floatValue6;
               floatValue10 = floatValue2;
               break;
            case 4:
               floatValue8 = floatValue7;
               floatValue9 = floatValue5;
               floatValue10 = floatValue2;
               break;
            case 5:
               floatValue8 = floatValue2;
               floatValue9 = floatValue5;
               floatValue10 = floatValue6;
         }

         return new dev.leg1tka.api.utils.text.ColorValue((int)(floatValue8 * 255.0F), (int)(floatValue9 * 255.0F), (int)(floatValue10 * 255.0F));
      }
   }

   public float b() {
      return this.i()[0];
   }

   public float c() {
      return this.i()[2];
   }

   public float d() {
      return this.i()[1];
   }

   private float[] i() {
      if (this.i == null) {
         this.i = this.j();
      }

      return this.i;
   }

   private float[] j() {
      float floatValue1 = (float)this.j / 255.0F;
      float floatValue2 = (float)this.k / 255.0F;
      float floatValue3 = (float)this.l / 255.0F;
      float floatValue4 = Math.max(floatValue1, Math.max(floatValue2, floatValue3));
      float floatValue5 = Math.min(floatValue1, Math.min(floatValue2, floatValue3));
      float floatValue6 = floatValue4 - floatValue5;
      float floatValue7 = 0.0F;
      if (floatValue6 != 0.0F) {
         if (floatValue4 == floatValue1) {
            floatValue7 = (floatValue2 - floatValue3) / floatValue6;
         } else if (floatValue4 == floatValue2) {
            floatValue7 = (floatValue3 - floatValue1) / floatValue6 + 2.0F;
         } else {
            floatValue7 = (floatValue1 - floatValue2) / floatValue6 + 4.0F;
         }

         floatValue7 /= 6.0F;
         if (floatValue7 < 0.0F) {
            floatValue7++;
         }
      }

      float floatValue8 = floatValue4 == 0.0F ? 0.0F : floatValue6 / floatValue4;
      return new float[]{floatValue7, floatValue8, floatValue4};
   }

   public dev.leg1tka.api.utils.text.ColorValue d(float floatValue1) {
      floatValue1 = MathHelper.clamp(floatValue1, 0.0F, 1.0F);
      return new dev.leg1tka.api.utils.text.ColorValue(
         (int)((float)this.j + (255.0F - (float)this.j) * floatValue1),
         (int)((float)this.k + (255.0F - (float)this.k) * floatValue1),
         (int)((float)this.l + (255.0F - (float)this.l) * floatValue1),
         this.m
      );
   }

   @Override
   public boolean equals(Object object1) {
      if (this == object1) {
         return true;
      } else if (object1 != null && this.getClass() == object1.getClass()) {
         dev.leg1tka.api.utils.text.ColorValue value2 = (dev.leg1tka.api.utils.text.ColorValue)object1;
         return Float.compare((float)this.j, (float)value2.j) == 0
            && Float.compare((float)this.k, (float)value2.k) == 0
            && Float.compare((float)this.l, (float)value2.l) == 0
            && Float.compare((float)this.m, (float)value2.m) == 0;
      } else {
         return false;
      }
   }

   public float a(dev.leg1tka.api.utils.text.ColorValue value1) {
      return Math.abs(this.b() - value1.b()) + Math.abs(this.d() - value1.d()) + Math.abs(this.c() - value1.c());
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.j, this.k, this.l, this.m);
   }

   @Generated
   public int e() {
      return this.j;
   }

   @Generated
   public int f() {
      return this.k;
   }

   @Generated
   public int g() {
      return this.l;
   }

   @Generated
   public int h() {
      return this.m;
   }
}
