package dev.leg1tka.mixin;

import dev.leg1tka.modules.render.Chams;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({HeadFeatureRenderer.class})
public abstract class HeadFeatureRendererMixin<S extends LivingEntityRenderState, M extends EntityModel<S> & ModelWithHead> extends FeatureRenderer<S, M> {
   public HeadFeatureRendererMixin(FeatureRendererContext<S, M> featureRendererContext1) {
      super(featureRendererContext1);
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/LivingEntityRenderState;FF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onRenderHead(MatrixStack matrixStack1, VertexConsumerProvider vertexConsumerProvider2, int intValue3, S value4, float floatValue5, float floatValue6, CallbackInfo callbackInfo7) {
      if (value4 instanceof PlayerEntityRenderState playerEntityRenderState8) {
         MinecraftClient minecraftClient9 = MinecraftClient.getInstance();
         if (minecraftClient9 != null && minecraftClient9.world != null) {
            if (minecraftClient9.world.getEntityById(playerEntityRenderState8.id) instanceof PlayerEntity playerEntity11) {
               if (Chams.c != null && Chams.c.c(playerEntity11)) {
                  callbackInfo7.cancel();
               }
            }
         }
      }
   }
}
