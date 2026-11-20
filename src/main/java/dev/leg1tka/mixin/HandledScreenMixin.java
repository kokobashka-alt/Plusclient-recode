package dev.leg1tka.mixin;

import dev.leg1tka.modules.misc.AHHelper;
import dev.leg1tka.modules.player.ItemScroller;
import dev.leg1tka.modules.render.ShulkerPreview;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({HandledScreen.class})
public abstract class HandledScreenMixin {
   @Shadow
   protected int x;
   @Shadow
   protected int y;
   @Shadow
   @Nullable
   protected Slot focusedSlot;

   public HandledScreenMixin() {
   }

   @Shadow
   @Nullable
   protected abstract Slot getSlotAt(double doubleValue1, double doubleValue3);

   @Shadow
   protected abstract void onMouseClick(@Nullable Slot slot1, int intValue2, int intValue3, SlotActionType slotActionType4);

   @Inject(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = {@At("RETURN")}
   )
   private void onRenderReturn(DrawContext drawContext1, int intValue2, int intValue3, float floatValue4, CallbackInfo callbackInfo5) {
      AHHelper.c.a(drawContext1, (HandledScreen<?>)(Object)this, this.x, this.y);
      ShulkerPreview.c.a(drawContext1, this.focusedSlot, intValue2, intValue3);
   }

   @Inject(
      method = {"drawMouseoverTooltip(Lnet/minecraft/client/gui/DrawContext;II)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onDrawMouseoverTooltip(DrawContext drawContext1, int intValue2, int intValue3, CallbackInfo callbackInfo4) {
      if (ShulkerPreview.c.a(this.focusedSlot)) {
         callbackInfo4.cancel();
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = {@At("HEAD")}
   )
   private void onRender(DrawContext drawContext1, int intValue2, int intValue3, float floatValue4, CallbackInfo callbackInfo5) {
      MinecraftClient minecraftClient6 = MinecraftClient.getInstance();
      ItemScroller itemScroller7 = ItemScroller.c;
      if (itemScroller7.isEnabled() && minecraftClient6.player != null && minecraftClient6.interactionManager != null) {
         long longValue8 = minecraftClient6.getWindow().getHandle();
         boolean flag10 = GLFW.glfwGetMouseButton(longValue8, 0) == 1;
         boolean flag11 = GLFW.glfwGetKey(longValue8, 340) == 1 || GLFW.glfwGetKey(longValue8, 344) == 1;
         if (flag10 && flag11) {
            Slot slot12 = this.getSlotAt((double)intValue2, (double)intValue3);
            if (slot12 != null && slot12.hasStack()) {
               if (itemScroller7.p()) {
                  this.onMouseClick(slot12, slot12.id, 0, SlotActionType.QUICK_MOVE);
               }
            }
         } else {
            itemScroller7.q();
         }
      }
   }
}
