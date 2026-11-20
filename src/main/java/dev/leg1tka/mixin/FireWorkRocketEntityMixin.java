package dev.leg1tka.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import dev.leg1tka.api.events.implement.EventFireWork;
import dev.leg1tka.api.utils.player.PlayerMotion;
import dev.leg1tka.modules.movement.Superfirework;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({FireworkRocketEntity.class})
public abstract class FireWorkRocketEntityMixin extends ProjectileEntity {
   @Unique
   private Vec3d rotation;
   @Shadow
   private LivingEntity shooter;

   public FireWorkRocketEntityMixin(EntityType<? extends ProjectileEntity> entityType1, World world2) {
      super(entityType1, world2);
   }

   @Inject(
      method = {"tick()V"},
      at = {@At("HEAD")}
   )
   public void tick(CallbackInfo callbackInfo1) {
      new EventFireWork((FireworkRocketEntity)(Object)this).call();
   }

   @ModifyExpressionValue(
      method = {"tick()V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/LivingEntity;getRotationVector()Lnet/minecraft/util/math/Vec3d;"
      )}
   )
   public Vec3d captureRotation(Vec3d vec3d1) {
      this.rotation = vec3d1;
      return this.rotation;
   }

   @Redirect(
      method = {"tick()V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/util/math/Vec3d;add(DDD)Lnet/minecraft/util/math/Vec3d;",
         ordinal = 0
      )
   )
   public Vec3d modifyBoost(Vec3d vec3d1, double doubleValue2, double doubleValue4, double doubleValue6) {
      MinecraftClient minecraftClient8 = MinecraftClient.getInstance();
      Superfirework superfirework9 = Superfirework.c;
      if (minecraftClient8.player == null || !minecraftClient8.player.isGliding()) {
         return this.defaultBoost(vec3d1);
      } else {
         return superfirework9 != null && superfirework9.isEnabled() ? this.handleElytraBoost(minecraftClient8, superfirework9, vec3d1) : this.defaultBoost(vec3d1);
      }
   }

   @Unique
   private Vec3d handleElytraBoost(MinecraftClient minecraftClient1, Superfirework superfirework2, Vec3d vec3d3) {
      String string4 = superfirework2.t().g();

      Vec3d vec3d5 = switch (string4) {
         case "BravoHVH" -> dev.leg1tka.api.utils.player.PlayerMotion.e(minecraftClient1.player);
         default -> {
            Vec2f vec2f8 = superfirework2.q();
            yield new Vec3d((double)vec2f8.x, (double)vec2f8.y, (double)vec2f8.x);
         }
      };
      return vec3d3.add(
         this.rotation.x * 0.1 + (this.rotation.x * vec3d5.x - vec3d3.x) * 0.5,
         this.rotation.y * 0.1 + (this.rotation.y * vec3d5.y - vec3d3.y) * 0.5,
         this.rotation.z * 0.1 + (this.rotation.z * vec3d5.z - vec3d3.z) * 0.5
      );
   }

   @Unique
   private Vec3d defaultBoost(Vec3d vec3d1) {
      return vec3d1.add(
         this.rotation.x * 0.1 + (this.rotation.x * 1.5 - vec3d1.x) * 0.5,
         this.rotation.y * 0.1 + (this.rotation.y * 1.5 - vec3d1.y) * 0.5,
         this.rotation.z * 0.1 + (this.rotation.z * 1.5 - vec3d1.z) * 0.5
      );
   }
}
