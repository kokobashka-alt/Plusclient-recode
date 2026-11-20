package dev.leg1tka.render.font;

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
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class MsdfTextRenderer implements dev.leg1tka.api.ClientAccess {
   private static char c;
   private final dev.leg1tka.render.font.MsdfFont d;
   private final float e;
   private static ShaderProgram f;
   private static GlUniform g;
   private static GlUniform h;
   private static GlUniform i;
   private static GlUniform j;
   private static GlUniform k;
   private static GlUniform l;
   private static GlUniform m;
   private static GlUniform n;

   public MsdfTextRenderer(dev.leg1tka.render.font.MsdfFont value1, float floatValue2) {
      this.d = value1;
      this.e = floatValue2;
   }

   public MsdfTextRenderer(String string1, float floatValue2) {
      this.d = dev.leg1tka.render.font.MsdfFont.i().a(string1).c(string1).b(string1).a();
      this.e = floatValue2;
   }

   public void a(MatrixStack matrixStack1, String string2, double doubleValue3, double doubleValue5, int intValue7) {
      this.d(matrixStack1, string2, (float)doubleValue3, (float)doubleValue5, intValue7);
   }

   public void a(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5) {
      this.d(matrixStack1, string2, floatValue3, floatValue4, intValue5);
   }

   public void a(String string1, float floatValue2, float floatValue3, int intValue4) {
      MatrixStack matrixStack5 = new MatrixStack();
      this.d(matrixStack5, string1, floatValue2, floatValue3, intValue4);
   }

   public void b(MatrixStack matrixStack1, String string2, double doubleValue3, double doubleValue5, int intValue7) {
      this.d(matrixStack1, string2, (float)(doubleValue3 - (double)this.a(string2) / 2.0), (float)doubleValue5, intValue7);
   }

   public void b(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5) {
      this.d(matrixStack1, string2, floatValue3 - this.a(string2) / 2.0F, floatValue4, intValue5);
   }

   public void c(MatrixStack matrixStack1, String string2, double doubleValue3, double doubleValue5, int intValue7) {
      this.d(matrixStack1, string2, (float)(doubleValue3 - (double)this.a(string2)), (float)doubleValue5, intValue7);
   }

   public void c(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5) {
      this.d(matrixStack1, string2, floatValue3 - this.a(string2), floatValue4, intValue5);
   }

   public void d(MatrixStack matrixStack1, String string2, double doubleValue3, double doubleValue5, int intValue7) {
      this.d(matrixStack1, string2, (float)doubleValue3, (float)doubleValue5, intValue7);
   }

   public void d(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5) {
      this.a(matrixStack1, string2, floatValue3, floatValue4, intValue5, false);
   }

   public void e(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5) {
      this.a(matrixStack1, string2, floatValue3, floatValue4, intValue5, true);
   }

   private void a(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5, boolean flag6) {
      if (string2 != null && !string2.isEmpty()) {
         float floatValue7 = this.e * 0.5F;
         floatValue4--;
         Matrix4f matrix4f8 = matrixStack1.peek().getPositionMatrix();
         BufferBuilder bufferBuilder9 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
         this.d.b(matrix4f8, bufferBuilder9, floatValue7, string2, 0.0F, floatValue3, floatValue4 + this.d.g() * floatValue7, 0.0F, 255, 255, 255, 255, flag6);
         BuiltBuffer builtBuffer10 = bufferBuilder9.endNullable();
         if (builtBuffer10 != null) {
            this.a(builtBuffer10, intValue5);
         }
      }
   }

   public void a(VertexConsumer vertexConsumer1, Matrix4f matrix4f2, String string3, float floatValue4, float floatValue5, int intValue6) {
      this.a(vertexConsumer1, matrix4f2, string3, floatValue4, floatValue5, intValue6, false);
   }

   public void a(VertexConsumer vertexConsumer1, Matrix4f matrix4f2, String string3, float floatValue4, float floatValue5, int intValue6, boolean flag7) {
      if (string3 != null && !string3.isEmpty()) {
         float floatValue8 = this.e * 0.5F;
         floatValue5--;
         int intValue9 = intValue6 >> 24 & 0xFF;
         if (intValue9 == 0) {
            intValue9 = 255;
         }

         this.d.b(matrix4f2, vertexConsumer1, floatValue8, string3, 0.0F, floatValue4, floatValue5 + this.d.g() * floatValue8, 0.0F, intValue6 >> 16 & 0xFF, intValue6 >> 8 & 0xFF, intValue6 & 0xFF, intValue9, flag7);
      }
   }

   public void a(BuiltBuffer builtBuffer1) {
      this.a(builtBuffer1, -1);
   }

   private void a(BuiltBuffer builtBuffer1, int intValue2) {
      ShaderProgram shaderProgram3 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.r);
      if (shaderProgram3 == null) {
         builtBuffer1.close();
      } else {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableCull();
         this.a(shaderProgram3, intValue2);
         RenderSystem.setShaderTexture(0, this.d.b());
         this.d.a();
         RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.r);
         BufferRenderer.drawWithGlobalProgram(builtBuffer1);
         RenderSystem.setShaderTexture(0, 0);
         RenderSystem.enableCull();
      }
   }

   public void a(String string1, float floatValue2, float floatValue3, int intValue4, int intValue5) {
      MatrixStack matrixStack6 = new MatrixStack();
      this.a(matrixStack6, string1, floatValue2, floatValue3, intValue4, intValue5);
   }

   public void a(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5, int intValue6) {
      if (string2 != null && !string2.isEmpty()) {
         float floatValue7 = this.a(string2);
         float floatValue8 = floatValue3;

         for (int intValue9 = 0; intValue9 < string2.length(); intValue9++) {
            char character10 = string2.charAt(intValue9);
            String string11 = String.valueOf(character10);
            float floatValue12 = this.a(string11);
            float floatValue13 = floatValue7 > 0.0F ? (floatValue8 - floatValue3) / floatValue7 : 0.0F;
            int intValue14 = a(intValue5, intValue6, floatValue13);
            this.d(matrixStack1, string11, floatValue8, floatValue4, intValue14);
            floatValue8 += floatValue12;
         }
      }
   }

   public void a(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5, int intValue6, int intValue7, int intValue8) {
      if (string2 != null && !string2.isEmpty()) {
         float floatValue9 = this.a(string2);
         float floatValue10 = floatValue3;

         for (int intValue11 = 0; intValue11 < string2.length(); intValue11++) {
            char character12 = string2.charAt(intValue11);
            String string13 = String.valueOf(character12);
            float floatValue14 = this.a(string13);
            float floatValue15 = floatValue9 > 0.0F ? (floatValue10 - floatValue3) / floatValue9 : 0.0F;
            int intValue16 = a(intValue5, intValue6, floatValue15);
            int intValue17 = a(intValue7, intValue8, floatValue15);
            int intValue18 = a(intValue16, intValue17, 0.5F);
            this.d(matrixStack1, string13, floatValue10, floatValue4, intValue18);
            floatValue10 += floatValue14;
         }
      }
   }

   public void b(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5, int intValue6) {
      if (string2 != null && !string2.isEmpty()) {
         int intValue7 = a(intValue5, intValue6, 0.5F);
         this.d(matrixStack1, string2, floatValue3, floatValue4, intValue7);
      }
   }

   public void a(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      if (string2 != null && !string2.isEmpty()) {
         if (!(floatValue5 <= 1.0F)) {
            int intValue7 = intValue6 >>> 24 & 0xFF;
            if (intValue7 == 0) {
               intValue7 = 255;
            }

            if (intValue7 > 4) {
               float floatValue8 = this.e * 0.5F;
               floatValue4--;
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               RenderSystem.disableCull();
               ShaderProgram shaderProgram9 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.r);
               if (shaderProgram9 != null) {
                  GlUniform glUniform10 = shaderProgram9.getUniform("TextureSize");
                  GlUniform glUniform11 = shaderProgram9.getUniform("Range");
                  GlUniform glUniform12 = shaderProgram9.getUniform("Thickness");
                  GlUniform glUniform13 = shaderProgram9.getUniform("EdgeStrength");
                  GlUniform glUniform14 = shaderProgram9.getUniform("Color");
                  GlUniform glUniform15 = shaderProgram9.getUniform("Outline");
                  GlUniform glUniform16 = shaderProgram9.getUniform("OutlineThickness");
                  GlUniform glUniform17 = shaderProgram9.getUniform("OutlineColor");
                  if (glUniform10 != null) {
                     glUniform10.set(this.d.c(), this.d.d());
                  }

                  if (glUniform11 != null) {
                     glUniform11.set(this.d.e());
                  }

                  if (glUniform12 != null) {
                     glUniform12.set(0.0F);
                  }

                  if (glUniform13 != null) {
                     glUniform13.set(0.5F);
                  }

                  if (glUniform15 != null) {
                     glUniform15.set(0);
                  }

                  if (glUniform16 != null) {
                     glUniform16.set(0.0F);
                  }

                  if (glUniform17 != null) {
                     glUniform17.set(1.0F, 1.0F, 1.0F, 1.0F);
                  }

                  RenderSystem.setShaderTexture(0, this.d.b());
                  this.d.a();
                  float floatValue18 = floatValue3;
                  float floatValue19 = 25.0F;
                  float floatValue20 = floatValue3 + floatValue5 - floatValue19;

                  for (int intValue21 = 0; intValue21 < string2.length(); intValue21++) {
                     String string22 = String.valueOf(string2.charAt(intValue21));
                     float floatValue23 = this.a(string22);
                     if (floatValue18 > floatValue3 + floatValue5 && intValue21 > 0) {
                        break;
                     }

                     int intValue24 = intValue6;
                     if (floatValue18 > floatValue20) {
                        float floatValue25 = (floatValue18 - floatValue20) / floatValue19;
                        floatValue25 = Math.max(0.0F, Math.min(1.0F, floatValue25));
                        float floatValue26 = (float)Math.cos((double)floatValue25 * Math.PI / 2.0);
                        int intValue27 = (int)((float)intValue7 * floatValue26);
                        intValue24 = intValue6 & 16777215 | intValue27 << 24;
                     }

                     if ((intValue24 >>> 24 & 0xFF) > 4 && this.a(string22, floatValue8)) {
                        float[] floatValueArray30 = this.a(intValue24);
                        if (glUniform14 != null) {
                           glUniform14.set(floatValueArray30[0], floatValueArray30[1], floatValueArray30[2], floatValueArray30[3]);
                        }

                        Matrix4f matrix4f31 = matrixStack1.peek().getPositionMatrix();
                        BufferBuilder bufferBuilder32 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
                        this.d.a(matrix4f31, bufferBuilder32, floatValue8, string22, 0.0F, floatValue18, floatValue4 + this.d.g() * floatValue8, 0.0F, 255, 255, 255, 255);
                        RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.r);
                        dev.leg1tka.render.RenderPrimitives.a(bufferBuilder32);
                     }

                     floatValue18 += floatValue23;
                  }

                  RenderSystem.setShaderTexture(0, 0);
                  RenderSystem.enableCull();
               }
            }
         }
      }
   }

   public void a(String string1, float floatValue2, float floatValue3, int intValue4, int intValue5, float floatValue6) {
      MatrixStack matrixStack7 = new MatrixStack();
      this.a(matrixStack7, string1, floatValue2, floatValue3, intValue4, intValue5, floatValue6, 1.15F);
   }

   public void a(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5, int intValue6, float floatValue7) {
      this.a(matrixStack1, string2, floatValue3, floatValue4, intValue5, intValue6, floatValue7, 1.15F);
   }

   public void a(String string1, float floatValue2, float floatValue3, int intValue4, int intValue5, float floatValue6, float floatValue7) {
      MatrixStack matrixStack8 = new MatrixStack();
      this.a(matrixStack8, string1, floatValue2, floatValue3, intValue4, intValue5, floatValue6, floatValue7);
   }

   public void a(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5, int intValue6, float floatValue7, float floatValue8) {
      if (string2 != null && !string2.isEmpty()) {
         float floatValue9 = this.a(string2);
         float floatValue10 = floatValue3;
         double doubleValue11 = (double)System.currentTimeMillis() * 0.001 * (double)Math.max(0.01F, floatValue7) % 2.0;
         float floatValue13 = Math.max(0.01F, floatValue8);

         for (int intValue14 = 0; intValue14 < string2.length(); intValue14++) {
            char character15 = string2.charAt(intValue14);
            String string16 = String.valueOf(character15);
            float floatValue17 = this.a(string16);
            float floatValue18 = floatValue9 > 0.0F ? (floatValue10 - floatValue3) / floatValue9 : 0.0F;
            float floatValue19 = a(floatValue18 * floatValue13 + (float)doubleValue11);
            int intValue20 = a(intValue5, intValue6, floatValue19);
            this.d(matrixStack1, string16, floatValue10, floatValue4, intValue20);
            floatValue10 += floatValue17;
         }
      }
   }

   public void c(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5, int intValue6) {
      if (string2 != null && !string2.isEmpty()) {
         this.d(matrixStack1, string2, floatValue3 - 1.0F, floatValue4, intValue6);
         this.d(matrixStack1, string2, floatValue3 + 1.0F, floatValue4, intValue6);
         this.d(matrixStack1, string2, floatValue3, floatValue4 - 1.0F, intValue6);
         this.d(matrixStack1, string2, floatValue3, floatValue4 + 1.0F, intValue6);
         this.d(matrixStack1, string2, floatValue3, floatValue4, intValue5);
      }
   }

   public void f(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5) {
      if (string2 != null && !string2.isEmpty()) {
         int intValue6 = 1426063360;
         this.d(matrixStack1, string2, floatValue3 + 1.0F, floatValue4 + 1.0F, intValue6);
         this.d(matrixStack1, string2, floatValue3, floatValue4, intValue5);
      }
   }

   public void e(MatrixStack matrixStack1, String string2, double doubleValue3, double doubleValue5, int intValue7) {
      this.g(matrixStack1, string2, (float)doubleValue3, (float)doubleValue5, intValue7);
   }

   public void g(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5) {
      if (string2 != null && !string2.isEmpty()) {
         float floatValue6 = this.e * 0.5F;
         floatValue4--;
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableCull();
         ShaderProgram shaderProgram7 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.r);
         if (shaderProgram7 != null) {
            GlUniform glUniform8 = shaderProgram7.getUniform("TextureSize");
            GlUniform glUniform9 = shaderProgram7.getUniform("Range");
            GlUniform glUniform10 = shaderProgram7.getUniform("Thickness");
            GlUniform glUniform11 = shaderProgram7.getUniform("EdgeStrength");
            GlUniform glUniform12 = shaderProgram7.getUniform("Color");
            if (glUniform8 != null) {
               glUniform8.set(this.d.c(), this.d.d());
            }

            if (glUniform9 != null) {
               glUniform9.set(this.d.e());
            }

            if (glUniform10 != null) {
               glUniform10.set(0.0F);
            }

            if (glUniform11 != null) {
               glUniform11.set(0.5F);
            }

            RenderSystem.setShaderTexture(0, this.d.b());
            this.d.a();
            float floatValue13 = floatValue3;
            int intValue14 = intValue5;
            StringBuilder stringBuilder15 = new StringBuilder();

            for (int intValue16 = 0; intValue16 < string2.length(); intValue16++) {
               char character17 = string2.charAt(intValue16);
               if (character17 == 167 && intValue16 + 1 < string2.length()) {
                  if (!stringBuilder15.isEmpty()) {
                     this.a(matrixStack1, glUniform12, stringBuilder15.toString(), floatValue13, floatValue4 + this.d.g() * floatValue6, floatValue6, intValue14);
                     floatValue13 += this.a(stringBuilder15.toString());
                     stringBuilder15.setLength(0);
                  }

                  char character18 = string2.charAt(intValue16 + 1);
                  int intValue19 = this.a(character18, intValue5);
                  if (intValue19 != -1) {
                     intValue14 = intValue19;
                  }

                  intValue16++;
               } else {
                  stringBuilder15.append(character17);
               }
            }

            if (!stringBuilder15.isEmpty()) {
               this.a(matrixStack1, glUniform12, stringBuilder15.toString(), floatValue13, floatValue4 + this.d.g() * floatValue6, floatValue6, intValue14);
            }

            RenderSystem.setShaderTexture(0, 0);
            RenderSystem.enableCull();
         }
      }
   }

   private void a(MatrixStack matrixStack1, GlUniform glUniform2, String string3, float floatValue4, float floatValue5, float floatValue6, int intValue7) {
      if (this.a(string3, floatValue6)) {
         float[] floatValueArray8 = this.a(intValue7);
         if (glUniform2 != null) {
            glUniform2.set(floatValueArray8[0], floatValueArray8[1], floatValueArray8[2], floatValueArray8[3]);
         }

         Matrix4f matrix4f9 = matrixStack1.peek().getPositionMatrix();
         BufferBuilder bufferBuilder10 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
         this.d.a(matrix4f9, bufferBuilder10, floatValue6, string3, 0.0F, floatValue4, floatValue5, 0.0F, 255, 255, 255, 255);
         RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.r);
         dev.leg1tka.render.RenderPrimitives.a(bufferBuilder10);
      }
   }

   private boolean a(String string1, float floatValue2) {
      return string1 != null && !string1.isEmpty() && this.d.a(string1, floatValue2) > 0.0F;
   }

   private void a(ShaderProgram shaderProgram1, int intValue2) {
      if (shaderProgram1 != f) {
         f = shaderProgram1;
         g = shaderProgram1.getUniform("TextureSize");
         h = shaderProgram1.getUniform("Range");
         i = shaderProgram1.getUniform("Thickness");
         j = shaderProgram1.getUniform("EdgeStrength");
         k = shaderProgram1.getUniform("Color");
         l = shaderProgram1.getUniform("Outline");
         m = shaderProgram1.getUniform("OutlineThickness");
         n = shaderProgram1.getUniform("OutlineColor");
      }

      if (g != null) {
         g.set(this.d.c(), this.d.d());
      }

      if (h != null) {
         h.set(this.d.e());
      }

      if (i != null) {
         i.set(0.0F);
      }

      if (j != null) {
         j.set(0.5F);
      }

      if (l != null) {
         l.set(0);
      }

      if (m != null) {
         m.set(0.0F);
      }

      if (n != null) {
         n.set(0.0F, 0.0F, 0.0F, 1.0F);
      }

      int intValue3 = intValue2 >> 24 & 0xFF;
      if (intValue3 == 0) {
         intValue3 = 255;
      }

      if (k != null) {
         k.set((float)(intValue2 >> 16 & 0xFF) / 255.0F, (float)(intValue2 >> 8 & 0xFF) / 255.0F, (float)(intValue2 & 0xFF) / 255.0F, (float)intValue3 / 255.0F);
      }
   }

   private int a(char character1, int intValue2) {
      int intValue3 = intValue2 >> 24 & 0xFF;
      if (intValue3 == 0) {
         intValue3 = 255;
      }
      return switch (character1) {
         case '0' -> intValue3 << 24 | 0;
         case '1' -> intValue3 << 24 | 170;
         case '2' -> intValue3 << 24 | 43520;
         case '3' -> intValue3 << 24 | 43690;
         case '4' -> intValue3 << 24 | 11141120;
         case '5' -> intValue3 << 24 | 11141290;
         case '6' -> intValue3 << 24 | 16755200;
         case '7' -> intValue3 << 24 | 11184810;
         case '8' -> intValue3 << 24 | 5592405;
         case '9' -> intValue3 << 24 | 5592575;
         default -> -1;
         case 'A', 'a' -> intValue3 << 24 | 5635925;
         case 'B', 'b' -> intValue3 << 24 | 5636095;
         case 'C', 'c' -> intValue3 << 24 | 16733525;
         case 'D', 'd' -> intValue3 << 24 | 16733695;
         case 'E', 'e' -> intValue3 << 24 | 16777045;
         case 'F', 'f' -> intValue3 << 24 | 16777215;
         case 'R', 'r' -> intValue2;
      };
   }

   private float[] a(int intValue1) {
      int intValue2 = intValue1 >> 24 & 0xFF;
      int intValue3 = intValue1 >> 16 & 0xFF;
      int intValue4 = intValue1 >> 8 & 0xFF;
      int intValue5 = intValue1 & 0xFF;
      if (intValue2 == 0) {
         intValue2 = 255;
      }

      return new float[]{(float)intValue3 / 255.0F, (float)intValue4 / 255.0F, (float)intValue5 / 255.0F, (float)intValue2 / 255.0F};
   }

   public static int a(int intValue0, int intValue1, float floatValue2) {
      floatValue2 = Math.max(0.0F, Math.min(1.0F, floatValue2));
      int intValue3 = intValue0 >> 24 & 0xFF;
      int intValue4 = intValue0 >> 16 & 0xFF;
      int intValue5 = intValue0 >> 8 & 0xFF;
      int intValue6 = intValue0 & 0xFF;
      int intValue7 = intValue1 >> 24 & 0xFF;
      int intValue8 = intValue1 >> 16 & 0xFF;
      int intValue9 = intValue1 >> 8 & 0xFF;
      int intValue10 = intValue1 & 0xFF;
      if (intValue3 == 0) {
         intValue3 = 255;
      }

      if (intValue7 == 0) {
         intValue7 = 255;
      }

      int intValue11 = (int)((float)intValue3 + (float)(intValue7 - intValue3) * floatValue2);
      int intValue12 = (int)((float)intValue4 + (float)(intValue8 - intValue4) * floatValue2);
      int intValue13 = (int)((float)intValue5 + (float)(intValue9 - intValue5) * floatValue2);
      int intValue14 = (int)((float)intValue6 + (float)(intValue10 - intValue6) * floatValue2);
      return intValue11 << 24 | intValue12 << 16 | intValue13 << 8 | intValue14;
   }

   private static float a(float floatValue0) {
      float floatValue1 = floatValue0 % 2.0F;
      if (floatValue1 < 0.0F) {
         floatValue1 += 2.0F;
      }

      return floatValue1 > 1.0F ? 2.0F - floatValue1 : floatValue1;
   }

   public float a(String string1) {
      return string1 == null ? 0.0F : this.d.a(string1, this.e) / 2.0F;
   }

   public boolean a(String string1, float[] floatValueArray2) {
      return this.d.a(string1, this.e * 0.5F, floatValueArray2);
   }

   public float b(String string1) {
      return this.a(string1);
   }

   public float a() {
      return this.e;
   }

   public float b() {
      return this.e;
   }

   public dev.leg1tka.render.font.MsdfFont c() {
      return this.d;
   }

   public float d() {
      return this.e;
   }
}
