package dev.leg1tka.modules.combat;


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
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventBinding;
import dev.leg1tka.api.events.implement.EventGameUpdate;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
public class AutoTrap extends Module {
   public static final AutoTrap c = new AutoTrap();
   private final ModeSetting d = new ModeSetting("Мод", "Obsidian", "Obsidian", "CobWeb");
   private final NumberSetting e = new NumberSetting("Дистанция", 3.0F, 1.0F, 5.0F, 0.1F);
   private final dev.leg1tka.settings.KeybindSetting f = new dev.leg1tka.settings.KeybindSetting("Бинд", -1);
   private final BooleanSetting g = new BooleanSetting("Из инвентаря", false);
   private final BooleanSetting h = new BooleanSetting("Ротация", true);
   private final MultiSetting i = new MultiSetting(
      "Таргеты",
      new BooleanSetting("Игроки", true),
      new BooleanSetting("Невидимые", true),
      new BooleanSetting("Себя", false)
   );
   private final BooleanSetting j;
   private PlayerEntity k;
   private int l;
   private int m;
   private boolean n;
   private boolean o;
   private final List p;
   private int q;
   private BlockPos r;
   private boolean s;
   private int t;
   private float u;
   private float v;

   public AutoTrap() {
      super(
         "AutoTrap",
         "Автоматически ставит ловушку вокруг игрока",
         Category.COMBAT
      );
      BooleanSetting booleanSetting10001 = new BooleanSetting("Реверс ротейт", true);
      BooleanSetting booleanSetting10002 = this.h;
      Objects.requireNonNull(this.h);
      this.j = booleanSetting10001.visibleWhen(createSupplier101(booleanSetting10002));
      this.l = -1;
      this.m = -1;
      this.n = false;
      this.o = false;
      this.p = new ArrayList();
      this.q = 0;
      this.r = null;
      this.s = false;
      this.t = 0;
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h, this.j, this.i});
   }

   @EventLink
   public void onBinding(EventBinding eventBinding1) {
      if (this.f.getKey() != -1 && eventBinding1.getKey() == this.f.getKey()) {
         this.k = this.t();
         if (this.k != null) {
            this.p();
         }
      }
   }



   public void onGameUpdate(EventGameUpdate eventGameUpdate1) {
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player == null || minecraftClient.world == null) return;
      if (!this.n) {
         if (this.f.getKey() == -1) {
            this.k = this.t();
            if (this.k != null) {
               this.p();
            }
         }
      }
      if (this.n) {
         this.q();
      }
   }

   private void a(BlockPos blockPos1) {
      Direction direction2 = this.c(blockPos1);
      if (direction2 != null) {
         BlockPos blockPos3 = blockPos1.offset(direction2);
         Direction direction4 = direction2.getOpposite();
         Vec3d vec3d5 = this.a(blockPos3, direction4);
         Vec2f vec2f6 = dev.leg1tka.api.utils.player.RotationRaycast.b(vec3d5);
         dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(vec2f6.x, vec2f6.y), 360.0F, 360.0F, 360.0F, 360.0F, 5, 1, false);
      }
   }

   private Vec3d a(BlockPos blockPos1, Direction direction2) {
      Vec3d vec3d3 = Vec3d.ofCenter(blockPos1);
      return vec3d3.add((double)direction2.getOffsetX() * 0.5, (double)direction2.getOffsetY() * 0.5, (double)direction2.getOffsetZ() * 0.5);
   }

   private boolean b(BlockPos blockPos1) {
      if (!this.h.getValue()) {
         return true;
      } else {
         Direction direction2 = this.c(blockPos1);
         if (direction2 == null) {
            return false;
         } else {
            BlockPos blockPos3 = blockPos1.offset(direction2);
            Direction direction4 = direction2.getOpposite();
            Vec3d vec3d5 = this.a(blockPos3, direction4);
            Vec2f vec2f6 = dev.leg1tka.api.utils.player.RotationRaycast.b(vec3d5);
            float floatValue7 = Math.abs(MathHelper.wrapDegrees(vec2f6.x - minecraftClient.player.getYaw()));
            float floatValue8 = Math.abs(MathHelper.wrapDegrees(vec2f6.y - minecraftClient.player.getPitch()));
            return floatValue7 < 5.0F && floatValue8 < 5.0F;
         }
      }
   }

   private void p() {
      this.p.clear();
      this.q = 0;
      this.s = false;
      this.t = 0;
      BlockPos blockPos1 = this.k.getBlockPos();
      if (this.d.b("Obsidian")) {
         this.p.add(blockPos1.add(1, 0, 0));
         this.p.add(blockPos1.add(-1, 0, 0));
         this.p.add(blockPos1.add(0, 0, 1));
         this.p.add(blockPos1.add(0, 0, -1));
         this.p.add(blockPos1.add(1, 1, 0));
         this.p.add(blockPos1.add(-1, 1, 0));
         this.p.add(blockPos1.add(0, 1, 1));
         this.p.add(blockPos1.add(0, 1, -1));
         this.p.add(blockPos1.add(0, 2, 0));
         this.p.add(blockPos1.add(1, 2, 0));
         this.p.add(blockPos1.add(-1, 2, 0));
         this.p.add(blockPos1.add(0, 2, 1));
         this.p.add(blockPos1.add(0, 2, -1));
      } else {
         this.p.add(blockPos1);
         this.p.add(blockPos1.up());
      }

      if (this.g.getValue()) {
         this.l = minecraftClient.player.getInventory().selectedSlot;
         int intValue2 = this.s();
         if (intValue2 == -1) {
            this.n = false;
            return;
         }

         if (intValue2 < 9) {
            minecraftClient.player.getInventory().selectedSlot = intValue2;
            this.m = -1;
         } else {
            this.m = intValue2;
            minecraftClient.interactionManager.clickSlot(minecraftClient.player.currentScreenHandler.syncId, intValue2, this.l, SlotActionType.SWAP, minecraftClient.player);
         }
      }

      this.n = true;
   }

   private void q() {
      if (this.k == null || this.k.isAlive() && !(minecraftClient.player.distanceTo(this.k) > this.e.e().floatValue())) {
         if (this.q >= this.p.size()) {
            this.r();
         } else {
            BlockPos blockPos1 = (BlockPos)this.p.get(this.q);
            this.r = blockPos1;
            if (!minecraftClient.world.getBlockState(blockPos1).isReplaceable()) {
               this.q++;
               this.s = false;
               this.t = 0;
            } else {
               Direction direction2 = this.c(blockPos1);
               if (direction2 == null) {
                  this.q++;
                  this.s = false;
                  this.t = 0;
               } else {
                  if (this.h.getValue()) {
                     if (!this.s) {
                        this.a(blockPos1);
                        this.s = true;
                        this.t = 0;
                        return;
                     }

                     this.t++;
                     if (!this.b(blockPos1) || this.t < 2) {
                        this.a(blockPos1);
                        return;
                     }
                  }

                  this.d(blockPos1);
                  this.q++;
                  this.s = false;
                  this.t = 0;
               }
            }
         }
      } else {
         this.r();
      }
   }

   private void r() {
      if (this.g.getValue()) {
         if (this.m != -1) {
            minecraftClient.interactionManager.clickSlot(minecraftClient.player.currentScreenHandler.syncId, this.m, this.l, SlotActionType.SWAP, minecraftClient.player);
            this.m = -1;
         } else if (this.l != -1) {
            minecraftClient.player.getInventory().selectedSlot = this.l;
         }

         this.l = -1;
      }

      this.n = false;
      this.k = null;
      this.r = null;
      this.p.clear();
      this.q = 0;
      this.s = false;
      this.t = 0;
   }

   private Direction c(BlockPos blockPos1) {
      Direction[] directionArray2 = new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

      for (Direction direction6 : directionArray2) {
         BlockPos blockPos7 = blockPos1.offset(direction6);
         BlockState blockState8 = minecraftClient.world.getBlockState(blockPos7);
         if (!blockState8.isReplaceable() && !blockState8.isLiquid() && blockState8.isSolidBlock(minecraftClient.world, blockPos7)) {
            return direction6;
         }
      }

      for (Direction direction12 : directionArray2) {
         BlockPos blockPos13 = blockPos1.offset(direction12);
         BlockState blockState14 = minecraftClient.world.getBlockState(blockPos13);
         if (!blockState14.isReplaceable() && !blockState14.isLiquid()) {
            return direction12;
         }
      }

      return null;
   }

   private void d(BlockPos blockPos1) {
      Direction direction2 = this.c(blockPos1);
      if (direction2 != null) {
         BlockPos blockPos3 = blockPos1.offset(direction2);
         Direction direction4 = direction2.getOpposite();
         Vec3d vec3d5 = this.a(blockPos3, direction4);
         BlockHitResult blockHitResult6 = new BlockHitResult(vec3d5, direction4, blockPos3, false);
         minecraftClient.interactionManager.interactBlock(minecraftClient.player, Hand.MAIN_HAND, blockHitResult6);
         minecraftClient.player.swingHand(Hand.MAIN_HAND);
      }
   }

   private int s() {
      Item item1 = this.d.b("Obsidian") ? Items.OBSIDIAN : Items.COBWEB;

      for (int intValue2 = 0; intValue2 < 36; intValue2++) {
         if (minecraftClient.player.getInventory().getStack(intValue2).getItem() == item1) {
            return intValue2;
         }
      }

      return -1;
   }

   private PlayerEntity t() {
      if (this.i.a("Себя")) {
         return minecraftClient.player;
      } else {
         ArrayList arrayList1 = new ArrayList();

         for (Entity entity3 : minecraftClient.world.getEntities()) {
            if (entity3 instanceof PlayerEntity) {
               PlayerEntity playerEntity4 = (PlayerEntity)entity3;
               if (playerEntity4 != minecraftClient.player
                  && playerEntity4.isAlive()
                  && this.i.a("Игроки")
                  && (!playerEntity4.hasStatusEffect(StatusEffects.INVISIBILITY) || this.i.a("Невидимые"))
                  && !AspectDLC.INSTANCE.friendStorage.c(playerEntity4.getName().getString())
                  && !(minecraftClient.player.distanceTo(playerEntity4) > this.e.e().floatValue())) {
                  arrayList1.add(playerEntity4);
               }
            }
         }

         if (arrayList1.isEmpty()) {
            return null;
         } else {
            arrayList1.sort(Comparator.comparingDouble(createToDoubleFunction102()));
            return (PlayerEntity)arrayList1.get(0);
         }
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      if (this.n) {
         this.r();
      }

      this.k = null;
      this.n = false;
      this.o = false;
      this.r = null;
      this.p.clear();
      this.q = 0;
      this.l = -1;
      this.m = -1;
      this.s = false;
      this.t = 0;
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.n = false;
      this.o = false;
      this.r = null;
      this.p.clear();
      this.q = 0;
      this.l = -1;
      this.m = -1;
      this.s = false;
      this.t = 0;
   }



   private static Supplier createSupplier101(BooleanSetting booleanSetting0) {
      return booleanSetting0::getValue;
   }

   private static ToDoubleFunction<Entity> createToDoubleFunction102() {
      return (Entity entity0) -> (double)minecraftClient.player.distanceTo(entity0);
   }
   public static void dev_leg1tka_o_gn_initializer() {}
}
