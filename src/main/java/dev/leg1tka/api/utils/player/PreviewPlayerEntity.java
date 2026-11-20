package dev.leg1tka.api.utils.player;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.mojang.authlib.GameProfile;
import java.util.function.DoubleConsumer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;

public class PreviewPlayerEntity extends OtherClientPlayerEntity {
   public boolean a;
   public DoubleConsumer b;

   public PreviewPlayerEntity(ClientWorld clientWorld1, GameProfile gameProfile2) {
      super(clientWorld1, gameProfile2);
   }

   public SkinTextures getSkinTextures() {
      MinecraftClient minecraftClient1 = MinecraftClient.getInstance();
      return minecraftClient1.player != null ? minecraftClient1.player.getSkinTextures() : super.getSkinTextures();
   }

   public void tickMovement() {
      if (!this.a && !this.isDead()) {
         Vec3d vec3d1 = this.getVelocity();
         Vec3d vec3d2 = new Vec3d(Math.abs(vec3d1.x) < 0.003 ? 0.0 : vec3d1.x, Math.abs(vec3d1.y) < 0.003 ? 0.0 : vec3d1.y, Math.abs(vec3d1.z) < 0.003 ? 0.0 : vec3d1.z);
         this.setVelocity(vec3d2);
         float floatValue3 = this.fallDistance;
         boolean flag4 = this.isOnGround();
         this.move(MovementType.SELF, vec3d2);
         if (!flag4 && this.isOnGround() && floatValue3 > 0.0F && this.b != null) {
            this.b.accept((double)floatValue3);
         }

         float floatValue5 = this.isOnGround() ? this.getWorld().getBlockState(this.getVelocityAffectingPos()).getBlock().getSlipperiness() * 0.91F : 0.91F;
         Vec3d vec3d6 = this.getVelocity();
         this.setVelocity(vec3d6.x * (double)floatValue5, (vec3d6.y - this.getFinalGravity()) * 0.98, vec3d6.z * (double)floatValue5);
      } else {
         this.setVelocity(Vec3d.ZERO);
      }

      super.tickMovement();
   }
}
