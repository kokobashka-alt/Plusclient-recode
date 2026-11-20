package dev.leg1tka.api.utils.resources;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class NamespaceResolver {
   private static String a;
   private static final String b = b();

   private NamespaceResolver() {
   }

   public static String a() {
      return b;
   }

   private static String b() {
      try {
         String string2;
         try (InputStream inputStream0 = dev.leg1tka.api.utils.resources.NamespaceResolver.class.getResourceAsStream("/META-INF/aspectdlc-ns.txt")) {
            if (inputStream0 == null) {
               return "aspectdlc";
            }

            String string1 = new String(inputStream0.readAllBytes(), StandardCharsets.UTF_8).trim();
            string2 = a(string1) ? string1 : "aspectdlc";
         }

         return string2;
      } catch (IOException iOException5) {
         return "aspectdlc";
      }
   }

   static boolean a(String string0) {
      if (string0 != null && !string0.isEmpty()) {
         for (int intValue1 = 0; intValue1 < string0.length(); intValue1++) {
            char character2 = string0.charAt(intValue1);
            boolean flag3 = character2 >= 'a' && character2 <= 'z' || character2 >= '0' && character2 <= '9' || character2 == '_' || character2 == '.' || character2 == '-';
            if (!flag3) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
