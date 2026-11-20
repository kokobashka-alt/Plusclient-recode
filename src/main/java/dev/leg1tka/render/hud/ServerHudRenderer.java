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

import dev.leg1tka.api.events.implement.EventRender;
import net.minecraft.client.network.ServerInfo;

public class ServerHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   private long f = System.currentTimeMillis();

   public ServerHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
   }

   @Override
   public void a(EventRender.Default default1) {
      float floatValue2 = this.e.a();
      float floatValue3 = this.e.b();
      long longValue4 = System.currentTimeMillis();
      float floatValue6 = 18.0F;
      String string7 = "local";
      if (minecraftClient != null) {
         ServerInfo serverInfo8 = minecraftClient.getCurrentServerEntry();
         if (serverInfo8 != null && serverInfo8.address != null && !serverInfo8.address.isEmpty()) {
            string7 = serverInfo8.address;
         }
      }

      String string33 = "unknown";
      if (minecraftClient != null && minecraftClient.player != null) {
         string33 = minecraftClient.player.getName().getString();
      } else if (minecraftClient != null && minecraftClient.getSession() != null) {
         string33 = minecraftClient.getSession().getUsername();
      }

      long longValue9 = longValue4 - this.f;
      long longValue11 = longValue9 / 1000L;
      long longValue13 = longValue11 / 3600L;
      long longValue15 = longValue11 % 3600L / 60L;
      long longValue17 = longValue11 % 60L;
      String string19 = longValue13 + "h " + longValue15 + "m " + longValue17 + "s";
      String string20 = "sessioninfo";
      String string21 = "server: " + string7;
      String string22 = "name: " + string33;
      String string23 = "playtime: " + string19;
      dev.leg1tka.render.font.MsdfTextRenderer value24 = dev.leg1tka.render.font.FontCache.a("nunito", 15);
      float floatValue25 = value24.b(string20);
      float floatValue26 = value24.b(string21);
      float floatValue27 = value24.b(string22);
      float floatValue28 = value24.b(string23);
      float floatValue29 = Math.max(floatValue25, Math.max(floatValue26, Math.max(floatValue27, floatValue28)));
      float floatValue30 = floatValue29 + 10.0F;
      int intValue31 = (int)((float)(longValue4 % 2000L) / 2000.0F * 360.0F);
      int intValue32 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue31);
      dev.leg1tka.render.RenderPrimitives.a(default1.getContext().getMatrices(), floatValue2, floatValue3, floatValue30, floatValue6 + 25.0F, 3.0F, 3.5F, intValue32, this.b());
      value24.f(default1.getContext().getMatrices(), string20, floatValue2 + 3.0F, floatValue3 + 5.0F, -1);
      value24.f(default1.getContext().getMatrices(), string21, floatValue2 + 3.0F, floatValue3 + 18.0F, -1);
      value24.f(default1.getContext().getMatrices(), string22, floatValue2 + 3.0F, floatValue3 + 25.5F, -1);
      value24.f(default1.getContext().getMatrices(), string23, floatValue2 + 3.0F, floatValue3 + 33.5F, -1);
      this.e.d(floatValue6 + 25.0F);
      this.e.c(floatValue30);
      super.a(default1);
   }
}
