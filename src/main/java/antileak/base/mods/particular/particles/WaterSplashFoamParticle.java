package antileak.base.mods.particular.particles;

import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

public class WaterSplashFoamParticle extends WaterSplashParticle {
   public WaterSplashFoamParticle(ClientWorld clientWorld1, double doubleValue2, double doubleValue4, double doubleValue6, SpriteProvider spriteProvider8, double doubleValue9, double doubleValue11, double doubleValue13) {
      super(clientWorld1, doubleValue2, doubleValue4, doubleValue6, spriteProvider8, doubleValue9, doubleValue11, doubleValue13);
      this.setColor(1.0F, 1.0F, 1.0F);
      this.setAlpha(0.85F);
   }

   public static class Factory implements ParticleFactory<SimpleParticleType> {
      private final FabricSpriteProvider sprites;

      public Factory(FabricSpriteProvider fabricSpriteProvider1) {
         this.sprites = fabricSpriteProvider1;
      }

      public Particle createParticle(SimpleParticleType simpleParticleType1, ClientWorld clientWorld2, double doubleValue3, double doubleValue5, double doubleValue7, double doubleValue9, double doubleValue11, double doubleValue13) {
         return new WaterSplashFoamParticle(clientWorld2, doubleValue3, doubleValue5, doubleValue7, this.sprites, doubleValue9, doubleValue11, doubleValue13);
      }
   }
}
