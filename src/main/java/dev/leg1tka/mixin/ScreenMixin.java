package dev.leg1tka.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.leg1tka.gui.menu.MenuScreenRouting;
import dev.leg1tka.modules.render.Optimizator;
import dev.leg1tka.gui.menu.MenuNavigation;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.ReconfiguringScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Screen.class})
public abstract class ScreenMixin {
   @Shadow
   public int width;
   @Shadow
   public int height;

   public ScreenMixin() {
   }

   @Inject(
      method = {"renderBackground(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onRenderBackground(DrawContext drawContext1, int intValue2, int intValue3, float floatValue4, CallbackInfo callbackInfo5) {
      MinecraftClient minecraftClient6 = MinecraftClient.getInstance();
      if (minecraftClient6 != null && minecraftClient6.world == null) {
         float floatValue7 = (float)this.width;
         float floatValue8 = (float)this.height;
         dev.leg1tka.gui.menu.MenuScreenRouting.c.a(drawContext1, floatValue7, floatValue8);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         callbackInfo5.cancel();
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onRenderHead(DrawContext drawContext1, int intValue2, int intValue3, float floatValue4, CallbackInfo callbackInfo5) {
      if (Optimizator.c.p()) {
         Screen screen6 = (Screen)(Object)this;
         if (screen6 instanceof ReconfiguringScreen || screen6 instanceof DownloadingTerrainScreen) {
            if (screen6 instanceof DownloadingTerrainScreen) {
               MinecraftClient.getInstance().setScreen(null);
            }

            callbackInfo5.cancel();
            return;
         }
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = {@At("RETURN")}
   )
   private void onRenderReturn(DrawContext drawContext1, int intValue2, int intValue3, float floatValue4, CallbackInfo callbackInfo5) {
      MinecraftClient minecraftClient6 = MinecraftClient.getInstance();
      if (minecraftClient6 != null && minecraftClient6.world == null && !((Object)this instanceof TitleScreen)) {
         drawContext1.draw();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   @Inject(
      method = {"renderWithTooltip(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = {@At("RETURN")}
   )
   private void onRenderWithTooltipReturn(DrawContext drawContext1, int intValue2, int intValue3, float floatValue4, CallbackInfo callbackInfo5) {
      MinecraftClient minecraftClient6 = MinecraftClient.getInstance();
      if (minecraftClient6 != null && minecraftClient6.world == null && !((Object)this instanceof TitleScreen)) {
         float floatValue7 = dev.leg1tka.gui.menu.MenuNavigation.b();
         if (floatValue7 < 0.999F) {
            drawContext1.draw();
            RenderSystem.disableDepthTest();
            dev.leg1tka.gui.menu.MenuScreenRouting.c.a(drawContext1, 1.0F - floatValue7);
            RenderSystem.enableDepthTest();
         }
      }
   }
}
