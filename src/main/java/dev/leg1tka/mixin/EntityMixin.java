package dev.leg1tka.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.implement.EventOnMovePost;
import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.api.ClientAccess;
import dev.leg1tka.modules.misc.NoPush;
import dev.leg1tka.modules.render.SeeInvisibles;
import dev.leg1tka.modules.render.ShaderCrystal;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Entity.class})
public abstract class EntityMixin implements dev.leg1tka.api.ClientAccess {
   public EntityMixin() {
   }

   @ModifyExpressionValue(
      method = {"move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/Entity;isControlledByPlayer()Z"
      )}
   )
   private boolean fixFallDistanceCalculation(boolean flag1) {
      return (Object)this == minecraftClient.player ? false : flag1;
   }

   @Inject(
      method = {"pushAwayFrom(Lnet/minecraft/entity/Entity;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void pushAwayFrom(CallbackInfo callbackInfo1) {
      if ((Object)this == minecraftClient.player && ModuleManager.a != null) {
         NoPush noPush2 = ModuleManager.ad;
         if (noPush2 != null && noPush2.isEnabled() && noPush2.p().a("Игроки")) {
            callbackInfo1.cancel();
         }
      }
   }

   @Inject(
      method = {"isPushedByFluids()Z"},
      at = {@At("RETURN")},
      cancellable = true
   )
   public void isPushedByFluids(CallbackInfoReturnable<Boolean> callbackInfoReturnable1) {
      if ((Object)this == minecraftClient.player && ModuleManager.a != null) {
         NoPush noPush2 = ModuleManager.ad;
         if (noPush2 != null && noPush2.isEnabled() && noPush2.p().a("Вода")) {
            callbackInfoReturnable1.setReturnValue(false);
         }
      }
   }

   @Inject(
      method = {"getTeamColorValue()I"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$getTeamColorValue(CallbackInfoReturnable<Integer> callbackInfoReturnable1) {
      if (ModuleManager.a != null) {
         ShaderCrystal shaderCrystal2 = ModuleManager.aq;
         if (shaderCrystal2 != null && shaderCrystal2.a((Entity)(Object)this)) {
            callbackInfoReturnable1.setReturnValue(shaderCrystal2.p());
         }
      }
   }

   @Inject(
      method = {"isInvisibleTo(Lnet/minecraft/entity/player/PlayerEntity;)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$allowSeeInvisibles(PlayerEntity playerEntity1, CallbackInfoReturnable<Boolean> callbackInfoReturnable2) {
      if ((Object)this instanceof PlayerEntity playerEntity3 && ModuleManager.a != null) {
         SeeInvisibles seeInvisibles4 = ModuleManager.ap;
         if (seeInvisibles4 != null && seeInvisibles4.a(playerEntity3)) {
            callbackInfoReturnable2.setReturnValue(false);
         }

         return;
      }
   }

   @Inject(
      method = {"updateVelocity(FLnet/minecraft/util/math/Vec3d;)V"},
      at = {@At("TAIL")}
   )
   private void AspectDLC$onUpdateVelocity(float floatValue1, Vec3d vec3d2, CallbackInfo callbackInfo3) {
      Entity entity4 = (Entity)(Object)this;
      if (MinecraftClient.getInstance().player != null
         && entity4.getId() == MinecraftClient.getInstance().player.getId()
         && EventInvoker.hasListeners(EventOnMovePost.class)) {
         EventOnMovePost eventOnMovePost5 = new EventOnMovePost(floatValue1, vec3d2);
         eventOnMovePost5.call();
      }
   }
}
