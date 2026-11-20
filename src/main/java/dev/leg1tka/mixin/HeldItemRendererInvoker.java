package dev.leg1tka.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({HeldItemRenderer.class})
public interface HeldItemRendererInvoker {
   @Accessor("mainHand")
   ItemStack whylol$getMainHand();

   @Accessor("offHand")
   ItemStack whylol$getOffHand();

   @Invoker("renderFirstPersonItem")
   void whylol$callRenderFirstPersonItem(
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

   @Invoker("applyEquipOffset")
   void whylol$applyEquipOffset(MatrixStack matrixStack1, Arm arm2, float floatValue3);

   @Invoker("swingArm")
   void whylol$callSwingArm(float floatValue1, float floatValue2, MatrixStack matrixStack3, int intValue4, Arm arm5);

   @Invoker("renderArmHoldingItem")
   void whylol$renderArmHoldingItem(MatrixStack matrixStack1, VertexConsumerProvider vertexConsumerProvider2, int intValue3, float floatValue4, float floatValue5, Arm arm6);

   @Invoker("renderMapInBothHands")
   void whylol$renderMapInBothHands(MatrixStack matrixStack1, VertexConsumerProvider vertexConsumerProvider2, int intValue3, float floatValue4, float floatValue5, float floatValue6);

   @Invoker("renderMapInOneHand")
   void whylol$renderMapInOneHand(MatrixStack matrixStack1, VertexConsumerProvider vertexConsumerProvider2, int intValue3, float floatValue4, Arm arm5, float floatValue6, ItemStack itemStack7);

   @Invoker("renderItem")
   void whylol$renderItem(
      LivingEntity livingEntity1, ItemStack itemStack2, ModelTransformationMode modelTransformationMode3, boolean flag4, MatrixStack matrixStack5, VertexConsumerProvider vertexConsumerProvider6, int intValue7
   );
}
