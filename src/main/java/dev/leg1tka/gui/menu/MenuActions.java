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

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MenuActions {
   public static int a;
   public static int b;
   public static int c;
   private static float d;
   private static float e;
   private static float f;
   private static float g;
   private static float h;
   private float i = 0.0F;
   private float j = 0.0F;
   private long k = 0L;
   private float l;
   private float m;
   private float n;
   private float o;
   private float p;
   private float q;
   private float r;

   public MenuActions() {
   }

   private void a(float floatValue1, float floatValue2, float floatValue3, boolean flag4) {
      this.l = floatValue3;
      this.o = flag4 ? this.l : dev.leg1tka.gui.menu.MenuLayout.a(236.0F);
      this.r = this.o;
      this.m = dev.leg1tka.gui.menu.MenuLayout.b(dev.leg1tka.gui.menu.MenuLayout.a(26.0F));
      this.p = dev.leg1tka.gui.menu.MenuLayout.b(floatValue1 - dev.leg1tka.gui.menu.MenuLayout.a(26.0F) - this.r);
      this.n = dev.leg1tka.gui.menu.MenuLayout.b(floatValue2 - this.l * 0.5F);
      this.q = this.n;
   }

   public boolean a(float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5) {
      this.a(floatValue1, floatValue2, floatValue3, false);
      float floatValue6 = dev.leg1tka.gui.menu.MenuLayout.a(16.0F);
      return this.m + this.o + floatValue6 < floatValue4 && this.p - floatValue6 > floatValue5;
   }

   public void a(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4, float floatValue5, float floatValue6, boolean flag7, float floatValue8, float floatValue9) {
      long longValue10 = System.nanoTime();
      float floatValue12 = this.k == 0L ? 0.016F : Math.min(0.05F, (float)((double)(longValue10 - this.k) / 1.0E9));
      this.k = longValue10;
      this.a(floatValue4, floatValue5, floatValue6, flag7);
      float floatValue13 = (1.0F - floatValue9) * dev.leg1tka.gui.menu.MenuLayout.a(36.0F);
      this.m -= floatValue13;
      this.p += floatValue13;
      boolean flag14 = floatValue8 > 0.5F && a((double)intValue2, (double)intValue3, this.m, this.n, this.o, this.l);
      boolean flag15 = floatValue8 > 0.5F && a((double)intValue2, (double)intValue3, this.p, this.q, this.r, this.l);
      this.i = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.i, flag14 ? 1.0F : 0.0F, floatValue12, 12.0F);
      this.j = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.j, flag15 ? 1.0F : 0.0F, floatValue12, 12.0F);
      if (!(floatValue8 < 0.01F)) {
         float floatValue16 = dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
         float floatValue17 = dev.leg1tka.gui.menu.MenuLayout.a(16.0F);
         float floatValue18 = floatValue17 - floatValue16 * 0.6F;
         float floatValue19 = this.l - floatValue16 * 2.0F;
         float floatValue20 = dev.leg1tka.gui.menu.MenuBackdropRenderer.g();
         float floatValue21 = -this.i * dev.leg1tka.gui.menu.MenuLayout.a(1.5F);
         dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack1, this.m, this.n + floatValue21, this.o, this.l, floatValue17, floatValue8, this.i);
         dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme backgroundTheme22 = dev.leg1tka.gui.menu.WallpaperSettings.a.a();
         float floatValue23 = flag7 ? floatValue19 : floatValue19 * 16.0F / 9.0F;
         float floatValue24 = this.m + floatValue16;
         float floatValue25 = this.n + floatValue16 + floatValue21;
         Identifier identifier26 = dev.leg1tka.gui.menu.MenuThumbnailCache.a(backgroundTheme22);
         dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue24, floatValue25 + dev.leg1tka.gui.menu.MenuLayout.a(1.5F), floatValue23, floatValue19, floatValue18, dev.leg1tka.gui.menu.MenuLayout.a(6.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.35F * floatValue8, 0));
         if (identifier26 != null) {
            dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack1, identifier26, dev.leg1tka.gui.menu.WallpaperSettings.a.a(backgroundTheme22.c()), 1.0F, floatValue24, floatValue25, floatValue23, floatValue19, floatValue18, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue8, 16777215));
         } else {
            dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue24, floatValue25, floatValue23, floatValue19, floatValue18, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue8, backgroundTheme22.e()), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue8, backgroundTheme22.f()), true);
         }

         int intValue27 = this.i > 0.01F ? dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.16F + this.i * 0.6F) * floatValue8, dev.leg1tka.gui.menu.MenuBackdropRenderer.c()) : dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.16F * floatValue8, 16777215);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue24, floatValue25, floatValue23, floatValue19, floatValue18, floatValue20, intValue27, intValue27, intValue27, intValue27);
         if (!flag7) {
            float floatValue28 = floatValue24 + floatValue23 + dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
            float floatValue29 = this.m + this.o - dev.leg1tka.gui.menu.MenuLayout.a(20.0F);
            float floatValue30 = floatValue29 - dev.leg1tka.gui.menu.MenuLayout.a(10.0F) - floatValue28;
            float floatValue31 = floatValue5 + floatValue21;
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, a(backgroundTheme22.b(), 14.0F, floatValue30), 14.0F, floatValue28, floatValue31 - dev.leg1tka.gui.menu.MenuLayout.a(7.5F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.96F * floatValue8, 16777215));
            dev.leg1tka.gui.components.MenuTextRenderer.a(
               matrixStack1,
               a("Сменить фон", 11.5F, floatValue30),
               11.5F,
               floatValue28,
               floatValue31 + dev.leg1tka.gui.menu.MenuLayout.a(9.0F),
               dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.5F * floatValue8, 16777215)
            );
            dev.leg1tka.gui.menu.MenuBackdropRenderer.b(
               matrixStack1,
               floatValue29,
               floatValue31 - this.i * dev.leg1tka.gui.menu.MenuLayout.a(1.5F),
               -90.0F,
               dev.leg1tka.gui.menu.MenuLayout.a(6.5F),
               Math.max(dev.leg1tka.gui.menu.MenuLayout.c() * 2.0F, dev.leg1tka.gui.menu.MenuLayout.a(1.6F)),
               dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.45F + this.i * 0.55F) * floatValue8, 16777215)
            );
         }

         float floatValue37 = -this.j * dev.leg1tka.gui.menu.MenuLayout.a(1.5F);
         dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack1, this.p, this.q + floatValue37, this.r, this.l, floatValue17, floatValue8, this.j);
         String string38 = MinecraftClient.getInstance().getSession().getUsername();
         float floatValue39 = flag7 ? this.p + (this.r - floatValue19) * 0.5F : this.p + floatValue16;
         float floatValue40 = this.q + floatValue16 + floatValue37;
         dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue39, floatValue40 + dev.leg1tka.gui.menu.MenuLayout.a(1.5F), floatValue19, floatValue19, floatValue18, dev.leg1tka.gui.menu.MenuLayout.a(6.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.35F * floatValue8, 0));
         dev.leg1tka.render.RenderPrimitives.a(matrixStack1, dev.leg1tka.accounts.AccountProfileResolver.b(string38).texture(), floatValue39, floatValue40, floatValue19, floatValue18, floatValue8);
         int intValue32 = this.j > 0.01F ? dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.16F + this.j * 0.6F) * floatValue8, dev.leg1tka.gui.menu.MenuBackdropRenderer.d()) : dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.14F * floatValue8, 16777215);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue39, floatValue40, floatValue19, floatValue19, floatValue18, floatValue20, intValue32, intValue32, intValue32, intValue32);
         if (!flag7) {
            float floatValue33 = floatValue39 + floatValue19 + dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
            float floatValue34 = this.p + this.r - dev.leg1tka.gui.menu.MenuLayout.a(20.0F);
            float floatValue35 = floatValue34 - dev.leg1tka.gui.menu.MenuLayout.a(10.0F) - floatValue33;
            float floatValue36 = floatValue5 + floatValue37;
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, a(string38, 14.0F, floatValue35), 14.0F, floatValue33, floatValue36 - dev.leg1tka.gui.menu.MenuLayout.a(7.5F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.96F * floatValue8, 16777215));
            dev.leg1tka.gui.components.MenuTextRenderer.a(
               matrixStack1,
               a("Сменить аккаунт", 11.5F, floatValue35),
               11.5F,
               floatValue33,
               floatValue36 + dev.leg1tka.gui.menu.MenuLayout.a(9.0F),
               dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.5F * floatValue8, 16777215)
            );
            dev.leg1tka.gui.menu.MenuBackdropRenderer.b(
               matrixStack1, floatValue34 + this.j * dev.leg1tka.gui.menu.MenuLayout.a(1.5F), floatValue36, 0.0F, dev.leg1tka.gui.menu.MenuLayout.a(6.5F), Math.max(dev.leg1tka.gui.menu.MenuLayout.c() * 2.0F, dev.leg1tka.gui.menu.MenuLayout.a(1.6F)), dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.45F + this.j * 0.55F) * floatValue8, 16777215)
            );
         }
      }
   }

   private static String a(String string0, float floatValue1, float floatValue2) {
      if (dev.leg1tka.gui.components.MenuTextRenderer.a(floatValue1, string0) <= floatValue2) {
         return string0;
      } else {
         String string3 = string0;

         while (string3.length() > 1 && dev.leg1tka.gui.components.MenuTextRenderer.a(floatValue1, string3 + "...") > floatValue2) {
            string3 = string3.substring(0, string3.length() - 1);
         }

         return string3.trim() + "...";
      }
   }

   public int a(double doubleValue1, double doubleValue3) {
      if (a(doubleValue1, doubleValue3, this.m, this.n, this.o, this.l)) {
         return 1;
      } else {
         return a(doubleValue1, doubleValue3, this.p, this.q, this.r, this.l) ? 2 : 0;
      }
   }

   private static boolean a(double doubleValue0, double doubleValue2, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      return doubleValue0 >= (double)floatValue4 && doubleValue0 <= (double)(floatValue4 + floatValue6) && doubleValue2 >= (double)floatValue5 && doubleValue2 <= (double)(floatValue5 + floatValue7);
   }
}
