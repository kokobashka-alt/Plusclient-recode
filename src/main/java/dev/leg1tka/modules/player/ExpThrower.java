package dev.leg1tka.modules.player;


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

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventMoveInput;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventPlayerSync;
import dev.leg1tka.api.events.implement.EventPlayerSyncEnd;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.PlayerInput;
public class ExpThrower extends Module {
   private static int d;
   private static Pattern e;
   private static Pattern f;
   public static final ExpThrower c = new ExpThrower();
   private static float g;
   private static int h;
   private static long i;
   private static long j;
   private static long k;
   private static int l;
   private static int m;
   final BooleanSetting n = new BooleanSetting("Обход Grim", true);
   private final NumberSetting o = new NumberSetting("Скорость наклона", 12.0F, 4.0F, 40.0F, 1.0F);
   private final ExpThrower.a p = new ExpThrower.a();
   private volatile int q;
   private volatile long r;
   private volatile long s;
   private volatile long t;
   private volatile long u;
   private volatile long v;
   private long w;
   private long x;
   private ExpState y = ExpState.NONE;
   private long z;
   private long A;
   private boolean B;
   private boolean C;
   private long D = 50L;
   private int E;
   private long F;
   private long G;
   private int H;
   private boolean I;
   private int J;
   int K = -1;
   private int L = -1;
   private ItemStack M = ItemStack.EMPTY;
   ClientPlayerEntity N;

   public ExpThrower() {
      super(
         "ExpThrower",
         "Опускает голову и кидает пузырьки опыта под себя с учётом КД и пинга",
         Category.PLAYER
      );
      this.addSettings(new Setting[]{this.n, this.o});
   }

   @Override
   public void onEnable() {
      boolean flag1 = this.p.b;
      this.p.b();
      this.I = false;
      this.J = 0;
      this.y = ExpState.NONE;
      this.z = 0L;
      this.B = false;
      this.C = false;
      this.D = 50L;
      this.E = 0;
      this.F = 0L;
      this.G = 0L;
      this.H = 0;
      this.w = this.r;
      this.x = this.t;
      this.N = minecraftClient.player;
      if (!flag1) {
         this.z();
         this.K = minecraftClient.player != null ? minecraftClient.player.getInventory().selectedSlot : -1;
      }

      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.I = false;
      this.v = 0L;
      super.onDisable();
      if (minecraftClient.player == null || minecraftClient.player != this.N) {
         this.z();
         this.K = -1;
      } else if (this.L >= 0) {
         this.p.a();
      } else {
         this.y();
      }
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (eventPacket1.getType() == EventPacket.Type.RECEIVE && eventPacket1.getPacket() != null) {
         if (eventPacket1.getPacket() instanceof net.minecraft.network.packet.s2c.play.GameMessageS2CPacket packet) {
            this.a(packet.content());
         }
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player == null) return;
      long now = System.currentTimeMillis();
      this.a(now);
      this.p();

      float currentPitch = minecraftClient.player.getPitch();
      float targetPitch = 90.0F;
      float speed = this.o.e().floatValue();
      float nextPitch = Math.min(targetPitch, currentPitch + speed);
      dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(minecraftClient.player.getYaw(), nextPitch), 360.0F, speed, 360.0F, speed, 1, 1, false);

      if (this.q()) {
         Hand hand = this.r();
         if (hand != null) {
            this.a(hand, now);
         }
      }
   }

   @EventLink(
      priority = -200
   )
   public void onMoveInput(EventMoveInput eventMoveInput1) {
      if (this.I && this.n.getValue()) {
         this.a(eventMoveInput1);
      }
   }

   @EventLink(
      priority = -200
   )
   public void onSync(EventPlayerSync eventPlayerSync1) {
      if (this.I && this.n.getValue() && minecraftClient.player != null) {
         minecraftClient.player.setSprinting(false);
      }
   }

   @EventLink(
      priority = -200
   )
   public void onSyncEnd(EventPlayerSyncEnd eventPlayerSyncEnd1) {
      if (this.I && this.n.getValue() && minecraftClient.player != null) {
         int slot = this.u();
         if (slot != -1) {
            this.b(slot);
            this.I = false;
         }
      }
   }

   private void a(Text text1) {
      long longValue2 = System.currentTimeMillis();
      if (longValue2 - this.u <= this.v) {
         String string4 = Formatting.strip(text1.getString());
         if (string4 != null && e.matcher(string4).find()) {
            long longValue5 = 0L;
            Matcher matcher7 = f.matcher(string4);

            while (matcher7.find()) {
               double doubleValue8 = Double.parseDouble(matcher7.group(1).replace(',', '.'));
               longValue5 += (long)(doubleValue8 * (double)c(matcher7.group(2).toLowerCase()));
            }

            if (longValue5 > 0L && longValue5 <= 60000L) {
               this.s = longValue5;
               this.t = longValue2;
            }
         }
      }
   }

   private static long c(String string0) {
      if (string0.equals("мс") || string0.equals("ms")) {
         return 1L;
      } else if (string0.startsWith("м") || string0.startsWith("m")) {
         return 60000L;
      } else {
         return !string0.startsWith("т") && !string0.startsWith("t") ? 1000L : 50L;
      }
   }

   private void a(long longValue1) {
      long longValue3 = this.s();
      long longValue5 = this.r;
      if (longValue5 != this.w) {
         this.w = longValue5;
         int intValue7 = this.q;
         if (intValue7 > 0) {
            this.y = ExpState.ITEM;
            this.E = 0;
            if (this.B && this.C) {
               this.D = Math.max(50L, this.D - 10L);
            }

            this.B = false;
            this.z = longValue5 + (long)intValue7 * 50L - longValue3 + this.D;
         } else if (!this.B) {
            this.z = 0L;
         }
      }

      longValue5 = this.t;
      if (longValue5 != this.x) {
         this.x = longValue5;
         long longValue12 = this.s;
         this.B = false;
         this.H = 0;
         this.z = Math.max(this.z, longValue5 + longValue12 - longValue3 + this.D);
         if (this.y != ExpState.ITEM) {
            this.y = ExpState.CHAT;
            long longValue9 = this.F > 0L ? longValue5 - this.F + longValue12 : longValue12;
            this.G = Math.max(longValue12, Math.min(longValue9, 60000L));
         }
      }

      if (this.B && longValue1 >= this.A) {
         this.B = false;
         this.b(longValue3);
      }
   }

   private void b(long longValue1) {
      long longValue3 = this.u;
      switch (this.y) {
         case NONE:
            this.F = longValue3;
            break;
         case ITEM:
            if (this.C) {
               this.D = Math.min(250L, this.D + 50L);
               this.z = System.currentTimeMillis() + 50L;
            } else if (++this.E >= 2) {
               this.y = ExpState.NONE;
               this.E = 0;
               this.z = 0L;
            }
            break;
         case CHAT:
            this.F = longValue3;
            if (++this.H >= 5) {
               this.H = 0;
               this.G = this.G * 3L / 4L;
            }

            if (this.G <= this.c(longValue1)) {
               this.y = ExpState.NONE;
               this.z = 0L;
            } else {
               this.z = longValue3 + this.G + this.D;
            }
      }
   }

   private void p() {
      if (!minecraftClient.player.getOffHandStack().isOf(Items.EXPERIENCE_BOTTLE)) {
         int intValue1 = minecraftClient.player.getInventory().selectedSlot;
         if (!this.n.getValue()) {
            if (minecraftClient.player.getMainHandStack().isOf(Items.EXPERIENCE_BOTTLE)) {
               return;
            }

            int intValue2 = this.t();
            if (intValue2 != -1) {
               minecraftClient.player.getInventory().selectedSlot = intValue2;
               this.J = 1;
               return;
            }
         } else if (minecraftClient.player.getInventory().getStack(0).isOf(Items.EXPERIENCE_BOTTLE)) {
            if (intValue1 != 0) {
               minecraftClient.player.getInventory().selectedSlot = 0;
               this.J = 1;
            }

            return;
         }

         if (this.u() != -1) {
            this.I = true;
         }
      }
   }

   private void a(Hand hand1, long longValue2) {
      if (!this.B && longValue2 >= this.z) {
         ItemStack itemStack4 = minecraftClient.player.getStackInHand(hand1);
         boolean flag5 = minecraftClient.player.getItemCooldownManager().isCoolingDown(itemStack4);
         if (!flag5 || this.z != 0L) {
            long longValue6 = this.s();
            this.v = this.c(longValue6) + 300L;
            this.u = longValue2;
            this.C = flag5;
            this.B = true;
            this.A = longValue2 + this.c(longValue6);
            if (flag5) {
               float floatValue8 = minecraftClient.player.getYaw();
               float floatValue9 = minecraftClient.player.getPitch();
               minecraftClient.interactionManager.sendSequencedPacket(minecraftClient.world, createSequencedPacketCreator185(hand1, floatValue8, floatValue9));
            } else {
               minecraftClient.interactionManager.interactItem(minecraftClient.player, hand1);
            }

            minecraftClient.player.swingHand(hand1);
         }
      }
   }

   private long c(long longValue1) {
      return Math.max(100L, longValue1 + 50L);
   }

   private boolean q() {
      if (minecraftClient.player.getPitch() < 80.0F) {
         return false;
      } else {
         dev.leg1tka.managers.ServerStorage serverStorage1 = AspectDLC.INSTANCE.serverStorage;
         return serverStorage1 == null || serverStorage1.c() >= 70.0F;
      }
   }

   private Hand r() {
      if (!minecraftClient.player.getMainHandStack().isOf(Items.EXPERIENCE_BOTTLE) || this.n.getValue() && minecraftClient.player.getInventory().selectedSlot != 0) {
         return minecraftClient.player.getOffHandStack().isOf(Items.EXPERIENCE_BOTTLE) ? Hand.OFF_HAND : null;
      } else {
         return Hand.MAIN_HAND;
      }
   }

   private long s() {
      PlayerListEntry playerListEntry1 = minecraftClient.getNetworkHandler() != null ? minecraftClient.getNetworkHandler().getPlayerListEntry(minecraftClient.player.getUuid()) : null;
      return playerListEntry1 != null ? (long)playerListEntry1.getLatency() : 100L;
   }

   private int t() {
      for (int intValue1 = 0; intValue1 < 9; intValue1++) {
         if (minecraftClient.player.getInventory().getStack(intValue1).isOf(Items.EXPERIENCE_BOTTLE)) {
            return intValue1;
         }
      }

      return -1;
   }

   private int u() {
      for (int intValue1 = 0; intValue1 < 36; intValue1++) {
         if (minecraftClient.player.getInventory().getStack(intValue1).isOf(Items.EXPERIENCE_BOTTLE)) {
            return intValue1;
         }
      }

      return -1;
   }

   void a(EventMoveInput eventMoveInput1) {
      eventMoveInput1.setForward(0.0F);
      eventMoveInput1.setStrafe(0.0F);
      eventMoveInput1.setJump(false);
   }

   boolean e(boolean flag1) {
      return minecraftClient.player.currentScreenHandler != minecraftClient.player.playerScreenHandler ? false : !flag1 || !this.v() && !minecraftClient.player.isSprinting() && !minecraftClient.player.hasVehicle();
   }

   private void b(int intValue1) {
      minecraftClient.interactionManager.clickSlot(0, intValue1 < 9 ? 36 + intValue1 : intValue1, 0, SlotActionType.SWAP, minecraftClient.player);
      minecraftClient.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(0));
   }

   private boolean v() {
      PlayerInput playerInput1 = minecraftClient.player.input.playerInput;
      return playerInput1.forward() || playerInput1.backward() || playerInput1.left() || playerInput1.right() || playerInput1.jump();
   }

   private int w() {
      if (this.M.isEmpty()) {
         return this.L;
      } else if (ItemStack.areItemsAndComponentsEqual(minecraftClient.player.getInventory().getStack(this.L), this.M)) {
         return this.L;
      } else {
         for (int intValue1 = 1; intValue1 < 36; intValue1++) {
            if (ItemStack.areItemsAndComponentsEqual(minecraftClient.player.getInventory().getStack(intValue1), this.M)) {
               return intValue1;
            }
         }

         return -1;
      }
   }

   void x() {
      ItemStack itemStack1 = minecraftClient.player.getInventory().getStack(0);
      if (itemStack1.isEmpty() || itemStack1.isOf(Items.EXPERIENCE_BOTTLE)) {
         int intValue2 = this.w();
         boolean flag3 = itemStack1.isEmpty() && intValue2 >= 0 && minecraftClient.player.getInventory().getStack(intValue2).isEmpty();
         boolean flag4 = this.M.isEmpty() && intValue2 >= 0 && !minecraftClient.player.getInventory().getStack(intValue2).isEmpty();
         if (intValue2 > 0 && !flag3 && !flag4) {
            this.b(intValue2);
         }
      }

      this.z();
   }

   void y() {
      if (minecraftClient.player != null && this.K >= 0 && this.K < 9) {
         minecraftClient.player.getInventory().selectedSlot = this.K;
      }

      this.K = -1;
   }

   void z() {
      this.L = -1;
      this.M = ItemStack.EMPTY;
   }



   private static SequencedPacketCreator createSequencedPacketCreator185(Hand hand0, float floatValue1, float floatValue2) {
      return intValue3 -> new PlayerInteractItemC2SPacket(hand0, intValue3, floatValue1, floatValue2);
   }

   final class a {
      boolean b;
      private boolean c;

      a() {
      }

      void a() {
         this.b = true;
         this.c = ExpThrower.this.n.getValue();
         EventInvoker.register(this);
      }

      void b() {
         if (this.b) {
            this.b = false;
            EventInvoker.unregister(this);
         }
      }

      @EventLink(
         priority = -200
      )
      public void onMoveInput(EventMoveInput eventMoveInput1) {
         if (this.c) {
            ExpThrower.this.a(eventMoveInput1);
         }
      }

      @EventLink(
         priority = -200
      )
      public void onSync(EventPlayerSync eventPlayerSync1) {
         if (dev.leg1tka.api.ClientAccess.minecraftClient.player != null && this.c) {
            dev.leg1tka.api.ClientAccess.minecraftClient.player.setSprinting(false);
         }
      }

      @EventLink(
         priority = -200
      )
      public void onSyncEnd(EventPlayerSyncEnd eventPlayerSyncEnd1) {
         if (dev.leg1tka.api.ClientAccess.minecraftClient.player == null || dev.leg1tka.api.ClientAccess.minecraftClient.player != ExpThrower.this.N) {
            ExpThrower.this.z();
            ExpThrower.this.K = -1;
            this.b();
         } else if (ExpThrower.this.e(this.c)) {
            ExpThrower.this.x();
            ExpThrower.this.y();
            this.b();
         }
      }
   }

   static enum ExpState {
      NONE,
      ITEM,
      CHAT;

      private ExpState() {
      }
   }
   public static void dev_leg1tka_o_jr_initializer() {}
}
