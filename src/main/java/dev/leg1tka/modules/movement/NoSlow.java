package dev.leg1tka.modules.movement;


import dev.leg1tka.modules.Module;
import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventSlowWalking;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.data.DataTracker.SerializedEntry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.UseAction;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.network.packet.s2c.play.RemoveEntityStatusEffectS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class NoSlow extends Module {
   public static NoSlow c = new NoSlow();
   private final ModeSetting d = new ModeSetting("Мод", "Grim Last", "Grim Last", "SkyTime", "GrimGrounded");
   private static int e;
   private static int f;
   private static int g;
   private static long h;
   private static long i;
   private static double j;
   private final Map k = new ConcurrentHashMap();
   private final Deque l = new ArrayDeque();
   private final Deque m = new ArrayDeque();
   private volatile int n;
   private boolean o;
   private boolean p;
   private boolean q;
   private long r;
   private long s;
   private int t;
   private int u = -1;
   private int v;

   public NoSlow() {
      super(
         "NoSlow",
         "Убирает замедление во время еды",
         Category.MOVEMENT
      );
      this.addSettings(new Setting[]{this.d});
   }

   @Override
   public void onDisable() {
      super.onDisable();
      if (this.o || !this.l.isEmpty() || !this.m.isEmpty()) {
         this.t();
      }

      this.k.clear();
      this.n = 0;
      this.v = 0;
   }

   public boolean p() {
      return this.isEnabled() && this.d.b("GrimGrounded") && this.q() && minecraftClient.player != null && !minecraftClient.player.isTouchingWater() && !minecraftClient.player.isSubmergedInWater();
   }

   public boolean q() {
      return this.isEnabled() && this.d.b("GrimGrounded") && minecraftClient.player != null && this.r()
         ? minecraftClient.player.input.hasForwardMovement()
            && !minecraftClient.player.horizontalCollision
            && !minecraftClient.player.collidedSoftly
            && (float)minecraftClient.player.getHungerManager().getFoodLevel() > 6.0F
            && !minecraftClient.player.isTouchingWater()
            && !minecraftClient.player.isSubmergedInWater()
            && !minecraftClient.player.isGliding()
            && !minecraftClient.player.hasVehicle()
         : false;
   }

   private boolean r() {
      return this.o && this.u == this.t && this.s();
   }

   private boolean s() {
      ClientPlayerEntity clientPlayerEntity1 = minecraftClient.player;
      return clientPlayerEntity1 != null && clientPlayerEntity1.isUsingItem() && this.a(clientPlayerEntity1.getActiveItem().getUseAction());
   }

   private boolean a(UseAction useAction1) {
      return useAction1 == UseAction.EAT || useAction1 == UseAction.DRINK || useAction1 == UseAction.BOW || useAction1 == UseAction.CROSSBOW;
   }

   private boolean a(RegistryEntry<StatusEffect> registryEntry1) {
      return registryEntry1 == StatusEffects.SPEED
         || registryEntry1 == StatusEffects.SLOWNESS
         || registryEntry1 == StatusEffects.JUMP_BOOST
         || registryEntry1 == StatusEffects.LEVITATION
         || registryEntry1 == StatusEffects.SLOW_FALLING
         || registryEntry1 == StatusEffects.DOLPHINS_GRACE
         || registryEntry1 == StatusEffects.BLINDNESS;
   }

   private void a(EventPacket eventPacket1) {
      Packet packet2 = eventPacket1.getPacket();
      if (eventPacket1.getType() == EventPacket.Type.RECEIVE) {
         this.b(eventPacket1);
      } else {
         if (packet2 instanceof PlayerInteractItemC2SPacket playerInteractItemC2SPacket3) {
            if (minecraftClient.player != null && this.a(minecraftClient.player.getStackInHand(playerInteractItemC2SPacket3.getHand()).getUseAction())) {
               this.t++;
               this.s = System.currentTimeMillis();
            }
         } else {
            if (packet2 instanceof PlayerActionC2SPacket playerActionC2SPacket4 && playerActionC2SPacket4.getAction() == Action.RELEASE_USE_ITEM) {
               if (this.o) {
                  this.t();
               }

               return;
            }

            if (packet2 instanceof TeleportConfirmC2SPacket) {
               if (this.o) {
                  this.t();
               }
            } else if (packet2 instanceof CommonPongC2SPacket commonPongC2SPacket5) {
               if (this.p) {
                  return;
               }

               Integer integer6 = (Integer)this.k.remove(commonPongC2SPacket5.getParameter());
               if (integer6 != null && integer6 == 1 && this.s()) {
                  if (this.o) {
                     this.u();
                  }

                  this.o = true;
                  this.u = this.t;
                  this.r = System.currentTimeMillis();
                  eventPacket1.setCancelled(true);
                  this.l.addLast(commonPongC2SPacket5);
                  return;
               }

               if (integer6 != null && this.o) {
                  this.t();
                  return;
               }

               if (this.o) {
                  eventPacket1.setCancelled(true);
                  this.l.addLast(commonPongC2SPacket5);
               }
            }
         }
      }
   }

   private void b(EventPacket eventPacket1) {
      if (!minecraftClient.isOnThread()) {
         Packet packet2 = eventPacket1.getPacket();
         if (packet2 instanceof CommonPingS2CPacket commonPingS2CPacket9) {
            int intValue10 = this.n;
            if (intValue10 != 0) {
               this.n = 0;
               if (this.k.size() > 64) {
                  this.k.clear();
               }

               this.k.put(commonPingS2CPacket9.getParameter(), intValue10);
            }
         } else if (packet2 instanceof GameJoinS2CPacket) {
            minecraftClient.execute(this::v);
         } else if (!(packet2 instanceof PlayerPositionLookS2CPacket) && !(packet2 instanceof PlayerRespawnS2CPacket)) {
            ClientPlayerEntity clientPlayerEntity3 = minecraftClient.player;
            if (clientPlayerEntity3 != null) {
               if (packet2 instanceof EntityTrackerUpdateS2CPacket entityTrackerUpdateS2CPacket4) {
                  if (entityTrackerUpdateS2CPacket4.id() == clientPlayerEntity3.getId()) {
                     for (SerializedEntry serializedEntry6 : entityTrackerUpdateS2CPacket4.trackedValues()) {
                        if (serializedEntry6.id() == 8 && serializedEntry6.value() instanceof Byte byte7) {
                           boolean flag11 = (byte7 & 1) != 0;
                           this.n = flag11 ? 1 : 2;
                        }
                     }
                  }
               } else {
                  if (!eventPacket1.isCancelled() && this.a(packet2, clientPlayerEntity3)) {
                     eventPacket1.setCancelled(true);
                     minecraftClient.execute(() -> this.a(packet2));
                  }
               }
            }
         } else {
            minecraftClient.execute(this::t);
         }
      }
   }

   private boolean a(Packet<?> packet1, ClientPlayerEntity clientPlayerEntity2) {
      int intValue3 = clientPlayerEntity2.getId();
      if (packet1 instanceof EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket9) {
         return entityVelocityUpdateS2CPacket9.getEntityId() == intValue3;
      } else if (packet1 instanceof ExplosionS2CPacket || packet1 instanceof PlayerAbilitiesS2CPacket) {
         return true;
      } else if (packet1 instanceof EntityStatusEffectS2CPacket entityStatusEffectS2CPacket8) {
         return entityStatusEffectS2CPacket8.getEntityId() == intValue3 && this.a(entityStatusEffectS2CPacket8.getEffectId());
      } else if (packet1 instanceof RemoveEntityStatusEffectS2CPacket removeEntityStatusEffectS2CPacket7) {
         return removeEntityStatusEffectS2CPacket7.entityId() == intValue3 && this.a(removeEntityStatusEffectS2CPacket7.effect());
      } else if (packet1 instanceof BlockUpdateS2CPacket blockUpdateS2CPacket6) {
         return this.a(blockUpdateS2CPacket6.getPos(), clientPlayerEntity2);
      } else if (packet1 instanceof ChunkDeltaUpdateS2CPacket chunkDeltaUpdateS2CPacket4) {
         boolean[] flagArray5 = new boolean[]{false};
         chunkDeltaUpdateS2CPacket4.visitUpdates((blockPos3x, blockState4x) -> {
            if (!flagArray5[0] && this.a(blockPos3x, clientPlayerEntity2)) {
               flagArray5[0] = true;
            }
         });
         return flagArray5[0];
      } else {
         return false;
      }
   }

   private boolean a(BlockPos blockPos1, ClientPlayerEntity clientPlayerEntity2) {
      return clientPlayerEntity2.squaredDistanceTo((double)blockPos1.getX() + 0.5, (double)blockPos1.getY() + 0.5, (double)blockPos1.getZ() + 0.5) <= 16.0;
   }

   private void a(Packet<?> packet1) {
      if (this.o) {
         this.m.addLast(packet1);
      } else {
         this.b(packet1);
      }
   }

   @SuppressWarnings({"rawtypes", "unchecked"})
   private void b(Packet<?> packet1) {
      ClientPlayNetworkHandler clientPlayNetworkHandler2 = minecraftClient.getNetworkHandler();
      if (clientPlayNetworkHandler2 != null) {
         ((Packet)packet1).apply(clientPlayNetworkHandler2);
      }
   }

   private void t() {
      if (this.o || !this.l.isEmpty() || !this.m.isEmpty()) {
         this.o = false;
         this.u();
      }
   }

   private void u() {
      ClientPlayNetworkHandler clientPlayNetworkHandler1 = minecraftClient.getNetworkHandler();
      if (clientPlayNetworkHandler1 == null) {
         this.l.clear();
         this.m.clear();
      } else {
         this.p = true;

         CommonPongC2SPacket commonPongC2SPacket2;
         try {
            while ((commonPongC2SPacket2 = (CommonPongC2SPacket)this.l.pollFirst()) != null) {
               clientPlayNetworkHandler1.sendPacket(commonPongC2SPacket2);
            }
         } finally {
            this.p = false;
         }

         Packet<?> packet6;
         while ((packet6 = (Packet<?>)this.m.pollFirst()) != null) {
            this.b(packet6);
         }
      }
   }

   private void v() {
      this.l.clear();
      this.m.clear();
      this.k.clear();
      this.n = 0;
      this.o = false;
   }

   private void w() {
      ClientPlayNetworkHandler clientPlayNetworkHandler1 = minecraftClient.getNetworkHandler();
      if (clientPlayNetworkHandler1 == null) {
         this.v();
      } else {
         ClientPlayerEntity clientPlayerEntity2 = minecraftClient.player;
         long longValue3 = System.currentTimeMillis();
         if (!clientPlayerEntity2.isUsingItem()) {
            this.q = false;
         }

         if (this.o) {
            if (longValue3 - this.r > 4000L) {
               this.t();
            } else {
               if (this.q()) {
                  clientPlayerEntity2.setSprinting(true);
               }
            }
         } else if (!this.q && this.u != this.t && this.s() && minecraftClient.options.useKey.isPressed()) {
            PlayerListEntry playerListEntry5 = clientPlayNetworkHandler1.getPlayerListEntry(clientPlayerEntity2.getUuid());
            long longValue6 = playerListEntry5 != null ? (long)playerListEntry5.getLatency() : 100L;
            if (longValue3 - this.s > longValue6 + 150L) {
               this.q = true;
               Hand hand8 = clientPlayerEntity2.getActiveHand();
               minecraftClient.interactionManager.stopUsingItem(clientPlayerEntity2);
               minecraftClient.interactionManager.interactItem(clientPlayerEntity2, hand8);
            }
         }
      }
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (this.d.b("GrimGrounded")) {
         this.a(eventPacket1);
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null) {
         if (this.d.b("GrimGrounded")) {
            this.w();
         } else {
            if (this.o || !this.l.isEmpty() || !this.m.isEmpty()) {
               this.t();
            }
         }
      }
   }

   @EventLink
   public void onSlowDown(EventSlowWalking eventSlowWalking1) {
      if (minecraftClient.player == null) {
         this.v = 0;
      } else if (this.d.b("GrimGrounded")) {
         if (this.r()) {
            eventSlowWalking1.setCancelled(true);
         }
      } else if (this.d.b("SkyTime")) {
         if (!minecraftClient.player.isUsingItem()) {
            this.v = 0;
         } else {
            this.v++;
            if (!minecraftClient.player.isTouchingWater() && !minecraftClient.player.isSubmergedInWater()) {
               if (minecraftClient.player.isSprinting()) {
                  minecraftClient.player.setSprinting(false);
                  minecraftClient.options.sprintKey.setPressed(false);
                  if (minecraftClient.getNetworkHandler() != null) {
                     minecraftClient.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.STOP_SPRINTING));
                  }
               }

               boolean flag2 = minecraftClient.player.input.movementForward != 0.0F || minecraftClient.player.input.movementSideways != 0.0F;
               if (flag2) {
                  eventSlowWalking1.setCancelled(true);
               }
            }
         }
      } else if (!minecraftClient.player.isUsingItem()) {
         this.v = 0;
      } else {
         if (this.d.b("Grim Last") && minecraftClient.player.getItemUseTime() % 2 == 0) {
            eventSlowWalking1.setCancelled(true);
         }
      }
   }
}
