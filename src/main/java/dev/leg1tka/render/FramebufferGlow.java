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
import dev.leg1tka.AspectDLC;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public final class FramebufferGlow implements dev.leg1tka.api.ClientAccess {
   public static final dev.leg1tka.render.FramebufferGlow c = new dev.leg1tka.render.FramebufferGlow();
   private final dev.leg1tka.render.FramebufferOutline d = new dev.leg1tka.render.FramebufferOutline();
   private Framebuffer e;
   private int f = -1;
   private int g = -1;
   private boolean h;
   private boolean i;

   private FramebufferGlow() {
   }

   public boolean a() {
      return this.h;
   }

   public void b() {
      this.e();
      if (this.e != null) {
         this.h = true;
         this.i = false;
         this.e.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         this.e.clear();
      }
   }

   public void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, float floatValue9) {
      if (this.h && this.e != null && !(floatValue4 <= 0.5F) && !(floatValue5 <= 0.5F)) {
         this.e.beginWrite(false);
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         dev.leg1tka.render.RenderPrimitives.c(matrixStack1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, floatValue9, -1);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         minecraftClient.getFramebuffer().beginWrite(false);
         this.i = true;
         dev.leg1tka.render.RenderPrimitives.e();
      }
   }

   public void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, dev.leg1tka.render.RenderExecutionMode value6) {
      if (this.h && this.i && this.e != null && !(floatValue4 <= 0.5F) && !(floatValue5 <= 0.5F)) {
         int intValue7 = this.g();
         int intValue8 = this.h();
         float floatValue9 = this.f();
         dev.leg1tka.render.RenderExecutionMode value10 = value6 != null ? value6 : dev.leg1tka.render.RenderExecutionMode.SHADER;
         dev.leg1tka.render.FramebufferOutline.BloomConfig bloomConfig11 = value10 == dev.leg1tka.render.RenderExecutionMode.THREADS ? dev.leg1tka.render.FramebufferOutline.BloomConfig.b(intValue7, intValue8, floatValue9) : dev.leg1tka.render.FramebufferOutline.BloomConfig.a(intValue7, intValue8, floatValue9);
         this.a(matrixStack1, floatValue2, floatValue3, floatValue4, floatValue5, this.e, bloomConfig11, false);
      }
   }

   private void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, Framebuffer framebuffer6, dev.leg1tka.render.FramebufferOutline.BloomConfig bloomConfig7, boolean flag8) {
      ShaderProgram shaderProgram9 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.w);
      if (shaderProgram9 != null) {
         minecraftClient.getFramebuffer().beginWrite(false);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableDepthTest();
         RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.w);
         RenderSystem.setShaderTexture(0, framebuffer6.getColorAttachment());
         float floatValue10 = (float)Math.max(1, minecraftClient.getWindow().getFramebufferWidth());
         float floatValue11 = (float)Math.max(1, minecraftClient.getWindow().getFramebufferHeight());
         float floatValue12 = (float)minecraftClient.getWindow().getScaleFactor();
         float floatValue13 = floatValue2 * floatValue12;
         float floatValue14 = floatValue3 * floatValue12;
         float floatValue15 = floatValue4 * floatValue12;
         float floatValue16 = floatValue5 * floatValue12;
         float floatValue17 = floatValue13 / floatValue10;
         float floatValue18 = 1.0F - floatValue14 / floatValue11;
         float floatValue19 = (floatValue13 + floatValue15) / floatValue10;
         float floatValue20 = 1.0F - (floatValue14 + floatValue16) / floatValue11;
         GlUniform glUniform22 = shaderProgram9.getUniform("texelSize");
         if (glUniform22 != null) {
            glUniform22.set(1.0F / floatValue10, 1.0F / floatValue11);
         }

         glUniform22 = shaderProgram9.getUniform("color");
         if (glUniform22 != null) {
            glUniform22.set(dev.leg1tka.api.utils.text.ColorUtils.c(bloomConfig7.color1()), dev.leg1tka.api.utils.text.ColorUtils.d(bloomConfig7.color1()), dev.leg1tka.api.utils.text.ColorUtils.e(bloomConfig7.color1()));
         }

         glUniform22 = shaderProgram9.getUniform("color2");
         if (glUniform22 != null) {
            glUniform22.set(dev.leg1tka.api.utils.text.ColorUtils.c(bloomConfig7.color2()), dev.leg1tka.api.utils.text.ColorUtils.d(bloomConfig7.color2()), dev.leg1tka.api.utils.text.ColorUtils.e(bloomConfig7.color2()));
         }

         glUniform22 = shaderProgram9.getUniform("time");
         if (glUniform22 != null) {
            glUniform22.set((float)(System.currentTimeMillis() % 100000L) / 1000.0F);
         }

         glUniform22 = shaderProgram9.getUniform("speed");
         if (glUniform22 != null) {
            glUniform22.set(bloomConfig7.waveSpeed());
         }

         glUniform22 = shaderProgram9.getUniform("scale");
         if (glUniform22 != null) {
            glUniform22.set(bloomConfig7.waveScale());
         }

         glUniform22 = shaderProgram9.getUniform("outline");
         if (glUniform22 != null) {
            glUniform22.set(bloomConfig7.outline());
         }

         glUniform22 = shaderProgram9.getUniform("glow");
         if (glUniform22 != null) {
            glUniform22.set(bloomConfig7.glow());
         }

         glUniform22 = shaderProgram9.getUniform("fill");
         if (glUniform22 != null) {
            glUniform22.set(bloomConfig7.fill());
         }

         glUniform22 = shaderProgram9.getUniform("alpha");
         if (glUniform22 != null) {
            glUniform22.set(bloomConfig7.alpha());
         }

         glUniform22 = shaderProgram9.getUniform("outlineOnly");
         if (glUniform22 != null) {
            glUniform22.set(flag8 ? 1.0F : 0.0F);
         }

         Matrix4f matrix4f23 = matrixStack1.peek().getPositionMatrix();
         BufferBuilder bufferBuilder24 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
         bufferBuilder24.vertex(matrix4f23, floatValue2, floatValue3, 0.0F).texture(floatValue17, floatValue18).color(1.0F, 1.0F, 1.0F, 1.0F);
         bufferBuilder24.vertex(matrix4f23, floatValue2, floatValue3 + floatValue5, 0.0F).texture(floatValue17, floatValue20).color(1.0F, 1.0F, 1.0F, 1.0F);
         bufferBuilder24.vertex(matrix4f23, floatValue2 + floatValue4, floatValue3 + floatValue5, 0.0F).texture(floatValue19, floatValue20).color(1.0F, 1.0F, 1.0F, 1.0F);
         bufferBuilder24.vertex(matrix4f23, floatValue2 + floatValue4, floatValue3, 0.0F).texture(floatValue19, floatValue18).color(1.0F, 1.0F, 1.0F, 1.0F);
         BufferRenderer.drawWithGlobalProgram(bufferBuilder24.end());
         dev.leg1tka.render.RenderPrimitives.e();
      }
   }

   public void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6) {
      this.a(matrixStack1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue6, floatValue6, floatValue6);
   }

   public void c() {
      this.h = false;
      this.i = false;
   }

   public void d() {
      this.h = false;
      this.i = false;
      this.d.c();
      if (this.e != null) {
         this.e.delete();
         this.e = null;
      }

      this.f = -1;
      this.g = -1;
   }

   private void e() {
      int intValue1 = minecraftClient.getWindow().getFramebufferWidth();
      int intValue2 = minecraftClient.getWindow().getFramebufferHeight();
      if (this.e == null || this.f != intValue1 || this.g != intValue2) {
         if (this.e != null) {
            this.e.delete();
         }

         this.e = new SimpleFramebuffer(intValue1, intValue2, false);
         this.f = intValue1;
         this.g = intValue2;
      }
   }

   private float f() {
      return 0.5882353F;
   }

   private int g() {
      if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null && AspectDLC.INSTANCE.themeStorage.b() != null) {
         dev.leg1tka.gui.themes.ThemePalette value1 = AspectDLC.INSTANCE.themeStorage.b().a();
         if (value1 == null || value1.c == null || value1.c.length == 0) {
            return dev.leg1tka.api.utils.text.ColorUtils.k(0);
         } else {
            return !"Rainbow".equals(value1.b()) ? value1.c[0] : dev.leg1tka.api.utils.text.ColorUtils.a();
         }
      } else {
         return dev.leg1tka.api.utils.text.ColorUtils.k(0);
      }
   }

   private int h() {
      if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null && AspectDLC.INSTANCE.themeStorage.b() != null) {
         dev.leg1tka.gui.themes.ThemePalette value1 = AspectDLC.INSTANCE.themeStorage.b().a();
         if (value1 == null) {
            return dev.leg1tka.api.utils.text.ColorUtils.k(180);
         } else if ("Rainbow".equals(value1.b())) {
            return dev.leg1tka.api.utils.text.ColorUtils.k(180);
         } else {
            return value1.c != null && value1.c.length > 0 ? value1.c[0] : dev.leg1tka.api.utils.text.ColorUtils.k(0);
         }
      } else {
         return dev.leg1tka.api.utils.text.ColorUtils.k(180);
      }
   }
}
