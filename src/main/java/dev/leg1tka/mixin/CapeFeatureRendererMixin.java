package dev.leg1tka.mixin;

import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.api.ClientAccess;
import dev.leg1tka.modules.render.Chams;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({CapeFeatureRenderer.class})
public class CapeFeatureRendererMixin implements dev.leg1tka.api.ClientAccess {
   public CapeFeatureRendererMixin() {
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$hideCape(
      MatrixStack matrixStack1, VertexConsumerProvider vertexConsumerProvider2, int intValue3, PlayerEntityRenderState playerEntityRenderState4, float floatValue5, float floatValue6, CallbackInfo callbackInfo7
   ) {
      if (ModuleManager.a != null && minecraftClient.world != null) {
         Chams chams8 = ModuleManager.b;
         if (chams8 != null && chams8.isEnabled()) {
            if (minecraftClient.world.getEntityById(playerEntityRenderState4.id) instanceof PlayerEntity playerEntity10 && chams8.c(playerEntity10)) {
               callbackInfo7.cancel();
            }
         }
      }
   }
}
