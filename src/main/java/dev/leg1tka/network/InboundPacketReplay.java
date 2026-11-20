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

import io.netty.channel.ChannelHandlerContext;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TrackedPosition;
import net.minecraft.entity.data.DataTracker.SerializedEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.common.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.common.KeepAliveS2CPacket;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.DamageTiltS2CPacket;
import net.minecraft.network.packet.s2c.play.EnterReconfigurationS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityDamageS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityPositionSyncS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.network.packet.s2c.play.ProfilelessChatMessageS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public final class InboundPacketReplay {
   private static final ArrayDeque<dev.leg1tka.network.InboundPacketReplay.a> c = new ArrayDeque<>();
   private static final TrackedPosition d = new TrackedPosition();
   private static volatile ClientConnection e;
   private static volatile ChannelHandlerContext f;
   private static volatile boolean g;
   private static volatile boolean h;
   private static volatile int i;
   private static volatile int j = -1;
   private static volatile int k = -1;
   private static volatile Vec3d l;
   private static volatile double m;
   private static boolean n;
   private static long o;
   private static volatile boolean p;
   private static volatile double q = Double.POSITIVE_INFINITY;
   private static volatile Vec3d r;
   private static volatile double s;
   private static volatile double t;
   private static volatile long u;
   private static volatile boolean v;
   private static volatile Vec3d w;
   private static volatile boolean x;
   private static volatile double y = 2.5;
   private static boolean z;
   private static boolean A;
   private static double B = 1.0;
   private static long C;
   private static long D;
   private static long E = 100000000L;
   private static long F;
   private static long G;
   private static long H;
   private static long I;
   private static double J;
   private static double K;
   private static double L;
   public static volatile double a = 4.0;
   private static final Runnable M = dev.leg1tka.network.InboundPacketReplay::n;
   public static volatile boolean b = true;
   private static volatile float N = Float.NaN;

   private InboundPacketReplay() {
   }

   public static boolean a(ClientConnection clientConnection0, ChannelHandlerContext channelHandlerContext1, PacketListener packetListener2, Packet<?> packet3) {
      if (!n && clientConnection0 == e && packetListener2 instanceof ClientPlayPacketListener) {
         if (packet3 instanceof HealthUpdateS2CPacket healthUpdateS2CPacket4) {
            N = healthUpdateS2CPacket4.getHealth();
         }

         v = false;
         z = false;
         e(packet3);
         long longValue10 = System.nanoTime();
         long longValue6 = z ? a(longValue10) : longValue10;
         Vec3d vec3d8 = k >= 0 ? l : null;
         boolean flag9 = g || p || x;
         if (!flag9 && c.isEmpty()) {
            w = vec3d8;
            return false;
         } else if (d(packet3)) {
            return false;
         } else if (p && c.isEmpty() && a(vec3d8, o() + 0.05)) {
            w = vec3d8;
            return false;
         } else {
            f = channelHandlerContext1;
            if (c.isEmpty()) {
               u = longValue10;
            }

            c.addLast(new dev.leg1tka.network.InboundPacketReplay.a(clientConnection0, channelHandlerContext1, packet3, longValue10, longValue6, vec3d8, z));
            i = c.size();
            a(packet3);
            if (c(packet3) || v && (p || x)) {
               g = false;
               p = false;
               x = false;
               h = true;
            }

            if (!g && !p && !x) {
               p();
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public static void a(ClientConnection clientConnection0, int intValue1) {
      j = intValue1;
      if (e != clientConnection0) {
         ChannelHandlerContext channelHandlerContext2 = f;
         g = false;
         p = false;
         x = false;
         e = clientConnection0;
         f = null;
         if (channelHandlerContext2 != null) {
            channelHandlerContext2.channel().eventLoop().execute(() -> {
               c.clear();
               i = 0;
            });
         }
      }
   }

   public static synchronized void a(int intValue0, Vec3d vec3d1) {
      k = intValue0;
      if (intValue0 < 0) {
         l = null;
         m = 0.0;
      } else {
         d.setPos(vec3d1);
         l = vec3d1;
         w = vec3d1;
         m = 0.0;
      }
   }

   public static synchronized void b(int intValue0, Vec3d vec3d1) {
      if (k == intValue0 && !g && i <= 0) {
         d.setPos(vec3d1);
         l = vec3d1;
         w = vec3d1;
      }
   }

   public static int a() {
      return k;
   }

   public static void b() {
      h = false;
      p = false;
      x = false;
      g = true;
   }

   public static void a(Vec3d vec3d0, double doubleValue1, double doubleValue3, double doubleValue5, double doubleValue7) {
      r = vec3d0;
      s = doubleValue1 * 0.5;
      t = doubleValue3;
      q = doubleValue5;
      y = doubleValue7;
      x = false;
      p = true;
      ChannelHandlerContext channelHandlerContext9 = f;
      if (channelHandlerContext9 != null && i > 0) {
         channelHandlerContext9.channel().eventLoop().execute(M);
      }
   }

   public static void c() {
      g = false;
      p = false;
      if (i == 0) {
         x = false;
      } else {
         x = true;
         ChannelHandlerContext channelHandlerContext0 = f;
         if (channelHandlerContext0 != null) {
            channelHandlerContext0.channel().eventLoop().execute(M);
         }
      }
   }

   public static long d() {
      return i == 0 ? 0L : (System.nanoTime() - u) / 1000000L;
   }

   public static void e() {
      g = false;
      p = false;
      x = false;
      ChannelHandlerContext channelHandlerContext0 = f;
      if (channelHandlerContext0 != null && i != 0) {
         channelHandlerContext0.channel().eventLoop().execute(dev.leg1tka.network.InboundPacketReplay::m);
      }
   }

   public static boolean f() {
      return g;
   }

   public static boolean g() {
      return p;
   }

   public static boolean h() {
      return g || i > 0;
   }

   public static boolean i() {
      boolean flag0 = h;
      h = false;
      return flag0;
   }

   public static int j() {
      return i;
   }

   public static Vec3d k() {
      return l;
   }

   public static double l() {
      return m;
   }

   private static void m() {
      if (!g && !p && !x) {
         p();
      }
   }

   private static long a(long longValue0) {
      long longValue2 = longValue0 - C;
      C = longValue0;
      if (longValue2 > 0L && longValue2 < 400000000L) {
         E = E + (longValue2 - E) / 5L;
      }

      long longValue4 = D == 0L ? longValue0 : D + E;
      longValue4 = Math.max(longValue0 - 60000000L, Math.min(longValue0 + 60000000L, longValue4));
      D = longValue4;
      return longValue4;
   }

   private static void n() {
      if (!n && (p || x)) {
         dev.leg1tka.network.InboundPacketReplay.a value0 = (dev.leg1tka.network.InboundPacketReplay.a)c.peekFirst();
         if (value0 == null) {
            x = false;
         } else {
            boolean flag1 = x;
            double doubleValue2 = flag1 ? Double.POSITIVE_INFINITY : o();
            double doubleValue4 = A ? doubleValue2 - 0.15 : doubleValue2;
            long longValue6 = Long.MIN_VALUE;

            for (dev.leg1tka.network.InboundPacketReplay.a value9 : c) {
               if (value9.moved && !a(value9.at, doubleValue4)) {
                  break;
               }

               longValue6 = value9.due;
            }

            long longValue20 = longValue6 == Long.MIN_VALUE ? 0L : longValue6 - o;
            double doubleValue10 = flag1 ? 4.0 : 1.0 + (y - 1.0) * Math.max(0.0, Math.min(1.0, (double)(longValue20 - 100000000L) / 3.0E8));
            B = B + (doubleValue10 - B) * 0.3;
            long longValue12 = (long)(5.0E7 * Math.max(1.0, B));
            if (o < value0.due - longValue12) {
               o = value0.due - longValue12;
            }

            o += longValue12;
            n = true;

            try {
               while ((value0 = (dev.leg1tka.network.InboundPacketReplay.a)c.peekFirst()) != null && value0.due <= o) {
                  if (value0.moved && !a(value0.at, doubleValue4)) {
                     A = true;
                     o = Math.min(o, value0.due);
                     break;
                  }

                  if (value0.moved) {
                     A = false;
                     doubleValue4 = doubleValue2;
                  }

                  c.pollFirst();
                  i = c.size();
                  if (value0.at != null) {
                     w = value0.at;
                  }

                  if (value0.connection.isOpen()) {
                     value0.connection.channelRead0(value0.context, b(value0.packet));
                  }
               }
            } finally {
               n = false;
               i = c.size();
               dev.leg1tka.network.InboundPacketReplay.a value16 = (dev.leg1tka.network.InboundPacketReplay.a)c.peekFirst();
               if (value16 != null) {
                  u = value16.time;
               } else {
                  x = false;
               }
            }
         }
      }
   }

   private static double o() {
      double doubleValue0 = q;
      Vec3d vec3d2 = w;
      return vec3d2 != null && doubleValue0 != Double.POSITIVE_INFINITY ? Math.max(doubleValue0, a(vec3d2)) : doubleValue0;
   }

   private static boolean a(Vec3d vec3d0, double doubleValue1) {
      return vec3d0 != null && r != null && doubleValue1 != Double.POSITIVE_INFINITY ? a(vec3d0) <= doubleValue1 : true;
   }

   private static double a(Vec3d vec3d0) {
      Vec3d vec3d1 = r;
      if (vec3d1 == null) {
         return 0.0;
      } else {
         double doubleValue2 = s;
         return Math.sqrt(new Box(vec3d0.x - doubleValue2, vec3d0.y, vec3d0.z - doubleValue2, vec3d0.x + doubleValue2, vec3d0.y + t, vec3d0.z + doubleValue2).squaredMagnitude(vec3d1));
      }
   }

   private static void p() {
      n = true;

      dev.leg1tka.network.InboundPacketReplay.a value0;
      try {
         while ((value0 = (dev.leg1tka.network.InboundPacketReplay.a)c.pollFirst()) != null) {
            i = c.size();
            if (value0.at != null) {
               w = value0.at;
            }

            if (value0.connection.isOpen()) {
               value0.connection.channelRead0(value0.context, b(value0.packet));
            }
         }
      } finally {
         n = false;
         i = c.size();
      }
   }

   private static void a(Packet<?> packet0) {
      if (packet0 instanceof BundleS2CPacket bundleS2CPacket8) {
         for (Packet packet11 : bundleS2CPacket8.getPackets()) {
            a(packet11);
         }
      } else if (packet0 instanceof HealthUpdateS2CPacket healthUpdateS2CPacket7) {
         float floatValue9 = healthUpdateS2CPacket7.getHealth();
         if (floatValue9 > 0.0F) {
            MinecraftClient.getInstance().execute(() -> a(floatValue9));
         }
      } else if (packet0 instanceof EntityTrackerUpdateS2CPacket entityTrackerUpdateS2CPacket1) {
         int intValue2 = entityTrackerUpdateS2CPacket1.id();
         ArrayList arrayList3 = null;

         for (SerializedEntry serializedEntry5 : entityTrackerUpdateS2CPacket1.trackedValues()) {
            boolean flag6 = serializedEntry5.id() == LivingEntity.HEALTH.id() && intValue2 != j;
            if (flag6 || serializedEntry5.id() == PlayerEntity.ABSORPTION_AMOUNT.id()) {
               if (arrayList3 == null) {
                  arrayList3 = new ArrayList(2);
               }

               arrayList3.add(serializedEntry5);
            }
         }

         if (arrayList3 != null) {
            ArrayList arrayList12 = arrayList3;
            MinecraftClient.getInstance().execute(() -> a(intValue2, arrayList12));
         }
      }
   }

   private static void a(float floatValue0) {
      ClientPlayerEntity clientPlayerEntity1 = MinecraftClient.getInstance().player;
      if (clientPlayerEntity1 != null) {
         clientPlayerEntity1.setHealth(floatValue0);
      }
   }

   private static void a(int intValue0, List<SerializedEntry<?>> list1) {
      ClientWorld clientWorld2 = MinecraftClient.getInstance().world;
      if (clientWorld2 != null) {
         Entity entity3 = clientWorld2.getEntityById(intValue0);
         if (entity3 instanceof LivingEntity) {
            if (!(entity3 instanceof PlayerEntity)) {
               list1.removeIf(serializedEntry0x -> serializedEntry0x.id() == PlayerEntity.ABSORPTION_AMOUNT.id());
            }

            if (!list1.isEmpty()) {
               entity3.getDataTracker().writeUpdatedEntries(list1);
            }
         }
      }
   }

   private static Packet<?> b(Packet<?> packet0) {
      if (packet0 instanceof HealthUpdateS2CPacket healthUpdateS2CPacket1) {
         float floatValue2 = N;
         return (Packet<?>)(floatValue2 > 0.0F && !(healthUpdateS2CPacket1.getHealth() <= 0.0F) && floatValue2 != healthUpdateS2CPacket1.getHealth()
            ? new HealthUpdateS2CPacket(floatValue2, healthUpdateS2CPacket1.getFood(), healthUpdateS2CPacket1.getSaturation())
            : packet0);
      } else {
         return packet0;
      }
   }

   private static boolean c(Packet<?> packet0) {
      if (packet0 instanceof BundleS2CPacket bundleS2CPacket5) {
         for (Packet packet3 : bundleS2CPacket5.getPackets()) {
            if (c(packet3)) {
               return true;
            }
         }

         return false;
      } else {
         if (b) {
            if (packet0 instanceof EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket1 && entityVelocityUpdateS2CPacket1.getEntityId() == j) {
               return true;
            }

            if (packet0 instanceof ExplosionS2CPacket) {
               return true;
            }
         }

         if (packet0 instanceof HealthUpdateS2CPacket healthUpdateS2CPacket4 && healthUpdateS2CPacket4.getHealth() <= 0.0F) {
            return true;
         }

         return packet0 instanceof PlayerPositionLookS2CPacket
            || packet0 instanceof EnterReconfigurationS2CPacket
            || packet0 instanceof DisconnectS2CPacket
            || packet0 instanceof PlayerRespawnS2CPacket
            || packet0 instanceof GameJoinS2CPacket;
      }
   }

   private static boolean d(Packet<?> packet0) {
      if (packet0 instanceof PlaySoundS2CPacket || packet0 instanceof PlaySoundFromEntityS2CPacket || packet0 instanceof ParticleS2CPacket) {
         return true;
      } else if (packet0 instanceof GameMessageS2CPacket || packet0 instanceof ChatMessageS2CPacket || packet0 instanceof ProfilelessChatMessageS2CPacket) {
         return true;
      } else if (packet0 instanceof KeepAliveS2CPacket || packet0 instanceof CustomPayloadS2CPacket) {
         return true;
      } else if (packet0 instanceof EntityDamageS2CPacket entityDamageS2CPacket3) {
         return entityDamageS2CPacket3.entityId() != j;
      } else if (packet0 instanceof DamageTiltS2CPacket damageTiltS2CPacket2) {
         return damageTiltS2CPacket2.id() != j;
      } else {
         return packet0 instanceof EntityAnimationS2CPacket entityAnimationS2CPacket1 ? entityAnimationS2CPacket1.getAnimationId() != 2 : false;
      }
   }

   private static void e(Packet<?> packet0) {
      if (!(packet0 instanceof BundleS2CPacket bundleS2CPacket1)) {
         if (k >= 0) {
            f(packet0);
         }
      } else {
         for (Packet packet3 : bundleS2CPacket1.getPackets()) {
            e(packet3);
         }
      }
   }

   private static synchronized void f(Packet<?> packet0) {
      int intValue1 = k;
      if (packet0 instanceof EntityS2CPacket entityS2CPacket2) {
         if (entityS2CPacket2.id == intValue1 && entityS2CPacket2.isPositionChanged()) {
            c(intValue1, d.withDelta((long)entityS2CPacket2.getDeltaX(), (long)entityS2CPacket2.getDeltaY(), (long)entityS2CPacket2.getDeltaZ()));
         }
      } else if (packet0 instanceof EntityPositionSyncS2CPacket entityPositionSyncS2CPacket3) {
         if (entityPositionSyncS2CPacket3.id() == intValue1) {
            c(intValue1, entityPositionSyncS2CPacket3.values().position());
         }
      } else if (packet0 instanceof EntityPositionS2CPacket entityPositionS2CPacket4) {
         if (entityPositionS2CPacket4.entityId() != intValue1) {
            return;
         }

         Vec3d vec3d5 = d.getPos();
         Vec3d vec3d6 = entityPositionS2CPacket4.change().position();
         double doubleValue7 = entityPositionS2CPacket4.relatives().contains(PositionFlag.X) ? vec3d5.x + vec3d6.x : vec3d6.x;
         double doubleValue9 = entityPositionS2CPacket4.relatives().contains(PositionFlag.Y) ? vec3d5.y + vec3d6.y : vec3d6.y;
         double doubleValue11 = entityPositionS2CPacket4.relatives().contains(PositionFlag.Z) ? vec3d5.z + vec3d6.z : vec3d6.z;
         c(intValue1, new Vec3d(doubleValue7, doubleValue9, doubleValue11));
      }
   }

   private static void c(int intValue0, Vec3d vec3d1) {
      if (k == intValue0) {
         Vec3d vec3d2 = d.getPos();
         z = true;
         m = vec3d2.distanceTo(vec3d1);
         if (m > a) {
            v = true;
         }

         d.setPos(vec3d1);
         l = vec3d1;
      }
   }

   static record a(ClientConnection connection, ChannelHandlerContext context, Packet<?> packet, long time, long due, Vec3d at, boolean moved)  {






}
}
