package dev.leg1tka.render;

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
import java.util.Arrays;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import org.joml.Matrix4f;

public final class RoundedRectangleRenderer {
   private static int a;
   private static float b;
   private float[] c = new float[640];
   private int[] d = new int[128];
   private float[] e = new float[4];
   private int f;

   public RoundedRectangleRenderer() {
   }

   public void a(float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      if (this.f == this.d.length) {
         this.c = Arrays.copyOf(this.c, this.c.length * 2);
         this.d = Arrays.copyOf(this.d, this.d.length * 2);
      }

      int intValue7 = this.f * 5;
      this.c[intValue7] = floatValue1;
      this.c[intValue7 + 1] = floatValue2;
      this.c[intValue7 + 2] = floatValue3;
      this.c[intValue7 + 3] = floatValue4;
      this.c[intValue7 + 4] = floatValue5;
      this.d[this.f] = intValue6;
      this.f++;
   }

   public boolean a() {
      return this.f == 0;
   }

   public void b() {
      this.f = 0;
   }

   public void a(Matrix4f matrix4f1) {
      if (this.f != 0) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableDepthTest();
         ShaderProgram shaderProgram2 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.d);
         if (shaderProgram2 == null) {
            this.b(matrix4f1);
         } else {
            GlUniform glUniform3 = shaderProgram2.getUniform("Radius");
            GlUniform glUniform4 = shaderProgram2.getUniform("Smoothness");
            if (glUniform4 != null) {
               glUniform4.set(1.0F);
            }

            int intValue5 = this.c();

            for (int intValue6 = 0; intValue6 < intValue5; intValue6++) {
               float floatValue7 = this.e[intValue6];
               BufferBuilder bufferBuilder8 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

               for (int intValue9 = 0; intValue9 < this.f; intValue9++) {
                  if (this.c[intValue9 * 5 + 4] == floatValue7) {
                     this.a(bufferBuilder8, matrix4f1, intValue9);
                  }
               }

               BuiltBuffer builtBuffer10 = bufferBuilder8.endNullable();
               if (builtBuffer10 != null) {
                  if (glUniform3 != null) {
                     glUniform3.set(floatValue7, floatValue7, floatValue7, floatValue7);
                  }

                  RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.d);
                  BufferRenderer.drawWithGlobalProgram(builtBuffer10);
               }
            }

            RenderSystem.disableBlend();
            this.f = 0;
         }
      }
   }

   private int c() {
      int intValue1 = 0;

      for (int intValue2 = 0; intValue2 < this.f; intValue2++) {
         float floatValue3 = this.c[intValue2 * 5 + 4];
         boolean flag4 = false;

         for (int intValue5 = 0; intValue5 < intValue1; intValue5++) {
            if (this.e[intValue5] == floatValue3) {
               flag4 = true;
               break;
            }
         }

         if (!flag4) {
            if (intValue1 == this.e.length) {
               this.e = Arrays.copyOf(this.e, this.e.length * 2);
            }

            this.e[intValue1++] = floatValue3;
         }
      }

      return intValue1;
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, int intValue3) {
      int intValue4 = intValue3 * 5;
      float floatValue5 = this.c[intValue4];
      float floatValue6 = this.c[intValue4 + 1];
      float floatValue7 = this.c[intValue4 + 2];
      float floatValue8 = this.c[intValue4 + 3];
      int intValue9 = this.d[intValue3];
      int intValue10 = intValue9 >> 24 & 0xFF;
      if (intValue10 == 0) {
         intValue10 = 255;
      }

      float floatValue11 = (float)(intValue9 >> 16 & 0xFF) / 255.0F;
      float floatValue12 = (float)(intValue9 >> 8 & 0xFF) / 255.0F;
      float floatValue13 = (float)(intValue9 & 0xFF) / 255.0F;
      float floatValue14 = (float)intValue10 / 255.0F;
      float floatValue15 = 1.5F;
      float floatValue16 = 1.5F;
      float floatValue17 = floatValue5 - floatValue15 / 2.0F;
      float floatValue18 = floatValue6 - floatValue16 / 2.0F;
      float floatValue19 = floatValue7 + floatValue15;
      float floatValue20 = floatValue8 + floatValue16;
      bufferBuilder1.vertex(matrix4f2, floatValue17, floatValue18, 0.0F).texture(floatValue7, floatValue8).color(floatValue11, floatValue12, floatValue13, floatValue14);
      bufferBuilder1.vertex(matrix4f2, floatValue17, floatValue18 + floatValue20, 0.0F).texture(floatValue7, floatValue8).color(floatValue11, floatValue12, floatValue13, floatValue14);
      bufferBuilder1.vertex(matrix4f2, floatValue17 + floatValue19, floatValue18 + floatValue20, 0.0F).texture(floatValue7, floatValue8).color(floatValue11, floatValue12, floatValue13, floatValue14);
      bufferBuilder1.vertex(matrix4f2, floatValue17 + floatValue19, floatValue18, 0.0F).texture(floatValue7, floatValue8).color(floatValue11, floatValue12, floatValue13, floatValue14);
   }

   private void b(Matrix4f matrix4f1) {
      BufferBuilder bufferBuilder2 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);

      for (int intValue3 = 0; intValue3 < this.f; intValue3++) {
         int intValue4 = intValue3 * 5;
         float floatValue5 = this.c[intValue4];
         float floatValue6 = this.c[intValue4 + 1];
         float floatValue7 = this.c[intValue4 + 2];
         float floatValue8 = this.c[intValue4 + 3];
         int intValue9 = this.d[intValue3];
         int intValue10 = intValue9 >> 24 & 0xFF;
         if (intValue10 == 0) {
            intValue10 = 255;
         }

         float floatValue11 = (float)(intValue9 >> 16 & 0xFF) / 255.0F;
         float floatValue12 = (float)(intValue9 >> 8 & 0xFF) / 255.0F;
         float floatValue13 = (float)(intValue9 & 0xFF) / 255.0F;
         float floatValue14 = (float)intValue10 / 255.0F;
         bufferBuilder2.vertex(matrix4f1, floatValue5, floatValue6, 0.0F).color(floatValue11, floatValue12, floatValue13, floatValue14);
         bufferBuilder2.vertex(matrix4f1, floatValue5, floatValue6 + floatValue8, 0.0F).color(floatValue11, floatValue12, floatValue13, floatValue14);
         bufferBuilder2.vertex(matrix4f1, floatValue5 + floatValue7, floatValue6 + floatValue8, 0.0F).color(floatValue11, floatValue12, floatValue13, floatValue14);
         bufferBuilder2.vertex(matrix4f1, floatValue5 + floatValue7, floatValue6, 0.0F).color(floatValue11, floatValue12, floatValue13, floatValue14);
      }

      BuiltBuffer builtBuffer15 = bufferBuilder2.endNullable();
      if (builtBuffer15 != null) {
         RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
         BufferRenderer.drawWithGlobalProgram(builtBuffer15);
      }

      RenderSystem.disableBlend();
      this.f = 0;
   }
}
