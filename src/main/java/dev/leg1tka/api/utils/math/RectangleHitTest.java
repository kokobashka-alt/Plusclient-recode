package dev.leg1tka.api.utils.math;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

public class RectangleHitTest {
   public RectangleHitTest() {
   }

   public static boolean a(float floatValue0, float floatValue1, float floatValue2, float floatValue3, int intValue4, int intValue5) {
      return (float)intValue4 >= floatValue0 && (float)intValue5 >= floatValue1 && (float)intValue4 < floatValue0 + floatValue2 && (float)intValue5 < floatValue1 + floatValue3;
   }

   public static boolean a(int intValue0, int intValue1, int intValue2, int intValue3, int intValue4, int intValue5) {
      return intValue0 >= intValue2 && intValue0 <= intValue2 + intValue4 && intValue1 >= intValue3 && intValue1 <= intValue3 + intValue5;
   }

   public static boolean a(double doubleValue0, double doubleValue2, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      return doubleValue0 >= (double)floatValue4 && doubleValue0 <= (double)(floatValue4 + floatValue6) && doubleValue2 >= (double)floatValue5 && doubleValue2 <= (double)(floatValue5 + floatValue7);
   }

   public static boolean a(float floatValue0, float floatValue1, float floatValue2, float floatValue3, double doubleValue4, double doubleValue6) {
      return doubleValue4 >= (double)floatValue0 && doubleValue6 >= (double)floatValue1 && doubleValue4 < (double)(floatValue0 + floatValue2) && doubleValue6 < (double)(floatValue1 + floatValue3);
   }

   public static boolean a(double doubleValue0, double doubleValue2, int intValue4, int intValue5, int intValue6, int intValue7) {
      return doubleValue0 >= (double)intValue4 && doubleValue0 <= (double)(intValue4 + intValue6) && doubleValue2 >= (double)intValue5 && doubleValue2 <= (double)(intValue5 + intValue7);
   }

   public static boolean a(double doubleValue0, double doubleValue2, double doubleValue4, double doubleValue6, double doubleValue8, double doubleValue10) {
      return doubleValue0 > doubleValue4 && doubleValue0 < doubleValue4 + doubleValue8 && doubleValue2 > doubleValue6 && doubleValue2 < doubleValue6 + doubleValue10;
   }
}
