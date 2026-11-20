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

import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventRender;

public class ShaderHands extends Module {
   public static ShaderHands c = new ShaderHands();
   private static final dev.leg1tka.render.StyledColorRenderer k = dev.leg1tka.render.StyledColorRenderer.a();
   public final ModeSetting d = new ModeSetting(
      "Режим",
      "Свечение",
      "Свечение",
      "Красивый"
   );
   public final NumberSetting e = new NumberSetting("Скорость волн", 1.2F, 0.1F, 5.0F, 0.1F)
      .a(() -> this.d.b("Красивый"));
   public final NumberSetting f = new NumberSetting("Частота волн", 1.0F, 1.0F, 3.0F, 0.1F)
      .a(() -> this.d.b("Красивый"));
   public final NumberSetting g = new NumberSetting("Ширина обводки", 1.2F, 0.1F, 5.0F, 0.1F);
   public final NumberSetting h = new NumberSetting("Сила свечения", 1.0F, 0.0F, 5.0F, 0.1F);
   public final NumberSetting i = new NumberSetting("Заливка", 0.6F, 0.0F, 1.0F, 0.01F);
   public final NumberSetting j = new NumberSetting("Прозрачность", 1.0F, 0.0F, 1.0F, 0.05F);

   public ShaderHands() {
      super(
         "ShaderHands",
         "Красивый Шейдер на руки и предметы",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h, this.i, this.j});
   }

   @Override
   public void onDisable() {
      k.e();
      super.onDisable();
   }

   @EventLink(
      priority = 0
   )
   public void onRender2D(EventRender.Default default1) {
      if (this.isEnabled()) {
         k.d();
      }
   }
}
