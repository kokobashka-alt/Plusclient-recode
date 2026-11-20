package dev.leg1tka.render;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.google.common.collect.Lists;
import java.awt.Rectangle;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL30;

public class ScissorStack {
   private static dev.leg1tka.render.ScissorStack.a a = new dev.leg1tka.render.ScissorStack.a();
   private static final List b = Lists.newArrayList();

   public ScissorStack() {
   }

   public static void a() {
      b.add(a.a());
   }

   public static void b() {
      if (!b.isEmpty()) {
         a = (dev.leg1tka.render.ScissorStack.a)b.remove(b.size() - 1);
         if (a.a) {
            GL30.glEnable(3089);
            GL30.glScissor(a.d, a.e, a.f, a.g);
         } else {
            GL30.glDisable(3089);
         }
      }
   }

   public static void c() {
      GL30.glDisable(3089);
      a.a = false;
   }

   private static Window d() {
      MinecraftClient minecraftClient0 = MinecraftClient.getInstance();
      return minecraftClient0 == null ? null : minecraftClient0.getWindow();
   }

   private static double e() {
      Window window0 = d();
      return window0 == null ? 1.0 : window0.getScaleFactor();
   }

   public static void a(int intValue0, int intValue1, int intValue2, int intValue3) {
      Window window4 = d();
      if (window4 != null) {
         double doubleValue5 = e();
         int intValue7 = (int)((double)intValue0 * doubleValue5);
         int intValue8 = (int)((double)intValue1 * doubleValue5);
         int intValue9 = (int)((double)intValue2 * doubleValue5);
         int intValue10 = (int)((double)intValue3 * doubleValue5);
         intValue8 = window4.getHeight() - intValue8 - intValue10;
         b(intValue7, intValue8, intValue9, intValue10);
      }
   }

   public static void a(double doubleValue0, double doubleValue2, double doubleValue4, double doubleValue6) {
      Window window8 = d();
      if (window8 != null) {
         double doubleValue9 = e();
         int intValue11 = (int)(doubleValue0 * doubleValue9);
         int intValue12 = (int)(doubleValue2 * doubleValue9);
         int intValue13 = (int)(doubleValue4 * doubleValue9);
         int intValue14 = (int)(doubleValue6 * doubleValue9);
         intValue12 = window8.getHeight() - intValue12 - intValue14;
         b(intValue11, intValue12, intValue13, intValue14);
      }
   }

   public static void a(double doubleValue0, double doubleValue2, double doubleValue4, double doubleValue6, float floatValue8) {
      Window window9 = d();
      if (window9 != null) {
         double doubleValue10 = e();
         float floatValue13 = (1.0F - floatValue8) / 2.0F;
         double doubleValue14 = doubleValue0 + doubleValue4 * (double)floatValue13;
         double doubleValue16 = doubleValue2 + doubleValue6 * (double)floatValue13;
         double doubleValue18 = doubleValue4 * (double)floatValue8;
         double doubleValue20 = doubleValue6 * (double)floatValue8;
         doubleValue14 = doubleValue14 * (double)floatValue8 + ((double)window9.getScaledWidth() - doubleValue18) * (double)floatValue13;
         int intValue22 = (int)(doubleValue14 * doubleValue10);
         int intValue23 = (int)(doubleValue16 * doubleValue10);
         int intValue24 = (int)(doubleValue18 * doubleValue10);
         int intValue25 = (int)(doubleValue20 * doubleValue10);
         intValue23 = window9.getHeight() - intValue23 - intValue25;
         b(intValue22, intValue23, intValue24, intValue25);
      }
   }

   public static void b(int intValue0, int intValue1, int intValue2, int intValue3) {
      Window window4 = d();
      if (window4 != null) {
         Rectangle rectangle5 = new Rectangle(0, 0, window4.getWidth(), window4.getHeight());
         Rectangle rectangle6;
         if (a.a) {
            rectangle6 = new Rectangle(a.d, a.e, a.f, a.g);
         } else {
            rectangle6 = rectangle5;
         }

         Rectangle rectangle7 = new Rectangle(intValue0 + a.b, intValue1 + a.c, intValue2, intValue3);
         Rectangle rectangle8 = rectangle6.intersection(rectangle7);
         rectangle8 = rectangle8.intersection(rectangle5);
         if (rectangle8.width < 0) {
            rectangle8.width = 0;
         }

         if (rectangle8.height < 0) {
            rectangle8.height = 0;
         }

         a.a = true;
         a.d = rectangle8.x;
         a.e = rectangle8.y;
         a.f = rectangle8.width;
         a.g = rectangle8.height;
         GL30.glEnable(3089);
         GL30.glScissor(rectangle8.x, rectangle8.y, rectangle8.width, rectangle8.height);
      }
   }

   public static void a(int intValue0, int intValue1) {
      a.b = intValue0;
      a.c = intValue1;
   }

   public static void b(int intValue0, int intValue1) {
      Window window2 = d();
      if (window2 != null) {
         int intValue3 = window2.getScaledHeight();
         double doubleValue4 = e();
         int intValue6 = (int)((double)intValue0 * doubleValue4);
         int intValue7 = (int)((double)intValue1 * doubleValue4);
         intValue7 = (int)((double)intValue3 * doubleValue4) - intValue7;
         a(intValue6, intValue7);
      }
   }

   static class a implements Cloneable {
      public boolean a;
      public int b;
      public int c;
      public int d;
      public int e;
      public int f;
      public int g;

      a() {
      }

      public dev.leg1tka.render.ScissorStack.a a() {
         try {
            return (dev.leg1tka.render.ScissorStack.a)super.clone();
         } catch (CloneNotSupportedException cloneNotSupportedException2) {
            throw new AssertionError(cloneNotSupportedException2);
         }
      }
   }
}
