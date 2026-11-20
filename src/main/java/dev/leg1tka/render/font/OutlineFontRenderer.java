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

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Font;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import org.joml.Matrix4f;

public class OutlineFontRenderer extends dev.leg1tka.render.font.AtlasFontRenderer {
   public OutlineFontRenderer(Font font1, boolean flag2, boolean flag3) {
      super(font1, flag2, flag3);
   }

   public int a(String string1, float floatValue2, float floatValue3, int intValue4, int intValue5, boolean flag6, boolean flag7) {
      int intValue9;
      if (flag6) {
         intValue9 = this.b(string1, floatValue2 + 1.0F, floatValue3 + 1.0F, intValue4, intValue5, true, flag7);
         intValue9 = Math.max(intValue9, this.b(string1, floatValue2, floatValue3, intValue4, intValue5, false, flag7));
      } else {
         intValue9 = this.b(string1, floatValue2, floatValue3, intValue4, intValue5, false, flag7);
      }

      return intValue9;
   }

   private int b(String string1, float floatValue2, float floatValue3, int intValue4, int intValue5, boolean flag6, boolean flag7) {
      if (string1 == null) {
         return 0;
      } else {
         if ((intValue4 & -67108864) == 0) {
            intValue4 |= -16777216;
         }

         if ((intValue5 & -67108864) == 0) {
            intValue5 |= -16777216;
         }

         if (flag6) {
            intValue4 = (intValue4 & 16579836) >> 2 | intValue4 & 0xFF000000;
            intValue5 = (intValue5 & 16579836) >> 2 | intValue5 & 0xFF000000;
         }

         return this.a(string1, floatValue2, floatValue3, flag6, intValue4, intValue5, flag7);
      }
   }

   private int a(String string1, float floatValue2, float floatValue3, boolean flag4, int intValue5, int intValue6, boolean flag7) {
      float floatValue8 = (float)this.b(string1);
      float floatValue9 = 0.0F;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      Matrix4f matrix4f10 = new Matrix4f();

      for (int intValue11 = 0; intValue11 < string1.length(); intValue11++) {
         char character12 = string1.charAt(intValue11);
         if (character12 == ' ' || character12 == 160) {
            floatValue2 += 4.0F;
         } else if (character12 < this.b.length && this.b[character12] != null) {
            float floatValue13 = (float)(this.b[character12].a - 8 + this.g);
            if (flag7) {
               float floatValue14 = floatValue9 / floatValue8;
               float floatValue15 = (floatValue9 + floatValue13) / floatValue8;
               int intValue16 = this.a(intValue5, intValue6, floatValue14);
               int intValue17 = this.a(intValue5, intValue6, floatValue15);
               this.a(character12, floatValue2, floatValue3, intValue16, intValue17, true, matrix4f10);
               floatValue9 += floatValue13;
            } else {
               this.a(character12, floatValue2, floatValue3, intValue5, intValue6, false, matrix4f10);
            }

            floatValue2 += floatValue13;
         }
      }

      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      return (int)floatValue2;
   }

   private int a(int intValue1, int intValue2, float floatValue3) {
      float floatValue4 = (float)(intValue1 >> 24 & 0xFF) / 255.0F;
      float floatValue5 = (float)(intValue1 >> 16 & 0xFF) / 255.0F;
      float floatValue6 = (float)(intValue1 >> 8 & 0xFF) / 255.0F;
      float floatValue7 = (float)(intValue1 & 0xFF) / 255.0F;
      float floatValue8 = (float)(intValue2 >> 24 & 0xFF) / 255.0F;
      float floatValue9 = (float)(intValue2 >> 16 & 0xFF) / 255.0F;
      float floatValue10 = (float)(intValue2 >> 8 & 0xFF) / 255.0F;
      float floatValue11 = (float)(intValue2 & 0xFF) / 255.0F;
      int intValue12 = (int)(((1.0F - floatValue3) * floatValue4 + floatValue3 * floatValue8) * 255.0F);
      int intValue13 = (int)(((1.0F - floatValue3) * floatValue5 + floatValue3 * floatValue9) * 255.0F);
      int intValue14 = (int)(((1.0F - floatValue3) * floatValue6 + floatValue3 * floatValue10) * 255.0F);
      int intValue15 = (int)(((1.0F - floatValue3) * floatValue7 + floatValue3 * floatValue11) * 255.0F);
      return intValue12 << 24 | intValue13 << 16 | intValue14 << 8 | intValue15;
   }

   private void a(char character1, float floatValue2, float floatValue3, int intValue4, int intValue5, boolean flag6, Matrix4f matrix4f7) {
      if (character1 < this.b.length && this.b[character1] != null) {
         float floatValue8 = (float)(intValue4 >> 24 & 0xFF) / 255.0F;
         float floatValue9 = (float)(intValue4 >> 16 & 0xFF) / 255.0F;
         float floatValue10 = (float)(intValue4 >> 8 & 0xFF) / 255.0F;
         float floatValue11 = (float)(intValue4 & 0xFF) / 255.0F;
         float floatValue12 = (float)(intValue5 >> 24 & 0xFF) / 255.0F;
         float floatValue13 = (float)(intValue5 >> 16 & 0xFF) / 255.0F;
         float floatValue14 = (float)(intValue5 >> 8 & 0xFF) / 255.0F;
         float floatValue15 = (float)(intValue5 & 0xFF) / 255.0F;
         dev.leg1tka.render.font.FontAtlas.a value16 = this.b[character1];
         float floatValue17 = (float)value16.c;
         float floatValue18 = (float)value16.d;
         int intValue19 = value16.a;
         float floatValue20 = (float)intValue19 - 0.01F;
         float floatValue21 = floatValue17 / 512.0F;
         float floatValue22 = floatValue18 / 512.0F;
         float floatValue23 = (floatValue17 + floatValue20 - 1.0F) / 512.0F;
         float floatValue24 = (floatValue18 + 7.99F) / 512.0F;
         RenderSystem.setShaderTexture(0, this.i);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
         Tessellator tessellator25 = Tessellator.getInstance();
         BufferBuilder bufferBuilder26 = tessellator25.begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
         if (flag6) {
            bufferBuilder26.vertex(matrix4f7, floatValue2, floatValue3, 0.0F).texture(floatValue21, floatValue22).color(floatValue9, floatValue10, floatValue11, floatValue8);
            bufferBuilder26.vertex(matrix4f7, floatValue2, floatValue3 + 7.99F, 0.0F).texture(floatValue21, floatValue24).color(floatValue9, floatValue10, floatValue11, floatValue8);
            bufferBuilder26.vertex(matrix4f7, floatValue2 + floatValue20 - 1.0F, floatValue3 + 7.99F, 0.0F).texture(floatValue23, floatValue24).color(floatValue13, floatValue14, floatValue15, floatValue12);
            bufferBuilder26.vertex(matrix4f7, floatValue2 + floatValue20 - 1.0F, floatValue3, 0.0F).texture(floatValue23, floatValue22).color(floatValue13, floatValue14, floatValue15, floatValue12);
         } else {
            bufferBuilder26.vertex(matrix4f7, floatValue2, floatValue3, 0.0F).texture(floatValue21, floatValue22).color(floatValue9, floatValue10, floatValue11, floatValue8);
            bufferBuilder26.vertex(matrix4f7, floatValue2, floatValue3 + 7.99F, 0.0F).texture(floatValue21, floatValue24).color(floatValue13, floatValue14, floatValue15, floatValue12);
            bufferBuilder26.vertex(matrix4f7, floatValue2 + floatValue20 - 1.0F, floatValue3 + 7.99F, 0.0F).texture(floatValue23, floatValue24).color(floatValue13, floatValue14, floatValue15, floatValue12);
            bufferBuilder26.vertex(matrix4f7, floatValue2 + floatValue20 - 1.0F, floatValue3, 0.0F).texture(floatValue23, floatValue22).color(floatValue9, floatValue10, floatValue11, floatValue8);
         }

         BufferRenderer.drawWithGlobalProgram(bufferBuilder26.end());
      }
   }
}
