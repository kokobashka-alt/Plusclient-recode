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

import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventBinding;
import dev.leg1tka.api.events.implement.EventPlayerSync;
import dev.leg1tka.api.events.implement.EventUpdate;
import net.minecraft.item.Items;
public class ClickPearl extends Module {
   public static final ClickPearl c = new ClickPearl();
   private final ModeSetting d = new ModeSetting(
      "Мод",
      "Обычный",
      "Обычный",
      "Легитный",
      "WellMine"
   );
   private final BooleanSetting e = new BooleanSetting("Обходить Grim", true);
   private final dev.leg1tka.settings.KeybindSetting f = new dev.leg1tka.settings.KeybindSetting("Клавиша броска", -98);
   private boolean g;
   private int h;

   public ClickPearl() {
      super("ClickPearl", "Click Pearl", Category.MISC);
      this.addSettings(new Setting[]{this.d, this.e, this.f});
   }

   @EventLink
   public void onKey(EventBinding eventBinding1) {
      if (this.f.getKey() == eventBinding1.getKey()) {
         createRunnable155(this).run();
      }
   }

   @EventLink
   public void onPlayerTick(EventUpdate eventUpdate1) {
      if (this.g) {
         dev.leg1tka.api.utils.player.InventorySearch.c(Items.ENDER_PEARL);
         this.g = false;
      }
   }

   @EventLink
   public void onPlayerSync(EventPlayerSync eventPlayerSync1) {
      if (this.g) {
         dev.leg1tka.api.utils.player.InventorySearch.c(Items.ENDER_PEARL);
         this.g = false;
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.g = false;
      this.h = 0;
   }



   private static Runnable createRunnable155(ClickPearl clickPearl0) {
      return () -> {
         if (clickPearl0.d.b("Обычный")) {
            dev.leg1tka.api.utils.player.InventorySearch.c(Items.ENDER_PEARL);
         } else if (clickPearl0.d.b("WellMine")) {
            dev.leg1tka.api.utils.player.InventorySearch.e(Items.ENDER_PEARL);
         } else {
            clickPearl0.g = true;
         }
      };
   }
   public static void dev_leg1tka_o_ig_initializer() {}
}
