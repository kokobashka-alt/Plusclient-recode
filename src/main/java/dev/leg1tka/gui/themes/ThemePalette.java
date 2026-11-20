package dev.leg1tka.gui.themes;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import lombok.Generated;

public class ThemePalette implements dev.leg1tka.api.ClientAccess {
   private String x;
   public int[] c;
   public int d;
   public int e;
   public int f;
   public int g;
   public int h;
   public int i;
   public int j;
   public int k;
   public int l;
   public int m;
   public int n;
   public int o;
   public int p;
   public int q;
   public int r;
   public int s;
   public int t;
   public int u;
   public int v;
   public int w;

   public ThemePalette(String string1, int... intValueArray2) {
      this.x = string1;
      this.c = intValueArray2;
      int intValue3 = intValueArray2.length > 0 ? intValueArray2[0] | 0xFF000000 : -5618433;
      int intValue4 = intValueArray2.length > 1 ? intValueArray2[1] | 0xFF000000 : -15921904;
      this.a(intValue3, intValue4);
   }

   public void a(int intValue1, int intValue2) {
      this.d = -15921904;
      this.e = intValue1;
      this.f = -1513240;
      this.g = -7829351;
      this.h = -16119283;
      this.i = intValue1;
      this.j = intValue1;
      this.k = -1;
      this.l = -14013899;
      this.m = intValue1;
      this.n = -14013899;
      this.o = intValue1;
      this.p = -14013899;
      this.q = -14013899;
      this.r = -15066590;
      this.s = intValue2;
      this.t = -1;
      this.u = intValue2;
      this.v = intValue1;
      this.w = intValue1;
   }

   public void a(
      int intValue1,
      int intValue2,
      int intValue3,
      int intValue4,
      int intValue5,
      int intValue6,
      int intValue7,
      int intValue8,
      int intValue9,
      int intValue10,
      int intValue11,
      int intValue12,
      int intValue13,
      int intValue14,
      int intValue15,
      int intValue16,
      int intValue17,
      int intValue18,
      int intValue19,
      int intValue20,
      int intValue21,
      int intValue22
   ) {
      if (this.c != null && this.c.length >= 2) {
         this.c[0] = intValue1;
         this.c[1] = intValue2;
      } else {
         this.c = new int[]{intValue1, intValue2};
      }

      this.d = intValue3;
      this.e = intValue4;
      this.f = intValue5;
      this.g = intValue6;
      this.h = intValue7;
      this.i = intValue8;
      this.j = intValue9;
      this.k = intValue10;
      this.l = intValue11;
      this.m = intValue12;
      this.n = intValue13;
      this.o = intValue14;
      this.p = intValue15;
      this.q = intValue16;
      this.r = intValue17;
      this.s = intValue18;
      this.t = intValue19;
      this.u = intValue20;
      this.v = intValue21;
      this.w = intValue22;
   }

   public int[] a() {
      return new int[]{
         this.d,
         this.e,
         this.f,
         this.g,
         this.h,
         this.i,
         this.j,
         this.k,
         this.l,
         this.m,
         this.n,
         this.o,
         this.p,
         this.q,
         this.r,
         this.s,
         this.t,
         this.u,
         this.v,
         this.w
      };
   }

   public void a(int[] intValueArray1) {
      if (intValueArray1 != null && intValueArray1.length >= 20) {
         this.d = intValueArray1[0];
         this.e = intValueArray1[1];
         this.f = intValueArray1[2];
         this.g = intValueArray1[3];
         this.h = intValueArray1[4];
         this.i = intValueArray1[5];
         this.j = intValueArray1[6];
         this.k = intValueArray1[7];
         this.l = intValueArray1[8];
         this.m = intValueArray1[9];
         this.n = intValueArray1[10];
         this.o = intValueArray1[11];
         this.p = intValueArray1[12];
         this.q = intValueArray1[13];
         this.r = intValueArray1[14];
         this.s = intValueArray1[15];
         this.t = intValueArray1[16];
         this.u = intValueArray1[17];
         this.v = intValueArray1[18];
         this.w = intValueArray1[19];
      }
   }

   public int a(int intValue1) {
      return this.x.equals("Rainbow") ? dev.leg1tka.api.utils.text.ColorUtils.a(10, intValue1, 0.6F, 1.0F, 1.0F) : dev.leg1tka.api.utils.text.ColorUtils.a(5, intValue1, this.c);
   }

   @Generated
   public String b() {
      return this.x;
   }

   @Generated
   public int[] c() {
      return this.c;
   }

   @Generated
   public int d() {
      return this.d;
   }

   @Generated
   public int e() {
      return this.e;
   }

   @Generated
   public int f() {
      return this.f;
   }

   @Generated
   public int g() {
      return this.g;
   }

   @Generated
   public int h() {
      return this.h;
   }

   @Generated
   public int i() {
      return this.i;
   }

   @Generated
   public int j() {
      return this.j;
   }

   @Generated
   public int k() {
      return this.k;
   }

   @Generated
   public int l() {
      return this.l;
   }

   @Generated
   public int m() {
      return this.m;
   }

   @Generated
   public int n() {
      return this.n;
   }

   @Generated
   public int o() {
      return this.o;
   }

   @Generated
   public int p() {
      return this.p;
   }

   @Generated
   public int q() {
      return this.q;
   }

   @Generated
   public int r() {
      return this.r;
   }

   @Generated
   public int s() {
      return this.s;
   }

   @Generated
   public int t() {
      return this.t;
   }

   @Generated
   public int u() {
      return this.u;
   }

   @Generated
   public int v() {
      return this.v;
   }

   @Generated
   public int w() {
      return this.w;
   }

   @Generated
   public void a(String string1) {
      this.x = string1;
   }

   @Generated
   public void b(int[] intValueArray1) {
      this.c = intValueArray1;
   }

   @Generated
   public void b(int intValue1) {
      this.d = intValue1;
   }

   @Generated
   public void c(int intValue1) {
      this.e = intValue1;
   }

   @Generated
   public void d(int intValue1) {
      this.f = intValue1;
   }

   @Generated
   public void e(int intValue1) {
      this.g = intValue1;
   }

   @Generated
   public void f(int intValue1) {
      this.h = intValue1;
   }

   @Generated
   public void g(int intValue1) {
      this.i = intValue1;
   }

   @Generated
   public void h(int intValue1) {
      this.j = intValue1;
   }

   @Generated
   public void i(int intValue1) {
      this.k = intValue1;
   }

   @Generated
   public void j(int intValue1) {
      this.l = intValue1;
   }

   @Generated
   public void k(int intValue1) {
      this.m = intValue1;
   }

   @Generated
   public void l(int intValue1) {
      this.n = intValue1;
   }

   @Generated
   public void m(int intValue1) {
      this.o = intValue1;
   }

   @Generated
   public void n(int intValue1) {
      this.p = intValue1;
   }

   @Generated
   public void o(int intValue1) {
      this.q = intValue1;
   }

   @Generated
   public void p(int intValue1) {
      this.r = intValue1;
   }

   @Generated
   public void q(int intValue1) {
      this.s = intValue1;
   }

   @Generated
   public void r(int intValue1) {
      this.t = intValue1;
   }

   @Generated
   public void s(int intValue1) {
      this.u = intValue1;
   }

   @Generated
   public void t(int intValue1) {
      this.v = intValue1;
   }

   @Generated
   public void u(int intValue1) {
      this.w = intValue1;
   }
}
