package dev.leg1tka.mixin;

import dev.leg1tka.network.ServerPingWorker;
import net.minecraft.client.network.MultiplayerServerListPinger;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MultiplayerServerListPinger.class})
public abstract class MultiplayerServerListPingerMixin {
   public MultiplayerServerListPingerMixin() {
   }

   @Shadow
   public abstract void add(ServerInfo serverInfo1, Runnable runnable2, Runnable runnable3);

   @Inject(
      method = {"add(Lnet/minecraft/client/network/ServerInfo;Ljava/lang/Runnable;Ljava/lang/Runnable;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onAddHead(ServerInfo serverInfo1, Runnable runnable2, Runnable runnable3, CallbackInfo callbackInfo4) {
      if (!Thread.currentThread().getName().startsWith("FastPing-Worker")) {
         callbackInfo4.cancel();
         dev.leg1tka.network.ServerPingWorker.a.execute(() -> {
            try {
               this.add(serverInfo1, runnable2, runnable3);
            } catch (Exception exception5) {
            }
         });
      }
   }
}
