package dev.leg1tka.mixin;

import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.api.ClientAccess;
import dev.leg1tka.modules.render.Chams;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({HeldItemFeatureRenderer.class})
public class HeldItemFeatureRendererMixin implements dev.leg1tka.api.ClientAccess {
   public HeldItemFeatureRendererMixin() {
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/EntityRenderState;FF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$hideHeldItems(
      MatrixStack matrixStack1, VertexConsumerProvider vertexConsumerProvider2, int intValue3, EntityRenderState entityRenderState4, float floatValue5, float floatValue6, CallbackInfo callbackInfo7
   ) {
      if (entityRenderState4 instanceof PlayerEntityRenderState playerEntityRenderState8 && ModuleManager.a != null && minecraftClient.world != null) {
         Chams chams9 = ModuleManager.b;
         if (chams9 != null && chams9.isEnabled() && minecraftClient.world.getEntityById(playerEntityRenderState8.id) instanceof PlayerEntity playerEntity11 && chams9.c(playerEntity11)) {
            callbackInfo7.cancel();
         }

         return;
      }
   }
}
