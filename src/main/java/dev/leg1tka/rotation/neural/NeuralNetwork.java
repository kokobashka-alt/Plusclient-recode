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

import java.util.Random;

public class NeuralNetwork {
   private final int a;
   private final int b;
   private final int c;
   private double[][] d;
   private double[][] e;
   private double[] f;
   private double[] g;
   private double h = 0.01;
   private double[][] i;
   private double[][] j;
   private double k = 0.9;
   private final Random l = new Random();

   public NeuralNetwork(int intValue1, int intValue2, int intValue3) {
      this.a = intValue1;
      this.b = intValue2;
      this.c = intValue3;
      this.b();
   }

   private void b() {
      this.d = new double[this.a][this.b];
      this.e = new double[this.b][this.c];
      this.f = new double[this.b];
      this.g = new double[this.c];
      this.i = new double[this.a][this.b];
      this.j = new double[this.b][this.c];
      double doubleValue1 = Math.sqrt(6.0 / (double)(this.a + this.b));
      double doubleValue3 = Math.sqrt(6.0 / (double)(this.b + this.c));

      for (int intValue5 = 0; intValue5 < this.a; intValue5++) {
         for (int intValue6 = 0; intValue6 < this.b; intValue6++) {
            this.d[intValue5][intValue6] = this.l.nextDouble() * 2.0 * doubleValue1 - doubleValue1;
         }
      }

      for (int intValue7 = 0; intValue7 < this.b; intValue7++) {
         for (int intValue8 = 0; intValue8 < this.c; intValue8++) {
            this.e[intValue7][intValue8] = this.l.nextDouble() * 2.0 * doubleValue3 - doubleValue3;
         }
      }
   }

   public double[] a(double[] doubleValueArray1) {
      if (doubleValueArray1.length != this.a) {
         throw new IllegalArgumentException("Input size mismatch");
      } else {
         double[] doubleValueArray2 = new double[this.b];

         for (int intValue3 = 0; intValue3 < this.b; intValue3++) {
            double doubleValue4 = this.f[intValue3];

            for (int intValue6 = 0; intValue6 < this.a; intValue6++) {
               doubleValue4 += doubleValueArray1[intValue6] * this.d[intValue6][intValue3];
            }

            doubleValueArray2[intValue3] = this.b(doubleValue4);
         }

         double[] doubleValueArray8 = new double[this.c];

         for (int intValue9 = 0; intValue9 < this.c; intValue9++) {
            double doubleValue5 = this.g[intValue9];

            for (int intValue7 = 0; intValue7 < this.b; intValue7++) {
               doubleValue5 += doubleValueArray2[intValue7] * this.e[intValue7][intValue9];
            }

            doubleValueArray8[intValue9] = this.d(doubleValue5);
         }

         return doubleValueArray8;
      }
   }

   public void a(double[] doubleValueArray1, double[] doubleValueArray2, double doubleValue3) {
      if (doubleValueArray1.length == this.a && doubleValueArray2.length == this.c) {
         double[] doubleValueArray5 = new double[this.b];

         for (int intValue6 = 0; intValue6 < this.b; intValue6++) {
            double doubleValue7 = this.f[intValue6];

            for (int intValue9 = 0; intValue9 < this.a; intValue9++) {
               doubleValue7 += doubleValueArray1[intValue9] * this.d[intValue9][intValue6];
            }

            doubleValueArray5[intValue6] = this.b(doubleValue7);
         }

         double[] doubleValueArray13 = new double[this.c];

         for (int intValue14 = 0; intValue14 < this.c; intValue14++) {
            double doubleValue8 = this.g[intValue14];

            for (int intValue10 = 0; intValue10 < this.b; intValue10++) {
               doubleValue8 += doubleValueArray5[intValue10] * this.e[intValue10][intValue14];
            }

            doubleValueArray13[intValue14] = this.d(doubleValue8);
         }

         double[] doubleValueArray15 = new double[this.c];

         for (int intValue16 = 0; intValue16 < this.c; intValue16++) {
            doubleValueArray15[intValue16] = (doubleValueArray2[intValue16] - doubleValueArray13[intValue16]) * this.e(doubleValueArray13[intValue16]) * doubleValue3;
         }

         double[] doubleValueArray17 = new double[this.b];

         for (int intValue18 = 0; intValue18 < this.b; intValue18++) {
            double doubleValue23 = 0.0;

            for (int intValue12 = 0; intValue12 < this.c; intValue12++) {
               doubleValue23 += doubleValueArray15[intValue12] * this.e[intValue18][intValue12];
            }

            doubleValueArray17[intValue18] = doubleValue23 * this.c(doubleValueArray5[intValue18]);
         }

         for (int intValue19 = 0; intValue19 < this.b; intValue19++) {
            for (int intValue24 = 0; intValue24 < this.c; intValue24++) {
               double doubleValue11 = this.h * doubleValueArray15[intValue24] * doubleValueArray5[intValue19];
               this.j[intValue19][intValue24] = this.k * this.j[intValue19][intValue24] + doubleValue11;
               this.e[intValue19][intValue24] = this.e[intValue19][intValue24] + this.j[intValue19][intValue24];
            }
         }

         for (int intValue20 = 0; intValue20 < this.a; intValue20++) {
            for (int intValue25 = 0; intValue25 < this.b; intValue25++) {
               double doubleValue26 = this.h * doubleValueArray17[intValue25] * doubleValueArray1[intValue20];
               this.i[intValue20][intValue25] = this.k * this.i[intValue20][intValue25] + doubleValue26;
               this.d[intValue20][intValue25] = this.d[intValue20][intValue25] + this.i[intValue20][intValue25];
            }
         }

         for (int intValue21 = 0; intValue21 < this.c; intValue21++) {
            this.g[intValue21] = this.g[intValue21] + this.h * doubleValueArray15[intValue21];
         }

         for (int intValue22 = 0; intValue22 < this.b; intValue22++) {
            this.f[intValue22] = this.f[intValue22] + this.h * doubleValueArray17[intValue22];
         }
      } else {
         throw new IllegalArgumentException("Size mismatch");
      }
   }

   private double b(double doubleValue1) {
      return Math.max(0.0, doubleValue1);
   }

   private double c(double doubleValue1) {
      return doubleValue1 > 0.0 ? 1.0 : 0.0;
   }

   private double d(double doubleValue1) {
      return Math.tanh(doubleValue1);
   }

   private double e(double doubleValue1) {
      return 1.0 - doubleValue1 * doubleValue1;
   }

   public void a(double doubleValue1) {
      this.h = doubleValue1;
   }

   public double a() {
      return this.h;
   }
}
