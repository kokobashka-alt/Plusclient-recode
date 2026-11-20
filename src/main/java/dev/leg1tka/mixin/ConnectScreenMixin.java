package dev.leg1tka.mixin;

import dev.leg1tka.accounts.ServerReconnectTracker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.network.CookieStorage;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ConnectScreen.class})
public class ConnectScreenMixin {
   public ConnectScreenMixin() {
   }

   @Inject(
      method = {"connect(Lnet/minecraft/client/gui/screen/Screen;Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/network/ServerAddress;Lnet/minecraft/client/network/ServerInfo;ZLnet/minecraft/client/network/CookieStorage;)V"},
      at = {@At("HEAD")}
   )
   private static void aspect$remember(
      Screen screen0, MinecraftClient minecraftClient1, ServerAddress serverAddress2, ServerInfo serverInfo3, boolean flag4, CookieStorage cookieStorage5, CallbackInfo callbackInfo6
   ) {
      dev.leg1tka.accounts.ServerReconnectTracker.a(serverAddress2, serverInfo3);
   }
}
