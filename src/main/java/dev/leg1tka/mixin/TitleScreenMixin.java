package dev.leg1tka.mixin;

import dev.leg1tka.gui.menu.MenuScreenRouting;
import dev.leg1tka.render.RenderPrimitives;
import dev.leg1tka.gui.AltManagerScreen;
import dev.leg1tka.gui.menu.MenuButton;
import dev.leg1tka.gui.menu.MenuClock;
import dev.leg1tka.gui.menu.MenuActions;
import dev.leg1tka.gui.menu.MenuIdleState;
import dev.leg1tka.gui.menu.MenuBackdropRenderer;
import dev.leg1tka.gui.menu.MenuLayout;
import dev.leg1tka.gui.menu.MenuNavigation;
import dev.leg1tka.gui.menu.MenuBackgroundCarousel;
import dev.leg1tka.gui.menu.MenuThumbnailCache;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({TitleScreen.class})
public abstract class TitleScreenMixin extends Screen {
   private static final float DBAS = 40.0F;
   private static final float DMAG = 1.28F;
   private static final float DGAP = 9.0F;
   private static final float DSEP = 21.0F;
   private static final float DPAD = 8.0F;
   private static final float DBOT = 26.0F;
   private final List<dev.leg1tka.gui.menu.MenuButton> btns = new ArrayList<>();
   private final float[] hov = new float[5];
   private final float[] ws = new float[5];
   private final dev.leg1tka.gui.menu.MenuClock clk = new dev.leg1tka.gui.menu.MenuClock();
   private final dev.leg1tka.gui.menu.MenuActions crn = new dev.leg1tka.gui.menu.MenuActions();
   private final dev.leg1tka.gui.menu.MenuBackgroundCarousel car = new dev.leg1tka.gui.menu.MenuBackgroundCarousel();
   private long lst = 0L;
   private static final long IDLE_MS = 25000L;
   private long idl = System.currentTimeMillis();
   private int lmx = Integer.MIN_VALUE;
   private int lmy = Integer.MIN_VALUE;
   private float AutoAccept = 0.0F;
   private float iaW = 0.0F;
   private boolean slept = false;
   private long lfr = 0L;

   private boolean poke() {
      this.idl = System.currentTimeMillis();
      if (!this.slept) {
         return false;
      } else {
         this.slept = false;
         if (this.AutoAccept > 0.3F) {
            this.iaW = this.AutoAccept;
            dev.leg1tka.gui.menu.MenuIdleState.b();
            return true;
         } else {
            return false;
         }
      }
   }

   protected TitleScreenMixin(Text text1) {
      super(text1);
   }

   private void suppressIas() {
      try {
         Class class1 = Class.forName("ru.vidtu.ias.config.IASConfig");
         Field field2 = class1.getField("titleText");
         field2.set(null, false);
         Field field3 = class1.getField("titleButton");
         field3.set(null, false);
         Field field4 = class1.getField("serversText");
         field4.set(null, false);
         Field field5 = class1.getField("serversButton");
         field5.set(null, false);
      } catch (Throwable throwable6) {
      }
   }

   @Inject(
      method = {"init()V"},
      at = {@At("TAIL")}
   )
   private void onInit(CallbackInfo callbackInfo1) {
      this.suppressIas();
      dev.leg1tka.gui.menu.MenuThumbnailCache.a();
      this.clk.a();
      this.btns.clear();
      this.clearChildren();

      for (int intValue2 = 0; intValue2 < 5; intValue2++) {
         this.hov[intValue2] = 0.0F;
      }

      this.lst = 0L;
      dev.leg1tka.gui.menu.MenuLayout.a();
      int intValue12 = Math.round(dev.leg1tka.gui.menu.MenuLayout.a(40.0F));
      int intValue3 = Math.round(dev.leg1tka.gui.menu.MenuLayout.a(9.0F));
      int intValue4 = intValue12 * 5 + intValue3 * 4;
      int intValue5 = (this.width - intValue4) / 2;
      int intValue6 = Math.round(this.baseY());
      dev.leg1tka.gui.menu.MenuButton value7 = new dev.leg1tka.gui.menu.MenuButton(intValue5, intValue6, intValue12, "w", Text.literal("Одиночная игра"), buttonWidget1x -> {
         if (this.client != null) {
            this.client.setScreen(new SelectWorldScreen(this));
         }
      });
      this.btns.add(value7);
      this.addDrawableChild(value7);
      intValue5 += intValue12 + intValue3;
      dev.leg1tka.gui.menu.MenuButton value8 = new dev.leg1tka.gui.menu.MenuButton(intValue5, intValue6, intValue12, "x", Text.literal("Сетевая игра"), buttonWidget1x -> {
         if (this.client != null) {
            this.client.setScreen(new MultiplayerScreen(this));
         }
      });
      this.btns.add(value8);
      this.addDrawableChild(value8);
      intValue5 += intValue12 + intValue3;
      dev.leg1tka.gui.menu.MenuButton value9 = new dev.leg1tka.gui.menu.MenuButton(intValue5, intValue6, intValue12, "y", Text.literal("Аккаунты"), buttonWidget1x -> {
         if (this.client != null) {
            this.client.setScreen(new AltManagerScreen(this));
         }
      });
      this.btns.add(value9);
      this.addDrawableChild(value9);
      intValue5 += intValue12 + intValue3;
      dev.leg1tka.gui.menu.MenuButton value10 = new dev.leg1tka.gui.menu.MenuButton(intValue5, intValue6, intValue12, "h", Text.literal("Настройки"), buttonWidget1x -> {
         if (this.client != null) {
            this.client.setScreen(new OptionsScreen(this, this.client.options));
         }
      });
      this.btns.add(value10);
      this.addDrawableChild(value10);
      intValue5 += intValue12 + intValue3;
      dev.leg1tka.gui.menu.MenuButton value11 = new dev.leg1tka.gui.menu.MenuButton(intValue5, intValue6, intValue12, "s", Text.literal("Выход"), buttonWidget1x -> {
         if (this.client != null) {
            this.client.scheduleStop();
         }
      }).a();
      this.btns.add(value11);
      this.addDrawableChild(value11);
   }

   private float baseY() {
      return (float)this.height - dev.leg1tka.gui.menu.MenuLayout.a(74.0F);
   }

   private float gapAfter(int intValue1) {
      return dev.leg1tka.gui.menu.MenuLayout.a(intValue1 == 3 ? 21.0F : 9.0F);
   }

   private void updateDock(int intValue1, int intValue2) {
      if (this.btns.size() >= 5) {
         long longValue3 = System.nanoTime();
         float floatValue5 = this.lst == 0L ? 0.016F : Math.min(0.05F, (float)((double)(longValue3 - this.lst) / 1.0E9));
         this.lst = longValue3;
         float floatValue6 = dev.leg1tka.gui.menu.MenuLayout.a(40.0F);
         float floatValue7 = floatValue6 * 0.27999997F;
         float floatValue8 = this.baseY();
         float floatValue9 = dev.leg1tka.gui.menu.MenuLayout.a(14.0F);
         int intValue10 = -1;
         if (!this.car.a() && (float)intValue2 >= floatValue8 - floatValue9 && (float)intValue2 <= floatValue8 + floatValue6 + floatValue9) {
            float floatValue11 = Float.MAX_VALUE;

            for (int intValue12 = 0; intValue12 < this.btns.size(); intValue12++) {
               dev.leg1tka.gui.menu.MenuButton value13 = this.btns.get(intValue12);
               float floatValue14 = value13.b() + value13.c() * 0.5F;
               float floatValue15 = Math.abs((float)intValue1 - floatValue14);
               if (floatValue15 < floatValue11) {
                  floatValue11 = floatValue15;
                  intValue10 = intValue12;
               }
            }

            dev.leg1tka.gui.menu.MenuButton value19 = this.btns.get(0);
            dev.leg1tka.gui.menu.MenuButton value21 = this.btns.get(this.btns.size() - 1);
            float floatValue24 = dev.leg1tka.gui.menu.MenuLayout.a(6.0F);
            if ((float)intValue1 < value19.b() - floatValue24 || (float)intValue1 > value21.b() + value21.c() + floatValue24) {
               intValue10 = -1;
            }
         }

         float floatValue18 = Math.min(1.0F, floatValue5 * 16.0F);
         float floatValue20 = 0.0F;

         for (int intValue22 = 0; intValue22 < 5; intValue22++) {
            float floatValue25 = intValue10 == intValue22 ? 1.0F : 0.0F;
            this.hov[intValue22] = this.hov[intValue22] + (floatValue25 - this.hov[intValue22]) * floatValue18;
            this.ws[intValue22] = floatValue6 + this.hov[intValue22] * floatValue7;
            floatValue20 += this.ws[intValue22];
            if (intValue22 < 4) {
               floatValue20 += this.gapAfter(intValue22);
            }
         }

         float floatValue23 = ((float)this.width - floatValue20) * 0.5F;

         for (int intValue26 = 0; intValue26 < 5; intValue26++) {
            dev.leg1tka.gui.menu.MenuButton value27 = this.btns.get(intValue26);
            float floatValue16 = this.ws[intValue26];
            float floatValue17 = floatValue8 - (floatValue16 - floatValue6) * 0.6F;
            value27.a(floatValue23, floatValue17, floatValue16, floatValue16, this.hov[intValue26]);
            floatValue23 += floatValue16;
            if (intValue26 < 4) {
               floatValue23 += this.gapAfter(intValue26);
            }
         }
      }
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;IIF)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onRender(DrawContext drawContext1, int intValue2, int intValue3, float floatValue4, CallbackInfo callbackInfo5) {
      this.suppressIas();
      if (this.children().size() != this.btns.size()) {
         this.children().removeIf(value1x -> !this.btns.contains(value1x));
      }

      dev.leg1tka.gui.menu.MenuLayout.a();
      float floatValue6 = (float)this.width;
      float floatValue7 = (float)this.height;
      MatrixStack matrixStack8 = drawContext1.getMatrices();
      dev.leg1tka.gui.menu.MenuScreenRouting.c.a(drawContext1, floatValue6, floatValue7);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack8, 0.0F, 0.0F, floatValue6, floatValue7 * 0.3F, 0.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.38F, 329226), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.004F, 329226));
      dev.leg1tka.render.RenderPrimitives.b(matrixStack8, 0.0F, floatValue7 * 0.55F, floatValue6, floatValue7 * 0.45F + 1.0F, 0.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.004F, 329226), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.62F, 329226));
      drawContext1.draw();
      dev.leg1tka.gui.menu.MenuBackdropRenderer.e();
      dev.leg1tka.render.RenderPrimitives.e();
      float floatValue9 = dev.leg1tka.gui.menu.MenuNavigation.b();
      float floatValue10 = floatValue9 * (1.0F - dev.leg1tka.gui.menu.MenuBackdropRenderer.b(this.car.b()));
      long longValue11 = System.currentTimeMillis();
      long longValue13 = System.nanoTime();
      float floatValue15 = this.lfr == 0L ? 0.016F : Math.min(0.05F, (float)((double)(longValue13 - this.lfr) / 1.0E9));
      this.lfr = longValue13;
      if (intValue2 != this.lmx || intValue3 != this.lmy) {
         boolean flag16 = this.lmx == Integer.MIN_VALUE;
         this.lmx = intValue2;
         this.lmy = intValue3;
         if (!flag16) {
            this.poke();
         }
      }

      if (this.car.b() < 0.01F && longValue11 - this.idl > 25000L && !dev.leg1tka.gui.menu.MenuIdleState.c()) {
         this.slept = true;
      }

      if (this.slept) {
         this.AutoAccept = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.AutoAccept, 1.0F, floatValue15, 0.9F);
      } else if (dev.leg1tka.gui.menu.MenuIdleState.c() && !dev.leg1tka.gui.menu.MenuIdleState.d()) {
         this.AutoAccept = this.iaW * (1.0F - dev.leg1tka.gui.menu.MenuIdleState.a(0L, 1200L));
      } else {
         this.AutoAccept = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.AutoAccept, 0.0F, floatValue15, 9.0F);
      }

      floatValue10 *= 1.0F - this.AutoAccept;
      if (this.AutoAccept > 0.01F) {
         dev.leg1tka.render.RenderPrimitives.b(matrixStack8, 0.0F, 0.0F, floatValue6, floatValue7, 0.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.22F * this.AutoAccept, 329226));
      }

      this.updateDock(intValue2, intValue3);
      float floatValue32 = dev.leg1tka.gui.menu.MenuLayout.a(40.0F);
      float floatValue17 = dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
      float floatValue18 = this.baseY();
      float floatValue19 = floatValue32 + floatValue17 * 2.0F;
      if (this.btns.size() == 5) {
         float floatValue10000 = this.btns.get(0).b() - floatValue17;
      } else {
         float floatValue36 = floatValue6 * 0.5F;
      }

      if (this.btns.size() == 5) {
         float floatValue37 = this.btns.get(4).b() + this.btns.get(4).c() + floatValue17;
      } else {
         float floatValue38 = floatValue6 * 0.5F;
      }

      float floatValue22 = floatValue18 + floatValue32 * 0.5F;
      float floatValue23 = floatValue32 * 5.0F + dev.leg1tka.gui.menu.MenuLayout.a(48.0F) + floatValue17 * 2.0F;
      boolean flag24 = !this.crn.a(floatValue6, floatValue22, floatValue19, (floatValue6 - floatValue23) * 0.5F, (floatValue6 + floatValue23) * 0.5F);
      this.clk.a(matrixStack8, floatValue6, floatValue7, floatValue9 * (1.0F - dev.leg1tka.gui.menu.MenuBackdropRenderer.b(this.car.b())), dev.leg1tka.gui.menu.MenuClock.a(floatValue7), intValue2, intValue3, this.AutoAccept);
      if (floatValue10 > 0.01F && this.btns.size() == 5) {
         float floatValue25 = (1.0F - floatValue10) * dev.leg1tka.gui.menu.MenuLayout.a(10.0F);
         dev.leg1tka.gui.menu.MenuButton value26 = this.btns.get(3);
         float floatValue27 = dev.leg1tka.gui.menu.MenuLayout.b((value26.b() + value26.c() + this.btns.get(4).b()) * 0.5F);
         float floatValue28 = floatValue32 * 0.55F;
         float floatValue29 = dev.leg1tka.gui.menu.MenuIdleState.a(3700L, 800L);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack8, floatValue27, floatValue22 + floatValue25 - floatValue28 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), floatValue28, 0.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.22F * floatValue10 * floatValue29, 16777215));
         float floatValue30 = dev.leg1tka.gui.menu.MenuIdleState.a(4300L, 1200L);
         this.crn.a(matrixStack8, intValue2, intValue3, floatValue6, floatValue22 + floatValue25, floatValue19, flag24, floatValue10 * floatValue30, floatValue30);
      }

      for (int intValue33 = 0; intValue33 < this.btns.size(); intValue33++) {
         float floatValue34 = dev.leg1tka.gui.menu.MenuIdleState.a(3000L + (long)intValue33 * 180L, 900L);
         dev.leg1tka.gui.menu.MenuButton value35 = this.btns.get(intValue33);
         value35.a(floatValue10 * floatValue34);
         value35.render(drawContext1, intValue2, intValue3, floatValue4);
      }

      this.car.a(matrixStack8, intValue2, intValue3, floatValue6, floatValue7);
      drawContext1.draw();
      callbackInfo5.cancel();
   }

   @Inject(
      method = {"mouseClicked(DDI)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onMouseClicked(double doubleValue1, double doubleValue3, int intValue5, CallbackInfoReturnable<Boolean> callbackInfoReturnable6) {
      boolean flag7 = this.poke();
      if (flag7 || dev.leg1tka.gui.menu.MenuIdleState.e()) {
         callbackInfoReturnable6.setReturnValue(true);
      } else if (this.car.a()) {
         this.car.a(doubleValue1, doubleValue3, intValue5);
         callbackInfoReturnable6.setReturnValue(true);
      } else if (this.car.b() > 0.3F) {
         callbackInfoReturnable6.setReturnValue(true);
      } else if (intValue5 == 0) {
         int intValue8 = this.crn.a(doubleValue1, doubleValue3);
         if (intValue8 == 1) {
            this.car.c();
            callbackInfoReturnable6.setReturnValue(true);
         } else if (intValue8 == 2) {
            if (this.client != null) {
               this.client.setScreen(new AltManagerScreen(this));
            }

            callbackInfoReturnable6.setReturnValue(true);
         }
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      boolean flag4 = this.poke();
      if (flag4 || dev.leg1tka.gui.menu.MenuIdleState.e()) {
         return true;
      } else {
         return this.car.a(keyCode) ? true : super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      if (this.poke() || dev.leg1tka.gui.menu.MenuIdleState.e()) {
         return true;
      } else {
         return this.car.a(verticalAmount) ? true : super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
      }
   }
}
