package dev.leg1tka.render.hud;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.implement.EventRender;
import java.awt.Color;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;

public class PlayerHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   private static float f;
   private static float g;
   private static float h;
   private static float i;
   private static float j;
   private static final int k = new Color(255, 255, 255, 255).getRGB();
   private static final int l = new Color(160, 160, 160, 255).getRGB();

   public PlayerHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
   }

   @Override
   public void a(EventRender.Default default1) {
      MatrixStack matrixStack2 = default1.getContext().getMatrices();
      float floatValue3 = this.e.a();
      float floatValue4 = this.e.b();
      dev.leg1tka.render.font.MsdfTextRenderer value5 = dev.leg1tka.render.font.FontCache.a("nunito", 14);
      dev.leg1tka.render.font.MsdfTextRenderer value6 = dev.leg1tka.render.font.FontCache.a("nunito", 12);
      dev.leg1tka.render.font.MsdfTextRenderer value7 = dev.leg1tka.render.font.FontCache.a("iconnew", 14);
      if (value5 != null && value6 != null) {
         int intValue8 = (int)((float)(System.currentTimeMillis() % 2000L) / 2000.0F * 360.0F);
         int intValue9 = e();
         int intValue10 = dev.leg1tka.api.utils.text.ColorUtils.a(8, 8, 10, 200);
         String string11 = "AspectDLC";
         String string12 = "p";
         String string13 = "y";
         String string14 = "b";
         int intValue15 = minecraftClient != null ? minecraftClient.getCurrentFps() : 0;
         int intValue16 = 0;
         if (minecraftClient != null && minecraftClient.player != null && minecraftClient.getNetworkHandler() != null) {
            PlayerListEntry playerListEntry17 = minecraftClient.getNetworkHandler().getPlayerListEntry(minecraftClient.player.getUuid());
            if (playerListEntry17 != null) {
               intValue16 = playerListEntry17.getLatency();
            }
         }

         float floatValue32 = f();
         String string18 = intValue15 + "fps";
         String string19 = intValue16 + "ms";
         String string20 = a(floatValue32) + "tps";
         float floatValue21 = value5.a(string11);
         float floatValue22 = 5.0F;
         if (value7 != null) {
            floatValue22 += value7.a(string12) + 2.0F;
         }

         floatValue22 += value6.a(string18) + 3.0F;
         if (value7 != null) {
            floatValue22 += value7.a(string13) + 2.0F;
         }

         floatValue22 += value6.a(string19) + 3.0F;
         if (value7 != null) {
            floatValue22 += value7.a(string14) + 2.0F;
         }

         floatValue22 += value6.a(string20);
         floatValue22 += 5.0F;
         float floatValue23 = 5.0F + floatValue21 + 5.0F;
         float floatValue24 = floatValue23 + 3.0F + floatValue22;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack2, floatValue3, floatValue4, floatValue24, 15.0F, 4.0F, intValue10);
         int intValue25 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue9, 180);
         dev.leg1tka.render.RenderPrimitives.a(matrixStack2, floatValue3, floatValue4, floatValue24, 15.0F, 4.0F, 4.0F, 4.0F, 4.0F, 0.6F, intValue25);
         float floatValue26 = floatValue3 + floatValue23 + 1.5F;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack2, floatValue26, floatValue4 + 3.0F, 0.5F, 9.0F, 0.0F, dev.leg1tka.api.utils.text.ColorUtils.a(60, 60, 65, 180));
         float floatValue27 = floatValue4 + 4.0F - 0.5F;
         float floatValue28 = floatValue3 + 5.0F;
         value5.a(matrixStack2, string11, floatValue28, floatValue27, dev.leg1tka.api.utils.text.ColorUtils.k(intValue8), dev.leg1tka.api.utils.text.ColorUtils.k(intValue8 + 120));
         float floatValue29 = floatValue4 + 4.0F + 0.7F;
         float floatValue30 = floatValue3 + floatValue23 + 3.0F + 5.0F;
         floatValue30 = this.a(matrixStack2, value7, value6, floatValue30, floatValue27, floatValue29, string12, String.valueOf(intValue15), "fps", intValue9, intValue8);
         floatValue30 += 3.0F;
         floatValue30 = this.a(matrixStack2, value7, value6, floatValue30, floatValue27, floatValue29, string13, String.valueOf(intValue16), "ms", intValue9, intValue8);
         floatValue30 += 3.0F;
         int intValue31 = a(floatValue32, intValue9);
         floatValue30 = this.a(matrixStack2, value7, value6, floatValue30, floatValue27, floatValue29, string14, a(floatValue32), "tps", intValue31, intValue8);
         this.e.c(floatValue24);
         this.e.d(15.0F);
      }
   }

   private float a(MatrixStack matrixStack1, dev.leg1tka.render.font.MsdfTextRenderer value2, dev.leg1tka.render.font.MsdfTextRenderer value3, float floatValue4, float floatValue5, float floatValue6, String string7, String string8, String string9, int intValue10, int intValue11) {
      float floatValue12 = floatValue4;
      if (value2 != null) {
         value2.a(matrixStack1, string7, floatValue4, floatValue6, dev.leg1tka.api.utils.text.ColorUtils.k(intValue11), dev.leg1tka.api.utils.text.ColorUtils.k(intValue11 + 120));
         floatValue12 = floatValue4 + value2.a(string7) + 2.0F;
      }

      value3.a(matrixStack1, string8, floatValue12, floatValue5, k);
      floatValue12 += value3.a(string8);
      value3.a(matrixStack1, string9, floatValue12 - 0.5F, floatValue5, intValue10);
      return floatValue12 + value3.a(string9);
   }

   private static int e() {
      if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null) {
         dev.leg1tka.gui.themes.ThemePalette value0 = AspectDLC.INSTANCE.themeStorage.b().a();
         if (value0 == null) {
            return dev.leg1tka.api.utils.text.ColorUtils.a();
         } else if ("Rainbow".equals(value0.b())) {
            return dev.leg1tka.api.utils.text.ColorUtils.a();
         } else {
            return value0.c != null && value0.c.length > 0 ? value0.c[0] : dev.leg1tka.api.utils.text.ColorUtils.a();
         }
      } else {
         return dev.leg1tka.api.utils.text.ColorUtils.a();
      }
   }

   private static float f() {
      return AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.tpsCalc != null ? Math.max(0.0F, Math.min(20.0F, AspectDLC.INSTANCE.tpsCalc.a())) : 20.0F;
   }

   private static int a(float floatValue0, int intValue1) {
      if (floatValue0 >= 19.0F) {
         return new Color(80, 220, 100, 255).getRGB();
      } else {
         return floatValue0 >= 15.0F ? new Color(240, 200, 60, 255).getRGB() : new Color(230, 70, 70, 255).getRGB();
      }
   }

   private static String a(float floatValue0) {
      int intValue1 = Math.round(floatValue0 * 10.0F);
      return intValue1 / 10 + "." + Math.abs(intValue1 % 10);
   }
}
