package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;

public class EventCloseInv extends Event {
   public int windowId;

   @Generated
   public EventCloseInv(int intValue1) {
      this.windowId = intValue1;
   }
}
