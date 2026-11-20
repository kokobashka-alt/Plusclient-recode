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

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget.PressAction;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.RotationAxis;
import org.lwjgl.glfw.GLFW;

public class MenuButton extends ButtonWidget {
   private static int a;
   private static int b;
   private final String c;
   private boolean d = false;
   private float e = 0.0F;
   private float f = 0.0F;
   private float g = -1.0F;
   private float h = -1.0F;
   private float i = -1.0F;
   private float j = -1.0F;
   private float k = 1.0F;
   private long l = 0L;

   public MenuButton(int intValue1, int intValue2, int intValue3, String string4, Text text5, PressAction pressAction6) {
      super(intValue1, intValue2, intValue3, intValue3, text5, pressAction6, ButtonWidget.DEFAULT_NARRATION_SUPPLIER);
      this.c = string4;
   }

   public dev.leg1tka.gui.menu.MenuButton a() {
      this.d = true;
      return this;
   }

   public void a(float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5) {
      this.g = floatValue1;
      this.h = floatValue2;
      this.i = floatValue3;
      this.j = floatValue4;
      this.e = floatValue5;
      this.setDimensionsAndPosition(Math.round(floatValue3), Math.round(floatValue4), Math.round(floatValue1), Math.round(floatValue2));
   }

   public void a(float floatValue1) {
      this.k = floatValue1;
   }

   public float b() {
      return this.g >= 0.0F ? this.g : (float)this.getX();
   }

   public float c() {
      return this.i > 0.0F ? this.i : (float)this.getWidth();
   }

   public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
      long longValue5 = System.nanoTime();
      float floatValue7 = this.l == 0L ? 0.016F : Math.min(0.05F, (float)((double)(longValue5 - this.l) / 1.0E9));
      this.l = longValue5;
      boolean flag8 = this.isMouseOver((double)mouseX, (double)mouseY) && this.k > 0.5F;
      if (this.g < 0.0F) {
         this.e = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.e, flag8 ? 1.0F : 0.0F, floatValue7, 12.0F);
      }

      boolean flag9 = flag8 && GLFW.glfwGetMouseButton(MinecraftClient.getInstance().getWindow().getHandle(), 0) == 1;
      this.f = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.f, flag9 ? 1.0F : 0.0F, floatValue7, 22.0F);
      float floatValue10 = dev.leg1tka.gui.menu.MenuNavigation.b() * this.k;
      if (!(floatValue10 < 0.02F)) {
         MatrixStack matrixStack11 = context.getMatrices();
         float floatValue12 = (1.0F - floatValue10) * dev.leg1tka.gui.menu.MenuLayout.a(10.0F);
         float floatValue13 = (0.94F + floatValue10 * 0.06F) * (1.0F - this.f * 0.07F);
         float floatValue14 = this.i > 0.0F ? this.i : (float)this.getWidth();
         float floatValue15 = this.j > 0.0F ? this.j : (float)this.getHeight();
         float floatValue16 = this.g >= 0.0F ? this.g : (float)this.getX();
         float floatValue17 = this.h >= 0.0F ? this.h : (float)this.getY();
         float floatValue18 = floatValue16 + floatValue14 / 2.0F;
         float floatValue19 = floatValue17 + floatValue15 / 2.0F;
         float floatValue20 = floatValue14 * 0.3F;
         int intValue21 = this.d ? -45730 : dev.leg1tka.gui.menu.MenuBackdropRenderer.c();
         int intValue22 = this.d ? -30117 : dev.leg1tka.gui.menu.MenuBackdropRenderer.d();
         matrixStack11.push();
         matrixStack11.translate(0.0F, floatValue12, 0.0F);
         matrixStack11.translate(floatValue18, floatValue19, 0.0F);
         matrixStack11.scale(floatValue13, floatValue13, 1.0F);
         matrixStack11.translate(-floatValue18, -floatValue19, 0.0F);
         if (this.e > 0.01F) {
            float floatValue23 = dev.leg1tka.gui.menu.MenuLayout.a(3.0F);
            dev.leg1tka.render.RenderPrimitives.a(
               matrixStack11,
               floatValue16 + floatValue23,
               floatValue17 + floatValue23 * 1.5F,
               floatValue14 - floatValue23 * 2.0F,
               floatValue15 - floatValue23,
               floatValue20,
               dev.leg1tka.gui.menu.MenuLayout.a(12.0F + this.e * 8.0F),
               dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.5F * this.e * floatValue10, intValue21)
            );
         }

         dev.leg1tka.render.RenderPrimitives.a(matrixStack11, floatValue16, floatValue17 + dev.leg1tka.gui.menu.MenuLayout.a(3.0F), floatValue14, floatValue15, floatValue20, dev.leg1tka.gui.menu.MenuLayout.a(8.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.16F * floatValue10, 0));
         float floatValue39 = ((float)mouseX - floatValue16) / Math.max(1.0F, floatValue14);
         float floatValue24 = ((float)mouseY - floatValue17) / Math.max(1.0F, floatValue15);
         dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack11, floatValue16, floatValue17, floatValue14, floatValue15, floatValue20, this.e, this.f, floatValue39, floatValue24, floatValue10, intValue21, intValue22);
         int intValue25 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue10 * (0.82F + this.e * 0.18F), 16777215);
         float floatValue26 = this.e * dev.leg1tka.gui.menu.MenuLayout.a(0.8F);
         float floatValue27 = floatValue14 * 0.43F;
         if (this.c.equals("s")) {
            float floatValue28 = floatValue27 * 1.0F;
            float floatValue29 = Math.max(dev.leg1tka.gui.menu.MenuLayout.c() * 2.0F, floatValue27 * 0.13F);

            for (int intValue30 = 0; intValue30 < 2; intValue30++) {
               matrixStack11.push();
               matrixStack11.translate(floatValue18, floatValue19 - floatValue26, 0.0F);
               matrixStack11.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(intValue30 == 0 ? 45.0F : -45.0F));
               dev.leg1tka.render.RenderPrimitives.b(matrixStack11, -floatValue28 * 0.5F, -floatValue29 * 0.5F, floatValue28, floatValue29, floatValue29 * 0.5F, intValue25);
               matrixStack11.pop();
            }
         } else {
            dev.leg1tka.gui.components.MenuTextRenderer.f(matrixStack11, this.c, floatValue27, floatValue18, floatValue19 - floatValue26, intValue25);
         }

         matrixStack11.pop();
         if (this.e > 0.15F && floatValue10 > 0.8F) {
            String string40 = this.getMessage().getString();
            float floatValue41 = dev.leg1tka.gui.menu.MenuBackdropRenderer.b((this.e - 0.15F) / 0.85F) * floatValue10;
            float floatValue42 = 12.5F;
            float floatValue31 = dev.leg1tka.gui.components.MenuTextRenderer.a(floatValue42, string40);
            float floatValue32 = dev.leg1tka.gui.menu.MenuLayout.a(24.0F);
            float floatValue33 = dev.leg1tka.gui.menu.MenuLayout.a(11.0F);
            float floatValue34 = dev.leg1tka.gui.menu.MenuLayout.a(5.0F);
            float floatValue35 = floatValue33 + floatValue34 + dev.leg1tka.gui.menu.MenuLayout.a(7.0F) + floatValue31 + floatValue33;
            float floatValue36 = dev.leg1tka.gui.menu.MenuLayout.b(floatValue18 - floatValue35 / 2.0F);
            float floatValue37 = dev.leg1tka.gui.menu.MenuLayout.b(floatValue17 - floatValue32 - dev.leg1tka.gui.menu.MenuLayout.a(10.0F) + (1.0F - floatValue41) * dev.leg1tka.gui.menu.MenuLayout.a(4.0F));
            float floatValue38 = floatValue37 + floatValue32 / 2.0F;
            dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack11, floatValue36, floatValue37, floatValue35, floatValue32, floatValue32 * 0.5F, floatValue41);
            dev.leg1tka.render.RenderPrimitives.a(matrixStack11, floatValue36 + floatValue33 + floatValue34 * 0.5F, floatValue38, floatValue34, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue41, intValue21));
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack11, string40, floatValue42, floatValue36 + floatValue33 + floatValue34 + dev.leg1tka.gui.menu.MenuLayout.a(7.0F), floatValue38, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue41, 16777215));
         }
      }
   }
}
