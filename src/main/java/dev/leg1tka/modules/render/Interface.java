package dev.leg1tka.modules.render;


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

import com.mojang.blaze3d.systems.RenderSystem;
import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventRender;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Interface extends Module {
   public static Interface c = new Interface();
   public final MultiSetting d = new MultiSetting(
      "Элементы",
      new BooleanSetting("Ватермарка", true),
      new BooleanSetting("Горячие клавиши", true),
      new BooleanSetting("Зелья", true),
      new BooleanSetting("Стафф", true),
      new BooleanSetting("Уведомления", true),
      new BooleanSetting("Таргет худ", true),
      new BooleanSetting("Таргет худ2", false),
      new BooleanSetting("Статус брони", true),
      new BooleanSetting("Кулдауны", true)
   );
   public final dev.leg1tka.settings.ThemeSetting e = new dev.leg1tka.settings.ThemeSetting("Тема");
   private final dev.leg1tka.render.hud.ServerStatusHudRenderer f;
   private final dev.leg1tka.render.hud.KeybindHudRenderer g;
   private final dev.leg1tka.render.hud.StatusEffectHudRenderer h;
   private final dev.leg1tka.render.hud.CooldownHudRenderer i;
   private final dev.leg1tka.render.hud.NotificationHudRenderer j;
   private final dev.leg1tka.render.hud.TotemHudRenderer k;
   private final dev.leg1tka.render.hud.TargetHudRenderer l;
   private final dev.leg1tka.render.hud.StaffHudRenderer m;
   private final dev.leg1tka.render.hud.ArmorHudRenderer n;

   public Interface() {
      super("Interface", "Интерфейс клиента", Category.RENDER);
      this.addSettings(new Setting[]{this.d, this.e});
      this.f = new dev.leg1tka.render.hud.ServerStatusHudRenderer(AspectDLC.draggable(this, "WaterMark", 10.0F, 10.0F));
      this.g = new dev.leg1tka.render.hud.KeybindHudRenderer(AspectDLC.draggable(this, "KeyBinds", 30.0F, 30.0F));
      this.h = new dev.leg1tka.render.hud.StatusEffectHudRenderer(AspectDLC.draggable(this, "Potions", 30.0F, 60.0F));
      this.i = new dev.leg1tka.render.hud.CooldownHudRenderer(AspectDLC.draggable(this, "Cooldowns", 30.0F, 80.0F));
      this.m = new dev.leg1tka.render.hud.StaffHudRenderer(AspectDLC.draggable(this, "StaffList", 60.0F, 100.0F));
      this.j = new dev.leg1tka.render.hud.NotificationHudRenderer(AspectDLC.draggable(this, "Notifications", 0.0F, 0.0F));
      this.k = new dev.leg1tka.render.hud.TotemHudRenderer(AspectDLC.draggable(this, "TargetHud", 30.0F, 90.0F));
      this.l = new dev.leg1tka.render.hud.TargetHudRenderer(AspectDLC.draggable(this, "TargetHud2", 30.0F, 130.0F));
      this.n = new dev.leg1tka.render.hud.ArmorHudRenderer(AspectDLC.draggable(this, "ArmorStatus", 0.0F, 0.0F));
   }

   public static boolean p() {
      return false;
   }

   private void a(dev.leg1tka.render.hud.HudRenderer value1, EventRender.Default default2) {
      value1.e.a(default2.getContext().getMatrices());

      try {
         value1.a(default2);
      } finally {
         value1.e.b(default2.getContext().getMatrices());
      }
   }

   public boolean c(String string1) {
      try {
         return this.d.a(string1);
      } catch (Exception exception3) {
         return false;
      }
   }

   public void a(String string1, boolean flag2) {
      try {
         this.d.a(string1, flag2);
      } catch (Exception exception4) {
      }
   }

   public List<String> q() {
      ArrayList arrayList1 = new ArrayList();

      for (BooleanSetting booleanSetting3 : this.d.e()) {
         arrayList1.add(booleanSetting3.getName());
      }

      return arrayList1;
   }

   public Map<String, dev.leg1tka.render.hud.HudRenderer> r() {
      LinkedHashMap linkedHashMap1 = new LinkedHashMap();
      linkedHashMap1.put("waterMark", this.f);
      linkedHashMap1.put("keyBinds", this.g);
      linkedHashMap1.put("potions", this.h);
      linkedHashMap1.put("cooldowns", this.i);
      linkedHashMap1.put("notifications", this.j);
      linkedHashMap1.put("targetHud", this.k);
      linkedHashMap1.put("targetHud2", this.l);
      linkedHashMap1.put("staffList", this.m);
      linkedHashMap1.put("armorStatus", this.n);
      return linkedHashMap1;
   }

   @EventLink(
      priority = -200
   )
   public void onEvent(EventRender.Default default1) {
      float floatValue2 = minecraftClient != null && minecraftClient.currentScreen instanceof ClickGuiScreen clickGuiScreen3 ? clickGuiScreen3.a() : 0.0F;
      if (!(floatValue2 >= 0.999F)) {
         boolean flag20 = floatValue2 > 0.001F;
         boolean flag21 = this.d.a("Ватермарка");
         boolean flag5 = this.d.a("Горячие клавиши");
         boolean flag6 = this.d.a("Зелья");
         boolean flag7 = false;

         try {
            flag7 = this.d.a("Кулдауны");
         } catch (Exception exception18) {
         }

         boolean flag8 = this.d.a("Стафф");
         boolean flag9 = this.d.a("Уведомления");
         boolean flag10 = this.d.a("Таргет худ");
         boolean flag11 = false;

         try {
            flag11 = this.d.a("Таргет худ2");
         } catch (Exception exception17) {
         }

         boolean flag12 = this.d.a("Статус брони");
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         dev.leg1tka.render.RenderInterpolation.a();
         if (flag20) {
            dev.leg1tka.render.ScreenCaptureRenderer.a();
         }

         try {
            if (flag21) {
               this.a(this.f, default1);
            }

            if (flag5) {
               this.a(this.g, default1);
            }

            if (flag7) {
               this.a(this.i, default1);
            }

            if (flag6) {
               this.a(this.h, default1);
            }

            if (flag8) {
               this.a(this.m, default1);
            }

            if (flag9) {
               this.a(this.j, default1);
            }

            if (flag10) {
               this.a(this.k, default1);
            }

            if (flag11) {
               this.a(this.l, default1);
            }

            if (flag12) {
               this.a(this.n, default1);
            }
         } finally {
            if (flag20) {
               dev.leg1tka.render.ScreenCaptureRenderer.a(default1.getContext(), 1.0F - floatValue2, 1.0F - 0.03F * floatValue2);
            }

            dev.leg1tka.render.RenderPrimitives.e();
         }
      }
   }
}
