package dev.leg1tka.modules.render;


import dev.leg1tka.modules.Module;
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
import java.util.ArrayList;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ContainerComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import org.lwjgl.opengl.GL11;

public class ShulkerPreview extends Module {
   public static ShulkerPreview c = new ShulkerPreview();
   private static float d;
   private static int e;
   private static int f;
   private static int g;
   private static int h;
   private static int i;
   private static int j;
   private static ShulkerPreview k;

   public ShulkerPreview() {
      super(
         "ShulkerPreview",
         "Показывает содержимое шалкера при наведении + CTRL",
         Category.RENDER
      );
      k = this;
   }

   public static ShulkerPreview p() {
      return k;
   }

   public void a(DrawContext drawContext1, Slot slot2, int intValue3, int intValue4) {
      if (this.a(slot2)) {
         ContainerComponent containerComponent5 = (ContainerComponent)slot2.getStack().get(DataComponentTypes.CONTAINER);
         this.a(drawContext1, slot2.getStack(), containerComponent5, (float)intValue3, (float)intValue4);
      }
   }

   public boolean a(Slot slot1) {
      if (!this.isEnabled() || minecraftClient == null || minecraftClient.player == null) {
         return false;
      } else if (!(minecraftClient.currentScreen instanceof HandledScreen)) {
         return false;
      } else if (!Screen.hasControlDown()) {
         return false;
      } else if (slot1 == null) {
         return false;
      } else {
         ItemStack itemStack2 = slot1.getStack();
         return !this.a(itemStack2) ? false : itemStack2.get(DataComponentTypes.CONTAINER) != null;
      }
   }

   private boolean a(ItemStack itemStack1) {
      return itemStack1 != null && !itemStack1.isEmpty()
         ? itemStack1.getItem() == Items.SHULKER_BOX
            || itemStack1.getItem() == Items.WHITE_SHULKER_BOX
            || itemStack1.getItem() == Items.ORANGE_SHULKER_BOX
            || itemStack1.getItem() == Items.MAGENTA_SHULKER_BOX
            || itemStack1.getItem() == Items.LIGHT_BLUE_SHULKER_BOX
            || itemStack1.getItem() == Items.YELLOW_SHULKER_BOX
            || itemStack1.getItem() == Items.LIME_SHULKER_BOX
            || itemStack1.getItem() == Items.PINK_SHULKER_BOX
            || itemStack1.getItem() == Items.GRAY_SHULKER_BOX
            || itemStack1.getItem() == Items.LIGHT_GRAY_SHULKER_BOX
            || itemStack1.getItem() == Items.CYAN_SHULKER_BOX
            || itemStack1.getItem() == Items.PURPLE_SHULKER_BOX
            || itemStack1.getItem() == Items.BLUE_SHULKER_BOX
            || itemStack1.getItem() == Items.BROWN_SHULKER_BOX
            || itemStack1.getItem() == Items.GREEN_SHULKER_BOX
            || itemStack1.getItem() == Items.RED_SHULKER_BOX
            || itemStack1.getItem() == Items.BLACK_SHULKER_BOX
         : false;
   }

   private int b(ItemStack itemStack1) {
      if (itemStack1.getItem() == Items.SHULKER_BOX) {
         return -6394435;
      } else if (itemStack1.getItem() == Items.WHITE_SHULKER_BOX) {
         return -1;
      } else if (itemStack1.getItem() == Items.ORANGE_SHULKER_BOX) {
         return -425955;
      } else if (itemStack1.getItem() == Items.MAGENTA_SHULKER_BOX) {
         return -3715395;
      } else if (itemStack1.getItem() == Items.LIGHT_BLUE_SHULKER_BOX) {
         return -12930086;
      } else if (itemStack1.getItem() == Items.YELLOW_SHULKER_BOX) {
         return -75715;
      } else if (itemStack1.getItem() == Items.LIME_SHULKER_BOX) {
         return -8337633;
      } else if (itemStack1.getItem() == Items.PINK_SHULKER_BOX) {
         return -816214;
      } else if (itemStack1.getItem() == Items.GRAY_SHULKER_BOX) {
         return -12103854;
      } else if (itemStack1.getItem() == Items.LIGHT_GRAY_SHULKER_BOX) {
         return -6447721;
      } else if (itemStack1.getItem() == Items.CYAN_SHULKER_BOX) {
         return -15295332;
      } else if (itemStack1.getItem() == Items.PURPLE_SHULKER_BOX) {
         return -7785800;
      } else if (itemStack1.getItem() == Items.BLUE_SHULKER_BOX) {
         return -12827478;
      } else if (itemStack1.getItem() == Items.BROWN_SHULKER_BOX) {
         return -8170446;
      } else if (itemStack1.getItem() == Items.GREEN_SHULKER_BOX) {
         return -10585066;
      } else if (itemStack1.getItem() == Items.RED_SHULKER_BOX) {
         return -5231066;
      } else {
         return itemStack1.getItem() == Items.BLACK_SHULKER_BOX ? -14869215 : -6394435;
      }
   }

   private void a(DrawContext drawContext1, ItemStack itemStack2, ContainerComponent containerComponent3, float floatValue4, float floatValue5) {
      MatrixStack matrixStack6 = drawContext1.getMatrices();
      int intValue7 = drawContext1.getScaledWindowWidth();
      int intValue8 = drawContext1.getScaledWindowHeight();
      float floatValue9 = 162.0F;
      float floatValue10 = 54.0F;
      float floatValue11 = floatValue9 + 14.0F;
      float floatValue12 = floatValue10 + 14.0F + 14.0F;
      float floatValue13 = floatValue4 + 12.0F;
      float floatValue14 = floatValue5 - 12.0F;
      if (floatValue13 + floatValue11 > (float)intValue7) {
         floatValue13 = floatValue4 - floatValue11 - 4.0F;
      }

      if (floatValue14 + floatValue12 > (float)intValue8) {
         floatValue14 = (float)intValue8 - floatValue12 - 4.0F;
      }

      if (floatValue14 < 4.0F) {
         floatValue14 = 4.0F;
      }

      if (floatValue13 < 4.0F) {
         floatValue13 = 4.0F;
      }

      int intValue15 = this.b(itemStack2);
      int intValue16 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue15, 0.85F);
      int intValue17 = this.a(intValue15, 0.6F);
      int intValue18 = this.b(intValue15, 1.3F);
      matrixStack6.push();
      GL11.glClear(256);
      RenderSystem.disableDepthTest();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      matrixStack6.translate(0.0F, 0.0F, 500.0F);
      dev.leg1tka.render.RenderPrimitives.e(matrixStack6, floatValue13 - 2.0F, floatValue14 - 2.0F, floatValue11 + 4.0F, floatValue12 + 4.0F, 7.0F, 8.0F, -1);
      drawContext1.fill((int)floatValue13, (int)floatValue14, (int)(floatValue13 + floatValue11), (int)(floatValue14 + floatValue12), intValue16);
      drawContext1.fill((int)floatValue13, (int)floatValue14, (int)(floatValue13 + floatValue11), (int)(floatValue14 + 2.0F), intValue18);
      drawContext1.fill((int)floatValue13, (int)(floatValue14 + floatValue12 - 2.0F), (int)(floatValue13 + floatValue11), (int)(floatValue14 + floatValue12), intValue17);
      drawContext1.fill((int)floatValue13, (int)floatValue14, (int)(floatValue13 + 2.0F), (int)(floatValue14 + floatValue12), intValue18);
      drawContext1.fill((int)(floatValue13 + floatValue11 - 2.0F), (int)floatValue14, (int)(floatValue13 + floatValue11), (int)(floatValue14 + floatValue12), intValue17);
      dev.leg1tka.render.font.MsdfTextRenderer value19 = dev.leg1tka.render.font.FontCache.a("sf_regular", 12);
      if (value19 != null) {
         String string20 = itemStack2.getName().getString();
         float floatValue21 = floatValue13 + 7.0F;
         float floatValue22 = floatValue14 + 7.0F - 1.0F;
         int intValue23 = this.b(intValue15) ? -1 : -15066598;
         value19.a(matrixStack6, string20, floatValue21, floatValue22, intValue23);
      }

      float floatValue30 = floatValue13 + 7.0F;
      float floatValue31 = floatValue14 + 7.0F + 14.0F - 2.0F;
      int intValue32 = this.a(intValue15, 0.5F);
      drawContext1.fill((int)(floatValue30 - 1.0F), (int)(floatValue31 - 1.0F), (int)(floatValue30 + floatValue9 + 1.0F), (int)(floatValue31 + floatValue10 + 1.0F), intValue32);
      ArrayList arrayList33 = new ArrayList();
      containerComponent3.stream().forEach(arrayList33::add);

      for (int intValue24 = 0; intValue24 < 27; intValue24++) {
         int intValue25 = intValue24 / 9;
         int intValue26 = intValue24 % 9;
         int intValue27 = (int)(floatValue30 + (float)(intValue26 * 18));
         int intValue28 = (int)(floatValue31 + (float)(intValue25 * 18));
         drawContext1.fill(intValue27, intValue28, intValue27 + 18 - 2, intValue28 + 18 - 2, -7631989);
         drawContext1.fill(intValue27, intValue28, intValue27 + 18 - 2, intValue28 + 1, -11184811);
         drawContext1.fill(intValue27, intValue28, intValue27 + 1, intValue28 + 18 - 2, -11184811);
         drawContext1.fill(intValue27, intValue28 + 18 - 3, intValue27 + 18 - 2, intValue28 + 18 - 2, -1);
         drawContext1.fill(intValue27 + 18 - 3, intValue28, intValue27 + 18 - 2, intValue28 + 18 - 2, -1);
         if (intValue24 < arrayList33.size()) {
            ItemStack itemStack29 = (ItemStack)arrayList33.get(intValue24);
            if (!itemStack29.isEmpty()) {
               drawContext1.drawItem(itemStack29, intValue27, intValue28);
               drawContext1.drawStackOverlay(minecraftClient.textRenderer, itemStack29, intValue27, intValue28);
            }
         }
      }

      RenderSystem.enableDepthTest();
      RenderSystem.disableBlend();
      matrixStack6.pop();
   }

   private int a(int intValue1, float floatValue2) {
      int intValue3 = intValue1 >> 24 & 0xFF;
      int intValue4 = (int)((float)(intValue1 >> 16 & 0xFF) * floatValue2);
      int intValue5 = (int)((float)(intValue1 >> 8 & 0xFF) * floatValue2);
      int intValue6 = (int)((float)(intValue1 & 0xFF) * floatValue2);
      return intValue3 << 24 | Math.min(255, intValue4) << 16 | Math.min(255, intValue5) << 8 | Math.min(255, intValue6);
   }

   private int b(int intValue1, float floatValue2) {
      int intValue3 = intValue1 >> 24 & 0xFF;
      int intValue4 = (int)Math.min(255.0F, (float)(intValue1 >> 16 & 0xFF) * floatValue2);
      int intValue5 = (int)Math.min(255.0F, (float)(intValue1 >> 8 & 0xFF) * floatValue2);
      int intValue6 = (int)Math.min(255.0F, (float)(intValue1 & 0xFF) * floatValue2);
      return intValue3 << 24 | intValue4 << 16 | intValue5 << 8 | intValue6;
   }

   private boolean b(int intValue1) {
      int intValue2 = intValue1 >> 16 & 0xFF;
      int intValue3 = intValue1 >> 8 & 0xFF;
      int intValue4 = intValue1 & 0xFF;
      double doubleValue5 = (0.299 * (double)intValue2 + 0.587 * (double)intValue3 + 0.114 * (double)intValue4) / 255.0;
      return doubleValue5 < 0.5;
   }
}
