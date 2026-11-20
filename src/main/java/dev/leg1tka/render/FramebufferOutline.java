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
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.util.math.Box;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public final class FramebufferOutline implements dev.leg1tka.api.ClientAccess {
   private Framebuffer c;
   private int d = -1;
   private int e = -1;
   private boolean f;

   public FramebufferOutline() {
   }

   public boolean a() {
      return this.f;
   }

   public void b() {
      this.f = false;
   }

   public void c() {
      this.b();
      if (this.c != null) {
         this.c.delete();
         this.c = null;
      }

      this.d = -1;
      this.e = -1;
   }

   public void a(Matrix4f matrix4f1, Box box2) {
      this.d();
      if (this.c != null) {
         this.f = true;
         this.c.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         this.c.clear();
         this.e();
         this.c.beginWrite(false);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
         this.b(matrix4f1, box2);
         RenderSystem.depthMask(true);
         RenderSystem.disableDepthTest();
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         minecraftClient.getFramebuffer().beginWrite(false);
      }
   }

   public void a(dev.leg1tka.render.FramebufferOutline.BloomConfig bloomConfig1) {
      if (this.f && this.c != null) {
         this.a(this.c, bloomConfig1);
      }
   }

   public void a(Framebuffer framebuffer1, dev.leg1tka.render.FramebufferOutline.BloomConfig bloomConfig2) {
      if (framebuffer1 != null && framebuffer1.getColorAttachment() != 0) {
         ShaderProgram shaderProgram3 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.w);
         if (shaderProgram3 != null) {
            minecraftClient.getFramebuffer().beginWrite(false);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.w);
            RenderSystem.setShaderTexture(0, framebuffer1.getColorAttachment());
            this.a(
               shaderProgram3,
               "texelSize",
               1.0F / (float)Math.max(1, minecraftClient.getWindow().getFramebufferWidth()),
               1.0F / (float)Math.max(1, minecraftClient.getWindow().getFramebufferHeight())
            );
            this.a(shaderProgram3, "color", dev.leg1tka.api.utils.text.ColorUtils.c(bloomConfig2.color1()), dev.leg1tka.api.utils.text.ColorUtils.d(bloomConfig2.color1()), dev.leg1tka.api.utils.text.ColorUtils.e(bloomConfig2.color1()));
            this.a(shaderProgram3, "color2", dev.leg1tka.api.utils.text.ColorUtils.c(bloomConfig2.color2()), dev.leg1tka.api.utils.text.ColorUtils.d(bloomConfig2.color2()), dev.leg1tka.api.utils.text.ColorUtils.e(bloomConfig2.color2()));
            this.a(shaderProgram3, "time", (float)(System.currentTimeMillis() % 100000L) / 1000.0F);
            this.a(shaderProgram3, "speed", bloomConfig2.waveSpeed());
            this.a(shaderProgram3, "scale", bloomConfig2.waveScale());
            this.a(shaderProgram3, "outline", bloomConfig2.outline());
            this.a(shaderProgram3, "glow", bloomConfig2.glow());
            this.a(shaderProgram3, "fill", bloomConfig2.fill());
            this.a(shaderProgram3, "alpha", bloomConfig2.alpha());
            this.a(shaderProgram3, "outlineOnly", 0.0F);
            this.f();
            RenderSystem.enableDepthTest();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderTexture(0, 0);
         }
      }
   }

   private void d() {
      int intValue1 = minecraftClient.getWindow().getFramebufferWidth();
      int intValue2 = minecraftClient.getWindow().getFramebufferHeight();
      if (this.c == null || this.d != intValue1 || this.e != intValue2) {
         if (this.c != null) {
            this.c.delete();
         }

         this.c = new SimpleFramebuffer(intValue1, intValue2, true);
         this.d = intValue1;
         this.e = intValue2;
      }
   }

   private void e() {
      if (this.c != null) {
         int intValue1 = GL11.glGetInteger(36010);
         int intValue2 = GL11.glGetInteger(36006);
         int intValue3 = minecraftClient.getWindow().getFramebufferWidth();
         int intValue4 = minecraftClient.getWindow().getFramebufferHeight();
         GL30.glBindFramebuffer(36008, minecraftClient.getFramebuffer().fbo);
         GL30.glBindFramebuffer(36009, this.c.fbo);
         GL30.glBlitFramebuffer(0, 0, intValue3, intValue4, 0, 0, intValue3, intValue4, 256, 9728);
         GL30.glBindFramebuffer(36008, intValue1);
         GL30.glBindFramebuffer(36009, intValue2);
      }
   }

   private void b(Matrix4f matrix4f1, Box box2) {
      BufferBuilder bufferBuilder3 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
      byte byteValue4 = -1;
      bufferBuilder3.vertex(matrix4f1, (float)box2.minX, (float)box2.minY, (float)box2.minZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.minX, (float)box2.minY, (float)box2.maxZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.maxX, (float)box2.minY, (float)box2.maxZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.maxX, (float)box2.minY, (float)box2.minZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.minX, (float)box2.maxY, (float)box2.minZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.maxX, (float)box2.maxY, (float)box2.minZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.maxX, (float)box2.maxY, (float)box2.maxZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.minX, (float)box2.maxY, (float)box2.maxZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.minX, (float)box2.minY, (float)box2.minZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.maxX, (float)box2.minY, (float)box2.minZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.maxX, (float)box2.maxY, (float)box2.minZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.minX, (float)box2.maxY, (float)box2.minZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.minX, (float)box2.minY, (float)box2.maxZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.minX, (float)box2.maxY, (float)box2.maxZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.maxX, (float)box2.maxY, (float)box2.maxZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.maxX, (float)box2.minY, (float)box2.maxZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.minX, (float)box2.minY, (float)box2.minZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.minX, (float)box2.maxY, (float)box2.minZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.minX, (float)box2.maxY, (float)box2.maxZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.minX, (float)box2.minY, (float)box2.maxZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.maxX, (float)box2.minY, (float)box2.minZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.maxX, (float)box2.minY, (float)box2.maxZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.maxX, (float)box2.maxY, (float)box2.maxZ).color(byteValue4);
      bufferBuilder3.vertex(matrix4f1, (float)box2.maxX, (float)box2.maxY, (float)box2.minZ).color(byteValue4);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder3.end());
   }

   private void f() {
      BufferBuilder bufferBuilder1 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      float floatValue2 = (float)Math.max(minecraftClient.getWindow().getScaledWidth(), 1);
      float floatValue3 = (float)Math.max(minecraftClient.getWindow().getScaledHeight(), 1);
      bufferBuilder1.vertex(0.0F, 0.0F, 0.0F).texture(0.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder1.vertex(0.0F, floatValue3, 0.0F).texture(0.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder1.vertex(floatValue2, floatValue3, 0.0F).texture(1.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder1.vertex(floatValue2, 0.0F, 0.0F).texture(1.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder1.end());
   }

   private void a(ShaderProgram shaderProgram1, String string2, float floatValue3) {
      GlUniform glUniform4 = shaderProgram1.getUniform(string2);
      if (glUniform4 != null) {
         glUniform4.set(floatValue3);
      }
   }

   private void a(ShaderProgram shaderProgram1, String string2, float floatValue3, float floatValue4) {
      GlUniform glUniform5 = shaderProgram1.getUniform(string2);
      if (glUniform5 != null) {
         glUniform5.set(floatValue3, floatValue4);
      }
   }

   private void a(ShaderProgram shaderProgram1, String string2, float floatValue3, float floatValue4, float floatValue5) {
      GlUniform glUniform6 = shaderProgram1.getUniform(string2);
      if (glUniform6 != null) {
         glUniform6.set(floatValue3, floatValue4, floatValue5);
      }
   }

   public static record BloomConfig(int color1, int color2, float waveSpeed, float waveScale, float outline, float glow, float fill, float alpha) {
      public static BloomConfig a(int intValue0, int intValue1) {
         return new BloomConfig(intValue0, intValue1, 1.2F, 1.0F, 1.1F, 1.0F, 0.6F, 1.0F);
      }

      public static BloomConfig b(int intValue0, int intValue1) {
         return new BloomConfig(intValue0, intValue1, 1.35F, 1.0F, 1.65F, 2.6F, 0.95F, 1.0F);
      }

      public static BloomConfig c(int intValue0, int intValue1) {
         return new BloomConfig(intValue0, intValue1, 1.55F, 1.0F, 2.15F, 4.2F, 1.0F, 1.0F);
      }

      public static BloomConfig a(int intValue0, int intValue1, float floatValue2) {
         float floatValue3 = Math.min(1.0F, floatValue2 * 1.18F);
         return new BloomConfig(intValue0, intValue1, 1.35F, 1.0F, 1.25F, 1.85F, 0.88F, floatValue3);
      }

      public static BloomConfig b(int intValue0, int intValue1, float floatValue2) {
         return new BloomConfig(intValue0, intValue1, 1.2F, 1.0F, 1.05F, 0.45F, 0.32F, Math.min(1.0F, floatValue2 * 0.92F));
      }
   }
}
