package dev.leg1tka.mixin;

import dev.leg1tka.api.utils.player.InventorySelection;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({InGameHud.class})
public abstract class HotbarSwapDisplayMixin {
   public HotbarSwapDisplayMixin() {
   }

   @Redirect(
      method = {"renderHotbar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/util/collection/DefaultedList;get(I)Ljava/lang/Object;"
      )
   )
   private Object aspect$hotbarSwapDisplay(DefaultedList<?> defaultedList1, int intValue2) {
      Object object3 = defaultedList1.get(intValue2);
      return object3 instanceof ItemStack itemStack4 ? dev.leg1tka.api.utils.player.InventorySelection.a(intValue2, itemStack4) : object3;
   }
}
