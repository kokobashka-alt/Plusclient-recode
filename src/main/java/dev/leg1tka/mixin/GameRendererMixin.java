package dev.leg1tka.mixin;

import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.modules.render.AspectRatio;
import dev.leg1tka.modules.render.Atmosphere;
import dev.leg1tka.modules.render.EntityESP;
import dev.leg1tka.modules.render.NoRender;
import dev.leg1tka.gui.menu.MenuBackgroundResources;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resource.ResourceFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GameRenderer.class})
public class GameRendererMixin {
   public GameRendererMixin() {
   }

   @Inject(
      method = {"preloadPrograms(Lnet/minecraft/resource/ResourceFactory;)V"},
      at = {@At("TAIL")}
   )
   private void onPreloadPrograms(ResourceFactory resourceFactory1, CallbackInfo callbackInfo2) {
      dev.leg1tka.gui.menu.MenuBackgroundResources.a(resourceFactory1);
   }

   @Inject(
      method = {"renderWorld(Lnet/minecraft/client/render/RenderTickCounter;)V"},
      at = {@At("TAIL")}
   )
   private void aspect$atmosphereStyle(RenderTickCounter renderTickCounter1, CallbackInfo callbackInfo2) {
      Atmosphere atmosphere3 = Atmosphere.c;
      if (atmosphere3 != null && atmosphere3.isEnabled()) {
         atmosphere3.t();
      }

      EntityESP entityESP4 = EntityESP.c;
      if (entityESP4 != null && entityESP4.isEnabled()) {
         entityESP4.p();
      }
   }

   @ModifyArg(
      method = {"getBasicProjectionMatrix(F)Lorg/joml/Matrix4f;"},
      at = @At(
         value = "INVOKE",
         target = "Lorg/joml/Matrix4f;perspective(FFFF)Lorg/joml/Matrix4f;"
      ),
      index = 1
   )
   private float AspectDLC$aspectRatio(float floatValue1) {
      AspectRatio aspectRatio2 = AspectRatio.c;
      return aspectRatio2 != null ? aspectRatio2.a(floatValue1) : floatValue1;
   }

   @Inject(
      method = {"tiltViewWhenHurt(Lnet/minecraft/client/util/math/MatrixStack;F)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$noHurtCameraShake(MatrixStack matrixStack1, float floatValue2, CallbackInfo callbackInfo3) {
      if (ModuleManager.a != null) {
         NoRender noRender4 = ModuleManager.an;
         if (noRender4 != null && noRender4.c("Тряска")) {
            callbackInfo3.cancel();
         }
      }
   }

   @Inject(
      method = {"showFloatingItem(Lnet/minecraft/item/ItemStack;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$hideTotemAnimation(ItemStack itemStack1, CallbackInfo callbackInfo2) {
      if (ModuleManager.a != null && itemStack1 != null && itemStack1.isOf(Items.TOTEM_OF_UNDYING)) {
         NoRender noRender3 = ModuleManager.an;
         if (noRender3 != null && noRender3.p()) {
            callbackInfo2.cancel();
         }
      }
   }
}
