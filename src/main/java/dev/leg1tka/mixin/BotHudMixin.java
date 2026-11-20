package dev.leg1tka.mixin;

import dev.leg1tka.network.bots.BotController;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({InGameHud.class})
public abstract class BotHudMixin {
   public BotHudMixin() {
   }

   @Inject(
      method = {"setOverlayMessage(Lnet/minecraft/text/Text;Z)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$botOverlay(Text text1, boolean flag2, CallbackInfo callbackInfo3) {
      if (dev.leg1tka.network.bots.BotController.f()) {
         callbackInfo3.cancel();
      }
   }
}
