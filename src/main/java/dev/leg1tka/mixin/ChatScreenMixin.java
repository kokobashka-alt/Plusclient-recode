package dev.leg1tka.mixin;

import dev.leg1tka.AspectDLC;
import dev.leg1tka.render.hud.HudElement;
import dev.leg1tka.render.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ChatScreen.class})
public class ChatScreenMixin {
   @Unique
   private boolean AspectDLC$leftPressed;

   public ChatScreenMixin() {
   }

   @Inject(
      method = {"mouseClicked(DDI)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onMouseClicked(double doubleValue1, double doubleValue3, int intValue5, CallbackInfoReturnable<Boolean> callbackInfoReturnable6) {
      for (dev.leg1tka.render.hud.HudElement value8 : dev.leg1tka.render.hud.HudElementRegistry.a.values()) {
         if (value8.f().isEnabled() && value8.a(doubleValue1, doubleValue3, intValue5)) {
            callbackInfoReturnable6.setReturnValue(true);
            return;
         }
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = {@At("HEAD")}
   )
   private void onRender(DrawContext drawContext1, int intValue2, int intValue3, float floatValue4, CallbackInfo callbackInfo5) {
      MinecraftClient minecraftClient6 = MinecraftClient.getInstance();
      Window window7 = minecraftClient6.getWindow();
      boolean flag8 = GLFW.glfwGetMouseButton(minecraftClient6.getWindow().getHandle(), 0) == 1;
      if (this.AspectDLC$leftPressed && !flag8) {
         for (dev.leg1tka.render.hud.HudElement value10 : dev.leg1tka.render.hud.HudElementRegistry.a.values()) {
            value10.a(0);
         }
      }

      this.AspectDLC$leftPressed = flag8;

      for (dev.leg1tka.render.hud.HudElement value12 : dev.leg1tka.render.hud.HudElementRegistry.a.values()) {
         if (value12.f().isEnabled()) {
            value12.a(intValue2, intValue3, window7, drawContext1.getMatrices());
         }
      }
   }

   @Inject(
      method = {"removed()V"},
      at = {@At("HEAD")}
   )
   private void onRemoved(CallbackInfo callbackInfo1) {
      this.AspectDLC$leftPressed = false;

      for (dev.leg1tka.render.hud.HudElement value3 : dev.leg1tka.render.hud.HudElementRegistry.a.values()) {
         value3.a(0);
      }

      AspectDLC.INSTANCE.configStorage.b();
   }
}
