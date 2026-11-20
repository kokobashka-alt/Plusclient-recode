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
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class ArmorHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   public ArmorHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
   }

   @Override
   public void a(EventRender.Default default1) {
      if (minecraftClient != null && minecraftClient.player != null) {
         DrawContext drawContext2 = default1.getContext();
         MatrixStack matrixStack3 = drawContext2.getMatrices();
         float floatValue4 = this.e.a();
         float floatValue5 = this.e.b();
         ItemStack itemStack6 = minecraftClient.player.getInventory().getArmorStack(3);
         ItemStack itemStack7 = minecraftClient.player.getInventory().getArmorStack(2);
         ItemStack itemStack8 = minecraftClient.player.getInventory().getArmorStack(1);
         ItemStack itemStack9 = minecraftClient.player.getInventory().getArmorStack(0);
         ItemStack itemStack10 = minecraftClient.player.getOffHandStack();
         float floatValue11 = 18.0F;
         float floatValue12 = 2.0F;
         float floatValue13 = 2.0F;
         float floatValue14 = 1.0F;
         int intValue15;
         if (!AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow")) {
            intValue15 = AspectDLC.INSTANCE.themeStorage.b().a().c[0];
         } else {
            intValue15 = dev.leg1tka.api.utils.text.ColorUtils.a();
         }

         ItemStack[] itemStackArray16 = new ItemStack[]{itemStack6, itemStack7, itemStack8, itemStack9, itemStack10};
         float floatValue17 = floatValue4;
         float floatValue18 = floatValue5;

         for (ItemStack itemStack22 : itemStackArray16) {
            if (!itemStack22.isEmpty()) {
               dev.leg1tka.render.RenderPrimitives.b(matrixStack3, floatValue17, floatValue18, floatValue11, floatValue11, 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(30, 30, 30, 180));
               int intValue23 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue15, 0.5F);
               dev.leg1tka.render.RenderPrimitives.b(matrixStack3, floatValue17, floatValue18, floatValue11, floatValue11, 2.0F, 1.0F, intValue23, intValue23, intValue23, intValue23);
               matrixStack3.push();
               matrixStack3.translate(floatValue17 + 1.0F, floatValue18 + 1.0F, 0.0F);
               drawContext2.drawItem(itemStack22, 0, 0);
               matrixStack3.pop();
               if (itemStack22.isDamageable()) {
                  int intValue24 = itemStack22.getMaxDamage();
                  int intValue25 = itemStack22.getDamage();
                  float floatValue26 = 1.0F - (float)intValue25 / (float)intValue24;
                  floatValue26 = MathHelper.clamp(floatValue26, 0.0F, 1.0F);
                  float floatValue27 = floatValue18 + floatValue11 + floatValue14;
                  dev.leg1tka.render.RenderPrimitives.b(matrixStack3, floatValue17, floatValue27, floatValue11, floatValue13, 1.0F, dev.leg1tka.api.utils.text.ColorUtils.a(40, 40, 40, 200));
                  int intValue29;
                  if (floatValue26 > 0.5F) {
                     intValue29 = dev.leg1tka.api.utils.text.ColorUtils.a(85, 255, 85, 255);
                  } else if (floatValue26 > 0.25F) {
                     intValue29 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 85, 255);
                  } else {
                     intValue29 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 85, 85, 255);
                  }

                  if (floatValue26 > 0.01F) {
                     dev.leg1tka.render.RenderPrimitives.b(matrixStack3, floatValue17, floatValue27, floatValue11 * floatValue26, floatValue13, 1.0F, intValue29);
                  }
               }

               floatValue17 += floatValue11 + floatValue12;
            }
         }

         float floatValue30 = (floatValue11 + floatValue12) * 5.0F - floatValue12;
         float floatValue31 = floatValue11 + floatValue14 + floatValue13;
         this.e.c(floatValue30);
         this.e.d(floatValue31);
         super.a(default1);
      }
   }
}
