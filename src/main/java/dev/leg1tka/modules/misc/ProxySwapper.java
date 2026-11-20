package dev.leg1tka.modules.misc;


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

public class ProxySwapper extends Module {
   public static final ProxySwapper c = new ProxySwapper();
   public final BooleanSetting d = new BooleanSetting("Логи: Анти-Детект", true);
   public final BooleanSetting e = new BooleanSetting("Безопасная смена", true);

   public ProxySwapper() {
      super(
         "ProxySwapper",
         "Прокси свитчер для мгновенного переключения клиентов",
         Category.MISC
      );
      this.addSettings(new Setting[]{this.d, this.e});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      dev.leg1tka.network.ProxySessionBridge.a(25565);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      dev.leg1tka.network.ProxySessionBridge.a();
   }

   public boolean p() {
      return this.d.getValue();
   }

   public boolean q() {
      return this.e.getValue();
   }
}
