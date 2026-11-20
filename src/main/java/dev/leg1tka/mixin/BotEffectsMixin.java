package dev.leg1tka.mixin;

import dev.leg1tka.network.bots.BotController;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ParticleManager.class})
public abstract class BotEffectsMixin {
   public BotEffectsMixin() {
   }

   @Inject(
      method = {"addParticle(Lnet/minecraft/client/particle/Particle;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$botParticle(Particle particle1, CallbackInfo callbackInfo2) {
      if (dev.leg1tka.network.bots.BotController.f()) {
         callbackInfo2.cancel();
      }
   }

   @Inject(
      method = {"addEmitter(Lnet/minecraft/entity/Entity;Lnet/minecraft/particle/ParticleEffect;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$botEmitter(Entity entity1, ParticleEffect particleEffect2, CallbackInfo callbackInfo3) {
      if (dev.leg1tka.network.bots.BotController.f()) {
         callbackInfo3.cancel();
      }
   }

   @Inject(
      method = {"addEmitter(Lnet/minecraft/entity/Entity;Lnet/minecraft/particle/ParticleEffect;I)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$botEmitterTicks(Entity entity1, ParticleEffect particleEffect2, int intValue3, CallbackInfo callbackInfo4) {
      if (dev.leg1tka.network.bots.BotController.f()) {
         callbackInfo4.cancel();
      }
   }
}
