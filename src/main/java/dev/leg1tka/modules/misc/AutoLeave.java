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

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.Locale;
import java.util.Set;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
public class AutoLeave extends Module {
   public static final AutoLeave c = new AutoLeave();
   private static Set<String> d;
   private final NumberSetting e = new NumberSetting(
      "Дистанция срабатывания",
      5.0F,
      3.0F,
      50.0F,
      1.0F
   );
   private final MultiSetting f = new MultiSetting(
      "Выходить если замечен",
      new BooleanSetting("Игрок", true),
      new BooleanSetting("Модератор", false)
   );
   private final ModeSetting g = new ModeSetting(
      "Тип выхода",
      "В мейн меню",
      "В мейн меню",
      "/hub",
      "/home",
      "/spawn"
   );
   private final BooleanSetting h = new BooleanSetting("Выключать баритон", false);
   private final BooleanSetting i = new BooleanSetting(
      "Выключать после выхода", true
   );
   private int j;

   public AutoLeave() {
      super(
         "AutoLeave",
         "Выходит с сервера, когда замечает поблизости игрока",
         Category.MISC
      );
      this.addSettings(new Setting[]{this.e, this.f, this.g, this.h, this.i});
   }

   @Override
   public void onEnable() {
      this.j = 0;
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.j = 0;
      super.onDisable();
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player == null || minecraftClient.world == null) return;
      if (this.j > 0) {
         this.j--;
         return;
      }
      float dist = this.e.e().floatValue();
      for (PlayerEntity player : minecraftClient.world.getPlayers()) {
         if (player == minecraftClient.player) continue;
         if (minecraftClient.player.distanceTo(player) <= dist && this.a(player)) {
            this.p();
            break;
         }
      }
   }

   private boolean a(PlayerEntity playerEntity1) {
      return this.b(playerEntity1) ? this.f.a("Модератор") : this.f.a("Игрок");
   }

   private boolean b(PlayerEntity playerEntity1) {
      if (playerEntity1 == null) {
         return false;
      } else {
         String string2 = playerEntity1.getName().getString();
         if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.staffStorage != null && AspectDLC.INSTANCE.staffStorage.c(string2)) {
            return true;
         } else {
            Team team3 = playerEntity1.getScoreboardTeam();
            if (team3 == null) {
               return false;
            } else {
               String string4 = team3.getPrefix().getString().toLowerCase(Locale.ROOT);

               for (String string6 : d) {
                  if (string4.contains(string6)) {
                     return true;
                  }
               }

               return false;
            }
         }
      }
   }

   private void p() {
      this.q();
      String string1 = this.g.g();
      switch (string1) {
         case "В мейн меню":
            this.r();
            break;
         case "/hub":
            this.c("hub");
            break;
         case "/home":
            this.c("home home");
            break;
         case "/spawn":
            this.c("spawn");
      }
   }

   private void q() {
      if (this.h.getValue() && minecraftClient.getNetworkHandler() != null) {
         minecraftClient.getNetworkHandler().sendChatMessage("#stop");
      }
   }

   private void r() {
      if (minecraftClient.getNetworkHandler() == null) {
         dev.leg1tka.api.utils.text.ClientChat.a(
            "Модуль не работает в одиночном мире"
         );
      } else {
         minecraftClient.getNetworkHandler().getConnection().disconnect(Text.literal("AutoLeave"));
         if (this.i.getValue()) {
            this.toggle();
         }
      }
   }

   private void c(String string1) {
      if (minecraftClient.getNetworkHandler() == null) {
         dev.leg1tka.api.utils.text.ClientChat.a(
            "AutoLeave нельзя использовать в одиночной игре!"
         );
      } else {
         minecraftClient.getNetworkHandler().sendChatCommand(string1);
         this.j = this.i.getValue() ? 10 : 30;
         if (this.i.getValue()) {
            this.toggle();
         }
      }
   }



      public static void dev_leg1tka_o_ie_initializer() {}
}
