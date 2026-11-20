package dev.leg1tka.modules.render;


import dev.leg1tka.modules.Module;
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
import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.Event3DRender;
import dev.leg1tka.api.events.implement.EventRender;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class BlockOverlay extends Module {
   public static BlockOverlay c = new BlockOverlay();
   private final ModeSetting d = new ModeSetting(
      "Режим", "Шейдер", "Шейдер", "Нитки"
   );
   private final NumberSetting e = new NumberSetting("Скорость волн", 1.2F, 0.1F, 5.0F, 0.1F)
      .a(() -> this.d.b("Шейдер"));
   private final NumberSetting f = new NumberSetting("Частота волн", 1.0F, 1.0F, 3.0F, 0.1F)
      .a(() -> this.d.b("Шейдер"));
   private final NumberSetting g = new NumberSetting("Скорость нитей", 1.4F, 0.1F, 5.0F, 0.1F)
      .a(() -> this.d.b("Нитки"));
   private final NumberSetting h = new NumberSetting("Изгиб нитей", 0.55F, 0.0F, 1.5F, 0.01F)
      .a(() -> this.d.b("Нитки"));
   private final NumberSetting i = new NumberSetting("Ширина обводки", 1.1F, 0.1F, 5.0F, 0.1F);
   private final NumberSetting j = new NumberSetting("Сила свечения", 1.0F, 0.0F, 5.0F, 0.1F);
   private final NumberSetting k = new NumberSetting("Заливка", 0.6F, 0.0F, 1.0F, 0.01F);
   private final NumberSetting l = new NumberSetting("Прозрачность", 1.0F, 0.0F, 1.0F, 0.01F);
   private final NumberSetting m = new NumberSetting("Плавность", 0.24F, 0.05F, 0.6F, 0.01F);
   private Framebuffer n;
   private int o = -1;
   private int p = -1;
   private boolean q;
   private BlockPos r;
   private Box s;
   private Box t;
   private int u = -1;
   private int v = -1;

   public BlockOverlay() {
      super("BlockOverlay", "Block overlay shader", Category.RENDER);
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m});
   }

   @Override
   public void onDisable() {
      this.q = false;
      this.r = null;
      this.s = null;
      this.t = null;
      super.onDisable();
   }

   @EventLink(
      priority = -100
   )
   public void onRender3D(Event3DRender event3DRender1) {
      if (minecraftClient != null && minecraftClient.world != null && minecraftClient.player != null) {
         Box box2 = this.q();
         if (box2 == null) {
            this.q = false;
            this.r = null;
            this.s = null;
            this.t = null;
         } else {
            if (this.s != null && this.t != null && this.r != null) {
               this.t = box2;
               this.s = this.a(this.s, this.t, this.m.f());
            } else {
               this.s = box2;
               this.t = box2;
            }

            this.r = BlockPos.ofFloored(box2.minX, box2.minY, box2.minZ);
            this.s();
            Vec3d vec3d3 = event3DRender1.getCamera().getPos();
            Box box4 = this.s.offset(-vec3d3.x, -vec3d3.y, -vec3d3.z);
            Matrix4f matrix4f5 = event3DRender1.getMatrices().peek().getPositionMatrix();
            this.p();
            if (this.n != null) {
               this.q = true;
               this.n.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
               this.n.clear();
               this.r();
               this.n.beginWrite(false);
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               RenderSystem.disableCull();
               RenderSystem.enableDepthTest();
               RenderSystem.depthMask(false);
               RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
               this.b(matrix4f5, box4);
               RenderSystem.depthMask(true);
               RenderSystem.disableDepthTest();
               RenderSystem.enableCull();
               RenderSystem.disableBlend();
               minecraftClient.getFramebuffer().beginWrite(false);
               if (this.d.b("Нитки")) {
                  this.a(matrix4f5, box4);
               }
            }
         }
      }
   }

   @EventLink(
      priority = 200
   )
   public void onRender2D(EventRender.Default default1) {
      if (this.q && this.n != null) {
         if (!this.d.b("Нитки")) {
            ShaderProgram shaderProgram2 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.w);
            if (shaderProgram2 != null) {
               boolean flag3 = this.d.b("Нитки");
               int intValue4 = this.u;
               int intValue5 = this.v;
               minecraftClient.getFramebuffer().beginWrite(false);
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               RenderSystem.enableDepthTest();
               RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.w);
               RenderSystem.setShaderTexture(0, this.n.getColorAttachment());
               this.a(
                  shaderProgram2,
                  "texelSize",
                  1.0F / (float)Math.max(1, minecraftClient.getWindow().getFramebufferWidth()),
                  1.0F / (float)Math.max(1, minecraftClient.getWindow().getFramebufferHeight())
               );
               this.a(shaderProgram2, "color", dev.leg1tka.api.utils.text.ColorUtils.c(intValue4), dev.leg1tka.api.utils.text.ColorUtils.d(intValue4), dev.leg1tka.api.utils.text.ColorUtils.e(intValue4));
               this.a(shaderProgram2, "color2", dev.leg1tka.api.utils.text.ColorUtils.c(intValue5), dev.leg1tka.api.utils.text.ColorUtils.d(intValue5), dev.leg1tka.api.utils.text.ColorUtils.e(intValue5));
               this.a(shaderProgram2, "time", (float)(System.currentTimeMillis() % 100000L) / 1000.0F);
               this.a(shaderProgram2, "speed", this.e.f());
               this.a(shaderProgram2, "scale", this.f.f());
               this.a(shaderProgram2, "outline", this.i.f());
               this.a(shaderProgram2, "glow", flag3 ? 0.0F : this.j.f());
               this.a(shaderProgram2, "fill", flag3 ? 0.0F : this.k.f());
               this.a(shaderProgram2, "alpha", flag3 ? 1.0F : this.l.f());
               this.a(shaderProgram2, "outlineOnly", flag3 ? 1.0F : 0.0F);
               this.t();
               RenderSystem.enableDepthTest();
               RenderSystem.disableBlend();
               RenderSystem.defaultBlendFunc();
               RenderSystem.setShaderTexture(0, 0);
            }
         }
      }
   }

   private void a(ShaderProgram shaderProgram1, String string2, float floatValue3) {
      GlUniform glUniform4 = shaderProgram1.getUniform(string2);
      if (glUniform4 != null) {
         glUniform4.set(floatValue3);
      }
   }

   private void a(ShaderProgram shaderProgram1, String string2, float floatValue3, float floatValue4) {
      GlUniform glUniform5 = shaderProgram1.getUniform(string2);
      if (glUniform5 != null) {
         glUniform5.set(floatValue3, floatValue4);
      }
   }

   private void a(ShaderProgram shaderProgram1, String string2, float floatValue3, float floatValue4, float floatValue5) {
      GlUniform glUniform6 = shaderProgram1.getUniform(string2);
      if (glUniform6 != null) {
         glUniform6.set(floatValue3, floatValue4, floatValue5);
      }
   }

   private void p() {
      int intValue1 = minecraftClient.getWindow().getFramebufferWidth();
      int intValue2 = minecraftClient.getWindow().getFramebufferHeight();
      if (this.n == null || this.o != intValue1 || this.p != intValue2) {
         if (this.n != null) {
            this.n.delete();
         }

         this.n = new SimpleFramebuffer(intValue1, intValue2, true);
         this.o = intValue1;
         this.p = intValue2;
      }
   }

   private Box q() {
      HitResult hitResult1 = minecraftClient.crosshairTarget;
      if (hitResult1 instanceof BlockHitResult blockHitResult2 && hitResult1.getType() == Type.BLOCK) {
         BlockPos blockPos3 = blockHitResult2.getBlockPos();
         if (blockPos3 == null) {
            return null;
         }

         if (minecraftClient.world.getBlockState(blockPos3).isAir()) {
            return null;
         }

         VoxelShape voxelShape4 = minecraftClient.world.getBlockState(blockPos3).getOutlineShape(minecraftClient.world, blockPos3);
         Box box5 = voxelShape4.isEmpty() ? new Box(blockPos3) : voxelShape4.getBoundingBox().offset(blockPos3);
         return box5.expand(0.002);
      }

      return null;
   }

   private Box a(Box box1, Box box2, float floatValue3) {
      return new Box(
         box1.minX + (box2.minX - box1.minX) * (double)floatValue3,
         box1.minY + (box2.minY - box1.minY) * (double)floatValue3,
         box1.minZ + (box2.minZ - box1.minZ) * (double)floatValue3,
         box1.maxX + (box2.maxX - box1.maxX) * (double)floatValue3,
         box1.maxY + (box2.maxY - box1.maxY) * (double)floatValue3,
         box1.maxZ + (box2.maxZ - box1.maxZ) * (double)floatValue3
      );
   }

   private void a(Matrix4f matrix4f1, Box box2) {
      byte byteValue3 = 5;
      byte byteValue4 = 18;
      float floatValue5 = (float)(System.currentTimeMillis() % 100000L) / 1000.0F * this.g.f();
      float floatValue6 = 0.0025F;
      float floatValue7 = 0.06F + this.h.f() * 0.2F;
      int intValue8 = Math.max(20, Math.min(255, (int)(this.l.f() * 210.0F)));
      int intValue9 = this.u;
      long longValue10 = this.r != null ? this.r.asLong() : 1L;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      RenderSystem.enableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      this.a(matrix4f1, box2, dev.leg1tka.api.utils.text.ColorUtils.d(intValue9, (int)(this.l.f() * this.k.f() * 170.0F)));

      for (int intValue12 = 0; intValue12 < 6; intValue12++) {
         int[] intValueArray13 = this.b(intValue12);

         for (int intValue14 = 0; intValue14 < byteValue3; intValue14++) {
            int intValue15 = intValue12 * 1000 + intValue14 * 53;
            int intValue16 = intValueArray13[intValue14 % intValueArray13.length];
            double doubleValue17 = (double)floatValue5 * (0.95 + this.a(longValue10, intValue15 + 1) * 0.55) + (double)intValue14 * 0.83 + (double)intValue12 * 1.11;
            double doubleValue19 = this.a(0.5 + Math.sin(doubleValue17 * 1.37 + this.a(longValue10, intValue15 + 2) * 6.2831853) * 0.38);
            Vec3d vec3d21 = this.a(box2, intValue12, intValue16, doubleValue19, 0.0015);
            Vec3d vec3d22 = this.a(
               box2, intValue12, this.a(0.5 + (this.a(longValue10, intValue15 + 3) - 0.5) * 0.46), this.a(0.5 + (this.a(longValue10, intValue15 + 4) - 0.5) * 0.46), 0.0015
            );
            Vec3d vec3d23 = this.a(
               box2, intValue16, this.a(0.5 + (this.a(longValue10, intValue15 + 5) - 0.5) * 0.46), this.a(0.5 + (this.a(longValue10, intValue15 + 6) - 0.5) * 0.46), 0.0015
            );
            Vec3d[] vec3dArray24 = this.c(intValue12);
            Vec3d[] vec3dArray25 = this.c(intValue16);
            Vec3d vec3d26 = this.d(intValue12);
            Vec3d vec3d27 = this.d(intValue16);
            double doubleValue28 = (double)floatValue7 * (0.7 + this.a(longValue10, intValue15 + 7)) * Math.sin(doubleValue17 * 1.9 + this.a(longValue10, intValue15 + 8) * 6.2831853);
            double doubleValue30 = (double)floatValue7 * (0.7 + this.a(longValue10, intValue15 + 9)) * Math.cos(doubleValue17 * 1.7 + this.a(longValue10, intValue15 + 10) * 6.2831853);
            Vec3d vec3d32 = vec3d21.subtract(vec3d22);
            Vec3d vec3d33 = vec3d22.add(vec3d32.multiply(0.38)).add(vec3dArray24[0].multiply(doubleValue28)).add(vec3dArray24[1].multiply(-doubleValue28 * 0.55));
            Vec3d vec3d34 = vec3d22.add(vec3d32.multiply(0.76)).add(vec3dArray24[0].multiply(-doubleValue28 * 0.65)).add(vec3dArray24[1].multiply(doubleValue28 * 0.4));
            Vec3d vec3d35 = vec3d23.subtract(vec3d21);
            Vec3d vec3d36 = vec3d21.add(vec3d35.multiply(0.24)).add(vec3dArray25[0].multiply(doubleValue30)).add(vec3dArray25[1].multiply(doubleValue30 * 0.45));
            Vec3d vec3d37 = vec3d21.add(vec3d35.multiply(0.62)).add(vec3dArray25[0].multiply(-doubleValue30 * 0.7)).add(vec3dArray25[1].multiply(-doubleValue30 * 0.35));
            int intValue38 = Math.max(18, Math.min(255, (int)((double)intValue8 * (0.74 + 0.26 * Math.sin(doubleValue17 * 2.6)))));
            int intValue39 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue9, intValue38);
            this.a(matrix4f1, vec3d22, vec3d33, vec3d34, vec3d21, vec3d26, byteValue4, intValue39, floatValue6);
            this.a(matrix4f1, vec3d21, vec3d36, vec3d37, vec3d23, vec3d27, byteValue4, intValue39, floatValue6);
         }
      }

      RenderSystem.enableDepthTest();
      RenderSystem.depthMask(true);
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
   }

   private void r() {
      if (this.n != null) {
         int intValue1 = GL11.glGetInteger(36010);
         int intValue2 = GL11.glGetInteger(36006);
         int intValue3 = minecraftClient.getWindow().getFramebufferWidth();
         int intValue4 = minecraftClient.getWindow().getFramebufferHeight();
         GL30.glBindFramebuffer(36008, minecraftClient.getFramebuffer().fbo);
         GL30.glBindFramebuffer(36009, this.n.fbo);
         GL30.glBlitFramebuffer(0, 0, intValue3, intValue4, 0, 0, intValue3, intValue4, 256, 9728);
         GL30.glBindFramebuffer(36008, intValue1);
         GL30.glBindFramebuffer(36009, intValue2);
      }
   }

   private Vec3d a(Vec3d vec3d1, Vec3d vec3d2, Vec3d vec3d3, Vec3d vec3d4, float floatValue5) {
      double doubleValue6 = 1.0 - (double)floatValue5;
      double doubleValue8 = doubleValue6 * doubleValue6;
      double doubleValue10 = (double)(floatValue5 * floatValue5);
      return vec3d1.multiply(doubleValue8 * doubleValue6)
         .add(vec3d2.multiply(3.0 * doubleValue8 * (double)floatValue5))
         .add(vec3d3.multiply(3.0 * doubleValue6 * doubleValue10))
         .add(vec3d4.multiply(doubleValue10 * (double)floatValue5));
   }

   private void a(Matrix4f matrix4f1, Vec3d vec3d2, Vec3d vec3d3, Vec3d vec3d4, Vec3d vec3d5, Vec3d vec3d6, int intValue7, int intValue8, float floatValue9) {
      Vec3d[] vec3dArray10 = new Vec3d[intValue7 + 1];

      for (int intValue11 = 0; intValue11 <= intValue7; intValue11++) {
         float floatValue12 = (float)intValue11 / (float)intValue7;
         vec3dArray10[intValue11] = this.a(vec3d2, vec3d3, vec3d4, vec3d5, floatValue12);
      }

      BufferBuilder bufferBuilder21 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);

      for (int intValue22 = 0; intValue22 < intValue7; intValue22++) {
         Vec3d vec3d13 = vec3dArray10[intValue22];
         Vec3d vec3d14 = vec3dArray10[intValue22 + 1];
         Vec3d vec3d15 = vec3d14.subtract(vec3d13);
         if (!(vec3d15.lengthSquared() < 1.0E-6)) {
            Vec3d vec3d16 = vec3d6.crossProduct(vec3d15).normalize().multiply((double)floatValue9);
            Vec3d vec3d17 = vec3d13.add(vec3d16);
            Vec3d vec3d18 = vec3d13.subtract(vec3d16);
            Vec3d vec3d19 = vec3d14.add(vec3d16);
            Vec3d vec3d20 = vec3d14.subtract(vec3d16);
            bufferBuilder21.vertex(matrix4f1, (float)vec3d17.x, (float)vec3d17.y, (float)vec3d17.z).color(intValue8);
            bufferBuilder21.vertex(matrix4f1, (float)vec3d18.x, (float)vec3d18.y, (float)vec3d18.z).color(intValue8);
            bufferBuilder21.vertex(matrix4f1, (float)vec3d20.x, (float)vec3d20.y, (float)vec3d20.z).color(intValue8);
            bufferBuilder21.vertex(matrix4f1, (float)vec3d19.x, (float)vec3d19.y, (float)vec3d19.z).color(intValue8);
         }
      }

      BufferRenderer.drawWithGlobalProgram(bufferBuilder21.end());
   }

   private void s() {
      if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null && AspectDLC.INSTANCE.themeStorage.b() != null) {
         dev.leg1tka.gui.themes.ThemePalette value1 = AspectDLC.INSTANCE.themeStorage.b().a();
         if (value1 == null) {
            this.u = dev.leg1tka.api.utils.text.ColorUtils.k(0);
            this.v = dev.leg1tka.api.utils.text.ColorUtils.k(180);
         } else {
            if (!"Rainbow".equals(value1.b())) {
               int intValue2 = value1.c != null && value1.c.length > 0 ? value1.c[0] : dev.leg1tka.api.utils.text.ColorUtils.k(0);
               this.u = intValue2;
               this.v = intValue2;
            } else {
               this.u = dev.leg1tka.api.utils.text.ColorUtils.a();
               this.v = dev.leg1tka.api.utils.text.ColorUtils.k(180);
            }
         }
      } else {
         this.u = dev.leg1tka.api.utils.text.ColorUtils.k(0);
         this.v = dev.leg1tka.api.utils.text.ColorUtils.k(180);
      }
   }

   private int[] b(int intValue1) {
      return switch (intValue1) {
         case 0, 1 -> new int[]{2, 3, 4, 5};
         case 2, 3 -> new int[]{0, 1, 4, 5};
         default -> new int[]{0, 1, 2, 3};
      };
   }

   private Vec3d[] c(int intValue1) {
      return switch (intValue1) {
         case 0, 1 -> new Vec3d[]{new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 0.0, 1.0)};
         case 2, 3 -> new Vec3d[]{new Vec3d(1.0, 0.0, 0.0), new Vec3d(0.0, 1.0, 0.0)};
         default -> new Vec3d[]{new Vec3d(0.0, 0.0, 1.0), new Vec3d(0.0, 1.0, 0.0)};
      };
   }

   private Vec3d d(int intValue1) {
      return switch (intValue1) {
         case 0 -> new Vec3d(0.0, 1.0, 0.0);
         case 1 -> new Vec3d(0.0, -1.0, 0.0);
         case 2 -> new Vec3d(0.0, 0.0, -1.0);
         case 3 -> new Vec3d(0.0, 0.0, 1.0);
         case 4 -> new Vec3d(-1.0, 0.0, 0.0);
         default -> new Vec3d(1.0, 0.0, 0.0);
      };
   }

   private Vec3d a(Box box1, int intValue2, int intValue3, double doubleValue4, double doubleValue6) {
      double doubleValue8 = Double.NaN;
      double doubleValue10 = Double.NaN;
      double doubleValue12 = Double.NaN;
      double[] doubleValueArray14 = this.a(box1, intValue2, doubleValue6);
      if (!Double.isNaN(doubleValueArray14[0])) {
         doubleValue8 = doubleValueArray14[0];
      }

      if (!Double.isNaN(doubleValueArray14[1])) {
         doubleValue10 = doubleValueArray14[1];
      }

      if (!Double.isNaN(doubleValueArray14[2])) {
         doubleValue12 = doubleValueArray14[2];
      }

      double[] doubleValueArray15 = this.a(box1, intValue3, doubleValue6);
      if (!Double.isNaN(doubleValueArray15[0])) {
         doubleValue8 = doubleValueArray15[0];
      }

      if (!Double.isNaN(doubleValueArray15[1])) {
         doubleValue10 = doubleValueArray15[1];
      }

      if (!Double.isNaN(doubleValueArray15[2])) {
         doubleValue12 = doubleValueArray15[2];
      }

      double doubleValue16 = this.a(doubleValue4);
      if (Double.isNaN(doubleValue8)) {
         doubleValue8 = this.a(box1.minX, box1.maxX, doubleValue16);
      }

      if (Double.isNaN(doubleValue10)) {
         doubleValue10 = this.a(box1.minY, box1.maxY, doubleValue16);
      }

      if (Double.isNaN(doubleValue12)) {
         doubleValue12 = this.a(box1.minZ, box1.maxZ, doubleValue16);
      }

      return new Vec3d(doubleValue8, doubleValue10, doubleValue12);
   }

   private double[] a(Box box1, int intValue2, double doubleValue3) {
      return switch (intValue2) {
         case 0 -> new double[]{Double.NaN, box1.maxY - doubleValue3, Double.NaN};
         case 1 -> new double[]{Double.NaN, box1.minY + doubleValue3, Double.NaN};
         case 2 -> new double[]{Double.NaN, Double.NaN, box1.minZ + doubleValue3};
         case 3 -> new double[]{Double.NaN, Double.NaN, box1.maxZ - doubleValue3};
         case 4 -> new double[]{box1.minX + doubleValue3, Double.NaN, Double.NaN};
         default -> new double[]{box1.maxX - doubleValue3, Double.NaN, Double.NaN};
      };
   }

   private Vec3d a(Box box1, int intValue2, double doubleValue3, double doubleValue5, double doubleValue7) {
      doubleValue3 = this.a(doubleValue3);
      doubleValue5 = this.a(doubleValue5);

      return switch (intValue2) {
         case 0 -> new Vec3d(this.a(box1.minX, box1.maxX, doubleValue3), box1.maxY - doubleValue7, this.a(box1.minZ, box1.maxZ, doubleValue5));
         case 1 -> new Vec3d(this.a(box1.minX, box1.maxX, doubleValue3), box1.minY + doubleValue7, this.a(box1.minZ, box1.maxZ, doubleValue5));
         case 2 -> new Vec3d(this.a(box1.minX, box1.maxX, doubleValue3), this.a(box1.minY, box1.maxY, doubleValue5), box1.minZ + doubleValue7);
         case 3 -> new Vec3d(this.a(box1.minX, box1.maxX, doubleValue3), this.a(box1.minY, box1.maxY, doubleValue5), box1.maxZ - doubleValue7);
         case 4 -> new Vec3d(box1.minX + doubleValue7, this.a(box1.minY, box1.maxY, doubleValue5), this.a(box1.minZ, box1.maxZ, doubleValue3));
         default -> new Vec3d(box1.maxX - doubleValue7, this.a(box1.minY, box1.maxY, doubleValue5), this.a(box1.minZ, box1.maxZ, doubleValue3));
      };
   }

   private double a(long longValue1, int intValue3) {
      long longValue4 = longValue1 + -7046029254386353131L * ((long)intValue3 + 1L);
      longValue4 ^= longValue4 >>> 30;
      longValue4 *= -4658895280553007687L;
      longValue4 ^= longValue4 >>> 27;
      longValue4 *= -7723592293110705685L;
      longValue4 ^= longValue4 >>> 31;
      return (double)(longValue4 & 16777215L) / 1.6777216E7;
   }

   private double a(double doubleValue1, double doubleValue3, double doubleValue5) {
      return doubleValue1 + (doubleValue3 - doubleValue1) * doubleValue5;
   }

   private double a(double doubleValue1) {
      return Math.max(0.0, Math.min(1.0, doubleValue1));
   }

   private void a(Matrix4f matrix4f1, Box box2, int intValue3) {
      BufferBuilder bufferBuilder4 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
      dev.leg1tka.render.RenderPrimitives.drawFilledBox(bufferBuilder4, matrix4f1, box2, intValue3);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder4.end());
   }

   private void b(Matrix4f matrix4f1, Box box2) {
      BufferBuilder bufferBuilder3 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
      dev.leg1tka.render.RenderPrimitives.drawFilledBox(bufferBuilder3, matrix4f1, (float)box2.minX, (float)box2.minY, (float)box2.minZ, (float)box2.maxX, (float)box2.maxY, (float)box2.maxZ, 1.0F, 1.0F, 1.0F, 1.0F);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder3.end());
   }

   private void t() {
      BufferBuilder bufferBuilder1 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      float floatValue2 = (float)Math.max(minecraftClient.getWindow().getScaledWidth(), 1);
      float floatValue3 = (float)Math.max(minecraftClient.getWindow().getScaledHeight(), 1);
      bufferBuilder1.vertex(0.0F, 0.0F, 0.0F).texture(0.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder1.vertex(0.0F, floatValue3, 0.0F).texture(0.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder1.vertex(floatValue2, floatValue3, 0.0F).texture(1.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder1.vertex(floatValue2, 0.0F, 0.0F).texture(1.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder1.end());
   }
}
