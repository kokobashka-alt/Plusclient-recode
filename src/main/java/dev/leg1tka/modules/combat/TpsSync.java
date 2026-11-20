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

import dev.leg1tka.AspectDLC;
import net.minecraft.util.math.MathHelper;

public class TpsSync extends Module {
   public static TpsSync c = new TpsSync();

   public TpsSync() {
      super(
         "TpsSync",
         "Синхронизация с TPS сервера",
         Category.COMBAT
      );
   }

   public float p() {
      if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.tpsCalc != null) {
         float floatValue1 = AspectDLC.INSTANCE.tpsCalc.a();
         return MathHelper.clamp(floatValue1, 0.1F, 20.0F);
      } else {
         return 20.0F;
      }
   }

   public long a(long longValue1) {
      if (!this.isEnabled()) {
         return longValue1;
      } else {
         float floatValue3 = this.p();
         if (floatValue3 >= 20.0F) {
            return longValue1;
         } else {
            float floatValue4 = 20.0F / floatValue3;
            float floatValue5 = 1.0F + (20.0F - floatValue3) * 0.05F;
            long longValue6 = (long)((float)longValue1 * floatValue4 * floatValue5);
            return Math.min(longValue6, 3000L);
         }
      }
   }

   public boolean a(long longValue1, long longValue3, long longValue5) {
      if (!this.isEnabled()) {
         return longValue5 >= longValue1 + longValue3;
      } else {
         long longValue7 = this.a(longValue3);
         return longValue5 >= longValue1 + longValue7;
      }
   }
}
