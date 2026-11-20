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

public final class RotationSequenceModel {
   public static int a;
   public static int b;
   public static int c;
   public static int d;
   private static int j;
   private static int k;
   private static double l;
   private static double m;
   private static double n;
   private static double o;
   private static double p;
   private static double q;
   private static double r;
   private static double s;
   private final int t;
   private final int u;
   private int v;
   private int w;
   private final double[] x;
   private final double[] y;
   private final double[] z;
   private final double[] A;
   private final double[] B;
   private final double[] C;
   private final double[] D;
   private final double[] E;
   private final double[] F;
   private final double[] G;
   private final double[] H;
   private final double[] I;
   private final double[] J;
   private final double[] K;
   private final double[] L;
   private final double[] M;
   private final double[] N;
   private final double[] O;
   private final double[] P;
   private final double[] Q;
   private final int[][] R;
   private final double[][] S;
   private final int[] T;
   private double U = 40.0;
   private double V = 40.0;
   private final double[] W;
   private final double[] X;
   private final double[] Y;
   private final double[] Z;
   private final double[] ModuleStorage;
   private final boolean[] ab;
   private final int[] ac;
   private final int[] ad;
   private final int[] ae;
   private final int[] af;
   private final boolean[] ag;
   public final float[][] e;
   public double f;
   public double g;
   public double h;
   public boolean i;

   public RotationSequenceModel(int intValue1) {
      this.t = intValue1;
      this.u = intValue1 + 20 + 2;
      this.x = new double[this.u];
      this.y = new double[this.u];
      this.z = new double[this.u];
      this.A = new double[this.u];
      this.B = new double[this.u];
      this.C = new double[this.u];
      this.D = new double[this.u];
      this.E = new double[this.u];
      this.F = new double[this.u];
      this.G = new double[this.u];
      this.H = new double[this.u];
      this.I = new double[this.u];
      this.J = new double[this.u];
      this.K = new double[this.u];
      this.L = new double[this.u];
      this.M = new double[this.u];
      this.N = new double[this.u];
      this.O = new double[this.u];
      this.P = new double[this.u];
      this.Q = new double[this.u];
      this.R = new int[this.u][16];
      this.S = new double[this.u][48];
      this.T = new int[this.u];
      this.W = new double[this.u];
      this.X = new double[this.u];
      this.Y = new double[this.u];
      this.Z = new double[this.u];
      this.ModuleStorage = new double[this.u];
      this.ab = new boolean[this.u];
      this.ac = new int[this.u];
      this.ad = new int[this.u];
      this.ae = new int[this.u];
      this.af = new int[this.u];
      this.ag = new boolean[this.u];
      this.e = new float[intValue1][31];
   }

   public void a() {
      this.v = 0;
      this.w = 0;
      this.U = this.V = 40.0;
   }

   public boolean b() {
      return this.v >= 5;
   }

   public void a(double[] doubleValueArray1, int[] intValueArray2, double[] doubleValueArray3, int intValue4) {
      int intValue5 = this.w;
      this.w = (this.w + 1) % this.u;
      if (this.v < this.u) {
         this.v++;
      }

      this.x[intValue5] = doubleValueArray1[4];
      this.y[intValue5] = doubleValueArray1[5];
      this.z[intValue5] = doubleValueArray1[6];
      this.A[intValue5] = doubleValueArray1[7];
      this.B[intValue5] = doubleValueArray1[8];
      this.C[intValue5] = doubleValueArray1[11];
      this.D[intValue5] = doubleValueArray1[9];
      this.E[intValue5] = doubleValueArray1[10];
      this.F[intValue5] = doubleValueArray1[20];
      this.G[intValue5] = doubleValueArray1[21];
      this.H[intValue5] = doubleValueArray1[22];
      this.I[intValue5] = doubleValueArray1[23];
      this.J[intValue5] = doubleValueArray1[24];
      this.K[intValue5] = doubleValueArray1[30];
      this.L[intValue5] = doubleValueArray1[64];
      this.M[intValue5] = doubleValueArray1[51];
      this.N[intValue5] = doubleValueArray1[66];
      this.O[intValue5] = doubleValueArray1[72];
      this.U = this.L[intValue5] > 0.0 ? 0.0 : Math.min(this.U + 1.0, 40.0);
      this.V = this.M[intValue5] > 0.0 ? 0.0 : Math.min(this.V + 1.0, 40.0);
      this.P[intValue5] = this.U;
      this.Q[intValue5] = this.V;
      int intValue6 = Math.min(intValue4, 16);
      this.T[intValue5] = intValue6;
      System.arraycopy(intValueArray2, 0, this.R[intValue5], 0, intValue6);
      System.arraycopy(doubleValueArray3, 0, this.S[intValue5], 0, intValue6 * 3);
   }

   public boolean a(int intValue1) {
      if (this.v == 0) {
         return false;
      } else {
         int intValue2 = (this.w - 1 + this.u) % this.u;

         for (int intValue3 = 0; intValue3 < this.T[intValue2]; intValue3++) {
            if (this.R[intValue2][intValue3] == intValue1) {
               return true;
            }
         }

         return false;
      }
   }

   private static double a(double doubleValue0) {
      return doubleValue0 - 360.0 * Math.floor((doubleValue0 + 180.0) / 360.0);
   }

   private static float a(double doubleValue0, double doubleValue2, double doubleValue4) {
      return (float)Math.max(doubleValue2, Math.min(doubleValue4, doubleValue0));
   }

   private boolean a(int intValue1, int intValue2) {
      if (this.ab[intValue1] && this.ab[intValue2]) {
         double doubleValue3 = this.C[intValue1] > 0.0 ? 1.27 : 1.62;
         double doubleValue5 = Math.toRadians(this.x[intValue1]);
         double doubleValue7 = Math.toRadians(this.y[intValue1]);
         double doubleValue9 = -Math.sin(doubleValue5) * Math.cos(doubleValue7);
         double doubleValue11 = -Math.sin(doubleValue7);
         double doubleValue13 = Math.cos(doubleValue5) * Math.cos(doubleValue7);
         double doubleValue15 = 1.0 / (Math.abs(doubleValue9) < 1.0E-12 ? 1.0E-12 : doubleValue9);
         double doubleValue17 = 1.0 / (Math.abs(doubleValue11) < 1.0E-12 ? 1.0E-12 : doubleValue11);
         double doubleValue19 = 1.0 / (Math.abs(doubleValue13) < 1.0E-12 ? 1.0E-12 : doubleValue13);
         double doubleValue21 = (this.W[intValue2] - 0.3 - 0.1) * doubleValue15;
         double doubleValue23 = (this.W[intValue2] + 0.3 + 0.1) * doubleValue15;
         double doubleValue25 = (this.X[intValue2] - doubleValue3 - 0.1) * doubleValue17;
         double doubleValue27 = (this.X[intValue2] + 1.8 - doubleValue3 + 0.1) * doubleValue17;
         double doubleValue29 = (this.Y[intValue2] - 0.3 - 0.1) * doubleValue19;
         double doubleValue31 = (this.Y[intValue2] + 0.3 + 0.1) * doubleValue19;
         double doubleValue33 = Math.max(Math.max(Math.min(doubleValue21, doubleValue23), Math.min(doubleValue25, doubleValue27)), Math.min(doubleValue29, doubleValue31));
         double doubleValue35 = Math.min(Math.min(Math.max(doubleValue21, doubleValue23), Math.max(doubleValue25, doubleValue27)), Math.max(doubleValue29, doubleValue31));
         double doubleValue37 = Math.max(doubleValue33, 0.0);
         return doubleValue35 >= doubleValue37 && doubleValue37 <= 8.0;
      } else {
         return false;
      }
   }

   public void b(int intValue1) {
      int intValue2 = (this.w - this.v + this.u) % this.u;

      for (int intValue3 = 0; intValue3 < this.v; intValue3++) {
         int intValue4 = (intValue2 + intValue3) % this.u;
         this.ab[intValue4] = false;
         this.W[intValue4] = this.X[intValue4] = this.Y[intValue4] = 0.0;

         for (int intValue5 = 0; intValue5 < this.T[intValue4]; intValue5++) {
            if (this.R[intValue4][intValue5] == intValue1) {
               this.ab[intValue4] = true;
               this.W[intValue4] = this.S[intValue4][intValue5 * 3] - this.z[intValue4];
               this.X[intValue4] = this.S[intValue4][intValue5 * 3 + 1] - this.A[intValue4];
               this.Y[intValue4] = this.S[intValue4][intValue5 * 3 + 2] - this.B[intValue4];
               break;
            }
         }

         double doubleValue46 = this.C[intValue4] > 0.0 ? 1.27 : 1.62;
         double doubleValue7 = this.X[intValue4] + 0.9 - doubleValue46;
         double doubleValue9 = Math.hypot(this.W[intValue4], this.Y[intValue4]);
         this.Z[intValue4] = Math.toDegrees(Math.atan2(-this.W[intValue4], this.Y[intValue4]));
         this.ModuleStorage[intValue4] = -Math.toDegrees(Math.atan2(doubleValue7, doubleValue9));
      }

      int intValue44 = 0;
      int intValue45 = 0;
      int intValue47 = 0;
      int intValue6 = 0;

      for (int intValue48 = 0; intValue48 < this.v; intValue48++) {
         int intValue8 = (intValue2 + intValue48) % this.u;
         int intValue51 = intValue48 > 0 ? (intValue2 + intValue48 - 1) % this.u : intValue8;
         boolean flag10 = intValue48 > 0 && this.a(intValue8, intValue51);
         this.ag[intValue48] = flag10;
         intValue44 = flag10 ? Math.min(intValue44 + 1, 20) : 0;
         intValue45 = !flag10 && this.ab[intValue8] ? Math.min(intValue45 + 1, 20) : 0;
         intValue47 = Math.abs(a(this.x[intValue8] - this.x[intValue51])) < 1.0E-4 ? Math.min(intValue47 + 1, 10) : 0;
         intValue6 = Math.abs(this.y[intValue8] - this.y[intValue51]) < 1.0E-4 ? Math.min(intValue6 + 1, 10) : 0;
         this.ac[intValue48] = intValue44;
         this.ad[intValue48] = intValue45;
         this.ae[intValue48] = intValue47;
         this.af[intValue48] = intValue6;
      }

      int intValue49 = this.v - this.t;

      for (int intValue50 = 0; intValue50 < this.t; intValue50++) {
         int intValue52 = Math.max(0, intValue49 + intValue50);
         int intValue53 = (intValue2 + intValue52) % this.u;
         int intValue11 = intValue52 > 0 ? (intValue2 + intValue52 - 1) % this.u : intValue53;
         float[] floatValueArray12 = this.e[intValue50];
         double doubleValue13 = this.C[intValue53] > 0.0 ? 1.27 : 1.62;
         double doubleValue15 = this.X[intValue53] + 0.9 - doubleValue13;
         double doubleValue17 = Math.hypot(this.W[intValue53], this.Y[intValue53]);
         double doubleValue19 = Math.max(Math.hypot(doubleValue17, doubleValue15), 0.001);
         double doubleValue21 = a(this.x[intValue53] - this.Z[intValue53]);
         double doubleValue23 = this.y[intValue53] - this.ModuleStorage[intValue53];
         double doubleValue25 = doubleValue21 / Math.toDegrees(Math.atan2(0.3, doubleValue19));
         double doubleValue27 = doubleValue23 / Math.toDegrees(Math.atan2(0.9, doubleValue19));
         double doubleValue29 = this.z[intValue53] - this.z[intValue11];
         double doubleValue31 = this.A[intValue53] - this.A[intValue11];
         double doubleValue33 = this.B[intValue53] - this.B[intValue11];
         double doubleValue35 = Math.toRadians(this.x[intValue53]);
         double doubleValue37 = -doubleValue29 * Math.sin(doubleValue35) + doubleValue33 * Math.cos(doubleValue35);
         double doubleValue39 = doubleValue29 * Math.cos(doubleValue35) + doubleValue33 * Math.sin(doubleValue35);
         double doubleValue41 = 20.0 / Math.max(this.N[intValue53], 0.1);
         boolean flag43 = this.ab[intValue53];
         floatValueArray12[0] = (float)(a(this.x[intValue53] - this.x[intValue11]) / 10.0);
         floatValueArray12[1] = (float)((this.y[intValue53] - this.y[intValue11]) / 10.0);
         floatValueArray12[2] = (float)(this.y[intValue53] / 45.0);
         floatValueArray12[3] = flag43 ? a(doubleValue21, -180.0, 180.0) / 30.0F : 0.0F;
         floatValueArray12[4] = flag43 ? a(doubleValue23, -90.0, 90.0) / 30.0F : 0.0F;
         floatValueArray12[5] = flag43 ? a(doubleValue25, -8.0, 8.0) / 4.0F : 0.0F;
         floatValueArray12[6] = flag43 ? a(doubleValue27, -8.0, 8.0) / 4.0F : 0.0F;
         floatValueArray12[7] = flag43 ? a(doubleValue19, 0.0, 8.0) / 4.0F : 0.0F;
         floatValueArray12[8] = flag43 ? a(a(this.Z[intValue53] - this.Z[intValue11]), -45.0, 45.0) / 10.0F : 0.0F;
         floatValueArray12[9] = flag43 ? a(this.ModuleStorage[intValue53] - this.ModuleStorage[intValue11], -45.0, 45.0) / 10.0F : 0.0F;
         floatValueArray12[10] = a(doubleValue37, -2.0, 2.0) / 0.5F;
         floatValueArray12[11] = a(doubleValue39, -2.0, 2.0) / 0.5F;
         floatValueArray12[12] = a(doubleValue31, -2.0, 2.0) / 0.5F;
         floatValueArray12[13] = (float)this.D[intValue53];
         floatValueArray12[14] = (float)this.E[intValue53];
         floatValueArray12[15] = (float)this.F[intValue53];
         floatValueArray12[16] = (float)this.G[intValue53];
         floatValueArray12[17] = (float)this.H[intValue53];
         floatValueArray12[18] = (float)this.I[intValue53];
         floatValueArray12[19] = (float)this.J[intValue53];
         floatValueArray12[20] = a(this.K[intValue53], 0.0, 3.0) / 3.0F;
         floatValueArray12[21] = a((this.P[intValue53] + 0.5) / doubleValue41, 0.0, 1.0);
         floatValueArray12[22] = (float)(this.Q[intValue53] / 40.0);
         floatValueArray12[23] = (float)this.M[intValue53];
         floatValueArray12[24] = (float)Math.exp(-Math.min(this.O[intValue53], 255.0) / 4.0);
         floatValueArray12[25] = flag43 ? a(this.X[intValue53], -3.0, 3.0) / 2.0F : 0.0F;
         floatValueArray12[26] = this.ag[intValue52] ? 1.0F : 0.0F;
         floatValueArray12[27] = (float)((double)this.ac[intValue52] / 20.0);
         floatValueArray12[28] = (float)((double)this.ad[intValue52] / 20.0);
         floatValueArray12[29] = (float)((double)this.ae[intValue52] / 10.0);
         floatValueArray12[30] = (float)((double)this.af[intValue52] / 10.0);
         if (intValue50 == this.t - 1) {
            this.f = doubleValue25;
            this.g = doubleValue27;
            this.h = doubleValue19;
            this.i = flag43;
         }
      }
   }
}
