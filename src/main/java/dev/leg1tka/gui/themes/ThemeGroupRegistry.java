package dev.leg1tka.gui.themes;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class ThemeGroupRegistry {
   private static final Map<String, String> a = new LinkedHashMap<>();
   private static final Map<String, Integer> b = new LinkedHashMap<>();
   private static final Set<String> c = Collections.unmodifiableSet(a.keySet());
   private static final Set<String> d = new HashSet<>();

   public ThemeGroupRegistry() {
   }

   public static dev.leg1tka.gui.themes.ThemeGroupRegistry.a a(PlayerEntity playerEntity0) {
      if (playerEntity0 == null) {
         return null;
      } else {
         MinecraftClient minecraftClient1 = MinecraftClient.getInstance();
         if (minecraftClient1.getNetworkHandler() != null) {
            PlayerListEntry playerListEntry2 = minecraftClient1.getNetworkHandler().getPlayerListEntry(playerEntity0.getUuid());
            if (playerListEntry2 != null && playerListEntry2.getDisplayName() != null) {
               String string3 = a(playerListEntry2.getDisplayName(), b(playerEntity0));
               if (string3 != null) {
                  return c(string3);
               }
            }
         }

         Team team4 = playerEntity0.getScoreboardTeam();
         if (team4 != null) {
            String string5 = b(team4);
            if (string5 != null) {
               return c(string5);
            }
         }

         return null;
      }
   }

   public static dev.leg1tka.gui.themes.ThemeGroupRegistry.a a(PlayerListEntry playerListEntry0) {
      if (playerListEntry0 == null) {
         return null;
      } else {
         if (playerListEntry0.getDisplayName() != null) {
            String string1 = playerListEntry0.getProfile() != null ? playerListEntry0.getProfile().getName() : null;
            String string2 = a(playerListEntry0.getDisplayName(), string1);
            if (string2 != null) {
               return c(string2);
            }
         }

         if (playerListEntry0.getScoreboardTeam() != null) {
            String string3 = b(playerListEntry0.getScoreboardTeam());
            if (string3 != null) {
               return c(string3);
            }
         }

         return null;
      }
   }

   public static dev.leg1tka.gui.themes.ThemeGroupRegistry.a a(Team team0) {
      if (team0 == null) {
         return null;
      } else {
         String string1 = b(team0);
         return string1 != null ? c(string1) : null;
      }
   }

   public static String a(Text text0) {
      return a(text0, null);
   }

   public static String a(Text text0, String string1) {
      if (text0 == null) {
         return null;
      } else {
         String string2 = string1 != null && !string1.isEmpty() ? string1.toLowerCase(Locale.ROOT) : null;
         String[] stringArray3 = new String[]{null};
         StringBuilder stringBuilder4 = new StringBuilder();
         text0.visit((style3x, string4x) -> {
            if (style3x != null && style3x.getFont() != null) {
               String string5 = a(style3x.getFont().toString());
               if (string5 != null) {
                  stringArray3[0] = string5;
                  return Optional.of(Boolean.TRUE);
               }
            }

            if (string4x != null && !string4x.isEmpty()) {
               if (string2 != null) {
                  int intValue6 = string4x.toLowerCase(Locale.ROOT).indexOf(string2);
                  if (intValue6 >= 0) {
                     stringBuilder4.append(string4x, 0, intValue6);
                     return Optional.of(Boolean.TRUE);
                  }
               }

               stringBuilder4.append(string4x);
               return Optional.empty();
            } else {
               return Optional.empty();
            }
         }, Style.EMPTY);
         return stringArray3[0] != null ? stringArray3[0] : d(stringBuilder4.toString());
      }
   }

   private static String d(String string0) {
      if (string0 != null && !string0.isEmpty()) {
         String string1 = string0.replaceAll("§.", "").toLowerCase(Locale.ROOT);

         for (String string5 : string1.split("[^a-z]+")) {
            if (!string5.isEmpty() && c.contains(string5)) {
               return string5;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static String b(PlayerEntity playerEntity0) {
      return playerEntity0.getGameProfile() != null ? playerEntity0.getGameProfile().getName() : playerEntity0.getName().getString();
   }

   public static String a(String string0) {
      if (string0 != null && !string0.isEmpty()) {
         String string1 = string0.toLowerCase(Locale.ROOT);
         String[] stringArray2 = new String[]{"groups/", "ranks/", "prefix/", "staff/", "roles/", "badges/", "tab/", "icons/"};

         for (String string6 : stringArray2) {
            int intValue7 = string1.indexOf(string6);
            if (intValue7 >= 0) {
               String string8 = string1.substring(intValue7 + string6.length());
               int intValue9 = string8.length();

               for (int intValue10 = 0; intValue10 < string8.length(); intValue10++) {
                  char character11 = string8.charAt(intValue10);
                  if (character11 < 'a' || character11 > 'z') {
                     intValue9 = intValue10;
                     break;
                  }
               }

               if (intValue9 > 0) {
                  String string17 = string8.substring(0, intValue9);
                  if (c.contains(string17)) {
                     return string17;
                  }
               }
            }
         }

         for (String string13 : c) {
            String string14 = "/" + string13;

            for (int intValue15 = string1.indexOf(string14); intValue15 >= 0; intValue15 = string1.indexOf(string14, intValue15 + 1)) {
               int intValue16 = intValue15 + string14.length();
               if (intValue16 >= string1.length() || string1.charAt(intValue16) < 'a' || string1.charAt(intValue16) > 'z') {
                  return string13;
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static String b(Team team0) {
      if (team0 == null) {
         return null;
      } else {
         if (team0.getPrefix() != null) {
            String string1 = a(team0.getPrefix());
            if (string1 != null) {
               return string1;
            }
         }

         return null;
      }
   }

   public static boolean b(String string0) {
      return string0 != null && d.contains(string0.toLowerCase(Locale.ROOT));
   }

   public static dev.leg1tka.gui.themes.ThemeGroupRegistry.a c(String string0) {
      if (string0 == null) {
         return null;
      } else {
         String string1 = string0.toLowerCase(Locale.ROOT);
         String string2 = a.getOrDefault(string1, string0);
         int intValue3 = b.getOrDefault(string1, -1);
         boolean flag4 = d.contains(string1);
         return new dev.leg1tka.gui.themes.ThemeGroupRegistry.a(string1, string2, intValue3, flag4);
      }
   }

   static {
      a.put("hydra", "Гидра");
      a.put("cerberus", "Цербер");
      a.put("triton", "Тритон");
      a.put("phoenix", "Феникс");
      a.put("pandar", "Пандар");
      a.put("heat", "Жара");
      a.put("cold", "Холод");
      a.put("kronos", "Кронос");
      a.put("summer", "Лето");
      a.put("winter", "Зима");
      a.put("phobos", "Фобос");
      a.put("ares", "Арес");
      a.put("aristocrat", "Аристократ");
      a.put("youtuber", "Ютубер");
      a.put("helper", "Хелпер");
      a.put("shelper", "Ст. Хелпер");
      a.put("moder", "Модер");
      a.put("smoder", "Ст. Модер");
      a.put("admin", "Админ");
      a.put("curator", "Куратор");
      a.put("owner", "Владелец");
      a.put("dev", "Разработчик");
      b.put("hydra", -14291019);
      b.put("cerberus", -14407514);
      b.put("triton", -14412111);
      b.put("phoenix", -13636240);
      b.put("pandar", -13779220);
      b.put("heat", -13635945);
      b.put("cold", -14123877);
      b.put("kronos", -14268497);
      b.put("summer", -13634800);
      b.put("winter", -13968170);
      b.put("phobos", -13675849);
      b.put("ares", -14123193);
      b.put("aristocrat", -14038609);
      b.put("youtuber", -13634684);
      b.put("helper", -14675989);
      b.put("shelper", -13631376);
      b.put("moder", -14155920);
      b.put("smoder", -16777017);
      b.put("admin", -13636944);
      b.put("curator", -43691);
      b.put("owner", -52429);
      b.put("dev", -11141121);
      d.add("youtuber");
      d.add("helper");
      d.add("shelper");
      d.add("moder");
      d.add("smoder");
      d.add("admin");
      d.add("curator");
      d.add("owner");
      d.add("dev");
   }

   public static record a(String key, String text, int col, boolean isstaff)  {



}
}
