package dev.leg1tka.api.utils.player;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import lombok.Generated;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public final class EntityBoxGeometry implements dev.leg1tka.api.ClientAccess {
   public static Vec3d a(Entity entity0) {
      Vec3d vec3d1 = minecraftClient.player.getEyePos();
      Box box2 = entity0.getBoundingBox();
      double doubleValue3 = 0.1;
      Vec3d vec3d5 = null;
      double doubleValue6 = Double.MAX_VALUE;

      for (double doubleValue8 = box2.minX; doubleValue8 <= box2.maxX; doubleValue8 += doubleValue3) {
         for (double doubleValue10 = box2.minY; doubleValue10 <= box2.maxY; doubleValue10 += doubleValue3) {
            for (double doubleValue12 = box2.minZ; doubleValue12 <= box2.maxZ; doubleValue12 += doubleValue3) {
               Vec3d vec3d14 = new Vec3d(doubleValue8, doubleValue10, doubleValue12);
               double doubleValue15 = vec3d1.distanceTo(vec3d14);
               if (doubleValue15 < doubleValue6) {
                  doubleValue6 = doubleValue15;
                  vec3d5 = vec3d14;
               }
            }
         }
      }

      return vec3d5;
   }

   @Generated
   private EntityBoxGeometry() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
