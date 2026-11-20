package dev.leg1tka.gui.menu;

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
import java.awt.Color;
import java.util.Arrays;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

public final class MenuBackdropRenderer {
   private static long a = -1L;
   private static int b = -8626945;
   private static int c = -41816;
   private static String d = null;
   private static int e = -8626945;

   private MenuBackdropRenderer() {
   }

   public static float a(float floatValue0) {
      return floatValue0 < 0.0F ? 0.0F : (floatValue0 > 1.0F ? 1.0F : floatValue0);
   }

   public static float b(float floatValue0) {
      floatValue0 = a(floatValue0);
      float floatValue1 = 1.0F - floatValue0;
      return 1.0F - floatValue1 * floatValue1 * floatValue1;
   }

   public static float a(float floatValue0, float floatValue1, float floatValue2, float floatValue3) {
      return floatValue0 + (floatValue1 - floatValue0) * Math.min(1.0F, floatValue2 * floatValue3);
   }

   public static int a(float floatValue0, int intValue1) {
      int intValue2 = Math.max(1, Math.min(255, (int)(a(floatValue0) * 255.0F)));
      return intValue2 << 24 | intValue1 & 16777215;
   }

   public static int a(int intValue0, float floatValue1) {
      int intValue2 = intValue0 >>> 24 & 0xFF;
      return a((float)intValue2 / 255.0F * floatValue1, intValue0);
   }

   public static float a() {
      return (float)((double)(System.currentTimeMillis() % 600000L) / 1000.0);
   }

   private static void h() {
      long longValue0 = System.currentTimeMillis() / 250L;
      if (longValue0 != a) {
         a = longValue0;
         int intValue2 = 0;
         int intValue3 = 0;
         String string4 = null;

         try {
            dev.leg1tka.gui.themes.ThemePalette value5 = AspectDLC.INSTANCE.themeStorage.b().a();
            string4 = value5.b() + Arrays.toString(value5.c);
            if (string4.equals(d)) {
               return;
            }

            if (value5.b().equals("Rainbow")) {
               intValue2 = dev.leg1tka.api.utils.text.ColorUtils.k(0);
               intValue3 = dev.leg1tka.api.utils.text.ColorUtils.k(90);
            } else {
               intValue2 = value5.c[0];
               intValue3 = value5.c.length > 1 ? value5.c[1] : value5.c[0];
            }
         } catch (Throwable throwable6) {
         }

         if (intValue2 == 0) {
            dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme backgroundTheme9 = dev.leg1tka.gui.menu.WallpaperSettings.a.a();
            string4 = "wp:" + backgroundTheme9.name();
            if (string4.equals(d)) {
               return;
            }

            intValue2 = backgroundTheme9.e();
            intValue3 = backgroundTheme9.f();
         }

         d = string4;
         e = 0xFF000000 | intValue2 & 16777215;
         intValue2 = a(intValue2);
         intValue3 = a(intValue3);
         if (a(intValue2, intValue3) < 60) {
            intValue3 = a(intValue2, 0.075F, 1.08F);
         }

         b = 0xFF000000 | intValue2 & 16777215;
         c = 0xFF000000 | intValue3 & 16777215;
      }
   }

   public static void b() {
      try {
         MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvent.of(Identifier.of("aspectdlc", "custom_click")), 1.0F));
      } catch (Throwable throwable1) {
      }
   }

   public static int c() {
      h();
      return b;
   }

   public static int d() {
      h();
      return c;
   }

   private static int a(int intValue0, int intValue1) {
      return Math.abs((intValue0 >> 16 & 0xFF) - (intValue1 >> 16 & 0xFF)) + Math.abs((intValue0 >> 8 & 0xFF) - (intValue1 >> 8 & 0xFF)) + Math.abs((intValue0 & 0xFF) - (intValue1 & 0xFF));
   }

   private static int a(int intValue0) {
      float[] floatValueArray1 = Color.RGBtoHSB(intValue0 >> 16 & 0xFF, intValue0 >> 8 & 0xFF, intValue0 & 0xFF, null);
      float floatValue2 = floatValueArray1[1] < 0.08F ? floatValueArray1[1] : Math.max(floatValueArray1[1], 0.55F);
      float floatValue3 = Math.max(floatValueArray1[2], 0.78F);
      return Color.HSBtoRGB(floatValueArray1[0], floatValue2, floatValue3);
   }

   private static int a(int intValue0, float floatValue1, float floatValue2) {
      float[] floatValueArray3 = Color.RGBtoHSB(intValue0 >> 16 & 0xFF, intValue0 >> 8 & 0xFF, intValue0 & 0xFF, null);
      return Color.HSBtoRGB((floatValueArray3[0] + floatValue1) % 1.0F, floatValueArray3[1], Math.min(1.0F, floatValueArray3[2] * floatValue2));
   }

   public static void e() {
      dev.leg1tka.render.ScreenBlurPass.a().a(16.0F);
      dev.leg1tka.render.ScreenBlurPass.a().c();
      dev.leg1tka.render.ScreenBlurPass.a().b();
      dev.leg1tka.render.ScreenBlurPass.a().a(1.0F);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, 0.0F);
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      if (!(floatValue6 < 0.01F)) {
         dev.leg1tka.render.RenderPrimitives.a(matrixStack0, floatValue1, floatValue2 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F), floatValue3, floatValue4, floatValue5, dev.leg1tka.gui.menu.MenuLayout.a(26.0F), a(0.18F * floatValue6, 0));
         dev.leg1tka.render.RenderPrimitives.a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, f(), floatValue6, 0.66F);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, a((0.03F + floatValue7 * 0.05F) * floatValue6, 16777215));
         int intValue8 = a((0.07F + floatValue7 * 0.08F) * floatValue6, 16777215);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, g(), intValue8, intValue8, intValue8, intValue8);
      }
   }

   public static int f() {
      h();
      return e;
   }

   public static float g() {
      return Math.max(dev.leg1tka.gui.menu.MenuLayout.c(), dev.leg1tka.gui.menu.MenuLayout.a(0.75F));
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5) {
      if (!(floatValue5 < 0.01F)) {
         float floatValue6 = dev.leg1tka.gui.menu.MenuLayout.a(70.0F);
         dev.leg1tka.render.RenderPrimitives.a(matrixStack0, floatValue1 - floatValue3 * 0.5F, floatValue2 - floatValue4 * 0.5F, floatValue3, floatValue4, Math.min(floatValue3, floatValue4) * 0.5F, floatValue6, a(0.34F * floatValue5, 0));
      }
   }

   public static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6) {
      if (!(floatValue5 < 0.01F)) {
         int intValue7 = c();
         int intValue8 = d();
         float floatValue9 = floatValue4 * 0.5F;
         dev.leg1tka.render.RenderPrimitives.a(matrixStack0, floatValue1 + dev.leg1tka.gui.menu.MenuLayout.a(4.0F), floatValue2 + dev.leg1tka.gui.menu.MenuLayout.a(4.0F), floatValue3 - dev.leg1tka.gui.menu.MenuLayout.a(8.0F), floatValue4, floatValue9, dev.leg1tka.gui.menu.MenuLayout.a(16.0F + floatValue6 * 8.0F), a((0.38F + floatValue6 * 0.25F) * floatValue5, intValue7));
         dev.leg1tka.render.RenderPrimitives.a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue9, a(floatValue5, intValue7), a(floatValue5, intValue8), true);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue9, a((0.2F + floatValue6 * 0.1F) * floatValue5, 16777215), a(0.012F * floatValue5, 16777215));
         int intValue10 = a(0.35F * floatValue5, 16777215);
         int intValue11 = a(0.06F * floatValue5, 16777215);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue9, g(), intValue10, intValue10, intValue11, intValue11);
      }
   }

   public static void a(
      MatrixStack matrixStack0, Identifier identifier1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, float floatValue9, float floatValue10, int intValue11
   ) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableDepthTest();
      ShaderProgram shaderProgram12 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.j);
      if (shaderProgram12 != null) {
         GlUniform glUniform13 = shaderProgram12.getUniform("Size");
         GlUniform glUniform14 = shaderProgram12.getUniform("Radius");
         GlUniform glUniform15 = shaderProgram12.getUniform("Smoothness");
         GlUniform glUniform16 = shaderProgram12.getUniform("ColorModulator");
         if (glUniform13 != null) {
            glUniform13.set(floatValue4, floatValue5);
         }

         if (glUniform14 != null) {
            glUniform14.set(floatValue6, floatValue6, floatValue6, floatValue6);
         }

         if (glUniform15 != null) {
            glUniform15.set(1.0F);
         }

         if (glUniform16 != null) {
            glUniform16.set(1.0F, 1.0F, 1.0F, 1.0F);
         }

         RenderSystem.setShaderTexture(0, identifier1);
         float floatValue17 = (float)(intValue11 >> 16 & 0xFF) / 255.0F;
         float floatValue18 = (float)(intValue11 >> 8 & 0xFF) / 255.0F;
         float floatValue19 = (float)(intValue11 & 0xFF) / 255.0F;
         float floatValue20 = (float)(intValue11 >>> 24 & 0xFF) / 255.0F;
         Matrix4f matrix4f21 = matrixStack0.peek().getPositionMatrix();
         BufferBuilder bufferBuilder22 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
         bufferBuilder22.vertex(matrix4f21, floatValue2, floatValue3, 0.0F).texture(floatValue7, floatValue8).color(floatValue17, floatValue18, floatValue19, floatValue20);
         bufferBuilder22.vertex(matrix4f21, floatValue2, floatValue3 + floatValue5, 0.0F).texture(floatValue7, floatValue10).color(floatValue17, floatValue18, floatValue19, floatValue20);
         bufferBuilder22.vertex(matrix4f21, floatValue2 + floatValue4, floatValue3 + floatValue5, 0.0F).texture(floatValue9, floatValue10).color(floatValue17, floatValue18, floatValue19, floatValue20);
         bufferBuilder22.vertex(matrix4f21, floatValue2 + floatValue4, floatValue3, 0.0F).texture(floatValue9, floatValue8).color(floatValue17, floatValue18, floatValue19, floatValue20);
         BufferRenderer.drawWithGlobalProgram(bufferBuilder22.end());
         RenderSystem.setShaderTexture(0, 0);
      }
   }

   public static void a(MatrixStack matrixStack0, Identifier identifier1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, int intValue9) {
      float floatValue10 = floatValue6 / floatValue7;
      float floatValue11 = floatValue2 / floatValue3;
      float floatValue12 = 0.0F;
      float floatValue13 = 0.0F;
      float floatValue14 = 1.0F;
      float floatValue15 = 1.0F;
      if (floatValue10 > floatValue11) {
         float floatValue16 = floatValue11 / floatValue10;
         floatValue13 = (1.0F - floatValue16) * 0.5F;
         floatValue15 = floatValue13 + floatValue16;
      } else {
         float floatValue17 = floatValue10 / floatValue11;
         floatValue12 = (1.0F - floatValue17) * 0.5F;
         floatValue14 = floatValue12 + floatValue17;
      }

      a(matrixStack0, identifier1, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, floatValue12, floatValue13, floatValue14, floatValue15, intValue9);
   }

   public static void a(
      MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, float floatValue9, float floatValue10
   ) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, floatValue9, floatValue10, c(), d());
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
      float floatValue10,
      int intValue11,
      int intValue12
   ) {
      if (!(floatValue10 < 0.01F)) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         ShaderProgram shaderProgram13 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.O);
         if (shaderProgram13 != null) {
            a(shaderProgram13, "Size", floatValue3, floatValue4);
            a(shaderProgram13, "Radius", floatValue5);
            a(shaderProgram13, "Px", dev.leg1tka.gui.menu.MenuLayout.c());
            a(shaderProgram13, "Hover", floatValue6);
            a(shaderProgram13, "Press", floatValue7);
            a(shaderProgram13, "Time", a());
            a(shaderProgram13, "Mouse", MathHelper.clamp(floatValue8, -0.5F, 1.5F), MathHelper.clamp(floatValue9, -0.5F, 1.5F));
            a(shaderProgram13, "Theme1", intValue11);
            a(shaderProgram13, "Theme2", intValue12);
            a(shaderProgram13, "Base", dev.leg1tka.render.RenderPrimitives.a(f(), 1.0F));
            Matrix4f matrix4f14 = matrixStack0.peek().getPositionMatrix();
            BufferBuilder bufferBuilder15 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
            bufferBuilder15.vertex(matrix4f14, floatValue1, floatValue2, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue10);
            bufferBuilder15.vertex(matrix4f14, floatValue1, floatValue2 + floatValue4, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue10);
            bufferBuilder15.vertex(matrix4f14, floatValue1 + floatValue3, floatValue2 + floatValue4, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue10);
            bufferBuilder15.vertex(matrix4f14, floatValue1 + floatValue3, floatValue2, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue10);
            BufferRenderer.drawWithGlobalProgram(bufferBuilder15.end());
         } else {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, a(0.6F * floatValue10, 1316382));
         }

         RenderSystem.depthMask(true);
      }
   }

   public static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      if (!(floatValue7 < 0.01F)) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         ShaderProgram shaderProgram8 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.Q);
         if (shaderProgram8 != null) {
            a(shaderProgram8, "Size", floatValue3, floatValue3);
            a(shaderProgram8, "Px", dev.leg1tka.gui.menu.MenuLayout.c());
            a(shaderProgram8, "Thick", floatValue4);
            a(shaderProgram8, "Progress", a(floatValue5));
            a(shaderProgram8, "Track", floatValue6);
            a(shaderProgram8, "Theme1", c());
            a(shaderProgram8, "Theme2", d());
            float floatValue9 = floatValue1 - floatValue3 * 0.5F;
            float floatValue10 = floatValue2 - floatValue3 * 0.5F;
            Matrix4f matrix4f11 = matrixStack0.peek().getPositionMatrix();
            BufferBuilder bufferBuilder12 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
            bufferBuilder12.vertex(matrix4f11, floatValue9, floatValue10, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue7);
            bufferBuilder12.vertex(matrix4f11, floatValue9, floatValue10 + floatValue3, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue7);
            bufferBuilder12.vertex(matrix4f11, floatValue9 + floatValue3, floatValue10 + floatValue3, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue7);
            bufferBuilder12.vertex(matrix4f11, floatValue9 + floatValue3, floatValue10, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue7);
            BufferRenderer.drawWithGlobalProgram(bufferBuilder12.end());
         }

         RenderSystem.depthMask(true);
      }
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, int intValue8, int intValue9, float floatValue10) {
      a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, 0.0F, intValue8, intValue9, floatValue10);
   }

   public static void a(
      MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, int intValue9, int intValue10, float floatValue11
   ) {
      if (!(floatValue11 < 0.004F) && !(floatValue3 <= 0.0F)) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         float floatValue12 = floatValue4 + Math.max(0.0F, floatValue5);
         ShaderProgram shaderProgram13 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.R);
         if (shaderProgram13 != null) {
            a(shaderProgram13, "Size", floatValue3, floatValue12);
            a(shaderProgram13, "Px", dev.leg1tka.gui.menu.MenuLayout.c());
            a(shaderProgram13, "Head", floatValue4);
            a(shaderProgram13, "Tail", Math.max(0.0F, floatValue5));
            a(shaderProgram13, "RadTop", floatValue6);
            a(shaderProgram13, "RadBot", floatValue7);
            a(shaderProgram13, "Feather", floatValue8);
            a(shaderProgram13, "Theme1", intValue9);
            a(shaderProgram13, "Theme2", intValue10);
            Matrix4f matrix4f14 = matrixStack0.peek().getPositionMatrix();
            BufferBuilder bufferBuilder15 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
            bufferBuilder15.vertex(matrix4f14, floatValue1, floatValue2, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue11);
            bufferBuilder15.vertex(matrix4f14, floatValue1, floatValue2 + floatValue12, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue11);
            bufferBuilder15.vertex(matrix4f14, floatValue1 + floatValue3, floatValue2 + floatValue12, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue11);
            bufferBuilder15.vertex(matrix4f14, floatValue1 + floatValue3, floatValue2, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue11);
            BufferRenderer.drawWithGlobalProgram(bufferBuilder15.end());
         }

         RenderSystem.depthMask(true);
      }
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      if (!(floatValue5 <= 0.001F)) {
         float floatValue7 = floatValue3 / 10.0F;
         float floatValue8 = floatValue1 - 3.4F * floatValue7;
         float floatValue9 = floatValue2 + 0.1F * floatValue7;
         float floatValue10 = floatValue1 - 0.9F * floatValue7;
         float floatValue11 = floatValue2 + 2.6F * floatValue7;
         float floatValue12 = floatValue1 + 3.8F * floatValue7;
         float floatValue13 = floatValue2 - 2.6F * floatValue7;
         float floatValue14 = a(floatValue5 / 0.38F);
         float floatValue15 = a((floatValue5 - 0.38F) / 0.62F);
         a(matrixStack0, floatValue8, floatValue9, floatValue10, floatValue11, floatValue14, floatValue4, intValue6);
         if (floatValue15 > 0.0F) {
            a(matrixStack0, floatValue10, floatValue11, floatValue12, floatValue13, floatValue15, floatValue4, intValue6);
         }
      }
   }

   private static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7) {
      float floatValue8 = floatValue3 - floatValue1;
      float floatValue9 = floatValue4 - floatValue2;
      float floatValue10 = (float)Math.sqrt((double)(floatValue8 * floatValue8 + floatValue9 * floatValue9)) * floatValue5;
      matrixStack0.push();
      matrixStack0.translate(floatValue1, floatValue2, 0.0F);
      matrixStack0.multiply(RotationAxis.POSITIVE_Z.rotation((float)Math.atan2((double)floatValue9, (double)floatValue8)));
      dev.leg1tka.render.RenderPrimitives.b(matrixStack0, -floatValue6 * 0.5F, -floatValue6 * 0.5F, floatValue10 + floatValue6, floatValue6, floatValue6 * 0.5F, intValue7);
      matrixStack0.pop();
   }

   private static void a(ShaderProgram shaderProgram0, String string1, float floatValue2) {
      GlUniform glUniform3 = shaderProgram0.getUniform(string1);
      if (glUniform3 != null) {
         glUniform3.set(floatValue2);
      }
   }

   private static void a(ShaderProgram shaderProgram0, String string1, float floatValue2, float floatValue3) {
      GlUniform glUniform4 = shaderProgram0.getUniform(string1);
      if (glUniform4 != null) {
         glUniform4.set(floatValue2, floatValue3);
      }
   }

   private static void a(ShaderProgram shaderProgram0, String string1, int intValue2) {
      GlUniform glUniform3 = shaderProgram0.getUniform(string1);
      if (glUniform3 != null) {
         glUniform3.set((float)(intValue2 >> 16 & 0xFF) / 255.0F, (float)(intValue2 >> 8 & 0xFF) / 255.0F, (float)(intValue2 & 0xFF) / 255.0F, 1.0F);
      }
   }

   public static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      matrixStack0.push();
      matrixStack0.translate(floatValue1, floatValue2, 0.0F);
      matrixStack0.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue3));
      float floatValue8 = floatValue4 * 0.34F;
      matrixStack0.push();
      matrixStack0.translate(0.0F, -floatValue8, 0.0F);
      matrixStack0.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45.0F));
      dev.leg1tka.render.RenderPrimitives.b(matrixStack0, -floatValue4 * 0.5F, -floatValue5 * 0.5F, floatValue4, floatValue5, floatValue5 * 0.5F, intValue6);
      matrixStack0.pop();
      matrixStack0.push();
      matrixStack0.translate(0.0F, floatValue8, 0.0F);
      matrixStack0.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-45.0F));
      dev.leg1tka.render.RenderPrimitives.b(matrixStack0, -floatValue4 * 0.5F, -floatValue5 * 0.5F, floatValue4, floatValue5, floatValue5 * 0.5F, intValue6);
      matrixStack0.pop();
      matrixStack0.pop();
   }
}
