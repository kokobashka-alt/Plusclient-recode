package dev.leg1tka.mixin;

import dev.leg1tka.gui.menu.MenuScreenRouting;
import dev.leg1tka.render.RenderPrimitives;
import dev.leg1tka.render.ScissorStack;
import dev.leg1tka.gui.menu.MenuParticleRenderer;
import dev.leg1tka.gui.menu.MenuIdleState;
import dev.leg1tka.gui.menu.MenuBackdropRenderer;
import dev.leg1tka.gui.menu.MenuLayout;
import dev.leg1tka.gui.components.MenuTextRenderer;
import dev.leg1tka.gui.menu.MenuBackgroundResources;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceReload;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({SplashOverlay.class})
public class SplashOverlayMixin {
   @Shadow
   @Final
   private MinecraftClient client;
   @Shadow
   @Final
   private ResourceReload reload;
   @Shadow
   @Final
   private Consumer<Optional<Throwable>> exceptionHandler;
   @Shadow
   @Final
   private boolean reloading;
   @Shadow
   private float progress;
   @Shadow
   private long reloadCompleteTime;
   @Shadow
   private long reloadStartTime;
   @Unique
   private boolean snd;
   @Unique
   private boolean seeded;
   @Unique
   private boolean warm;
   @Unique
   private float shown = 0.0F;
   @Unique
   private long lst = 0L;
   @Unique
   private float plx = 0.0F;
   @Unique
   private float ply = 0.0F;
   @Unique
   private float XCarry = -1.0F;
   @Unique
   private float vt = 0.0F;
   @Unique
   private float vc = -1.0F;
   @Unique
   private float bgT = -1.0F;
   @Unique
   private static final float IN = 700.0F;
   @Unique
   private static final float F0 = 350.0F;
   @Unique
   private static final float FILL = 4300.0F;
   @Unique
   private static final float HOLD = 2000.0F;
   @Unique
   private static final float OUT = 2000.0F;
   @Unique
   private static final float HAND = 700.0F;
   @Unique
   private static final float I_HOLD = 820.0F;
   @Unique
   private static final float I_OUT = 360.0F;
   @Unique
   private static final dev.leg1tka.gui.menu.MenuParticleRenderer PILL = new dev.leg1tka.gui.menu.MenuParticleRenderer();

   public SplashOverlayMixin() {
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onRender(DrawContext drawContext1, int intValue2, int intValue3, float floatValue4, CallbackInfo callbackInfo5) {
      callbackInfo5.cancel();
      int intValue6 = drawContext1.getScaledWindowWidth();
      int intValue7 = drawContext1.getScaledWindowHeight();
      long longValue8 = Util.getMeasuringTimeMs();
      if (this.reloadStartTime == -1L) {
         this.reloadStartTime = longValue8;
      }

      float floatValue10 = this.reload.getProgress();
      this.progress = MathHelper.clamp(this.progress * 0.92F + floatValue10 * 0.08F, 0.0F, 1.0F);
      if (this.reloadCompleteTime == -1L && this.reload.isComplete()) {
         try {
            this.reload.throwException();
            this.exceptionHandler.accept(Optional.empty());
         } catch (Throwable throwable20) {
            this.exceptionHandler.accept(Optional.of(throwable20));
         }

         this.reloadCompleteTime = longValue8;
      }

      long longValue11 = System.nanoTime();
      float floatValue13 = this.lst == 0L ? 0.016F : Math.min(0.05F, (float)((double)(longValue11 - this.lst) / 1.0E9));
      this.lst = longValue11;
      float floatValue14 = this.reloadCompleteTime > -1L ? 1.0F : this.progress;
      this.shown = Math.min(floatValue14, this.shown + (floatValue14 - this.shown) * Math.min(1.0F, floatValue13 * 6.0F) + floatValue13 * 0.02F);
      float floatValue15 = this.reloadCompleteTime > -1L ? (float)(longValue8 - this.reloadCompleteTime) : -1.0F;
      if (dev.leg1tka.gui.menu.MenuBackgroundResources.a()) {
         dev.leg1tka.gui.menu.MenuLayout.a();
         if (this.reloading) {
            if (floatValue15 >= 1180.0F) {
               this.finish(intValue6, intValue7);
            } else {
               if (this.client.currentScreen != null) {
                  this.client.currentScreen.render(drawContext1, intValue2, intValue3, floatValue4);
                  drawContext1.draw();
                  dev.leg1tka.render.RenderPrimitives.e();
               }

               this.island(drawContext1, (float)intValue6, (float)intValue7, longValue8, floatValue13, floatValue15);
            }
         } else {
            this.vt = this.vt + Math.min(floatValue13, 0.05F) * 1000.0F;
            if (this.reloadCompleteTime > -1L && this.vc < 0.0F) {
               this.vc = this.vt;
            }

            float floatValue21 = this.vc < 0.0F ? Float.MAX_VALUE : Math.max(this.vc, 4950.0F) + 2000.0F;
            float floatValue17 = this.vt - floatValue21;
            float floatValue18 = smooth(floatValue17 / 2000.0F);
            float floatValue19 = smooth((floatValue17 - 2000.0F) / 700.0F);
            if (floatValue17 >= 2000.0F) {
               dev.leg1tka.gui.menu.MenuIdleState.a();
               if (!this.seeded) {
                  this.seeded = true;
                  dev.leg1tka.gui.menu.MenuScreenRouting.c.a(this.plx, this.ply);
               }

               if (!this.snd) {
                  this.snd = true;
                  if (this.client.getSoundManager() != null) {
                     this.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 1.0F, 0.7F));
                  }
               }
            }

            if (floatValue17 >= 2700.0F) {
               this.finish(intValue6, intValue7);
            } else {
               if (this.client.currentScreen != null) {
                  if (floatValue17 >= 2000.0F) {
                     this.client.currentScreen.render(drawContext1, intValue2, intValue3, floatValue4);
                     drawContext1.draw();
                     dev.leg1tka.render.RenderPrimitives.e();
                  } else if (!this.warm && this.vc >= 0.0F && this.vt - this.vc > 150.0F) {
                     this.warm = true;
                     this.client.currentScreen.render(drawContext1, intValue2, intValue3, floatValue4);
                     drawContext1.draw();
                     dev.leg1tka.render.RenderPrimitives.e();
                  }
               }

               this.fancy(drawContext1, (float)intValue6, (float)intValue7, floatValue13, floatValue17, floatValue18, floatValue19);
            }
         }
      } else {
         float floatValue16 = smooth(floatValue15 / 800.0F);
         if (floatValue16 > 0.0F && !this.reloading) {
            dev.leg1tka.gui.menu.MenuIdleState.a();
         }

         if (floatValue16 >= 1.0F) {
            this.finish(intValue6, intValue7);
         } else {
            if ((floatValue16 > 0.0F || this.reloading) && this.client.currentScreen != null) {
               this.client.currentScreen.render(drawContext1, intValue2, intValue3, floatValue4);
               drawContext1.draw();
               dev.leg1tka.render.RenderPrimitives.e();
            }

            this.simple(drawContext1, intValue6, intValue7, floatValue16, floatValue14);
         }
      }
   }

   @Unique
   private static float smooth(float floatValue0) {
      floatValue0 = MathHelper.clamp(floatValue0, 0.0F, 1.0F);
      return floatValue0 * floatValue0 * (3.0F - 2.0F * floatValue0);
   }

   @Unique
   private static float backOut(float floatValue0) {
      floatValue0 = MathHelper.clamp(floatValue0, 0.0F, 1.0F) - 1.0F;
      float floatValue1 = 1.2F;
      return 1.0F + (floatValue1 + 1.0F) * floatValue0 * floatValue0 * floatValue0 + floatValue1 * floatValue0 * floatValue0;
   }

   @Unique
   private void finish(int intValue1, int intValue2) {
      if (this.client.currentScreen != null) {
         this.client.currentScreen.init(this.client, intValue1, intValue2);
      }

      this.client.setOverlay(null);
   }

   @Unique
   private void wallpaper(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, Identifier identifier5, int intValue6) {
      float floatValue7 = (floatValue2 + 114.0F) * floatValue4;
      float floatValue8 = (floatValue3 + 18.0F) * floatValue4;
      float floatValue9 = (floatValue2 - floatValue7) * 0.5F + this.plx * 12.0F;
      float floatValue10 = (floatValue3 - floatValue8) * 0.5F + this.ply * 7.0F;
      float floatValue11 = dev.leg1tka.gui.menu.MenuBackgroundResources.e();
      float floatValue12 = floatValue7 / floatValue8;
      float floatValue13 = 0.0F;
      float floatValue14 = 0.0F;
      float floatValue15 = 1.0F;
      float floatValue16 = 1.0F;
      if (floatValue12 > floatValue11) {
         float floatValue17 = floatValue11 / floatValue12;
         floatValue14 = (1.0F - floatValue17) * 0.5F;
         floatValue16 = floatValue14 + floatValue17;
      } else {
         float floatValue18 = floatValue12 / floatValue11;
         floatValue13 = (1.0F - floatValue18) * 0.5F;
         floatValue15 = floatValue13 + floatValue18;
      }

      dev.leg1tka.render.RenderPrimitives.a(matrixStack1, identifier5, floatValue9, floatValue10, floatValue7, floatValue8, floatValue13, floatValue14, floatValue15, floatValue16, intValue6);
      dev.leg1tka.render.RenderPrimitives.e();
   }

   @Unique
   private void fancy(DrawContext drawContext1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      MatrixStack matrixStack8 = drawContext1.getMatrices();
      float floatValue9 = 1.0F - floatValue7;
      float floatValue10 = floatValue2 * 0.5F;
      int intValue11 = this.client.getWindow().getWidth();
      int intValue12 = this.client.getWindow().getHeight();
      if (intValue11 > 0 && intValue12 > 0) {
         float floatValue13 = MathHelper.clamp((float)(this.client.mouse.getX() / (double)intValue11), 0.0F, 1.0F);
         float floatValue14 = MathHelper.clamp((float)(this.client.mouse.getY() / (double)intValue12), 0.0F, 1.0F);
         float floatValue15 = Math.min(1.0F, Math.min(floatValue4, 0.05F) * 3.2F);
         this.plx = MathHelper.lerp(floatValue15, this.plx, (0.5F - floatValue13) * 2.0F);
         this.ply = MathHelper.lerp(floatValue15, this.ply, (0.5F - floatValue14) * 2.0F);
      }

      if (floatValue7 <= 0.0F) {
         dev.leg1tka.render.RenderPrimitives.b(matrixStack8, 0.0F, 0.0F, floatValue2, floatValue3, 0.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(1.0F, 460554));
      }

      float floatValue42 = 1.07F + 0.035F * (1.0F - dev.leg1tka.gui.menu.MenuBackdropRenderer.b(this.vt / 9000.0F));
      float floatValue43 = dev.leg1tka.gui.menu.MenuIdleState.d() ? dev.leg1tka.gui.menu.MenuIdleState.g() : MathHelper.lerp(floatValue6, floatValue42, 1.07F);
      boolean flag44 = dev.leg1tka.gui.menu.MenuBackgroundResources.b();
      boolean flag16 = dev.leg1tka.gui.menu.MenuBackgroundResources.c();
      boolean flag17 = flag44 || flag16 && this.vt > 1500.0F;
      if (flag17 && this.bgT < 0.0F) {
         this.bgT = this.vt;
      }

      float floatValue18 = this.bgT < 0.0F ? 0.0F : dev.leg1tka.gui.menu.MenuBackdropRenderer.b((this.vt - this.bgT) / 1100.0F);
      if (flag16 && (floatValue6 > 0.001F || !flag44) && floatValue18 > 0.004F) {
         this.wallpaper(matrixStack8, floatValue2, floatValue3, floatValue43, dev.leg1tka.gui.menu.MenuBackgroundResources.b, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue18 * floatValue9, 16777215));
      }

      if (flag44 && floatValue18 * (1.0F - floatValue6) * floatValue9 > 0.004F) {
         this.wallpaper(matrixStack8, floatValue2, floatValue3, floatValue43, dev.leg1tka.gui.menu.MenuBackgroundResources.a, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue18 * (1.0F - floatValue6) * floatValue9, 16777215));
      }

      float floatValue19 = (1.0F - floatValue6) * floatValue9;
      if (floatValue19 > 0.004F) {
         float floatValue20 = flag44 ? 0.6F : 0.68F;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack8, 0.0F, 0.0F, floatValue2, floatValue3, 0.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue20 * floatValue19, 329226));
      }

      float floatValue45 = dev.leg1tka.gui.menu.MenuBackdropRenderer.b((this.vt - 150.0F) / 700.0F);
      float floatValue21 = smooth(floatValue5 / 1000.0F);
      float floatValue22 = floatValue45 * (1.0F - floatValue21) * floatValue9;
      if (floatValue22 < 0.004F) {
         dev.leg1tka.render.RenderPrimitives.e();
      } else {
         int intValue23 = dev.leg1tka.gui.menu.MenuBackdropRenderer.c();
         int intValue24 = dev.leg1tka.gui.menu.MenuBackdropRenderer.d();
         float floatValue25 = smoother((this.vt - 350.0F) / 4300.0F);
         float floatValue26 = this.vt - 350.0F - 4300.0F;
         float floatValue27 = 1.0F - smooth(floatValue26 / 600.0F);
         float floatValue28 = this.vt / 1000.0F;
         float floatValue29 = 1.0F + 0.035F * floatValue21;
         float floatValue30 = Math.min(Math.max(floatValue2 * 0.3F, dev.leg1tka.gui.menu.MenuLayout.a(260.0F)), Math.min(dev.leg1tka.gui.menu.MenuLayout.a(420.0F), floatValue2 - dev.leg1tka.gui.menu.MenuLayout.a(48.0F))) * floatValue29;
         float floatValue31 = floatValue30 * 0.09104704F;
         float floatValue32 = floatValue3 * 0.5F - (1.0F - floatValue45) * dev.leg1tka.gui.menu.MenuLayout.a(6.0F) - dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
         if (dev.leg1tka.gui.menu.MenuBackgroundResources.d()) {
            dev.leg1tka.gui.menu.MenuBackgroundResources.a(matrixStack8, floatValue10, floatValue32, floatValue30, floatValue22, 0.6F);
         } else {
            float floatValue33 = 20.0F;
            float floatValue34 = floatValue33 * 0.18F;
            float floatValue35 = dev.leg1tka.gui.components.MenuTextRenderer.a("suisse", floatValue33, "ASPECTDLC", floatValue34);
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack8, "suisse", "ASPECTDLC", floatValue33, floatValue10 - floatValue35 * 0.5F, floatValue32, floatValue34, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue22, 16777215), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue22, 16777215));
         }

         dev.leg1tka.render.RenderPrimitives.e();
         float floatValue46 = Math.max(dev.leg1tka.gui.menu.MenuLayout.c() * 2.0F, dev.leg1tka.gui.menu.MenuLayout.a(3.0F));
         float floatValue47 = floatValue30 * 0.56F;
         float floatValue48 = dev.leg1tka.gui.menu.MenuLayout.b(floatValue10 - floatValue47 * 0.5F);
         float floatValue36 = dev.leg1tka.gui.menu.MenuLayout.b(floatValue32 + floatValue31 * 0.5F + dev.leg1tka.gui.menu.MenuLayout.a(24.0F) + (1.0F - floatValue45) * dev.leg1tka.gui.menu.MenuLayout.a(4.0F));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack8, floatValue48, floatValue36, floatValue47, floatValue46, floatValue46 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.12F * floatValue22, 16777215));
         if (floatValue25 > 0.001F) {
            float floatValue37 = Math.max(floatValue46, floatValue47 * floatValue25);
            float floatValue38 = 0.85F + 0.15F * MathHelper.sin(floatValue28 * 1.7F);
            dev.leg1tka.render.RenderPrimitives.a(matrixStack8, floatValue48, floatValue36, floatValue37, floatValue46, floatValue46 * 0.5F, dev.leg1tka.gui.menu.MenuLayout.a(12.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.55F * floatValue38 * floatValue22, intValue23));
            dev.leg1tka.render.RenderPrimitives.a(matrixStack8, floatValue48 + floatValue37 * 0.5F, floatValue36, floatValue37 * 0.5F, floatValue46, floatValue46 * 0.5F, dev.leg1tka.gui.menu.MenuLayout.a(7.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.35F * floatValue38 * floatValue22, intValue24));
            dev.leg1tka.render.RenderPrimitives.a(matrixStack8, floatValue48, floatValue36, floatValue37, floatValue46, floatValue46 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue22, intValue23), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue22, intValue24), true);
            float floatValue39 = dev.leg1tka.gui.menu.MenuLayout.a(60.0F);
            float floatValue40 = (floatValue28 * 0.55F % 1.4F - 0.2F) * (floatValue47 + floatValue39) - floatValue39;
            dev.leg1tka.render.ScissorStack.a();
            dev.leg1tka.render.ScissorStack.a((double)floatValue48, (double)(floatValue36 - 1.0F), (double)floatValue37, (double)(floatValue46 + 2.0F));
            dev.leg1tka.render.RenderPrimitives.a(matrixStack8, floatValue48 + floatValue40, floatValue36, floatValue39 * 0.5F, floatValue46, 0.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.004F, 16777215), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.5F * floatValue22, 16777215), true);
            dev.leg1tka.render.RenderPrimitives.a(matrixStack8, floatValue48 + floatValue40 + floatValue39 * 0.5F, floatValue36, floatValue39 * 0.5F, floatValue46, 0.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.5F * floatValue22, 16777215), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.004F, 16777215), true);
            dev.leg1tka.render.ScissorStack.c();
            dev.leg1tka.render.ScissorStack.b();
            if (floatValue27 > 0.01F) {
               float floatValue41 = floatValue46 * 2.2F;
               dev.leg1tka.render.RenderPrimitives.a(
                  matrixStack8,
                  floatValue48 + floatValue37 - floatValue41 * 0.5F,
                  floatValue36 + floatValue46 * 0.5F - floatValue41 * 0.5F,
                  floatValue41,
                  floatValue41,
                  floatValue41 * 0.5F,
                  dev.leg1tka.gui.menu.MenuLayout.a(8.0F),
                  dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.5F * floatValue27 * floatValue22, intValue24)
               );
               dev.leg1tka.render.RenderPrimitives.a(matrixStack8, floatValue48 + floatValue37, floatValue36 + floatValue46 * 0.5F, floatValue46 * 1.7F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.95F * floatValue27 * floatValue22, 16777215));
            }
         }

         dev.leg1tka.render.RenderPrimitives.e();
      }
   }

   @Unique
   private static float smoother(float floatValue0) {
      floatValue0 = MathHelper.clamp(floatValue0, 0.0F, 1.0F);
      return floatValue0 * floatValue0 * floatValue0 * (floatValue0 * (floatValue0 * 6.0F - 15.0F) + 10.0F);
   }

   @Unique
   private void island(DrawContext drawContext1, float floatValue2, float floatValue3, long longValue4, float floatValue6, float floatValue7) {
      MatrixStack matrixStack8 = drawContext1.getMatrices();
      long longValue9 = longValue4 - this.reloadStartTime;
      boolean flag11 = floatValue7 >= 0.0F;
      float floatValue12 = MathHelper.clamp((float)longValue9 / 560.0F, 0.0F, 1.0F);
      float floatValue13 = dev.leg1tka.gui.menu.MenuBackdropRenderer.b((float)longValue9 / 260.0F);
      float floatValue14 = flag11 ? smooth((floatValue7 - 820.0F) / 360.0F) : 0.0F;
      float floatValue15 = floatValue13 * (1.0F - floatValue14);
      if (!(floatValue15 < 0.01F)) {
         boolean flag16 = flag11 && floatValue7 > 160.0F;
         float floatValue17 = flag11 ? Math.abs(1.0F - MathHelper.clamp(floatValue7 / 160.0F, 0.0F, 2.0F)) : 1.0F;
         String string18 = flag16
            ? "Ресурсы обновлены"
            : "Обновление ресурсов";
         String string19;
         if (flag16) {
            float floatValue20 = Math.max(0.1F, (float)(this.reloadCompleteTime - this.reloadStartTime) / 1000.0F);
            string19 = "Готово за " + String.format(Locale.ROOT, "%.1f", floatValue20).replace('.', ',') + " с";
         } else {
            string19 = this.packs();
         }

         float floatValue39 = dev.leg1tka.gui.menu.MenuLayout.a(50.0F);
         float floatValue21 = floatValue39 * 0.5F;
         float floatValue22 = dev.leg1tka.gui.menu.MenuLayout.a(32.0F);
         float floatValue23 = (floatValue39 - floatValue22) * 0.5F;
         float floatValue24 = Math.max(dev.leg1tka.gui.components.MenuTextRenderer.a(13.5F, string18), dev.leg1tka.gui.components.MenuTextRenderer.a(11.5F, string19));
         float floatValue25 = flag16 ? 0.0F : dev.leg1tka.gui.components.MenuTextRenderer.a(13.5F, "100%") + dev.leg1tka.gui.menu.MenuLayout.a(14.0F);
         float floatValue26 = floatValue23 + floatValue22 + dev.leg1tka.gui.menu.MenuLayout.a(12.0F) + floatValue24 + floatValue25 + dev.leg1tka.gui.menu.MenuLayout.a(20.0F);
         this.XCarry = this.XCarry < 0.0F ? floatValue26 : dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.XCarry, floatValue26, floatValue6, 10.0F);
         float floatValue27 = backOut(floatValue12) * (1.0F - floatValue14);
         float floatValue28 = floatValue39 + (this.XCarry - floatValue39) * floatValue27;
         float floatValue29 = dev.leg1tka.gui.menu.MenuLayout.b(floatValue2 * 0.5F - floatValue28 * 0.5F);
         float floatValue30 = dev.leg1tka.gui.menu.MenuLayout.b(floatValue3 * 0.75F - floatValue39 * 0.5F + (1.0F - floatValue13) * dev.leg1tka.gui.menu.MenuLayout.a(10.0F) + floatValue14 * dev.leg1tka.gui.menu.MenuLayout.a(8.0F));
         dev.leg1tka.render.RenderPrimitives.a(matrixStack8, floatValue29, floatValue30 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F), floatValue28, floatValue39, floatValue21, dev.leg1tka.gui.menu.MenuLayout.a(26.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.32F * floatValue15, 0));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack8, floatValue29, floatValue30, floatValue28, floatValue39, floatValue21, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.84F * floatValue15, dev.leg1tka.render.RenderPrimitives.a(dev.leg1tka.gui.menu.MenuBackdropRenderer.f(), 1.0F)));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack8, floatValue29, floatValue30, floatValue28, floatValue39, floatValue21, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.07F * floatValue15, 16777215), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.01F * floatValue15, 16777215));
         int intValue31 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.09F * floatValue15, 16777215);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack8, floatValue29, floatValue30, floatValue28, floatValue39, floatValue21, dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue31, intValue31, intValue31, intValue31);
         float floatValue32 = floatValue29 + floatValue23 + floatValue22 * 0.5F;
         float floatValue33 = floatValue30 + floatValue39 * 0.5F;
         float floatValue34 = flag11 ? Math.max(this.shown, dev.leg1tka.gui.menu.MenuBackdropRenderer.b(floatValue7 / 260.0F)) : this.shown;
         dev.leg1tka.gui.menu.MenuBackdropRenderer.b(matrixStack8, floatValue32, floatValue33, floatValue22, dev.leg1tka.gui.menu.MenuLayout.a(3.0F), floatValue34, 0.14F, floatValue15);
         float floatValue35 = flag11 ? dev.leg1tka.gui.menu.MenuBackdropRenderer.b((floatValue7 - 120.0F) / 420.0F) : 0.0F;
         if (floatValue35 < 1.0F) {
            PILL.a(matrixStack8, floatValue32, floatValue33, dev.leg1tka.gui.menu.MenuLayout.a(17.0F) * (1.0F - floatValue35 * 0.4F), Float.NaN, Float.NaN, floatValue15 * (1.0F - floatValue35), 0.0F);
         }

         if (floatValue35 > 0.0F) {
            dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack8, floatValue32, floatValue33, dev.leg1tka.gui.menu.MenuLayout.a(15.0F), Math.max(dev.leg1tka.gui.menu.MenuLayout.c() * 2.0F, dev.leg1tka.gui.menu.MenuLayout.a(2.4F)), floatValue35, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue15, 16777215));
         }

         float floatValue36 = this.XCarry > floatValue39 ? MathHelper.clamp((floatValue28 - floatValue39) / (this.XCarry - floatValue39), 0.0F, 1.0F) : 0.0F;
         float floatValue37 = floatValue15 * smooth((floatValue36 - 0.55F) / 0.45F) * floatValue17;
         if (floatValue37 > 0.01F) {
            dev.leg1tka.render.ScissorStack.a();
            dev.leg1tka.render.ScissorStack.a((double)(floatValue29 + floatValue21 * 0.5F), (double)floatValue30, (double)(floatValue28 - floatValue21), (double)floatValue39);
            float floatValue38 = floatValue32 + floatValue22 * 0.5F + dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack8, string18, 13.5F, floatValue38, floatValue33 - dev.leg1tka.gui.menu.MenuLayout.a(7.5F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.95F * floatValue37, 16777215));
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack8, string19, 11.5F, floatValue38, floatValue33 + dev.leg1tka.gui.menu.MenuLayout.a(8.5F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.55F * floatValue37, 16777215));
            if (!flag16) {
               dev.leg1tka.gui.components.MenuTextRenderer.c(matrixStack8, Math.round(this.shown * 100.0F) + "%", 13.5F, floatValue29 + floatValue28 - dev.leg1tka.gui.menu.MenuLayout.a(20.0F), floatValue33, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.95F * floatValue37, 16777215));
            }

            dev.leg1tka.render.ScissorStack.c();
            dev.leg1tka.render.ScissorStack.b();
         }

         dev.leg1tka.render.RenderPrimitives.e();
      }
   }

   @Unique
   private String packs() {
      int intValue1 = 0;

      try {
         for (String string3 : this.client.getResourcePackManager().getEnabledIds()) {
            if (string3.startsWith("file/")) {
               intValue1++;
            }
         }
      } catch (Throwable throwable5) {
      }

      if (intValue1 == 0) {
         return "Стандартные ресурсы";
      } else {
         int intValue6 = intValue1 % 10;
         int intValue7 = intValue1 % 100;
         String string4 = intValue6 == 1 && intValue7 != 11
            ? "пакет"
            : (intValue6 < 2 || intValue6 > 4 || intValue7 >= 12 && intValue7 <= 14 ? "пакетов" : "пакета");
         return intValue1 + " " + string4 + " ресурсов";
      }
   }

   @Unique
   private void simple(DrawContext drawContext1, int intValue2, int intValue3, float floatValue4, float floatValue5) {
      int intValue6 = (int)((1.0F - floatValue4) * 255.0F);
      if (!this.reloading) {
         drawContext1.fill(0, 0, intValue2, intValue3, intValue6 << 24 | 460553);
      }

      short value7 = 200;
      int intValue8 = (intValue2 - value7) / 2;
      int intValue9 = this.reloading ? intValue3 - 24 : intValue3 / 2 + 28;
      drawContext1.fill(intValue8, intValue9, intValue8 + value7, intValue9 + 2, Math.min(intValue6, 40) << 24 | 16777215);
      int intValue10 = (int)((float)value7 * floatValue5);
      if (intValue10 > 0) {
         drawContext1.fill(intValue8, intValue9, intValue8 + intValue10, intValue9 + 2, intValue6 << 24 | 16777215);
      }
   }
}
