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

public class SwingAnimations extends Module {
   public static SwingAnimations c = new SwingAnimations();
   public boolean d = true;
   public boolean e = true;
   public boolean f = false;
   public final BooleanSetting g = new BooleanSetting("Мод на красивые руки", false);
   public final ModeSetting h = new ModeSetting(
         "Вид анимации",
         "Классик",
         "Классик",
         "Шарп"
      )
      .a(this.g::getValue);
   public final NumberSetting i = new NumberSetting(
         "Плавность анимации", 1.0F, 0.35F, 2.5F, 0.05F
      )
      .a(this.g::getValue);
   public final BooleanSetting j = new BooleanSetting("каст режим", false).visibleWhen(this.g::getValue);
   public final BooleanSetting k = new BooleanSetting("Анимация свинга", true).visibleWhen(() -> !this.g.getValue());
   public final ModeSetting l = new ModeSetting(
         "Тип свинга",
         "Smooth",
         "Smooth",
         "Static",
         "Down",
         "DropDown",
         "Poke",
         "SelfBack",
         "Feast",
         "ToBack",
         "Block",
         "Akrien",
         "Break",
         "Pander",
         "Slant"
      )
      .a(() -> !this.g.getValue() && this.k.getValue());
   public final NumberSetting m = new NumberSetting("Сила анимации", 1.0F, 0.1F, 3.0F, 0.01F)
      .a(() -> !this.g.getValue() && this.k.getValue() && !this.l.b("Pander"));
   public final NumberSetting n = new NumberSetting("Угол DropDown", 12.0F, 1.0F, 360.0F, 1.0F).a(() -> !this.g.getValue() && this.k.getValue() && this.l.b("DropDown"));
   public final NumberSetting o = new NumberSetting("Наклон DropDown", 12.0F, 1.0F, 360.0F, 1.0F)
      .a(() -> !this.g.getValue() && this.k.getValue() && this.l.b("DropDown"));
   public final BooleanSetting p = new BooleanSetting("Плавная анимация", false).visibleWhen(() -> !this.g.getValue());
   public final NumberSetting q = new NumberSetting("Скорость анимации", 12.0F, 1.0F, 50.0F, 1.0F)
      .a(() -> !this.g.getValue() && this.p.getValue());
   public final BooleanSetting r = new BooleanSetting("Только при Aura", false).visibleWhen(() -> !this.g.getValue());
   public final BooleanSetting s = new BooleanSetting("Свап рук", false).visibleWhen(() -> !this.g.getValue());
   public final BooleanSetting t = new BooleanSetting("каст режим", false).visibleWhen(() -> !this.g.getValue() && this.k.getValue());
   public final BooleanSetting u = new BooleanSetting("Анимация еды", false).visibleWhen(() -> !this.g.getValue());

   public SwingAnimations() {
      super(
         "SwingAnimations",
         "Кастомная анимация аттаки",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u});
   }
}
