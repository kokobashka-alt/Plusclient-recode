package dev.leg1tka.modules.movement;


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
import dev.leg1tka.api.events.implement.EventMove;
import net.minecraft.util.math.Vec3d;
public class ElytraMotion extends Module {
   public static final ElytraMotion c = new ElytraMotion();
   public NumberSetting d = new NumberSetting("Дистанция до игрока", 3.0F, 0.0F, 6.0F, 0.1F);
   public BooleanSetting e = new BooleanSetting("Обход", false);

   public ElytraMotion() {
      super(
         "ElytraMotion",
         "Зависает рядом с игроком на эликах",
         Category.MOVEMENT
      );
      this.addSettings(new Setting[]{this.d, this.e});
   }

   @EventLink
   public void onMove(EventMove eventMove1) {
      if (minecraftClient.player == null || !minecraftClient.player.isGliding()) return;
      net.minecraft.entity.LivingEntity target = AttackAura.c.E();
      if (target != null && target.isAlive()) {
         double dist = minecraftClient.player.distanceTo(target);
         if (dist <= this.d.e().floatValue()) {
            Vec3d targetVel = target.getVelocity();
            if (this.e.getValue()) {
               eventMove1.setMovePos(new Vec3d(targetVel.x * 0.95, targetVel.y, targetVel.z * 0.95));
            } else {
               eventMove1.setMovePos(new Vec3d(0.0, 0.0, 0.0));
            }
         }
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
   }

   public static void dev_leg1tka_o_gp_initializer() {}
}
