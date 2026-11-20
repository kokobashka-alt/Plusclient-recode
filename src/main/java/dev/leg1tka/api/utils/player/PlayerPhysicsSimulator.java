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

import it.unimi.dsi.fastutil.objects.Object2DoubleArrayMap;
import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.block.TrapdoorBlock;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;

public class PlayerPhysicsSimulator implements dev.leg1tka.api.ClientAccess {
   public final PlayerEntity c;
   public final SimulatedPlayerInput d;
   public Vec3d e;
   public Vec3d f;
   public Box g;
   public float h;
   public float i;
   public boolean j;
   public float k;
   public int l;
   public boolean m;
   public boolean n;
   public boolean o;
   public boolean p;
   public boolean q;
   public boolean r;
   public boolean s;
   public boolean t;
   private final Object2DoubleMap u;
   private final HashSet v;
   private int w = 0;
   private boolean x = false;
   private static double y;

   public PlayerPhysicsSimulator(
      PlayerEntity playerEntity1,
      SimulatedPlayerInput simulatedPlayerInput2,
      Vec3d vec3d3,
      Vec3d vec3d4,
      Box box5,
      float floatValue6,
      float floatValue7,
      boolean flag8,
      float floatValue9,
      int intValue10,
      boolean flag11,
      boolean flag12,
      boolean flag13,
      boolean flag14,
      boolean flag15,
      boolean flag16,
      boolean flag17,
      boolean flag18,
      Object2DoubleMap<TagKey<Fluid>> object2DoubleMap19,
      HashSet<TagKey<Fluid>> hashSet20
   ) {
      this.c = playerEntity1;
      this.d = simulatedPlayerInput2;
      this.e = vec3d3;
      this.f = vec3d4;
      this.g = box5;
      this.h = floatValue6;
      this.i = floatValue7;
      this.j = flag8;
      this.k = floatValue9;
      this.l = intValue10;
      this.m = flag11;
      this.n = flag12;
      this.o = flag13;
      this.p = flag14;
      this.q = flag15;
      this.r = flag16;
      this.s = flag17;
      this.t = flag18;
      this.u = object2DoubleMap19;
      this.v = hashSet20;
   }

   public static dev.leg1tka.api.utils.player.PlayerPhysicsSimulator a(int intValue0) {
      dev.leg1tka.api.utils.player.PlayerPhysicsSimulator value1 = a(SimulatedPlayerInput.a(minecraftClient.player.input.playerInput));

      for (int intValue2 = 0; intValue2 < intValue0; intValue2++) {
         value1.b();
      }

      return value1;
   }

   public static dev.leg1tka.api.utils.player.PlayerPhysicsSimulator a(PlayerEntity playerEntity0, int intValue1) {
      dev.leg1tka.api.utils.player.PlayerPhysicsSimulator value2 = a(playerEntity0, SimulatedPlayerInput.a(playerEntity0));

      for (int intValue3 = 0; intValue3 < intValue1; intValue3++) {
         value2.b();
      }

      return value2;
   }

   public static dev.leg1tka.api.utils.player.PlayerPhysicsSimulator a(SimulatedPlayerInput simulatedPlayerInput0) {
      ClientPlayerEntity clientPlayerEntity1 = minecraftClient.player;
      return new dev.leg1tka.api.utils.player.PlayerPhysicsSimulator(
         clientPlayerEntity1,
         simulatedPlayerInput0,
         clientPlayerEntity1.getPos(),
         clientPlayerEntity1.getVelocity(),
         clientPlayerEntity1.getBoundingBox(),
         clientPlayerEntity1.getYaw(),
         clientPlayerEntity1.getPitch(),
         clientPlayerEntity1.isSprinting(),
         clientPlayerEntity1.fallDistance,
         clientPlayerEntity1.jumpingCooldown,
         clientPlayerEntity1.jumping,
         clientPlayerEntity1.isGliding(),
         clientPlayerEntity1.isOnGround(),
         clientPlayerEntity1.horizontalCollision,
         clientPlayerEntity1.verticalCollision,
         clientPlayerEntity1.isTouchingWater(),
         clientPlayerEntity1.isSwimming(),
         clientPlayerEntity1.isSubmergedInWater(),
         new Object2DoubleArrayMap(clientPlayerEntity1.fluidHeight),
         new HashSet<>(clientPlayerEntity1.submergedFluidTag)
      );
   }

   public static dev.leg1tka.api.utils.player.PlayerPhysicsSimulator a(PlayerEntity playerEntity0, SimulatedPlayerInput simulatedPlayerInput1) {
      return new dev.leg1tka.api.utils.player.PlayerPhysicsSimulator(
         playerEntity0,
         simulatedPlayerInput1,
         playerEntity0.getPos(),
         playerEntity0.getPos().subtract(new Vec3d(playerEntity0.prevX, playerEntity0.prevY, playerEntity0.prevZ)),
         playerEntity0.getBoundingBox(),
         playerEntity0.getYaw(),
         playerEntity0.getPitch(),
         playerEntity0.isSprinting(),
         playerEntity0.fallDistance,
         playerEntity0.jumpingCooldown,
         playerEntity0.jumping,
         playerEntity0.isGliding(),
         playerEntity0.isOnGround(),
         playerEntity0.horizontalCollision,
         playerEntity0.verticalCollision,
         playerEntity0.isTouchingWater(),
         playerEntity0.isSwimming(),
         playerEntity0.isSubmergedInWater(),
         new Object2DoubleArrayMap(playerEntity0.fluidHeight),
         new HashSet<>(playerEntity0.submergedFluidTag)
      );
   }

   public Vec3d a() {
      return this.c.getPos();
   }

   public void b() {
      this.w++;
      this.x = false;
      if (!(this.e.y <= -70.0)) {
         this.d.a();
         this.t();
         this.v();
         this.u();
         if (this.l > 0) {
            this.l--;
         }

         this.m = this.d.d.jump();
         double doubleValue1 = this.f.x;
         double doubleValue3 = this.f.y;
         double doubleValue5 = this.f.z;
         if (Math.abs(this.f.x) < 0.003) {
            doubleValue1 = 0.0;
         }

         if (Math.abs(this.f.y) < 0.003) {
            doubleValue3 = 0.0;
         }

         if (Math.abs(this.f.z) < 0.003) {
            doubleValue5 = 0.0;
         }

         if (this.o) {
            this.n = false;
         }

         this.f = new Vec3d(doubleValue1, doubleValue3, doubleValue5);
         if (this.m) {
            double doubleValue7 = this.f() ? this.b(FluidTags.LAVA) : this.b(FluidTags.WATER);
            boolean flag9 = this.s() && doubleValue7 > 0.0;
            double doubleValue10 = this.r();
            if (!flag9 || this.o && !(doubleValue7 > doubleValue10)) {
               if (!this.f() || this.o && !(doubleValue7 > doubleValue10)) {
                  if ((this.o || flag9 && doubleValue7 <= doubleValue10) && this.l == 0) {
                     this.c();
                     this.l = 10;
                  }
               } else {
                  this.a(FluidTags.LAVA);
               }
            } else {
               this.a(FluidTags.WATER);
            }
         }

         float floatValue12 = this.d.c * 0.98F;
         float floatValue8 = this.d.b * 0.98F;
         float floatValue13 = 0.0F;
         if (this.a(StatusEffects.SLOW_FALLING) || this.a(StatusEffects.LEVITATION)) {
            this.k();
         }

         this.b(new Vec3d((double)floatValue12, (double)floatValue13, (double)floatValue8));
      }
   }

   private void b(Vec3d vec3d1) {
      if (this.s && !this.c.hasVehicle()) {
         double doubleValue2 = this.y().y;
         double doubleValue4 = doubleValue2 < -0.2 ? 0.085 : 0.06;
         BlockPos blockPos6 = new BlockPos(MathHelper.floor(this.e.x), MathHelper.floor(this.e.y + 1.0 - 0.1), MathHelper.floor(this.e.z));
         if (doubleValue2 <= 0.0 || this.d.d.jump() || !this.c.getWorld().getBlockState(blockPos6).getFluidState().isEmpty()) {
            this.f = this.f.add(0.0, (doubleValue2 - this.f.y) * doubleValue4, 0.0);
         }
      }

      double doubleValue19 = this.f.y;
      double doubleValue20 = 0.08;
      boolean flag21 = this.f.y <= 0.0;
      if (this.f.y <= 0.0 && this.a(StatusEffects.SLOW_FALLING)) {
         doubleValue20 = 0.01;
         this.k();
      }

      if (this.s() && this.c.shouldSwimInFluids()) {
         double doubleValue25 = this.e.y;
         float floatValue28 = this.m() ? 0.9F : 0.8F;
         float floatValue30 = 0.02F;
         float floatValue31 = (float)this.b(EntityAttributes.WATER_MOVEMENT_EFFICIENCY);
         if (!this.o) {
            floatValue31 *= 0.5F;
         }

         if (floatValue31 > 0.0F) {
            floatValue28 += (0.54600006F - floatValue28) * floatValue31 / 3.0F;
            floatValue30 += (this.j() - floatValue30) * floatValue31 / 3.0F;
         }

         if (this.a(StatusEffects.DOLPHINS_GRACE)) {
            floatValue28 = 0.96F;
         }

         this.a(floatValue30, vec3d1);
         this.c(this.f);
         Vec3d vec3d32 = this.f;
         if (this.p && this.d()) {
            vec3d32 = new Vec3d(vec3d32.x, 0.2, vec3d32.z);
         }

         this.f = vec3d32.multiply((double)floatValue28, 0.8, (double)floatValue28);
         Vec3d vec3d33 = this.c.applyFluidMovingSpeed(doubleValue20, flag21, this.f);
         this.f = vec3d33;
         if (this.p && this.a(vec3d33.x, vec3d33.y + 0.6 - this.e.y + doubleValue25, vec3d33.z)) {
            this.f = new Vec3d(vec3d33.x, 0.3, vec3d33.z);
         }
      } else if (this.f() && this.c.shouldSwimInFluids()) {
         double doubleValue24 = this.e.y;
         this.a(0.02F, vec3d1);
         this.c(this.f);
         if (this.b(FluidTags.LAVA) <= this.r()) {
            this.f = this.f.multiply(0.5, 0.8, 0.5);
            this.f = this.c.applyFluidMovingSpeed(doubleValue20, flag21, this.f);
         } else {
            this.f = this.f.multiply(0.5);
         }

         if (!this.c.hasNoGravity()) {
            this.f = this.f.add(0.0, -doubleValue20 / 4.0, 0.0);
         }

         if (this.p && this.a(this.f.x, this.f.y + 0.6 - this.e.y + doubleValue24, this.f.z)) {
            this.f = new Vec3d(this.f.x, 0.3, this.f.z);
         }
      } else if (this.n) {
         Vec3d vec3d7 = this.f;
         if (vec3d7.y > -0.5) {
            this.k = 1.0F;
         }

         Vec3d vec3d8 = this.a(this.i + (this.i - this.c.prevPitch), this.h + (this.h - this.c.prevYaw));
         float floatValue9 = this.i * (float) (Math.PI / 180.0);
         double doubleValue10 = Math.sqrt(vec3d8.x * vec3d8.x + vec3d8.z * vec3d8.z);
         double doubleValue12 = this.f.horizontalLength();
         double doubleValue14 = vec3d8.length();
         float floatValue16 = MathHelper.cos(floatValue9);
         floatValue16 = (float)((double)floatValue16 * (double)floatValue16 * Math.min(1.0, doubleValue14 / 0.4));
         vec3d7 = this.f.add(0.0, doubleValue20 * (-1.0 + (double)floatValue16 * 0.75), 0.0);
         if (vec3d7.y < 0.0 && doubleValue10 > 0.0) {
            double doubleValue17 = vec3d7.y * -0.1 * (double)floatValue16;
            vec3d7 = vec3d7.add(vec3d8.x * doubleValue17 / doubleValue10, doubleValue17, vec3d8.z * doubleValue17 / doubleValue10);
         }

         if (floatValue9 < 0.0F && doubleValue10 > 0.0) {
            double doubleValue35 = doubleValue12 * (double)(-MathHelper.sin(floatValue9)) * 0.04;
            vec3d7 = vec3d7.add(-vec3d8.x * doubleValue35 / doubleValue10, doubleValue35 * 3.2, -vec3d8.z * doubleValue35 / doubleValue10);
         }

         if (doubleValue10 > 0.0) {
            vec3d7 = vec3d7.add((vec3d8.x / doubleValue10 * doubleValue12 - vec3d7.x) * 0.1, 0.0, (vec3d8.z / doubleValue10 * doubleValue12 - vec3d7.z) * 0.1);
         }

         this.f = vec3d7.multiply(0.99, 0.98, 0.99);
         this.c(this.f);
      } else {
         BlockPos blockPos23 = this.q();
         float floatValue26 = this.c.getWorld().getBlockState(blockPos23).getBlock().getSlipperiness();
         float floatValue27 = this.o ? floatValue26 * 0.91F : 0.91F;
         Vec3d vec3d29 = this.a(vec3d1, floatValue26);
         double doubleValue11 = vec3d29.y;
         if (this.a(StatusEffects.LEVITATION)) {
            StatusEffectInstance statusEffectInstance13 = this.c(StatusEffects.LEVITATION);
            if (statusEffectInstance13 != null) {
               doubleValue11 += (0.05 * (double)(statusEffectInstance13.getAmplifier() + 1) - vec3d29.y) * 0.2;
            }
         } else if (this.c.getWorld().isClient() && !this.c.getWorld().isChunkLoaded(blockPos23)) {
            doubleValue11 = this.e.y > (double)this.c.getWorld().getBottomY() ? -0.1 : 0.0;
         } else if (!this.c.hasNoGravity()) {
            doubleValue11 -= doubleValue20;
         }

         if (this.c.hasNoDrag()) {
            this.f = new Vec3d(vec3d29.x, doubleValue11, vec3d29.z);
         } else {
            this.f = new Vec3d(vec3d29.x * (double)floatValue27, doubleValue11 * 0.98F, vec3d29.z * (double)floatValue27);
         }
      }

      if (this.c.getAbilities().flying && !this.c.hasVehicle()) {
         this.f = new Vec3d(this.f.x, doubleValue19 * 0.6, this.f.z);
         this.k();
      }
   }

   private Vec3d a(Vec3d vec3d1, float floatValue2) {
      this.a(this.a(floatValue2), vec3d1);
      this.f = this.e(this.f);
      this.c(this.f);
      Vec3d vec3d3 = this.f;
      BlockPos blockPos4 = this.a(this.e);
      BlockState blockState5 = this.a(blockPos4);
      if ((this.p || this.m) && (this.d() || blockState5 != null && blockState5.isOf(Blocks.POWDER_SNOW) && PowderSnowBlock.canWalkOnPowderSnow(this.c))) {
         vec3d3 = new Vec3d(vec3d3.x, 0.2, vec3d3.z);
      }

      return vec3d3;
   }

   private void a(float floatValue1, Vec3d vec3d2) {
      Vec3d vec3d3 = Entity.movementInputToVelocity(vec3d2, floatValue1, this.h);
      this.f = this.f.add(vec3d3);
   }

   private float a(float floatValue1) {
      return this.o ? this.j() * (0.21600002F / (floatValue1 * floatValue1 * floatValue1)) : this.i();
   }

   private float i() {
      float floatValue1 = 0.02F;
      return this.d.d.sprint() ? floatValue1 + 0.006F : floatValue1;
   }

   private float j() {
      return 0.1F;
   }

   private void c(Vec3d vec3d1) {
      Vec3d vec3d2 = this.f(vec3d1);
      Vec3d vec3d3 = this.d(vec3d2);
      if (vec3d3.lengthSquared() > 1.0E-7) {
         this.e = this.e.add(vec3d3);
         this.g = this.c.dimensions.getBoxAt(this.e);
      }

      boolean flag4 = !MathHelper.approximatelyEquals(vec3d1.x, vec3d3.x);
      boolean flag5 = !MathHelper.approximatelyEquals(vec3d1.z, vec3d3.z);
      this.p = flag4 || flag5;
      this.q = vec3d1.y != vec3d3.y;
      this.o = this.q && vec3d1.y < 0.0;
      if (!this.s()) {
         this.t();
      }

      if (this.o) {
         this.k();
      } else if (vec3d1.y < 0.0) {
         this.k = this.k - (float)vec3d1.y;
      }

      Vec3d vec3d6 = this.f;
      if (this.p || this.q) {
         this.f = new Vec3d(flag4 ? 0.0 : vec3d6.x, this.o ? 0.0 : vec3d6.y, flag5 ? 0.0 : vec3d6.z);
      }
   }

   private Vec3d d(Vec3d vec3d1) {
      Box box2 = new Box(-0.3, 0.0, -0.3, 0.3, 1.8, 0.3).offset(this.e);
      List list3 = Collections.emptyList();
      Vec3d vec3d4;
      if (vec3d1.lengthSquared() == 0.0) {
         vec3d4 = vec3d1;
      } else {
         vec3d4 = Entity.adjustMovementForCollisions(this.c, vec3d1, box2, this.c.getWorld(), list3);
      }

      boolean flag5 = vec3d1.x != vec3d4.x;
      boolean flag6 = vec3d1.y != vec3d4.y;
      boolean flag7 = vec3d1.z != vec3d4.z;
      boolean flag8 = this.o || flag6 && vec3d1.y < 0.0;
      if (this.c.getStepHeight() > 0.0F && flag8 && (flag5 || flag7)) {
         Vec3d vec3d9 = Entity.adjustMovementForCollisions(this.c, new Vec3d(vec3d1.x, (double)this.c.getStepHeight(), vec3d1.z), box2, this.c.getWorld(), list3);
         Vec3d vec3d10 = Entity.adjustMovementForCollisions(
            this.c, new Vec3d(0.0, (double)this.c.getStepHeight(), 0.0), box2.stretch(vec3d1.x, 0.0, vec3d1.z), this.c.getWorld(), list3
         );
         Vec3d vec3d11 = Entity.adjustMovementForCollisions(this.c, new Vec3d(vec3d1.x, 0.0, vec3d1.z), box2.offset(vec3d10), this.c.getWorld(), list3).add(vec3d10);
         if (vec3d10.y < (double)this.c.getStepHeight() && vec3d11.horizontalLengthSquared() > vec3d9.horizontalLengthSquared()) {
            vec3d9 = vec3d11;
         }

         if (vec3d9.horizontalLengthSquared() > vec3d4.horizontalLengthSquared()) {
            return vec3d9.add(Entity.adjustMovementForCollisions(this.c, new Vec3d(0.0, -vec3d9.y + vec3d1.y, 0.0), box2.offset(vec3d9), this.c.getWorld(), list3));
         }
      }

      return vec3d4;
   }

   private void k() {
      this.k = 0.0F;
   }

   public void c() {
      this.f = this.f.add(0.0, (double)this.n() - this.f.y, 0.0);
      if (this.m()) {
         float floatValue1 = (float)Math.toRadians((double)this.h);
         this.f = this.f.add((double)(-MathHelper.sin(floatValue1)) * 0.2, 0.0, (double)MathHelper.cos(floatValue1) * 0.2);
      }
   }

   private Vec3d e(Vec3d vec3d1) {
      if (!this.d()) {
         return vec3d1;
      } else {
         this.k();
         double doubleValue2 = MathHelper.clamp(vec3d1.x, -0.15F, 0.15F);
         double doubleValue4 = MathHelper.clamp(vec3d1.z, -0.15F, 0.15F);
         double doubleValue6 = Math.max(vec3d1.y, -0.15F);
         if (doubleValue6 < 0.0 && !this.a(this.a(this.e)).isOf(Blocks.SCAFFOLDING) && this.c.isHoldingOntoLadder()) {
            doubleValue6 = 0.0;
         }

         return new Vec3d(doubleValue2, doubleValue6, doubleValue4);
      }
   }

   public boolean d() {
      BlockPos blockPos1 = this.a(this.e);
      BlockState blockState2 = this.a(blockPos1);
      return blockState2.isIn(BlockTags.CLIMBABLE) ? true : blockState2.getBlock() instanceof TrapdoorBlock && this.a(blockPos1, blockState2);
   }

   private boolean a(BlockPos blockPos1, BlockState blockState2) {
      if (!(Boolean)blockState2.get(TrapdoorBlock.OPEN)) {
         return false;
      } else {
         BlockState blockState3 = this.c.getWorld().getBlockState(blockPos1.down());
         return blockState3.isOf(Blocks.LADDER) && ((Direction)blockState3.get(LadderBlock.FACING)).equals(blockState2.get(TrapdoorBlock.FACING));
      }
   }

   private Vec3d f(Vec3d vec3d1) {
      if (vec3d1.y <= 0.0 && this.l()) {
         double doubleValue2 = vec3d1.x;
         double doubleValue4 = vec3d1.z;

         double doubleValue6;
         for (doubleValue6 = 0.05; doubleValue2 != 0.0 && this.c.getWorld().isSpaceEmpty(this.c, this.g.offset(doubleValue2, -0.5, 0.0)); doubleValue2 += doubleValue2 > 0.0 ? -doubleValue6 : doubleValue6) {
            if (doubleValue2 < doubleValue6 && doubleValue2 >= -doubleValue6) {
               doubleValue2 = 0.0;
               break;
            }
         }

         while (doubleValue4 != 0.0 && this.c.getWorld().isSpaceEmpty(this.c, this.g.offset(0.0, -0.5, doubleValue4))) {
            if (doubleValue4 < doubleValue6 && doubleValue4 >= -doubleValue6) {
               doubleValue4 = 0.0;
               break;
            }

            doubleValue4 += doubleValue4 > 0.0 ? -doubleValue6 : doubleValue6;
         }

         while (doubleValue2 != 0.0 && doubleValue4 != 0.0 && this.c.getWorld().isSpaceEmpty(this.c, this.g.offset(doubleValue2, -0.5, doubleValue4))) {
            doubleValue2 = doubleValue2 < doubleValue6 && doubleValue2 >= -doubleValue6 ? 0.0 : (doubleValue2 > 0.0 ? doubleValue2 - doubleValue6 : doubleValue2 + doubleValue6);
            if (doubleValue4 < doubleValue6 && doubleValue4 >= -doubleValue6) {
               doubleValue4 = 0.0;
               break;
            }

            doubleValue4 += doubleValue4 > 0.0 ? -doubleValue6 : doubleValue6;
         }

         if (vec3d1.x != doubleValue2 || vec3d1.z != doubleValue4) {
            this.x = true;
         }

         if (this.e()) {
            vec3d1 = new Vec3d(doubleValue2, vec3d1.y, doubleValue4);
         }
      }

      return vec3d1;
   }

   protected boolean e() {
      return this.d.d.sneak() || this.d.a;
   }

   private boolean l() {
      return this.o || (double)this.k < 0.5 && !this.c.getWorld().isSpaceEmpty(this.c, this.g.offset(0.0, (double)this.k - 0.5, 0.0));
   }

   private boolean m() {
      return this.j;
   }

   private float n() {
      return 0.42F * this.p() + this.o();
   }

   private float o() {
      if (this.a(StatusEffects.JUMP_BOOST)) {
         StatusEffectInstance statusEffectInstance1 = this.c(StatusEffects.JUMP_BOOST);
         return 0.1F * (float)(statusEffectInstance1.getAmplifier() + 1);
      } else {
         return 0.0F;
      }
   }

   private float p() {
      float floatValue1 = 0.0F;
      Block block2 = this.a(this.a(this.e)).getBlock();
      if (block2 != null) {
         floatValue1 = block2.getJumpVelocityMultiplier();
      }

      float floatValue3 = 0.0F;
      Block block4 = this.a(this.q()).getBlock();
      if (block4 != null) {
         floatValue3 = block4.getJumpVelocityMultiplier();
      }

      return floatValue1 == 1.0F ? floatValue3 : floatValue1;
   }

   private boolean a(double doubleValue1, double doubleValue3, double doubleValue5) {
      return this.a(this.g.offset(doubleValue1, doubleValue3, doubleValue5));
   }

   private boolean a(Box box1) {
      return this.c.getWorld().isSpaceEmpty(this.c, box1) && !this.c.getWorld().containsFluid(box1);
   }

   private void a(TagKey<Fluid> tagKey1) {
      this.f = this.f.add(0.0, 0.04F, 0.0);
   }

   private BlockPos q() {
      return BlockPos.ofFloored(this.e.x, this.g.minY - 0.5000001, this.e.z);
   }

   private double r() {
      return (double)this.c.getStandingEyeHeight() < 0.4 ? 0.0 : 0.4;
   }

   private boolean s() {
      return this.r;
   }

   public boolean f() {
      return this.u.getDouble(FluidTags.LAVA) > 0.0;
   }

   private void t() {
      if (this.c.getVehicle() instanceof BoatEntity) {
         BoatEntity boatEntity1 = (BoatEntity)this.c.getVehicle();
         if (!boatEntity1.isSubmergedInWater()) {
            this.r = false;
            return;
         }
      }

      if (this.a(FluidTags.WATER, 0.014)) {
         this.k();
         this.r = true;
      } else {
         this.r = false;
      }
   }

   private void u() {
      if (this.s) {
         this.s = this.m() && this.s() && !this.c.hasVehicle();
      } else {
         this.s = this.m() && this.g() && !this.c.hasVehicle() && this.c.getWorld().getFluidState(this.a(this.e)).isIn(FluidTags.WATER);
      }
   }

   private void v() {
      this.t = this.v.contains(FluidTags.WATER);
      this.v.clear();
      double doubleValue1 = this.w() - 0.11111111F;
      if (this.c.getVehicle() instanceof BoatEntity boatEntity3
         && !boatEntity3.isSubmergedInWater()
         && boatEntity3.getBoundingBox().maxY >= doubleValue1
         && boatEntity3.getBoundingBox().minY <= doubleValue1) {
         return;
      }

      BlockPos blockPos7 = BlockPos.ofFloored(this.e.x, doubleValue1, this.e.z);
      FluidState fluidState8 = this.c.getWorld().getFluidState(blockPos7);
      double doubleValue5 = (double)((float)blockPos7.getY() + fluidState8.getHeight(this.c.getWorld(), blockPos7));
      if (doubleValue5 > doubleValue1) {
         this.v.addAll(fluidState8.streamTags().toList());
      }
   }

   private double w() {
      return this.e.y + (double)this.c.getStandingEyeHeight();
   }

   public boolean g() {
      return this.t && this.s();
   }

   private double b(TagKey<Fluid> tagKey1) {
      return this.u.getDouble(tagKey1);
   }

   private boolean a(TagKey<Fluid> tagKey1, double doubleValue2) {
      if (this.x()) {
         return false;
      } else {
         Box box4 = this.g.contract(0.001);
         int intValue5 = MathHelper.floor(box4.minX);
         int intValue6 = MathHelper.ceil(box4.maxX);
         int intValue7 = MathHelper.floor(box4.minY);
         int intValue8 = MathHelper.ceil(box4.maxY);
         int intValue9 = MathHelper.floor(box4.minZ);
         int intValue10 = MathHelper.ceil(box4.maxZ);
         double doubleValue11 = 0.0;
         boolean flag13 = true;
         boolean flag14 = false;
         Vec3d vec3d15 = Vec3d.ZERO;
         int intValue16 = 0;
         Mutable mutable17 = new Mutable();

         for (int intValue18 = intValue5; intValue18 < intValue6; intValue18++) {
            for (int intValue19 = intValue7; intValue19 < intValue8; intValue19++) {
               for (int intValue20 = intValue9; intValue20 < intValue10; intValue20++) {
                  mutable17.set(intValue18, intValue19, intValue20);
                  FluidState fluidState21 = this.c.getWorld().getFluidState(mutable17);
                  if (fluidState21.isIn(tagKey1)) {
                     double doubleValue22 = (double)((float)intValue19 + fluidState21.getHeight(this.c.getWorld(), mutable17));
                     if (doubleValue22 >= box4.minY) {
                        flag14 = true;
                        doubleValue11 = Math.max(doubleValue22 - box4.minY, doubleValue11);
                        if (flag13) {
                           Vec3d vec3d24 = fluidState21.getVelocity(this.c.getWorld(), mutable17);
                           if (doubleValue11 < 0.4) {
                              vec3d24 = vec3d24.multiply(doubleValue11);
                           }

                           vec3d15 = vec3d15.add(vec3d24);
                           intValue16++;
                        }
                     }
                  }
               }
            }
         }

         if (vec3d15.length() > 0.0) {
            if (intValue16 > 0) {
               vec3d15 = vec3d15.multiply(1.0 / (double)intValue16);
            }

            vec3d15 = vec3d15.multiply(doubleValue2);
            if (Math.abs(this.f.x) < 0.003 && Math.abs(this.f.z) < 0.003 && vec3d15.length() < 0.0045) {
               vec3d15 = vec3d15.normalize().multiply(0.0045);
            }

            this.f = this.f.add(vec3d15);
         }

         this.u.put(tagKey1, doubleValue11);
         return flag14;
      }
   }

   private boolean x() {
      Box box1 = this.g.expand(1.0);
      int intValue2 = MathHelper.floor(box1.minX);
      int intValue3 = MathHelper.ceil(box1.maxX);
      int intValue4 = MathHelper.floor(box1.minZ);
      int intValue5 = MathHelper.ceil(box1.maxZ);
      return !this.c.getWorld().isRegionLoaded(intValue2, intValue4, intValue3, intValue5);
   }

   private Vec3d y() {
      return this.a(this.i, this.h);
   }

   private Vec3d a(float floatValue1, float floatValue2) {
      float floatValue3 = (float)((double)floatValue1 * Math.PI / 180.0);
      float floatValue4 = (float)((double)(-floatValue2) * Math.PI / 180.0);
      float floatValue5 = MathHelper.cos(floatValue4);
      float floatValue6 = MathHelper.sin(floatValue4);
      float floatValue7 = MathHelper.cos(floatValue3);
      float floatValue8 = MathHelper.sin(floatValue3);
      return new Vec3d((double)(floatValue6 * floatValue7), (double)(-floatValue8), (double)(floatValue5 * floatValue7));
   }

   public boolean a(RegistryEntry<StatusEffect> registryEntry1) {
      StatusEffectInstance statusEffectInstance2 = this.c.getStatusEffect(registryEntry1);
      return statusEffectInstance2 != null && statusEffectInstance2.getDuration() >= this.w;
   }

   private StatusEffectInstance c(RegistryEntry<StatusEffect> registryEntry1) {
      StatusEffectInstance statusEffectInstance2 = this.c.getStatusEffect(registryEntry1);
      return statusEffectInstance2 != null && statusEffectInstance2.getDuration() >= this.w ? statusEffectInstance2 : null;
   }

   public double b(RegistryEntry<EntityAttribute> registryEntry1) {
      return this.c.getAttributes().getValue(registryEntry1);
   }

   public dev.leg1tka.api.utils.player.PlayerPhysicsSimulator h() {
      return new dev.leg1tka.api.utils.player.PlayerPhysicsSimulator(
         this.c,
         this.d,
         this.e,
         this.f,
         this.g,
         this.h,
         this.i,
         this.j,
         this.k,
         this.l,
         this.m,
         this.n,
         this.o,
         this.p,
         this.q,
         this.r,
         this.s,
         this.t,
         new Object2DoubleArrayMap(this.u),
         new HashSet<>(this.v)
      );
   }

   public BlockPos a(Vec3d vec3d1) {
      return new BlockPos(MathHelper.floor(vec3d1.x), MathHelper.floor(vec3d1.y), MathHelper.floor(vec3d1.z));
   }

   public BlockState a(BlockPos blockPos1) {
      return this.c.getWorld().getBlockState(blockPos1);
   }

   public static class SimulatedPlayerInput extends Input {
      public boolean a = false;
      public float b;
      public float c;
      public PlayerInput d;
      public static double e;

      public SimulatedPlayerInput(PlayerInput playerInput1) {
         this.d = playerInput1;
      }

      public void a() {
         if (this.d.forward() != this.d.backward()) {
            this.b = this.d.forward() ? 1.0F : -1.0F;
         } else {
            this.b = 0.0F;
         }

         if (this.d.left() == this.d.right()) {
            this.c = 0.0F;
         } else {
            this.c = this.d.left() ? 1.0F : -1.0F;
         }

         if (this.d.sneak()) {
            this.c *= 0.3F;
            this.b *= 0.3F;
         }
      }

      public String toString() {
         return "SimulatedPlayerInput(forwards={"
            + this.d.forward()
            + "}, backwards={"
            + this.d.backward()
            + "}, left={"
            + this.d.left()
            + "}, right={"
            + this.d.right()
            + "}, jumping={"
            + this.d.jump()
            + "}, sprinting="
            + this.d.sprint()
            + ", slowDown="
            + this.d.sneak()
            + ")";
      }

      public static SimulatedPlayerInput a(PlayerInput playerInput0) {
         return new SimulatedPlayerInput(playerInput0);
      }

      public static SimulatedPlayerInput a(PlayerEntity playerEntity0) {
         Vec3d vec3d1 = playerEntity0.getPos().subtract(new Vec3d(playerEntity0.prevX, playerEntity0.prevY, playerEntity0.prevZ));
         double doubleValue2 = vec3d1.horizontalLengthSquared();
         PlayerInput playerInput4 = new PlayerInput(false, false, false, false, !playerEntity0.isOnGround(), playerEntity0.isSneaking(), doubleValue2 >= 0.014641);
         if (doubleValue2 > 0.0025000000000000005) {
            double doubleValue5 = dev.leg1tka.api.utils.player.MovementGeometry.a(vec3d1, playerEntity0.getYaw());
            double doubleValue7 = MathHelper.wrapDegrees(doubleValue5);
            playerInput4 = dev.leg1tka.api.utils.player.MovementGeometry.a(playerInput4, doubleValue7);
         }

         return new SimulatedPlayerInput(playerInput4);
      }
   }
}
