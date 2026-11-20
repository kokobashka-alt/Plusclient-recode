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

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.util.math.MathHelper;

public class VertexTransforms {
   private static int a;
   private static int b;

   public VertexTransforms() {
   }

   public static void a(MatrixStack matrixStack0, VertexConsumer vertexConsumer1, float floatValue2, boolean flag3, int intValue4, int intValue5, int intValue6) {
      Entry entry7 = matrixStack0.peek();
      a(entry7, vertexConsumer1, floatValue2, 0.0F, 0.5F, 0.0F, 0.25F, intValue4, intValue5, intValue6);
      if (flag3) {
         a(entry7, vertexConsumer1, floatValue2 * 1.05F, 0.5F, 1.0F, 0.0F, 0.25F, intValue4, intValue5, intValue6);
      }
   }

   private static void a(Entry entry0, VertexConsumer vertexConsumer1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8, int intValue9) {
      float floatValue10 = 1.0F / floatValue2;

      for (int intValue11 = 0; intValue11 < 24; intValue11++) {
         float floatValue12 = (float) Math.PI * (-0.5F + (float)intValue11 / 24.0F);
         float floatValue13 = (float) Math.PI * (-0.5F + (float)(intValue11 + 1) / 24.0F);
         float floatValue14 = floatValue2 * MathHelper.sin(floatValue12);
         float floatValue15 = floatValue2 * MathHelper.sin(floatValue13);
         float floatValue16 = floatValue2 * MathHelper.cos(floatValue12);
         float floatValue17 = floatValue2 * MathHelper.cos(floatValue13);
         float floatValue18 = floatValue5 + (1.0F - (float)intValue11 / 24.0F) * (floatValue6 - floatValue5);
         float floatValue19 = floatValue5 + (1.0F - (float)(intValue11 + 1) / 24.0F) * (floatValue6 - floatValue5);

         for (int intValue20 = 0; intValue20 < 32; intValue20++) {
            float floatValue21 = (float) (Math.PI * 2) * (float)intValue20 / 32.0F;
            float floatValue22 = (float) (Math.PI * 2) * (float)(intValue20 + 1) / 32.0F;
            float floatValue23 = floatValue16 * MathHelper.sin(floatValue21);
            float floatValue24 = floatValue16 * MathHelper.cos(floatValue21);
            float floatValue25 = floatValue17 * MathHelper.sin(floatValue21);
            float floatValue26 = floatValue17 * MathHelper.cos(floatValue21);
            float floatValue27 = floatValue17 * MathHelper.sin(floatValue22);
            float floatValue28 = floatValue17 * MathHelper.cos(floatValue22);
            float floatValue29 = floatValue16 * MathHelper.sin(floatValue22);
            float floatValue30 = floatValue16 * MathHelper.cos(floatValue22);
            float floatValue31 = floatValue3 + (float)intValue20 / 32.0F * (floatValue4 - floatValue3);
            float floatValue32 = floatValue3 + (float)(intValue20 + 1) / 32.0F * (floatValue4 - floatValue3);
            vertexConsumer1.vertex(entry0, floatValue23, floatValue14, floatValue24)
               .color(intValue9)
               .texture(floatValue31, floatValue18)
               .overlay(intValue8)
               .light(intValue7)
               .normal(entry0, floatValue23 * floatValue10, floatValue14 * floatValue10, floatValue24 * floatValue10);
            vertexConsumer1.vertex(entry0, floatValue25, floatValue15, floatValue26)
               .color(intValue9)
               .texture(floatValue31, floatValue19)
               .overlay(intValue8)
               .light(intValue7)
               .normal(entry0, floatValue25 * floatValue10, floatValue15 * floatValue10, floatValue26 * floatValue10);
            vertexConsumer1.vertex(entry0, floatValue27, floatValue15, floatValue28)
               .color(intValue9)
               .texture(floatValue32, floatValue19)
               .overlay(intValue8)
               .light(intValue7)
               .normal(entry0, floatValue27 * floatValue10, floatValue15 * floatValue10, floatValue28 * floatValue10);
            vertexConsumer1.vertex(entry0, floatValue29, floatValue14, floatValue30)
               .color(intValue9)
               .texture(floatValue32, floatValue18)
               .overlay(intValue8)
               .light(intValue7)
               .normal(entry0, floatValue29 * floatValue10, floatValue14 * floatValue10, floatValue30 * floatValue10);
         }
      }
   }
}
