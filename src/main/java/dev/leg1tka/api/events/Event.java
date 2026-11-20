package dev.leg1tka.api.events;

import java.lang.reflect.InvocationTargetException;
import lombok.Generated;

public class Event {
   private boolean cancelled;

   public Event() {
   }

   public void cancel() {
      this.cancelled = true;
   }

   public void call() {
      try {
         EventInvoker.invoke(this);
      } catch (InvocationTargetException | InstantiationException | IllegalAccessException reflectiveOperationException2) {
         throw new RuntimeException("Failed to Invoke Method", reflectiveOperationException2);
      }
   }

   @Generated
   public void setCancelled(boolean flag1) {
      this.cancelled = flag1;
   }

   @Generated
   public boolean isCancelled() {
      return this.cancelled;
   }
}
