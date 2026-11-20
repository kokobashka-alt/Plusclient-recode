package dev.leg1tka.modules.combat;


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

public class AntiBot extends Module {
   public static final AntiBot c = new AntiBot();

   public AntiBot() {
      super(
         "AntiBot",
         "Игнорирует ботов античита",
         Category.COMBAT
      );
   }
}
