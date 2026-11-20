package dev.leg1tka.mixin;

import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.modules.render.EntityESP;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityRenderer.class})
public abstract class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {
   public EntityRendererMixin() {
   }

   @Inject(
      method = {"updateRenderState(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/render/entity/state/EntityRenderState;F)V"},
      at = {@At("TAIL")}
   )
   private void AspectDLC$hideEspLabel(T value1, S value2, float floatValue3, CallbackInfo callbackInfo4) {
      if (ModuleManager.a != null && value2.displayName != null) {
         EntityESP entityESP5 = ModuleManager.P;
         if (entityESP5 != null && entityESP5.a(value1)) {
            value2.displayName = null;
         }
      }
   }
}
