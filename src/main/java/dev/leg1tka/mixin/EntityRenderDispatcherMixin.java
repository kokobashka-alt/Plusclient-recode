package dev.leg1tka.mixin;

import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.modules.render.NoRender;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityRenderDispatcher.class})
public class EntityRenderDispatcherMixin {
   public EntityRenderDispatcherMixin() {
   }

   @Inject(
      method = {"renderShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/entity/state/EntityRenderState;FFLnet/minecraft/world/WorldView;F)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void AspectDLC$renderShadow(
      MatrixStack matrixStack0, VertexConsumerProvider vertexConsumerProvider1, EntityRenderState entityRenderState2, float floatValue3, float floatValue4, WorldView worldView5, float floatValue6, CallbackInfo callbackInfo7
   ) {
      if (ModuleManager.a != null) {
         NoRender noRender8 = ModuleManager.an;
         if (noRender8 != null && noRender8.c("Тени")) {
            callbackInfo7.cancel();
         }
      }
   }
}
