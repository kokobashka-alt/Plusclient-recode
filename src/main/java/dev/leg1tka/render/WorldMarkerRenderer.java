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

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class WorldMarkerRenderer implements dev.leg1tka.api.ClientAccess {
   double c;
   double d;
   double e;
   double f;
   double g;
   double h;
   long i;
   LivingEntity j;
   boolean k;
   long l;
   float m;
   float n;
   float o;
   float p;
   float q;
   float r;
   float s;
   float t;
   float u;

   public WorldMarkerRenderer(LivingEntity livingEntity1, double doubleValue2, double doubleValue4, double doubleValue6) {
      this.j = livingEntity1;
      this.c = doubleValue2;
      this.d = doubleValue4;
      this.e = doubleValue6;
      this.i = System.currentTimeMillis();
      this.p = (float)(Math.random() * 360.0);
      this.q = (float)(Math.random() * 360.0);
      this.r = (float)(Math.random() * 360.0);
      this.s = 1.4F + (float)Math.random() * 3.4F;
      this.t = 1.4F + (float)Math.random() * 3.4F;
      this.u = 1.4F + (float)Math.random() * 3.4F;
      this.m = (float)((Math.random() - 0.5) * 0.0022);
      this.n = 0.031F + (float)Math.random() * 0.02F;
      this.o = (float)((Math.random() - 0.5) * 0.0022);
   }

   public void a(float floatValue1, long longValue2, LivingEntity livingEntity4) {
      float floatValue5 = floatValue1 * 60.0F;
      this.p = this.p + this.s * floatValue5;
      this.q = this.q + this.t * floatValue5;
      this.r = this.r + this.u * floatValue5;
      if (!this.k) {
         this.c = this.c + (double)(this.m * floatValue5);
         this.d = this.d + (double)(this.n * floatValue5);
         this.e = this.e + (double)(this.o * floatValue5);
         this.m *= 0.992F;
         this.o *= 0.992F;
         this.n *= 0.989F;
         if (this.j != null) {
            double doubleValue6 = Math.max(2.2, (double)this.j.getHeight() * 1.85);
            if (this.d >= doubleValue6) {
               this.d = doubleValue6;
               this.b(longValue2);
               return;
            }
         }

         boolean flag8 = livingEntity4 == null || this.j == null || !this.j.isAlive() || this.j != livingEntity4;
         if (flag8 || longValue2 - this.i >= 560L) {
            this.b(longValue2);
         }
      }
   }

   public boolean a(long longValue1) {
      return this.k && longValue1 - this.l >= 320L;
   }

   public int a(int intValue1, int intValue2, float floatValue3, long longValue4) {
      float floatValue6 = this.c(longValue4);
      if (floatValue6 <= 0.001F) {
         return 0;
      } else {
         int intValue7 = dev.leg1tka.api.utils.text.ColorUtils.c(intValue1, (int)(floatValue6 * 255.0F));
         int intValue8 = dev.leg1tka.api.utils.text.ColorUtils.c(intValue2, (int)(floatValue6 * 255.0F));
         return TargetESP.c.a(intValue7, intValue8, floatValue3);
      }
   }

   public boolean a(BufferBuilder bufferBuilder1, MatrixStack matrixStack2, Vec3d vec3d3, float floatValue4, int intValue5) {
      float floatValue6 = (float)(intValue5 >> 24 & 0xFF) / 255.0F;
      if (floatValue6 <= 0.001F) {
         return false;
      } else {
         Vec3d vec3d7 = this.a(floatValue4);
         if (vec3d7 == null) {
            return false;
         } else {
            float floatValue8 = this.k ? MathHelper.lerp(MathHelper.clamp((float)(System.currentTimeMillis() - this.l) / 320.0F, 0.0F, 1.0F), 1.0F, 0.45F) : 1.0F;
            float floatValue9 = 0.12F * floatValue8;
            matrixStack2.push();
            matrixStack2.translate(vec3d7.x - vec3d3.x, vec3d7.y - vec3d3.y, vec3d7.z - vec3d3.z);
            matrixStack2.multiply(RotationAxis.POSITIVE_X.rotationDegrees(this.p));
            matrixStack2.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(this.q));
            matrixStack2.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(this.r));
            matrixStack2.scale(floatValue9, floatValue9, floatValue9);
            Matrix4f matrix4f10 = matrixStack2.peek().getPositionMatrix();
            this.a(bufferBuilder1, matrix4f10, intValue5);
            matrixStack2.pop();
            return true;
         }
      }
   }

   public boolean b(BufferBuilder bufferBuilder1, MatrixStack matrixStack2, Vec3d vec3d3, float floatValue4, int intValue5) {
      float floatValue6 = (float)(intValue5 >> 24 & 0xFF) / 255.0F;
      if (floatValue6 <= 0.001F) {
         return false;
      } else {
         Vec3d vec3d7 = this.a(floatValue4);
         if (vec3d7 == null) {
            return false;
         } else {
            float floatValue8 = this.k ? MathHelper.lerp(MathHelper.clamp((float)(System.currentTimeMillis() - this.l) / 320.0F, 0.0F, 1.0F), 1.0F, 0.45F) : 1.0F;
            float floatValue9 = 0.12F * floatValue8;
            matrixStack2.push();
            matrixStack2.translate(vec3d7.x - vec3d3.x, vec3d7.y - vec3d3.y, vec3d7.z - vec3d3.z);
            matrixStack2.multiply(RotationAxis.POSITIVE_X.rotationDegrees(this.p));
            matrixStack2.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(this.q));
            matrixStack2.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(this.r));
            matrixStack2.scale(floatValue9, floatValue9, floatValue9);
            Matrix4f matrix4f10 = matrixStack2.peek().getPositionMatrix();
            this.b(bufferBuilder1, matrix4f10, dev.leg1tka.api.utils.text.ColorUtils.c(intValue5, Math.max(1, (int)((float)(intValue5 >> 24 & 0xFF) * 0.7F))));
            matrixStack2.pop();
            return true;
         }
      }
   }

   public boolean a(BufferBuilder bufferBuilder1, MatrixStack matrixStack2, Vec3d vec3d3, float floatValue4, float floatValue5, float floatValue6, int intValue7, long longValue8) {
      float floatValue10 = this.c(longValue8);
      if (floatValue10 <= 0.001F) {
         return false;
      } else {
         Vec3d vec3d11 = this.a(floatValue6);
         if (vec3d11 == null) {
            return false;
         } else {
            float floatValue12 = this.k ? MathHelper.lerp(MathHelper.clamp((float)(longValue8 - this.l) / 320.0F, 0.0F, 1.0F), 1.0F, 0.55F) : 1.0F;
            float floatValue13 = 0.95F * floatValue12;
            int intValue14 = (int)(floatValue10 * 0.15F * 255.0F);
            if (intValue14 <= 0) {
               return false;
            } else {
               int intValue15 = intValue7 >> 16 & 0xFF;
               int intValue16 = intValue7 >> 8 & 0xFF;
               int intValue17 = intValue7 & 0xFF;
               matrixStack2.push();
               matrixStack2.translate(vec3d11.x - vec3d3.x, vec3d11.y - vec3d3.y, vec3d11.z - vec3d3.z);
               matrixStack2.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-floatValue4));
               matrixStack2.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue5));
               matrixStack2.scale(floatValue13, floatValue13, floatValue13);
               Matrix4f matrix4f18 = matrixStack2.peek().getPositionMatrix();
               bufferBuilder1.vertex(matrix4f18, -0.5F, 0.5F, 0.0F).texture(0.0F, 1.0F).color(intValue15, intValue16, intValue17, intValue14);
               bufferBuilder1.vertex(matrix4f18, 0.5F, 0.5F, 0.0F).texture(1.0F, 1.0F).color(intValue15, intValue16, intValue17, intValue14);
               bufferBuilder1.vertex(matrix4f18, 0.5F, -0.5F, 0.0F).texture(1.0F, 0.0F).color(intValue15, intValue16, intValue17, intValue14);
               bufferBuilder1.vertex(matrix4f18, -0.5F, -0.5F, 0.0F).texture(0.0F, 0.0F).color(intValue15, intValue16, intValue17, intValue14);
               matrixStack2.pop();
               return true;
            }
         }
      }
   }

   private void b(long longValue1) {
      if (!this.k) {
         Vec3d vec3d3 = this.a(1.0F);
         if (vec3d3 != null) {
            this.f = vec3d3.x;
            this.g = vec3d3.y;
            this.h = vec3d3.z;
         }

         this.l = longValue1;
         this.k = true;
         this.j = null;
      }
   }

   private float c(long longValue1) {
      if (!this.k) {
         float floatValue3 = MathHelper.clamp((float)(longValue1 - this.i) / 140.0F, 0.0F, 1.0F);
         float floatValue4 = 1.0F - MathHelper.clamp((float)(longValue1 - this.i - 440L) / 120.0F, 0.0F, 0.35F);
         return floatValue3 * floatValue4;
      } else {
         return 1.0F - MathHelper.clamp((float)(longValue1 - this.l) / 320.0F, 0.0F, 1.0F);
      }
   }

   private Vec3d a(float floatValue1) {
      return !this.k && this.j != null
         ? new Vec3d(
            MathHelper.lerp((double)floatValue1, this.j.lastRenderX, this.j.getX()) + this.c,
            MathHelper.lerp((double)floatValue1, this.j.lastRenderY, this.j.getY()) + this.d,
            MathHelper.lerp((double)floatValue1, this.j.lastRenderZ, this.j.getZ()) + this.e
         )
         : new Vec3d(this.f, this.g, this.h);
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, int intValue3) {
      float floatValue4 = -0.5F;
      float floatValue5 = 0.5F;
      int intValue6 = dev.leg1tka.api.utils.text.ColorUtils.c(intValue3, Math.max(1, (int)((float)(intValue3 >> 24 & 0xFF) * 0.16F)));
      this.a(bufferBuilder1, matrix4f2, floatValue4, floatValue4, floatValue4, floatValue5, floatValue5, floatValue5, intValue6);
   }

   private void b(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, int intValue3) {
      for (byte[] byteValueArray7 : TargetESP.g) {
         bufferBuilder1.vertex(matrix4f2, (float)byteValueArray7[0] * 0.5F, (float)byteValueArray7[1] * 0.5F, (float)byteValueArray7[2] * 0.5F).color(intValue3);
         bufferBuilder1.vertex(matrix4f2, (float)byteValueArray7[3] * 0.5F, (float)byteValueArray7[4] * 0.5F, (float)byteValueArray7[5] * 0.5F).color(intValue3);
      }
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, int intValue9) {
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue5).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue4, floatValue5).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue4, floatValue8).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue8).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue7, floatValue5).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue7, floatValue8).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue8).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue5).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue5).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue7, floatValue5).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue5).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue4, floatValue5).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue8).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue4, floatValue8).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue8).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue7, floatValue8).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue5).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue8).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue7, floatValue8).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue7, floatValue5).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue4, floatValue5).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue5).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue8).color(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue4, floatValue8).color(intValue9);
   }
}
