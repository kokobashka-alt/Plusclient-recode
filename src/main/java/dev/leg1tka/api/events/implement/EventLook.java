package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;

public class EventLook extends Event {
   private double yaw;
   private double pitch;

   @Generated
   public double getYaw() {
      return this.yaw;
   }

   @Generated
   public double getPitch() {
      return this.pitch;
   }

   @Generated
   public void setYaw(double doubleValue1) {
      this.yaw = doubleValue1;
   }

   @Generated
   public void setPitch(double doubleValue1) {
      this.pitch = doubleValue1;
   }

   @Generated
   public EventLook(double doubleValue1, double doubleValue3) {
      this.yaw = doubleValue1;
      this.pitch = doubleValue3;
   }
}
