package dev.leg1tka.mixin;

import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({Camera.class})
public interface ICameraMixin {
   @Invoker("setRotation")
   void setCustomRotation(float floatValue1, float floatValue2);

   @Invoker("clipToSpace")
   float setClipToSpace(float floatValue1);

   @Invoker("moveBy")
   void setCustomMoveBy(float floatValue1, float floatValue2, float floatValue3);
}
