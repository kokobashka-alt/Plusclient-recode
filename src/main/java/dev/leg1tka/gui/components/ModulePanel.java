package dev.leg1tka.gui.components;


import dev.leg1tka.modules.Module;
import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import lombok.Generated;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class ModulePanel extends dev.leg1tka.gui.components.SettingWidget {
   private final Module g;
   private final dev.leg1tka.gui.components.CategoryPanel h;
   private final dev.leg1tka.api.animation.TimedAnimation i = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.n, 280L);
   private final dev.leg1tka.api.animation.TimedAnimation j = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.n, 200L);
   private float k;
   private long l;
   public boolean f;
   private boolean m;
   private boolean n;
   private final ObjectArrayList o = new ObjectArrayList();

   public ModulePanel(Module module1, dev.leg1tka.gui.components.CategoryPanel value2) {
      this.g = module1;
      this.h = value2;
      this.k = module1.isEnabled() ? 1.0F : 0.0F;
      this.i.b(0.0F);

      for (Setting setting4 : module1.getSettings()) {
         Object object5 = null;
         if (setting4 instanceof BooleanSetting booleanSetting6) {
            object5 = new dev.leg1tka.gui.components.BooleanSettingWidget(booleanSetting6);
         } else if (setting4 instanceof NumberSetting numberSetting7) {
            object5 = new dev.leg1tka.gui.components.NumberSettingWidget(numberSetting7);
         } else if (setting4 instanceof ModeSetting modeSetting8) {
            object5 = new dev.leg1tka.gui.components.MultiSettingWidget(modeSetting8);
         } else if (setting4 instanceof MultiSetting multiSetting9) {
            object5 = new dev.leg1tka.gui.components.ModeSettingWidget(multiSetting9);
         } else if (setting4 instanceof dev.leg1tka.settings.KeybindSetting value10) {
            object5 = new dev.leg1tka.gui.components.BindSettingWidget(value10);
         } else if (setting4 instanceof ColorSetting colorSetting11) {
            object5 = new dev.leg1tka.gui.components.ColorSettingWidget(colorSetting11);
         } else if (setting4 instanceof dev.leg1tka.settings.ThemeSetting value12) {
            object5 = new dev.leg1tka.gui.components.ThemeSettingWidget(value12);
         } else if (setting4 instanceof dev.leg1tka.settings.StringSetting value13) {
            object5 = new dev.leg1tka.gui.components.StringSettingWidget(value13);
         }

         if (object5 != null) {
            ((dev.leg1tka.gui.components.SettingWidget)object5).a(this);
            this.o.add(object5);
         }
      }

      if ("Interface".equalsIgnoreCase(module1.getName())) {
         dev.leg1tka.gui.components.ModulePanel.a value14 = new dev.leg1tka.gui.components.ModulePanel.a(this);
         value14.a(this);
         this.o.add(value14);
      }
   }

   public boolean b() {
      return this.m;
   }

   public dev.leg1tka.api.animation.TimedAnimation k() {
      return this.i;
   }

   @Override
   public void a(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4) {
      this.m = dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue2, (double)intValue3, (double)this.b, (double)this.c, (double)this.d, 16.5);
      this.j.a(this.m);
      this.i.a(this.f);
      long longValue5 = System.nanoTime();
      float floatValue7 = this.l == 0L ? 0.016F : Math.min(0.05F, (float)(longValue5 - this.l) / 1.0E9F);
      this.l = longValue5;
      float floatValue8 = this.g.isEnabled() ? 1.0F : 0.0F;
      this.k = this.k + (floatValue8 - this.k) * (1.0F - (float)Math.exp((double)(-floatValue7 * 10.0F)));
      if (Math.abs(floatValue8 - this.k) < 0.002F) {
         this.k = floatValue8;
      }

      if (this.m) {
         dev.leg1tka.render.hud.HudVisibilityState.a();
      }

      float floatValue9 = Math.max(Math.min(this.h.i().d(), 1.0F), 0.0F);
      if (!(floatValue9 < 0.01F)) {
         int intValue10 = dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue9);
         int intValue11 = dev.leg1tka.gui.menu.MenuBackdropRenderer.c();
         int intValue12 = dev.leg1tka.gui.menu.MenuBackdropRenderer.d();
         float floatValue13 = MathHelper.clamp(this.j.d(), 0.0F, 1.0F);
         double doubleValue14 = (double)(System.currentTimeMillis() % 6000L) / 6000.0 * Math.PI * 2.0;
         float floatValue16 = (float)(Math.sin(doubleValue14) * 0.5 + 0.5) * 0.45F;
         int intValue17 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue11, intValue12, floatValue16);
         int intValue18 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue12, intValue11, floatValue16);
         float floatValue19 = this.k * this.k * (3.0F - 2.0F * this.k);
         float floatValue20 = MathHelper.clamp(this.i.d(), 0.0F, 1.0F);
         float floatValue21 = 16.5F + (this.e - 16.5F) * floatValue20;
         if (floatValue19 > 0.01F) {
            int intValue22 = Math.max(1, (int)((50.0F + 30.0F * floatValue13) * floatValue19 * floatValue9));
            float floatValue23 = Math.max(7.0F, this.d * floatValue19);
            dev.leg1tka.render.RenderPrimitives.a(
               matrixStack1, this.b + this.d - floatValue23, this.c, floatValue23, 16.5F, 3.5F, 7.0F, dev.leg1tka.api.utils.text.ColorUtils.d(intValue17, intValue22), dev.leg1tka.api.utils.text.ColorUtils.d(intValue18, intValue22), dev.leg1tka.api.utils.text.ColorUtils.d(intValue17, intValue22), dev.leg1tka.api.utils.text.ColorUtils.d(intValue18, intValue22)
            );
         }

         int intValue40 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a((18.0F + 14.0F * floatValue13 * (1.0F - floatValue19)) * floatValue9));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, this.b, this.c, this.d, floatValue21, 3.5F, intValue40);
         if (floatValue19 > 0.004F) {
            float floatValue41 = Math.max(7.0F, this.d * floatValue19);
            float floatValue24 = this.b + this.d - floatValue41;
            int intValue25 = dev.leg1tka.gui.themes.ThemeAccentResolver.a((150.0F + 20.0F * floatValue13) * floatValue9 * Math.min(1.0F, floatValue19 * 1.6F));
            float floatValue26 = floatValue21 - 16.5F;
            float floatValue27 = floatValue20 * floatValue20 * floatValue20 * (floatValue20 * (floatValue20 * 6.0F - 15.0F) + 10.0F);
            float floatValue28 = Math.min(floatValue26, (this.e - 16.5F) * 0.65F * floatValue27);
            dev.leg1tka.gui.menu.MenuBackdropRenderer.a(
               matrixStack1,
               floatValue24,
               this.c,
               floatValue41,
               16.5F,
               floatValue28,
               3.5F,
               3.5F * (1.0F - floatValue20),
               Math.min(floatValue41, 14.0F * (1.0F - floatValue19)),
               intValue17,
               intValue18,
               (float)intValue25 / 255.0F
            );
         }

         int intValue42 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, (int)((12.0F + 30.0F * floatValue19 + 10.0F * floatValue13) * floatValue9));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, this.b, this.c, this.d, 8.25F, 3.5F, 3.5F, 0.0F, 0.0F, intValue42, intValue42, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 0), dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 0));
         int intValue43 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, Math.max(1, (int)((6.0F + 22.0F * floatValue19) * floatValue9 * (1.0F - floatValue20))));
         int intValue44 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 0);
         float floatValue45 = this.c + 16.5F - 0.8F;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, this.b + 3.0F, floatValue45, (this.d - 6.0F) / 2.0F, 0.6F, 0.0F, intValue44, intValue43, intValue44, intValue43);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, this.b + this.d / 2.0F, floatValue45, (this.d - 6.0F) / 2.0F, 0.6F, 0.0F, intValue43, intValue44, intValue43, intValue44);
         dev.leg1tka.render.font.MsdfTextRenderer value46 = dev.leg1tka.render.font.FontCache.a("nunito", 13);
         if (value46 == null) {
            value46 = dev.leg1tka.render.font.FontCache.a("semibold", 15);
         }

         int intValue47 = a(floatValue19, floatValue9);
         if (value46 != null) {
            if (this.n) {
               b(
                  matrixStack1,
                  value46,
                  "Нажмите клавишу...",
                  this.b + this.d / 2.0F,
                  this.c + 8.25F,
                  dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue10)
               );
            } else {
               String string29 = this.g.getDisplayName();
               int intValue30 = dev.leg1tka.api.utils.text.ColorUtils.a(220, 220, 230, intValue10);
               int intValue31 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue30, intValue47, floatValue19);
               if (floatValue19 > 0.01F) {
                  b(matrixStack1, value46, string29, this.b + this.d / 2.0F, this.c + 9.0F, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, dev.leg1tka.gui.themes.ThemeAccentResolver.a(90.0F * floatValue19 * floatValue9)));
               }

               b(matrixStack1, value46, string29, this.b + this.d / 2.0F, this.c + 8.25F, intValue31);
               if (!this.o.isEmpty()) {
                  int intValue32 = dev.leg1tka.api.utils.text.ColorUtils.a(130, 130, 145, dev.leg1tka.gui.themes.ThemeAccentResolver.a(220.0F * floatValue9));
                  int intValue34 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue32, intValue47, Math.max(floatValue19, floatValue13 * 0.6F));
                  a(matrixStack1, this.b + this.d - 7.5F, this.c + 8.25F, -90.0F + 90.0F * this.i.d(), intValue34);
               }
            }
         }

         if (this.i.d() > 0.01F) {
            float floatValue48 = this.c + 16.5F - (1.0F - this.i.d()) * 8.0F;
            float floatValue49 = this.h.c() + 16.5F;
            float floatValue50 = this.h.c() + this.h.e() - 4.0F;
            float floatValue51 = this.c + 16.5F;
            float floatValue33 = this.c + floatValue21;
            float floatValue52 = Math.max(floatValue51, floatValue49);
            float floatValue35 = Math.min(floatValue33, floatValue50);
            float floatValue36 = Math.max(0.0F, floatValue35 - floatValue52);
            ObjectListIterator objectListIterator37 = this.o.iterator();

            while (objectListIterator37.hasNext()) {
               dev.leg1tka.gui.components.SettingWidget value38 = (dev.leg1tka.gui.components.SettingWidget)objectListIterator37.next();
               value38.h().b(floatValue9);
               value38.i().a(value38.a());
               float floatValue39 = MathHelper.clamp(value38.i().d(), 0.0F, 1.0F);
               if (value38.a() || floatValue39 > 0.0F) {
                  value38.a(this.b + 2.0F);
                  value38.b(floatValue48);
                  value38.c(this.d - 4.0F);
                  dev.leg1tka.render.ScissorStack.a();
                  dev.leg1tka.render.ScissorStack.a((double)this.b, (double)floatValue52, (double)this.d, (double)floatValue36);
                  value38.a(matrixStack1, intValue2, intValue3, floatValue4);
                  dev.leg1tka.render.ScissorStack.b();
                  floatValue48 += value38.c() * floatValue39;
               }
            }
         }
      }
   }

   private static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, int intValue4) {
      float floatValue5 = 4.2F;
      float floatValue6 = 1.3F;
      matrixStack0.push();
      matrixStack0.translate(floatValue1, floatValue2, 0.0F);
      matrixStack0.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue3));

      for (byte byteValue7 = -1; byteValue7 <= 1; byteValue7 += 2) {
         matrixStack0.push();
         matrixStack0.translate((float)byteValue7 * 1.4F, -0.2F, 0.0F);
         matrixStack0.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)byteValue7 * -45.0F));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack0, -floatValue5 / 2.0F, -floatValue6 / 2.0F, floatValue5, floatValue6, floatValue6 / 2.0F, intValue4);
         matrixStack0.pop();
      }

      matrixStack0.pop();
   }

   @Override
   public void a(double doubleValue1, double doubleValue3, int intValue5) {
      if (this.n) {
         if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)this.b, (double)this.c, (double)this.d, 16.5) && intValue5 == 2) {
            this.n = false;
         } else {
            if (intValue5 != 0) {
               this.g.setKeyBind(dev.leg1tka.gui.components.InputBindings.g(intValue5));
            }

            this.n = false;
         }
      } else {
         if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)this.b, (double)this.c, (double)this.d, 16.5)) {
            if (intValue5 == 0) {
               this.g.toggle();
            }

            if (intValue5 == 1 && !this.o.isEmpty()) {
               this.f = !this.f;
            }

            if (intValue5 == 2) {
               this.n = !this.n;
            }
         }

         if (this.f) {
            ObjectListIterator objectListIterator6 = this.o.iterator();

            while (objectListIterator6.hasNext()) {
               dev.leg1tka.gui.components.SettingWidget value7 = (dev.leg1tka.gui.components.SettingWidget)objectListIterator6.next();
               if (value7.a() && value7.i().d() > 0.5F) {
                  value7.a(doubleValue1, doubleValue3, intValue5);
               }
            }
         }
      }
   }

   @Override
   public void b(double doubleValue1, double doubleValue3, int intValue5) {
      if (this.f) {
         ObjectListIterator objectListIterator6 = this.o.iterator();

         while (objectListIterator6.hasNext()) {
            dev.leg1tka.gui.components.SettingWidget value7 = (dev.leg1tka.gui.components.SettingWidget)objectListIterator6.next();
            value7.b(doubleValue1, doubleValue3, intValue5);
         }
      }
   }

   @Override
   public void a(int intValue1, int intValue2, int intValue3) {
      if (this.n) {
         if (intValue1 != 256 && intValue1 != 261 && intValue1 != 259) {
            this.g.setKeyBind(intValue1);
         } else {
            this.g.setKeyBind(-1);
         }

         this.n = false;
      } else {
         if (this.f) {
            ObjectListIterator objectListIterator4 = this.o.iterator();

            while (objectListIterator4.hasNext()) {
               dev.leg1tka.gui.components.SettingWidget value5 = (dev.leg1tka.gui.components.SettingWidget)objectListIterator4.next();
               value5.a(intValue1, intValue2, intValue3);
            }
         }
      }
   }

   public boolean l() {
      return this.n;
   }

   @Override
   public void a(char character1, int intValue2) {
      if (this.f) {
         ObjectListIterator objectListIterator3 = this.o.iterator();

         while (objectListIterator3.hasNext()) {
            dev.leg1tka.gui.components.SettingWidget value4 = (dev.leg1tka.gui.components.SettingWidget)objectListIterator3.next();
            value4.a(character1, intValue2);
         }
      }
   }

   @Generated
   public Module m() {
      return this.g;
   }

   @Generated
   public dev.leg1tka.gui.components.CategoryPanel n() {
      return this.h;
   }

   @Generated
   public dev.leg1tka.api.animation.TimedAnimation o() {
      return this.i;
   }

   @Generated
   public dev.leg1tka.api.animation.TimedAnimation p() {
      return this.j;
   }

   @Generated
   public float q() {
      return this.k;
   }

   @Generated
   public long r() {
      return this.l;
   }

   @Generated
   public boolean s() {
      return this.f;
   }

   @Generated
   public boolean t() {
      return this.m;
   }

   @Generated
   public ObjectArrayList<dev.leg1tka.gui.components.SettingWidget> u() {
      return this.o;
   }

   public static class a extends dev.leg1tka.gui.components.SettingWidget {
      private final dev.leg1tka.gui.components.ModulePanel f;
      private final dev.leg1tka.api.animation.TimedAnimation g = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.n, 300L);

      public a(dev.leg1tka.gui.components.ModulePanel value1) {
         this.f = value1;
      }

      @Override
      public void a(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4) {
         float floatValue5 = this.f.n().i().d();
         if (!(floatValue5 < 0.01F)) {
            float floatValue6 = this.b + 2.0F;
            float floatValue7 = this.c + 1.0F;
            float floatValue8 = this.d - 4.0F;
            float floatValue9 = 14.0F;
            boolean flag10 = dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue2, (double)intValue3, (double)floatValue6, (double)floatValue7, (double)floatValue8, (double)floatValue9);
            this.g.a(flag10);
            if (flag10) {
               dev.leg1tka.render.hud.HudVisibilityState.a();
            }

            int intValue11 = dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue5);
            int intValue12 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(30.0F * floatValue5));
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue6, floatValue7, floatValue8, floatValue9, 3.0F, intValue12);
            dev.leg1tka.render.font.MsdfTextRenderer value13 = dev.leg1tka.render.font.FontCache.a("semibold", 13);
            if (value13 == null) {
               value13 = dev.leg1tka.render.font.FontCache.a("medium", 13);
            }

            dev.leg1tka.gui.components.SettingWidget.a(
               matrixStack1,
               value13,
               "Открыть менеджер тем",
               floatValue6 + 4.0F,
               floatValue7 + 4.0F,
               dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue11)
            );
            this.e = 16.0F;
         }
      }

      @Override
      public void a(double doubleValue1, double doubleValue3, int intValue5) {
         float floatValue6 = this.b + 2.0F;
         float floatValue7 = this.c + 1.0F;
         float floatValue8 = this.d - 4.0F;
         float floatValue9 = 14.0F;
         if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)floatValue6, (double)floatValue7, (double)floatValue8, (double)floatValue9) && intValue5 == 0) {
            dev.leg1tka.gui.themes.CustomThemeRepository value10 = this.f.n().m().d();
            value10.a(!value10.g());
         }
      }

      @Override
      public boolean a() {
         return true;
      }

      @Override
      public float c() {
         return 16.0F;
      }
   }
}
