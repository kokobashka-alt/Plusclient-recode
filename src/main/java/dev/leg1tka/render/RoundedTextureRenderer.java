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
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public final class RoundedTextureRenderer {
   private static float a;
   private static float b;
   private static float c;
   private static float d;
   private final float e;
   private final float f;
   private Identifier[] g = new Identifier[64];
   private float[] h = new float[64];
   private float[] i = new float[64];
   private int[] j = new int[64];
   private boolean[] k = new boolean[64];
   private int l;

   public RoundedTextureRenderer(float floatValue1, float floatValue2) {
      this.e = floatValue1;
      this.f = floatValue2;
   }

   public void a(Identifier identifier1, float floatValue2, float floatValue3, int intValue4) {
      if (identifier1 != null) {
         if (this.l == this.g.length) {
            int intValue5 = this.l * 2;
            this.g = Arrays.copyOf(this.g, intValue5);
            this.h = Arrays.copyOf(this.h, intValue5);
            this.i = Arrays.copyOf(this.i, intValue5);
            this.j = Arrays.copyOf(this.j, intValue5);
            this.k = Arrays.copyOf(this.k, intValue5);
         }

         this.g[this.l] = identifier1;
         this.h[this.l] = floatValue2;
         this.i[this.l] = floatValue3;
         this.j[this.l] = intValue4;
         this.l++;
      }
   }

   public boolean a() {
      return this.l == 0;
   }

   public void b() {
      Arrays.fill(this.g, 0, this.l, null);
      this.l = 0;
   }

   public void a(Matrix4f matrix4f1) {
      if (this.l != 0) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableDepthTest();
         ShaderProgram shaderProgram2 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.f);
         if (shaderProgram2 == null) {
            this.b();
         } else {
            GlUniform glUniform3 = shaderProgram2.getUniform("Size");
            GlUniform glUniform4 = shaderProgram2.getUniform("Radius");
            if (glUniform3 != null) {
               glUniform3.set(this.e, this.e);
            }

            if (glUniform4 != null) {
               glUniform4.set(this.f);
            }

            Arrays.fill(this.k, 0, this.l, false);

            for (int intValue5 = 0; intValue5 < this.l; intValue5++) {
               if (!this.k[intValue5]) {
                  Identifier identifier6 = this.g[intValue5];
                  BufferBuilder bufferBuilder7 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

                  for (int intValue8 = intValue5; intValue8 < this.l; intValue8++) {
                     if (!this.k[intValue8] && identifier6.equals(this.g[intValue8])) {
                        this.k[intValue8] = true;
                        this.a(bufferBuilder7, matrix4f1, this.h[intValue8], this.i[intValue8], 0.125F, this.j[intValue8]);
                        this.a(bufferBuilder7, matrix4f1, this.h[intValue8], this.i[intValue8], 0.625F, this.j[intValue8]);
                     }
                  }

                  BuiltBuffer builtBuffer9 = bufferBuilder7.endNullable();
                  if (builtBuffer9 != null) {
                     RenderSystem.setShaderTexture(0, identifier6);
                     RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.f);
                     BufferRenderer.drawWithGlobalProgram(builtBuffer9);
                  }
               }
            }

            RenderSystem.setShaderTexture(0, 0);
            this.b();
         }
      }
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      float floatValue7 = floatValue5 + 0.125F;
      float floatValue8 = 0.25F;
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, 0.0F).texture(floatValue5, 0.125F).color(intValue6);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4 + this.e, 0.0F).texture(floatValue5, floatValue8).color(intValue6);
      bufferBuilder1.vertex(matrix4f2, floatValue3 + this.e, floatValue4 + this.e, 0.0F).texture(floatValue7, floatValue8).color(intValue6);
      bufferBuilder1.vertex(matrix4f2, floatValue3 + this.e, floatValue4, 0.0F).texture(floatValue7, 0.125F).color(intValue6);
   }
}
