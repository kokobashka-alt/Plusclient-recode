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
import java.awt.Color;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Generated;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.BlockPos;
import org.joml.Matrix4f;

public final class RenderPrimitives implements dev.leg1tka.api.ClientAccess {
   private static final ConcurrentHashMap c = new ConcurrentHashMap();
   private static final UUID d = new UUID(0L, 0L);

   public static void drawBoxLines(BufferBuilder buffer, Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b, float a) {
      buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a);

      buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a);

      buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a);
   }

   public static void drawBoxLines(BufferBuilder buffer, Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, int r, int g, int b, int a) {
      buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a);

      buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a);

      buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a);
   }

   public static void drawFilledBox(BufferBuilder buffer, Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b, float a) {
      buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a);

      buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a);

      buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a);

      buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a);

      buffer.vertex(matrix, x1, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x1, y1, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x1, y2, z1).color(r, g, b, a);

      buffer.vertex(matrix, x2, y1, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z1).color(r, g, b, a);
      buffer.vertex(matrix, x2, y2, z2).color(r, g, b, a);
      buffer.vertex(matrix, x2, y1, z2).color(r, g, b, a);
   }

   public static void drawFilledBox(BufferBuilder buffer, Matrix4f matrix, Box box, int color) {
      float r = (float)(color >> 16 & 0xFF) / 255.0F;
      float g = (float)(color >> 8 & 0xFF) / 255.0F;
      float b = (float)(color & 0xFF) / 255.0F;
      float a = (float)(color >> 24 & 0xFF) / 255.0F;
      drawFilledBox(buffer, matrix, (float)box.minX, (float)box.minY, (float)box.minZ, (float)box.maxX, (float)box.maxY, (float)box.maxZ, r, g, b, a);
   }

   public static void drawOutlinedBox(MatrixStack matrices, Box box, int r, int g, int b, int a) {
      drawOutlinedBox(matrices.peek().getPositionMatrix(), box, r, g, b, a);
   }

   public static void drawOutlinedBox(MatrixStack matrices, Box box, float r, float g, float b, float a) {
      drawOutlinedBox(matrices.peek().getPositionMatrix(), box, r, g, b, a);
   }

   public static void drawOutlinedBox(Matrix4f matrix, Box box, int r, int g, int b, int a) {
      BufferBuilder buffer = Tessellator.getInstance().begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
      drawBoxLines(buffer, matrix, (float)box.minX, (float)box.minY, (float)box.minZ, (float)box.maxX, (float)box.maxY, (float)box.maxZ, r, g, b, a);
      BufferRenderer.drawWithGlobalProgram(buffer.end());
   }

   public static void drawOutlinedBox(Matrix4f matrix, Box box, float r, float g, float b, float a) {
      BufferBuilder buffer = Tessellator.getInstance().begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
      drawBoxLines(buffer, matrix, (float)box.minX, (float)box.minY, (float)box.minZ, (float)box.maxX, (float)box.maxY, (float)box.maxZ, r, g, b, a);
      BufferRenderer.drawWithGlobalProgram(buffer.end());
   }

   private static int g() {
      return 150;
   }

   private static int a(int intValue0) {
      int intValue1 = dev.leg1tka.api.utils.text.ColorUtils.t(intValue0);
      int intValue2 = (intValue1 <= 0 ? 255 : intValue1) * g() / 255;
      return dev.leg1tka.api.utils.text.ColorUtils.d(intValue0, MathHelper.clamp(intValue2, 0, 255));
   }

   public static boolean a() {
      return false;
   }

   public static boolean b() {
      return false;
   }

   public static boolean c() {
      return false;
   }

   public static dev.leg1tka.render.RenderExecutionMode d() {
      return dev.leg1tka.render.RenderExecutionMode.SHADER;
   }

   public static void a(BufferBuilder bufferBuilder0) {
      if (bufferBuilder0 != null) {
         try {
            BufferRenderer.drawWithGlobalProgram(bufferBuilder0.end());
         } catch (IllegalStateException illegalStateException2) {
         }
      }
   }

   public static void e() {
      minecraftClient.getFramebuffer().beginWrite(false);
      RenderSystem.enableDepthTest();
      RenderSystem.depthMask(true);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.colorMask(true, true, true, true);
      RenderSystem.setShaderTexture(0, 0);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, boolean flag7) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue5, floatValue5, floatValue5, intValue6, flag7, dev.leg1tka.render.RenderExecutionMode.SHADER);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, boolean flag7, dev.leg1tka.render.RenderExecutionMode value8) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue5, floatValue5, floatValue5, intValue6, flag7, value8);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, int intValue9) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, intValue9, true, dev.leg1tka.render.RenderExecutionMode.SHADER);
   }

   public static void a(
      MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, int intValue9, boolean flag10
   ) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, intValue9, flag10, dev.leg1tka.render.RenderExecutionMode.SHADER);
   }

   public static void a(
      MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, int intValue9, boolean flag10, dev.leg1tka.render.RenderExecutionMode value11
   ) {
      if (b() && !(floatValue3 <= 0.5F) && !(floatValue4 <= 0.5F)) {
         dev.leg1tka.render.RenderExecutionMode value12 = value11 != null ? value11 : dev.leg1tka.render.RenderExecutionMode.SHADER;
         a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, intValue9, value12);
         if (flag10 && dev.leg1tka.render.FramebufferGlow.c.a()) {
            dev.leg1tka.render.FramebufferGlow value13 = dev.leg1tka.render.FramebufferGlow.c;
            value13.a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8);
            value13.a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, value12);
         }

         if (value12 == dev.leg1tka.render.RenderExecutionMode.THREADS && floatValue3 >= 22.0F && floatValue4 >= 12.0F) {
            long longValue15 = dev.leg1tka.render.RenderQuads.a(floatValue1, floatValue2, floatValue3, floatValue4);
            dev.leg1tka.render.RenderQuads.a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, intValue9, longValue15);
         }
      }
   }

   private static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, int intValue9, dev.leg1tka.render.RenderExecutionMode value10) {
      dev.leg1tka.render.ScreenBlurPass.a().a(dev.leg1tka.render.RenderInterpolation.e());
      dev.leg1tka.render.ScreenBlurPass.a().c();
      int intValue11 = dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.api.utils.text.ColorUtils.d(intValue9, dev.leg1tka.api.utils.text.ColorUtils.a(), 0.4F), 255);
      int intValue12 = dev.leg1tka.api.utils.text.ColorUtils.a(245, 248, 255, 255);
      float floatValue13 = MathHelper.clamp((float)g() / 255.0F * 0.94F, 0.3F, 1.0F);
      float floatValue14 = dev.leg1tka.render.RenderInterpolation.b();
      float floatValue15 = 0.016F + floatValue14 * 0.052F;
      float floatValue16 = value10 == dev.leg1tka.render.RenderExecutionMode.THREADS ? floatValue13 * 0.58F : floatValue13 * 0.78F;
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, intValue12, floatValue16, 2.75F, intValue11, 0.34F, false, 0.52F, floatValue15, 0.15F, false);
      e();
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, int intValue7) {
      if (b()) {
         a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, intValue7, true);
      } else {
         b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, a(intValue6));
      }
   }

   public static void a(DrawContext drawContext0, ItemStack itemStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5) {
      if (drawContext0 != null && itemStack1 != null && !itemStack1.isEmpty()) {
         MatrixStack matrixStack6 = drawContext0.getMatrices();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         matrixStack6.push();
         matrixStack6.translate(floatValue2, floatValue3, floatValue5);
         matrixStack6.scale(floatValue4, floatValue4, 1.0F);
         drawContext0.drawItem(itemStack1, 0, 0);
         matrixStack6.pop();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(true);
      }
   }

   public static void a(
      MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, int intValue7, int intValue8, int intValue9, int intValue10, int intValue11
   ) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      ShaderProgram shaderProgram12 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.t);
      Matrix4f matrix4f13 = matrixStack0.peek().getPositionMatrix();
      GlUniform glUniform14 = shaderProgram12.getUniform("Size");
      GlUniform glUniform15 = shaderProgram12.getUniform("Radius");
      GlUniform glUniform16 = shaderProgram12.getUniform("Smoothness");
      GlUniform glUniform17 = shaderProgram12.getUniform("LeftTopColor");
      GlUniform glUniform18 = shaderProgram12.getUniform("LeftBottomColor");
      GlUniform glUniform19 = shaderProgram12.getUniform("CenterTopColor");
      GlUniform glUniform20 = shaderProgram12.getUniform("CenterBottomColor");
      GlUniform glUniform21 = shaderProgram12.getUniform("RightTopColor");
      GlUniform glUniform22 = shaderProgram12.getUniform("RightBottomColor");
      if (glUniform14 != null) {
         glUniform14.set(floatValue3, floatValue4);
      }

      if (glUniform15 != null) {
         glUniform15.set(floatValue5, floatValue5, floatValue5, floatValue5);
      }

      if (glUniform16 != null) {
         glUniform16.set(1.0F);
      }

      if (glUniform17 != null) {
         int intValue23 = intValue6 >> 24 & 0xFF;
         if (intValue23 == 0) {
            intValue23 = 255;
         }

         glUniform17.set((float)(intValue6 >> 16 & 0xFF) / 255.0F, (float)(intValue6 >> 8 & 0xFF) / 255.0F, (float)(intValue6 & 0xFF) / 255.0F, (float)intValue23 / 255.0F);
      }

      if (glUniform18 != null) {
         int intValue24 = intValue7 >> 24 & 0xFF;
         if (intValue24 == 0) {
            intValue24 = 255;
         }

         glUniform18.set((float)(intValue7 >> 16 & 0xFF) / 255.0F, (float)(intValue7 >> 8 & 0xFF) / 255.0F, (float)(intValue7 & 0xFF) / 255.0F, (float)intValue24 / 255.0F);
      }

      if (glUniform19 != null) {
         int intValue25 = intValue8 >> 24 & 0xFF;
         if (intValue25 == 0) {
            intValue25 = 255;
         }

         glUniform19.set((float)(intValue8 >> 16 & 0xFF) / 255.0F, (float)(intValue8 >> 8 & 0xFF) / 255.0F, (float)(intValue8 & 0xFF) / 255.0F, (float)intValue25 / 255.0F);
      }

      if (glUniform20 != null) {
         int intValue26 = intValue9 >> 24 & 0xFF;
         if (intValue26 == 0) {
            intValue26 = 255;
         }

         glUniform20.set((float)(intValue9 >> 16 & 0xFF) / 255.0F, (float)(intValue9 >> 8 & 0xFF) / 255.0F, (float)(intValue9 & 0xFF) / 255.0F, (float)intValue26 / 255.0F);
      }

      if (glUniform21 != null) {
         int intValue27 = intValue10 >> 24 & 0xFF;
         if (intValue27 == 0) {
            intValue27 = 255;
         }

         glUniform21.set((float)(intValue10 >> 16 & 0xFF) / 255.0F, (float)(intValue10 >> 8 & 0xFF) / 255.0F, (float)(intValue10 & 0xFF) / 255.0F, (float)intValue27 / 255.0F);
      }

      if (glUniform22 != null) {
         int intValue28 = intValue11 >> 24 & 0xFF;
         if (intValue28 == 0) {
            intValue28 = 255;
         }

         glUniform22.set((float)(intValue11 >> 16 & 0xFF) / 255.0F, (float)(intValue11 >> 8 & 0xFF) / 255.0F, (float)(intValue11 & 0xFF) / 255.0F, (float)intValue28 / 255.0F);
      }

      BufferBuilder bufferBuilder29 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
      bufferBuilder29.vertex(matrix4f13, floatValue1, floatValue2, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder29.vertex(matrix4f13, floatValue1, floatValue2 + floatValue4, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder29.vertex(matrix4f13, floatValue1 + floatValue3, floatValue2 + floatValue4, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder29.vertex(matrix4f13, floatValue1 + floatValue3, floatValue2, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.t);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder29.end());
      RenderSystem.disableBlend();
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8, int intValue9, int intValue10) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      ShaderProgram shaderProgram11 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.o);
      Matrix4f matrix4f12 = matrixStack0.peek().getPositionMatrix();
      float floatValue13 = floatValue3 + floatValue6 * 2.0F;
      float floatValue14 = floatValue4 + floatValue6 * 2.0F;
      float floatValue15 = floatValue1 - floatValue6;
      float floatValue16 = floatValue2 - floatValue6;
      GlUniform glUniform17 = shaderProgram11.getUniform("Size");
      GlUniform glUniform18 = shaderProgram11.getUniform("Softness");
      GlUniform glUniform19 = shaderProgram11.getUniform("Radius");
      GlUniform glUniform20 = shaderProgram11.getUniform("TopLeftColor");
      GlUniform glUniform21 = shaderProgram11.getUniform("TopRightColor");
      GlUniform glUniform22 = shaderProgram11.getUniform("BottomLeftColor");
      GlUniform glUniform23 = shaderProgram11.getUniform("BottomRightColor");
      if (glUniform17 != null) {
         glUniform17.set(floatValue13, floatValue14);
      }

      if (glUniform18 != null) {
         glUniform18.set(floatValue6);
      }

      if (glUniform19 != null) {
         glUniform19.set(floatValue5);
      }

      if (glUniform20 != null) {
         int intValue24 = intValue7 >> 24 & 0xFF;
         if (intValue24 == 0) {
            intValue24 = 255;
         }

         glUniform20.set((float)(intValue7 >> 16 & 0xFF) / 255.0F, (float)(intValue7 >> 8 & 0xFF) / 255.0F, (float)(intValue7 & 0xFF) / 255.0F, (float)intValue24 / 255.0F);
      }

      if (glUniform21 != null) {
         int intValue25 = intValue8 >> 24 & 0xFF;
         if (intValue25 == 0) {
            intValue25 = 255;
         }

         glUniform21.set((float)(intValue8 >> 16 & 0xFF) / 255.0F, (float)(intValue8 >> 8 & 0xFF) / 255.0F, (float)(intValue8 & 0xFF) / 255.0F, (float)intValue25 / 255.0F);
      }

      if (glUniform22 != null) {
         int intValue26 = intValue9 >> 24 & 0xFF;
         if (intValue26 == 0) {
            intValue26 = 255;
         }

         glUniform22.set((float)(intValue9 >> 16 & 0xFF) / 255.0F, (float)(intValue9 >> 8 & 0xFF) / 255.0F, (float)(intValue9 & 0xFF) / 255.0F, (float)intValue26 / 255.0F);
      }

      if (glUniform23 != null) {
         int intValue27 = intValue10 >> 24 & 0xFF;
         if (intValue27 == 0) {
            intValue27 = 255;
         }

         glUniform23.set((float)(intValue10 >> 16 & 0xFF) / 255.0F, (float)(intValue10 >> 8 & 0xFF) / 255.0F, (float)(intValue10 & 0xFF) / 255.0F, (float)intValue27 / 255.0F);
      }

      BufferBuilder bufferBuilder28 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
      bufferBuilder28.vertex(matrix4f12, floatValue15, floatValue16, 0.0F).texture(0.0F, 0.0F);
      bufferBuilder28.vertex(matrix4f12, floatValue15, floatValue16 + floatValue14, 0.0F).texture(0.0F, 1.0F);
      bufferBuilder28.vertex(matrix4f12, floatValue15 + floatValue13, floatValue16 + floatValue14, 0.0F).texture(1.0F, 1.0F);
      bufferBuilder28.vertex(matrix4f12, floatValue15 + floatValue13, floatValue16, 0.0F).texture(1.0F, 0.0F);
      RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.o);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder28.end());
      RenderSystem.disableBlend();
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, int intValue7, int intValue8, int intValue9) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, 10.0F, intValue6, intValue7, intValue8, intValue9);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, intValue7, intValue7, intValue7, intValue7);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, 10.0F, intValue6, intValue6, intValue6, intValue6);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, 0.0F, 10.0F, intValue5, intValue5, intValue5, intValue5);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, intValue7, intValue7, intValue8, intValue8);
   }

   public static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, intValue7, intValue8, intValue7, intValue8);
   }

   public static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, int intValue9) {
      a(matrixStack0, floatValue1 + floatValue7, floatValue2 + floatValue8, floatValue3, floatValue4, floatValue5, floatValue6, intValue9, intValue9, intValue9, intValue9);
   }

   public static void a(
      MatrixStack matrixStack0,
      float floatValue1,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      int intValue9,
      int intValue10,
      int intValue11,
      int intValue12
   ) {
      a(matrixStack0, floatValue1 + floatValue7, floatValue2 + floatValue8, floatValue3, floatValue4, floatValue5, floatValue6, intValue9, intValue10, intValue11, intValue12);
   }

   public static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      ShaderProgram shaderProgram8 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.q);
      if (shaderProgram8 == null) {
         RenderSystem.disableBlend();
      } else {
         Matrix4f matrix4f9 = matrixStack0.peek().getPositionMatrix();
         float floatValue10 = floatValue3 + floatValue6 * 2.0F;
         float floatValue11 = floatValue4 + floatValue6 * 2.0F;
         float floatValue12 = floatValue1 - floatValue6;
         float floatValue13 = floatValue2 - floatValue6;
         GlUniform glUniform14 = shaderProgram8.getUniform("Size");
         GlUniform glUniform15 = shaderProgram8.getUniform("Softness");
         GlUniform glUniform16 = shaderProgram8.getUniform("Radius");
         if (glUniform14 != null) {
            glUniform14.set(floatValue10, floatValue11);
         }

         if (glUniform15 != null) {
            glUniform15.set(floatValue6);
         }

         if (glUniform16 != null) {
            glUniform16.set(floatValue5);
         }

         int intValue17 = intValue7 >> 24 & 0xFF;
         if (intValue17 == 0) {
            intValue17 = 255;
         }

         float floatValue18 = (float)(intValue7 >> 16 & 0xFF) / 255.0F;
         float floatValue19 = (float)(intValue7 >> 8 & 0xFF) / 255.0F;
         float floatValue20 = (float)(intValue7 & 0xFF) / 255.0F;
         float floatValue21 = (float)intValue17 / 255.0F;
         String[] stringArray22 = new String[]{"TopLeftColor", "TopRightColor", "BottomLeftColor", "BottomRightColor"};

         for (String string26 : stringArray22) {
            GlUniform glUniform27 = shaderProgram8.getUniform(string26);
            if (glUniform27 != null) {
               glUniform27.set(floatValue18, floatValue19, floatValue20, floatValue21);
            }
         }

         BufferBuilder bufferBuilder28 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
         bufferBuilder28.vertex(matrix4f9, floatValue12, floatValue13, 0.0F).texture(0.0F, 0.0F);
         bufferBuilder28.vertex(matrix4f9, floatValue12, floatValue13 + floatValue11, 0.0F).texture(0.0F, 1.0F);
         bufferBuilder28.vertex(matrix4f9, floatValue12 + floatValue10, floatValue13 + floatValue11, 0.0F).texture(1.0F, 1.0F);
         bufferBuilder28.vertex(matrix4f9, floatValue12 + floatValue10, floatValue13, 0.0F).texture(1.0F, 0.0F);
         RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.q);
         BufferRenderer.drawWithGlobalProgram(bufferBuilder28.end());
         RenderSystem.disableBlend();
      }
   }

   public static void a(
      MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8, int intValue9, int intValue10, int intValue11, int intValue12
   ) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      ShaderProgram shaderProgram13 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.p);
      Matrix4f matrix4f14 = matrixStack0.peek().getPositionMatrix();
      float floatValue15 = floatValue3 + floatValue6 * 2.0F;
      float floatValue16 = floatValue4 + floatValue6 * 2.0F;
      float floatValue17 = floatValue1 - floatValue6;
      float floatValue18 = floatValue2 - floatValue6;
      GlUniform glUniform19 = shaderProgram13.getUniform("Size");
      GlUniform glUniform20 = shaderProgram13.getUniform("Softness");
      GlUniform glUniform21 = shaderProgram13.getUniform("Radius");
      GlUniform glUniform22 = shaderProgram13.getUniform("LeftTopColor");
      GlUniform glUniform23 = shaderProgram13.getUniform("LeftBottomColor");
      GlUniform glUniform24 = shaderProgram13.getUniform("CenterTopColor");
      GlUniform glUniform25 = shaderProgram13.getUniform("CenterBottomColor");
      GlUniform glUniform26 = shaderProgram13.getUniform("RightTopColor");
      GlUniform glUniform27 = shaderProgram13.getUniform("RightBottomColor");
      if (glUniform19 != null) {
         glUniform19.set(floatValue15, floatValue16);
      }

      if (glUniform20 != null) {
         glUniform20.set(floatValue6);
      }

      if (glUniform21 != null) {
         glUniform21.set(floatValue5);
      }

      if (glUniform22 != null) {
         int intValue28 = intValue7 >> 24 & 0xFF;
         if (intValue28 == 0) {
            intValue28 = 255;
         }

         glUniform22.set((float)(intValue7 >> 16 & 0xFF) / 255.0F, (float)(intValue7 >> 8 & 0xFF) / 255.0F, (float)(intValue7 & 0xFF) / 255.0F, (float)intValue28 / 255.0F);
      }

      if (glUniform23 != null) {
         int intValue29 = intValue8 >> 24 & 0xFF;
         if (intValue29 == 0) {
            intValue29 = 255;
         }

         glUniform23.set((float)(intValue8 >> 16 & 0xFF) / 255.0F, (float)(intValue8 >> 8 & 0xFF) / 255.0F, (float)(intValue8 & 0xFF) / 255.0F, (float)intValue29 / 255.0F);
      }

      if (glUniform24 != null) {
         int intValue30 = intValue9 >> 24 & 0xFF;
         if (intValue30 == 0) {
            intValue30 = 255;
         }

         glUniform24.set((float)(intValue9 >> 16 & 0xFF) / 255.0F, (float)(intValue9 >> 8 & 0xFF) / 255.0F, (float)(intValue9 & 0xFF) / 255.0F, (float)intValue30 / 255.0F);
      }

      if (glUniform25 != null) {
         int intValue31 = intValue10 >> 24 & 0xFF;
         if (intValue31 == 0) {
            intValue31 = 255;
         }

         glUniform25.set((float)(intValue10 >> 16 & 0xFF) / 255.0F, (float)(intValue10 >> 8 & 0xFF) / 255.0F, (float)(intValue10 & 0xFF) / 255.0F, (float)intValue31 / 255.0F);
      }

      if (glUniform26 != null) {
         int intValue32 = intValue11 >> 24 & 0xFF;
         if (intValue32 == 0) {
            intValue32 = 255;
         }

         glUniform26.set((float)(intValue11 >> 16 & 0xFF) / 255.0F, (float)(intValue11 >> 8 & 0xFF) / 255.0F, (float)(intValue11 & 0xFF) / 255.0F, (float)intValue32 / 255.0F);
      }

      if (glUniform27 != null) {
         int intValue33 = intValue12 >> 24 & 0xFF;
         if (intValue33 == 0) {
            intValue33 = 255;
         }

         glUniform27.set((float)(intValue12 >> 16 & 0xFF) / 255.0F, (float)(intValue12 >> 8 & 0xFF) / 255.0F, (float)(intValue12 & 0xFF) / 255.0F, (float)intValue33 / 255.0F);
      }

      BufferBuilder bufferBuilder34 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
      bufferBuilder34.vertex(matrix4f14, floatValue17, floatValue18, 0.0F).texture(0.0F, 0.0F);
      bufferBuilder34.vertex(matrix4f14, floatValue17, floatValue18 + floatValue16, 0.0F).texture(0.0F, 1.0F);
      bufferBuilder34.vertex(matrix4f14, floatValue17 + floatValue15, floatValue18 + floatValue16, 0.0F).texture(1.0F, 1.0F);
      bufferBuilder34.vertex(matrix4f14, floatValue17 + floatValue15, floatValue18, 0.0F).texture(1.0F, 0.0F);
      RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.p);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder34.end());
      RenderSystem.disableBlend();
   }

   public static void a(
      MatrixStack matrixStack0, Identifier identifier1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, float floatValue9, int intValue10
   ) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShaderTexture(0, identifier1);
      Matrix4f matrix4f11 = matrixStack0.peek().getPositionMatrix();
      int intValue12 = intValue10 >> 24 & 0xFF;
      if (intValue12 == 0) {
         intValue12 = 255;
      }

      float floatValue13 = (float)(intValue10 >> 16 & 0xFF) / 255.0F;
      float floatValue14 = (float)(intValue10 >> 8 & 0xFF) / 255.0F;
      float floatValue15 = (float)(intValue10 & 0xFF) / 255.0F;
      float floatValue16 = (float)intValue12 / 255.0F;
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
      BufferBuilder bufferBuilder17 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      bufferBuilder17.vertex(matrix4f11, floatValue2, floatValue3, 0.0F).texture(floatValue6, floatValue7).color(floatValue13, floatValue14, floatValue15, floatValue16);
      bufferBuilder17.vertex(matrix4f11, floatValue2, floatValue3 + floatValue5, 0.0F).texture(floatValue6, floatValue9).color(floatValue13, floatValue14, floatValue15, floatValue16);
      bufferBuilder17.vertex(matrix4f11, floatValue2 + floatValue4, floatValue3 + floatValue5, 0.0F).texture(floatValue8, floatValue9).color(floatValue13, floatValue14, floatValue15, floatValue16);
      bufferBuilder17.vertex(matrix4f11, floatValue2 + floatValue4, floatValue3, 0.0F).texture(floatValue8, floatValue7).color(floatValue13, floatValue14, floatValue15, floatValue16);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder17.end());
      RenderSystem.setShaderTexture(0, 0);
      RenderSystem.disableBlend();
   }

   public static void a(MatrixStack matrixStack0, Identifier identifier1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      a(matrixStack0, identifier1, floatValue2, floatValue3, floatValue4, floatValue5, 0.0F, 0.0F, 1.0F, 1.0F, intValue6);
   }

   public static void a(MatrixStack matrixStack0, String string1, String string2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7) {
      a(matrixStack0, Identifier.of(string1, string2), floatValue3, floatValue4, floatValue5, floatValue6, intValue7);
   }

   public static void a(MatrixStack matrixStack0, Sprite sprite1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      float floatValue6 = (sprite1.getMaxU() - sprite1.getMinU()) * 0.012F;
      float floatValue7 = (sprite1.getMaxV() - sprite1.getMinV()) * 0.012F;
      a(matrixStack0, sprite1.getAtlasId(), floatValue2, floatValue3, floatValue4, floatValue4, sprite1.getMinU() + floatValue6, sprite1.getMinV() + floatValue7, sprite1.getMaxU() - floatValue6, sprite1.getMaxV() - floatValue7, intValue5);
   }

   public static void a(MatrixStack matrixStack0, PlayerEntity playerEntity1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6) {
      if (playerEntity1 != null) {
         Identifier identifier7 = a(playerEntity1);
         a(matrixStack0, identifier7, floatValue2, floatValue3, floatValue4, floatValue5, 1.0F, floatValue6);
      }
   }

   public static void a(MatrixStack matrixStack0, String string1, float floatValue2, float floatValue3, float floatValue4, float floatValue5) {
      a(matrixStack0, string1, floatValue2, floatValue3, floatValue4, floatValue5, 1.0F, 0.0F);
   }

   public static void a(MatrixStack matrixStack0, String string1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      if (string1 != null && !string1.isEmpty()) {
         Identifier identifier8 = b(string1);
         a(matrixStack0, identifier8, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7);
      }
   }

   public static void a(MatrixStack matrixStack0, UUID uUID1, float floatValue2, float floatValue3, float floatValue4, float floatValue5) {
      a(matrixStack0, uUID1, floatValue2, floatValue3, floatValue4, floatValue5, 1.0F, 0.0F);
   }

   public static void a(MatrixStack matrixStack0, UUID uUID1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      if (uUID1 != null) {
         Identifier identifier8 = a(uUID1);
         a(matrixStack0, identifier8, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7);
      }
   }

   public static void a(MatrixStack matrixStack0, PlayerListEntry playerListEntry1, float floatValue2, float floatValue3, float floatValue4, float floatValue5) {
      a(matrixStack0, playerListEntry1, floatValue2, floatValue3, floatValue4, floatValue5, 1.0F, 0.0F);
   }

   public static void a(MatrixStack matrixStack0, PlayerListEntry playerListEntry1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      if (playerListEntry1 != null) {
         Identifier identifier8 = playerListEntry1.getSkinTextures().texture();
         if (identifier8 == null) {
            identifier8 = DefaultSkinHelper.getSkinTextures(playerListEntry1.getProfile().getId()).texture();
         }

         a(matrixStack0, identifier8, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7);
      }
   }

   public static void a(MatrixStack matrixStack0, Identifier identifier1, float floatValue2, float floatValue3, float floatValue4, float floatValue5) {
      a(matrixStack0, identifier1, floatValue2, floatValue3, floatValue4, floatValue5, 1.0F, 0.0F);
   }

   public static void a(MatrixStack matrixStack0, Identifier identifier1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6) {
      a(matrixStack0, identifier1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, 0.0F);
   }

   private static void a(MatrixStack matrixStack0, Identifier identifier1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      if (identifier1 == null) {
         identifier1 = DefaultSkinHelper.getSkinTextures(d).texture();
      }

      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShaderTexture(0, identifier1);
      ShaderProgram shaderProgram8 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.s);
      Matrix4f matrix4f9 = matrixStack0.peek().getPositionMatrix();
      GlUniform glUniform10 = shaderProgram8.getUniform("location");
      GlUniform glUniform11 = shaderProgram8.getUniform("size");
      GlUniform glUniform12 = shaderProgram8.getUniform("radius");
      GlUniform glUniform13 = shaderProgram8.getUniform("alpha");
      GlUniform glUniform14 = shaderProgram8.getUniform("u");
      GlUniform glUniform15 = shaderProgram8.getUniform("v");
      GlUniform glUniform16 = shaderProgram8.getUniform("w");
      GlUniform glUniform17 = shaderProgram8.getUniform("h");
      GlUniform glUniform18 = shaderProgram8.getUniform("hurtPercent");
      if (glUniform10 != null) {
         glUniform10.set(floatValue2, floatValue3);
      }

      if (glUniform11 != null) {
         glUniform11.set(floatValue4, floatValue4);
      }

      if (glUniform12 != null) {
         glUniform12.set(floatValue5);
      }

      if (glUniform13 != null) {
         glUniform13.set(floatValue6);
      }

      if (glUniform14 != null) {
         glUniform14.set(0.125F);
      }

      if (glUniform15 != null) {
         glUniform15.set(0.125F);
      }

      if (glUniform16 != null) {
         glUniform16.set(0.125F);
      }

      if (glUniform17 != null) {
         glUniform17.set(0.125F);
      }

      if (glUniform18 != null) {
         glUniform18.set(floatValue7);
      }

      BufferBuilder bufferBuilder19 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
      bufferBuilder19.vertex(matrix4f9, floatValue2, floatValue3, 0.0F).texture(0.0F, 0.0F);
      bufferBuilder19.vertex(matrix4f9, floatValue2, floatValue3 + floatValue4, 0.0F).texture(0.0F, 1.0F);
      bufferBuilder19.vertex(matrix4f9, floatValue2 + floatValue4, floatValue3 + floatValue4, 0.0F).texture(1.0F, 1.0F);
      bufferBuilder19.vertex(matrix4f9, floatValue2 + floatValue4, floatValue3, 0.0F).texture(1.0F, 0.0F);
      RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.s);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder19.end());
      b(matrixStack0, identifier1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7);
      RenderSystem.setShaderTexture(0, 0);
      RenderSystem.disableBlend();
   }

   private static void b(MatrixStack matrixStack0, Identifier identifier1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      RenderSystem.setShaderTexture(0, identifier1);
      ShaderProgram shaderProgram8 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.s);
      Matrix4f matrix4f9 = matrixStack0.peek().getPositionMatrix();
      GlUniform glUniform10 = shaderProgram8.getUniform("location");
      GlUniform glUniform11 = shaderProgram8.getUniform("size");
      GlUniform glUniform12 = shaderProgram8.getUniform("radius");
      GlUniform glUniform13 = shaderProgram8.getUniform("alpha");
      GlUniform glUniform14 = shaderProgram8.getUniform("u");
      GlUniform glUniform15 = shaderProgram8.getUniform("v");
      GlUniform glUniform16 = shaderProgram8.getUniform("w");
      GlUniform glUniform17 = shaderProgram8.getUniform("h");
      GlUniform glUniform18 = shaderProgram8.getUniform("hurtPercent");
      if (glUniform10 != null) {
         glUniform10.set(floatValue2, floatValue3);
      }

      if (glUniform11 != null) {
         glUniform11.set(floatValue4, floatValue4);
      }

      if (glUniform12 != null) {
         glUniform12.set(floatValue5);
      }

      if (glUniform13 != null) {
         glUniform13.set(floatValue6);
      }

      if (glUniform14 != null) {
         glUniform14.set(0.625F);
      }

      if (glUniform15 != null) {
         glUniform15.set(0.125F);
      }

      if (glUniform16 != null) {
         glUniform16.set(0.125F);
      }

      if (glUniform17 != null) {
         glUniform17.set(0.125F);
      }

      if (glUniform18 != null) {
         glUniform18.set(floatValue7);
      }

      BufferBuilder bufferBuilder19 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
      bufferBuilder19.vertex(matrix4f9, floatValue2, floatValue3, 0.0F).texture(0.0F, 0.0F);
      bufferBuilder19.vertex(matrix4f9, floatValue2, floatValue3 + floatValue4, 0.0F).texture(0.0F, 1.0F);
      bufferBuilder19.vertex(matrix4f9, floatValue2 + floatValue4, floatValue3 + floatValue4, 0.0F).texture(1.0F, 1.0F);
      bufferBuilder19.vertex(matrix4f9, floatValue2 + floatValue4, floatValue3, 0.0F).texture(1.0F, 0.0F);
      RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.s);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder19.end());
   }

   private static Identifier a(PlayerEntity playerEntity0) {
      if (minecraftClient.getNetworkHandler() == null) {
         return DefaultSkinHelper.getSkinTextures(playerEntity0.getUuid()).texture();
      } else {
         PlayerListEntry playerListEntry1 = minecraftClient.getNetworkHandler().getPlayerListEntry(playerEntity0.getUuid());
         return playerListEntry1 != null ? playerListEntry1.getSkinTextures().texture() : DefaultSkinHelper.getSkinTextures(playerEntity0.getUuid()).texture();
      }
   }

   private static Identifier b(String string0) {
      String string1 = string0.toLowerCase(Locale.ROOT);
      Identifier identifier2 = (Identifier)c.get(string1);
      if (identifier2 != null) {
         return identifier2;
      } else {
         if (minecraftClient.getNetworkHandler() != null) {
            for (PlayerListEntry playerListEntry4 : minecraftClient.getNetworkHandler().getPlayerList()) {
               if (playerListEntry4.getProfile().getName().equalsIgnoreCase(string0)) {
                  Identifier identifier5 = playerListEntry4.getSkinTextures().texture();
                  c.put(string1, identifier5);
                  return identifier5;
               }
            }
         }

         if (minecraftClient.world != null) {
            for (PlayerEntity playerEntity8 : minecraftClient.world.getPlayers()) {
               if (playerEntity8.getName().getString().equalsIgnoreCase(string0)) {
                  Identifier identifier9 = a(playerEntity8);
                  c.put(string1, identifier9);
                  return identifier9;
               }
            }
         }

         Identifier identifier7 = DefaultSkinHelper.getSkinTextures(UUID.nameUUIDFromBytes(string0.getBytes())).texture();
         c.put(string1, identifier7);
         return identifier7;
      }
   }

   private static Identifier a(UUID uUID0) {
      String string1 = uUID0.toString();
      if (c.containsKey(string1)) {
         return (Identifier)c.get(string1);
      } else {
         if (minecraftClient.getNetworkHandler() != null) {
            PlayerListEntry playerListEntry2 = minecraftClient.getNetworkHandler().getPlayerListEntry(uUID0);
            if (playerListEntry2 != null) {
               Identifier identifier5 = playerListEntry2.getSkinTextures().texture();
               c.put(string1, identifier5);
               return identifier5;
            }
         }

         if (minecraftClient.world != null) {
            PlayerEntity playerEntity4 = minecraftClient.world.getPlayerByUuid(uUID0);
            if (playerEntity4 != null) {
               Identifier identifier3 = a(playerEntity4);
               c.put(string1, identifier3);
               return identifier3;
            }
         }

         return DefaultSkinHelper.getSkinTextures(uUID0).texture();
      }
   }

   public static void f() {
      c.clear();
   }

   public static void a(String string0) {
      c.remove(string0.toLowerCase(Locale.ROOT));
   }

   public static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      c(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue5, floatValue5, floatValue5, intValue6);
   }

   public static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, intValue5, dev.leg1tka.render.RenderExecutionMode.SHADER);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, int intValue5, dev.leg1tka.render.RenderExecutionMode value6) {
      dev.leg1tka.render.RenderExecutionMode value7 = value6 != null ? value6 : dev.leg1tka.render.RenderExecutionMode.SHADER;
      if (b()) {
         a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, 3.0F, 3.5F, 3.5F, 3.0F, intValue5, true, value7);
         if (value7 != dev.leg1tka.render.RenderExecutionMode.THREADS) {
            a(matrixStack0, floatValue1 + floatValue3 - 14.5F, floatValue2 + 3.0F, 10.0F, 10.0F, 2.0F, intValue5, true, value7);
         }
      } else {
         a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, 3.0F, 3.5F, intValue5, value7);
      }
   }

   public static void c(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      if (!(floatValue3 <= 6.0F) && !(floatValue4 <= 6.0F)) {
         float floatValue6 = floatValue1 - 1.0F;
         float floatValue7 = floatValue2 + 1.0F;
         float floatValue8 = Math.max(1.0F, floatValue3 - 2.0F);
         float floatValue9 = Math.max(1.0F, floatValue4 - 2.0F);
         float floatValue10 = (float)(intValue5 >>> 24 & 0xFF) / 255.0F;
         if (!(floatValue10 <= 0.001F)) {
            if (floatValue9 <= 20.0F) {
               float[][] floatValueArrayArray33 = new float[][]{
                  {0.05F, 0.08F, 8.6F},
                  {0.92F, 0.1F, 8.8F},
                  {0.16F, 0.78F, 6.3F},
                  {0.77F, 0.8F, 6.5F},
                  {0.31F, 0.18F, 6.0F},
                  {0.58F, 0.74F, 5.8F},
                  {0.45F, 0.45F, 5.1F},
                  {0.86F, 0.46F, 5.3F},
                  {0.23F, 0.52F, 4.9F},
                  {0.67F, 0.3F, 5.0F},
                  {0.11F, 0.34F, 5.5F},
                  {0.38F, 0.7F, 5.2F},
                  {0.72F, 0.16F, 5.7F},
                  {0.95F, 0.68F, 5.1F}
               };
               float floatValue34 = Math.min((float)floatValueArrayArray33.length, 3.7F + Math.max(0.0F, (floatValue8 - 84.0F) / 32.0F));
               int intValue35 = dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.api.utils.text.ColorUtils.b(intValue5, 0.62F), Math.max(0, Math.min(255, (int)(82.0F * floatValue10))));
               dev.leg1tka.render.ScissorStack.a();
               dev.leg1tka.render.ScissorStack.a((double)floatValue6, (double)floatValue7, (double)floatValue8, (double)floatValue9);

               try {
                  for (int intValue36 = 0; intValue36 < floatValueArrayArray33.length; intValue36++) {
                     float floatValue38 = floatValue34 - (float)intValue36;
                     if (!(floatValue38 <= 0.0F)) {
                        float floatValue39 = Math.max(0.0F, Math.min(1.0F, floatValue38));
                        floatValue39 = floatValue39 * floatValue39 * (3.0F - 2.0F * floatValue39);
                        if (!(floatValue39 <= 0.02F)) {
                           float floatValue41 = floatValueArrayArray33[intValue36][2];
                           float floatValue42 = floatValue6 + 0.8F + floatValueArrayArray33[intValue36][0] * Math.max(1.0F, floatValue8 - floatValue41 + 1.6F);
                           float floatValue45 = floatValue7 - 1.2F + floatValueArrayArray33[intValue36][1] * Math.max(1.0F, floatValue9 - floatValue41 + 2.4F);
                           int intValue46 = Math.max(0, Math.min(255, (int)(86.0F * floatValue39 * floatValue10)));
                           if (intValue46 > 0) {
                              int intValue47 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue35, intValue46);
                              b(matrixStack0, floatValue42, floatValue45, floatValue41, floatValue41, 0.0F, 0.5F, intValue47, intValue47, intValue47, intValue47);
                           }
                        }
                     }
                  }
               } finally {
                  dev.leg1tka.render.ScissorStack.c();
                  dev.leg1tka.render.ScissorStack.b();
               }
            } else {
               float[][] floatValueArrayArray11 = new float[][]{
                  {0.05F, 4.0F, 9.6F},
                  {0.87F, 4.0F, 9.2F},
                  {0.5F, 8.0F, 7.4F},
                  {0.18F, 13.0F, 6.2F},
                  {0.72F, 13.0F, 6.0F},
                  {0.07F, 21.0F, 5.6F},
                  {0.91F, 21.0F, 5.8F},
                  {0.24F, 30.0F, 5.4F},
                  {0.66F, 30.0F, 5.5F},
                  {0.04F, 38.0F, 6.8F},
                  {0.9F, 38.0F, 7.0F},
                  {0.15F, 47.0F, 5.4F},
                  {0.78F, 47.0F, 5.5F},
                  {0.08F, 56.0F, 5.1F},
                  {0.92F, 56.0F, 5.2F},
                  {0.23F, 65.0F, 5.8F},
                  {0.69F, 65.0F, 5.9F},
                  {0.52F, 71.0F, 7.2F},
                  {0.06F, 74.0F, 7.6F},
                  {0.88F, 74.0F, 7.4F},
                  {0.14F, 85.0F, 5.7F},
                  {0.82F, 85.0F, 5.8F},
                  {0.09F, 97.0F, 6.5F},
                  {0.9F, 98.0F, 6.6F}
               };
               byte byteValue12 = 10;
               float floatValue13 = Math.max(0.0F, floatValue9 - 24.0F);
               float floatValue14 = Math.min((float)floatValueArrayArray11.length, (float)byteValue12 + floatValue13 / 10.0F);
               float floatValue15 = Math.max(0.0F, Math.min(1.0F, (floatValue9 - 10.0F) / 16.0F));
               floatValue15 = floatValue15 * floatValue15 * (3.0F - 2.0F * floatValue15);
               int intValue16 = dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.api.utils.text.ColorUtils.b(intValue5, 0.72F), Math.max(0, Math.min(255, (int)(40.0F * floatValue10))));
               dev.leg1tka.render.ScissorStack.a();
               dev.leg1tka.render.ScissorStack.a((double)floatValue6, (double)floatValue7, (double)floatValue8, (double)floatValue9);

               try {
                  for (int intValue17 = 0; intValue17 < floatValueArrayArray11.length; intValue17++) {
                     float floatValue18 = floatValue14 - (float)intValue17;
                     if (!(floatValue18 <= 0.0F)) {
                        float floatValue19 = Math.max(0.0F, Math.min(1.0F, floatValue18));
                        floatValue19 = floatValue19 * floatValue19 * (3.0F - 2.0F * floatValue19);
                        floatValue19 *= floatValue15;
                        if (!(floatValue19 <= 0.015F)) {
                           float floatValue20 = floatValueArrayArray11[intValue17][2];
                           float floatValue21 = floatValue6 + 2.0F + floatValueArrayArray11[intValue17][0] * Math.max(1.0F, floatValue8 - floatValue20 - 4.0F);
                           float floatValue22 = floatValue7 + floatValueArrayArray11[intValue17][1];
                           float floatValue23 = floatValue7 + floatValue9 - 1.0F;
                           if (!(floatValue22 >= floatValue23)) {
                              if (floatValue22 + floatValue20 > floatValue23) {
                                 float floatValue24 = Math.max(0.0F, Math.min(1.0F, (floatValue23 - floatValue22) / Math.max(1.0F, floatValue20)));
                                 floatValue24 = floatValue24 * floatValue24 * (3.0F - 2.0F * floatValue24);
                                 floatValue19 *= floatValue24;
                                 if (floatValue19 <= 0.015F) {
                                    continue;
                                 }
                              }

                              int intValue49 = Math.max(0, Math.min(255, (int)(58.0F * floatValue19 * floatValue10)));
                              if (intValue49 > 0) {
                                 int intValue25 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue16, intValue49);
                                 b(matrixStack0, floatValue21, floatValue22, floatValue20, floatValue20, 0.0F, 0.55F, intValue25, intValue25, intValue25, intValue25);
                              }
                           }
                        }
                     }
                  }
               } finally {
                  dev.leg1tka.render.ScissorStack.c();
                  dev.leg1tka.render.ScissorStack.b();
               }
            }
         }
      }
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, int intValue4, int intValue5) {
      if (b()) {
         int intValue6 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue4, intValue5, 0.5F);
         a(matrixStack0, floatValue1 - 0.25F, floatValue2 - 1.25F, floatValue3 + 0.5F, 9.0F, 1.3F, intValue6, true);
      } else {
         b(matrixStack0, floatValue1 - 0.25F, floatValue2 - 1.25F, floatValue3 + 0.5F, 9.0F, 1.3F, a(intValue4));
         b(matrixStack0, floatValue1, floatValue2 - 1.0F, floatValue3, 8.5F, 1.0F, a(intValue5));
      }
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8, int intValue9) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, intValue7, intValue8, intValue9, true);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8, int intValue9, boolean flag10) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, intValue7, intValue8, intValue9, flag10, dev.leg1tka.render.RenderExecutionMode.SHADER);
   }

   public static void a(
      MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8, int intValue9, boolean flag10, dev.leg1tka.render.RenderExecutionMode value11
   ) {
      dev.leg1tka.render.RenderExecutionMode value12 = value11 != null ? value11 : dev.leg1tka.render.RenderExecutionMode.SHADER;
      if (b() && flag10) {
         int intValue17 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue8, intValue9, 0.5F);
         a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue6, floatValue5, intValue17, true, value12);
      } else if (a() && !flag10) {
         b(matrixStack0, floatValue1 - 0.5F, floatValue2 - 0.5F, floatValue3 + 1.0F, floatValue4 + 1.0F, floatValue6, a(intValue7));
         b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, a(intValue8), a(intValue9));
         if (value12 == dev.leg1tka.render.RenderExecutionMode.THREADS && floatValue3 >= 22.0F && floatValue4 >= 12.0F) {
            int intValue16 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue8, intValue9, 0.5F);
            long longValue18 = dev.leg1tka.render.RenderQuads.a(floatValue1, floatValue2, floatValue3, floatValue4);
            dev.leg1tka.render.RenderQuads.a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, intValue16, longValue18);
         }
      } else {
         b(matrixStack0, floatValue1 - 0.5F, floatValue2 - 0.5F, floatValue3 + 1.0F, floatValue4 + 1.0F, floatValue6, a(intValue7));
         b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, a(intValue8), a(intValue9));
         if (value12 == dev.leg1tka.render.RenderExecutionMode.THREADS && floatValue3 >= 22.0F && floatValue4 >= 12.0F) {
            int intValue13 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue8, intValue9, 0.5F);
            long longValue14 = dev.leg1tka.render.RenderQuads.a(floatValue1, floatValue2, floatValue3, floatValue4);
            dev.leg1tka.render.RenderQuads.a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, intValue13, longValue14);
         }
      }
   }

   public static void c(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, intValue7, dev.leg1tka.render.RenderExecutionMode.SHADER);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, dev.leg1tka.render.RenderExecutionMode value8) {
      dev.leg1tka.render.RenderExecutionMode value9 = value8 != null ? value8 : dev.leg1tka.render.RenderExecutionMode.SHADER;
      if (b()) {
         a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue6, floatValue5, intValue7, true, value9);
      } else {
         a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, 255), dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, 255), dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, 255));
         if (value9 == dev.leg1tka.render.RenderExecutionMode.THREADS && floatValue3 >= 22.0F && floatValue4 >= 12.0F) {
            long longValue10 = dev.leg1tka.render.RenderQuads.a(floatValue1, floatValue2, floatValue3, floatValue4);
            dev.leg1tka.render.RenderQuads.a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, intValue7, longValue10);
         }
      }
   }

   public static void a(
      MatrixStack matrixStack0,
      float floatValue1,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      int intValue8,
      int intValue9,
      int intValue10,
      int intValue11,
      int intValue12,
      int intValue13
   ) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue6, floatValue7, intValue8, intValue9, intValue10, intValue11, intValue12, intValue13);
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, intValue8, intValue9, intValue10, intValue11, intValue12, intValue13);
   }

   public static void a(
      MatrixStack matrixStack0,
      float floatValue1,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      int intValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      int intValue10,
      int intValue11,
      int intValue12,
      int intValue13,
      int intValue14,
      int intValue15
   ) {
      if (b()) {
         int intValue16 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue10, intValue15, 0.5F);
         float floatValue17 = floatValue2 + floatValue6;
         float floatValue18 = floatValue4 - floatValue6;
         if (floatValue18 > 0.5F) {
            a(matrixStack0, floatValue1, floatValue17, floatValue3, floatValue18, 0.0F, 0.0F, 0.0F, 0.0F, intValue16, true);
         }

         a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue6, floatValue7, floatValue8, floatValue9, intValue10, intValue11, intValue12, intValue13, intValue14, intValue15);
      } else {
         b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, 0.0F, intValue5);
         a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue6, floatValue7, floatValue8, floatValue9, intValue10, intValue11, intValue12, intValue13, intValue14, intValue15);
      }
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, 0.0F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(40, 40, 40, 255), floatValue7));
      b(matrixStack0, floatValue1 + floatValue5, floatValue2 + floatValue5, floatValue3 - floatValue5 * 2.0F, floatValue4 - floatValue5 * 2.0F, 0.0F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(20, 20, 20, 255), floatValue7));
      b(matrixStack0, floatValue1 + floatValue5 + 2.0F, floatValue2 + floatValue5 + 2.0F, floatValue6, floatValue6, 0.0F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(40, 40, 40, 255), floatValue7));
      b(matrixStack0, floatValue1 + floatValue5 + 3.0F, floatValue2 + floatValue5 + 3.0F, floatValue6 - 2.0F, floatValue6 - 2.0F, 0.0F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(25, 25, 25, 255), floatValue7));
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3) {
      b(matrixStack0, floatValue1 - 1.0F, floatValue2 - 1.0F, 22.0F, 22.0F, 1.0F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(21, 21, 21, 255), floatValue3));
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, int intValue7, float floatValue8) {
      b(matrixStack0, floatValue1, floatValue2, floatValue3, 5.5F, 1.25F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.b(intValue6, 0.5F), floatValue8 * 0.8F));
      b(matrixStack0, floatValue1, floatValue2, floatValue3 * floatValue4, 5.5F, 1.25F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.b(intValue6, 0.8F), floatValue8 * 0.8F));
      a(matrixStack0, floatValue1, floatValue2, floatValue3 * floatValue5, 5.5F, 1.25F, dev.leg1tka.api.utils.text.ColorUtils.a(intValue6, floatValue8), dev.leg1tka.api.utils.text.ColorUtils.a(intValue7, floatValue8), true);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8) {
      int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 215, 0, 255);
      b(matrixStack0, floatValue1, floatValue2, floatValue3 * floatValue5, floatValue4, 1.25F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.b(intValue9, 0.65F), floatValue7 * floatValue8 * 0.8F));
      a(matrixStack0, floatValue1, floatValue2, floatValue3 * floatValue6, floatValue4, 1.25F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.b(intValue9, 0.55F), floatValue7 * floatValue8), dev.leg1tka.api.utils.text.ColorUtils.a(intValue9, floatValue7 * floatValue8), true);
   }

   public static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3) {
      b(matrixStack0, floatValue1, floatValue2, 6.2F, 4.5F, 0.0F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, 255), floatValue3));
   }

   public static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, int intValue4, int intValue5) {
      a(matrixStack0, floatValue1 + 1.0F, floatValue2 + 1.0F, floatValue3, 2.0F, 0.0F, 8.0F, intValue5);
      b(matrixStack0, floatValue1, floatValue2, floatValue3 + 1.2F, 4.5F, 0.0F, intValue4);
   }

   public static void c(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      if (b()) {
         a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, dev.leg1tka.api.utils.text.ColorUtils.a(), true);
      } else {
         b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, intValue6);
      }
   }

   public static void c(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, int intValue9) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableDepthTest();
      ShaderProgram shaderProgram10 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.c);
      if (shaderProgram10 == null) {
         d(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, intValue9);
      } else {
         Matrix4f matrix4f11 = matrixStack0.peek().getPositionMatrix();
         GlUniform glUniform12 = shaderProgram10.getUniform("Size");
         GlUniform glUniform13 = shaderProgram10.getUniform("Radius");
         GlUniform glUniform14 = shaderProgram10.getUniform("Smoothness");
         float floatValue15 = 1.0F;
         if (glUniform12 != null) {
            glUniform12.set(floatValue3, floatValue4);
         }

         if (glUniform13 != null) {
            glUniform13.set(floatValue5, floatValue8, floatValue6, floatValue7);
         }

         if (glUniform14 != null) {
            glUniform14.set(floatValue15);
         }

         int intValue16 = intValue9 >> 24 & 0xFF;
         if (intValue16 == 0) {
            intValue16 = 255;
         }

         float floatValue17 = (float)(intValue9 >> 16 & 0xFF) / 255.0F;
         float floatValue18 = (float)(intValue9 >> 8 & 0xFF) / 255.0F;
         float floatValue19 = (float)(intValue9 & 0xFF) / 255.0F;
         float floatValue20 = (float)intValue16 / 255.0F;
         float floatValue21 = -floatValue15 / 2.0F + floatValue15 * 2.0F;
         float floatValue22 = floatValue15 / 2.0F + floatValue15;
         float floatValue23 = floatValue1 - floatValue21 / 2.0F;
         float floatValue24 = floatValue2 - floatValue22 / 2.0F;
         float floatValue25 = floatValue3 + floatValue21;
         float floatValue26 = floatValue4 + floatValue22;
         BufferBuilder bufferBuilder27 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
         bufferBuilder27.vertex(matrix4f11, floatValue23, floatValue24, 0.0F).color(floatValue17, floatValue18, floatValue19, floatValue20);
         bufferBuilder27.vertex(matrix4f11, floatValue23, floatValue24 + floatValue26, 0.0F).color(floatValue17, floatValue18, floatValue19, floatValue20);
         bufferBuilder27.vertex(matrix4f11, floatValue23 + floatValue25, floatValue24 + floatValue26, 0.0F).color(floatValue17, floatValue18, floatValue19, floatValue20);
         bufferBuilder27.vertex(matrix4f11, floatValue23 + floatValue25, floatValue24, 0.0F).color(floatValue17, floatValue18, floatValue19, floatValue20);
         a(bufferBuilder27);
         RenderSystem.disableBlend();
      }
   }

   private static void d(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      Matrix4f matrix4f6 = matrixStack0.peek().getPositionMatrix();
      int intValue7 = intValue5 >> 24 & 0xFF;
      if (intValue7 == 0) {
         intValue7 = 255;
      }

      float floatValue8 = (float)(intValue5 >> 16 & 0xFF) / 255.0F;
      float floatValue9 = (float)(intValue5 >> 8 & 0xFF) / 255.0F;
      float floatValue10 = (float)(intValue5 & 0xFF) / 255.0F;
      float floatValue11 = (float)intValue7 / 255.0F;
      BufferBuilder bufferBuilder12 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
      bufferBuilder12.vertex(matrix4f6, floatValue1, floatValue2, 0.0F).color(floatValue8, floatValue9, floatValue10, floatValue11);
      bufferBuilder12.vertex(matrix4f6, floatValue1, floatValue2 + floatValue4, 0.0F).color(floatValue8, floatValue9, floatValue10, floatValue11);
      bufferBuilder12.vertex(matrix4f6, floatValue1 + floatValue3, floatValue2 + floatValue4, 0.0F).color(floatValue8, floatValue9, floatValue10, floatValue11);
      bufferBuilder12.vertex(matrix4f6, floatValue1 + floatValue3, floatValue2, 0.0F).color(floatValue8, floatValue9, floatValue10, floatValue11);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      a(bufferBuilder12);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, int intValue4) {
      Matrix4f matrix4f5 = matrixStack0.peek().getPositionMatrix();
      b(matrixStack0, floatValue1 - floatValue3 / 2.0F, floatValue2 - floatValue3 / 2.0F, floatValue3, floatValue3, floatValue3 / 2.0F - 0.5F, intValue4);
   }

   public static void d(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7) {
      if (!(floatValue3 <= 0.0F) && !(floatValue4 <= 0.0F)) {
         float floatValue8 = floatValue3 / 2.0F;
         float floatValue9 = (float)Math.toRadians((double)floatValue5);
         float floatValue10 = (float)Math.toRadians((double)floatValue6);
         float floatValue11 = (float) (Math.PI * 2);
         if (floatValue9 < 0.0F) {
            floatValue9 += floatValue11;
         }

         if (floatValue10 < 0.0F) {
            floatValue10 += floatValue11;
         }

         while (floatValue10 < floatValue9) {
            floatValue10 += floatValue11;
         }

         if (floatValue10 - floatValue9 <= 1.0E-4F) {
            floatValue10 = floatValue9 + floatValue11;
         }

         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         ShaderProgram shaderProgram12 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.i);
         GlUniform glUniform13 = shaderProgram12.getUniform("Size");
         GlUniform glUniform14 = shaderProgram12.getUniform("Radius");
         GlUniform glUniform15 = shaderProgram12.getUniform("Thickness");
         GlUniform glUniform16 = shaderProgram12.getUniform("StartAngle");
         GlUniform glUniform17 = shaderProgram12.getUniform("EndAngle");
         GlUniform glUniform18 = shaderProgram12.getUniform("Smoothness");
         GlUniform glUniform19 = shaderProgram12.getUniform("ColorModulator");
         if (glUniform13 != null) {
            glUniform13.set(floatValue3, floatValue3);
         }

         if (glUniform14 != null) {
            glUniform14.set(floatValue8);
         }

         if (glUniform15 != null) {
            glUniform15.set(floatValue4);
         }

         if (glUniform16 != null) {
            glUniform16.set(floatValue9);
         }

         if (glUniform17 != null) {
            glUniform17.set(floatValue10);
         }

         if (glUniform18 != null) {
            glUniform18.set(Math.min(1.0F, floatValue4 * 0.5F));
         }

         if (glUniform19 != null) {
            glUniform19.set(1.0F, 1.0F, 1.0F, 1.0F);
         }

         Matrix4f matrix4f20 = matrixStack0.peek().getPositionMatrix();
         BufferBuilder bufferBuilder21 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
         int intValue22 = intValue7 >> 24 & 0xFF;
         if (intValue22 == 0) {
            intValue22 = 255;
         }

         float floatValue23 = (float)(intValue7 >> 16 & 0xFF) / 255.0F;
         float floatValue24 = (float)(intValue7 >> 8 & 0xFF) / 255.0F;
         float floatValue25 = (float)(intValue7 & 0xFF) / 255.0F;
         float floatValue26 = (float)intValue22 / 255.0F;
         bufferBuilder21.vertex(matrix4f20, floatValue1, floatValue2, 0.0F).color(floatValue23, floatValue24, floatValue25, floatValue26);
         bufferBuilder21.vertex(matrix4f20, floatValue1, floatValue2 + floatValue3, 0.0F).color(floatValue23, floatValue24, floatValue25, floatValue26);
         bufferBuilder21.vertex(matrix4f20, floatValue1 + floatValue3, floatValue2 + floatValue3, 0.0F).color(floatValue23, floatValue24, floatValue25, floatValue26);
         bufferBuilder21.vertex(matrix4f20, floatValue1 + floatValue3, floatValue2, 0.0F).color(floatValue23, floatValue24, floatValue25, floatValue26);
         RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.i);
         BufferRenderer.drawWithGlobalProgram(bufferBuilder21.end());
         RenderSystem.disableBlend();
      }
   }

   public static void b(
      MatrixStack matrixStack0,
      float floatValue1,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      int intValue9,
      int intValue10,
      int intValue11,
      int intValue12
   ) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableDepthTest();
      ShaderProgram shaderProgram13 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.n);
      if (shaderProgram13 == null) {
         d(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, intValue9);
      } else {
         Matrix4f matrix4f14 = matrixStack0.peek().getPositionMatrix();
         GlUniform glUniform15 = shaderProgram13.getUniform("Size");
         GlUniform glUniform16 = shaderProgram13.getUniform("Radius");
         GlUniform glUniform17 = shaderProgram13.getUniform("Smoothness");
         GlUniform glUniform18 = shaderProgram13.getUniform("ColorModulator");
         GlUniform glUniform19 = shaderProgram13.getUniform("TopLeftColor");
         GlUniform glUniform20 = shaderProgram13.getUniform("BottomLeftColor");
         GlUniform glUniform21 = shaderProgram13.getUniform("TopRightColor");
         GlUniform glUniform22 = shaderProgram13.getUniform("BottomRightColor");
         if (glUniform15 != null) {
            glUniform15.set(floatValue3, floatValue4);
         }

         if (glUniform16 != null) {
            glUniform16.set(floatValue5, floatValue8, floatValue6, floatValue7);
         }

         if (glUniform17 != null) {
            glUniform17.set(1.0F);
         }

         if (glUniform18 != null) {
            glUniform18.set(1.0F, 1.0F, 1.0F, 1.0F);
         }

         int intValue23 = intValue9 >> 24 & 0xFF;
         if (glUniform19 != null) {
            glUniform19.set((float)(intValue9 >> 16 & 0xFF) / 255.0F, (float)(intValue9 >> 8 & 0xFF) / 255.0F, (float)(intValue9 & 0xFF) / 255.0F, (float)intValue23 / 255.0F);
         }

         int intValue24 = intValue11 >> 24 & 0xFF;
         if (glUniform20 != null) {
            glUniform20.set((float)(intValue11 >> 16 & 0xFF) / 255.0F, (float)(intValue11 >> 8 & 0xFF) / 255.0F, (float)(intValue11 & 0xFF) / 255.0F, (float)intValue24 / 255.0F);
         }

         int intValue25 = intValue10 >> 24 & 0xFF;
         if (glUniform21 != null) {
            glUniform21.set((float)(intValue10 >> 16 & 0xFF) / 255.0F, (float)(intValue10 >> 8 & 0xFF) / 255.0F, (float)(intValue10 & 0xFF) / 255.0F, (float)intValue25 / 255.0F);
         }

         int intValue26 = intValue12 >> 24 & 0xFF;
         if (glUniform22 != null) {
            glUniform22.set((float)(intValue12 >> 16 & 0xFF) / 255.0F, (float)(intValue12 >> 8 & 0xFF) / 255.0F, (float)(intValue12 & 0xFF) / 255.0F, (float)intValue26 / 255.0F);
         }

         BufferBuilder bufferBuilder27 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
         bufferBuilder27.vertex(matrix4f14, floatValue1, floatValue2, 0.0F).texture(0.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
         bufferBuilder27.vertex(matrix4f14, floatValue1, floatValue2 + floatValue4, 0.0F).texture(0.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
         bufferBuilder27.vertex(matrix4f14, floatValue1 + floatValue3, floatValue2 + floatValue4, 0.0F).texture(1.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
         bufferBuilder27.vertex(matrix4f14, floatValue1 + floatValue3, floatValue2, 0.0F).texture(1.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.n);
         a(bufferBuilder27);
      }
   }

   public static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, int intValue7, int intValue8, int intValue9) {
      b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue5, floatValue5, floatValue5, intValue6, intValue7, intValue8, intValue9);
   }

   public static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, int intValue7) {
      b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue5, floatValue5, floatValue5, intValue6, intValue6, intValue7, intValue7);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, int intValue5, int intValue6) {
      b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, 0.0F, 0.0F, 0.0F, 0.0F, intValue5, intValue5, intValue6, intValue6);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, int intValue7, boolean flag8) {
      if (flag8) {
         b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue5, floatValue5, floatValue5, intValue6, intValue7, intValue6, intValue7);
      } else {
         b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue5, floatValue5, floatValue5, intValue6, intValue6, intValue7, intValue7);
      }
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, float floatValue9, int intValue10) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, floatValue9, intValue10, intValue10, intValue10, intValue10);
   }

   public static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8, int intValue9, int intValue10) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue5, floatValue5, floatValue5, floatValue6, intValue7, intValue8, intValue9, intValue10);
   }

   public static void a(
      MatrixStack matrixStack0,
      float floatValue1,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      int intValue10,
      int intValue11,
      int intValue12,
      int intValue13
   ) {
      if (!(floatValue9 <= 0.0F)) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         ShaderProgram shaderProgram14 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.h);
         Matrix4f matrix4f15 = matrixStack0.peek().getPositionMatrix();
         GlUniform glUniform16 = shaderProgram14.getUniform("Size");
         GlUniform glUniform17 = shaderProgram14.getUniform("Radius");
         GlUniform glUniform18 = shaderProgram14.getUniform("Smoothness");
         GlUniform glUniform19 = shaderProgram14.getUniform("ColorModulator");
         GlUniform glUniform20 = shaderProgram14.getUniform("Outline");
         GlUniform glUniform21 = shaderProgram14.getUniform("TopLeftColor");
         GlUniform glUniform22 = shaderProgram14.getUniform("BottomLeftColor");
         GlUniform glUniform23 = shaderProgram14.getUniform("TopRightColor");
         GlUniform glUniform24 = shaderProgram14.getUniform("BottomRightColor");
         if (glUniform16 != null) {
            glUniform16.set(floatValue3, floatValue4);
         }

         if (glUniform17 != null) {
            glUniform17.set(floatValue5, floatValue8, floatValue6, floatValue7);
         }

         if (glUniform18 != null) {
            glUniform18.set(1.0F);
         }

         if (glUniform19 != null) {
            glUniform19.set(1.0F, 1.0F, 1.0F, 1.0F);
         }

         if (glUniform20 != null) {
            glUniform20.set(floatValue9);
         }

         if (glUniform21 != null) {
            int intValue25 = intValue10 >> 24 & 0xFF;
            if (intValue25 == 0) {
               intValue25 = 255;
            }

            glUniform21.set((float)(intValue10 >> 16 & 0xFF) / 255.0F, (float)(intValue10 >> 8 & 0xFF) / 255.0F, (float)(intValue10 & 0xFF) / 255.0F, (float)intValue25 / 255.0F);
         }

         if (glUniform22 != null) {
            int intValue26 = intValue12 >> 24 & 0xFF;
            if (intValue26 == 0) {
               intValue26 = 255;
            }

            glUniform22.set((float)(intValue12 >> 16 & 0xFF) / 255.0F, (float)(intValue12 >> 8 & 0xFF) / 255.0F, (float)(intValue12 & 0xFF) / 255.0F, (float)intValue26 / 255.0F);
         }

         if (glUniform23 != null) {
            int intValue27 = intValue11 >> 24 & 0xFF;
            if (intValue27 == 0) {
               intValue27 = 255;
            }

            glUniform23.set((float)(intValue11 >> 16 & 0xFF) / 255.0F, (float)(intValue11 >> 8 & 0xFF) / 255.0F, (float)(intValue11 & 0xFF) / 255.0F, (float)intValue27 / 255.0F);
         }

         if (glUniform24 != null) {
            int intValue28 = intValue13 >> 24 & 0xFF;
            if (intValue28 == 0) {
               intValue28 = 255;
            }

            glUniform24.set((float)(intValue13 >> 16 & 0xFF) / 255.0F, (float)(intValue13 >> 8 & 0xFF) / 255.0F, (float)(intValue13 & 0xFF) / 255.0F, (float)intValue28 / 255.0F);
         }

         BufferBuilder bufferBuilder29 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
         bufferBuilder29.vertex(matrix4f15, floatValue1, floatValue2, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
         bufferBuilder29.vertex(matrix4f15, floatValue1, floatValue2 + floatValue4, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
         bufferBuilder29.vertex(matrix4f15, floatValue1 + floatValue3, floatValue2 + floatValue4, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
         bufferBuilder29.vertex(matrix4f15, floatValue1 + floatValue3, floatValue2, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.h);
         BufferRenderer.drawWithGlobalProgram(bufferBuilder29.end());
         RenderSystem.disableBlend();
      }
   }

   public static void d(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, int intValue9) {
      if (dev.leg1tka.render.ScreenBlurPass.f() != null) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         Matrix4f matrix4f10 = matrixStack0.peek().getPositionMatrix();
         ShaderProgram shaderProgram11 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.j);
         GlUniform glUniform12 = shaderProgram11.getUniform("Size");
         GlUniform glUniform13 = shaderProgram11.getUniform("Radius");
         GlUniform glUniform14 = shaderProgram11.getUniform("Smoothness");
         GlUniform glUniform15 = shaderProgram11.getUniform("ColorModulator");
         if (glUniform12 != null) {
            glUniform12.set(floatValue3, floatValue4);
         }

         if (glUniform13 != null) {
            glUniform13.set(floatValue5, floatValue8, floatValue6, floatValue7);
         }

         if (glUniform14 != null) {
            glUniform14.set(0.5F);
         }

         if (glUniform15 != null) {
            glUniform15.set(1.0F, 1.0F, 1.0F, 1.0F);
         }

         RenderSystem.setShaderTexture(0, dev.leg1tka.render.ScreenBlurPass.d());
         RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.j);
         int intValue16 = minecraftClient.getWindow().getScaledWidth();
         int intValue17 = minecraftClient.getWindow().getScaledHeight();
         float floatValue18 = floatValue1 / (float)intValue16;
         float floatValue19 = ((float)intValue17 - floatValue2) / (float)intValue17;
         float floatValue20 = (floatValue1 + floatValue3) / (float)intValue16;
         float floatValue21 = ((float)intValue17 - floatValue2 - floatValue4) / (float)intValue17;
         int intValue22 = intValue9 >> 24 & 0xFF;
         if (intValue22 == 0) {
            intValue22 = 255;
         }

         float floatValue23 = (float)(intValue9 >> 16 & 0xFF) / 255.0F;
         float floatValue24 = (float)(intValue9 >> 8 & 0xFF) / 255.0F;
         float floatValue25 = (float)(intValue9 & 0xFF) / 255.0F;
         float floatValue26 = (float)intValue22 / 255.0F;
         BufferBuilder bufferBuilder27 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
         bufferBuilder27.vertex(matrix4f10, floatValue1, floatValue2, 0.0F).texture(floatValue18, floatValue19).color(floatValue23, floatValue24, floatValue25, floatValue26);
         bufferBuilder27.vertex(matrix4f10, floatValue1, floatValue2 + floatValue4, 0.0F).texture(floatValue18, floatValue21).color(floatValue23, floatValue24, floatValue25, floatValue26);
         bufferBuilder27.vertex(matrix4f10, floatValue1 + floatValue3, floatValue2 + floatValue4, 0.0F).texture(floatValue20, floatValue21).color(floatValue23, floatValue24, floatValue25, floatValue26);
         bufferBuilder27.vertex(matrix4f10, floatValue1 + floatValue3, floatValue2, 0.0F).texture(floatValue20, floatValue19).color(floatValue23, floatValue24, floatValue25, floatValue26);
         BufferRenderer.drawWithGlobalProgram(bufferBuilder27.end());
         RenderSystem.setShaderTexture(0, 0);
         RenderSystem.disableBlend();
      }
   }

   public static void d(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      d(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue5, floatValue5, floatValue5, intValue6);
   }

   public static void a(float floatValue0, int intValue1, dev.leg1tka.render.font.RenderCallback value2, MatrixStack matrixStack3) {
      int intValue4 = intValue1 >> 24 & 0xFF;
      int intValue5 = intValue1 >> 16 & 0xFF;
      int intValue6 = intValue1 >> 8 & 0xFF;
      int intValue7 = intValue1 & 0xFF;
      if (intValue4 == 0) {
         intValue4 = 255;
      }

      dev.leg1tka.render.FramebufferCapture.a().a(floatValue0, new Color(intValue5, intValue6, intValue7, intValue4));
      value2.a();
      dev.leg1tka.render.FramebufferCapture.a().a(matrixStack3, value2);
   }

   public static void a(float floatValue0, float floatValue1, int intValue2, dev.leg1tka.render.font.RenderCallback value3, MatrixStack matrixStack4) {
      int intValue5 = intValue2 >> 24 & 0xFF;
      int intValue6 = intValue2 >> 16 & 0xFF;
      int intValue7 = intValue2 >> 8 & 0xFF;
      int intValue8 = intValue2 & 0xFF;
      if (intValue5 == 0) {
         intValue5 = 255;
      }

      dev.leg1tka.render.FramebufferCapture.a().a(floatValue0, floatValue1, new Color(intValue6, intValue7, intValue8, intValue5));
      value3.a();
      dev.leg1tka.render.FramebufferCapture.a().a(matrixStack4, value3);
   }

   public static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, float floatValue9, int intValue10) {
      dev.leg1tka.render.ScreenBlurPass.a().c();
      if (dev.leg1tka.render.ScreenBlurPass.f() != null) {
         dev.leg1tka.render.ScreenBlurPass.a().a(floatValue9);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         Matrix4f matrix4f11 = matrixStack0.peek().getPositionMatrix();
         ShaderProgram shaderProgram12 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.j);
         GlUniform glUniform13 = shaderProgram12.getUniform("Size");
         GlUniform glUniform14 = shaderProgram12.getUniform("Radius");
         GlUniform glUniform15 = shaderProgram12.getUniform("Smoothness");
         GlUniform glUniform16 = shaderProgram12.getUniform("ColorModulator");
         if (glUniform13 != null) {
            glUniform13.set(floatValue3, floatValue4);
         }

         if (glUniform14 != null) {
            glUniform14.set(floatValue5, floatValue8, floatValue6, floatValue7);
         }

         if (glUniform15 != null) {
            glUniform15.set(0.5F);
         }

         if (glUniform16 != null) {
            glUniform16.set(1.0F, 1.0F, 1.0F, 1.0F);
         }

         RenderSystem.setShaderTexture(0, dev.leg1tka.render.ScreenBlurPass.d());
         RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.j);
         int intValue17 = minecraftClient.getWindow().getScaledWidth();
         int intValue18 = minecraftClient.getWindow().getScaledHeight();
         float floatValue19 = floatValue1 / (float)intValue17;
         float floatValue20 = ((float)intValue18 - floatValue2) / (float)intValue18;
         float floatValue21 = (floatValue1 + floatValue3) / (float)intValue17;
         float floatValue22 = ((float)intValue18 - floatValue2 - floatValue4) / (float)intValue18;
         int intValue23 = intValue10 >> 24 & 0xFF;
         if (intValue23 == 0) {
            intValue23 = 255;
         }

         float floatValue24 = (float)(intValue10 >> 16 & 0xFF) / 255.0F;
         float floatValue25 = (float)(intValue10 >> 8 & 0xFF) / 255.0F;
         float floatValue26 = (float)(intValue10 & 0xFF) / 255.0F;
         float floatValue27 = (float)intValue23 / 255.0F;
         BufferBuilder bufferBuilder28 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
         bufferBuilder28.vertex(matrix4f11, floatValue1, floatValue2, 0.0F).texture(floatValue19, floatValue20).color(floatValue24, floatValue25, floatValue26, floatValue27);
         bufferBuilder28.vertex(matrix4f11, floatValue1, floatValue2 + floatValue4, 0.0F).texture(floatValue19, floatValue22).color(floatValue24, floatValue25, floatValue26, floatValue27);
         bufferBuilder28.vertex(matrix4f11, floatValue1 + floatValue3, floatValue2 + floatValue4, 0.0F).texture(floatValue21, floatValue22).color(floatValue24, floatValue25, floatValue26, floatValue27);
         bufferBuilder28.vertex(matrix4f11, floatValue1 + floatValue3, floatValue2, 0.0F).texture(floatValue21, floatValue20).color(floatValue24, floatValue25, floatValue26, floatValue27);
         BufferRenderer.drawWithGlobalProgram(bufferBuilder28.end());
         RenderSystem.setShaderTexture(0, 0);
         RenderSystem.disableBlend();
      }
   }

   public static void e(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7) {
      b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue5, floatValue5, floatValue5, floatValue6, intValue7);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, float floatValue7) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, intValue6, floatValue7, 1.0F);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, float floatValue7, float floatValue8) {
      float floatValue9 = Math.max(0.0F, Math.min(1.0F, floatValue7));
      if (!(floatValue9 <= 0.001F)) {
         int intValue10 = Math.max(1, (int)(255.0F * floatValue9));
         e(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, 16.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue10));
         b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, a(intValue6, floatValue9 * floatValue8));
      }
   }

   public static int a(int intValue0, float floatValue1) {
      int intValue2 = intValue0 >> 16 & 0xFF;
      int intValue3 = intValue0 >> 8 & 0xFF;
      int intValue4 = intValue0 & 0xFF;
      return dev.leg1tka.api.utils.text.ColorUtils.a(
         Math.min((int)(12.0F + (float)intValue2 * 0.08F), 255),
         Math.min((int)(12.0F + (float)intValue3 * 0.08F), 255),
         Math.min((int)(14.0F + (float)intValue4 * 0.08F), 255),
         Math.max(1, (int)(155.0F * floatValue1))
      );
   }

   public static void a(
      MatrixStack matrixStack0,
      float floatValue1,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      int intValue9,
      float floatValue10,
      float floatValue11,
      int intValue12,
      float floatValue13,
      boolean flag14,
      float floatValue15,
      float floatValue16,
      float floatValue17,
      boolean flag18
   ) {
      int intValue19;
      if (flag18) {
         intValue19 = minecraftClient.getFramebuffer().getColorAttachment();
      } else {
         dev.leg1tka.render.ScreenBlurPass.a().c();
         if (dev.leg1tka.render.ScreenBlurPass.e() == null) {
            return;
         }

         intValue19 = dev.leg1tka.render.ScreenBlurPass.d();
         if (intValue19 == 0) {
            return;
         }
      }

      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      RenderSystem.setShaderTexture(0, intValue19);
      ShaderProgram shaderProgram20 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.k);
      GlUniform glUniform21 = shaderProgram20.getUniform("GlobalAlpha");
      GlUniform glUniform22 = shaderProgram20.getUniform("Size");
      GlUniform glUniform23 = shaderProgram20.getUniform("Radius");
      GlUniform glUniform24 = shaderProgram20.getUniform("Smoothness");
      GlUniform glUniform25 = shaderProgram20.getUniform("FresnelPower");
      GlUniform glUniform26 = shaderProgram20.getUniform("FresnelColor");
      GlUniform glUniform27 = shaderProgram20.getUniform("FresnelAlpha");
      GlUniform glUniform28 = shaderProgram20.getUniform("BaseAlpha");
      GlUniform glUniform29 = shaderProgram20.getUniform("FresnelInvert");
      GlUniform glUniform30 = shaderProgram20.getUniform("FresnelMix");
      GlUniform glUniform31 = shaderProgram20.getUniform("DistortStrength");
      GlUniform glUniform32 = shaderProgram20.getUniform("CornerSmoothness");
      GlUniform glUniform33 = shaderProgram20.getUniform("MotionVector");
      GlUniform glUniform34 = shaderProgram20.getUniform("MotionStrength");
      float floatValue35 = dev.leg1tka.render.RenderInterpolation.b();
      float floatValue36 = floatValue35 * (0.0045F + floatValue16 * 1.35F);
      float floatValue37 = dev.leg1tka.render.RenderInterpolation.c();
      float floatValue38 = dev.leg1tka.render.RenderInterpolation.d();
      if (glUniform21 != null) {
         glUniform21.set(floatValue10);
      }

      if (glUniform22 != null) {
         glUniform22.set(floatValue3, floatValue4);
      }

      if (glUniform23 != null) {
         glUniform23.set(floatValue5, floatValue8, floatValue6, floatValue7);
      }

      if (glUniform24 != null) {
         glUniform24.set(0.5F);
      }

      if (glUniform25 != null) {
         glUniform25.set(floatValue11);
      }

      int intValue39 = intValue12 >> 24 & 0xFF;
      if (intValue39 == 0) {
         intValue39 = 255;
      }

      if (glUniform26 != null) {
         glUniform26.set((float)(intValue12 >> 16 & 0xFF) / 255.0F, (float)(intValue12 >> 8 & 0xFF) / 255.0F, (float)(intValue12 & 0xFF) / 255.0F);
      }

      if (glUniform27 != null) {
         glUniform27.set((float)intValue39 / 255.0F);
      }

      if (glUniform28 != null) {
         glUniform28.set(floatValue13);
      }

      if (glUniform29 != null) {
         glUniform29.set(flag14 ? 1 : 0);
      }

      if (glUniform30 != null) {
         glUniform30.set(floatValue15);
      }

      if (glUniform31 != null) {
         glUniform31.set(floatValue16);
      }

      if (glUniform32 != null) {
         glUniform32.set(floatValue17);
      }

      if (glUniform33 != null) {
         glUniform33.set(floatValue37, floatValue38);
      }

      if (glUniform34 != null) {
         glUniform34.set(floatValue36);
      }

      Matrix4f matrix4f40 = matrixStack0.peek().getPositionMatrix();
      float floatValue41 = (float)minecraftClient.getWindow().getFramebufferWidth();
      float floatValue42 = (float)minecraftClient.getWindow().getFramebufferHeight();
      float floatValue43 = (float)minecraftClient.getWindow().getScaleFactor();
      float floatValue44 = floatValue1 * floatValue43;
      float floatValue45 = floatValue2 * floatValue43;
      float floatValue46 = floatValue3 * floatValue43;
      float floatValue47 = floatValue4 * floatValue43;
      float floatValue48 = floatValue44 / floatValue41;
      float floatValue49 = 1.0F - floatValue45 / floatValue42;
      float floatValue50 = (floatValue44 + floatValue46) / floatValue41;
      float floatValue51 = 1.0F - (floatValue45 + floatValue47) / floatValue42;
      int intValue52 = intValue9 >> 24 & 0xFF;
      if (intValue52 == 0) {
         intValue52 = 255;
      }

      float floatValue53 = (float)(intValue9 >> 16 & 0xFF) / 255.0F;
      float floatValue54 = (float)(intValue9 >> 8 & 0xFF) / 255.0F;
      float floatValue55 = (float)(intValue9 & 0xFF) / 255.0F;
      float floatValue56 = (float)intValue52 / 255.0F;
      RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.k);
      BufferBuilder bufferBuilder57 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      bufferBuilder57.vertex(matrix4f40, floatValue1, floatValue2, 0.0F).texture(floatValue48, floatValue49).color(floatValue53, floatValue54, floatValue55, floatValue56);
      bufferBuilder57.vertex(matrix4f40, floatValue1, floatValue2 + floatValue4, 0.0F).texture(floatValue48, floatValue51).color(floatValue53, floatValue54, floatValue55, floatValue56);
      bufferBuilder57.vertex(matrix4f40, floatValue1 + floatValue3, floatValue2 + floatValue4, 0.0F).texture(floatValue50, floatValue51).color(floatValue53, floatValue54, floatValue55, floatValue56);
      bufferBuilder57.vertex(matrix4f40, floatValue1 + floatValue3, floatValue2, 0.0F).texture(floatValue50, floatValue49).color(floatValue53, floatValue54, floatValue55, floatValue56);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder57.end());
      RenderSystem.setShaderTexture(0, 0);
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
   }

   @Generated
   private RenderPrimitives() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
