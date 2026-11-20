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

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventPacket;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.item.MaceItem;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;

public final class AttackTimingController implements dev.leg1tka.api.ClientAccess {
   public static String c;
   public static String d;
   public static String e;
   public static String f;
   public static String g;
   public static String h;
   public static String i;
   public static String j;
   public final dev.leg1tka.rotation.AttackTimingController.a k = new dev.leg1tka.rotation.AttackTimingController.a();
   public int l;
   private long m = System.currentTimeMillis();
   private int n = 20;
   private volatile boolean o;
   private boolean p;
   private boolean q = true;
   private double r;
   private double s;
   private double t;
   private double u;
   private float v;
   private boolean w;
   private boolean x;
   private int y;
   private int z;

   public AttackTimingController() {
   }

   public void a() {
      if (this.o) {
         this.o = false;
         this.n = 20;
         this.g();
         this.f();
         this.r();
      }

      if (minecraftClient.player != null && this.n < 20) {
         this.n++;
      }

      if (this.z > 0) {
         this.z--;
      }
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      Packet packet2 = eventPacket1.getPacket();
      if (eventPacket1.getType() != EventPacket.Type.RECEIVE) {
         if (packet2 instanceof PlayerMoveC2SPacket playerMoveC2SPacket3) {
            this.a(playerMoveC2SPacket3);
         }

         if (packet2 instanceof HandSwingC2SPacket) {
            this.m = System.currentTimeMillis();
         }
      } else {
         if (packet2 instanceof GameJoinS2CPacket || packet2 instanceof PlayerRespawnS2CPacket) {
            this.o = true;
         }
      }
   }

   public void a(dev.leg1tka.rotation.AttackTimingController.a value1, Runnable runnable2) {
      if (!value1.e && this.e(value1)) {
         boolean flag4 = this.f(value1) || this.n();
         if (!flag4) {
            this.l();
            this.g(value1);
         } else {
            if (this.a(value1, 0)) {
               this.a(value1);
               runnable2.run();
               this.j();
               this.l();
               this.g(value1);
            }
         }
      } else {
         this.l();
         this.g(value1);
      }
   }

   private void j() {
      this.m = System.currentTimeMillis();
      this.n = 0;
   }

   public void a(dev.leg1tka.rotation.AttackTimingController.a value1) {
      if (!value1.e && this.e(value1)) {
         if (value1.h && minecraftClient.player.isUsingItem() && minecraftClient.player.getActiveItem().getItem() == Items.SHIELD) {
            minecraftClient.interactionManager.stopUsingItem(minecraftClient.player);
         }

         if (value1.a() && !this.q()) {
            if (!value1.a("Быстрый")) {
               if (value1.a("Packet")) {
                  this.k();
               } else if (value1.a("СпейсТайм")) {
                  minecraftClient.player.setSprinting(false);
               } else if (value1.a("Ультра")) {
                  this.m();
               } else {
                  if (value1.a("Легитный")) {
                     if (Sprint.c.isEnabled()) {
                        this.l = 1;
                     }

                     minecraftClient.options.sprintKey.setPressed(false);
                     minecraftClient.player.setSprinting(false);
                  }
               }
            }
         }
      }
   }

   private void g(dev.leg1tka.rotation.AttackTimingController.a value1) {
      if (value1 != null && value1.a() && !value1.a("Быстрый") && !Sprint.c.isEnabled() && minecraftClient.getWindow() != null) {
         minecraftClient.options.sprintKey.setPressed(InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), minecraftClient.options.sprintKey.getDefaultKey().getCode()));
      }
   }

   private void k() {
      if (!this.w && minecraftClient.player != null && minecraftClient.player.networkHandler != null) {
         this.x = minecraftClient.player.isSprinting() || o();
         if (this.x) {
            this.w = true;
            minecraftClient.options.sprintKey.setPressed(false);
            minecraftClient.player.setSprinting(false);
            minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.STOP_SPRINTING));
         }
      }
   }

   private void l() {
      if (this.w) {
         boolean flag1 = this.x;
         this.w = false;
         this.x = false;
         if (flag1 && minecraftClient.player != null && minecraftClient.player.networkHandler != null) {
            minecraftClient.player.setSprinting(true);
            minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.START_SPRINTING));
         }
      }
   }

   public void b() {
      this.l();
   }

   public void b(dev.leg1tka.rotation.AttackTimingController.a value1) {
      if (this.y > 0) {
         this.y--;
      }

      if (value1 != null && value1.d && value1.a("Быстрый") && minecraftClient.player != null && minecraftClient.world != null) {
         if (this.e(value1) && this.a(value1, 1)) {
            this.y = 1;
            minecraftClient.player.setSprinting(false);
         }
      }
   }

   public boolean c() {
      return this.y > 0 && this.k.d && this.k.a("Быстрый");
   }

   private void m() {
      if (!minecraftClient.player.isSprinting() && !o()) {
         this.z = Math.max(this.z, 2);
      } else {
         minecraftClient.options.sprintKey.setPressed(false);
         minecraftClient.player.setSprinting(false);
         this.z = 2 + ThreadLocalRandom.current().nextInt(3);
      }
   }

   public boolean d() {
      return this.z > 0 && this.k.d && this.k.a("Ультра");
   }

   public boolean e() {
      return this.c() || this.d();
   }

   public void f() {
      this.z = 0;
   }

   public void g() {
      this.y = 0;
   }

   public void h() {
      this.b();
      this.g();
      this.f();
      this.k.d = false;
      this.k.a = null;
   }

   public boolean i() {
      if (minecraftClient.player == null) {
         return false;
      } else if (this.c()) {
         minecraftClient.player.setSprinting(false);
         return true;
      } else if (this.l != 0) {
         minecraftClient.player.setSprinting(false);
         minecraftClient.options.sprintKey.setPressed(false);
         this.l--;
         return true;
      } else {
         dev.leg1tka.rotation.AttackTimingController.a value1 = this.k;
         if (!value1.d || value1.a == null || !value1.a() || !this.e(value1) || !this.c(value1) && !this.w && !this.d()) {
            return false;
         } else {
            minecraftClient.player.setSprinting(false);
            minecraftClient.options.sprintKey.setPressed(false);
            return true;
         }
      }
   }

   public boolean a(dev.leg1tka.rotation.AttackTimingController.a value1, int intValue2) {
      for (int intValue3 = 0; intValue3 <= intValue2; intValue3++) {
         if (this.b(value1, intValue3)) {
            return true;
         }
      }

      return false;
   }

   public boolean c(dev.leg1tka.rotation.AttackTimingController.a value1) {
      if (value1.e) {
         return false;
      } else if (value1.a("Быстрый")) {
         return this.c();
      } else if (value1.a("СпейсТайм") && this.d(value1)) {
         return true;
      } else {
         return this.e(value1) && this.a(value1, 0) && (this.f(value1) || this.n())
            ? (value1.k ? this.b(1) : this.a(value1, 60L)) && !minecraftClient.player.isSubmergedInWater() && (minecraftClient.player.isOnGround() || minecraftClient.player.getVelocity().y <= 0.2)
            : false;
      }
   }

   public boolean d(dev.leg1tka.rotation.AttackTimingController.a value1) {
      return value1 != null && value1.d && value1.a("СпейсТайм") && minecraftClient.player != null && !value1.e
         ? this.e(value1)
            && !minecraftClient.player.isOnGround()
            && !minecraftClient.player.isSubmergedInWater()
            && !this.q()
            && minecraftClient.player.getVelocity().y <= 0.16477328182606651
            && this.a(value1, 3)
         : false;
   }

   public boolean e(dev.leg1tka.rotation.AttackTimingController.a value1) {
      if (minecraftClient.player != null && value1 != null && value1.a != null && value1.a.isAlive() && !value1.a.isRemoved()) {
         Box box3 = value1.c != null ? value1.c : value1.a.getBoundingBox();
         if (box3 == null) return false;
         Vec3d vec3d2 = minecraftClient.player.getEyePos();
         double doubleValue4 = vec3d2.x - MathHelper.clamp(vec3d2.x, box3.minX, box3.maxX);
         double doubleValue6 = vec3d2.y - MathHelper.clamp(vec3d2.y, box3.minY, box3.maxY);
         double doubleValue8 = vec3d2.z - MathHelper.clamp(vec3d2.z, box3.minZ, box3.maxZ);
         double doubleValue10 = (double)value1.b;
         return doubleValue4 * doubleValue4 + doubleValue6 * doubleValue6 + doubleValue8 * doubleValue8 <= doubleValue10 * doubleValue10;
      } else {
         return false;
      }
   }

   public boolean f(dev.leg1tka.rotation.AttackTimingController.a value1) {
      if (value1 == null || value1.a == null) return false;
      if (AttackAura.c != null && !AttackAura.c.A()) {
         return true;
      }
      Box box = value1.c != null ? value1.c : value1.a.getBoundingBox();
      if (box == null) return false;
      float yaw = minecraftClient.player.getYaw();
      float pitch = minecraftClient.player.getPitch();
      if (dev.leg1tka.rotation.RotationStorage.c != null && dev.leg1tka.rotation.RotationStorage.c.b()) {
         dev.leg1tka.rotation.Rotation rot = dev.leg1tka.rotation.RotationStorage.c.k();
         if (rot != null) {
            yaw = rot.getYaw();
            pitch = rot.getPitch();
         }
      }
      Vec3d vec3d2 = minecraftClient.player.getEyePos();
      Vec3d vec3d3 = Vec3d.fromPolar(pitch, yaw);
      return box.contains(vec3d2) || box.raycast(vec3d2, vec3d2.add(vec3d3.multiply((double)value1.b))).isPresent();
   }

   private boolean n() {
      return AirStuck.c != null && AirStuck.c.isEnabled() && AirStuck.c.p();
   }

   private static boolean o() {
      return AspectDLC.INSTANCE.serverStorage.i();
   }

   private boolean p() {
      return o() && !minecraftClient.player.isGliding() && !minecraftClient.player.isTouchingWater();
   }

   private boolean q() {
      return minecraftClient.player.isTouchingWater() || minecraftClient.player.isSubmergedInWater() || minecraftClient.player.isSwimming();
   }

   private boolean c(dev.leg1tka.rotation.AttackTimingController.a value1, int intValue2) {
      return this.a(value1, (long)Math.max(0, intValue2) * 50L);
   }

   private boolean a(dev.leg1tka.rotation.AttackTimingController.a value1, long longValue2) {
      long longValue4 = Math.max(0L, longValue2);
      float floatValue6 = 0.9F;
      if ("Быстрый".equals(value1.m)) {
         floatValue6 = dev.leg1tka.api.utils.player.PlayerEnvironment.a();
      }

      float floatValue7 = 0.5F + (float)longValue4 / 50.0F;
      long longValue8 = Math.max(0L, 250L - longValue4);
      return minecraftClient.player != null && minecraftClient.player.getAttackCooldownProgress(floatValue7) >= floatValue6 && System.currentTimeMillis() - this.m >= longValue8;
   }

   public boolean b(dev.leg1tka.rotation.AttackTimingController.a value1, int intValue2) {
      if (value1 == null || value1.a == null || !value1.a.isAlive()) {
         return false;
      } else if (value1.j && a(minecraftClient.currentScreen)) {
         return false;
      } else if (minecraftClient.player.isUsingItem() && minecraftClient.player.getActiveItem().getItem() != Items.SHIELD && value1.i) {
         return false;
      } else {
         if (value1.k) {
            if (!this.b(intValue2)) {
               return false;
            }

            if (this.s() && minecraftClient.player.getVelocity().y < 0.0) {
               return true;
            }
         } else if (!this.c(value1, intValue2)) {
            return false;
         }

         if (this.n()) {
            return true;
         } else {
            dev.leg1tka.rotation.AttackTimingController.b value3 = this.a(intValue2);
            if (!value1.f || this.a(value3)) {
               return true;
            } else {
               return value1.g && value3.onGround && !minecraftClient.options.jumpKey.isPressed() ? true : this.a(value1, value3);
            }
         }
      }
   }

   private boolean a(dev.leg1tka.rotation.AttackTimingController.b value1) {
      return minecraftClient.player.hasStatusEffect(StatusEffects.BLINDNESS)
         || minecraftClient.player.hasStatusEffect(StatusEffects.LEVITATION)
         || a(value1.box.expand(-0.001))
         || minecraftClient.player.isSubmergedInWater()
         || minecraftClient.player.isInLava()
         || minecraftClient.player.isClimbing()
         || minecraftClient.player.isGliding()
         || minecraftClient.player.getAbilities().flying;
   }

   private boolean a(dev.leg1tka.rotation.AttackTimingController.a value1, dev.leg1tka.rotation.AttackTimingController.b value2) {
      boolean flag3 = value2.horizontalCollision || !minecraftClient.world.isSpaceEmpty(minecraftClient.player, value2.box.expand(0.22, 0.0, 0.22).contract(1.0E-7));
      boolean flag4 = minecraftClient.player.hurtTime > 0 || minecraftClient.player.hasStatusEffect(StatusEffects.SLOWNESS);
      float floatValue5 = value1.b() ? ThreadLocalRandom.current().nextFloat(0.01F, 0.08F) : 0.01F;
      float floatValue6 = flag3 ? 0.0F : floatValue5;
      double doubleValue7 = -0.0;
      if (flag4) {
         floatValue6 = Math.min(floatValue6, flag3 ? 0.008F : 0.012F);
         doubleValue7 = Math.max(doubleValue7, flag3 ? -0.012 : -0.018);
      }

      return !value2.onGround && value2.fallDistance > floatValue6 && value2.velocityY < doubleValue7;
   }

   private dev.leg1tka.rotation.AttackTimingController.b a(int intValue1) {
      boolean flag2 = this.p ? this.q : minecraftClient.player.isOnGround();
      float floatValue3 = this.p ? this.v : minecraftClient.player.fallDistance;
      double doubleValue4 = this.p ? this.u : minecraftClient.player.getVelocity().y;
      Box box6 = minecraftClient.player.getBoundingBox();
      double doubleValue7 = Math.max(0.0, minecraftClient.player.getAttributeValue(EntityAttributes.GRAVITY));

      for (int intValue9 = 0; intValue9 < Math.max(0, intValue1); intValue9++) {
         if (flag2 && minecraftClient.options.jumpKey.isPressed()) {
            flag2 = false;
            doubleValue4 = 0.42;
         } else if (!flag2) {
            doubleValue4 = (doubleValue4 - doubleValue7) * 0.98;
            if (doubleValue4 < 0.0) {
               floatValue3 += (float)(-doubleValue4);
            }
         }

         box6 = box6.offset(0.0, doubleValue4, 0.0);
      }

      return new dev.leg1tka.rotation.AttackTimingController.b(flag2, floatValue3, doubleValue4, box6, minecraftClient.player.horizontalCollision);
   }

   private void a(PlayerMoveC2SPacket playerMoveC2SPacket1) {
      boolean flag2 = playerMoveC2SPacket1.isOnGround();
      if (playerMoveC2SPacket1.changesPosition()) {
         double doubleValue3 = playerMoveC2SPacket1.getX(this.r);
         double doubleValue5 = playerMoveC2SPacket1.getY(this.s);
         double doubleValue7 = playerMoveC2SPacket1.getZ(this.t);
         if (this.p) {
            this.u = doubleValue5 - this.s;
            if (flag2) {
               this.v = 0.0F;
            } else if (this.u < -1.0E-7) {
               this.v = this.v + (float)(-this.u);
            }
         } else {
            this.p = true;
            this.v = 0.0F;
            this.u = 0.0;
         }

         this.r = doubleValue3;
         this.s = doubleValue5;
         this.t = doubleValue7;
      } else if (flag2) {
         this.v = 0.0F;
      }

      this.q = flag2;
   }

   private void r() {
      this.p = false;
      this.q = true;
      this.r = this.s = this.t = this.u = 0.0;
      this.v = 0.0F;
   }

   private boolean s() {
      return !minecraftClient.player.isOnGround()
         && (MaceItem.shouldDealAdditionalDamage(minecraftClient.player) || minecraftClient.player.fallDistance >= 0.5F || minecraftClient.player.fallDistance > 0.0F && minecraftClient.player.getVelocity().y < -0.05);
   }

   private boolean b(int intValue1) {
      if (minecraftClient.player.getItemCooldownManager().isCoolingDown(minecraftClient.player.getMainHandStack())) {
         return false;
      } else if (this.s()) {
         return true;
      } else {
         int intValue2 = Math.max(0, intValue1);
         return (double)minecraftClient.player.fallDistance >= 0.5 ? this.n > 3 - intValue2 : minecraftClient.player.getAttackCooldownProgress(0.5F + (float)intValue2) >= 0.9F;
      }
   }

   private static boolean a(Screen screen0) {
      return screen0 instanceof HandledScreen;
   }

   private static boolean a(Box box0) {
      Mutable mutable1 = new Mutable();
      int intValue2 = MathHelper.floor(box0.minX);
      int intValue3 = MathHelper.floor(box0.maxX);
      int intValue4 = MathHelper.floor(box0.minY);
      int intValue5 = MathHelper.floor(box0.maxY);
      int intValue6 = MathHelper.floor(box0.minZ);
      int intValue7 = MathHelper.floor(box0.maxZ);

      for (int intValue8 = intValue2; intValue8 <= intValue3; intValue8++) {
         for (int intValue9 = intValue4; intValue9 <= intValue5; intValue9++) {
            for (int intValue10 = intValue6; intValue10 <= intValue7; intValue10++) {
               if (minecraftClient.world.getBlockState(mutable1.set(intValue8, intValue9, intValue10)).isOf(Blocks.COBWEB)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static class a {
      public LivingEntity a;
      public float b;
      public Box c;
      public boolean d;
      public boolean e;
      public boolean f;
      public boolean g;
      public boolean h;
      public boolean i;
      public boolean j;
      public boolean k;
      public String l = "Off";
      public String m = "Рандомный";

      public a() {
      }

      public boolean a() {
         return !"Off".equals(this.l);
      }

      public boolean a(String string1) {
         return this.l.equals(string1);
      }

      public boolean b() {
         return "Рандомный".equals(this.m);
      }
   }

   static record b(boolean onGround, float fallDistance, double velocityY, Box box, boolean horizontalCollision)  {




}
}
