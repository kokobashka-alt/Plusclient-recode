package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;

public class EventRotation extends Event {
   private float yaw;
   private float pitch;
   private float partialTicks;

   @Generated
   public float getYaw() {
      return this.yaw;
   }

   @Generated
   public float getPitch() {
      return this.pitch;
   }

   @Generated
   public float getPartialTicks() {
      return this.partialTicks;
   }

   @Generated
   public void setYaw(float floatValue1) {
      this.yaw = floatValue1;
   }

   @Generated
   public void setPitch(float floatValue1) {
      this.pitch = floatValue1;
   }

   @Generated
   public void setPartialTicks(float floatValue1) {
      this.partialTicks = floatValue1;
   }

   @Generated
   public EventRotation(float floatValue1, float floatValue2, float floatValue3) {
      this.yaw = floatValue1;
      this.pitch = floatValue2;
      this.partialTicks = floatValue3;
   }
}
