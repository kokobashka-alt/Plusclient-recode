package dev.leg1tka.mixin;

import dev.leg1tka.network.bots.BotController;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({WorldRenderer.class})
public abstract class BotWorldRendererMixin {
   public BotWorldRendererMixin() {
   }

   @Inject(
      method = {"setBlockBreakingInfo(ILnet/minecraft/util/math/BlockPos;I)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$botBreaking(int intValue1, BlockPos blockPos2, int intValue3, CallbackInfo callbackInfo4) {
      if (dev.leg1tka.network.bots.BotController.f()) {
         callbackInfo4.cancel();
      }
   }
}
