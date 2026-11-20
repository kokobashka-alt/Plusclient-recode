package dev.leg1tka.mixin;

import dev.leg1tka.api.events.implement.EventChunkReload;
import dev.leg1tka.api.ClientAccess;
import dev.leg1tka.gui.components.InputBindings;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Keyboard.class})
public class KeyboardMixin implements dev.leg1tka.api.ClientAccess {
   public KeyboardMixin() {
   }

   @Inject(
      method = {"onKey(JIIII)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onKey(long longValue1, int intValue3, int intValue4, int intValue5, int intValue6, CallbackInfo callbackInfo7) {
      if (minecraftClient.currentScreen == null) {
         dev.leg1tka.gui.components.InputBindings.a(intValue3, intValue5);
         if (minecraftClient.currentScreen != null) {
            callbackInfo7.cancel();
         }
      }
   }

   @Inject(
      method = {"processF3(I)Z"},
      at = {@At("RETURN")}
   )
   private void processF3(int intValue1, CallbackInfoReturnable<Boolean> callbackInfoReturnable2) {
      if (intValue1 == 65 && (Boolean)callbackInfoReturnable2.getReturnValue()) {
         new EventChunkReload().call();
      }
   }
}
