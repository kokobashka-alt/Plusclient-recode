package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;

public class EventMoveInput extends Event {
   private float forward;
   private float strafe;
   private boolean jump;
   private boolean sneak;

   @Generated
   public float getForward() {
      return this.forward;
   }

   @Generated
   public float getStrafe() {
      return this.strafe;
   }

   @Generated
   public boolean isJump() {
      return this.jump;
   }

   @Generated
   public boolean isSneak() {
      return this.sneak;
   }

   @Generated
   public void setForward(float floatValue1) {
      this.forward = floatValue1;
   }

   @Generated
   public void setStrafe(float floatValue1) {
      this.strafe = floatValue1;
   }

   @Generated
   public void setJump(boolean flag1) {
      this.jump = flag1;
   }

   @Generated
   public void setSneak(boolean flag1) {
      this.sneak = flag1;
   }

   @Generated
   public EventMoveInput(float floatValue1, float floatValue2, boolean flag3, boolean flag4) {
      this.forward = floatValue1;
      this.strafe = floatValue2;
      this.jump = flag3;
      this.sneak = flag4;
   }
}
