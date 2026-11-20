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

import java.util.regex.Pattern;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.text.PlainTextContent.Literal;
import net.minecraft.util.Formatting;

public class StyledTextReplacer {
   public StyledTextReplacer() {
   }

   public static Text a(Text text0, String string1, String string2) {
      if (text0 != null && string1 != null && string2 != null) {
         MutableText mutableText3 = Text.empty().setStyle(text0.getStyle());
         a(mutableText3, text0, string1, string2);
         return mutableText3;
      } else {
         return text0;
      }
   }

   private static void a(MutableText mutableText0, Text text1, String string2, String string3) {
      TextContent textContent4 = text1.getContent();
      Style style5 = text1.getStyle();
      if (textContent4 instanceof Literal literal6) {
         Pattern pattern7 = Pattern.compile(Pattern.quote(string2), 2);
         String string8 = pattern7.matcher(literal6.string()).replaceAll(string3);
         mutableText0.append(Text.literal(string8).setStyle(style5));
      }

      for (Text text10 : text1.getSiblings()) {
         a(mutableText0, text10, string2, string3);
      }
   }

   public static String a(String string0) {
      return string0.replaceAll("ꔗ", Formatting.BLUE + "MODER")
         .replaceAll("ꔥ", Formatting.BLUE + "ST.MODER")
         .replaceAll("ꔡ", Formatting.LIGHT_PURPLE + "MODER+")
         .replaceAll("ꔀ", Formatting.GRAY + "PLAYER")
         .replaceAll("ꔉ", Formatting.YELLOW + "HELPER")
         .replaceAll("◆", "@")
         .replaceAll("┃", "|")
         .replaceAll("ꕆ", Formatting.YELLOW + "PEGAS")
         .replaceAll("ꔸ", Formatting.YELLOW + "GOD")
         .replaceAll("ꔳ", Formatting.AQUA + "Ml.admin")
         .replaceAll("ꔅ", Formatting.RED + "Y" + Formatting.WHITE + "T")
         .replaceAll("ꔂ", Formatting.BLUE + "D.MODER")
         .replaceAll("ꕠ", Formatting.YELLOW + "D.HELPER")
         .replaceAll("ꕄ", Formatting.RED + "VAMPIRE")
         .replaceAll("ꔖ", Formatting.AQUA + "OVERLORD")
         .replaceAll("ꕈ", Formatting.GREEN + "COBRA")
         .replaceAll("ꔨ", Formatting.LIGHT_PURPLE + "DRAGON")
         .replaceAll("ꔤ", Formatting.RED + "IMPERATOR")
         .replaceAll("ꔠ", Formatting.GOLD + "MAGISTER")
         .replaceAll("ꔄ", Formatting.BLUE + "HERO")
         .replaceAll("ꔒ", Formatting.GREEN + "AVENGER")
         .replaceAll("ꕒ", Formatting.WHITE + "RABBIT")
         .replaceAll("ꔈ", Formatting.YELLOW + "TITAN")
         .replaceAll("ꕀ", Formatting.DARK_GREEN + "HYDRA")
         .replaceAll("ꔶ", Formatting.GOLD + "TIGER")
         .replaceAll("ꔲ", Formatting.DARK_PURPLE + "BULL")
         .replaceAll("ꕖ", Formatting.BLACK + "BUNNY")
         .replaceAll("ꕗꕘ", Formatting.YELLOW + "SPONSOR")
         .replaceAll("\ud83d\udd25", "@")
         .replaceAll("ᴀ", "A")
         .replaceAll("ʙ", "B")
         .replaceAll("ᴄ", "C")
         .replaceAll("ᴅ", "D")
         .replaceAll("ᴇ", "E")
         .replaceAll("ғ", "F")
         .replaceAll("ɢ", "G")
         .replaceAll("ʜ", "H")
         .replaceAll("ɪ", "I")
         .replaceAll("ᴊ", "J")
         .replaceAll("ᴋ", "K")
         .replaceAll("ʟ", "L")
         .replaceAll("ᴍ", "M")
         .replaceAll("ɴ", "N")
         .replaceAll("ꜱ", "S")
         .replaceAll("s", "S")
         .replaceAll("ᴏ", "O")
         .replaceAll("ᴘ", "P")
         .replaceAll("ǫ", "Q")
         .replaceAll("ʀ", "R")
         .replaceAll("ᴛ", "T")
         .replaceAll("ᴜ", "U")
         .replaceAll("ᴠ", "V")
         .replaceAll("ᴡ", "W")
         .replaceAll("ꜰ", "F")
         .replaceAll("x", "X")
         .replaceAll("ʏ", "Y")
         .replaceAll("ᴢ", "Z");
   }

   public static Text a(Text text0) {
      if (text0.getString().contains("ꔗ")) {
         text0 = a(text0, "ꔗ", Formatting.BLUE + "MODER");
      }

      if (text0.getString().contains("ꔥ")) {
         text0 = a(text0, "ꔥ", Formatting.BLUE + "ST.MODER");
      }

      if (text0.getString().contains("ꔡ")) {
         text0 = a(text0, "ꔡ", Formatting.LIGHT_PURPLE + "MODER+");
      }

      if (text0.getString().contains("ꔀ")) {
         text0 = a(text0, "ꔀ", Formatting.GRAY + "PLAYER");
      }

      if (text0.getString().contains("ꔉ")) {
         text0 = a(text0, "ꔉ", Formatting.YELLOW + "HELPER");
      }

      if (text0.getString().contains("◆")) {
         text0 = a(text0, "◆", "@");
      }

      if (text0.getString().contains("┃")) {
         text0 = a(text0, "┃", "|");
      }

      if (text0.getString().contains("ꔳ")) {
         text0 = a(text0, "ꔳ", Formatting.AQUA + "Ml.admin");
      }

      if (text0.getString().contains("ꔅ")) {
         text0 = a(text0, "ꔅ", Formatting.RED + "Y" + Formatting.WHITE + "T");
      }

      if (text0.getString().contains("ꔂ")) {
         text0 = a(text0, "ꔂ", Formatting.BLUE + "D.MODER");
      }

      if (text0.getString().contains("ꕠ")) {
         text0 = a(text0, "ꕠ", Formatting.YELLOW + "D.HELPER");
      }

      if (text0.getString().contains("ꕄ")) {
         text0 = a(text0, "ꕄ", Formatting.RED + "DRACULA");
      }

      if (text0.getString().contains("ꔖ")) {
         text0 = a(text0, "ꔖ", Formatting.AQUA + "OVERLORD");
      }

      if (text0.getString().contains("ꕈ")) {
         text0 = a(text0, "ꕈ", Formatting.GREEN + "COBRA");
      }

      if (text0.getString().contains("ꔨ")) {
         text0 = a(text0, "ꔨ", Formatting.LIGHT_PURPLE + "DRAGON");
      }

      if (text0.getString().contains("ꔤ")) {
         text0 = a(text0, "ꔤ", Formatting.RED + "IMPERATOR");
      }

      if (text0.getString().contains("ꔠ")) {
         text0 = a(text0, "ꔠ", Formatting.GOLD + "MAGISTER");
      }

      if (text0.getString().contains("ꔄ")) {
         text0 = a(text0, "ꔄ", Formatting.BLUE + "HERO");
      }

      if (text0.getString().contains("ꔒ")) {
         text0 = a(text0, "ꔒ", Formatting.GREEN + "AVENGER");
      }

      if (text0.getString().contains("ꕒ")) {
         text0 = a(text0, "ꕒ", Formatting.WHITE + "RABBIT");
      }

      if (text0.getString().contains("ꔈ")) {
         text0 = a(text0, "ꔈ", Formatting.YELLOW + "TITAN");
      }

      if (text0.getString().contains("ꕀ")) {
         text0 = a(text0, "ꕀ", Formatting.DARK_GREEN + "HYDRA");
      }

      if (text0.getString().contains("ꔶ")) {
         text0 = a(text0, "ꔶ", Formatting.GOLD + "TIGER");
      }

      if (text0.getString().contains("ꔲ")) {
         text0 = a(text0, "ꔲ", Formatting.DARK_PURPLE + "BULL");
      }

      if (text0.getString().contains("ꕖ")) {
         text0 = a(text0, "ꕖ", Formatting.BLACK + "BUNNY");
      }

      if (text0.getString().contains("ꕗꕘ")) {
         text0 = a(text0, "ꕗꕘ", Formatting.YELLOW + "SPONSOR");
      }

      if (text0.getString().contains("\ud83d\udd25")) {
         text0 = a(text0, "\ud83d\udd25", "@");
      }

      if (text0.getString().contains("ᴀ")) {
         text0 = a(text0, "ᴀ", "A");
      }

      if (text0.getString().contains("ʙ")) {
         text0 = a(text0, "ʙ", "B");
      }

      if (text0.getString().contains("ᴄ")) {
         text0 = a(text0, "ᴄ", "C");
      }

      if (text0.getString().contains("ᴅ")) {
         text0 = a(text0, "ᴅ", "D");
      }

      if (text0.getString().contains("ᴇ")) {
         text0 = a(text0, "ᴇ", "E");
      }

      if (text0.getString().contains("ғ")) {
         text0 = a(text0, "ғ", "F");
      }

      if (text0.getString().contains("ɢ")) {
         text0 = a(text0, "ɢ", "G");
      }

      if (text0.getString().contains("ʜ")) {
         text0 = a(text0, "ʜ", "H");
      }

      if (text0.getString().contains("ɪ")) {
         text0 = a(text0, "ɪ", "I");
      }

      if (text0.getString().contains("ᴊ")) {
         text0 = a(text0, "ᴊ", "J");
      }

      if (text0.getString().contains("ᴋ")) {
         text0 = a(text0, "ᴋ", "K");
      }

      if (text0.getString().contains("ʟ")) {
         text0 = a(text0, "ʟ", "L");
      }

      if (text0.getString().contains("ᴍ")) {
         text0 = a(text0, "ᴍ", "M");
      }

      if (text0.getString().contains("ɴ")) {
         text0 = a(text0, "ɴ", "N");
      }

      if (text0.getString().contains("ꜱ")) {
         text0 = a(text0, "ꜱ", "S");
      }

      if (text0.getString().contains("s")) {
         text0 = a(text0, "s", "S");
      }

      if (text0.getString().contains("ᴏ")) {
         text0 = a(text0, "ᴏ", "O");
      }

      if (text0.getString().contains("ᴘ")) {
         text0 = a(text0, "ᴘ", "P");
      }

      if (text0.getString().contains("ǫ")) {
         text0 = a(text0, "ǫ", "Q");
      }

      if (text0.getString().contains("ʀ")) {
         text0 = a(text0, "ʀ", "R");
      }

      if (text0.getString().contains("ᴛ")) {
         text0 = a(text0, "ᴛ", "T");
      }

      if (text0.getString().contains("ᴜ")) {
         text0 = a(text0, "ᴜ", "U");
      }

      if (text0.getString().contains("ᴠ")) {
         text0 = a(text0, "ᴠ", "V");
      }

      if (text0.getString().contains("ᴡ")) {
         text0 = a(text0, "ᴡ", "W");
      }

      if (text0.getString().contains("ꜰ")) {
         text0 = a(text0, "ꜰ", "F");
      }

      if (text0.getString().contains("x")) {
         text0 = a(text0, "x", "X");
      }

      if (text0.getString().contains("ʏ")) {
         text0 = a(text0, "ʏ", "Y");
      }

      if (text0.getString().contains("ᴢ")) {
         text0 = a(text0, "ᴢ", "Z");
      }

      return text0;
   }
}
