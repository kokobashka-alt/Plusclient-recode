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

import dev.leg1tka.mixin.IEntity;
import lombok.Generated;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Items;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class PlayerEnvironment implements dev.leg1tka.api.ClientAccess {
   private static int c;
   private static int d;
   private static double e;
   private static int f = Integer.MIN_VALUE;
   private static int g = Integer.MIN_VALUE;

   public static float a() {
      if (minecraftClient.player.getMainHandStack().getItem() == Items.AIR) {
         return 0.9F;
      } else {
         return !(minecraftClient.player.getMainHandStack().getItem() instanceof AxeItem) && !(minecraftClient.player.getMainHandStack().getItem() instanceof ShovelItem) ? 0.93F : 0.95F;
      }
   }

   public static boolean b() {
      BlockPos blockPos0 = BlockPos.ofFloored(minecraftClient.player.getPos().add(0.0, -0.4F, 0.0));
      return minecraftClient.world.getBlockState(blockPos0).isOf(Blocks.WATER)
         ? true
         : a(0.0, 3.0, 0.0) == Blocks.AIR && a(0.0, 2.0, 0.0) == Blocks.AIR && a(0.0, 1.0, 0.0) == Blocks.AIR
            || minecraftClient.player.fallDistance < (a(0.0, 2.0, 0.0) != Blocks.AIR ? 0.08F : 0.6F)
            || minecraftClient.player.fallDistance > 1.2F;
   }

   public static boolean a(LivingEntity livingEntity0) {
      return a(livingEntity0, 0.0F);
   }

   public static boolean a(LivingEntity livingEntity0, float floatValue1) {
      k();
      boolean flag2 = ModuleManager.ah.isEnabled();
      boolean flag3 = minecraftClient.player.hasStatusEffect(StatusEffects.SLOW_FALLING);
      boolean flag4 = f();
      boolean flag5 = ModuleManager.i.B.getValue();
      if (flag2 && flag4) {
         return true;
      } else if (flag2 && flag3) {
         return minecraftClient.player.getVelocity().y < 0.0 && minecraftClient.player.fallDistance >= floatValue1;
      } else if (m()) {
         return n();
      } else {
         boolean flag6 = c();
         boolean flag7 = !minecraftClient.player.isOnGround() && (minecraftClient.player.getVelocity().y < 0.0 || flag6) && (minecraftClient.player.fallDistance >= floatValue1 || flag6);
         if (g()) {
            return i();
         } else if (h()) {
            return d();
         } else if (p()) {
            return true;
         } else {
            return !flag5 ? flag7 : minecraftClient.player.isOnGround() || flag7;
         }
      }
   }

   public static boolean b(LivingEntity livingEntity0) {
      return b(livingEntity0, 0.0F);
   }

   public static boolean b(LivingEntity livingEntity0, float floatValue1) {
      k();
      boolean flag2 = ModuleManager.ah.isEnabled();
      boolean flag3 = minecraftClient.player.hasStatusEffect(StatusEffects.SLOW_FALLING);
      boolean flag4 = f();
      if (flag2 && flag4) {
         return true;
      } else if (flag2 && flag3) {
         return minecraftClient.player.getVelocity().y < 0.0 && minecraftClient.player.fallDistance >= floatValue1;
      } else if (m()) {
         return n();
      } else {
         boolean flag5 = c();
         boolean flag6 = !minecraftClient.player.isOnGround() && (minecraftClient.player.getVelocity().y < 0.0 || flag5) && (minecraftClient.player.fallDistance >= floatValue1 || flag5);
         if (g()) {
            return i();
         } else if (h()) {
            return d();
         } else {
            return p() ? true : flag6;
         }
      }
   }

   public static boolean c() {
      AirStuck airStuck0 = AirStuck.c;
      return airStuck0 != null && airStuck0.q();
   }

   private static boolean g() {
      return ModuleManager.ab.isEnabled() && minecraftClient.options != null && minecraftClient.options.jumpKey.isPressed() && j();
   }

   private static boolean h() {
      return ModuleManager.ab.isEnabled() && minecraftClient.options != null && minecraftClient.options.jumpKey.isPressed();
   }

   private static boolean i() {
      return minecraftClient.player != null
         && !minecraftClient.player.isOnGround()
         && minecraftClient.player.getVelocity().y <= 0.01
         && !minecraftClient.player.isTouchingWater()
         && !minecraftClient.player.isSubmergedInWater()
         && !minecraftClient.player.isInLava()
         && !minecraftClient.player.isClimbing()
         && !minecraftClient.player.hasVehicle()
         && !minecraftClient.player.getAbilities().flying;
   }

   public static boolean d() {
      return minecraftClient.player != null
         && minecraftClient.world != null
         && ModuleManager.ab.isEnabled()
         && minecraftClient.options != null
         && minecraftClient.options.jumpKey.isPressed()
         && !minecraftClient.player.isOnGround()
         && minecraftClient.player.getVelocity().y < 0.0
         && !minecraftClient.player.isTouchingWater()
         && !minecraftClient.player.isSubmergedInWater()
         && !minecraftClient.player.isInLava()
         && !minecraftClient.player.isClimbing()
         && !minecraftClient.player.hasVehicle()
         && !minecraftClient.player.getAbilities().flying
         && !minecraftClient.player.hasStatusEffect(StatusEffects.LEVITATION)
         && !minecraftClient.player.hasStatusEffect(StatusEffects.SLOW_FALLING)
         && !minecraftClient.player.hasStatusEffect(StatusEffects.BLINDNESS)
         && !minecraftClient.player.isGliding()
         && !f();
   }

   private static boolean j() {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         Box box0 = minecraftClient.player.getBoundingBox().contract(0.03);
         Box box1 = new Box(box0.minX, box0.maxY, box0.minZ, box0.maxX, box0.maxY + 0.32, box0.maxZ);

         for (BlockPos blockPos3 : BlockPos.iterate(
            MathHelper.floor(box1.minX),
            MathHelper.floor(box1.minY),
            MathHelper.floor(box1.minZ),
            MathHelper.floor(box1.maxX),
            MathHelper.floor(box1.maxY),
            MathHelper.floor(box1.maxZ)
         )) {
            BlockState blockState4 = minecraftClient.world.getBlockState(blockPos3);
            if (!blockState4.isAir() && !blockState4.getCollisionShape(minecraftClient.world, blockPos3).isEmpty()) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean e() {
      return f() || minecraftClient.player.hasStatusEffect(StatusEffects.SLOW_FALLING);
   }

   private static void k() {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         boolean flag0 = o();
         if (flag0) {
            f = minecraftClient.player.age;
            if (l()) {
               g = minecraftClient.player.age;
            }
         }
      } else {
         f = Integer.MIN_VALUE;
         g = Integer.MIN_VALUE;
      }
   }

   private static boolean l() {
      return minecraftClient.player != null && minecraftClient.options != null
         ? minecraftClient.options.jumpKey.isPressed() && !minecraftClient.player.isOnGround() && !minecraftClient.player.isSubmergedInWater() && minecraftClient.player.getVelocity().y > 0.05
         : false;
   }

   private static boolean m() {
      return minecraftClient.player != null && minecraftClient.options != null && minecraftClient.options.jumpKey.isPressed() ? minecraftClient.player.age - g <= 8 && minecraftClient.player.age - f <= 10 : false;
   }

   private static boolean n() {
      return minecraftClient.player != null
         && !minecraftClient.player.isOnGround()
         && !minecraftClient.player.isTouchingWater()
         && !minecraftClient.player.isSubmergedInWater()
         && minecraftClient.player.fallDistance > 0.0F
         && minecraftClient.player.getVelocity().y < 0.0;
   }

   private static boolean o() {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         BlockPos blockPos0 = BlockPos.ofFloored(minecraftClient.player.getPos().add(0.0, -0.4F, 0.0));
         return minecraftClient.player.isTouchingWater() || minecraftClient.player.isSubmergedInWater() || minecraftClient.world.getBlockState(blockPos0).isOf(Blocks.WATER);
      } else {
         return false;
      }
   }

   private static boolean p() {
      double doubleValue0 = (double)minecraftClient.player.getStepHeight();
      Vec3d vec3d2 = new Vec3d(0.0, doubleValue0, 0.0);
      Vec3d vec3d3 = ((IEntity)minecraftClient.player).invokeAdjustMovementForCollisions(vec3d2);
      boolean flag4 = f();
      BlockPos blockPos5 = BlockPos.ofFloored(minecraftClient.player.getPos().add(0.0, (double)(minecraftClient.player.getHeight() / 2.0F), 0.0));
      return minecraftClient.player.isInLava()
         || minecraftClient.player.isClimbing()
         || minecraftClient.world.getBlockState(blockPos5).isOf(Blocks.WATER)
         || minecraftClient.player.hasStatusEffect(StatusEffects.LEVITATION)
         || minecraftClient.player.hasStatusEffect(StatusEffects.SLOW_FALLING)
         || minecraftClient.player.hasStatusEffect(StatusEffects.BLINDNESS)
         || flag4
         || minecraftClient.player.isGliding()
         || minecraftClient.player.hasVehicle()
         || minecraftClient.player.getAbilities().flying
         || minecraftClient.player.isTouchingWater()
         || vec3d3.y < (double)minecraftClient.player.getStepHeight() - 0.5 && minecraftClient.player.isOnGround();
   }

   public static boolean f() {
      Box box0 = minecraftClient.player.getBoundingBox();

      for (BlockPos blockPos2 : BlockPos.iterate(
         MathHelper.floor(box0.minX),
         MathHelper.floor(box0.minY),
         MathHelper.floor(box0.minZ),
         MathHelper.floor(box0.maxX),
         MathHelper.floor(box0.maxY),
         MathHelper.floor(box0.maxZ)
      )) {
         if (minecraftClient.world.getBlockState(blockPos2).isOf(Blocks.COBWEB)) {
            return true;
         }
      }

      return false;
   }

   public static Block a(double doubleValue0, double doubleValue2, double doubleValue4) {
      return minecraftClient.world.getBlockState(minecraftClient.player.getBlockPos().add((int)doubleValue0, (int)doubleValue2, (int)doubleValue4)).getBlock();
   }

   public static boolean a(float floatValue0) {
      Vec3d vec3d1 = minecraftClient.player.getRotationVector();
      double doubleValue2 = minecraftClient.player.getVelocity().x;
      double doubleValue4 = minecraftClient.player.getVelocity().y;
      double doubleValue6 = minecraftClient.player.getVelocity().z;
      float floatValue8 = MathHelper.cos(minecraftClient.player.getPitch() * (float) (Math.PI / 180.0));
      floatValue8 = (float)((double)(floatValue8 * floatValue8) * Math.min(vec3d1.length() / 0.4, 1.0));
      Vec3d vec3d9 = new Vec3d(doubleValue2, doubleValue4, doubleValue6).add(0.0, 0.08 * (-1.0 + (double)floatValue8 * 0.75), 0.0);
      doubleValue4 = vec3d9.y * 0.98F;
      return doubleValue4 < (double)floatValue0;
   }

   @Generated
   private PlayerEnvironment() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
