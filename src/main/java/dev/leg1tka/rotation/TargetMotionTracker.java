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

import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public final class TargetMotionTracker implements dev.leg1tka.api.ClientAccess {
   public static float c;
   private final dev.leg1tka.rotation.TargetMotionHistory d = new dev.leg1tka.rotation.TargetMotionHistory();
   private LivingEntity e;
   private ClientWorld f;
   private dev.leg1tka.rotation.TargetMotionHistory.d g;
   private long h;
   private ClientWorld i;
   private volatile float j = 20.0F;
   private long k;

   public TargetMotionTracker() {
   }

   public synchronized void a(Packet<?> packet1) {
      if (packet1 instanceof WorldTimeUpdateS2CPacket) {
         long longValue2 = System.nanoTime();
         if (this.k != 0L && longValue2 > this.k) {
            float floatValue4 = Math.max(0.0F, Math.min(20.0F, 20.0F * (1.0E9F / (float)(longValue2 - this.k))));
            this.j = (float)Math.round(floatValue4 * 100.0F) / 100.0F;
         }

         this.k = longValue2;
      }
   }

   public void a(LivingEntity livingEntity1, float floatValue2, float floatValue3, float floatValue4, long longValue5) {
      this.c();
      if (minecraftClient.player != null && minecraftClient.world != null && livingEntity1 != null && livingEntity1.isAlive() && !livingEntity1.isRemoved()) {
         if (this.e != livingEntity1 || this.f != minecraftClient.world) {
            this.a();
            this.e = livingEntity1;
            this.f = minecraftClient.world;
         }

         this.h = d();
         Box box7 = livingEntity1.getBoundingBox();
         Vec3d vec3d8 = livingEntity1.getPos();
         Box box9 = box7.offset(livingEntity1.getLerpTargetX() - vec3d8.x, livingEntity1.getLerpTargetY() - vec3d8.y, livingEntity1.getLerpTargetZ() - vec3d8.z);
         this.d.a(this.h, a(box9));
         PlayerListEntry playerListEntry10 = minecraftClient.getNetworkHandler() == null ? null : minecraftClient.getNetworkHandler().getPlayerListEntry(minecraftClient.player.getUuid());
         int intValue11 = playerListEntry10 == null ? 0 : playerListEntry10.getLatency();
         Vec3d vec3d12 = minecraftClient.player.getEyePos();
         Vec3d vec3d13 = Vec3d.fromPolar(floatValue3, floatValue2);
         this.g = this.d
            .a(
               this.h,
               a(box7),
               vec3d12.x,
               vec3d12.y,
               vec3d12.z,
               intValue11,
               (double)this.j,
               new dev.leg1tka.rotation.TargetMotionHistory.b(vec3d13.x, vec3d13.y, vec3d13.z, (double)livingEntity1.getTargetingMargin()),
               (double)floatValue4,
               longValue5
            );
      } else {
         this.a();
      }
   }

   private void c() {
      if (this.i != minecraftClient.world) {
         this.b();
         this.i = minecraftClient.world;
      }
   }

   public boolean a(Entity entity1) {
      return this.g != null
         && minecraftClient.player != null
         && entity1 != null
         && entity1 == this.e
         && entity1.isAlive()
         && !entity1.isRemoved()
         && this.f == minecraftClient.world
         && d() - this.h <= 100L;
   }

   public Box b(Entity entity1) {
      if (!this.a(entity1)) {
         return entity1.getBoundingBox();
      } else {
         dev.leg1tka.rotation.TargetMotionHistory.a value2 = this.g.box();
         return new Box(value2.minX(), value2.minY(), value2.minZ(), value2.maxX(), value2.maxY(), value2.maxZ());
      }
   }

   public Vec3d c(Entity entity1) {
      if (!this.a(entity1)) {
         return Vec3d.ZERO;
      } else {
         dev.leg1tka.rotation.TargetMotionHistory.a value2 = this.g.box();
         Box box3 = entity1.getBoundingBox();
         Vec3d vec3d4 = new Vec3d((value2.minX() + value2.maxX() - box3.minX - box3.maxX) * 0.5, value2.minY() - box3.minY, (value2.minZ() + value2.maxZ() - box3.minZ - box3.maxZ) * 0.5);
         return vec3d4.lengthSquared() < 1.0E-12 ? Vec3d.ZERO : vec3d4;
      }
   }

   public Vec3d d(Entity entity1) {
      if (!this.a(entity1)) {
         return Vec3d.ZERO;
      } else if (this.g.historical()) {
         return this.c(entity1);
      } else {
         dev.leg1tka.rotation.TargetMotionHistory.a value2 = this.d.a(d(), this.g.windowMs());
         if (value2 == null) {
            return Vec3d.ZERO;
         } else {
            Box box3 = entity1.getBoundingBox();
            return new Vec3d((value2.minX() + value2.maxX() - box3.minX - box3.maxX) * 0.5, value2.minY() - box3.minY, (value2.minZ() + value2.maxZ() - box3.minZ - box3.maxZ) * 0.5);
         }
      }
   }

   public float e(Entity entity1) {
      return this.a(entity1) ? (float)this.g.reach() : 3.0F;
   }

   public boolean f(Entity entity1) {
      return this.a(entity1) && this.g.historical();
   }

   public void a() {
      this.d.a();
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = 0L;
   }

   public synchronized void b() {
      this.a();
      this.i = null;
      this.j = 20.0F;
      this.k = 0L;
   }

   private static long d() {
      return System.nanoTime() / 1000000L;
   }

   private static dev.leg1tka.rotation.TargetMotionHistory.a a(Box box0) {
      return new dev.leg1tka.rotation.TargetMotionHistory.a(box0.minX, box0.minY, box0.minZ, box0.maxX, box0.maxY, box0.maxZ);
   }
}
