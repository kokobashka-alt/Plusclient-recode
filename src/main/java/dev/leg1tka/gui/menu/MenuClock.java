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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class MenuClock {
   private static final Locale a = Locale.forLanguageTag("ru");
   private static final DateTimeFormatter b = DateTimeFormatter.ofPattern("HH:mm");
   private static final DateTimeFormatter c = DateTimeFormatter.ofPattern("EEEE, d MMMM", a);
   private static long d;
   private static float e;
   private static float f;
   private static float g;
   private static float h;
   private static float i;
   private final char[] j = new char[5];
   private final char[] k = new char[5];
   private final long[] l = new long[5];
   private long m = 0L;

   public MenuClock() {
   }

   public void a() {
      this.m = System.currentTimeMillis();
   }

   private float a(int intValue1) {
      if (dev.leg1tka.gui.menu.MenuIdleState.d()) {
         return intValue1 == 0 ? dev.leg1tka.gui.menu.MenuIdleState.a(100L, 900L) : dev.leg1tka.gui.menu.MenuIdleState.a(1500L + (long)(intValue1 - 1) * 350L, 1100L);
      } else if (dev.leg1tka.gui.menu.MenuIdleState.c()) {
         return 1.0F;
      } else if (this.m == 0L) {
         return 1.0F;
      } else {
         float floatValue2 = (float)(System.currentTimeMillis() - this.m - (long)intValue1 * 70L) / 560.0F;
         return dev.leg1tka.gui.menu.MenuBackdropRenderer.b(floatValue2);
      }
   }

   public static float[] a(float floatValue0, float floatValue1, boolean flag2) {
      float floatValue3 = (flag2 ? 52.8F : 66.0F) * 1.35F;
      float floatValue4 = dev.leg1tka.gui.menu.MenuLayout.a(flag2 ? 73.6F : 103.04F);
      float floatValue5 = floatValue4 * 0.36F;
      float floatValue6 = dev.leg1tka.gui.menu.MenuLayout.a(19.0F) * 0.7F;
      float floatValue7 = dev.leg1tka.gui.menu.MenuLayout.a(floatValue3) * 0.7F;
      float floatValue8 = dev.leg1tka.gui.menu.MenuLayout.a(16.0F) * 0.7F;
      float floatValue9 = dev.leg1tka.gui.menu.MenuLayout.a(22.0F);
      float floatValue10 = dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
      float floatValue11 = dev.leg1tka.gui.menu.MenuLayout.a(18.0F);
      float floatValue12 = floatValue5 + floatValue9 + floatValue6 + floatValue10 + floatValue7 + (flag2 ? 0.0F : floatValue11 + floatValue8);
      float floatValue13 = floatValue1 * 0.45F - floatValue12 * 0.5F;
      float floatValue14 = floatValue13 + floatValue5 * 0.5F;
      float floatValue15 = floatValue13 + floatValue5 + floatValue9 + floatValue6 * 0.5F;
      float floatValue16 = floatValue15 + floatValue6 * 0.5F + floatValue10 + floatValue7 * 0.5F;
      float floatValue17 = floatValue16 + floatValue7 * 0.5F + floatValue11 + floatValue8 * 0.5F;
      return new float[]{floatValue4, floatValue14, floatValue15, floatValue16, floatValue17};
   }

   public static boolean a(float floatValue0) {
      return floatValue0 < dev.leg1tka.gui.menu.MenuLayout.a(560.0F);
   }

   private static String b(int intValue0) {
      if (intValue0 >= 5 && intValue0 < 12) {
         return "Доброе утро";
      } else if (intValue0 >= 12 && intValue0 < 18) {
         return "Добрый день";
      } else {
         return intValue0 >= 18 && intValue0 < 23
            ? "Добрый вечер"
            : "Доброй ночи";
      }
   }

   public void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, boolean flag5, int intValue6, int intValue7, float floatValue8) {
      LocalDateTime localDateTime9 = LocalDateTime.now();
      String string10 = localDateTime9.format(b);
      String string11 = localDateTime9.format(c);
      if (!string11.isEmpty()) {
         string11 = Character.toUpperCase(string11.charAt(0)) + string11.substring(1);
      }

      long longValue12 = System.currentTimeMillis();

      for (int intValue14 = 0; intValue14 < 5; intValue14++) {
         char character15 = string10.charAt(intValue14);
         if (this.j[intValue14] == 0) {
            this.j[intValue14] = character15;
            this.k[intValue14] = character15;
         } else if (this.j[intValue14] != character15) {
            this.k[intValue14] = this.j[intValue14];
            this.j[intValue14] = character15;
            this.l[intValue14] = longValue12;
         }
      }

      float floatValue36 = (flag5 ? 52.8F : 66.0F) * 1.35F;
      float[] floatValueArray37 = a(floatValue2, floatValue3, flag5);
      float floatValue16 = floatValueArray37[0];
      float floatValue17 = floatValueArray37[1];
      float floatValue18 = floatValueArray37[2];
      float floatValue19 = floatValueArray37[3];
      float floatValue20 = floatValueArray37[4];
      float floatValue21 = floatValue2 * 0.5F;
      float floatValue22 = dev.leg1tka.gui.menu.MenuLayout.a(16.0F) * 0.7F;
      dev.leg1tka.gui.menu.MenuIdleState.a(floatValue21, floatValue17, floatValue16);
      dev.leg1tka.gui.menu.MenuIdleState.a.a(Math.max(floatValue8, dev.leg1tka.gui.menu.MenuIdleState.h()));
      float floatValue23 = this.a(0) * floatValue4;
      if (floatValue23 > 0.01F) {
         float floatValue24 = 0.85F + 0.15F * this.a(0);
         float floatValue25 = dev.leg1tka.gui.menu.MenuIdleState.d() ? dev.leg1tka.gui.menu.MenuIdleState.a(900L, 2500L) : 1.0F;
         float floatValue26 = 0.3F + 0.25F * (0.5F + 0.5F * MathHelper.sin((float)longValue12 / 1000.0F * 0.9F));
         float floatValue27 = floatValue25 * MathHelper.lerp(floatValue8, 1.0F, floatValue26);
         boolean flag28 = floatValue8 < 0.05F && dev.leg1tka.gui.menu.MenuIdleState.h() < 0.5F;
         dev.leg1tka.gui.menu.MenuIdleState.a
            .a(
               matrixStack1,
               floatValue21,
               floatValue17 + (1.0F - this.a(0)) * dev.leg1tka.gui.menu.MenuLayout.a(8.0F),
               floatValue16 * floatValue24,
               flag28 ? (float)intValue6 : Float.NaN,
               flag28 ? (float)intValue7 : Float.NaN,
               floatValue23,
               floatValue27
            );
      }

      float floatValue38 = this.a(1) * floatValue4;
      if (floatValue38 > 0.01F) {
         float floatValue39 = (1.0F - this.a(1)) * dev.leg1tka.gui.menu.MenuLayout.a(10.0F);
         dev.leg1tka.gui.components.MenuTextRenderer.d(matrixStack1, string11, 19.0F, floatValue21, floatValue18 + floatValue39, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.84F * floatValue38, 16777215));
      }

      float floatValue40 = this.a(2) * floatValue4;
      if (floatValue40 > 0.01F) {
         float floatValue41 = (1.0F - this.a(2)) * dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
         float floatValue43 = dev.leg1tka.gui.components.MenuTextRenderer.a(floatValue36, "0");
         float floatValue45 = dev.leg1tka.gui.components.MenuTextRenderer.a(floatValue36, ":") + dev.leg1tka.gui.menu.MenuLayout.a(floatValue36 * 0.04F);
         float floatValue29 = floatValue43 * 4.0F + floatValue45;
         float floatValue30 = floatValue21 - floatValue29 * 0.5F;
         float floatValue31 = floatValue19 + floatValue41;
         float floatValue32 = dev.leg1tka.gui.menu.MenuLayout.a(floatValue36) * 0.36F;

         for (int intValue33 = 0; intValue33 < 5; intValue33++) {
            if (intValue33 == 2) {
               dev.leg1tka.gui.components.MenuTextRenderer.d(matrixStack1, ":", floatValue36, floatValue30 + floatValue45 * 0.5F, floatValue31 - dev.leg1tka.gui.menu.MenuLayout.a(floatValue36 * 0.03F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.9F * floatValue40, 16777215));
               floatValue30 += floatValue45;
            } else {
               float floatValue34 = this.l[intValue33] == 0L ? 1.0F : dev.leg1tka.gui.menu.MenuBackdropRenderer.b((float)(longValue12 - this.l[intValue33]) / 520.0F);
               float floatValue35 = floatValue30 + floatValue43 * 0.5F;
               if (floatValue34 < 1.0F) {
                  dev.leg1tka.gui.components.MenuTextRenderer.d(matrixStack1, String.valueOf(this.k[intValue33]), floatValue36, floatValue35, floatValue31 - floatValue34 * floatValue32, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((1.0F - floatValue34) * floatValue40, 16777215));
                  dev.leg1tka.gui.components.MenuTextRenderer.d(matrixStack1, String.valueOf(this.j[intValue33]), floatValue36, floatValue35, floatValue31 + (1.0F - floatValue34) * floatValue32, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue34 * floatValue40, 16777215));
               } else {
                  dev.leg1tka.gui.components.MenuTextRenderer.d(matrixStack1, String.valueOf(this.j[intValue33]), floatValue36, floatValue35, floatValue31, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue40, 16777215));
               }

               floatValue30 += floatValue43;
            }
         }
      }

      float floatValue42 = this.a(3) * floatValue4;
      if (floatValue42 > 0.01F && !flag5) {
         float floatValue44 = (1.0F - this.a(3)) * dev.leg1tka.gui.menu.MenuLayout.a(10.0F);
         String string46 = MinecraftClient.getInstance().getSession().getUsername();
         String string47 = b(localDateTime9.getHour()) + ", ";
         float floatValue48 = dev.leg1tka.gui.components.MenuTextRenderer.a(16.0F, string47);
         float floatValue49 = dev.leg1tka.gui.components.MenuTextRenderer.a(16.0F, string46);
         float floatValue50 = floatValue21 - (floatValue48 + floatValue49) * 0.5F;
         float floatValue51 = floatValue20 + floatValue44;
         dev.leg1tka.gui.components.MenuTextRenderer.e(matrixStack1, string47, 16.0F, floatValue50, floatValue51, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.72F * floatValue42, 16777215));
         dev.leg1tka.gui.components.MenuTextRenderer.e(matrixStack1, string46, 16.0F, floatValue50 + floatValue48, floatValue51, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.98F * floatValue42, 16777215));
         float floatValue52 = dev.leg1tka.gui.menu.MenuLayout.b(floatValue51 + floatValue22 * 0.5F + dev.leg1tka.gui.menu.MenuLayout.a(6.0F));
         float floatValue53 = Math.max(dev.leg1tka.gui.menu.MenuLayout.c() * 2.0F, dev.leg1tka.gui.menu.MenuLayout.a(2.0F));
         dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue50 + floatValue48, floatValue52, floatValue49, floatValue53, floatValue53 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue42, dev.leg1tka.gui.menu.MenuBackdropRenderer.c()), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue42, dev.leg1tka.gui.menu.MenuBackdropRenderer.d()), true);
      }
   }
}
