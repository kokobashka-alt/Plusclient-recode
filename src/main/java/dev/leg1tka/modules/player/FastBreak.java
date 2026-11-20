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
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class FastBreak extends Module {
   public static FastBreak c = new FastBreak();
   private final NumberSetting d = new NumberSetting("Ускорение", 0.5F, 0.3F, 1.0F, 0.1F);

   public FastBreak() {
      super(
         "FastBreak",
         "Ускоряет ломание блоков",
         Category.PLAYER
      );
      this.addSettings(new Setting[]{this.d});
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null && minecraftClient.interactionManager != null) {
         if (minecraftClient.crosshairTarget instanceof BlockHitResult blockHitResult2) {
            if (minecraftClient.options.attackKey.isPressed()) {
               a(minecraftClient.interactionManager, minecraftClient.player, minecraftClient.world, blockHitResult2.getBlockPos(), blockHitResult2.getSide(), this.d.f(), true);
            }
         }
      }
   }

   public float p() {
      return this.d.f();
   }

   public static int a(float floatValue0) {
      return Math.max(1, Math.round(Math.max(0.3F, floatValue0) / 0.35F));
   }

   public static boolean a(
      ClientPlayerInteractionManager clientPlayerInteractionManager0, ClientPlayerEntity clientPlayerEntity1, ClientWorld clientWorld2, BlockPos blockPos3, Direction direction4, float floatValue5, boolean flag6
   ) {
      if (clientPlayerInteractionManager0 != null && clientPlayerEntity1 != null && clientWorld2 != null && blockPos3 != null) {
         BlockState blockState7 = clientWorld2.getBlockState(blockPos3);
         if (blockState7 != null && !blockState7.isAir()) {
            Direction direction8 = direction4 == null ? Direction.UP : direction4;
            int intValue9 = a(floatValue5);

            for (int intValue10 = 0; intValue10 < intValue9; intValue10++) {
               clientPlayerInteractionManager0.updateBlockBreakingProgress(blockPos3, direction8);
            }

            if (flag6) {
               clientPlayerEntity1.swingHand(Hand.MAIN_HAND);
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean a(ClientPlayNetworkHandler clientPlayNetworkHandler0, ClientPlayerEntity clientPlayerEntity1, BlockPos blockPos2, Direction direction3, boolean flag4) {
      if (clientPlayNetworkHandler0 != null && clientPlayerEntity1 != null && blockPos2 != null) {
         Direction direction5 = direction3 == null ? Direction.UP : direction3;
         clientPlayNetworkHandler0.sendPacket(new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, blockPos2, direction5));
         clientPlayNetworkHandler0.sendPacket(new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, blockPos2, direction5));
         if (flag4) {
            clientPlayNetworkHandler0.sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
         }

         return true;
      } else {
         return false;
      }
   }
}
