package dev.leg1tka.mixin;

import dev.leg1tka.gui.menu.VanillaWidgetStyling;
import dev.leg1tka.gui.menu.MenuPreferences;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PressableWidget.class})
public abstract class PressableWidgetMixin extends ClickableWidget {
   public PressableWidgetMixin(int intValue1, int intValue2, int intValue3, int intValue4, Text text5) {
      super(intValue1, intValue2, intValue3, intValue4, text5);
   }

   @Inject(
      method = {"renderWidget(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onRenderWidget(DrawContext drawContext1, int intValue2, int intValue3, float floatValue4, CallbackInfo callbackInfo5) {
      MinecraftClient minecraftClient6 = MinecraftClient.getInstance();
      if (dev.leg1tka.gui.menu.MenuPreferences.a() && (minecraftClient6.world == null || !(minecraftClient6.currentScreen instanceof HandledScreen))) {
         dev.leg1tka.gui.menu.VanillaWidgetStyling.c.a(drawContext1, this, this.alpha, floatValue4, intValue2, intValue3);
         callbackInfo5.cancel();
      }
   }
}
