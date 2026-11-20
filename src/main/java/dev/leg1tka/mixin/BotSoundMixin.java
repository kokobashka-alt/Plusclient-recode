package dev.leg1tka.mixin;

import dev.leg1tka.network.bots.BotController;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({SoundManager.class})
public abstract class BotSoundMixin {
   public BotSoundMixin() {
   }

   @Inject(
      method = {"play(Lnet/minecraft/client/sound/SoundInstance;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$botSound(SoundInstance soundInstance1, CallbackInfo callbackInfo2) {
      if (dev.leg1tka.network.bots.BotController.f()) {
         callbackInfo2.cancel();
      }
   }

   @Inject(
      method = {"play(Lnet/minecraft/client/sound/SoundInstance;I)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$botSoundDelayed(SoundInstance soundInstance1, int intValue2, CallbackInfo callbackInfo3) {
      if (dev.leg1tka.network.bots.BotController.f()) {
         callbackInfo3.cancel();
      }
   }
}
