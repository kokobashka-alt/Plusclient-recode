package dev.leg1tka.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({Entity.class})
public interface EntityInvoker {
   @Invoker("unsetRemoved")
   void invokeUnsetRemoved();

   @Invoker("adjustMovementForCollisions")
   Vec3d invokeAdjustMovementForCollisions(Vec3d vec3d1);
}
