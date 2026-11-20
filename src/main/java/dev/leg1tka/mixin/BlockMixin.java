package dev.leg1tka.mixin;

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.implement.EventBlockPlaced;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Block.class})
public class BlockMixin {
   public BlockMixin() {
   }

   @Inject(
      method = {"onPlaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V"},
      at = {@At("HEAD")}
   )
   private void onPlacedEvent(World world1, BlockPos blockPos2, BlockState blockState3, LivingEntity livingEntity4, ItemStack itemStack5, CallbackInfo callbackInfo6) {
      if (world1.isClient() && livingEntity4 == MinecraftClient.getInstance().player && EventInvoker.hasListeners(EventBlockPlaced.class)) {
         new EventBlockPlaced(blockPos2.toImmutable(), itemStack5).call();
      }
   }
}
