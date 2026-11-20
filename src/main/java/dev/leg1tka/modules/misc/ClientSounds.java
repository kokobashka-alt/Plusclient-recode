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

public class ClientSounds extends Module {
   public static ClientSounds c = new ClientSounds();
   public final ModeSetting d = new ModeSetting("Режим", "Celestial", "Celestial");
   public final NumberSetting e = new NumberSetting("Громкость", 50.0F, 1.0F, 100.0F, 0.5F);

   public ClientSounds() {
      super(
         "ClientSounds",
         "Добавляет звуки клиента",
         Category.MISC
      );
      this.addSettings(new Setting[]{this.d, this.e});
   }
}
