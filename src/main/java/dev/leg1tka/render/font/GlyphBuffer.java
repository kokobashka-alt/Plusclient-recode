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

import java.util.Arrays;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import org.joml.Matrix4f;

public final class GlyphBuffer {
   private dev.leg1tka.render.font.MsdfTextRenderer[] a = new dev.leg1tka.render.font.MsdfTextRenderer[128];
   private String[] b = new String[128];
   private float[] c = new float[128];
   private float[] d = new float[128];
   private int[] e = new int[128];
   private boolean[] f = new boolean[128];
   private dev.leg1tka.render.font.MsdfFont[] g = new dev.leg1tka.render.font.MsdfFont[4];
   private int h;

   public GlyphBuffer() {
   }

   public void a(dev.leg1tka.render.font.MsdfTextRenderer value1, String string2, float floatValue3, float floatValue4, int intValue5) {
      this.a(value1, string2, floatValue3, floatValue4, intValue5, false);
   }

   public void a(dev.leg1tka.render.font.MsdfTextRenderer value1, String string2, float floatValue3, float floatValue4, int intValue5, boolean flag6) {
      if (value1 != null && string2 != null && !string2.isEmpty()) {
         if (this.h == this.a.length) {
            int intValue7 = this.h * 2;
            this.a = Arrays.copyOf(this.a, intValue7);
            this.b = Arrays.copyOf(this.b, intValue7);
            this.c = Arrays.copyOf(this.c, intValue7);
            this.d = Arrays.copyOf(this.d, intValue7);
            this.e = Arrays.copyOf(this.e, intValue7);
            this.f = Arrays.copyOf(this.f, intValue7);
         }

         this.a[this.h] = value1;
         this.b[this.h] = string2;
         this.c[this.h] = floatValue3;
         this.d[this.h] = floatValue4;
         this.e[this.h] = intValue5;
         this.f[this.h] = flag6;
         this.h++;
      }
   }

   public boolean a() {
      return this.h == 0;
   }

   public void b() {
      Arrays.fill(this.a, 0, this.h, null);
      Arrays.fill(this.b, 0, this.h, null);
      this.h = 0;
   }

   public void a(Matrix4f matrix4f1) {
      if (this.h != 0) {
         int intValue2 = 0;

         for (int intValue3 = 0; intValue3 < this.h; intValue3++) {
            dev.leg1tka.render.font.MsdfFont value4 = this.a[intValue3].c();
            boolean flag5 = false;

            for (int intValue6 = 0; intValue6 < intValue2; intValue6++) {
               if (this.g[intValue6] == value4) {
                  flag5 = true;
                  break;
               }
            }

            if (!flag5) {
               if (intValue2 == this.g.length) {
                  this.g = Arrays.copyOf(this.g, this.g.length * 2);
               }

               this.g[intValue2++] = value4;
            }
         }

         for (int intValue9 = 0; intValue9 < intValue2; intValue9++) {
            dev.leg1tka.render.font.MsdfFont value10 = this.g[intValue9];
            dev.leg1tka.render.font.MsdfTextRenderer value11 = null;
            BufferBuilder bufferBuilder12 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

            for (int intValue7 = 0; intValue7 < this.h; intValue7++) {
               dev.leg1tka.render.font.MsdfTextRenderer value8 = this.a[intValue7];
               if (value8.c() == value10) {
                  if (value11 == null) {
                     value11 = value8;
                  }

                  value8.a(bufferBuilder12, matrix4f1, this.b[intValue7], this.c[intValue7], this.d[intValue7], this.e[intValue7], this.f[intValue7]);
               }
            }

            BuiltBuffer builtBuffer13 = bufferBuilder12.endNullable();
            if (builtBuffer13 != null && value11 != null) {
               value11.a(builtBuffer13);
            } else if (builtBuffer13 != null) {
               builtBuffer13.close();
            }

            this.g[intValue9] = null;
         }

         this.b();
      }
   }
}
