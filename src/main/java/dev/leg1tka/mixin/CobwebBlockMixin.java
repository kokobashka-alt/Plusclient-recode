package dev.leg1tka.mixin;

import dev.leg1tka.managers.ModuleManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.CobwebBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({CobwebBlock.class})
public class CobwebBlockMixin {
   public CobwebBlockMixin() {
   }

   @Inject(
      method = {"onEntityCollision(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/Entity;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onEntityCollision(BlockState blockState1, World world2, BlockPos blockPos3, Entity entity4, CallbackInfo callbackInfo5) {
      if (ModuleManager.ag.isEnabled() && ModuleManager.ag.d.b("Коллизия")) {
         callbackInfo5.cancel();
      }
   }
}
