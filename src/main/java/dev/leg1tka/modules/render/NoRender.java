package dev.leg1tka.modules.render;


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

public class NoRender extends Module {
   public static NoRender c = new NoRender();
   private final MultiSetting d = new MultiSetting(
      "Элементы",
      new BooleanSetting("Огонь", false),
      new BooleanSetting("Плохие эффекты", false),
      new BooleanSetting("Оверлей в блоке", false),
      new BooleanSetting("Частицы", false),
      new BooleanSetting("Погода", false),
      new BooleanSetting("Облака", false),
      new BooleanSetting("Блок-сущности", false),
      new BooleanSetting("Тени", false),
      new BooleanSetting("Анимацию тотема", false),
      new BooleanSetting("Тряска", false),
      new BooleanSetting("Виньетка", false),
      new BooleanSetting("Любые взрывы", false)
   );

   public NoRender() {
      super(
         "NoRender",
         "Убирает выбранные элементы рендера",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.d});
   }

   public boolean c(String string1) {
      return this.isEnabled() && this.d.a(string1);
   }

   public boolean p() {
      return this.c("Анимацию тотема");
   }
}
