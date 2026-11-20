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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class FontAtlas {
   protected static int a;
   protected dev.leg1tka.render.font.FontAtlas.a[] b = new dev.leg1tka.render.font.FontAtlas.a[1104];
   protected Font c;
   protected boolean d;
   protected boolean e;
   protected int f = -1;
   protected int g = 0;
   protected Identifier h;
   protected int i;
   private static int j = 0;

   public FontAtlas(Font font1, boolean flag2, boolean flag3) {
      this.c = font1;
      this.d = flag2;
      this.e = flag3;
      this.a(font1, flag2, flag3, this.b);
   }

   protected void a(Font font1, boolean flag2, boolean flag3, dev.leg1tka.render.font.FontAtlas.a[] valueArray4) {
      BufferedImage bufferedImage5 = this.b(font1, flag2, flag3, valueArray4);

      try {
         NativeImage nativeImage6 = new NativeImage(bufferedImage5.getWidth(), bufferedImage5.getHeight(), false);

         for (int intValue7 = 0; intValue7 < bufferedImage5.getHeight(); intValue7++) {
            for (int intValue8 = 0; intValue8 < bufferedImage5.getWidth(); intValue8++) {
               int intValue9 = bufferedImage5.getRGB(intValue8, intValue7);
               int intValue10 = intValue9 >> 24 & 0xFF;
               int intValue11 = intValue9 >> 16 & 0xFF;
               int intValue12 = intValue9 >> 8 & 0xFF;
               int intValue13 = intValue9 & 0xFF;
               nativeImage6.setColorArgb(intValue8, intValue7, intValue10 << 24 | intValue11 << 16 | intValue12 << 8 | intValue13);
            }
         }

         NativeImageBackedTexture nativeImageBackedTexture15 = new NativeImageBackedTexture(nativeImage6);
         this.i = nativeImageBackedTexture15.getGlId();
         String string16 = "cfont_" + j++;
         this.h = Identifier.of("customfont", string16);
         MinecraftClient.getInstance().getTextureManager().registerTexture(this.h, nativeImageBackedTexture15);
      } catch (Exception exception14) {
         exception14.printStackTrace();
      }
   }

   protected BufferedImage b(Font font1, boolean flag2, boolean flag3, dev.leg1tka.render.font.FontAtlas.a[] valueArray4) {
      BufferedImage bufferedImage5 = new BufferedImage(512, 512, 2);
      Graphics2D graphics2D6 = (Graphics2D)bufferedImage5.getGraphics();
      graphics2D6.setFont(font1);
      graphics2D6.setColor(new Color(255, 255, 255, 0));
      graphics2D6.fillRect(0, 0, 512, 512);
      graphics2D6.setColor(Color.WHITE);
      graphics2D6.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, flag3 ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
      graphics2D6.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, flag2 ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
      graphics2D6.setRenderingHint(RenderingHints.KEY_ANTIALIASING, flag2 ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
      FontMetrics fontMetrics7 = graphics2D6.getFontMetrics();
      int intValue8 = 0;
      int intValue9 = 0;
      int intValue10 = 1;

      for (int intValue11 = 0; intValue11 < valueArray4.length; intValue11++) {
         char character12 = (char)intValue11;
         if (character12 > 1039 && character12 < 1104 || character12 < 256) {
            dev.leg1tka.render.font.FontAtlas.a value13 = new dev.leg1tka.render.font.FontAtlas.a();
            Rectangle2D rectangle2D14 = fontMetrics7.getStringBounds(String.valueOf(character12), graphics2D6);
            value13.a = rectangle2D14.getBounds().width + 8;
            value13.b = rectangle2D14.getBounds().height;
            if (intValue9 + value13.a >= 512) {
               intValue9 = 0;
               intValue10 += intValue8;
               intValue8 = 0;
            }

            if (value13.b > intValue8) {
               intValue8 = value13.b;
            }

            value13.c = intValue9;
            value13.d = intValue10;
            if (value13.b > this.f) {
               this.f = value13.b;
            }

            valueArray4[intValue11] = value13;
            graphics2D6.drawString(String.valueOf(character12), intValue9 + 2, intValue10 + fontMetrics7.getAscent());
            intValue9 += value13.a;
         }
      }

      return bufferedImage5;
   }

   public void a(dev.leg1tka.render.font.FontAtlas.a[] valueArray1, char character2, float floatValue3, float floatValue4, Matrix4f matrix4f5, BufferBuilder bufferBuilder6) {
      try {
         if (valueArray1[character2] == null) {
            return;
         }

         this.a(
            floatValue3,
            floatValue4,
            (float)valueArray1[character2].a,
            (float)valueArray1[character2].b,
            (float)valueArray1[character2].c,
            (float)valueArray1[character2].d,
            (float)valueArray1[character2].a,
            (float)valueArray1[character2].b,
            matrix4f5,
            bufferBuilder6
         );
      } catch (Exception exception8) {
      }
   }

   protected void a(float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, Matrix4f matrix4f9, BufferBuilder bufferBuilder10) {
      float floatValue11 = floatValue5 / 512.0F;
      float floatValue12 = floatValue6 / 512.0F;
      float floatValue13 = floatValue7 / 512.0F;
      float floatValue14 = floatValue8 / 512.0F;
      bufferBuilder10.vertex(matrix4f9, floatValue1 + floatValue3, floatValue2, 0.0F).texture(floatValue11 + floatValue13, floatValue12);
      bufferBuilder10.vertex(matrix4f9, floatValue1, floatValue2, 0.0F).texture(floatValue11, floatValue12);
      bufferBuilder10.vertex(matrix4f9, floatValue1, floatValue2 + floatValue4, 0.0F).texture(floatValue11, floatValue12 + floatValue14);
      bufferBuilder10.vertex(matrix4f9, floatValue1, floatValue2 + floatValue4, 0.0F).texture(floatValue11, floatValue12 + floatValue14);
      bufferBuilder10.vertex(matrix4f9, floatValue1 + floatValue3, floatValue2 + floatValue4, 0.0F).texture(floatValue11 + floatValue13, floatValue12 + floatValue14);
      bufferBuilder10.vertex(matrix4f9, floatValue1 + floatValue3, floatValue2, 0.0F).texture(floatValue11 + floatValue13, floatValue12);
   }

   public int a(String string1) {
      return this.a();
   }

   public int a() {
      return (this.f - 8) / 2;
   }

   public int b(String string1) {
      int intValue2 = 0;

      for (char character6 : string1.toCharArray()) {
         if (character6 < this.b.length && this.b[character6] != null) {
            intValue2 += this.b[character6].a - 8 + this.g;
         }
      }

      return intValue2 / 2;
   }

   public void a(boolean flag1) {
      if (this.d != flag1) {
         this.d = flag1;
         this.a(this.c, flag1, this.e, this.b);
      }
   }

   public void b(boolean flag1) {
      if (this.e != flag1) {
         this.e = flag1;
         this.a(this.c, this.d, flag1, this.b);
      }
   }

   public void a(Font font1) {
      this.c = font1;
      this.a(font1, this.d, this.e, this.b);
   }

   @Generated
   public Font b() {
      return this.c;
   }

   @Generated
   public boolean c() {
      return this.d;
   }

   @Generated
   public boolean d() {
      return this.e;
   }

   @Generated
   public Identifier e() {
      return this.h;
   }

   @Generated
   public int f() {
      return this.i;
   }

   protected static class a {
      public int a;
      public int b;
      public int c;
      public int d;

      protected a() {
      }
   }
}
