package antileak.base.mods.particular.particles;

import antileak.base.mods.particular.ParticularParticleTypes;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;

public class WaterSplashEmitterParticle extends SpriteBillboardParticle {
   private final float width;
   private final float speed;
   private boolean spawned;

   public WaterSplashEmitterParticle(ClientWorld clientWorld1, double doubleValue2, double doubleValue4, double doubleValue6, double doubleValue8, double doubleValue10, double doubleValue12) {
      super(clientWorld1, doubleValue2, doubleValue4, doubleValue6, 0.0, 0.0, 0.0);
      this.width = Math.max(0.2F, (float)doubleValue8);
      this.speed = Math.max(0.0F, (float)doubleValue10);
      this.maxAge = 4;
      this.collidesWithWorld = false;
      this.setAlpha(0.0F);
      this.scale = 0.0F;
   }

   public void tick() {
      if (this.age++ >= this.maxAge) {
         this.markDead();
      } else {
         if (!this.spawned) {
            this.spawned = true;
            this.spawnChildren();
         }
      }
   }

   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.NO_RENDER;
   }

   private void spawnChildren() {
      int intValue1 = Math.max(8, MathHelper.ceil(this.width * 10.0F));
      double doubleValue2 = Math.max(0.18, (double)this.width * 0.25);
      double doubleValue4 = 0.06 + (double)this.speed * 0.05;

      for (int intValue6 = 0; intValue6 < intValue1; intValue6++) {
         double doubleValue7 = (Math.PI * 2) * (double)intValue6 / (double)intValue1;
         double doubleValue9 = this.x + Math.cos(doubleValue7) * doubleValue2;
         double doubleValue11 = this.z + Math.sin(doubleValue7) * doubleValue2;
         double doubleValue13 = Math.cos(doubleValue7) * (0.02 + (double)this.speed * 0.02);
         double doubleValue15 = Math.sin(doubleValue7) * (0.02 + (double)this.speed * 0.02);
         this.world.addParticle(ParticularParticleTypes.WATER_SPLASH, doubleValue9, this.y, doubleValue11, doubleValue13, doubleValue4, doubleValue15);
         this.world.addParticle(ParticularParticleTypes.WATER_SPLASH_FOAM, doubleValue9, this.y + 0.03, doubleValue11, doubleValue13 * 0.55, doubleValue4 * 0.35, doubleValue15 * 0.55);
      }

      this.world.addParticle(ParticularParticleTypes.WATER_SPLASH_RING, this.x, this.y, this.z, 0.0, 0.02 + (double)this.speed * 0.01, 0.0);
      int intValue17 = Math.max(3, MathHelper.ceil(this.width * 2.0F));

      for (int intValue18 = 0; intValue18 < intValue17; intValue18++) {
         double doubleValue8 = (this.random.nextDouble() - 0.5) * 0.04;
         double doubleValue10 = 0.05 + this.random.nextDouble() * 0.05 + (double)this.speed * 0.03;
         double doubleValue12 = (this.random.nextDouble() - 0.5) * 0.04;
         this.world.addParticle(ParticleTypes.FALLING_WATER, this.x, this.y + 0.02, this.z, doubleValue8, doubleValue10, doubleValue12);
      }
   }

   public static class Factory implements ParticleFactory<SimpleParticleType> {
      public Factory() {
      }

      public Particle createParticle(SimpleParticleType simpleParticleType1, ClientWorld clientWorld2, double doubleValue3, double doubleValue5, double doubleValue7, double doubleValue9, double doubleValue11, double doubleValue13) {
         return new WaterSplashEmitterParticle(clientWorld2, doubleValue3, doubleValue5, doubleValue7, doubleValue9, doubleValue11, doubleValue13);
      }
   }
}
