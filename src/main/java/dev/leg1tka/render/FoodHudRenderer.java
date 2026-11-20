package dev.leg1tka.render;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public final class FoodHudRenderer {
   private static final Identifier a = Identifier.ofVanilla("hud/food_full");
   private static final Identifier b = Identifier.ofVanilla("hud/food_half");
   private static final Identifier c = Identifier.ofVanilla("hud/food_full_hunger");
   private static final Identifier d = Identifier.ofVanilla("hud/food_half_hunger");
   private static int e;

   private FoodHudRenderer() {
   }

   public static void a(DrawContext drawContext0, PlayerEntity playerEntity1, int intValue2, int intValue3) {
      HungerManager hungerManager4 = playerEntity1.getHungerManager();
      int intValue5 = hungerManager4.getFoodLevel();
      float floatValue6 = hungerManager4.getSaturationLevel();
      FoodComponent foodComponent7 = (FoodComponent)playerEntity1.getMainHandStack().get(DataComponentTypes.FOOD);
      if (foodComponent7 == null) {
         foodComponent7 = (FoodComponent)playerEntity1.getOffHandStack().get(DataComponentTypes.FOOD);
      }

      int intValue8 = intValue5;
      float floatValue9 = floatValue6;
      if (foodComponent7 != null && (intValue5 < 20 || foodComponent7.canAlwaysEat())) {
         intValue8 = Math.min(20, intValue5 + foodComponent7.nutrition());
         floatValue9 = MathHelper.clamp(floatValue6 + foodComponent7.saturation(), 0.0F, (float)intValue8);
      }

      float floatValue10 = (float)(Util.getMeasuringTimeMs() % 1200L) / 1200.0F;
      float floatValue11 = 0.25F + 0.5F * (1.0F - Math.abs(floatValue10 * 2.0F - 1.0F));
      int intValue12 = Math.max(1, (int)(floatValue11 * 255.0F));
      boolean flag13 = playerEntity1.hasStatusEffect(StatusEffects.HUNGER);
      boolean flag14 = playerEntity1.getAir() < playerEntity1.getMaxAir() || playerEntity1.isSubmergedIn(FluidTags.WATER);
      int intValue15 = intValue2 - (flag14 ? 20 : 10);

      for (int intValue16 = 0; intValue16 < 10; intValue16++) {
         int intValue17 = intValue3 - intValue16 * 8 - 9;
         if (intValue8 > intValue5) {
            int intValue18 = MathHelper.clamp(intValue5 - intValue16 * 2, 0, 2);
            int intValue19 = MathHelper.clamp(intValue8 - intValue16 * 2, 0, 2);
            if (intValue19 > intValue18) {
               Identifier identifier20 = intValue19 == 2 ? (flag13 ? c : a) : (flag13 ? d : b);
               drawContext0.drawGuiTexture(RenderLayer::getGuiTextured, identifier20, intValue17, intValue2, 9, 9, intValue12 << 24 | 16777215);
            }
         }

         int intValue21 = a(floatValue6 - (float)(intValue16 * 2));
         int intValue22 = a(floatValue9 - (float)(intValue16 * 2));
         if (intValue21 > 0) {
            drawContext0.drawGuiTexture(RenderLayer::getGuiTextured, intValue21 == 2 ? a : b, intValue17, intValue15, 9, 9, -10929);
         }

         if (intValue22 > intValue21) {
            drawContext0.drawGuiTexture(RenderLayer::getGuiTextured, intValue22 == 2 ? a : b, intValue17, intValue15, 9, 9, intValue12 << 24 | 16766287);
         }
      }
   }

   private static int a(float floatValue0) {
      if (floatValue0 >= 1.5F) {
         return 2;
      } else {
         return floatValue0 >= 0.5F ? 1 : 0;
      }
   }
}
