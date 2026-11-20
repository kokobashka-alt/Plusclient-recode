package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;
import net.minecraft.util.math.MathHelper;

public class EventKeyboardInput extends Event {
   private float movementForward;
   private float movementSideways;

   public void setYaw(float floatValue1, float floatValue2) {
      float floatValue3 = this.getMovementForward();
      float floatValue4 = this.getMovementSideways();
      double doubleValue5 = MathHelper.wrapDegrees(Math.toDegrees(this.direction(floatValue2, (double)floatValue3, (double)floatValue4)));
      if (floatValue3 != 0.0F || floatValue4 != 0.0F) {
         float floatValue7 = 0.0F;
         float floatValue8 = 0.0F;
         float floatValue9 = Float.MAX_VALUE;

         for (float floatValue10 = -1.0F; floatValue10 <= 1.0F; floatValue10++) {
            for (float floatValue11 = -1.0F; floatValue11 <= 1.0F; floatValue11++) {
               if (floatValue11 != 0.0F || floatValue10 != 0.0F) {
                  double doubleValue12 = MathHelper.wrapDegrees(Math.toDegrees(this.direction(floatValue1, (double)floatValue10, (double)floatValue11)));
                  double doubleValue14 = Math.abs(doubleValue5 - doubleValue12);
                  if (doubleValue14 < (double)floatValue9) {
                     floatValue9 = (float)doubleValue14;
                     floatValue7 = floatValue10;
                     floatValue8 = floatValue11;
                  }
               }
            }
         }

         this.setMovementForward(floatValue7);
         this.setMovementSideways(floatValue8);
      }
   }

   private double direction(float floatValue1, double doubleValue2, double doubleValue4) {
      if (doubleValue2 < 0.0) {
         floatValue1 += 180.0F;
      }

      float floatValue6 = 1.0F;
      if (doubleValue2 < 0.0) {
         floatValue6 = -0.5F;
      } else if (doubleValue2 > 0.0) {
         floatValue6 = 0.5F;
      }

      if (doubleValue4 > 0.0) {
         floatValue1 -= 90.0F * floatValue6;
      }

      if (doubleValue4 < 0.0) {
         floatValue1 += 90.0F * floatValue6;
      }

      return Math.toRadians((double)floatValue1);
   }

   @Generated
   public EventKeyboardInput(float floatValue1, float floatValue2) {
      this.movementForward = floatValue1;
      this.movementSideways = floatValue2;
   }

   @Generated
   public float getMovementForward() {
      return this.movementForward;
   }

   @Generated
   public float getMovementSideways() {
      return this.movementSideways;
   }

   @Generated
   public void setMovementForward(float floatValue1) {
      this.movementForward = floatValue1;
   }

   @Generated
   public void setMovementSideways(float floatValue1) {
      this.movementSideways = floatValue1;
   }
}
