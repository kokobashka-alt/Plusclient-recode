package dev.leg1tka.mixin;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.utils.resources.BaritoneAccess;
import dev.leg1tka.network.bots.BotController;
import dev.leg1tka.api.utils.text.ClientChat;
import dev.leg1tka.api.utils.security.ClientIntegrityState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.s2c.play.EntityPositionSyncS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientPlayNetworkHandler.class})
public abstract class ClientPlayNetworkHandlerMixin {
   @Shadow
   private ClientWorld world;

   public ClientPlayNetworkHandlerMixin() {
   }

   @Inject(
      method = {"sendChatMessage(Ljava/lang/String;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void sendChatMessage(@NotNull String string1, CallbackInfo callbackInfo2) {
      if (string1.startsWith("#")) {
         if (!dev.leg1tka.api.utils.resources.BaritoneAccess.a()) {
            callbackInfo2.cancel();
         }
      } else if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.commandStorage != null) {
         if (string1.startsWith(AspectDLC.INSTANCE.commandStorage.d())) {
            try {
               AspectDLC.INSTANCE
                  .commandStorage
                  .b()
                  .execute(string1.substring(AspectDLC.INSTANCE.commandStorage.d().length()), AspectDLC.INSTANCE.commandStorage.a());
            } catch (CommandSyntaxException commandSyntaxException4) {
               dev.leg1tka.api.utils.text.ClientChat.a(
                  Formatting.RED
                     + "Ошибка в использовании!"
               );
            }

            callbackInfo2.cancel();
         }
      }
   }

   @Inject(
      method = {"onEntityVelocityUpdate(Lnet/minecraft/network/packet/s2c/play/EntityVelocityUpdateS2CPacket;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onVelocityUpdate(EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket1, CallbackInfo callbackInfo2) {
      EventPacket eventPacket3 = new EventPacket(entityVelocityUpdateS2CPacket1, EventPacket.Type.RECEIVE);
      eventPacket3.call();
      if (eventPacket3.isCancelled()) {
         callbackInfo2.cancel();
      }
   }

   @Inject(
      method = {"onExplosion(Lnet/minecraft/network/packet/s2c/play/ExplosionS2CPacket;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onExplosion(ExplosionS2CPacket explosionS2CPacket1, CallbackInfo callbackInfo2) {
      EventPacket eventPacket3 = new EventPacket(explosionS2CPacket1, EventPacket.Type.RECEIVE);
      eventPacket3.call();
      if (eventPacket3.isCancelled()) {
         callbackInfo2.cancel();
      }
   }

   @Inject(
      method = {"onEntityPositionSync(Lnet/minecraft/network/packet/s2c/play/EntityPositionSyncS2CPacket;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onEntityPositionSync(EntityPositionSyncS2CPacket entityPositionSyncS2CPacket1, CallbackInfo callbackInfo2) {
      MinecraftClient minecraftClient3 = MinecraftClient.getInstance();
      if (this.world == null || minecraftClient3.player == null || minecraftClient3.world == null) {
         callbackInfo2.cancel();
      }
   }

   @Inject(
      method = {"onGameMessage(Lnet/minecraft/network/packet/s2c/play/GameMessageS2CPacket;)V"},
      at = {@At("HEAD")}
   )
   private void onGameMessage(GameMessageS2CPacket gameMessageS2CPacket1, CallbackInfo callbackInfo2) {
      if (MinecraftClient.getInstance().isOnThread() && !dev.leg1tka.network.bots.BotController.f()) {
         dev.leg1tka.network.bots.BotController.a((ClientPlayNetworkHandler)(Object)this, gameMessageS2CPacket1);
      }
   }

   @Inject(
      method = {"onGameJoin(Lnet/minecraft/network/packet/s2c/play/GameJoinS2CPacket;)V"},
      at = {@At("HEAD")}
   )
   private void onGameJoin(GameJoinS2CPacket gameJoinS2CPacket1, CallbackInfo callbackInfo2) {
      ClientPlayNetworkHandler clientPlayNetworkHandler3 = (ClientPlayNetworkHandler)(Object)this;
      dev.leg1tka.network.bots.BotController.a(clientPlayNetworkHandler3);
      if (!dev.leg1tka.network.bots.BotController.f()) {
         String string4 = "";
         if (clientPlayNetworkHandler3.getServerInfo() != null && clientPlayNetworkHandler3.getServerInfo().address != null) {
            string4 = clientPlayNetworkHandler3.getServerInfo().address;
         }

         MinecraftClient minecraftClient5 = MinecraftClient.getInstance();
         if (string4.isEmpty() && minecraftClient5 != null && minecraftClient5.getCurrentServerEntry() != null && minecraftClient5.getCurrentServerEntry().address != null) {
            string4 = minecraftClient5.getCurrentServerEntry().address;
         }

         if (string4.isEmpty() && clientPlayNetworkHandler3.getConnection() != null && clientPlayNetworkHandler3.getConnection().getAddress() != null) {
            string4 = clientPlayNetworkHandler3.getConnection().getAddress().toString();
         }

         if (!string4.isEmpty()) {
            String string6 = string4.trim().toLowerCase();
            if (string6.contains("shard")) {
               dev.leg1tka.api.utils.security.ClientIntegrityState.f();
            }
         }
      }
   }
}
