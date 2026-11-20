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

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Objects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public final class MenuScreenRouting implements dev.leg1tka.api.ClientAccess {
   private static float d;
   private static float e;
   public static final dev.leg1tka.gui.menu.MenuScreenRouting c = new dev.leg1tka.gui.menu.MenuScreenRouting();
   private static float f;
   private static float g;
   private final ArrayList<dev.leg1tka.gui.menu.MenuScreenRouting.b> h = new ArrayList<>();
   private Identifier i = dev.leg1tka.gui.menu.WallpaperSettings.a.a().c();
   private Identifier j = null;
   private float k = 0.0F;
   private float l = 0.0F;
   private float m = 0.0F;
   private float n = 1.0F;
   private float o;
   private float p;
   private float q;
   private float r;
   private float s = 0.0F;
   private float t = 0.0F;
   private long u = 0L;
   private boolean v = false;
   private final dev.leg1tka.gui.menu.MenuScreenRouting.a[] w = new dev.leg1tka.gui.menu.MenuScreenRouting.a[64];
   private dev.leg1tka.gui.menu.WallpaperSettings.BackgroundEffect x = null;
   private dev.leg1tka.gui.menu.WallpaperSettings.BackgroundEffect y = null;
   private float z = 0.0F;

   private MenuScreenRouting() {
      for (int intValue1 = 0; intValue1 < this.w.length; intValue1++) {
         this.w[intValue1] = new dev.leg1tka.gui.menu.MenuScreenRouting.a();
      }
   }

   private static float b() {
      return (float)Math.random();
   }

   private void a(dev.leg1tka.gui.menu.WallpaperSettings.BackgroundEffect backgroundEffect1) {
      for (int intValue2 = 0; intValue2 < this.w.length; intValue2++) {
         dev.leg1tka.gui.menu.MenuScreenRouting.a value3 = this.w[intValue2];
         value3.a = b();
         value3.b = b();
         value3.f = intValue2 % 3 == 0 ? 1.0F : (intValue2 % 3 == 1 ? 0.55F : 0.2F);
         value3.e = (float)((Math.PI * 2) * (double)b());
         value3.g = 6.0F + b() * 10.0F;
         value3.j = b();
         switch (backgroundEffect1) {
            case SNOW:
               value3.c = (4.0E-4F + b() * 4.0E-4F) * (0.5F + value3.f);
               value3.d = (1.0F + b() * 0.8F) * (0.8F + value3.f * 1.6F);
               break;
            case ASH:
               value3.c = (1.8E-4F + b() * 2.2E-4F) * (0.5F + value3.f);
               value3.d = (0.9F + b() * 0.9F) * (0.8F + value3.f * 1.2F);
               value3.g = 14.0F + b() * 18.0F;
               break;
            case EMBER:
               value3.c = (3.5E-4F + b() * 5.0E-4F) * (0.6F + value3.f);
               value3.d = (0.8F + b() * 0.9F) * (0.8F + value3.f * 1.1F);
               value3.g = 10.0F + b() * 16.0F;
               break;
            case DUST:
               float floatValue4 = (float)((Math.PI * 2) * (double)b());
               float floatValue5 = 6.0E-5F + b() * 1.0E-4F;
               value3.h = MathHelper.cos(floatValue4) * floatValue5;
               value3.i = MathHelper.sin(floatValue4) * floatValue5;
               value3.d = (0.7F + b() * 0.7F) * (0.8F + value3.f);
               break;
            case FOG:
               value3.h = (5.0E-5F + b() * 8.0E-5F) * (b() < 0.5F ? -1.0F : 1.0F);
               value3.d = 0.3F + b() * 0.3F;
               value3.b = 0.25F + b() * 0.7F;
         }
      }
   }

   public void a(float floatValue1, float floatValue2) {
      this.s = floatValue1;
      this.t = floatValue2;
   }

   public void a() {
      this.b(this.c());
   }

   public void a(Identifier identifier1) {
      this.j = identifier1;
      this.b(this.c());
   }

   private Identifier c() {
      if (this.j != null) {
         return this.j;
      } else {
         MinecraftClient minecraftClient1 = MinecraftClient.getInstance();
         boolean flag2 = minecraftClient1.currentScreen instanceof TitleScreen || minecraftClient1.currentScreen == null;
         dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme backgroundTheme3 = dev.leg1tka.gui.menu.WallpaperSettings.a.a();
         return flag2 ? backgroundTheme3.c() : backgroundTheme3.d();
      }
   }

   private void b(Identifier identifier1) {
      this.i = identifier1;
      if (this.h.isEmpty()) {
         this.h.add(new dev.leg1tka.gui.menu.MenuScreenRouting.b(identifier1, true));
      } else {
         if (!Objects.equals(((dev.leg1tka.gui.menu.MenuScreenRouting.b)this.h.get(this.h.size() - 1)).a, identifier1)) {
            this.h.add(new dev.leg1tka.gui.menu.MenuScreenRouting.b(identifier1, false));

            while (this.h.size() > 5) {
               this.h.remove(0);
            }
         }
      }
   }

   private static float a(float floatValue0) {
      floatValue0 = MathHelper.clamp(floatValue0, 0.0F, 1.0F);
      return floatValue0 < 0.5F ? 4.0F * floatValue0 * floatValue0 * floatValue0 : 1.0F - (float)Math.pow((double)(-2.0F * floatValue0 + 2.0F), 3.0) / 2.0F;
   }

   private static float a(dev.leg1tka.gui.menu.MenuScreenRouting.b value0) {
      return value0.b ? 1.0F : a(value0.c);
   }

   private void a(MinecraftClient minecraftClient1) {
      if (!this.v) {
         this.v = true;

         for (dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme backgroundTheme5 : dev.leg1tka.gui.menu.WallpaperSettings.b) {
            try {
               AbstractTexture abstractTexture6 = minecraftClient1.getTextureManager().getTexture(backgroundTheme5.c());
               AbstractTexture abstractTexture7 = minecraftClient1.getTextureManager().getTexture(backgroundTheme5.d());
               abstractTexture6.setFilter(true, false);
               abstractTexture7.setFilter(true, false);
            } catch (Throwable throwable8) {
            }
         }
      }
   }

   private void a(MatrixStack matrixStack1, float floatValue2) {
      for (dev.leg1tka.gui.menu.MenuScreenRouting.b value4 : this.h) {
         float floatValue5 = a(value4) * floatValue2;
         if (!(floatValue5 <= 0.003F)) {
            float floatValue6 = value4.b ? 1.0F : 1.0F + 0.03F * (1.0F - a(value4));
            float floatValue7 = this.q * floatValue6;
            float floatValue8 = this.r * floatValue6;
            a(matrixStack1, value4.a, this.o - (floatValue7 - this.q) * 0.5F, this.p - (floatValue8 - this.r) * 0.5F, floatValue7, floatValue8, Math.max(1, (int)(floatValue5 * 255.0F)) << 24 | 16777215);
         }
      }
   }

   public void a(DrawContext drawContext1, float floatValue2) {
      if (!(floatValue2 <= 0.003F) && !this.h.isEmpty()) {
         MatrixStack matrixStack3 = drawContext1.getMatrices();
         this.a(matrixStack3, floatValue2);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
      }
   }

   private static void a(MatrixStack matrixStack0, Identifier identifier1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      float floatValue7 = dev.leg1tka.gui.menu.WallpaperSettings.a.a(identifier1);
      float floatValue8 = floatValue4 / floatValue5;
      float floatValue9 = 0.0F;
      float floatValue10 = 0.0F;
      float floatValue11 = 1.0F;
      float floatValue12 = 1.0F;
      if (floatValue8 > floatValue7) {
         float floatValue13 = floatValue7 / floatValue8;
         floatValue10 = (1.0F - floatValue13) * 0.5F;
         floatValue12 = floatValue10 + floatValue13;
      } else {
         float floatValue14 = floatValue8 / floatValue7;
         floatValue9 = (1.0F - floatValue14) * 0.5F;
         floatValue11 = floatValue9 + floatValue14;
      }

      dev.leg1tka.render.RenderPrimitives.a(matrixStack0, identifier1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue9, floatValue10, floatValue11, floatValue12, intValue6);
   }

   public void a(DrawContext drawContext1, float floatValue2, float floatValue3) {
      if (drawContext1 != null && !(floatValue2 <= 0.0F) && !(floatValue3 <= 0.0F)) {
         long longValue4 = System.nanoTime();
         float floatValue6 = this.u == 0L ? 0.016F : Math.min(0.05F, (float)((double)(longValue4 - this.u) / 1.0E9));
         this.u = longValue4;
         MinecraftClient minecraftClient7 = MinecraftClient.getInstance();
         dev.leg1tka.gui.menu.WallpaperSettings.a.a(minecraftClient7.getTextureManager());
         this.a(minecraftClient7);
         this.b(this.c());
         float floatValue8 = Math.min(floatValue6, 0.034F);

         for (dev.leg1tka.gui.menu.MenuScreenRouting.b value10 : this.h) {
            if (!value10.b && value10.c < 1.0F) {
               value10.c = Math.min(1.0F, value10.c + floatValue8 / 0.62F);
            }
         }

         float floatValue26 = dev.leg1tka.gui.menu.MenuNavigation.c();
         if (floatValue26 != this.m) {
            this.l = this.k;
            this.m = floatValue26;
            this.n = 0.0F;
         }

         this.n = Math.min(1.0F, this.n + floatValue8 / 0.62F);
         this.k = MathHelper.lerp(a(this.n), this.l, this.m);
         float floatValue27 = 0.5F;
         float floatValue11 = 0.5F;
         int intValue12 = minecraftClient7.getWindow().getWidth();
         int intValue13 = minecraftClient7.getWindow().getHeight();
         if (intValue12 > 0 && intValue13 > 0) {
            floatValue27 = MathHelper.clamp((float)(minecraftClient7.mouse.getX() / (double)intValue12), 0.0F, 1.0F);
            floatValue11 = MathHelper.clamp((float)(minecraftClient7.mouse.getY() / (double)intValue13), 0.0F, 1.0F);
         }

         float floatValue14 = Math.min(1.0F, floatValue6 * 3.2F);
         this.s = MathHelper.lerp(floatValue14, this.s, (0.5F - floatValue27) * 2.0F);
         this.t = MathHelper.lerp(floatValue14, this.t, (0.5F - floatValue11) * 2.0F);
         float floatValue15 = 57.0F;
         float floatValue16 = 9.0F;
         float floatValue17 = dev.leg1tka.gui.menu.MenuIdleState.g();
         float floatValue18 = (floatValue2 + floatValue15 * 2.0F) * floatValue17;
         float floatValue19 = (floatValue3 + floatValue16 * 2.0F) * floatValue17;
         float floatValue20 = (floatValue2 - floatValue18) * 0.5F + this.k + this.s * 12.0F;
         float floatValue21 = (floatValue3 - floatValue19) * 0.5F + this.t * 7.0F;
         this.o = floatValue20;
         this.p = floatValue21;
         this.q = floatValue18;
         this.r = floatValue19;

         for (int intValue22 = this.h.size() - 1; intValue22 > 0; intValue22--) {
            if (a((dev.leg1tka.gui.menu.MenuScreenRouting.b)this.h.get(intValue22)) >= 1.0F) {
               this.h.subList(0, intValue22).clear();
               break;
            }
         }

         MatrixStack matrixStack28 = drawContext1.getMatrices();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         this.a(matrixStack28, 1.0F);
         dev.leg1tka.gui.menu.MenuScreenRouting.b value23 = (dev.leg1tka.gui.menu.MenuScreenRouting.b)this.h.get(this.h.size() - 1);
         float floatValue24 = a(value23);
         if (!value23.b && floatValue24 < 1.0F) {
            float floatValue25 = MathHelper.sin((float) Math.PI * floatValue24) * 0.13F;
            dev.leg1tka.render.RenderPrimitives.b(matrixStack28, 0.0F, 0.0F, floatValue2, floatValue3, 0.0F, Math.max(1, (int)(floatValue25 * 255.0F)) << 24);
         }

         dev.leg1tka.gui.menu.WallpaperSettings.BackgroundEffect backgroundEffect29 = c(this.i);
         if (this.x == null) {
            this.x = backgroundEffect29;
            this.a(this.x);
         }

         if (backgroundEffect29 != this.x) {
            this.y = backgroundEffect29;
         }

         if (this.y != null) {
            this.z = Math.max(0.0F, this.z - floatValue6 * 4.0F);
            if (this.z <= 0.0F) {
               this.x = this.y;
               this.y = null;
               this.a(this.x);
            }
         } else {
            this.z = Math.min(1.0F, this.z + floatValue6 * 1.5F);
         }

         this.a(matrixStack28, floatValue2, floatValue3, floatValue6 * 60.0F, this.z);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
      }
   }

   private static dev.leg1tka.gui.menu.WallpaperSettings.BackgroundEffect c(Identifier identifier0) {
      for (dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme backgroundTheme4 : dev.leg1tka.gui.menu.WallpaperSettings.b) {
         if (backgroundTheme4.c().equals(identifier0) || backgroundTheme4.d().equals(identifier0)) {
            return backgroundTheme4.a();
         }
      }

      return dev.leg1tka.gui.menu.WallpaperSettings.BackgroundEffect.SNOW;
   }

   private void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5) {
      if (!(floatValue5 < 0.01F)) {
         int intValue6 = this.x == dev.leg1tka.gui.menu.WallpaperSettings.BackgroundEffect.FOG ? 10 : this.w.length;

         for (int intValue7 = 0; intValue7 < intValue6; intValue7++) {
            dev.leg1tka.gui.menu.MenuScreenRouting.a value8 = this.w[intValue7];
            float floatValue9 = 0.6F + value8.f * 1.4F;
            float floatValue10 = this.s * 12.0F * floatValue9;
            float floatValue11 = this.t * 7.0F * floatValue9;
            switch (this.x) {
               case SNOW:
               case ASH:
                  value8.b = value8.b + value8.c * floatValue4;
                  if (value8.b > 1.05F) {
                     value8.b = -0.05F;
                     value8.a = b();
                  }

                  value8.e = value8.e + (this.x == dev.leg1tka.gui.menu.WallpaperSettings.BackgroundEffect.ASH ? 0.007F : 0.012F) * floatValue4 * (0.6F + value8.f * 0.6F);
                  float floatValue20 = value8.a * floatValue2 + MathHelper.sin(value8.e) * value8.g + floatValue10;
                  float floatValue23 = value8.b * floatValue3 + floatValue11;
                  float floatValue25 = value8.f > 0.9F ? 0.22F : 0.3F + (1.0F - value8.f) * 0.3F;
                  int intValue27 = this.x == dev.leg1tka.gui.menu.WallpaperSettings.BackgroundEffect.ASH ? 12171714 : 16777215;
                  if (this.x == dev.leg1tka.gui.menu.WallpaperSettings.BackgroundEffect.ASH) {
                     floatValue25 *= 0.75F;
                  }

                  a(matrixStack1, floatValue20, floatValue23, value8.d, floatValue25 * floatValue5, intValue27);
                  break;
               case EMBER:
                  value8.b = value8.b - value8.c * floatValue4;
                  if (value8.b < -0.05F) {
                     value8.b = 1.05F;
                     value8.a = b();
                  }

                  value8.e += 0.02F * floatValue4;
                  float floatValue19 = value8.a * floatValue2 + MathHelper.sin(value8.e * 0.6F) * value8.g + floatValue10;
                  float floatValue22 = value8.b * floatValue3 + floatValue11;
                  float floatValue24 = 0.55F + 0.45F * MathHelper.sin(value8.e * 2.3F + value8.j * 6.0F);
                  float floatValue26 = 1.0F - MathHelper.clamp((1.0F - value8.b) * 1.2F, 0.0F, 1.0F) * 0.6F;
                  int intValue28 = value8.j < 0.5F ? 16757852 : 16742981;
                  float floatValue17 = (0.35F + (1.0F - value8.f) * 0.35F) * floatValue24 * floatValue26;
                  if (value8.f > 0.9F) {
                     b(matrixStack1, floatValue19, floatValue22, value8.d * 3.2F, floatValue17 * 0.35F * floatValue5, intValue28);
                  }

                  a(matrixStack1, floatValue19, floatValue22, value8.d, floatValue17 * floatValue5, intValue28);
                  break;
               case DUST:
                  value8.a = value8.a + value8.h * floatValue4;
                  value8.b = value8.b + value8.i * floatValue4;
                  if (value8.a < -0.03F) {
                     value8.a = 1.03F;
                  }

                  if (value8.a > 1.03F) {
                     value8.a = -0.03F;
                  }

                  if (value8.b < -0.03F) {
                     value8.b = 1.03F;
                  }

                  if (value8.b > 1.03F) {
                     value8.b = -0.03F;
                  }

                  value8.e += 0.015F * floatValue4;
                  float floatValue18 = 0.35F + 0.65F * (0.5F + 0.5F * MathHelper.sin(value8.e + value8.j * 6.0F));
                  float floatValue21 = (0.22F + (1.0F - value8.f) * 0.25F) * floatValue18;
                  a(matrixStack1, value8.a * floatValue2 + floatValue10, value8.b * floatValue3 + floatValue11, value8.d, floatValue21 * floatValue5, 16773590);
                  break;
               case FOG:
                  value8.a = value8.a + value8.h * floatValue4;
                  if (value8.a < -0.6F) {
                     value8.a = 1.2F;
                  }

                  if (value8.a > 1.2F) {
                     value8.a = -0.6F;
                  }

                  value8.e += 0.004F * floatValue4;
                  float floatValue12 = value8.d * floatValue2;
                  float floatValue13 = floatValue12 * 0.32F;
                  float floatValue14 = value8.a * floatValue2 + floatValue10 * 0.5F;
                  float floatValue15 = value8.b * floatValue3 + MathHelper.sin(value8.e) * 6.0F + floatValue11 * 0.5F - floatValue13 * 0.5F;
                  float floatValue16 = (0.045F + value8.j * 0.04F) * (0.7F + 0.3F * MathHelper.sin(value8.e * 1.7F));
                  dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue14, floatValue15, floatValue12, floatValue13, floatValue13 * 0.5F, floatValue13 * 0.8F, (int)(Math.max(0.004F, floatValue16 * floatValue5) * 255.0F) << 24 | 16777215);
            }
         }
      }
   }

   private static void a(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      int intValue6 = Math.max(1, Math.min(255, (int)(floatValue4 * 255.0F)));
      dev.leg1tka.render.RenderPrimitives.a(matrixStack0, floatValue1, floatValue2, floatValue3, intValue6 << 24 | intValue5);
   }

   private static void b(MatrixStack matrixStack0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, int intValue5) {
      int intValue6 = Math.max(1, Math.min(255, (int)(floatValue4 * 255.0F)));
      dev.leg1tka.render.RenderPrimitives.a(matrixStack0, floatValue1 - floatValue3 * 0.25F, floatValue2 - floatValue3 * 0.25F, floatValue3 * 0.5F, floatValue3 * 0.5F, floatValue3 * 0.25F, floatValue3 * 0.5F, intValue6 << 24 | intValue5);
   }

   static class a {
      float a;
      float b;
      float c;
      float d;
      float e;
      float f;
      float g;
      float h;
      float i;
      float j;

      a() {
      }
   }

   static class b {
      final Identifier a;
      final boolean b;
      float c;

      b(Identifier identifier1, boolean flag2) {
         this.a = identifier1;
         this.b = flag2;
         this.c = flag2 ? 1.0F : 0.0F;
      }
   }
}
