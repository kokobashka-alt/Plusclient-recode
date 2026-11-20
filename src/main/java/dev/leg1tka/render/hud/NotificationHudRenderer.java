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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class NotificationHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   private static float f;
   private static float g;
   private static float h;
   private final Map<dev.leg1tka.render.hud.NotificationQueue.a, dev.leg1tka.api.animation.Animation> i = new HashMap<>();
   private final Map j = new HashMap();
   private final Set k = new HashSet();
   private long l = System.currentTimeMillis();

   public NotificationHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
      if (value1 != null) {
         value1.c(110.0F);
         value1.d(12.0F);
      }
   }

   private dev.leg1tka.render.font.MsdfTextRenderer a(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("nunito", intValue1);
   }

   private dev.leg1tka.render.font.MsdfTextRenderer b(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("icon", intValue1);
   }

   private dev.leg1tka.render.font.MsdfTextRenderer c(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("iconsminced", intValue1);
   }

   @Override
   public void a(EventRender.Default default1) {
      if (minecraftClient != null) {
         long longValue2 = System.currentTimeMillis();
         float floatValue4 = (float)(longValue2 - this.l) / 1000.0F;
         this.l = longValue2;
         boolean flag5 = minecraftClient.currentScreen instanceof ChatScreen;
         List<dev.leg1tka.render.hud.NotificationQueue.a> list6 = dev.leg1tka.render.hud.NotificationQueue.a();
         boolean flag7 = false;
         List<dev.leg1tka.render.hud.NotificationQueue.a> list8;
         if (list6.isEmpty()) {
            this.i.clear();
            this.j.clear();
            if (!flag5) {
               super.a(default1);
               return;
            }

            flag7 = true;
            list8 = List.of(
               new dev.leg1tka.render.hud.NotificationQueue.a(
                  "Тестовое уведомление",
                  "b",
                  true,
                  null,
                  null,
                  longValue2
               )
            );
         } else {
            list8 = list6;
         }

         int intValue9;
         if (!AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow")) {
            intValue9 = AspectDLC.INSTANCE.themeStorage.b().a().c[0];
         } else {
            intValue9 = dev.leg1tka.api.utils.text.ColorUtils.a();
         }

         int intValue10 = minecraftClient.getWindow().getScaledWidth();
         int intValue11 = minecraftClient.getWindow().getScaledHeight();
         if (this.e.a() <= 0.5F && this.e.b() <= 0.5F) {
            this.e.a((float)intValue10 / 2.0F - 55.0F);
            this.e.b((float)intValue11 - 35.0F);
            this.e.c(110.0F);
            this.e.d(12.0F);
         }

         if (this.e.c() <= 10.0F) {
            this.e.c(110.0F);
            this.e.d(12.0F);
         }

         float floatValue12 = this.e.a() + 55.0F;
         float floatValue13 = this.e.b();
         long longValue14 = System.currentTimeMillis();
         MatrixStack matrixStack16 = default1.getContext().getMatrices();
         float floatValue17 = 0.0F;

         for (dev.leg1tka.render.hud.NotificationQueue.a value19 : list8) {
            dev.leg1tka.api.animation.Animation value20 = this.i.computeIfAbsent(value19, value0 -> new dev.leg1tka.api.animation.Animation(0.0F, 10.5F, dev.leg1tka.api.animation.Easings.h));
            value20.animateTo(1.0F);
            float floatValue21 = flag7 ? 1.0F : value20.getValue();
            long longValue22 = longValue14 - value19.f;
            float floatValue24 = floatValue21;
            if (!flag7 && longValue22 > 2250L) {
               floatValue24 = MathHelper.clamp((float)(2500L - longValue22) / 250.0F, 0.0F, 1.0F) * floatValue21;
            }

            if (!(floatValue24 <= 0.01F)) {
               int intValue25 = (int)(255.0F * floatValue24);
               String string26 = value19.a() ? value19.d : value19.a;
               if (string26 != null) {
                  string26 = string26.replace(
                        "Зачарованное золотое яблоко",
                        "Чарка"
                     )
                     .replace(
                        "Зачарованное яблоко",
                        "Чарка"
                     );
               }

               float floatValue27 = this.a(12).b(string26 != null ? string26 : "");
               String string28 = "";
               float floatValue29;
               if (flag7) {
                  string28 = "Вкл";
                  floatValue29 = 110.0F;
               } else if (!value19.a()) {
                  string28 = value19.c ? "Вкл" : "Выкл";
                  float floatValue30 = this.a(11).b(string28);
                  floatValue29 = 17.5F + floatValue27 + 6.0F + floatValue30 + 5.0F;
               } else {
                  floatValue29 = 17.5F + floatValue27 + 6.0F;
               }

               if (floatValue29 < 65.0F) {
                  floatValue29 = 65.0F;
               }

               float floatValue47 = floatValue13 + floatValue17;
               Float float31 = (Float)this.j.get(value19);
               if (float31 != null && !flag7) {
                  float31 = float31 + (floatValue47 - float31) * Math.min(1.0F, 14.0F * floatValue4);
               } else {
                  float31 = floatValue47;
               }

               this.j.put(value19, float31);
               float floatValue32 = flag7 ? 0.0F : (1.0F - floatValue21) * 4.0F;
               float floatValue33 = floatValue12 - floatValue29 / 2.0F;
               floatValue33 = MathHelper.clamp(floatValue33, 4.0F, (float)intValue10 - floatValue29 - 4.0F);
               float floatValue34 = float31 + floatValue32;
               int intValue35 = intValue9 >> 16 & 0xFF;
               int intValue36 = intValue9 >> 8 & 0xFF;
               int intValue37 = intValue9 & 0xFF;
               int intValue38 = (int)(12.0F + (float)intValue35 * 0.08F);
               int intValue39 = (int)(12.0F + (float)intValue36 * 0.08F);
               int intValue40 = (int)(14.0F + (float)intValue37 * 0.08F);
               int intValue41 = (int)(155.0F * floatValue24);
               int intValue42 = dev.leg1tka.api.utils.text.ColorUtils.a(Math.min(intValue38, 255), Math.min(intValue39, 255), Math.min(intValue40, 255), intValue41);
               dev.leg1tka.render.RenderPrimitives.e(matrixStack16, floatValue33, floatValue34, floatValue29, 12.0F, 3.0F, 16.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, (int)(255.0F * floatValue24)));
               dev.leg1tka.render.RenderPrimitives.b(matrixStack16, floatValue33, floatValue34, floatValue29, 12.0F, 3.0F, intValue42);
               if (value19.b()) {
                  matrixStack16.push();
                  matrixStack16.translate(floatValue33 + 3.0F, floatValue34 + 1.5F, 0.0F);
                  matrixStack16.scale(0.5625F, 0.5625F, 1.0F);
                  default1.getContext().drawItem(value19.e, 0, 0);
                  matrixStack16.pop();
               } else if (!value19.a()) {
                  String string43 = value19.b != null && !value19.b.isEmpty() ? value19.b : "b";
                  dev.leg1tka.render.font.MsdfTextRenderer value44 = this.b(13);
                  float floatValue45 = value44.b(string43);
                  int intValue46 = value19.c ? dev.leg1tka.api.utils.text.ColorUtils.d(intValue9, intValue25) : dev.leg1tka.api.utils.text.ColorUtils.a(225, 75, 75, intValue25);
                  value44.d(matrixStack16, string43, floatValue33 + 3.5F + (8.5F - floatValue45) / 2.0F, floatValue34 + 5.25F, intValue46);
               } else {
                  dev.leg1tka.render.font.MsdfTextRenderer value50 = this.c(13);
                  float floatValue52 = value50.b("j");
                  value50.d(matrixStack16, "j", floatValue33 + 3.5F + (8.5F - floatValue52) / 2.0F, floatValue34 + 5.0F, dev.leg1tka.api.utils.text.ColorUtils.a(235, 175, 50, intValue25));
               }

               int intValue51 = dev.leg1tka.api.utils.text.ColorUtils.a(180, 185, 200, (int)(140.0F * floatValue24));
               dev.leg1tka.render.RenderPrimitives.b(matrixStack16, floatValue33 + 14.0F, floatValue34 + 3.25F, 0.8F, 5.5F, 0.4F, intValue51);
               float floatValue53 = floatValue33 + 17.5F;
               float floatValue54 = floatValue34 + 3.9F;
               this.a(12).d(matrixStack16, string26 != null ? string26 : "", floatValue53, floatValue54, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue25));
               if (!value19.a()) {
                  int intValue55 = value19.c ? dev.leg1tka.api.utils.text.ColorUtils.a(80, 220, 80, intValue25) : dev.leg1tka.api.utils.text.ColorUtils.a(225, 75, 75, intValue25);
                  this.a(11).d(matrixStack16, string28, floatValue53 + floatValue27 + 6.0F, floatValue54, intValue55);
               }

               floatValue17 += 15.0F * floatValue21;
            }
         }

         if (!flag7) {
            this.k.clear();
            this.k.addAll(list8);
            this.i.keySet().removeIf(value1x -> !this.k.contains(value1x));
            this.j.keySet().removeIf(object1x -> !this.k.contains(object1x));
         }

         super.a(default1);
      }
   }
}
