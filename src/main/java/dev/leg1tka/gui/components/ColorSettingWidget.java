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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class ColorSettingWidget extends dev.leg1tka.gui.components.SettingWidget {
   private final ColorSetting f;
   private boolean g;
   private final dev.leg1tka.api.animation.TimedAnimation h = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.n, 300L);
   private boolean i;
   private boolean j;
   private float k;
   private float l;
   private float m;

   public ColorSettingWidget(ColorSetting colorSetting1) {
      this.f = colorSetting1;
      this.b();
   }

   private void b() {
      Color color1 = new Color(this.f.getColor(), true);
      float[] floatValueArray2 = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
      this.k = floatValueArray2[0];
      this.l = floatValueArray2[1];
      this.m = floatValueArray2[2];
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
         this.h.a(this.g);
         float floatValue9 = 55.0F;
         dev.leg1tka.render.font.MsdfTextRenderer value10 = dev.leg1tka.render.font.FontCache.a("bold", 14);
         if (value10 == null) {
            value10 = dev.leg1tka.render.font.FontCache.a("semibold", 14);
         }

         if (value10 == null) {
            value10 = dev.leg1tka.render.font.FontCache.a("sf_regular", 14);
         }

         float floatValue11 = 8.0F;
         float floatValue12 = this.b + this.d - floatValue11 - 5.0F;
         float floatValue13 = this.c + 2.5F;
         float floatValue14 = floatValue12 - (this.b + 4.5F) - 2.0F;
         a(matrixStack1, value10, this.f.getDisplayName(), this.b + 4.5F, this.c + 2.5F, floatValue14, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue8));
         if (dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue2, (double)intValue3, (double)floatValue12, (double)floatValue13, (double)floatValue11, (double)floatValue11)) {
            dev.leg1tka.render.hud.HudVisibilityState.a();
         }

         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue12 - 0.5F, floatValue13 - 0.5F, floatValue11 + 1.0F, floatValue11 + 1.0F, 1.5F, dev.leg1tka.api.utils.text.ColorUtils.a(60, 60, 65, dev.leg1tka.gui.themes.ThemeAccentResolver.a(150.0F * floatValue7)));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue12, floatValue13, floatValue11, floatValue11, 1.5F, this.f.getColor());
         if (this.h.d() > 0.01F) {
            float floatValue15 = this.c + 13.0F;
            float floatValue16 = this.h.d() * floatValue9;
            float floatValue17 = floatValue7 * this.h.d();
            int intValue18 = dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue17);
            dev.leg1tka.render.ScissorStack.a();
            dev.leg1tka.render.ScissorStack.a((double)this.b, (double)floatValue15, (double)this.d, (double)floatValue16);
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, this.b + 4.5F, floatValue15 - 0.5F, this.d - 9.0F, floatValue9 + 1.0F, 2.5F, dev.leg1tka.api.utils.text.ColorUtils.a(60, 60, 65, dev.leg1tka.gui.themes.ThemeAccentResolver.a(150.0F * floatValue17)));
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, this.b + 5.0F, floatValue15, this.d - 10.0F, floatValue9, 2.5F, dev.leg1tka.api.utils.text.ColorUtils.a(20, 20, 25, intValue18));
            float floatValue19 = this.b + 9.0F;
            float floatValue20 = floatValue15 + 4.0F;
            float floatValue21 = 40.0F;
            int intValue22 = dev.leg1tka.api.utils.text.ColorUtils.d(Color.HSBtoRGB(this.k, 1.0F, 1.0F), intValue18);
            int intValue23 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue18);
            int intValue24 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 0);
            int intValue25 = dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, intValue18);
            int intValue26 = dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, 0);
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue19, floatValue20, floatValue21, floatValue21, 2.0F, intValue22);
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue19, floatValue20, floatValue21, floatValue21, 2.0F, intValue23, intValue24, intValue23, intValue24);
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue19, floatValue20, floatValue21, floatValue21, 2.0F, intValue26, intValue26, intValue25, intValue25);
            float floatValue27 = floatValue19 + this.l * floatValue21;
            float floatValue28 = floatValue20 + (1.0F - this.m) * floatValue21;
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue27 - 2.5F, floatValue28 - 2.5F, 5.0F, 5.0F, 2.5F, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, dev.leg1tka.gui.themes.ThemeAccentResolver.a(180.0F * floatValue17)));
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue27 - 1.5F, floatValue28 - 1.5F, 3.0F, 3.0F, 1.5F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue18));
            float floatValue29 = floatValue19 + floatValue21 + 6.0F;
            float floatValue30 = floatValue20;
            float floatValue31 = 6.0F;

            for (float floatValue32 = 0.0F; floatValue32 <= floatValue21; floatValue32 += 0.5F) {
               int intValue33 = dev.leg1tka.api.utils.text.ColorUtils.d(Color.HSBtoRGB(floatValue32 / floatValue21, 1.0F, 1.0F), intValue18);
               dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue29, floatValue30 + floatValue32, floatValue31, 1.0F, 0.0F, intValue33);
            }

            float floatValue34 = floatValue30 + this.k * floatValue21;
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue29 - 1.5F, floatValue34 - 2.5F, floatValue31 + 3.0F, 5.0F, 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, dev.leg1tka.gui.themes.ThemeAccentResolver.a(180.0F * floatValue17)));
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue29 - 0.5F, floatValue34 - 1.5F, floatValue31 + 1.0F, 3.0F, 1.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue18));
            if (this.i) {
               this.l = Math.max(0.0F, Math.min(1.0F, ((float)intValue2 - floatValue19) / floatValue21));
               this.m = 1.0F - Math.max(0.0F, Math.min(1.0F, ((float)intValue3 - floatValue20) / floatValue21));
               this.k();
            } else if (this.j) {
               this.k = Math.max(0.0F, Math.min(1.0F, ((float)intValue3 - floatValue30) / floatValue21));
               this.k();
            }

            dev.leg1tka.render.ScissorStack.b();
         }

         this.e = 13.0F + this.h.d() * floatValue9;
      }
   }

   private void k() {
      int intValue1 = Color.HSBtoRGB(this.k, this.l, this.m);
      this.f.setColor(intValue1 | 0xFF000000);
   }

   @Override
   public void a(double doubleValue1, double doubleValue3, int intValue5) {
      float floatValue6 = 8.0F;
      float floatValue7 = this.b + this.d - floatValue6 - 5.0F;
      float floatValue8 = this.c + 2.5F;
      if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)floatValue7, (double)floatValue8, (double)floatValue6, (double)floatValue6) && intValue5 == 1) {
         this.g = !this.g;
      } else {
         if (this.g && intValue5 == 0) {
            float floatValue9 = this.b + 9.0F;
            float floatValue10 = this.c + 13.0F + 4.0F;
            float floatValue11 = 40.0F;
            float floatValue12 = floatValue9 + floatValue11 + 6.0F;
            if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)floatValue9, (double)floatValue10, (double)floatValue11, (double)floatValue11)) {
               this.i = true;
            } else if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)(floatValue12 - 2.0F), (double)floatValue10, 10.0, (double)floatValue11)) {
               this.j = true;
            }
         }
      }
   }

   @Override
   public void b(double doubleValue1, double doubleValue3, int intValue5) {
      this.i = false;
      this.j = false;
   }
}
