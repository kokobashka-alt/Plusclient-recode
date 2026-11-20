package dev.leg1tka.mixin;

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.implement.EventAttackEntity;
import dev.leg1tka.api.events.implement.EventAttackEntityPost;
import dev.leg1tka.api.events.implement.EventBlockBreakDelay;
import dev.leg1tka.api.events.implement.EventInteractBlock;
import dev.leg1tka.network.ProxySessionBridge;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ClientPlayerInteractionManager.class})
public abstract class ClientPlayerInteractionManagerMixin {
   public ClientPlayerInteractionManagerMixin() {
   }

   @Inject(
      method = {"attackEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$proxyAttack(PlayerEntity playerEntity1, Entity entity2, CallbackInfo callbackInfo3) {
      if (dev.leg1tka.network.ProxySessionBridge.d()) {
         callbackInfo3.cancel();
      }
   }

   @Inject(
      method = {"clickSlot(IIILnet/minecraft/screen/slot/SlotActionType;Lnet/minecraft/entity/player/PlayerEntity;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$proxyClick(int intValue1, int intValue2, int intValue3, SlotActionType slotActionType4, PlayerEntity playerEntity5, CallbackInfo callbackInfo6) {
      if (dev.leg1tka.network.ProxySessionBridge.d()) {
         callbackInfo6.cancel();
      }
   }

   @Inject(
      method = {"attackBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$proxyAttackBlock(BlockPos blockPos1, Direction direction2, CallbackInfoReturnable<Boolean> callbackInfoReturnable3) {
      if (dev.leg1tka.network.ProxySessionBridge.d()) {
         callbackInfoReturnable3.setReturnValue(false);
      }
   }

   @Inject(
      method = {"updateBlockBreakingProgress(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$proxyBreak(BlockPos blockPos1, Direction direction2, CallbackInfoReturnable<Boolean> callbackInfoReturnable3) {
      if (dev.leg1tka.network.ProxySessionBridge.d()) {
         callbackInfoReturnable3.setReturnValue(false);
      }
   }

   @Inject(
      method = {"interactBlock(Lnet/minecraft/client/network/ClientPlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$proxyInteractBlock(ClientPlayerEntity clientPlayerEntity1, Hand hand2, BlockHitResult blockHitResult3, CallbackInfoReturnable<ActionResult> callbackInfoReturnable4) {
      if (dev.leg1tka.network.ProxySessionBridge.d()) {
         callbackInfoReturnable4.setReturnValue(ActionResult.FAIL);
      }
   }

   @Inject(
      method = {"interactItem(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$proxyInteractItem(PlayerEntity playerEntity1, Hand hand2, CallbackInfoReturnable<ActionResult> callbackInfoReturnable3) {
      if (dev.leg1tka.network.ProxySessionBridge.d()) {
         callbackInfoReturnable3.setReturnValue(ActionResult.FAIL);
      }
   }

   @Inject(
      method = {"interactEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$proxyInteractEntity(PlayerEntity playerEntity1, Entity entity2, Hand hand3, CallbackInfoReturnable<ActionResult> callbackInfoReturnable4) {
      if (dev.leg1tka.network.ProxySessionBridge.d()) {
         callbackInfoReturnable4.setReturnValue(ActionResult.FAIL);
      }
   }

   @Inject(
      method = {"interactEntityAtLocation(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/hit/EntityHitResult;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$proxyInteractAt(PlayerEntity playerEntity1, Entity entity2, EntityHitResult entityHitResult3, Hand hand4, CallbackInfoReturnable<ActionResult> callbackInfoReturnable5) {
      if (dev.leg1tka.network.ProxySessionBridge.d()) {
         callbackInfoReturnable5.setReturnValue(ActionResult.FAIL);
      }
   }

   @Inject(
      method = {"attackEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void attackEntity(PlayerEntity playerEntity1, Entity entity2, CallbackInfo callbackInfo3) {
      try {
         if (playerEntity1 != null && entity2 != null) {
            EventAttackEntity eventAttackEntity4 = new EventAttackEntity(playerEntity1, entity2);
            EventInvoker.invoke(eventAttackEntity4);
            if (eventAttackEntity4.isCancelled()) {
               callbackInfo3.cancel();
            }
         }
      } catch (Exception exception5) {
      }
   }

   @Inject(
      method = {"attackEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)V"},
      at = {@At("TAIL")}
   )
   private void attackEntityPost(PlayerEntity playerEntity1, Entity entity2, CallbackInfo callbackInfo3) {
      if (playerEntity1 != null && entity2 != null && EventInvoker.hasListeners(EventAttackEntityPost.class)) {
         new EventAttackEntityPost(playerEntity1, entity2).call();
      }
   }

   @Inject(
      method = {"updateBlockBreakingProgress(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z"},
      at = {@At("HEAD")}
   )
   private void onUpdateBlockBreakingProgress(BlockPos blockPos1, Direction direction2, CallbackInfoReturnable<Boolean> callbackInfoReturnable3) {
      ClientPlayerInteractionManager clientPlayerInteractionManager4 = (ClientPlayerInteractionManager)(Object)this;
      if (clientPlayerInteractionManager4.blockBreakingCooldown > 0 && EventInvoker.hasListeners(EventBlockBreakDelay.class)) {
         EventBlockBreakDelay eventBlockBreakDelay5 = new EventBlockBreakDelay(clientPlayerInteractionManager4.blockBreakingCooldown);
         eventBlockBreakDelay5.call();
         if (eventBlockBreakDelay5.isReset()) {
            clientPlayerInteractionManager4.blockBreakingCooldown = 0;
         }
      }
   }

   @Inject(
      method = {"interactBlock(Lnet/minecraft/client/network/ClientPlayerEntity;Lnet/minecraft/util/Hand;Lnet/minecraft/util/hit/BlockHitResult;)Lnet/minecraft/util/ActionResult;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onInteractBlock(ClientPlayerEntity clientPlayerEntity1, Hand hand2, BlockHitResult blockHitResult3, CallbackInfoReturnable<ActionResult> callbackInfoReturnable4) {
      if (EventInvoker.hasListeners(EventInteractBlock.class)) {
         EventInteractBlock eventInteractBlock5 = new EventInteractBlock(hand2, blockHitResult3);
         eventInteractBlock5.call();
         if (eventInteractBlock5.isCancelled()) {
            callbackInfoReturnable4.setReturnValue(ActionResult.SUCCESS);
         }
      }
   }
}
