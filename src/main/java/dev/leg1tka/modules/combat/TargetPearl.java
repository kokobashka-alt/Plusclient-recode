package dev.leg1tka.modules.combat;


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

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventBinding;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.LookAndOnGround;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class TargetPearl extends Module {
   public static final TargetPearl c = new TargetPearl();
   public final ModeSetting d = new ModeSetting(
      "Тип",
      "Автоматический",
      "По бинду",
      "Автоматический"
   );
   public final dev.leg1tka.settings.KeybindSetting e = new dev.leg1tka.settings.KeybindSetting("Бинд", -1).visibleWhen(() -> this.d.b("По бинду"));
   public final ModeSetting f = new ModeSetting("Наводить", "ShardAC", "Fast", "ShardAC");
   public final NumberSetting g = new NumberSetting("Мин. дистанция", 5.0F, 1.0F, 30.0F, 1.0F);
   public final BooleanSetting h = new BooleanSetting("Обходить Grim", true);
   public final BooleanSetting i = new BooleanSetting("Только за противником", false);
   public final BooleanSetting j = new BooleanSetting("Игнорировать друзей", true);
   private static int k;
   private final dev.leg1tka.api.utils.time.Stopwatch l = new dev.leg1tka.api.utils.time.Stopwatch();
   private final dev.leg1tka.api.utils.math.VectorInterpolation.c m = new TargetPearl.a();
   EnderPearlEntity n;
   private Vec3d o;
   private int p = -1;
   private int q = -1;
   private long r;
   private long s;
   private PearlState t = PearlState.IDLE;
   private Vec3d u;
   private Vec3d v = Vec3d.ZERO;
   private dev.leg1tka.api.utils.math.VectorInterpolation.b w;
   private float x;
   private float y;
   private int z;
   private int A;
   private int B;

   public TargetPearl() {
      super(
         "TargetPearl",
         "Автоматически бросает жемчуг в цель",
         Category.COMBAT
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h, this.i, this.j});
   }

   public boolean p() {
      return this.isEnabled() && (this.t == PearlState.AIMING || this.t == PearlState.THROWING);
   }

   @EventLink
   public void onBinding(EventBinding eventBinding1) {
      if (minecraftClient.player != null && minecraftClient.world != null && minecraftClient.currentScreen == null) {
         if (this.d.b("По бинду") && eventBinding1.getKey() == this.e.getKey()) {
            if (this.t == PearlState.IDLE && this.q()) {
               this.r();
            }
         }
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         Vec3d vec3d2 = minecraftClient.player.getPos();
         this.v = this.u == null ? Vec3d.ZERO : vec3d2.subtract(this.u);
         this.u = vec3d2;
         if (this.p != -1 && (!(minecraftClient.world.getEntityById(this.p) instanceof EnderPearlEntity enderPearlEntity4) || !enderPearlEntity4.isAlive())) {
            this.p = -1;
         }

         switch (this.t) {
            case IDLE:
               if (this.d.b("Автоматический") && this.q()) {
                  this.r();
               }
               break;
            case AIMING:
               this.s();
               break;
            case THROWING:
               this.t();
         }
      } else {
         this.e(false);
         this.u = null;
      }
   }

   @Override
   public void onDisable() {
      this.e(false);
      this.p = -1;
      this.s = 0L;
      this.l.reset();
      super.onDisable();
   }

   private boolean q() {
      if (minecraftClient.player == null) {
         return false;
      } else {
         return System.currentTimeMillis() < this.s
            ? false
            : !minecraftClient.player.getItemCooldownManager().isCoolingDown(new ItemStack(Items.ENDER_PEARL)) && this.l.hasElapsed(1000L) && this.B();
      }
   }

   private void r() {
      this.n = this.A();
      if (this.n != null) {
         dev.leg1tka.api.utils.math.VectorInterpolation.a value1 = dev.leg1tka.api.utils.math.VectorInterpolation.a(this.m, this.n.getPos(), this.n.getVelocity(), false);
         if (value1 != null && this.a(value1.land())) {
            this.o = value1.land();
            this.w = dev.leg1tka.api.utils.math.VectorInterpolation.a(this.m, this.x(), this.y(), this.o);
            if (this.w == null || this.w.err() > this.z()) {
               this.w();
            } else if (this.f.b("Fast") && !this.h.getValue()) {
               this.u();
            } else {
               this.x = minecraftClient.player.getYaw();
               this.y = minecraftClient.player.getPitch();
               float floatValue2 = (float)Math.hypot((double)MathHelper.wrapDegrees(this.w.yaw() - this.x), (double)(this.w.pitch() - this.y));
               this.A = this.f.b("Fast") ? 1 : (floatValue2 <= 30.0F ? 2 : (floatValue2 <= 90.0F ? 3 : 4));
               this.z = 0;
               this.B = 0;
               this.t = PearlState.AIMING;
               this.s();
            }
         } else {
            this.w();
         }
      }
   }

   private void s() {
      if (this.B() && this.o != null) {
         this.w = dev.leg1tka.api.utils.math.VectorInterpolation.a(this.m, this.x(), this.y(), this.o, this.w.yaw(), this.w.pitch(), 0.25);
         this.z++;
         float floatValue1;
         float floatValue2;
         if (this.z >= this.A) {
            dev.leg1tka.api.utils.math.VectorInterpolation.b value3 = dev.leg1tka.api.utils.math.VectorInterpolation.a(this.m, this.x(), this.y(), this.o, this.w, minecraftClient.player.getYaw(), minecraftClient.player.getPitch(), dev.leg1tka.rotation.RotationContext.a());
            floatValue1 = value3.yaw();
            floatValue2 = value3.pitch();
            this.t = PearlState.THROWING;
         } else {
            double doubleValue7 = (double)this.z / (double)this.A;
            double doubleValue5 = doubleValue7 * doubleValue7 * doubleValue7 * (10.0 - 15.0 * doubleValue7 + 6.0 * doubleValue7 * doubleValue7);
            floatValue1 = this.x + (float)((double)MathHelper.wrapDegrees(this.w.yaw() - this.x) * doubleValue5);
            floatValue2 = this.y + (float)((double)(this.w.pitch() - this.y) * doubleValue5);
         }

         this.a(floatValue1, floatValue2);
      } else {
         this.e(false);
      }
   }

   private void t() {
      if (this.B() && this.o != null) {
         float floatValue1 = minecraftClient.player.getYaw();
         float floatValue2 = minecraftClient.player.getPitch();
         dev.leg1tka.api.utils.math.VectorInterpolation.b value3 = dev.leg1tka.api.utils.math.VectorInterpolation.a(this.m, this.x(), this.y(), this.o, floatValue1, floatValue2, true);
         if (value3.err() <= this.z()) {
            this.a(floatValue1, floatValue2);
            this.v();
            this.e(true);
         } else if (++this.B > 2) {
            this.w();
            this.e(false);
         } else {
            this.x = floatValue1;
            this.y = floatValue2;
            this.A = 1;
            this.z = 0;
            this.t = PearlState.AIMING;
            this.s();
         }
      } else {
         this.e(false);
      }
   }

   private void u() {
      float floatValue1 = minecraftClient.player.getYaw();
      float floatValue2 = minecraftClient.player.getPitch();

      try {
         minecraftClient.player.setYaw(this.w.yaw());
         minecraftClient.player.setPitch(this.w.pitch());
         minecraftClient.player.networkHandler.sendPacket(new LookAndOnGround(this.w.yaw(), this.w.pitch(), minecraftClient.player.isOnGround(), minecraftClient.player.horizontalCollision));
         this.v();
         this.e(true);
      } finally {
         minecraftClient.player.setYaw(floatValue1);
         minecraftClient.player.setPitch(floatValue2);
      }
   }

   private void a(float floatValue1, float floatValue2) {
      float floatValue3 = this.h.getValue() ? 60.0F : 180.0F;
      dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(floatValue1, floatValue2), 360.0F, 360.0F, floatValue3, floatValue3, 2, 2, false);
   }

   private void v() {
      if (minecraftClient.player.getOffHandStack().isOf(Items.ENDER_PEARL)) {
         minecraftClient.interactionManager.interactItem(minecraftClient.player, Hand.OFF_HAND);
      } else {
         dev.leg1tka.api.utils.player.InventorySearch.c(Items.ENDER_PEARL);
      }
   }

   private void e(boolean flag1) {
      if (flag1) {
         this.l.reset();
         this.s = System.currentTimeMillis() + 2500L;
         if (this.n != null) {
            this.p = this.n.getId();
         }
      }

      if (!flag1 && this.t != PearlState.IDLE && dev.leg1tka.rotation.RotationStorage.c != null && dev.leg1tka.rotation.RotationStorage.c.h() == 2) {
         dev.leg1tka.rotation.RotationStorage.c.a(dev.leg1tka.rotation.RotationStorage.RotationState.RESET);
      }

      this.n = null;
      this.o = null;
      this.w = null;
      this.t = PearlState.IDLE;
   }

   private void w() {
      if (this.n != null) {
         this.q = this.n.getId();
         this.r = System.currentTimeMillis() + 300L;
      }

      this.n = null;
      this.o = null;
   }

   private Vec3d x() {
      return new Vec3d(minecraftClient.player.getX(), minecraftClient.player.getEyeY() - 0.1, minecraftClient.player.getZ());
   }

   private Vec3d y() {
      return new Vec3d(this.v.x, minecraftClient.player.isOnGround() ? 0.0 : this.v.y, this.v.z);
   }

   private double z() {
      return 0.75 + (this.o == null ? 0.0 : minecraftClient.player.getPos().distanceTo(this.o) * 0.01);
   }

   private EnderPearlEntity A() {
      Box box1 = minecraftClient.player.getBoundingBox().expand(256.0);
      LivingEntity livingEntity2 = AttackAura.c != null ? AttackAura.c.E() : null;
      long longValue3 = System.currentTimeMillis();
      return minecraftClient.world
         .getOtherEntities(
            minecraftClient.player,
            box1,
            entity4 -> {
               if (entity4 instanceof EnderPearlEntity enderPearlEntity5
                  && enderPearlEntity5.isAlive()
                  && enderPearlEntity5.getOwner() != minecraftClient.player
                  && enderPearlEntity5.getId() != this.p
                  && (enderPearlEntity5.getId() != this.q || longValue3 >= this.r)
                  && !this.a(enderPearlEntity5.getOwner())
                  && (!this.i.getValue() || livingEntity2 != null && enderPearlEntity5.getOwner() == livingEntity2)) {
                  return true;
               }

               return false;
            }
         )
         .stream()
         .map(entity0 -> (EnderPearlEntity)entity0)
         .filter(enderPearlEntity1x -> this.a(enderPearlEntity1x) <= 256.0)
         .min(Comparator.comparingDouble(this::a))
         .orElse(null);
   }

   private boolean a(Entity entity1) {
      return this.j.getValue() && entity1 instanceof PlayerEntity playerEntity2
         ? AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.friendStorage != null && AspectDLC.INSTANCE.friendStorage.c(playerEntity2.getName().getString())
         : false;
   }

   private double a(EnderPearlEntity enderPearlEntity1) {
      double doubleValue2 = enderPearlEntity1.getX() - minecraftClient.player.getX();
      double doubleValue4 = enderPearlEntity1.getZ() - minecraftClient.player.getZ();
      return Math.sqrt(doubleValue2 * doubleValue2 + doubleValue4 * doubleValue4);
   }

   private boolean a(Vec3d vec3d1) {
      double doubleValue2 = minecraftClient.player.getPos().distanceTo(vec3d1);
      return doubleValue2 >= (double)this.g.f() && doubleValue2 <= 256.0;
   }

   private boolean B() {
      return minecraftClient.player.getMainHandStack().isOf(Items.ENDER_PEARL)
         || minecraftClient.player.getOffHandStack().isOf(Items.ENDER_PEARL)
         || dev.leg1tka.api.utils.player.InventorySearch.b(Items.ENDER_PEARL, 0, 8) != -1
         || dev.leg1tka.api.utils.player.InventorySearch.b(Items.ENDER_PEARL, 9, 45) != -1;
   }

   final class a implements dev.leg1tka.api.utils.math.VectorInterpolation.c {
      a() {
      }

      @Override
      public boolean a(Vec3d vec3d1, Vec3d vec3d2) {
         return dev.leg1tka.api.ClientAccess.minecraftClient.world.raycast(new RaycastContext(vec3d1, vec3d2, ShapeType.COLLIDER, FluidHandling.NONE, dev.leg1tka.api.ClientAccess.minecraftClient.player)).getType()
            == Type.BLOCK;
      }

      @Override
      public boolean a(Vec3d vec3d1) {
         return dev.leg1tka.api.ClientAccess.minecraftClient.world.getFluidState(BlockPos.ofFloored(vec3d1)).isIn(FluidTags.WATER);
      }

      @Override
      public boolean b(Vec3d vec3d1, Vec3d vec3d2) {
         Box box3 = new Box(vec3d1, vec3d2).expand(0.3);

         for (Entity entity5 : dev.leg1tka.api.ClientAccess.minecraftClient
            .world
            .getOtherEntities(
               dev.leg1tka.api.ClientAccess.minecraftClient.player,
               box3,
               entity1x -> entity1x.isAlive() && !entity1x.isSpectator() && entity1x.canHit() && entity1x != TargetPearl.this.n && !(entity1x instanceof EnderPearlEntity)
            )) {
            if (entity5.getBoundingBox().expand(0.3).raycast(vec3d1, vec3d2).isPresent()) {
               return true;
            }
         }

         return false;
      }

      @Override
      public double a() {
         return (double)dev.leg1tka.api.ClientAccess.minecraftClient.world.getBottomY();
      }
   }

   public static enum PearlState {
      IDLE,
      AIMING,
      THROWING;

      private PearlState() {
      }
   }
}
