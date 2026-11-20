package dev.leg1tka.rotation;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public abstract class RotationStrategy implements dev.leg1tka.api.ClientAccess {
   public Vec2f e = Vec2f.ZERO;

   public RotationStrategy() {
   }

   public abstract void a(LivingEntity livingEntity1);

   public static Vec2f a(float floatValue0, float floatValue1) {
      if ((floatValue0 != -90.0F || floatValue1 != 90.0F) && floatValue0 != -180.0F) {
         float floatValue2 = dev.leg1tka.rotation.RotationContext.c();
         floatValue0 -= floatValue0 % floatValue2;
         floatValue1 -= floatValue1 % floatValue2;
         return new Vec2f(floatValue0, floatValue1);
      } else {
         return new Vec2f(minecraftClient.player.getYaw(), minecraftClient.player.getPitch());
      }
   }

   protected boolean b(LivingEntity livingEntity1) {
      return minecraftClient.player != null && livingEntity1 != null && minecraftClient.player.isGliding() && livingEntity1.isGliding() && ModuleManager.O != null && ModuleManager.O.isEnabled() ? dev.leg1tka.api.utils.player.EntityFilters.e(livingEntity1) : false;
   }

   protected int a() {
      return ModuleManager.O == null ? 0 : Math.max(0, ModuleManager.O.e.e().intValue());
   }

   public static Vec3d a(LivingEntity livingEntity0, Vec3d vec3d1) {
      return minecraftClient.player != null && livingEntity0 != null && minecraftClient.player.isGliding() && livingEntity0.isGliding() && ModuleManager.O != null && ModuleManager.O.isEnabled() && dev.leg1tka.api.utils.player.EntityFilters.e(livingEntity0) ? dev.leg1tka.api.utils.player.EntityFilters.a(livingEntity0, vec3d1) : vec3d1;
   }

   protected Box c(LivingEntity livingEntity1) {
      Box box2 = BackTrackOld.c.a((Entity)livingEntity1);
      if (box2 == null) {
         box2 = livingEntity1.getBoundingBox();
      }

      if (box2 == null) {
         return null;
      } else if (!this.b(livingEntity1)) {
         return box2;
      } else {
         Vec3d vec3d3 = box2.getCenter();
         Vec3d vec3d4 = a(livingEntity1, vec3d3);
         return box2.offset(vec3d4.subtract(vec3d3));
      }
   }
}
