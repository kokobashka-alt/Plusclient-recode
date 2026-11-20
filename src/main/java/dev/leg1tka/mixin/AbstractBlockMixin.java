package dev.leg1tka.mixin;

import dev.leg1tka.api.events.implement.EventBlockCollide;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({AbstractBlock.class})
public class AbstractBlockMixin {
   public AbstractBlockMixin() {
   }

   @Inject(
      method = {"getOutlineShape(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getOutlineShape(BlockState blockState1, BlockView blockView2, BlockPos blockPos3, ShapeContext shapeContext4, CallbackInfoReturnable<VoxelShape> callbackInfoReturnable5) {
      EventBlockCollide eventBlockCollide6 = new EventBlockCollide(blockPos3);
      eventBlockCollide6.call();
      if (eventBlockCollide6.isCancelled()) {
         callbackInfoReturnable5.setReturnValue(VoxelShapes.empty());
      }
   }

   @Inject(
      method = {"getCollisionShape(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/ShapeContext;)Lnet/minecraft/util/shape/VoxelShape;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getCollisionShape(BlockState blockState1, BlockView blockView2, BlockPos blockPos3, ShapeContext shapeContext4, CallbackInfoReturnable<VoxelShape> callbackInfoReturnable5) {
      EventBlockCollide eventBlockCollide6 = new EventBlockCollide(blockPos3);
      eventBlockCollide6.call();
      if (eventBlockCollide6.isCancelled()) {
         callbackInfoReturnable5.setReturnValue(VoxelShapes.empty());
      }
   }

   @Inject(
      method = {"getRaycastShape(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/shape/VoxelShape;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getRaycastShape(BlockState blockState1, BlockView blockView2, BlockPos blockPos3, CallbackInfoReturnable<VoxelShape> callbackInfoReturnable4) {
      EventBlockCollide eventBlockCollide5 = new EventBlockCollide(blockPos3);
      eventBlockCollide5.call();
      if (eventBlockCollide5.isCancelled()) {
         callbackInfoReturnable4.setReturnValue(VoxelShapes.empty());
      }
   }
}
