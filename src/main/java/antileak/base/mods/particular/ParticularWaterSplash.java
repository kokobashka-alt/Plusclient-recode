package antileak.base.mods.particular;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public final class ParticularWaterSplash {
   private final Deque<Float> velocities = new ArrayDeque<>(4);

   public ParticularWaterSplash() {
   }

   public void trackVelocity(ClientPlayerEntity clientPlayerEntity1) {
      if (clientPlayerEntity1 != null) {
         this.velocities.addLast((float)Math.abs(clientPlayerEntity1.getVelocity().y));
         if (this.velocities.size() > 4) {
            this.velocities.removeFirst();
         }
      }
   }

   public void trySpawnOnWaterEntry(ClientPlayerEntity clientPlayerEntity1) {
      if (clientPlayerEntity1 != null) {
         World world2 = clientPlayerEntity1.getWorld();
         if (world2 != null && world2.isClient) {
            double doubleValue3 = this.findWaterSurfaceY(clientPlayerEntity1);
            float floatValue5 = this.velocities.isEmpty() ? 0.0F : (Float) Collections.max(this.velocities);
            spawnEmitter(world2, clientPlayerEntity1.getX(), doubleValue3, clientPlayerEntity1.getZ(), clientPlayerEntity1.getWidth() * 2.0F, floatValue5);
         }
      }
   }

   private double findWaterSurfaceY(ClientPlayerEntity clientPlayerEntity1) {
      World world2 = clientPlayerEntity1.getWorld();
      BlockPos blockPos3 = BlockPos.ofFloored(clientPlayerEntity1.getX(), clientPlayerEntity1.getY(), clientPlayerEntity1.getZ());

      for (int intValue4 = 0; intValue4 < 5; intValue4++) {
         BlockPos blockPos5 = blockPos3.up(intValue4);
         FluidState fluidState6 = world2.getFluidState(blockPos5);
         if (fluidState6.isIn(FluidTags.WATER)) {
            return (double)((float)blockPos5.getY() + fluidState6.getHeight(world2, blockPos5)) - 0.01;
         }
      }

      return clientPlayerEntity1.getY();
   }

   public static void spawnEmitter(World world0, double doubleValue1, double doubleValue3, double doubleValue5, float floatValue7, float floatValue8) {
      if (world0 != null && world0.isClient) {
         world0.addParticle(ParticularParticleTypes.WATER_SPLASH_EMITTER, doubleValue1, doubleValue3, doubleValue5, (double)floatValue7, (double)floatValue8, 0.0);
      }
   }
}
