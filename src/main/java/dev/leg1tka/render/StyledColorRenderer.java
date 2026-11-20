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

import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.leg1tka.AspectDLC;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class StyledColorRenderer implements dev.leg1tka.api.ClientAccess {
   private static float c;
   private static dev.leg1tka.render.StyledColorRenderer d;
   private Framebuffer e;
   private Framebuffer f;
   private Framebuffer g;
   private final List<Framebuffer> h = new ArrayList<>();
   private int i = -1;
   private int j = -1;
   private boolean k;
   private boolean l;
   private int m = -1;
   private int n = -1;

   public StyledColorRenderer() {
   }

   public static dev.leg1tka.render.StyledColorRenderer a() {
      if (d == null) {
         d = new dev.leg1tka.render.StyledColorRenderer();
      }

      return d;
   }

   public void b() {
      ShaderHands shaderHands1 = this.g();
      if (!this.a(shaderHands1)) {
         this.e();
      } else {
         this.f();
         if (this.e != null) {
            this.a(this.e);
            this.k = true;
         }
      }
   }

   public void c() {
      ShaderHands shaderHands1 = this.g();
      if (!this.a(shaderHands1)) {
         this.e();
      } else {
         this.f();
         if (this.e != null && this.f != null && this.g != null) {
            if (this.k) {
               this.a(this.f);
               this.l = true;
            }
         }
      }
   }

   public void d() {
      if (this.l) {
         this.f();
         if (this.e != null && this.f != null && this.g != null) {
            ShaderHands shaderHands1 = this.g();
            if (!this.a(shaderHands1)) {
               this.e();
            } else {
               ShaderProgram shaderProgram2 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.y);
               if (shaderProgram2 == null) {
                  this.e();
               } else {
                  this.g.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                  this.g.clear();
                  this.g.beginWrite(false);
                  RenderSystem.disableDepthTest();
                  RenderSystem.disableBlend();
                  RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.y);
                  RenderSystem.setShaderTexture(0, this.e.getColorAttachment());
                  RenderSystem.setShaderTexture(1, this.f.getColorAttachment());
                  int intValue3 = this.e.getDepthAttachment();
                  int intValue4 = this.f.getDepthAttachment();
                  if (intValue3 != 0 && intValue3 != this.m) {
                     this.b(intValue3);
                     this.m = intValue3;
                  }

                  if (intValue4 != 0 && intValue4 != this.n) {
                     this.b(intValue4);
                     this.n = intValue4;
                  }

                  RenderSystem.setShaderTexture(2, intValue3);
                  RenderSystem.setShaderTexture(3, intValue4);
                  this.i();
                  RenderSystem.enableDepthTest();
                  float floatValue5 = shaderHands1.h.f();
                  float floatValue6 = shaderHands1.i.f();
                  float floatValue7 = shaderHands1.j.f();
                  float floatValue8 = shaderHands1.g.f();
                  boolean flag9 = floatValue5 > 0.001F;
                  boolean flag10 = floatValue6 > 0.001F && floatValue7 > 0.001F;
                  int intValue11 = AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow") ? dev.leg1tka.api.utils.text.ColorUtils.k(0) : AspectDLC.INSTANCE.themeStorage.b().a().c[0];
                  if (shaderHands1.d.b("Красивый")) {
                     this.a(shaderHands1, intValue11, intValue11, floatValue5, floatValue6, floatValue7, floatValue8);
                     this.e();
                  } else {
                     int intValue13 = 0;
                     if (flag9) {
                        int intValue14 = Math.max(3, Math.min(8, 4 + Math.round(floatValue8 * 0.7F)));
                        intValue13 = this.a(intValue14);
                     }

                     minecraftClient.getFramebuffer().beginWrite(true);
                     RenderSystem.enableBlend();
                     RenderSystem.colorMask(true, true, true, false);
                     RenderSystem.disableDepthTest();
                     ShaderProgram shaderProgram16 = flag9 ? minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.A) : null;
                     if (shaderProgram16 != null) {
                        RenderSystem.blendFuncSeparate(SrcFactor.SRC_ALPHA, DstFactor.ONE, SrcFactor.ZERO, DstFactor.ONE);
                        RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.A);
                        RenderSystem.setShaderTexture(0, intValue13);
                        RenderSystem.setShaderTexture(1, this.g.getColorAttachment());
                        this.a(shaderProgram16, "color", dev.leg1tka.api.utils.text.ColorUtils.c(intValue11), dev.leg1tka.api.utils.text.ColorUtils.d(intValue11), dev.leg1tka.api.utils.text.ColorUtils.e(intValue11));
                        this.a(shaderProgram16, "color2", dev.leg1tka.api.utils.text.ColorUtils.c(intValue11), dev.leg1tka.api.utils.text.ColorUtils.d(intValue11), dev.leg1tka.api.utils.text.ColorUtils.e(intValue11));
                        this.a(shaderProgram16, "exposure", 1.0F + floatValue5 * 1.8F);
                        this.i();
                     }

                     if (flag10) {
                        ShaderProgram shaderProgram15 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.z);
                        if (shaderProgram15 == null) {
                           this.h();
                           this.e();
                           return;
                        }

                        RenderSystem.blendFuncSeparate(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA, SrcFactor.ZERO, DstFactor.ONE);
                        RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.z);
                        RenderSystem.setShaderTexture(0, this.g.getColorAttachment());
                        this.a(shaderProgram15, "color", dev.leg1tka.api.utils.text.ColorUtils.c(intValue11), dev.leg1tka.api.utils.text.ColorUtils.d(intValue11), dev.leg1tka.api.utils.text.ColorUtils.e(intValue11));
                        this.a(shaderProgram15, "fill", floatValue6);
                        this.a(shaderProgram15, "alpha", floatValue7);
                        this.i();
                     }

                     this.h();
                     this.e();
                  }
               }
            }
         }
      }
   }

   public void e() {
      this.k = false;
      this.l = false;
      this.m = -1;
      this.n = -1;
   }

   private int a(int intValue1) {
      this.c(intValue1);
      if (this.h.isEmpty()) {
         return this.g.getColorAttachment();
      } else {
         int intValue2 = this.g.getColorAttachment();
         ShaderProgram shaderProgram3 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.B);
         ShaderProgram shaderProgram4 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.C);
         if (shaderProgram3 != null && shaderProgram4 != null) {
            for (int intValue5 = 0; intValue5 < intValue1; intValue5++) {
               Framebuffer framebuffer6 = (Framebuffer)this.h.get(intValue5);
               framebuffer6.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
               framebuffer6.clear();
               framebuffer6.beginWrite(true);
               RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.B);
               RenderSystem.setShaderTexture(0, intValue2);
               this.a(shaderProgram3, framebuffer6.textureWidth, framebuffer6.textureHeight, 1.0F + (float)intValue5);
               this.i();
               intValue2 = framebuffer6.getColorAttachment();
            }

            for (int intValue7 = intValue1 - 1; intValue7 >= 1; intValue7--) {
               Framebuffer framebuffer8 = (Framebuffer)this.h.get(intValue7 - 1);
               framebuffer8.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
               framebuffer8.clear();
               framebuffer8.beginWrite(true);
               RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.C);
               RenderSystem.setShaderTexture(0, intValue2);
               this.a(shaderProgram4, framebuffer8.textureWidth, framebuffer8.textureHeight, 1.0F + (float)intValue7);
               this.a(shaderProgram4, "color", 1.0F, 1.0F, 1.0F);
               this.i();
               intValue2 = framebuffer8.getColorAttachment();
            }

            minecraftClient.getFramebuffer().beginWrite(true);
            return intValue2;
         } else {
            return intValue2;
         }
      }
   }

   private void a(Framebuffer framebuffer1) {
      int intValue2 = GL11.glGetInteger(36010);
      int intValue3 = GL11.glGetInteger(36006);
      GL30.glBindFramebuffer(36008, minecraftClient.getFramebuffer().fbo);
      GL30.glBindFramebuffer(36009, framebuffer1.fbo);
      GL30.glBlitFramebuffer(0, 0, this.i, this.j, 0, 0, this.i, this.j, 16640, 9728);
      GL30.glBindFramebuffer(36008, intValue2);
      GL30.glBindFramebuffer(36009, intValue3);
      minecraftClient.getFramebuffer().beginWrite(true);
   }

   private void b(int intValue1) {
      RenderSystem.bindTexture(intValue1);
      GL11.glTexParameteri(3553, 34892, 0);
      GL11.glTexParameteri(3553, 10241, 9728);
      GL11.glTexParameteri(3553, 10240, 9728);
      RenderSystem.bindTexture(0);
   }

   private void f() {
      int intValue1 = minecraftClient.getWindow().getFramebufferWidth();
      int intValue2 = minecraftClient.getWindow().getFramebufferHeight();
      if (intValue1 != this.i || intValue2 != this.j || this.e == null || this.f == null || this.g == null) {
         if (this.e != null) {
            this.e.delete();
         }

         if (this.f != null) {
            this.f.delete();
         }

         if (this.g != null) {
            this.g.delete();
         }

         for (Framebuffer framebuffer4 : this.h) {
            framebuffer4.delete();
         }

         this.h.clear();
         this.e = new SimpleFramebuffer(intValue1, intValue2, true);
         this.f = new SimpleFramebuffer(intValue1, intValue2, true);
         this.g = new SimpleFramebuffer(intValue1, intValue2, true);
         this.i = intValue1;
         this.j = intValue2;
         this.m = -1;
         this.n = -1;
      }
   }

   private void c(int intValue1) {
      while (this.h.size() > intValue1) {
         int intValue2 = this.h.size() - 1;
         ((Framebuffer)this.h.get(intValue2)).delete();
         this.h.remove(intValue2);
      }

      for (int intValue6 = 0; intValue6 < intValue1; intValue6++) {
         int intValue3 = Math.max(2, this.i >> intValue6 + 1);
         int intValue4 = Math.max(2, this.j >> intValue6 + 1);
         if (intValue6 >= this.h.size()) {
            SimpleFramebuffer simpleFramebuffer5 = new SimpleFramebuffer(intValue3, intValue4, false);
            this.b(simpleFramebuffer5);
            this.h.add(simpleFramebuffer5);
         } else {
            Framebuffer framebuffer7 = (Framebuffer)this.h.get(intValue6);
            if (framebuffer7.textureWidth != intValue3 || framebuffer7.textureHeight != intValue4) {
               framebuffer7.delete();
               SimpleFramebuffer simpleFramebuffer8 = new SimpleFramebuffer(intValue3, intValue4, false);
               this.b(simpleFramebuffer8);
               this.h.set(intValue6, simpleFramebuffer8);
            }
         }
      }
   }

   private void b(Framebuffer framebuffer1) {
      RenderSystem.bindTexture(framebuffer1.getColorAttachment());
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      RenderSystem.bindTexture(0);
   }

   private ShaderHands g() {
      return AspectDLC.INSTANCE != null && ModuleManager.a != null ? ModuleManager.ar : null;
   }

   private void a(ShaderHands shaderHands1, int intValue2, int intValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      ShaderProgram shaderProgram8 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.w);
      if (shaderProgram8 != null) {
         minecraftClient.getFramebuffer().beginWrite(false);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableDepthTest();
         RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.w);
         RenderSystem.setShaderTexture(0, this.g.getColorAttachment());
         this.a(
            shaderProgram8, "texelSize", 1.0F / (float)Math.max(1, minecraftClient.getWindow().getFramebufferWidth()), 1.0F / (float)Math.max(1, minecraftClient.getWindow().getFramebufferHeight())
         );
         this.a(shaderProgram8, "color", dev.leg1tka.api.utils.text.ColorUtils.c(intValue2), dev.leg1tka.api.utils.text.ColorUtils.d(intValue2), dev.leg1tka.api.utils.text.ColorUtils.e(intValue2));
         this.a(shaderProgram8, "color2", dev.leg1tka.api.utils.text.ColorUtils.c(intValue3), dev.leg1tka.api.utils.text.ColorUtils.d(intValue3), dev.leg1tka.api.utils.text.ColorUtils.e(intValue3));
         this.a(shaderProgram8, "time", (float)(System.currentTimeMillis() % 100000L) / 1000.0F);
         this.a(shaderProgram8, "speed", shaderHands1.e.f());
         this.a(shaderProgram8, "scale", shaderHands1.f.f());
         this.a(shaderProgram8, "outline", floatValue7);
         this.a(shaderProgram8, "glow", floatValue4);
         this.a(shaderProgram8, "fill", floatValue5);
         this.a(shaderProgram8, "alpha", floatValue6);
         this.a(shaderProgram8, "outlineOnly", 0.0F);
         this.i();
         RenderSystem.enableDepthTest();
         RenderSystem.disableBlend();
         RenderSystem.defaultBlendFunc();
         this.h();
      }
   }

   private void h() {
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, 0);
      RenderSystem.setShaderTexture(1, 0);
      RenderSystem.setShaderTexture(2, 0);
      RenderSystem.setShaderTexture(3, 0);
      minecraftClient.getFramebuffer().beginWrite(true);
   }

   private boolean a(ShaderHands shaderHands1) {
      if (shaderHands1 != null && shaderHands1.isEnabled()) {
         boolean flag2 = shaderHands1.h.f() > 0.001F;
         boolean flag3 = shaderHands1.i.f() > 0.001F && shaderHands1.j.f() > 0.001F;
         return flag2 || flag3;
      } else {
         return false;
      }
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

   private void a(ShaderProgram shaderProgram1, int intValue2, int intValue3, float floatValue4) {
      this.a(shaderProgram1, "uSize", (float)Math.max(1, intValue2), (float)Math.max(1, intValue3));
      this.a(shaderProgram1, "uOffset", floatValue4, floatValue4);
      this.a(shaderProgram1, "uHalfPixel", 0.5F / (float)Math.max(1, intValue2), 0.5F / (float)Math.max(1, intValue3));
   }

   private void i() {
      float floatValue1 = (float)Math.max(minecraftClient.getWindow().getScaledWidth(), 1);
      float floatValue2 = (float)Math.max(minecraftClient.getWindow().getScaledHeight(), 1);
      BufferBuilder bufferBuilder3 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      bufferBuilder3.vertex(0.0F, 0.0F, 0.0F).texture(0.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder3.vertex(0.0F, floatValue2, 0.0F).texture(0.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder3.vertex(floatValue1, floatValue2, 0.0F).texture(1.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder3.vertex(floatValue1, 0.0F, 0.0F).texture(1.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder3.end());
   }
}
