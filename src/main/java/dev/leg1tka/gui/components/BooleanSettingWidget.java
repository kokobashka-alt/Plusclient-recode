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

public class BooleanSettingWidget extends dev.leg1tka.gui.components.SettingWidget {
   private final BooleanSetting f;
   private final dev.leg1tka.api.animation.TimedAnimation g = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.n, 250L);
   private boolean h;

   public BooleanSettingWidget(BooleanSetting booleanSetting1) {
      this.f = booleanSetting1;
      this.g.b(booleanSetting1.getValue() ? 1.0F : 0.0F);
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
         float floatValue9 = 15.0F;
         float floatValue10 = 8.5F;
         float floatValue11 = this.b + this.d - floatValue9 - 4.0F;
         float floatValue12 = this.c + 4.0F;
         float floatValue13 = this.b + 4.0F;
         float floatValue14 = this.c + 4.0F;
         boolean flag15 = dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue2, (double)intValue3, (double)this.b, (double)this.c, (double)this.d, 17.0);
         if (flag15) {
            dev.leg1tka.render.hud.HudVisibilityState.a();
         }

         dev.leg1tka.render.font.MsdfTextRenderer value16 = dev.leg1tka.render.font.FontCache.a("bold", 14);
         if (value16 == null) {
            value16 = dev.leg1tka.render.font.FontCache.a("semibold", 14);
         }

         if (value16 == null) {
            value16 = dev.leg1tka.render.font.FontCache.a("sf_regular", 14);
         }

         float floatValue17 = floatValue11 - floatValue13 - 3.0F;
         a(matrixStack1, value16, this.h ? "Binding..." : this.f.getDisplayName(), floatValue13, floatValue14 + 0.5F, floatValue17, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue8));
         this.g.a(this.f.getValue());
         float floatValue18 = this.g.d();
         if (this.a != null && this.a.m().isEnabled()) {
            boolean flag26 = true;
         } else {
            boolean flag10000 = false;
         }

         int intValue20 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(25.0F * floatValue7));
         a(matrixStack1, floatValue11, floatValue12, floatValue9, floatValue10, 4.0F, intValue20, floatValue18, floatValue7);
         float floatValue21 = 3.2F;
         float floatValue22 = floatValue11 + 1.0F;
         float floatValue23 = floatValue11 + floatValue9 - floatValue21 * 2.0F - 1.0F;
         float floatValue24 = floatValue22 + (floatValue23 - floatValue22) * floatValue18;
         float floatValue25 = floatValue12 + 1.0F;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue24, floatValue25, floatValue21 * 2.0F, floatValue21 * 2.0F, floatValue21, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue8));
         this.e = 17.0F;
      }
   }

   @Override
   public void a(double doubleValue1, double doubleValue3, int intValue5) {
      if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)this.b, (double)this.c, (double)this.d, 17.0)) {
         if (intValue5 == 0) {
            this.f.setValue(!this.f.getValue());
         }

         if (intValue5 == 2 && this.h) {
            this.h = false;
            return;
         }

         if (this.h) {
            this.f.setKeyBind(intValue5);
            this.h = false;
         }

         if (intValue5 == 2) {
            this.h = true;
         }
      }
   }

   @Override
   public void a(int intValue1, int intValue2, int intValue3) {
      if (this.h) {
         if (intValue1 != 256 && intValue1 != 261 && intValue1 != 259) {
            this.f.setKeyBind(intValue1);
         } else {
            this.f.setKeyBind(-1);
         }

         this.h = false;
      }
   }

   public boolean b() {
      return this.h;
   }
}
