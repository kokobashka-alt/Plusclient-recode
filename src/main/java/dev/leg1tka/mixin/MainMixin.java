package dev.leg1tka.mixin;

import dev.leg1tka.AspectDLC;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Main.class})
public class MainMixin {
   public MainMixin() {
   }

   @Inject(
      method = {"main"},
      at = {@At("HEAD")}
   )
   private static void onMain(String[] stringArray0, CallbackInfo callbackInfo1) {
      if (AspectDLC.INSTANCE.isServer) {
         try {
            AspectDLC.INSTANCE.closeMinecraft();
         } catch (Exception exception3) {
            exception3.printStackTrace();
         }

         AspectDLC.INSTANCE.isServer = false;
      }
   }
}
