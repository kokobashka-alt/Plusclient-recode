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

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class StringSettingWidget extends dev.leg1tka.gui.components.SettingWidget {
   private static float f;
   private final dev.leg1tka.settings.StringSetting g;
   private boolean h;
   private boolean i;
   private int j;
   private float k;
   private long l;

   public StringSettingWidget(dev.leg1tka.settings.StringSetting value1) {
      this.g = value1;
      this.e = 28.0F;
   }

   @Override
   public boolean a() {
      return this.g.isVisible();
   }

   public boolean b() {
      return this.h;
   }

   private dev.leg1tka.render.font.MsdfTextRenderer k() {
      dev.leg1tka.render.font.MsdfTextRenderer value1 = dev.leg1tka.render.font.FontCache.a("bold", 14);
      if (value1 == null) {
         value1 = dev.leg1tka.render.font.FontCache.a("semibold", 14);
      }

      if (value1 == null) {
         value1 = dev.leg1tka.render.font.FontCache.a("sf_regular", 14);
      }

      return value1;
   }

   private dev.leg1tka.render.font.MsdfTextRenderer l() {
      dev.leg1tka.render.font.MsdfTextRenderer value1 = dev.leg1tka.render.font.FontCache.a("bold", 13);
      return value1 == null ? this.k() : value1;
   }

   private float m() {
      return this.b + 4.5F;
   }

   private float n() {
      return this.c + 14.0F;
   }

   private float o() {
      return this.d - 9.0F;
   }

   @Override
   public void a(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4) {
      float floatValue5 = this.a != null && this.a.n() != null ? this.a.n().i().d() : this.h().d();
      float floatValue6 = this.i().d();
      float floatValue7 = MathHelper.clamp(floatValue5 * floatValue6, 0.0F, 1.0F);
      if (!(floatValue7 < 0.01F)) {
         int intValue8 = dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue7);
         dev.leg1tka.render.font.MsdfTextRenderer value9 = this.k();
         dev.leg1tka.render.font.MsdfTextRenderer value10 = this.l();
         float floatValue11 = this.m();
         float floatValue12 = this.n();
         float floatValue13 = this.o();
         if (dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue2, (double)intValue3, (double)floatValue11, (double)floatValue12, (double)floatValue13, 11.0)) {
            dev.leg1tka.render.hud.HudVisibilityState.b();
         }

         String string14 = this.g.e();
         this.j = Math.min(this.j, string14.length());
         String string15 = this.g.getDisplayName();
         if (this.h) {
            String string16 = string14.length() + "/" + this.g.g();
            float floatValue17 = value10 == null ? 0.0F : value10.a(string16);
            a(matrixStack1, value9, string15, this.b + 4.5F, this.c + 2.0F, floatValue13 - floatValue17 - 4.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue8));
            a(matrixStack1, value10, string16, floatValue11 + floatValue13 - floatValue17, this.c + 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(140, 140, 150, intValue8));
         } else {
            a(matrixStack1, value9, string15, this.b + 4.5F, this.c + 2.0F, floatValue13, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue8));
         }

         int intValue23 = this.h ? dev.leg1tka.api.utils.text.ColorUtils.a(40, 40, 48, intValue8) : dev.leg1tka.api.utils.text.ColorUtils.a(20, 20, 25, intValue8);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue11, floatValue12, floatValue13, 11.0F, 2.5F, intValue23);
         if (this.h) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue11 + 2.0F, floatValue12 + 11.0F - 1.0F, floatValue13 - 4.0F, 1.0F, 0.5F, dev.leg1tka.gui.themes.ThemeAccentResolver.a(dev.leg1tka.gui.themes.ThemeAccentResolver.a(), floatValue7));
         }

         if (value10 != null) {
            float floatValue24 = floatValue13 - 6.0F;
            float floatValue18 = floatValue11 + 3.0F;
            float floatValue19 = value10.a(string14);
            if (!this.h) {
               this.k = 0.0F;
               String string25 = string14.isEmpty() ? "..." : string14;
               int intValue26 = string14.isEmpty() ? dev.leg1tka.api.utils.text.ColorUtils.a(140, 140, 150, intValue8) : dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue8);
               dev.leg1tka.render.ScissorStack.a();
               dev.leg1tka.render.ScissorStack.a((double)floatValue18, (double)floatValue12, (double)floatValue24, 11.0);
               a(matrixStack1, value10, string25, floatValue18, floatValue12, intValue26);
               dev.leg1tka.render.ScissorStack.b();
            } else {
               float floatValue20 = value10.a(string14.substring(0, this.j));
               if (floatValue20 - this.k > floatValue24 - 1.0F) {
                  this.k = floatValue20 - floatValue24 + 1.0F;
               }

               if (floatValue20 - this.k < 0.0F) {
                  this.k = floatValue20;
               }

               this.k = MathHelper.clamp(this.k, 0.0F, Math.max(0.0F, floatValue19 - floatValue24 + 1.0F));
               dev.leg1tka.render.ScissorStack.a();
               dev.leg1tka.render.ScissorStack.a((double)(floatValue18 - 1.0F), (double)floatValue12, (double)(floatValue24 + 2.0F), 11.0);
               if (this.i && !string14.isEmpty()) {
                  dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue18 - this.k - 1.0F, floatValue12 + 1.5F, floatValue19 + 2.0F, 8.0F, 1.5F, dev.leg1tka.gui.themes.ThemeAccentResolver.a(dev.leg1tka.gui.themes.ThemeAccentResolver.a(), floatValue7 * 0.45F));
               }

               a(matrixStack1, value10, string14, floatValue18 - this.k, floatValue12, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue8));
               long longValue21 = System.currentTimeMillis();
               if (!this.i && (longValue21 - this.l < 500L || longValue21 / 500L % 2L == 0L)) {
                  dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue18 + floatValue20 - this.k, floatValue12 + 2.0F, 0.7F, 7.0F, 0.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue8));
               }

               dev.leg1tka.render.ScissorStack.b();
            }
         }
      }
   }

   private void a(String string1, int intValue2) {
      this.g.a(string1);
      this.j = MathHelper.clamp(intValue2, 0, this.g.e().length());
      this.i = false;
      this.l = System.currentTimeMillis();
   }

   private void a(String string1) {
      StringBuilder stringBuilder2 = new StringBuilder();

      for (char character6 : string1.toCharArray()) {
         if (!Character.isISOControl(character6)) {
            stringBuilder2.append(character6);
         }
      }

      String string7 = this.g.e();
      if (this.i) {
         this.a(stringBuilder2.toString(), stringBuilder2.length());
      } else {
         int intValue8 = Math.max(0, this.g.g() - string7.length());
         String string9 = stringBuilder2.length() > intValue8 ? stringBuilder2.substring(0, intValue8) : stringBuilder2.toString();
         this.a(string7.substring(0, this.j) + string9 + string7.substring(this.j), this.j + string9.length());
      }
   }

   private static int b(String string0, int intValue1) {
      while (intValue1 > 0 && Character.isWhitespace(string0.charAt(intValue1 - 1))) {
         intValue1--;
      }

      while (intValue1 > 0 && !Character.isWhitespace(string0.charAt(intValue1 - 1))) {
         intValue1--;
      }

      return intValue1;
   }

   private static int c(String string0, int intValue1) {
      while (intValue1 < string0.length() && Character.isWhitespace(string0.charAt(intValue1))) {
         intValue1++;
      }

      while (intValue1 < string0.length() && !Character.isWhitespace(string0.charAt(intValue1))) {
         intValue1++;
      }

      return intValue1;
   }

   @Override
   public void a(double doubleValue1, double doubleValue3, int intValue5) {
      if (intValue5 == 0) {
         boolean flag6 = dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)this.m(), (double)this.n(), (double)this.o(), 11.0);
         if (!flag6) {
            this.h = false;
            this.i = false;
         } else {
            this.h = true;
            this.i = false;
            this.l = System.currentTimeMillis();
            dev.leg1tka.render.font.MsdfTextRenderer value7 = this.l();
            String string8 = this.g.e();
            float floatValue9 = (float)doubleValue1 - (this.m() + 3.0F) + this.k;
            this.j = string8.length();
            float floatValue10 = 0.0F;

            for (int intValue11 = 1; intValue11 <= string8.length() && value7 != null; intValue11++) {
               float floatValue12 = value7.a(string8.substring(0, intValue11));
               if (floatValue9 < (floatValue10 + floatValue12) / 2.0F) {
                  this.j = intValue11 - 1;
                  break;
               }

               floatValue10 = floatValue12;
            }
         }
      }
   }

   @Override
   public void a(int intValue1, int intValue2, int intValue3) {
      if (this.h) {
         String string4 = this.g.e();
         this.j = Math.min(this.j, string4.length());
         boolean flag5 = Screen.hasControlDown();
         this.l = System.currentTimeMillis();
         switch (intValue1) {
            case 65:
               if (flag5) {
                  this.i = !string4.isEmpty();
               }
               break;
            case 67:
               if (flag5 && this.i) {
                  p().keyboard.setClipboard(string4);
               }
               break;
            case 86:
               if (flag5) {
                  String string8 = p().keyboard.getClipboard();
                  if (string8 != null) {
                     this.a(string8.replace('\n', ' ').replace('\r', ' '));
                  }
               }
               break;
            case 88:
               if (flag5 && this.i) {
                  p().keyboard.setClipboard(string4);
                  this.a("", 0);
               }
               break;
            case 256:
            case 257:
            case 335:
               this.h = false;
               this.i = false;
               break;
            case 259:
               if (this.i) {
                  this.a("", 0);
               } else if (this.j > 0) {
                  int intValue7 = flag5 ? b(string4, this.j) : this.j - 1;
                  this.a(string4.substring(0, intValue7) + string4.substring(this.j), intValue7);
               }
               break;
            case 261:
               if (this.i) {
                  this.a("", 0);
               } else if (this.j < string4.length()) {
                  int intValue6 = flag5 ? c(string4, this.j) : this.j + 1;
                  this.a(string4.substring(0, this.j) + string4.substring(intValue6), this.j);
               }
               break;
            case 262:
               this.j = this.i ? string4.length() : (flag5 ? c(string4, this.j) : Math.min(string4.length(), this.j + 1));
               this.i = false;
               break;
            case 263:
               this.j = this.i ? 0 : (flag5 ? b(string4, this.j) : Math.max(0, this.j - 1));
               this.i = false;
               break;
            case 268:
               this.j = 0;
               this.i = false;
               break;
            case 269:
               this.j = string4.length();
               this.i = false;
         }
      }
   }

   private static MinecraftClient p() {
      return MinecraftClient.getInstance();
   }

   @Override
   public void a(char character1, int intValue2) {
      if (this.h && !Character.isISOControl(character1)) {
         this.a(String.valueOf(character1));
      }
   }
}
