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

public class MultiSettingWidget extends dev.leg1tka.gui.components.SettingWidget {
   private final ModeSetting f;
   private final List g = new ArrayList();

   public MultiSettingWidget(ModeSetting modeSetting1) {
      this.f = modeSetting1;

      for (int intValue2 = 0; intValue2 < modeSetting1.f().size(); intValue2++) {
         this.g.add(new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.n, 250L));
      }
   }

   private float a(dev.leg1tka.render.font.MsdfTextRenderer value1, String string2) {
      String string3 = this.f.c(string2);
      return (value1 == null ? (float)string3.length() * 5.5F : value1.a(string3)) + 9.0F;
   }

   private float a(dev.leg1tka.render.font.MsdfTextRenderer value1) {
      float floatValue2 = this.d - 8.0F;
      float floatValue3 = 0.0F;
      float floatValue4 = 16.0F;
      boolean flag5 = true;

      for (String string7 : this.f.f()) {
         float floatValue8 = this.a(value1, string7);
         if (!flag5 && floatValue3 + floatValue8 > floatValue2) {
            floatValue4 += 16.0F;
            floatValue3 = floatValue8 + 3.0F;
         } else {
            floatValue3 += floatValue8 + 3.0F;
            flag5 = false;
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
            boolean flag26 = true;
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
         a(matrixStack1, value10, this.f.getDisplayName(), this.b + 4.0F, this.c + 2.0F, this.d - 8.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue8));
         float floatValue13 = this.b + 4.0F;
         float floatValue14 = this.c + 15.0F;
         boolean flag15 = true;
         int intValue16 = 0;

         for (String string18 : this.f.f()) {
            float floatValue19 = this.a(value11, string18);
            if (!flag15 && floatValue13 + floatValue19 > this.b + this.d - 4.0F) {
               floatValue13 = this.b + 4.0F;
               floatValue14 += 16.0F;
            }

            flag15 = false;
            if (intValue16 < this.g.size()) {
               dev.leg1tka.api.animation.TimedAnimation value20 = (dev.leg1tka.api.animation.TimedAnimation)this.g.get(intValue16);
               value20.a(this.f.b(string18));
               float floatValue21 = value20.d();
               boolean flag22 = dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue2, (double)intValue3, (double)floatValue13, (double)floatValue14, (double)floatValue19, 13.0);
               if (flag22) {
                  dev.leg1tka.render.hud.HudVisibilityState.a();
               }

               int intValue23 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(20.0F * floatValue7));
               a(matrixStack1, floatValue13, floatValue14, floatValue19, 13.0F, 3.0F, intValue23, floatValue21, floatValue7);
               if (value11 != null) {
                  String string24 = this.f.c(string18);
                  float floatValue25 = value11.a(string24);
                  a(matrixStack1, value11, string24, floatValue13 + (floatValue19 - floatValue25) / 2.0F, floatValue14 + 2.8F, a(floatValue21, floatValue7));
               }
            }

            floatValue13 += floatValue19 + 3.0F;
            intValue16++;
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

      for (String string11 : this.f.f()) {
         float floatValue12 = this.a(value6, string11);
         if (!flag9 && floatValue7 + floatValue12 > this.b + this.d - 4.0F) {
            floatValue7 = this.b + 4.0F;
            floatValue8 += 16.0F;
         }

         flag9 = false;
         if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)floatValue7, (double)floatValue8, (double)floatValue12, 13.0) && intValue5 == 0) {
            this.f.a(string11);
            return;
         }

         floatValue7 += floatValue12 + 3.0F;
      }
   }
}
