package dev.leg1tka.mixin;

import dev.leg1tka.gui.menu.VanillaWidgetStyling;
import dev.leg1tka.gui.menu.MenuPreferences;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({SliderWidget.class})
public abstract class SliderWidgetMixin extends ClickableWidget {
   @Shadow
   protected double value;

   @Shadow
   public void setValue(double doubleValue1) {
   }

   public SliderWidgetMixin(int intValue1, int intValue2, int intValue3, int intValue4, Text text5) {
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
         dev.leg1tka.gui.menu.VanillaWidgetStyling.c.a(drawContext1, (SliderWidget)(Object)this, this.value, this.alpha, floatValue4);
         callbackInfo5.cancel();
      }
   }

   @Inject(
      method = {"setValueFromMouse(D)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onSetValueFromMouse(double doubleValue1, CallbackInfo callbackInfo3) {
      MinecraftClient minecraftClient4 = MinecraftClient.getInstance();
      if (dev.leg1tka.gui.menu.MenuPreferences.a() && (minecraftClient4.world == null || !(minecraftClient4.currentScreen instanceof HandledScreen))) {
         float floatValue5 = 14.0F;
         double doubleValue6 = (doubleValue1 - (double)((float)this.getX() + floatValue5)) / (double)Math.max(1.0F, (float)this.width - floatValue5 * 2.0F);
         this.setValue(MathHelper.clamp(doubleValue6, 0.0, 1.0));
         callbackInfo3.cancel();
      }
   }
}
