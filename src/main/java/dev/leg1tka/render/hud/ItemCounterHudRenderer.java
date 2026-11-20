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

import com.mojang.blaze3d.systems.RenderSystem;
import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.implement.EventRender;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ItemCounterHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   private static float f;
   private static float g;
   private static float h;
   private static float i;
   private static float j;
   private static float k;

   public ItemCounterHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
   }

   @Override
   public void a(EventRender.Default default1) {
      if (minecraftClient != null && minecraftClient.player != null) {
         DrawContext drawContext2 = default1.getContext();
         MatrixStack matrixStack3 = drawContext2.getMatrices();
         float floatValue4 = this.e.a();
         float floatValue5 = this.e.b();
         int intValue6 = this.a(Items.FIREWORK_ROCKET.getDefaultStack().getItem());
         int intValue7 = this.a(Items.TOTEM_OF_UNDYING.getDefaultStack().getItem());
         String string8 = "x" + intValue6;
         String string9 = "x" + intValue7;
         dev.leg1tka.render.font.MsdfTextRenderer value10 = dev.leg1tka.render.font.FontCache.a("nunito", 14);
         float floatValue11 = 10.0F;
         if (value10 != null) {
            float floatValue12 = value10.b(string8);
            float floatValue13 = value10.b(string9);
            floatValue11 = Math.max(floatValue12, floatValue13) + 10.0F;
         }

         if (floatValue11 < 28.0F) {
            floatValue11 = 28.0F;
         }

         float floatValue20 = 22.0F + floatValue11;
         float floatValue21 = 18.0F;
         int intValue14;
         if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null && !AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow")) {
            intValue14 = AspectDLC.INSTANCE.themeStorage.b().a().c[0];
         } else {
            intValue14 = dev.leg1tka.api.utils.text.ColorUtils.a();
         }

         dev.leg1tka.render.RenderExecutionMode value15 = this.b();
         this.a(drawContext2, matrixStack3, floatValue4, floatValue5, floatValue20, floatValue21, floatValue11, intValue14, value15, Items.FIREWORK_ROCKET.getDefaultStack(), string8, value10);
         float floatValue17 = floatValue5 + floatValue21 + 2.0F;
         this.a(drawContext2, matrixStack3, floatValue4, floatValue17, floatValue20, floatValue21, floatValue11, intValue14, value15, Items.TOTEM_OF_UNDYING.getDefaultStack(), string9, value10);
         float floatValue19 = floatValue21 * 2.0F + 2.0F;
         this.e.c(floatValue20);
         this.e.d(floatValue19);
         super.a(default1);
      }
   }

   private void a(
      DrawContext drawContext1,
      MatrixStack matrixStack2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      int intValue8,
      dev.leg1tka.render.RenderExecutionMode value9,
      ItemStack itemStack10,
      String string11,
      dev.leg1tka.render.font.MsdfTextRenderer value12
   ) {
      this.a(matrixStack2, floatValue3, floatValue4, 20.0F, floatValue6, intValue8);
      float floatValue13 = 16.0F;
      float floatValue14 = (20.0F - floatValue13) * 0.5F;
      float floatValue15 = (floatValue6 - floatValue13) * 0.5F;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      matrixStack2.push();
      matrixStack2.translate(floatValue3 + floatValue14, floatValue4 + floatValue15, 0.0F);
      drawContext1.drawItem(itemStack10, 0, 0);
      matrixStack2.pop();
      RenderSystem.enableDepthTest();
      RenderSystem.depthMask(true);
      float floatValue16 = floatValue3 + 20.0F + 2.0F;
      this.a(matrixStack2, floatValue16, floatValue4, floatValue7, floatValue6, intValue8);
      if (value12 != null) {
         float floatValue17 = value12.b(string11);
         float floatValue18 = value12.a();
         float floatValue19 = floatValue16 + (floatValue7 - floatValue17) * 0.5F;
         float floatValue20 = floatValue4 + (floatValue6 - floatValue18) * 1.5F;
         value12.d(matrixStack2, string11, floatValue19, floatValue20, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 255));
      }
   }

   private int a(Item item1) {
      if (minecraftClient.player == null) {
         return 0;
      } else {
         int intValue2 = 0;

         for (int intValue3 = 0; intValue3 < minecraftClient.player.getInventory().size(); intValue3++) {
            ItemStack itemStack4 = minecraftClient.player.getInventory().getStack(intValue3);
            if (!itemStack4.isEmpty() && itemStack4.getItem() == item1) {
               intValue2 += itemStack4.getCount();
            }
         }

         return intValue2;
      }
   }
}
