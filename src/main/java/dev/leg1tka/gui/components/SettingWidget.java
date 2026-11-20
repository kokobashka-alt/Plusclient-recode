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

import lombok.Generated;
import net.minecraft.client.util.math.MatrixStack;

public abstract class SettingWidget {
   public dev.leg1tka.gui.components.ModulePanel a;
   public float b;
   public float c;
   public float d;
   public float e;
   private final dev.leg1tka.api.animation.TimedAnimation f = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.C, 550L);
   private final dev.leg1tka.api.animation.TimedAnimation g = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.h, 280L);
   private final dev.leg1tka.api.animation.TimedAnimation h = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.h, 280L);

   public SettingWidget() {
   }

   private static float a(dev.leg1tka.render.font.MsdfTextRenderer value0, float floatValue1) {
      return floatValue1 + 3.5F - 0.126F * value0.d();
   }

   private static float e(float floatValue0) {
      dev.leg1tka.gui.menu.MenuLayout.a();
      return dev.leg1tka.gui.menu.MenuLayout.b(floatValue0);
   }

   public static void a(MatrixStack matrixStack0, dev.leg1tka.render.font.MsdfTextRenderer value1, String string2, float floatValue3, float floatValue4, int intValue5) {
      if (value1 != null) {
         value1.d(matrixStack0, string2, e(floatValue3), e(a(value1, floatValue4)), intValue5);
      }
   }

   public static void b(MatrixStack matrixStack0, dev.leg1tka.render.font.MsdfTextRenderer value1, String string2, float floatValue3, float floatValue4, int intValue5) {
      if (value1 != null && string2 != null && !string2.isEmpty()) {
         float floatValue6 = value1.d() * 0.5F;
         dev.leg1tka.render.font.GlyphVertexWriter value7 = value1.c().a(72);
         float floatValue8 = value7 != null ? (value7.c() + value7.d()) * 0.5F : 0.36F;
         float floatValue9 = floatValue4 + 2.5F - (value1.c().g() - floatValue8) * floatValue6;
         dev.leg1tka.gui.menu.MenuLayout.a();
         float floatValue10 = dev.leg1tka.gui.menu.MenuLayout.b(floatValue3 - value1.a(string2) * 0.5F);
         float floatValue11 = dev.leg1tka.gui.menu.MenuLayout.b(floatValue9);
         value1.e(matrixStack0, string2, floatValue10, floatValue11, intValue5);
      }
   }

   public static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, float floatValue7, float floatValue8) {
      dev.leg1tka.render.RenderPrimitives.b(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, intValue6);
      if (!(floatValue7 <= 0.01F)) {
         int intValue9 = dev.leg1tka.gui.menu.MenuBackdropRenderer.c();
         int intValue10 = dev.leg1tka.gui.menu.MenuBackdropRenderer.d();
         float floatValue11 = floatValue7 * floatValue8;
         dev.leg1tka.render.RenderPrimitives.a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, 5.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.35F * floatValue11, intValue9));
         dev.leg1tka.render.RenderPrimitives.a(matrixStack0, floatValue1, floatValue2, floatValue3, floatValue4, floatValue5, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue11, intValue9), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue11, intValue10), true);
      }
   }

   public static int a(float floatValue0, float floatValue1) {
      return dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue1));
   }

   public static void a(MatrixStack matrixStack0, dev.leg1tka.render.font.MsdfTextRenderer value1, String string2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      if (value1 != null && string2 != null && !string2.isEmpty()) {
         float floatValue7 = e(a(value1, floatValue4));
         float floatValue8 = value1.a(string2);
         if (floatValue8 <= floatValue5 + 0.5F) {
            value1.d(matrixStack0, string2, e(floatValue3), floatValue7, intValue6);
         } else {
            double doubleValue9 = (double)System.currentTimeMillis() / 1000.0;
            double doubleValue11 = 5.5;
            double doubleValue13 = doubleValue9 % doubleValue11 / doubleValue11;
            double doubleValue15 = doubleValue13 < 0.5 ? doubleValue13 * 2.0 : 2.0 - doubleValue13 * 2.0;
            float floatValue17 = (float)(doubleValue15 * doubleValue15 * (3.0 - 2.0 * doubleValue15));
            float floatValue18 = Math.max(0.0F, floatValue8 - floatValue5 - 6.0F);
            float floatValue19 = floatValue17 * floatValue18;
            dev.leg1tka.render.ScissorStack.a();
            dev.leg1tka.render.ScissorStack.a((double)floatValue3, (double)(floatValue4 - 5.0F), (double)floatValue5, 20.0);
            value1.d(matrixStack0, string2, e(floatValue3 - floatValue19), floatValue7, intValue6);
            dev.leg1tka.render.ScissorStack.b();
         }
      }
   }

   public boolean a() {
      return true;
   }

   public void a(float floatValue1, float floatValue2, float floatValue3) {
      this.b = floatValue1;
      this.c = floatValue2;
      this.d = floatValue3;
   }

   public float c() {
      return this.e;
   }

   public void a(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4) {
   }

   public void a(double doubleValue1, double doubleValue3, int intValue5) {
   }

   public void b(double doubleValue1, double doubleValue3, int intValue5) {
   }

   public void a(int intValue1, int intValue2, int intValue3) {
   }

   public void a(char character1, int intValue2) {
   }

   @Generated
   public dev.leg1tka.gui.components.ModulePanel d() {
      return this.a;
   }

   @Generated
   public float e() {
      return this.b;
   }

   @Generated
   public float f() {
      return this.c;
   }

   @Generated
   public float g() {
      return this.d;
   }

   @Generated
   public dev.leg1tka.api.animation.TimedAnimation h() {
      return this.f;
   }

   @Generated
   public dev.leg1tka.api.animation.TimedAnimation i() {
      return this.g;
   }

   @Generated
   public dev.leg1tka.api.animation.TimedAnimation j() {
      return this.h;
   }

   @Generated
   public void a(dev.leg1tka.gui.components.ModulePanel value1) {
      this.a = value1;
   }

   @Generated
   public void a(float floatValue1) {
      this.b = floatValue1;
   }

   @Generated
   public void b(float floatValue1) {
      this.c = floatValue1;
   }

   @Generated
   public void c(float floatValue1) {
      this.d = floatValue1;
   }

   @Generated
   public void d(float floatValue1) {
      this.e = floatValue1;
   }
}
