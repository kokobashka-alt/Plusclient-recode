package dev.leg1tka.gui;

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
import java.util.List;
import lombok.Generated;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class ClickGuiScreen extends Screen implements dev.leg1tka.api.ClientAccess {
   private final List<dev.leg1tka.gui.components.CategoryPanel> c = new ArrayList<>();
   private final dev.leg1tka.gui.components.PanelLabel d;
   private final dev.leg1tka.gui.themes.CustomThemeRepository e;
   private boolean f;
   private static float g;
   private static float h;
   private static float i;
   private static float j;
   private float k;
   private long l = -1L;
   private static long m;
   private static long n;
   private static long o;
   private static long p;
   private static long q;

   public ClickGuiScreen() {
      super(Text.of("AspectDLC ClickGui"));
      this.d = new dev.leg1tka.gui.components.PanelLabel("Поиск...");

      for (Category category4 : Category.values()) {
         if (!category4.a()) {
            this.c.add(new dev.leg1tka.gui.components.CategoryPanel(category4, this));
         }
      }

      this.e = new dev.leg1tka.gui.themes.CustomThemeRepository(this);
      this.f = false;
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
   }

   public void render(DrawContext context, int mouseX, int mouseY, float delta) {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         long longValue5 = System.currentTimeMillis();
         float floatValue7 = this.l < 0L ? 0.0F : Math.min(50.0F, (float)(longValue5 - this.l));
         this.l = longValue5;
         this.k = MathHelper.clamp(this.k + (this.f ? -floatValue7 / 400.0F : floatValue7 / 620.0F), 0.0F, 1.0F);
         if (this.f && this.k <= 0.0F) {
            minecraftClient.setScreen(null);
         } else {
            float floatValue8 = this.a(this.k);
            dev.leg1tka.render.hud.HudVisibilityState.g();
            dev.leg1tka.render.hud.HudVisibilityState.h();
            dev.leg1tka.render.hud.HudVisibilityState.i();
            int intValue9 = minecraftClient.getWindow().getScaledWidth();
            int intValue10 = minecraftClient.getWindow().getScaledHeight();
            if (floatValue8 > 0.02F) {
               dev.leg1tka.render.RenderPrimitives.e(context.getMatrices(), 0.0F, 0.0F, (float)intValue9, (float)intValue10, 0.0F, 20.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, Math.max(1, dev.leg1tka.gui.themes.ThemeAccentResolver.a(120.0F * floatValue8))));
            }

            dev.leg1tka.render.RenderPrimitives.b(context.getMatrices(), 0.0F, 0.0F, (float)intValue9, (float)intValue10, 0.0F, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, dev.leg1tka.gui.themes.ThemeAccentResolver.a(100.0F * floatValue8)));
            float floatValue11 = 101.0F;
            float floatValue12 = 7.5F;
            float floatValue13 = 275.0F;
            float floatValue14 = (float)this.c.size() * (floatValue11 + floatValue12) - floatValue12;
            float floatValue15 = ((float)intValue9 - floatValue14) / 2.0F;
            float floatValue16 = ((float)intValue10 - floatValue13) / 2.0F - 10.0F;
            context.getMatrices().push();

            for (int intValue17 = 0; intValue17 < this.c.size(); intValue17++) {
               dev.leg1tka.gui.components.CategoryPanel value18 = (dev.leg1tka.gui.components.CategoryPanel)this.c.get(intValue17);
               value18.i().b(this.b(intValue17));
               value18.a(floatValue15 + (float)intValue17 * (floatValue11 + floatValue12));
               value18.b(floatValue16 + this.c(intValue17));
               value18.c(floatValue11);
               value18.d(floatValue13);
               value18.a(context.getMatrices(), mouseX, mouseY, delta);
            }

            float floatValue30 = 90.0F;
            float floatValue31 = 18.0F;
            float floatValue19 = (float)intValue9 / 2.0F - floatValue30 / 2.0F;
            float floatValue20 = floatValue16 + floatValue13 + 10.0F + this.c(this.c.size());
            this.d.b = this.b(this.c.size());
            this.d.a(floatValue19, floatValue20, floatValue30, floatValue31);
            this.d.a(context.getMatrices(), mouseX, mouseY, delta);
            this.e.a(20.0F);
            this.e.b(floatValue16);
            this.e.a(context.getMatrices(), mouseX, mouseY, delta);
            dev.leg1tka.render.font.MsdfTextRenderer value21 = dev.leg1tka.render.font.FontCache.a("bold", 14);
            if (value21 == null) {
               value21 = dev.leg1tka.render.font.FontCache.a("semibold", 14);
            }

            if (value21 == null) {
               value21 = dev.leg1tka.render.font.FontCache.a("sf_regular", 14);
            }

            for (dev.leg1tka.gui.components.CategoryPanel value23 : this.c) {
               boolean flag24 = dev.leg1tka.api.utils.math.RectangleHitTest.a((double)mouseX, (double)mouseY, (double)value23.b(), (double)value23.c(), (double)value23.d(), (double)value23.e());

               for (dev.leg1tka.gui.components.ModulePanel value26 : value23.g()) {
                  if (value26.b() && flag24 && this.d.a()) {
                     String string27 = value26.m().getDescription();
                     if (string27 != null && !string27.isEmpty() && !"NULLABLE".equalsIgnoreCase(string27)) {
                        float floatValue28 = value26.e();
                        float floatValue29 = value23.c() - 14.0F;
                        dev.leg1tka.gui.components.SettingWidget.a(context.getMatrices(), value21, string27, floatValue28, floatValue29, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(240.0F * value23.i().d())));
                     }
                  }
               }
            }

            context.getMatrices().pop();
            if (p == 0L) {
               m = GLFW.glfwCreateStandardCursor(221188);
               n = GLFW.glfwCreateStandardCursor(221186);
               o = GLFW.glfwCreateStandardCursor(221188);
               p = GLFW.glfwCreateStandardCursor(221185);
            }

            long longValue32 = dev.leg1tka.render.hud.HudVisibilityState.d() ? m : (dev.leg1tka.render.hud.HudVisibilityState.e() ? n : (dev.leg1tka.render.hud.HudVisibilityState.f() ? o : p));
            this.a(longValue32);
         }
      }
   }

   private void a(long longValue1) {
      if (longValue1 != 0L && longValue1 != q) {
         GLFW.glfwSetCursor(minecraftClient.getWindow().getHandle(), longValue1);
         q = longValue1;
      }
   }

   private float a(float floatValue1) {
      floatValue1 = MathHelper.clamp(floatValue1, 0.0F, 1.0F);
      float floatValue2 = 1.0F - floatValue1;
      return 1.0F - floatValue2 * floatValue2 * floatValue2;
   }

   private float a(int intValue1) {
      int intValue2 = this.c.size() + 1;
      return MathHelper.clamp(this.k * (1.0F + 0.1F * (float)(intValue2 - 1)) - 0.1F * (float)intValue1, 0.0F, 1.0F);
   }

   private float b(int intValue1) {
      return this.a(this.a(intValue1));
   }

   private float c(int intValue1) {
      return (1.0F - this.a(this.a(intValue1))) * 14.0F;
   }

   public float a() {
      return this.a(this.k);
   }

   private void h() {
      if (!this.f) {
         this.f = true;
         if (p != 0L) {
            this.a(p);
         }
      }
   }

   public void removed() {
      if (p != 0L) {
         this.a(p);
      }

      super.removed();
   }

   public boolean a(String string1) {
      return !this.d.a() && !string1.replaceAll(" ", "").toLowerCase().contains(this.d.a.replaceAll(" ", "").toLowerCase());
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.f) {
         return false;
      } else {
         this.e.a(mouseX, mouseY, button);
         this.d.a(mouseX, mouseY, button);
         if (this.d.a()) {
            for (dev.leg1tka.gui.components.CategoryPanel value7 : this.c) {
               if (dev.leg1tka.api.utils.math.RectangleHitTest.a(mouseX, mouseY, (double)value7.b(), (double)value7.c(), (double)value7.d(), (double)value7.e())) {
                  value7.a(mouseX, mouseY, button);
               }
            }
         } else {
            for (dev.leg1tka.gui.components.CategoryPanel value9 : this.c) {
               value9.a(mouseX, mouseY, button);
            }
         }

         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (this.f) {
         return false;
      } else {
         this.e.b(mouseX, mouseY, button);

         for (dev.leg1tka.gui.components.CategoryPanel value7 : this.c) {
            value7.b(mouseX, mouseY, button);
         }

         return super.mouseReleased(mouseX, mouseY, button);
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      if (this.f) {
         return false;
      } else {
         this.e.a(mouseX, mouseY, horizontalAmount, verticalAmount);

         for (dev.leg1tka.gui.components.CategoryPanel value10 : this.c) {
            value10.a(mouseX, mouseY, horizontalAmount, verticalAmount);
         }

         return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
      }
   }

   private boolean i() {
      for (dev.leg1tka.gui.components.CategoryPanel value2 : this.c) {
         for (dev.leg1tka.gui.components.ModulePanel value4 : value2.g()) {
            if (value4.l()) {
               return true;
            }

            ObjectListIterator objectListIterator5 = value4.u().iterator();

            while (objectListIterator5.hasNext()) {
               dev.leg1tka.gui.components.SettingWidget value6 = (dev.leg1tka.gui.components.SettingWidget)objectListIterator5.next();
               if (value6 instanceof dev.leg1tka.gui.components.BindSettingWidget value7 && value7.b()) {
                  return true;
               }

               if (value6 instanceof dev.leg1tka.gui.components.BooleanSettingWidget value8 && value8.b()) {
                  return true;
               }

               if (value6 instanceof dev.leg1tka.gui.components.StringSettingWidget value9 && value9.b()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.i()) {
         for (dev.leg1tka.gui.components.CategoryPanel value7 : this.c) {
            value7.a(keyCode, scanCode, modifiers);
         }

         return true;
      } else if (keyCode == 256) {
         this.h();
         return true;
      } else if (this.f) {
         return false;
      } else {
         this.e.a(keyCode);
         this.d.a(keyCode, scanCode, modifiers);

         for (dev.leg1tka.gui.components.CategoryPanel value5 : this.c) {
            value5.a(keyCode, scanCode, modifiers);
         }

         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public boolean charTyped(char chr, int modifiers) {
      if (this.f) {
         return false;
      } else {
         this.e.a(chr);
         this.d.a(chr, modifiers);

         for (dev.leg1tka.gui.components.CategoryPanel value4 : this.c) {
            value4.a(chr, modifiers);
         }

         return super.charTyped(chr, modifiers);
      }
   }

   public boolean shouldPause() {
      return false;
   }

   @Generated
   public List<dev.leg1tka.gui.components.CategoryPanel> b() {
      return this.c;
   }

   @Generated
   public dev.leg1tka.gui.components.PanelLabel c() {
      return this.d;
   }

   @Generated
   public dev.leg1tka.gui.themes.CustomThemeRepository d() {
      return this.e;
   }

   @Generated
   public boolean e() {
      return this.f;
   }

   @Generated
   public float f() {
      return this.k;
   }

   @Generated
   public long g() {
      return this.l;
   }
}
