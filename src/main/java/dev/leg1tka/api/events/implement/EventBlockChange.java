package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class EventBlockChange extends Event {
   private final BlockPos pos;
   private final BlockState oldState;
   private final BlockState newState;

   @Generated
   public EventBlockChange(BlockPos blockPos1, BlockState blockState2, BlockState blockState3) {
      this.pos = blockPos1;
      this.oldState = blockState2;
      this.newState = blockState3;
   }

   @Generated
   public BlockPos getPos() {
      return this.pos;
   }

   @Generated
   public BlockState getOldState() {
      return this.oldState;
   }

   @Generated
   public BlockState getNewState() {
      return this.newState;
   }
}
