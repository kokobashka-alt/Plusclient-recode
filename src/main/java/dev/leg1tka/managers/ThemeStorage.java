package dev.leg1tka.managers;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Arrays;
import lombok.Generated;

public class ThemeStorage {
   private ObjectArrayList a = new ObjectArrayList();
   private dev.leg1tka.managers.ThemeStorage.Theme b;

   public ThemeStorage() {
      this.c();
   }

   private void c() {
      this.a
         .addAll(
            Arrays.asList(
               dev.leg1tka.managers.ThemeStorage.Theme.Custom,
               dev.leg1tka.managers.ThemeStorage.Theme.Purple,
               dev.leg1tka.managers.ThemeStorage.Theme.Red,
               dev.leg1tka.managers.ThemeStorage.Theme.Blue,
               dev.leg1tka.managers.ThemeStorage.Theme.Green,
               dev.leg1tka.managers.ThemeStorage.Theme.Pink,
               dev.leg1tka.managers.ThemeStorage.Theme.Orange,
               dev.leg1tka.managers.ThemeStorage.Theme.Blues,
               dev.leg1tka.managers.ThemeStorage.Theme.Yellows,
               dev.leg1tka.managers.ThemeStorage.Theme.Cyan,
               dev.leg1tka.managers.ThemeStorage.Theme.Magenta,
               dev.leg1tka.managers.ThemeStorage.Theme.Steel,
               dev.leg1tka.managers.ThemeStorage.Theme.Midnight,
               dev.leg1tka.managers.ThemeStorage.Theme.Mint,
               dev.leg1tka.managers.ThemeStorage.Theme.Coral,
               dev.leg1tka.managers.ThemeStorage.Theme.Amethyst,
               dev.leg1tka.managers.ThemeStorage.Theme.Sunset,
               dev.leg1tka.managers.ThemeStorage.Theme.Cherry,
               dev.leg1tka.managers.ThemeStorage.Theme.Teal,
               dev.leg1tka.managers.ThemeStorage.Theme.Lime,
               dev.leg1tka.managers.ThemeStorage.Theme.Wine,
               dev.leg1tka.managers.ThemeStorage.Theme.Crimson,
               dev.leg1tka.managers.ThemeStorage.Theme.Navy,
               dev.leg1tka.managers.ThemeStorage.Theme.Amber,
               dev.leg1tka.managers.ThemeStorage.Theme.Violet,
               dev.leg1tka.managers.ThemeStorage.Theme.Forest,
               dev.leg1tka.managers.ThemeStorage.Theme.Ruby,
               dev.leg1tka.managers.ThemeStorage.Theme.Sapphire,
               dev.leg1tka.managers.ThemeStorage.Theme.Pearl,
               dev.leg1tka.managers.ThemeStorage.Theme.Ember,
               dev.leg1tka.managers.ThemeStorage.Theme.Arctic,
               dev.leg1tka.managers.ThemeStorage.Theme.Neon,
               dev.leg1tka.managers.ThemeStorage.Theme.Shadow,
               dev.leg1tka.managers.ThemeStorage.Theme.Gold,
               dev.leg1tka.managers.ThemeStorage.Theme.Silver,
               dev.leg1tka.managers.ThemeStorage.Theme.Bronze
            )
         );
      this.b = (dev.leg1tka.managers.ThemeStorage.Theme)this.a.get(1);
   }

   @Generated
   public ObjectArrayList<dev.leg1tka.managers.ThemeStorage.Theme> a() {
      return this.a;
   }

   @Generated
   public dev.leg1tka.managers.ThemeStorage.Theme b() {
      return this.b;
   }

   @Generated
   public void a(ObjectArrayList<dev.leg1tka.managers.ThemeStorage.Theme> objectArrayList1) {
      this.a = objectArrayList1;
   }

   @Generated
   public void a(dev.leg1tka.managers.ThemeStorage.Theme theme1) {
      this.b = theme1;
   }

   public static enum Theme {
      Custom(new dev.leg1tka.gui.themes.ThemePalette("Rainbow", dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 0))),
      Purple(new dev.leg1tka.gui.themes.ThemePalette("Lavender", dev.leg1tka.api.utils.text.ColorUtils.a(190, 143, 255, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(190, 143, 255, 255), 0.35F))),
      Red(new dev.leg1tka.gui.themes.ThemePalette("Blood", dev.leg1tka.api.utils.text.ColorUtils.a(230, 50, 57, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(230, 50, 57, 255), 0.35F))),
      Blue(new dev.leg1tka.gui.themes.ThemePalette("Ocean", dev.leg1tka.api.utils.text.ColorUtils.a(95, 113, 191, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(95, 113, 191, 255), 0.35F))),
      Green(new dev.leg1tka.gui.themes.ThemePalette("Emerald", dev.leg1tka.api.utils.text.ColorUtils.a(60, 220, 140, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(60, 220, 140, 255), 0.35F))),
      Pink(new dev.leg1tka.gui.themes.ThemePalette("Rose", dev.leg1tka.api.utils.text.ColorUtils.a(255, 120, 190, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(255, 120, 190, 255), 0.35F))),
      Orange(new dev.leg1tka.gui.themes.ThemePalette("Gold", dev.leg1tka.api.utils.text.ColorUtils.a(252, 192, 88, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(252, 192, 88, 255), 0.35F))),
      Blues(new dev.leg1tka.gui.themes.ThemePalette("Diamond", dev.leg1tka.api.utils.text.ColorUtils.a(125, 217, 250, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(125, 217, 250, 255), 0.35F))),
      Yellows(new dev.leg1tka.gui.themes.ThemePalette("Sun", dev.leg1tka.api.utils.text.ColorUtils.a(252, 231, 88, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(252, 231, 88, 255), 0.35F))),
      Cyan(new dev.leg1tka.gui.themes.ThemePalette("Aqua", dev.leg1tka.api.utils.text.ColorUtils.a(64, 224, 208, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(64, 224, 208, 255), 0.35F))),
      Magenta(new dev.leg1tka.gui.themes.ThemePalette("Neon", dev.leg1tka.api.utils.text.ColorUtils.a(255, 64, 200, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(255, 64, 200, 255), 0.35F))),
      Steel(new dev.leg1tka.gui.themes.ThemePalette("Steel", dev.leg1tka.api.utils.text.ColorUtils.a(168, 178, 192, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(168, 178, 192, 255), 0.35F))),
      Midnight(new dev.leg1tka.gui.themes.ThemePalette("Void", dev.leg1tka.api.utils.text.ColorUtils.a(88, 72, 168, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(88, 72, 168, 255), 0.35F))),
      Mint(new dev.leg1tka.gui.themes.ThemePalette("Mint", dev.leg1tka.api.utils.text.ColorUtils.a(120, 255, 200, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(120, 255, 200, 255), 0.35F))),
      Coral(new dev.leg1tka.gui.themes.ThemePalette("Coral", dev.leg1tka.api.utils.text.ColorUtils.a(255, 127, 102, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(255, 127, 102, 255), 0.35F))),
      Amethyst(new dev.leg1tka.gui.themes.ThemePalette("Amethyst", dev.leg1tka.api.utils.text.ColorUtils.a(155, 89, 214, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(155, 89, 214, 255), 0.35F))),
      Sunset(new dev.leg1tka.gui.themes.ThemePalette("Sunset", dev.leg1tka.api.utils.text.ColorUtils.a(255, 140, 66, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(255, 94, 98, 255), 0.35F))),
      Cherry(new dev.leg1tka.gui.themes.ThemePalette("Cherry", dev.leg1tka.api.utils.text.ColorUtils.a(255, 82, 112, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(255, 82, 112, 255), 0.35F))),
      Teal(new dev.leg1tka.gui.themes.ThemePalette("Teal", dev.leg1tka.api.utils.text.ColorUtils.a(32, 178, 170, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(32, 178, 170, 255), 0.35F))),
      Lime(new dev.leg1tka.gui.themes.ThemePalette("Lime", dev.leg1tka.api.utils.text.ColorUtils.a(180, 255, 80, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(180, 255, 80, 255), 0.35F))),
      Wine(new dev.leg1tka.gui.themes.ThemePalette("Wine", dev.leg1tka.api.utils.text.ColorUtils.a(140, 36, 72, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(140, 36, 72, 255), 0.35F))),
      Crimson(new dev.leg1tka.gui.themes.ThemePalette("Crimson", dev.leg1tka.api.utils.text.ColorUtils.a(220, 20, 60, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(220, 20, 60, 255), 0.35F))),
      Navy(new dev.leg1tka.gui.themes.ThemePalette("Navy", dev.leg1tka.api.utils.text.ColorUtils.a(25, 25, 112, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(25, 25, 112, 255), 0.35F))),
      Amber(new dev.leg1tka.gui.themes.ThemePalette("Amber", dev.leg1tka.api.utils.text.ColorUtils.a(255, 191, 0, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(255, 191, 0, 255), 0.35F))),
      Violet(new dev.leg1tka.gui.themes.ThemePalette("Violet", dev.leg1tka.api.utils.text.ColorUtils.a(138, 43, 226, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(138, 43, 226, 255), 0.35F))),
      Forest(new dev.leg1tka.gui.themes.ThemePalette("Forest", dev.leg1tka.api.utils.text.ColorUtils.a(34, 139, 34, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(34, 139, 34, 255), 0.35F))),
      Ruby(new dev.leg1tka.gui.themes.ThemePalette("Ruby", dev.leg1tka.api.utils.text.ColorUtils.a(224, 17, 95, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(224, 17, 95, 255), 0.35F))),
      Sapphire(new dev.leg1tka.gui.themes.ThemePalette("Sapphire", dev.leg1tka.api.utils.text.ColorUtils.a(15, 82, 186, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(15, 82, 186, 255), 0.35F))),
      Pearl(new dev.leg1tka.gui.themes.ThemePalette("Pearl", dev.leg1tka.api.utils.text.ColorUtils.a(240, 234, 214, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(240, 234, 214, 255), 0.35F))),
      Ember(new dev.leg1tka.gui.themes.ThemePalette("Ember", dev.leg1tka.api.utils.text.ColorUtils.a(255, 69, 0, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(255, 69, 0, 255), 0.35F))),
      Arctic(new dev.leg1tka.gui.themes.ThemePalette("Arctic", dev.leg1tka.api.utils.text.ColorUtils.a(176, 224, 230, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(176, 224, 230, 255), 0.35F))),
      Neon(new dev.leg1tka.gui.themes.ThemePalette("Neon", dev.leg1tka.api.utils.text.ColorUtils.a(57, 255, 20, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(57, 255, 20, 255), 0.35F))),
      Shadow(new dev.leg1tka.gui.themes.ThemePalette("Shadow", dev.leg1tka.api.utils.text.ColorUtils.a(52, 52, 52, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(52, 52, 52, 255), 0.35F))),
      Gold(new dev.leg1tka.gui.themes.ThemePalette("Gold", dev.leg1tka.api.utils.text.ColorUtils.a(255, 215, 0, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(255, 215, 0, 255), 0.35F))),
      Silver(new dev.leg1tka.gui.themes.ThemePalette("Silver", dev.leg1tka.api.utils.text.ColorUtils.a(192, 192, 192, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(192, 192, 192, 255), 0.35F))),
      Bronze(new dev.leg1tka.gui.themes.ThemePalette("Bronze", dev.leg1tka.api.utils.text.ColorUtils.a(205, 127, 50, 255), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(205, 127, 50, 255), 0.35F)));

      final dev.leg1tka.gui.themes.ThemePalette a;

      @Generated
      private Theme(dev.leg1tka.gui.themes.ThemePalette value3) {
         this.a = value3;
      }

      @Generated
      public dev.leg1tka.gui.themes.ThemePalette a() {
         return this.a;
      }
   }
}
