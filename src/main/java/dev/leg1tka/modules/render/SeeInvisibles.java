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

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class SeeInvisibles extends Module {
   public static SeeInvisibles c = new SeeInvisibles();
   private static int d = -1;
   private final NumberSetting e = new NumberSetting("Прозрачность", 0.6F, 0.0F, 0.95F, 0.05F);
   private final BooleanSetting f = new BooleanSetting("Прозрачная броня", true);

   public SeeInvisibles() {
      super(
         "SeeInvisibles",
         "Показывает невидимых игроков полупрозрачными",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.e, this.f});
   }

   public int p() {
      return this.s() << 24 | 16777215;
   }

   private int s() {
      return Math.round((1.0F - MathHelper.clamp(this.e.f(), 0.0F, 0.95F)) * 255.0F);
   }

   public boolean a(PlayerEntity playerEntity1) {
      return this.isEnabled() && minecraftClient.player != null && playerEntity1 != null && playerEntity1 != minecraftClient.player && (playerEntity1.isInvisible() || playerEntity1.hasStatusEffect(StatusEffects.INVISIBILITY));
   }

   public void e(boolean flag1) {
      d = flag1 && this.f.getValue() ? this.s() : -1;
   }

   public static void q() {
      d = -1;
   }

   public static int r() {
      return d;
   }
}
