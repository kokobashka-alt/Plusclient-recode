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

public class Optimizator extends Module {
   public static final Optimizator c = new Optimizator();
   private final BooleanSetting d = new BooleanSetting("Быстрая загрузка", true);

   public Optimizator() {
      super("Optimizator", "Оптимизация клиента", Category.RENDER);
      this.addSettings(new Setting[]{this.d});
   }

   public boolean p() {
      return this.isEnabled() && this.d.getValue();
   }
}
