package dev.leg1tka.gui;


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

import dev.leg1tka.api.events.implement.EventRender;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class ThemePreviewScreen extends Screen implements dev.leg1tka.api.ClientAccess {
   public static boolean c = false;
   private static final Identifier d = Identifier.of("aspectdlc", "textures/preview/background.png");
   private static int e;
   private static int f;
   private boolean g;
   private int h = 0;
   private final List<ThemePreviewScreen.b> i = new ArrayList<>();
   private final List<ThemePreviewScreen.a> j = List.of(
      new ThemePreviewScreen.a(StatusEffects.SPEED, 1, a(60), false),
      new ThemePreviewScreen.a(StatusEffects.STRENGTH, 1, a(180), false),
      new ThemePreviewScreen.a(StatusEffects.FIRE_RESISTANCE, 0, a(420), false),
      new ThemePreviewScreen.a(StatusEffects.REGENERATION, 1, a(30), false),
      new ThemePreviewScreen.a(StatusEffects.HASTE, 1, a(120), false),
      new ThemePreviewScreen.a(StatusEffects.JUMP_BOOST, 0, a(180), false),
      new ThemePreviewScreen.a(StatusEffects.NIGHT_VISION, 0, 0, true),
      new ThemePreviewScreen.a(StatusEffects.SLOWNESS, 0, a(45), false)
   );

   public ThemePreviewScreen() {
      super(Text.literal("HUD Preview"));
   }

   private static int a(int intValue0) {
      return intValue0 * 20;
   }

   protected void init() {
      super.init();
      dev.leg1tka.render.hud.StatusEffectOrder.a(true);
      if (Interface.c != null && !Interface.c.isEnabled()) {
         Interface.c.setEnabled(true);
      }
   }

   public void removed() {
      super.removed();

      for (dev.leg1tka.render.hud.HudElement value2 : dev.leg1tka.render.hud.HudElementRegistry.a.values()) {
         value2.a(0);
      }

      dev.leg1tka.render.hud.StatusEffectOrder.a(false);
   }

   public boolean shouldPause() {
      return false;
   }

   public void tick() {
      super.tick();
      dev.leg1tka.render.hud.StatusEffectOrder.d();
   }

   public void render(DrawContext context, int mouseX, int mouseY, float delta) {
      MatrixStack matrixStack5 = context.getMatrices();
      int intValue6 = this.width;
      int intValue7 = this.height;
      int intValue8 = intValue6 - 220;
      dev.leg1tka.render.RenderPrimitives.a(matrixStack5, d, 0.0F, 0.0F, (float)intValue8, (float)intValue7, -1);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack5, 0.0F, 0.0F, (float)intValue8, (float)intValue7, 0.0F, 1073741824);
      EventRender.Default default9 = new EventRender.Default(context, delta);
      if (Interface.c != null) {
         try {
            Interface.c.onEvent(default9);
         } catch (Throwable throwable14) {
         }
      }

      boolean flag10 = GLFW.glfwGetMouseButton(MinecraftClient.getInstance().getWindow().getHandle(), 0) == 1;
      if (this.g && !flag10) {
         for (dev.leg1tka.render.hud.HudElement value12 : dev.leg1tka.render.hud.HudElementRegistry.a.values()) {
            value12.a(0);
         }
      }

      this.g = flag10;
      Window window15 = MinecraftClient.getInstance().getWindow();

      for (dev.leg1tka.render.hud.HudElement value13 : dev.leg1tka.render.hud.HudElementRegistry.a.values()) {
         if (value13.f().isEnabled()) {
            value13.a(mouseX, mouseY, window15, matrixStack5);
         }
      }

      this.i.clear();
      this.a(context, intValue6 - 220, 0, 220, intValue7, mouseX, mouseY);
   }

   private void a(DrawContext drawContext1, int intValue2, int intValue3, int intValue4, int intValue5, int intValue6, int intValue7) {
      drawContext1.fill(intValue2, intValue3, intValue2 + intValue4, intValue3 + intValue5, -535818224);
      drawContext1.fill(intValue2, intValue3, intValue2 + 1, intValue3 + intValue5, -14013910);
      int intValue8 = intValue3 + 8 - this.h;
      int intValue9 = intValue2 + 8;
      int intValue10 = intValue4 - 16;
      drawContext1.drawText(this.textRenderer, Text.literal("HUD PREVIEW").formatted(Formatting.WHITE), intValue9, intValue8, -1, false);
      intValue8 += 12;
      drawContext1.drawText(
         this.textRenderer,
         Text.literal(
               "Перетаскивай виджеты мышкой"
            )
            .formatted(Formatting.GRAY),
         intValue9,
         intValue8,
         -5592406,
         false
      );
      intValue8 += 14;
      intValue8 = this.a(drawContext1, intValue9, intValue8, intValue10, "Виджеты");
      if (Interface.c != null) {
         for (String string12 : Interface.c.q()) {
            boolean flag13 = Interface.c.c(string12);
            intValue8 = this.a(drawContext1, intValue9, intValue8, intValue10, string12, flag13, intValue6, intValue7, () -> Interface.c.a(string12, !flag13));
         }
      }

      intValue8 += 6;
      intValue8 = this.a(drawContext1, intValue9, intValue8, intValue10, "Модули (с биндом)");
      ObjectListIterator objectListIterator35 = ModuleManager.a.a().iterator();

      while (objectListIterator35.hasNext()) {
         Module module37 = (Module)objectListIterator35.next();
         if (module37.getKeyBind() != -1 && module37.getKeyBind() != 0) {
            boolean flag41 = module37.isEnabled();
            String string14 = module37.getDisplayName();
            intValue8 = this.a(drawContext1, intValue9, intValue8, intValue10, string14, flag41, intValue6, intValue7, () -> module37.setEnabled(!flag41));
         }
      }

      intValue8 += 6;
      intValue8 = this.a(drawContext1, intValue9, intValue8, intValue10, "Стиль Potions");
      dev.leg1tka.render.hud.StatusEffectHudRenderer value36 = this.a();
      if (value36 != null) {
         boolean flag38 = value36.e();
         int intValue42 = (intValue10 - 4) / 2;
         intValue8 = this.b(drawContext1, intValue9, intValue8, intValue42, "Обычный", !flag38, intValue6, intValue7, () -> value36.c(false));
         this.b(drawContext1, intValue9 + intValue42 + 4, intValue8 - 14, intValue42, "Компакт", flag38, intValue6, intValue7, () -> value36.c(true));
      }

      intValue8 += 6;
      intValue8 = this.a(drawContext1, intValue9, intValue8, intValue10, "Добавить зелье");

      for (ThemePreviewScreen.a value43 : this.j) {
         intValue8 = this.b(drawContext1, intValue9, intValue8, intValue10, "+ " + value43.a(), false, intValue6, intValue7, () -> dev.leg1tka.render.hud.StatusEffectOrder.a(value43.entry, value43.amp, value43.durTicks, value43.infinite));
      }

      intValue8 += 6;
      intValue8 = this.a(drawContext1, intValue9, intValue8, intValue10, "Активные эффекты");
      List<StatusEffectInstance> list40 = dev.leg1tka.render.hud.StatusEffectOrder.b();
      if (list40.isEmpty()) {
         drawContext1.drawText(this.textRenderer, Text.literal("(пусто)").formatted(Formatting.DARK_GRAY), intValue9, intValue8, -11184811, false);
         intValue8 += 12;
      } else {
         for (StatusEffectInstance statusEffectInstance45 : list40) {
            String string15 = ((StatusEffect)statusEffectInstance45.getEffectType().value()).getName().getString();
            String string16 = statusEffectInstance45.getAmplifier() == 0 ? "" : " " + b(statusEffectInstance45.getAmplifier() + 1);
            String string17 = statusEffectInstance45.isInfinite() ? "inf" : statusEffectInstance45.getDuration() / 20 / 60 + ":" + String.format("%02d", statusEffectInstance45.getDuration() / 20 % 60);
            String string18 = "× " + string15 + string16 + "  " + string17;
            RegistryEntry registryEntry19 = statusEffectInstance45.getEffectType();
            intValue8 = this.b(drawContext1, intValue9, intValue8, intValue10, string18, false, intValue6, intValue7, () -> dev.leg1tka.render.hud.StatusEffectOrder.a(registryEntry19));
         }
      }

      intValue8 += 8;
      intValue8 = this.b(
         drawContext1, intValue9, intValue8, intValue10, "Сбросить позиции", false, intValue6, intValue7, this::b
      );
      intValue8 += 4;
      intValue8 = this.b(drawContext1, intValue9, intValue8, intValue10, "Закрыть превью", false, intValue6, intValue7, this::close);
   }

   private static String b(int intValue0) {
      return switch (intValue0) {
         case 1 -> "I";
         case 2 -> "II";
         case 3 -> "III";
         case 4 -> "IV";
         case 5 -> "V";
         default -> String.valueOf(intValue0);
      };
   }

   private int a(DrawContext drawContext1, int intValue2, int intValue3, int intValue4, String string5) {
      drawContext1.fill(intValue2, intValue3 + 4, intValue2 + intValue4, intValue3 + 5, -14013910);
      drawContext1.drawText(this.textRenderer, Text.literal(string5).formatted(Formatting.GRAY), intValue2, intValue3 - 2, -4210753, false);
      return intValue3 + 12;
   }

   private int a(DrawContext drawContext1, int intValue2, int intValue3, int intValue4, String string5, boolean flag6, int intValue7, int intValue8, Runnable runnable9) {
      byte byteValue10 = 12;
      boolean flag11 = intValue7 >= intValue2 && intValue7 < intValue2 + intValue4 && intValue8 >= intValue3 && intValue8 < intValue3 + byteValue10;
      if (flag11) {
         drawContext1.fill(intValue2, intValue3, intValue2 + intValue4, intValue3 + byteValue10, 1090519039);
      }

      int intValue12 = flag6 ? -5261569 : -11184811;
      drawContext1.fill(intValue2 + 1, intValue3 + 2, intValue2 + 9, intValue3 + 10, -16777216);
      drawContext1.fill(intValue2 + 2, intValue3 + 3, intValue2 + 8, intValue3 + 9, intValue12);
      if (flag6) {
         drawContext1.fill(intValue2 + 3, intValue3 + 5, intValue2 + 7, intValue3 + 6, -16777216);
         drawContext1.fill(intValue2 + 4, intValue3 + 6, intValue2 + 6, intValue3 + 7, -16777216);
      }

      drawContext1.drawText(this.textRenderer, Text.literal(string5), intValue2 + 13, intValue3 + 2, -2039584, false);
      this.i.add(new ThemePreviewScreen.b(intValue2, intValue3, intValue4, byteValue10, runnable9));
      return intValue3 + byteValue10 + 1;
   }

   private int b(DrawContext drawContext1, int intValue2, int intValue3, int intValue4, String string5, boolean flag6, int intValue7, int intValue8, Runnable runnable9) {
      byte byteValue10 = 12;
      boolean flag11 = intValue7 >= intValue2 && intValue7 < intValue2 + intValue4 && intValue8 >= intValue3 && intValue8 < intValue3 + byteValue10;
      int intValue12 = flag6 ? -5261569 : (flag11 ? -13290187 : -14803426);
      int intValue13 = flag6 ? -16777216 : -2039584;
      drawContext1.fill(intValue2, intValue3, intValue2 + intValue4, intValue3 + byteValue10, intValue12);
      drawContext1.drawText(this.textRenderer, Text.literal(string5), intValue2 + 4, intValue3 + 2, intValue13, false);
      this.i.add(new ThemePreviewScreen.b(intValue2, intValue3, intValue4, byteValue10, runnable9));
      return intValue3 + byteValue10 + 2;
   }

   private dev.leg1tka.render.hud.StatusEffectHudRenderer a() {
      if (Interface.c == null) {
         return null;
      } else {
         Map map1 = Interface.c.r();
         dev.leg1tka.render.hud.HudRenderer value2 = (dev.leg1tka.render.hud.HudRenderer)map1.get("potions");
         return value2 instanceof dev.leg1tka.render.hud.StatusEffectHudRenderer ? (dev.leg1tka.render.hud.StatusEffectHudRenderer)value2 : null;
      }
   }

   private void b() {
      for (dev.leg1tka.render.hud.HudElement value2 : dev.leg1tka.render.hud.HudElementRegistry.a.values()) {
         value2.a(value2.initialXVal);
         value2.b(value2.initialYVal);
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (mouseX >= (double)(this.width - 220)) {
         if (button == 0) {
            for (ThemePreviewScreen.b value9 : this.i) {
               if (value9.a(mouseX, mouseY)) {
                  value9.onClick.run();
                  return true;
               }
            }
         }

         return super.mouseClicked(mouseX, mouseY, button);
      } else {
         if (button == 0) {
            for (dev.leg1tka.render.hud.HudElement value7 : dev.leg1tka.render.hud.HudElementRegistry.a.values()) {
               if (value7.f().isEnabled() && value7.a(mouseX, mouseY, button)) {
                  return true;
               }
            }
         }

         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      for (dev.leg1tka.render.hud.HudElement value7 : dev.leg1tka.render.hud.HudElementRegistry.a.values()) {
         value7.a(button);
      }

      return super.mouseReleased(mouseX, mouseY, button);
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      if (mouseX >= (double)(this.width - 220)) {
         this.h = Math.max(0, this.h - (int)(verticalAmount * 14.0));
         return true;
      } else {
         return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.close();
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

      static record a(RegistryEntry<StatusEffect> entry, int amp, int durTicks, boolean infinite) {



      public String a() {
         String string1 = ((StatusEffect)this.entry.value()).getName().getString();
         String string2 = this.amp == 0 ? "" : " " + a(this.amp + 1);
         String string3 = this.infinite ? "inf" : this.durTicks / 20 / 60 + ":" + String.format("%02d", this.durTicks / 20 % 60);
         return string1 + string2 + "  " + string3;
      }

      private static String a(int intValue0) {
         return switch (intValue0) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            default -> String.valueOf(intValue0);
         };
      }

      public RegistryEntry<StatusEffect> b() {
         return this.entry;
      }

      public int c() {
         return this.amp;
      }

      public int d() {
         return this.durTicks;
      }

      public boolean e() {
         return this.infinite;
      }
   }

   static record b(int x, int y, int w, int h, Runnable onClick)  {





      boolean a(double doubleValue1, double doubleValue3) {
         return doubleValue1 >= (double)this.x && doubleValue1 < (double)(this.x + this.w) && doubleValue3 >= (double)this.y && doubleValue3 < (double)(this.y + this.h);
      }
   }
}
