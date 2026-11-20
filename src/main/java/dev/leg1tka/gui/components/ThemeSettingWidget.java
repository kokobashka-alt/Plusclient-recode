package dev.leg1tka.gui.components;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class ThemeSettingWidget extends dev.leg1tka.gui.components.SettingWidget {
   private final dev.leg1tka.settings.ThemeSetting f;
   private final Map<dev.leg1tka.managers.ThemeStorage.Theme, dev.leg1tka.api.animation.TimedAnimation> g = new HashMap<>();

   public ThemeSettingWidget(dev.leg1tka.settings.ThemeSetting value1) {
      this.f = value1;

      for (dev.leg1tka.managers.ThemeStorage.Theme theme3 : value1.e()) {
         this.g.put(theme3, new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.n, 350L));
      }
   }

   @Override
   public boolean a() {
      return this.f.isVisible();
   }

   @Override
   public void a(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4) {
      float floatValue5 = this.a != null && this.a.n() != null ? this.a.n().i().d() : this.h().d();
      float floatValue6 = this.i().d();
      float floatValue7 = MathHelper.clamp(floatValue5 * floatValue6, 0.0F, 1.0F);
      if (!(floatValue7 < 0.01F)) {
         float floatValue8 = this.b + 2.0F;
         float floatValue9 = floatValue8;
         float floatValue10 = this.c + 4.0F;
         float floatValue11 = this.d - 4.0F;

         for (dev.leg1tka.managers.ThemeStorage.Theme theme13 : this.f.e()) {
            if (floatValue9 + 10.0F > floatValue8 + floatValue11) {
               floatValue9 = floatValue8;
               floatValue10 += 12.0F;
            }

            dev.leg1tka.api.animation.TimedAnimation value14 = this.g.computeIfAbsent(theme13, theme0 -> new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.n, 350L));
            value14.a(this.f.f() == theme13);
            if (dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue2, (double)intValue3, (double)(floatValue9 - 1.0F), (double)(floatValue10 - 1.0F), 10.0, 10.0)) {
               dev.leg1tka.render.hud.HudVisibilityState.a();
            }

            if (value14.d() > 0.01F) {
               float floatValue15 = value14.d() * floatValue7;
               dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue9 - 1.5F, floatValue10 - 1.5F, 11.0F, 11.0F, 5.5F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue15)));
            }

            this.a(matrixStack1, theme13, floatValue9, floatValue10, floatValue7, value14.d());
            floatValue9 += 12.0F;
         }

         this.e = floatValue10 - this.c + 16.0F;
      }
   }

   private void a(MatrixStack matrixStack1, dev.leg1tka.managers.ThemeStorage.Theme theme2, float floatValue3, float floatValue4, float floatValue5, float floatValue6) {
      dev.leg1tka.gui.themes.ThemePalette value7 = theme2.a();
      boolean flag8 = "Rainbow".equalsIgnoreCase(value7.b());
      int intValue9 = dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue5);
      if (flag8) {
         int[] intValueArray10 = new int[4];

         for (int intValue11 = 0; intValue11 < 4; intValue11++) {
            intValueArray10[intValue11] = Color.HSBtoRGB((float)intValue11 / 4.0F, 0.8F, 1.0F);
         }

         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue3, floatValue4, 8.0F, 8.0F, 4.0F, dev.leg1tka.api.utils.text.ColorUtils.d(intValueArray10[0], intValue9), dev.leg1tka.api.utils.text.ColorUtils.d(intValueArray10[1], intValue9), dev.leg1tka.api.utils.text.ColorUtils.d(intValueArray10[2], intValue9), dev.leg1tka.api.utils.text.ColorUtils.d(intValueArray10[3], intValue9));
      } else {
         int intValue14 = value7.c.length > 0 ? value7.c[0] : dev.leg1tka.api.utils.text.ColorUtils.a();
         int intValue16 = value7.c.length > 1 ? value7.c[1] : intValue14;
         int intValue12 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue14, intValue9);
         int intValue13 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue16, intValue9);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue3, floatValue4, 8.0F, 8.0F, 4.0F, intValue12, intValue13, intValue12, intValue13);
      }

      if (floatValue6 > 0.01F) {
         float floatValue15 = 3.0F * floatValue6;
         float floatValue17 = floatValue3 + (8.0F - floatValue15) / 2.0F;
         float floatValue18 = floatValue4 + (8.0F - floatValue15) / 2.0F;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue17, floatValue18, floatValue15, floatValue15, floatValue15 / 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue5 * floatValue6)));
      }
   }

   @Override
   public void a(double doubleValue1, double doubleValue3, int intValue5) {
      if (intValue5 == 0) {
         float floatValue6 = this.b + 2.0F;
         float floatValue7 = floatValue6;
         float floatValue8 = this.c + 4.0F;
         float floatValue9 = this.d - 4.0F;

         for (dev.leg1tka.managers.ThemeStorage.Theme theme11 : this.f.e()) {
            if (floatValue7 + 10.0F > floatValue6 + floatValue9) {
               floatValue7 = floatValue6;
               floatValue8 += 12.0F;
            }

            if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)(floatValue7 - 1.0F), (double)(floatValue8 - 1.0F), 10.0, 10.0)) {
               this.f.a(theme11);
               return;
            }

            floatValue7 += 12.0F;
         }
      }
   }
}
