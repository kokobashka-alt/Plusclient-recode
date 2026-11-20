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
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.NativeImage.Format;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.joml.Matrix4f;

public final class MenuBackgroundResources {
   public static final Identifier a = Identifier.of("aspectdlc", "splash_bg");
   public static final Identifier b = Identifier.of("aspectdlc", "splash_wp");
   public static final Identifier c = Identifier.of("aspectdlc", "textures/splash/logo_sdf.png");
   private static float d;
   private static float e;
   private static float f;
   private static float g;
   private static boolean h = false;
   private static boolean i = false;
   private static boolean j = false;
   private static boolean k = false;
   private static float l = 1.7777778F;

   private MenuBackgroundResources() {
   }

   public static boolean a() {
      return h;
   }

   public static boolean b() {
      return j;
   }

   public static boolean c() {
      return k;
   }

   public static boolean d() {
      return i;
   }

   public static float e() {
      return l;
   }

   private static InputStream a(Identifier identifier0) {
      return dev.leg1tka.gui.menu.MenuBackgroundResources.class.getClassLoader().getResourceAsStream("assets/" + identifier0.getNamespace() + "/" + identifier0.getPath());
   }

   private static byte[] a(Identifier identifier0, ResourceFactory resourceFactory1) {
      try {
         if (identifier0.getNamespace().equals("aspectdlc")) {
            byte[] byteValueArray12;
            try (InputStream inputStream11 = a(identifier0)) {
               byteValueArray12 = inputStream11 == null ? null : inputStream11.readAllBytes();
            }

            return byteValueArray12;
         } else {
            Optional optional2 = resourceFactory1.getResource(identifier0);
            if (optional2.isEmpty()) {
               return null;
            } else {
               byte[] byteValueArray4;
               try (InputStream inputStream3 = ((Resource)optional2.get()).getInputStream()) {
                  byteValueArray4 = inputStream3.readAllBytes();
               }

               return byteValueArray4;
            }
         }
      } catch (Exception exception10) {
         return null;
      }
   }

   private static String a(String string0, ResourceFactory resourceFactory1, Set<Identifier> set2) {
      StringBuilder stringBuilder3 = new StringBuilder(string0.length() + 1024);

      for (String string7 : string0.split("\r?\n", -1)) {
         String string8 = string7.trim();
         if (string8.startsWith("#moj_import")) {
            int intValue9 = string8.indexOf(60);
            int intValue10 = string8.lastIndexOf(62);
            Identifier identifier11 = intValue9 >= 0 && intValue10 > intValue9 ? Identifier.tryParse(string8.substring(intValue9 + 1, intValue10)) : null;
            if (identifier11 != null) {
               Identifier identifier12 = identifier11.withPrefixedPath("shaders/include/");
               if (set2.add(identifier12)) {
                  byte[] byteValueArray13 = a(identifier12, resourceFactory1);
                  if (byteValueArray13 != null) {
                     stringBuilder3.append(a(new String(byteValueArray13, StandardCharsets.UTF_8), resourceFactory1, set2).replaceAll("(?m)^\\s*#version[^\\n]*", ""));
                  }
               }

               stringBuilder3.append('\n');
               continue;
            }
         }

         stringBuilder3.append(string7).append('\n');
      }

      return stringBuilder3.toString();
   }

   public static void a(ResourceFactory resourceFactory0) {
      MinecraftClient minecraftClient1 = MinecraftClient.getInstance();
      ResourceFactory resourceFactory2 = identifier2x -> {
         byte[] byteValueArray3x = a(identifier2x, resourceFactory0);
         if (byteValueArray3x == null) {
            return Optional.empty();
         } else {
            String string4x = identifier2x.getPath();
            if (string4x.endsWith(".vsh") || string4x.endsWith(".fsh") || string4x.endsWith(".glsl")) {
               byteValueArray3x = a(new String(byteValueArray3x, StandardCharsets.UTF_8), resourceFactory0, new HashSet<>()).getBytes(StandardCharsets.UTF_8);
            }

            byte[] byteValueArray5 = byteValueArray3x;
            return Optional.of(new Resource(minecraftClient1.getDefaultResourcePack(), () -> new ByteArrayInputStream(byteValueArray5)));
         }
      };
      ShaderProgramKey[] shaderProgramKeyArray3 = new ShaderProgramKey[]{
         ShaderProgramKeys.POSITION_COLOR, ShaderProgramKeys.POSITION_TEX_COLOR, ShaderProgramKeys.POSITION_TEX, dev.leg1tka.render.ShaderRegistry.c, dev.leg1tka.render.ShaderRegistry.h, dev.leg1tka.render.ShaderRegistry.n, dev.leg1tka.render.ShaderRegistry.o, dev.leg1tka.render.ShaderRegistry.r, dev.leg1tka.render.ShaderRegistry.j, dev.leg1tka.render.ShaderRegistry.P
      };
      boolean flag4 = true;

      for (ShaderProgramKey shaderProgramKey8 : shaderProgramKeyArray3) {
         try {
            minecraftClient1.getShaderLoader().preload(resourceFactory2, new ShaderProgramKey[]{shaderProgramKey8});
            if (minecraftClient1.getShaderLoader().getOrCreateProgram(shaderProgramKey8) == null) {
               flag4 = false;
               System.err.println("[Aspect] splash shader " + shaderProgramKey8 + " missing after preload");
            }
         } catch (Throwable throwable12) {
            flag4 = false;
            System.err.println("[Aspect] splash shader " + shaderProgramKey8 + " preload failed: " + throwable12);
         }
      }

      try {
         minecraftClient1.getShaderLoader().preload(resourceFactory2, new ShaderProgramKey[]{ShaderProgramKeys.POSITION});
      } catch (Throwable throwable11) {
         System.err.println("[Aspect] splash shader position preload failed: " + throwable11);
      }

      h = flag4;

      try {
         minecraftClient1.getShaderLoader().preload(resourceFactory2, new ShaderProgramKey[]{dev.leg1tka.render.ShaderRegistry.S});
         if (minecraftClient1.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.S) != null) {
            try (InputStream inputStream15 = a(c)) {
               if (inputStream15 != null) {
                  NativeImageBackedTexture nativeImageBackedTexture16 = new NativeImageBackedTexture(NativeImage.read(Format.RGBA, inputStream15));
                  nativeImageBackedTexture16.setFilter(true, false);
                  minecraftClient1.getTextureManager().registerTexture(c, nativeImageBackedTexture16);
                  i = true;
               }
            }
         }
      } catch (Throwable throwable14) {
         System.err.println("[Aspect] splash logo failed: " + throwable14);
      }

      f();
   }

   private static void f() {
      MinecraftClient minecraftClient0 = MinecraftClient.getInstance();
      Identifier identifier1 = dev.leg1tka.gui.menu.WallpaperSettings.a.a().c();
      CompletableFuture.runAsync(() -> {
         try {
            try (InputStream inputStream2 = a(identifier1)) {
               if (inputStream2 != null) {
                  NativeImage nativeImage3 = NativeImage.read(inputStream2);
                  float floatValue4 = (float)nativeImage3.getWidth() / (float)nativeImage3.getHeight();
                  NativeImage nativeImage5 = nativeImage3;
                  if (nativeImage3.getWidth() > 2560) {
                     nativeImage5 = new NativeImage(2560, Math.max(1, Math.round(2560.0F / floatValue4)), false);
                     nativeImage3.resizeSubRectTo(0, 0, nativeImage3.getWidth(), nativeImage3.getHeight(), nativeImage5);
                     nativeImage3.close();
                  }

                  short value6 = 256;
                  int intValue7 = Math.max(1, Math.round((float)value6 / floatValue4));
                  NativeImage nativeImage8 = new NativeImage(value6, intValue7, false);
                  nativeImage5.resizeSubRectTo(0, 0, nativeImage5.getWidth(), nativeImage5.getHeight(), nativeImage8);
                  a(nativeImage8, 9);
                  a(nativeImage8, 9);
                  a(nativeImage8, 9);
                  a(nativeImage8, 9);
                  minecraftClient0.execute(() -> {
                     NativeImageBackedTexture nativeImageBackedTexture2x = new NativeImageBackedTexture(nativeImage8);
                     nativeImageBackedTexture2x.setFilter(true, false);
                     minecraftClient0.getTextureManager().registerTexture(a, nativeImageBackedTexture2x);
                     j = true;
                  });
                  NativeImage nativeImage9 = nativeImage5;
                  minecraftClient0.execute(() -> {
                     NativeImageBackedTexture nativeImageBackedTexture3x = new NativeImageBackedTexture(nativeImage9);
                     nativeImageBackedTexture3x.setFilter(true, false);
                     minecraftClient0.getTextureManager().registerTexture(b, nativeImageBackedTexture3x);
                     l = floatValue4;
                     k = true;
                  });
                  return;
               }
            }
         } catch (Throwable throwable12) {
         }
      }, Util.getMainWorkerExecutor());
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5) {
      if (i && !(floatValue4 < 0.004F)) {
         float floatValue6 = floatValue3 * 1386.0F / 1318.0F;
         float floatValue7 = floatValue6 * 194.0F / 1386.0F;
         float floatValue8 = floatValue1 - floatValue6 * 0.5F;
         float floatValue9 = floatValue2 - floatValue7 * 0.49793813F;
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.setShaderTexture(0, c);
         ShaderProgram shaderProgram10 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.S);
         if (shaderProgram10 != null) {
            a(shaderProgram10, "OutlinePx", floatValue5);
            a(shaderProgram10, "Shadow", 0.2F);
            Matrix4f matrix4f11 = matrixStack0.peek().getPositionMatrix();
            BufferBuilder bufferBuilder12 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
            bufferBuilder12.vertex(matrix4f11, floatValue8, floatValue9, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue4);
            bufferBuilder12.vertex(matrix4f11, floatValue8, floatValue9 + floatValue7, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue4);
            bufferBuilder12.vertex(matrix4f11, floatValue8 + floatValue6, floatValue9 + floatValue7, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue4);
            bufferBuilder12.vertex(matrix4f11, floatValue8 + floatValue6, floatValue9, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue4);
            BufferRenderer.drawWithGlobalProgram(bufferBuilder12.end());
         }

         RenderSystem.setShaderTexture(0, 0);
         RenderSystem.depthMask(true);
      }
   }

   private static void a(ShaderProgram shaderProgram0, String string1, float floatValue2) {
      GlUniform glUniform3 = shaderProgram0.getUniform(string1);
      if (glUniform3 != null) {
         glUniform3.set(floatValue2);
      }
   }

   private static void a(NativeImage nativeImage0, int intValue1) {
      int intValue2 = nativeImage0.getWidth();
      int intValue3 = nativeImage0.getHeight();
      int[] intValueArray4 = new int[intValue2 * intValue3];
      int[] intValueArray5 = new int[intValue2 * intValue3];

      for (int intValue6 = 0; intValue6 < intValue3; intValue6++) {
         for (int intValue7 = 0; intValue7 < intValue2; intValue7++) {
            intValueArray4[intValue6 * intValue2 + intValue7] = nativeImage0.getColorArgb(intValue7, intValue6);
         }
      }

      a(intValueArray4, intValueArray5, intValue2, intValue3, intValue1, true);
      a(intValueArray5, intValueArray4, intValue2, intValue3, intValue1, false);

      for (int intValue8 = 0; intValue8 < intValue3; intValue8++) {
         for (int intValue9 = 0; intValue9 < intValue2; intValue9++) {
            nativeImage0.setColorArgb(intValue9, intValue8, intValueArray4[intValue8 * intValue2 + intValue9] | 0xFF000000);
         }
      }
   }

   private static void a(int[] intValueArray0, int[] intValueArray1, int intValue2, int intValue3, int intValue4, boolean flag5) {
      int intValue6 = flag5 ? intValue2 : intValue3;
      int intValue7 = flag5 ? intValue3 : intValue2;

      for (int intValue8 = 0; intValue8 < intValue7; intValue8++) {
         int intValue9 = 0;
         int intValue10 = 0;
         int intValue11 = 0;
         int intValue12 = 0;

         for (int intValue13 = -intValue4; intValue13 <= intValue4; intValue13++) {
            int intValue14 = intValueArray0[a(Math.max(0, Math.min(intValue6 - 1, intValue13)), intValue8, intValue2, flag5)];
            intValue9 += intValue14 >> 16 & 0xFF;
            intValue10 += intValue14 >> 8 & 0xFF;
            intValue11 += intValue14 & 0xFF;
            intValue12++;
         }

         for (int intValue16 = 0; intValue16 < intValue6; intValue16++) {
            intValueArray1[a(intValue16, intValue8, intValue2, flag5)] = 0xFF000000 | intValue9 / intValue12 << 16 | intValue10 / intValue12 << 8 | intValue11 / intValue12;
            int intValue17 = intValueArray0[a(Math.max(0, intValue16 - intValue4), intValue8, intValue2, flag5)];
            int intValue15 = intValueArray0[a(Math.min(intValue6 - 1, intValue16 + intValue4 + 1), intValue8, intValue2, flag5)];
            intValue9 += (intValue15 >> 16 & 0xFF) - (intValue17 >> 16 & 0xFF);
            intValue10 += (intValue15 >> 8 & 0xFF) - (intValue17 >> 8 & 0xFF);
            intValue11 += (intValue15 & 0xFF) - (intValue17 & 0xFF);
         }
      }
   }

   private static int a(int intValue0, int intValue1, int intValue2, boolean flag3) {
      return flag3 ? intValue1 * intValue2 + intValue0 : intValue0 * intValue2 + intValue1;
   }
}
