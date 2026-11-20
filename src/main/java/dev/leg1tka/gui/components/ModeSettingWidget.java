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

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class ModeSettingWidget extends dev.leg1tka.gui.components.SettingWidget {
   private final MultiSetting f;
   private final List g = new ArrayList();

   public ModeSettingWidget(MultiSetting multiSetting1) {
      this.f = multiSetting1;

      for (int intValue2 = 0; intValue2 < multiSetting1.e().size(); intValue2++) {
         this.g.add(new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.n, 250L));
      }
   }

   private float a(dev.leg1tka.render.font.MsdfTextRenderer value1, String string2) {
      return (value1 == null ? (float)string2.length() * 5.5F : value1.a(string2)) + 9.0F;
   }

   private float a(dev.leg1tka.render.font.MsdfTextRenderer value1) {
      float floatValue2 = this.d - 8.0F;
      float floatValue3 = 0.0F;
      float floatValue4 = 16.0F;
      boolean flag5 = true;

      for (BooleanSetting booleanSetting7 : this.f.e()) {
         if (booleanSetting7.isVisible()) {
            float floatValue8 = this.a(value1, booleanSetting7.getDisplayName());
            if (!flag5 && floatValue3 + floatValue8 > floatValue2) {
               floatValue4 += 16.0F;
               floatValue3 = floatValue8 + 3.0F;
            } else {
               floatValue3 += floatValue8 + 3.0F;
               flag5 = false;
            }
         }
      }

      return floatValue4 + 13.0F;
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
            boolean flag32 = true;
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

         float floatValue12 = this.a(value11);
         this.e = floatValue12 + 2.0F;
         long longValue13 = this.f.e().stream().filter(booleanSetting0 -> booleanSetting0.isVisible() && booleanSetting0.getValue()).count();
         long longValue15 = this.f.e().stream().filter(Setting::isVisible).count();
         String string17 = longValue13 + "/" + longValue15;
         float floatValue18 = value11 == null ? 15.0F : value11.a(string17);
         float floatValue19 = this.d - 8.0F - floatValue18 - 4.0F;
         a(matrixStack1, value10, this.f.getDisplayName(), this.b + 4.0F, this.c + 2.0F, floatValue19, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue8));
         if (value11 != null) {
            a(matrixStack1, value11, string17, this.b + this.d - floatValue18 - 4.0F, this.c + 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(180.0F * floatValue7)));
         }

         float floatValue20 = this.b + 4.0F;
         float floatValue21 = this.c + 15.0F;
         boolean flag22 = true;
         int intValue23 = 0;

         for (BooleanSetting booleanSetting25 : this.f.e()) {
            if (!booleanSetting25.isVisible()) {
               intValue23++;
            } else {
               float floatValue26 = this.a(value11, booleanSetting25.getDisplayName());
               if (!flag22 && floatValue20 + floatValue26 > this.b + this.d - 4.0F) {
                  floatValue20 = this.b + 4.0F;
                  floatValue21 += 16.0F;
               }

               flag22 = false;
               if (intValue23 < this.g.size()) {
                  dev.leg1tka.api.animation.TimedAnimation value27 = (dev.leg1tka.api.animation.TimedAnimation)this.g.get(intValue23);
                  value27.a(booleanSetting25.getValue());
                  float floatValue28 = value27.d();
                  boolean flag29 = dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue2, (double)intValue3, (double)floatValue20, (double)floatValue21, (double)floatValue26, 13.0);
                  if (flag29) {
                     dev.leg1tka.render.hud.HudVisibilityState.a();
                  }

                  int intValue30 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(20.0F * floatValue7));
                  a(matrixStack1, floatValue20, floatValue21, floatValue26, 13.0F, 3.0F, intValue30, floatValue28, floatValue7);
                  if (value11 != null) {
                     float floatValue31 = value11.a(booleanSetting25.getDisplayName());
                     a(matrixStack1, value11, booleanSetting25.getDisplayName(), floatValue20 + (floatValue26 - floatValue31) / 2.0F, floatValue21 + 2.8F, a(floatValue28, floatValue7));
                  }
               }

               floatValue20 += floatValue26 + 3.0F;
               intValue23++;
            }
         }
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

      float floatValue7 = this.b + 4.0F;
      float floatValue8 = this.c + 15.0F;
      boolean flag9 = true;

      for (BooleanSetting booleanSetting11 : this.f.e()) {
         if (booleanSetting11.isVisible()) {
            float floatValue12 = this.a(value6, booleanSetting11.getDisplayName());
            if (!flag9 && floatValue7 + floatValue12 > this.b + this.d - 4.0F) {
               floatValue7 = this.b + 4.0F;
               floatValue8 += 16.0F;
            }

            flag9 = false;
            if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)floatValue7, (double)floatValue8, (double)floatValue12, 13.0) && intValue5 == 0) {
               booleanSetting11.setValue(!booleanSetting11.getValue());
               return;
            }

            floatValue7 += floatValue12 + 3.0F;
         }
      }
   }
}
