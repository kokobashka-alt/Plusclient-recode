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

import java.util.ArrayDeque;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientTickEndC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.RemoveEntityStatusEffectS2CPacket;

public final class PacketDelayQueue implements dev.leg1tka.api.ClientAccess {
   private static final ArrayDeque c = new ArrayDeque();
   private static boolean d;
   private static boolean e;
   private static volatile long f;
   private static volatile int g;
   private static volatile int h;
   private static long i;
   private static volatile boolean j;
   private static volatile long k;
   private static volatile long l;
   private static boolean m;
   private static long n;
   private static long o;

   private PacketDelayQueue() {
   }

   public static synchronized boolean a() {
      long longValue0 = System.currentTimeMillis();
      if (longValue0 - k < 200L) {
         return false;
      } else {
         j = true;
         m = false;
         o = longValue0;
         return true;
      }
   }

   public static synchronized void b() {
      if (j) {
         m = true;
         n = f;
      }
   }

   public static synchronized void c() {
      if (j) {
         j = false;
         m = false;
         i();
      }
   }

   public static boolean d() {
      return j;
   }

   private static boolean j() {
      return k >= o || System.currentTimeMillis() - o > 250L;
   }

   public static long e() {
      return f;
   }

   public static int f() {
      return g;
   }

   public static int g() {
      return h;
   }

   private static void k() {
      f++;
      g = 0;
      h = 0;
   }

   public static synchronized boolean a(Packet<?> packet0) {
      if (!j) {
         if (!c.isEmpty()) {
            i();
         }

         return false;
      } else if (packet0 instanceof CommonPongC2SPacket) {
         if (j()) {
            c();
            return false;
         } else {
            c.addLast(packet0);
            return true;
         }
      } else if (packet0 instanceof TeleportConfirmC2SPacket) {
         c();
         return false;
      } else if (m && e(packet0)) {
         dev.leg1tka.network.PacketSender.a(packet0);
         c();
         return true;
      } else {
         return false;
      }
   }

   public static synchronized void b(Packet<?> packet0) {
      if (packet0 instanceof CommonPongC2SPacket) {
         j = false;
         m = false;
         if (!c.isEmpty()) {
            i();
         }
      }
   }

   public static synchronized void c(Packet<?> packet0) {
      if (j && l >= o && packet0 instanceof ClientCommandC2SPacket clientCommandC2SPacket1 && (clientCommandC2SPacket1.getMode() == Mode.START_SPRINTING || clientCommandC2SPacket1.getMode() == Mode.STOP_SPRINTING)) {
         j = false;
         m = false;
         i();
      }

      if (packet0 instanceof TeleportConfirmC2SPacket) {
         e = true;
      } else if (packet0 instanceof PlayerMoveC2SPacket) {
         if (e) {
            e = false;
         } else {
            d = true;
            k();
         }
      } else if (packet0 instanceof ClientTickEndC2SPacket) {
         if (!d) {
            k();
         }

         d = false;
      } else {
         if (packet0 instanceof PlayerActionC2SPacket playerActionC2SPacket2) {
            if (playerActionC2SPacket2.getAction() == Action.START_DESTROY_BLOCK || playerActionC2SPacket2.getAction() == Action.STOP_DESTROY_BLOCK) {
               g++;
            }
         } else if (packet0 instanceof PlayerInteractBlockC2SPacket) {
            g++;
         } else if (packet0 instanceof PlayerInteractEntityC2SPacket) {
            h++;
         }
      }
   }

   public static void d(Packet<?> packet0) {
      if (!(packet0 instanceof ExplosionS2CPacket) && !(packet0 instanceof PlayerPositionLookS2CPacket) && !(packet0 instanceof PlayerAbilitiesS2CPacket)) {
         int intValue1;
         if (packet0 instanceof EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket3) {
            intValue1 = entityVelocityUpdateS2CPacket3.getEntityId();
         } else if (packet0 instanceof EntityStatusEffectS2CPacket entityStatusEffectS2CPacket4) {
            intValue1 = entityStatusEffectS2CPacket4.getEntityId();
         } else if (packet0 instanceof RemoveEntityStatusEffectS2CPacket removeEntityStatusEffectS2CPacket5) {
            intValue1 = removeEntityStatusEffectS2CPacket5.entityId();
         } else {
            if (!(packet0 instanceof EntityAttributesS2CPacket entityAttributesS2CPacket2)) {
               return;
            }

            intValue1 = entityAttributesS2CPacket2.getEntityId();
         }

         ClientPlayerEntity clientPlayerEntity6 = minecraftClient.player;
         if (clientPlayerEntity6 != null && clientPlayerEntity6.getId() == intValue1) {
            if (packet0 instanceof EntityAttributesS2CPacket) {
               l = System.currentTimeMillis();
            } else {
               k = System.currentTimeMillis();
            }
         }
      } else {
         k = System.currentTimeMillis();
      }
   }

   public static synchronized void h() {
      if (minecraftClient.getNetworkHandler() == null) {
         c.clear();
         m = false;
         j = false;
         e = false;
         d = false;
         h = 0;
         g = 0;
      } else {
         if (j) {
            if (!j() && (!m || f <= n)) {
               return;
            }

            c();
         }

         if (!c.isEmpty()) {
            i();
         }
      }
   }

   public static synchronized void i() {
      while (!c.isEmpty()) {
         Packet packet0 = (Packet)c.pollFirst();
         if (minecraftClient.getNetworkHandler() != null) {
            dev.leg1tka.network.PacketSender.a(packet0);
         }
      }
   }

   private static boolean e(Packet<?> packet0) {
      return packet0 instanceof PlayerMoveC2SPacket ? !e : packet0 instanceof ClientTickEndC2SPacket && !d;
   }
}
