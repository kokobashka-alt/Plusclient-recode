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
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import org.joml.Matrix4f;

public final class OccludedOutlineShader {
   private static int a;
   private static int b;
   private static int c;
   private static int d;
   private static float e;
   private static float f;
   private static float g;
   private static float h;
   private static final int[][] i = new int[][]{{0, 1}, {1, 3}, {3, 2}, {2, 0}, {4, 5}, {5, 7}, {7, 6}, {6, 4}, {0, 4}, {1, 5}, {2, 6}, {3, 7}};
   private static final int[][] j = new int[][]{{0, 1, 5, 4}, {1, 3, 7, 5}, {3, 2, 6, 7}, {2, 0, 4, 6}};
   private float[] k = new float[128];
   private int l;
   private float[] m = new float[128];
   private int n;
   private final float[] o = new float[24];
   private float p;

   public OccludedOutlineShader() {
   }

   public void a(Matrix4f matrix4f1, int intValue2) {
      this.l = 0;
      this.n = 0;
      float floatValue3 = matrix4f1.m11();
      this.p = floatValue3 > 1.0E-4F && intValue2 > 0 ? 2.0F / (floatValue3 * (float)intValue2) : 0.002F;
   }

   public void a(float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8) {
      if (this.l * 8 == this.k.length) {
         this.k = Arrays.copyOf(this.k, this.k.length * 2);
      }

      int intValue9 = this.l * 8;
      this.k[intValue9] = floatValue1;
      this.k[intValue9 + 1] = floatValue2;
      this.k[intValue9 + 2] = floatValue3;
      this.k[intValue9 + 3] = floatValue4;
      this.k[intValue9 + 4] = floatValue5;
      this.k[intValue9 + 5] = floatValue6;
      this.k[intValue9 + 6] = Float.intBitsToFloat(intValue7);
      this.k[intValue9 + 7] = Float.intBitsToFloat(intValue8);
      this.l++;
   }

   public void a(float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      if (this.n * 8 == this.m.length) {
         this.m = Arrays.copyOf(this.m, this.m.length * 2);
      }

      int intValue7 = this.n * 8;
      this.m[intValue7] = floatValue1;
      this.m[intValue7 + 1] = floatValue2;
      this.m[intValue7 + 2] = floatValue3;
      this.m[intValue7 + 3] = floatValue4;
      this.m[intValue7 + 4] = floatValue5;
      this.m[intValue7 + 5] = Float.intBitsToFloat(intValue6);
      this.n++;
   }

   public void a(Matrix4f matrix4f1, float floatValue2) {
      if (this.l != 0 || this.n != 0) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(false);
         ShaderProgram shaderProgram3 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.g);
         if (shaderProgram3 != null) {
            GlUniform glUniform4 = shaderProgram3.getUniform("Mode");
            GlUniform glUniform5 = shaderProgram3.getUniform("Hidden");
            GlUniform glUniform6 = shaderProgram3.getUniform("Time");
            if (glUniform6 != null) {
               glUniform6.set(floatValue2);
            }

            RenderSystem.depthFunc(516);
            if (glUniform5 != null) {
               glUniform5.set(1.0F);
            }

            this.a(matrix4f1, glUniform4);
            RenderSystem.depthFunc(515);
            if (glUniform5 != null) {
               glUniform5.set(0.0F);
            }

            this.a(matrix4f1, glUniform4);
         }

         RenderSystem.depthFunc(515);
         RenderSystem.depthMask(true);
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
      }
   }

   private void a(Matrix4f matrix4f1, GlUniform glUniform2) {
      if (this.l > 0) {
         this.a(glUniform2, 1, this.a(matrix4f1));
         this.a(glUniform2, 0, this.b(matrix4f1));
      }

      if (this.n > 0) {
         this.a(glUniform2, 3, this.d(matrix4f1));
         this.a(glUniform2, 2, this.c(matrix4f1));
      }
   }

   private void a(GlUniform glUniform1, int intValue2, BuiltBuffer builtBuffer3) {
      if (builtBuffer3 != null) {
         if (glUniform1 != null) {
            glUniform1.set((float)intValue2);
         }

         RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.g);
         BufferRenderer.drawWithGlobalProgram(builtBuffer3);
      }
   }

   private void a(int intValue1) {
      for (int intValue2 = 0; intValue2 < 8; intValue2++) {
         this.o[intValue2 * 3] = (intValue2 & 1) == 0 ? this.k[intValue1] : this.k[intValue1 + 3];
         this.o[intValue2 * 3 + 1] = (intValue2 & 4) == 0 ? this.k[intValue1 + 1] : this.k[intValue1 + 4];
         this.o[intValue2 * 3 + 2] = (intValue2 & 2) == 0 ? this.k[intValue1 + 2] : this.k[intValue1 + 5];
      }
   }

   private BuiltBuffer a(Matrix4f matrix4f1) {
      BufferBuilder bufferBuilder2 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

      for (int intValue3 = 0; intValue3 < this.l; intValue3++) {
         int intValue4 = intValue3 * 8;
         this.a(intValue4);
         int intValue5 = Float.floatToRawIntBits(this.k[intValue4 + 6]);
         int intValue6 = Float.floatToRawIntBits(this.k[intValue4 + 7]);
         int intValue7 = a(intValue5, 0.09F);
         int intValue8 = a(intValue6, 0.0F);

         for (int[] intValueArray12 : j) {
            for (int intValue16 : intValueArray12) {
               boolean flag17 = (intValue16 & 4) != 0;
               this.a(bufferBuilder2, matrix4f1, intValue16, 0.0F, 0.0F, flag17 ? intValue8 : intValue7);
            }
         }

         this.a(bufferBuilder2, matrix4f1, 0, 0.0F, 0.0F, intValue7);
         this.a(bufferBuilder2, matrix4f1, 1, 0.0F, 0.0F, intValue7);
         this.a(bufferBuilder2, matrix4f1, 3, 0.0F, 0.0F, intValue7);
         this.a(bufferBuilder2, matrix4f1, 2, 0.0F, 0.0F, intValue7);
      }

      return bufferBuilder2.endNullable();
   }

   private BuiltBuffer b(Matrix4f matrix4f1) {
      BufferBuilder bufferBuilder2 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

      for (int intValue3 = 0; intValue3 < this.l; intValue3++) {
         int intValue4 = intValue3 * 8;
         this.a(intValue4);
         int intValue5 = Float.floatToRawIntBits(this.k[intValue4 + 6]);
         int intValue6 = a(Float.floatToRawIntBits(this.k[intValue4 + 7]), 0.22F);

         for (int[] intValueArray10 : i) {
            int intValue11 = intValueArray10[0];
            int intValue12 = intValueArray10[1];
            this.a(bufferBuilder2, matrix4f1, intValue11, intValue12, (intValue11 & 4) != 0 ? intValue6 : intValue5, (intValue12 & 4) != 0 ? intValue6 : intValue5);
         }
      }

      return bufferBuilder2.endNullable();
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, int intValue3, int intValue4, int intValue5, int intValue6) {
      float floatValue7 = this.o[intValue3 * 3];
      float floatValue8 = this.o[intValue3 * 3 + 1];
      float floatValue9 = this.o[intValue3 * 3 + 2];
      float floatValue10 = this.o[intValue4 * 3];
      float floatValue11 = this.o[intValue4 * 3 + 1];
      float floatValue12 = this.o[intValue4 * 3 + 2];
      float floatValue13 = floatValue10 - floatValue7;
      float floatValue14 = floatValue11 - floatValue8;
      float floatValue15 = floatValue12 - floatValue9;
      float floatValue16 = (floatValue7 + floatValue10) * 0.5F;
      float floatValue17 = (floatValue8 + floatValue11) * 0.5F;
      float floatValue18 = (floatValue9 + floatValue12) * 0.5F;
      float floatValue19 = floatValue14 * floatValue18 - floatValue15 * floatValue17;
      float floatValue20 = floatValue15 * floatValue16 - floatValue13 * floatValue18;
      float floatValue21 = floatValue13 * floatValue17 - floatValue14 * floatValue16;
      float floatValue22 = (float)Math.sqrt((double)(floatValue19 * floatValue19 + floatValue20 * floatValue20 + floatValue21 * floatValue21));
      if (!(floatValue22 < 1.0E-6F)) {
         floatValue19 /= floatValue22;
         floatValue20 /= floatValue22;
         floatValue21 /= floatValue22;
         float floatValue23 = (float)Math.sqrt((double)(floatValue13 * floatValue13 + floatValue14 * floatValue14 + floatValue15 * floatValue15));
         float floatValue24 = 2.0F * this.p * (float)Math.sqrt((double)(floatValue7 * floatValue7 + floatValue8 * floatValue8 + floatValue9 * floatValue9));
         float floatValue25 = 2.0F * this.p * (float)Math.sqrt((double)(floatValue10 * floatValue10 + floatValue11 * floatValue11 + floatValue12 * floatValue12));
         bufferBuilder1.vertex(matrix4f2, floatValue7 - floatValue19 * floatValue24, floatValue8 - floatValue20 * floatValue24, floatValue9 - floatValue21 * floatValue24).texture(-1.0F, 0.0F).color(intValue5);
         bufferBuilder1.vertex(matrix4f2, floatValue10 - floatValue19 * floatValue25, floatValue11 - floatValue20 * floatValue25, floatValue12 - floatValue21 * floatValue25).texture(-1.0F, floatValue23).color(intValue6);
         bufferBuilder1.vertex(matrix4f2, floatValue10 + floatValue19 * floatValue25, floatValue11 + floatValue20 * floatValue25, floatValue12 + floatValue21 * floatValue25).texture(1.0F, floatValue23).color(intValue6);
         bufferBuilder1.vertex(matrix4f2, floatValue7 + floatValue19 * floatValue24, floatValue8 + floatValue20 * floatValue24, floatValue9 + floatValue21 * floatValue24).texture(1.0F, 0.0F).color(intValue5);
      }
   }

   private BuiltBuffer c(Matrix4f matrix4f1) {
      BufferBuilder bufferBuilder2 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

      for (int intValue3 = 0; intValue3 < this.n; intValue3++) {
         int intValue4 = intValue3 * 8;
         float floatValue5 = this.m[intValue4];
         float floatValue6 = this.m[intValue4 + 1];
         float floatValue7 = this.m[intValue4 + 2];
         float floatValue8 = this.m[intValue4 + 3] / 0.72F;
         int intValue9 = Float.floatToRawIntBits(this.m[intValue4 + 5]);
         bufferBuilder2.vertex(matrix4f1, floatValue5 - floatValue8, floatValue6, floatValue7 - floatValue8).texture(-1.0F, -1.0F).color(intValue9);
         bufferBuilder2.vertex(matrix4f1, floatValue5 - floatValue8, floatValue6, floatValue7 + floatValue8).texture(-1.0F, 1.0F).color(intValue9);
         bufferBuilder2.vertex(matrix4f1, floatValue5 + floatValue8, floatValue6, floatValue7 + floatValue8).texture(1.0F, 1.0F).color(intValue9);
         bufferBuilder2.vertex(matrix4f1, floatValue5 + floatValue8, floatValue6, floatValue7 - floatValue8).texture(1.0F, -1.0F).color(intValue9);
      }

      return bufferBuilder2.endNullable();
   }

   private BuiltBuffer d(Matrix4f matrix4f1) {
      BufferBuilder bufferBuilder2 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

      for (int intValue3 = 0; intValue3 < this.n; intValue3++) {
         int intValue4 = intValue3 * 8;
         float floatValue5 = this.m[intValue4];
         float floatValue6 = this.m[intValue4 + 1];
         float floatValue7 = this.m[intValue4 + 2];
         float floatValue8 = this.m[intValue4 + 3];
         float floatValue9 = this.m[intValue4 + 4];
         int intValue10 = Float.floatToRawIntBits(this.m[intValue4 + 5]);
         float floatValue11 = (float)Math.sqrt((double)(floatValue5 * floatValue5 + floatValue7 * floatValue7));
         if (!(floatValue11 < 1.0E-4F)) {
            float floatValue12 = floatValue7 / floatValue11 * floatValue8;
            float floatValue13 = -floatValue5 / floatValue11 * floatValue8;
            bufferBuilder2.vertex(matrix4f1, floatValue5 - floatValue12, floatValue6, floatValue7 - floatValue13).texture(-1.0F, 0.0F).color(intValue10);
            bufferBuilder2.vertex(matrix4f1, floatValue5 + floatValue12, floatValue6, floatValue7 + floatValue13).texture(1.0F, 0.0F).color(intValue10);
            bufferBuilder2.vertex(matrix4f1, floatValue5 + floatValue12, floatValue6 + floatValue9, floatValue7 + floatValue13).texture(1.0F, 1.0F).color(intValue10);
            bufferBuilder2.vertex(matrix4f1, floatValue5 - floatValue12, floatValue6 + floatValue9, floatValue7 - floatValue13).texture(-1.0F, 1.0F).color(intValue10);
         }
      }

      return bufferBuilder2.endNullable();
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, int intValue3, float floatValue4, float floatValue5, int intValue6) {
      bufferBuilder1.vertex(matrix4f2, this.o[intValue3 * 3], this.o[intValue3 * 3 + 1], this.o[intValue3 * 3 + 2]).texture(floatValue4, floatValue5).color(intValue6);
   }

   private static int a(int intValue0, float floatValue1) {
      int intValue2 = Math.round((float)(intValue0 >>> 24) / 255.0F * floatValue1 * 255.0F);
      return Math.max(0, Math.min(255, intValue2)) << 24 | intValue0 & 16777215;
   }
}
