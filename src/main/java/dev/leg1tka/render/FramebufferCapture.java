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

import com.mojang.blaze3d.systems.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class FramebufferCapture {
   private static dev.leg1tka.render.FramebufferCapture a;
   private static final MinecraftClient b = MinecraftClient.getInstance();
   private Framebuffer c;
   private int d;
   private int e;
   private float f = 10.0F;
   private float g = 1.0F;
   private Color h = Color.WHITE;
   private Matrix4f i;
   private int j;
   private static int k;
   private static int l;

   public FramebufferCapture() {
   }

   public static dev.leg1tka.render.FramebufferCapture a() {
      if (a == null) {
         a = new dev.leg1tka.render.FramebufferCapture();
      }

      return a;
   }

   private void c() {
      int intValue1 = b.getWindow().getFramebufferWidth();
      int intValue2 = b.getWindow().getFramebufferHeight();
      if (this.c == null || this.d != intValue1 || this.e != intValue2) {
         if (this.c != null) {
            this.c.delete();
         }

         this.c = new SimpleFramebuffer(intValue1, intValue2, false);
         this.d = intValue1;
         this.e = intValue2;
      }
   }

   public void a(float floatValue1, Color color2) {
      this.a(floatValue1, 1.0F, color2);
   }

   public void a(float floatValue1, float floatValue2, Color color3) {
      this.c();
      this.f = floatValue1;
      this.g = floatValue2;
      this.h = color3;
      this.i = new Matrix4f(RenderSystem.getProjectionMatrix());
      this.j = GL11.glGetInteger(36006);
      GL30.glBindFramebuffer(36160, this.c.fbo);
      GL11.glViewport(0, 0, this.d, this.e);
      RenderSystem.clearColor(0.0F, 0.0F, 0.0F, 0.0F);
      RenderSystem.clear(16384);
      RenderSystem.setProjectionMatrix(this.i, ProjectionType.ORTHOGRAPHIC);
   }

   public void a(MatrixStack matrixStack1, dev.leg1tka.render.font.RenderCallback value2) {
      GL30.glBindFramebuffer(36160, this.j);
      GL11.glViewport(0, 0, b.getWindow().getFramebufferWidth(), b.getWindow().getFramebufferHeight());
      RenderSystem.setProjectionMatrix(this.i, ProjectionType.ORTHOGRAPHIC);
      this.a(matrixStack1);
      if (value2 != null) {
         value2.a();
      }
   }

   private float a(float floatValue1, float floatValue2) {
      return (float)Math.exp((double)(-(floatValue1 * floatValue1) / (2.0F * floatValue2 * floatValue2)));
   }

   private void a(MatrixStack matrixStack1) {
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(770, 1);
      RenderSystem.disableDepthTest();
      int intValue2 = b.getWindow().getScaledWidth();
      int intValue3 = b.getWindow().getScaledHeight();
      RenderSystem.setShaderTexture(0, this.c.getColorAttachment());
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      Matrix4f matrix4f4 = matrixStack1.peek().getPositionMatrix();
      float floatValue5 = (float)this.h.getRed() / 255.0F;
      float floatValue6 = (float)this.h.getGreen() / 255.0F;
      float floatValue7 = (float)this.h.getBlue() / 255.0F;
      float floatValue8 = (float)this.h.getAlpha() / 255.0F * this.g;
      float floatValue9 = this.f * 0.4F;
      float[] floatValueArray10 = new float[6];
      float floatValue11 = 0.0F;

      for (int intValue12 = 0; intValue12 < 6; intValue12++) {
         float floatValue13 = this.f * (float)(intValue12 + 1) / 6.0F;
         floatValueArray10[intValue12] = this.a(floatValue13, floatValue9);
         floatValue11 += floatValueArray10[intValue12];
      }

      for (int intValue25 = 0; intValue25 < 6; intValue25++) {
         floatValueArray10[intValue25] /= floatValue11;
      }

      BufferBuilder bufferBuilder26 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

      for (int intValue27 = 0; intValue27 < 6; intValue27++) {
         float floatValue14 = this.f * (float)(intValue27 + 1) / 6.0F;
         float floatValue15 = floatValue8 * floatValueArray10[intValue27] * 0.7F;
         if (!(floatValue15 < 0.001F)) {
            floatValue15 = Math.min(floatValue15, 1.0F);

            for (int intValue16 = 0; intValue16 < 12; intValue16++) {
               float floatValue17 = (float)((double)intValue16 * 2.0 * Math.PI) / 12.0F;
               float floatValue18 = (float)Math.cos((double)floatValue17) * floatValue14;
               float floatValue19 = (float)Math.sin((double)floatValue17) * floatValue14;
               bufferBuilder26.vertex(matrix4f4, floatValue18, floatValue19, 0.0F).texture(0.0F, 1.0F).color(floatValue5, floatValue6, floatValue7, floatValue15);
               bufferBuilder26.vertex(matrix4f4, floatValue18, (float)intValue3 + floatValue19, 0.0F).texture(0.0F, 0.0F).color(floatValue5, floatValue6, floatValue7, floatValue15);
               bufferBuilder26.vertex(matrix4f4, (float)intValue2 + floatValue18, (float)intValue3 + floatValue19, 0.0F).texture(1.0F, 0.0F).color(floatValue5, floatValue6, floatValue7, floatValue15);
               bufferBuilder26.vertex(matrix4f4, (float)intValue2 + floatValue18, floatValue19, 0.0F).texture(1.0F, 1.0F).color(floatValue5, floatValue6, floatValue7, floatValue15);
               if (intValue27 > 0) {
                  float floatValue20 = (float)(((double)intValue16 + 0.5) * 2.0 * Math.PI) / 12.0F;
                  float floatValue21 = floatValue14 * 0.6F;
                  float floatValue22 = (float)Math.cos((double)floatValue20) * floatValue21;
                  float floatValue23 = (float)Math.sin((double)floatValue20) * floatValue21;
                  float floatValue24 = floatValue15 * 0.5F;
                  bufferBuilder26.vertex(matrix4f4, floatValue22, floatValue23, 0.0F).texture(0.0F, 1.0F).color(floatValue5, floatValue6, floatValue7, floatValue24);
                  bufferBuilder26.vertex(matrix4f4, floatValue22, (float)intValue3 + floatValue23, 0.0F).texture(0.0F, 0.0F).color(floatValue5, floatValue6, floatValue7, floatValue24);
                  bufferBuilder26.vertex(matrix4f4, (float)intValue2 + floatValue22, (float)intValue3 + floatValue23, 0.0F).texture(1.0F, 0.0F).color(floatValue5, floatValue6, floatValue7, floatValue24);
                  bufferBuilder26.vertex(matrix4f4, (float)intValue2 + floatValue22, floatValue23, 0.0F).texture(1.0F, 1.0F).color(floatValue5, floatValue6, floatValue7, floatValue24);
               }
            }
         }
      }

      BufferRenderer.drawWithGlobalProgram(bufferBuilder26.end());
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
      RenderSystem.setShaderTexture(0, 0);
      RenderSystem.defaultBlendFunc();
      RenderSystem.enableDepthTest();
      RenderSystem.disableBlend();
   }

   public static void a(float floatValue0, int intValue1, dev.leg1tka.render.font.RenderCallback value2, MatrixStack matrixStack3) {
      a(floatValue0, 1.0F, intValue1, value2, matrixStack3);
   }

   public static void a(float floatValue0, float floatValue1, int intValue2, dev.leg1tka.render.font.RenderCallback value3, MatrixStack matrixStack4) {
      int intValue5 = intValue2 >> 24 & 0xFF;
      int intValue6 = intValue2 >> 16 & 0xFF;
      int intValue7 = intValue2 >> 8 & 0xFF;
      int intValue8 = intValue2 & 0xFF;
      if (intValue5 == 0) {
         intValue5 = 255;
      }

      dev.leg1tka.render.FramebufferCapture value9 = a();
      value9.a(floatValue0, floatValue1, new Color(intValue6, intValue7, intValue8, intValue5));
      value3.a();
      value9.a(matrixStack4, value3);
   }

   public void b() {
      if (this.c != null) {
         this.c.delete();
         this.c = null;
      }
   }
}
