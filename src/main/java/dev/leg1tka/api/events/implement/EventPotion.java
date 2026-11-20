package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;
import net.minecraft.util.math.BlockPos;

public class EventPotion extends Event {
   private final EventPotion.Type type;
   private final int data;
   private final BlockPos pos;

   @Generated
   public EventPotion(EventPotion.Type type1, int intValue2, BlockPos blockPos3) {
      this.type = type1;
      this.data = intValue2;
      this.pos = blockPos3;
   }

   @Generated
   public EventPotion.Type getType() {
      return this.type;
   }

   @Generated
   public int getData() {
      return this.data;
   }

   @Generated
   public BlockPos getPos() {
      return this.pos;
   }

   public static enum Type {
      PARTICLES;

      private Type() {
      }
   }
}
