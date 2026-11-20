package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;

public class EventSlowWalking extends Event {
   private boolean cancelled;

   public EventSlowWalking() {
   }

   @Override
   public boolean isCancelled() {
      return this.cancelled;
   }

   @Override
   public void setCancelled(boolean flag1) {
      this.cancelled = flag1;
   }
}
