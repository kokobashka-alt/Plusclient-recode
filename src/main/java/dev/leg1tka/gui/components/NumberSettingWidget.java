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

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class NumberSettingWidget extends dev.leg1tka.gui.components.SettingWidget {
   private final NumberSetting f;
   private boolean g;
   private final dev.leg1tka.api.animation.TimedAnimation h = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.n, 120L);

   public NumberSettingWidget(NumberSetting numberSetting1) {
      this.f = numberSetting1;
   }

   private int b() {
      float floatValue1 = this.f.i();
      if (floatValue1 <= 0.0F) {
         return 2;
      } else {
         String string2 = BigDecimal.valueOf((double)floatValue1).stripTrailingZeros().toPlainString();
         int intValue3 = string2.indexOf(46);
         return intValue3 < 0 ? 0 : Math.max(0, Math.min(string2.length() - intValue3 - 1, 4));
      }
   }

   private double a(double doubleValue1) {
      double doubleValue3 = (double)this.f.i();
      if (doubleValue3 <= 0.0) {
         return doubleValue1;
      } else {
         double doubleValue5 = (double)Math.round(doubleValue1 / doubleValue3) * doubleValue3;
         int intValue7 = Math.max(this.b(), 4);
         return new BigDecimal(Double.toString(doubleValue5)).setScale(intValue7, RoundingMode.HALF_UP).doubleValue();
      }
   }

   private String b(double doubleValue1) {
      if (!Double.isNaN(doubleValue1) && !Double.isInfinite(doubleValue1)) {
         int intValue3 = this.b();
         return intValue3 == 0
            ? String.valueOf(Math.round(doubleValue1))
            : BigDecimal.valueOf(doubleValue1).setScale(intValue3, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
      } else {
         return "0";
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
         int intValue8 = dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue7);
         if (this.a != null && this.a.m().isEnabled()) {
            boolean flag31 = true;
         } else {
            boolean flag10000 = false;
         }

         dev.leg1tka.render.font.MsdfTextRenderer value10 = dev.leg1tka.render.font.FontCache.a("bold", 14);
         if (value10 == null) {
            value10 = dev.leg1tka.render.font.FontCache.a("semibold", 14);
         }

         if (value10 == null) {
            value10 = dev.leg1tka.render.font.FontCache.a("sf_regular", 14);
         }

         dev.leg1tka.render.font.MsdfTextRenderer value11 = dev.leg1tka.render.font.FontCache.a("bold", 13);
         if (value11 == null) {
            value11 = value10;
         }

         String string12 = this.b((double)this.f.f());
         float floatValue13 = value11 == null ? 13.0F : value11.a(string12);
         float floatValue14 = floatValue13 + 8.0F;
         float floatValue15 = 10.5F;
         float floatValue16 = this.b + this.d - floatValue14 - 4.0F;
         float floatValue17 = this.c + 2.0F;
         a(matrixStack1, floatValue16, floatValue17, floatValue14, floatValue15, 2.5F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(20.0F * floatValue7)), 1.0F, floatValue7);
         if (value11 != null) {
            a(matrixStack1, value11, string12, floatValue16 + 4.0F, floatValue17 + 1.8F, a(1.0F, floatValue7));
         }

         float floatValue18 = floatValue16 - (this.b + 4.0F) - 3.0F;
         a(matrixStack1, value10, this.f.getDisplayName(), this.b + 4.0F, this.c + 2.0F, floatValue18, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue8));
         float floatValue19 = this.b + 4.0F;
         float floatValue20 = this.d - 8.0F;
         float floatValue21 = this.c + 14.5F;
         float floatValue22 = 3.2F;
         boolean flag23 = dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue2, (double)intValue3, (double)this.b, (double)this.c, (double)this.d, 21.0);
         if (flag23 || this.g) {
            dev.leg1tka.render.hud.HudVisibilityState.a();
         }

         if (this.g) {
            double doubleValue24 = (double)(this.f.h() - this.f.g());
            double doubleValue26 = (double)this.f.g() + (double)MathHelper.clamp(((float)intValue2 - floatValue19) / floatValue20, 0.0F, 1.0F) * doubleValue24;
            this.f.a((float)this.a(doubleValue26));
         }

         this.h.a(floatValue20 * (this.f.f() - this.f.g()) / (this.f.h() - this.f.g()));
         float floatValue29 = MathHelper.clamp(this.h.d(), 0.0F, floatValue20);
         int intValue25 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(25.0F * floatValue7));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue19, floatValue21, floatValue20, floatValue22, 1.5F, intValue25);
         if (floatValue29 > 0.0F) {
            a(matrixStack1, floatValue19, floatValue21, floatValue29, floatValue22, 1.5F, intValue25, 1.0F, floatValue7);
         }

         float floatValue30 = 3.5F;
         float floatValue27 = floatValue19 + floatValue29;
         float floatValue28 = floatValue21 + floatValue22 / 2.0F;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue27 - floatValue30, floatValue28 - floatValue30, floatValue30 * 2.0F, floatValue30 * 2.0F, floatValue30, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue8));
         this.e = 21.0F;
      }
   }

   @Override
   public void a(double doubleValue1, double doubleValue3, int intValue5) {
      if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)this.b, (double)this.c, (double)this.d, 21.0) && intValue5 == 0) {
         this.g = true;
      }
   }

   @Override
   public void b(double doubleValue1, double doubleValue3, int intValue5) {
      this.g = false;
   }
}
