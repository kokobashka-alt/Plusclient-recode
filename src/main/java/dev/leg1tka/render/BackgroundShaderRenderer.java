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
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import org.joml.Matrix4f;

public final class BackgroundShaderRenderer implements dev.leg1tka.api.ClientAccess {
   public static final dev.leg1tka.render.BackgroundShaderRenderer c = new dev.leg1tka.render.BackgroundShaderRenderer();

   private BackgroundShaderRenderer() {
   }

   public void a() {
      CustomWorld customWorld1 = CustomWorld.c;
      if (customWorld1 != null && customWorld1.u()) {
         String string3 = customWorld1.v();

         ShaderProgramKey shaderProgramKey2 = switch (string3) {
            case "Lagoon" -> dev.leg1tka.render.ShaderRegistry.G;
            case "Cosmos" -> dev.leg1tka.render.ShaderRegistry.H;
            case "Sunset" -> dev.leg1tka.render.ShaderRegistry.I;
            case "Pulse" -> dev.leg1tka.render.ShaderRegistry.J;
            case "Vortex" -> dev.leg1tka.render.ShaderRegistry.K;
            default -> dev.leg1tka.render.ShaderRegistry.F;
         };
         ShaderProgram shaderProgram12 = minecraftClient.getShaderLoader().getOrCreateProgram(shaderProgramKey2);
         if (shaderProgram12 != null) {
            int intValue13 = customWorld1.x();
            float floatValue5 = dev.leg1tka.api.utils.text.ColorUtils.p(intValue13)[0] / 255.0F;
            float floatValue6 = dev.leg1tka.api.utils.text.ColorUtils.p(intValue13)[1] / 255.0F;
            float floatValue7 = dev.leg1tka.api.utils.text.ColorUtils.p(intValue13)[2] / 255.0F;
            float floatValue8 = Math.max(0.0F, Math.min(1.0F, customWorld1.w()));
            GlUniform glUniform9 = shaderProgram12.getUniform("Time");
            if (glUniform9 != null) {
               glUniform9.set((float)(System.currentTimeMillis() % 1000000L) / 1000.0F);
            }

            GlUniform glUniform10 = shaderProgram12.getUniform("Accent");
            if (glUniform10 != null) {
               glUniform10.set(floatValue5, floatValue6, floatValue7);
            }

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.disableCull();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, floatValue8);
            RenderSystem.setShader(shaderProgramKey2);
            BufferBuilder bufferBuilder11 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            this.a(bufferBuilder11, new Matrix4f(), 100.0F);
            BufferRenderer.drawWithGlobalProgram(bufferBuilder11.end());
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
         }
      }
   }

   public void b() {
   }

   private void a(VertexConsumer vertexConsumer1, Matrix4f matrix4f2, float floatValue3) {
      this.a(vertexConsumer1, matrix4f2, 1, -floatValue3, floatValue3, -floatValue3, -floatValue3, floatValue3, floatValue3, floatValue3, floatValue3, floatValue3, floatValue3, floatValue3, -floatValue3);
      this.a(vertexConsumer1, matrix4f2, 0, -floatValue3, -floatValue3, floatValue3, -floatValue3, -floatValue3, -floatValue3, floatValue3, -floatValue3, -floatValue3, floatValue3, -floatValue3, floatValue3);
      this.a(vertexConsumer1, matrix4f2, 2, floatValue3, floatValue3, -floatValue3, floatValue3, -floatValue3, -floatValue3, -floatValue3, -floatValue3, -floatValue3, -floatValue3, floatValue3, -floatValue3);
      this.a(vertexConsumer1, matrix4f2, 4, -floatValue3, floatValue3, floatValue3, -floatValue3, -floatValue3, floatValue3, floatValue3, -floatValue3, floatValue3, floatValue3, floatValue3, floatValue3);
      this.a(vertexConsumer1, matrix4f2, 3, -floatValue3, floatValue3, -floatValue3, -floatValue3, -floatValue3, -floatValue3, -floatValue3, -floatValue3, floatValue3, -floatValue3, floatValue3, floatValue3);
      this.a(vertexConsumer1, matrix4f2, 5, floatValue3, floatValue3, floatValue3, floatValue3, -floatValue3, floatValue3, floatValue3, -floatValue3, -floatValue3, floatValue3, floatValue3, -floatValue3);
   }

   private void a(
      VertexConsumer vertexConsumer1,
      Matrix4f matrix4f2,
      int intValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      float floatValue10,
      float floatValue11,
      float floatValue12,
      float floatValue13,
      float floatValue14,
      float floatValue15
   ) {
      int intValue16 = intValue3 % 3;
      int intValue17 = intValue3 / 3;
      float floatValue18 = (float)intValue16 / 3.0F;
      float floatValue19 = (float)(intValue16 + 1) / 3.0F;
      float floatValue20 = (float)intValue17 / 2.0F;
      float floatValue21 = (float)(intValue17 + 1) / 2.0F;
      vertexConsumer1.vertex(matrix4f2, floatValue4, floatValue5, floatValue6).texture(floatValue18, floatValue20).color(255, 255, 255, 255);
      vertexConsumer1.vertex(matrix4f2, floatValue7, floatValue8, floatValue9).texture(floatValue18, floatValue21).color(255, 255, 255, 255);
      vertexConsumer1.vertex(matrix4f2, floatValue10, floatValue11, floatValue12).texture(floatValue19, floatValue21).color(255, 255, 255, 255);
      vertexConsumer1.vertex(matrix4f2, floatValue13, floatValue14, floatValue15).texture(floatValue19, floatValue20).color(255, 255, 255, 255);
   }
}
