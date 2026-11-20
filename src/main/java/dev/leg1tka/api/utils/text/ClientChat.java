package dev.leg1tka.api.utils.text;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.awt.Color;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public final class ClientChat {
   public static void a(Object object0) {
      a("AspectDLC", object0);
   }

   public static void a(String string0, Object object1) {
      a(string0, (Text)Text.literal(String.valueOf(object1)).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(new Color(200, 200, 200).getRGB()))));
   }

   public static void a(String string0, Text text1) {
      MinecraftClient minecraftClient2 = MinecraftClient.getInstance();
      if (minecraftClient2.player == null) {
         System.out.println(string0 + " " + text1.getString());
      } else {
         MutableText mutableText3 = Text.literal("");

         for (int intValue4 = 0; intValue4 < string0.length(); intValue4++) {
            mutableText3.append(
               Text.literal(String.valueOf(string0.charAt(intValue4)))
                  .setStyle(Style.EMPTY.withBold(true).withColor(TextColor.fromRgb(dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.k(0), dev.leg1tka.api.utils.text.ColorUtils.k(90), (float)intValue4 / (float)string0.length()))))
            );
         }

         mutableText3.append(Text.literal(" -> ").setStyle(Style.EMPTY.withBold(false).withColor(TextColor.fromRgb(new Color(200, 200, 200).getRGB()))));
         mutableText3.append(Text.empty().setStyle(Style.EMPTY.withBold(false)).append(text1));
         minecraftClient2.player.sendMessage(mutableText3, false);
      }
   }

   @Generated
   private ClientChat() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
