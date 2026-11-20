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
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Nuker extends Module {
   public static Nuker c = new Nuker();
   private final NumberSetting d = new NumberSetting("Дистанция", 3.0F, 1.0F, 5.0F, 1.0F);
   private final BooleanSetting e = new BooleanSetting("Ломать все блоки", false);
   private final BooleanSetting f = new BooleanSetting("Анимация руки", true);
   private final Set g = new HashSet();
   private BlockPos h;

   public Nuker() {
      super(
         "Nuker",
         "Автоматически ломает блоки в радиусе",
         Category.PLAYER
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f});
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player == null || minecraftClient.world == null || minecraftClient.interactionManager == null) {
         this.u();
      } else if (!this.e.getValue() && this.g.isEmpty()) {
         this.u();
      } else {
         if (!this.r()) {
            this.h = this.s();
         }

         if (this.h != null) {
            this.t();
         }
      }
   }

   private boolean r() {
      return this.h != null && this.a(this.h) && this.b(this.h);
   }

   private BlockPos s() {
      int intValue1 = Math.round(this.d.f());
      BlockPos blockPos2 = minecraftClient.player.getBlockPos();
      return BlockPos.stream(blockPos2.add(-intValue1, 0, -intValue1), blockPos2.add(intValue1, intValue1, intValue1))
         .<BlockPos>map(BlockPos::toImmutable)
         .filter(this::a)
         .filter(this::b)
         .min(Comparator.comparingDouble(blockPos0 -> minecraftClient.player.squaredDistanceTo(Vec3d.ofCenter(blockPos0))))
         .orElse(null);
   }

   private boolean a(BlockPos blockPos1) {
      double doubleValue2 = (double)this.d.f();
      return minecraftClient.player.squaredDistanceTo(Vec3d.ofCenter(blockPos1)) <= doubleValue2 * doubleValue2;
   }

   private boolean b(BlockPos blockPos1) {
      BlockState blockState2 = minecraftClient.world.getBlockState(blockPos1);
      if (blockState2 == null || blockState2.isAir() || blockState2.getHardness(minecraftClient.world, blockPos1) < 0.0F) {
         return false;
      } else if (this.e.getValue()) {
         return true;
      } else {
         String string3 = Registries.BLOCK.getId(blockState2.getBlock()).getPath().toLowerCase();
         return this.g.contains(string3);
      }
   }

   private void t() {
      if (this.h != null && minecraftClient.player != null && minecraftClient.interactionManager != null) {
         minecraftClient.interactionManager.attackBlock(this.h, Direction.UP);
         minecraftClient.interactionManager.updateBlockBreakingProgress(this.h, Direction.UP);
         if (this.f.getValue()) {
            minecraftClient.player.swingHand(Hand.MAIN_HAND);
         }

         if (minecraftClient.world.getBlockState(this.h).isAir()) {
            this.u();
         }
      }
   }

   private void u() {
      this.h = null;
      if (minecraftClient.interactionManager != null) {
         minecraftClient.interactionManager.cancelBlockBreaking();
      }
   }

   public void c(String string1) {
      this.g.add(f(string1));
   }

   public void d(String string1) {
      this.g.remove(f(string1));
   }

   public void p() {
      this.g.clear();
      this.u();
   }

   public boolean e(String string1) {
      return this.g.contains(f(string1));
   }

   public Set<String> q() {
      return new HashSet<>(this.g);
   }

   public static String f(String string0) {
      if (string0 == null) {
         return "";
      } else {
         String string1 = string0.toLowerCase().trim();
         int intValue2 = string1.indexOf(58);
         return intValue2 >= 0 ? string1.substring(intValue2 + 1) : string1;
      }
   }

   @Override
   public void onDisable() {
      this.u();
      super.onDisable();
   }
}
