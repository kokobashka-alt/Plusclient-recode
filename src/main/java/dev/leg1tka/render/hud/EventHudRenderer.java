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
import java.lang.reflect.Field;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class EventHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   private static float f;
   private static float g;
   private static float h;
   private static final int i = dev.leg1tka.api.utils.text.ColorUtils.a(70, 220, 70, 255);
   private static final int j = dev.leg1tka.api.utils.text.ColorUtils.a(220, 70, 70, 255);
   private static final int k = dev.leg1tka.api.utils.text.ColorUtils.a(255, 170, 0, 255);
   private static final int l = dev.leg1tka.api.utils.text.ColorUtils.a(66, 165, 245, 255);
   private static final int m = dev.leg1tka.api.utils.text.ColorUtils.a(164, 66, 255, 255);
   private final dev.leg1tka.api.animation.Animation n = new dev.leg1tka.api.animation.Animation(60.0F, 10.0F, dev.leg1tka.api.animation.Easings.k);
   private final dev.leg1tka.api.animation.Animation o = new dev.leg1tka.api.animation.Animation(0.0F, 8.0F, dev.leg1tka.api.animation.Easings.F);
   private String p = null;
   private boolean q = false;
   private long r = 0L;
   private static final Pattern s = Pattern.compile("(\\d+)(?!.*\\d)");

   public EventHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
   }

   @Override
   public void a(EventRender.Default default1) {
      if (minecraftClient != null && minecraftClient.player != null && minecraftClient.world != null) {
         MatrixStack matrixStack2 = default1.getContext().getMatrices();
         int intValue3 = minecraftClient.getWindow().getScaledWidth();
         dev.leg1tka.render.font.MsdfTextRenderer value4 = dev.leg1tka.render.font.FontCache.a("nunito", 13);
         dev.leg1tka.render.font.MsdfTextRenderer value5 = dev.leg1tka.render.font.FontCache.a("nunito", 12);
         dev.leg1tka.render.font.MsdfTextRenderer value6 = dev.leg1tka.render.font.FontCache.a("iconnew", 14);
         if (value4 != null && value5 != null) {
            int intValue7 = this.i();
            this.f();
            long longValue8 = System.currentTimeMillis();
            boolean flag10 = this.p != null && longValue8 < this.r;
            ClientBossBar clientBossBar11 = flag10 ? null : this.g();
            String string12;
            int intValue13;
            if (flag10) {
               string12 = this.p;
               intValue13 = this.q ? i : j;
            } else if (clientBossBar11 != null) {
               string12 = clientBossBar11.getName().getString();
               intValue13 = dev.leg1tka.api.utils.text.ColorUtils.a(200, 50, 50, 255);
            } else {
               string12 = "AspectDLC Client";
               intValue13 = intValue7;
            }

            float floatValue14 = 6.0F;
            float floatValue15 = 6.0F;
            float floatValue16 = floatValue15 + floatValue14 + 4.0F + value4.a(string12) + floatValue15;
            this.n.animateTo(floatValue16);
            this.o.animateTo(1.0F);
            float floatValue17 = this.o.getValue();
            float floatValue18 = Math.min(1.0F, floatValue17 * 1.5F);
            float floatValue19 = this.n.getValue();
            float floatValue20 = 16.0F;
            String string21 = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
            float floatValue22 = value4.a(string21);
            int intValue23 = this.h();
            boolean flag24 = !minecraftClient.isInSingleplayer() && intValue23 < 999;
            float floatValue25 = flag24 ? 14.5F : 0.0F;
            float floatValue26 = floatValue22 + 4.0F + floatValue19 + (flag24 ? 4.0F + floatValue25 : 0.0F);
            float floatValue27 = (float)intValue3 / 2.0F;
            float floatValue28 = 5.0F + floatValue20 / 2.0F;
            matrixStack2.push();
            matrixStack2.translate(floatValue27, floatValue28, 0.0F);
            matrixStack2.scale(floatValue17, floatValue17, 1.0F);
            matrixStack2.translate(-floatValue27, -floatValue28, 0.0F);
            float floatValue29 = floatValue27 - floatValue26 / 2.0F;
            float floatValue30 = floatValue29 + floatValue22 + 4.0F;
            float floatValue31 = 5.0F;
            float floatValue32 = floatValue31 + (floatValue20 - value4.a()) / 2.0F;
            value4.a(matrixStack2, string21, floatValue29, floatValue32, dev.leg1tka.api.utils.text.ColorUtils.d(-1, (int)(255.0F * floatValue18)));
            int intValue33 = dev.leg1tka.api.utils.text.ColorUtils.a(10, 10, 10, (int)(230.0F * floatValue18));
            if (dev.leg1tka.render.RenderPrimitives.b()) {
               dev.leg1tka.render.RenderPrimitives.a(matrixStack2, floatValue30, floatValue31, floatValue19, floatValue20, 7.0F, intValue7, false, this.b());
            } else {
               dev.leg1tka.render.RenderPrimitives.d(matrixStack2, floatValue30, floatValue31, floatValue19, floatValue20, 7.0F, intValue33);
               dev.leg1tka.render.RenderPrimitives.b(matrixStack2, floatValue30, floatValue31, floatValue19, floatValue20, 7.0F, intValue33);
            }

            if (flag10) {
               this.b(matrixStack2, value4, floatValue30, floatValue31, floatValue19, floatValue20, string12, intValue13, floatValue18);
            } else if (clientBossBar11 != null) {
               this.a(matrixStack2, value4, value5, floatValue30, floatValue31, floatValue19, floatValue20, clientBossBar11, floatValue18, intValue7);
            } else {
               this.a(matrixStack2, value4, floatValue30, floatValue31, floatValue19, floatValue20, string12, intValue13, floatValue18);
            }

            if (flag24) {
               this.a(matrixStack2, floatValue30 + floatValue19 + 4.0F, floatValue31, floatValue20, intValue23, floatValue18);
            }

            matrixStack2.pop();
            this.e.c(floatValue26);
            this.e.d(floatValue20);
            this.e.a(floatValue29);
            this.e.b(floatValue31);
            super.a(default1);
         }
      }
   }

   private void a(MatrixStack matrixStack1, dev.leg1tka.render.font.MsdfTextRenderer value2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, String string7, int intValue8, float floatValue9) {
      float floatValue10 = 6.0F;
      float floatValue11 = floatValue3 + 6.0F;
      float floatValue12 = floatValue4 + (floatValue6 - floatValue10) / 2.0F;
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue11, floatValue12, floatValue10, floatValue10, floatValue10 / 2.0F, dev.leg1tka.api.utils.text.ColorUtils.d(intValue8, (int)(255.0F * floatValue9)));
      value2.a(matrixStack1, string7, floatValue11 + floatValue10 + 4.0F, floatValue4 + (floatValue6 - value2.a()) / 2.0F, dev.leg1tka.api.utils.text.ColorUtils.d(-1, (int)(255.0F * floatValue9)));
   }

   private void b(MatrixStack matrixStack1, dev.leg1tka.render.font.MsdfTextRenderer value2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, String string7, int intValue8, float floatValue9) {
      float floatValue10 = 7.0F;
      float floatValue11 = floatValue3 + 6.0F;
      float floatValue12 = floatValue4 + (floatValue6 - floatValue10) / 2.0F;
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue11, floatValue12, floatValue10, floatValue10, 3.5F, dev.leg1tka.api.utils.text.ColorUtils.d(intValue8, (int)(255.0F * floatValue9)));
      value2.a(matrixStack1, string7, floatValue11 + floatValue10 + 5.0F, floatValue4 + (floatValue6 - value2.a()) / 2.0F, dev.leg1tka.api.utils.text.ColorUtils.d(-1, (int)(255.0F * floatValue9)));
   }

   private void a(MatrixStack matrixStack1, dev.leg1tka.render.font.MsdfTextRenderer value2, dev.leg1tka.render.font.MsdfTextRenderer value3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, ClientBossBar clientBossBar8, float floatValue9, int intValue10) {
      String string11 = clientBossBar8.getName().getString();
      Matcher matcher12 = s.matcher(string11);
      String string13 = matcher12.find() ? matcher12.group(1) + "s" : "??s";
      String string14 = string11.replaceAll(",?\\s*до конца.*$", "")
         .replaceAll(
            "Телепортация.*",
            "Телепортация"
         )
         .trim();
      float floatValue15 = value2.a(string13);
      float floatValue16 = value2.a(string14);
      float floatValue17 = floatValue15 + 8.0F;
      float floatValue18 = floatValue4 + (floatValue6 - floatValue17 - 4.0F - floatValue16) / 2.0F;
      float floatValue19 = floatValue5 + (floatValue7 - value2.a()) / 2.0F;
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue18 - 2.0F, floatValue5 + 2.0F, floatValue17, floatValue7 - 4.0F, 3.0F, dev.leg1tka.api.utils.text.ColorUtils.a(200, 50, 50, (int)(200.0F * floatValue9)));
      value2.a(matrixStack1, string13, floatValue18 + (floatValue17 - floatValue15) / 2.0F - 2.0F, floatValue19, dev.leg1tka.api.utils.text.ColorUtils.d(-1, (int)(255.0F * floatValue9)));
      value2.a(matrixStack1, string14, floatValue18 + floatValue17 + 4.0F, floatValue19, dev.leg1tka.api.utils.text.ColorUtils.d(-1, (int)(255.0F * floatValue9)));
   }

   private void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, int intValue5, float floatValue6) {
      int intValue7 = intValue5 <= 60 ? 4 : (intValue5 <= 100 ? 3 : (intValue5 <= 150 ? 2 : (intValue5 <= 250 ? 1 : 0)));
      float[] floatValueArray8 = new float[]{3.0F, 5.0F, 7.0F, 9.0F};
      float floatValue9 = 2.5F;
      float floatValue10 = 1.5F;
      float floatValue11 = floatValue3 + floatValue4 - 2.0F;

      for (int intValue12 = 0; intValue12 < 4; intValue12++) {
         float floatValue13 = floatValueArray8[intValue12];
         int intValue14 = intValue12 < intValue7 ? dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, (int)(210.0F * floatValue6)) : dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, (int)(45.0F * floatValue6));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue2 + (float)intValue12 * (floatValue9 + floatValue10), floatValue11 - floatValue13, floatValue9, floatValue13, 0.8F, intValue14);
      }
   }

   private void f() {
      List list1 = dev.leg1tka.render.hud.NotificationQueue.a();
      if (!list1.isEmpty()) {
         dev.leg1tka.render.hud.NotificationQueue.a value2 = (dev.leg1tka.render.hud.NotificationQueue.a)list1.get(list1.size() - 1);
         String string3 = value2.a() ? value2.d : value2.a;
         if (!value2.a()) {
            string3 = value2.a + (value2.c ? " вкл" : " выкл");
         }

         this.p = string3;
         this.q = value2.c;
         this.r = value2.f + 2500L + 500L;
      }

      if (this.p != null && System.currentTimeMillis() >= this.r) {
         this.p = null;
      }
   }

   private ClientBossBar g() {
      if (minecraftClient.inGameHud == null) {
         return null;
      } else {
         BossBarHud bossBarHud1 = minecraftClient.inGameHud.getBossBarHud();
         if (bossBarHud1 == null) {
            return null;
         } else {
            try {
               Field field2 = BossBarHud.class.getDeclaredField("bossBars");
               field2.setAccessible(true);
               Map<?, ClientBossBar> map3 = (Map)field2.get(bossBarHud1);
               if (map3 == null) {
                  return null;
               }

               for (ClientBossBar clientBossBar5 : map3.values()) {
                  String string6 = clientBossBar5.getName().getString().toLowerCase();
                  if (string6.contains("pvp")
                     || string6.contains("пвп")
                     || string6.contains("кт")
                     || string6.contains("FireworkESP")
                     || string6.contains("телепортация")
                     || string6.contains("до конца")) {
                     return clientBossBar5;
                  }
               }
            } catch (Exception exception7) {
            }

            return null;
         }
      }
   }

   private int h() {
      if (minecraftClient.player != null && minecraftClient.getNetworkHandler() != null) {
         PlayerListEntry playerListEntry1 = minecraftClient.getNetworkHandler().getPlayerListEntry(minecraftClient.player.getUuid());
         return playerListEntry1 != null ? playerListEntry1.getLatency() : 0;
      } else {
         return 0;
      }
   }

   private int i() {
      if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null) {
         dev.leg1tka.gui.themes.ThemePalette value1 = AspectDLC.INSTANCE.themeStorage.b().a();
         if (value1 == null) {
            return dev.leg1tka.api.utils.text.ColorUtils.a();
         } else if ("Rainbow".equals(value1.b())) {
            return dev.leg1tka.api.utils.text.ColorUtils.a();
         } else {
            return value1.c != null && value1.c.length > 0 ? value1.c[0] : dev.leg1tka.api.utils.text.ColorUtils.a();
         }
      } else {
         return dev.leg1tka.api.utils.text.ColorUtils.a();
      }
   }

   public static void e() {
      MinecraftClient minecraftClient0 = MinecraftClient.getInstance();
      if (minecraftClient0 != null && minecraftClient0.player != null) {
         EquipmentSlot[] equipmentSlotArray1 = new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD};
         String[] stringArray2 = new String[]{
            "Ботинки",
            "Поножи",
            "Нагрудник",
            "Шлем"
         };

         for (int intValue3 = 0; intValue3 < equipmentSlotArray1.length; intValue3++) {
            ItemStack itemStack4 = minecraftClient0.player.getEquippedStack(equipmentSlotArray1[intValue3]);
            if (!itemStack4.isEmpty() && itemStack4.isDamageable()) {
               float floatValue5 = (float)(itemStack4.getMaxDamage() - itemStack4.getDamage()) / (float)itemStack4.getMaxDamage();
               if (floatValue5 <= 0.1F && floatValue5 > 0.0F) {
                  dev.leg1tka.render.hud.NotificationQueue.a(stringArray2[intValue3] + " — низкая прочность!", "!");
                  return;
               }
            }
         }
      }
   }
}
