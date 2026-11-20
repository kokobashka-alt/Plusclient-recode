package dev.leg1tka.rotation.neural;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public final class RotationDatasetLoader implements dev.leg1tka.api.ClientAccess {
   public final int c;
   public final int d;
   public final int e;
   public final int f;
   public final int g;
   public final List h;
   public final List i;
   public final int j;
   public final String k;
   public final File l;
   public final long m;
   private final float[] n;
   private final float[] o;
   private final float[] p;
   private final float[] q;
   private final float[] r;
   private final float[][][] s;
   private final float[][] t;
   private final float[][][] u;
   private final float[][] v;

   private RotationDatasetLoader(File file1, JsonObject jsonObject2) {
      this.l = file1;
      this.m = System.currentTimeMillis();
      this.c = jsonObject2.get("ctx").getAsInt();
      this.d = jsonObject2.get("gen").getAsInt();
      this.e = jsonObject2.get("z").getAsInt();
      this.f = jsonObject2.has("eps") ? jsonObject2.get("eps").getAsInt() : 0;
      this.g = jsonObject2.get("cond_dim").getAsInt();
      this.h = b(jsonObject2.getAsJsonArray("tick_feats"));
      this.i = b(jsonObject2.getAsJsonArray("state_feats"));
      this.j = jsonObject2.has("epoch") ? jsonObject2.get("epoch").getAsInt() : -1;
      JsonObject jsonObject3 = jsonObject2.getAsJsonObject("norm");
      this.n = a(jsonObject3.getAsJsonArray("h_mean"));
      this.o = a(jsonObject3.getAsJsonArray("h_std"));
      this.p = a(jsonObject3.getAsJsonArray("s_mean"));
      this.q = a(jsonObject3.getAsJsonArray("s_std"));
      this.r = a(jsonObject3.getAsJsonArray("o_std"));
      JsonArray jsonArray4 = jsonObject2.getAsJsonArray("layers");
      this.s = new float[jsonArray4.size()][][];
      this.t = new float[jsonArray4.size()][];
      a(jsonArray4, this.s, this.t);
      if (jsonObject2.has("still_layers")) {
         JsonArray jsonArray5 = jsonObject2.getAsJsonArray("still_layers");
         this.u = new float[jsonArray5.size()][][];
         this.v = new float[jsonArray5.size()][];
         a(jsonArray5, this.u, this.v);
         if (this.u[0][0].length != this.g) {
            throw new IllegalStateException(
               "голова пауз ждёт "
                  + this.u[0][0].length
                  + " входов, а нужно "
                  + this.g
            );
         }
      } else {
         this.u = null;
         this.v = null;
      }

      if (this.n.length != this.h.size() || this.p.length != this.i.size()) {
         throw new IllegalStateException(
            "нормализация не совпадает с признаками"
         );
      } else if (this.c * this.h.size() + this.i.size() != this.g) {
         throw new IllegalStateException(
            "cond_dim не совпадает с ctx и признаками"
         );
      } else if (this.s[0][0].length != this.g + this.e + this.f) {
         throw new IllegalStateException(
            "первый слой ждёт "
               + this.s[0][0].length
               + " входов, а нужно "
               + (this.g + this.e + this.f)
         );
      } else if (this.s[this.s.length - 1].length != this.d * 2) {
         throw new IllegalStateException("выход сети не " + this.d + "×2");
      } else {
         String string8 = "";
         if (jsonObject2.has("metrics") && jsonObject2.has("human")) {
            JsonObject jsonObject6 = jsonObject2.getAsJsonObject("metrics");
            JsonObject jsonObject7 = jsonObject2.getAsJsonObject("human");
            string8 = String.format(
               "на хитбоксе %.0f%% (ты %.0f%%), скорость %.0f°/с (ты %.0f), ускорения %.0f (ты %.0f), дрожь %.1f%% (ты %.1f%%)",
               jsonObject6.get("on_box").getAsDouble() * 100.0,
               jsonObject7.get("on_box").getAsDouble() * 100.0,
               jsonObject6.get("speed_p50").getAsDouble(),
               jsonObject7.get("speed_p50").getAsDouble(),
               jsonObject6.get("acc_p50").getAsDouble(),
               jsonObject7.get("acc_p50").getAsDouble(),
               jsonObject6.get("hf").getAsDouble() * 100.0,
               jsonObject7.get("hf").getAsDouble() * 100.0
            );
         }

         this.k = string8;
      }
   }

   public static dev.leg1tka.rotation.neural.RotationDatasetLoader a(File file0) throws Exception {
      dev.leg1tka.rotation.neural.RotationDatasetLoader value2;
      try (FileReader fileReader1 = new FileReader(file0)) {
         value2 = new dev.leg1tka.rotation.neural.RotationDatasetLoader(file0, JsonParser.parseReader(fileReader1).getAsJsonObject());
      }

      return value2;
   }

   public float[][] a(float[][] floatValueArrayArray1, float[] floatValueArray2, float[] floatValueArray3, float[] floatValueArray4) {
      float[] floatValueArray5 = new float[this.g + this.e + this.f];
      int intValue6 = this.a(floatValueArrayArray1, floatValueArray2, floatValueArray5);
      System.arraycopy(floatValueArray3, 0, floatValueArray5, intValue6, this.e);
      if (floatValueArray4 != null) {
         System.arraycopy(floatValueArray4, 0, floatValueArray5, intValue6 + this.e, this.f);
      }

      floatValueArray5 = a(this.s, this.t, floatValueArray5);
      float[][] floatValueArrayArray7 = new float[this.d][2];

      for (int intValue8 = 0; intValue8 < this.d; intValue8++) {
         floatValueArrayArray7[intValue8][0] = floatValueArray5[intValue8 * 2] * this.r[0];
         floatValueArrayArray7[intValue8][1] = floatValueArray5[intValue8 * 2 + 1] * this.r[1];
      }

      return floatValueArrayArray7;
   }

   public boolean a() {
      return this.u != null;
   }

   public float a(float[][] floatValueArrayArray1, float[] floatValueArray2) {
      if (this.u == null) {
         return 0.0F;
      } else {
         float[] floatValueArray3 = new float[this.g];
         this.a(floatValueArrayArray1, floatValueArray2, floatValueArray3);
         float floatValue4 = a(this.u, this.v, floatValueArray3)[0];
         return (float)(1.0 / (1.0 + Math.exp((double)(-floatValue4))));
      }
   }

   private int a(float[][] floatValueArrayArray1, float[] floatValueArray2, float[] floatValueArray3) {
      int intValue4 = this.h.size();
      int intValue5 = 0;

      for (int intValue6 = 0; intValue6 < this.c; intValue6++) {
         for (int intValue7 = 0; intValue7 < intValue4; intValue7++) {
            floatValueArray3[intValue5++] = (floatValueArrayArray1[intValue6][intValue7] - this.n[intValue7]) / this.o[intValue7];
         }
      }

      for (int intValue8 = 0; intValue8 < floatValueArray2.length; intValue8++) {
         floatValueArray3[intValue5++] = (floatValueArray2[intValue8] - this.p[intValue8]) / this.q[intValue8];
      }

      return intValue5;
   }

   private static float[] a(float[][][] floatValueArrayArrayArray0, float[][] floatValueArrayArray1, float[] floatValueArray2) {
      for (int intValue3 = 0; intValue3 < floatValueArrayArrayArray0.length; intValue3++) {
         float[][] floatValueArrayArray4 = floatValueArrayArrayArray0[intValue3];
         float[] floatValueArray5 = floatValueArrayArray1[intValue3];
         float[] floatValueArray6 = new float[floatValueArrayArray4.length];

         for (int intValue7 = 0; intValue7 < floatValueArrayArray4.length; intValue7++) {
            float floatValue8 = floatValueArray5[intValue7];
            float[] floatValueArray9 = floatValueArrayArray4[intValue7];

            for (int intValue10 = 0; intValue10 < floatValueArray9.length; intValue10++) {
               floatValue8 += floatValueArray9[intValue10] * floatValueArray2[intValue10];
            }

            floatValueArray6[intValue7] = intValue3 < floatValueArrayArrayArray0.length - 1 ? a(floatValue8) : floatValue8;
         }

         floatValueArray2 = floatValueArray6;
      }

      return floatValueArray2;
   }

   private static void a(JsonArray jsonArray0, float[][][] floatValueArrayArrayArray1, float[][] floatValueArrayArray2) {
      for (int intValue3 = 0; intValue3 < jsonArray0.size(); intValue3++) {
         JsonObject jsonObject4 = jsonArray0.get(intValue3).getAsJsonObject();
         JsonArray jsonArray5 = jsonObject4.getAsJsonArray("w");
         floatValueArrayArrayArray1[intValue3] = new float[jsonArray5.size()][];

         for (int intValue6 = 0; intValue6 < jsonArray5.size(); intValue6++) {
            floatValueArrayArrayArray1[intValue3][intValue6] = a(jsonArray5.get(intValue6).getAsJsonArray());
         }

         floatValueArrayArray2[intValue3] = a(jsonObject4.getAsJsonArray("b"));
      }
   }

   private static float a(float floatValue0) {
      return floatValue0 > 0.0F ? floatValue0 : (float)(Math.exp((double)floatValue0) - 1.0);
   }

   private static float[] a(JsonArray jsonArray0) {
      float[] floatValueArray1 = new float[jsonArray0.size()];

      for (int intValue2 = 0; intValue2 < floatValueArray1.length; intValue2++) {
         floatValueArray1[intValue2] = jsonArray0.get(intValue2).getAsFloat();
      }

      return floatValueArray1;
   }

   private static List<String> b(JsonArray jsonArray0) {
      ArrayList arrayList1 = new ArrayList();

      for (JsonElement jsonElement3 : jsonArray0) {
         arrayList1.add(jsonElement3.getAsString());
      }

      return arrayList1;
   }
}
