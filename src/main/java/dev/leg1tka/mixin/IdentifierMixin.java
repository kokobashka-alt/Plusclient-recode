package dev.leg1tka.mixin;

import java.util.Locale;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Identifier.class})
public abstract class IdentifierMixin {
   private static final String SAFE_JOIN_PATH = "invalid_join_id";

   public IdentifierMixin() {
   }

   @Inject(
      method = {"validatePath(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void AspectDLC$sanitizeJoinPath(String string0, String string1, CallbackInfoReturnable<String> callbackInfoReturnable2) {
      if (shouldSanitizePath(string1)) {
         callbackInfoReturnable2.setReturnValue(sanitizePath(string1));
      }
   }

   @Inject(
      method = {"validateNamespace(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void AspectDLC$sanitizeJoinNamespace(String string0, String string1, CallbackInfoReturnable<String> callbackInfoReturnable2) {
      if (shouldSanitizeNamespace(string0)) {
         callbackInfoReturnable2.setReturnValue(sanitizeNamespace(string0));
      }
   }

   private static boolean shouldSanitizeNamespace(String string0) {
      if (string0 != null && !string0.isEmpty()) {
         for (int intValue1 = 0; intValue1 < string0.length(); intValue1++) {
            char character2 = string0.charAt(intValue1);
            if (!isAllowedNamespaceChar(character2)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   private static boolean shouldSanitizePath(String string0) {
      if (string0 != null && !string0.isEmpty()) {
         for (int intValue1 = 0; intValue1 < string0.length(); intValue1++) {
            char character2 = string0.charAt(intValue1);
            if (!isAllowedPathChar(character2)) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   private static boolean isAllowedNamespaceChar(char character0) {
      return character0 >= 'a' && character0 <= 'z' || character0 >= '0' && character0 <= '9' || character0 == '_' || character0 == '.' || character0 == '-';
   }

   private static boolean isAllowedPathChar(char character0) {
      return isAllowedNamespaceChar(character0) || character0 == '/';
   }

   private static String sanitizeNamespace(String string0) {
      StringBuilder stringBuilder1 = new StringBuilder(string0.length());
      String string2 = string0.toLowerCase(Locale.ROOT);

      for (int intValue3 = 0; intValue3 < string2.length(); intValue3++) {
         char character4 = string2.charAt(intValue3);
         if (isAllowedNamespaceChar(character4)) {
            stringBuilder1.append(character4);
         } else {
            stringBuilder1.append('_');
         }
      }

      String string5 = stringBuilder1.toString();
      return string5.isBlank() ? "minecraft" : string5;
   }

   private static String sanitizePath(String string0) {
      StringBuilder stringBuilder1 = new StringBuilder(string0.length());
      String string2 = string0.toLowerCase(Locale.ROOT);

      for (int intValue3 = 0; intValue3 < string2.length(); intValue3++) {
         char character4 = string2.charAt(intValue3);
         if (isAllowedPathChar(character4)) {
            stringBuilder1.append(character4);
         } else {
            stringBuilder1.append('_');
         }
      }

      String string5 = stringBuilder1.toString();
      return string5.isBlank() ? "invalid_join_id" : string5;
   }
}
