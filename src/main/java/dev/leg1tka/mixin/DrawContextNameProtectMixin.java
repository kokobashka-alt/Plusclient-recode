package dev.leg1tka.mixin;

import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.modules.misc.NameProtect;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin({DrawContext.class})
public class DrawContextNameProtectMixin {
   public DrawContextNameProtectMixin() {
   }

   @ModifyVariable(
      method = {"drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)I"},
      at = @At("HEAD"),
      argsOnly = true,
      ordinal = 0
   )
   private String AspectDLC$patchStringShadow(String string1) {
      return this.patch(string1);
   }

   @ModifyVariable(
      method = {"drawText(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;IIIZ)I"},
      at = @At("HEAD"),
      argsOnly = true,
      ordinal = 0
   )
   private String AspectDLC$patchString(String string1) {
      return this.patch(string1);
   }

   private String patch(String string1) {
      if (ModuleManager.a == null) {
         return string1;
      } else {
         NameProtect nameProtect2 = ModuleManager.A;
         return nameProtect2 != null && nameProtect2.isEnabled() ? nameProtect2.d(string1) : string1;
      }
   }
}
