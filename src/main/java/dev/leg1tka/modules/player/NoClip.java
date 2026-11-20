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

import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventMoveInput;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventPlayerSyncEnd;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import net.minecraft.block.BlockState;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import net.minecraft.network.packet.s2c.play.PlayerActionResponseS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Direction.Axis;

public class NoClip extends Module {
   public static NoClip c = new NoClip();
   private static String d;
   private static String e;
   private static int f;
   private static int g;
   private static int h;
   private static int i;
   private static int j;
   private static int k;
   private static long l;
   private static double m;
   private static double n;
   private static int o;
   private static int p;
   private static int q;
   private static int r;
   private static int s;
   private static int t;
   private static double u;
   private static double v;
   private static double w;
   private static double x;
   private final ModeSetting y = new ModeSetting(
      "Режим",
      "Удержание",
      "Удержание",
      "Вперёд"
   );
   private final NumberSetting z = new NumberSetting("Глубина (блоков)", 1.0F, 1.0F, 10.0F, 1.0F);
   private final NumberSetting A = new NumberSetting(
         "Окно удержания (тики)", 10.0F, 1.0F, 20.0F, 1.0F
      )
      .a(() -> this.y.b("Удержание"));
   private final NumberSetting B = new NumberSetting("Буст флага", 0.0F, 0.0F, 0.3F, 0.01F)
      .a(() -> this.y.b("Удержание"));
   private final NumberSetting C = new NumberSetting("Макс. циклов", 30.0F, 1.0F, 100.0F, 1.0F);
   private final NumberSetting D = new NumberSetting(
         "Зазор разбега (блоков)", 0.4F, 0.2F, 1.0F, 0.05F
      )
      .a(() -> this.y.b("Вперёд"));
   private final NumberSetting E = new NumberSetting(
         "Макс. задержка пинга (мс)",
         1500.0F,
         300.0F,
         5000.0F,
         100.0F
      )
      .a(() -> this.y.b("Вперёд"));
   private final NumberSetting F = new NumberSetting(
      "Задержка попытки (мс)", 0.0F, 0.0F, 2000.0F, 50.0F
   );
   private final BooleanSetting G = new BooleanSetting("Центрировать", false)
      .visibleWhen(() -> this.y.b("Удержание"));
   private final BooleanSetting H = new BooleanSetting("Спринт при проходе", true)
      .visibleWhen(() -> this.y.b("Вперёд"));
   private final BooleanSetting I = new BooleanSetting("Автосвап инструмента", true);
   private final BooleanSetting J = new BooleanSetting("Ломать моментально", true)
      .visibleWhen(() -> this.y.b("Удержание"));
   private final BooleanSetting K = new BooleanSetting("Скрытие действий", false)
      .visibleWhen(() -> this.y.b("Удержание"));
   private final BooleanSetting L = new BooleanSetting("Отладка", true).visibleWhen(() -> this.y.b("Вперёд"));
   private ClipState M = ClipState.IDLE;
   private BlockPos N;
   private BlockState O;
   private double P;
   private int Q;
   private int R;
   private int S;
   private int T;
   private int U = -1;
   private long V;
   private long W;
   private int X = -1;
   private boolean Y;
   private volatile boolean Z;
   private volatile boolean ModuleStorage;
   private volatile int ab;
   private boolean ac;
   private boolean ad;
   private int ae = -1;
   private int af = -1;
   private int ag = -1;
   private boolean ah;
   private float Storage;
   private final List<BlockPos> ModuleManager = new ArrayList<>(6);
   private final List<BlockState> ModuleRegistry = new ArrayList<>(6);
   private final List<Boolean> al = new ArrayList<>(6);
   private final List<Integer> am = new ArrayList<>(6);
   private int an;
   private int ao = -1;
   private BlockPos ap;
   private Direction aq;
   private BlockPos ar;
   private double as;
   private double at;
   private boolean au;
   private double av;
   private int aw;
   private final Object ax = new Object();
   private final ConcurrentLinkedDeque ay = new ConcurrentLinkedDeque();
   private volatile boolean az;
   private long aA;

   public NoClip() {
      super(
         "NoClip",
         "Клип сквозь блоки через Grim",
         Category.PLAYER
      );
      this.addSettings(new Setting[]{this.y, this.z, this.A, this.B, this.C, this.D, this.E, this.F, this.G, this.H, this.I, this.J, this.K, this.L});
   }

   public static boolean p() {
      return c.isEnabled() && c.y.b("Вперёд") && c.M.ordinal() >= ClipState.POSITION.ordinal();
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.T = 0;
      this.S = 0;
      this.V = 0L;
      this.ac = false;
      this.ad = false;
      this.a(ClipState.IDLE);
   }

   @Override
   public void onDisable() {
      this.W();
      this.K();
      super.onDisable();
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null && minecraftClient.interactionManager != null) {
         this.Q++;
         if (this.y.b("Вперёд")) {
            if (this.M.ordinal() > ClipState.IDLE.ordinal() && this.M.ordinal() < ClipState.POSITION.ordinal()) {
               this.W();
            }

            this.r();
         } else {
            if (this.M.ordinal() >= ClipState.POSITION.ordinal() || this.az) {
               this.W();
            }

            switch (this.M) {
               case IDLE:
                  this.I();
                  break;
               case TOOL:
                  this.J();
                  break;
               case CENTER:
                  this.L();
                  break;
               case AIM:
                  this.M();
                  break;
               case MINE:
                  this.P();
                  break;
               case HOLD:
                  this.R();
                  break;
               case SETTLE:
                  this.S();
            }
         }
      }
   }

   @EventLink(
      priority = -100
   )
   public void onSyncEnd(EventPlayerSyncEnd eventPlayerSyncEnd1) {
      if (minecraftClient.player != null) {
         if (this.M == ClipState.HOLD && this.Y) {
            this.Y = false;
            minecraftClient.player
               .networkHandler
               .sendPacket(new PositionAndOnGround(minecraftClient.player.getX(), minecraftClient.player.getY() + (double)this.B.f(), minecraftClient.player.getZ(), false, minecraftClient.player.horizontalCollision));
         }
      }
   }

   @EventLink(
      priority = 200
   )
   public void onPacket(EventPacket eventPacket1) {
      if (eventPacket1.getType() == EventPacket.Type.SEND) {
         if (eventPacket1.getPacket() instanceof CommonPongC2SPacket commonPongC2SPacket6 && this.az) {
            synchronized (this.ax) {
               if (!this.az) {
                  return;
               }

               this.ay.addLast(commonPongC2SPacket6);
               eventPacket1.cancel();
               return;
            }
         }
      } else {
         if (eventPacket1.getPacket() instanceof PlayerPositionLookS2CPacket playerPositionLookS2CPacket2) {
            this.ab = playerPositionLookS2CPacket2.teleportId();
            if (this.az) {
               this.C();
            }

            if (this.M == ClipState.HOLD || this.M == ClipState.SETTLE || this.M == ClipState.CONTACT || this.M == ClipState.ENTER || this.M == ClipState.PASS) {
               this.Z = true;
            }
         } else if (eventPacket1.getPacket() instanceof PlayerActionResponseS2CPacket && this.M == ClipState.HOLD) {
            this.ModuleStorage = true;
         }
      }
   }

   @EventLink(
      priority = -100
   )
   public void onMoveInput(EventMoveInput eventMoveInput1) {
      if (minecraftClient.player != null && this.M != ClipState.IDLE) {
         eventMoveInput1.setJump(false);
         eventMoveInput1.setSneak(false);
         if (this.y.b("Вперёд")) {
            eventMoveInput1.setStrafe(0.0F);
            switch (this.M) {
               case POSITION:
                  minecraftClient.player.setSprinting(false);
                  if (System.currentTimeMillis() < this.V) {
                     eventMoveInput1.setForward(0.0F);
                  } else {
                     this.a(eventMoveInput1, this.as, this.at);
                  }
                  break;
               case PREMINE:
               case PRIME:
               case ENTER:
               default:
                  minecraftClient.player.setSprinting(false);
                  eventMoveInput1.setForward(0.0F);
                  break;
               case APPROACH:
                  minecraftClient.player.setSprinting(false);
                  eventMoveInput1.setForward(this.au ? 1.0F : 0.0F);
                  break;
               case CONTACT:
                  minecraftClient.player.setSprinting(false);
                  eventMoveInput1.setForward(1.0F);
                  break;
               case PASS:
                  eventMoveInput1.setForward(1.0F);
                  if (this.H.getValue()) {
                     minecraftClient.player.setSprinting(true);
                  }
            }
         } else if (this.M == ClipState.CENTER && this.N != null) {
            minecraftClient.player.setSprinting(false);
            this.a(eventMoveInput1, (double)this.N.getX() + 0.5, (double)this.N.getZ() + 0.5);
         } else {
            eventMoveInput1.setForward(0.0F);
            eventMoveInput1.setStrafe(0.0F);
         }
      }
   }

   private void a(EventMoveInput eventMoveInput1, double doubleValue2, double doubleValue4) {
      float floatValue6 = this.q();
      double doubleValue7 = (double)floatValue6 * 0.91;
      double doubleValue9 = 1.0 / (1.0 - doubleValue7);
      double doubleValue11 = 0.98 * (double)minecraftClient.player.getMovementSpeed() * (0.21600002 / (double)(floatValue6 * floatValue6 * floatValue6)) * doubleValue9;
      double doubleValue13 = doubleValue2 - minecraftClient.player.getX() - minecraftClient.player.getVelocity().x * doubleValue9;
      double doubleValue15 = doubleValue4 - minecraftClient.player.getZ() - minecraftClient.player.getVelocity().z * doubleValue9;
      double doubleValue17 = Math.max(Math.abs(doubleValue13), Math.abs(doubleValue15));
      float floatValue19 = 0.0F;
      float floatValue20 = 0.0F;
      if (doubleValue17 > 0.1) {
         float floatValue21 = minecraftClient.player.getYaw() * (float) (Math.PI / 180.0);
         double doubleValue22 = (double)MathHelper.sin(floatValue21);
         double doubleValue24 = (double)MathHelper.cos(floatValue21);

         for (int intValue26 = -1; intValue26 <= 1; intValue26++) {
            for (int intValue27 = -1; intValue27 <= 1; intValue27++) {
               if (intValue26 != 0 || intValue27 != 0) {
                  double doubleValue28 = doubleValue11 / Math.sqrt((double)(intValue26 * intValue26 + intValue27 * intValue27));
                  double doubleValue30 = Math.max(
                     Math.abs(doubleValue13 - ((double)intValue27 * doubleValue24 - (double)intValue26 * doubleValue22) * doubleValue28),
                     Math.abs(doubleValue15 - ((double)intValue26 * doubleValue24 + (double)intValue27 * doubleValue22) * doubleValue28)
                  );
                  if (doubleValue30 < doubleValue17 - 0.001) {
                     doubleValue17 = doubleValue30;
                     floatValue19 = (float)intValue26;
                     floatValue20 = (float)intValue27;
                  }
               }
            }
         }
      }

      eventMoveInput1.setForward(floatValue19);
      eventMoveInput1.setStrafe(floatValue20);
   }

   private float q() {
      return minecraftClient.world.getBlockState(minecraftClient.player.getVelocityAffectingPos()).getBlock().getSlipperiness();
   }

   private void r() {
      if (this.az && System.currentTimeMillis() - this.aA > (long)this.E.f()) {
         this.c(
            "пинг держится дольше лимита"
         );
      } else {
         switch (this.M) {
            case IDLE:
               this.s();
            case TOOL:
            case CENTER:
            case AIM:
            case MINE:
            case HOLD:
            case SETTLE:
            default:
               break;
            case POSITION:
               this.u();
               break;
            case PREMINE:
               this.v();
               break;
            case PRIME:
               this.w();
               break;
            case APPROACH:
               this.x();
               break;
            case CONTACT:
               this.F();
               this.a(ClipState.ENTER);
               break;
            case ENTER:
               this.z();
               break;
            case PASS:
               this.A();
         }
      }
   }

   private void s() {
      if (System.currentTimeMillis() >= this.V && minecraftClient.player.isOnGround()) {
         this.aq = Direction.fromHorizontalDegrees((double)dev.leg1tka.rotation.Rotation.b());
         BlockPos blockPos1 = minecraftClient.player.getBlockPos();

         for (int intValue2 = 1; intValue2 <= 2; intValue2++) {
            if (this.a(blockPos1.offset(this.aq, intValue2))) {
               if (!this.t()) {
                  return;
               }

               this.S = 0;
               this.a((double)this.D.f());
               this.a(ClipState.POSITION);
               return;
            }
         }

         this.d("впереди нет стены");
         this.setEnabled(false);
      }
   }

   private boolean a(BlockPos blockPos1) {
      this.ModuleManager.clear();
      this.ModuleRegistry.clear();
      this.ar = blockPos1.toImmutable();
      Box box2 = minecraftClient.player.getBoundingBox().contract(0.001);
      boolean flag3 = this.aq.getAxis() == Axis.X;
      int intValue4 = MathHelper.floor(flag3 ? box2.minZ : box2.minX);
      int intValue5 = MathHelper.floor(flag3 ? box2.maxZ : box2.maxX);
      int intValue6 = MathHelper.floor(box2.minY);
      int intValue7 = MathHelper.floor(box2.maxY);

      for (int intValue8 = intValue6; intValue8 <= intValue7; intValue8++) {
         for (int intValue9 = intValue4; intValue9 <= intValue5; intValue9++) {
            BlockPos blockPos10 = flag3 ? new BlockPos(blockPos1.getX(), intValue8, intValue9) : new BlockPos(intValue9, intValue8, blockPos1.getZ());
            BlockState blockState11 = minecraftClient.world.getBlockState(blockPos10);
            if (!blockState11.getCollisionShape(minecraftClient.world, blockPos10).isEmpty()) {
               this.ModuleManager.add(blockPos10);
               this.ModuleRegistry.add(blockState11);
            }
         }
      }

      return !this.ModuleManager.isEmpty();
   }

   private boolean t() {
      this.al.clear();
      this.am.clear();
      this.ao = -1;
      int intValue1 = minecraftClient.player.getInventory().selectedSlot;

      for (int intValue2 = 0; intValue2 < this.ModuleManager.size(); intValue2++) {
         BlockPos blockPos3 = (BlockPos)this.ModuleManager.get(intValue2);
         BlockState blockState4 = (BlockState)this.ModuleRegistry.get(intValue2);
         if (!this.a(blockState4, blockPos3)) {
            this.d("блок не ломается: " + blockState4.getBlock().getName().getString());
            this.setEnabled(false);
            return false;
         }

         int intValue5 = this.I.getValue() ? dev.leg1tka.api.utils.player.ItemAttributes.a(blockState4) : -1;
         if (intValue5 >= 0) {
            minecraftClient.player.getInventory().selectedSlot = intValue5;
         }

         this.al.add(this.b(blockPos3));
         minecraftClient.player.getInventory().selectedSlot = intValue1;
      }

      for (int intValue6 = 0; intValue6 < this.ModuleManager.size(); intValue6++) {
         if (!(Boolean)this.al.get(intValue6) && (this.ao < 0 || ((BlockPos)this.ModuleManager.get(intValue6)).getY() < ((BlockPos)this.ModuleManager.get(this.ao)).getY())) {
            this.ao = intValue6;
         }
      }

      for (int intValue7 = 0; intValue7 < this.ModuleManager.size(); intValue7++) {
         if (!(Boolean)this.al.get(intValue7) && intValue7 != this.ao) {
            this.am.add(intValue7);
         }
      }

      this.an = 0;
      this.ap = null;
      this.d(
         String.format(
            "стена: %d блок(ов), заранее %d, последний %s",
            this.ModuleManager.size(),
            this.am.size(),
            this.ao < 0 ? "мгновенный" : "докопать"
         )
      );
      return true;
   }

   private void a(double doubleValue1) {
      double doubleValue3 = (double)minecraftClient.player.getWidth() * 0.5 + doubleValue1;
      this.as = this.aq.getAxis() == Axis.X
         ? (this.aq.getOffsetX() > 0 ? (double)this.ar.getX() - doubleValue3 : (double)(this.ar.getX() + 1) + doubleValue3)
         : minecraftClient.player.getX();
      this.at = this.aq.getAxis() == Axis.Z
         ? (this.aq.getOffsetZ() > 0 ? (double)this.ar.getZ() - doubleValue3 : (double)(this.ar.getZ() + 1) + doubleValue3)
         : minecraftClient.player.getZ();
   }

   private void u() {
      this.F();
      if (this.Q > 60) {
         this.c("не встал на зазор разбега");
      } else {
         double doubleValue1 = minecraftClient.player.getVelocity().x;
         double doubleValue3 = minecraftClient.player.getVelocity().z;
         if (minecraftClient.player.isOnGround() && System.currentTimeMillis() >= this.V && !(doubleValue1 * doubleValue1 + doubleValue3 * doubleValue3 >= 1.0E-4)) {
            if (!(Math.abs(minecraftClient.player.getX() - this.as) > 0.12) && !(Math.abs(minecraftClient.player.getZ() - this.at) > 0.12)) {
               if (!(new dev.leg1tka.rotation.Rotation(minecraftClient.player).a(this.G()) >= 1.0F)) {
                  if (!this.am.isEmpty()) {
                     this.a(ClipState.PREMINE);
                  } else if (this.ao >= 0) {
                     this.a(ClipState.PRIME);
                  } else {
                     this.a(ClipState.APPROACH);
                  }
               }
            }
         }
      }
   }

   private void v() {
      int intValue1 = (Integer)this.am.get(this.an);
      BlockPos blockPos2 = (BlockPos)this.ModuleManager.get(intValue1);
      this.ap = blockPos2;
      this.F();
      if (this.Q > this.b(intValue1)) {
         this.c("не докопал верхний блок");
      } else {
         if (this.I.getValue()) {
            this.a((BlockState)this.ModuleRegistry.get(intValue1));
         }

         if (!(new dev.leg1tka.rotation.Rotation(minecraftClient.player).a(this.G()) >= 1.0F)) {
            dev.leg1tka.api.utils.player.BlockBreakingState.a();
            minecraftClient.interactionManager.blockBreakingCooldown = 0;
            if (minecraftClient.interactionManager.isBreakingBlock()) {
               minecraftClient.interactionManager.updateBlockBreakingProgress(blockPos2, this.aq.getOpposite());
            } else {
               minecraftClient.interactionManager.attackBlock(blockPos2, this.aq.getOpposite());
            }

            minecraftClient.player.swingHand(Hand.MAIN_HAND);
            if (minecraftClient.world.getBlockState(blockPos2).isAir()) {
               this.c(intValue1);
               this.B();
               this.W = System.currentTimeMillis();
               this.d(
                  "верхний блок сломан, держу пинг"
               );
               if (++this.an < this.am.size()) {
                  this.a(ClipState.PREMINE);
               } else {
                  this.a(this.ao >= 0 ? ClipState.PRIME : ClipState.APPROACH);
               }
            }
         }
      }
   }

   private void w() {
      BlockPos blockPos1 = (BlockPos)this.ModuleManager.get(this.ao);
      this.ap = blockPos1;
      this.F();
      if (this.Q > this.b(this.ao)) {
         this.c("не подготовил нижний блок");
      } else {
         if (this.I.getValue()) {
            this.a((BlockState)this.ModuleRegistry.get(this.ao));
         }

         if (!(new dev.leg1tka.rotation.Rotation(minecraftClient.player).a(this.G()) >= 1.0F)) {
            dev.leg1tka.api.utils.player.BlockBreakingState.a();
            minecraftClient.interactionManager.blockBreakingCooldown = 0;
            if (!minecraftClient.interactionManager.isBreakingBlock()) {
               minecraftClient.interactionManager.attackBlock(blockPos1, this.aq.getOpposite());
               minecraftClient.player.swingHand(Hand.MAIN_HAND);
            } else {
               float floatValue2 = minecraftClient.world.getBlockState(blockPos1).calcBlockBreakingDelta(minecraftClient.player, minecraftClient.world, blockPos1);
               if (minecraftClient.interactionManager.currentBreakingProgress + floatValue2 >= 1.0F) {
                  this.d(
                     "нижний блок готов к последнему удару"
                  );
                  this.a(ClipState.APPROACH);
               } else {
                  minecraftClient.interactionManager.updateBlockBreakingProgress(blockPos1, this.aq.getOpposite());
                  minecraftClient.player.swingHand(Hand.MAIN_HAND);
               }
            }
         }
      }
   }

   private int b(int intValue1) {
      return (int)Math.min(400.0F, dev.leg1tka.api.utils.player.ItemAttributes.a((BlockState)this.ModuleRegistry.get(intValue1), (BlockPos)this.ModuleManager.get(intValue1)) * 2.0F + 40.0F);
   }

   private void x() {
      if (this.ao >= 0) {
         dev.leg1tka.api.utils.player.BlockBreakingState.a();
      }

      this.F();
      if (minecraftClient.player.isOnGround() && this.Q <= 40) {
         double doubleValue1 = this.H();
         double doubleValue3 = this.b(minecraftClient.player.getVelocity().x, minecraftClient.player.getVelocity().z);
         int intValue5 = this.a(doubleValue1, doubleValue3, this.y(), (double)this.q() * 0.91);
         if (intValue5 < 0) {
            this.c(
               String.format(
                  "нет пути разбега (зазор %.3f)", doubleValue1
               )
            );
         } else if (intValue5 == 2) {
            this.a(doubleValue1, doubleValue3);
         } else {
            this.au = intValue5 == 1;
         }
      } else {
         this.c("сбился разбег");
      }
   }

   private int a(double doubleValue1, double doubleValue3, double doubleValue5, double doubleValue7) {
      if (doubleValue1 >= 0.0 && doubleValue3 - doubleValue1 >= 0.01) {
         return 2;
      } else {
         for (int intValue9 = 1; intValue9 >= 0; intValue9--) {
            double doubleValue10 = (Math.abs(doubleValue3) < 0.003 ? 0.0 : doubleValue3) + (double)intValue9 * doubleValue5;
            double doubleValue12 = doubleValue1 - doubleValue10;
            if (!(doubleValue12 < 0.005) && this.a(doubleValue12, doubleValue10 * doubleValue7, doubleValue5, doubleValue7, 12)) {
               return intValue9;
            }
         }

         return -1;
      }
   }

   private boolean a(double doubleValue1, double doubleValue3, double doubleValue5, double doubleValue7, int intValue9) {
      if (doubleValue3 - doubleValue1 >= 0.01) {
         return true;
      } else if (intValue9 <= 0) {
         return false;
      } else {
         for (int intValue10 = 1; intValue10 >= 0; intValue10--) {
            double doubleValue11 = (Math.abs(doubleValue3) < 0.003 ? 0.0 : doubleValue3) + (double)intValue10 * doubleValue5;
            if (!(doubleValue11 <= 0.0)) {
               double doubleValue13 = doubleValue1 - doubleValue11;
               if (!(doubleValue13 < 0.005) && this.a(doubleValue13, doubleValue11 * doubleValue7, doubleValue5, doubleValue7, intValue9 - 1)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private double y() {
      float floatValue1 = this.q();
      return 0.98 * (double)minecraftClient.player.getMovementSpeed() * (0.21600002 / (double)(floatValue1 * floatValue1 * floatValue1));
   }

   private void a(double doubleValue1, double doubleValue3) {
      this.Z = false;
      if (this.ao >= 0) {
         BlockPos blockPos5 = (BlockPos)this.ModuleManager.get(this.ao);
         minecraftClient.interactionManager.blockBreakingCooldown = 0;
         minecraftClient.interactionManager.updateBlockBreakingProgress(blockPos5, this.aq.getOpposite());
         if (!minecraftClient.world.getBlockState(blockPos5).isAir()) {
            this.c(
               "нижний блок не сломался последним ударом"
            );
            return;
         }

         this.c(this.ao);
      }

      for (int intValue7 = 0; intValue7 < this.ModuleManager.size(); intValue7++) {
         if ((Boolean)this.al.get(intValue7)) {
            BlockPos blockPos6 = (BlockPos)this.ModuleManager.get(intValue7);
            if (this.I.getValue()) {
               this.a((BlockState)this.ModuleRegistry.get(intValue7));
            }

            minecraftClient.interactionManager.blockBreakingCooldown = 0;
            minecraftClient.interactionManager.attackBlock(blockPos6, this.aq.getOpposite());
            if (!minecraftClient.world.getBlockState(blockPos6).isAir()) {
               this.c(
                  "блок не сломался мгновенно"
               );
               return;
            }

            this.c(intValue7);
         }
      }

      minecraftClient.interactionManager.blockBreakingCooldown = 0;
      minecraftClient.player.swingHand(Hand.MAIN_HAND);
      this.W = System.currentTimeMillis();
      this.av = doubleValue3 - doubleValue1;
      this.d(
         String.format(
            "ломаю: зазор %.4f, скорость %.4f -> вход %.4f",
            doubleValue1,
            doubleValue3,
            this.av
         )
      );
      this.a(ClipState.CONTACT);
   }

   private void c(int intValue1) {
      BlockPos blockPos2 = (BlockPos)this.ModuleManager.get(intValue1);
      minecraftClient.world.getPendingUpdateManager().blockPosToPendingUpdate.remove(blockPos2.asLong());
      minecraftClient.world.setBlockState(blockPos2, (BlockState)this.ModuleRegistry.get(intValue1), 3);
   }

   private void z() {
      this.F();
      if (!this.Z) {
         if (this.Q > 30) {
            this.c("отката не было");
         }
      } else {
         this.Z = false;
         int intValue1 = this.ab;
         this.d(
            String.format(
               "телепорт %s (AutoEZ %d): глубина %.4f (ждал %.4f)",
               intValue1 < 0 ? "Grim" : "сервер",
               intValue1,
               -this.H(),
               this.av
            )
         );
         if (!this.E()) {
            this.c("не внутри стены");
         } else {
            this.aw = 0;
            this.a(ClipState.PASS);
         }
      }
   }

   private void A() {
      this.F();
      if (this.Z) {
         this.Z = false;
         int intValue1 = this.ab;
         this.d(
            String.format(
               "телепорт при проходе %s (AutoEZ %d), глубина %.4f",
               intValue1 < 0 ? "Grim" : "сервер",
               intValue1,
               -this.H()
            )
         );
      }

      double doubleValue5 = -this.H();
      if (doubleValue5 >= 1.0 + (double)minecraftClient.player.getWidth() - 0.001) {
         this.T++;
         this.d("прошёл блок " + this.T);
         this.setEnabled(false);
      } else if (doubleValue5 <= 0.0) {
         this.c("вытолкнуло назад");
      } else if (this.Q > 120) {
         this.c("таймаут прохода");
      } else {
         double doubleValue3 = this.b(minecraftClient.player.getVelocity().x, minecraftClient.player.getVelocity().z);
         this.aw = Math.abs(doubleValue3) < 0.001 ? this.aw + 1 : 0;
         if (this.aw >= 4) {
            this.T++;
            if (this.T >= (int)this.z.f() || !this.a(this.ar.offset(this.aq))) {
               this.d(
                  "упёрся внутри блока, пройдено "
                     + this.T
               );
               this.setEnabled(false);
            } else if (this.t()) {
               this.d(
                  "следующий блок, разбег внутри"
               );
               this.a(Math.min((double)this.D.f(), 0.8));
               this.a(ClipState.POSITION);
            }
         }
      }
   }

   private void B() {
      if (!this.az) {
         this.aA = System.currentTimeMillis();
         this.az = true;
      }
   }

   private void C() {
      synchronized (this.ax) {
         this.az = false;

         CommonPongC2SPacket commonPongC2SPacket2;
         while ((commonPongC2SPacket2 = (CommonPongC2SPacket)this.ay.pollFirst()) != null) {
            if (minecraftClient.getNetworkHandler() != null) {
               dev.leg1tka.network.PacketSender.a(commonPongC2SPacket2);
            }
         }
      }
   }

   private void c(String string1) {
      this.d(string1);
      this.D();
      if (++this.S >= (int)this.C.f()) {
         this.setEnabled(false);
      } else {
         this.V = System.currentTimeMillis() + (long)this.F.f();
         this.a(ClipState.IDLE);
      }
   }

   private void D() {
      if (minecraftClient.interactionManager != null && minecraftClient.interactionManager.isBreakingBlock()) {
         minecraftClient.interactionManager.cancelBlockBreaking();
      }

      this.C();
      this.U();
      this.Z = false;
   }

   private boolean E() {
      Box box1 = minecraftClient.player.getBoundingBox().contract(1.0E-4);

      for (BlockPos blockPos3 : this.ModuleManager) {
         if (box1.intersects(new Box(blockPos3))) {
            return true;
         }
      }

      return false;
   }

   private void F() {
      this.a(this.G());
   }

   private dev.leg1tka.rotation.Rotation G() {
      double doubleValue1;
      if (this.ap != null) {
         doubleValue1 = (double)this.ap.getY() + 0.5;
      } else {
         int intValue3 = Integer.MAX_VALUE;
         int intValue4 = Integer.MIN_VALUE;

         for (BlockPos blockPos6 : this.ModuleManager) {
            intValue3 = Math.min(intValue3, blockPos6.getY());
            intValue4 = Math.max(intValue4, blockPos6.getY() + 1);
         }

         doubleValue1 = (double)(intValue3 + intValue4) * 0.5;
      }

      double doubleValue7 = Math.max(0.05, this.H() + (double)minecraftClient.player.getWidth() * 0.5);
      float floatValue8 = (float)(-Math.toDegrees(Math.atan2(doubleValue1 - minecraftClient.player.getEyeY(), doubleValue7)));
      return new dev.leg1tka.rotation.Rotation(this.aq.getPositiveHorizontalDegrees(), MathHelper.clamp(floatValue8, -90.0F, 90.0F));
   }

   private double H() {
      double doubleValue1 = (double)minecraftClient.player.getWidth() * 0.5;

      return switch (this.aq) {
         case EAST -> (double)this.ar.getX() - (minecraftClient.player.getX() + doubleValue1);
         case WEST -> minecraftClient.player.getX() - doubleValue1 - (double)(this.ar.getX() + 1);
         case SOUTH -> (double)this.ar.getZ() - (minecraftClient.player.getZ() + doubleValue1);
         default -> minecraftClient.player.getZ() - doubleValue1 - (double)(this.ar.getZ() + 1);
      };
   }

   private double b(double doubleValue1, double doubleValue3) {
      return doubleValue1 * (double)this.aq.getOffsetX() + doubleValue3 * (double)this.aq.getOffsetZ();
   }

   private void d(String string1) {
      if (this.L.getValue()) {
         dev.leg1tka.api.utils.text.ClientChat.a("§7[NoClip] §f" + string1);
      }
   }

   private void I() {
      if (System.currentTimeMillis() >= this.V && minecraftClient.player.isOnGround()) {
         BlockPos blockPos1 = BlockPos.ofFloored(minecraftClient.player.getX(), minecraftClient.player.getY() - 0.001, minecraftClient.player.getZ());
         BlockState blockState2 = minecraftClient.world.getBlockState(blockPos1);
         if (!this.a(blockState2, blockPos1)) {
            this.setEnabled(false);
         } else {
            this.N = blockPos1;
            this.O = blockState2;
            this.S = 0;
            this.P = (double)blockPos1.getY() + blockState2.getCollisionShape(minecraftClient.world, blockPos1).getMax(Axis.Y);
            if (this.K.getValue() && !this.ac) {
               this.a(ClipState.TOOL);
               this.J();
            } else {
               this.a(ClipState.CENTER);
               this.L();
            }
         }
      }
   }

   private void J() {
      if (dev.leg1tka.api.utils.player.SprintInputController.a()) {
         if (this.Q > 40) {
            this.V();
         }
      } else if (this.ad) {
         this.ad = false;
         this.ac = true;
         this.a(ClipState.CENTER);
      } else {
         int intValue1 = dev.leg1tka.api.utils.player.ItemAttributes.b(this.O);
         int intValue2 = minecraftClient.player.getInventory().selectedSlot;
         if (intValue1 >= 9) {
            this.af = intValue1;
            this.ag = intValue2;
            this.ad = true;
            dev.leg1tka.api.utils.player.SprintInputController.b(() -> this.a(intValue1, intValue2));
         } else {
            if (intValue1 >= 0 && intValue1 != intValue2) {
               this.ae = intValue2;
               minecraftClient.player.getInventory().selectedSlot = intValue1;
            }

            this.ac = true;
            this.a(ClipState.CENTER);
         }
      }
   }

   private void a(int intValue1, int intValue2) {
      if (minecraftClient.player != null && minecraftClient.interactionManager != null) {
         minecraftClient.interactionManager.clickSlot(0, intValue1, intValue2, SlotActionType.SWAP, minecraftClient.player);
         minecraftClient.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(0));
      }
   }

   private void K() {
      if (minecraftClient.player != null && minecraftClient.interactionManager != null) {
         if (this.af >= 0) {
            int intValue1 = this.af;
            int intValue2 = this.ag;
            dev.leg1tka.api.utils.player.SprintInputController.b(() -> this.a(intValue1, intValue2));
         }

         if (this.ae >= 0) {
            minecraftClient.player.getInventory().selectedSlot = this.ae;
         }
      }

      this.af = -1;
      this.ag = -1;
      this.ae = -1;
      this.ad = false;
      this.ac = false;
   }

   private void L() {
      if (!minecraftClient.player.isOnGround()) {
         this.V();
      } else {
         double doubleValue1 = Math.abs((double)this.N.getX() + 0.5 - minecraftClient.player.getX());
         double doubleValue3 = Math.abs((double)this.N.getZ() + 0.5 - minecraftClient.player.getZ());
         boolean flag5 = doubleValue1 <= 0.19 && doubleValue3 <= 0.19;
         if (!flag5 && !this.G.getValue()) {
            this.V();
         } else {
            double doubleValue6 = minecraftClient.player.getVelocity().x;
            double doubleValue8 = minecraftClient.player.getVelocity().z;
            if (flag5 && doubleValue6 * doubleValue6 + doubleValue8 * doubleValue8 < 1.0E-4) {
               this.a(ClipState.AIM);
               this.M();
            } else if (this.Q > 40) {
               this.V();
            }
         }
      }
   }

   private void M() {
      if (this.Q > 20) {
         this.V();
      } else {
         dev.leg1tka.rotation.Rotation value1 = new dev.leg1tka.rotation.Rotation(minecraftClient.player.getYaw(), 90.0F);
         this.a(value1);
         if (this.Q >= 1 && new dev.leg1tka.rotation.Rotation(minecraftClient.player).a(value1) < 1.0F && this.N()) {
            this.O();
         }
      }
   }

   private boolean N() {
      return System.currentTimeMillis() - this.W >= 320L;
   }

   private void O() {
      if (!this.K.getValue() && (this.I.getValue() || !this.J.getValue())) {
         this.a(minecraftClient.world.getBlockState(this.N));
      }

      this.R = (int)Math.min(200.0F, dev.leg1tka.api.utils.player.ItemAttributes.a(minecraftClient.world.getBlockState(this.N), this.N) * 2.0F + 20.0F);
      this.a(ClipState.MINE);
      this.e(true);
   }

   private void P() {
      this.a(new dev.leg1tka.rotation.Rotation(minecraftClient.player.getYaw(), 90.0F));
      if (this.Q <= this.R && minecraftClient.player.isOnGround()) {
         this.e(false);
      } else {
         this.V();
      }
   }

   private void e(boolean flag1) {
      if (!this.a(minecraftClient.world.getBlockState(this.N), this.N)) {
         this.V();
      } else if (this.K.getValue()) {
         this.f(flag1);
      } else {
         minecraftClient.interactionManager.blockBreakingCooldown = 0;
         boolean flag2 = flag1 || !minecraftClient.interactionManager.isBreakingBlock();
         if (flag2) {
            minecraftClient.interactionManager.attackBlock(this.N, Direction.UP);
         } else {
            minecraftClient.interactionManager.updateBlockBreakingProgress(this.N, Direction.UP);
         }

         if (!this.J.getValue()) {
            dev.leg1tka.api.utils.player.BlockBreakingState.a();
         }

         minecraftClient.player.swingHand(Hand.MAIN_HAND);
         if (minecraftClient.world.getBlockState(this.N).isAir()) {
            if (!flag2) {
               this.W = System.currentTimeMillis();
            }

            minecraftClient.interactionManager.blockBreakingCooldown = 0;
            minecraftClient.world.getPendingUpdateManager().blockPosToPendingUpdate.remove(this.N.asLong());
            minecraftClient.world.setBlockState(this.N, this.O, 3);
            this.Q();
         }
      }
   }

   private void f(boolean flag1) {
      float floatValue2 = minecraftClient.world.getBlockState(this.N).calcBlockBreakingDelta(minecraftClient.player, minecraftClient.world, this.N);
      minecraftClient.player.networkHandler.sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
      if (!flag1 && this.ah) {
         this.Storage += floatValue2;
         if (!(this.Storage < 1.0F)) {
            this.a(Action.STOP_DESTROY_BLOCK);
            this.ah = false;
            this.W = System.currentTimeMillis();
            this.Q();
         }
      } else {
         this.a(Action.START_DESTROY_BLOCK);
         if (floatValue2 >= 1.0F) {
            this.Q();
         } else {
            this.ah = true;
            this.Storage = 0.0F;
         }
      }
   }

   private void a(Action action1) {
      minecraftClient.interactionManager.syncSelectedSlot();
      minecraftClient.interactionManager.sendSequencedPacket(minecraftClient.world, intValue2 -> new PlayerActionC2SPacket(action1, this.N, Direction.UP, intValue2));
   }

   private void Q() {
      this.Z = false;
      this.ModuleStorage = false;
      this.X = -1;
      this.Y = this.B.f() > 0.0F;
      this.a(ClipState.HOLD);
   }

   private void R() {
      this.a(new dev.leg1tka.rotation.Rotation(minecraftClient.player.getYaw(), 90.0F));
      if (this.Z) {
         this.Z = false;
         this.a(ClipState.SETTLE);
      } else {
         if (this.ModuleStorage && this.X < 0) {
            this.X = this.Q;
         }

         boolean flag1 = this.X >= 0 ? this.Q - this.X >= 2 : this.Q >= (int)this.A.f();
         if (flag1 && this.N()) {
            if (++this.S >= (int)this.C.f()) {
               this.V();
            } else {
               this.O();
            }
         }
      }
   }

   private void S() {
      if (this.T()) {
         this.V();
         if (++this.T >= (int)this.z.f()) {
            this.setEnabled(false);
         }
      } else if (this.Q > 8) {
         if (this.S < (int)this.C.f() && minecraftClient.player.isOnGround()) {
            if (!this.N()) {
               return;
            }

            this.S++;
            this.O();
         } else {
            this.V();
         }
      }
   }

   private boolean T() {
      return minecraftClient.player.isOnGround() && minecraftClient.player.getY() < this.P - 0.05 && !minecraftClient.world.getBlockState(this.N).getCollisionShape(minecraftClient.world, this.N).isEmpty();
   }

   private void a(dev.leg1tka.rotation.Rotation value1) {
      dev.leg1tka.rotation.RotationStorage.a(value1, 180.0F, 180.0F, 30.0F, 30.0F, 4, 5, false);
   }

   private boolean b(BlockPos blockPos1) {
      return minecraftClient.world.getBlockState(blockPos1).calcBlockBreakingDelta(minecraftClient.player, minecraftClient.world, blockPos1) >= 1.0F;
   }

   private void a(BlockState blockState1) {
      int intValue2 = dev.leg1tka.api.utils.player.ItemAttributes.a(blockState1);
      if (intValue2 >= 0 && intValue2 != minecraftClient.player.getInventory().selectedSlot) {
         if (this.U == -1) {
            this.U = minecraftClient.player.getInventory().selectedSlot;
         }

         minecraftClient.player.getInventory().selectedSlot = intValue2;
      }
   }

   private void U() {
      if (this.U != -1 && minecraftClient.player != null) {
         minecraftClient.player.getInventory().selectedSlot = this.U;
      }

      this.U = -1;
   }

   private boolean a(BlockState blockState1, BlockPos blockPos2) {
      return !blockState1.isAir() && blockState1.getFluidState().isEmpty() && blockState1.getHardness(minecraftClient.world, blockPos2) >= 0.0F && !blockState1.getCollisionShape(minecraftClient.world, blockPos2).isEmpty();
   }

   private void V() {
      this.W();
      this.V = System.currentTimeMillis() + (long)this.F.f();
   }

   private void W() {
      if (minecraftClient.interactionManager != null && minecraftClient.interactionManager.isBreakingBlock()) {
         minecraftClient.interactionManager.cancelBlockBreaking();
      }

      if (this.ah && this.N != null && minecraftClient.getNetworkHandler() != null) {
         minecraftClient.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.ABORT_DESTROY_BLOCK, this.N, Direction.DOWN));
      }

      this.ah = false;
      this.C();
      this.U();
      dev.leg1tka.rotation.RotationStorage rotationStorage1 = dev.leg1tka.rotation.RotationStorage.c;
      if (rotationStorage1 != null && rotationStorage1.h() == 5 && rotationStorage1.c() == dev.leg1tka.rotation.RotationStorage.RotationState.AIM) {
         rotationStorage1.a(dev.leg1tka.rotation.RotationStorage.RotationState.RESET);
      }

      this.Z = false;
      this.ModuleStorage = false;
      this.Y = false;
      this.a(ClipState.IDLE);
   }

   private void a(ClipState clipState1) {
      this.M = clipState1;
      this.Q = 0;
   }

   static enum ClipState {
      IDLE,
      TOOL,
      CENTER,
      AIM,
      MINE,
      HOLD,
      SETTLE,
      POSITION,
      PREMINE,
      PRIME,
      APPROACH,
      CONTACT,
      ENTER,
      PASS;

      private ClipState() {
      }
   }
}
