package dev.leg1tka.mixin;

import com.google.common.base.MoreObjects;
import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.render.StyledColorRenderer;
import dev.leg1tka.modules.combat.AttackAura;
import dev.leg1tka.api.animation.AnimationDirectionState;
import dev.leg1tka.modules.render.ShaderHands;
import dev.leg1tka.modules.render.SwingAnimations;
import dev.leg1tka.modules.render.ViewModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.item.HeldItemRenderer.HandRenderType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({HeldItemRenderer.class})
public abstract class HeldItemRendererMixin {
   @Shadow
   private ItemStack mainHand;
   @Shadow
   private float equipProgressMainHand;
   @Shadow
   private float prevEquipProgressMainHand;
   @Shadow
   private float prevEquipProgressOffHand;
   @Shadow
   private float equipProgressOffHand;
   @Shadow
   private ItemStack offHand;

   public HeldItemRendererMixin() {
   }

   @Shadow
   protected abstract void renderFirstPersonItem(
      AbstractClientPlayerEntity abstractClientPlayerEntity1,
      float floatValue2,
      float floatValue3,
      Hand hand4,
      float floatValue5,
      ItemStack itemStack6,
      float floatValue7,
      MatrixStack matrixStack8,
      VertexConsumerProvider vertexConsumerProvider9,
      int intValue10
   );

   @Inject(
      method = {"renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"},
      at = {@At("HEAD")}
   )
   private void onRenderItemHead(float floatValue1, MatrixStack matrixStack2, Immediate immediate3, ClientPlayerEntity clientPlayerEntity4, int intValue5, CallbackInfo callbackInfo6) {
      ShaderHands shaderHands7 = this.getShaderHands();
      if (shaderHands7 != null && shaderHands7.isEnabled()) {
         dev.leg1tka.render.StyledColorRenderer.a().b();
      }
   }

   @Inject(
      method = {"renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"},
      at = {@At("TAIL")}
   )
   private void onRenderItemTail(float floatValue1, MatrixStack matrixStack2, Immediate immediate3, ClientPlayerEntity clientPlayerEntity4, int intValue5, CallbackInfo callbackInfo6) {
      ShaderHands shaderHands7 = this.getShaderHands();
      if (shaderHands7 != null && shaderHands7.isEnabled()) {
         dev.leg1tka.render.StyledColorRenderer.a().c();
      }
   }

   @Redirect(
      method = {"renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"
      )
   )
   private void onRenderFirstPersonItemCall(
      HeldItemRenderer heldItemRenderer1,
      AbstractClientPlayerEntity abstractClientPlayerEntity2,
      float floatValue3,
      float floatValue4,
      Hand hand5,
      float floatValue6,
      ItemStack itemStack7,
      float floatValue8,
      MatrixStack matrixStack9,
      VertexConsumerProvider vertexConsumerProvider10,
      int intValue11
   ) {
      Hand hand12 = hand5;
      SwingAnimations swingAnimations13 = this.getTweaks();
      if (swingAnimations13 != null && swingAnimations13.isEnabled() && !swingAnimations13.g.getValue() && swingAnimations13.s.getValue()) {
         hand12 = hand5 == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND;
      }

      ((HeldItemRendererInvoker)heldItemRenderer1).whylol$callRenderFirstPersonItem(abstractClientPlayerEntity2, floatValue3, floatValue4, hand12, floatValue6, itemStack7, floatValue8, matrixStack9, vertexConsumerProvider10, intValue11);
   }

   @ModifyArg(
      method = {"renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderArmHoldingItem(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IFFLnet/minecraft/util/Arm;)V"
      ),
      index = 5
   )
   private Arm swapEmptyHandArm(Arm arm1) {
      SwingAnimations swingAnimations2 = this.getTweaks();
      if (swingAnimations2 != null && swingAnimations2.isEnabled() && !swingAnimations2.g.getValue() && swingAnimations2.s.getValue()) {
         return arm1 == Arm.RIGHT ? Arm.LEFT : Arm.RIGHT;
      } else {
         return arm1;
      }
   }

   @Inject(
      method = {"renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/util/math/MatrixStack;push()V",
         shift = Shift.AFTER
      )}
   )
   private void onRenderFirstPersonItem(
      AbstractClientPlayerEntity abstractClientPlayerEntity1,
      float floatValue2,
      float floatValue3,
      Hand hand4,
      float floatValue5,
      ItemStack itemStack6,
      float floatValue7,
      MatrixStack matrixStack8,
      VertexConsumerProvider vertexConsumerProvider9,
      int intValue10,
      CallbackInfo callbackInfo11
   ) {
      ViewModel viewModel12 = this.getViewModel();
      if (viewModel12 != null && viewModel12.isEnabled()) {
         if (hand4 == Hand.MAIN_HAND) {
            matrixStack8.translate(viewModel12.d.f(), viewModel12.e.f(), viewModel12.f.f());
         } else {
            matrixStack8.translate(viewModel12.g.f(), viewModel12.h.f(), viewModel12.i.f());
         }
      }
   }

   @Redirect(
      method = {"renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/HeldItemRenderer;swingArm(FFLnet/minecraft/client/util/math/MatrixStack;ILnet/minecraft/util/Arm;)V",
         ordinal = 2
      )
   )
   private void onSwingArm(HeldItemRenderer heldItemRenderer1, float floatValue2, float floatValue3, MatrixStack matrixStack4, int intValue5, Arm arm6) {
      SwingAnimations swingAnimations7 = this.getTweaks();
      if (swingAnimations7 != null && swingAnimations7.isEnabled()) {
         boolean flag8 = swingAnimations7.g.getValue() && swingAnimations7.j.getValue() || !swingAnimations7.g.getValue() && swingAnimations7.k.getValue() && swingAnimations7.t.getValue();
         if (flag8) {
            dev.leg1tka.api.animation.AnimationDirectionState.a(floatValue2);
         }

         if (swingAnimations7.g.getValue()) {
            this.callSwingArm(heldItemRenderer1, floatValue2, floatValue3, matrixStack4, intValue5, arm6);
         } else if (!swingAnimations7.k.getValue()) {
            this.callSwingArm(heldItemRenderer1, floatValue2, floatValue3, matrixStack4, intValue5, arm6);
         } else {
            AttackAura attackAura9 = ModuleManager.a != null ? ModuleManager.i : null;
            if (!swingAnimations7.r.getValue() || attackAura9 != null && attackAura9.isEnabled() && attackAura9.E() != null && attackAura9.E().isAlive()) {
               if (MinecraftClient.getInstance().player != null) {
                  Arm arm10 = MinecraftClient.getInstance().player.getMainArm();
                  if (swingAnimations7.s.getValue()) {
                     arm10 = arm10 == Arm.RIGHT ? Arm.LEFT : Arm.RIGHT;
                  }

                  if (arm6 != arm10) {
                     this.callSwingArm(heldItemRenderer1, floatValue2, floatValue3, matrixStack4, intValue5, arm6);
                     return;
                  }
               }

               int intValue20 = arm6 == Arm.RIGHT ? 1 : -1;
               flag8 = swingAnimations7.g.getValue() && swingAnimations7.j.getValue() || !swingAnimations7.g.getValue() && swingAnimations7.t.getValue();
               if (flag8) {
                  intValue20 = dev.leg1tka.api.animation.AnimationDirectionState.a() ? 1 : -1;
               }

               float floatValue11 = swingAnimations7.m.f();
               float floatValue12 = MathHelper.sin(floatValue2 * floatValue2 * (float) Math.PI);
               float floatValue13 = MathHelper.sin(MathHelper.sqrt(floatValue2) * (float) Math.PI);
               String string14 = swingAnimations7.l.g();
               switch (string14) {
                  case "Down":
                     matrixStack4.translate((float)intValue20 * 0.56F, -0.32F, -0.72F);
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(76 * intValue20)));
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(floatValue13 * -5.0F * floatValue11));
                     matrixStack4.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(floatValue13 * -100.0F * floatValue11));
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue13 * -155.0F * floatValue11));
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-100.0F));
                     break;
                  case "Poke":
                     float floatValue27 = (float)Math.sin((double)floatValue2 * (Math.PI / 2) * 2.0);
                     float floatValue30 = floatValue11 / 3.0F;
                     matrixStack4.translate((float)intValue20 * 0.56F, -0.52F, -0.72F);
                     matrixStack4.translate(0.0F, 0.0F, floatValue30 * -floatValue27);
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(75.0F * (float)intValue20));
                     matrixStack4.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((-75.0F * (floatValue11 / 4.0F) * floatValue27 - 60.0F) * (float)intValue20));
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-75.0F));
                     break;
                  case "Static":
                     matrixStack4.translate((float)intValue20 * 0.56F, -0.42F, -0.72F);
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue13 * -60.0F * floatValue11));
                     matrixStack4.translate(0.0, -0.1, 0.0);
                     break;
                  case "Feast":
                     matrixStack4.translate((float)intValue20 * 0.56F, -0.32F, -0.72F);
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(30 * intValue20)));
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(floatValue13 * 75.0F * (float)intValue20 * floatValue11));
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue13 * -65.0F * floatValue11));
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(30 * intValue20)));
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-80.0F));
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(35 * intValue20)));
                     break;
                  case "Akrien":
                     matrixStack4.translate((float)intValue20 * 0.65F, -0.32F, -0.72F);
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(76 * intValue20)));
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(floatValue13 * -5.0F * floatValue11));
                     matrixStack4.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(floatValue13 * -100.0F * floatValue11));
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue13 * -155.0F * floatValue11));
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-100.0F));
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(floatValue13 * 25.0F * floatValue11));
                     matrixStack4.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(floatValue13 * -25.0F * floatValue11));
                     matrixStack4.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(floatValue12 * 15.0F * floatValue11));
                     matrixStack4.translate(floatValue13 * 0.18F * floatValue11, floatValue13 * 0.59F * floatValue11, 0.0F);
                     break;
                  case "Smooth":
                     this.applySwingOffset(matrixStack4, intValue20, floatValue2, floatValue11);
                     break;
                  case "Block":
                     if (floatValue2 > 0.0F) {
                        float floatValue25 = MathHelper.sin(MathHelper.sqrt(floatValue2) * (float) Math.PI);
                        matrixStack4.translate(0.56F * (float)intValue20, floatValue3 * -0.2F - 0.5F, -0.7F);
                        matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(45 * intValue20)));
                        matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue25 * -85.0F * floatValue11));
                        matrixStack4.translate(-0.1F * (float)intValue20, 0.28F, 0.2F);
                        matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-85.0F));
                     } else {
                        float floatValue26 = -0.4F * MathHelper.sin(MathHelper.sqrt(floatValue2) * (float) Math.PI);
                        float floatValue29 = 0.2F * MathHelper.sin(MathHelper.sqrt(floatValue2) * (float) (Math.PI * 2));
                        float floatValue18 = -0.2F * MathHelper.sin(floatValue2 * (float) Math.PI);
                        matrixStack4.translate(floatValue26 * (float)intValue20 * floatValue11, floatValue29 * floatValue11, floatValue18 * floatValue11);
                        this.applyEquipOffset(matrixStack4, intValue20, floatValue3);
                        this.applySwingOffset(matrixStack4, intValue20, floatValue2, floatValue11);
                     }
                     break;
                  case "ToBack":
                     float floatValue24 = MathHelper.sin(MathHelper.sqrt(floatValue2) * (float) Math.PI);
                     matrixStack4.translate(0.65F * (float)intValue20, -0.45F, -0.9F);
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(50.0F));
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((-30.0F * (1.0F - floatValue24 * floatValue11) - 30.0F) * (float)intValue20));
                     matrixStack4.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(110.0F * (float)intValue20));
                     break;
                  case "SelfBack":
                     float floatValue23 = (float)Math.sin((double)floatValue2 * (Math.PI / 2) * 2.0);
                     matrixStack4.translate(0.65F * (float)intValue20, -0.3F, -0.8F);
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(90 * intValue20)));
                     matrixStack4.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(-70 * intValue20)));
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-100.0F - 60.0F * floatValue11 * floatValue23));
                     break;
                  case "Break":
                  case "Брик":
                     matrixStack4.translate(0.66F * (float)intValue20, -0.3F, -0.38F);
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(270 * intValue20)));
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue13 * 10.0F * floatValue11));
                     matrixStack4.scale(0.5F, 0.5F, 0.5F);
                     matrixStack4.translate(-0.1F * (float)intValue20, 0.2F, 0.0F);
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-10.0F * (float)intValue20));
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-105.0F * (float)intValue20));
                     break;
                  case "DropDown":
                     float floatValue22 = (float)Math.sin((double)floatValue2 * (Math.PI / 2) * 2.0);
                     this.applyEquipOffset(matrixStack4, intValue20, 0.0F);
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(80.0F));
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(swingAnimations7.n.f()));
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-swingAnimations7.o.f() * floatValue22 * floatValue11));
                     break;
                  case "Pander":
                     float floatValue21 = MathHelper.sin(floatValue2 * (float) Math.PI);
                     float floatValue28 = 1.0F - floatValue3;
                     matrixStack4.translate((float)intValue20 * 0.56F, -0.52F, -0.72F);
                     matrixStack4.translate((0.3F - floatValue21 * 0.15F) * (float)intValue20, 0.2F - floatValue28 * 0.12F, -0.15F - floatValue21 * 0.13F);
                     matrixStack4.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((76.0F - 10.0F * floatValue21) * (float)intValue20));
                     matrixStack4.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((-16.0F - 8.0F * floatValue21) * (float)intValue20));
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-83.0F - 26.0F * floatValue21));
                     break;
                  case "Slant":
                     float floatValue16 = (float)Math.sin((double)floatValue2 * (Math.PI / 2) * 2.0);
                     float floatValue17 = 35.0F * floatValue11;
                     matrixStack4.translate((float)intValue20 * 0.56F, -0.52F, -0.72F);
                     matrixStack4.translate(0.0F, 0.0F, -0.3F * floatValue16 * floatValue11);
                     matrixStack4.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue16 * -floatValue17));
                     matrixStack4.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue16 * floatValue17));
                     break;
                  default:
                     this.callSwingArm(heldItemRenderer1, floatValue2, floatValue3, matrixStack4, intValue5, arm6);
               }
            } else {
               this.callSwingArm(heldItemRenderer1, floatValue2, floatValue3, matrixStack4, intValue5, arm6);
            }
         }
      } else {
         this.callSwingArm(heldItemRenderer1, floatValue2, floatValue3, matrixStack4, intValue5, arm6);
      }
   }

   @Overwrite
   public void renderItem(float floatValue1, MatrixStack matrixStack2, Immediate immediate3, ClientPlayerEntity clientPlayerEntity4, int intValue5) {
      float floatValue6 = clientPlayerEntity4.getHandSwingProgress(floatValue1);
      Hand hand7 = (Hand)MoreObjects.firstNonNull(clientPlayerEntity4.preferredHand, Hand.MAIN_HAND);
      float floatValue8 = clientPlayerEntity4.getLerpedPitch(floatValue1);
      HandRenderType handRenderType9 = HeldItemRenderer.getHandRenderType(clientPlayerEntity4);
      float floatValue10 = MathHelper.lerp(floatValue1, clientPlayerEntity4.lastRenderPitch, clientPlayerEntity4.renderPitch);
      float floatValue11 = MathHelper.lerp(floatValue1, clientPlayerEntity4.lastRenderYaw, clientPlayerEntity4.renderYaw);
      if (handRenderType9.renderMainHand) {
         float floatValue12 = hand7 == Hand.MAIN_HAND ? floatValue6 : 0.0F;
         float floatValue13 = 1.0F - MathHelper.lerp(floatValue1, this.prevEquipProgressMainHand, this.equipProgressMainHand);
         this.renderFirstPersonItem(clientPlayerEntity4, floatValue1, floatValue8, Hand.MAIN_HAND, floatValue12, this.mainHand, floatValue13, matrixStack2, immediate3, intValue5);
      }

      if (handRenderType9.renderOffHand) {
         float floatValue14 = hand7 == Hand.OFF_HAND ? floatValue6 : 0.0F;
         float floatValue15 = 1.0F - MathHelper.lerp(floatValue1, this.prevEquipProgressOffHand, this.equipProgressOffHand);
         this.renderFirstPersonItem(clientPlayerEntity4, floatValue1, floatValue8, Hand.OFF_HAND, floatValue14, this.offHand, floatValue15, matrixStack2, immediate3, intValue5);
      }

      immediate3.draw();
   }

   @Inject(
      method = {"applyEatOrDrinkTransformation(Lnet/minecraft/client/util/math/MatrixStack;FLnet/minecraft/util/Arm;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/PlayerEntity;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onApplyEatOrDrinkTransformation(MatrixStack matrixStack1, float floatValue2, Arm arm3, ItemStack itemStack4, PlayerEntity playerEntity5, CallbackInfo callbackInfo6) {
      SwingAnimations swingAnimations7 = this.getTweaks();
      if (swingAnimations7 != null && swingAnimations7.isEnabled() && !swingAnimations7.g.getValue() && swingAnimations7.u.getValue() && playerEntity5.isUsingItem()) {
         this.applyEatOrDrinkTransformationCustom(matrixStack1, floatValue2, arm3, itemStack4);
         callbackInfo6.cancel();
      }
   }

   private void applyEatOrDrinkTransformationCustom(MatrixStack matrixStack1, float floatValue2, Arm arm3, ItemStack itemStack4) {
      if (MinecraftClient.getInstance().player != null) {
         float floatValue5 = (float)MinecraftClient.getInstance().player.getItemUseTimeLeft() - floatValue2 + 1.0F;
         float floatValue6 = floatValue5 / (float)itemStack4.getMaxUseTime(MinecraftClient.getInstance().player);
         if (floatValue6 < 0.8F) {
            float floatValue7 = MathHelper.abs(MathHelper.cos(floatValue5 / 4.0F * (float) Math.PI) * 0.005F);
            matrixStack1.translate(0.0F, floatValue7, 0.0F);
         }

         float floatValue13 = 1.0F - (float)Math.pow((double)floatValue6, 27.0);
         int intValue8 = arm3 == Arm.RIGHT ? 1 : -1;
         float floatValue9 = 0.0F;
         float floatValue10 = 0.0F;
         float floatValue11 = 0.0F;
         ViewModel viewModel12 = this.getViewModel();
         if (viewModel12 != null && viewModel12.isEnabled()) {
            if (arm3 == Arm.RIGHT) {
               floatValue9 = viewModel12.d.f();
               floatValue10 = viewModel12.e.f();
               floatValue11 = viewModel12.f.f();
            } else {
               floatValue9 = viewModel12.g.f();
               floatValue10 = viewModel12.h.f();
               floatValue11 = viewModel12.i.f();
            }
         }

         matrixStack1.translate(floatValue13 * 0.6F * (float)intValue8 + floatValue9, floatValue13 * -0.5F + floatValue10, floatValue11);
         matrixStack1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)intValue8 * floatValue13 * 90.0F));
         matrixStack1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue13 * 10.0F));
         matrixStack1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)intValue8 * floatValue13 * 30.0F));
      }
   }

   private void applyEquipOffset(MatrixStack matrixStack1, int intValue2, float floatValue3) {
      matrixStack1.translate((float)intValue2 * 0.56F, -0.52F + floatValue3 * -0.6F, -0.72F);
   }

   private void applySwingOffset(MatrixStack matrixStack1, int intValue2, float floatValue3, float floatValue4) {
      float floatValue5 = MathHelper.sin(floatValue3 * floatValue3 * (float) Math.PI);
      matrixStack1.translate(0.56F * (float)intValue2, -0.52F, -0.72F);
      matrixStack1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)intValue2 * (45.0F + floatValue5 * -20.0F * floatValue4)));
      float floatValue6 = MathHelper.sin(MathHelper.sqrt(floatValue3) * (float) Math.PI);
      matrixStack1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)intValue2 * floatValue6 * -20.0F * floatValue4));
      matrixStack1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue6 * -80.0F * floatValue4));
      matrixStack1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)intValue2 * -45.0F));
   }

   private void callSwingArm(HeldItemRenderer heldItemRenderer1, float floatValue2, float floatValue3, MatrixStack matrixStack4, int intValue5, Arm arm6) {
      ((HeldItemRendererInvoker)heldItemRenderer1).whylol$callSwingArm(floatValue2, floatValue3, matrixStack4, intValue5, arm6);
   }

   private SwingAnimations getTweaks() {
      return ModuleManager.a == null ? null : ModuleManager.av;
   }

   private ViewModel getViewModel() {
      return ModuleManager.a == null ? null : ModuleManager.aE;
   }

   private ShaderHands getShaderHands() {
      return ModuleManager.a == null ? null : ModuleManager.ar;
   }
}
