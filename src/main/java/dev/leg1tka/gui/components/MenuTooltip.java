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

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

public class MenuTooltip {
   private boolean a = false;
   private float b = 0.0F;
   private float c = 0.0F;
   private final float[] d = new float[dev.leg1tka.gui.menu.WallpaperSettings.b.length];
   private long e = 0L;

   public MenuTooltip() {
   }

   public void a(DrawContext drawContext1, int intValue2, int intValue3, float floatValue4, float floatValue5) {
      float floatValue6 = dev.leg1tka.gui.menu.MenuNavigation.b();
      if (!(floatValue6 < 0.02F)) {
         long longValue7 = System.nanoTime();
         float floatValue9 = this.e == 0L ? 0.016F : Math.min(0.05F, (float)((double)(longValue7 - this.e) / 1.0E9));
         this.e = longValue7;
         float floatValue10 = 20.0F;
         float floatValue11 = floatValue5 - 40.0F;
         float floatValue12 = 152.0F;
         float floatValue13 = 26.0F;
         boolean flag14 = (float)intValue2 >= floatValue10 && (float)intValue2 <= floatValue10 + floatValue12 && (float)intValue3 >= floatValue11 && (float)intValue3 <= floatValue11 + floatValue13;
         if (flag14) {
            this.b = Math.min(1.0F, this.b + floatValue9 * 10.0F);
         } else {
            this.b = Math.max(0.0F, this.b - floatValue9 * 10.0F);
         }

         if (this.a) {
            this.c = Math.min(1.0F, this.c + floatValue9 * 16.0F);
         } else {
            this.c = Math.max(0.0F, this.c - floatValue9 * 16.0F);
         }

         MatrixStack matrixStack15 = drawContext1.getMatrices();
         float floatValue16 = (1.0F - floatValue6) * 10.0F;
         matrixStack15.push();
         matrixStack15.translate(0.0F, floatValue16, 0.0F);
         int intValue17 = 16 + (int)(this.b * 18.0F);
         int intValue18 = 18 + (int)(this.b * 18.0F);
         int intValue19 = 24 + (int)(this.b * 22.0F);
         int intValue20 = Math.max(1, (int)((60.0F + this.b * 46.0F) * floatValue6));
         int intValue21 = intValue20 << 24 | intValue17 << 16 | intValue18 << 8 | intValue19;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack15, floatValue10, floatValue11, floatValue12, floatValue13, 7.0F, intValue21);
         int intValue22 = Math.max(1, (int)((22.0F + this.b * 34.0F) * floatValue6));
         int intValue23 = intValue22 << 24 | 16777215;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack15, floatValue10 + 2.0F, floatValue11 + 1.0F, floatValue12 - 4.0F, 1.0F, 1.0F, intValue23);
         int intValue24 = Math.max(1, (int)((24.0F + this.b * 22.0F) * floatValue6));
         int intValue25 = intValue24 << 24 | 16777215;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack15, floatValue10, floatValue11, floatValue12, floatValue13, 7.0F, 0.75F, intValue25, intValue25, intValue25, intValue25);
         dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme backgroundTheme26 = dev.leg1tka.gui.menu.WallpaperSettings.a.a();
         int intValue27 = Math.max(1, (int)(floatValue6 * 220.0F));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack15, floatValue10 + 7.0F, floatValue11 + 7.0F, 16.0F, 12.0F, 3.0F, intValue27 << 24 | backgroundTheme26.e() & 16777215, intValue27 << 24 | backgroundTheme26.f() & 16777215);
         int intValue28 = Math.max(1, (int)(floatValue6 * 45.0F)) << 24 | 16777215;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack15, floatValue10 + 7.0F, floatValue11 + 7.0F, 16.0F, 12.0F, 3.0F, 0.5F, intValue28, intValue28, intValue28, intValue28);
         dev.leg1tka.render.font.MsdfTextRenderer value29 = dev.leg1tka.render.font.FontCache.a("nunito", 13);
         int intValue30 = !flag14 && !(this.b > 0.25F) ? Math.max(1, (int)(floatValue6 * 215.0F)) << 24 | 13948640 : Math.max(1, (int)(floatValue6 * 255.0F)) << 24 | 16777215;
         if (value29 != null) {
            float floatValue31 = floatValue11 + 13.0F - 2.457F;
            value29.a(matrixStack15, backgroundTheme26.b(), floatValue10 + 29.0F, floatValue31, intValue30);
         }

         float floatValue63 = floatValue10 + floatValue12 - 11.0F;
         float floatValue32 = floatValue11 + 13.0F;
         int intValue33 = Math.max(1, (int)(floatValue6 * (150.0F + this.b * 105.0F))) << 24 | 16777215;
         matrixStack15.push();
         matrixStack15.translate(floatValue63, floatValue32, 0.0F);
         matrixStack15.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(this.c * 180.0F));
         matrixStack15.push();
         matrixStack15.translate(-1.4F, 0.0F, 0.0F);
         matrixStack15.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45.0F));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack15, -0.6F, -2.0F, 1.2F, 4.0F, 0.6F, intValue33);
         matrixStack15.pop();
         matrixStack15.push();
         matrixStack15.translate(1.4F, 0.0F, 0.0F);
         matrixStack15.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-45.0F));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack15, -0.6F, -2.0F, 1.2F, 4.0F, 0.6F, intValue33);
         matrixStack15.pop();
         matrixStack15.pop();
         if (this.c > 0.01F) {
            dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme[] backgroundThemeArray34 = dev.leg1tka.gui.menu.WallpaperSettings.b;
            float floatValue35 = 168.0F;
            float floatValue36 = 25.0F;
            float floatValue37 = 24.0F + (float)backgroundThemeArray34.length * 27.0F + 5.0F;
            float floatValue39 = floatValue11 - floatValue37 - 6.0F;
            float floatValue40 = this.c * floatValue6;
            matrixStack15.push();
            float floatValue41 = floatValue10 + floatValue35 * 0.5F;
            float floatValue42 = floatValue39 + floatValue37;
            matrixStack15.translate(floatValue41, floatValue42, 0.0F);
            float floatValue43 = 0.94F + this.c * 0.06F;
            matrixStack15.scale(floatValue43, floatValue43, 1.0F);
            matrixStack15.translate(-floatValue41, -floatValue42, 0.0F);
            int intValue44 = Math.max(1, (int)(floatValue40 * 75.0F)) << 24;
            dev.leg1tka.render.RenderPrimitives.a(matrixStack15, floatValue10, floatValue39 + 4.0F, floatValue35, floatValue37, 11.0F, 22.0F, intValue44);
            dev.leg1tka.render.RenderPrimitives.a(matrixStack15, floatValue10, floatValue39 + 2.0F, floatValue35, floatValue37, 11.0F, 8.0F, Math.max(1, (int)(floatValue40 * 50.0F)) << 24);
            int intValue45 = Math.max(1, (int)(floatValue40 * 195.0F)) << 24 | 1184796;
            dev.leg1tka.render.RenderPrimitives.b(matrixStack15, floatValue10, floatValue39, floatValue35, floatValue37, 11.0F, intValue45);
            int intValue46 = Math.max(1, (int)(floatValue40 * 36.0F));
            dev.leg1tka.render.RenderPrimitives.b(matrixStack15, floatValue10 + 2.0F, floatValue39 + 1.0F, floatValue35 - 4.0F, 1.0F, 1.0F, intValue46 << 24 | 16777215);
            int intValue47 = Math.max(1, (int)(floatValue40 * 26.0F));
            int intValue48 = intValue47 << 24 | 16777215;
            dev.leg1tka.render.RenderPrimitives.b(matrixStack15, floatValue10, floatValue39, floatValue35, floatValue37, 11.0F, 0.75F, intValue48, intValue48, intValue48, intValue48);
            dev.leg1tka.render.font.MsdfTextRenderer value49 = dev.leg1tka.render.font.FontCache.a("bold", 10);
            if (value49 != null) {
               int intValue50 = Math.max(1, (int)(floatValue40 * 145.0F)) << 24 | 10001320;
               value49.a(matrixStack15, "ОБОИ", floatValue10 + 10.0F, floatValue39 + 7.0F, intValue50);
            }

            int intValue64 = Math.max(1, (int)(floatValue40 * 20.0F));
            dev.leg1tka.render.RenderPrimitives.b(matrixStack15, floatValue10 + 8.0F, floatValue39 + 19.0F, floatValue35 - 16.0F, 0.6F, 0.3F, intValue64 << 24 | 16777215);
            float floatValue51 = floatValue10 + 4.0F;
            float floatValue52 = floatValue35 - 8.0F;

            for (int intValue53 = 0; intValue53 < backgroundThemeArray34.length; intValue53++) {
               dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme backgroundTheme54 = backgroundThemeArray34[intValue53];
               float floatValue55 = floatValue39 + 24.0F + (float)intValue53 * 27.0F;
               float floatValue56 = floatValue55 + floatValue36 * 0.5F;
               boolean flag57 = this.a && (float)intValue2 >= floatValue51 && (float)intValue2 <= floatValue51 + floatValue52 && (float)intValue3 >= floatValue55 && (float)intValue3 <= floatValue55 + floatValue36;
               if (flag57) {
                  this.d[intValue53] = Math.min(1.0F, this.d[intValue53] + floatValue9 * 14.0F);
               } else {
                  this.d[intValue53] = Math.max(0.0F, this.d[intValue53] - floatValue9 * 14.0F);
               }

               boolean flag58 = backgroundTheme54 == backgroundTheme26;
               if (flag58) {
                  int intValue59 = Math.max(1, (int)(floatValue40 * (32.0F + this.d[intValue53] * 14.0F))) << 24 | 16777215;
                  dev.leg1tka.render.RenderPrimitives.b(matrixStack15, floatValue51, floatValue55, floatValue52, floatValue36, 6.0F, intValue59);
                  int intValue60 = Math.max(1, (int)(floatValue40 * 24.0F)) << 24 | 16777215;
                  dev.leg1tka.render.RenderPrimitives.b(matrixStack15, floatValue51 + 2.0F, floatValue55 + 1.0F, floatValue52 - 4.0F, 1.0F, 0.5F, intValue60);
               } else if (this.d[intValue53] > 0.01F) {
                  int intValue65 = Math.max(1, (int)(floatValue40 * this.d[intValue53] * 22.0F)) << 24 | 16777215;
                  dev.leg1tka.render.RenderPrimitives.b(matrixStack15, floatValue51, floatValue55, floatValue52, floatValue36, 6.0F, intValue65);
               }

               int intValue66 = Math.max(1, (int)(floatValue40 * 225.0F));
               dev.leg1tka.render.RenderPrimitives.b(matrixStack15, floatValue51 + 7.0F, floatValue56 - 6.0F, 18.0F, 12.0F, 3.5F, intValue66 << 24 | backgroundTheme54.e() & 16777215, intValue66 << 24 | backgroundTheme54.f() & 16777215);
               int intValue67 = Math.max(1, (int)(floatValue40 * 45.0F)) << 24 | 16777215;
               dev.leg1tka.render.RenderPrimitives.b(matrixStack15, floatValue51 + 7.0F, floatValue56 - 6.0F, 18.0F, 12.0F, 3.5F, 0.5F, intValue67, intValue67, intValue67, intValue67);
               if (value29 != null) {
                  int intValue61 = flag58
                     ? Math.max(1, (int)(floatValue40 * 255.0F)) << 24 | 16777215
                     : Math.max(1, (int)(floatValue40 * (165.0F + this.d[intValue53] * 90.0F))) << 24 | 13817054;
                  float floatValue62 = floatValue56 - 2.457F;
                  value29.a(matrixStack15, backgroundTheme54.b(), floatValue51 + 31.0F, floatValue62, intValue61);
               }

               if (flag58) {
                  float floatValue68 = floatValue51 + floatValue52 - 12.0F;
                  int intValue69 = Math.max(1, (int)(floatValue40 * 255.0F)) << 24 | 16777215;
                  matrixStack15.push();
                  matrixStack15.translate(floatValue68, floatValue56, 0.0F);
                  matrixStack15.push();
                  matrixStack15.translate(-2.0F, 0.5F, 0.0F);
                  matrixStack15.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-45.0F));
                  dev.leg1tka.render.RenderPrimitives.b(matrixStack15, -0.6F, -1.5F, 1.2F, 3.0F, 0.6F, intValue69);
                  matrixStack15.pop();
                  matrixStack15.push();
                  matrixStack15.translate(1.0F, -0.5F, 0.0F);
                  matrixStack15.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(45.0F));
                  dev.leg1tka.render.RenderPrimitives.b(matrixStack15, -0.6F, -2.8F, 1.2F, 5.6F, 0.6F, intValue69);
                  matrixStack15.pop();
                  matrixStack15.pop();
               }
            }

            matrixStack15.pop();
         }

         matrixStack15.pop();
      }
   }

   public boolean a(double doubleValue1, double doubleValue3, int intValue5, float floatValue6) {
      if (intValue5 != 0) {
         return false;
      } else {
         float floatValue7 = 20.0F;
         float floatValue8 = floatValue6 - 40.0F;
         float floatValue9 = 152.0F;
         float floatValue10 = 26.0F;
         dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme[] backgroundThemeArray11 = dev.leg1tka.gui.menu.WallpaperSettings.b;
         float floatValue12 = 168.0F;
         float floatValue13 = 25.0F;
         float floatValue14 = 24.0F + (float)backgroundThemeArray11.length * 27.0F + 5.0F;
         float floatValue16 = floatValue8 - floatValue14 - 6.0F;
         if (this.a) {
            float floatValue17 = floatValue7 + 4.0F;
            float floatValue18 = floatValue12 - 8.0F;

            for (int intValue19 = 0; intValue19 < backgroundThemeArray11.length; intValue19++) {
               float floatValue20 = floatValue16 + 24.0F + (float)intValue19 * 27.0F;
               if (doubleValue1 >= (double)floatValue17 && doubleValue1 <= (double)(floatValue17 + floatValue18) && doubleValue3 >= (double)floatValue20 && doubleValue3 <= (double)(floatValue20 + floatValue13)) {
                  dev.leg1tka.gui.menu.WallpaperSettings.a.a(backgroundThemeArray11[intValue19]);
                  this.a = false;
                  return true;
               }
            }

            if (doubleValue1 >= (double)floatValue7 && doubleValue1 <= (double)(floatValue7 + floatValue9) && doubleValue3 >= (double)floatValue8 && doubleValue3 <= (double)(floatValue8 + floatValue10)) {
               this.a = false;
               return true;
            } else {
               this.a = false;
               return true;
            }
         } else if (doubleValue1 >= (double)floatValue7 && doubleValue1 <= (double)(floatValue7 + floatValue9) && doubleValue3 >= (double)floatValue8 && doubleValue3 <= (double)(floatValue8 + floatValue10)) {
            this.a = true;
            return true;
         } else {
            return false;
         }
      }
   }
}
