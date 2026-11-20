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

public class AspectRatio extends Module {
   public static AspectRatio c = new AspectRatio();
   private final ModeSetting d = new ModeSetting(
      "Соотношение", "4:3", "4:3", "16:9", "16:10", "5:4", "Свой"
   );
   private final NumberSetting e = new NumberSetting("Своё значение", 1.33F, 0.5F, 3.0F, 0.01F)
      .a(() -> this.d.b("Свой"));

   public AspectRatio() {
      super(
         "AspectRatio",
         "Растягивает изображение мира под выбранное соотношение сторон",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.d, this.e});
   }

   public float a(float floatValue1) {
      if (!this.isEnabled()) {
         return floatValue1;
      } else {
         String string2 = this.d.g();

         return switch (string2) {
            case "4:3" -> 1.3333334F;
            case "16:9" -> 1.7777778F;
            case "16:10" -> 1.6F;
            case "5:4" -> 1.25F;
            default -> this.e.f();
         };
      }
   }
}
