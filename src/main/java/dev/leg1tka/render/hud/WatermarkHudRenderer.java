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
import java.util.Arrays;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;

public class WatermarkHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   private static String f;
   private static float g;
   private static int h;
   private boolean i = true;
   private boolean j = true;
   private boolean k = true;
   private boolean l = true;
   private boolean m = true;
   private boolean n = false;
   private boolean o = false;
   private boolean p = true;
   private static String q = null;
   private static int r = -1;
   private static boolean s = false;
   private int t;

   public WatermarkHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
   }

   public boolean e() {
      return this.i;
   }

   public void c(boolean flag1) {
      this.i = flag1;
   }

   public boolean f() {
      return this.j;
   }

   public void d(boolean flag1) {
      this.j = flag1;
   }

   public boolean g() {
      return this.k;
   }

   public void e(boolean flag1) {
      this.k = flag1;
   }

   public boolean h() {
      return this.l;
   }

   public void f(boolean flag1) {
      this.l = flag1;
   }

   public boolean i() {
      return this.m;
   }

   public void g(boolean flag1) {
      this.m = flag1;
   }

   public boolean j() {
      return this.n;
   }

   public void h(boolean flag1) {
      this.n = flag1;
   }

   public boolean k() {
      return this.o;
   }

   public void i(boolean flag1) {
      this.o = flag1;
   }

   public boolean l() {
      return this.p;
   }

   public void j(boolean flag1) {
      this.p = flag1;
   }

   @Override
   public void a(EventRender.Default default1) {
      this.b(default1);
      super.a(default1);
   }

   public void b(EventRender.Default default1) {
      MatrixStack matrixStack2 = default1.getContext().getMatrices();
      float floatValue3 = this.e.a();
      float floatValue4 = this.e.b();
      dev.leg1tka.render.font.MsdfTextRenderer value5 = dev.leg1tka.render.font.FontCache.a("nunito", 15);
      dev.leg1tka.render.font.MsdfTextRenderer value6 = dev.leg1tka.render.font.FontCache.a("iconnew", 14);
      dev.leg1tka.render.font.MsdfTextRenderer value7 = dev.leg1tka.render.font.FontCache.a("iconnew", 15);
      dev.leg1tka.render.font.MsdfTextRenderer value8 = dev.leg1tka.render.font.FontCache.a("icon", 14);
      dev.leg1tka.render.font.MsdfTextRenderer value9 = dev.leg1tka.render.font.FontCache.a("icon", 14);
      dev.leg1tka.render.font.MsdfTextRenderer value10 = value6 != null ? value6 : value8;
      dev.leg1tka.render.font.MsdfTextRenderer value11 = dev.leg1tka.render.font.FontCache.a("nunito", 13);
      float floatValue12 = 16.0F;
      float floatValue13 = 16.0F;
      float floatValue14 = 2.5F;
      String string15 = "AspectDLC";
      int intValue16 = (int)((float)(System.currentTimeMillis() % 2000L) / 2000.0F * 360.0F);
      float floatValue17 = value5.a(string15);
      float floatValue18 = floatValue3 + floatValue13 + floatValue14;
      float floatValue19 = floatValue18 + 4.0F;
      float floatValue20 = floatValue4 + 6.2F;
      int intValue21;
      if (!AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow")) {
         intValue21 = AspectDLC.INSTANCE.themeStorage.b().a().c[0];
      } else {
         intValue21 = dev.leg1tka.api.utils.text.ColorUtils.a();
      }

      dev.leg1tka.render.RenderExecutionMode value22 = this.b();
      float floatValue23 = 3.0F;
      int intValue24 = new Color(255, 255, 255, 255).getRGB();
      float floatValue25 = floatValue4 + 6.8F;
      float floatValue27 = floatValue17 + 8.0F;
      this.a(matrixStack2, floatValue3, floatValue4, floatValue13, floatValue12, intValue21);
      if (value9 != null) {
         float floatValue30 = value9.b("d");
         float floatValue31 = floatValue3 + (floatValue13 - floatValue30) * 0.5F;
         float floatValue32 = floatValue4 + 6.4F;
         value9.a(matrixStack2, "d", floatValue31, floatValue32, dev.leg1tka.api.utils.text.ColorUtils.k(intValue16), dev.leg1tka.api.utils.text.ColorUtils.k(intValue16 + 120));
      }

      this.a(matrixStack2, floatValue18, floatValue4, floatValue27, floatValue12, intValue21);
      int intValue61 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue21, 0.32F);
      dev.leg1tka.render.RenderPrimitives.a(matrixStack2, floatValue19 - 1.2F, floatValue20 - 1.35F, floatValue17 + 2.0F, 7.2F, 3.0F, 5.0F, intValue61);
      value5.a(matrixStack2, string15, floatValue19, floatValue20, dev.leg1tka.api.utils.text.ColorUtils.k(intValue16), dev.leg1tka.api.utils.text.ColorUtils.k(intValue16 + 120));
      float floatValue62 = floatValue18 + floatValue27 + 2.5F;
      float floatValue63 = 15.85F;
      String string33 = "e";
      float floatValue34 = floatValue4 + 7.45F;
      String string35 = "p";
      String string36 = "y";
      float floatValue37 = floatValue4 + 7.25F;
      int intValue38 = minecraftClient != null ? minecraftClient.getCurrentFps() : 0;
      String string39 = String.valueOf(intValue38);
      String string40 = "fps";
      String string41 = string39 + string40;
      int intValue42 = 0;
      if (minecraftClient != null && minecraftClient.player != null && minecraftClient.getNetworkHandler() != null) {
         PlayerListEntry playerListEntry43 = minecraftClient.getNetworkHandler().getPlayerListEntry(minecraftClient.player.getUuid());
         if (playerListEntry43 != null) {
            intValue42 = playerListEntry43.getLatency();
         }
      }

      String string64 = String.valueOf(intValue42);
      String string44 = "ms";
      String string45 = string64 + string44;
      float floatValue46 = floatValue23;
      if (this.i) {
         floatValue46 = floatValue23 + (value10 != null ? value10.a(string35) + 2.0F : 0.0F);
         floatValue46 += value11.a(string41) + 2.0F;
      }

      if (this.j) {
         floatValue46 += value10 != null ? value10.a(string36) + 2.0F : 0.0F;
         floatValue46 += value11.a(string45) + 2.0F;
      }

      if (this.l) {
         String string47 = "b";
         float floatValue48 = this.m();
         String string49 = this.a(floatValue48);
         String string50 = "tps";
         String string51 = string49 + string50;
         floatValue46 += value10 != null ? value10.a(string47) + 2.0F : 0.0F;
         floatValue46 += value11.a(string51) + 2.0F;
      }

      floatValue46 += floatValue23;
      float floatValue69 = floatValue46 - 1.05F;
      this.a(matrixStack2, floatValue62, floatValue4, floatValue69, floatValue63, intValue21);
      float floatValue70 = floatValue62 + floatValue23 + 1.5F;
      if (this.i) {
         if (value10 != null) {
            value10.a(matrixStack2, string35, floatValue70, floatValue37, intValue21, intValue21);
            floatValue70 += value10.a(string35) + 2.0F;
         }

         value11.a(matrixStack2, string39, floatValue70, floatValue25, intValue24);
         value11.a(matrixStack2, string40, floatValue70 + value11.a(string39) - 1.0F, floatValue25, intValue21);
         floatValue70 += value11.a(string41) + 2.0F;
      }

      if (this.j) {
         if (value10 != null) {
            value10.a(matrixStack2, string36, floatValue70, floatValue37, intValue21, intValue21);
            floatValue70 += value10.a(string36) + 2.0F;
         }

         value11.a(matrixStack2, string64, floatValue70, floatValue25, intValue24);
         value11.a(matrixStack2, string44, (double)(floatValue70 + value11.a(string64)) - 0.5, (double)floatValue25, intValue21);
         floatValue70 += value11.a(string45) + 2.0F;
      }

      if (this.l) {
         String string71 = "b";
         float floatValue73 = this.m();
         String string75 = this.a(floatValue73);
         String string52 = "tps";
         String string53 = string75 + string52;
         if (value10 != null) {
            value10.a(matrixStack2, string71, floatValue70, floatValue37, intValue21, intValue21);
            floatValue70 += value10.a(string71) + 2.0F;
         }

         value11.a(matrixStack2, string75, floatValue70, floatValue25, intValue24);
         value11.a(matrixStack2, string52, floatValue70 + value11.a(string75) - 1.0F, floatValue25, intValue21);
      }

      float floatValue72 = floatValue4 + floatValue12 + 2.5F;
      float floatValue74 = 15.85F;
      float floatValue76 = floatValue72 + 6.8F;
      float floatValue77 = floatValue72 + 7.25F;
      dev.leg1tka.render.font.MsdfTextRenderer value78 = dev.leg1tka.render.font.FontCache.a("icon", 16);
      float floatValue54 = 0.0F;
      if (this.k) {
         String string56 = "Singleplayer";
         if (minecraftClient != null) {
            ServerInfo serverInfo57 = minecraftClient.getCurrentServerEntry();
            if (serverInfo57 != null && serverInfo57.address != null && !serverInfo57.address.isEmpty()) {
               string56 = this.a(serverInfo57.address);
            }
         }

         String string80 = "m";
         dev.leg1tka.render.font.MsdfTextRenderer value58 = dev.leg1tka.render.font.FontCache.a("iconnew", 15);
         float floatValue59 = floatValue23;
         if (value58 != null) {
            floatValue59 = floatValue23 + value58.a(string80) + 3.0F;
         }

         floatValue59 += value11.a(string56) + floatValue23;
         if (floatValue59 < 40.0F) {
            floatValue59 = 40.0F;
         }

         this.a(matrixStack2, floatValue3, floatValue72, floatValue59, floatValue74, intValue21);
         float floatValue60 = floatValue3 + floatValue23 + 1.5F;
         if (value58 != null) {
            value58.a(matrixStack2, string80, floatValue60 - 1.0F, floatValue77 - 0.3F, intValue21, intValue21);
            floatValue60 += value58.a(string80) + 3.0F;
         }

         this.a(matrixStack2, string56, floatValue60, floatValue76, intValue21, intValue24);
         floatValue54 += floatValue59;
      }

      float floatValue79 = floatValue13 + floatValue14 + floatValue27 + 2.0F + floatValue69;
      boolean flag81 = this.p;
      float floatValue82 = flag81 ? Math.max(floatValue79, floatValue54) : floatValue79;
      float floatValue84 = flag81 ? floatValue12 + 2.5F + floatValue74 : floatValue12;
      this.e.c(floatValue82);
      this.e.d(floatValue84);
      this.a(matrixStack2, intValue21, value11, value78);
   }

   private void a(MatrixStack matrixStack1, int intValue2, dev.leg1tka.render.font.MsdfTextRenderer value3, dev.leg1tka.render.font.MsdfTextRenderer value4) {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         int intValue5 = minecraftClient.getWindow().getScaledWidth();
         int intValue6 = minecraftClient.getWindow().getScaledHeight();
         int intValue7 = new Color(255, 255, 255, 255).getRGB();
         float floatValue8 = 4.0F;
         float floatValue9 = 4.0F;
         float floatValue10 = 4.0F;
         float floatValue11 = 15.85F;
         float floatValue12 = 2.5F;
         float floatValue13 = 3.0F;
         int intValue14 = minecraftClient.player != null ? (int)Math.floor(minecraftClient.player.getX()) : 0;
         int intValue15 = minecraftClient.player != null ? (int)Math.floor(minecraftClient.player.getY()) : 0;
         int intValue16 = minecraftClient.player != null ? (int)Math.floor(minecraftClient.player.getZ()) : 0;
         String string17 = intValue14 + " " + intValue15 + " " + intValue16;
         String string18 = "x";
         float floatValue19 = floatValue13;
         if (value4 != null) {
            floatValue19 = floatValue13 + value4.b(string18) + 3.0F;
         }

         floatValue19 += value3.a(string17) + floatValue13;
         if (floatValue19 < 40.0F) {
            floatValue19 = 40.0F;
         }

         float floatValue20 = (float)intValue6 - floatValue10 - floatValue11;
         this.a(matrixStack1, floatValue8, floatValue20, floatValue19, floatValue11, intValue2);
         float floatValue22 = floatValue8 + floatValue13 + 1.5F;
         float floatValue23 = floatValue20 + 6.8F;
         float floatValue24 = floatValue20 + 7.25F;
         if (value4 != null) {
            value4.d(matrixStack1, string18, floatValue22, floatValue24 - 0.5F, intValue2);
            floatValue22 += value4.b(string18) + 3.0F;
         }

         value3.a(matrixStack1, string17, floatValue22, floatValue23, intValue7);
         double doubleValue25 = (double)dev.leg1tka.api.utils.math.MathOperations.a();
         String string27 = this.a(doubleValue25);
         String string28 = "bps";
         String string29 = string27 + string28;
         String string30 = "c";
         float floatValue31 = floatValue13;
         if (value4 != null) {
            floatValue31 = floatValue13 + value4.b(string30) + 3.0F;
         }

         floatValue31 += value3.a(string29) + floatValue13;
         if (floatValue31 < 40.0F) {
            floatValue31 = 40.0F;
         }

         float floatValue32 = floatValue20 - floatValue12 - floatValue11;
         this.a(matrixStack1, floatValue8, floatValue32, floatValue31, floatValue11, intValue2);
         float floatValue34 = floatValue8 + floatValue13 + 1.5F;
         float floatValue35 = floatValue32 + 6.8F;
         float floatValue36 = floatValue32 + 7.25F;
         if (value4 != null) {
            value4.d(matrixStack1, string30, floatValue34, floatValue36 - 0.5F, intValue2);
            floatValue34 += value4.b(string30) + 3.0F;
         }

         value3.a(matrixStack1, string27, floatValue34, floatValue35, intValue7);
         value3.a(matrixStack1, string28, floatValue34 + value3.a(string27) - 1.0F, floatValue35, intValue2);
      }
   }

   public void c(EventRender.Default default1) {
      float floatValue2 = this.e.a();
      float floatValue3 = this.e.b();
      MatrixStack matrixStack4 = default1.getContext().getMatrices();
      dev.leg1tka.render.font.MsdfTextRenderer value5 = dev.leg1tka.render.font.FontCache.a("wave", 30);
      String string6 = "AspectDLC";
      int intValue7 = dev.leg1tka.api.utils.text.ColorUtils.k(90);
      int intValue8 = dev.leg1tka.api.utils.text.ColorUtils.k(180);
      int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.k(270);
      int intValue10 = dev.leg1tka.api.utils.text.ColorUtils.u(360);
      float floatValue11 = 95.0F + value5.a("ful");
      dev.leg1tka.render.RenderPrimitives.a(matrixStack4, floatValue2, floatValue3, floatValue11, 12.0F, 10.0F, 15.0F, intValue10, intValue8, intValue7, intValue9);
      value5.a(matrixStack4, string6, floatValue2, floatValue3, intValue7, intValue8);
      this.e.c(Math.max(floatValue11, value5.a(string6)));
      this.e.d(12.0F);
   }

   private String a(double doubleValue1) {
      int intValue3 = (int)Math.round(doubleValue1 * 100.0);
      int intValue4 = Math.abs(intValue3 % 100);
      return intValue3 / 100 + "." + (intValue4 < 10 ? "0" : "") + intValue4;
   }

   private void a(MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, int intValue5, int intValue6) {
      dev.leg1tka.render.font.MsdfTextRenderer value7 = dev.leg1tka.render.font.FontCache.a("nunito", 13);
      String[] stringArray8 = string2.split("\\.");
      if (stringArray8.length < 2) {
         value7.a(matrixStack1, string2, floatValue3, floatValue4, intValue6);
      } else {
         String string9 = String.join(".", Arrays.copyOf(stringArray8, stringArray8.length - 1));
         String string10 = "." + stringArray8[stringArray8.length - 1];
         value7.a(matrixStack1, string9, floatValue3, floatValue4, intValue6);
         float floatValue11 = floatValue3 + value7.a(string9) - 2.0F;
         value7.a(matrixStack1, string10, floatValue11, floatValue4, intValue5);
      }
   }

   private String a(String string1) {
      if (string1 != null && !string1.isEmpty()) {
         String string2 = string1;
         int intValue3 = string1.indexOf(58);
         if (intValue3 > 0) {
            string2 = string1.substring(0, intValue3);
         }

         String[] stringArray4 = string2.split("\\.");
         return stringArray4.length >= 3 ? String.join(".", Arrays.copyOfRange(stringArray4, 1, stringArray4.length)) : string2;
      } else {
         return "";
      }
   }

   private float m() {
      return AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.tpsCalc != null ? Math.max(0.0F, Math.min(20.0F, AspectDLC.INSTANCE.tpsCalc.a())) : 20.0F;
   }

   private String a(float floatValue1) {
      int intValue2 = Math.round(floatValue1 * 10.0F);
      return intValue2 / 10 + "." + Math.abs(intValue2 % 10);
   }
}
