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

import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Generated;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class CategoryPanel {
   public float a;
   public float b;
   public float c;
   public float d;
   public final Category e;
   public final List<dev.leg1tka.gui.components.ModulePanel> f = new ArrayList<>();
   private final dev.leg1tka.api.animation.TimedAnimation i = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.n, 350L);
   private final dev.leg1tka.api.animation.TimedAnimation j = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.n, 250L);
   private final dev.leg1tka.api.animation.TimedAnimation k = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.i, 220L);
   public float g;
   public float h;
   private final ClickGuiScreen l;

   public CategoryPanel(Category category1, ClickGuiScreen clickGuiScreen2) {
      this.e = category1;
      this.l = clickGuiScreen2;
      if (ModuleManager.a != null) {
         ModuleManager.a
            .a()
            .stream()
            .filter(module1x -> module1x.getCategory() == this.e && !module1x.l())
            .sorted(Comparator.comparing(module0 -> module0.getName().toLowerCase()))
            .forEach(module1x -> this.f.add(new dev.leg1tka.gui.components.ModulePanel(module1x, this)));
      }
   }

   public void a() {
      if (this.h > 0.0F) {
         this.g = MathHelper.clamp(this.g, -this.h, 0.0F);
      } else {
         this.g = 0.0F;
      }
   }

   public void a(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4) {
      float floatValue5 = MathHelper.clamp(this.j.d(), 0.0F, 1.0F);
      if (!(floatValue5 < 0.01F)) {
         int intValue6 = dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue5);
         int intValue7 = dev.leg1tka.gui.themes.ThemeAccentResolver.a();
         dev.leg1tka.render.RenderPrimitives.a(matrixStack1, this.a, this.b + 3.0F, this.c, this.d, 8.0F, 16.0F, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, Math.max(1, dev.leg1tka.gui.themes.ThemeAccentResolver.a(130.0F * floatValue5))));
         dev.leg1tka.render.RenderPrimitives.e(matrixStack1, this.a, this.b, this.c, this.d, 8.0F, 20.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, Math.max(1, intValue6)));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, this.a, this.b, this.c, this.d, 8.0F, dev.leg1tka.api.utils.text.ColorUtils.a(12, 12, 15, dev.leg1tka.gui.themes.ThemeAccentResolver.a(190.0F * floatValue5)));
         dev.leg1tka.render.RenderPrimitives.b(
            matrixStack1, this.a, this.b, this.c, 30.0F, 8.0F, 8.0F, 0.0F, 0.0F, dev.leg1tka.gui.themes.ThemeAccentResolver.a(intValue7, 0.12F * floatValue5), dev.leg1tka.gui.themes.ThemeAccentResolver.a(intValue7, 0.12F * floatValue5), dev.leg1tka.gui.themes.ThemeAccentResolver.a(intValue7, 0.0F), dev.leg1tka.gui.themes.ThemeAccentResolver.a(intValue7, 0.0F)
         );
         dev.leg1tka.render.font.MsdfTextRenderer value8 = dev.leg1tka.render.font.FontCache.a("nunito", 16);
         if (value8 == null) {
            value8 = dev.leg1tka.render.font.FontCache.a("bold", 15);
         }

         if (value8 != null) {
            String string9 = this.e.name();
            float floatValue10 = value8.a(string9);
            String string11 = this.e.c();
            float floatValue12 = b(value8, string9);
            float floatValue13 = b(dev.leg1tka.render.font.FontCache.a("icon", 20), string11) / 20.0F;
            int intValue14 = floatValue13 > 0.0F ? MathHelper.clamp(Math.round(floatValue12 * 1.3F / floatValue13), 10, 24) : 14;
            dev.leg1tka.render.font.MsdfTextRenderer value15 = dev.leg1tka.render.font.FontCache.a("icon", intValue14);
            float floatValue16 = value15 == null ? 0.0F : value15.a(string11) + 4.0F;
            float floatValue17 = this.a + (this.c - floatValue10 - floatValue16) / 2.0F;
            if (value15 != null) {
               float floatValue18 = a(value8, string9, this.b + 4.5F) - a(value15, string11, 0.0F);
               dev.leg1tka.gui.components.SettingWidget.a(matrixStack1, value15, string11, floatValue17, floatValue18, dev.leg1tka.gui.themes.ThemeAccentResolver.a(intValue7, floatValue5));
            }

            dev.leg1tka.gui.components.SettingWidget.a(matrixStack1, value8, string9, floatValue17 + floatValue16, this.b + 4.5F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue6));
         }

         this.a();
         this.i.a(this.g);
         float floatValue19 = 2.0F;
         float floatValue20 = 16.5F;
         dev.leg1tka.render.ScissorStack.a();
         dev.leg1tka.render.ScissorStack.a((double)this.a, (double)(this.b + floatValue20), (double)this.c, (double)(this.d - floatValue20 - 4.0F));

         for (dev.leg1tka.gui.components.ModulePanel value22 : this.f) {
            if (!this.l.a(value22.m().getName())) {
               value22.a(this.a + 2.5F);
               value22.b(this.b + floatValue20 + floatValue19 + this.i.d());
               value22.c(this.c - 5.0F);
               float floatValue23 = 16.5F;
               float floatValue24 = 0.0F;
               if (value22.k().d() > 0.01F) {
                  ObjectListIterator objectListIterator25 = value22.u().iterator();

                  while (objectListIterator25.hasNext()) {
                     dev.leg1tka.gui.components.SettingWidget value26 = (dev.leg1tka.gui.components.SettingWidget)objectListIterator25.next();
                     float floatValue27 = MathHelper.clamp(value26.i().d(), 0.0F, 1.0F);
                     if (value26.a() || floatValue27 > 0.0F) {
                        floatValue24 += value26.c() * floatValue27;
                     }
                  }
               }

               value22.d(floatValue23 + floatValue24 * value22.k().d());
               dev.leg1tka.render.ScissorStack.a((double)this.a, (double)(this.b + floatValue20), (double)this.c, (double)(this.d - floatValue20 - 4.0F));
               value22.a(matrixStack1, intValue2, intValue3, floatValue4);
               dev.leg1tka.render.ScissorStack.a((double)this.a, (double)(this.b + floatValue20), (double)this.c, (double)(this.d - floatValue20 - 4.0F));
               floatValue19 += value22.c() + 2.0F;
            }
         }

         this.h = Math.max(0.0F, floatValue19 - (this.d - floatValue20 - 8.0F));
         this.k.a(this.h > 0.0F);
         dev.leg1tka.render.ScissorStack.c();
         dev.leg1tka.render.ScissorStack.b();
      }
   }

   private static float[] a(dev.leg1tka.render.font.MsdfTextRenderer value0, String string1) {
      if (value0 != null && string1 != null) {
         dev.leg1tka.render.font.MsdfFont value2 = value0.c();
         float floatValue3 = -Float.MAX_VALUE;
         float floatValue4 = Float.MAX_VALUE;

         for (int intValue5 = 0; intValue5 < string1.length(); intValue5++) {
            dev.leg1tka.render.font.GlyphVertexWriter value6 = value2.a(string1.charAt(intValue5));
            if (value6 != null && value6.c() != value6.d()) {
               floatValue3 = Math.max(floatValue3, value6.c());
               floatValue4 = Math.min(floatValue4, value6.d());
            }
         }

         return floatValue3 < floatValue4 ? null : new float[]{floatValue3, floatValue4};
      } else {
         return null;
      }
   }

   private static float b(dev.leg1tka.render.font.MsdfTextRenderer value0, String string1) {
      float[] floatValueArray2 = a(value0, string1);
      return floatValueArray2 == null ? 0.0F : (floatValueArray2[0] - floatValueArray2[1]) * value0.d() * 0.5F;
   }

   private static float a(dev.leg1tka.render.font.MsdfTextRenderer value0, String string1, float floatValue2) {
      float[] floatValueArray3 = a(value0, string1);
      if (floatValueArray3 == null) {
         return floatValue2 + 3.5F;
      } else {
         float floatValue4 = value0.d() * 0.5F;
         float floatValue5 = floatValue2 + 3.5F - 0.126F * value0.d();
         float floatValue6 = floatValue5 - 1.5F + value0.c().g() * floatValue4;
         return floatValue6 - 1.0F - (floatValueArray3[0] + floatValueArray3[1]) / 2.0F * floatValue4;
      }
   }

   public void a(double doubleValue1, double doubleValue3, int intValue5) {
      if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)this.a, (double)(this.b + 16.5F), (double)this.c, (double)(this.d - 16.5F))) {
         for (dev.leg1tka.gui.components.ModulePanel value7 : this.f) {
            if (!this.l.a(value7.m().getName())) {
               value7.a(doubleValue1, doubleValue3, intValue5);
            }
         }
      }
   }

   public void b(double doubleValue1, double doubleValue3, int intValue5) {
      for (dev.leg1tka.gui.components.ModulePanel value7 : this.f) {
         if (!this.l.a(value7.m().getName())) {
            value7.b(doubleValue1, doubleValue3, intValue5);
         }
      }
   }

   public void a(double doubleValue1, double doubleValue3, double doubleValue5, double doubleValue7) {
      if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)this.a, (double)this.b, (double)this.c, (double)this.d)) {
         this.g += (float)(doubleValue7 * 30.0);
         this.a();
      }
   }

   public void a(int intValue1, int intValue2, int intValue3) {
      for (dev.leg1tka.gui.components.ModulePanel value5 : this.f) {
         if (!this.l.a(value5.m().getName())) {
            value5.a(intValue1, intValue2, intValue3);
         }
      }
   }

   public void a(char character1, int intValue2) {
      for (dev.leg1tka.gui.components.ModulePanel value4 : this.f) {
         if (!this.l.a(value4.m().getName())) {
            value4.a(character1, intValue2);
         }
      }
   }

   @Generated
   public float b() {
      return this.a;
   }

   @Generated
   public float c() {
      return this.b;
   }

   @Generated
   public float d() {
      return this.c;
   }

   @Generated
   public float e() {
      return this.d;
   }

   @Generated
   public Category f() {
      return this.e;
   }

   @Generated
   public List<dev.leg1tka.gui.components.ModulePanel> g() {
      return this.f;
   }

   @Generated
   public dev.leg1tka.api.animation.TimedAnimation h() {
      return this.i;
   }

   @Generated
   public dev.leg1tka.api.animation.TimedAnimation i() {
      return this.j;
   }

   @Generated
   public dev.leg1tka.api.animation.TimedAnimation j() {
      return this.k;
   }

   @Generated
   public float k() {
      return this.g;
   }

   @Generated
   public float l() {
      return this.h;
   }

   @Generated
   public ClickGuiScreen m() {
      return this.l;
   }

   @Generated
   public void a(float floatValue1) {
      this.a = floatValue1;
   }

   @Generated
   public void b(float floatValue1) {
      this.b = floatValue1;
   }

   @Generated
   public void c(float floatValue1) {
      this.c = floatValue1;
   }

   @Generated
   public void d(float floatValue1) {
      this.d = floatValue1;
   }

   @Generated
   public void e(float floatValue1) {
      this.g = floatValue1;
   }

   @Generated
   public void f(float floatValue1) {
      this.h = floatValue1;
   }
}
