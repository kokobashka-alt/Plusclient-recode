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

public final class AnimatedQuadShader {
   public static int a;
   public static int b;
   public static int c;
   public static int d;
   public static int e;
   public static int f;
   private static final float[] g = new float[]{6.0F, 1.0F, 1.0F, 1.0F, 2.5F, 6.0F};
   private static int h;
   private float[] i = new float[2048];
   private int[] j = new int[256];
   private int[] k = new int[256];
   private int l;

   public AnimatedQuadShader() {
   }

   public void a(float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, float floatValue7) {
      this.a(0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, intValue6, floatValue7, 0.0F, 0.0F);
   }

   public void a(float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      this.a(1, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, intValue6, 0.0F, 0.0F, 0.0F);
   }

   public void a(float floatValue1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      this.a(2, floatValue1, floatValue2, floatValue3, floatValue4, 0.0F, intValue5, 0.0F, 0.0F, 0.0F);
   }

   public void a(float floatValue1, float floatValue2, float floatValue3, float floatValue4, int intValue5, float floatValue6) {
      this.a(3, floatValue1, floatValue2, floatValue3, floatValue4, 0.0F, intValue5, floatValue6, 0.0F, 0.0F);
   }

   public void b(float floatValue1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      this.a(4, floatValue1, floatValue2, floatValue3, floatValue4, 0.0F, intValue5, 0.0F, 0.0F, 0.0F);
   }

   public void a(float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, int intValue7) {
      this.a(
         5,
         floatValue1,
         floatValue2,
         floatValue3,
         floatValue4,
         floatValue5,
         intValue6,
         (float)(intValue7 >> 16 & 0xFF) / 127.5F - 1.0F,
         (float)(intValue7 >> 8 & 0xFF) / 127.5F - 1.0F,
         (float)(intValue7 & 0xFF) / 127.5F - 1.0F
      );
   }

   public void a(int intValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, float floatValue8, float floatValue9, float floatValue10) {
      if (!(floatValue4 <= 0.0F) && !(floatValue5 <= 0.0F) && intValue7 >>> 24 != 0) {
         if (this.l == this.k.length) {
            int intValue11 = this.l * 2;
            this.i = Arrays.copyOf(this.i, intValue11 * 8);
            this.j = Arrays.copyOf(this.j, intValue11);
            this.k = Arrays.copyOf(this.k, intValue11);
         }

         int intValue12 = this.l * 8;
         this.i[intValue12] = floatValue2;
         this.i[intValue12 + 1] = floatValue3;
         this.i[intValue12 + 2] = floatValue4;
         this.i[intValue12 + 3] = floatValue5;
         this.i[intValue12 + 4] = floatValue6;
         this.i[intValue12 + 5] = floatValue8;
         this.i[intValue12 + 6] = floatValue9;
         this.i[intValue12 + 7] = floatValue10;
         this.j[this.l] = intValue7;
         this.k[this.l] = intValue1;
         this.l++;
      }
   }

   public boolean a() {
      return this.l == 0;
   }

   public void b() {
      this.l = 0;
   }

   public void a(Matrix4f matrix4f1, float floatValue2) {
      if (this.l != 0) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableDepthTest();
         RenderSystem.disableCull();
         ShaderProgram shaderProgram3 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.e);
         if (shaderProgram3 == null) {
            this.l = 0;
         } else {
            GlUniform glUniform4 = shaderProgram3.getUniform("Time");
            if (glUniform4 != null) {
               glUniform4.set(floatValue2);
            }

            BufferBuilder bufferBuilder5 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);

            for (int intValue6 = 0; intValue6 < this.l; intValue6++) {
               this.a(bufferBuilder5, matrix4f1, intValue6);
            }

            BuiltBuffer builtBuffer7 = bufferBuilder5.endNullable();
            if (builtBuffer7 != null) {
               BufferRenderer.drawWithGlobalProgram(builtBuffer7);
            }

            RenderSystem.enableCull();
            this.l = 0;
         }
      }
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, int intValue3) {
      int intValue4 = intValue3 * 8;
      int intValue5 = this.k[intValue3];
      float floatValue6 = g[intValue5];
      float floatValue7 = this.i[intValue4 + 2];
      float floatValue8 = this.i[intValue4 + 3];
      float floatValue9 = this.i[intValue4] - floatValue6;
      float floatValue10 = this.i[intValue4 + 1] - floatValue6;
      float floatValue11 = floatValue7 + floatValue6 * 2.0F;
      float floatValue12 = floatValue8 + floatValue6 * 2.0F;
      int intValue13 = Math.min(32767, Math.round(this.i[intValue4 + 4] * 64.0F));
      float floatValue14 = this.i[intValue4 + 5];
      float floatValue15 = this.i[intValue4 + 6];
      float floatValue16 = this.i[intValue4 + 7];
      int intValue17 = this.j[intValue3];
      int intValue18 = intValue17 >>> 24;
      int intValue19 = intValue17 >> 16 & 0xFF;
      int intValue20 = intValue17 >> 8 & 0xFF;
      int intValue21 = intValue17 & 0xFF;
      bufferBuilder1.vertex(matrix4f2, floatValue9, floatValue10, 0.0F).color(intValue19, intValue20, intValue21, intValue18).texture(floatValue7, floatValue8).light(intValue13, intValue5).normal(floatValue14, floatValue15, floatValue16);
      bufferBuilder1.vertex(matrix4f2, floatValue9, floatValue10 + floatValue12, 0.0F).color(intValue19, intValue20, intValue21, intValue18).texture(floatValue7, floatValue8).light(intValue13, intValue5).normal(floatValue14, floatValue15, floatValue16);
      bufferBuilder1.vertex(matrix4f2, floatValue9 + floatValue11, floatValue10 + floatValue12, 0.0F).color(intValue19, intValue20, intValue21, intValue18).texture(floatValue7, floatValue8).light(intValue13, intValue5).normal(floatValue14, floatValue15, floatValue16);
      bufferBuilder1.vertex(matrix4f2, floatValue9 + floatValue11, floatValue10, 0.0F).color(intValue19, intValue20, intValue21, intValue18).texture(floatValue7, floatValue8).light(intValue13, intValue5).normal(floatValue14, floatValue15, floatValue16);
   }
}
