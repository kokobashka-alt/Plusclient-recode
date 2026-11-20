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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.network.ClientConfigurationNetworkHandler;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.common.ResourcePackRemoveS2CPacket;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import net.minecraft.network.packet.s2c.common.SynchronizeTagsS2CPacket;
import net.minecraft.network.packet.s2c.config.FeaturesS2CPacket;
import net.minecraft.network.packet.s2c.config.ReadyS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkLoadDistanceS2CPacket;
import net.minecraft.network.packet.s2c.play.CommandTreeS2CPacket;
import net.minecraft.network.packet.s2c.play.CommonPlayerSpawnInfo;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.network.packet.s2c.play.ServerMetadataS2CPacket;
import net.minecraft.network.packet.s2c.play.SimulationDistanceS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket.Reason;

public final class PacketRegistry {
   static volatile FeaturesS2CPacket a;
   static volatile SynchronizeTagsS2CPacket b;
   static final List c = new CopyOnWriteArrayList();
   public static volatile GameJoinS2CPacket d;
   public static volatile CommonPlayerSpawnInfo e;
   public static volatile int f = 10;
   public static volatile int g = 10;
   public static volatile CommandTreeS2CPacket h;
   public static volatile ServerMetadataS2CPacket i;
   public static volatile WorldTimeUpdateS2CPacket j;
   public static volatile GameStateChangeS2CPacket k;
   public static volatile GameStateChangeS2CPacket l;
   public static volatile GameStateChangeS2CPacket m;
   private static final Map n = new LinkedHashMap();
   private static boolean o;

   private PacketRegistry() {
   }

   static void a() {
      b();
      d = null;
      e = null;
      h = null;
      i = null;
      j = null;
      m = null;
      l = null;
      k = null;
      synchronized (n) {
         n.clear();
      }
   }

   static void b() {
      a = null;
      b = null;
      c.clear();
      o = true;
   }

   public static List<ResourcePackSendS2CPacket> c() {
      synchronized (n) {
         return new ArrayList<>(n.values());
      }
   }

   static void a(Packet<?> packet0, PacketListener packetListener1) {
      if (packetListener1 instanceof ClientConfigurationNetworkHandler) {
         if (!o) {
            b();
         }

         if (packet0 instanceof FeaturesS2CPacket featuresS2CPacket2) {
            a = featuresS2CPacket2;
         } else if (packet0.getClass().getName().startsWith("net.minecraft.network.packet.s2c.config.") && !(packet0 instanceof ReadyS2CPacket)) {
            c.add(packet0);
         }
      } else {
         o = false;
      }

      if (packet0 instanceof SynchronizeTagsS2CPacket synchronizeTagsS2CPacket18) {
         b = synchronizeTagsS2CPacket18;
      } else if (packet0 instanceof ResourcePackSendS2CPacket resourcePackSendS2CPacket3) {
         synchronized (n) {
            n.put(resourcePackSendS2CPacket3.id(), resourcePackSendS2CPacket3);
         }
      } else if (packet0 instanceof ResourcePackRemoveS2CPacket resourcePackRemoveS2CPacket4) {
         synchronized (n) {
            if (resourcePackRemoveS2CPacket4.id().isPresent()) {
               n.remove(resourcePackRemoveS2CPacket4.id().get());
            } else {
               n.clear();
            }
         }
      } else if (packet0 instanceof GameJoinS2CPacket gameJoinS2CPacket5) {
         d = gameJoinS2CPacket5;
         e = gameJoinS2CPacket5.commonPlayerSpawnInfo();
         f = gameJoinS2CPacket5.viewDistance();
         g = gameJoinS2CPacket5.simulationDistance();
      } else if (packet0 instanceof PlayerRespawnS2CPacket playerRespawnS2CPacket6) {
         e = playerRespawnS2CPacket6.commonPlayerSpawnInfo();
      } else if (packet0 instanceof ChunkLoadDistanceS2CPacket chunkLoadDistanceS2CPacket7) {
         f = chunkLoadDistanceS2CPacket7.getDistance();
      } else if (packet0 instanceof SimulationDistanceS2CPacket simulationDistanceS2CPacket8) {
         g = simulationDistanceS2CPacket8.simulationDistance();
      } else if (packet0 instanceof CommandTreeS2CPacket commandTreeS2CPacket9) {
         h = commandTreeS2CPacket9;
      } else if (packet0 instanceof ServerMetadataS2CPacket serverMetadataS2CPacket10) {
         i = serverMetadataS2CPacket10;
      } else if (packet0 instanceof WorldTimeUpdateS2CPacket worldTimeUpdateS2CPacket11) {
         j = worldTimeUpdateS2CPacket11;
      } else if (packet0 instanceof GameStateChangeS2CPacket gameStateChangeS2CPacket12) {
         Reason reason20 = gameStateChangeS2CPacket12.getReason();
         if (reason20 == GameStateChangeS2CPacket.RAIN_STARTED || reason20 == GameStateChangeS2CPacket.RAIN_STOPPED) {
            k = gameStateChangeS2CPacket12;
         } else if (reason20 == GameStateChangeS2CPacket.RAIN_GRADIENT_CHANGED) {
            l = gameStateChangeS2CPacket12;
         } else if (reason20 == GameStateChangeS2CPacket.THUNDER_GRADIENT_CHANGED) {
            m = gameStateChangeS2CPacket12;
         }
      }
   }
}
