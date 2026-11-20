package dev.leg1tka.api.utils.text;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.leg1tka.AspectDLC;
import java.awt.Color;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class ColorUtils {
   public static final Color a = new Color(36, 218, 118);
   public static final Color b = new Color(255, 196, 67);
   public static final Color c = new Color(255, 134, 0);
   public static final Color d = new Color(239, 72, 54);
   public static final Color e = new Color(125, 217, 250);

   public ColorUtils() {
   }

   public static int a(int intValue0) {
      return intValue0 >> 16 & 0xFF;
   }

   public static int b(int intValue0) {
      return intValue0 >> 8 & 0xFF;
   }

   public static float c(int intValue0) {
      return (float)a(intValue0) / 255.0F;
   }

   public static float d(int intValue0) {
      return (float)b(intValue0) / 255.0F;
   }

   public static float e(int intValue0) {
      return (float)l(intValue0) / 255.0F;
   }

   public static float f(int intValue0) {
      return (float)m(intValue0) / 255.0F;
   }

   public static int a(int intValue0, int intValue1) {
      return b(intValue0, intValue0, intValue0, intValue1);
   }

   public static int a(int intValue0, int intValue1, float floatValue2) {
      floatValue2 = MathHelper.clamp(floatValue2, 0.0F, 1.0F);
      int intValue3 = MathHelper.lerp(floatValue2, a(intValue0), a(intValue1));
      int intValue4 = MathHelper.lerp(floatValue2, b(intValue0), b(intValue1));
      int intValue5 = MathHelper.lerp(floatValue2, l(intValue0), l(intValue1));
      int intValue6 = MathHelper.lerp(floatValue2, m(intValue0), m(intValue1));
      return a(intValue3, intValue4, intValue5, intValue6);
   }

   public static int a(String string0) {
      if (string0 != null && string0.length() == 7 && string0.startsWith("#")) {
         try {
            int intValue1 = Integer.parseInt(string0.substring(1), 16);
            return 0xFF000000 | intValue1;
         } catch (NumberFormatException numberFormatException2) {
            return -16777216;
         }
      } else {
         return -16777216;
      }
   }

   public static int a(int intValue0, float floatValue1) {
      int intValue2 = intValue0 >> 24 & 0xFF;
      int intValue3 = (int)((float)intValue2 * Math.max(0.0F, Math.min(1.0F, floatValue1)));
      return intValue0 & 16777215 | intValue3 << 24;
   }

   public static int g(int intValue0) {
      return intValue0 >> 16 & 0xFF;
   }

   public static int h(int intValue0) {
      return intValue0 >> 8 & 0xFF;
   }

   public static int i(int intValue0) {
      return intValue0 & 0xFF;
   }

   public static int j(int intValue0) {
      return intValue0 >> 24 & 0xFF;
   }

   public static int b(String string0) {
      if (string0.startsWith("#")) {
         string0 = string0.substring(1);
      }

      if (string0.length() != 6) {
         throw new IllegalArgumentException(
            "Недопустимый формат HEX: " + string0
         );
      } else {
         int intValue1 = Integer.parseInt(string0.substring(0, 2), 16);
         int intValue2 = Integer.parseInt(string0.substring(2, 4), 16);
         int intValue3 = Integer.parseInt(string0.substring(4, 6), 16);
         return b(intValue1, intValue2, intValue3);
      }
   }

   public static int a() {
      return !AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow") ? AspectDLC.INSTANCE.themeStorage.b().a().c[0] : k(0);
   }

   public static int k(int intValue0) {
      return AspectDLC.INSTANCE.themeStorage.b().a().a(intValue0);
   }

   public static int b() {
      return AspectDLC.INSTANCE.themeStorage.b().a().c[0];
   }

   public static int a(int intValue0, int intValue1, float floatValue2, float floatValue3, float floatValue4) {
      int intValue5 = (int)((System.currentTimeMillis() / (long)intValue0 + (long)intValue1) % 360L);
      float floatValue6 = (float)intValue5 / 360.0F;
      int intValue7 = Color.HSBtoRGB(floatValue6, floatValue2, floatValue3);
      return b(a(intValue7), b(intValue7), l(intValue7), Math.max(0, Math.min(255, (int)(floatValue4 * 255.0F))));
   }

   public static int a(int intValue0, int intValue1, double doubleValue2) {
      doubleValue2 = (double)((float)dev.leg1tka.api.utils.math.MathOperations.b(0.0, 1.0, doubleValue2));
      return b((int)dev.leg1tka.api.utils.math.MathOperations.a(a(intValue0), a(intValue1), doubleValue2), (int)dev.leg1tka.api.utils.math.MathOperations.a(b(intValue0), b(intValue1), doubleValue2), (int)dev.leg1tka.api.utils.math.MathOperations.a(l(intValue0), l(intValue1), doubleValue2), (int)dev.leg1tka.api.utils.math.MathOperations.a(m(intValue0), m(intValue1), doubleValue2));
   }

   public static int[] a(int intValue0, int intValue1, int intValue2) {
      int[] intValueArray3 = new int[intValue2];

      for (int intValue4 = 0; intValue4 < intValue2; intValue4++) {
         double doubleValue5 = (double)intValue4 / (double)(intValue2 - 1);
         intValueArray3[intValue4] = a(intValue0, intValue1, doubleValue5);
      }

      return intValueArray3;
   }

   public static int l(int intValue0) {
      return intValue0 & 0xFF;
   }

   public static int b(int intValue0, int intValue1, float floatValue2) {
      return a(
         (float)a(intValue0) * (1.0F - floatValue2) + (float)a(intValue1) * floatValue2,
         (float)b(intValue0) * (1.0F - floatValue2) + (float)b(intValue1) * floatValue2,
         (float)l(intValue0) * (1.0F - floatValue2) + (float)l(intValue1) * floatValue2,
         (float)m(intValue0) * (1.0F - floatValue2) + (float)m(intValue1) * floatValue2
      );
   }

   public static int b(int intValue0, float floatValue1) {
      float[] floatValueArray2 = w(intValue0);
      float[] floatValueArray3 = Color.RGBtoHSB((int)(floatValueArray2[0] * 255.0F), (int)(floatValueArray2[1] * 255.0F), (int)(floatValueArray2[2] * 255.0F), null);
      floatValueArray3[2] *= floatValue1;
      floatValueArray3[2] = Math.max(0.0F, Math.min(1.0F, floatValueArray3[2]));
      int intValue4 = Color.HSBtoRGB(floatValueArray3[0], floatValueArray3[1], floatValueArray3[2]);
      return f(intValue4, (float)((int)(floatValueArray2[3] * 255.0F)));
   }

   public static int c(int intValue0, float floatValue1) {
      return a((float)a(intValue0) * floatValue1, (float)b(intValue0) * floatValue1, (float)l(intValue0) * floatValue1, (float)m(intValue0));
   }

   public static int b(int intValue0, int intValue1) {
      return b(intValue0, intValue1, 0.5F);
   }

   public static int m(int intValue0) {
      return intValue0 >> 24 & 0xFF;
   }

   public static int d(int intValue0, float floatValue1) {
      return a((float)a(intValue0), (float)b(intValue0), (float)l(intValue0), (float)m(intValue0) * floatValue1);
   }

   public static int c(int intValue0, int intValue1) {
      intValue1 = Math.max(0, Math.min(255, intValue1));
      return intValue1 << 24 | intValue0 & 16777215;
   }

   public static Color c() {
      return new Color(Color.HSBtoRGB((float)Math.random(), (float)(0.75 + Math.random() / 4.0), (float)(0.75 + Math.random() / 4.0)));
   }

   public static int a(float floatValue0, float floatValue1, float floatValue2, float floatValue3) {
      return new Color((int)floatValue0, (int)floatValue1, (int)floatValue2, (int)floatValue3).getRGB();
   }

   public static float[] n(int intValue0) {
      return new float[]{c(intValue0), d(intValue0), e(intValue0), f(intValue0)};
   }

   public static float[] o(int intValue0) {
      return new float[]{(float)a(intValue0) / 255.0F, (float)b(intValue0) / 255.0F, (float)l(intValue0) / 255.0F, (float)m(intValue0) / 255.0F};
   }

   public static Color a(int intValue0, int intValue1, Color color2, Color color3, boolean flag4) {
      int intValue5 = 0;
      if (intValue0 == 0) {
         intValue5 = intValue1 % 360;
      } else {
         intValue5 = (int)((System.currentTimeMillis() / (long)intValue0 + (long)intValue1) % 360L);
      }

      intValue5 = (intValue5 >= 180 ? 360 - intValue5 : intValue5) * 2;
      return flag4 ? b(color2, color3, (float)intValue5 / 360.0F) : a(color2, color3, (float)intValue5 / 360.0F);
   }

   public static Color a(int intValue0, int intValue1, Color color2, Color color3) {
      return a(intValue0, intValue1, color2, color3, false);
   }

   public static Color b(float floatValue0, float floatValue1, float floatValue2, float floatValue3) {
      float floatValue4 = 1800.0F;
      float floatValue5 = (float)(System.currentTimeMillis() % (long)((int)floatValue4)) + (floatValue1 - floatValue0) * floatValue3;

      while (floatValue5 > floatValue4) {
         floatValue5 -= floatValue4;
      }

      floatValue5 /= floatValue4;
      if (floatValue5 > 1.0F) {
         floatValue5 = 1.0F - (floatValue5 - 1.0F);
      }

      return Color.getHSBColor(++floatValue5, floatValue2, 1.0F);
   }

   private static int e(int intValue0, int intValue1) {
      long longValue2 = System.currentTimeMillis();
      long longValue4 = (longValue2 / (long)intValue0 + (long)intValue1) % 360L;
      return (int)longValue4;
   }

   public static void a(Color color0, float floatValue1) {
      float floatValue2 = (float)color0.getRed() / 255.0F;
      float floatValue3 = (float)color0.getGreen() / 255.0F;
      float floatValue4 = (float)color0.getBlue() / 255.0F;
      RenderSystem.setShaderColor(floatValue2, floatValue3, floatValue4, floatValue1);
   }

   public static int b(int intValue0, int intValue1, int intValue2) {
      return 0xFF000000 | intValue0 << 16 | intValue1 << 8 | intValue2;
   }

   public static int a(int intValue0, int intValue1, int intValue2, int intValue3) {
      return intValue3 << 24 | intValue0 << 16 | intValue1 << 8 | intValue2;
   }

   public static float[] p(int intValue0) {
      return new float[]{
         (float)(intValue0 >> 16 & 0xFF) / 255.0F, (float)(intValue0 >> 8 & 0xFF) / 255.0F, (float)(intValue0 & 0xFF) / 255.0F, (float)(intValue0 >> 24 & 0xFF) / 255.0F
      };
   }

   public static int a(double doubleValue0, double doubleValue2, double doubleValue4, double doubleValue6) {
      return a((int)doubleValue0, (int)doubleValue2, (int)doubleValue4, (int)doubleValue6);
   }

   public static int q(int intValue0) {
      return intValue0 >> 16 & 0xFF;
   }

   public static int r(int intValue0) {
      return intValue0 >> 8 & 0xFF;
   }

   public static int c(int intValue0, int intValue1, float floatValue2) {
      float[] floatValueArray3 = p(intValue0);
      float[] floatValueArray4 = p(intValue1);
      return a(
         (int)dev.leg1tka.api.utils.math.MathOperations.a(floatValueArray3[0] * 255.0F, floatValueArray4[0] * 255.0F, floatValue2),
         (int)dev.leg1tka.api.utils.math.MathOperations.a(floatValueArray3[1] * 255.0F, floatValueArray4[1] * 255.0F, floatValue2),
         (int)dev.leg1tka.api.utils.math.MathOperations.a(floatValueArray3[2] * 255.0F, floatValueArray4[2] * 255.0F, floatValue2),
         (int)dev.leg1tka.api.utils.math.MathOperations.a(floatValueArray3[3] * 255.0F, floatValueArray4[3] * 255.0F, floatValue2)
      );
   }

   public static int d(int intValue0, int intValue1, float floatValue2) {
      floatValue2 = Math.min(1.0F, Math.max(0.0F, floatValue2));
      int intValue3 = q(intValue0);
      int intValue4 = r(intValue0);
      int intValue5 = s(intValue0);
      int intValue6 = t(intValue0);
      int intValue7 = q(intValue1);
      int intValue8 = r(intValue1);
      int intValue9 = s(intValue1);
      int intValue10 = t(intValue1);
      int intValue11 = b(intValue3, intValue7, (double)floatValue2);
      int intValue12 = b(intValue4, intValue8, (double)floatValue2);
      int intValue13 = b(intValue5, intValue9, (double)floatValue2);
      int intValue14 = b(intValue6, intValue10, (double)floatValue2);
      return intValue14 << 24 | intValue11 << 16 | intValue12 << 8 | intValue13;
   }

   public static MutableText a(String string0, int intValue1, int intValue2) {
      MutableText mutableText3 = Text.empty();

      for (int intValue4 = 0; intValue4 < string0.length(); intValue4++) {
         int intValue5 = d(intValue1, intValue2, (float)intValue4 / (float)string0.length());
         MutableText mutableText6 = Text.literal(String.valueOf(string0.charAt(intValue4))).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(intValue5)));
         mutableText3.append(mutableText6);
      }

      return mutableText3;
   }

   public static Text a(Text text0, String string1, String string2) {
      if (text0 != null && string1 != null && string2 != null) {
         String string3 = text0.getString();
         String string4 = string3.replace(string1, string2);
         return Text.literal(string4);
      } else {
         return text0;
      }
   }

   public static int a(int intValue0, int intValue1, int... intValueArray2) {
      int intValue3 = (int)((System.currentTimeMillis() / (long)intValue0 + (long)intValue1) % 360L);
      intValue3 = (intValue3 > 180 ? 360 - intValue3 : intValue3) + 180;
      int intValue4 = (int)((float)intValue3 / 360.0F * (float)intValueArray2.length);
      if (intValue4 == intValueArray2.length) {
         intValue4--;
      }

      int intValue5 = intValueArray2[intValue4];
      int intValue6 = intValueArray2[intValue4 == intValueArray2.length - 1 ? 0 : intValue4 + 1];
      return d(intValue5, intValue6, (float)intValue3 / 360.0F * (float)intValueArray2.length - (float)intValue4);
   }

   public static int e(int intValue0, int intValue1, float floatValue2) {
      int intValue3 = a();
      return a(intValue0, intValue1, intValue3, b(intValue3, floatValue2));
   }

   public static int s(int intValue0) {
      return intValue0 & 0xFF;
   }

   public static int t(int intValue0) {
      return intValue0 >> 24 & 0xFF;
   }

   public static int b(int intValue0, int intValue1, int intValue2, int intValue3) {
      int intValue4 = 0;
      intValue4 |= intValue3 << 24;
      intValue4 |= intValue0 << 16;
      intValue4 |= intValue1 << 8;
      return intValue4 | intValue2;
   }

   public static int u(int intValue0) {
      return b(intValue0, intValue0, intValue0, 255);
   }

   public static float[] v(int intValue0) {
      return new float[]{(float)a(intValue0) / 255.0F, (float)b(intValue0) / 255.0F, (float)l(intValue0) / 255.0F, f(intValue0)};
   }

   public static float[] w(int intValue0) {
      return new float[]{(float)a(intValue0) / 255.0F, (float)b(intValue0) / 255.0F, (float)l(intValue0) / 255.0F, f(intValue0)};
   }

   public static void b(double doubleValue0, double doubleValue2, double doubleValue4, double doubleValue6) {
      GL11.glColor4d(doubleValue0, doubleValue2, doubleValue4, doubleValue6);
   }

   public static int d(int intValue0, int intValue1) {
      return intValue0 & 16777215 | intValue1 << 24;
   }

   public static float a(float floatValue0, float floatValue1, float floatValue2) {
      return floatValue0 + floatValue2 * (floatValue1 - floatValue0);
   }

   public static Color a(Color color0, Color color1, float floatValue2) {
      floatValue2 = Math.min(1.0F, Math.max(0.0F, floatValue2));
      return new Color(
         b(color0.getRed(), color1.getRed(), (double)floatValue2),
         b(color0.getGreen(), color1.getGreen(), (double)floatValue2),
         b(color0.getBlue(), color1.getBlue(), (double)floatValue2),
         b(color0.getAlpha(), color1.getAlpha(), (double)floatValue2)
      );
   }

   public static Double a(double doubleValue0, double doubleValue2, double doubleValue4) {
      return doubleValue0 + (doubleValue2 - doubleValue0) * doubleValue4;
   }

   public static float a(float floatValue0, float floatValue1, double doubleValue2) {
      return a((double)floatValue0, (double)floatValue1, (double)((float)doubleValue2)).floatValue();
   }

   public static int b(int intValue0, int intValue1, double doubleValue2) {
      return c(intValue0, intValue1, (float)doubleValue2);
   }

   public static Color b(Color color0, Color color1, float floatValue2) {
      floatValue2 = Math.min(1.0F, Math.max(0.0F, floatValue2));
      float[] floatValueArray3 = Color.RGBtoHSB(color0.getRed(), color0.getGreen(), color0.getBlue(), null);
      float[] floatValueArray4 = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
      Color color5 = Color.getHSBColor(a(floatValueArray3[0], floatValueArray4[0], (double)floatValue2), a(floatValueArray3[1], floatValueArray4[1], (double)floatValue2), a(floatValueArray3[2], floatValueArray4[2], (double)floatValue2));
      return new Color(color5.getRed(), color5.getGreen(), color5.getBlue(), b(color0.getAlpha(), color1.getAlpha(), (double)floatValue2));
   }

   public static void a(Color color0) {
      if (color0 == null) {
         color0 = Color.white;
      }

      b(
         (double)((float)color0.getRed() / 255.0F),
         (double)((float)color0.getGreen() / 255.0F),
         (double)((float)color0.getBlue() / 255.0F),
         (double)((float)color0.getAlpha() / 255.0F)
      );
   }

   public static void x(int intValue0) {
      e(intValue0, (float)(intValue0 >> 24 & 0xFF) / 255.0F);
   }

   public static void e(int intValue0, float floatValue1) {
      float floatValue2 = (float)(intValue0 >> 16 & 0xFF) / 255.0F;
      float floatValue3 = (float)(intValue0 >> 8 & 0xFF) / 255.0F;
      float floatValue4 = (float)(intValue0 & 0xFF) / 255.0F;
      RenderSystem.setShaderColor(floatValue2, floatValue3, floatValue4, floatValue1);
   }

   public static int f(int intValue0, float floatValue1) {
      return a((double)q(intValue0), (double)r(intValue0), (double)s(intValue0), (double)((float)t(intValue0) * floatValue1 / 255.0F));
   }

   public static int g(int intValue0, float floatValue1) {
      return a((double)(y(intValue0) * floatValue1), (double)(C(intValue0) * floatValue1), (double)(A(intValue0) * floatValue1), (double)E(intValue0));
   }

   public static float y(int intValue0) {
      return (float)(intValue0 >> 16 & 0xFF) / 255.0F;
   }

   public static int z(int intValue0) {
      return intValue0 >> 16 & 0xFF;
   }

   public static float A(int intValue0) {
      return (float)(intValue0 & 0xFF) / 255.0F;
   }

   public static int B(int intValue0) {
      return intValue0 & 0xFF;
   }

   public static float C(int intValue0) {
      return (float)(intValue0 >> 8 & 0xFF) / 255.0F;
   }

   public static int D(int intValue0) {
      return intValue0 >> 8 & 0xFF;
   }

   public static float E(int intValue0) {
      return (float)(intValue0 >> 24 & 0xFF) / 255.0F;
   }

   public static int F(int intValue0) {
      return intValue0 >> 24 & 0xFF;
   }

   public static int h(int intValue0, float floatValue1) {
      return 0;
   }

   private static dev.leg1tka.gui.themes.ThemePalette x() {
      return AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null && AspectDLC.INSTANCE.themeStorage.b() != null
         ? AspectDLC.INSTANCE.themeStorage.b().a()
         : null;
   }

   public static int d() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? -15921904 : value0.d;
   }

   public static int e() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? a() : value0.e;
   }

   public static int f() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? -1513240 : value0.f;
   }

   public static int g() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? -7829351 : value0.g;
   }

   public static int h() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? -16119283 : value0.h;
   }

   public static int i() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? a() : value0.i;
   }

   public static int j() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? a() : value0.j;
   }

   public static int k() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? -1 : value0.k;
   }

   public static int l() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? -14013899 : value0.l;
   }

   public static int m() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? a() : value0.m;
   }

   public static int n() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? -14013899 : value0.n;
   }

   public static int o() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? a() : value0.o;
   }

   public static int p() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? -14013899 : value0.p;
   }

   public static int q() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? -14013899 : value0.q;
   }

   public static int r() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? -15066590 : value0.r;
   }

   public static int s() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? -15921904 : value0.s;
   }

   public static int t() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? -1 : value0.t;
   }

   public static int u() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? -15921904 : value0.u;
   }

   public static int v() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? a() : value0.v;
   }

   public static int w() {
      dev.leg1tka.gui.themes.ThemePalette value0 = x();
      return value0 == null ? a() : value0.w;
   }
}
