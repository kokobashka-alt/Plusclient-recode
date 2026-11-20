package dev.leg1tka.mixin;

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.implement.EventMoveInput;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.util.PlayerInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({KeyboardInput.class})
public abstract class KeyboardInputMixin extends Input {
   private static final MinecraftClient mc = MinecraftClient.getInstance();

   public KeyboardInputMixin() {
   }

   @Inject(
      method = {"tick()V"},
      at = {@At("TAIL")}
   )
   private void onTickTail(CallbackInfo callbackInfo1) {
      if (EventInvoker.hasListeners(EventMoveInput.class)) {
         EventMoveInput eventMoveInput2 = new EventMoveInput(this.movementForward, this.movementSideways, this.playerInput.jump(), this.playerInput.sneak());
         eventMoveInput2.call();
         float floatValue3 = eventMoveInput2.getForward();
         float floatValue4 = eventMoveInput2.getStrafe();
         this.playerInput = new PlayerInput(floatValue3 > 0.0F, floatValue3 < 0.0F, floatValue4 > 0.0F, floatValue4 < 0.0F, eventMoveInput2.isJump(), eventMoveInput2.isSneak(), this.playerInput.sprint());
         this.movementForward = floatValue3;
         this.movementSideways = floatValue4;
      }
   }
}
