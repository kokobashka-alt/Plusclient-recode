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

import lombok.Generated;

public class NoPush extends Module {
   public static NoPush c = new NoPush();
   private MultiSetting d = new MultiSetting(
      "Коллизия",
      new BooleanSetting("Блоки", true),
      new BooleanSetting("Вода", false),
      new BooleanSetting("Удочик", true),
      new BooleanSetting("Игроки", true)
   );

   public NoPush() {
      super("NoPush", "Отключает коллизию", Category.MISC);
      this.addSettings(new Setting[]{this.d});
   }

   @Generated
   public MultiSetting p() {
      return this.d;
   }

   @Generated
   public void a(MultiSetting multiSetting1) {
      this.d = multiSetting1;
   }
}
