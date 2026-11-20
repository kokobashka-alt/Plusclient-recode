package dev.leg1tka.mixin;

import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.api.utils.text.ColorUtils;
import dev.leg1tka.modules.render.Atmosphere;
import dev.leg1tka.modules.render.NoRender;
import dev.leg1tka.modules.render.CustomWorld;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Fog;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.render.BackgroundRenderer.FogType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({BackgroundRenderer.class})
public class BackgroundRendererMixin {
   public BackgroundRendererMixin() {
   }

   @Inject(
      method = {"getFogModifier(Lnet/minecraft/entity/Entity;F)Lnet/minecraft/client/render/BackgroundRenderer$StatusEffectFogModifier;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void AspectDLC$getFogModifier(Entity entity0, float floatValue1, CallbackInfoReturnable<Object> callbackInfoReturnable2) {
      if (ModuleManager.a != null) {
         NoRender noRender3 = ModuleManager.an;
         if (noRender3 != null && noRender3.c("Плохие эффекты")) {
            callbackInfoReturnable2.setReturnValue(null);
         }
      }
   }

   @Inject(
      method = {"applyFog(Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/BackgroundRenderer$FogType;Lorg/joml/Vector4f;FZF)Lnet/minecraft/client/render/Fog;"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private static void AspectDLC$applyFog(Camera camera0, FogType fogType1, Vector4f vector4f2, float floatValue3, boolean flag4, float floatValue5, CallbackInfoReturnable<Fog> callbackInfoReturnable6) {
      if (ModuleManager.a != null) {
         Atmosphere atmosphere7 = Atmosphere.c;
         if (atmosphere7 != null && fogType1 == FogType.FOG_TERRAIN) {
            Atmosphere.d = vector4f2.x();
            Atmosphere.e = vector4f2.y();
            Atmosphere.f = vector4f2.z();
         }

         CustomWorld customWorld8 = ModuleManager.aF;
         if (customWorld8 != null && customWorld8.q()) {
            float floatValue13 = Math.max(12.0F, customWorld8.s());
            float floatValue15 = Math.min(floatValue3, floatValue13);
            float floatValue11 = Math.max(0.0F, floatValue15 * 0.05F);
            int intValue12 = customWorld8.t();
            callbackInfoReturnable6.setReturnValue(new Fog(floatValue11, floatValue15, FogShape.SPHERE, dev.leg1tka.api.utils.text.ColorUtils.c(intValue12), dev.leg1tka.api.utils.text.ColorUtils.d(intValue12), dev.leg1tka.api.utils.text.ColorUtils.e(intValue12), 1.0F));
         } else {
            if (atmosphere7 != null && fogType1 == FogType.FOG_TERRAIN && atmosphere7.s() && camera0.getSubmersionType() == CameraSubmersionType.NONE) {
               if (camera0.getFocusedEntity() instanceof LivingEntity livingEntity9
                  && (livingEntity9.hasStatusEffect(StatusEffects.BLINDNESS) || livingEntity9.hasStatusEffect(StatusEffects.DARKNESS))) {
                  return;
               }

               Fog fog14 = (Fog)callbackInfoReturnable6.getReturnValue();
               if (fog14 != null) {
                  callbackInfoReturnable6.setReturnValue(new Fog(floatValue3 * 4.0F, floatValue3 * 5.0F, fog14.shape(), fog14.red(), fog14.green(), fog14.blue(), fog14.alpha()));
               }
            }
         }
      }
   }
}
