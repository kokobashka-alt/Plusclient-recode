package dev.leg1tka.mixin;

import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.modules.misc.NameProtect;
import net.minecraft.text.TextVisitFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin({TextVisitFactory.class})
public class TextVisitFactoryMixin {
   public TextVisitFactoryMixin() {
   }

   @ModifyArg(
      method = {"visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/text/TextVisitFactory;visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z",
         ordinal = 0
      ),
      index = 0
   )
   private static String AspectDLC$patchVisitedText(String string0) {
      if (ModuleManager.a == null) {
         return string0;
      } else {
         NameProtect nameProtect1 = ModuleManager.A;
         return nameProtect1 != null && nameProtect1.isEnabled() ? nameProtect1.d(string0) : string0;
      }
   }
}
