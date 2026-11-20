package dev.leg1tka.mixin;

import dev.leg1tka.modules.render.TalismanIndication;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({ItemModelManager.class})
public class ItemModelManagerMixin {
   public ItemModelManagerMixin() {
   }

   @ModifyVariable(
      method = {"update(Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)V"},
      at = @At("HEAD"),
      argsOnly = true
   )
   private ItemStack replaceTalismanModel(ItemStack itemStack1) {
      return TalismanIndication.c.a(itemStack1);
   }
}
