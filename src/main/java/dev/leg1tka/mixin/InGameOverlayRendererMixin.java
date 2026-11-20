package dev.leg1tka.mixin;

import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.modules.render.NoRender;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({InGameOverlayRenderer.class})
public class InGameOverlayRendererMixin {
   public InGameOverlayRendererMixin() {
   }

   @Inject(
      method = {"renderFireOverlay(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void AspectDLC$renderFireOverlay(MatrixStack matrixStack0, VertexConsumerProvider vertexConsumerProvider1, CallbackInfo callbackInfo2) {
      if (ModuleManager.a != null) {
         NoRender noRender3 = ModuleManager.an;
         if (noRender3 != null && noRender3.c("Огонь")) {
            callbackInfo2.cancel();
         }
      }
   }

   @Inject(
      method = {"renderInWallOverlay(Lnet/minecraft/client/texture/Sprite;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void AspectDLC$renderInWallOverlay(Sprite sprite0, MatrixStack matrixStack1, VertexConsumerProvider vertexConsumerProvider2, CallbackInfo callbackInfo3) {
      if (ModuleManager.a != null) {
         NoRender noRender4 = ModuleManager.an;
         if (noRender4 != null && noRender4.c("Оверлей в блоке")) {
            callbackInfo3.cancel();
         }
      }
   }
}
