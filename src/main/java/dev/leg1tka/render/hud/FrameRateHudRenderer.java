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

import dev.leg1tka.api.events.implement.EventRender;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;

public class FrameRateHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   private final MinecraftClient f = MinecraftClient.getInstance();
   private final List<Long> g = new ArrayList<>();
   private boolean h = false;

   public FrameRateHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
   }

   @Override
   public void a(EventRender.Default default1) {
      float floatValue2 = this.e.a();
      float floatValue3 = this.e.b();
      dev.leg1tka.render.font.MsdfTextRenderer value4 = dev.leg1tka.render.font.FontCache.a("nunito", 15);
      dev.leg1tka.render.font.MsdfTextRenderer value5 = dev.leg1tka.render.font.FontCache.a("nunito", 10);
      float floatValue6 = 20.0F;
      float floatValue7 = 2.0F;
      boolean flag8 = this.f.options.forwardKey.isPressed();
      boolean flag9 = this.f.options.leftKey.isPressed();
      boolean flag10 = this.f.options.backKey.isPressed();
      boolean flag11 = this.f.options.rightKey.isPressed();
      boolean flag12 = this.f.options.jumpKey.isPressed();
      boolean flag13 = this.f.options.attackKey.isPressed();
      boolean flag14 = this.f.options.useKey.isPressed();
      long longValue15 = System.currentTimeMillis();
      if (flag13 && !this.h) {
         this.g.add(longValue15);
      }

      this.h = flag13;
      this.g.removeIf(long2x -> longValue15 - long2x > 1000L);
      int intValue17 = this.g.size();
      float floatValue18 = floatValue2 + floatValue6 + floatValue7;
      this.a(default1, floatValue18, floatValue3, floatValue6, floatValue6, "W", flag8, value4);
      float floatValue21 = floatValue3 + floatValue6 + floatValue7;
      this.a(default1, floatValue2, floatValue21, floatValue6, floatValue6, "A", flag9, value4);
      float floatValue22 = floatValue2 + floatValue6 + floatValue7;
      float floatValue23 = floatValue3 + floatValue6 + floatValue7;
      this.a(default1, floatValue22, floatValue23, floatValue6, floatValue6, "S", flag10, value4);
      float floatValue24 = floatValue2 + (floatValue6 + floatValue7) * 2.0F;
      float floatValue25 = floatValue3 + floatValue6 + floatValue7;
      this.a(default1, floatValue24, floatValue25, floatValue6, floatValue6, "D", flag11, value4);
      float floatValue26 = floatValue6 * 3.0F + floatValue7 * 2.0F;
      float floatValue27 = 20.0F;
      float floatValue29 = floatValue3 + (floatValue6 + floatValue7) * 2.0F;
      this.a(default1, floatValue2, floatValue29, floatValue26, floatValue27, "Space", flag12, value4);
      float floatValue30 = (floatValue26 - floatValue7) / 2.0F;
      float floatValue31 = 20.0F;
      float floatValue33 = floatValue3 + (floatValue6 + floatValue7) * 2.0F + floatValue27 + floatValue7;
      float floatValue34 = (float)(System.currentTimeMillis() % 2000L) / 2000.0F * 360.0F;
      int intValue35 = dev.leg1tka.api.utils.text.ColorUtils.k((int)floatValue34);
      this.a(default1, floatValue2, floatValue33, floatValue30, floatValue31, "LMB", flag13, value4, value5, intValue17, intValue35);
      float floatValue36 = floatValue2 + floatValue30 + floatValue7;
      float floatValue37 = floatValue3 + (floatValue6 + floatValue7) * 2.0F + floatValue27 + floatValue7;
      this.a(default1, floatValue36, floatValue37, floatValue30, floatValue31, "RMB", flag14, value4);
      float floatValue38 = floatValue6 * 3.0F + floatValue7 * 2.0F;
      float floatValue39 = floatValue6 * 2.0F + floatValue7 + floatValue27 + floatValue7 + floatValue31 + floatValue7;
      this.e.c(floatValue38);
      this.e.d(floatValue39);
      super.a(default1);
   }

   private void a(EventRender.Default default1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, String string6, boolean flag7, Object object8) {
      int intValue9 = flag7 ? dev.leg1tka.api.utils.text.ColorUtils.a(180, 180, 180, 200) : dev.leg1tka.api.utils.text.ColorUtils.a(25, 25, 25, 150);
      int intValue10 = flag7 ? dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, 255) : dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 255);
      dev.leg1tka.render.RenderPrimitives.c(default1.getContext().getMatrices(), floatValue2, floatValue3, floatValue4, floatValue5, 3.0F, intValue9);
      dev.leg1tka.render.font.MsdfTextRenderer value11 = dev.leg1tka.render.font.FontCache.a("nunito", 15);
      float floatValue12 = value11.b(string6);
      float floatValue13 = 8.0F;
      float floatValue14 = floatValue2 + (floatValue4 - floatValue12) / 2.0F;
      float floatValue15 = floatValue3 + (floatValue5 - floatValue13) / 2.0F;
      value11.d(default1.getContext().getMatrices(), string6, floatValue14 - 0.5F, floatValue15 + 2.0F, intValue10);
   }

   private void a(
      EventRender.Default default1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, String string6, boolean flag7, Object object8, Object object9, int intValue10, int intValue11
   ) {
      int intValue12 = flag7 ? dev.leg1tka.api.utils.text.ColorUtils.a(180, 180, 180, 200) : dev.leg1tka.api.utils.text.ColorUtils.a(25, 25, 25, 150);
      int intValue13 = flag7 ? dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, 255) : dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 255);
      dev.leg1tka.render.RenderPrimitives.c(default1.getContext().getMatrices(), floatValue2, floatValue3, floatValue4, floatValue5, 3.0F, intValue12);
      dev.leg1tka.render.font.MsdfTextRenderer value14 = dev.leg1tka.render.font.FontCache.a("nunito", 15);
      dev.leg1tka.render.font.MsdfTextRenderer value15 = dev.leg1tka.render.font.FontCache.a("nunito", 12);
      float floatValue16 = value14.b(string6);
      float floatValue17 = floatValue2 + (floatValue4 - floatValue16) / 2.0F;
      float floatValue18 = 8.0F;
      float floatValue19 = floatValue3 + (floatValue5 - floatValue18) / 2.0F;
      value14.d(default1.getContext().getMatrices(), string6, floatValue17 - 0.5F, floatValue19 + 2.0F, intValue13);
      String string20 = "cps: " + intValue10;
      float floatValue21 = value15.b(string20);
      float floatValue22 = floatValue2 + (floatValue4 - floatValue21) / 2.0F;
      float floatValue23 = floatValue19 + 12.0F;
      value15.d(default1.getContext().getMatrices(), string20, floatValue22, floatValue23 - 3.0F, intValue11);
   }
}
