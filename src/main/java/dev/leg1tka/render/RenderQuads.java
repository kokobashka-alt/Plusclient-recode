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
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public final class RenderQuads implements dev.leg1tka.api.ClientAccess {
   private static float c;
   private static float d;
   private static float e;
   private static int f;
   private static int g;
   private static final double[][] h = new double[][]{{0.0, 1.0, 0.0}, {0.0, -1.0, 0.0}, {0.0, 0.0, -1.0}, {0.0, 0.0, 1.0}, {-1.0, 0.0, 0.0}, {1.0, 0.0, 0.0}};
   private static final double[][][] i = new double[][][]{
      {{1.0, 0.0, 0.0}, {0.0, 0.0, 1.0}},
      {{1.0, 0.0, 0.0}, {0.0, 0.0, 1.0}},
      {{1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}},
      {{1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}},
      {{0.0, 0.0, 1.0}, {0.0, 1.0, 0.0}},
      {{0.0, 0.0, 1.0}, {0.0, 1.0, 0.0}}
   };
   private static final int[][] j = new int[][]{{2, 3, 4, 5}, {2, 3, 4, 5}, {0, 1, 4, 5}, {0, 1, 4, 5}, {0, 1, 2, 3}, {0, 1, 2, 3}};
   private static final double[] k = new double[39];
   private static final double[] l = new double[3];

   private RenderQuads() {
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, int intValue5, long longValue6) {
      if (!(floatValue3 < 22.0F) && !(floatValue4 < 12.0F)) {
         int intValue8 = Math.max(22, Math.min(255, 132));
         matrixStack0.push();
         matrixStack0.translate(floatValue1, floatValue2, 0.0F);
         Matrix4f matrix4f9 = matrixStack0.peek().getPositionMatrix();
         dev.leg1tka.render.ScissorStack.a();
         dev.leg1tka.render.ScissorStack.a((double)floatValue1, (double)floatValue2, (double)floatValue3, (double)floatValue4);

         try {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableCull();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
            a(matrix4f9, floatValue3, floatValue4, longValue6, intValue5, intValue8);
         } finally {
            dev.leg1tka.render.ScissorStack.c();
            dev.leg1tka.render.ScissorStack.b();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            matrixStack0.pop();
            dev.leg1tka.render.RenderPrimitives.e();
         }
      }
   }

   private static void a(Matrix4f matrix4f0, float floatValue1, float floatValue2, long longValue3, int intValue5, int intValue6) {
      float floatValue7 = (float)(System.currentTimeMillis() % 100000L) / 1000.0F * 1.4F;
      float floatValue8 = 0.85F;
      float floatValue9 = 8.025F;

      for (int intValue10 = 0; intValue10 < 6; intValue10++) {
         int[] intValueArray11 = j[intValue10];
         double[] doubleValueArray12 = h[intValue10];
         double[] doubleValueArray13 = i[intValue10][0];
         double[] doubleValueArray14 = i[intValue10][1];

         for (int intValue15 = 0; intValue15 < 3; intValue15++) {
            int intValue16 = intValue10 * 1000 + intValue15 * 53;
            int intValue17 = intValueArray11[intValue15 % intValueArray11.length];
            double doubleValue18 = (double)floatValue7 * (0.95 + a(longValue3, intValue16 + 1) * 0.55) + (double)intValue15 * 0.83 + (double)intValue10 * 1.11;
            double doubleValue20 = a(0.5 + Math.sin(doubleValue18 * 1.37 + a(longValue3, intValue16 + 2) * 6.2831853) * 0.38);
            a(intValue10, intValue17, doubleValue20, 0.08, (double)floatValue1, (double)floatValue2);
            double doubleValue22 = l[0];
            double doubleValue24 = l[1];
            double doubleValue26 = l[2];
            a(intValue10, a(0.5 + (a(longValue3, intValue16 + 3) - 0.5) * 0.46), a(0.5 + (a(longValue3, intValue16 + 4) - 0.5) * 0.46), 0.08, (double)floatValue1, (double)floatValue2);
            double doubleValue28 = l[0];
            double doubleValue30 = l[1];
            double doubleValue32 = l[2];
            a(intValue17, a(0.5 + (a(longValue3, intValue16 + 5) - 0.5) * 0.46), a(0.5 + (a(longValue3, intValue16 + 6) - 0.5) * 0.46), 0.08, (double)floatValue1, (double)floatValue2);
            double doubleValue34 = l[0];
            double doubleValue36 = l[1];
            double doubleValue38 = l[2];
            double[] doubleValueArray40 = i[intValue17][0];
            double[] doubleValueArray41 = i[intValue17][1];
            double[] doubleValueArray42 = h[intValue17];
            double doubleValue43 = (double)floatValue9 * (0.7 + a(longValue3, intValue16 + 7)) * Math.sin(doubleValue18 * 1.9 + a(longValue3, intValue16 + 8) * 6.2831853);
            double doubleValue45 = (double)floatValue9 * (0.7 + a(longValue3, intValue16 + 9)) * Math.cos(doubleValue18 * 1.7 + a(longValue3, intValue16 + 10) * 6.2831853);
            double doubleValue47 = doubleValue22 - doubleValue28;
            double doubleValue49 = doubleValue24 - doubleValue30;
            double doubleValue51 = doubleValue26 - doubleValue32;
            double doubleValue53 = doubleValue28 + doubleValue47 * 0.38 + doubleValueArray13[0] * doubleValue43 + doubleValueArray14[0] * -doubleValue43 * 0.55;
            double doubleValue55 = doubleValue30 + doubleValue49 * 0.38 + doubleValueArray13[1] * doubleValue43 + doubleValueArray14[1] * -doubleValue43 * 0.55;
            double doubleValue57 = doubleValue32 + doubleValue51 * 0.38 + doubleValueArray13[2] * doubleValue43 + doubleValueArray14[2] * -doubleValue43 * 0.55;
            double doubleValue59 = doubleValue28 + doubleValue47 * 0.76 + doubleValueArray13[0] * -doubleValue43 * 0.65 + doubleValueArray14[0] * doubleValue43 * 0.4;
            double doubleValue61 = doubleValue30 + doubleValue49 * 0.76 + doubleValueArray13[1] * -doubleValue43 * 0.65 + doubleValueArray14[1] * doubleValue43 * 0.4;
            double doubleValue63 = doubleValue32 + doubleValue51 * 0.76 + doubleValueArray13[2] * -doubleValue43 * 0.65 + doubleValueArray14[2] * doubleValue43 * 0.4;
            double doubleValue65 = doubleValue34 - doubleValue22;
            double doubleValue67 = doubleValue36 - doubleValue24;
            double doubleValue69 = doubleValue38 - doubleValue26;
            double doubleValue71 = doubleValue22 + doubleValue65 * 0.24 + doubleValueArray40[0] * doubleValue45 + doubleValueArray41[0] * doubleValue45 * 0.45;
            double doubleValue73 = doubleValue24 + doubleValue67 * 0.24 + doubleValueArray40[1] * doubleValue45 + doubleValueArray41[1] * doubleValue45 * 0.45;
            double doubleValue75 = doubleValue26 + doubleValue69 * 0.24 + doubleValueArray40[2] * doubleValue45 + doubleValueArray41[2] * doubleValue45 * 0.45;
            double doubleValue77 = doubleValue22 + doubleValue65 * 0.62 + doubleValueArray40[0] * -doubleValue45 * 0.7 + doubleValueArray41[0] * -doubleValue45 * 0.35;
            double doubleValue79 = doubleValue24 + doubleValue67 * 0.62 + doubleValueArray40[1] * -doubleValue45 * 0.7 + doubleValueArray41[1] * -doubleValue45 * 0.35;
            double doubleValue81 = doubleValue26 + doubleValue69 * 0.62 + doubleValueArray40[2] * -doubleValue45 * 0.7 + doubleValueArray41[2] * -doubleValue45 * 0.35;
            int intValue83 = Math.max(18, Math.min(255, (int)((double)intValue6 * (0.74 + 0.26 * Math.sin(doubleValue18 * 2.6)))));
            int intValue84 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue5, intValue83);
            a(matrix4f0, doubleValue28, doubleValue30, doubleValue32, doubleValue53, doubleValue55, doubleValue57, doubleValue59, doubleValue61, doubleValue63, doubleValue22, doubleValue24, doubleValue26, doubleValueArray12[0], doubleValueArray12[1], doubleValueArray12[2], intValue84, floatValue8);
            a(matrix4f0, doubleValue22, doubleValue24, doubleValue26, doubleValue71, doubleValue73, doubleValue75, doubleValue77, doubleValue79, doubleValue81, doubleValue34, doubleValue36, doubleValue38, doubleValueArray42[0], doubleValueArray42[1], doubleValueArray42[2], intValue84, floatValue8);
         }
      }
   }

   private static void a(
      Matrix4f matrix4f0,
      double doubleValue1,
      double doubleValue3,
      double doubleValue5,
      double doubleValue7,
      double doubleValue9,
      double doubleValue11,
      double doubleValue13,
      double doubleValue15,
      double doubleValue17,
      double doubleValue19,
      double doubleValue21,
      double doubleValue23,
      double doubleValue25,
      double doubleValue27,
      double doubleValue29,
      int intValue31,
      float floatValue32
   ) {
      for (int intValue33 = 0; intValue33 <= 12; intValue33++) {
         double doubleValue34 = (double)intValue33 / 12.0;
         double doubleValue36 = 1.0 - doubleValue34;
         double doubleValue38 = doubleValue36 * doubleValue36;
         double doubleValue40 = doubleValue34 * doubleValue34;
         double doubleValue42 = doubleValue38 * doubleValue36;
         double doubleValue44 = 3.0 * doubleValue38 * doubleValue34;
         double doubleValue46 = 3.0 * doubleValue36 * doubleValue40;
         double doubleValue48 = doubleValue40 * doubleValue34;
         int intValue50 = intValue33 * 3;
         k[intValue50] = doubleValue1 * doubleValue42 + doubleValue7 * doubleValue44 + doubleValue13 * doubleValue46 + doubleValue19 * doubleValue48;
         k[intValue50 + 1] = doubleValue3 * doubleValue42 + doubleValue9 * doubleValue44 + doubleValue15 * doubleValue46 + doubleValue21 * doubleValue48;
         k[intValue50 + 2] = doubleValue5 * doubleValue42 + doubleValue11 * doubleValue44 + doubleValue17 * doubleValue46 + doubleValue23 * doubleValue48;
      }

      BufferBuilder bufferBuilder65 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);

      for (int intValue66 = 0; intValue66 < 12; intValue66++) {
         int intValue35 = intValue66 * 3;
         int intValue67 = intValue35 + 3;
         double doubleValue37 = k[intValue35];
         double doubleValue39 = k[intValue35 + 1];
         double doubleValue41 = k[intValue35 + 2];
         double doubleValue43 = k[intValue67];
         double doubleValue45 = k[intValue67 + 1];
         double doubleValue47 = k[intValue67 + 2];
         double doubleValue49 = doubleValue43 - doubleValue37;
         double doubleValue51 = doubleValue45 - doubleValue39;
         double doubleValue53 = doubleValue47 - doubleValue41;
         if (!(doubleValue49 * doubleValue49 + doubleValue51 * doubleValue51 + doubleValue53 * doubleValue53 < 1.0E-6)) {
            double doubleValue55 = doubleValue27 * doubleValue53 - doubleValue29 * doubleValue51;
            double doubleValue57 = doubleValue29 * doubleValue49 - doubleValue25 * doubleValue53;
            double doubleValue59 = doubleValue25 * doubleValue51 - doubleValue27 * doubleValue49;
            double doubleValue61 = doubleValue55 * doubleValue55 + doubleValue57 * doubleValue57 + doubleValue59 * doubleValue59;
            if (!(doubleValue61 < 1.0E-6)) {
               double doubleValue63 = (double)floatValue32 / Math.sqrt(doubleValue61);
               doubleValue55 *= doubleValue63;
               doubleValue57 *= doubleValue63;
               doubleValue59 *= doubleValue63;
               bufferBuilder65.vertex(matrix4f0, (float)(doubleValue37 + doubleValue55), (float)(doubleValue39 + doubleValue57), (float)(doubleValue41 + doubleValue59)).color(intValue31);
               bufferBuilder65.vertex(matrix4f0, (float)(doubleValue37 - doubleValue55), (float)(doubleValue39 - doubleValue57), (float)(doubleValue41 - doubleValue59)).color(intValue31);
               bufferBuilder65.vertex(matrix4f0, (float)(doubleValue43 - doubleValue55), (float)(doubleValue45 - doubleValue57), (float)(doubleValue47 - doubleValue59)).color(intValue31);
               bufferBuilder65.vertex(matrix4f0, (float)(doubleValue43 + doubleValue55), (float)(doubleValue45 + doubleValue57), (float)(doubleValue47 + doubleValue59)).color(intValue31);
            }
         }
      }

      BufferRenderer.drawWithGlobalProgram(bufferBuilder65.end());
   }

   private static void a(int intValue0, int intValue1, double doubleValue2, double doubleValue4, double doubleValue6, double doubleValue8) {
      double doubleValue10 = Double.NaN;
      double doubleValue12 = Double.NaN;
      double doubleValue14 = Double.NaN;
      switch (intValue0) {
         case 0:
            doubleValue12 = doubleValue8 - doubleValue4;
            break;
         case 1:
            doubleValue12 = doubleValue4;
            break;
         case 2:
            doubleValue14 = doubleValue4;
            break;
         case 3:
            doubleValue14 = 0.35F - doubleValue4;
            break;
         case 4:
            doubleValue10 = doubleValue4;
            break;
         default:
            doubleValue10 = doubleValue6 - doubleValue4;
      }

      switch (intValue1) {
         case 0:
            doubleValue12 = doubleValue8 - doubleValue4;
            break;
         case 1:
            doubleValue12 = doubleValue4;
            break;
         case 2:
            doubleValue14 = doubleValue4;
            break;
         case 3:
            doubleValue14 = 0.35F - doubleValue4;
            break;
         case 4:
            doubleValue10 = doubleValue4;
            break;
         default:
            doubleValue10 = doubleValue6 - doubleValue4;
      }

      double doubleValue16 = a(doubleValue2);
      l[0] = Double.isNaN(doubleValue10) ? doubleValue6 * doubleValue16 : doubleValue10;
      l[1] = Double.isNaN(doubleValue12) ? doubleValue8 * doubleValue16 : doubleValue12;
      l[2] = Double.isNaN(doubleValue14) ? 0.35F * doubleValue16 : doubleValue14;
   }

   private static void a(int intValue0, double doubleValue1, double doubleValue3, double doubleValue5, double doubleValue7, double doubleValue9) {
      doubleValue1 = a(doubleValue1);
      doubleValue3 = a(doubleValue3);
      switch (intValue0) {
         case 0:
            l[0] = doubleValue7 * doubleValue1;
            l[1] = doubleValue9 - doubleValue5;
            l[2] = 0.35F * doubleValue3;
            break;
         case 1:
            l[0] = doubleValue7 * doubleValue1;
            l[1] = doubleValue5;
            l[2] = 0.35F * doubleValue3;
            break;
         case 2:
            l[0] = doubleValue7 * doubleValue1;
            l[1] = doubleValue9 * doubleValue3;
            l[2] = doubleValue5;
            break;
         case 3:
            l[0] = doubleValue7 * doubleValue1;
            l[1] = doubleValue9 * doubleValue3;
            l[2] = 0.35F - doubleValue5;
            break;
         case 4:
            l[0] = doubleValue5;
            l[1] = doubleValue9 * doubleValue3;
            l[2] = 0.35F * doubleValue1;
            break;
         default:
            l[0] = doubleValue7 - doubleValue5;
            l[1] = doubleValue9 * doubleValue3;
            l[2] = 0.35F * doubleValue1;
      }
   }

   private static double a(long longValue0, int intValue2) {
      long longValue3 = longValue0 + -7046029254386353131L * ((long)intValue2 + 1L);
      longValue3 ^= longValue3 >>> 30;
      longValue3 *= -4658895280553007687L;
      longValue3 ^= longValue3 >>> 27;
      longValue3 *= -7723592293110705685L;
      longValue3 ^= longValue3 >>> 31;
      return (double)(longValue3 & 16777215L) / 1.6777216E7;
   }

   private static double a(double doubleValue0) {
      return Math.max(0.0, Math.min(1.0, doubleValue0));
   }

   public static long a(float floatValue0, float floatValue1, float floatValue2, float floatValue3) {
      return (long)(floatValue0 * 31.7F) << 32 ^ (long)(floatValue1 * 17.3F) << 16 ^ (long)(floatValue2 * 11.1F + floatValue3 * 7.9F);
   }
}
