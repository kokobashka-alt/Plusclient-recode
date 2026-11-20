package dev.leg1tka.mixin;

import dev.leg1tka.network.bots.BotController;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ToastManager.class})
public abstract class BotUiMixin {
   public BotUiMixin() {
   }

   @Inject(
      method = {"add(Lnet/minecraft/client/toast/Toast;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$botToast(Toast toast1, CallbackInfo callbackInfo2) {
      if (dev.leg1tka.network.bots.BotController.f()) {
         callbackInfo2.cancel();
      }
   }
}
