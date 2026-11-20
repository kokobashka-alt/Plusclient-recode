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
import net.minecraft.util.math.RotationAxis;

public class PanelLabel {
   private float c;
   private float d;
   private float e;
   private float f;
   public String a = "";
   private boolean g;
   private final String h;
   public float b = 1.0F;
   private static float i;
   private static float j;

   public PanelLabel(String string1) {
      this.h = string1;
   }

   public void a(float floatValue1, float floatValue2, float floatValue3, float floatValue4) {
      this.c = floatValue1;
      this.d = floatValue2;
      this.e = floatValue3;
      this.f = floatValue4;
   }

   public void a(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4) {
      if (!(this.b < 0.02F)) {
         if (dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue2, (double)intValue3, (double)this.c, (double)this.d, (double)this.e, (double)this.f)) {
            dev.leg1tka.render.hud.HudVisibilityState.b();
         }

         int intValue5 = Math.max(1, dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * this.b));
         dev.leg1tka.render.RenderPrimitives.a(matrixStack1, this.c, this.d + 2.0F, this.e, this.f, 6.0F, 12.0F, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, Math.max(1, dev.leg1tka.gui.themes.ThemeAccentResolver.a(120.0F * this.b))));
         dev.leg1tka.render.RenderPrimitives.e(matrixStack1, this.c, this.d, this.e, this.f, 6.0F, 20.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue5));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, this.c, this.d, this.e, this.f, 6.0F, dev.leg1tka.api.utils.text.ColorUtils.a(12, 12, 15, dev.leg1tka.gui.themes.ThemeAccentResolver.a(190.0F * this.b)));
         if (this.g) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, this.c + 4.0F, this.d + this.f - 1.0F, this.e - 8.0F, 1.0F, 0.5F, dev.leg1tka.gui.themes.ThemeAccentResolver.a(dev.leg1tka.gui.themes.ThemeAccentResolver.a(), this.b));
         }

         int intValue6 = dev.leg1tka.gui.themes.ThemeAccentResolver.a(dev.leg1tka.gui.themes.ThemeAccentResolver.a(), this.b * (this.g ? 1.0F : 0.85F));
         float floatValue7 = this.c + 8.5F + 1.0F;
         float floatValue8 = this.d + this.f / 2.0F;
         float floatValue9 = 7.0F;
         dev.leg1tka.render.RenderPrimitives.d(matrixStack1, floatValue7 - floatValue9 / 2.0F - 0.8F, floatValue8 - floatValue9 / 2.0F - 0.8F, floatValue9, 1.3F, 0.0F, 360.0F, intValue6);
         matrixStack1.push();
         matrixStack1.translate(floatValue7 + 2.3F, floatValue8 + 2.3F, 0.0F);
         matrixStack1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45.0F));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, -1.6F, -0.7F, 3.2F, 1.4F, 0.7F, intValue6);
         matrixStack1.pop();
         dev.leg1tka.render.font.MsdfTextRenderer value10 = dev.leg1tka.render.font.FontCache.a("sf_regular", 14);
         String string11 = this.a.isEmpty() && !this.g ? this.h : this.a;
         String string12 = this.g && System.currentTimeMillis() % 1000L > 500L ? "|" : "";
         int intValue13 = this.a.isEmpty() && !this.g ? dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.api.utils.text.ColorUtils.g(), dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * this.b)) : dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.api.utils.text.ColorUtils.f(), dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * this.b));
         dev.leg1tka.gui.components.SettingWidget.a(matrixStack1, value10, string11 + string12, this.c + 17.0F + 2.0F, this.d + this.f / 2.0F - 3.5F, intValue13);
      }
   }

   public void a(char character1, int intValue2) {
      if (this.g && this.a.length() < 15) {
         this.a = this.a + character1;
      }
   }

   public void a(int intValue1, int intValue2, int intValue3) {
      if (this.g) {
         if (intValue1 == 259 && !this.a.isEmpty()) {
            this.a = this.a.substring(0, this.a.length() - 1);
         }

         if (intValue1 == 257 || intValue1 == 256) {
            this.g = false;
         }
      }
   }

   public void a(double doubleValue1, double doubleValue3, int intValue5) {
      this.g = dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)this.c, (double)this.d, (double)this.e, (double)this.f);
   }

   public boolean a() {
      return this.a.isEmpty();
   }

   public String b() {
      return this.a;
   }
}
