package dev.leg1tka.mixin;

import dev.leg1tka.network.bots.BotController;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GameRenderer.class})
public abstract class BotRenderMixin {
   public BotRenderMixin() {
   }

   @Inject(
      method = {"showFloatingItem(Lnet/minecraft/item/ItemStack;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$botFloatingItem(ItemStack itemStack1, CallbackInfo callbackInfo2) {
      if (dev.leg1tka.network.bots.BotController.f()) {
         callbackInfo2.cancel();
      }
   }
}
