package dev.leg1tka.mixin;

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.implement.EventChangeSprint;
import dev.leg1tka.api.events.implement.EventOnTravelPost;
import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.modules.render.SwingAnimations;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class})
public abstract class LivingEntityMixin {
   public LivingEntityMixin() {
   }

   @Inject(
      method = {"setSprinting(Z)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onSetSprinting(boolean flag1, CallbackInfo callbackInfo2) {
      if ((Object)this instanceof ClientPlayerEntity && flag1) {
         try {
            EventChangeSprint eventChangeSprint3 = new EventChangeSprint(true);
            EventInvoker.invoke(eventChangeSprint3);
            if (!eventChangeSprint3.isSprinting()) {
               callbackInfo2.cancel();
            }
         } catch (Exception exception4) {
            exception4.printStackTrace();
         }
      }
   }

   @Inject(
      method = {"getHandSwingDuration()I"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onGetHandSwingDuration(CallbackInfoReturnable<Integer> callbackInfoReturnable1) {
      if ((Object)this == MinecraftClient.getInstance().player) {
         if (ModuleManager.a != null) {
            SwingAnimations swingAnimations2 = ModuleManager.av;
            if (swingAnimations2 != null && swingAnimations2.isEnabled() && swingAnimations2.p.getValue()) {
               callbackInfoReturnable1.setReturnValue((int)swingAnimations2.q.f());
            }
         }
      }
   }

   @Inject(
      method = {"calcGlidingVelocity(Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/Vec3d;"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void onsetvelocity(Vec3d vec3d1, CallbackInfoReturnable<Vec3d> callbackInfoReturnable2) throws Exception {
      if (EventInvoker.hasListeners(EventOnTravelPost.class)) {
         EventOnTravelPost eventOnTravelPost3 = new EventOnTravelPost(vec3d1.multiply(0.99F, 0.98F, 0.99F));
         EventInvoker.invoke(eventOnTravelPost3);
         callbackInfoReturnable2.setReturnValue(eventOnTravelPost3.getOldVelocity());
      }
   }
}
