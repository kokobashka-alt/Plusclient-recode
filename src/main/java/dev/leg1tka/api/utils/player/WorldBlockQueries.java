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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;

public final class WorldBlockQueries implements dev.leg1tka.api.ClientAccess {
   private static final Map c = new HashMap();
   private static final Mutable d = new Mutable();
   private static ClientWorld e;

   private WorldBlockQueries() {
   }

   public static Box a(PlayerEntity playerEntity0, int intValue1) {
      if (playerEntity0 == null) {
         return null;
      } else {
         Box box2 = playerEntity0.getBoundingBox();
         if (minecraftClient.world == null) {
            return box2;
         } else {
            Vec3d vec3d3 = c(playerEntity0);
            boolean flag4 = playerEntity0.isOnGround();
            double doubleValue5 = playerEntity0.hasNoGravity() ? 0.0 : playerEntity0.getFinalGravity();
            int intValue7 = MathHelper.clamp(intValue1, 0, 12);

            for (int intValue8 = 0; intValue8 < intValue7; intValue8++) {
               boolean flag9 = a(box2);
               Vec3d vec3d10 = flag4 && vec3d3.y <= 0.0 ? new Vec3d(vec3d3.x, -0.08, vec3d3.z) : vec3d3;
               if (flag9) {
                  vec3d10 = vec3d10.multiply(0.25, 0.05, 0.25);
               }

               Vec3d vec3d11 = Entity.adjustMovementForCollisions(playerEntity0, vec3d10, box2, minecraftClient.world, List.of());
               flag4 = vec3d10.y < 0.0 && Math.abs(vec3d11.y - vec3d10.y) > 1.0E-7;
               box2 = box2.offset(vec3d11);
               double doubleValue12 = Math.abs(vec3d11.x - vec3d10.x) > 1.0E-7 ? 0.0 : vec3d10.x;
               double doubleValue14 = Math.abs(vec3d11.z - vec3d10.z) > 1.0E-7 ? 0.0 : vec3d10.z;
               double doubleValue16 = Math.abs(vec3d11.y - vec3d10.y) > 1.0E-7 ? 0.0 : vec3d10.y;
               vec3d3 = new Vec3d(doubleValue12 * (flag4 ? 1.0 : 0.98), flag4 ? 0.0 : (doubleValue16 - doubleValue5) * 0.98, doubleValue14 * (flag4 ? 1.0 : 0.98));
            }

            return box2;
         }
      }
   }

   public static boolean a(PlayerEntity playerEntity0) {
      return playerEntity0 != null && a(playerEntity0.getBoundingBox());
   }

   public static boolean a(Box box0) {
      if (minecraftClient.world == null) {
         return false;
      } else {
         int intValue1 = MathHelper.floor(box0.minX + 1.0E-5);
         int intValue2 = MathHelper.floor(box0.minY + 1.0E-5);
         int intValue3 = MathHelper.floor(box0.minZ + 1.0E-5);
         int intValue4 = MathHelper.floor(box0.maxX - 1.0E-5);
         int intValue5 = MathHelper.floor(box0.maxY - 1.0E-5);
         int intValue6 = MathHelper.floor(box0.maxZ - 1.0E-5);

         for (int intValue7 = intValue1; intValue7 <= intValue4; intValue7++) {
            for (int intValue8 = intValue2; intValue8 <= intValue5; intValue8++) {
               for (int intValue9 = intValue3; intValue9 <= intValue6; intValue9++) {
                  if (minecraftClient.world.getBlockState(d.set(intValue7, intValue8, intValue9)).isOf(Blocks.COBWEB)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public static Vec3d b(PlayerEntity playerEntity0) {
      return playerEntity0 != null && minecraftClient.world != null ? c(playerEntity0) : Vec3d.ZERO;
   }

   private static Vec3d c(PlayerEntity playerEntity0) {
      if (e != minecraftClient.world) {
         c.clear();
         e = minecraftClient.world;
      }

      if (c.size() > 128) {
         c.clear();
      }

      int intValue1 = minecraftClient.player == null ? playerEntity0.age : minecraftClient.player.age;
      Vec3d vec3d2 = playerEntity0.getTrackedPosition().getPos();
      dev.leg1tka.api.utils.player.WorldBlockQueries.a value3 = (dev.leg1tka.api.utils.player.WorldBlockQueries.a)c.get(playerEntity0.getUuid());
      if (value3 != null && value3.tick == intValue1) {
         return value3.velocity;
      } else {
         Vec3d vec3d4 = new Vec3d(playerEntity0.getX() - playerEntity0.prevX, playerEntity0.getY() - playerEntity0.prevY, playerEntity0.getZ() - playerEntity0.prevZ);
         Vec3d vec3d5 = vec3d4;
         int intValue6 = intValue1;
         if (value3 != null && intValue1 > value3.tick && intValue1 - value3.tick <= 5) {
            if (vec3d2.squaredDistanceTo(value3.server) > 1.0E-6) {
               int intValue7 = Math.max(1, intValue1 - value3.serverTick);
               Vec3d vec3d8 = vec3d2.subtract(value3.server).multiply(1.0 / (double)intValue7);
               if (vec3d8.lengthSquared() <= 16.0) {
                  vec3d5 = new Vec3d(vec3d8.x * 0.75 + value3.velocity.x * 0.25, vec3d8.y, vec3d8.z * 0.75 + value3.velocity.z * 0.25);
               }
            } else {
               intValue6 = value3.serverTick;
               vec3d5 = intValue1 - intValue6 <= 2 ? value3.velocity : Vec3d.ZERO;
            }
         }

         if (!Double.isFinite(vec3d5.lengthSquared()) || vec3d5.lengthSquared() > 16.0) {
            vec3d5 = Vec3d.ZERO;
         }

         c.put(playerEntity0.getUuid(), new dev.leg1tka.api.utils.player.WorldBlockQueries.a(intValue1, intValue6, vec3d2, vec3d5));
         return vec3d5;
      }
   }

   static record a(int tick, int serverTick, Vec3d server, Vec3d velocity)  {



}
}
