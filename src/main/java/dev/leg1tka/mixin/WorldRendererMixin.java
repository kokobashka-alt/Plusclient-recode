package dev.leg1tka.mixin;

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.implement.Event3DRender;
import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.api.ClientAccess;
import dev.leg1tka.modules.render.Atmosphere;
import dev.leg1tka.modules.render.NoRender;
import dev.leg1tka.modules.render.ShaderCrystal;
import java.awt.Color;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Fog;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.profiler.Profilers;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({WorldRenderer.class})
public class WorldRendererMixin implements dev.leg1tka.api.ClientAccess {
   public WorldRendererMixin() {
   }

   @Inject(
      method = {"renderParticles(Lnet/minecraft/client/render/FrameGraphBuilder;Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/render/Fog;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$renderParticles(FrameGraphBuilder frameGraphBuilder1, Camera camera2, float floatValue3, Fog fog4, CallbackInfo callbackInfo5) {
      if (ModuleManager.a != null) {
         NoRender noRender6 = ModuleManager.an;
         if (noRender6 != null && noRender6.c("Частицы")) {
            callbackInfo5.cancel();
         }
      }
   }

   @Inject(
      method = {"renderWeather(Lnet/minecraft/client/render/FrameGraphBuilder;Lnet/minecraft/util/math/Vec3d;FLnet/minecraft/client/render/Fog;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$renderWeather(FrameGraphBuilder frameGraphBuilder1, Vec3d vec3d2, float floatValue3, Fog fog4, CallbackInfo callbackInfo5) {
      if (ModuleManager.a != null) {
         NoRender noRender6 = ModuleManager.an;
         if (noRender6 != null && noRender6.c("Погода")) {
            callbackInfo5.cancel();
         }
      }
   }

   @Inject(
      method = {"addWeatherParticlesAndSound(Lnet/minecraft/client/render/Camera;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$addWeatherParticlesAndSound(Camera camera1, CallbackInfo callbackInfo2) {
      if (ModuleManager.a != null) {
         NoRender noRender3 = ModuleManager.an;
         if (noRender3 != null && noRender3.c("Погода")) {
            callbackInfo2.cancel();
         }
      }
   }

   @Inject(
      method = {"renderClouds(Lnet/minecraft/client/render/FrameGraphBuilder;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lnet/minecraft/client/option/CloudRenderMode;Lnet/minecraft/util/math/Vec3d;FIF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$renderClouds(
      FrameGraphBuilder frameGraphBuilder1, Matrix4f matrix4f2, Matrix4f matrix4f3, CloudRenderMode cloudRenderMode4, Vec3d vec3d5, float floatValue6, int intValue7, float floatValue8, CallbackInfo callbackInfo9
   ) {
      if (ModuleManager.a != null) {
         NoRender noRender10 = ModuleManager.an;
         if (noRender10 != null && noRender10.c("Облака")) {
            callbackInfo9.cancel();
         }
      }
   }

   @Inject(
      method = {"renderBlockEntities(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/render/Camera;F)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$renderBlockEntities(MatrixStack matrixStack1, Immediate immediate2, Immediate immediate3, Camera camera4, float floatValue5, CallbackInfo callbackInfo6) {
      if (ModuleManager.a != null) {
         NoRender noRender7 = ModuleManager.an;
         if (noRender7 != null && noRender7.c("Блок-сущности")) {
            callbackInfo6.cancel();
         }
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/util/ObjectAllocator;Lnet/minecraft/client/render/RenderTickCounter;ZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V"},
      at = {@At("RETURN")}
   )
   private void render(
      ObjectAllocator objectAllocator1, RenderTickCounter renderTickCounter2, boolean flag3, Camera camera4, GameRenderer gameRenderer5, Matrix4f matrix4f6, Matrix4f matrix4f7, CallbackInfo callbackInfo8
   ) {
      if (EventInvoker.hasListeners(Event3DRender.class)) {
         Profilers.get().swap("AspectDLC_renderWorld");
         MatrixStack matrixStack9 = new MatrixStack();
         matrixStack9.multiplyPositionMatrix(matrix4f6);
         new Event3DRender(matrixStack9, matrix4f6, matrix4f7, camera4, renderTickCounter2.getTickDelta(false)).call();
      }
   }

   @Inject(
      method = {"drawEntityOutlinesFramebuffer()V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$drawEntityOutlinesFramebuffer(CallbackInfo callbackInfo1) {
      if (ModuleManager.a != null) {
         ShaderCrystal shaderCrystal2 = ModuleManager.aq;
         if (shaderCrystal2 != null && shaderCrystal2.isEnabled()) {
            callbackInfo1.cancel();
         }
      }
   }

   @ModifyVariable(
      method = {"drawBlockOutline(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/entity/Entity;DDDLnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)V"},
      at = @At("HEAD"),
      argsOnly = true,
      ordinal = 0
   )
   private int aspect$rainbowOutline(int intValue1) {
      Atmosphere atmosphere2 = Atmosphere.c;
      if (atmosphere2 != null && atmosphere2.c("Complementary")) {
         float floatValue3 = (float)(System.currentTimeMillis() % 6000L) / 6000.0F;
         return -436207616 | Color.HSBtoRGB(floatValue3, 0.75F, 1.0F) & 16777215;
      } else {
         return intValue1;
      }
   }

   @Inject(
      method = {"drawBlockOutline(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/entity/Entity;DDDLnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void onDrawBlockOutline(CallbackInfo callbackInfo1) {
      if (ModuleManager.H.isEnabled()) {
         callbackInfo1.cancel();
      }
   }
}
