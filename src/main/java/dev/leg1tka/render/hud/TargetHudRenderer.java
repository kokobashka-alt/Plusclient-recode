package dev.leg1tka.render.hud;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.implement.EventRender;
import java.util.Locale;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class TargetHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   private static final Locale f = Locale.forLanguageTag("ru");
   private final dev.leg1tka.api.animation.Animation g = new dev.leg1tka.api.animation.Animation(0.0F, 9.0F, dev.leg1tka.api.animation.Easings.h);
   private final dev.leg1tka.api.animation.Animation h = new dev.leg1tka.api.animation.Animation(1.0F, 9.2F, dev.leg1tka.api.animation.Easings.h);
   private final dev.leg1tka.api.animation.Animation i = new dev.leg1tka.api.animation.Animation(0.0F, 9.2F, dev.leg1tka.api.animation.Easings.h);
   private final dev.leg1tka.api.animation.Animation j = new dev.leg1tka.api.animation.Animation(0.0F, 14.0F, dev.leg1tka.api.animation.Easings.h);
   private LivingEntity k;
   private int l = dev.leg1tka.api.utils.text.ColorUtils.a(124, 91, 242, 255);

   public TargetHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
   }

   private dev.leg1tka.render.font.MsdfTextRenderer a(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("nunito", intValue1);
   }

   @Override
   public void a(EventRender.Default default1) {
      this.b(default1);
      super.a(default1);
   }

   private void b(EventRender.Default default1) {
      if (minecraftClient.player == null) {
         this.e.c(0.0F);
         this.e.d(0.0F);
      } else {
         AttackAura attackAura2 = ModuleManager.i;
         boolean flag3 = minecraftClient.currentScreen instanceof ChatScreen;
         LivingEntity livingEntity4 = attackAura2 != null ? attackAura2.E() : null;
         boolean flag5 = flag3 || livingEntity4 != null;
         this.g.setSpeed(flag5 ? 9.0F : 5.0F);
         this.g.animateTo(flag5 ? 1.0F : 0.0F);
         float floatValue6 = MathHelper.clamp(this.g.getValue(), 0.0F, 1.0F);
         if (flag5) {
            this.k = (LivingEntity)(flag3 ? minecraftClient.player : livingEntity4);
         }

         LivingEntity livingEntity7 = this.k;
         if (livingEntity7 != null && !(floatValue6 <= 0.01F)) {
            if (flag5) {
               if (!AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow")) {
                  this.l = AspectDLC.INSTANCE.themeStorage.b().a().c[0];
               } else {
                  this.l = dev.leg1tka.api.utils.text.ColorUtils.a();
               }
            }

            int intValue8 = this.l;
            int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.b(intValue8, 0.4F);
            float floatValue10 = HPFixer.b(livingEntity7);
            boolean flag11 = floatValue10 == -1.0F;
            float floatValue12 = Math.max(1.0F, livingEntity7.getMaxHealth());
            float floatValue13 = livingEntity7.getAbsorptionAmount();
            this.h.animateTo(flag11 ? 0.0F : MathHelper.clamp(floatValue10 / floatValue12, 0.0F, 1.0F));
            this.i.animateTo(MathHelper.clamp(floatValue13 / floatValue12, 0.0F, 1.0F));
            this.j.animateTo(MathHelper.clamp((float)livingEntity7.hurtTime / 10.0F, 0.0F, 1.0F));
            float floatValue14 = MathHelper.clamp(this.h.getValue(), 0.0F, 1.0F);
            float floatValue15 = MathHelper.clamp(this.i.getValue(), 0.0F, 1.0F);
            float floatValue16 = MathHelper.clamp(this.j.getValue(), 0.0F, 1.0F);
            String string17 = livingEntity7.getName().getString();
            String string18;
            if (flag11) {
               string18 = "HP: ???";
            } else {
               string18 = "HP: " + String.format(f, "%.1f", floatValue10);
               if (floatValue13 > 0.0F) {
                  string18 = string18 + "(" + String.format(f, "%.1f", floatValue13) + ")";
               }
            }

            dev.leg1tka.render.font.MsdfTextRenderer value19 = this.a(13);
            dev.leg1tka.render.font.MsdfTextRenderer value20 = this.a(12);
            float floatValue21 = this.e.a();
            float floatValue22 = this.e.b();
            float floatValue23 = 4.0F;
            float floatValue24 = 24.0F;
            float floatValue25 = floatValue24 + floatValue23 * 2.0F;
            float floatValue26 = floatValue21 + floatValue23 + floatValue24 + 4.0F;
            float floatValue27 = Math.max(value19.a(string17), value20.a(string18));
            float floatValue28 = Math.max(92.0F, floatValue26 - floatValue21 + floatValue27 + floatValue23 + 2.0F);
            float floatValue30 = floatValue21 + floatValue28 - floatValue23 - floatValue26;
            float floatValue31 = 4.0F;
            float floatValue32 = floatValue22 + floatValue25 - floatValue23 - floatValue31;
            int intValue33 = (int)(255.0F * floatValue6);
            MatrixStack matrixStack34 = default1.getContext().getMatrices();
            matrixStack34.push();
            this.a(matrixStack34, floatValue21, floatValue22, floatValue28, floatValue25, 3.0F, intValue8, floatValue6);
            float floatValue35 = floatValue21 + floatValue23;
            float floatValue36 = floatValue22 + floatValue23;
            if (livingEntity7 instanceof PlayerEntity playerEntity37) {
               dev.leg1tka.render.RenderPrimitives.a(matrixStack34, playerEntity37.getUuid(), floatValue35, floatValue36, floatValue24, 2.0F, floatValue6, floatValue16);
            } else {
               dev.leg1tka.render.RenderPrimitives.b(matrixStack34, floatValue35, floatValue36, floatValue24, floatValue24, 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(60, 60, 66, intValue33));
               if (floatValue16 > 0.01F) {
                  dev.leg1tka.render.RenderPrimitives.b(matrixStack34, floatValue35, floatValue36, floatValue24, floatValue24, 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(220, 40, 40, (int)(200.0F * floatValue16 * floatValue6)));
               }

               this.a(16).b(matrixStack34, "?", floatValue35 + floatValue24 / 2.0F, floatValue36 + 5.0F, dev.leg1tka.api.utils.text.ColorUtils.a(220, 220, 220, intValue33));
            }

            value19.a(matrixStack34, string17, floatValue26, floatValue22 + floatValue23 + 1.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue33));
            value20.a(matrixStack34, string18, floatValue26, floatValue22 + floatValue23 + 10.5F, dev.leg1tka.api.utils.text.ColorUtils.a(235, 235, 235, intValue33));
            dev.leg1tka.render.RenderPrimitives.b(matrixStack34, floatValue26, floatValue32, floatValue30, floatValue31, 1.5F, dev.leg1tka.api.utils.text.ColorUtils.a(40, 40, 48, (int)(220.0F * floatValue6)));
            float floatValue39 = floatValue30 * floatValue14;
            if (floatValue39 > 1.0F) {
               dev.leg1tka.render.RenderPrimitives.a(matrixStack34, floatValue26, floatValue32, floatValue39, floatValue31, 1.5F, dev.leg1tka.api.utils.text.ColorUtils.a(intValue9, floatValue6), dev.leg1tka.api.utils.text.ColorUtils.a(intValue8, floatValue6), true);
            }

            float floatValue38 = floatValue30 * floatValue15;
            if (floatValue38 > 1.0F) {
               dev.leg1tka.render.RenderPrimitives.b(matrixStack34, floatValue26, floatValue32, floatValue38, floatValue31, 1.5F, dev.leg1tka.api.utils.text.ColorUtils.a(232, 188, 28, intValue33));
            }

            matrixStack34.pop();
            this.e.c(floatValue28);
            this.e.d(floatValue25);
         } else {
            this.e.c(0.0F);
            this.e.d(0.0F);
         }
      }
   }
}
