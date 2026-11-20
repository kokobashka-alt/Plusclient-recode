package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class EventAttackEntityPost extends Event {
   private final PlayerEntity player;
   private final Entity target;

   @Generated
   public EventAttackEntityPost(PlayerEntity playerEntity1, Entity entity2) {
      this.player = playerEntity1;
      this.target = entity2;
   }

   @Generated
   public PlayerEntity getPlayer() {
      return this.player;
   }

   @Generated
   public Entity getTarget() {
      return this.target;
   }
}
