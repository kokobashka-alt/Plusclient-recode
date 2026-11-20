package dev.leg1tka.managers;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventPopTotem;
import dev.leg1tka.api.events.implement.EventTickPre;
import java.lang.reflect.InvocationTargetException;
import lombok.Generated;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerActionResponseS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.util.math.BlockPos;

public class ServerStorage implements dev.leg1tka.api.ClientAccess {
   private int c;
   private float d;
   private float e;
   private float f;
   private double g;
   private double h;
   private double i;
   private boolean j;
   private boolean k;
   private boolean l;
   private boolean m;
   private volatile BlockPos n;
   private volatile BlockPos o;
   private volatile int p;
   private volatile boolean q;

   public ServerStorage() {
      EventInvoker.register(this);
   }

   @EventLink
   public void onTick(EventTickPre eventTickPre1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         double doubleValue2 = minecraftClient.player.prevY - minecraftClient.player.getY();
         if (minecraftClient.player.isOnGround()) {
            this.f = 0.0F;
         } else if (doubleValue2 > 0.0) {
            this.f += (float)doubleValue2;
         }
      }
   }

   @EventLink
   public void onPacketSend(EventPacket eventPacket1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         if (eventPacket1.getPacket() instanceof PlayerMoveC2SPacket playerMoveC2SPacket2) {
            if (playerMoveC2SPacket2.changesPosition()) {
               this.g = playerMoveC2SPacket2.getX(minecraftClient.player.getX());
               this.h = playerMoveC2SPacket2.getY(minecraftClient.player.getY());
               this.i = playerMoveC2SPacket2.getZ(minecraftClient.player.getZ());
            }

            if (playerMoveC2SPacket2.changesLook()) {
               this.d = playerMoveC2SPacket2.getYaw(minecraftClient.player.getYaw());
               this.e = playerMoveC2SPacket2.getPitch(minecraftClient.player.getPitch());
            }

            this.j = playerMoveC2SPacket2.isOnGround();
            this.m = playerMoveC2SPacket2.horizontalCollision();
         }

         if (eventPacket1.getPacket() instanceof UpdateSelectedSlotC2SPacket updateSelectedSlotC2SPacket4) {
            this.c = updateSelectedSlotC2SPacket4.getSelectedSlot();
         }

         if (eventPacket1.getType() == EventPacket.Type.SEND && eventPacket1.getPacket() instanceof PlayerActionC2SPacket playerActionC2SPacket5) {
            switch (playerActionC2SPacket5.getAction()) {
               case START_DESTROY_BLOCK:
                  this.o = playerActionC2SPacket5.getPos().toImmutable();
                  this.n = null;
                  this.q = true;
                  break;
               case ABORT_DESTROY_BLOCK:
                  this.n = playerActionC2SPacket5.getPos().toImmutable();
                  this.q = false;
                  break;
               case STOP_DESTROY_BLOCK:
                  if (playerActionC2SPacket5.getPos().equals(this.o)) {
                     this.q = false;
                  }
            }
         }

         if (eventPacket1.getPacket() instanceof ClientCommandC2SPacket clientCommandC2SPacket6) {
            switch (clientCommandC2SPacket6.getMode()) {
               case START_SPRINTING:
                  if (this.k) {
                     eventPacket1.cancel();
                  }

                  this.k = true;
                  break;
               case STOP_SPRINTING:
                  if (!this.k) {
                     eventPacket1.cancel();
                  }

                  this.k = false;
                  break;
               case PRESS_SHIFT_KEY:
                  this.l = true;
                  break;
               case RELEASE_SHIFT_KEY:
                  this.l = false;
            }
         }
      }
   }

   @EventLink
   public void onPacketReceive(EventPacket eventPacket1) throws InvocationTargetException, IllegalAccessException, InstantiationException {
      if (eventPacket1.getType() == EventPacket.Type.RECEIVE) {
         if (eventPacket1.getPacket() instanceof PlayerActionResponseS2CPacket playerActionResponseS2CPacket2) {
            this.p = Math.max(this.p, playerActionResponseS2CPacket2.sequence());
         } else if (eventPacket1.getPacket() instanceof PlayerRespawnS2CPacket) {
            this.p = 0;
            this.q = false;
         } else if (eventPacket1.getPacket() instanceof GameJoinS2CPacket) {
            this.p = 0;
            this.n = null;
            this.o = null;
            this.q = false;
         }
      }

      if (minecraftClient.player != null && minecraftClient.world != null) {
         if (eventPacket1.getPacket() instanceof EntityStatusS2CPacket entityStatusS2CPacket5 && entityStatusS2CPacket5.getStatus() == 35) {
            if (!(entityStatusS2CPacket5.getEntity(minecraftClient.world) instanceof PlayerEntity playerEntity7)) {
               return;
            }

            EventInvoker.invoke(new EventPopTotem(playerEntity7));
         }
      }
   }

   @Generated
   public int a() {
      return this.c;
   }

   @Generated
   public float b() {
      return this.d;
   }

   @Generated
   public float c() {
      return this.e;
   }

   @Generated
   public float d() {
      return this.f;
   }

   @Generated
   public double e() {
      return this.g;
   }

   @Generated
   public double f() {
      return this.h;
   }

   @Generated
   public double g() {
      return this.i;
   }

   @Generated
   public boolean h() {
      return this.j;
   }

   @Generated
   public boolean i() {
      return this.k;
   }

   @Generated
   public boolean j() {
      return this.l;
   }

   @Generated
   public boolean k() {
      return this.m;
   }

   @Generated
   public BlockPos l() {
      return this.n;
   }

   @Generated
   public BlockPos m() {
      return this.o;
   }

   @Generated
   public int n() {
      return this.p;
   }

   @Generated
   public boolean o() {
      return this.q;
   }
}
