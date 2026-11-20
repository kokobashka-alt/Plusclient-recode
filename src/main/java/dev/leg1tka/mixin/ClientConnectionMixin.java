package dev.leg1tka.mixin;

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.network.bots.BotController;
import dev.leg1tka.api.utils.security.ClientIntegrityState;
import dev.leg1tka.network.InboundPacketReplay;
import dev.leg1tka.network.PacketSender;
import dev.leg1tka.network.PacketDelayQueue;
import dev.leg1tka.network.ProxySessionBridge;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import java.lang.reflect.InvocationTargetException;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.ClientLoginPacketListener;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.handshake.ConnectionIntent;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientConnection.class})
public abstract class ClientConnectionMixin {
   @Shadow
   private Channel channel;
   @Shadow
   private volatile PacketListener packetListener;

   public ClientConnectionMixin() {
   }

   @Inject(
      method = {"channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void channelRead0(ChannelHandlerContext channelHandlerContext1, Packet<?> packet2, CallbackInfo callbackInfo3) throws InvocationTargetException, IllegalAccessException, InstantiationException {
      if (dev.leg1tka.network.bots.BotController.a((ClientConnection)(Object)this, this.packetListener, packet2)) {
         callbackInfo3.cancel();
      } else if (dev.leg1tka.network.InboundPacketReplay.a((ClientConnection)(Object)this, channelHandlerContext1, this.packetListener, packet2)) {
         callbackInfo3.cancel();
      } else {
         if (this.channel != null && this.channel.isOpen()) {
            dev.leg1tka.network.ProxySessionBridge.a((ClientConnection)(Object)this, this.channel, this.packetListener, packet2);
         }

         dev.leg1tka.network.PacketDelayQueue.d(packet2);
         EventPacket eventPacket4 = new EventPacket(packet2, EventPacket.Type.RECEIVE);
         EventInvoker.invoke(eventPacket4);
         if (eventPacket4.isCancelled()) {
            callbackInfo3.cancel();
         }
      }
   }

   @Inject(
      method = {"send(Lnet/minecraft/network/packet/Packet;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void send(Packet<?> packet1, CallbackInfo callbackInfo2) throws InvocationTargetException, IllegalAccessException, InstantiationException {
      if (!dev.leg1tka.network.ProxySessionBridge.f()) {
         if (!dev.leg1tka.network.bots.BotController.a((ClientConnection)(Object)this)) {
            if (packet1 instanceof HandshakeC2SPacket handshakeC2SPacket3
               && (handshakeC2SPacket3.intendedState() == ConnectionIntent.LOGIN || handshakeC2SPacket3.intendedState() == ConnectionIntent.TRANSFER)
               && handshakeC2SPacket3.address() != null
               && handshakeC2SPacket3.address().trim().toLowerCase().contains("shard")) {
               dev.leg1tka.api.utils.security.ClientIntegrityState.f();
            }

            ClientConnection clientConnection5 = (ClientConnection)(Object)this;
            if (dev.leg1tka.network.PacketSender.a().contains(packet1)) {
               dev.leg1tka.network.PacketSender.a().remove(packet1);
               if (!aspect$gated(clientConnection5, packet1, callbackInfo2)) {
                  dev.leg1tka.network.PacketDelayQueue.c(packet1);
               }
            } else {
               EventPacket eventPacket4 = new EventPacket(packet1, EventPacket.Type.SEND);
               EventInvoker.invoke(eventPacket4);
               if (eventPacket4.isCancelled()) {
                  dev.leg1tka.network.PacketDelayQueue.b(packet1);
                  callbackInfo2.cancel();
               } else if (dev.leg1tka.network.PacketDelayQueue.a(packet1)) {
                  callbackInfo2.cancel();
               } else if (!aspect$gated(clientConnection5, packet1, callbackInfo2)) {
                  dev.leg1tka.network.PacketDelayQueue.c(packet1);
               }
            }
         }
      }
   }

   @Unique
   private static boolean aspect$gated(ClientConnection clientConnection0, Packet<?> packet1, CallbackInfo callbackInfo2) {
      int intValue3 = dev.leg1tka.network.ProxySessionBridge.a(clientConnection0, packet1);
      if (intValue3 == 0) {
         return false;
      } else {
         callbackInfo2.cancel();
         if (intValue3 == 2) {
            dev.leg1tka.network.PacketDelayQueue.c(packet1);
         }

         return true;
      }
   }

   @Inject(
      method = {"connect(Ljava/lang/String;ILnet/minecraft/network/listener/ClientLoginPacketListener;)V"},
      at = {@At("HEAD")}
   )
   private void onConnectLogin(String string1, int intValue2, ClientLoginPacketListener clientLoginPacketListener3, CallbackInfo callbackInfo4) {
      if (string1 != null && string1.trim().toLowerCase().contains("shard")) {
         dev.leg1tka.api.utils.security.ClientIntegrityState.f();
      }
   }
}
