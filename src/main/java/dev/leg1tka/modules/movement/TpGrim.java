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
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.Full;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TpGrim extends Module {
   public static TpGrim c = new TpGrim();
   public final NumberSetting d = new NumberSetting("X", 0.0F, -3.0E7F, 3.0E7F, 1.0F);
   public final NumberSetting e = new NumberSetting("Y", 64.0F, -64.0F, 320.0F, 1.0F);
   public final NumberSetting f = new NumberSetting("Z", 0.0F, -3.0E7F, 3.0E7F, 1.0F);
   private final Map g = new HashMap();
   private Vec3d h = null;
   private boolean i = false;
   private int j = 0;
   private int k = 0;

   public TpGrim() {
      super(
         "TpGrim",
         "Грим телепорт под землей",
         Category.MOVEMENT
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (minecraftClient.player != null) {
         this.a(this.d.e().doubleValue(), this.e.e().doubleValue(), this.f.e().doubleValue());
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.i = false;
      this.h = null;
      this.j = 0;
      this.k = 0;
   }

   public void a(double doubleValue1, double doubleValue3, double doubleValue5) {
      this.h = new Vec3d(doubleValue1, doubleValue3, doubleValue5);
      this.i = true;
      this.j = 0;
      this.k = 0;
      dev.leg1tka.api.utils.text.ClientChat.a(String.format("ТП -> %.0f %.0f %.0f", doubleValue1, doubleValue3, doubleValue5));
   }

   public boolean c(String string1) {
      Vec3d vec3d2 = (Vec3d)this.g.get(string1.toLowerCase());
      if (vec3d2 == null) {
         dev.leg1tka.api.utils.text.ClientChat.a("Игрок не найден: " + string1);
         return false;
      } else {
         dev.leg1tka.api.utils.text.ClientChat.a(String.format("ТП к игроку %s (%.0f %.0f %.0f)", string1, vec3d2.x, vec3d2.y, vec3d2.z));
         this.a(vec3d2.x, vec3d2.y, vec3d2.z);
         return true;
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         for (PlayerEntity playerEntity3 : minecraftClient.world.getPlayers()) {
            if (playerEntity3 != minecraftClient.player) {
               this.g.put(playerEntity3.getName().getString().toLowerCase(), playerEntity3.getPos());
            }
         }

         if (this.i && this.h != null) {
            if (this.j > 0) {
               this.j--;
               this.a(minecraftClient.player.getPos());
               minecraftClient.player.setVelocity(0.0, 0.0, 0.0);
            } else {
               Vec3d vec3d18 = minecraftClient.player.getPos();
               double doubleValue19 = Math.hypot(this.h.x - vec3d18.x, this.h.z - vec3d18.z);
               if (doubleValue19 < 1.5) {
                  this.i = false;
                  minecraftClient.player.setVelocity(0.0, 0.0, 0.0);
                  minecraftClient.player.fallDistance = 0.0F;
                  dev.leg1tka.api.utils.text.ClientChat.a(String.format("Прибыли! (%d шагов)", this.k));
                  this.setEnabled(false);
               } else {
                  Vec3d vec3d5 = this.h.subtract(vec3d18).normalize();
                  double doubleValue6 = Math.min(doubleValue19, ThreadLocalRandom.current().nextDouble(7.0, 9.5));
                  double doubleValue8 = ThreadLocalRandom.current().nextDouble(-0.4, 0.4);
                  Vec3d vec3d10 = new Vec3d(-vec3d5.z, 0.0, vec3d5.x).multiply(doubleValue8);
                  Vec3d vec3d11 = vec3d18.add(vec3d5.multiply(doubleValue6)).add(vec3d10);
                  if (this.a(minecraftClient.world, vec3d11.x, vec3d11.z, vec3d18.y)) {
                     vec3d11 = this.a(minecraftClient.world, vec3d18, vec3d11);
                  }

                  double doubleValue12 = this.a(minecraftClient.world, vec3d11.x, vec3d11.z);
                  boolean flag14 = doubleValue12 - vec3d18.y > 2.0;
                  double doubleValue15 = flag14 ? this.c(minecraftClient.world, vec3d11.x, vec3d11.z) : this.b(minecraftClient.world, vec3d11.x, vec3d11.z);
                  Vec3d vec3d17 = new Vec3d(vec3d11.x, doubleValue15, vec3d11.z);
                  this.a(vec3d17);
                  minecraftClient.player.setPosition(vec3d17.x, vec3d17.y, vec3d17.z);
                  minecraftClient.player.setVelocity(0.0, 0.0, 0.0);
                  minecraftClient.player.fallDistance = 0.0F;
                  this.k++;
                  if (this.k % 4 == 0) {
                     this.j = ThreadLocalRandom.current().nextInt(12, 18);
                  } else {
                     this.j = ThreadLocalRandom.current().nextInt(2, 4);
                  }
               }
            }
         }
      }
   }

   private boolean a(BlockState blockState1) {
      return !blockState1.isAir() && blockState1.getFluidState().isEmpty() && blockState1.blocksMovement();
   }

   private double a(World world1, double doubleValue2, double doubleValue4) {
      int intValue6 = this.a(doubleValue2);
      int intValue7 = this.a(doubleValue4);
      short value8 = 320;
      int intValue9 = world1.getBottomY();

      for (int intValue10 = value8; intValue10 >= intValue9; intValue10--) {
         if (this.a(world1.getBlockState(new BlockPos(intValue6, intValue10, intValue7)))) {
            return (double)intValue10 + 1.0;
         }
      }

      return (double)intValue9 + 1.0;
   }

   private double b(World world1, double doubleValue2, double doubleValue4) {
      return this.a(world1, doubleValue2, doubleValue4) - 0.9;
   }

   private double c(World world1, double doubleValue2, double doubleValue4) {
      int intValue6 = this.a(doubleValue2);
      int intValue7 = this.a(doubleValue4);
      double doubleValue8 = this.a(world1, doubleValue2, doubleValue4);
      int intValue10 = (int)Math.floor(doubleValue8) - 1;
      int intValue11 = world1.getBottomY();

      for (int intValue12 = intValue10; intValue12 >= Math.max(intValue11, intValue10 - 5); intValue12--) {
         BlockState blockState13 = world1.getBlockState(new BlockPos(intValue6, intValue12, intValue7));
         BlockState blockState14 = world1.getBlockState(new BlockPos(intValue6, intValue12 + 1, intValue7));
         if (this.a(blockState13) && this.a(blockState14)) {
            return (double)intValue12 + 0.1;
         }
      }

      return doubleValue8 - 0.9;
   }

   private boolean a(World world1, double doubleValue2, double doubleValue4, double doubleValue6) {
      return doubleValue6 - this.a(world1, doubleValue2, doubleValue4) > 4.0;
   }

   private Vec3d a(World world1, Vec3d vec3d2, Vec3d vec3d3) {
      Vec3d vec3d4 = vec3d3.subtract(vec3d2).normalize();
      Vec3d vec3d5 = new Vec3d(-vec3d4.z, 0.0, vec3d4.x);
      double[] doubleValueArray6 = new double[]{2.0, -2.0, 4.0, -4.0, 6.0, -6.0};

      for (double doubleValue10 : doubleValueArray6) {
         Vec3d vec3d12 = vec3d3.add(vec3d5.multiply(doubleValue10));
         if (!this.a(world1, vec3d12.x, vec3d12.z, vec3d2.y)) {
            return vec3d12;
         }
      }

      return vec3d2.add(this.h.subtract(vec3d2).normalize().multiply(3.0));
   }

   private void a(Vec3d vec3d1) {
      if (minecraftClient.player != null && minecraftClient.getNetworkHandler() != null) {
         minecraftClient.getNetworkHandler().sendPacket(new Full(vec3d1.x, vec3d1.y, vec3d1.z, minecraftClient.player.getYaw(), minecraftClient.player.getPitch(), true, false));
      }
   }

   private int a(double doubleValue1) {
      int intValue3 = (int)doubleValue1;
      return doubleValue1 < (double)intValue3 ? intValue3 - 1 : intValue3;
   }
}
