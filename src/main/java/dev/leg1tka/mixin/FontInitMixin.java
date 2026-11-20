package dev.leg1tka.mixin;

import dev.leg1tka.render.font.FontCache;
import dev.leg1tka.render.font.Fonts;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MinecraftClient.class})
public class FontInitMixin {
   public FontInitMixin() {
   }

   @Inject(
      method = {"onFinishedLoading(Lnet/minecraft/client/MinecraftClient$LoadingContext;)V"},
      at = {@At("TAIL")}
   )
   private void onFinishedLoading(CallbackInfo callbackInfo1) {
      dev.leg1tka.render.font.Fonts.a();
      dev.leg1tka.render.font.FontCache.a();
   }
}
