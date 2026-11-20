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

public final class RotationModelWeights {
   public static String a;
   public static int b;
   public final int c;
   public final int d;
   public final int e;
   public final int f;
   public final int g;
   public final int h;
   public final int i;
   public final int[] j;
   public final double k;
   public final double l;
   public final double m;
   public final double n;
   public final double o;
   public final double p;
   public final double q;
   private final float[][] r;
   private final float[] s;
   private final float[][][][] t;
   private final float[][] u;
   private final float[][] v;
   private final float[][] w;
   private final float[][] x;
   private final float[][] y;
   private final float[][] z;
   private final float[][] A;
   private final float[] B;
   private final float[] C;
   private final float[] D;
   private final float[] E;
   private final float[] F;
   private final float[] G;
   private final float[][] H;
   private final float[][] I;
   private final float[] J;
   private final float[] K;
   private final float[] L;
   private final float[] M;
   private final float[] N;
   private final double[] O;
   private final double[] P;
   private final double[] Q;
   private static dev.leg1tka.rotation.neural.RotationModelWeights R;
   private static String S;

   private RotationModelWeights(JsonObject jsonObject1) {
      if (!"neuro3-dmol-1".equals(jsonObject1.get("format").getAsString())) {
         throw new IllegalStateException("format " + jsonObject1.get("format"));
      } else {
         this.c = jsonObject1.get("nf").getAsInt();
         this.d = jsonObject1.get("hist").getAsInt();
         this.e = jsonObject1.get("chunk").getAsInt();
         this.f = jsonObject1.get("ch").getAsInt();
         this.g = jsonObject1.get("k").getAsInt();
         this.h = 3 * this.g + 1;
         this.i = jsonObject1.get("cap").getAsInt();
         this.k = jsonObject1.get("scale").getAsDouble();
         this.l = jsonObject1.get("ls_min").getAsDouble();
         this.m = jsonObject1.get("ls_max").getAsDouble();
         this.n = jsonObject1.get("gcd").getAsDouble();
         JsonArray jsonArray2 = jsonObject1.has("rho") ? jsonObject1.getAsJsonArray("rho") : null;
         this.o = jsonArray2 != null ? jsonArray2.get(0).getAsDouble() : 0.0;
         this.p = jsonArray2 != null ? jsonArray2.get(1).getAsDouble() : 0.0;
         this.q = jsonArray2 != null ? jsonArray2.get(2).getAsDouble() : 0.0;
         JsonArray jsonArray3 = jsonObject1.getAsJsonArray("dilations");
         this.j = new int[jsonArray3.size()];

         for (int intValue4 = 0; intValue4 < this.j.length; intValue4++) {
            this.j[intValue4] = jsonArray3.get(intValue4).getAsInt();
         }

         JsonObject jsonObject11 = jsonObject1.getAsJsonObject("w");
         float[][][] floatValueArrayArrayArray5 = c(jsonObject11.get("inp.weight"));
         this.r = new float[this.f][this.c];

         for (int intValue6 = 0; intValue6 < this.f; intValue6++) {
            for (int intValue7 = 0; intValue7 < this.c; intValue7++) {
               this.r[intValue6][intValue7] = floatValueArrayArrayArray5[intValue6][intValue7][0];
            }
         }

         this.s = a(jsonObject11.get("inp.bias"));
         this.t = new float[this.j.length][][][];
         this.u = new float[this.j.length][];

         for (int intValue12 = 0; intValue12 < this.j.length; intValue12++) {
            float[][][] floatValueArrayArrayArray13 = c(jsonObject11.get("convs." + intValue12 + ".weight"));
            this.t[intValue12] = new float[3][this.f][this.f];

            for (int intValue8 = 0; intValue8 < this.f; intValue8++) {
               for (int intValue9 = 0; intValue9 < this.f; intValue9++) {
                  for (int intValue10 = 0; intValue10 < 3; intValue10++) {
                     this.t[intValue12][intValue10][intValue8][intValue9] = floatValueArrayArrayArray13[intValue8][intValue9][intValue10];
                  }
               }
            }

            this.u[intValue12] = a(jsonObject11.get("convs." + intValue12 + ".bias"));
         }

         this.v = b(jsonObject11.get("yaw1.weight"));
         this.B = a(jsonObject11.get("yaw1.bias"));
         this.w = b(jsonObject11.get("yaw2.weight"));
         this.C = a(jsonObject11.get("yaw2.bias"));
         this.x = b(jsonObject11.get("pit1.weight"));
         this.D = a(jsonObject11.get("pit1.bias"));
         this.y = b(jsonObject11.get("pit2.weight"));
         this.E = a(jsonObject11.get("pit2.bias"));
         this.z = b(jsonObject11.get("clk1.weight"));
         this.F = a(jsonObject11.get("clk1.bias"));
         this.A = b(jsonObject11.get("clk2.weight"));
         this.G = a(jsonObject11.get("clk2.bias"));
         if (this.r.length == this.f
            && this.v[0].length == this.f + this.e
            && this.C.length == this.e * this.h
            && this.x[0].length == this.f + 2 * this.e + 2
            && this.E.length == this.h
            && this.z[0].length == this.f
            && this.G.length == this.e
            && this.c <= 31) {
            this.H = new float[this.d][this.f];
            this.I = new float[this.d][this.f];
            this.J = new float[this.f + this.e];
            this.K = new float[this.B.length];
            this.L = new float[this.f + 2 * this.e + 2];
            this.M = new float[this.D.length];
            this.N = new float[this.F.length];
            this.O = new double[this.g + 1];
            this.P = new double[this.g];
            this.Q = new double[this.g];
         } else {
            throw new IllegalStateException("shape mismatch");
         }
      }
   }

   public static synchronized dev.leg1tka.rotation.neural.RotationModelWeights a() {
      if (R == null && S == null) {
         try {
            R = new dev.leg1tka.rotation.neural.RotationModelWeights(JsonParser.parseString(dev.leg1tka.rotation.neural.EmbeddedRotationModel.a()).getAsJsonObject());
         } catch (Exception exception1) {
            S = String.valueOf(exception1.getMessage());
         }
      }

      return R;
   }

   public static String b() {
      return S;
   }

   private static float a(float floatValue0) {
      return floatValue0 > 0.0F ? floatValue0 : (float)(Math.exp((double)floatValue0) - 1.0);
   }

   private static void a(float[][] floatValueArrayArray0, float[] floatValueArray1, float[] floatValueArray2, float[] floatValueArray3, int intValue4, boolean flag5) {
      for (int intValue6 = 0; intValue6 < intValue4; intValue6++) {
         float floatValue7 = floatValueArray1[intValue6];
         float[] floatValueArray8 = floatValueArrayArray0[intValue6];

         for (int intValue9 = 0; intValue9 < floatValueArray2.length; intValue9++) {
            floatValue7 += floatValueArray8[intValue9] * floatValueArray2[intValue9];
         }

         floatValueArray3[intValue6] = flag5 ? a(floatValue7) : floatValue7;
      }
   }

   public void a(float[][] floatValueArrayArray1, float[] floatValueArray2) {
      for (int intValue3 = 0; intValue3 < this.d; intValue3++) {
         float[] floatValueArray4 = floatValueArrayArray1[intValue3];
         float[] floatValueArray5 = this.H[intValue3];

         for (int intValue6 = 0; intValue6 < this.f; intValue6++) {
            float floatValue7 = this.s[intValue6];
            float[] floatValueArray8 = this.r[intValue6];

            for (int intValue9 = 0; intValue9 < this.c; intValue9++) {
               floatValue7 += floatValueArray8[intValue9] * floatValueArray4[intValue9];
            }

            floatValueArray5[intValue6] = floatValue7;
         }
      }

      for (int intValue17 = 0; intValue17 < this.j.length; intValue17++) {
         int intValue18 = this.j[intValue17];
         float[][][] floatValueArrayArrayArray19 = this.t[intValue17];
         float[] floatValueArray20 = this.u[intValue17];

         for (int intValue21 = 0; intValue21 < this.d; intValue21++) {
            float[] floatValueArray23 = this.I[intValue21];
            System.arraycopy(floatValueArray20, 0, floatValueArray23, 0, this.f);

            for (int intValue25 = 0; intValue25 < 3; intValue25++) {
               int intValue10 = intValue21 - (2 - intValue25) * intValue18;
               if (intValue10 >= 0) {
                  float[] floatValueArray11 = this.H[intValue10];
                  float[][] floatValueArrayArray12 = floatValueArrayArrayArray19[intValue25];

                  for (int intValue13 = 0; intValue13 < this.f; intValue13++) {
                     float[] floatValueArray14 = floatValueArrayArray12[intValue13];
                     float floatValue15 = 0.0F;

                     for (int intValue16 = 0; intValue16 < this.f; intValue16++) {
                        floatValue15 += floatValueArray14[intValue16] * floatValueArray11[intValue16];
                     }

                     floatValueArray23[intValue13] += floatValue15;
                  }
               }
            }
         }

         for (int intValue22 = 0; intValue22 < this.d; intValue22++) {
            float[] floatValueArray24 = this.H[intValue22];
            float[] floatValueArray26 = this.I[intValue22];

            for (int intValue27 = 0; intValue27 < this.f; intValue27++) {
               floatValueArray24[intValue27] += a(floatValueArray26[intValue27]);
            }
         }
      }

      System.arraycopy(this.H[this.d - 1], 0, floatValueArray2, 0, this.f);
   }

   public void a(float[] floatValueArray1, float[] floatValueArray2) {
      a(this.z, this.F, floatValueArray1, this.N, this.N.length, true);

      for (int intValue3 = 0; intValue3 < this.e; intValue3++) {
         float floatValue4 = this.G[intValue3];
         float[] floatValueArray5 = this.A[intValue3];

         for (int intValue6 = 0; intValue6 < this.N.length; intValue6++) {
            floatValue4 += floatValueArray5[intValue6] * this.N[intValue6];
         }

         floatValueArray2[intValue3] = (float)(1.0 / (1.0 + Math.exp((double)(-floatValue4))));
      }
   }

   public void a(float[] floatValueArray1, float[] floatValueArray2, float[] floatValueArray3) {
      System.arraycopy(floatValueArray1, 0, this.J, 0, this.f);
      System.arraycopy(floatValueArray2, 0, this.J, this.f, this.e);
      a(this.v, this.B, this.J, this.K, this.K.length, true);
      a(this.w, this.C, this.K, floatValueArray3, this.h, false);
   }

   public void a(float[] floatValueArray1, float[] floatValueArray2, int intValue3, float[] floatValueArray4) {
      System.arraycopy(floatValueArray1, 0, this.L, 0, this.f);
      System.arraycopy(floatValueArray2, 0, this.L, this.f, this.e);

      for (int intValue5 = 0; intValue5 < this.e; intValue5++) {
         this.L[this.f + this.e + intValue5] = intValue5 == 0 ? 1.0F : 0.0F;
      }

      this.L[this.f + 2 * this.e] = (float)((double)intValue3 / this.k);
      this.L[this.f + 2 * this.e + 1] = intValue3 == 0 ? 1.0F : 0.0F;
      a(this.x, this.D, this.L, this.M, this.M.length, true);
      a(this.y, this.E, this.M, floatValueArray4, this.h, false);
   }

   private double b(double doubleValue1) {
      double doubleValue3 = doubleValue1 >= 0.0 ? this.O[0] : 0.0;

      for (int intValue5 = 0; intValue5 < this.g; intValue5++) {
         doubleValue3 += this.O[intValue5 + 1] / (1.0 + Math.exp(-(doubleValue1 - this.P[intValue5]) / this.Q[intValue5]));
      }

      return doubleValue3;
   }

   public int a(float[] floatValueArray1, float floatValue2, double doubleValue3) {
      double doubleValue5 = Double.NEGATIVE_INFINITY;

      for (int intValue7 = 0; intValue7 <= this.g; intValue7++) {
         doubleValue5 = Math.max(doubleValue5, (double)floatValueArray1[intValue7]);
      }

      double doubleValue16 = 0.0;

      for (int intValue9 = 0; intValue9 <= this.g; intValue9++) {
         this.O[intValue9] = Math.exp((double)floatValueArray1[intValue9] - doubleValue5);
         doubleValue16 += this.O[intValue9];
      }

      for (int intValue17 = 0; intValue17 <= this.g; intValue17++) {
         this.O[intValue17] = this.O[intValue17] / doubleValue16;
      }

      for (int intValue18 = 0; intValue18 < this.g; intValue18++) {
         this.P[intValue18] = (double)floatValueArray1[this.g + 1 + intValue18] * this.k;
         this.Q[intValue18] = Math.exp(Math.max(this.l, Math.min(this.m, (double)floatValueArray1[2 * this.g + 1 + intValue18]))) * this.k * (double)floatValue2;
      }

      double doubleValue19 = this.b((double)(-this.i) - 0.5);
      double doubleValue11 = this.b((double)this.i + 0.5) - doubleValue19;
      int intValue13 = -this.i;
      int intValue14 = this.i;

      while (intValue13 < intValue14) {
         int intValue15 = Math.floorDiv(intValue13 + intValue14, 2);
         if ((this.b((double)intValue15 + 0.5) - doubleValue19) / doubleValue11 > doubleValue3) {
            intValue14 = intValue15;
         } else {
            intValue13 = intValue15 + 1;
         }
      }

      return intValue13;
   }

   public static double a(double doubleValue0) {
      double doubleValue2 = -doubleValue0 / Math.sqrt(2.0);
      double doubleValue4 = 1.0 / (1.0 + 0.5 * Math.abs(doubleValue2));
      double doubleValue6 = doubleValue4
         * Math.exp(
            -doubleValue2 * doubleValue2
               - 1.26551223
               + doubleValue4
                  * (
                     1.00002368
                        + doubleValue4
                           * (
                              0.37409196
                                 + doubleValue4
                                    * (
                                       0.09678418
                                          + doubleValue4
                                             * (
                                                -0.18628806
                                                   + doubleValue4
                                                      * (0.27886807 + doubleValue4 * (-1.13520398 + doubleValue4 * (1.48851587 + doubleValue4 * (-0.82215223 + doubleValue4 * 0.17087277))))
                                             )
                                    )
                           )
                  )
         );
      double doubleValue8 = doubleValue2 >= 0.0 ? doubleValue6 : 2.0 - doubleValue6;
      return 0.5 * doubleValue8;
   }

   private static float[] a(JsonElement jsonElement0) {
      JsonArray jsonArray1 = jsonElement0.getAsJsonArray();
      float[] floatValueArray2 = new float[jsonArray1.size()];

      for (int intValue3 = 0; intValue3 < floatValueArray2.length; intValue3++) {
         floatValueArray2[intValue3] = jsonArray1.get(intValue3).getAsFloat();
      }

      return floatValueArray2;
   }

   private static float[][] b(JsonElement jsonElement0) {
      JsonArray jsonArray1 = jsonElement0.getAsJsonArray();
      float[][] floatValueArrayArray2 = new float[jsonArray1.size()][];

      for (int intValue3 = 0; intValue3 < floatValueArrayArray2.length; intValue3++) {
         floatValueArrayArray2[intValue3] = a(jsonArray1.get(intValue3));
      }

      return floatValueArrayArray2;
   }

   private static float[][][] c(JsonElement jsonElement0) {
      JsonArray jsonArray1 = jsonElement0.getAsJsonArray();
      float[][][] floatValueArrayArrayArray2 = new float[jsonArray1.size()][][];

      for (int intValue3 = 0; intValue3 < floatValueArrayArrayArray2.length; intValue3++) {
         floatValueArrayArrayArray2[intValue3] = b(jsonArray1.get(intValue3));
      }

      return floatValueArrayArrayArray2;
   }
}
