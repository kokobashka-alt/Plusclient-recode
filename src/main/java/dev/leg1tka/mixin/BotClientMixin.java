package dev.leg1tka.mixin;

import dev.leg1tka.network.bots.BotController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen.WorldEntryReason;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
   value = {MinecraftClient.class},
   priority = 500
)
public abstract class BotClientMixin {
   public BotClientMixin() {
   }

   @Inject(
      method = {"setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$botScreen(Screen screen1, CallbackInfo callbackInfo2) {
      if (dev.leg1tka.network.bots.BotController.a(screen1)) {
         callbackInfo2.cancel();
      }
   }

   @Inject(
      method = {"enterReconfiguration(Lnet/minecraft/client/gui/screen/Screen;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$botReconfigure(Screen screen1, CallbackInfo callbackInfo2) {
      if (dev.leg1tka.network.bots.BotController.f()) {
         callbackInfo2.cancel();
      }
   }

   @Inject(
      method = {"joinWorld(Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/client/gui/screen/DownloadingTerrainScreen$WorldEntryReason;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$botJoinWorld(ClientWorld clientWorld1, WorldEntryReason worldEntryReason2, CallbackInfo callbackInfo3) {
      if (dev.leg1tka.network.bots.BotController.f()) {
         callbackInfo3.cancel();
      }
   }
}
