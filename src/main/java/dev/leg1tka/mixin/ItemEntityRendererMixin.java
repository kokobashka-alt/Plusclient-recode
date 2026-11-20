package dev.leg1tka.mixin;

import dev.leg1tka.modules.render.Cosmetics;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemEntityRenderer.class})
public abstract class ItemEntityRendererMixin extends EntityRenderer<ItemEntity, ItemEntityRenderState> {
   @Shadow
   @Final
   private ItemModelManager itemModelManager;

   protected ItemEntityRendererMixin(Context context1) {
      super(context1);
   }

   @Inject(
      method = {"updateRenderState(Lnet/minecraft/entity/ItemEntity;Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;F)V"},
      at = {@At("TAIL")}
   )
   private void items2d$useGuiModel(ItemEntity itemEntity1, ItemEntityRenderState itemEntityRenderState2, float floatValue3, CallbackInfo callbackInfo4) {
      if (Cosmetics.c.p()) {
         this.itemModelManager.updateForNonLivingEntity(itemEntityRenderState2.itemRenderState, itemEntity1.getStack(), ModelTransformationMode.GUI, itemEntity1);
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/render/entity/state/ItemEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void items2d$renderSprite(ItemEntityRenderState itemEntityRenderState1, MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider3, int intValue4, CallbackInfo callbackInfo5) {
      Cosmetics cosmetics6 = Cosmetics.c;
      if (cosmetics6.p() && !itemEntityRenderState1.itemRenderState.isEmpty()) {
         callbackInfo5.cancel();
         float floatValue7 = cosmetics6.d.f();
         float floatValue8 = cosmetics6.e.getValue() ? MathHelper.sin(itemEntityRenderState1.age / 10.0F + itemEntityRenderState1.uniqueOffset) * 0.1F + 0.1F : 0.0F;
         matrixStack2.push();
         matrixStack2.translate(0.0F, floatValue7 * 0.5F + floatValue8, 0.0F);
         matrixStack2.multiply(this.dispatcher.getRotation());
         matrixStack2.scale(floatValue7, floatValue7, 0.01F);
         int intValue9 = cosmetics6.f.getValue() ? itemEntityRenderState1.renderedAmount : 1;

         for (int intValue10 = intValue9 - 1; intValue10 >= 0; intValue10--) {
            matrixStack2.push();
            matrixStack2.translate((float)intValue10 * 0.06F, (float)(-intValue10) * 0.06F, (float)(-intValue10) * 1.0F);
            itemEntityRenderState1.itemRenderState.render(matrixStack2, vertexConsumerProvider3, intValue4, OverlayTexture.DEFAULT_UV);
            matrixStack2.pop();
         }

         matrixStack2.pop();
         super.render(itemEntityRenderState1, matrixStack2, vertexConsumerProvider3, intValue4);
      }
   }
}
