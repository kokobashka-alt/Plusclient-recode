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

import dev.leg1tka.AspectDLC;

public class CustomWorld extends Module {
   public static CustomWorld c = new CustomWorld();
   private final MultiSetting d = new MultiSetting(
      "Настройки мира",
      new BooleanSetting("Время", true),
      new BooleanSetting("Фог", true),
      new BooleanSetting("Шейдер", false)
   );
   private final NumberSetting e = new NumberSetting("Время", 12.0F, 0.0F, 24.0F, 1.0F).a(() -> this.d.a("Время"));
   private final NumberSetting f = new NumberSetting("Дистанция фога", 100.0F, 20.0F, 200.0F, 1.0F)
      .a(() -> this.d.a("Фог"));
   private final ModeSetting g = new ModeSetting(
         "Тип шейдера", "Stardust", "Stardust", "Lagoon", "Cosmos", "Sunset", "Pulse", "Vortex"
      )
      .a(() -> this.d.a("Шейдер"));
   private final NumberSetting h = new NumberSetting(
         "Прозрачность шейдера", 70.0F, 0.0F, 100.0F, 5.0F
      )
      .a(() -> this.d.a("Шейдер"));

   public CustomWorld() {
      super("CustomWorld", "Настройки мира", Category.RENDER);
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h});
   }

   public boolean p() {
      return this.isEnabled() && this.d.a("Время");
   }

   public boolean q() {
      return this.isEnabled() && this.d.a("Фог");
   }

   public long r() {
      return (long)this.e.f() * 1000L;
   }

   public float s() {
      return this.f.f();
   }

   public int t() {
      return !AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow") ? AspectDLC.INSTANCE.themeStorage.b().a().c[0] : dev.leg1tka.api.utils.text.ColorUtils.a();
   }

   public boolean u() {
      return this.isEnabled() && this.d.a("Шейдер");
   }

   public String v() {
      return this.g.g();
   }

   public float w() {
      return this.h.f() / 100.0F;
   }

   public int x() {
      return !AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow") ? AspectDLC.INSTANCE.themeStorage.b().a().c[0] : dev.leg1tka.api.utils.text.ColorUtils.a();
   }
}
