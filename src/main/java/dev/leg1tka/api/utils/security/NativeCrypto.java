package dev.leg1tka.api.utils.security;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public final class NativeCrypto {
   // Present without a ConstantValue or a bytecode write in the supplied class.
   private static final String b = null;
   private static final byte[] a = e("V0l0nCl13nt$3cur1ty");

   private NativeCrypto() {
   }

   public static String a(String string0) {
      try {
         SecretKeySpec secretKeySpec1 = new SecretKeySpec(a, "AES");
         Cipher cipher2 = Cipher.getInstance("AES");
         cipher2.init(1, secretKeySpec1);
         byte[] byteValueArray3 = cipher2.doFinal(string0.getBytes(StandardCharsets.UTF_8));
         return Base64.getEncoder().encodeToString(byteValueArray3);
      } catch (Exception exception4) {
         return string0;
      }
   }

   public static String b(String string0) {
      try {
         SecretKeySpec secretKeySpec1 = new SecretKeySpec(a, "AES");
         Cipher cipher2 = Cipher.getInstance("AES");
         cipher2.init(2, secretKeySpec1);
         byte[] byteValueArray3 = Base64.getDecoder().decode(string0);
         byte[] byteValueArray4 = cipher2.doFinal(byteValueArray3);
         return new String(byteValueArray4, StandardCharsets.UTF_8);
      } catch (Exception exception5) {
         return string0;
      }
   }

   public static String c(String string0) {
      try {
         MessageDigest messageDigest1 = MessageDigest.getInstance("SHA-256");
         byte[] byteValueArray2 = messageDigest1.digest(string0.getBytes(StandardCharsets.UTF_8));
         return a(byteValueArray2);
      } catch (Exception exception3) {
         return "";
      }
   }

   public static String a(String string0, int intValue1) {
      StringBuilder stringBuilder2 = new StringBuilder();

      for (char character6 : string0.toCharArray()) {
         stringBuilder2.append((char)(character6 ^ intValue1));
      }

      return stringBuilder2.toString();
   }

   private static byte[] e(String string0) {
      try {
         MessageDigest messageDigest1 = MessageDigest.getInstance("SHA-256");
         byte[] byteValueArray2 = messageDigest1.digest(string0.getBytes(StandardCharsets.UTF_8));
         byte[] byteValueArray3 = new byte[16];
         System.arraycopy(byteValueArray2, 0, byteValueArray3, 0, 16);
         return byteValueArray3;
      } catch (Exception exception4) {
         return new byte[16];
      }
   }

   private static String a(byte[] byteValueArray0) {
      StringBuilder stringBuilder1 = new StringBuilder();

      for (byte byteValue5 : byteValueArray0) {
         String string6 = Integer.toHexString(255 & byteValue5);
         if (string6.length() == 1) {
            stringBuilder1.append('0');
         }

         stringBuilder1.append(string6);
      }

      return stringBuilder1.toString();
   }

   static NativeCrypto.CryptoLib a() {
      try {
         return (NativeCrypto.CryptoLib)Native.load("aspectdlc_crypto", NativeCrypto.CryptoLib.class);
      } catch (Exception exception1) {
         return null;
      }
   }

   public static boolean a(byte[] byteValueArray0, String string1) {
      try {
         MessageDigest messageDigest2 = MessageDigest.getInstance("SHA-256");
         byte[] byteValueArray3 = messageDigest2.digest(byteValueArray0);
         String string4 = a(byteValueArray3);
         return string4.equalsIgnoreCase(string1);
      } catch (Exception exception5) {
         return false;
      }
   }

   public static String d(String string0) {
      return b(string0);
   }

   public interface CryptoLib extends Library {
      NativeCrypto.CryptoLib a = !Platform.isWindows() && !Platform.isLinux() ? null : NativeCrypto.a();
   }
}
