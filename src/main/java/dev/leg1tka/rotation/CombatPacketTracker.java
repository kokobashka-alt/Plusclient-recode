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

import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventPacket;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class CombatPacketTracker implements dev.leg1tka.api.ClientAccess {
   private static float e;
   private float f = 24.0F;
   private float g = 6.0F;
   private volatile float h;
   private volatile float i;
   private float j;
   private float k;
   private boolean l;
   private boolean m;
   public float c;
   public float d;

   public CombatPacketTracker() {
   }

   @EventLink(
      priority = -200
   )
   public void onPacket(EventPacket eventPacket1) {
      if (!eventPacket1.isCancelled()) {
         if (eventPacket1.getType() == EventPacket.Type.SEND) {
            if (eventPacket1.getPacket() instanceof PlayerMoveC2SPacket playerMoveC2SPacket3 && playerMoveC2SPacket3.changesLook()) {
               this.h = playerMoveC2SPacket3.getYaw(1.0F);
               this.i = playerMoveC2SPacket3.getPitch(1.0F);
            }
         } else if (eventPacket1.getPacket() instanceof PlayerPositionLookS2CPacket playerPositionLookS2CPacket2) {
            this.h = playerPositionLookS2CPacket2.change().yaw();
            this.i = playerPositionLookS2CPacket2.change().pitch();
         }
      }
   }

   public boolean a() {
      return this.m;
   }

   public void a(LivingEntity livingEntity1, Vec3d vec3d2, Box box3, boolean flag4) {
      if (minecraftClient.player != null) {
         float floatValue5 = this.l ? this.j : d();
         float floatValue6 = this.l ? this.k : e();
         double doubleValue7 = MathHelper.clamp(livingEntity1.getEyeY() - livingEntity1.getY() - 0.3, 0.0, (double)livingEntity1.getHeight());
         Vec3d vec3d9 = livingEntity1.getPos().add(vec3d2).add(0.0, doubleValue7, 0.0).subtract(minecraftClient.player.getEyePos());
         float floatValue10 = (float)Math.toDegrees(Math.atan2(-vec3d9.x, vec3d9.z));
         float floatValue11 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d9.y, Math.hypot(vec3d9.x, vec3d9.z))), -90.0, 90.0);
         this.c = floatValue10;
         this.d = floatValue11;
         boolean flag12 = a(floatValue5, floatValue6, 10.0, box3);
         this.a(floatValue5, floatValue6, floatValue10, floatValue11, flag12);
         this.m = true;
         dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(this.j, this.k), a(45.0F, 60.0F), 1, 1, flag4);
      }
   }

   public void a(boolean flag1) {
      if (this.m && minecraftClient.player != null) {
         float floatValue2 = d();
         float floatValue3 = e();
         double doubleValue4 = Math.hypot((double)Math.abs(MathHelper.wrapDegrees(this.h - floatValue2)), (double)Math.abs(this.i - floatValue3));
         if (doubleValue4 < 3.0) {
            this.b();
         } else {
            float floatValue6 = this.l ? this.j : floatValue2;
            float floatValue7 = this.l ? this.k : floatValue3;
            this.a(floatValue6, floatValue7, floatValue2, floatValue3, false);
            dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(this.j, this.k), a(45.0F, 60.0F), 1, 1, flag1);
         }
      }
   }

   public void b() {
      this.m = false;
      this.l = false;
      dev.leg1tka.rotation.RotationStorage rotationStorage1 = dev.leg1tka.rotation.RotationStorage.c;
      if (minecraftClient.player != null && rotationStorage1 != null && rotationStorage1.b()) {
         if (dev.leg1tka.rotation.FreeLookStorage.a()) {
            minecraftClient.player.setYaw(dev.leg1tka.rotation.FreeLookStorage.b());
            minecraftClient.player.setPitch(MathHelper.clamp(dev.leg1tka.rotation.FreeLookStorage.c(), -90.0F, 90.0F));
         }

         rotationStorage1.a();
      }
   }

   public void c() {
      this.m = false;
      this.l = false;
   }

   private void a(float floatValue1, float floatValue2, float floatValue3, float floatValue4, boolean flag5) {
      float floatValue6 = a(45.0F, 60.0F);
      float floatValue7 = a(7.0F, 14.0F);
      float floatValue8 = 0.3F;
      if (flag5) {
         floatValue6 = 0.0F;
         floatValue7 = 0.0F;
      }

      this.f = this.f + (floatValue6 - this.f) * floatValue8;
      this.g = this.g + (floatValue7 - this.g) * floatValue8;
      float floatValue9 = MathHelper.wrapDegrees(floatValue3 - floatValue1);
      float floatValue10 = floatValue4 - floatValue2;
      float floatValue11 = Math.min(Math.abs(floatValue9), this.f);
      float floatValue12 = Math.min(Math.abs(floatValue10), this.g);
      float floatValue13 = MathHelper.clamp(floatValue9, -floatValue11, floatValue11);
      float floatValue14 = MathHelper.clamp(floatValue10, -floatValue12, floatValue12);
      float floatValue15 = floatValue1 + floatValue13;
      float floatValue16 = MathHelper.clamp(floatValue2 + floatValue14, -90.0F, 90.0F);
      double doubleValue17 = dev.leg1tka.rotation.RotationContext.a();
      this.j = a(floatValue15, this.h, doubleValue17);
      this.k = b(floatValue16, this.i, doubleValue17);
      this.l = true;
   }

   private static float a(float floatValue0, float floatValue1, double doubleValue2) {
      double doubleValue4 = (double)MathHelper.wrapDegrees(floatValue0 - floatValue1);
      long longValue6 = Math.round(doubleValue4 / doubleValue2);
      return (float)((double)floatValue1 + (double)longValue6 * doubleValue2);
   }

   private static float b(float floatValue0, float floatValue1, double doubleValue2) {
      double doubleValue4 = (double)MathHelper.clamp(floatValue0, -90.0F, 90.0F);
      long longValue6 = Math.round((doubleValue4 - (double)floatValue1) / doubleValue2);
      double doubleValue8 = (double)floatValue1 + (double)longValue6 * doubleValue2;
      if (doubleValue8 > 90.0) {
         longValue6 = (long)Math.floor((90.0 - (double)floatValue1) / doubleValue2);
      } else if (doubleValue8 < -90.0) {
         longValue6 = (long)Math.ceil((-90.0 - (double)floatValue1) / doubleValue2);
      }

      return (float)((double)floatValue1 + (double)longValue6 * doubleValue2);
   }

   private static boolean a(float floatValue0, float floatValue1, double doubleValue2, Box box4) {
      Vec3d vec3d5 = minecraftClient.player.getEyePos();
      return box4.contains(vec3d5) || box4.raycast(vec3d5, vec3d5.add(Vec3d.fromPolar(floatValue1, floatValue0).multiply(doubleValue2))).isPresent();
   }

   private static float d() {
      return dev.leg1tka.rotation.FreeLookStorage.a() ? dev.leg1tka.rotation.FreeLookStorage.b() : minecraftClient.player.getYaw();
   }

   private static float e() {
      return dev.leg1tka.rotation.FreeLookStorage.a() ? dev.leg1tka.rotation.FreeLookStorage.c() : minecraftClient.player.getPitch();
   }

   private static float a(float floatValue0, float floatValue1) {
      return floatValue0 + ThreadLocalRandom.current().nextFloat() * (floatValue1 - floatValue0);
   }
}
