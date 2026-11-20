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

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class BindSettingWidget extends dev.leg1tka.gui.components.SettingWidget {
   private final dev.leg1tka.settings.KeybindSetting f;
   private boolean g;

   public BindSettingWidget(dev.leg1tka.settings.KeybindSetting value1) {
      this.f = value1;
   }

   private String k() {
      if (this.g) {
         return "...";
      } else {
         return this.f.getKey() == -1 ? "None" : dev.leg1tka.gui.components.InputBindings.d(this.f.getKey());
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
            boolean flag21 = true;
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

         String string12 = this.k();
         float floatValue13 = value11 == null ? 18.0F : value11.a(string12);
         float floatValue14 = Math.max(16.0F, floatValue13 + 8.0F);
         float floatValue15 = 10.5F;
         float floatValue16 = this.b + this.d - floatValue14 - 4.0F;
         float floatValue17 = this.c + (17.0F - floatValue15) / 2.0F;
         if (dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue2, (double)intValue3, (double)floatValue16, (double)floatValue17, (double)floatValue14, (double)floatValue15)) {
            dev.leg1tka.render.hud.HudVisibilityState.a();
         }

         float floatValue18 = floatValue16 - (this.b + 4.0F) - 2.0F;
         a(matrixStack1, value10, this.f.getDisplayName(), this.b + 4.0F, this.c + 4.5F, floatValue18, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue8));
         boolean flag19 = this.f.getKey() != -1;
         int intValue20;
         if (this.g) {
            intValue20 = dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.gui.menu.MenuBackdropRenderer.c(), dev.leg1tka.gui.themes.ThemeAccentResolver.a(180.0F * floatValue7));
         } else if (flag19) {
            intValue20 = dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.gui.menu.MenuBackdropRenderer.c(), intValue8);
         } else {
            intValue20 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(20.0F * floatValue7));
         }

         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue16, floatValue17, floatValue14, floatValue15, 2.5F, intValue20);
         if (value11 != null) {
            a(matrixStack1, value11, string12, floatValue16 + (floatValue14 - floatValue13) / 2.0F, floatValue17 + 1.8F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue8));
         }

         this.e = 17.0F;
      }
   }

   @Override
   public void a(double doubleValue1, double doubleValue3, int intValue5) {
      dev.leg1tka.render.font.MsdfTextRenderer value6 = dev.leg1tka.render.font.FontCache.a("bold", 13);
      if (value6 == null) {
         value6 = dev.leg1tka.render.font.FontCache.a("semibold", 13);
      }

      if (value6 == null) {
         value6 = dev.leg1tka.render.font.FontCache.a("sf_regular", 13);
      }

      String string7 = this.k();
      float floatValue8 = value6 == null ? 18.0F : value6.a(string7);
      float floatValue9 = Math.max(16.0F, floatValue8 + 8.0F);
      float floatValue10 = 10.5F;
      float floatValue11 = this.b + this.d - floatValue9 - 4.0F;
      float floatValue12 = this.c + (17.0F - floatValue10) / 2.0F;
      if (this.g) {
         if (intValue5 != 0) {
            this.f.setKey(dev.leg1tka.gui.components.InputBindings.g(intValue5));
         }

         this.g = false;
      } else if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)floatValue11, (double)floatValue12, (double)floatValue9, (double)floatValue10) && intValue5 == 0) {
         this.g = true;
      }
   }

   @Override
   public void a(int intValue1, int intValue2, int intValue3) {
      if (this.g) {
         if (intValue1 != 256 && intValue1 != 261 && intValue1 != 259) {
            this.f.setKey(intValue1);
         } else {
            this.f.setKey(-1);
         }

         this.g = false;
      }
   }

   public boolean b() {
      return this.g;
   }
}
