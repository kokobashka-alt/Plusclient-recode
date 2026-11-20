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
import java.util.Random;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class MenuParticleRenderer {
   public static float a;
   private final Random b = new Random();
   private float c = 0.0F;
   private float d = 0.0F;
   private long e = System.currentTimeMillis() + 2500L;
   private long f = 0L;
   private long g = 0L;
   private float h = 0.0F;

   public MenuParticleRenderer() {
   }

   public void a(float floatValue1) {
      this.h = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue1);
   }

   private float a() {
      return this.h;
   }

   private float b() {
      long longValue1 = System.currentTimeMillis();
      if (this.f == 0L && longValue1 >= this.e) {
         this.f = longValue1;
      }

      if (this.f == 0L) {
         return 0.0F;
      } else {
         float floatValue3 = (float)(longValue1 - this.f) / 260.0F;
         if (floatValue3 >= 1.0F) {
            this.f = 0L;
            this.e = longValue1 + (this.b.nextFloat() < 0.18F ? 180L : 3500L + (long)this.b.nextInt(4000));
            return 0.0F;
         } else {
            return floatValue3 < 0.4F ? dev.leg1tka.gui.menu.MenuBackdropRenderer.b(floatValue3 / 0.4F) : 1.0F - dev.leg1tka.gui.menu.MenuBackdropRenderer.b((floatValue3 - 0.4F) / 0.6F);
         }
      }
   }

   public void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8) {
      if (!(floatValue7 < 0.01F)) {
         long longValue9 = System.nanoTime();
         float floatValue11 = this.g == 0L ? 0.016F : Math.min(0.05F, (float)((double)(longValue9 - this.g) / 1.0E9));
         this.g = longValue9;
         float floatValue12 = 0.0F;
         float floatValue13 = 0.0F;
         if (!Float.isNaN(floatValue5)) {
            float floatValue14 = floatValue5 - floatValue2;
            float floatValue15 = floatValue6 - floatValue3;
            float floatValue16 = (float)Math.sqrt((double)(floatValue14 * floatValue14 + floatValue15 * floatValue15));
            float floatValue17 = Math.min(1.0F, floatValue16 / (floatValue4 * 3.0F));
            if (floatValue16 > 0.001F) {
               floatValue12 = floatValue14 / floatValue16 * floatValue17;
               floatValue13 = floatValue15 / floatValue16 * floatValue17;
            }
         }

         this.c = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.c, floatValue12, floatValue11, 6.0F);
         this.d = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.d, floatValue13, floatValue11, 6.0F);
         float floatValue22 = floatValue4 * 0.36F;
         float floatValue23 = floatValue2 - floatValue4 * 0.5F;
         float floatValue24 = floatValue3 - floatValue22 * 0.5F;
         int intValue25 = dev.leg1tka.gui.menu.MenuBackdropRenderer.c();
         int intValue18 = dev.leg1tka.gui.menu.MenuBackdropRenderer.d();
         if (floatValue8 > 0.01F) {
            dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue23 + floatValue4 * 0.2F, floatValue24 + floatValue22 * 0.15F, floatValue4 * 0.6F, floatValue22 * 0.7F, floatValue22 * 0.35F, floatValue4 * 0.35F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.3F * floatValue8 * floatValue7, intValue25));
         }

         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         ShaderProgram shaderProgram19 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.P);
         if (shaderProgram19 != null) {
            a(shaderProgram19, "Size", floatValue4, floatValue22);
            a(shaderProgram19, "Px", dev.leg1tka.gui.menu.MenuLayout.c());
            a(shaderProgram19, "Look", this.c, this.d);
            a(shaderProgram19, "Blink", this.h > 0.5F ? this.a() : Math.max(this.b(), this.a()));
            a(shaderProgram19, "Theme1", intValue25);
            a(shaderProgram19, "Theme2", intValue18);
            Matrix4f matrix4f20 = matrixStack1.peek().getPositionMatrix();
            BufferBuilder bufferBuilder21 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
            bufferBuilder21.vertex(matrix4f20, floatValue23, floatValue24, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue7);
            bufferBuilder21.vertex(matrix4f20, floatValue23, floatValue24 + floatValue22, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue7);
            bufferBuilder21.vertex(matrix4f20, floatValue23 + floatValue4, floatValue24 + floatValue22, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue7);
            bufferBuilder21.vertex(matrix4f20, floatValue23 + floatValue4, floatValue24, 0.0F).color(1.0F, 1.0F, 1.0F, floatValue7);
            BufferRenderer.drawWithGlobalProgram(bufferBuilder21.end());
         }

         RenderSystem.depthMask(true);
      }
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
}
