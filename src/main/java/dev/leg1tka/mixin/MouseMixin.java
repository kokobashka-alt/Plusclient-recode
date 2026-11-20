package dev.leg1tka.mixin;

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.implement.EventLook;
import dev.leg1tka.gui.components.InputBindings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.util.math.Smoother;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Mouse.class})
public abstract class MouseMixin {
   @Shadow
   @Final
   private MinecraftClient client;
   @Shadow
   private double cursorDeltaX;
   @Shadow
   private double cursorDeltaY;
   @Shadow
   private Smoother cursorXSmoother;
   @Shadow
   private Smoother cursorYSmoother;

   public MouseMixin() {
   }

   @Inject(
      method = {"onMouseButton(JIII)V"},
      at = {@At("HEAD")},
      cancellable = false
   )
   private void onMouseButton(long longValue1, int intValue3, int intValue4, int intValue5, CallbackInfo callbackInfo6) {
      try {
         if (this.client.player == null) {
            return;
         }

         int intValue8 = intValue4 == 1 ? 1 : 0;
         dev.leg1tka.gui.components.InputBindings.b(intValue3, intValue8);
      } catch (Exception exception9) {
      }
   }

   @Inject(
      method = {"updateMouse(D)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onUpdateMouse(double doubleValue1, CallbackInfo callbackInfo3) {
      try {
         if (this.client.player == null) {
            return;
         }

         double doubleValue4 = (Double)this.client.options.getMouseSensitivity().getValue() * 0.6 + 0.2;
         double doubleValue6 = doubleValue4 * doubleValue4 * doubleValue4 * 8.0;
         double doubleValue8;
         double doubleValue10;
         if (this.client.options.smoothCameraEnabled) {
            doubleValue8 = this.cursorXSmoother.smooth(this.cursorDeltaX * doubleValue6, doubleValue1 * doubleValue6);
            doubleValue10 = this.cursorYSmoother.smooth(this.cursorDeltaY * doubleValue6, doubleValue1 * doubleValue6);
         } else if (this.client.options.getPerspective().isFirstPerson() && this.client.player.isUsingSpyglass()) {
            this.cursorXSmoother.clear();
            this.cursorYSmoother.clear();
            doubleValue8 = this.cursorDeltaX * doubleValue4 * doubleValue4 * doubleValue4;
            doubleValue10 = this.cursorDeltaY * doubleValue4 * doubleValue4 * doubleValue4;
         } else {
            this.cursorXSmoother.clear();
            this.cursorYSmoother.clear();
            doubleValue8 = this.cursorDeltaX * doubleValue6;
            doubleValue10 = this.cursorDeltaY * doubleValue6;
         }

         int intValue12 = this.client.options.getInvertYMouse().getValue() ? -1 : 1;
         EventLook eventLook13 = new EventLook(doubleValue8, doubleValue10 * (double)intValue12);
         EventInvoker.invoke(eventLook13);
         if (!eventLook13.isCancelled()) {
            this.client.getTutorialManager().onUpdateMouse(eventLook13.getYaw(), eventLook13.getPitch());
            this.client.player.changeLookDirection(eventLook13.getYaw(), eventLook13.getPitch());
         }

         this.cursorDeltaX = 0.0;
         this.cursorDeltaY = 0.0;
         callbackInfo3.cancel();
      } catch (Exception exception14) {
      }
   }
}
