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

public class BotManager extends Module {
   public static final BotManager c = new BotManager();
   public final ModeSetting d = new ModeSetting(
      "Чат ботов",
      "Только свой",
      "Только свой",
      "Все в общий"
   );
   public final BooleanSetting e = new BooleanSetting("Уведомления", true);
   public final BooleanSetting f = new BooleanSetting("Авто-логин", true);
   public final BooleanSetting g = new BooleanSetting("Авто-регистрация", true);
   public final dev.leg1tka.settings.StringSetting h = new dev.leg1tka.settings.StringSetting("Пароль", "", 32).a(() -> this.f.getValue() || this.g.getValue());
   public final BooleanSetting i = new BooleanSetting("Физика в фоне", true);
   public final BooleanSetting j = new BooleanSetting("Авто-возрождение", true);
   public final BooleanSetting k = new BooleanSetting("Анти-AFK", false);
   public final MultiSetting l = new MultiSetting(
         "Действия",
         new BooleanSetting("Прыжок", true),
         new BooleanSetting("Ходьба", false),
         new BooleanSetting("Поворот камеры", true)
      )
      .a(this.k::getValue);
   public final NumberSetting m = new NumberSetting("Интервал (сек)", 15.0F, 1.0F, 120.0F, 1.0F).a(this.k::getValue);

   public BotManager() {
      super(
         "BotManager",
         "Фоновые боты: .bot connect/control/list, чат, автологин, анти-AFK",
         Category.MISC
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m});
   }

   @Override
   public void onDisable() {
      super.onDisable();
      dev.leg1tka.network.bots.BotController.j();
   }

   public static boolean p() {
      return c.isEnabled();
   }

   public boolean q() {
      return this.d.b("Все в общий");
   }

   public String r() {
      String string1 = this.h.e();
      return string1 != null && !string1.isBlank() ? string1.trim() : null;
   }
}
