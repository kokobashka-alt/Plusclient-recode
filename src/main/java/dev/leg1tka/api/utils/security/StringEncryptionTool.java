package dev.leg1tka.api.utils.security;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.api.utils.security.NativeCrypto;

public class StringEncryptionTool {
   public StringEncryptionTool() {
   }

   public static void a(String[] stringArray0) {
      if (stringArray0.length == 0) {
         System.out.println("Usage: java StringEncryptor <string to encrypt>");
         System.out.println("Example: java StringEncryptor \"My Secret API Key\"");
      } else {
         String string1 = String.join(" ", stringArray0);
         String string2 = NativeCrypto.a(string1);
         System.out
            .println(
               "═══════════════════════════════════════"
            );
         System.out.println("String Encryption Tool");
         System.out
            .println(
               "═══════════════════════════════════════"
            );
         System.out.println("Plaintext:  " + string1);
         System.out.println("Encrypted:  " + string2);
         System.out.println("");
         System.out.println("Usage NameProtect code:");
         System.out.println("String value = NativeCrypto.loadString(\"" + string2 + "\");");
         System.out
            .println(
               "═══════════════════════════════════════"
            );
         String string3 = NativeCrypto.b(string2);
         if (string3.equals(string1)) {
            System.out.println("✓ Verification successful");
         } else {
            System.out.println("✗ Verification failed!");
         }
      }
   }
}
