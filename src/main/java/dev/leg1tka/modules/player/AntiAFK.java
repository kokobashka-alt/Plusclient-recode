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

import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventUpdate;

public class AntiAFK extends Module {
   public static AntiAFK c = new AntiAFK();
   private long d = 0L;
   private long e = 0L;
   private long f = 0L;
   private boolean g = false;
   private boolean h = false;
   private static long i;
   private static long j;
   private static long k;

   public AntiAFK() {
      super(
         "AntiAFK",
         "Автоматически нажимает пробел и идет вперед чтобы не кикнуло за АФК",
         Category.PLAYER
      );
   }

   @Override
   public void onEnable() {
      this.d = System.currentTimeMillis();
      this.e = 0L;
      this.f = 0L;
      this.g = false;
      this.h = false;
      super.onEnable();
   }

   @Override
   public void onDisable() {
      if (minecraftClient.player != null && minecraftClient.options != null) {
         minecraftClient.options.forwardKey.setPressed(false);
         minecraftClient.options.jumpKey.setPressed(false);
      }

      this.g = false;
      this.h = false;
      super.onDisable();
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null && minecraftClient.options != null) {
         long longValue2 = System.currentTimeMillis();
         if (this.h && longValue2 - this.f >= 100L) {
            minecraftClient.options.jumpKey.setPressed(false);
            this.h = false;
         }

         if (this.g) {
            if (longValue2 - this.e >= 2000L) {
               minecraftClient.options.forwardKey.setPressed(false);
               this.g = false;
               this.d = longValue2;
            }
         } else if (!this.h && longValue2 - this.d >= 5000L) {
            minecraftClient.options.jumpKey.setPressed(true);
            this.h = true;
            this.f = longValue2;
            minecraftClient.options.forwardKey.setPressed(true);
            this.g = true;
            this.e = longValue2;
         }
      }
   }
}
