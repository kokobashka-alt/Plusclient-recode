package dev.leg1tka.gui.menu;

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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class MenuBackgroundCarousel {
   private static float a;
   private boolean b = false;
   private float c = 0.0F;
   private float d = 0.0F;
   private int e = 0;
   private dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme f = null;
   private long g = 0L;
   private float h = 0.0F;
   private float i = 0.0F;
   private float j = 0.0F;
   private float k = 0.0F;
   private float l = 0.0F;
   private float m = 0.0F;
   private float n = dev.leg1tka.gui.menu.MenuPreferences.a() ? 1.0F : 0.0F;
   private float o;
   private float p;
   private float q;
   private float r;
   private float s;
   private float t;
   private float u;
   private float v;
   private float w;
   private float x;
   private float y;
   private float z;
   private float A;
   private float B;
   private float C;
   private float D;
   private float E;
   private float F;
   private float G;

   public MenuBackgroundCarousel() {
   }

   public boolean a() {
      return this.b;
   }

   public float b() {
      return this.c;
   }

   public void c() {
      if (!this.b) {
         this.b = true;
         this.f = dev.leg1tka.gui.menu.WallpaperSettings.a.a();
         dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme[] backgroundThemeArray1 = dev.leg1tka.gui.menu.WallpaperSettings.b;
         int intValue2 = 0;

         for (int intValue3 = 0; intValue3 < backgroundThemeArray1.length; intValue3++) {
            if (backgroundThemeArray1[intValue3] == this.f) {
               intValue2 = intValue3;
            }
         }

         this.e = intValue2;
         this.d = (float)intValue2;
         dev.leg1tka.gui.menu.MenuThumbnailCache.a();
      }
   }

   private void d() {
      this.b = false;
      dev.leg1tka.gui.menu.MenuScreenRouting.c.a(null);
   }

   private void e() {
      this.b = false;
      dev.leg1tka.gui.menu.WallpaperSettings.a.a(this.f());
      dev.leg1tka.gui.menu.MenuScreenRouting.c.a(null);
   }

   private dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme f() {
      dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme[] backgroundThemeArray1 = dev.leg1tka.gui.menu.WallpaperSettings.b;
      return backgroundThemeArray1[Math.floorMod(this.e, backgroundThemeArray1.length)];
   }

   private void b(int intValue1) {
      this.e += intValue1;
      dev.leg1tka.gui.menu.MenuScreenRouting.c.a(this.f().c());
   }

   private void a(float floatValue1, float floatValue2) {
      this.q = MathHelper.clamp(floatValue1 * 0.24F, dev.leg1tka.gui.menu.MenuLayout.a(200.0F), dev.leg1tka.gui.menu.MenuLayout.a(460.0F));
      this.r = this.q * 0.5625F;
      this.s = this.q * 0.5F + this.q * 0.8F * 0.5F + dev.leg1tka.gui.menu.MenuLayout.a(22.0F);
      this.o = floatValue1 * 0.5F;
      this.p = floatValue2 * 0.46F;
      this.w = dev.leg1tka.gui.menu.MenuLayout.a(38.0F);
      float floatValue3 = this.s + this.q * 0.8F * 0.5F + dev.leg1tka.gui.menu.MenuLayout.a(18.0F) + this.w * 0.5F;
      this.t = Math.max(this.w * 0.5F + dev.leg1tka.gui.menu.MenuLayout.a(12.0F), this.o - floatValue3);
      this.u = Math.min(floatValue1 - this.w * 0.5F - dev.leg1tka.gui.menu.MenuLayout.a(12.0F), this.o + floatValue3);
      this.v = this.p;
      this.y = dev.leg1tka.gui.menu.MenuLayout.a(34.0F);
      this.x = this.p + this.r * 0.5F + dev.leg1tka.gui.menu.MenuLayout.a(78.0F);
      this.A = dev.leg1tka.gui.menu.MenuLayout.a(112.0F);
      this.C = dev.leg1tka.gui.menu.MenuLayout.a(136.0F);
      float floatValue4 = dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
      this.z = dev.leg1tka.gui.menu.MenuLayout.b(this.o - (this.A + floatValue4 + this.C) * 0.5F);
      this.B = this.z + this.A + floatValue4;
      this.F = dev.leg1tka.gui.menu.MenuLayout.a(320.0F);
      this.G = dev.leg1tka.gui.menu.MenuLayout.a(52.0F);
      this.D = dev.leg1tka.gui.menu.MenuLayout.b(this.o - this.F * 0.5F);
      this.E = dev.leg1tka.gui.menu.MenuLayout.b(this.x + this.y + dev.leg1tka.gui.menu.MenuLayout.a(34.0F));
   }

   public void a(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4, float floatValue5) {
      long longValue6 = System.nanoTime();
      float floatValue8 = this.g == 0L ? 0.016F : Math.min(0.05F, (float)((double)(longValue6 - this.g) / 1.0E9));
      this.g = longValue6;
      this.c = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.c, this.b ? 1.0F : 0.0F, floatValue8, this.b ? 9.0F : 12.0F);
      if (this.c < 0.003F) {
         this.c = 0.0F;
      } else {
         this.d = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.d, (float)this.e, floatValue8, 11.0F);
         if (Math.abs(this.d - (float)this.e) < 5.0E-4F) {
            this.d = (float)this.e;
         }

         this.a(floatValue4, floatValue5);
         float floatValue9 = dev.leg1tka.gui.menu.MenuBackdropRenderer.b(this.c);
         float floatValue10 = floatValue9 * dev.leg1tka.gui.menu.MenuNavigation.b();
         int intValue11 = dev.leg1tka.gui.menu.MenuBackdropRenderer.c();
         int intValue12 = dev.leg1tka.gui.menu.MenuBackdropRenderer.d();
         float floatValue13 = dev.leg1tka.gui.menu.MenuBackdropRenderer.g();
         dev.leg1tka.render.RenderPrimitives.d(matrixStack1, 0.0F, 0.0F, floatValue4, floatValue5, 0.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue10, 16777215));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, 0.0F, 0.0F, floatValue4, floatValue5, 0.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.48F * floatValue10, 329226), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.72F * floatValue10, 329226));
         float floatValue14 = (1.0F - floatValue9) * dev.leg1tka.gui.menu.MenuLayout.a(18.0F);
         float floatValue15 = this.p - this.r * 0.5F;
         dev.leg1tka.gui.components.MenuTextRenderer.b(
            matrixStack1,
            "Выбери атмосферу меню",
            24.0F,
            this.o,
            floatValue15 - dev.leg1tka.gui.menu.MenuLayout.a(52.0F) + floatValue14,
            dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue10, 16777215)
         );
         dev.leg1tka.gui.components.MenuTextRenderer.b(
            matrixStack1,
            "Листай стрелками или колесом мыши",
            13.0F,
            this.o,
            floatValue15 - dev.leg1tka.gui.menu.MenuLayout.a(26.0F) + floatValue14,
            dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.58F * floatValue10, 16777215)
         );
         dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme[] backgroundThemeArray16 = dev.leg1tka.gui.menu.WallpaperSettings.b;
         int intValue17 = backgroundThemeArray16.length;
         ArrayList<Integer> arrayList18 = new ArrayList<>();
         int intValue19 = MathHelper.floor(this.d);

         for (int intValue20 = intValue19 - 2; intValue20 <= intValue19 + 3; intValue20++) {
            if (Math.abs((float)intValue20 - this.d) <= 2.05F) {
               arrayList18.add(intValue20);
            }
         }

         arrayList18.sort((integer1x, integer2x) -> Float.compare(Math.abs((float)integer2x.intValue() - this.d), Math.abs((float)integer1x.intValue() - this.d)));
         float floatValue50 = this.q;
         float floatValue21 = this.r;
         boolean flag22 = this.b
            && a((double)intValue2, (double)intValue3, this.o - floatValue50 * 0.5F, this.p - floatValue21 * 0.5F, floatValue50, floatValue21)
            && Math.abs(this.d - (float)this.e) < 0.2F;
         this.l = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.l, flag22 ? 1.0F : 0.0F, floatValue8, 12.0F);

         for (int intValue24 : arrayList18) {
            float floatValue25 = (float)intValue24 - this.d;
            float floatValue26 = Math.abs(floatValue25);
            float floatValue27 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(1.0F - floatValue26);
            float floatValue28 = MathHelper.lerp(Math.min(1.0F, floatValue26), 1.0F, 0.8F);
            float floatValue29 = (floatValue26 <= 1.0F ? 1.0F : dev.leg1tka.gui.menu.MenuBackdropRenderer.a(1.0F - (floatValue26 - 1.0F) * 1.25F)) * floatValue10;
            if (!(floatValue29 < 0.01F)) {
               float floatValue30 = this.q * floatValue28;
               float floatValue31 = this.r * floatValue28;
               float floatValue32 = this.l * floatValue27 * dev.leg1tka.gui.menu.MenuLayout.a(4.0F);
               float floatValue33 = this.o + floatValue25 * this.s - floatValue30 * 0.5F;
               float floatValue34 = this.p - floatValue31 * 0.5F + floatValue14 * (0.6F + floatValue26 * 0.4F) - floatValue32;
               float floatValue35 = dev.leg1tka.gui.menu.MenuLayout.a(14.0F) * floatValue28;
               dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme backgroundTheme36 = backgroundThemeArray16[Math.floorMod(intValue24, intValue17)];
               dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue33, floatValue34 + dev.leg1tka.gui.menu.MenuLayout.a(10.0F), floatValue30, floatValue31, floatValue35, dev.leg1tka.gui.menu.MenuLayout.a(28.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.55F * floatValue29, 0));
               if (floatValue27 > 0.01F) {
                  float floatValue37 = dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
                  dev.leg1tka.render.RenderPrimitives.a(
                     matrixStack1,
                     floatValue33 + floatValue37,
                     floatValue34 + floatValue37,
                     floatValue30 - floatValue37 * 2.0F,
                     floatValue31 - floatValue37,
                     floatValue35,
                     dev.leg1tka.gui.menu.MenuLayout.a(24.0F + this.l * 8.0F),
                     dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.45F + this.l * 0.15F) * floatValue27 * floatValue29, intValue11)
                  );
               }

               float floatValue71 = MathHelper.lerp(floatValue27, 0.5F, 1.0F);
               int intValue38 = (int)(floatValue71 * 255.0F);
               int intValue39 = intValue38 << 16 | intValue38 << 8 | intValue38;
               Identifier identifier40 = dev.leg1tka.gui.menu.MenuThumbnailCache.a(backgroundTheme36);
               if (identifier40 != null) {
                  dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack1, identifier40, dev.leg1tka.gui.menu.WallpaperSettings.a.a(backgroundTheme36.c()), 1.0F, floatValue33, floatValue34, floatValue30, floatValue31, floatValue35, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue29, intValue39));
               } else {
                  dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue33, floatValue34, floatValue30, floatValue31, floatValue35, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue29 * floatValue71, backgroundTheme36.e()), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue29 * floatValue71, backgroundTheme36.f()), true);
               }

               dev.leg1tka.render.RenderPrimitives.b(
                  matrixStack1,
                  floatValue33,
                  floatValue34,
                  floatValue30,
                  floatValue31 * 0.45F,
                  floatValue35,
                  floatValue35,
                  0.0F,
                  0.0F,
                  dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.09F * floatValue29, 16777215),
                  dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.09F * floatValue29, 16777215),
                  dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.004F * floatValue29, 16777215),
                  dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.004F * floatValue29, 16777215)
               );
               int intValue41 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.16F * (1.0F - floatValue27) * floatValue29, 16777215);
               dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue33, floatValue34, floatValue30, floatValue31, floatValue35, floatValue13, intValue41, intValue41, intValue41, intValue41);
               if (floatValue27 > 0.01F) {
                  float floatValue42 = Math.max(dev.leg1tka.gui.menu.MenuLayout.c() * 2.0F, dev.leg1tka.gui.menu.MenuLayout.a(1.6F));
                  int intValue43 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue27 * floatValue29, intValue11);
                  int intValue44 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue27 * floatValue29, intValue12);
                  dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue33 - floatValue42, floatValue34 - floatValue42, floatValue30 + floatValue42 * 2.0F, floatValue31 + floatValue42 * 2.0F, floatValue35 + floatValue42, floatValue42, intValue43, intValue44, intValue43, intValue44);
               }

               if (backgroundTheme36 == this.f) {
                  String string77 = "СЕЙЧАС";
                  float floatValue80 = 9.5F;
                  float floatValue82 = dev.leg1tka.gui.menu.MenuLayout.a(20.0F) * floatValue28;
                  float floatValue45 = dev.leg1tka.gui.menu.MenuLayout.a(5.0F);
                  float floatValue46 = dev.leg1tka.gui.menu.MenuLayout.a(9.0F) + floatValue45 + dev.leg1tka.gui.menu.MenuLayout.a(6.0F) + dev.leg1tka.gui.components.MenuTextRenderer.a(floatValue80, string77, 1.2F) + dev.leg1tka.gui.menu.MenuLayout.a(10.0F);
                  float floatValue47 = floatValue33 + dev.leg1tka.gui.menu.MenuLayout.a(9.0F) * floatValue28;
                  float floatValue48 = floatValue34 + dev.leg1tka.gui.menu.MenuLayout.a(9.0F) * floatValue28;
                  float floatValue49 = floatValue48 + floatValue82 * 0.5F;
                  dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack1, floatValue47, floatValue48, floatValue46, floatValue82, floatValue82 * 0.5F, floatValue29);
                  dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue47 + dev.leg1tka.gui.menu.MenuLayout.a(9.0F) + floatValue45 * 0.5F, floatValue49, floatValue45, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue29, intValue11));
                  dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, string77, floatValue80, floatValue47 + dev.leg1tka.gui.menu.MenuLayout.a(9.0F) + floatValue45 + dev.leg1tka.gui.menu.MenuLayout.a(6.0F), floatValue49, 1.2F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue29, 16777215));
               }

               float floatValue78 = floatValue34 + floatValue31 + dev.leg1tka.gui.menu.MenuLayout.a(18.0F) + floatValue32;
               if (floatValue27 > 0.5F) {
                  dev.leg1tka.gui.components.MenuTextRenderer.b(matrixStack1, backgroundTheme36.b(), 15.0F, floatValue33 + floatValue30 * 0.5F, floatValue78, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue29 * (floatValue27 * 2.0F - 1.0F), 16777215));
               } else {
                  dev.leg1tka.gui.components.MenuTextRenderer.b(matrixStack1, backgroundTheme36.b(), 12.5F, floatValue33 + floatValue30 * 0.5F, floatValue78, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue29 * 0.65F * (1.0F - floatValue27 * 2.0F), 16777215));
               }
            }
         }

         float floatValue51 = this.p + this.r * 0.5F + dev.leg1tka.gui.menu.MenuLayout.a(46.0F) + floatValue14;
         float floatValue52 = dev.leg1tka.gui.menu.MenuLayout.a(14.0F);
         float floatValue53 = dev.leg1tka.gui.menu.MenuLayout.a(5.0F);
         float floatValue54 = dev.leg1tka.gui.menu.MenuLayout.a(18.0F);
         float floatValue55 = this.o - (float)(intValue17 - 1) * floatValue52 * 0.5F;
         float floatValue56 = (this.d % (float)intValue17 + (float)intValue17) % (float)intValue17;

         for (int intValue57 = 0; intValue57 < intValue17; intValue57++) {
            float floatValue59 = Math.abs((float)intValue57 - floatValue56);
            floatValue59 = Math.min(floatValue59, (float)intValue17 - floatValue59);
            float floatValue62 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(1.0F - floatValue59);
            float floatValue64 = MathHelper.lerp(floatValue62, floatValue53, floatValue54);
            float floatValue66 = floatValue55 + (float)intValue57 * floatValue52 - floatValue64 * 0.5F;
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue66, floatValue51 - floatValue53 * 0.5F, floatValue64, floatValue53, floatValue53 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.26F * floatValue10, 16777215));
            if (floatValue62 > 0.01F) {
               dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue66, floatValue51 - floatValue53 * 0.5F, floatValue64, floatValue53, floatValue53 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue62 * floatValue10, intValue11), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue62 * floatValue10, intValue12), true);
            }
         }

         boolean flag58 = this.b && a((double)intValue2, (double)intValue3, this.z, this.x, this.A, this.y);
         boolean flag61 = this.b && a((double)intValue2, (double)intValue3, this.B, this.x, this.C, this.y);
         this.j = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.j, flag58 ? 1.0F : 0.0F, floatValue8, 12.0F);
         this.k = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.k, flag61 ? 1.0F : 0.0F, floatValue8, 12.0F);
         float floatValue63 = this.x + floatValue14;
         float floatValue65 = floatValue63 + this.y * 0.5F;
         float floatValue67 = dev.leg1tka.gui.menu.MenuLayout.a(1.5F);
         float floatValue68 = Math.max(dev.leg1tka.gui.menu.MenuLayout.c() * 2.0F, dev.leg1tka.gui.menu.MenuLayout.a(1.6F));
         dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack1, this.z, floatValue63 - this.j * floatValue67, this.A, this.y, this.y * 0.5F, floatValue10, this.j);
         dev.leg1tka.gui.menu.MenuBackdropRenderer.b(matrixStack1, this.B, floatValue63 - this.k * floatValue67, this.C, this.y, floatValue10, this.k);
         float floatValue69 = 13.5F;
         float floatValue70 = dev.leg1tka.gui.menu.MenuLayout.a(10.0F);
         float floatValue72 = dev.leg1tka.gui.menu.MenuLayout.a(6.5F);
         float floatValue73 = dev.leg1tka.gui.components.MenuTextRenderer.a(floatValue69, "Назад");
         float floatValue74 = this.z + (this.A - (floatValue72 + floatValue70 + floatValue73)) * 0.5F;
         dev.leg1tka.gui.menu.MenuBackdropRenderer.b(matrixStack1, floatValue74 + floatValue72 * 0.35F - this.j * floatValue67, floatValue65 - this.j * floatValue67, 180.0F, floatValue72, floatValue68, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue10, 16777215));
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "Назад", floatValue69, floatValue74 + floatValue72 + floatValue70, floatValue65 - this.j * floatValue67, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue10, 16777215));
         float floatValue75 = dev.leg1tka.gui.components.MenuTextRenderer.a(floatValue69, "Выбрать");
         float floatValue76 = dev.leg1tka.gui.menu.MenuLayout.a(9.0F);
         float floatValue79 = this.B + (this.C - (floatValue75 + floatValue70 + floatValue76)) * 0.5F;
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "Выбрать", floatValue69, floatValue79, floatValue65 - this.k * floatValue67, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue10, 16777215));
         float floatValue81 = floatValue79 + floatValue75 + floatValue70 + floatValue76 * 0.5F;
         float floatValue83 = floatValue65 - this.k * floatValue67;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue81 - floatValue68 * 0.5F, floatValue83 - floatValue76 * 0.5F, floatValue68, floatValue76, floatValue68 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue10, 16777215));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue81 - floatValue76 * 0.5F, floatValue83 - floatValue68 * 0.5F, floatValue76, floatValue68, floatValue68 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue10, 16777215));
         this.a(matrixStack1, intValue2, intValue3, floatValue8, floatValue10, floatValue14);
         boolean flag84 = this.b && a((double)intValue2, (double)intValue3, this.t, this.v) <= this.w * 0.5F;
         boolean flag85 = this.b && a((double)intValue2, (double)intValue3, this.u, this.v) <= this.w * 0.5F;
         this.h = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.h, flag84 ? 1.0F : 0.0F, floatValue8, 12.0F);
         this.i = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.i, flag85 ? 1.0F : 0.0F, floatValue8, 12.0F);
         this.a(matrixStack1, this.t, this.v + floatValue14, 180.0F, this.h, floatValue10);
         this.a(matrixStack1, this.u, this.v + floatValue14, 0.0F, this.i, floatValue10);
      }
   }

   private void a(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4, float floatValue5, float floatValue6) {
      boolean flag7 = dev.leg1tka.gui.menu.MenuPreferences.a();
      boolean flag8 = this.b && a((double)intValue2, (double)intValue3, this.D, this.E, this.F, this.G);
      this.m = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.m, flag8 ? 1.0F : 0.0F, floatValue4, 12.0F);
      this.n = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.n, flag7 ? 1.0F : 0.0F, floatValue4, 14.0F);
      float floatValue9 = this.n * this.n * (3.0F - 2.0F * this.n);
      float floatValue10 = this.E + floatValue6 * 1.2F;
      float floatValue11 = this.G * 0.5F;
      dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack1, this.D, floatValue10, this.F, this.G, floatValue11, floatValue5, this.m);
      float floatValue12 = dev.leg1tka.gui.menu.MenuLayout.a(20.0F);
      float floatValue13 = floatValue10 + this.G * 0.5F;
      dev.leg1tka.gui.components.MenuTextRenderer.a(
         matrixStack1,
         "Шейдерные кнопки",
         13.5F,
         this.D + floatValue12,
         floatValue13 - dev.leg1tka.gui.menu.MenuLayout.a(7.5F),
         dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.95F * floatValue5, 16777215)
      );
      String string14 = flag7
         ? "Кнопки в настройках: Стекло и Кастом"
         : "Кнопки в настройках: обычные Minecraft";
      dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, string14, 11.5F, this.D + floatValue12, floatValue13 + dev.leg1tka.gui.menu.MenuLayout.a(8.5F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.55F * floatValue5, 16777215));
      float floatValue15 = dev.leg1tka.gui.menu.MenuLayout.a(40.0F);
      float floatValue16 = dev.leg1tka.gui.menu.MenuLayout.a(22.0F);
      float floatValue17 = this.D + this.F - floatValue12 - floatValue15;
      float floatValue18 = floatValue13 - floatValue16 * 0.5F;
      int intValue19 = dev.leg1tka.gui.menu.MenuBackdropRenderer.c();
      int intValue20 = dev.leg1tka.gui.menu.MenuBackdropRenderer.d();
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue17, floatValue18, floatValue15, floatValue16, floatValue16 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.14F * floatValue5, 16777215));
      if (floatValue9 > 0.01F) {
         dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue17, floatValue18, floatValue15, floatValue16, floatValue16 * 0.5F, dev.leg1tka.gui.menu.MenuLayout.a(10.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.4F * floatValue9 * floatValue5, intValue19));
         dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue17, floatValue18, floatValue15, floatValue16, floatValue16 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue9 * floatValue5, intValue19), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue9 * floatValue5, intValue20), true);
      }

      float floatValue21 = floatValue16 - dev.leg1tka.gui.menu.MenuLayout.a(6.0F);
      float floatValue22 = floatValue17 + dev.leg1tka.gui.menu.MenuLayout.a(3.0F) + (floatValue15 - floatValue21 - dev.leg1tka.gui.menu.MenuLayout.a(6.0F)) * floatValue9;
      float floatValue23 = floatValue18 + dev.leg1tka.gui.menu.MenuLayout.a(3.0F);
      dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue22, floatValue23 + dev.leg1tka.gui.menu.MenuLayout.a(1.0F), floatValue21, floatValue21, floatValue21 * 0.5F, dev.leg1tka.gui.menu.MenuLayout.a(5.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.35F * floatValue5, 0));
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue22, floatValue23, floatValue21, floatValue21, floatValue21 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue5, 16777215));
   }

   private void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6) {
      float floatValue7 = this.w * 0.5F;
      dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack1, floatValue2 - floatValue7, floatValue3 - floatValue7, this.w, this.w, floatValue7, floatValue6, floatValue5);
      if (floatValue5 > 0.01F) {
         int intValue8 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue5 * floatValue6, dev.leg1tka.gui.menu.MenuBackdropRenderer.c());
         int intValue9 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue5 * floatValue6, dev.leg1tka.gui.menu.MenuBackdropRenderer.d());
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue2 - floatValue7, floatValue3 - floatValue7, this.w, this.w, floatValue7, dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue8, intValue9, intValue8, intValue9);
      }

      float floatValue11 = floatValue4 == 0.0F ? 1.0F : -1.0F;
      float floatValue12 = floatValue11 * floatValue5 * dev.leg1tka.gui.menu.MenuLayout.a(1.5F);
      float floatValue10 = dev.leg1tka.gui.menu.MenuLayout.a(7.5F);
      dev.leg1tka.gui.menu.MenuBackdropRenderer.b(matrixStack1, floatValue2 + floatValue12 + floatValue11 * floatValue10 * 0.18F, floatValue3, floatValue4, floatValue10, Math.max(dev.leg1tka.gui.menu.MenuLayout.c() * 2.0F, dev.leg1tka.gui.menu.MenuLayout.a(1.7F)), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue6 * (0.75F + floatValue5 * 0.25F), 16777215));
   }

   public boolean a(double doubleValue1, double doubleValue3, int intValue5) {
      if (!this.b) {
         return false;
      } else if (intValue5 == 1) {
         this.d();
         return true;
      } else if (intValue5 != 0) {
         return true;
      } else if (a(doubleValue1, doubleValue3, this.t, this.v) <= this.w * 0.5F) {
         this.b(-1);
         return true;
      } else if (a(doubleValue1, doubleValue3, this.u, this.v) <= this.w * 0.5F) {
         this.b(1);
         return true;
      } else if (a(doubleValue1, doubleValue3, this.z, this.x, this.A, this.y)) {
         this.d();
         return true;
      } else if (a(doubleValue1, doubleValue3, this.B, this.x, this.C, this.y)) {
         this.e();
         return true;
      } else if (a(doubleValue1, doubleValue3, this.D, this.E, this.F, this.G)) {
         dev.leg1tka.gui.menu.MenuPreferences.a(!dev.leg1tka.gui.menu.MenuPreferences.a());
         dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
         return true;
      } else {
         for (int intValue6 = -2; intValue6 <= 2; intValue6++) {
            int intValue7 = this.e + intValue6;
            float floatValue8 = (float)intValue7 - this.d;
            float floatValue9 = Math.abs(floatValue8);
            if (!(floatValue9 > 1.6F)) {
               float floatValue10 = MathHelper.lerp(Math.min(1.0F, floatValue9), 1.0F, 0.8F);
               float floatValue11 = this.q * floatValue10;
               float floatValue12 = this.r * floatValue10;
               float floatValue13 = this.o + floatValue8 * this.s - floatValue11 * 0.5F;
               float floatValue14 = this.p - floatValue12 * 0.5F;
               if (a(doubleValue1, doubleValue3, floatValue13, floatValue14, floatValue11, floatValue12)) {
                  if (intValue6 == 0) {
                     this.e();
                  } else {
                     this.b(intValue6);
                  }

                  return true;
               }
            }
         }

         return true;
      }
   }

   public boolean a(double doubleValue1) {
      if (!this.b) {
         return false;
      } else {
         if (doubleValue1 > 0.0) {
            this.b(-1);
         } else if (doubleValue1 < 0.0) {
            this.b(1);
         }

         return true;
      }
   }

   public boolean a(int intValue1) {
      if (!this.b) {
         return false;
      } else {
         switch (intValue1) {
            case 32:
            case 257:
            case 335:
               this.e();
               break;
            case 65:
            case 263:
               this.b(-1);
               break;
            case 68:
            case 262:
               this.b(1);
               break;
            case 256:
            case 259:
               this.d();
         }

         return true;
      }
   }

   private static boolean a(double doubleValue0, double doubleValue2, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      return doubleValue0 >= (double)floatValue4 && doubleValue0 <= (double)(floatValue4 + floatValue6) && doubleValue2 >= (double)floatValue5 && doubleValue2 <= (double)(floatValue5 + floatValue7);
   }

   private static float a(double doubleValue0, double doubleValue2, float floatValue4, float floatValue5) {
      double doubleValue6 = doubleValue0 - (double)floatValue4;
      double doubleValue8 = doubleValue2 - (double)floatValue5;
      return (float)Math.sqrt(doubleValue6 * doubleValue6 + doubleValue8 * doubleValue8);
   }
}
