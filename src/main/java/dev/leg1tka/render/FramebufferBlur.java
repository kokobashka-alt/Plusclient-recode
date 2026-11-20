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

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.systems.RenderSystem;
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

public class FramebufferBlur implements dev.leg1tka.api.ClientAccess {
   private static final Supplier c = Suppliers.memoize(() -> {
      SimpleFramebuffer simpleFramebuffer0 = new SimpleFramebuffer(1920, 1024, false);
      a(simpleFramebuffer0);
      return simpleFramebuffer0;
   });

   public FramebufferBlur() {
   }

   private static void a(Framebuffer framebuffer0) {
      RenderSystem.bindTexture(framebuffer0.getColorAttachment());
      RenderSystem.texParameter(3553, 10241, 9729);
      RenderSystem.texParameter(3553, 10240, 9729);
      RenderSystem.bindTexture(0);
   }

   public void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      if (!(floatValue4 <= 0.5F) && !(floatValue5 <= 0.5F)) {
         Framebuffer framebuffer8 = minecraftClient.getFramebuffer();
         if (framebuffer8 != null && framebuffer8.getColorAttachment() > 0) {
            SimpleFramebuffer simpleFramebuffer9 = (SimpleFramebuffer)c.get();
            if (simpleFramebuffer9.textureWidth != framebuffer8.textureWidth || simpleFramebuffer9.textureHeight != framebuffer8.textureHeight) {
               simpleFramebuffer9.resize(framebuffer8.textureWidth, framebuffer8.textureHeight);
               a(simpleFramebuffer9);
            }

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableCull();
            simpleFramebuffer9.beginWrite(false);
            framebuffer8.draw(simpleFramebuffer9.textureWidth, simpleFramebuffer9.textureHeight);
            framebuffer8.beginWrite(false);
            RenderSystem.setShaderTexture(0, simpleFramebuffer9.getColorAttachment());
            ShaderProgram shaderProgram10 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.L);
            a(shaderProgram10, floatValue4, floatValue5, floatValue6, floatValue7);
            Matrix4f matrix4f11 = matrixStack1.peek().getPositionMatrix();
            BufferBuilder bufferBuilder12 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
            bufferBuilder12.vertex(matrix4f11, floatValue2, floatValue3, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
            bufferBuilder12.vertex(matrix4f11, floatValue2, floatValue3 + floatValue5, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
            bufferBuilder12.vertex(matrix4f11, floatValue2 + floatValue4, floatValue3 + floatValue5, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
            bufferBuilder12.vertex(matrix4f11, floatValue2 + floatValue4, floatValue3, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.L);
            BufferRenderer.drawWithGlobalProgram(bufferBuilder12.end());
            RenderSystem.setShaderTexture(0, 0);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         }
      }
   }

   private static void a(ShaderProgram shaderProgram0, float floatValue1, float floatValue2, float floatValue3, float floatValue4) {
      GlUniform glUniform5 = shaderProgram0.getUniform("Size");
      GlUniform glUniform6 = shaderProgram0.getUniform("Radius");
      GlUniform glUniform7 = shaderProgram0.getUniform("Smoothness");
      GlUniform glUniform8 = shaderProgram0.getUniform("BlurRadius");
      if (glUniform5 != null) {
         glUniform5.set(floatValue1, floatValue2);
      }

      if (glUniform6 != null) {
         glUniform6.set(floatValue3, floatValue3, floatValue3, floatValue3);
      }

      if (glUniform7 != null) {
         glUniform7.set(1.0F);
      }

      if (glUniform8 != null) {
         glUniform8.set(floatValue4);
      }
   }
}
