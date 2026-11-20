package dev.leg1tka.mixin;

import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.modules.render.NoRender;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ParticleManager.class})
public class ParticleManagerMixin {
   public ParticleManagerMixin() {
   }

   @Inject(
      method = {"addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$addParticle(
      ParticleEffect particleEffect1, double doubleValue2, double doubleValue4, double doubleValue6, double doubleValue8, double doubleValue10, double doubleValue12, CallbackInfoReturnable<Particle> callbackInfoReturnable14
   ) {
      if (particleEffect1.getType() == ParticleTypes.EXPLOSION || particleEffect1.getType() == ParticleTypes.EXPLOSION_EMITTER) {
         if (ModuleManager.a != null) {
            NoRender noRender15 = ModuleManager.an;
            if (noRender15 != null && noRender15.c("Любые взрывы")) {
               callbackInfoReturnable14.setReturnValue(null);
            }
         }
      }
   }
}
