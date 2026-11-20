package dev.leg1tka.mixin;

import dev.leg1tka.accounts.ServerReconnectTracker;
import dev.leg1tka.network.bots.BotSession;
import dev.leg1tka.network.bots.BotController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({DisconnectedScreen.class})
public abstract class DisconnectedScreenMixin extends Screen {
   @Shadow
   @Final
   private DirectionalLayoutWidget grid;

   protected DisconnectedScreenMixin(Text text1) {
      super(text1);
   }

   @Inject(
      method = {"init()V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/widget/DirectionalLayoutWidget;refreshPositions()V"
      )}
   )
   private void aspect$reconnect(CallbackInfo callbackInfo1) {
      int intValue2 = 0;

      for (dev.leg1tka.network.bots.BotSession value4 : dev.leg1tka.network.bots.BotController.d()) {
         if (intValue2++ >= 4) {
            break;
         }

         String string5 = value4.a();
         this.grid
            .add(ButtonWidget.builder(Text.literal("Вернуться: " + string5), buttonWidget1x -> dev.leg1tka.network.bots.BotController.a(string5)).width(200).build());
      }

      ServerAddress serverAddress6 = dev.leg1tka.accounts.ServerReconnectTracker.a();
      ServerInfo serverInfo7 = dev.leg1tka.accounts.ServerReconnectTracker.b();
      if (serverAddress6 != null && serverInfo7 != null) {
         this.grid
            .add(
               ButtonWidget.builder(
                     Text.literal("Переподключиться"), buttonWidget2x -> {
                        MinecraftClient minecraftClient3 = MinecraftClient.getInstance();
                        ConnectScreen.connect(new MultiplayerScreen(new TitleScreen()), minecraftClient3, serverAddress6, serverInfo7, false, null);
                     }
                  )
                  .width(200)
                  .build()
            );
      }
   }
}
