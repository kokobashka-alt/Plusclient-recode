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

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class EntityAimGeometry implements dev.leg1tka.api.ClientAccess {
   public EntityAimGeometry() {
   }

   public float a(LivingEntity livingEntity1) {
      return this.b(livingEntity1) <= 0.9 ? 0.93F : 1.0F;
   }

   private double b(LivingEntity livingEntity1) {
      Vec3d vec3d2 = minecraftClient.player.getEyePos();
      double doubleValue3 = MathHelper.clamp(vec3d2.x, livingEntity1.getBoundingBox().minX, livingEntity1.getBoundingBox().maxX);
      double doubleValue5 = MathHelper.clamp(vec3d2.z, livingEntity1.getBoundingBox().minZ, livingEntity1.getBoundingBox().maxZ);
      return Math.hypot(vec3d2.x - doubleValue3, vec3d2.z - doubleValue5);
   }
}
