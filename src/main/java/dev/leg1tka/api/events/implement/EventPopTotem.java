package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;
import net.minecraft.entity.player.PlayerEntity;

public class EventPopTotem extends Event {
   private final PlayerEntity player;

   @Generated
   public EventPopTotem(PlayerEntity playerEntity1) {
      this.player = playerEntity1;
   }

   @Generated
   public PlayerEntity getPlayer() {
      return this.player;
   }
}
