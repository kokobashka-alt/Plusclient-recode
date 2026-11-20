package dev.leg1tka.gui.components;


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

import dev.leg1tka.api.events.implement.EventBinding;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Generated;
import org.lwjgl.glfw.GLFW;

public final class InputBindings implements dev.leg1tka.api.ClientAccess {
   public static int c;

   public static void a(int intValue0, int intValue1) {
      if (intValue0 > -1) {
         if (intValue1 == 1) {
            if (intValue0 == 344) {
               dev.leg1tka.api.utils.sound.ClientSounds.a("opengui.wav", 0.6, 1.0F);
               minecraftClient.setScreen(new ClickGuiScreen());
            }

            if (intValue0 == ModuleManager.y.d.getKey()) {
               minecraftClient.setScreen(new AutoBuyScreen());
            }

            new EventBinding(intValue0, EventBinding.BindType.KEYBOARD).call();
            AirStuck.c.b(intValue0);
            ObjectArrayList objectArrayList2 = ModuleManager.a.a();
            int intValue3 = 0;

            for (int intValue4 = objectArrayList2.size(); intValue3 < intValue4; intValue3++) {
               Module module5 = (Module)objectArrayList2.get(intValue3);
               if (module5.getKeyBind() == intValue0) {
                  module5.toggle();
               }
            }
         }
      }
   }

   public static String a(int intValue0) {
      if (intValue0 == -1) {
         return "None";
      } else if (intValue0 >= 1000) {
         return d(intValue0);
      } else {
         String string1 = GLFW.glfwGetKeyName(intValue0, 0);
         return string1 != null ? string1.toUpperCase() : d(intValue0);
      }
   }

   public static void b(int intValue0, int intValue1) {
      if (minecraftClient.currentScreen == null) {
         if (intValue0 >= 0) {
            if (intValue1 == 1) {
               int intValue2 = 1000 + intValue0;
               new EventBinding(intValue2, EventBinding.BindType.MOUSE).call();
               AirStuck.c.b(intValue2);
               ObjectArrayList objectArrayList3 = ModuleManager.a.a();
               int intValue4 = 0;

               for (int intValue5 = objectArrayList3.size(); intValue4 < intValue5; intValue4++) {
                  Module module6 = (Module)objectArrayList3.get(intValue4);
                  if (module6.getKeyBind() == intValue2) {
                     module6.toggle();
                  }
               }
            }
         }
      }
   }

   public static boolean b(int intValue0) {
      if (intValue0 == -1) {
         return false;
      } else {
         long longValue1 = minecraftClient.getWindow().getHandle();
         if (intValue0 >= 1000) {
            int intValue3 = intValue0 - 1000;
            return GLFW.glfwGetMouseButton(longValue1, intValue3) == 1;
         } else {
            return GLFW.glfwGetKey(longValue1, intValue0) == 1;
         }
      }
   }

   public static boolean c(int intValue0) {
      return b(intValue0);
   }

   public static String d(int intValue0) {
      if (intValue0 == -1) {
         return "n/a";
      } else if (intValue0 >= 1000) {
         int intValue2 = intValue0 - 1000;

         return switch (intValue2) {
            case 0 -> "ЛКМ";
            case 1 -> "ПКМ";
            case 2 -> "СКМ";
            case 3 -> "MOUSE4";
            case 4 -> "MOUSE5";
            default -> "MOUSE" + (intValue2 + 1);
         };
      } else if (intValue0 >= 65 && intValue0 <= 90) {
         return String.valueOf((char)(65 + (intValue0 - 65)));
      } else if (intValue0 >= 48 && intValue0 <= 57) {
         return String.valueOf((char)(48 + (intValue0 - 48)));
      } else {
         String string1 = switch (intValue0) {
            case 39 -> "'";
            case 44 -> ",";
            case 45 -> "-";
            case 46 -> ".";
            case 47 -> "/";
            case 59 -> ";";
            case 61 -> "=";
            case 91 -> "[";
            case 92 -> "\\";
            case 93 -> "]";
            case 96 -> "`";
            default -> null;
         };
         if (string1 != null) {
            return string1;
         } else {
            return switch (intValue0) {
               case 32 -> "SPACE";
               case 256 -> "ESC";
               case 257 -> "ENTER";
               case 258 -> "TAB";
               case 259 -> "BACKSPACE";
               case 260 -> "INSERT";
               case 261 -> "DELETE";
               case 262 -> "RIGHT";
               case 263 -> "LEFT";
               case 264 -> "DOWN";
               case 265 -> "UP";
               case 266 -> "PAGEUP";
               case 267 -> "PAGEDOWN";
               case 268 -> "HOME";
               case 269 -> "END";
               case 280 -> "CAPS";
               case 290 -> "F1";
               case 291 -> "F2";
               case 292 -> "F3";
               case 293 -> "F4";
               case 294 -> "F5";
               case 295 -> "F6";
               case 296 -> "F7";
               case 297 -> "F8";
               case 298 -> "F9";
               case 299 -> "F10";
               case 300 -> "F11";
               case 301 -> "F12";
               case 320 -> "NUM0";
               case 321 -> "NUM1";
               case 322 -> "NUM2";
               case 323 -> "NUM3";
               case 324 -> "NUM4";
               case 325 -> "NUM5";
               case 326 -> "NUM6";
               case 327 -> "NUM7";
               case 328 -> "NUM8";
               case 329 -> "NUM9";
               case 330 -> "NUM.";
               case 331 -> "NUM/";
               case 332 -> "NUM*";
               case 333 -> "NUM-";
               case 334 -> "NUM+";
               case 335 -> "NUMENTER";
               case 340 -> "LSHIFT";
               case 341 -> "LCTRL";
               case 342 -> "LALT";
               case 344 -> "RSHIFT";
               case 345 -> "RCTRL";
               case 346 -> "RALT";
               default -> "KEY" + intValue0;
            };
         }
      }
   }

   public static boolean e(int intValue0) {
      return intValue0 >= 1000;
   }

   public static int f(int intValue0) {
      return e(intValue0) ? intValue0 - 1000 : -1;
   }

   public static int g(int intValue0) {
      return 1000 + intValue0;
   }

   @Generated
   private InputBindings() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
