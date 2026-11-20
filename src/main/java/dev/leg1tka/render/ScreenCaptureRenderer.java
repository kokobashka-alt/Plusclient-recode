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
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;

public final class ScreenCaptureRenderer implements dev.leg1tka.api.ClientAccess {
   private static Framebuffer c;

   private ScreenCaptureRenderer() {
   }

   public static void a() {
      int intValue0 = minecraftClient.getWindow().getFramebufferWidth();
      int intValue1 = minecraftClient.getWindow().getFramebufferHeight();
      if (c == null || c.textureWidth != intValue0 || c.textureHeight != intValue1) {
         if (c != null) {
            c.delete();
         }

         c = new SimpleFramebuffer(intValue0, intValue1, false);
      }

      c.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
      c.clear();
      c.beginWrite(true);
      GL20.glBlendEquationSeparate(32774, 32776);
   }

   public static void a(DrawContext drawContext0, float floatValue1, float floatValue2) {
      drawContext0.draw();
      GL14.glBlendEquation(32774);
      minecraftClient.getFramebuffer().beginWrite(true);
      if (!(floatValue1 <= 0.002F)) {
         float floatValue3 = (float)minecraftClient.getWindow().getScaledWidth();
         float floatValue4 = (float)minecraftClient.getWindow().getScaledHeight();
         float floatValue5 = floatValue3 / 2.0F;
         float floatValue6 = floatValue4 / 2.0F;
         float floatValue7 = floatValue5 - floatValue5 * floatValue2;
         float floatValue8 = floatValue6 - floatValue6 * floatValue2;
         float floatValue9 = floatValue5 + floatValue5 * floatValue2;
         float floatValue10 = floatValue6 + floatValue6 * floatValue2;
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(1, 771);
         RenderSystem.disableDepthTest();
         RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
         RenderSystem.setShaderTexture(0, c.getColorAttachment());
         Matrix4f matrix4f11 = drawContext0.getMatrices().peek().getPositionMatrix();
         BufferBuilder bufferBuilder12 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
         bufferBuilder12.vertex(matrix4f11, floatValue7, floatValue8, 0.0F).texture(0.0F, 1.0F).color(floatValue1, floatValue1, floatValue1, floatValue1);
         bufferBuilder12.vertex(matrix4f11, floatValue7, floatValue10, 0.0F).texture(0.0F, 0.0F).color(floatValue1, floatValue1, floatValue1, floatValue1);
         bufferBuilder12.vertex(matrix4f11, floatValue9, floatValue10, 0.0F).texture(1.0F, 0.0F).color(floatValue1, floatValue1, floatValue1, floatValue1);
         bufferBuilder12.vertex(matrix4f11, floatValue9, floatValue8, 0.0F).texture(1.0F, 1.0F).color(floatValue1, floatValue1, floatValue1, floatValue1);
         BufferRenderer.drawWithGlobalProgram(bufferBuilder12.end());
         RenderSystem.setShaderTexture(0, 0);
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
      }
   }
}
