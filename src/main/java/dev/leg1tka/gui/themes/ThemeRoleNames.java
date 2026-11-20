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

import java.util.HashMap;
import java.util.Map;

public class ThemeRoleNames {
   private static final Map a = new HashMap();
   private static final Map b = new HashMap();
   private static final int[] c = new int[]{
      42240,
      42244,
      42248,
      42258,
      42262,
      42272,
      42276,
      42280,
      42336,
      42290,
      42294,
      42308,
      42326,
      42312,
      42304,
      42322,
      42249,
      42259,
      42263,
      42273,
      42277,
      42281,
      42291,
      42295,
      42241,
      42245,
      42313,
      4144,
      4138,
      4132,
      4134,
      4140,
      4139,
      4151,
      4130,
      4148,
      4141,
      4152,
      4133,
      4131,
      4149,
      4150,
      4137,
      4129,
      4145,
      4143,
      4146,
      4135,
      4153,
      4126
   };

   public ThemeRoleNames() {
   }

   public static String a(int intValue0) {
      return (String)a.get(intValue0);
   }

   public static int a(int intValue0, int intValue1, int intValue2, float floatValue3, int intValue4) {
      if (b(intValue0)) {
         Integer integer5 = (Integer)b.get(intValue0);
         if (integer5 == null) {
            return a(intValue4, floatValue3);
         } else {
            int intValue6 = dev.leg1tka.api.utils.text.ColorUtils.b(integer5, 0.8F);
            float floatValue7 = intValue2 <= 1 ? 1.0F : (float)intValue1 / (float)(intValue2 - 1);
            int intValue8 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue6, integer5, floatValue7);
            return a(intValue8, floatValue3);
         }
      } else {
         return a(intValue4, floatValue3);
      }
   }

   private static boolean b(int intValue0) {
      for (int intValue4 : c) {
         if (intValue4 == intValue0) {
            return true;
         }
      }

      return false;
   }

   private static int a(int intValue0, float floatValue1) {
      int intValue2 = Math.max(0, Math.min(255, (int)(floatValue1 * 255.0F)));
      return dev.leg1tka.api.utils.text.ColorUtils.d(intValue0, intValue2);
   }

   static {
      a.put(9889, "");
      a.put(9733, "");
      a.put(42240, "PLAYER");
      a.put(42244, "HERO");
      a.put(42248, "TITAN");
      a.put(42258, "AVENGER");
      a.put(42262, "OVERLORD");
      a.put(42272, "MAGISTER");
      a.put(42276, "IMPERATOR");
      a.put(42280, "DRAGON");
      a.put(42336, "D.HELPER");
      a.put(42290, "BULL");
      a.put(42294, "TIGER");
      a.put(42308, "VAMPIRE");
      a.put(42326, "BUNNY");
      a.put(42312, "COBRA");
      a.put(42304, "HYDRA");
      a.put(42322, "RABBIT");
      a.put(42249, "HELPER");
      a.put(42259, "ML.MODER");
      a.put(42263, "MODER");
      a.put(42273, "MODER+");
      a.put(42277, "ST.MODER");
      a.put(42281, "GL.MODER");
      a.put(42291, "ML.ADMIN");
      a.put(42295, "ADMIN");
      a.put(42241, "MEDIA");
      a.put(42245, "YT");
      a.put(42305, "GOD");
      a.put(4144, "HERO");
      a.put(4138, "TITAN");
      a.put(4132, "PRINCE");
      a.put(4134, "PHOENIX");
      a.put(4140, "OVERLORD");
      a.put(4139, "GUARDIAN");
      a.put(4151, "KRATOS");
      a.put(4130, "PHANTOM");
      a.put(4148, "CUSTOM");
      a.put(4141, "WINTER");
      a.put(4152, "SAKURA");
      a.put(4133, "SUMMER");
      a.put(4131, "HALLOWEEN");
      a.put(4149, "TIKTOK");
      a.put(4150, "TIKTOK+");
      a.put(4137, "MEDIA");
      a.put(4129, "YOUTUBE");
      a.put(4145, "HELPER");
      a.put(4143, "ML.ADMIN");
      a.put(4146, "MODER");
      a.put(4135, "CURATOR");
      a.put(4153, "SPECTATOR");
      a.put(4126, "DEVELOPER");
      a.put(7424, "A");
      a.put(665, "B");
      a.put(7428, "C");
      a.put(7429, "D");
      a.put(7431, "E");
      a.put(42800, "F");
      a.put(610, "G");
      a.put(668, "H");
      a.put(618, "I");
      a.put(7434, "J");
      a.put(7435, "K");
      a.put(671, "L");
      a.put(7437, "M");
      a.put(628, "N");
      a.put(7439, "O");
      a.put(7448, "P");
      a.put(491, "Q");
      a.put(640, "R");
      a.put(7451, "T");
      a.put(7452, "U");
      a.put(42801, "S");
      a.put(7456, "V");
      a.put(7457, "W");
      a.put(7521, "X");
      a.put(655, "Y");
      a.put(7458, "Z");
      b.put(42240, dev.leg1tka.api.utils.text.ColorUtils.b(141, 143, 141));
      b.put(42244, dev.leg1tka.api.utils.text.ColorUtils.b(100, 113, 251));
      b.put(42248, dev.leg1tka.api.utils.text.ColorUtils.b(245, 220, 29));
      b.put(42258, dev.leg1tka.api.utils.text.ColorUtils.b(79, 201, 83));
      b.put(42262, dev.leg1tka.api.utils.text.ColorUtils.b(85, 255, 255));
      b.put(42272, dev.leg1tka.api.utils.text.ColorUtils.b(224, 138, 52));
      b.put(42276, dev.leg1tka.api.utils.text.ColorUtils.b(202, 60, 60));
      b.put(42280, dev.leg1tka.api.utils.text.ColorUtils.b(245, 51, 238));
      b.put(42336, dev.leg1tka.api.utils.text.ColorUtils.b(214, 200, 42));
      b.put(42290, dev.leg1tka.api.utils.text.ColorUtils.b(121, 81, 202));
      b.put(42294, dev.leg1tka.api.utils.text.ColorUtils.b(202, 130, 60));
      b.put(42308, dev.leg1tka.api.utils.text.ColorUtils.b(202, 60, 60));
      b.put(42326, dev.leg1tka.api.utils.text.ColorUtils.b(68, 65, 66));
      b.put(42312, dev.leg1tka.api.utils.text.ColorUtils.b(127, 214, 86));
      b.put(42304, dev.leg1tka.api.utils.text.ColorUtils.b(92, 120, 7));
      b.put(42322, dev.leg1tka.api.utils.text.ColorUtils.b(230, 232, 230));
      b.put(42249, dev.leg1tka.api.utils.text.ColorUtils.b(214, 200, 42));
      b.put(42259, dev.leg1tka.api.utils.text.ColorUtils.b(100, 113, 251));
      b.put(42263, dev.leg1tka.api.utils.text.ColorUtils.b(100, 113, 251));
      b.put(42273, dev.leg1tka.api.utils.text.ColorUtils.b(121, 81, 202));
      b.put(42277, dev.leg1tka.api.utils.text.ColorUtils.b(100, 113, 251));
      b.put(42281, dev.leg1tka.api.utils.text.ColorUtils.b(121, 81, 202));
      b.put(42291, dev.leg1tka.api.utils.text.ColorUtils.b(64, 151, 214));
      b.put(42295, dev.leg1tka.api.utils.text.ColorUtils.b(202, 60, 60));
      b.put(42241, dev.leg1tka.api.utils.text.ColorUtils.b(121, 81, 202));
      b.put(42245, dev.leg1tka.api.utils.text.ColorUtils.b(255, 255, 255));
      b.put(42305, dev.leg1tka.api.utils.text.ColorUtils.b(245, 198, 29));
      b.put(42313, dev.leg1tka.api.utils.text.ColorUtils.b(202, 130, 60));
      b.put(4144, dev.leg1tka.api.utils.text.ColorUtils.b(13, 176, 209));
      b.put(4138, dev.leg1tka.api.utils.text.ColorUtils.b(21, 232, 24));
      b.put(4132, dev.leg1tka.api.utils.text.ColorUtils.b(232, 169, 21));
      b.put(4134, dev.leg1tka.api.utils.text.ColorUtils.b(237, 215, 19));
      b.put(4140, dev.leg1tka.api.utils.text.ColorUtils.b(64, 163, 152));
      b.put(4139, dev.leg1tka.api.utils.text.ColorUtils.b(86, 196, 99));
      b.put(4151, dev.leg1tka.api.utils.text.ColorUtils.b(147, 46, 230));
      b.put(4130, dev.leg1tka.api.utils.text.ColorUtils.b(230, 46, 46));
      b.put(4148, dev.leg1tka.api.utils.text.ColorUtils.b(16, 35, 179));
      b.put(4141, dev.leg1tka.api.utils.text.ColorUtils.b(55, 154, 184));
      b.put(4152, dev.leg1tka.api.utils.text.ColorUtils.b(184, 39, 159));
      b.put(4133, dev.leg1tka.api.utils.text.ColorUtils.b(255, 182, 56));
      b.put(4131, dev.leg1tka.api.utils.text.ColorUtils.b(232, 60, 30));
      b.put(4149, dev.leg1tka.api.utils.text.ColorUtils.b(0, 0, 0));
      b.put(4150, dev.leg1tka.api.utils.text.ColorUtils.b(0, 0, 0));
      b.put(4137, dev.leg1tka.api.utils.text.ColorUtils.b(37, 232, 30));
      b.put(4129, dev.leg1tka.api.utils.text.ColorUtils.b(232, 30, 30));
      b.put(4145, dev.leg1tka.api.utils.text.ColorUtils.b(30, 134, 232));
      b.put(4143, dev.leg1tka.api.utils.text.ColorUtils.b(89, 167, 227));
      b.put(4146, dev.leg1tka.api.utils.text.ColorUtils.b(62, 137, 194));
      b.put(4135, dev.leg1tka.api.utils.text.ColorUtils.b(56, 235, 74));
      b.put(4153, dev.leg1tka.api.utils.text.ColorUtils.b(173, 184, 174));
      b.put(4126, dev.leg1tka.api.utils.text.ColorUtils.b(255, 0, 25));
   }
}
