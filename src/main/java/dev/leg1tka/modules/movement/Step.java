package dev.leg1tka.modules.movement;


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

import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventUpdate;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

public class Step extends Module {
   public static Step c = new Step();
   public ModeSetting d = new ModeSetting("Режим", "Vanilla", "Vanilla", "NCP", "Motion");
   public NumberSetting e = new NumberSetting("Высота", 1.0F, 1.0F, 10.0F, 0.5F);
   public BooleanSetting f = new BooleanSetting("Reverse", false);
   public NumberSetting g = new NumberSetting("Высота Reverse", 1.0F, 1.0F, 10.0F, 0.5F);
   private int h = 0;

   public Step() {
      super(
         "Step",
         "Моментально взбирается на блок",
         Category.MOVEMENT
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.h = 0;
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         if (this.f.getValue() && minecraftClient.player.isOnGround() && !minecraftClient.options.jumpKey.isPressed() && !minecraftClient.player.isSneaking() && !this.q()) {
            float floatValue2 = this.g.f();
            if (this.d(floatValue2)) {
               Vec3d vec3d3 = minecraftClient.player.getVelocity();
               minecraftClient.player.setVelocity(vec3d3.x, (double)(-floatValue2), vec3d3.z);
            }
         }

         if (minecraftClient.player.horizontalCollision && minecraftClient.player.isOnGround() && !minecraftClient.options.jumpKey.isPressed()) {
            float floatValue4 = this.p();
            if (floatValue4 > 0.6F && floatValue4 <= this.e.f()) {
               if (this.d.b("Vanilla")) {
                  this.a(floatValue4);
               }

               if (this.d.b("NCP")) {
                  this.b(floatValue4);
               }

               if (this.d.b("Motion")) {
                  this.c(floatValue4);
               }
            }
         } else {
            this.h = 0;
         }
      }
   }

   private void a(float floatValue1) {
      minecraftClient.player.setPosition(minecraftClient.player.getX(), minecraftClient.player.getY() + (double)floatValue1, minecraftClient.player.getZ());
   }

   private void b(float floatValue1) {
      double[] doubleValueArray2 = null;
      double doubleValue3 = minecraftClient.player.getY();
      if (floatValue1 <= 1.0F) {
         doubleValueArray2 = new double[]{0.42, 0.753};
      } else if (floatValue1 <= 1.5F) {
         doubleValueArray2 = new double[]{0.42, 0.75, 1.0, 1.16, 1.23, 1.2};
      } else if (floatValue1 <= 2.0F) {
         doubleValueArray2 = new double[]{0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43};
      } else if (floatValue1 <= 2.5F) {
         doubleValueArray2 = new double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907};
      } else if (floatValue1 <= 3.0F) {
         doubleValueArray2 = new double[]{0.42, 0.78, 0.63, 0.51, 0.9, 1.21, 1.45, 1.43, 1.78, 2.1, 2.4, 2.7};
      }

      if (doubleValueArray2 != null) {
         for (double doubleValue8 : doubleValueArray2) {
            minecraftClient.player.setPosition(minecraftClient.player.getX(), doubleValue3 + doubleValue8, minecraftClient.player.getZ());
         }
      }
   }

   private void c(float floatValue1) {
      Vec3d vec3d2 = minecraftClient.player.getVelocity();
      double doubleValue3 = 0.42;
      if (floatValue1 <= 1.0F) {
         doubleValue3 = 0.42;
      } else if (floatValue1 <= 1.5F) {
         doubleValue3 = 0.52;
      } else if (floatValue1 <= 2.0F) {
         doubleValue3 = 0.62;
      } else if (floatValue1 <= 2.5F) {
         doubleValue3 = 0.72;
      } else if (floatValue1 <= 3.0F) {
         doubleValue3 = 0.82;
      }

      minecraftClient.player.setVelocity(vec3d2.x, doubleValue3, vec3d2.z);
   }

   private float p() {
      Box box1 = minecraftClient.player.getBoundingBox();
      float floatValue2 = 0.0F;
      double doubleValue3 = 0.3;
      double doubleValue5 = Math.toRadians((double)minecraftClient.player.getYaw());
      double doubleValue7 = -Math.sin(doubleValue5) * doubleValue3;
      double doubleValue9 = Math.cos(doubleValue5) * doubleValue3;

      for (double doubleValue11 = 0.6; doubleValue11 <= (double)this.e.f() + 0.6; doubleValue11 += 0.1) {
         Box box13 = box1.offset(doubleValue7, doubleValue11, doubleValue9);

         for (BlockPos blockPos15 : BlockPos.iterate(
            (int)Math.floor(box13.minX),
            (int)Math.floor(box13.minY),
            (int)Math.floor(box13.minZ),
            (int)Math.floor(box13.maxX),
            (int)Math.floor(box13.maxY),
            (int)Math.floor(box13.maxZ)
         )) {
            BlockState blockState16 = minecraftClient.world.getBlockState(blockPos15);
            if (!blockState16.isAir()) {
               VoxelShape voxelShape17 = blockState16.getCollisionShape(minecraftClient.world, blockPos15);
               if (!voxelShape17.isEmpty()) {
                  for (Box box19 : voxelShape17.getBoundingBoxes()) {
                     Box box20 = box19.offset(blockPos15);
                     float floatValue21 = (float)(box20.maxY - minecraftClient.player.getY());
                     if (floatValue21 > 0.6F && floatValue21 <= this.e.f()) {
                        floatValue2 = Math.max(floatValue2, floatValue21);
                     }
                  }
               }
            }
         }
      }

      return floatValue2;
   }

   private boolean q() {
      Box box1 = minecraftClient.player.getBoundingBox().offset(0.0, 1.0, 0.0);

      for (BlockPos blockPos3 : BlockPos.iterate(
         (int)Math.floor(box1.minX),
         (int)Math.floor(box1.minY),
         (int)Math.floor(box1.minZ),
         (int)Math.floor(box1.maxX),
         (int)Math.floor(box1.maxY),
         (int)Math.floor(box1.maxZ)
      )) {
         if (!minecraftClient.world.getBlockState(blockPos3).isAir()) {
            return true;
         }
      }

      return false;
   }

   private boolean d(float floatValue1) {
      Box box2 = minecraftClient.player.getBoundingBox();

      for (double doubleValue3 = 0.1; doubleValue3 <= (double)floatValue1; doubleValue3 += 0.1) {
         Box box5 = box2.offset(0.0, -doubleValue3, 0.0);

         for (BlockPos blockPos7 : BlockPos.iterate(
            (int)Math.floor(box5.minX),
            (int)Math.floor(box5.minY),
            (int)Math.floor(box5.minZ),
            (int)Math.floor(box5.maxX),
            (int)Math.floor(box5.maxY),
            (int)Math.floor(box5.maxZ)
         )) {
            if (!minecraftClient.world.getBlockState(blockPos7).isAir()) {
               return false;
            }
         }
      }

      return true;
   }
}
