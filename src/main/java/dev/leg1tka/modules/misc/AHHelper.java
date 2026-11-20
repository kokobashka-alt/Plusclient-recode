package dev.leg1tka.modules.misc;


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

import java.util.OptionalLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;

public class AHHelper extends Module {
   public static final AHHelper c = new AHHelper();
   private static final Pattern d = Pattern.compile(
      "(?:\\$|Цена|цена|Стоимость|стоимость|Купить|купить|Price|price:?)\\s*([\\d\\s,.]+)"
   );
   private final ColorSetting e = new ColorSetting("Цвет дешевого", dev.leg1tka.api.utils.text.ColorUtils.a(0, 255, 140, 235));

   public AHHelper() {
      super(
         "AHHelper",
         "Помощник в поиске дешевых предметов на аукционе",
         Category.MISC
      );
   }

   public void a(DrawContext drawContext1, HandledScreen<?> handledScreen2, int intValue3, int intValue4) {
      if (this.isEnabled() && minecraftClient.player != null && handledScreen2 != null && drawContext1 != null) {
         Slot slot5 = this.a(handledScreen2);
         if (slot5 != null) {
            int intValue6 = intValue3 + slot5.x;
            int intValue7 = intValue4 + slot5.y;
            int intValue8 = this.e.getColor();
            int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue8, 0.3F);
            int intValue10 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue8, 0.16F);
            drawContext1.fill(intValue6 - 2, intValue7 - 2, intValue6 + 18, intValue7 + 18, intValue10);
            drawContext1.fill(intValue6, intValue7, intValue6 + 16, intValue7 + 16, intValue9);
            drawContext1.fill(intValue6 - 2, intValue7 - 2, intValue6 + 18, intValue7 - 1, intValue8);
            drawContext1.fill(intValue6 - 2, intValue7 + 17, intValue6 + 18, intValue7 + 18, intValue8);
            drawContext1.fill(intValue6 - 2, intValue7 - 1, intValue6 - 1, intValue7 + 17, intValue8);
            drawContext1.fill(intValue6 + 17, intValue7 - 1, intValue6 + 18, intValue7 + 17, intValue8);
         }
      }
   }

   private Slot a(HandledScreen<?> handledScreen1) {
      if (handledScreen1.getScreenHandler() != null && handledScreen1.getScreenHandler().slots != null) {
         AHHelper.a value2 = null;

         for (Slot slot4 : handledScreen1.getScreenHandler().slots) {
            if (this.a(slot4)) {
               ItemStack itemStack5 = slot4.getStack();
               if (!itemStack5.isEmpty()) {
                  OptionalLong optionalLong6 = this.a(itemStack5);
                  if (!optionalLong6.isEmpty()) {
                     AHHelper.a value7 = new AHHelper.a(slot4, itemStack5, optionalLong6.getAsLong());
                     if (value7.c > 0L && (value2 == null || this.a(value7, value2))) {
                        value2 = value7;
                     }
                  }
               }
            }
         }

         return value2 == null ? null : value2.a;
      } else {
         return null;
      }
   }

   private boolean a(AHHelper.a value1, AHHelper.a value2) {
      int intValue3 = Double.compare(value1.a(), value2.a());
      if (intValue3 != 0) {
         return intValue3 < 0;
      } else {
         int intValue4 = Long.compare(value1.c, value2.c);
         return intValue4 != 0 ? intValue4 < 0 : value1.b.getCount() > value2.b.getCount();
      }
   }

   private boolean a(Slot slot1) {
      return slot1 != null && slot1.inventory != null && !(slot1.inventory instanceof PlayerInventory) && slot1.isEnabled() && slot1.id >= 0;
   }

   private OptionalLong a(ItemStack itemStack1) {
      LoreComponent loreComponent2 = (LoreComponent)itemStack1.get(DataComponentTypes.LORE);
      if (loreComponent2 == null) {
         return OptionalLong.empty();
      } else {
         long longValue3 = Long.MAX_VALUE;

         for (Text text6 : loreComponent2.lines()) {
            String string7 = text6.getString();
            if (string7 != null
               && (
                  string7.contains("$")
                     || string7.contains("Цена")
                     || string7.contains("цена")
                     || string7.contains("Стоимость")
                     || string7.contains("монет")
                     || string7.contains("Price")
                     || string7.contains("price")
               )) {
               Matcher matcher8 = d.matcher(string7);

               while (matcher8.find()) {
                  String string9 = matcher8.group(1).replaceAll("[^\\d]", "");
                  if (!string9.isEmpty()) {
                     try {
                        long longValue10 = Long.parseLong(string9);
                        if (longValue10 > 0L) {
                           longValue3 = Math.min(longValue3, longValue10);
                        }
                     } catch (Exception exception13) {
                     }
                  }
               }

               if (longValue3 == Long.MAX_VALUE) {
                  String string14 = string7.replaceAll("[^\\d]", "");
                  if (!string14.isEmpty()) {
                     try {
                        long longValue15 = Long.parseLong(string14);
                        if (longValue15 > 0L) {
                           longValue3 = Math.min(longValue3, longValue15);
                        }
                     } catch (Exception exception12) {
                     }
                  }
               }
            }
         }

         return longValue3 == Long.MAX_VALUE ? OptionalLong.empty() : OptionalLong.of(longValue3);
      }
   }

   static class a {
      final Slot a;
      final ItemStack b;
      final long c;

      a(Slot slot1, ItemStack itemStack2, long longValue3) {
         this.a = slot1;
         this.b = itemStack2;
         this.c = longValue3;
      }

      double a() {
         return (double)this.c / (double)Math.max(1, this.b.getCount());
      }
   }
}
