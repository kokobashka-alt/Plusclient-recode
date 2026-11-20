package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;
import net.minecraft.util.math.Vec3d;

public class EventMove extends Event {
   private Vec3d movePos;

   @Generated
   public Vec3d getMovePos() {
      return this.movePos;
   }

   @Generated
   public void setMovePos(Vec3d vec3d1) {
      this.movePos = vec3d1;
   }

   @Generated
   public EventMove() {
   }

   @Generated
   public EventMove(Vec3d vec3d1) {
      this.movePos = vec3d1;
   }
}
