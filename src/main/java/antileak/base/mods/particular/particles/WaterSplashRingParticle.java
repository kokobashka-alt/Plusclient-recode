package antileak.base.mods.particular.particles;

import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;

public class WaterSplashRingParticle extends SpriteBillboardParticle {
   private final SpriteProvider sprites;
   private final float startScale;

   public WaterSplashRingParticle(ClientWorld clientWorld1, double doubleValue2, double doubleValue4, double doubleValue6, SpriteProvider spriteProvider8, double doubleValue9, double doubleValue11, double doubleValue13) {
      super(clientWorld1, doubleValue2, doubleValue4, doubleValue6, doubleValue9, doubleValue11, doubleValue13);
      this.sprites = spriteProvider8;
      this.startScale = 0.14F + this.random.nextFloat() * 0.06F;
      this.maxAge = 16 + this.random.nextInt(6);
      this.collidesWithWorld = false;
      this.velocityY = 0.01;
      this.setColor(0.82F, 0.92F, 1.0F);
      this.scale = this.startScale;
      this.setAlpha(0.9F);
      this.setSpriteForAge(spriteProvider8);
   }

   public void tick() {
      if (this.age++ >= this.maxAge) {
         this.markDead();
      } else {
         this.velocityX *= 0.92;
         this.velocityY *= 0.9;
         this.velocityZ *= 0.92;
         this.move(this.velocityX, this.velocityY, this.velocityZ);
         float floatValue1 = (float)this.age / (float)this.maxAge;
         this.scale = this.startScale * (1.0F + floatValue1 * 2.2F);
         this.setAlpha(MathHelper.clamp(1.0F - floatValue1 * 1.15F, 0.0F, 1.0F));
         this.setSpriteForAge(this.sprites);
      }
   }

   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
   }

   public static class Factory implements ParticleFactory<SimpleParticleType> {
      private final FabricSpriteProvider sprites;

      public Factory(FabricSpriteProvider fabricSpriteProvider1) {
         this.sprites = fabricSpriteProvider1;
      }

      public Particle createParticle(SimpleParticleType simpleParticleType1, ClientWorld clientWorld2, double doubleValue3, double doubleValue5, double doubleValue7, double doubleValue9, double doubleValue11, double doubleValue13) {
         return new WaterSplashRingParticle(clientWorld2, doubleValue3, doubleValue5, doubleValue7, this.sprites, doubleValue9, doubleValue11, doubleValue13);
      }
   }
}
