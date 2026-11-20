package dev.leg1tka.render.hud;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.implement.EventRender;

public class CoordinateHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   public CoordinateHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
   }

   @Override
   public void a(EventRender.Default default1) {
      this.b(default1);
      super.a(default1);
   }

   public void b(EventRender.Default default1) {
      float floatValue2 = this.e.a();
      float floatValue3 = this.e.b();
      dev.leg1tka.render.font.MsdfTextRenderer value4 = dev.leg1tka.render.font.FontCache.a("nunito", 13);
      dev.leg1tka.render.font.MsdfTextRenderer value5 = dev.leg1tka.render.font.FontCache.a("icon", 16);
      int intValue6;
      if (!AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow")) {
         intValue6 = AspectDLC.INSTANCE.themeStorage.b().a().c[0];
      } else {
         intValue6 = dev.leg1tka.api.utils.text.ColorUtils.a();
      }

      dev.leg1tka.render.RenderExecutionMode value7 = this.b();
      int intValue8 = (int)Math.floor(minecraftClient.player.getX());
      int intValue9 = (int)Math.floor(minecraftClient.player.getY());
      int intValue10 = (int)Math.floor(minecraftClient.player.getZ());
      float floatValue11 = 24.0F;
      double doubleValue12 = (double)dev.leg1tka.api.utils.math.MathOperations.a();
      String string14 = "Bps: " + this.a(doubleValue12);
      String string15 = "Xyz: " + intValue8 + " " + intValue9 + " " + intValue10;
      float floatValue16 = Math.max(82.0F, Math.max(12.0F + value5.b("c") + 3.0F + value4.b(string14), 12.0F + value5.b("x") + 3.0F + value4.b(string15)));
      this.a(default1.getContext().getMatrices(), floatValue2, floatValue3, floatValue16, floatValue11, intValue6);
      float floatValue17 = floatValue3 + 6.0F;
      float floatValue18 = floatValue3 + 13.5F;
      int intValue19 = (int)((float)(System.currentTimeMillis() % 2400L) / 2400.0F * 360.0F);
      int intValue20 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue19);
      int intValue21 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue19 + 140);
      value5.d(default1.getContext().getMatrices(), "c", floatValue2 + 3.25F, floatValue17, intValue20);
      value5.d(default1.getContext().getMatrices(), "x", floatValue2 + 3.25F, floatValue18, intValue21);
      value4.a(default1.getContext().getMatrices(), string14, floatValue2 + 13.5F, floatValue17, intValue20, intValue21);
      value4.a(default1.getContext().getMatrices(), string15, floatValue2 + 13.5F, floatValue18, intValue21, intValue20);
      this.e.d(floatValue11);
      this.e.c(floatValue16);
   }

   public void c(EventRender.Default default1) {
      float floatValue2 = this.e.a();
      float floatValue3 = this.e.b();
      float floatValue4 = (float)(System.currentTimeMillis() % 2000L) / 2000.0F * 360.0F;
      int intValue5 = dev.leg1tka.api.utils.text.ColorUtils.k((int)floatValue4);
      int intValue6 = dev.leg1tka.api.utils.text.ColorUtils.k((int)(floatValue4 + 30.0F));
      int intValue7 = dev.leg1tka.api.utils.text.ColorUtils.k((int)(floatValue4 + 90.0F));
      int intValue8 = dev.leg1tka.api.utils.text.ColorUtils.k((int)(floatValue4 + 120.0F));
      int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.k((int)(floatValue4 + 180.0F));
      int intValue10 = dev.leg1tka.api.utils.text.ColorUtils.k((int)(floatValue4 + 210.0F));
      String string11 = "info";
      String string12 = "x: " + (int)minecraftClient.player.getPos().getX();
      String string13 = "y: " + (int)minecraftClient.player.getPos().getY();
      String string14 = "z: " + (int)minecraftClient.player.getPos().getZ();
      String string15 = "bps: " + (int)dev.leg1tka.api.utils.math.MathOperations.a();
      dev.leg1tka.render.font.MsdfTextRenderer value16 = dev.leg1tka.render.font.FontCache.a("nunito", 15);
      float floatValue17 = value16.b(string12);
      float floatValue18 = value16.b(string13);
      float floatValue19 = value16.b(string14);
      float floatValue20 = value16.b(string15);
      float floatValue21 = value16.b(string11);
      float floatValue22 = Math.max(Math.max(floatValue17, Math.max(floatValue18, floatValue19)), floatValue20);
      float floatValue23 = 9.0F;
      float floatValue24 = floatValue22 + floatValue23;
      float floatValue25 = 48.0F;
      floatValue24 = Math.max(floatValue24, 35.0F);
      float floatValue26 = floatValue2 + floatValue24 / 2.0F;
      dev.leg1tka.render.RenderPrimitives.a(default1.getContext().getMatrices(), floatValue2, floatValue3, floatValue24, floatValue25, dev.leg1tka.api.utils.text.ColorUtils.a(25, 25, 25, 150), 3.5F, 0.0F, 10.0F, 10.0F, intValue5, intValue6, intValue7, intValue8, intValue9, intValue10);
      float floatValue27 = 5.0F;
      dev.leg1tka.render.RenderPrimitives.a(default1.getContext().getMatrices(), floatValue2 + floatValue27, floatValue3 + 12.0F, floatValue24 - floatValue27 * 2.0F, 2.5F, 0.0F, 10.0F, 10.0F, intValue5, intValue6, intValue7, intValue8, intValue9, intValue10);
      value16.f(default1.getContext().getMatrices(), string11, floatValue26 - floatValue21 / 2.0F, floatValue3 + 5.0F, -1);
      value16.f(default1.getContext().getMatrices(), string12, floatValue2 + 4.5F, floatValue3 + 17.0F, -1);
      value16.f(default1.getContext().getMatrices(), string13, floatValue2 + 4.5F, floatValue3 + 24.0F, -1);
      value16.f(default1.getContext().getMatrices(), string14, floatValue2 + 4.5F, floatValue3 + 31.0F, -1);
      value16.f(default1.getContext().getMatrices(), string15, floatValue2 + 4.5F, floatValue3 + 38.0F, -1);
      this.e.c(floatValue24);
      this.e.d(floatValue25);
   }

   private String a(double doubleValue1) {
      int intValue3 = (int)Math.round(doubleValue1 * 100.0);
      int intValue4 = Math.abs(intValue3 % 100);
      return intValue3 / 100 + "." + (intValue4 < 10 ? "0" : "") + intValue4;
   }
}
