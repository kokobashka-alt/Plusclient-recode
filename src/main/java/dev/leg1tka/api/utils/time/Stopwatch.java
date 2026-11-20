package dev.leg1tka.api.utils.time;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import lombok.Generated;

public class Stopwatch {
   private long startMillis;

   public Stopwatch() {
      this.reset();
   }

   public boolean hasElapsed(float floatValue1) {
      return (float)System.currentTimeMillis() - floatValue1 >= (float)this.startMillis;
   }

   public boolean hasElapsed(long longValue1) {
      return System.currentTimeMillis() - this.startMillis >= longValue1;
   }

   public boolean hasElapsedStrict(long longValue1) {
      return System.currentTimeMillis() - this.startMillis > longValue1;
   }

   public void reset() {
      this.startMillis = System.currentTimeMillis();
   }

   public long elapsedMillis() {
      return System.currentTimeMillis() - this.startMillis;
   }

   @Generated
   public long getStartMillis() {
      return this.startMillis;
   }

   @Generated
   public void setStartMillis(long longValue1) {
      this.startMillis = longValue1;
   }
}
