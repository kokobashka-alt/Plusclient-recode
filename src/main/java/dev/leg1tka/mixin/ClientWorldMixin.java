package dev.leg1tka.mixin;

import dev.leg1tka.api.events.implement.EventBlockChange;
import dev.leg1tka.api.events.implement.EventPotion;
import net.minecraft.block.BlockState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClientWorld.class})
public class ClientWorldMixin {
   public ClientWorldMixin() {
   }

   @Inject(
      method = {"syncWorldEvent(Lnet/minecraft/entity/player/PlayerEntity;ILnet/minecraft/util/math/BlockPos;I)V"},
      at = {@At("HEAD")}
   )
   private void onSyncWorldEvent(PlayerEntity playerEntity1, int intValue2, BlockPos blockPos3, int intValue4, CallbackInfo callbackInfo5) {
      if (intValue2 == 2002 || intValue2 == 2007) {
         new EventPotion(EventPotion.Type.PARTICLES, intValue4, blockPos3).call();
      }
   }

   @Inject(
      method = {"handleBlockUpdate(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)V"},
      at = {@At("HEAD")}
   )
   private void onHandleBlockUpdate(BlockPos blockPos1, BlockState blockState2, int intValue3, CallbackInfo callbackInfo4) {
      BlockState blockState5 = ((ClientWorld)(Object)this).getBlockState(blockPos1);
      if (blockState5 != blockState2) {
         new EventBlockChange(blockPos1.toImmutable(), blockState5, blockState2).call();
      }
   }
}
