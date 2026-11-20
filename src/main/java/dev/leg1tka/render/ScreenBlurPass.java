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
import lombok.Generated;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import org.lwjgl.opengl.GL30;

public class ScreenBlurPass implements dev.leg1tka.api.ClientAccess {
   private static dev.leg1tka.render.ScreenBlurPass c;
   private static Framebuffer d;
   private static Framebuffer e;
   private int f = -1;
   private int g = -1;
   private long h = 0L;
   private boolean i = true;
   private float j = 1.0F;
   private final int k = 4;

   public ScreenBlurPass() {
   }

   public static dev.leg1tka.render.ScreenBlurPass a() {
      if (c == null) {
         c = new dev.leg1tka.render.ScreenBlurPass();
      }

      return c;
   }

   public void b() {
      boolean flag1 = this.i;
      this.i = false;
      if (flag1) {
         this.g();
      }
   }

   public void c() {
      this.i = true;
   }

   private void g() {
      long longValue1 = System.currentTimeMillis();
      if (longValue1 - this.h >= 16L) {
         this.h = longValue1;
         int intValue3 = minecraftClient.getWindow().getFramebufferWidth();
         int intValue4 = minecraftClient.getWindow().getFramebufferHeight();
         if (d == null || e == null || this.f != intValue3 || this.g != intValue4) {
            if (d != null) {
               d.delete();
            }

            if (e != null) {
               e.delete();
            }

            d = new SimpleFramebuffer(intValue3, intValue4, false);
            e = new SimpleFramebuffer(intValue3, intValue4, false);
            this.a(d);
            this.a(e);
            this.f = intValue3;
            this.g = intValue4;
         }

         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         ShaderProgram shaderProgram5 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.l);
         ShaderProgram shaderProgram6 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.m);
         d.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         d.clear();
         d.beginWrite(true);
         RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.l);
         minecraftClient.getFramebuffer().beginRead();
         RenderSystem.setShaderTexture(0, minecraftClient.getFramebuffer().getColorAttachment());
         this.a(shaderProgram5, intValue3, intValue4);
         this.a((float)minecraftClient.getWindow().getScaledWidth(), (float)minecraftClient.getWindow().getScaledHeight());
         minecraftClient.getFramebuffer().endRead();
         d.endWrite();
         Framebuffer[] framebufferArray7 = new Framebuffer[]{d, e};

         for (int intValue8 = 1; intValue8 < 4; intValue8++) {
            int intValue9 = (intValue8 + 1) % 2;
            int intValue10 = intValue8 % 2;
            Framebuffer framebuffer11 = framebufferArray7[intValue9];
            Framebuffer framebuffer12 = framebufferArray7[intValue10];
            framebuffer12.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            framebuffer12.clear();
            framebuffer12.beginWrite(true);
            RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.l);
            framebuffer11.beginRead();
            RenderSystem.setShaderTexture(0, framebuffer11.getColorAttachment());
            this.a(shaderProgram5, framebuffer11.textureWidth, framebuffer11.textureHeight);
            this.a((float)minecraftClient.getWindow().getScaledWidth(), (float)minecraftClient.getWindow().getScaledHeight());
            framebuffer11.endRead();
            framebuffer12.endWrite();
         }

         for (int intValue13 = 0; intValue13 < 4; intValue13++) {
            int intValue14 = intValue13 % 2;
            int intValue15 = (intValue13 + 1) % 2;
            Framebuffer framebuffer16 = framebufferArray7[intValue14];
            Framebuffer framebuffer17 = framebufferArray7[intValue15];
            framebuffer17.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            framebuffer17.clear();
            framebuffer17.beginWrite(true);
            RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.m);
            framebuffer16.beginRead();
            RenderSystem.setShaderTexture(0, framebuffer16.getColorAttachment());
            this.a(shaderProgram6, framebuffer16.textureWidth, framebuffer16.textureHeight);
            this.a((float)minecraftClient.getWindow().getScaledWidth(), (float)minecraftClient.getWindow().getScaledHeight());
            framebuffer16.endRead();
            framebuffer17.endWrite();
         }

         RenderSystem.disableBlend();
         minecraftClient.getFramebuffer().beginWrite(true);
         RenderSystem.setShaderTexture(0, 0);
      }
   }

   private void a(Framebuffer framebuffer1) {
      RenderSystem.bindTexture(framebuffer1.getColorAttachment());
      GL30.glTexParameteri(3553, 10241, 9729);
      GL30.glTexParameteri(3553, 10240, 9729);
      RenderSystem.bindTexture(0);
   }

   private void a(ShaderProgram shaderProgram1, int intValue2, int intValue3) {
      GlUniform glUniform4 = shaderProgram1.getUniform("Resolution");
      GlUniform glUniform5 = shaderProgram1.getUniform("Offset");
      GlUniform glUniform6 = shaderProgram1.getUniform("Saturation");
      GlUniform glUniform7 = shaderProgram1.getUniform("TintIntensity");
      GlUniform glUniform8 = shaderProgram1.getUniform("TintColor");
      if (glUniform4 != null) {
         glUniform4.set(1.0F / (float)intValue2, 1.0F / (float)intValue3);
      }

      if (glUniform5 != null) {
         glUniform5.set(this.j);
      }

      if (glUniform6 != null) {
         glUniform6.set(1.0F);
      }

      if (glUniform7 != null) {
         glUniform7.set(0.0F);
      }

      if (glUniform8 != null) {
         glUniform8.set(1.0F, 1.0F, 1.0F);
      }
   }

   private void a(float floatValue1, float floatValue2) {
      BufferBuilder bufferBuilder3 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      bufferBuilder3.vertex(0.0F, 0.0F, 0.0F).texture(0.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder3.vertex(0.0F, floatValue2, 0.0F).texture(0.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder3.vertex(floatValue1, floatValue2, 0.0F).texture(1.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder3.vertex(floatValue1, 0.0F, 0.0F).texture(1.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder3.end());
   }

   public static int d() {
      a().c();
      return d != null ? d.getColorAttachment() : 0;
   }

   @Generated
   public static Framebuffer e() {
      return d;
   }

   @Generated
   public static Framebuffer f() {
      return e;
   }

   @Generated
   public void a(float floatValue1) {
      this.j = floatValue1;
   }
}
