package dev.leg1tka.settings;

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
import java.util.List;
import java.util.function.Supplier;

public class ThemeSetting extends Setting {
   public ThemeSetting(String string1) {
      super(string1);
   }

   public List<dev.leg1tka.managers.ThemeStorage.Theme> e() {
      return (List<dev.leg1tka.managers.ThemeStorage.Theme>)(AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null ? AspectDLC.INSTANCE.themeStorage.a() : List.of());
   }

   public dev.leg1tka.managers.ThemeStorage.Theme f() {
      return AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null ? AspectDLC.INSTANCE.themeStorage.b() : null;
   }

   public void a(dev.leg1tka.managers.ThemeStorage.Theme theme1) {
      if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null && theme1 != null) {
         AspectDLC.INSTANCE.themeStorage.a(theme1);
         if (theme1 != dev.leg1tka.managers.ThemeStorage.Theme.Custom && theme1.a().c != null && theme1.a().c.length > 0) {
            int intValue2 = theme1.a().c[0];
            int intValue3 = theme1.a().c.length > 1 ? theme1.a().c[1] : -15921904;
            theme1.a().a(intValue2, intValue3);
         }
      }
   }

   public dev.leg1tka.settings.ThemeSetting a(Supplier<Boolean> supplier1) {
      this.visibleCondition = supplier1;
      return this;
   }
}
