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

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Generated;
import net.minecraft.network.packet.Packet;

public final class PacketSender implements dev.leg1tka.api.ClientAccess {
   private static final List c = new CopyOnWriteArrayList();

   public static void a(Packet<?> packet0) {
      c.add(packet0);
      minecraftClient.getNetworkHandler().sendPacket(packet0);
   }

   public static void b(Packet<?> packet0) {
      minecraftClient.getNetworkHandler().sendPacket(packet0);
   }

   @Generated
   private PacketSender() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }

   @Generated
   public static List<Packet<?>> a() {
      return c;
   }
}
