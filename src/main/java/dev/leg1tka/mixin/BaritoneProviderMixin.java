package dev.leg1tka.mixin;

import baritone.api.IBaritone;
import baritone.api.IBaritoneProvider;
import dev.leg1tka.network.bots.BotController;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(
   targets = {"baritone/BaritoneProvider"},
   remap = false
)
public abstract class BaritoneProviderMixin implements IBaritoneProvider {
   public BaritoneProviderMixin() {
   }

   public IBaritone getBaritoneForPlayer(ClientPlayerEntity clientPlayerEntity1) {
      if (dev.leg1tka.network.bots.BotController.f()) {
         return null;
      } else {
         for (IBaritone iBaritone3 : this.getAllBaritones()) {
            if (clientPlayerEntity1 != null && clientPlayerEntity1 == iBaritone3.getPlayerContext().player()) {
               return iBaritone3;
            }
         }

         return null;
      }
   }
}
