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
import dev.leg1tka.api.events.implement.EventAttackEntity;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.player.PlayerEntity;

public class AutoEZ extends Module {
   public static final AutoEZ c = new AutoEZ();
   private final ModeSetting d = new ModeSetting(
      "Режим",
      "Глобальный чат",
      "Глобальный чат",
      "Личные сообщения"
   );
   private final dev.leg1tka.settings.StringSetting e = new dev.leg1tka.settings.StringSetting("Что писать", "ez by leg1tka", 100);
   private final BooleanSetting f = new BooleanSetting("Упоминать ник", true)
      .visibleWhen(() -> this.d.b("Глобальный чат"));
   private final NumberSetting g = new NumberSetting("Таймаут килла (сек)", 2.0F, 0.5F, 10.0F, 0.5F);
   private final Map h = new ConcurrentHashMap();

   public AutoEZ() {
      super(
         "AutoEZ",
         "Пишет сообщение при убийстве игрока",
         Category.MISC
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g});
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.h.clear();
   }

   @EventLink
   public void onAttack(EventAttackEntity eventAttackEntity1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         if (eventAttackEntity1.getTarget() instanceof PlayerEntity playerEntity3 && playerEntity3 != minecraftClient.player) {
            this.h.put(playerEntity3, System.currentTimeMillis());
         }
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         long longValue2 = System.currentTimeMillis();
         Iterator iterator4 = this.h.entrySet().iterator();

         while (iterator4.hasNext()) {
            Entry entry5 = (Entry)iterator4.next();
            PlayerEntity playerEntity6 = (PlayerEntity)entry5.getKey();
            Long long7 = (Long)entry5.getValue();
            if (longValue2 - long7 > (long)(this.g.e().floatValue() * 1000.0F)) {
               iterator4.remove();
            } else if (!playerEntity6.isAlive() || playerEntity6.getHealth() <= 0.0F || playerEntity6.getRemovalReason() == RemovalReason.KILLED) {
               this.c(playerEntity6.getName().getString());
               iterator4.remove();
            }
         }
      } else {
         this.h.clear();
      }
   }

   private void c(String string1) {
      String string2 = this.e.e();
      if (this.d.b("Личные сообщения")) {
         if (minecraftClient.getNetworkHandler() != null) {
            minecraftClient.getNetworkHandler().sendChatCommand("msg " + string1 + " " + string2);
         }
      } else if (minecraftClient.getNetworkHandler() != null) {
         if (this.f.getValue()) {
            minecraftClient.getNetworkHandler().sendChatMessage("!" + string1 + " " + string2);
         } else {
            minecraftClient.getNetworkHandler().sendChatMessage("!" + string2);
         }
      }
   }
}
