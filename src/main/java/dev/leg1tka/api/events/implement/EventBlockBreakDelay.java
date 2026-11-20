package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;

public class EventBlockBreakDelay extends Event {
   private final int delay;
   private boolean reset;

   public EventBlockBreakDelay(int intValue1) {
      this.delay = intValue1;
   }

   @Generated
   public int getDelay() {
      return this.delay;
   }

   @Generated
   public boolean isReset() {
      return this.reset;
   }

   @Generated
   public void setReset(boolean flag1) {
      this.reset = flag1;
   }
}
