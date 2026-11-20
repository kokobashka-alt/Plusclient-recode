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

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;

public class ViewModel extends Module {
   public static ViewModel c = new ViewModel();
   public final NumberSetting d = new NumberSetting("Правая рука X", 0.0F, -2.0F, 2.0F, 0.01F);
   public final NumberSetting e = new NumberSetting("Правая рука Y", 0.0F, -2.0F, 2.0F, 0.01F);
   public final NumberSetting f = new NumberSetting("Правая рука Z", 0.0F, -2.0F, 2.0F, 0.01F);
   public final NumberSetting g = new NumberSetting("Левая рука X", 0.0F, -2.0F, 2.0F, 0.01F);
   public final NumberSetting h = new NumberSetting("Левая рука Y", 0.0F, -2.0F, 2.0F, 0.01F);
   public final NumberSetting i = new NumberSetting("Левая рука Z", 0.0F, -2.0F, 2.0F, 0.01F);
   public final BooleanSetting j = new BooleanSetting("Только с аурой", false);

   public ViewModel() {
      super(
         "ViewModel",
         "Оффсеты рук от первого лица",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h, this.i, this.j});
   }

   public void a(MatrixStack matrixStack1, Arm arm2) {
      if (arm2 == Arm.RIGHT) {
         matrixStack1.translate(this.d.f(), this.e.f(), this.f.f());
      } else {
         matrixStack1.translate(this.g.f(), this.h.f(), this.i.f());
      }
   }
}
