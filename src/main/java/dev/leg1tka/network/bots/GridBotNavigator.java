package dev.leg1tka.network.bots;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.util.List;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class GridBotNavigator implements dev.leg1tka.network.bots.BotNavigator {
   private static int a;
   private static int b;
   private static int c;
   private static long d;
   private final dev.leg1tka.network.bots.BotInput e;
   private BlockPos f;
   private List g;
   private int h;
   private int i;
   private int j;
   private int k;
   private Vec3d l;
   private int m;
   private String n = "";

   public GridBotNavigator(dev.leg1tka.network.bots.BotSession value1) {
      this.e = value1.e();
   }

   public boolean a(dev.leg1tka.network.bots.BotSession value1) {
      return this.e == value1.e();
   }

   @Override
   public void a(BlockPos blockPos1) {
      this.f = blockPos1;
      this.g = null;
      this.h = 0;
      this.i = 0;
      this.j = 0;
      this.k = 0;
      this.l = null;
      this.n = "";
      this.e.a();
   }

   @Override
   public dev.leg1tka.network.bots.BotNavigator.a a(ClientPlayerEntity clientPlayerEntity1, ClientWorld clientWorld2) {
      if (this.f == null) {
         return dev.leg1tka.network.bots.BotNavigator.a.FAILED;
      } else {
         this.i++;
         double doubleValue3 = dev.leg1tka.network.bots.BotBlockActions.a(clientPlayerEntity1);
         if (clientPlayerEntity1.isOnGround() && dev.leg1tka.network.bots.BotBlockActions.a(clientWorld2, clientPlayerEntity1, clientPlayerEntity1.getEyePos(), this.f, doubleValue3) != null) {
            this.e.a();
            return dev.leg1tka.network.bots.BotNavigator.a.ARRIVED;
         } else if (this.i > 900) {
            this.n = "таймаут 45с";
            this.e.a();
            return dev.leg1tka.network.bots.BotNavigator.a.FAILED;
         } else if (this.g == null) {
            this.e.a();
            if (this.k > 0) {
               this.k--;
               return dev.leg1tka.network.bots.BotNavigator.a.WALKING;
            } else if (!clientPlayerEntity1.isOnGround() && !clientPlayerEntity1.isTouchingWater()) {
               return dev.leg1tka.network.bots.BotNavigator.a.WALKING;
            } else {
               long longValue5 = System.currentTimeMillis() / 50L;
               if (longValue5 == d) {
                  return dev.leg1tka.network.bots.BotNavigator.a.WALKING;
               } else {
                  d = longValue5;
                  this.g = new dev.leg1tka.network.bots.GridPathSearch(clientWorld2, clientPlayerEntity1).a(this.f, doubleValue3 - 0.2);
                  if (this.g == null) {
                     return this.a("путь не найден");
                  } else {
                     this.h = this.g.size() > 1 ? 1 : 0;
                     this.l = null;
                     return dev.leg1tka.network.bots.BotNavigator.a.WALKING;
                  }
               }
            }
         } else {
            return this.a(clientPlayerEntity1);
         }
      }
   }

   @Override
   public void a(ClientPlayerEntity clientPlayerEntity1, float floatValue2, float floatValue3) {
      this.e.a();
      b(clientPlayerEntity1, floatValue2, floatValue3);
   }

   @Override
   public void a() {
      this.e.a();
   }

   @Override
   public void b() {
      this.e.a();
      this.f = null;
      this.g = null;
   }

   @Override
   public String c() {
      return this.n;
   }

   private dev.leg1tka.network.bots.BotNavigator.a a(ClientPlayerEntity clientPlayerEntity1) {
      Vec3d vec3d2 = (Vec3d)this.g.get(this.h);
      double doubleValue3 = vec3d2.x - clientPlayerEntity1.getX();
      double doubleValue5 = vec3d2.z - clientPlayerEntity1.getZ();
      double doubleValue7 = Math.sqrt(doubleValue3 * doubleValue3 + doubleValue5 * doubleValue5);

      double doubleValue9;
      for (doubleValue9 = vec3d2.y - clientPlayerEntity1.getY(); doubleValue7 < 0.35 && Math.abs(doubleValue9) < 1.0 && this.h < this.g.size() - 1; doubleValue9 = vec3d2.y - clientPlayerEntity1.getY()) {
         vec3d2 = (Vec3d)this.g.get(++this.h);
         doubleValue3 = vec3d2.x - clientPlayerEntity1.getX();
         doubleValue5 = vec3d2.z - clientPlayerEntity1.getZ();
         doubleValue7 = Math.sqrt(doubleValue3 * doubleValue3 + doubleValue5 * doubleValue5);
      }

      if (this.h == this.g.size() - 1 && doubleValue7 < 0.35 && Math.abs(doubleValue9) < 1.0) {
         return this.a(
            "сундук не виден с конца пути"
         );
      } else if (!(doubleValue9 > 2.0) && !(a(clientPlayerEntity1, this.h > 0 ? (Vec3d)this.g.get(this.h - 1) : vec3d2, vec3d2) > 1.6)) {
         if (this.l == null || this.i - this.m >= 40) {
            boolean flag11 = this.l != null && clientPlayerEntity1.getPos().squaredDistanceTo(this.l) < 0.36;
            this.l = clientPlayerEntity1.getPos();
            this.m = this.i;
            if (flag11) {
               return this.a("застрял на месте");
            }
         }

         float floatValue20 = dev.leg1tka.network.bots.BotBlockActions.a(clientPlayerEntity1.getPos(), vec3d2);
         float floatValue12 = a(clientPlayerEntity1.getYaw(), floatValue20, 40.0F);
         b(clientPlayerEntity1, floatValue12, a(clientPlayerEntity1.getPitch(), 8.0F, 10.0F));
         float floatValue13 = Math.abs(MathHelper.wrapDegrees(floatValue20 - floatValue12));
         boolean flag14 = floatValue13 < 75.0F;
         boolean flag15 = doubleValue9 > 0.6;
         boolean flag16 = clientPlayerEntity1.isOnGround() && flag14 && (flag15 && doubleValue7 < 1.4 || clientPlayerEntity1.horizontalCollision);
         if (clientPlayerEntity1.isTouchingWater() || clientPlayerEntity1.isInLava()) {
            flag16 = true;
         }

         boolean flag17 = flag14 && floatValue13 < 20.0F && doubleValue7 > 2.5 && !flag15 && clientPlayerEntity1.getHungerManager().getFoodLevel() > 6;
         this.e.a(flag14, flag16, flag17);
         return dev.leg1tka.network.bots.BotNavigator.a.WALKING;
      } else {
         return this.a("сбился с пути");
      }
   }

   private dev.leg1tka.network.bots.BotNavigator.a a(String string1) {
      this.g = null;
      this.e.a();
      if (++this.j > 4) {
         this.n = string1;
         return dev.leg1tka.network.bots.BotNavigator.a.FAILED;
      } else {
         this.k = 10;
         return dev.leg1tka.network.bots.BotNavigator.a.WALKING;
      }
   }

   private static double a(ClientPlayerEntity clientPlayerEntity0, Vec3d vec3d1, Vec3d vec3d2) {
      double doubleValue3 = vec3d2.x - vec3d1.x;
      double doubleValue5 = vec3d2.z - vec3d1.z;
      double doubleValue7 = doubleValue3 * doubleValue3 + doubleValue5 * doubleValue5;
      double doubleValue9 = doubleValue7 < 1.0E-6 ? 0.0 : MathHelper.clamp(((clientPlayerEntity0.getX() - vec3d1.x) * doubleValue3 + (clientPlayerEntity0.getZ() - vec3d1.z) * doubleValue5) / doubleValue7, 0.0, 1.0);
      double doubleValue11 = vec3d1.x + doubleValue3 * doubleValue9 - clientPlayerEntity0.getX();
      double doubleValue13 = vec3d1.z + doubleValue5 * doubleValue9 - clientPlayerEntity0.getZ();
      return Math.sqrt(doubleValue11 * doubleValue11 + doubleValue13 * doubleValue13);
   }

   private static float a(float floatValue0, float floatValue1, float floatValue2) {
      return floatValue0 + MathHelper.clamp(MathHelper.wrapDegrees(floatValue1 - floatValue0), -floatValue2, floatValue2);
   }

   private static void b(ClientPlayerEntity clientPlayerEntity0, float floatValue1, float floatValue2) {
      clientPlayerEntity0.setYaw(floatValue1);
      clientPlayerEntity0.setPitch(MathHelper.clamp(floatValue2, -90.0F, 90.0F));
      clientPlayerEntity0.setHeadYaw(floatValue1);
   }
}
