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

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventKeyboardInput;
import dev.leg1tka.api.events.implement.EventUpdate;
import lombok.Generated;
import net.minecraft.util.math.MathHelper;

public class RotationStorage implements dev.leg1tka.api.ClientAccess {
   public static dev.leg1tka.rotation.RotationStorage c;
   private dev.leg1tka.rotation.RotationStorage.RotationState d = dev.leg1tka.rotation.RotationStorage.RotationState.IDLE;
   private float e;
   private float f;
   private float g;
   private float h;
   private int i;
   private int j;
   private int k;
   private dev.leg1tka.rotation.Rotation l;

   public RotationStorage() {
      c = this;
      EventInvoker.register(this);
   }

   public static double a(float floatValue0, float floatValue1, float floatValue2) {
      if (floatValue1 < 0.0F) {
         floatValue0 += 180.0F;
      }

      float floatValue3 = 1.0F;
      if (floatValue1 < 0.0F) {
         floatValue3 = -0.5F;
      }

      if (floatValue1 > 0.0F) {
         floatValue3 = 0.5F;
      }

      if (floatValue2 > 0.0F) {
         floatValue0 -= 90.0F * floatValue3;
      }

      if (floatValue2 < 0.0F) {
         floatValue0 += 90.0F * floatValue3;
      }

      return Math.toRadians((double)floatValue0);
   }

   public static void a(EventKeyboardInput eventKeyboardInput0, float floatValue1) {
      float floatValue2 = eventKeyboardInput0.getMovementForward();
      float floatValue3 = eventKeyboardInput0.getMovementSideways();
      if (floatValue2 != 0.0F || floatValue3 != 0.0F) {
         double doubleValue4 = MathHelper.wrapDegrees(Math.toDegrees(a(floatValue1, floatValue2, floatValue3)));
         float floatValue6 = 0.0F;
         float floatValue7 = 0.0F;
         float floatValue8 = Float.MAX_VALUE;

         for (float floatValue9 = -1.0F; floatValue9 <= 1.0F; floatValue9++) {
            for (float floatValue10 = -1.0F; floatValue10 <= 1.0F; floatValue10++) {
               if (floatValue9 != 0.0F || floatValue10 != 0.0F) {
                  double doubleValue11 = MathHelper.wrapDegrees(Math.toDegrees(a(floatValue1, floatValue9, floatValue10)));
                  float floatValue13 = Math.abs(MathHelper.wrapDegrees((float)(doubleValue4 - doubleValue11)));
                  if (floatValue13 < floatValue8) {
                     floatValue8 = floatValue13;
                     floatValue6 = floatValue9;
                     floatValue7 = floatValue10;
                  }
               }
            }
         }

         eventKeyboardInput0.setMovementForward(floatValue6);
         eventKeyboardInput0.setMovementSideways(floatValue7);
      }
   }

   @EventLink
   public void onInput(EventKeyboardInput eventKeyboardInput1) {
      if (this.b()) {
         a(eventKeyboardInput1, MathHelper.wrapDegrees(minecraftClient.gameRenderer.getCamera().getYaw()));
      }
   }

   private void l() {
      dev.leg1tka.rotation.Rotation value1 = new dev.leg1tka.rotation.Rotation(dev.leg1tka.rotation.FreeLookStorage.b(), dev.leg1tka.rotation.FreeLookStorage.c());
      if (this.a(value1, this.f(), this.g())) {
         this.a();
      }
   }

   @EventLink
   public void onEventTick(EventUpdate eventUpdate1) {
      if (this.c().equals(dev.leg1tka.rotation.RotationStorage.RotationState.AIM) && this.j() > this.i()) {
         this.a(dev.leg1tka.rotation.RotationStorage.RotationState.RESET);
      }

      if (this.c().equals(dev.leg1tka.rotation.RotationStorage.RotationState.RESET)) {
         this.l();
      }

      this.k++;
   }

   public static void a(dev.leg1tka.rotation.Rotation value0, float floatValue1, float floatValue2, float floatValue3, float floatValue4, int intValue5, int intValue6, boolean flag7) {
      dev.leg1tka.rotation.RotationStorage rotationStorage8 = c;
      if (minecraftClient.player != null) {
         if (rotationStorage8.h() <= intValue6) {
            if (rotationStorage8.c().equals(dev.leg1tka.rotation.RotationStorage.RotationState.IDLE) && !flag7) {
               dev.leg1tka.rotation.FreeLookStorage.a(minecraftClient.player.getYaw());
               dev.leg1tka.rotation.FreeLookStorage.b(minecraftClient.player.getPitch());
               dev.leg1tka.rotation.FreeLookStorage.a(true);
            }

            rotationStorage8.a(floatValue1);
            rotationStorage8.b(floatValue2);
            rotationStorage8.c(floatValue3);
            rotationStorage8.d(floatValue4);
            rotationStorage8.b(intValue5);
            rotationStorage8.a(intValue6);
            rotationStorage8.a(dev.leg1tka.rotation.RotationStorage.RotationState.AIM);
            rotationStorage8.a(value0);
            rotationStorage8.a(value0, floatValue1, floatValue2);
         }
      }
   }

   public static void a(dev.leg1tka.rotation.Rotation value0, float floatValue1, int intValue2, int intValue3, boolean flag4) {
      dev.leg1tka.rotation.RotationStorage rotationStorage5 = c;
      if (minecraftClient.player != null) {
         if (rotationStorage5.h() <= intValue3) {
            if (rotationStorage5.c().equals(dev.leg1tka.rotation.RotationStorage.RotationState.IDLE) && !flag4) {
               dev.leg1tka.rotation.FreeLookStorage.a(minecraftClient.player.getYaw());
               dev.leg1tka.rotation.FreeLookStorage.b(minecraftClient.player.getPitch());
               dev.leg1tka.rotation.FreeLookStorage.a(true);
            }

            rotationStorage5.a(360.0F);
            rotationStorage5.b(360.0F);
            rotationStorage5.c(floatValue1);
            rotationStorage5.d(floatValue1);
            rotationStorage5.b(intValue2);
            rotationStorage5.a(intValue3);
            rotationStorage5.a(dev.leg1tka.rotation.RotationStorage.RotationState.AIM);
            rotationStorage5.a(value0);
            minecraftClient.player.setYaw(value0.getYaw());
            minecraftClient.player.setPitch(MathHelper.clamp(value0.getPitch(), -90.0F, 90.0F));
            rotationStorage5.c(0);
         }
      }
   }

   public static void a(dev.leg1tka.rotation.Rotation value0, float floatValue1, float floatValue2, int intValue3, int intValue4) {
      a(value0, floatValue1, floatValue1, floatValue2, floatValue2, intValue3, intValue4, false);
   }

   public static void a(dev.leg1tka.rotation.Rotation value0, float floatValue1, float floatValue2, float floatValue3, int intValue4, int intValue5) {
      a(value0, floatValue1, floatValue2, floatValue3, floatValue3, intValue4, intValue5, false);
   }

   private boolean a(dev.leg1tka.rotation.Rotation value1, float floatValue2, float floatValue3) {
      if (minecraftClient.player == null) {
         return false;
      } else {
         dev.leg1tka.rotation.Rotation value4 = new dev.leg1tka.rotation.Rotation(minecraftClient.player);
         float floatValue5 = MathHelper.wrapDegrees(value1.getYaw() - value4.getYaw());
         float floatValue6 = value1.getPitch() - value4.getPitch();
         float floatValue7 = Math.min(Math.abs(floatValue5), floatValue2);
         float floatValue8 = Math.min(Math.abs(floatValue6), floatValue3);
         float floatValue9 = minecraftClient.player.getYaw();
         floatValue9 += dev.leg1tka.rotation.RotationContext.a(MathHelper.clamp(floatValue5, -floatValue7, floatValue7));
         minecraftClient.player.setYaw(floatValue9);
         float floatValue10 = minecraftClient.player.getPitch();
         floatValue10 += dev.leg1tka.rotation.RotationContext.a(MathHelper.clamp(floatValue6, -floatValue8, floatValue8));
         floatValue10 = MathHelper.clamp(floatValue10, -90.0F, 90.0F);
         minecraftClient.player.setPitch(floatValue10);
         this.c(0);
         return new dev.leg1tka.rotation.Rotation(minecraftClient.player).a(value1) < 1.0F;
      }
   }

   public void a() {
      this.a(dev.leg1tka.rotation.RotationStorage.RotationState.IDLE);
      this.a(0);
      dev.leg1tka.rotation.FreeLookStorage.a(false);
   }

   public boolean b() {
      return !this.d.equals(dev.leg1tka.rotation.RotationStorage.RotationState.IDLE);
   }

   @Generated
   public dev.leg1tka.rotation.RotationStorage.RotationState c() {
      return this.d;
   }

   @Generated
   public float d() {
      return this.e;
   }

   @Generated
   public float e() {
      return this.f;
   }

   @Generated
   public float f() {
      return this.g;
   }

   @Generated
   public float g() {
      return this.h;
   }

   @Generated
   public int h() {
      return this.i;
   }

   @Generated
   public int i() {
      return this.j;
   }

   @Generated
   public int j() {
      return this.k;
   }

   @Generated
   public dev.leg1tka.rotation.Rotation k() {
      return this.l;
   }

   @Generated
   public dev.leg1tka.rotation.RotationStorage a(dev.leg1tka.rotation.RotationStorage.RotationState rotationState1) {
      this.d = rotationState1;
      return this;
   }

   @Generated
   public dev.leg1tka.rotation.RotationStorage a(float floatValue1) {
      this.e = floatValue1;
      return this;
   }

   @Generated
   public dev.leg1tka.rotation.RotationStorage b(float floatValue1) {
      this.f = floatValue1;
      return this;
   }

   @Generated
   public dev.leg1tka.rotation.RotationStorage c(float floatValue1) {
      this.g = floatValue1;
      return this;
   }

   @Generated
   public dev.leg1tka.rotation.RotationStorage d(float floatValue1) {
      this.h = floatValue1;
      return this;
   }

   @Generated
   public dev.leg1tka.rotation.RotationStorage a(int intValue1) {
      this.i = intValue1;
      return this;
   }

   @Generated
   public dev.leg1tka.rotation.RotationStorage b(int intValue1) {
      this.j = intValue1;
      return this;
   }

   @Generated
   public dev.leg1tka.rotation.RotationStorage c(int intValue1) {
      this.k = intValue1;
      return this;
   }

   @Generated
   public dev.leg1tka.rotation.RotationStorage a(dev.leg1tka.rotation.Rotation value1) {
      this.l = value1;
      return this;
   }

   public static enum RotationState {
      AIM,
      RESET,
      IDLE;

      private RotationState() {
      }
   }
}
