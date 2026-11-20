package dev.leg1tka.mixin;

import dev.leg1tka.network.ServerPingWorker;
import java.net.InetSocketAddress;
import java.util.Optional;
import net.minecraft.client.network.Address;
import net.minecraft.client.network.AllowedAddressResolver;
import net.minecraft.client.network.ServerAddress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({AllowedAddressResolver.class})
public class AllowedAddressResolverMixin {
   public AllowedAddressResolverMixin() {
   }

   @Inject(
      method = {"resolve(Lnet/minecraft/client/network/ServerAddress;)Ljava/util/Optional;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onResolveHead(ServerAddress serverAddress1, CallbackInfoReturnable<Optional<Address>> callbackInfoReturnable2) {
      if (serverAddress1 != null) {
         String string3 = serverAddress1.getAddress() + ":" + serverAddress1.getPort();
         Optional optional4 = dev.leg1tka.network.ServerPingWorker.a(string3);
         if (optional4.isEmpty()) {
            optional4 = dev.leg1tka.network.ServerPingWorker.a(serverAddress1.getAddress()).map(address1x -> Address.create(new InetSocketAddress(address1x.getInetSocketAddress().getAddress(), serverAddress1.getPort())));
         }

         if (optional4.isPresent()) {
            callbackInfoReturnable2.setReturnValue(optional4);
         }
      }
   }

   @Inject(
      method = {"resolve(Lnet/minecraft/client/network/ServerAddress;)Ljava/util/Optional;"},
      at = {@At("RETURN")}
   )
   private void onResolveReturn(ServerAddress serverAddress1, CallbackInfoReturnable<Optional<Address>> callbackInfoReturnable2) {
      if (serverAddress1 != null && callbackInfoReturnable2.getReturnValue() != null && ((Optional)callbackInfoReturnable2.getReturnValue()).isPresent()) {
         String string3 = serverAddress1.getAddress() + ":" + serverAddress1.getPort();
         dev.leg1tka.network.ServerPingWorker.a(string3, (Address)((Optional)callbackInfoReturnable2.getReturnValue()).get());
         dev.leg1tka.network.ServerPingWorker.a(serverAddress1.getAddress(), (Address)((Optional)callbackInfoReturnable2.getReturnValue()).get());
      }
   }
}
