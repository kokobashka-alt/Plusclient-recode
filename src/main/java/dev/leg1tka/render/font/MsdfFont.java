package dev.leg1tka.render.font;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public final class MsdfFont implements dev.leg1tka.api.ClientAccess {
   private final String c;
   private final AbstractTexture d;
   private final float e;
   private final float f;
   private final float g;
   private final float h;
   private final float i;
   private final float j;
   private final float k;
   private final HashMap l;
   private final dev.leg1tka.render.font.GlyphVertexWriter[] m;
   private boolean n = false;

   MsdfFont(String string1, AbstractTexture abstractTexture2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, float floatValue9, HashMap<Integer, dev.leg1tka.render.font.GlyphVertexWriter> hashMap10) {
      this.c = string1;
      this.d = abstractTexture2;
      this.e = floatValue3;
      this.f = floatValue4;
      this.g = floatValue5;
      this.h = floatValue7;
      this.i = floatValue8;
      this.j = floatValue9;
      this.k = floatValue6 > 0.0F ? floatValue5 * 0.5F / floatValue6 : 0.09375F;
      this.l = hashMap10;
      int intValue11 = -1;

      for (int intValue13 : hashMap10.keySet()) {
         if (intValue13 >= 0 && intValue13 <= 65535 && intValue13 > intValue11) {
            intValue11 = intValue13;
         }
      }

      this.m = new dev.leg1tka.render.font.GlyphVertexWriter[intValue11 + 1];

      for (Entry entry16 : hashMap10.entrySet()) {
         int intValue14 = (Integer)entry16.getKey();
         if (intValue14 >= 0 && intValue14 <= intValue11) {
            this.m[intValue14] = (dev.leg1tka.render.font.GlyphVertexWriter)entry16.getValue();
         }
      }
   }

   public void a() {
      if (!this.n) {
         this.d.setFilter(true, false);
         this.n = true;
      }
   }

   public int b() {
      return this.d.getGlId();
   }

   public float c() {
      return this.e;
   }

   public float d() {
      return this.f;
   }

   public float e() {
      return this.g;
   }

   public float f() {
      return this.h;
   }

   public float g() {
      return this.h + this.j;
   }

   public dev.leg1tka.render.font.GlyphVertexWriter a(int intValue1) {
      return intValue1 >= 0 && intValue1 < this.m.length ? this.m[intValue1] : (dev.leg1tka.render.font.GlyphVertexWriter)this.l.get(intValue1);
   }

   private dev.leg1tka.render.font.GlyphVertexWriter a(char character1) {
      return character1 < this.m.length ? this.m[character1] : null;
   }

   public String h() {
      return this.c;
   }

   public void a(
      Matrix4f matrix4f1, VertexConsumer vertexConsumer2, float floatValue3, String string4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, int intValue9, int intValue10, int intValue11, int intValue12
   ) {
      this.b(matrix4f1, vertexConsumer2, floatValue3, string4, floatValue5, floatValue6, floatValue7, floatValue8, intValue9, intValue10, intValue11, intValue12, false);
   }

   public void a(
      Matrix4f matrix4f1,
      VertexConsumer vertexConsumer2,
      float floatValue3,
      String string4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      int intValue9,
      int intValue10,
      int intValue11,
      int intValue12,
      boolean flag13
   ) {
      this.b(matrix4f1, vertexConsumer2, floatValue3, string4, floatValue5, floatValue6, floatValue7, floatValue8, intValue9, intValue10, intValue11, intValue12, flag13);
   }

   public int b(
      Matrix4f matrix4f1,
      VertexConsumer vertexConsumer2,
      float floatValue3,
      String string4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      int intValue9,
      int intValue10,
      int intValue11,
      int intValue12,
      boolean flag13
   ) {
      if (string4 == null) {
         return 0;
      } else {
         int intValue14 = 0;
         int intValue15 = string4.length();

         for (int intValue16 = 0; intValue16 < intValue15; intValue16++) {
            char character17 = b(string4.charAt(intValue16));
            if (character17 == 167 && intValue16 + 1 < intValue15) {
               intValue16++;
            } else {
               dev.leg1tka.render.font.GlyphVertexWriter value18 = this.a(character17);
               if (value18 != null) {
                  if (value18.a()) {
                     floatValue6 += value18.a(floatValue3) + floatValue5;
                  } else {
                     floatValue6 += value18.a(matrix4f1, vertexConsumer2, floatValue3, floatValue6, floatValue7, floatValue8, intValue9, intValue10, intValue11, intValue12, flag13) + floatValue5;
                     intValue14++;
                  }
               }
            }
         }

         return intValue14;
      }
   }

   public float a(String string1, float floatValue2) {
      if (string1 == null) {
         return 0.0F;
      } else {
         float floatValue3 = 0.0F;
         int intValue4 = string1.length();

         for (int intValue5 = 0; intValue5 < intValue4; intValue5++) {
            char character6 = b(string1.charAt(intValue5));
            if (character6 == 167 && intValue5 + 1 < intValue4) {
               intValue5++;
            } else {
               dev.leg1tka.render.font.GlyphVertexWriter value7 = this.a(character6);
               if (value7 != null) {
                  floatValue3 += value7.a(floatValue2);
               }
            }
         }

         return floatValue3;
      }
   }

   public boolean a(String string1, float floatValue2, float[] floatValueArray3) {
      if (string1 == null) {
         return false;
      } else {
         float floatValue4 = 0.0F;
         float floatValue5 = 0.0F;
         float floatValue6 = 0.0F;
         boolean flag7 = false;
         int intValue8 = string1.length();

         for (int intValue9 = 0; intValue9 < intValue8; intValue9++) {
            char character10 = b(string1.charAt(intValue9));
            if (character10 == 167 && intValue9 + 1 < intValue8) {
               intValue9++;
            } else {
               dev.leg1tka.render.font.GlyphVertexWriter value11 = this.a(character10);
               if (value11 != null) {
                  if (!value11.a()) {
                     float floatValue12 = floatValue4 + (value11.b() + this.k) * floatValue2;
                     float floatValue13 = floatValue4 + (value11.b() + value11.e() - this.k) * floatValue2;
                     if (!flag7) {
                        floatValue5 = floatValue12;
                        floatValue6 = floatValue13;
                        flag7 = true;
                     } else {
                        floatValue5 = Math.min(floatValue5, floatValue12);
                        floatValue6 = Math.max(floatValue6, floatValue13);
                     }
                  }

                  floatValue4 += value11.a(floatValue2);
               }
            }
         }

         floatValueArray3[0] = flag7 ? floatValue5 : 0.0F;
         floatValueArray3[1] = flag7 ? floatValue6 : floatValue4;
         return flag7;
      }
   }

   private static char b(char character0) {
      return switch (character0) {
         case 'ǫ' -> 'Q';
         case 'ɢ' -> 'G';
         case 'ɪ' -> 'I';
         case 'ɴ' -> 'N';
         case 'ʀ' -> 'R';
         case 'ʏ' -> 'Y';
         case 'ʙ' -> 'B';
         case 'ʜ' -> 'H';
         case 'ʟ' -> 'L';
         case 'ғ', 'ꜰ' -> 'F';
         case 'ᴀ' -> 'A';
         case 'ᴄ' -> 'C';
         case 'ᴅ' -> 'D';
         case 'ᴇ' -> 'E';
         case 'ᴊ' -> 'J';
         case 'ᴋ' -> 'K';
         case 'ᴍ' -> 'M';
         case 'ᴏ' -> 'O';
         case 'ᴘ' -> 'P';
         case 'ᴛ' -> 'T';
         case 'ᴜ' -> 'U';
         case 'ᴠ' -> 'V';
         case 'ᴡ' -> 'W';
         case 'ᴢ' -> 'Z';
         case 'ꜱ' -> 'S';
         default -> character0;
      };
   }

   static String a(Identifier identifier0) {
      try {
         InputStream inputStream1 = minecraftClient.getResourceManager().open(identifier0);
         BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream1));
         String string3 = bufferedReader2.lines().collect(Collectors.joining("\n"));
         bufferedReader2.close();
         inputStream1.close();
         return string3;
      } catch (Exception exception4) {
         throw new RuntimeException("Failed to read resource: " + identifier0, exception4);
      }
   }

   public static dev.leg1tka.render.font.MsdfFont.a i() {
      return new dev.leg1tka.render.font.MsdfFont.a();
   }

   public static class a {
      private String a = "?";
      private Identifier b;
      private Identifier c;

      public a() {
      }

      public dev.leg1tka.render.font.MsdfFont.a a(String string1) {
         this.a = string1;
         return this;
      }

      public dev.leg1tka.render.font.MsdfFont.a b(String string1) {
         Identifier identifier2 = Identifier.of(dev.leg1tka.api.utils.resources.NamespaceResolver.a(), "fonts/msdf/" + string1 + "/font.json");
         Identifier identifier3 = Identifier.of(dev.leg1tka.api.utils.resources.NamespaceResolver.a(), "fonts/msdf/" + string1 + ".json");
         this.b = dev.leg1tka.api.ClientAccess.minecraftClient.getResourceManager().getResource(identifier2).isPresent() ? identifier2 : identifier3;
         return this;
      }

      public dev.leg1tka.render.font.MsdfFont.a c(String string1) {
         Identifier identifier2 = Identifier.of(dev.leg1tka.api.utils.resources.NamespaceResolver.a(), "fonts/msdf/" + string1 + "/font.png");
         Identifier identifier3 = Identifier.of(dev.leg1tka.api.utils.resources.NamespaceResolver.a(), "fonts/msdf/" + string1 + ".png");
         this.c = dev.leg1tka.api.ClientAccess.minecraftClient.getResourceManager().getResource(identifier2).isPresent() ? identifier2 : identifier3;
         return this;
      }

      public dev.leg1tka.render.font.MsdfFont a() {
         String string1 = dev.leg1tka.render.font.MsdfFont.a(this.b);
         JsonObject jsonObject2 = JsonParser.parseString(string1).getAsJsonObject();
         JsonObject jsonObject3 = jsonObject2.getAsJsonObject("atlas");
         float floatValue4 = jsonObject3.get("width").getAsFloat();
         float floatValue5 = jsonObject3.get("height").getAsFloat();
         float floatValue6 = jsonObject3.get("distanceRange").getAsFloat();
         float floatValue7 = jsonObject3.has("size") ? jsonObject3.get("size").getAsFloat() : 0.0F;
         JsonObject jsonObject8 = jsonObject2.getAsJsonObject("metrics");
         float floatValue9 = jsonObject8.get("lineHeight").getAsFloat();
         float floatValue10 = jsonObject8.get("ascender").getAsFloat();
         float floatValue11 = jsonObject8.get("descender").getAsFloat();
         HashMap hashMap12 = new HashMap();

         for (JsonElement jsonElement15 : jsonObject2.getAsJsonArray("glyphs")) {
            JsonObject jsonObject16 = jsonElement15.getAsJsonObject();
            int intValue17 = jsonObject16.get("unicode").getAsInt();
            float floatValue18 = jsonObject16.get("advance").getAsFloat();
            float floatValue19 = 0.0F;
            float floatValue20 = 0.0F;
            float floatValue21 = 0.0F;
            float floatValue22 = 0.0F;
            if (jsonObject16.has("planeBounds") && !jsonObject16.get("planeBounds").isJsonNull()) {
               JsonObject jsonObject23 = jsonObject16.getAsJsonObject("planeBounds");
               floatValue19 = jsonObject23.get("left").getAsFloat();
               floatValue20 = jsonObject23.get("top").getAsFloat();
               floatValue21 = jsonObject23.get("right").getAsFloat();
               floatValue22 = jsonObject23.get("bottom").getAsFloat();
            }

            float floatValue29 = 0.0F;
            float floatValue24 = 0.0F;
            float floatValue25 = 0.0F;
            float floatValue26 = 0.0F;
            if (jsonObject16.has("atlasBounds") && !jsonObject16.get("atlasBounds").isJsonNull()) {
               JsonObject jsonObject27 = jsonObject16.getAsJsonObject("atlasBounds");
               floatValue29 = jsonObject27.get("left").getAsFloat();
               floatValue24 = jsonObject27.get("top").getAsFloat();
               floatValue25 = jsonObject27.get("right").getAsFloat();
               floatValue26 = jsonObject27.get("bottom").getAsFloat();
            }

            dev.leg1tka.render.font.GlyphVertexWriter value30 = new dev.leg1tka.render.font.GlyphVertexWriter(intValue17, floatValue18, floatValue19, floatValue20, floatValue21, floatValue22, floatValue29, floatValue24, floatValue25, floatValue26, floatValue4, floatValue5);
            hashMap12.put(intValue17, value30);
         }

         AbstractTexture abstractTexture28 = dev.leg1tka.api.ClientAccess.minecraftClient.getTextureManager().getTexture(this.c);
         return new dev.leg1tka.render.font.MsdfFont(this.a, abstractTexture28, floatValue4, floatValue5, floatValue6, floatValue7, floatValue9, floatValue10, floatValue11, hashMap12);
      }
   }
}
