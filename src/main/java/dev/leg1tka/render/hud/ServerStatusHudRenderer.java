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
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;

public class ServerStatusHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   private static final int f = new Color(255, 255, 255, 255).getRGB();
   private static final int g = new Color(130, 130, 130, 255).getRGB();
   private static float h;
   private static float i;
   private static float j;
   private static float k;

   public ServerStatusHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
   }

   @Override
   public void a(EventRender.Default default1) {
      MatrixStack matrixStack2 = default1.getContext().getMatrices();
      float floatValue3 = this.e.a();
      float floatValue4 = this.e.b();
      dev.leg1tka.render.font.MsdfTextRenderer value5 = dev.leg1tka.render.font.FontCache.a("nunito", 14);
      dev.leg1tka.render.font.MsdfTextRenderer value6 = dev.leg1tka.render.font.FontCache.a("nunito", 12);
      dev.leg1tka.render.font.MsdfTextRenderer value7 = dev.leg1tka.render.font.FontCache.a("iconnew", 13);
      dev.leg1tka.render.font.MsdfTextRenderer value8 = dev.leg1tka.render.font.FontCache.a("icon1", 13);
      dev.leg1tka.render.font.MsdfTextRenderer value9 = value8 != null ? value8 : value7;
      if (value5 != null && value6 != null) {
         int intValue10 = (int)((float)(System.currentTimeMillis() % 4000L) / 4000.0F * 360.0F);
         int intValue11 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue10);
         int intValue12 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue10 + 120);
         int intValue13 = e();
         String string14 = "AspectDLC";
         String string15 = "d";
         String string16 = "BUILD: BETA";
         String string17 = "o";
         int intValue18 = minecraftClient != null ? minecraftClient.getCurrentFps() : 0;
         String string19 = intValue18 + " Fps";
         String string20 = "c";
         int intValue21 = 0;
         if (minecraftClient != null && minecraftClient.player != null && minecraftClient.getNetworkHandler() != null) {
            PlayerListEntry playerListEntry22 = minecraftClient.getNetworkHandler().getPlayerListEntry(minecraftClient.player.getUuid());
            if (playerListEntry22 != null) {
               intValue21 = playerListEntry22.getLatency();
            }
         }

         String string46 = intValue21 + "ms";
         String string23 = "y";
         float floatValue24 = f();
         String string25 = a(floatValue24) + " TPS";
         String string26 = "p";
         String string27 = "UID: 777";
         String string28 = "e";
         String string29 = "Singleplayer";
         if (minecraftClient != null) {
            ServerInfo serverInfo30 = minecraftClient.getCurrentServerEntry();
            if (serverInfo30 != null && serverInfo30.address != null && !serverInfo30.address.isEmpty()) {
               string29 = serverInfo30.address;
            }
         }

         String string47 = "b";
         String string31 = "•";
         float floatValue32 = value6.a(string31);
         float floatValue33 = 1.5F;
         float floatValue34 = floatValue33 * 2.0F + floatValue32;
         float floatValue35 = 0.0F;
         if (value7 != null) {
            floatValue35 += value7.a(string15) + 1.5F;
         }

         floatValue35 += value5.a(string14);
         floatValue35 += floatValue34;
         if (value7 != null) {
            floatValue35 += value7.a(string17) + 1.5F;
         }

         floatValue35 += value6.a(string16);
         floatValue35 += floatValue34;
         if (value7 != null) {
            floatValue35 += value7.a(string20) + 1.5F;
         }

         floatValue35 += value6.a(string19);
         floatValue35 += floatValue34;
         if (value7 != null) {
            floatValue35 += value7.a(string23) + 1.5F;
         }

         floatValue35 += value6.a(string46);
         float floatValue36 = 0.0F;
         if (value7 != null) {
            floatValue36 += value7.a(string26) + 1.5F;
         }

         floatValue36 += value6.a(string25);
         floatValue36 += floatValue34;
         if (value7 != null) {
            floatValue36 += value7.a(string28) + 1.5F;
         }

         floatValue36 += value6.a(string27);
         floatValue36 += floatValue34;
         if (value9 != null) {
            floatValue36 += value9.a(string47) + 1.5F;
         }

         floatValue36 += value6.a(string29);
         float floatValue37 = 5.0F + floatValue35;
         float floatValue38 = 15.5F;
         float floatValue39 = 5.0F + floatValue36;
         float floatValue40 = 15.5F;
         float floatValue41 = 2.5F;
         float floatValue42 = floatValue4 + floatValue38 + floatValue41;
         this.a(matrixStack2, floatValue3, floatValue4, floatValue37, floatValue38, intValue13);
         this.a(matrixStack2, floatValue3, floatValue42, floatValue39, floatValue40, intValue13);
         float floatValue43 = floatValue3 + 2.5F;
         float floatValue44 = floatValue4 + 2.5F + 3.5F;
         float floatValue45 = floatValue4 + 2.5F + 5.0F;
         if (value7 != null) {
            value7.a(matrixStack2, string15, floatValue43, floatValue45, intValue13);
            floatValue43 += value7.a(string15) + 1.5F;
         }

         value5.a(matrixStack2, string14, floatValue43, floatValue44 - 1.0F, intValue11, intValue12);
         floatValue43 += value5.a(string14);
         floatValue43 += floatValue33;
         value6.a(matrixStack2, string31, floatValue43, floatValue44, g);
         floatValue43 += floatValue32 + floatValue33;
         if (value7 != null) {
            value7.a(matrixStack2, string17, floatValue43, floatValue45, intValue13);
            floatValue43 += value7.a(string17) + 1.5F;
         }

         value6.a(matrixStack2, string16, floatValue43, floatValue44, f);
         floatValue43 += value6.a(string16);
         floatValue43 += floatValue33;
         value6.a(matrixStack2, string31, floatValue43, floatValue44, g);
         floatValue43 += floatValue32 + floatValue33;
         if (value7 != null) {
            value7.a(matrixStack2, string20, floatValue43, floatValue45, intValue13);
            floatValue43 += value7.a(string20) + 1.5F;
         }

         value6.a(matrixStack2, string19, floatValue43, floatValue44, f);
         floatValue43 += value6.a(string19);
         floatValue43 += floatValue33;
         value6.a(matrixStack2, string31, floatValue43, floatValue44, g);
         floatValue43 += floatValue32 + floatValue33;
         if (value7 != null) {
            value7.a(matrixStack2, string23, floatValue43, floatValue45, intValue13);
            floatValue43 += value7.a(string23) + 1.5F;
         }

         value6.a(matrixStack2, string46, floatValue43, floatValue44, f);
         floatValue43 = floatValue3 + 2.5F;
         floatValue44 = floatValue42 + 2.5F + 3.5F;
         floatValue45 = floatValue42 + 2.5F + 5.0F;
         if (value7 != null) {
            value7.a(matrixStack2, string26, floatValue43, floatValue45, intValue13);
            floatValue43 += value7.a(string26) + 1.5F;
         }

         value6.a(matrixStack2, string25, floatValue43, floatValue44, f);
         floatValue43 += value6.a(string25);
         floatValue43 += floatValue33;
         value6.a(matrixStack2, string31, floatValue43, floatValue44, g);
         floatValue43 += floatValue32 + floatValue33;
         if (value7 != null) {
            value7.a(matrixStack2, string28, floatValue43, floatValue45, intValue13);
            floatValue43 += value7.a(string28) + 1.5F;
         }

         value6.a(matrixStack2, string27, floatValue43, floatValue44, f);
         floatValue43 += value6.a(string27);
         floatValue43 += floatValue33;
         value6.a(matrixStack2, string31, floatValue43, floatValue44, g);
         floatValue43 += floatValue32 + floatValue33;
         if (value9 != null) {
            value9.a(matrixStack2, string47, floatValue43, floatValue45, intValue13);
            floatValue43 += value9.a(string47) + 1.5F;
         }

         value6.a(matrixStack2, string29, floatValue43, floatValue44 - 0.5F, f);
         this.e.c(Math.max(floatValue37, floatValue39));
         this.e.d(floatValue38 + floatValue41 + floatValue40);
      }
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

   private static String a(float floatValue0) {
      int intValue1 = Math.round(floatValue0 * 10.0F);
      return intValue1 / 10 + "." + Math.abs(intValue1 % 10);
   }
}
