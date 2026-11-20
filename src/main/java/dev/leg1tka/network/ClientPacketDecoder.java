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
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import net.minecraft.network.NetworkState;
import net.minecraft.network.packet.Packet;

public final class ClientPacketDecoder extends ByteToMessageDecoder {
   private volatile NetworkState a;

   public ClientPacketDecoder(NetworkState<?> networkState1) {
      this.a = networkState1;
   }

   public void a(NetworkState<?> networkState1) {
      this.a = networkState1;
   }

   protected void decode(ChannelHandlerContext channelHandlerContext1, ByteBuf byteBuf2, List<Object> list3) {
      if (byteBuf2.readableBytes() != 0) {
         Packet packet4 = (Packet)this.a.codec().decode(byteBuf2);
         if (byteBuf2.readableBytes() > 0) {
            dev.leg1tka.network.ProxySessionBridge.a("decode " + packet4.getClass().getSimpleName() + " +" + byteBuf2.readableBytes() + "b", new IllegalStateException("trailing bytes"));
            byteBuf2.skipBytes(byteBuf2.readableBytes());
         }

         list3.add(packet4);
      }
   }
}
