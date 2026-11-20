package antileak.base.mods.maseffects.particles;

import net.fabricmc.fabric.api.client.particle.v1.FabricSpriteProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;

public class ReviveSparkParticle extends SpriteBillboardParticle {
   private final SpriteProvider sprites;
   private final int targetEntityId;
   private final float startScale;

   public ReviveSparkParticle(ClientWorld clientWorld1, double doubleValue2, double doubleValue4, double doubleValue6, SpriteProvider spriteProvider8, double doubleValue9, double doubleValue11, double doubleValue13) {
      super(clientWorld1, doubleValue2, doubleValue4, doubleValue6, 0.0, 0.0, 0.0);
      this.sprites = spriteProvider8;
      this.targetEntityId = MathHelper.floor(doubleValue9);
      this.startScale = 0.16F + this.random.nextFloat() * 0.08F;
      this.maxAge = 18 + this.random.nextInt(8);
      this.collidesWithWorld = false;
      this.setColor(1.0F, 1.0F, 1.0F);
      this.setAlpha(0.0F);
      this.scale = this.startScale;
      this.velocityX = doubleValue11 * 0.08 + (this.random.nextDouble() - 0.5) * 0.02;
      this.velocityY = doubleValue13 * 0.08 + (this.random.nextDouble() - 0.5) * 0.02 + 0.02;
      this.velocityZ = (this.random.nextDouble() - 0.5) * 0.02;
      this.setSpriteForAge(spriteProvider8);
   }

   public void tick() {
      if (this.age++ >= this.maxAge) {
         this.markDead();
      } else {
         Entity entity1 = this.world.getEntityById(this.targetEntityId);
         if (entity1 != null) {
            double doubleValue2 = entity1.getX();
            double doubleValue4 = entity1.getY() + (double)entity1.getHeight() * 0.5;
            double doubleValue6 = entity1.getZ();
            double doubleValue8 = doubleValue2 - this.x;
            double doubleValue10 = doubleValue4 - this.y;
            double doubleValue12 = doubleValue6 - this.z;
            double doubleValue14 = Math.max(0.001, Math.sqrt(doubleValue8 * doubleValue8 + doubleValue10 * doubleValue10 + doubleValue12 * doubleValue12));
            double doubleValue16 = this.age > 6 ? 0.075 : 0.025;
            this.velocityX += doubleValue8 / doubleValue14 * doubleValue16;
            this.velocityY += doubleValue10 / doubleValue14 * doubleValue16;
            this.velocityZ += doubleValue12 / doubleValue14 * doubleValue16;
         }

         this.velocityX *= 0.94;
         this.velocityY *= 0.94;
         this.velocityZ *= 0.94;
         this.move(this.velocityX, this.velocityY, this.velocityZ);
         this.setSpriteForAge(this.sprites);
         float floatValue18 = (float)this.age / (float)this.maxAge;
         this.setAlpha(MathHelper.clamp(1.0F - floatValue18 * 1.25F, 0.0F, 1.0F));
         this.scale = this.startScale * (1.0F - floatValue18 * 0.45F);
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
         return new ReviveSparkParticle(clientWorld2, doubleValue3, doubleValue5, doubleValue7, this.sprites, doubleValue9, doubleValue11, doubleValue13);
      }
   }
}
