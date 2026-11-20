package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;

public class EventBinding extends Event {
   private final int key;
   private final EventBinding.BindType bindType;

   public boolean isKeyDown(int intValue1) {
      return this.key == intValue1;
   }

   @Generated
   public EventBinding(int intValue1, EventBinding.BindType bindType2) {
      this.key = intValue1;
      this.bindType = bindType2;
   }

   @Generated
   public int getKey() {
      return this.key;
   }

   @Generated
   public EventBinding.BindType getBindType() {
      return this.bindType;
   }

   public static enum BindType {
      KEYBOARD,
      MOUSE;

      private BindType() {
      }
   }
}
