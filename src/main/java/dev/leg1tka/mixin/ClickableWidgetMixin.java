package dev.leg1tka.mixin;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ClickableWidget.class})
public abstract class ClickableWidgetMixin {
   @Unique
   private static final SoundEvent CLICK_SOUND = SoundEvent.of(Identifier.of("aspectdlc", "custom_click"));

   public ClickableWidgetMixin() {
   }

   @Inject(
      method = {"playDownSound(Lnet/minecraft/client/sound/SoundManager;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onPlayDownSound(SoundManager soundManager1, CallbackInfo callbackInfo2) {
      soundManager1.play(PositionedSoundInstance.master(CLICK_SOUND, 1.0F));
      callbackInfo2.cancel();
   }

   @Inject(
      method = {"playClickSound(Lnet/minecraft/client/sound/SoundManager;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void onPlayClickSound(SoundManager soundManager0, CallbackInfo callbackInfo1) {
      soundManager0.play(PositionedSoundInstance.master(CLICK_SOUND, 1.0F));
      callbackInfo1.cancel();
   }
}
