package dev.leg1tka.mixin;

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.implement.EventRotation;
import java.lang.reflect.InvocationTargetException;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({Camera.class})
public abstract class CameraMixin {
   public CameraMixin() {
   }

   @Redirect(
      method = {"update(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;ZZF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V"
      )
   )
   private void redirectSetRotation(Camera camera1, float floatValue2, float floatValue3, BlockView blockView4, Entity entity5, boolean flag6, boolean flag7, float floatValue8) throws InvocationTargetException, IllegalAccessException, InstantiationException {
      EventRotation eventRotation9 = new EventRotation(floatValue2, floatValue3, floatValue8);
      EventInvoker.invoke(eventRotation9);
      float floatValue10 = eventRotation9.getYaw();
      float floatValue11 = eventRotation9.getPitch();
      if (flag6 && flag7) {
         floatValue10 += 180.0F;
         floatValue11 = -floatValue11;
      }

      ((ICameraMixin)camera1).setCustomRotation(floatValue10, floatValue11);
   }

   @Redirect(
      method = {"update(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;ZZF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/Camera;clipToSpace(F)F"
      )
   )
   private float redirectClipToSpace(Camera camera1, float floatValue2, BlockView blockView3, Entity entity4, boolean flag5, boolean flag6, float floatValue7) {
      return ((ICameraMixin)camera1).setClipToSpace(floatValue2);
   }

   @Redirect(
      method = {"update(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;ZZF)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/Camera;moveBy(FFF)V"
      )
   )
   private void redirectMoveBy(Camera camera1, float floatValue2, float floatValue3, float floatValue4, BlockView blockView5, Entity entity6, boolean flag7, boolean flag8, float floatValue9) {
      ((ICameraMixin)camera1).setCustomMoveBy(floatValue2, floatValue3, floatValue4);
   }
}
