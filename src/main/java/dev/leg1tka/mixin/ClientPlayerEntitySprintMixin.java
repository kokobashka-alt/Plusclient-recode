package dev.leg1tka.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.authlib.GameProfile;
import dev.leg1tka.api.events.implement.EventSprint;
import dev.leg1tka.network.bots.BotController;
import dev.leg1tka.network.ProtocolVersionResolver;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(
   value = {ClientPlayerEntity.class},
   priority = 1500
)
public abstract class ClientPlayerEntitySprintMixin extends PlayerEntity {
   public ClientPlayerEntitySprintMixin(World world1, BlockPos blockPos2, float floatValue3, GameProfile gameProfile4) {
      super(world1, blockPos2, floatValue3, gameProfile4);
   }

   @WrapOperation(
      method = {"tickMovement()V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z",
         ordinal = 1
      )},
      require = 0
   )
   private boolean aspect$sprintKey(KeyBinding keyBinding1, Operation<Boolean> operation2) {
      if (dev.leg1tka.network.bots.BotController.f()) {
         return dev.leg1tka.network.bots.BotController.h();
      } else if (!dev.leg1tka.network.ProtocolVersionResolver.a() || !this.horizontalCollision && !this.collidedSoftly) {
         EventSprint eventSprint3 = new EventSprint();
         eventSprint3.call();
         return eventSprint3.isCancelled() ? false : (Boolean)operation2.call(new Object[]{keyBinding1});
      } else {
         return false;
      }
   }
}
