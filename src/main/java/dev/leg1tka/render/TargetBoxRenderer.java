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

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class TargetBoxRenderer implements dev.leg1tka.api.ClientAccess {
   private static float g;
   private static float h;
   private static float i;
   private static int j;
   private LivingEntity k;
   private float l;
   private double m;
   private double n;
   private double o;
   private long p;
   private Matrix4f q;
   private double r;
   private double s;
   private double t;
   private final Matrix4f u = new Matrix4f();
   private final Matrix4f v = new Matrix4f();
   private final Vector3f w = new Vector3f();
   private final float[] x = new float[48];
   private int y;
   private int z;
   public int c;
   public boolean d;
   public float e = 14.0F;
   public float f = 9.0F;
   private float A;
   private float B;

   public TargetBoxRenderer() {
   }

   public void a(Matrix4f matrix4f1, Vec3d vec3d2, float floatValue3, LivingEntity livingEntity4, boolean flag5, Vec3d vec3d6, boolean flag7) {
      long longValue8 = System.nanoTime();
      float floatValue10 = this.p == 0L ? 0.0F : MathHelper.clamp((float)(longValue8 - this.p) / 1.0E9F, 0.0F, 0.1F);
      this.p = longValue8;
      boolean flag11 = flag5 && livingEntity4 != null;
      if (flag11) {
         if (this.k == livingEntity4 && !(this.l < 0.02F)) {
            float floatValue12 = 1.0F - (float)Math.exp((double)(-floatValue10 * 22.0F));
            this.m = this.m + (vec3d6.x - this.m) * (double)floatValue12;
            this.n = this.n + (vec3d6.y - this.n) * (double)floatValue12;
            this.o = this.o + (vec3d6.z - this.o) * (double)floatValue12;
         } else {
            this.m = vec3d6.x;
            this.n = vec3d6.y;
            this.o = vec3d6.z;
         }

         this.k = livingEntity4;
      }

      this.l = this.l + ((flag11 ? 1.0F : 0.0F) - this.l) * (1.0F - (float)Math.exp((double)(-floatValue10 * (flag11 ? this.e : this.f))));
      LivingEntity livingEntity21 = this.k;
      if (livingEntity21 != null && !(this.l < 0.01F) && !livingEntity21.isRemoved()) {
         this.q = matrix4f1;
         this.r = vec3d2.x;
         this.s = vec3d2.y;
         this.t = vec3d2.z;
         this.y = this.d ? this.c : dev.leg1tka.api.utils.text.ColorUtils.k(0);
         this.z = this.d ? dev.leg1tka.api.utils.text.ColorUtils.d(this.c, -1, 0.35F) : dev.leg1tka.api.utils.text.ColorUtils.k(90);
         double doubleValue13 = MathHelper.lerp((double)floatValue3, livingEntity21.prevX, livingEntity21.getX()) + this.m;
         double doubleValue15 = MathHelper.lerp((double)floatValue3, livingEntity21.prevY, livingEntity21.getY()) + this.n;
         double doubleValue17 = MathHelper.lerp((double)floatValue3, livingEntity21.prevZ, livingEntity21.getZ()) + this.o;
         this.A = (float)(doubleValue15 - this.s);
         this.B = this.A + livingEntity21.getHeight();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableCull();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
         BufferBuilder bufferBuilder19 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
         if (flag7 && livingEntity21 instanceof PlayerEntity) {
            this.a(bufferBuilder19, livingEntity21, floatValue3, doubleValue13, doubleValue15, doubleValue17);
         } else {
            this.a(bufferBuilder19, livingEntity21, doubleValue13, doubleValue15, doubleValue17);
         }

         BuiltBuffer builtBuffer20 = bufferBuilder19.endNullable();
         if (builtBuffer20 != null) {
            BufferRenderer.drawWithGlobalProgram(builtBuffer20);
         }

         RenderSystem.enableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
      } else {
         if (!flag11 && this.l < 0.01F) {
            this.k = null;
         }
      }
   }

   public void a() {
      this.k = null;
      this.l = 0.0F;
      this.p = 0L;
   }

   private void a(BufferBuilder bufferBuilder1, LivingEntity livingEntity2, double doubleValue3, double doubleValue5, double doubleValue7) {
      Box box9 = livingEntity2.getBoundingBox();
      float floatValue10 = (float)((box9.maxX - box9.minX) * 0.5);
      float floatValue11 = (float)((box9.maxZ - box9.minZ) * 0.5);
      float floatValue12 = (float)(doubleValue3 - this.r) - floatValue10;
      float floatValue13 = (float)(doubleValue3 - this.r) + floatValue10;
      float floatValue14 = (float)(doubleValue7 - this.t) - floatValue11;
      float floatValue15 = (float)(doubleValue7 - this.t) + floatValue11;
      float floatValue16 = (float)(doubleValue5 - this.s);
      float floatValue17 = floatValue16 + (float)(box9.maxY - box9.minY);
      float floatValue18 = this.l * 0.16F;
      this.a(bufferBuilder1, floatValue12, floatValue16, floatValue14, floatValue13, floatValue16, floatValue14, floatValue13, floatValue17, floatValue14, floatValue12, floatValue17, floatValue14, floatValue18);
      this.a(bufferBuilder1, floatValue13, floatValue16, floatValue15, floatValue12, floatValue16, floatValue15, floatValue12, floatValue17, floatValue15, floatValue13, floatValue17, floatValue15, floatValue18);
      this.a(bufferBuilder1, floatValue12, floatValue16, floatValue15, floatValue12, floatValue16, floatValue14, floatValue12, floatValue17, floatValue14, floatValue12, floatValue17, floatValue15, floatValue18);
      this.a(bufferBuilder1, floatValue13, floatValue16, floatValue14, floatValue13, floatValue16, floatValue15, floatValue13, floatValue17, floatValue15, floatValue13, floatValue17, floatValue14, floatValue18);
      this.a(bufferBuilder1, floatValue12, floatValue17, floatValue14, floatValue13, floatValue17, floatValue14, floatValue13, floatValue17, floatValue15, floatValue12, floatValue17, floatValue15, floatValue18);
      this.a(bufferBuilder1, floatValue12, floatValue16, floatValue15, floatValue13, floatValue16, floatValue15, floatValue13, floatValue16, floatValue14, floatValue12, floatValue16, floatValue14, floatValue18);

      for (int intValue19 = 0; intValue19 < 2; intValue19++) {
         float floatValue20 = intValue19 == 0 ? 0.075F : 0.022F;
         float floatValue21 = this.l * (intValue19 == 0 ? 0.22F : 0.95F);
         this.a(bufferBuilder1, floatValue12, floatValue16, floatValue14, floatValue13, floatValue16, floatValue14, floatValue20, floatValue21);
         this.a(bufferBuilder1, floatValue13, floatValue16, floatValue14, floatValue13, floatValue16, floatValue15, floatValue20, floatValue21);
         this.a(bufferBuilder1, floatValue13, floatValue16, floatValue15, floatValue12, floatValue16, floatValue15, floatValue20, floatValue21);
         this.a(bufferBuilder1, floatValue12, floatValue16, floatValue15, floatValue12, floatValue16, floatValue14, floatValue20, floatValue21);
         this.a(bufferBuilder1, floatValue12, floatValue17, floatValue14, floatValue13, floatValue17, floatValue14, floatValue20, floatValue21);
         this.a(bufferBuilder1, floatValue13, floatValue17, floatValue14, floatValue13, floatValue17, floatValue15, floatValue20, floatValue21);
         this.a(bufferBuilder1, floatValue13, floatValue17, floatValue15, floatValue12, floatValue17, floatValue15, floatValue20, floatValue21);
         this.a(bufferBuilder1, floatValue12, floatValue17, floatValue15, floatValue12, floatValue17, floatValue14, floatValue20, floatValue21);
         this.a(bufferBuilder1, floatValue12, floatValue16, floatValue14, floatValue12, floatValue17, floatValue14, floatValue20, floatValue21);
         this.a(bufferBuilder1, floatValue13, floatValue16, floatValue14, floatValue13, floatValue17, floatValue14, floatValue20, floatValue21);
         this.a(bufferBuilder1, floatValue13, floatValue16, floatValue15, floatValue13, floatValue17, floatValue15, floatValue20, floatValue21);
         this.a(bufferBuilder1, floatValue12, floatValue16, floatValue15, floatValue12, floatValue17, floatValue15, floatValue20, floatValue21);
      }
   }

   private void a(BufferBuilder bufferBuilder1, LivingEntity livingEntity2, float floatValue3, double doubleValue4, double doubleValue6, double doubleValue8) {
      float floatValue10 = MathHelper.lerpAngleDegrees(floatValue3, livingEntity2.prevBodyYaw, livingEntity2.bodyYaw);
      float floatValue11 = MathHelper.wrapDegrees(MathHelper.lerpAngleDegrees(floatValue3, livingEntity2.prevHeadYaw, livingEntity2.headYaw) - floatValue10);
      float floatValue12 = livingEntity2.getLerpedPitch(floatValue3) * (float) (Math.PI / 180.0);
      float floatValue13 = livingEntity2.limbAnimator.getPos(floatValue3);
      float floatValue14 = Math.min(1.0F, livingEntity2.limbAnimator.getSpeed(floatValue3));
      float floatValue15 = (float)livingEntity2.age + floatValue3;
      float floatValue16 = livingEntity2.getHandSwingProgress(floatValue3);
      boolean flag17 = livingEntity2.isInSneakingPose();
      Arm arm18 = livingEntity2.preferredHand == Hand.MAIN_HAND ? livingEntity2.getMainArm() : livingEntity2.getMainArm().getOpposite();
      float floatValue19 = 0.0F;
      float floatValue20 = 0.0F;
      float floatValue21 = 0.0F;
      float floatValue22 = 0.0F;
      float floatValue23 = -5.0F;
      float floatValue24 = 2.0F;
      float floatValue25 = 0.0F;
      float floatValue26 = 5.0F;
      float floatValue27 = 2.0F;
      float floatValue28 = 0.0F;
      float floatValue29 = 12.0F;
      float floatValue30 = 0.0F;
      float floatValue31 = MathHelper.cos(floatValue13 * 0.6662F + (float) Math.PI) * floatValue14;
      float floatValue32 = MathHelper.cos(floatValue13 * 0.6662F) * floatValue14;
      float floatValue33 = MathHelper.cos(floatValue13 * 0.6662F) * 1.4F * floatValue14;
      float floatValue34 = MathHelper.cos(floatValue13 * 0.6662F + (float) Math.PI) * 1.4F * floatValue14;
      float floatValue35 = 0.0F;
      float floatValue36 = 0.0F;
      float floatValue37 = 0.0F;
      float floatValue38 = 0.0F;
      if (livingEntity2.hasVehicle()) {
         floatValue31 -= (float) (Math.PI / 5);
         floatValue32 -= (float) (Math.PI / 5);
         floatValue33 = -1.4137167F;
         floatValue34 = -1.4137167F;
      }

      if (floatValue16 > 0.0F) {
         floatValue22 = MathHelper.sin(MathHelper.sqrt(floatValue16) * (float) (Math.PI * 2)) * 0.2F * (arm18 == Arm.LEFT ? -1.0F : 1.0F);
         floatValue25 = MathHelper.sin(floatValue22) * 5.0F;
         floatValue23 = -MathHelper.cos(floatValue22) * 5.0F;
         floatValue28 = -MathHelper.sin(floatValue22) * 5.0F;
         floatValue26 = MathHelper.cos(floatValue22) * 5.0F;
         floatValue35 += floatValue22;
         floatValue36 += floatValue22;
         floatValue32 += floatValue22;
         float floatValue39 = 1.0F - floatValue16;
         floatValue39 *= floatValue39;
         floatValue39 *= floatValue39;
         floatValue39 = 1.0F - floatValue39;
         float floatValue40 = MathHelper.sin(floatValue39 * (float) Math.PI) * 1.2F + MathHelper.sin(floatValue16 * (float) Math.PI) * -(floatValue12 - 0.7F) * 0.75F;
         float floatValue41 = MathHelper.sin(floatValue16 * (float) Math.PI) * -0.4F;
         if (arm18 == Arm.RIGHT) {
            floatValue31 -= floatValue40;
            floatValue35 += floatValue22 * 2.0F;
            floatValue37 += floatValue41;
         } else {
            floatValue32 -= floatValue40;
            floatValue36 += floatValue22 * 2.0F;
            floatValue38 += floatValue41;
         }
      }

      if (flag17) {
         floatValue21 = 0.5F;
         floatValue31 += 0.4F;
         floatValue32 += 0.4F;
         floatValue30 = 4.0F;
         floatValue19 = 4.2F;
         floatValue20 = 3.2F;
         floatValue24 += 3.2F;
         floatValue27 += 3.2F;
      }

      floatValue37 += MathHelper.cos(floatValue15 * 0.09F) * 0.05F + 0.05F;
      floatValue31 += MathHelper.sin(floatValue15 * 0.067F) * 0.05F;
      floatValue38 -= MathHelper.cos(floatValue15 * 0.09F) * 0.05F + 0.05F;
      floatValue32 -= MathHelper.sin(floatValue15 * 0.067F) * 0.05F;
      this.u
         .identity()
         .translate((float)(doubleValue4 - this.r), (float)(doubleValue6 - this.s) - (flag17 ? 0.125F : 0.0F), (float)(doubleValue8 - this.t))
         .rotateY((180.0F - floatValue10) * (float) (Math.PI / 180.0))
         .scale(-0.9375F, -0.9375F, 0.9375F)
         .translate(0.0F, -1.501F, 0.0F)
         .scale(0.0625F);
      this.a(0, 0.0F, floatValue19, 0.0F, floatValue12, floatValue11 * (float) (Math.PI / 180.0), 0.0F, 0.0F, -4.0F, 0.0F);
      this.a(1, 0.0F, floatValue19, 0.0F, floatValue12, floatValue11 * (float) (Math.PI / 180.0), 0.0F, 0.0F, -8.0F, 0.0F);
      this.a(2, 0.0F, floatValue20, 0.0F, floatValue21, floatValue22, 0.0F, 0.0F, 0.0F, 0.0F);
      this.a(3, 0.0F, floatValue20, 0.0F, floatValue21, floatValue22, 0.0F, 0.0F, 12.0F, 0.0F);
      this.a(4, floatValue23, floatValue24, floatValue25, floatValue31, floatValue35, floatValue37, 0.0F, 0.0F, 0.0F);
      this.a(5, floatValue23, floatValue24, floatValue25, floatValue31, floatValue35, floatValue37, -1.0F, 5.0F, 0.0F);
      this.a(6, floatValue23, floatValue24, floatValue25, floatValue31, floatValue35, floatValue37, -1.0F, 10.0F, 0.0F);
      this.a(7, floatValue26, floatValue27, floatValue28, floatValue32, floatValue36, floatValue38, 0.0F, 0.0F, 0.0F);
      this.a(8, floatValue26, floatValue27, floatValue28, floatValue32, floatValue36, floatValue38, 1.0F, 5.0F, 0.0F);
      this.a(9, floatValue26, floatValue27, floatValue28, floatValue32, floatValue36, floatValue38, 1.0F, 10.0F, 0.0F);
      this.a(10, -1.9F, floatValue29, floatValue30, floatValue33, 0.005F, 0.005F, 0.0F, 0.0F, 0.0F);
      this.a(11, -1.9F, floatValue29, floatValue30, floatValue33, 0.005F, 0.005F, 0.0F, 6.0F, 0.0F);
      this.a(12, -1.9F, floatValue29, floatValue30, floatValue33, 0.005F, 0.005F, 0.0F, 12.0F, 0.0F);
      this.a(13, 1.9F, floatValue29, floatValue30, floatValue34, -0.005F, -0.005F, 0.0F, 0.0F, 0.0F);
      this.a(14, 1.9F, floatValue29, floatValue30, floatValue34, -0.005F, -0.005F, 0.0F, 6.0F, 0.0F);
      this.a(15, 1.9F, floatValue29, floatValue30, floatValue34, -0.005F, -0.005F, 0.0F, 12.0F, 0.0F);

      for (int intValue49 = 0; intValue49 < 2; intValue49++) {
         float floatValue51 = intValue49 == 0 ? 0.075F : 0.022F;
         float floatValue52 = this.l * (intValue49 == 0 ? 0.24F : 1.0F);
         this.a(bufferBuilder1, 2, 1, floatValue51, floatValue52);
         this.a(bufferBuilder1, 2, 3, floatValue51, floatValue52);
         this.a(bufferBuilder1, 2, 4, floatValue51, floatValue52);
         this.a(bufferBuilder1, 2, 7, floatValue51, floatValue52);
         this.a(bufferBuilder1, 4, 5, floatValue51, floatValue52);
         this.a(bufferBuilder1, 5, 6, floatValue51, floatValue52);
         this.a(bufferBuilder1, 7, 8, floatValue51, floatValue52);
         this.a(bufferBuilder1, 8, 9, floatValue51, floatValue52);
         this.a(bufferBuilder1, 3, 10, floatValue51, floatValue52);
         this.a(bufferBuilder1, 3, 13, floatValue51, floatValue52);
         this.a(bufferBuilder1, 10, 11, floatValue51, floatValue52);
         this.a(bufferBuilder1, 11, 12, floatValue51, floatValue52);
         this.a(bufferBuilder1, 13, 14, floatValue51, floatValue52);
         this.a(bufferBuilder1, 14, 15, floatValue51, floatValue52);
      }

      this.a(bufferBuilder1, 0, 0.117F, this.l * 0.35F);
      this.a(bufferBuilder1, 0, 0.072000004F, this.l * 0.9F);

      for (int intValue50 = 2; intValue50 < 16; intValue50++) {
         this.a(bufferBuilder1, intValue50, 0.081F, this.l * 0.25F);
         this.a(bufferBuilder1, intValue50, 0.045F, this.l);
      }
   }

   private void a(int intValue1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, float floatValue9, float floatValue10) {
      this.v.set(this.u).translate(floatValue2, floatValue3, floatValue4).rotateZYX(floatValue7, floatValue6, floatValue5);
      this.v.transformPosition(floatValue8, floatValue9, floatValue10, this.w);
      this.x[intValue1 * 3] = this.w.x;
      this.x[intValue1 * 3 + 1] = this.w.y;
      this.x[intValue1 * 3 + 2] = this.w.z;
   }

   private void a(BufferBuilder bufferBuilder1, int intValue2, int intValue3, float floatValue4, float floatValue5) {
      float[] floatValueArray6 = this.x;
      this.a(bufferBuilder1, floatValueArray6[intValue2 * 3], floatValueArray6[intValue2 * 3 + 1], floatValueArray6[intValue2 * 3 + 2], floatValueArray6[intValue3 * 3], floatValueArray6[intValue3 * 3 + 1], floatValueArray6[intValue3 * 3 + 2], floatValue4, floatValue5);
   }

   private void a(BufferBuilder bufferBuilder1, int intValue2, float floatValue3, float floatValue4) {
      float[] floatValueArray5 = this.x;
      float floatValue6 = floatValueArray5[intValue2 * 3];
      float floatValue7 = floatValueArray5[intValue2 * 3 + 1];
      float floatValue8 = floatValueArray5[intValue2 * 3 + 2];
      float floatValue9 = MathHelper.sqrt(floatValue6 * floatValue6 + floatValue7 * floatValue7 + floatValue8 * floatValue8);
      if (!(floatValue9 < 1.0E-4F)) {
         float floatValue10 = floatValue6 / floatValue9;
         float floatValue11 = floatValue7 / floatValue9;
         float floatValue12 = floatValue8 / floatValue9;
         float floatValue13 = -floatValue12;
         float floatValue14 = floatValue10;
         float floatValue15 = MathHelper.sqrt(floatValue13 * floatValue13 + floatValue10 * floatValue10);
         if (floatValue15 < 1.0E-4F) {
            floatValue13 = 1.0F;
            floatValue14 = 0.0F;
            floatValue15 = 1.0F;
         }

         floatValue13 /= floatValue15;
         floatValue14 /= floatValue15;
         float floatValue16 = -floatValue14 * floatValue11;
         float floatValue17 = floatValue14 * floatValue10 - floatValue13 * floatValue12;
         float floatValue18 = floatValue13 * floatValue11;
         int intValue19 = this.a(floatValue7);
         float floatValue20 = (float)(intValue19 >> 16 & 0xFF) / 255.0F;
         float floatValue21 = (float)(intValue19 >> 8 & 0xFF) / 255.0F;
         float floatValue22 = (float)(intValue19 & 0xFF) / 255.0F;
         float floatValue23 = floatValue20 + (1.0F - floatValue20) * 0.45F;
         float floatValue24 = floatValue21 + (1.0F - floatValue21) * 0.45F;
         float floatValue25 = floatValue22 + (1.0F - floatValue22) * 0.45F;

         for (int intValue26 = 0; intValue26 < 10; intValue26++) {
            float floatValue27 = (float)intValue26 * (float) (Math.PI * 2) / 10.0F;
            float floatValue28 = (float)(intValue26 + 1) * (float) (Math.PI * 2) / 10.0F;
            float floatValue29 = MathHelper.sin(floatValue27) * floatValue3;
            float floatValue30 = MathHelper.cos(floatValue27) * floatValue3;
            float floatValue31 = MathHelper.sin(floatValue28) * floatValue3;
            float floatValue32 = MathHelper.cos(floatValue28) * floatValue3;
            bufferBuilder1.vertex(this.q, floatValue6, floatValue7, floatValue8).color(floatValue23, floatValue24, floatValue25, floatValue4);
            bufferBuilder1.vertex(this.q, floatValue6 + floatValue13 * floatValue30 + floatValue16 * floatValue29, floatValue7 + floatValue17 * floatValue29, floatValue8 + floatValue14 * floatValue30 + floatValue18 * floatValue29)
               .color(floatValue20, floatValue21, floatValue22, floatValue4);
            bufferBuilder1.vertex(this.q, floatValue6 + floatValue13 * floatValue32 + floatValue16 * floatValue31, floatValue7 + floatValue17 * floatValue31, floatValue8 + floatValue14 * floatValue32 + floatValue18 * floatValue31)
               .color(floatValue20, floatValue21, floatValue22, floatValue4);
            bufferBuilder1.vertex(this.q, floatValue6, floatValue7, floatValue8).color(floatValue23, floatValue24, floatValue25, floatValue4);
         }
      }
   }

   private void a(BufferBuilder bufferBuilder1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, float floatValue9) {
      float floatValue10 = floatValue5 - floatValue2;
      float floatValue11 = floatValue6 - floatValue3;
      float floatValue12 = floatValue7 - floatValue4;
      float floatValue13 = (floatValue2 + floatValue5) * 0.5F;
      float floatValue14 = (floatValue3 + floatValue6) * 0.5F;
      float floatValue15 = (floatValue4 + floatValue7) * 0.5F;
      float floatValue16 = floatValue11 * -floatValue15 - floatValue12 * -floatValue14;
      float floatValue17 = floatValue12 * -floatValue13 - floatValue10 * -floatValue15;
      float floatValue18 = floatValue10 * -floatValue14 - floatValue11 * -floatValue13;
      float floatValue19 = MathHelper.sqrt(floatValue16 * floatValue16 + floatValue17 * floatValue17 + floatValue18 * floatValue18);
      if (!(floatValue19 < 1.0E-6F)) {
         float floatValue20 = floatValue8 * 0.5F / floatValue19;
         floatValue16 *= floatValue20;
         floatValue17 *= floatValue20;
         floatValue18 *= floatValue20;
         int intValue21 = this.a(floatValue3);
         int intValue22 = this.a(floatValue6);
         float floatValue23 = (float)(intValue21 >> 16 & 0xFF) / 255.0F;
         float floatValue24 = (float)(intValue21 >> 8 & 0xFF) / 255.0F;
         float floatValue25 = (float)(intValue21 & 0xFF) / 255.0F;
         float floatValue26 = (float)(intValue22 >> 16 & 0xFF) / 255.0F;
         float floatValue27 = (float)(intValue22 >> 8 & 0xFF) / 255.0F;
         float floatValue28 = (float)(intValue22 & 0xFF) / 255.0F;
         bufferBuilder1.vertex(this.q, floatValue2 - floatValue16, floatValue3 - floatValue17, floatValue4 - floatValue18).color(floatValue23, floatValue24, floatValue25, floatValue9);
         bufferBuilder1.vertex(this.q, floatValue2 + floatValue16, floatValue3 + floatValue17, floatValue4 + floatValue18).color(floatValue23, floatValue24, floatValue25, floatValue9);
         bufferBuilder1.vertex(this.q, floatValue5 + floatValue16, floatValue6 + floatValue17, floatValue7 + floatValue18).color(floatValue26, floatValue27, floatValue28, floatValue9);
         bufferBuilder1.vertex(this.q, floatValue5 - floatValue16, floatValue6 - floatValue17, floatValue7 - floatValue18).color(floatValue26, floatValue27, floatValue28, floatValue9);
      }
   }

   private void a(
      BufferBuilder bufferBuilder1,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      float floatValue10,
      float floatValue11,
      float floatValue12,
      float floatValue13,
      float floatValue14
   ) {
      int intValue15 = this.a(floatValue3);
      int intValue16 = this.a(floatValue9);
      bufferBuilder1.vertex(this.q, floatValue2, floatValue3, floatValue4)
         .color((float)(intValue15 >> 16 & 0xFF) / 255.0F, (float)(intValue15 >> 8 & 0xFF) / 255.0F, (float)(intValue15 & 0xFF) / 255.0F, floatValue14);
      bufferBuilder1.vertex(this.q, floatValue5, floatValue6, floatValue7)
         .color((float)(intValue15 >> 16 & 0xFF) / 255.0F, (float)(intValue15 >> 8 & 0xFF) / 255.0F, (float)(intValue15 & 0xFF) / 255.0F, floatValue14);
      bufferBuilder1.vertex(this.q, floatValue8, floatValue9, floatValue10)
         .color((float)(intValue16 >> 16 & 0xFF) / 255.0F, (float)(intValue16 >> 8 & 0xFF) / 255.0F, (float)(intValue16 & 0xFF) / 255.0F, floatValue14);
      bufferBuilder1.vertex(this.q, floatValue11, floatValue12, floatValue13)
         .color((float)(intValue16 >> 16 & 0xFF) / 255.0F, (float)(intValue16 >> 8 & 0xFF) / 255.0F, (float)(intValue16 & 0xFF) / 255.0F, floatValue14);
   }

   private int a(float floatValue1) {
      float floatValue2 = this.B - this.A < 1.0E-4F ? 0.0F : MathHelper.clamp((floatValue1 - this.A) / (this.B - this.A), 0.0F, 1.0F);
      return dev.leg1tka.api.utils.text.ColorUtils.d(this.y, this.z, floatValue2);
   }
}
