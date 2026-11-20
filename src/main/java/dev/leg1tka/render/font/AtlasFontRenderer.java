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
import java.awt.Color;
import java.awt.Font;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import org.joml.Matrix4f;

public class AtlasFontRenderer extends dev.leg1tka.render.font.FontAtlas {
   private final int[] p = new int[32];
   protected dev.leg1tka.render.font.FontAtlas.a[] j = new dev.leg1tka.render.font.FontAtlas.a[1104];
   protected dev.leg1tka.render.font.FontAtlas.a[] k = new dev.leg1tka.render.font.FontAtlas.a[1104];
   protected dev.leg1tka.render.font.FontAtlas.a[] l = new dev.leg1tka.render.font.FontAtlas.a[1104];
   protected int m;
   protected int n;
   protected int o;

   public AtlasFontRenderer(Font font1, boolean flag2, boolean flag3) {
      super(font1, flag2, flag3);
      this.g();

      for (int intValue4 = 0; intValue4 < 32; intValue4++) {
         int intValue5 = (intValue4 >> 3 & 1) * 85;
         int intValue6 = (intValue4 >> 2 & 1) * 170 + intValue5;
         int intValue7 = (intValue4 >> 1 & 1) * 170 + intValue5;
         int intValue8 = (intValue4 & 1) * 170 + intValue5;
         if (intValue4 == 6) {
            intValue6 += 85;
         }

         if (intValue4 >= 16) {
            intValue6 /= 4;
            intValue7 /= 4;
            intValue8 /= 4;
         }

         this.p[intValue4] = (intValue6 & 0xFF) << 16 | (intValue7 & 0xFF) << 8 | intValue8 & 0xFF;
      }
   }

   public float a(String string1, double doubleValue2, double doubleValue4, int intValue6) {
      float floatValue7 = this.a(string1, doubleValue2 + 0.5, doubleValue4 + 0.5, intValue6, true);
      return Math.max(floatValue7, this.a(string1, doubleValue2, doubleValue4, intValue6, false));
   }

   public float a(String string1, float floatValue2, float floatValue3, int intValue4, int intValue5) {
      if (string1 == null) {
         return 0.0F;
      } else {
         floatValue2--;
         if ((intValue4 & -67108864) == 0) {
            intValue4 |= -16777216;
         }

         if ((intValue5 & -67108864) == 0) {
            intValue5 |= -16777216;
         }

         float floatValue6 = (float)(intValue4 >> 24 & 0xFF) / 255.0F;
         float floatValue7 = (float)(intValue4 >> 16 & 0xFF) / 255.0F;
         float floatValue8 = (float)(intValue4 >> 8 & 0xFF) / 255.0F;
         float floatValue9 = (float)(intValue4 & 0xFF) / 255.0F;
         float floatValue10 = (float)(intValue5 >> 24 & 0xFF) / 255.0F;
         float floatValue11 = (float)(intValue5 >> 16 & 0xFF) / 255.0F;
         float floatValue12 = (float)(intValue5 >> 8 & 0xFF) / 255.0F;
         float floatValue13 = (float)(intValue5 & 0xFF) / 255.0F;
         double doubleValue14 = (double)floatValue2 * 2.0;
         double doubleValue16 = ((double)floatValue3 - 3.0) * 2.0;
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         Matrix4f matrix4f18 = new Matrix4f();
         matrix4f18.scale(0.5F, 0.5F, 0.5F);
         dev.leg1tka.render.font.FontAtlas.a[] valueArray19 = this.b;
         int intValue20 = string1.length();

         for (int intValue21 = 0; intValue21 < intValue20; intValue21++) {
            char character22 = string1.charAt(intValue21);
            if (character22 < valueArray19.length && valueArray19[character22] != null) {
               RenderSystem.setShaderTexture(0, this.i);
               RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
               Tessellator tessellator23 = Tessellator.getInstance();
               BufferBuilder bufferBuilder24 = tessellator23.begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
               dev.leg1tka.render.font.FontAtlas.a value25 = valueArray19[character22];
               float floatValue26 = (float)value25.c;
               float floatValue27 = (float)value25.d;
               float floatValue28 = (float)value25.a;
               float floatValue29 = (float)value25.b;
               float floatValue30 = floatValue26 / 512.0F;
               float floatValue31 = floatValue27 / 512.0F;
               float floatValue32 = (floatValue26 + floatValue28) / 512.0F;
               float floatValue33 = (floatValue27 + floatValue29) / 512.0F;
               bufferBuilder24.vertex(matrix4f18, (float)doubleValue14, (float)doubleValue16, 0.0F).texture(floatValue30, floatValue31).color(floatValue7, floatValue8, floatValue9, floatValue6);
               bufferBuilder24.vertex(matrix4f18, (float)doubleValue14, (float)doubleValue16 + floatValue29, 0.0F).texture(floatValue30, floatValue33).color(floatValue11, floatValue12, floatValue13, floatValue10);
               bufferBuilder24.vertex(matrix4f18, (float)doubleValue14 + floatValue28, (float)doubleValue16 + floatValue29, 0.0F).texture(floatValue32, floatValue33).color(floatValue11, floatValue12, floatValue13, floatValue10);
               bufferBuilder24.vertex(matrix4f18, (float)doubleValue14 + floatValue28, (float)doubleValue16, 0.0F).texture(floatValue32, floatValue31).color(floatValue7, floatValue8, floatValue9, floatValue6);
               BufferRenderer.drawWithGlobalProgram(bufferBuilder24.end());
               doubleValue14 += (double)(value25.a - 8 + this.g);
            } else if (character22 == ' ' || character22 == 160) {
               doubleValue14 += 8.0;
            }
         }

         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         return (float)doubleValue14 / 2.0F;
      }
   }

   public float b(String string1, float floatValue2, float floatValue3, int intValue4, int intValue5) {
      if (string1 == null) {
         return 0.0F;
      } else {
         floatValue2--;
         if ((intValue4 & -67108864) == 0) {
            intValue4 |= -16777216;
         }

         if ((intValue5 & -67108864) == 0) {
            intValue5 |= -16777216;
         }

         double doubleValue6 = (double)floatValue2 * 2.0;
         double doubleValue8 = ((double)floatValue3 - 3.0) * 2.0;
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         Matrix4f matrix4f10 = new Matrix4f();
         matrix4f10.scale(0.5F, 0.5F, 0.5F);
         dev.leg1tka.render.font.FontAtlas.a[] valueArray11 = this.b;
         int intValue12 = string1.length();
         float floatValue13 = (float)this.b(string1) * 2.0F;
         float floatValue14 = 0.0F;

         for (int intValue15 = 0; intValue15 < intValue12; intValue15++) {
            char character16 = string1.charAt(intValue15);
            if (character16 < valueArray11.length && valueArray11[character16] != null) {
               RenderSystem.setShaderTexture(0, this.i);
               RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
               Tessellator tessellator17 = Tessellator.getInstance();
               BufferBuilder bufferBuilder18 = tessellator17.begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
               dev.leg1tka.render.font.FontAtlas.a value19 = valueArray11[character16];
               float floatValue20 = (float)value19.c;
               float floatValue21 = (float)value19.d;
               float floatValue22 = (float)value19.a;
               float floatValue23 = (float)value19.b;
               float floatValue24 = (float)(value19.a - 8 + this.g);
               float floatValue25 = floatValue20 / 512.0F;
               float floatValue26 = floatValue21 / 512.0F;
               float floatValue27 = (floatValue20 + floatValue22) / 512.0F;
               float floatValue28 = (floatValue21 + floatValue23) / 512.0F;
               float floatValue29 = floatValue13 <= 0.0F ? 0.0F : floatValue14 / floatValue13;
               float floatValue30 = floatValue13 <= 0.0F ? 1.0F : (floatValue14 + floatValue24) / floatValue13;
               int intValue31 = this.a(intValue4, intValue5, floatValue29);
               int intValue32 = this.a(intValue4, intValue5, floatValue30);
               float floatValue33 = (float)(intValue31 >> 24 & 0xFF) / 255.0F;
               float floatValue34 = (float)(intValue31 >> 16 & 0xFF) / 255.0F;
               float floatValue35 = (float)(intValue31 >> 8 & 0xFF) / 255.0F;
               float floatValue36 = (float)(intValue31 & 0xFF) / 255.0F;
               float floatValue37 = (float)(intValue32 >> 24 & 0xFF) / 255.0F;
               float floatValue38 = (float)(intValue32 >> 16 & 0xFF) / 255.0F;
               float floatValue39 = (float)(intValue32 >> 8 & 0xFF) / 255.0F;
               float floatValue40 = (float)(intValue32 & 0xFF) / 255.0F;
               bufferBuilder18.vertex(matrix4f10, (float)doubleValue6, (float)doubleValue8, 0.0F).texture(floatValue25, floatValue26).color(floatValue34, floatValue35, floatValue36, floatValue33);
               bufferBuilder18.vertex(matrix4f10, (float)doubleValue6, (float)doubleValue8 + floatValue23, 0.0F).texture(floatValue25, floatValue28).color(floatValue34, floatValue35, floatValue36, floatValue33);
               bufferBuilder18.vertex(matrix4f10, (float)doubleValue6 + floatValue22, (float)doubleValue8 + floatValue23, 0.0F).texture(floatValue27, floatValue28).color(floatValue38, floatValue39, floatValue40, floatValue37);
               bufferBuilder18.vertex(matrix4f10, (float)doubleValue6 + floatValue22, (float)doubleValue8, 0.0F).texture(floatValue27, floatValue26).color(floatValue38, floatValue39, floatValue40, floatValue37);
               BufferRenderer.drawWithGlobalProgram(bufferBuilder18.end());
               doubleValue6 += (double)floatValue24;
               floatValue14 += floatValue24;
            } else if (character16 == ' ' || character16 == 160) {
               doubleValue6 += 8.0;
               floatValue14 += 8.0F;
            }
         }

         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         return (float)doubleValue6 / 2.0F;
      }
   }

   private int a(int intValue1, int intValue2, float floatValue3) {
      float floatValue4 = (float)(intValue1 >> 24 & 0xFF) / 255.0F;
      float floatValue5 = (float)(intValue1 >> 16 & 0xFF) / 255.0F;
      float floatValue6 = (float)(intValue1 >> 8 & 0xFF) / 255.0F;
      float floatValue7 = (float)(intValue1 & 0xFF) / 255.0F;
      float floatValue8 = (float)(intValue2 >> 24 & 0xFF) / 255.0F;
      float floatValue9 = (float)(intValue2 >> 16 & 0xFF) / 255.0F;
      float floatValue10 = (float)(intValue2 >> 8 & 0xFF) / 255.0F;
      float floatValue11 = (float)(intValue2 & 0xFF) / 255.0F;
      int intValue12 = (int)(((1.0F - floatValue3) * floatValue4 + floatValue3 * floatValue8) * 255.0F);
      int intValue13 = (int)(((1.0F - floatValue3) * floatValue5 + floatValue3 * floatValue9) * 255.0F);
      int intValue14 = (int)(((1.0F - floatValue3) * floatValue6 + floatValue3 * floatValue10) * 255.0F);
      int intValue15 = (int)(((1.0F - floatValue3) * floatValue7 + floatValue3 * floatValue11) * 255.0F);
      return intValue12 << 24 | intValue13 << 16 | intValue14 << 8 | intValue15;
   }

   public float a(String string1, float floatValue2, float floatValue3, int intValue4) {
      return this.a(string1, (double)floatValue2, (double)floatValue3, intValue4, false);
   }

   public float b(String string1, float floatValue2, float floatValue3, int intValue4) {
      return this.a(string1, floatValue2 - (float)this.b(string1) / 2.0F, floatValue3, intValue4);
   }

   public float c(String string1, float floatValue2, float floatValue3, int intValue4) {
      return this.a(string1, (double)(floatValue2 - (float)this.b(string1) / 2.0F), (double)floatValue3, intValue4);
   }

   public float a(String string1, double doubleValue2, double doubleValue4, int intValue6, boolean flag7) {
      doubleValue2--;
      if (string1 == null) {
         return 0.0F;
      } else {
         if (intValue6 == 553648127) {
            intValue6 = 16777215;
         }

         if ((intValue6 & -67108864) == 0) {
            intValue6 |= -16777216;
         }

         if (flag7) {
            intValue6 = (intValue6 & 16579836) >> 2 | intValue6 & new Color(20, 20, 20, 200).getRGB();
         }

         dev.leg1tka.render.font.FontAtlas.a[] valueArray8 = this.b;
         float floatValue9 = (float)(intValue6 >> 24 & 0xFF) / 255.0F;
         boolean flag10 = false;
         boolean flag11 = false;
         boolean flag12 = false;
         boolean flag13 = false;
         doubleValue2 *= 2.0;
         doubleValue4 = (doubleValue4 - 3.0) * 2.0;
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.setShaderColor((float)(intValue6 >> 16 & 0xFF) / 255.0F, (float)(intValue6 >> 8 & 0xFF) / 255.0F, (float)(intValue6 & 0xFF) / 255.0F, floatValue9);
         Matrix4f matrix4f14 = new Matrix4f();
         matrix4f14.scale(0.5F, 0.5F, 0.5F);
         int intValue15 = string1.length();
         int intValue16 = this.i;
         RenderSystem.setShaderTexture(0, intValue16);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX);

         for (int intValue17 = 0; intValue17 < intValue15; intValue17++) {
            char character18 = string1.charAt(intValue17);
            if (String.valueOf(character18).equals("§") && intValue17 < intValue15 - 1) {
               int intValue25 = 21;

               try {
                  intValue25 = "0123456789abcdefklmnor".indexOf(string1.charAt(intValue17 + 1));
               } catch (Exception exception21) {
                  exception21.printStackTrace();
               }

               if (intValue25 >= 16) {
                  if (intValue25 == 17) {
                     flag10 = true;
                     if (flag11) {
                        intValue16 = this.o;
                        valueArray8 = this.l;
                     } else {
                        intValue16 = this.m;
                        valueArray8 = this.j;
                     }
                  } else if (intValue25 == 18) {
                     flag12 = true;
                  } else if (intValue25 == 19) {
                     flag13 = true;
                  } else if (intValue25 == 20) {
                     flag11 = true;
                     if (flag10) {
                        intValue16 = this.o;
                        valueArray8 = this.l;
                     } else {
                        intValue16 = this.n;
                        valueArray8 = this.k;
                     }
                  } else if (intValue25 == 21) {
                     flag10 = false;
                     flag11 = false;
                     flag13 = false;
                     flag12 = false;
                     RenderSystem.setShaderColor((float)(intValue6 >> 16 & 0xFF) / 255.0F, (float)(intValue6 >> 8 & 0xFF) / 255.0F, (float)(intValue6 & 0xFF) / 255.0F, floatValue9);
                     intValue16 = this.i;
                     valueArray8 = this.b;
                  }
               } else {
                  flag10 = false;
                  flag11 = false;
                  flag13 = false;
                  flag12 = false;
                  intValue16 = this.i;
                  valueArray8 = this.b;
                  if (intValue25 < 0 || intValue25 > 15) {
                     intValue25 = 15;
                  }

                  if (flag7) {
                     intValue25 += 16;
                  }

                  int intValue26 = this.p[intValue25];
                  RenderSystem.setShaderColor((float)(intValue26 >> 16 & 0xFF) / 255.0F, (float)(intValue26 >> 8 & 0xFF) / 255.0F, (float)(intValue26 & 0xFF) / 255.0F, floatValue9);
               }

               intValue17++;
            } else if (character18 < valueArray8.length && valueArray8[character18] != null) {
               RenderSystem.setShaderTexture(0, intValue16);
               Tessellator tessellator19 = Tessellator.getInstance();
               BufferBuilder bufferBuilder20 = tessellator19.begin(DrawMode.TRIANGLES, VertexFormats.POSITION_TEXTURE);
               this.a(valueArray8, character18, (float)doubleValue2, (float)doubleValue4, matrix4f14, bufferBuilder20);
               BufferRenderer.drawWithGlobalProgram(bufferBuilder20.end());
               if (flag12) {
                  this.a(
                     doubleValue2,
                     doubleValue4 + (double)((float)valueArray8[character18].b / 2.0F),
                     doubleValue2 + (double)valueArray8[character18].a - 8.0,
                     doubleValue4 + (double)((float)valueArray8[character18].b / 2.0F),
                     1.0F,
                     matrix4f14
                  );
               }

               if (flag13) {
                  this.a(doubleValue2, doubleValue4 + (double)valueArray8[character18].b - 2.0, doubleValue2 + (double)valueArray8[character18].a - 8.0, doubleValue4 + (double)valueArray8[character18].b - 2.0, 1.0F, matrix4f14);
               }

               doubleValue2 += (double)(valueArray8[character18].a - 8 + this.g);
            }
         }

         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         return (float)doubleValue2 / 2.0F;
      }
   }

   @Override
   public int b(String string1) {
      int intValue2 = 0;
      dev.leg1tka.render.font.FontAtlas.a[] valueArray3 = this.b;
      boolean flag4 = false;
      boolean flag5 = false;
      int intValue6 = string1.length();

      for (int intValue7 = 0; intValue7 < intValue6; intValue7++) {
         char character8 = string1.charAt(intValue7);
         if (String.valueOf(character8).equals("§") && intValue7 < intValue6 - 1) {
            int intValue9 = "0123456789abcdefklmnor".indexOf(string1.charAt(intValue7 + 1));
            if (intValue9 < 16) {
               flag4 = false;
               flag5 = false;
            } else if (intValue9 == 17) {
               flag4 = true;
               valueArray3 = flag5 ? this.l : this.j;
            } else if (intValue9 == 20) {
               flag5 = true;
               valueArray3 = flag4 ? this.l : this.k;
            } else if (intValue9 == 21) {
               flag4 = false;
               flag5 = false;
               valueArray3 = this.b;
            }

            intValue7++;
         } else if (character8 < valueArray3.length && valueArray3[character8] != null) {
            intValue2 += valueArray3[character8].a - 8 + this.g;
         }
      }

      return intValue2 / 2;
   }

   @Override
   public void a(Font font1) {
      super.a(font1);
      this.g();
   }

   @Override
   public void a(boolean flag1) {
      super.a(flag1);
      this.g();
   }

   @Override
   public void b(boolean flag1) {
      super.b(flag1);
      this.g();
   }

   private void g() {
      dev.leg1tka.render.font.FontAtlas value1 = new dev.leg1tka.render.font.FontAtlas(this.c.deriveFont(1), this.d, this.e);
      this.m = value1.f();
      this.j = value1.b;
      dev.leg1tka.render.font.FontAtlas value2 = new dev.leg1tka.render.font.FontAtlas(this.c.deriveFont(2), this.d, this.e);
      this.n = value2.f();
      this.k = value2.b;
      dev.leg1tka.render.font.FontAtlas value3 = new dev.leg1tka.render.font.FontAtlas(this.c.deriveFont(3), this.d, this.e);
      this.o = value3.f();
      this.l = value3.b;
   }

   private void a(double doubleValue1, double doubleValue3, double doubleValue5, double doubleValue7, float floatValue9, Matrix4f matrix4f10) {
      RenderSystem.setShader(ShaderProgramKeys.POSITION);
      RenderSystem.lineWidth(floatValue9);
      Tessellator tessellator11 = Tessellator.getInstance();
      BufferBuilder bufferBuilder12 = tessellator11.begin(DrawMode.LINES, VertexFormats.POSITION);
      bufferBuilder12.vertex(matrix4f10, (float)doubleValue1, (float)doubleValue3, 0.0F);
      bufferBuilder12.vertex(matrix4f10, (float)doubleValue5, (float)doubleValue7, 0.0F);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder12.end());
   }

   public void b(String string1, double doubleValue2, double doubleValue4, int intValue6) {
      this.a(string1, doubleValue2 - 0.5, doubleValue4, Color.BLACK.getRGB(), false);
      this.a(string1, doubleValue2 + 0.5, doubleValue4, Color.BLACK.getRGB(), false);
      this.a(string1, doubleValue2, doubleValue4 - 0.5, Color.BLACK.getRGB(), false);
      this.a(string1, doubleValue2, doubleValue4 + 0.5, Color.BLACK.getRGB(), false);
      this.a(string1, doubleValue2, doubleValue4, intValue6, false);
   }

   public void d(String string1, float floatValue2, float floatValue3, int intValue4) {
      this.b(string1, floatValue2 - 0.5F, floatValue3, Color.BLACK.getRGB());
      this.b(string1, floatValue2 + 0.5F, floatValue3, Color.BLACK.getRGB());
      this.b(string1, floatValue2, floatValue3 - 0.5F, Color.BLACK.getRGB());
      this.b(string1, floatValue2, floatValue3 + 0.5F, Color.BLACK.getRGB());
      this.b(string1, floatValue2, floatValue3, intValue4);
   }
}
