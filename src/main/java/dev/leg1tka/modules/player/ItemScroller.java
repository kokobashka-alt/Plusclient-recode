package dev.leg1tka.modules.player;


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

public class ItemScroller extends Module {
   public static ItemScroller c = new ItemScroller();
   public final NumberSetting d = new NumberSetting("Задержка", 50.0F, 0.0F, 200.0F, 1.0F);
   private long e;

   public ItemScroller() {
      super(
         "ItemScroller",
         "Убирает задержку перемещения предметов",
         Category.PLAYER
      );
      this.addSettings(new Setting[]{this.d});
   }

   public boolean p() {
      long longValue1 = System.currentTimeMillis();
      if (longValue1 - this.e < (long)this.d.f()) {
         return false;
      } else {
         this.e = longValue1;
         return true;
      }
   }

   public void q() {
      this.e = 0L;
   }

   @Override
   public void onDisable() {
      this.q();
      super.onDisable();
   }
}
