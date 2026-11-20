package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;

public class EventInteractBlock extends Event {
   private final Hand hand;
   private final BlockHitResult hit;

   public EventInteractBlock(Hand hand1, BlockHitResult blockHitResult2) {
      this.hand = hand1;
      this.hit = blockHitResult2;
   }

   @Generated
   public Hand getHand() {
      return this.hand;
   }

   @Generated
   public BlockHitResult getHit() {
      return this.hit;
   }
}
