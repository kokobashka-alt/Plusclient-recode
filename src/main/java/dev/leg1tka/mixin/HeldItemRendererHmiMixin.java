package dev.leg1tka.mixin;

import dev.leg1tka.AspectDLC;
import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.modules.render.SwingAnimations;
import dev.leg1tka.modules.render.ViewModel;
import java.util.Random;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.ComparatorBlock;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.item.HeldItemRenderer.HandRenderType;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.LingeringPotionItem;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.item.SplashPotionItem;
import net.minecraft.item.consume.UseAction;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({HeldItemRenderer.class})
public abstract class HeldItemRendererHmiMixin {
   private boolean repPower = false;
   private float prevAge = 0.0F;
   private double previousRotation = 0.0;
   private float swingAngleY = 0.0F;
   private float swingAngleX = 0.0F;
   private float swingVelocityY = 0.0F;
   private float swingVelocityX = 0.0F;
   private float swingVelocityZ = 0.0F;
   private static final float GRAVITY = 0.1F;
   private static final float DAMPING = 0.88F;
   private static final float SENSITIVITY = 0.015F;
   private float vertAngleY = 0.0F;
   private float vertVelocityY = 0.0F;
   private float vertVelocityYSlime = 0.0F;
   private float vertAngleYSlime = 0.0F;
   private float riptideCounter = 0.0F;
   private float netherCounter = 0.0F;
   @Shadow
   private ItemStack mainHand;
   @Shadow
   @Final
   private MinecraftClient client;
   private float fallCounter = 0.0F;
   private float inWaterCounter = 0.0F;
   private float inspect = 0.0F;
   private float tilt = 0.0F;
   private float freezeCounter = 0.0F;
   private float clCount = 0.0F;
   private float crawlCount = 0.0F;
   private float directionalCrawlCount = 0.0F;
   private float climbCount = 0.0F;
   private float mouseHolding = 1.0F;
   private boolean isSwinging = false;
   private float swingProgress = 0.0F;
   private boolean isForward = false;
   private boolean isAttacking = false;
   private boolean left = false;
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

   public HeldItemRendererHmiMixin() {
   }

   private float easeInOutBack(float floatValue1) {
      float floatValue2 = 1.70158F;
      float floatValue3 = floatValue2 * 1.525F;
      return (float)(
         (double)floatValue1 < 0.5
            ? Math.pow((double)(2.0F * floatValue1), 2.0) * (double)((floatValue3 + 1.0F) * 2.0F * floatValue1 - floatValue3) / 2.0
            : (Math.pow((double)(2.0F * floatValue1 - 2.0F), 2.0) * (double)((floatValue3 + 1.0F) * (floatValue1 * 2.0F - 2.0F) + floatValue3) + 2.0) / 2.0
      );
   }

   private float getAttackDamage(ItemStack itemStack1) {
      AttributeModifiersComponent attributeModifiersComponent2 = (AttributeModifiersComponent)itemStack1.getComponents().get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
      if (attributeModifiersComponent2 == null) {
         return 0.0F;
      } else {
         float floatValue3 = 0.0F;

         for (Entry entry5 : attributeModifiersComponent2.modifiers()) {
            if (entry5.attribute().value() == EntityAttributes.ATTACK_DAMAGE.value()) {
               floatValue3 += (float)entry5.modifier().value();
            }
         }

         return floatValue3;
      }
   }

   private boolean isSharpAnimation(SwingAnimations swingAnimations1) {
      return swingAnimations1 != null && swingAnimations1.h.b("Шарп");
   }

   private void altSwing(MatrixStack matrixStack1, Arm arm2, float floatValue3, ItemStack itemStack4) {
      int intValue5 = arm2 == Arm.RIGHT ? 1 : -1;
      float floatValue6 = MathHelper.sin(floatValue3 * 3.14F);
      matrixStack1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)intValue5 * (45.0F + floatValue6 * 0.0F)));
      matrixStack1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)intValue5 * -45.0F));
   }

   @Inject(
      method = {"renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"},
      at = {@At("HEAD")},
      cancellable = true
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
      SwingAnimations swingAnimations12 = ModuleManager.av;
      if (swingAnimations12.isEnabled() && swingAnimations12.g.getValue()) {
         boolean flag13 = hand4 == Hand.MAIN_HAND;
         Arm arm14 = flag13 ? abstractClientPlayerEntity1.getMainArm() : abstractClientPlayerEntity1.getMainArm().getOpposite();
         float floatValue15 = flag13 ? 1.0F : -1.0F;
         if (swingAnimations12.s.getValue()) {
            arm14 = arm14.getOpposite();
            floatValue15 *= -1.0F;
         }

         this.renderCustomFirstPersonItem(abstractClientPlayerEntity1, floatValue2, floatValue3, hand4, arm14, floatValue15, floatValue5, itemStack6, floatValue7, matrixStack8, vertexConsumerProvider9, intValue10);
         callbackInfo11.cancel();
      }
   }

   private void renderCustomFirstPersonItem(
      AbstractClientPlayerEntity abstractClientPlayerEntity1,
      float floatValue2,
      float floatValue3,
      Hand hand4,
      Arm arm5,
      float floatValue6,
      float floatValue7,
      ItemStack itemStack8,
      float floatValue9,
      MatrixStack matrixStack10,
      VertexConsumerProvider vertexConsumerProvider11,
      int intValue12
   ) {
      SwingAnimations swingAnimations13 = ModuleManager.av;
      if (swingAnimations13.isEnabled() && swingAnimations13.g.getValue() && !abstractClientPlayerEntity1.isUsingSpyglass()) {
         SwingAnimations swingAnimations14 = ModuleManager.av;
         float floatValue15 = abstractClientPlayerEntity1.getYaw();
         double doubleValue16 = Math.toRadians((double)floatValue15);
         double doubleValue18 = -Math.sin(doubleValue16);
         double doubleValue20 = Math.cos(doubleValue16);
         Vec3d vec3d22 = abstractClientPlayerEntity1.getVelocity();
         double doubleValue23 = vec3d22.x * doubleValue18 + vec3d22.z * doubleValue20;
         double doubleValue25 = abstractClientPlayerEntity1.getVelocity().getHorizontal().x * doubleValue20 - vec3d22.z * doubleValue18;
         float floatValue27;
         if (abstractClientPlayerEntity1.getPitch() != 0.0F) {
            floatValue27 = 90.0F / abstractClientPlayerEntity1.getPitch() / 10.0F;
         } else {
            floatValue27 = 1.0F;
         }

         if (floatValue27 > 1.0F) {
            floatValue27 = 1.0F;
         }

         if (floatValue27 < 0.0F) {
            floatValue27 = 1.0F;
         }

         boolean flag28 = hand4 == Hand.MAIN_HAND;
         matrixStack10.push();
         matrixStack10.push();
         ViewModel viewModel29 = ModuleManager.a != null ? ModuleManager.aE : null;
         if (viewModel29 != null && viewModel29.isEnabled()) {
            viewModel29.a(matrixStack10, arm5);
         }

         double doubleValue30 = AspectDLC.deltaTime * 30.0;
         float floatValue32 = MathHelper.clamp(swingAnimations14.i.f(), 0.35F, 2.5F);
         float floatValue33 = (float)Math.pow((double)MathHelper.clamp(floatValue7, 0.0F, 1.0F), (double)floatValue32);
         float floatValue34 = (double)floatValue33 < 0.6
            ? MathHelper.sin(MathHelper.clamp(floatValue33, 0.0F, 0.12506F) * 12.56F)
            : MathHelper.sin(MathHelper.clamp(floatValue33, 0.62532F, 0.75038F) * 12.56F);
         float floatValue35 = MathHelper.sin(floatValue33 * 3.14F);
         floatValue35 = this.easeInOutBack(floatValue35);
         boolean flag36 = itemStack8.isIn(ItemTags.SWORDS) && this.isSharpAnimation(swingAnimations14);
         if ((
               itemStack8.isOf(Items.EXPERIENCE_BOTTLE)
                  || itemStack8.isOf(Items.WIND_CHARGE)
                  || itemStack8.isOf(Items.EGG)
                  || itemStack8.isOf(Items.ENDER_EYE)
                  || itemStack8.isOf(Items.SNOWBALL)
                  || itemStack8.getItem() instanceof SplashPotionItem
                  || itemStack8.getItem() instanceof LingeringPotionItem
            )
            && abstractClientPlayerEntity1.getOffHandStack().isEmpty()
            && itemStack8.getUseAction() != UseAction.SPEAR
            && !itemStack8.isOf(Items.FIRE_CHARGE)
            && !abstractClientPlayerEntity1.isSwimming()
            && !abstractClientPlayerEntity1.isCrawling()
            && !abstractClientPlayerEntity1.isClimbing()) {
            if (abstractClientPlayerEntity1.getMainArm() == Arm.LEFT) {
               flag28 = !flag28;
            }

            matrixStack10.push();
            matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-25.0F * floatValue6));
            matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-10.0F));
            matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(25.0F * floatValue6 * floatValue35));
            matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30.0F * floatValue35));
            matrixStack10.translate(-0.15 * (double)floatValue6, 0.1, 0.1);
            matrixStack10.translate(0.0, -0.55 * (double)floatValue35, 0.4 * (double)floatValue35 * 3.14F);
            HeldItemRendererAccessor heldItemRendererAccessor37 = (HeldItemRendererAccessor)this;
            heldItemRendererAccessor37.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, floatValue9, 0.0F, arm5.getOpposite());
            matrixStack10.pop();
         }

         if (this.client.options.attackKey.isPressed() && !this.isAttacking && (double)floatValue7 == 0.0) {
            this.left = !this.left;
         }

         if (!itemStack8.isEmpty()) {
            if (abstractClientPlayerEntity1.getMainArm() == Arm.LEFT) {
               flag28 = !flag28;
            }

            if ((this.left || itemStack8.isIn(ItemTags.AXES) || itemStack8.getUseAction() == UseAction.SPEAR || itemStack8.getUseAction() == UseAction.BLOCK)
               && !itemStack8.isIn(ItemTags.SHOVELS)) {
               if (flag36) {
                  matrixStack10.translate(0.1 * (double)floatValue6 * (double)floatValue34, 0.1 * (double)floatValue34, -0.5 * (double)floatValue35);
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-30.0F * floatValue34));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-20.0F * floatValue34 * floatValue6));
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(40.0F * floatValue35));
               } else if (itemStack8.isIn(ItemTags.SWORDS) || itemStack8.isIn(ItemTags.AXES)) {
                  matrixStack10.translate(0.8 * (double)floatValue6 * (double)floatValue34, 0.3 * (double)floatValue34, -0.5 * (double)floatValue35);
                  matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(15.0F * floatValue34 * floatValue6));
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-20.0F * floatValue34));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-70.0F * floatValue34 * floatValue6));
                  if (itemStack8.isIn(ItemTags.SWORDS)) {
                     matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(40.0F * floatValue35));
                  } else {
                     matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(30.0F * floatValue35));
                  }
               } else if (itemStack8.getUseAction() == UseAction.SPEAR) {
                  matrixStack10.translate(0.0, 0.0, 0.45 * (double)floatValue34);
                  matrixStack10.translate(-0.25 * (double)floatValue6 * (double)floatValue35, -0.35 * (double)floatValue34, -0.6 * (double)floatValue35);
                  matrixStack10.translate(0.0, 0.1 * (double)floatValue35, 0.0);
                  matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(15.0F * floatValue34 * floatValue6));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(30.0F * floatValue34 * floatValue6));
               } else if (itemStack8.isIn(ConventionalItemTags.TOOLS) && itemStack8.getUseAction() != UseAction.BLOCK && !itemStack8.isIn(ItemTags.SHOVELS)) {
                  matrixStack10.translate(0.1 * (double)floatValue6 * (double)floatValue34, 0.1 * (double)floatValue34, -0.5 * (double)floatValue35);
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-30.0F * floatValue34));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-20.0F * floatValue34 * floatValue6));
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(40.0F * floatValue35));
               } else if (itemStack8.getUseAction() != UseAction.BLOCK) {
                  matrixStack10.translate(0.1 * (double)floatValue6 * (double)floatValue34, 0.1 * (double)floatValue34, -0.1 * (double)floatValue35);
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-30.0F * floatValue34));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-10.0F * floatValue34 * floatValue6));
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(40.0F * floatValue35));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(10.0F * floatValue35 * floatValue6));
               } else {
                  matrixStack10.translate(0.1 * (double)floatValue6 * (double)floatValue34, 0.1 * (double)floatValue34, -0.2 * (double)floatValue35);
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-10.0F * floatValue34));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-10.0F * floatValue34 * floatValue6));
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(20.0F * floatValue35));
               }
            } else if (!itemStack8.isIn(ItemTags.SHOVELS)) {
               if (flag36) {
                  matrixStack10.translate(0.1 * (double)floatValue6 * (double)floatValue34, 0.1 * (double)floatValue34, -0.5 * (double)floatValue35);
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-30.0F * floatValue34));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-20.0F * floatValue34 * floatValue6));
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(40.0F * floatValue35));
               } else if (itemStack8.isIn(ItemTags.SWORDS)) {
                  matrixStack10.translate(-0.55 * (double)floatValue6 * (double)floatValue34, -0.8 * (double)floatValue34, -0.77 * (double)floatValue35);
                  matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(5.0F * floatValue34 * floatValue6));
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-30.0F * floatValue34));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(70.0F * floatValue34 * floatValue6));
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(50.0F * floatValue35));
               } else if (itemStack8.isIn(ConventionalItemTags.TOOLS) && !itemStack8.isIn(ItemTags.SHOVELS)) {
                  matrixStack10.translate(0.1 * (double)floatValue6 * (double)floatValue34, 0.1 * (double)floatValue34, -0.5 * (double)floatValue35);
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-30.0F * floatValue34));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-20.0F * floatValue34 * floatValue6));
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(40.0F * floatValue35));
               } else {
                  matrixStack10.translate(0.1 * (double)floatValue6 * (double)floatValue34, 0.1 * (double)floatValue34, -0.1 * (double)floatValue35);
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(-30.0F * floatValue34));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-10.0F * floatValue34 * floatValue6));
                  matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(40.0F * floatValue35));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(10.0F * floatValue35 * floatValue6));
               }
            } else if (itemStack8.isIn(ItemTags.SHOVELS)) {
               matrixStack10.translate(0.0, 0.15 * (double)floatValue34, -0.25 * (double)floatValue34);
               matrixStack10.translate(0.0, 0.0, -0.2 * (double)floatValue35);
               matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(15.0F * floatValue34));
               matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-35.0F * floatValue34));
               matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30.0F * floatValue35));
            }
         } else if (Block.getBlockFromItem(itemStack8.getItem()) != Blocks.AIR
            && (
               !itemStack8.isIn(ConventionalItemTags.TOOLS)
                  || itemStack8.isIn(ItemTags.TRIMMABLE_ARMOR)
                  || itemStack8.isIn(ItemTags.BOOKSHELF_BOOKS)
                  || itemStack8.getUseAction() == UseAction.EAT
                  || !itemStack8.isEnchantable()
            )
            && itemStack8.getUseAction() != UseAction.BOW
            && itemStack8.getUseAction() != UseAction.SPYGLASS
            && this.getAttackDamage(itemStack8) == 0.0F
            && itemStack8.getUseAction() != UseAction.BLOCK
            && !itemStack8.isOf(Items.WARPED_FUNGUS_ON_A_STICK)
            && !itemStack8.isOf(Items.CARROT_ON_A_STICK)
            && !itemStack8.isOf(Items.FISHING_ROD)
            && !itemStack8.isOf(Items.SHEARS)) {
            floatValue7 = (float)((double)floatValue7 * 1.2);
            if (floatValue7 > 1.0F) {
               floatValue7 = 0.0F;
            }
         } else if (!itemStack8.isIn(ItemTags.SHOVELS)) {
            floatValue7 = (float)((double)floatValue7 * 1.5);
            if (floatValue7 > 1.0F) {
               floatValue7 = 0.0F;
            }
         }

         if (abstractClientPlayerEntity1.getVelocity().length() >= 0.08) {
            this.crawlCount = (float)((double)this.crawlCount + 0.1 * abstractClientPlayerEntity1.getVelocity().length() * 2.0 * doubleValue30);
            this.directionalCrawlCount = (float)((double)this.directionalCrawlCount + 0.1 * doubleValue23 * 4.0 * doubleValue30);
            this.directionalCrawlCount = (float)(
               (double)this.directionalCrawlCount + (doubleValue23 > 0.0 ? 0.1 * Math.abs(doubleValue25) * 4.0 * doubleValue30 : 0.1 * Math.abs(doubleValue25) * -1.0 * 4.0 * doubleValue30)
            );
         }

         if (abstractClientPlayerEntity1.getVelocity().getY() > 0.0) {
            this.climbCount = (float)((double)this.climbCount + 0.1 * doubleValue30);
         }

         if (abstractClientPlayerEntity1.getVelocity().getY() < 0.0) {
            this.climbCount = (float)((double)this.climbCount - 0.1 * doubleValue30);
         }

         if ((abstractClientPlayerEntity1.isCrawling() && swingAnimations14.e || abstractClientPlayerEntity1.isClimbing() && !abstractClientPlayerEntity1.isOnGround() && Math.abs(abstractClientPlayerEntity1.getVelocity().getY()) > 0.0 && swingAnimations14.e)
            && !abstractClientPlayerEntity1.isUsingItem()
            && floatValue7 == 0.0F) {
            this.clCount = (float)((double)this.clCount + 0.1 * doubleValue30);
            if (this.clCount > 1.0F) {
               this.clCount = 1.0F;
            }

            if (!itemStack8.isOf(Items.LANTERN) && !itemStack8.isOf(Items.SOUL_LANTERN)) {
               matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-20.0F * this.clCount));
            }
         } else {
            this.clCount = (float)((double)this.clCount * Math.pow(0.88F, doubleValue30));
         }

         if (floatValue7 == 0.0F) {
            matrixStack10.translate(flag28 ? abstractClientPlayerEntity1.getPitch() / 650.0F * this.clCount * -1.0F : abstractClientPlayerEntity1.getPitch() / 650.0F * this.clCount, 0.0F, 0.0F);
            matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(abstractClientPlayerEntity1.getPitch() * this.clCount));
         }

         if (!itemStack8.isOf(Items.LANTERN) && !itemStack8.isOf(Items.SOUL_LANTERN)) {
            matrixStack10.translate(0.0F, 0.0F, abstractClientPlayerEntity1.getPitch() / 120.0F * this.clCount);
         } else if (floatValue7 == 0.0F) {
            matrixStack10.translate(0.0F, 0.0F, abstractClientPlayerEntity1.getPitch() / 80.0F * this.clCount);
         }

         if (abstractClientPlayerEntity1.isClimbing() && swingAnimations14.e && !abstractClientPlayerEntity1.isOnGround() && !itemStack8.isOf(Items.LANTERN) && !itemStack8.isOf(Items.SOUL_LANTERN) && !abstractClientPlayerEntity1.isUsingItem()) {
            matrixStack10.translate(0.0, 0.1, -0.2);
         }

         if ((abstractClientPlayerEntity1.isInFluid() || abstractClientPlayerEntity1.inPowderSnow) && !abstractClientPlayerEntity1.isSwimming() && !abstractClientPlayerEntity1.isSubmergedInWater()) {
            this.inWaterCounter = (float)((double)this.inWaterCounter + 0.1 * doubleValue30);
            if (this.inWaterCounter >= 1.0F) {
               this.inWaterCounter = 1.0F;
            }
         } else {
            this.inWaterCounter = (float)((double)this.inWaterCounter * Math.pow(0.88F, doubleValue30));
         }

         if (abstractClientPlayerEntity1.inPowderSnow && (double)abstractClientPlayerEntity1.getFreezingScale() > 0.1) {
            this.freezeCounter = (float)((double)this.freezeCounter + 0.1 * doubleValue30);
         } else {
            this.freezeCounter = (float)((double)this.freezeCounter * Math.pow(0.88F, doubleValue30));
         }

         matrixStack10.translate(0.0, 0.02 * (double)this.inWaterCounter, 0.0);
         matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(8.0F * floatValue6 * this.inWaterCounter));
         matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(0.3F * MathHelper.sin(this.freezeCounter * 5.0F)));
         if (abstractClientPlayerEntity1.getVelocity().getY() < -0.85 && itemStack8.isOf(Items.MACE) && abstractClientPlayerEntity1.getMainHandStack() == itemStack8) {
            this.fallCounter = (float)((double)this.fallCounter + 0.1 * doubleValue30);
            if (this.fallCounter >= 1.0F) {
               this.fallCounter = 1.0F;
            }
         } else {
            this.fallCounter = (float)((double)this.fallCounter * Math.pow(0.88F, doubleValue30));
         }

         if (flag28) {
            matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(45.0F * this.fallCounter));
            matrixStack10.translate(0.0, -0.2 * (double)this.fallCounter, 0.0);
         }

         this.vertAngleY = (float)((double)this.vertAngleY + abstractClientPlayerEntity1.getVelocity().getY() * 0.015F * doubleValue30);
         this.vertAngleY = (float)((double)this.vertAngleY - (double)(0.1F * this.vertAngleY) * doubleValue30);
         this.vertAngleY = (float)((double)this.vertAngleY * Math.pow(0.88F, doubleValue30));
         this.vertVelocityYSlime = (float)((double)this.vertVelocityYSlime + abstractClientPlayerEntity1.getVelocity().getY() * 0.015F * doubleValue30);
         this.vertVelocityYSlime = (float)((double)this.vertVelocityYSlime - (double)(0.1F * this.vertAngleYSlime) * doubleValue30);
         this.vertVelocityYSlime = (float)((double)this.vertVelocityYSlime * Math.pow(0.88F, doubleValue30));
         this.vertAngleYSlime = (float)((double)this.vertAngleYSlime + (double)this.vertVelocityYSlime * doubleValue30);
         matrixStack10.translate(0.0F, this.vertAngleY * -1.0F, 0.0F);
         matrixStack10.translate(0.0, Math.sin((double)abstractClientPlayerEntity1.age * 0.1) * 0.007 * (double)floatValue6, 0.0);
         matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0.15F * MathHelper.sin((float)abstractClientPlayerEntity1.age * 0.15F) * floatValue6));
         if (!itemStack8.isEmpty() || abstractClientPlayerEntity1.isCrawling() || abstractClientPlayerEntity1.isClimbing() && !abstractClientPlayerEntity1.isOnGround() || abstractClientPlayerEntity1.isSwimming()) {
            if (abstractClientPlayerEntity1.getMainArm() == Arm.LEFT) {
               flag28 = !flag28;
            }

            if (itemStack8.getUseAction() == UseAction.BLOCK) {
               matrixStack10.translate(0.0F, 0.0F, 0.0F);
            } else {
               matrixStack10.translate(0.0, -0.1, 0.1);
            }
         }

         if (itemStack8.isOf(Items.LANTERN) || itemStack8.isOf(Items.SOUL_LANTERN) || itemStack8.isIn(ItemTags.HANGING_SIGNS)) {
            matrixStack10.translate(0.0, 0.1, 0.0);
            if (abstractClientPlayerEntity1.isSwimming()) {
               matrixStack10.translate(0.0, -0.1, 0.1);
            }
         }

         if (abstractClientPlayerEntity1.isSwimming() && floatValue7 == 0.0F && swingAnimations14.d) {
            double doubleValue65 = (double)this.crawlCount;
            double doubleValue39 = 1.5;
            double doubleValue41 = 2.0;
            double doubleValue43 = doubleValue65 * doubleValue41;
            double doubleValue45 = Math.sin(doubleValue43) * doubleValue39;
            double doubleValue47 = doubleValue45 * 0.8 + this.previousRotation * 0.2;
            matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(flag28 ? doubleValue47 : -doubleValue47)));
            matrixStack10.translate(0.0, 0.0, doubleValue47 * 0.2F);
            double doubleValue49 = (double)(this.crawlCount * 2.0F);
            double doubleValue51 = Math.cos(doubleValue49);
            double doubleValue53 = doubleValue51;
            if (doubleValue51 <= 0.0) {
               doubleValue53 = doubleValue51 * 0.5;
            }

            matrixStack10.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float)(flag28 ? doubleValue53 * 30.0 : doubleValue53 * 30.0 * -1.0)));
            matrixStack10.translate(0.0, 0.0, doubleValue51 * 0.2F);
            if (itemStack8.isEmpty() && !flag28 && !abstractClientPlayerEntity1.isInvisible()) {
               matrixStack10.translate((double)(1.0F * floatValue6), 0.0 - (double)floatValue9 * 0.3, 0.3);
               matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0F * floatValue6));
               matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-40.0F * floatValue6));
               matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30.0F));
               this.altSwing(matrixStack10, arm5, floatValue7, itemStack8);
               float floatValue55 = MathHelper.sin(floatValue9 * 3.14F);
               matrixStack10.scale(0.9F, 0.9F, 0.9F);
               HeldItemRendererAccessor heldItemRendererAccessor56 = (HeldItemRendererAccessor)this;
               heldItemRendererAccessor56.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, 0.0F, 0.0F, arm5);
            }

            this.previousRotation = doubleValue47;
         }

         if ((abstractClientPlayerEntity1.isClimbing() && !abstractClientPlayerEntity1.isOnGround() || abstractClientPlayerEntity1.isCrawling() && floatValue7 == 0.0F) && !abstractClientPlayerEntity1.isUsingItem()) {
            double doubleValue66 = (double)this.climbCount;
            float floatValue75 = (float)abstractClientPlayerEntity1.getVelocity().getY();
            float floatValue40 = MathHelper.cos((float)doubleValue66 * 2.0F);
            if (abstractClientPlayerEntity1.isClimbing()) {
               if (!itemStack8.isOf(Items.LANTERN) && !itemStack8.isOf(Items.SOUL_LANTERN)) {
                  matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(20.0F * floatValue40 * floatValue6));
               } else {
                  matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(1.0F * floatValue40 * floatValue6));
               }
            }

            if (abstractClientPlayerEntity1.isCrawling() && !abstractClientPlayerEntity1.isUsingItem() && floatValue7 == 0.0F) {
               float floatValue93 = MathHelper.sin(this.directionalCrawlCount * 4.0F * this.mouseHolding);
               float floatValue42 = MathHelper.cos(this.directionalCrawlCount * 4.0F * this.mouseHolding);
               if (itemStack8.isOf(Items.LANTERN) || itemStack8.isOf(Items.SOUL_LANTERN)) {
                  floatValue93 *= 0.14F;
                  floatValue42 *= 0.14F;
               }

               matrixStack10.translate(0.2 * (double)floatValue93, 0.3 * (double)floatValue93 * (double)floatValue6, -0.2 * (double)floatValue93 * (double)floatValue6 * (double)floatValue27);
               matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(25.0F * floatValue93));
               matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.clamp(20.0F * floatValue42 * floatValue6, 0.0F, 20.0F)));
            }

            if (itemStack8.isEmpty() && !flag28 && !abstractClientPlayerEntity1.isInvisible() && (!abstractClientPlayerEntity1.isOnGround() && abstractClientPlayerEntity1.isClimbing() || abstractClientPlayerEntity1.isCrawling())) {
               matrixStack10.translate((double)(1.0F * floatValue6), 0.0 - (double)floatValue9 * 0.3, 0.3);
               matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0F * floatValue6));
               matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-40.0F * floatValue6));
               matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30.0F));
               this.altSwing(matrixStack10, arm5, floatValue7, itemStack8);
               matrixStack10.scale(0.9F, 0.9F, 0.9F);
               HeldItemRendererAccessor heldItemRendererAccessor94 = (HeldItemRendererAccessor)this;
               heldItemRendererAccessor94.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, 0.0F, 0.0F, arm5);
            }
         }

         if (itemStack8.isEmpty()) {
            if (flag28 && !abstractClientPlayerEntity1.isInvisible()) {
               if ((abstractClientPlayerEntity1.isOnGround() || !abstractClientPlayerEntity1.isClimbing()) && !abstractClientPlayerEntity1.isSwimming() && !abstractClientPlayerEntity1.isCrawling()) {
                  if (abstractClientPlayerEntity1.getMainArm() == Arm.LEFT) {
                     flag28 = !flag28;
                  }

                  matrixStack10.translate(0.0, 0.2 * (double)floatValue34, 0.15 * (double)floatValue34);
                  matrixStack10.translate(0.1 * (double)floatValue6 * (double)floatValue35, 0.15 * (double)floatValue35, -0.45 * (double)floatValue35);
                  matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(35.0F * floatValue35 * floatValue6));
                  matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-30.0F * floatValue35));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-10.0F * floatValue34 * floatValue6));
                  matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(10.0F * floatValue34));
                  HeldItemRendererAccessor heldItemRendererAccessor72 = (HeldItemRendererAccessor)this;
                  heldItemRendererAccessor72.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, 0.0F, 0.0F, arm5);
               } else {
                  matrixStack10.translate((double)(1.0F * floatValue6), 0.0 - (double)floatValue9 * 0.3, 0.3);
                  matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0F * floatValue6));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-40.0F * floatValue6));
                  matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30.0F));
                  this.altSwing(matrixStack10, arm5, floatValue7, itemStack8);
                  float floatValue71 = MathHelper.sin(floatValue9 * 3.14F);
                  matrixStack10.scale(0.9F, 0.9F, 0.9F);
                  HeldItemRendererAccessor heldItemRendererAccessor74 = (HeldItemRendererAccessor)this;
                  heldItemRendererAccessor74.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, 0.0F, 0.0F, arm5);
               }
            }
         } else if (itemStack8.contains(DataComponentTypes.MAP_ID)) {
            if (flag28 && this.mainHand.isEmpty()) {
               matrixStack10.translate(0.0, 0.1, 0.0);
               HeldItemRendererAccessor heldItemRendererAccessor70 = (HeldItemRendererAccessor)this;
               heldItemRendererAccessor70.invokeRenderMapInBothHands(matrixStack10, vertexConsumerProvider11, intValue12, floatValue3, floatValue9, floatValue7);
            } else {
               matrixStack10.translate(flag28 ? -0.1 : 0.1, 0.1, 0.0);
               HeldItemRendererAccessor heldItemRendererAccessor69 = (HeldItemRendererAccessor)this;
               heldItemRendererAccessor69.invokeRenderMapInOneHand(matrixStack10, vertexConsumerProvider11, intValue12, floatValue9, arm5, floatValue7, itemStack8);
            }
         } else if (itemStack8.getUseAction() == UseAction.CROSSBOW) {
            matrixStack10.push();
            boolean flag68 = CrossbowItem.isCharged(itemStack8);
            boolean flag73 = arm5 == Arm.RIGHT;
            int intValue84 = flag73 ? 1 : -1;
            if (abstractClientPlayerEntity1.isUsingItem() && abstractClientPlayerEntity1.getItemUseTimeLeft() > 0 && abstractClientPlayerEntity1.getActiveHand() == hand4) {
               HeldItemRendererAccessor heldItemRendererAccessor91 = (HeldItemRendererAccessor)this;
               heldItemRendererAccessor91.invokeApplyEquipOffset(matrixStack10, arm5, floatValue9);
               matrixStack10.translate((float)intValue84 * -0.4785682F, -0.24387F, 0.05731531F);
               matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-11.935F));
               matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)intValue84 * 65.3F));
               matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)intValue84 * 9.785F));
               float floatValue101 = (float)itemStack8.getMaxUseTime(abstractClientPlayerEntity1) - ((float)abstractClientPlayerEntity1.getItemUseTimeLeft() - floatValue2 + 1.0F);
               float floatValue108 = floatValue101 / (float)CrossbowItem.getPullTime(itemStack8, abstractClientPlayerEntity1);
               if (floatValue108 > 1.0F) {
                  floatValue108 = 1.0F;
               }

               if (floatValue108 > 0.1F) {
                  float floatValue112 = MathHelper.sin((floatValue101 - 0.1F) * 1.3F);
                  float floatValue115 = floatValue108 - 0.1F;
                  float floatValue119 = floatValue112 * floatValue115;
                  matrixStack10.translate(floatValue119 * 0.0F, floatValue119 * 0.004F, floatValue119 * 0.0F);
               }

               matrixStack10.translate(floatValue108 * 0.0F, floatValue108 * 0.0F, floatValue108 * 0.04F);
               matrixStack10.scale(1.0F, 1.0F, 1.0F);
               matrixStack10.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float)intValue84 * 45.0F));
            } else {
               ((HeldItemRendererAccessor)this).invokeSwingArm(floatValue7, floatValue9, matrixStack10, intValue84, arm5);
               if (flag68 && floatValue7 < 0.001F && flag28) {
                  matrixStack10.translate((float)intValue84 * -0.341864F, 0.0F, 0.0F);
                  matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)intValue84 * 10.0F));
               }
            }

            matrixStack10.translate(0.0F, 0.0F, -1.0F);
            matrixStack10.translate(-0.45 * (double)intValue84, 0.45, 1.7);
            matrixStack10.translate((double)(1.0F * floatValue6), 0.0 - (double)floatValue9 * 0.3, 0.3);
            matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0F * floatValue6));
            matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-40.0F * floatValue6));
            matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30.0F));
            this.altSwing(matrixStack10, arm5, floatValue7, itemStack8);
            float floatValue92 = MathHelper.sin(floatValue9 * 3.14F);
            matrixStack10.scale(0.9F, 0.9F, 0.9F);
            HeldItemRendererAccessor heldItemRendererAccessor102 = (HeldItemRendererAccessor)this;
            heldItemRendererAccessor102.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, 0.0F, 0.0F, arm5);
            matrixStack10.translate(-0.25 * (double)intValue84, 1.25, 0.05);
            matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(-90 * intValue84)));
            matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(77.0F));
            matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(85 * intValue84)));
            matrixStack10.scale(1.2F, 1.2F, 1.2F);
            matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-10.0F));
            matrixStack10.translate(0.0, -0.15, 0.15);
            heldItemRendererAccessor102.invokeRenderItem(
               abstractClientPlayerEntity1,
               itemStack8,
               flag73 ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND,
               !flag73,
               matrixStack10,
               vertexConsumerProvider11,
               intValue12
            );
            matrixStack10.pop();
            if (abstractClientPlayerEntity1.isUsingItem() && abstractClientPlayerEntity1.getItemUseTimeLeft() > 0 && abstractClientPlayerEntity1.getActiveHand() == hand4) {
               float floatValue109 = (float)itemStack8.getMaxUseTime(abstractClientPlayerEntity1) - ((float)abstractClientPlayerEntity1.getItemUseTimeLeft() - floatValue2 + 1.0F);
               float floatValue113 = floatValue109 / (float)CrossbowItem.getPullTime(itemStack8, abstractClientPlayerEntity1);
               if (floatValue113 > 1.0F) {
                  floatValue113 = 1.0F;
               }

               if (floatValue113 > 0.1F) {
                  float floatValue116 = MathHelper.sin((floatValue109 - 0.1F) * 1.3F);
                  float floatValue120 = floatValue113 - 0.1F;
                  float floatValue121 = floatValue116 * floatValue120;
                  matrixStack10.translate(floatValue121 * 0.0F, floatValue121 * 0.004F, floatValue121 * 0.0F);
               }

               matrixStack10.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((double)floatValue113 <= 0.2 ? 75.0F * floatValue113 * 5.0F * (float)intValue84 : (float)(75 * intValue84)));
               matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(10.0F * floatValue113 * 1.5F));
               matrixStack10.translate(-0.37 * (double)intValue84, 0.0, 0.6);
               matrixStack10.translate(0.15 * (double)floatValue113 * (double)intValue84, 0.0, 0.0);
               heldItemRendererAccessor102.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, floatValue9, floatValue7, arm5.getOpposite());
            }
         } else {
            boolean flag67 = arm5 == Arm.RIGHT;
            int intValue38 = flag67 ? 1 : -1;
            if (abstractClientPlayerEntity1.isUsingItem() && abstractClientPlayerEntity1.getItemUseTimeLeft() > 0 && abstractClientPlayerEntity1.getActiveHand() == hand4) {
               switch (itemStack8.getUseAction()) {
                  case NONE:
                     HeldItemRendererAccessor heldItemRendererAccessor78 = (HeldItemRendererAccessor)this;
                     heldItemRendererAccessor78.invokeApplyEquipOffset(matrixStack10, arm5, floatValue9);
                     break;
                  case EAT:
                  case DRINK:
                     float floatValue86 = (float)itemStack8.getMaxUseTime(abstractClientPlayerEntity1) - ((float)abstractClientPlayerEntity1.getItemUseTimeLeft() - floatValue2 + 1.0F);
                     float floatValue97 = floatValue86 / 5.0F;
                     if (floatValue97 > 1.0F) {
                        floatValue97 = 1.0F;
                     }

                     float floatValue104 = MathHelper.sin(floatValue86 / 2.0F * 3.14F);
                     floatValue104 /= 10.0F;
                     matrixStack10.translate((double)(1 * intValue38), 0.1, 0.3);
                     matrixStack10.translate(0.2 * (double)intValue38 * (double)floatValue97, -0.7 * (double)floatValue97, -0.2 * (double)floatValue97);
                     matrixStack10.translate(0.0, -0.2 * (double)floatValue104, -0.2 * (double)floatValue104);
                     matrixStack10.translate(0.0, 0.1 * (double)this.easeInOutBack(MathHelper.sin(floatValue97 * 3.14F)), 0.0);
                     matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(45 * intValue38)));
                     matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(-40 * intValue38)));
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30.0F));
                     this.altSwing(matrixStack10, arm5, floatValue7, itemStack8);
                     float floatValue111 = MathHelper.sin(floatValue9 * 3.14F);
                     matrixStack10.scale(0.9F, 0.9F, 0.9F);
                     matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0F * floatValue97 * (float)intValue38));
                     HeldItemRendererAccessor heldItemRendererAccessor44 = (HeldItemRendererAccessor)this;
                     heldItemRendererAccessor44.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, 0.0F, floatValue7, arm5);
                     break;
                  case BLOCK:
                     float floatValue117 = (float)itemStack8.getMaxUseTime(abstractClientPlayerEntity1) - ((float)abstractClientPlayerEntity1.getItemUseTimeLeft() - floatValue2 + 1.0F);
                     float floatValue46 = floatValue117 / 4.0F;
                     float floatValue122 = floatValue117 / 6.0F;
                     if (floatValue46 > 1.0F) {
                        floatValue46 = 1.0F;
                     }

                     if (floatValue122 > 1.0F) {
                        floatValue122 = 1.0F;
                     }

                     matrixStack10.translate(0.0, -0.2, 0.0);
                     matrixStack10.translate((double)(1 * intValue38), 0.0, 0.3);
                     matrixStack10.translate(0.7 * (double)floatValue46 * (double)intValue38, 0.0, -1.3 * (double)floatValue46);
                     matrixStack10.translate(-0.2 * (double)intValue38 * (double)floatValue122, 0.0, 0.0);
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float)(10.0 * Math.sin((double)floatValue122 * 3.14))));
                     matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(70.0F * floatValue46 * (float)intValue38));
                     matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(45 * intValue38)));
                     matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(-40 * intValue38)));
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30.0F));
                     matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(5 * intValue38) * floatValue46));
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-10.0F * floatValue46));
                     matrixStack10.translate(0.0, 0.0, -0.2 * (double)floatValue46);
                     this.altSwing(matrixStack10, arm5, floatValue7, itemStack8);
                     matrixStack10.scale(0.9F, 0.9F, 0.9F);
                     HeldItemRendererAccessor heldItemRendererAccessor48 = (HeldItemRendererAccessor)this;
                     heldItemRendererAccessor48.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, 0.0F, floatValue7, arm5);
                     matrixStack10.translate(0.35 * (double)intValue38, -0.13, -0.12);
                     matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(10.0F * (float)intValue38));
                     matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(10.0F * (float)intValue38));
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(0.0F));
                     matrixStack10.translate(-0.2 * (double)intValue38, -0.04, 0.15);
                     matrixStack10.scale(1.0F, 1.0F, 1.0F);
                     break;
                  case BOW:
                     matrixStack10.push();
                     if (abstractClientPlayerEntity1.getMainArm() == Arm.LEFT) {
                        flag28 = !flag28;
                     }

                     float floatValue123 = (float)itemStack8.getMaxUseTime(abstractClientPlayerEntity1) - ((float)abstractClientPlayerEntity1.getItemUseTimeLeft() - floatValue2 + 1.0F);
                     float floatValue50 = floatValue123 / 20.0F;
                     float floatValue125 = (floatValue50 * floatValue50 + floatValue50 * 2.0F) / 3.0F;
                     if (floatValue50 > 1.0F) {
                        floatValue50 = 1.0F;
                     }

                     if (floatValue50 > 0.1F) {
                        float floatValue52 = MathHelper.sin((floatValue123 - 0.1F) * 1.3F);
                        float floatValue129 = floatValue52 * floatValue50;
                        matrixStack10.translate(floatValue129 * 0.0F, floatValue129 * 0.004F, floatValue129 * 0.0F);
                     }

                     matrixStack10.translate(flag28 ? -0.1 : 0.1, 0.0, (double)floatValue50 * 0.15);
                     HeldItemRendererAccessor heldItemRendererAccessor126 = (HeldItemRendererAccessor)this;
                     heldItemRendererAccessor126.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, floatValue9, floatValue7, arm5);
                     matrixStack10.pop();
                     matrixStack10.translate(flag28 ? -0.5 : 0.5, -0.45, 0.1);
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotation(0.3F));
                     if (flag28) {
                        matrixStack10.multiply(RotationAxis.NEGATIVE_Z.rotation(-0.3F));
                        matrixStack10.multiply(RotationAxis.NEGATIVE_Y.rotation(1.0F));
                     } else {
                        matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotation(-0.3F));
                        matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotation(1.0F));
                     }

                     heldItemRendererAccessor126.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, floatValue9, floatValue7, arm5.getOpposite());
                     if (flag28) {
                        matrixStack10.multiply(RotationAxis.NEGATIVE_Y.rotation(2.5F));
                     } else {
                        matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotation(2.5F));
                     }

                     matrixStack10.translate(flag28 ? -0.65 : 0.65, -0.35, 0.27);
                     if (floatValue50 > 1.0F) {
                        floatValue50 = 1.0F;
                     }

                     matrixStack10.pop();
                     if (swingAnimations14.f) {
                        matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(10 * intValue38)));
                     }

                     matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(75.0F));
                     matrixStack10.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees((float)(-15 * intValue38)));
                     matrixStack10.translate(0.8 * (double)intValue38, (double)(0.0F - floatValue9 * 0.3F), -0.1);
                     if (floatValue125 > 0.1F) {
                        float floatValue130 = MathHelper.sin((floatValue123 - 0.1F) * 1.3F);
                        float floatValue132 = floatValue50 - 0.1F;
                        float floatValue135 = floatValue130 * floatValue132;
                        matrixStack10.translate(floatValue135 * 0.0F, floatValue135 * 0.004F, floatValue135 * 0.0F);
                     }

                     matrixStack10.push();
                     break;
                  case SPEAR:
                     if (abstractClientPlayerEntity1.getOffHandStack().isEmpty() && !abstractClientPlayerEntity1.isCrawling() && !abstractClientPlayerEntity1.isSwimming() && !abstractClientPlayerEntity1.isClimbing()) {
                        matrixStack10.push();
                        matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(-25 * intValue38)));
                        matrixStack10.translate(-0.15 * (double)intValue38, 0.1, 0.1);
                        HeldItemRendererAccessor heldItemRendererAccessor127 = (HeldItemRendererAccessor)this;
                        heldItemRendererAccessor127.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, floatValue9, floatValue7, arm5.getOpposite());
                        matrixStack10.pop();
                     }

                     float floatValue128 = (float)itemStack8.getMaxUseTime(abstractClientPlayerEntity1) - ((float)abstractClientPlayerEntity1.getItemUseTimeLeft() - floatValue2 + 1.0F);
                     float floatValue124 = floatValue128 / 10.0F;
                     if (floatValue124 > 1.0F) {
                        floatValue124 = 1.0F;
                     }

                     if (floatValue124 > 0.1F) {
                        float floatValue54 = MathHelper.sin((floatValue128 - 0.1F) * 1.3F);
                        float floatValue134 = floatValue124 - 0.1F;
                        float floatValue137 = floatValue54 * floatValue134;
                        matrixStack10.translate(floatValue137 * 0.0F, floatValue137 * 0.004F, floatValue137 * 0.0F);
                     }

                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(45.0F));
                     matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(25 * intValue38)));
                     matrixStack10.translate(0.2 * (double)intValue38, 0.0, 0.8);
                     HeldItemRendererAccessor heldItemRendererAccessor131 = (HeldItemRendererAccessor)this;
                     heldItemRendererAccessor131.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, floatValue9, floatValue7, arm5);
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(135.0F));
                     matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(-65 * intValue38)));
                     matrixStack10.translate((double)(0.65F * (float)intValue38), -1.0, -0.6);
                     break;
                  case BRUSH:
                     float floatValue133 = (float)(abstractClientPlayerEntity1.getItemUseTimeLeft() % 10);
                     float floatValue136 = floatValue133 - floatValue2 + 1.0F;
                     float floatValue57 = 1.0F - floatValue136 / 10.0F;
                     float floatValue58 = -15.0F + 75.0F * MathHelper.cos(floatValue57 * 2.0F * (float) Math.PI);
                     float floatValue59 = (float)itemStack8.getMaxUseTime(abstractClientPlayerEntity1) - ((float)abstractClientPlayerEntity1.getItemUseTimeLeft() - floatValue2 + 1.0F);
                     float floatValue60 = floatValue59 / 4.0F;
                     if (floatValue60 > 1.0F) {
                        floatValue60 = 1.0F;
                     }

                     matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(25 * intValue38) * floatValue60));
                     matrixStack10.translate((double)(0.3F * (float)intValue38 * floatValue60), 0.3 * (double)floatValue60, 0.1 * (double)floatValue60);
                     if (floatValue60 == 1.0F) {
                        matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(floatValue58 / 20.0F));
                     }

                     HeldItemRendererAccessor heldItemRendererAccessor61 = (HeldItemRendererAccessor)this;
                     heldItemRendererAccessor61.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, floatValue9, floatValue7, arm5);
                     break;
                  case BUNDLE:
                     matrixStack10.translate((double)(1 * intValue38), 0.0 - (double)floatValue9 * 0.3, 0.3);
                     matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(45 * intValue38)));
                     matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(-40 * intValue38)));
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30.0F));
                     this.altSwing(matrixStack10, arm5, floatValue7, itemStack8);
                     matrixStack10.scale(0.9F, 0.9F, 0.9F);
                     HeldItemRendererAccessor heldItemRendererAccessor62 = (HeldItemRendererAccessor)this;
                     heldItemRendererAccessor62.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, 0.0F, 0.0F, arm5);
               }
            } else if (abstractClientPlayerEntity1.isUsingRiptide() && itemStack8.getUseAction() == UseAction.SPEAR) {
               this.riptideCounter = (float)((double)this.riptideCounter + 0.15 * doubleValue30);
               float floatValue77 = (float)itemStack8.getMaxUseTime(abstractClientPlayerEntity1) - ((float)abstractClientPlayerEntity1.getItemUseTimeLeft() - floatValue2 + 1.0F);
               float floatValue85 = floatValue77 / 10.0F;
               if (floatValue85 > 1.0F) {
                  floatValue85 = 1.0F;
               }

               if (floatValue85 > 0.1F) {
                  float floatValue95 = MathHelper.sin((floatValue77 - 0.1F) * 1.3F);
                  float floatValue103 = floatValue85 - 0.1F;
                  float floatValue110 = floatValue95 * floatValue103;
                  matrixStack10.translate(floatValue110 * 0.0F, floatValue110 * 0.004F, floatValue110 * 0.0F);
               }

               matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(45.0F - this.riptideCounter * 2.0F));
               matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(25 * intValue38)));
               matrixStack10.translate(0.2 * (double)intValue38, 0.0, 0.75);
               matrixStack10.translate(0.0, 0.0, 0.01 * (double)MathHelper.sin(this.riptideCounter * 6.28F));
               HeldItemRendererAccessor heldItemRendererAccessor96 = (HeldItemRendererAccessor)this;
               heldItemRendererAccessor96.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, floatValue9, floatValue7, arm5);
               matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(135.0F));
               matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(-65 * intValue38)));
               matrixStack10.translate((double)(0.65F * (float)intValue38), -1.0, -0.6);
            } else {
               this.riptideCounter = 0.0F;
               if (!itemStack8.isOf(Items.LANTERN) && !itemStack8.isOf(Items.SOUL_LANTERN) && !itemStack8.isIn(ItemTags.HANGING_SIGNS)) {
                  if (itemStack8.getUseAction() == UseAction.BLOCK) {
                     matrixStack10.translate(0.0, -0.2, 0.0);
                  }
               } else {
                  matrixStack10.translate(0.1 * (double)intValue38, 0.0, -0.1);
                  matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(10.0F));
               }

               matrixStack10.translate((double)(1 * intValue38), 0.0 - (double)floatValue9 * 0.3, 0.3);
               matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(45 * intValue38)));
               matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(-40 * intValue38)));
               matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30.0F));
               this.altSwing(matrixStack10, arm5, floatValue7, itemStack8);
               matrixStack10.scale(0.9F, 0.9F, 0.9F);
               HeldItemRendererAccessor heldItemRendererAccessor76 = (HeldItemRendererAccessor)this;
               heldItemRendererAccessor76.invokeRenderArmHoldingItem(matrixStack10, vertexConsumerProvider11, intValue12, 0.0F, 0.0F, arm5);
            }

            matrixStack10.translate(-0.3 * (double)intValue38, 0.65, -0.1);
            matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(-65 * intValue38)));
            matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(10.0F));
            if (itemStack8.isIn(ItemTags.WOOL_CARPETS)) {
               matrixStack10.translate(0.2 * (double)intValue38, -0.1, 0.0);
            }

            if (Block.getBlockFromItem(itemStack8.getItem()) == Blocks.AIR || itemStack8.getUseAction() == UseAction.EAT || itemStack8.isIn(ConventionalItemTags.BUCKETS)) {
               if ((
                     !itemStack8.isIn(ConventionalItemTags.TOOLS)
                        || itemStack8.isIn(ItemTags.TRIMMABLE_ARMOR)
                        || itemStack8.isIn(ItemTags.BOOKSHELF_BOOKS)
                        || itemStack8.getUseAction() == UseAction.EAT
                        || !itemStack8.isEnchantable()
                  )
                  && itemStack8.getUseAction() != UseAction.BOW
                  && itemStack8.getUseAction() != UseAction.SPYGLASS
                  && this.getAttackDamage(itemStack8) == 0.0F
                  && itemStack8.getUseAction() != UseAction.BLOCK
                  && !itemStack8.isOf(Items.WARPED_FUNGUS_ON_A_STICK)
                  && !itemStack8.isOf(Items.CARROT_ON_A_STICK)
                  && !itemStack8.isOf(Items.FISHING_ROD)
                  && !itemStack8.isOf(Items.SHEARS)
                  && !itemStack8.isIn(ItemTags.HOES)
                  && !swingAnimations14.f) {
                  if (itemStack8.getUseAction() == UseAction.BRUSH) {
                     matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(25.0F));
                     matrixStack10.translate(flag28 ? 0.0 : 0.35, flag28 ? 0.0 : 0.25, flag28 ? 0.0 : 0.37);
                     if (!flag28) {
                        matrixStack10.scale(0.75F, 0.75F, 0.75F);
                     }

                     matrixStack10.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees((float)(-75 * intValue38)));
                     matrixStack10.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(35.0F));
                     matrixStack10.translate(flag28 ? -0.05 : 0.85, flag28 ? 0.0 : 0.05, flag28 ? 0.08 : -0.2);
                  } else {
                     matrixStack10.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float)(5 * intValue38)));
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(15.0F));
                     matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(75 * intValue38)));
                     matrixStack10.translate(0.0, -0.05, -0.1);
                     matrixStack10.scale(0.7F, 0.7F, 0.7F);
                  }

                  if (itemStack8.isOf(Items.FEATHER) || itemStack8.isOf(Items.SLIME_BALL) || itemStack8.isOf(Items.PUFFERFISH)) {
                     this.vertVelocityYSlime = (float)((double)this.vertVelocityYSlime + (double)floatValue7 * 0.03 * AspectDLC.deltaTime * 30.0);
                     if ((
                           abstractClientPlayerEntity1.getVelocity().length() > 0.09 && abstractClientPlayerEntity1.isOnGround()
                              || abstractClientPlayerEntity1.isSwimming()
                              || abstractClientPlayerEntity1.isCrawling()
                              || abstractClientPlayerEntity1.isClimbing() && !abstractClientPlayerEntity1.isOnGround()
                        )
                        && (Boolean)this.client.options.getBobView().getValue()) {
                        Random random80 = new Random();
                        boolean flag88 = random80.nextBoolean();
                        this.vertVelocityYSlime = this.vertVelocityYSlime + (float)(-0.05 * abstractClientPlayerEntity1.getVelocity().length() * AspectDLC.deltaTime * 30.0);
                     }

                     matrixStack10.scale(1.0F, 1.0F + this.vertAngleYSlime * -2.0F, 1.0F);
                  }
               } else if (itemStack8.getUseAction() == UseAction.BLOCK && itemStack8.getUseAction() != UseAction.SPEAR) {
                  matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(160 * intValue38)));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(-60 * intValue38)));
                  matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-70.0F));
                  matrixStack10.scale(0.75F, 0.75F, 0.75F);
                  matrixStack10.translate(0.15 * (double)intValue38, flag28 ? 0.35 : 0.45, flag28 ? -0.15 : -0.1);
                  matrixStack10.translate(0.17 * (double)intValue38, 0.0, 0.3);
                  matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(-90 * intValue38)));
               } else if (itemStack8.getUseAction() == UseAction.SPEAR) {
                  matrixStack10.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float)(75 * intValue38)));
                  matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(45 * intValue38)));
                  matrixStack10.translate(-0.3F * (float)intValue38, 0.0F, 0.0F);
               } else if (itemStack8.getUseAction() != UseAction.SPEAR) {
                  matrixStack10.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float)(75 * intValue38)));
                  matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(70.0F));
                  matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(45 * intValue38)));
               }

               if (itemStack8.getUseAction() != UseAction.BLOCK) {
                  matrixStack10.scale(1.2F, 1.2F, 1.2F);
               }

               if (itemStack8.getUseAction() == UseAction.BOW && !abstractClientPlayerEntity1.isUsingItem()) {
                  matrixStack10.translate(-0.1 * (double)intValue38, -0.2, 0.0);
               }

               if (itemStack8.isOf(Items.MACE)) {
                  if (swingAnimations14.f) {
                     matrixStack10.translate(-0.08, 0.17, 0.0);
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(40.0F));
                  }

                  matrixStack10.translate(0.1 * (double)intValue38, 0.0, 0.0);
                  matrixStack10.scale(0.9F, 0.9F, 0.9F);
               }
            } else if (itemStack8.getName().toString().toLowerCase().contains("TORCH".toLowerCase())) {
               matrixStack10.scale(1.5F, 1.5F, 1.5F);
               matrixStack10.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float)(25 * intValue38)));
               matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(5.0F));
               matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(75 * intValue38)));
               matrixStack10.translate(0.2 * (double)intValue38, 0.2, 0.05);
            } else if ((
                  itemStack8.isOf(Items.STRING)
                     || itemStack8.isOf(Items.REDSTONE)
                     || itemStack8.isOf(Items.LEVER)
                     || itemStack8.isOf(Items.TRIPWIRE_HOOK)
                     || Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(ConventionalBlockTags.GLASS_PANES)
                     || Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.RAILS)
                     || Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.CLIMBABLE)
                     || itemStack8.isIn(ItemTags.DOORS)
               )
               && !Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.LEAVES)
               && !Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.COMBINATION_STEP_SOUND_BLOCKS)
               && !Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.BANNERS)) {
               matrixStack10.translate(0.0, 0.0, -0.1);
               matrixStack10.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float)(5 * intValue38)));
               matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(15.0F));
               matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(75 * intValue38)));
            } else if (!itemStack8.isOf(Items.LANTERN) && !itemStack8.isOf(Items.SOUL_LANTERN) && !itemStack8.isIn(ItemTags.HANGING_SIGNS)) {
               matrixStack10.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float)(25 * intValue38)));
               matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(5.0F));
               matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(75 * intValue38)));
               matrixStack10.translate(0.2 * (double)intValue38, 0.2, 0.05);
               if (Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.BANNERS)) {
                  matrixStack10.translate(-0.2 * (double)intValue38, 0.0, 0.0);
                  matrixStack10.scale(1.1F, 1.1F, 1.1F);
               }
            } else {
               float floatValue79 = (float)(AspectDLC.deltaTime * 30.0);
               float floatValue87 = abstractClientPlayerEntity1.prevHeadYaw - abstractClientPlayerEntity1.getHeadYaw();
               float floatValue98 = abstractClientPlayerEntity1.prevPitch - abstractClientPlayerEntity1.getPitch();
               this.swingVelocityY += floatValue87 * 0.015F * floatValue79;
               this.swingVelocityY += floatValue7 * 2.0F * floatValue79;
               this.swingVelocityX += floatValue98 * 0.015F * floatValue79;
               this.swingVelocityY = this.swingVelocityY - 0.1F * this.swingAngleY * floatValue79;
               this.swingVelocityX = this.swingVelocityX - 0.1F * this.swingAngleX * floatValue79;
               this.swingVelocityY = (float)((double)this.swingVelocityY * Math.pow(0.88F, (double)floatValue79));
               this.swingVelocityX = (float)((double)this.swingVelocityX * Math.pow(0.88F, (double)floatValue79));
               this.swingAngleY = this.swingAngleY + this.swingVelocityY * floatValue79;
               this.swingAngleX = this.swingAngleX + this.swingVelocityX * floatValue79;
               double doubleValue106 = abstractClientPlayerEntity1.getVelocity().length();
               this.swingVelocityZ = (float)(
                  (double)this.swingVelocityZ
                     + (
                        flag28
                           ? (doubleValue106 * -1.0 * 15.0 - (double)this.swingVelocityZ) * 0.1F * (double)floatValue79
                           : (doubleValue106 * 15.0 - (double)this.swingVelocityZ) * 0.1F * (double)floatValue79
                     )
               );
               if ((doubleValue106 > 0.09 && abstractClientPlayerEntity1.isOnGround() || abstractClientPlayerEntity1.isSwimming() || abstractClientPlayerEntity1.isClimbing() && !abstractClientPlayerEntity1.isOnGround())
                  && (Boolean)this.client.options.getBobView().getValue()) {
                  Random random114 = new Random();
                  boolean flag118 = random114.nextBoolean();
                  this.swingVelocityY += (float)(flag118 ? -5.5 * doubleValue106 * (double)floatValue79 : 5.5 * doubleValue106 * (double)floatValue79);
               }

               matrixStack10.translate(0.0, 0.0, -0.1);
               matrixStack10.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees((float)(35 * intValue38) + this.swingAngleY));
               matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(15.0F + this.swingAngleX));
               matrixStack10.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)(75 * intValue38) + this.swingVelocityZ));
               if (itemStack8.isIn(ItemTags.HANGING_SIGNS)) {
                  matrixStack10.translate(0.0, -0.1, 0.0);
                  matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(-45 * intValue38)));
               }

               matrixStack10.translate(0.3 * (double)intValue38, -0.35, 0.0);
               matrixStack10.translate(0.0, 0.0, 0.1);
               matrixStack10.scale(1.5F, 1.5F, 1.5F);
            }

            if (itemStack8.getItem() instanceof BlockItem
               && (
                  !itemStack8.isIn(ConventionalItemTags.BUCKETS)
                        && itemStack8.getUseAction() != UseAction.EAT
                        && !itemStack8.isIn(ItemTags.BANNERS)
                        && !itemStack8.isOf(Items.STRING)
                        && !itemStack8.isOf(Items.REDSTONE)
                        && !itemStack8.isOf(Items.LEVER)
                        && !itemStack8.isOf(Items.TRIPWIRE_HOOK)
                        && !Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(ConventionalBlockTags.GLASS_PANES)
                        && !Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.RAILS)
                        && !Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.CLIMBABLE)
                        && !itemStack8.isIn(ItemTags.DOORS)
                     || Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.LEAVES)
               )
               && !Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.COMBINATION_STEP_SOUND_BLOCKS)) {
               BlockItem blockItem83 = (BlockItem)itemStack8.getItem();
               BlockRenderManager blockRenderManager90 = MinecraftClient.getInstance().getBlockRenderManager();
               blockRenderManager90.getModel(blockItem83.getBlock().getDefaultState());
               matrixStack10.push();
               if (!flag67) {
                  matrixStack10.translate(-0.4F, 0.0F, 0.0F);
               }

               matrixStack10.scale(0.4F, 0.4F, 0.4F);
               matrixStack10.translate(-0.9 * (double)intValue38, -0.45, -0.5);
               if (Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.BUTTONS)) {
                  matrixStack10.translate(0.2 * (double)intValue38, -0.15, -0.2);
               }

               if (Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.PRESSURE_PLATES)) {
                  matrixStack10.translate(0.0, 0.1, 0.0);
               }

               if (itemStack8.isOf(Items.SLIME_BLOCK)
                  || itemStack8.isOf(Items.HONEY_BLOCK)
                  || Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.FLOWERS)
                  || Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.LEAVES)
                  || Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.SAPLINGS)
                  || Block.getBlockFromItem(itemStack8.getItem()).getDefaultState().isIn(BlockTags.SWORD_EFFICIENT)) {
                  this.vertVelocityYSlime = (float)((double)this.vertVelocityYSlime + (double)floatValue7 * 0.03 * AspectDLC.deltaTime * 30.0);
                  if ((
                        abstractClientPlayerEntity1.getVelocity().length() > 0.09 && abstractClientPlayerEntity1.isOnGround()
                           || abstractClientPlayerEntity1.isSwimming()
                           || abstractClientPlayerEntity1.isCrawling()
                           || abstractClientPlayerEntity1.isClimbing() && !abstractClientPlayerEntity1.isOnGround()
                     )
                     && (Boolean)this.client.options.getBobView().getValue()) {
                     Random random99 = new Random();
                     boolean flag107 = random99.nextBoolean();
                     this.vertVelocityYSlime = this.vertVelocityYSlime + (float)(-0.05 * abstractClientPlayerEntity1.getVelocity().length() * AspectDLC.deltaTime * 30.0);
                  }

                  matrixStack10.scale(1.0F, 1.0F + this.vertAngleYSlime * -2.0F, 1.0F);
               }

               BlockState blockState100 = blockItem83.getBlock().getDefaultState();
               if ((float)abstractClientPlayerEntity1.age - this.prevAge >= 100.0F) {
                  this.repPower = !this.repPower;
                  this.prevAge = (float)abstractClientPlayerEntity1.age;
               }

               if (blockItem83.getBlock() == Blocks.REPEATER && this.repPower) {
                  blockState100 = (BlockState)blockState100.with(RepeaterBlock.POWERED, true);
               }

               if (blockItem83.getBlock() == Blocks.COMPARATOR && this.repPower) {
                  blockState100 = (BlockState)blockState100.with(ComparatorBlock.POWERED, true);
               }

               if (blockItem83.getBlock() == Blocks.REDSTONE_TORCH && abstractClientPlayerEntity1.isSubmergedInWater()) {
                  blockState100 = (BlockState)blockState100.with(RedstoneTorchBlock.LIT, false);
               }

               if ((blockItem83.getBlock() == Blocks.CAMPFIRE || blockItem83.getBlock() == Blocks.SOUL_CAMPFIRE) && abstractClientPlayerEntity1.isSubmergedInWater()) {
                  blockState100 = (BlockState)blockState100.with(CampfireBlock.LIT, false);
               }

               if (itemStack8.isIn(ItemTags.BEDS)) {
                  if (flag28) {
                     matrixStack10.translate(0.9, 0.0, 0.8);
                  }

                  matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(90 * intValue38)));
               }

               blockRenderManager90.renderBlockAsEntity(blockState100, matrixStack10, vertexConsumerProvider11, intValue12, OverlayTexture.DEFAULT_UV);
               matrixStack10.pop();
            } else {
               if (itemStack8.isIn(ConventionalItemTags.TOOLS)
                     && !itemStack8.isIn(ItemTags.TRIMMABLE_ARMOR)
                     && !itemStack8.isIn(ItemTags.BOOKSHELF_BOOKS)
                     && itemStack8.getUseAction() != UseAction.EAT
                     && itemStack8.isEnchantable()
                  || itemStack8.getUseAction() == UseAction.BOW
                  || itemStack8.getUseAction() == UseAction.SPYGLASS
                  || this.getAttackDamage(itemStack8) != 0.0F
                  || itemStack8.getUseAction() == UseAction.BLOCK
                  || itemStack8.isOf(Items.WARPED_FUNGUS_ON_A_STICK)
                  || itemStack8.isOf(Items.CARROT_ON_A_STICK)
                  || itemStack8.isOf(Items.FISHING_ROD)
                  || itemStack8.isOf(Items.SHEARS)) {
                  if (itemStack8.isIn(ItemTags.SWORDS) && !flag36) {
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-60.0F * floatValue35));
                     matrixStack10.translate(0.0, 0.1 * (double)floatValue35, -0.1 * (double)floatValue35);
                  }

                  if (itemStack8.isIn(ItemTags.SHOVELS)) {
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-80.0F * floatValue34));
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(30.0F * floatValue35));
                  } else if (itemStack8.getUseAction() == UseAction.SPEAR) {
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F * floatValue34));
                     matrixStack10.translate(0.0, 0.1 * (double)floatValue34, -0.1 * (double)floatValue34);
                  } else if (itemStack8.getUseAction() != UseAction.BLOCK) {
                     matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-25.0F * floatValue35));
                     matrixStack10.translate(0.0, 0.05 * (double)floatValue35, -0.05 * (double)floatValue35);
                  }
               }

               if (itemStack8.isOf(Items.NETHER_STAR) || itemStack8.isOf(Items.END_CRYSTAL) && swingAnimations14.f) {
                  this.netherCounter = (float)((double)this.netherCounter + 0.9 * doubleValue30);
                  matrixStack10.translate(0.0, 0.25 + 0.02 * (double)MathHelper.sin(this.netherCounter * 0.1F), 0.0);
                  matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(3.0F * MathHelper.sin(this.netherCounter * 0.2F)));
                  matrixStack10.scale(
                     1.0F + 0.01F * MathHelper.sin(this.netherCounter),
                     1.0F + 0.01F * MathHelper.sin(this.netherCounter),
                     1.0F + 0.01F * MathHelper.sin(this.netherCounter)
                  );
               } else {
                  this.netherCounter = 0.0F;
               }

               if (swingAnimations14.f) {
                  if (itemStack8.isIn(ItemTags.SWORDS)) {
                     matrixStack10.translate(0.0, 0.2, 0.0);
                  }

                  if (itemStack8.isOf(Items.FEATHER) || itemStack8.isOf(Items.SLIME_BALL) || itemStack8.isOf(Items.PUFFERFISH)) {
                     this.vertVelocityYSlime = (float)((double)this.vertVelocityYSlime + (double)floatValue7 * 0.03 * AspectDLC.deltaTime * 30.0);
                     if ((
                           abstractClientPlayerEntity1.getVelocity().length() > 0.09 && abstractClientPlayerEntity1.isOnGround()
                              || abstractClientPlayerEntity1.isSwimming()
                              || abstractClientPlayerEntity1.isCrawling()
                              || abstractClientPlayerEntity1.isClimbing() && !abstractClientPlayerEntity1.isOnGround()
                        )
                        && (Boolean)this.client.options.getBobView().getValue()) {
                        Random random81 = new Random();
                        boolean flag89 = random81.nextBoolean();
                        this.vertVelocityYSlime = this.vertVelocityYSlime + (float)(-0.05 * abstractClientPlayerEntity1.getVelocity().length() * AspectDLC.deltaTime * 30.0);
                     }

                     matrixStack10.scale(1.0F, 1.0F + this.vertAngleYSlime * -2.0F, 1.0F);
                  }
               }

               if (itemStack8.isIn(ItemTags.SHOVELS)) {
                  matrixStack10.translate(0.07 * (double)intValue38, 0.0, 0.05);
                  matrixStack10.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)(90 * intValue38)));
                  matrixStack10.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-15.0F));
               }

               if (itemStack8.isOf(Items.TORCH)) {
                  abstractClientPlayerEntity1.getWorld().addParticle(ParticleTypes.ITEM_SLIME, abstractClientPlayerEntity1.getPos().getX(), abstractClientPlayerEntity1.getPos().getY(), abstractClientPlayerEntity1.getPos().getZ(), 0.1, 0.1, 0.1);
               }

               HeldItemRendererAccessor heldItemRendererAccessor82 = (HeldItemRendererAccessor)this;
               heldItemRendererAccessor82.invokeRenderItem(
                  abstractClientPlayerEntity1,
                  itemStack8,
                  flag67 ? ModelTransformationMode.FIRST_PERSON_RIGHT_HAND : ModelTransformationMode.FIRST_PERSON_LEFT_HAND,
                  !flag67,
                  matrixStack10,
                  vertexConsumerProvider11,
                  intValue12
               );
            }
         }

         matrixStack10.pop();
         matrixStack10.pop();
         this.isAttacking = this.client.options.attackKey.isPressed();
      }
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

   @Shadow
   protected abstract void swingArm(float floatValue1, float floatValue2, MatrixStack matrixStack3, int intValue4, Arm arm5);

   @Shadow
   private static HandRenderType getHandRenderType(ClientPlayerEntity clientPlayerEntity0) {
      throw new AssertionError();
   }
}
