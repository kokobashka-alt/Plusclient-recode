package dev.leg1tka.mixin;

import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.api.ClientAccess;
import dev.leg1tka.api.utils.security.ClientIntegrityState;
import dev.leg1tka.modules.render.SeeInvisibles;
import dev.leg1tka.render.InvisibleTargetAccess;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LivingEntityRenderer.class})
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> implements dev.leg1tka.api.ClientAccess {
   public LivingEntityRendererMixin() {
   }

   @Inject(
      method = {"updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V"},
      at = {@At("TAIL")}
   )
   private void AspectDLC$updateSeeInvisiblesState(T value1, S value2, float floatValue3, CallbackInfo callbackInfo4) {
      boolean flag5 = this.AspectDLC$shouldRenderInvisible((T)value1);
      ((dev.leg1tka.render.InvisibleTargetAccess)value2).AspectDLC$setSeeInvisiblesTarget(flag5);
      if (flag5) {
         value2.invisible = true;
         value2.invisibleToPlayer = false;
      }

      if (value1 == minecraftClient.player && minecraftClient.options != null && !minecraftClient.options.getPerspective().isFirstPerson() && dev.leg1tka.api.utils.security.ClientIntegrityState.b()) {
         value2.pitch = dev.leg1tka.api.utils.security.ClientIntegrityState.d();
         value2.bodyYaw = dev.leg1tka.api.utils.security.ClientIntegrityState.c();
         value2.yawDegrees = 0.0F;
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = {@At("HEAD")}
   )
   private void AspectDLC$beginInvisibleEquipment(S value1, MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider3, int intValue4, CallbackInfo callbackInfo5) {
      SeeInvisibles.c.e(((dev.leg1tka.render.InvisibleTargetAccess)value1).AspectDLC$isSeeInvisiblesTarget());
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = {@At("RETURN")}
   )
   private void AspectDLC$endInvisibleEquipment(S value1, MatrixStack matrixStack2, VertexConsumerProvider vertexConsumerProvider3, int intValue4, CallbackInfo callbackInfo5) {
      SeeInvisibles.q();
   }

   @ModifyConstant(
      method = {"render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      constant = {@Constant(
         intValue = 654311423
      )}
   )
   private int AspectDLC$changeInvisibleAlpha(int intValue1, S value2, MatrixStack matrixStack3, VertexConsumerProvider vertexConsumerProvider4, int intValue5) {
      return ((dev.leg1tka.render.InvisibleTargetAccess)value2).AspectDLC$isSeeInvisiblesTarget() ? SeeInvisibles.c.p() : intValue1;
   }

   @Unique
   private boolean AspectDLC$shouldRenderInvisible(T value1) {
      if (value1 instanceof PlayerEntity playerEntity2 && ModuleManager.a != null) {
         SeeInvisibles seeInvisibles3 = ModuleManager.ap;
         return seeInvisibles3 != null && seeInvisibles3.a(playerEntity2);
      }

      return false;
   }

   @Unique
   private PlayerEntity AspectDLC$resolvePlayer(S value1) {
      if (value1 instanceof PlayerEntityRenderState playerEntityRenderState2 && minecraftClient.world != null) {
         return minecraftClient.world.getEntityById(playerEntityRenderState2.id) instanceof PlayerEntity playerEntity4 ? playerEntity4 : null;
      }

      return null;
   }
}
