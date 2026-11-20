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

public class AutoBuy extends Module {
   public static AutoBuy c = new AutoBuy();
   public dev.leg1tka.settings.KeybindSetting d = new dev.leg1tka.settings.KeybindSetting("Бинд гуи", -1);

   public AutoBuy() {
      super("AutoBuy", Category.MISC);
      this.addSettings(new Setting[]{this.d});
      this.d(true);
   }
}
