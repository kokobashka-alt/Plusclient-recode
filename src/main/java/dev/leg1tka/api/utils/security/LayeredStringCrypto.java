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

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class LayeredStringCrypto {
   private static final byte[] a = a("V0l0n$M1l1t4ry#3ncrypt10n@2024", 32);
   private static final byte[] b = a("$3c0nd4ry&L4y3r^Pr0t3ct!0n", 32);
   private static String c;
   private static final SecureRandom d = new SecureRandom();

   private LayeredStringCrypto() {
   }

   public static String a(byte[] byteValueArray0) {
      try {
         byte[] byteValueArray1 = b(byteValueArray0);
         byte[] byteValueArray2 = a(byteValueArray1, 66);
         byte[] byteValueArray3 = b(byteValueArray2, a);
         byte[] byteValueArray4 = a(byteValueArray3, 171);
         byte[] byteValueArray5 = b(byteValueArray4, b);
         return Base64.getEncoder().encodeToString(byteValueArray5);
      } catch (Exception exception6) {
         throw new SecurityException("Encryption failed", exception6);
      }
   }

   public static byte[] a(String string0) {
      try {
         byte[] byteValueArray1 = Base64.getDecoder().decode(string0);
         byte[] byteValueArray2 = c(byteValueArray1, b);
         byte[] byteValueArray3 = a(byteValueArray2, 171);
         byte[] byteValueArray4 = c(byteValueArray3, a);
         byte[] byteValueArray5 = a(byteValueArray4, 66);
         return c(byteValueArray5);
      } catch (Exception exception6) {
         throw new SecurityException("Decryption failed", exception6);
      }
   }

   private static byte[] b(byte[] byteValueArray0, byte[] byteValueArray1) throws Exception {
      byte[] byteValueArray2 = new byte[16];
      d.nextBytes(byteValueArray2);
      Cipher cipher3 = Cipher.getInstance("AES/CBC/PKCS5Padding");
      SecretKeySpec secretKeySpec4 = new SecretKeySpec(byteValueArray1, "AES");
      IvParameterSpec ivParameterSpec5 = new IvParameterSpec(byteValueArray2);
      cipher3.init(1, secretKeySpec4, ivParameterSpec5);
      byte[] byteValueArray6 = cipher3.doFinal(byteValueArray0);
      ByteBuffer byteBuffer7 = ByteBuffer.allocate(byteValueArray2.length + byteValueArray6.length);
      byteBuffer7.put(byteValueArray2);
      byteBuffer7.put(byteValueArray6);
      return byteBuffer7.array();
   }

   private static byte[] c(byte[] byteValueArray0, byte[] byteValueArray1) throws Exception {
      byte[] byteValueArray2 = new byte[16];
      byte[] byteValueArray3 = new byte[byteValueArray0.length - 16];
      System.arraycopy(byteValueArray0, 0, byteValueArray2, 0, 16);
      System.arraycopy(byteValueArray0, 16, byteValueArray3, 0, byteValueArray3.length);
      Cipher cipher4 = Cipher.getInstance("AES/CBC/PKCS5Padding");
      SecretKeySpec secretKeySpec5 = new SecretKeySpec(byteValueArray1, "AES");
      IvParameterSpec ivParameterSpec6 = new IvParameterSpec(byteValueArray2);
      cipher4.init(2, secretKeySpec5, ivParameterSpec6);
      return cipher4.doFinal(byteValueArray3);
   }

   private static byte[] a(byte[] byteValueArray0, int intValue1) {
      byte[] byteValueArray2 = new byte[byteValueArray0.length];
      int intValue3 = intValue1;

      for (int intValue4 = 0; intValue4 < byteValueArray0.length; intValue4++) {
         byteValueArray2[intValue4] = (byte)(byteValueArray0[intValue4] ^ intValue3);
         intValue3 = intValue3 * 31 + intValue4 % 256 & 0xFF;
      }

      return byteValueArray2;
   }

   private static byte[] b(byte[] byteValueArray0) throws Exception {
      Deflater deflater1 = new Deflater(9);
      deflater1.setInput(byteValueArray0);
      deflater1.finish();
      byte[] byteValueArray2 = new byte[byteValueArray0.length * 2];
      int intValue3 = deflater1.deflate(byteValueArray2);
      deflater1.end();
      byte[] byteValueArray4 = new byte[intValue3];
      System.arraycopy(byteValueArray2, 0, byteValueArray4, 0, intValue3);
      return byteValueArray4;
   }

   private static byte[] c(byte[] byteValueArray0) throws Exception {
      Inflater inflater1 = new Inflater();
      inflater1.setInput(byteValueArray0);
      byte[] byteValueArray2 = new byte[byteValueArray0.length * 10];
      int intValue3 = inflater1.inflate(byteValueArray2);
      inflater1.end();
      byte[] byteValueArray4 = new byte[intValue3];
      System.arraycopy(byteValueArray2, 0, byteValueArray4, 0, intValue3);
      return byteValueArray4;
   }

   private static byte[] a(String string0, int intValue1) {
      try {
         MessageDigest messageDigest2 = MessageDigest.getInstance("SHA-256");
         byte[] byteValueArray3 = messageDigest2.digest(string0.getBytes(StandardCharsets.UTF_8));
         if (intValue1 == 32) {
            return byteValueArray3;
         } else {
            byte[] byteValueArray4 = new byte[intValue1];
            System.arraycopy(byteValueArray3, 0, byteValueArray4, 0, Math.min(intValue1, byteValueArray3.length));
            return byteValueArray4;
         }
      } catch (Exception exception5) {
         return new byte[intValue1];
      }
   }

   public static String b(String string0) {
      try {
         byte[] byteValueArray1 = new byte[8];
         d.nextBytes(byteValueArray1);
         byte[] byteValueArray2 = string0.getBytes(StandardCharsets.UTF_8);
         byte[] byteValueArray3 = new byte[byteValueArray1.length + byteValueArray2.length];
         System.arraycopy(byteValueArray1, 0, byteValueArray3, 0, byteValueArray1.length);
         System.arraycopy(byteValueArray2, 0, byteValueArray3, byteValueArray1.length, byteValueArray2.length);
         return a(byteValueArray3);
      } catch (Exception exception4) {
         return string0;
      }
   }

   public static String c(String string0) {
      try {
         byte[] byteValueArray1 = a(string0);
         byte[] byteValueArray2 = new byte[byteValueArray1.length - 8];
         System.arraycopy(byteValueArray1, 8, byteValueArray2, 0, byteValueArray2.length);
         return new String(byteValueArray2, StandardCharsets.UTF_8);
      } catch (Exception exception3) {
         return string0;
      }
   }

   public static boolean a(String string0, String string1) {
      if (string0 != null && string1 != null) {
         byte[] byteValueArray2 = string0.getBytes(StandardCharsets.UTF_8);
         byte[] byteValueArray3 = string1.getBytes(StandardCharsets.UTF_8);
         if (byteValueArray2.length != byteValueArray3.length) {
            return false;
         } else {
            int intValue4 = 0;

            for (int intValue5 = 0; intValue5 < byteValueArray2.length; intValue5++) {
               intValue4 |= byteValueArray2[intValue5] ^ byteValueArray3[intValue5];
            }

            return intValue4 == 0;
         }
      } else {
         return string0 == string1;
      }
   }

   public static String a(int intValue0) {
      byte[] byteValueArray1 = new byte[intValue0];
      d.nextBytes(byteValueArray1);
      return Base64.getUrlEncoder().withoutPadding().encodeToString(byteValueArray1);
   }

   public static String a(byte[] byteValueArray0, byte[] byteValueArray1) {
      try {
         Mac mac2 = Mac.getInstance("HmacSHA256");
         mac2.init(new SecretKeySpec(byteValueArray1, "HmacSHA256"));
         byte[] byteValueArray3 = mac2.doFinal(byteValueArray0);
         return Base64.getEncoder().encodeToString(byteValueArray3);
      } catch (Exception exception4) {
         return "";
      }
   }
}
