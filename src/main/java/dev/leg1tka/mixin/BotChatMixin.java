package dev.leg1tka.mixin;

import dev.leg1tka.network.bots.BotController;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHud.ChatState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ChatHud.class})
public abstract class BotChatMixin {
   public BotChatMixin() {
   }

   @Inject(
      method = {"restoreChatState(Lnet/minecraft/client/gui/hud/ChatHud$ChatState;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$botRestore(ChatState chatState1, CallbackInfo callbackInfo2) {
      if (dev.leg1tka.network.bots.BotController.f()) {
         callbackInfo2.cancel();
      }
   }
}
