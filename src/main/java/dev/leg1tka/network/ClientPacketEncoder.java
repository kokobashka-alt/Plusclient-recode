package dev.leg1tka.network;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;
import net.minecraft.network.NetworkState;
import net.minecraft.network.packet.Packet;

public final class ClientPacketEncoder extends MessageToByteEncoder<Packet<?>> {
   private volatile NetworkState a;

   public ClientPacketEncoder(NetworkState<?> networkState1) {
      this.a = networkState1;
   }

   public void a(NetworkState<?> networkState1) {
      this.a = networkState1;
   }

   public void write(ChannelHandlerContext channelHandlerContext1, Object object2, ChannelPromise channelPromise3) {
      if (object2 instanceof Packet packet4) {
         ByteBuf byteBuf5 = null;

         try {
            if (this.acceptOutboundMessage(packet4)) {
               byteBuf5 = this.allocateBuffer(channelHandlerContext1, packet4, this.isPreferDirect());

               try {
                  this.a(channelHandlerContext1, packet4, byteBuf5);
               } finally {
                  ReferenceCountUtil.release(packet4);
               }

               if (byteBuf5.isReadable()) {
                  channelHandlerContext1.write(byteBuf5, channelPromise3);
                  byteBuf5 = null;
               } else {
                  byteBuf5.release();
                  byteBuf5 = null;
                  channelPromise3.setSuccess();
               }
            } else {
               channelHandlerContext1.write(object2, channelPromise3);
            }
         } catch (Throwable throwable15) {
            if (byteBuf5 != null) {
               try {
                  byteBuf5.release();
               } catch (Exception exception13) {
               }

               Object object16 = null;
            }

            dev.leg1tka.network.ProxySessionBridge.a("encode " + packet4.getClass().getSimpleName(), throwable15);
            channelPromise3.setSuccess();
         }
      } else {
         try {
            channelHandlerContext1.write(object2, channelPromise3);
         } catch (Exception exception12) {
            channelPromise3.setSuccess();
         }
      }
   }

   @Override
   protected void encode(ChannelHandlerContext channelHandlerContext1, Packet<?> packet2, ByteBuf byteBuf3) throws Exception {
      ((NetworkState)this.a).codec().encode(byteBuf3, packet2);
   }

   protected void a(ChannelHandlerContext channelHandlerContext1, Packet<?> packet2, ByteBuf byteBuf3) throws Exception {
      this.encode(channelHandlerContext1, packet2, byteBuf3);
   }
}
