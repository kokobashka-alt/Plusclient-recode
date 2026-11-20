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

import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextIconButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public final class VanillaWidgetStyling implements dev.leg1tka.api.ClientAccess {
   public static final dev.leg1tka.gui.menu.VanillaWidgetStyling c = new dev.leg1tka.gui.menu.VanillaWidgetStyling();
   private final Map<ClickableWidget, dev.leg1tka.gui.menu.VanillaWidgetStyling.a> d = new WeakHashMap<>();

   private VanillaWidgetStyling() {
   }

   private dev.leg1tka.gui.menu.VanillaWidgetStyling.a a(ClickableWidget clickableWidget1, boolean flag2, boolean flag3) {
      dev.leg1tka.gui.menu.VanillaWidgetStyling.a value4 = this.d.computeIfAbsent(clickableWidget1, clickableWidget0 -> new dev.leg1tka.gui.menu.VanillaWidgetStyling.a());
      long longValue5 = System.nanoTime();
      float floatValue7 = value4.c == 0L ? 0.0F : Math.min(0.05F, (float)(longValue5 - value4.c) / 1.0E9F);
      value4.c = longValue5;
      value4.a = value4.a + ((flag2 ? 1.0F : 0.0F) - value4.a) * (1.0F - (float)Math.exp((double)(-floatValue7 * 9.0F)));
      value4.b = value4.b + ((flag3 ? 1.0F : 0.0F) - value4.b) * (1.0F - (float)Math.exp((double)(-floatValue7 * 22.0F)));
      return value4;
   }

   private static float a(float floatValue0) {
      floatValue0 = MathHelper.clamp(floatValue0, 0.0F, 1.0F);
      return floatValue0 * floatValue0 * (3.0F - 2.0F * floatValue0);
   }

   private static int a(int intValue0, int intValue1, float floatValue2) {
      int intValue3 = Math.round((float)(intValue0 >> 16 & 0xFF) + (float)((intValue1 >> 16 & 0xFF) - (intValue0 >> 16 & 0xFF)) * floatValue2);
      int intValue4 = Math.round((float)(intValue0 >> 8 & 0xFF) + (float)((intValue1 >> 8 & 0xFF) - (intValue0 >> 8 & 0xFF)) * floatValue2);
      int intValue5 = Math.round((float)(intValue0 & 0xFF) + (float)((intValue1 & 0xFF) - (intValue0 & 0xFF)) * floatValue2);
      return intValue3 << 16 | intValue4 << 8 | intValue5;
   }

   private static float a(int intValue0) {
      return ((float)(intValue0 >> 16 & 0xFF) * 0.299F + (float)(intValue0 >> 8 & 0xFF) * 0.587F + (float)(intValue0 & 0xFF) * 0.114F) / 255.0F;
   }

   public void a(DrawContext drawContext1, ClickableWidget clickableWidget2, float floatValue3, float floatValue4, int intValue5, int intValue6) {
      if (drawContext1 != null && clickableWidget2 != null && clickableWidget2.visible) {
         drawContext1.draw();
         dev.leg1tka.gui.menu.MenuLayout.a();
         boolean flag7 = clickableWidget2.active;
         boolean flag8 = flag7 && (clickableWidget2.isHovered() || clickableWidget2.isFocused() && minecraftClient.getNavigationType().isKeyboard());
         dev.leg1tka.gui.menu.VanillaWidgetStyling.a value9 = this.a(clickableWidget2, flag8, flag8 && clickableWidget2.isHovered() && minecraftClient.mouse.wasLeftButtonClicked());
         float floatValue10 = a(value9.a);
         float floatValue11 = (float)clickableWidget2.getX();
         float floatValue12 = (float)clickableWidget2.getY();
         float floatValue13 = (float)clickableWidget2.getWidth();
         float floatValue14 = (float)clickableWidget2.getHeight();
         float floatValue15 = floatValue13 > 40.0F ? floatValue14 * 0.5F : 6.0F;
         float floatValue16 = flag7 ? floatValue3 : floatValue3 * 0.55F;
         int intValue17 = dev.leg1tka.gui.menu.MenuBackdropRenderer.c();
         int intValue18 = dev.leg1tka.gui.menu.MenuBackdropRenderer.d();
         MatrixStack matrixStack19 = drawContext1.getMatrices();
         dev.leg1tka.render.RenderPrimitives.a(matrixStack19, floatValue11, floatValue12 + 2.0F, floatValue13, floatValue14, floatValue15, 10.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.18F * floatValue16, 0));
         if (floatValue10 > 0.01F) {
            dev.leg1tka.render.RenderPrimitives.a(matrixStack19, floatValue11, floatValue12, floatValue13, floatValue14, floatValue15, 12.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.42F * floatValue10 * floatValue16, intValue17));
         }

         float floatValue20 = floatValue13 > 0.0F ? ((float)intValue5 - floatValue11) / floatValue13 : 0.5F;
         float floatValue21 = floatValue14 > 0.0F ? ((float)intValue6 - floatValue12) / floatValue14 : 0.5F;
         dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack19, floatValue11, floatValue12, floatValue13, floatValue14, floatValue15, floatValue10, value9.b, floatValue20, floatValue21, floatValue16, intValue17, intValue18);
         dev.leg1tka.render.RenderPrimitives.e();
         if (!(clickableWidget2 instanceof TextIconButtonWidget) && clickableWidget2.getMessage() != null && !clickableWidget2.getMessage().getString().isEmpty()) {
            int intValue22 = flag7 ? -1 : -6250336;
            this.a(drawContext1, clickableWidget2.getMessage(), floatValue11, floatValue12, floatValue13, floatValue14, intValue22, true);
         }
      }
   }

   private void a(DrawContext drawContext1, Text text2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, boolean flag8) {
      String string9 = text2.getString();
      dev.leg1tka.render.font.MsdfTextRenderer value10 = null;
      float floatValue11 = 0.0F;

      for (int intValue12 = 15; intValue12 >= 9; intValue12--) {
         value10 = dev.leg1tka.render.font.FontCache.a("nunito", intValue12);
         if (value10 == null) {
            break;
         }

         floatValue11 = value10.a(string9);
         if (floatValue11 <= floatValue5 - 8.0F) {
            break;
         }
      }

      if (value10 != null) {
         float floatValue20 = value10.d() * 0.5F;
         dev.leg1tka.render.font.GlyphVertexWriter value13 = value10.c().a(72);
         float floatValue14 = value13 != null ? (value13.c() + value13.d()) * 0.5F : 0.36F;
         float floatValue15 = dev.leg1tka.gui.menu.MenuLayout.b(floatValue3 + (floatValue5 - floatValue11) * 0.5F);
         float floatValue16 = dev.leg1tka.gui.menu.MenuLayout.b(floatValue4 + floatValue6 * 0.5F + 2.5F - (value10.c().g() - floatValue14) * floatValue20);
         drawContext1.draw();
         if (flag8) {
            value10.e(drawContext1.getMatrices(), string9, floatValue15, floatValue16 + 0.75F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.35F * ((float)(intValue7 >>> 24) / 255.0F), 0));
         }

         value10.e(drawContext1.getMatrices(), string9, floatValue15, floatValue16, intValue7);
      } else {
         int intValue17 = minecraftClient.textRenderer.getWidth(text2);
         int intValue18 = Math.round(floatValue3 + (floatValue5 - (float)intValue17) * 0.5F);
         int intValue19 = Math.round(floatValue4 + (floatValue6 - 8.0F) * 0.5F);
         drawContext1.drawText(minecraftClient.textRenderer, text2, intValue18, intValue19, intValue7, flag8);
      }
   }

   public void a(DrawContext drawContext1, SliderWidget sliderWidget2, double doubleValue3, float floatValue5, float floatValue6) {
      if (drawContext1 != null && sliderWidget2 != null && sliderWidget2.visible) {
         drawContext1.draw();
         dev.leg1tka.gui.menu.MenuLayout.a();
         boolean flag7 = sliderWidget2.active;
         boolean flag8 = flag7 && (sliderWidget2.isHovered() || sliderWidget2.isFocused() && minecraftClient.getNavigationType().isKeyboard());
         dev.leg1tka.gui.menu.VanillaWidgetStyling.a value9 = this.a(sliderWidget2, flag8, false);
         float floatValue10 = a(value9.a);
         float floatValue11 = (float)sliderWidget2.getX();
         float floatValue12 = (float)sliderWidget2.getY();
         float floatValue13 = (float)sliderWidget2.getWidth();
         float floatValue14 = (float)sliderWidget2.getHeight();
         float floatValue15 = floatValue13 > 40.0F ? floatValue14 * 0.5F : 6.0F;
         float floatValue16 = flag7 ? floatValue5 : floatValue5 * 0.55F;
         int intValue17 = dev.leg1tka.gui.menu.MenuBackdropRenderer.c();
         int intValue18 = dev.leg1tka.gui.menu.MenuBackdropRenderer.d();
         MatrixStack matrixStack19 = drawContext1.getMatrices();
         dev.leg1tka.render.RenderPrimitives.a(matrixStack19, floatValue11, floatValue12 + 2.0F, floatValue13, floatValue14, floatValue15, 10.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.18F * floatValue16, 0));
         if (floatValue10 > 0.01F) {
            dev.leg1tka.render.RenderPrimitives.a(matrixStack19, floatValue11, floatValue12, floatValue13, floatValue14, floatValue15, 12.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.32F * floatValue10 * floatValue16, intValue17));
         }

         dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack19, floatValue11, floatValue12, floatValue13, floatValue14, floatValue15, 0.0F, 0.0F, 0.5F, 0.5F, floatValue16, intValue17, intValue18);
         float floatValue20 = 14.0F;
         float floatValue21 = (float)MathHelper.clamp(doubleValue3, 0.0, 1.0);
         float floatValue22 = Math.max(1.0F, floatValue13 - floatValue20 * 2.0F);
         float floatValue23 = 3.0F;
         float floatValue24 = floatValue12 + floatValue14 * 0.5F - floatValue23 * 0.5F;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack19, floatValue11 + floatValue20, floatValue24, floatValue22, floatValue23, floatValue23 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.14F + 0.06F * floatValue10) * floatValue16, 16777215));
         float floatValue25 = floatValue21 * floatValue22;
         if (floatValue25 > 1.0F) {
            dev.leg1tka.render.RenderPrimitives.a(matrixStack19, floatValue11 + floatValue20, floatValue24, floatValue25, floatValue23, floatValue23 * 0.5F, 6.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.25F + 0.25F * floatValue10) * floatValue16, intValue17));
            dev.leg1tka.render.RenderPrimitives.a(matrixStack19, floatValue11 + floatValue20, floatValue24, floatValue25, floatValue23, floatValue23 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue16, intValue17), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue16, intValue18), true);
         }

         float floatValue26 = 8.0F;
         float floatValue27 = floatValue14 - 6.0F;
         float floatValue28 = floatValue12 + 3.0F;
         float floatValue29 = floatValue11 + floatValue20 - floatValue26 * 0.5F + floatValue21 * floatValue22;
         dev.leg1tka.render.RenderPrimitives.a(matrixStack19, floatValue29, floatValue28 + 1.0F, floatValue26, floatValue27, 3.5F, 6.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.3F * floatValue16, 0));
         if (floatValue10 > 0.01F) {
            dev.leg1tka.render.RenderPrimitives.a(matrixStack19, floatValue29, floatValue28, floatValue26, floatValue27, 3.5F, 8.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.45F * floatValue10 * floatValue16, intValue17));
         }

         dev.leg1tka.render.RenderPrimitives.b(matrixStack19, floatValue29, floatValue28, floatValue26, floatValue27, 3.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.85F + 0.15F * floatValue10) * floatValue16, 16777215));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack19, floatValue29 + 1.0F, floatValue28 + 1.0F, floatValue26 - 2.0F, 1.0F, 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.4F * floatValue16, 16777215));
         dev.leg1tka.render.RenderPrimitives.e();
         if (sliderWidget2.getMessage() != null && !sliderWidget2.getMessage().getString().isEmpty()) {
            this.a(drawContext1, sliderWidget2.getMessage(), floatValue11, floatValue12, floatValue13, floatValue14, flag7 ? -1 : -6250336, true);
         }
      }
   }

   static class a {
      float a;
      float b;
      long c;

      a() {
      }
   }
}
