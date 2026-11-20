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

import dev.leg1tka.AspectDLC;
import dev.leg1tka.mixin.ChatScreenAccessor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public class NameProtect extends Module {
   public static final NameProtect c = new NameProtect();
   private final BooleanSetting d = new BooleanSetting("Скрывать друзей", true);
   private final BooleanSetting e = new BooleanSetting("Скрывать гриф", false);
   private final dev.leg1tka.settings.StringSetting f = new dev.leg1tka.settings.StringSetting("Никнейм", "AspectDLC", 32);
   private final ModeSetting g = new ModeSetting("Фейк донат", "Нет", "Нет", "SpaceTime", "SpookyTime");
   private final ModeSetting h = new ModeSetting(
         "Ранг SpaceTime", "jupiter", "jupiter", "sun", "orbit", "neptune", "heaven", "mars", "venus", "pizdo", "space"
      )
      .a(() -> this.g.b("SpaceTime"));
   private final ModeSetting i = new ModeSetting(
         "Ранг SpookyTime",
         "Князь",
         "Князь",
         "Герцог",
         "Сквид",
         "Титан",
         "Элита",
         "Глава",
         "Аспид",
         "Герой"
      )
      .a(() -> this.g.b("SpookyTime"));
   private static String j;
   private static final Map k = new LinkedHashMap();
   private static final Map l = new LinkedHashMap();
   private static int m;
   private final Map n = new LinkedHashMap<String, String>(512, 0.75F, true) {
      @Override
      protected boolean removeEldestEntry(Entry<String, String> entry1) {
         return this.size() > 512;
      }
   };

   private static Text a(String string0, int intValue1) {
      return Text.literal(string0).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(intValue1)));
   }

   private static Text a(String[] stringArray0, int... intValueArray1) {
      MutableText mutableText2 = Text.empty();

      for (int intValue3 = 0; intValue3 < stringArray0.length; intValue3++) {
         mutableText2.append(Text.literal(stringArray0[intValue3]).setStyle(Style.EMPTY.withBold(true).withColor(TextColor.fromRgb(intValueArray1[intValue3]))));
      }

      return mutableText2;
   }

   private NameProtect() {
      super("NameProtect", "Скрывает никнеймы", Category.MISC);
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h, this.i});
   }

   public Text a(Text text1) {
      if (text1 != null && !this.g.b("Нет")) {
         if (!text1.getString().contains("Ранг:")) {
            return text1;
         } else {
            Text text2 = this.g.b("SpaceTime") ? (Text)k.get(this.h.g()) : (Text)l.get(this.i.g());
            if (text2 == null) {
               return text1;
            } else {
               MutableText mutableText3 = Text.empty();
               int[] intValueArray4 = new int[]{0};
               text1.visit((style3x, string4x) -> {
                  if (intValueArray4[0] == 0) {
                     int intValue5 = string4x.indexOf("Ранг:");
                     if (intValue5 < 0) {
                        mutableText3.append(Text.literal(string4x).setStyle(style3x));
                        return Optional.empty();
                     }

                     int intValue6 = intValue5 + "Ранг:".length();

                     while (intValue6 < string4x.length() && string4x.charAt(intValue6) == ' ') {
                        intValue6++;
                     }

                     mutableText3.append(Text.literal(string4x.substring(0, intValue6)).setStyle(style3x));
                     mutableText3.append(text2.copy());
                     intValueArray4[0] = 1;
                     string4x = string4x.substring(intValue6);
                  }

                  if (intValueArray4[0] == 1) {
                     int intValue7 = string4x.indexOf(167);
                     if (intValue7 < 0) {
                        return Optional.empty();
                     }

                     string4x = string4x.substring(intValue7);
                     intValueArray4[0] = 2;
                  }

                  if (!string4x.isEmpty()) {
                     mutableText3.append(Text.literal(string4x).setStyle(style3x));
                  }

                  return Optional.empty();
               }, Style.EMPTY);
               return (Text)(intValueArray4[0] == 0 ? text1 : mutableText3);
            }
         }
      } else {
         return text1;
      }
   }

   public String c(String string1) {
      if (string1 == null) {
         return null;
      } else if (!this.r()) {
         return string1;
      } else {
         String string2 = this.f(string1);
         String string3 = (String)this.n.get(string2);
         if (string3 != null) {
            return string3;
         } else {
            String string5 = this.p();
            String string4 = this.a(string1, minecraftClient.getSession().getUsername(), string5);
            if (this.d.getValue() && AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.friendStorage != null) {
               for (String string7 : AspectDLC.INSTANCE.friendStorage.c()) {
                  string4 = this.a(string4, string7, string5);
               }
            }

            string4 = this.e(string4);
            this.n.put(string2, string4);
            return string4;
         }
      }
   }

   public String d(String string1) {
      return this.c(string1);
   }

   public Text b(Text text1) {
      if (text1 == null) {
         return null;
      } else if (!this.r()) {
         return text1;
      } else {
         String string3 = this.p();
         Text text2 = dev.leg1tka.api.utils.text.StyledTextReplacer.a(text1, minecraftClient.getSession().getUsername(), string3);
         if (this.d.getValue() && AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.friendStorage != null) {
            for (String string5 : AspectDLC.INSTANCE.friendStorage.c()) {
               text2 = dev.leg1tka.api.utils.text.StyledTextReplacer.a(text2, string5, string3);
            }
         }

         return text2;
      }
   }

   public String p() {
      String string1 = this.f.e();
      return string1 != null && !string1.isBlank() ? string1 : "AspectDLC";
   }

   public boolean q() {
      return this.e.getValue();
   }

   private String a(String string1, String string2, String string3) {
      if (string1 != null && string2 != null && !string2.isEmpty()) {
         int intValue4 = this.a(string1, string2, 0);
         if (intValue4 < 0) {
            return string1;
         } else {
            StringBuilder stringBuilder5 = new StringBuilder(string1.length() + string3.length());
            int intValue6 = 0;

            for (int intValue7 = intValue4; intValue7 >= 0; intValue7 = this.a(string1, string2, intValue6)) {
               stringBuilder5.append(string1, intValue6, intValue7).append(string3);
               intValue6 = intValue7 + string2.length();
            }

            stringBuilder5.append(string1, intValue6, string1.length());
            return stringBuilder5.toString();
         }
      } else {
         return string1;
      }
   }

   private int a(String string1, String string2, int intValue3) {
      int intValue4 = string1.length() - string2.length();

      for (int intValue5 = Math.max(0, intValue3); intValue5 <= intValue4; intValue5++) {
         if (string1.regionMatches(true, intValue5, string2, 0, string2.length())) {
            return intValue5;
         }
      }

      return -1;
   }

   private String e(String string1) {
      if (string1 != null && this.e.getValue()) {
         String string2 = string1.replaceAll("Анархия-\\d+", "AspectDLC.fun");
         return string2.replaceAll("ГРИФ #\\d+", "AspectDLC.fun");
      } else {
         return string1;
      }
   }

   private String f(String string1) {
      String string2 = minecraftClient != null && minecraftClient.getSession() != null ? minecraftClient.getSession().getUsername() : "";
      int intValue3 = 0;
      if (this.d.getValue() && AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.friendStorage != null) {
         List list4 = AspectDLC.INSTANCE.friendStorage.c();
         intValue3 = list4.hashCode();
      }

      return string2 + this.p() + this.d.getValue() + this.e.getValue() + intValue3 + string1;
   }

   private boolean r() {
      return this.isEnabled() && minecraftClient != null && minecraftClient.player != null && minecraftClient.world != null && !this.s();
   }

   private boolean s() {
      if (!(minecraftClient.currentScreen instanceof ChatScreen chatScreen1)) {
         return false;
      } else {
         TextFieldWidget textFieldWidget6 = ((ChatScreenAccessor)chatScreen1).AspectDLC$getChatField();
         if (textFieldWidget6 == null) {
            return false;
         } else {
            String string3 = textFieldWidget6.getText();
            if (string3 == null) {
               return false;
            } else {
               String string4 = string3.trim().toLowerCase();
               String string5 = AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.commandStorage != null
                  ? AspectDLC.INSTANCE.commandStorage.d().toLowerCase()
                  : ".";
               return string4.startsWith(string5 + "friend remove");
            }
         }
      }
   }

   static {
      k.put(
         "jupiter",
         a(
            new String[]{"ᴊ", "ᴜ", "ᴘ", "ɪ", "ᴛ", "ᴇ", "ʀ"},
            16480256,
            15887880,
            15295248,
            14768408,
            14175776,
            13583400,
            12990768
         )
      );
      k.put("sun", a(new String[]{"s", "ᴜ", "ɴ"}, 16495872, 15915264, 15334656));
      k.put("orbit", a(new String[]{"ᴏ", "ʀ", "ʙ", "ɪ", "ᴛ"}, 12253952, 13366528, 14479360, 15526400, 16638976));
      k.put("neptune", a(new String[]{"ɴ", "ᴇ", "ᴘ", "ᴛ", "ᴜ", "ɴ", "ᴇ"}, 33787, 38905, 44279, 49397, 54514, 59888, 65006));
      k.put("heaven", a(new String[]{"ʜ", "ᴇ", "ᴀ", "ᴠ", "ᴇ", "ɴ"}, 10353648, 10215411, 10077173, 9938680, 9800442, 9662205));
      k.put("mars", a(new String[]{"ᴍ", "ᴀ", "ʀ", "s"}, 16449536, 16525568, 16666880, 16742912));
      k.put("venus", a(new String[]{"ᴠ", "ᴇ", "ɴ", "ᴜ", "s"}, 9848827, 11564540, 13345789, 15061502, 16777215));
      k.put("pizdo", a(new String[]{"ᴘ", "ɪ", "ᴢ", "ᴅ", "ᴏ"}, 12031811, 10585402, 9204530, 7757865, 6311456));
      k.put("space", a(new String[]{"s", "ᴘ", "ᴀ", "ᴄ", "ᴇ"}, 6357247, 8651007, 10879231, 13172991, 15401215));
      l.put("Князь", a("Князь", 16711680));
      l.put("Герцог", a("Герцог", 16711680));
      l.put("Сквид", a("Сквид", 65535));
      l.put("Титан", a("Титан", 16777215));
      l.put("Элита", a("Элита", 11141375));
      l.put("Глава", a("Глава", 16755200));
      l.put("Аспид", a("Аспид", 65535));
      l.put("Герой", a("Герой", 5635925));
   }
}
