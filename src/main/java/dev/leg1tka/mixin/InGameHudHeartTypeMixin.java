package dev.leg1tka.mixin;

import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.modules.render.NoRender;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(
   targets = {"net/minecraft/client/gui/hud/InGameHud$HeartType"}
)
public class InGameHudHeartTypeMixin {
   public InGameHudHeartTypeMixin() {
   }

   @Redirect(
      method = {"fromPlayerState(Lnet/minecraft/entity/player/PlayerEntity;)Lnet/minecraft/client/gui/hud/InGameHud$HeartType;"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/player/PlayerEntity;hasStatusEffect(Lnet/minecraft/registry/entry/RegistryEntry;)Z"
      )
   )
   private static boolean hookStatus(PlayerEntity playerEntity0, RegistryEntry<StatusEffect> registryEntry1) {
      NoRender noRender2 = ModuleManager.an;
      return noRender2 == null
            || !noRender2.c("Плохие эффекты")
            || !registryEntry1.equals(StatusEffects.POISON) && !registryEntry1.equals(StatusEffects.WITHER)
         ? playerEntity0.hasStatusEffect(registryEntry1)
         : false;
   }
}
