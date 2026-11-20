package dev.leg1tka.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.PlayerInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({ClientPlayerEntity.class})
public interface ClientPlayerInputAccessor {
   @Accessor("lastPlayerInput")
   void aspect$setLastPlayerInput(PlayerInput playerInput1);
}
