package dev.leg1tka.mixin;

import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.modules.render.CustomWorld;
import net.minecraft.client.world.ClientWorld.Properties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Properties.class})
public class ClientWorldPropertiesMixin {
   @Shadow
   private long timeOfDay;

   public ClientWorldPropertiesMixin() {
   }

   @Inject(
      method = {"setTimeOfDay(J)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$setTimeOfDay(long longValue1, CallbackInfo callbackInfo3) {
      if (ModuleManager.a != null) {
         CustomWorld customWorld4 = ModuleManager.aF;
         if (customWorld4 != null && customWorld4.p()) {
            this.timeOfDay = customWorld4.r();
            callbackInfo3.cancel();
         }
      }
   }
}
