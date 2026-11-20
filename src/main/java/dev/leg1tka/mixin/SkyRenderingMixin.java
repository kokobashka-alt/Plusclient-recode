package dev.leg1tka.mixin;

import dev.leg1tka.render.BackgroundShaderRenderer;
import dev.leg1tka.modules.render.CustomWorld;
import net.minecraft.client.render.SkyRendering;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({SkyRendering.class})
public class SkyRenderingMixin {
   public SkyRenderingMixin() {
   }

   @Inject(
      method = {"renderSky(FFF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderShaderSky(float floatValue1, float floatValue2, float floatValue3, CallbackInfo callbackInfo4) {
      CustomWorld customWorld5 = CustomWorld.c;
      if (customWorld5 != null && customWorld5.u()) {
         dev.leg1tka.render.BackgroundShaderRenderer.c.a();
         callbackInfo4.cancel();
      }
   }

   @Inject(
      method = {"close"},
      at = {@At("HEAD")}
   )
   private void closeShaderSky(CallbackInfo callbackInfo1) {
      dev.leg1tka.render.BackgroundShaderRenderer.c.b();
   }
}
