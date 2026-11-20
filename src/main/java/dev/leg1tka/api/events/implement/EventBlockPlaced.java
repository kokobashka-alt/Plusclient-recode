package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class EventBlockPlaced extends Event {
   private final BlockPos pos;
   private final ItemStack stack;

   public EventBlockPlaced(BlockPos blockPos1, ItemStack itemStack2) {
      this.pos = blockPos1;
      this.stack = itemStack2;
   }

   @Generated
   public BlockPos getPos() {
      return this.pos;
   }

   @Generated
   public ItemStack getStack() {
      return this.stack;
   }
}
