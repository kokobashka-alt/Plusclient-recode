package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;
import net.minecraft.util.math.BlockPos;

public class EventBlockCollide extends Event {
   private final BlockPos pos;

   public EventBlockCollide(BlockPos blockPos1) {
      this.pos = blockPos1;
   }

   @Generated
   public BlockPos getPos() {
      return this.pos;
   }
}
