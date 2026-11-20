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
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class Cosmetics extends Module {
   public static Cosmetics c = new Cosmetics();
   private static float g;
   private static float h;
   private static float i;
   private static float j;
   private static int k;
   private static final Cosmetics.a[] l = new Cosmetics.a[]{
      new Cosmetics.a(0.08F, 0.1F, 0.88F),
      new Cosmetics.a(0.28F, 0.34F, 0.78F),
      new Cosmetics.a(0.56F, 0.82F, 0.62F),
      new Cosmetics.a(0.86F, 0.3F, 0.52F),
      new Cosmetics.a(1.14F, 0.46F, 0.4F),
      new Cosmetics.a(1.24F, 0.04F, 0.3F),
      new Cosmetics.a(1.02F, -0.18F, 0.28F),
      new Cosmetics.a(1.18F, -0.64F, 0.22F),
      new Cosmetics.a(0.86F, -0.46F, 0.2F),
      new Cosmetics.a(0.8F, -0.98F, 0.14F),
      new Cosmetics.a(0.54F, -0.74F, 0.16F),
      new Cosmetics.a(0.3F, -1.16F, 0.12F),
      new Cosmetics.a(0.1F, -0.54F, 0.18F)
   };
   private final MultiSetting m = new MultiSetting(
      "Косметика",
      new BooleanSetting("Крылья", true),
      new BooleanSetting("Крылья 2", false),
      new BooleanSetting("2D предметы", false)
   );
   private final BooleanSetting n = new BooleanSetting("Анимация крыльев", true)
      .visibleWhen(() -> this.m.a("Крылья"));
   private final NumberSetting o = new NumberSetting("Размер", 1.0F, 0.65F, 1.8F, 0.05F).a(() -> this.m.a("Крылья"));
   private final BooleanSetting p = new BooleanSetting("Анимация крыльев", true)
      .visibleWhen(() -> this.m.a("Крылья 2"));
   private final NumberSetting q = new NumberSetting("Размер", 1.0F, 0.65F, 1.8F, 0.05F).a(() -> this.m.a("Крылья 2"));
   public final NumberSetting d = new NumberSetting("Размер предметов", 0.5F, 0.2F, 1.5F, 0.05F)
      .a(() -> this.m.a("2D предметы"));
   public final BooleanSetting e = new BooleanSetting("Покачивание", true)
      .visibleWhen(() -> this.m.a("2D предметы"));
   public final BooleanSetting f = new BooleanSetting("Показывать стак", true)
      .visibleWhen(() -> this.m.a("2D предметы"));
   private float r;
   private boolean s;
   private boolean t;
   private boolean u;

   public Cosmetics() {
      super("Cosmetics", "Визуальные украшения", Category.RENDER);
      this.addSettings(new Setting[]{this.m, this.n, this.o, this.p, this.q, this.d, this.e, this.f});
   }

   @Override
   public void onDisable() {
      this.s = false;
      this.t = false;
      this.u = false;
      super.onDisable();
   }

   public boolean p() {
      return this.isEnabled() && this.m.a("2D предметы");
   }

   @EventLink
   public void onRender3D(Event3DRender event3DRender1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         this.s();
         boolean flag2 = this.m.a("Крылья");
         boolean flag3 = this.m.a("Крылья 2");
         if (flag2 || flag3) {
            float floatValue4 = event3DRender1.getTickDelta();
            MatrixStack matrixStack5 = event3DRender1.getMatrices();
            Vec3d vec3d6 = event3DRender1.getCamera().getPos();

            for (PlayerEntity playerEntity8 : minecraftClient.world.getPlayers()) {
               if (this.a(playerEntity8) && (playerEntity8 != minecraftClient.player || !minecraftClient.options.getPerspective().isFirstPerson())) {
                  if (flag2) {
                     this.a(playerEntity8, floatValue4, matrixStack5, vec3d6);
                  }

                  if (flag3) {
                     this.b(playerEntity8, floatValue4, matrixStack5, vec3d6);
                  }
               }
            }
         }
      }
   }

   private void a(PlayerEntity playerEntity1, float floatValue2, MatrixStack matrixStack3, Vec3d vec3d4) {
      if (!playerEntity1.isGliding() && playerEntity1.getPose() != EntityPose.SWIMMING && !playerEntity1.isInSwimmingPose()) {
         Vec3d vec3d5 = playerEntity1.getVelocity();
         float floatValue6 = MathHelper.lerp(floatValue2, playerEntity1.prevBodyYaw, playerEntity1.bodyYaw);
         float floatValue7 = floatValue6 * (float) (Math.PI / 180.0);
         Vec3d vec3d8 = new Vec3d((double)(-MathHelper.sin(floatValue7)), 0.0, (double)MathHelper.cos(floatValue7));
         Vec3d vec3d9 = new Vec3d(vec3d8.z, 0.0, -vec3d8.x);
         float floatValue10 = (float)(vec3d5.x * vec3d8.x + vec3d5.z * vec3d8.z);
         float floatValue11 = (float)(vec3d5.x * vec3d9.x + vec3d5.z * vec3d9.z);
         float floatValue12 = (float)vec3d5.y;
         boolean flag13 = this.n.getValue();
         float floatValue14 = flag13 ? MathHelper.clamp(-floatValue10 * 140.0F - floatValue12 * 48.0F, -24.0F, 26.0F) : 0.0F;
         float floatValue15 = flag13 ? MathHelper.clamp(floatValue11 * 90.0F, -10.0F, 10.0F) : 0.0F;
         float floatValue16 = flag13 ? MathHelper.clamp(Math.abs(floatValue10) * 0.95F + Math.abs(floatValue11) * 0.65F + Math.abs(floatValue12) * 0.75F, 0.0F, 1.7F) : 0.0F;
         float floatValue17 = ((float)playerEntity1.age + floatValue2) * 0.22F * 1.6F + floatValue16 * 0.4F;
         float floatValue18 = flag13 ? MathHelper.sin(floatValue17) : 0.0F;
         float floatValue19 = flag13 ? MathHelper.cos(floatValue17) : 0.0F;
         float floatValue20 = 18.0F + floatValue16 * 5.0F;
         float floatValue21 = 13.0F + floatValue14 * 0.3F + floatValue19 * 4.0F;
         float floatValue22 = floatValue18 * 25.0F + floatValue15 * 0.75F;
         EntityPose entityPose23 = playerEntity1.getPose();
         boolean flag24 = playerEntity1.isGliding();
         boolean flag25 = entityPose23 == EntityPose.SWIMMING || flag24;
         if (flag25) {
            floatValue20 -= 4.0F;
            floatValue21 -= 6.0F;
            floatValue22 *= 0.72F;
         }

         if (playerEntity1.isSneaking()) {
            floatValue20 -= 3.0F;
            floatValue21 += 8.0F;
         }

         double doubleValue26 = MathHelper.lerp((double)floatValue2, playerEntity1.prevX, playerEntity1.getX()) - vec3d4.x;
         double doubleValue28 = MathHelper.lerp((double)floatValue2, playerEntity1.prevY, playerEntity1.getY()) - vec3d4.y;
         double doubleValue30 = MathHelper.lerp((double)floatValue2, playerEntity1.prevZ, playerEntity1.getZ()) - vec3d4.z;
         matrixStack3.push();
         matrixStack3.translate(doubleValue26, doubleValue28, doubleValue30);
         matrixStack3.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-floatValue6));
         this.a(matrixStack3, playerEntity1, floatValue2, entityPose23, flag24);
         int intValue32 = this.r();
         int intValue33 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue32, 132);
         int intValue34 = dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.api.utils.text.ColorUtils.b(intValue32, 0.85F), 102);
         int intValue35 = dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.api.utils.text.ColorUtils.b(intValue32, 0.58F), 214);
         RenderSystem.enableBlend();
         RenderSystem.disableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.blendFunc(770, 771);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
         float floatValue36 = 1.0F * this.o.f();
         BufferBuilder bufferBuilder37 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
         this.a(bufferBuilder37, matrixStack3, 1.0F, floatValue20, floatValue21, floatValue22, floatValue36, intValue33, intValue34);
         this.a(bufferBuilder37, matrixStack3, -1.0F, floatValue20, floatValue21, floatValue22, floatValue36, intValue33, intValue34);
         BufferRenderer.drawWithGlobalProgram(bufferBuilder37.end());
         RenderSystem.lineWidth(1.9F);
         BufferBuilder bufferBuilder38 = Tessellator.getInstance().begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
         this.a(bufferBuilder38, matrixStack3, 1.0F, floatValue20, floatValue21, floatValue22, floatValue36, intValue35);
         this.a(bufferBuilder38, matrixStack3, -1.0F, floatValue20, floatValue21, floatValue22, floatValue36, intValue35);
         BufferRenderer.drawWithGlobalProgram(bufferBuilder38.end());
         RenderSystem.enableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
         matrixStack3.pop();
      }
   }

   private void b(PlayerEntity playerEntity1, float floatValue2, MatrixStack matrixStack3, Vec3d vec3d4) {
      if (playerEntity1.isAlive() && !playerEntity1.isInvisible()) {
         if (!playerEntity1.isGliding() && playerEntity1.getPose() != EntityPose.SWIMMING && !playerEntity1.isInSwimmingPose()) {
            double doubleValue5 = MathHelper.lerp((double)floatValue2, playerEntity1.prevX, playerEntity1.getX()) - vec3d4.x;
            double doubleValue7 = MathHelper.lerp((double)floatValue2, playerEntity1.prevY, playerEntity1.getY()) - vec3d4.y;
            double doubleValue9 = MathHelper.lerp((double)floatValue2, playerEntity1.prevZ, playerEntity1.getZ()) - vec3d4.z;
            float floatValue11 = this.a(playerEntity1, floatValue2);
            Vec3d vec3d12 = playerEntity1.getVelocity();
            float floatValue13 = floatValue11 * (float) (Math.PI / 180.0);
            Vec3d vec3d14 = new Vec3d((double)(-MathHelper.sin(floatValue13)), 0.0, (double)MathHelper.cos(floatValue13));
            Vec3d vec3d15 = new Vec3d(vec3d14.z, 0.0, -vec3d14.x);
            float floatValue16 = (float)(vec3d12.x * vec3d14.x + vec3d12.z * vec3d14.z);
            float floatValue17 = (float)(vec3d12.x * vec3d15.x + vec3d12.z * vec3d15.z);
            float floatValue18 = (float)vec3d12.y;
            boolean flag19 = this.p.getValue();
            float floatValue20 = flag19 ? MathHelper.clamp(-floatValue16 * 140.0F - floatValue18 * 48.0F, -24.0F, 26.0F) : 0.0F;
            float floatValue21 = flag19 ? MathHelper.clamp(floatValue17 * 90.0F, -10.0F, 10.0F) : 0.0F;
            float floatValue22 = flag19 ? MathHelper.clamp(Math.abs(floatValue16) * 0.95F + Math.abs(floatValue17) * 0.65F + Math.abs(floatValue18) * 0.75F, 0.0F, 1.7F) : 0.0F;
            float floatValue23 = ((float)playerEntity1.age + floatValue2) * 0.22F * 1.6F + floatValue22 * 0.4F;
            float floatValue24 = flag19 ? MathHelper.sin(floatValue23) : 0.0F;
            float floatValue25 = flag19 ? MathHelper.cos(floatValue23) : 0.0F;
            float floatValue26 = 18.0F + floatValue22 * 5.0F;
            float floatValue27 = 13.0F + floatValue20 * 0.3F + floatValue25 * 4.0F;
            float floatValue28 = floatValue24 * 25.0F + floatValue21 * 0.75F;
            EntityPose entityPose29 = playerEntity1.getPose();
            boolean flag30 = playerEntity1.isGliding();
            boolean flag31 = entityPose29 == EntityPose.SWIMMING || flag30;
            if (flag31) {
               floatValue26 -= 4.0F;
               floatValue27 -= 6.0F;
               floatValue28 *= 0.72F;
            }

            if (playerEntity1.isSneaking()) {
               floatValue26 -= 3.0F;
               floatValue27 += 8.0F;
            }

            Cosmetics.b value32 = this.a(playerEntity1, floatValue2, entityPose29);
            float floatValue33 = floatValue26 * value32.g;
            float floatValue34 = value32.h * this.q.f();
            float floatValue35 = value32.o + floatValue27 * 0.18F;
            float floatValue36 = value32.n + floatValue28 * 0.2F;
            int intValue37 = this.r();
            int intValue38 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue37, 220);
            int intValue39 = dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.api.utils.text.ColorUtils.c(intValue37, -1, 0.28F), Math.round(48.4F));
            int intValue40 = dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.api.utils.text.ColorUtils.c(intValue37, -1, 0.55F), Math.round(57.199997F));
            int intValue41 = dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.api.utils.text.ColorUtils.b(intValue37, 0.62F), Math.round(136.4F));
            int intValue42 = dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.api.utils.text.ColorUtils.c(intValue37, -1, 0.28F), Math.round(44.0F));
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.blendFunc(770, 771);
            RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
            matrixStack3.push();
            matrixStack3.translate(doubleValue5, doubleValue7, doubleValue9);
            matrixStack3.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - floatValue11));
            if (value32.a != 0.0F || value32.b != 0.0F) {
               matrixStack3.translate(0.0F, value32.a, value32.b);
            }

            if (value32.e != 0.0F) {
               matrixStack3.multiply(RotationAxis.POSITIVE_X.rotationDegrees(value32.e));
            }

            if (value32.f != 0.0F) {
               matrixStack3.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(value32.f));
            }

            matrixStack3.translate(0.0F, value32.c, value32.d);
            matrixStack3.scale(floatValue34, floatValue34, floatValue34);
            this.a(matrixStack3, -1.0F, floatValue33, floatValue35, floatValue36, intValue38, intValue39, intValue40, intValue41, intValue42, value32);
            this.a(matrixStack3, 1.0F, floatValue33, floatValue35, floatValue36, intValue38, intValue39, intValue40, intValue41, intValue42, value32);
            matrixStack3.pop();
            RenderSystem.enableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
         }
      }
   }

   private boolean a(PlayerEntity playerEntity1) {
      return minecraftClient.player == null || playerEntity1 == null ? false : playerEntity1.isAlive() && !playerEntity1.isSpectator() && !playerEntity1.isInvisible();
   }

   private int q() {
      if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null && AspectDLC.INSTANCE.themeStorage.b() != null) {
         dev.leg1tka.gui.themes.ThemePalette value1 = AspectDLC.INSTANCE.themeStorage.b().a();
         return value1 != null && value1.c != null && value1.c.length != 0 ? value1.c[0] : dev.leg1tka.api.utils.text.ColorUtils.k(0);
      } else {
         return dev.leg1tka.api.utils.text.ColorUtils.k(0);
      }
   }

   private int r() {
      if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null && AspectDLC.INSTANCE.themeStorage.b() != null) {
         dev.leg1tka.gui.themes.ThemePalette value1 = AspectDLC.INSTANCE.themeStorage.b().a();
         if (value1 == null) {
            return dev.leg1tka.api.utils.text.ColorUtils.a();
         } else {
            return "Rainbow".equals(value1.b()) ? dev.leg1tka.api.utils.text.ColorUtils.a() : this.q();
         }
      } else {
         return dev.leg1tka.api.utils.text.ColorUtils.a();
      }
   }

   private void s() {
      boolean flag1 = this.m.a("Крылья");
      boolean flag2 = this.m.a("Крылья 2");
      if (flag1 && flag2) {
         if (flag1 != this.t && flag2 == this.u) {
            this.m.a("Крылья 2", false);
            flag2 = false;
         } else {
            this.m.a("Крылья", false);
            flag1 = false;
         }
      }

      this.t = flag1;
      this.u = flag2;
   }

   private void a(MatrixStack matrixStack1, PlayerEntity playerEntity2, float floatValue3, EntityPose entityPose4, boolean flag5) {
      if (flag5) {
         float floatValue8 = playerEntity2.getPitch(floatValue3);
         float floatValue9 = MathHelper.clamp(floatValue8, -65.0F, 65.0F);
         matrixStack1.translate(0.0F, 0.3F, 0.0F);
         matrixStack1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-(90.0F + floatValue9)));
         matrixStack1.translate(0.0F, -0.15F, 0.12F);
      } else if (entityPose4 == EntityPose.SWIMMING) {
         float floatValue6 = playerEntity2.getPitch(floatValue3);
         float floatValue7 = MathHelper.clamp(floatValue6, -65.0F, 65.0F);
         matrixStack1.translate(0.0F, 0.3F, 0.0F);
         matrixStack1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-(90.0F + floatValue7)));
         matrixStack1.translate(0.0F, -0.15F, 0.12F);
      } else {
         if (playerEntity2.isSneaking()) {
            matrixStack1.translate(0.0F, 1.15F, 0.0F);
            matrixStack1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(24.0F));
            matrixStack1.translate(0.0F, 0.0F, 0.08F);
         } else {
            matrixStack1.translate(0.0F, 1.3F, 0.08F);
         }
      }
   }

   private float a(PlayerEntity playerEntity1, float floatValue2) {
      float floatValue3 = MathHelper.lerpAngleDegrees(floatValue2, playerEntity1.prevBodyYaw, playerEntity1.bodyYaw);
      if (playerEntity1 != minecraftClient.player) {
         return floatValue3;
      } else if (!this.s) {
         this.r = floatValue3;
         this.s = true;
         return this.r;
      } else {
         float floatValue4 = MathHelper.wrapDegrees(floatValue3 - this.r);
         this.r = this.r + MathHelper.clamp(floatValue4, -14.0F, 14.0F);
         return this.r;
      }
   }

   private Cosmetics.b a(PlayerEntity playerEntity1, float floatValue2, EntityPose entityPose3) {
      float floatValue4 = playerEntity1.getPitch(floatValue2);
      if (playerEntity1.isGliding()) {
         float floatValue8 = MathHelper.clamp(floatValue4, -65.0F, 65.0F);
         return new Cosmetics.b(1.18F, 0.1F, 0.0F, 0.0F, -(90.0F + floatValue8), 0.0F, 0.76F, 0.92F, 0.1F, 0.58F, 0.05F, 0.0F, 0.06F, -5.0F, -2.0F, 0.13F);
      } else if (entityPose3 == EntityPose.SWIMMING || playerEntity1.isInSwimmingPose()) {
         float floatValue5 = MathHelper.clamp(floatValue4, -65.0F, 65.0F);
         float floatValue6 = playerEntity1.isInSwimmingPose() ? 1.1F : 1.18F;
         float floatValue7 = playerEntity1.isInSwimmingPose() ? 0.18F : 0.12F;
         return new Cosmetics.b(floatValue6, floatValue7, 0.18F, 0.48F, -(90.0F + floatValue5), 0.0F, 0.84F, 0.96F, 0.12F, 0.7F, 0.03F, 0.0F, 0.01F, -7.0F, -3.0F, 0.16F);
      } else {
         return playerEntity1.isSneaking()
            ? new Cosmetics.b(0.0F, 0.0F, 0.96F, 0.1F, 18.0F, 0.0F, 1.0F, 1.0F, 0.18F, 4.5F, 0.06F, 0.0F, 0.02F, -11.0F, -4.0F, 0.12F)
            : new Cosmetics.b(0.0F, 0.0F, 1.18F, 0.1F, 0.0F, 0.0F, 1.0F, 1.0F, 0.18F, 4.5F, 0.06F, 0.0F, 0.02F, -11.0F, -4.0F, 0.12F);
      }
   }

   private void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, int intValue7, int intValue8, int intValue9, int intValue10, Cosmetics.b value11) {
      matrixStack1.push();
      matrixStack1.translate(floatValue2 * value11.k, value11.l, value11.m);
      matrixStack1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(floatValue2 * floatValue3));
      matrixStack1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue2 * floatValue5));
      matrixStack1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue4));
      RenderSystem.blendFunc(770, 1);
      this.a(matrixStack1, floatValue2, 1.22F, intValue7, dev.leg1tka.api.utils.text.ColorUtils.d(intValue7, 0));
      this.a(matrixStack1, floatValue2, 0.84F, intValue8, dev.leg1tka.api.utils.text.ColorUtils.d(intValue8, 0));
      RenderSystem.blendFunc(770, 771);
      this.a(matrixStack1, floatValue2, 1.0F, intValue6, dev.leg1tka.api.utils.text.ColorUtils.d(intValue6, 10));
      RenderSystem.blendFunc(770, 1);
      this.a(matrixStack1, floatValue2, 1.0F, intValue9);
      this.b(matrixStack1, floatValue2, 0.96F, intValue10);
      matrixStack1.pop();
   }

   private void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, int intValue4, int intValue5) {
      Matrix4f matrix4f6 = matrixStack1.peek().getPositionMatrix();
      BufferBuilder bufferBuilder7 = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);

      for (int intValue8 = 0; intValue8 < l.length; intValue8++) {
         Cosmetics.a value9 = l[intValue8];
         Cosmetics.a value10 = l[(intValue8 + 1) % l.length];
         this.a(bufferBuilder7, matrix4f6, 0.0F, 0.0F, 0.0F, intValue4);
         this.a(bufferBuilder7, matrix4f6, floatValue2 * value9.a * floatValue3, value9.b * floatValue3, 0.0F, this.a(intValue5, value9.c));
         this.a(bufferBuilder7, matrix4f6, floatValue2 * value10.a * floatValue3, value10.b * floatValue3, 0.0F, this.a(intValue5, value10.c));
      }

      BufferRenderer.drawWithGlobalProgram(bufferBuilder7.end());
   }

   private void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, int intValue4) {
      Matrix4f matrix4f5 = matrixStack1.peek().getPositionMatrix();
      BufferBuilder bufferBuilder6 = Tessellator.getInstance().begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
      RenderSystem.lineWidth(1.35F);
      GL11.glEnable(2848);

      for (int intValue7 = 0; intValue7 < l.length; intValue7++) {
         Cosmetics.a value8 = l[intValue7];
         Cosmetics.a value9 = l[(intValue7 + 1) % l.length];
         this.a(bufferBuilder6, matrix4f5, floatValue2 * value8.a * floatValue3, value8.b * floatValue3, 0.0F, floatValue2 * value9.a * floatValue3, value9.b * floatValue3, 0.0F, intValue4);
      }

      BufferRenderer.drawWithGlobalProgram(bufferBuilder6.end());
      GL11.glDisable(2848);
   }

   private void b(MatrixStack matrixStack1, float floatValue2, float floatValue3, int intValue4) {
      Matrix4f matrix4f5 = matrixStack1.peek().getPositionMatrix();
      BufferBuilder bufferBuilder6 = Tessellator.getInstance().begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
      int[] intValueArray7 = new int[]{2, 4, 7, 9, 11};
      RenderSystem.lineWidth(0.9F);

      for (int intValue11 : intValueArray7) {
         Cosmetics.a value12 = l[intValue11];
         this.a(bufferBuilder6, matrix4f5, 0.0F, 0.0F, 0.0F, dev.leg1tka.api.utils.text.ColorUtils.d(intValue4, Math.max(8, (int)((float)(intValue4 >> 24 & 0xFF) * 0.75F))));
         this.a(bufferBuilder6, matrix4f5, floatValue2 * value12.a * floatValue3, value12.b * floatValue3, 0.0F, this.a(intValue4, value12.c));
      }

      BufferRenderer.drawWithGlobalProgram(bufferBuilder6.end());
   }

   private int a(int intValue1, float floatValue2) {
      int intValue3 = intValue1 >> 24 & 0xFF;
      return dev.leg1tka.api.utils.text.ColorUtils.d(intValue1, Math.max(0, Math.min(255, (int)((float)intValue3 * floatValue2))));
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue5).color(intValue6);
   }

   private void a(BufferBuilder bufferBuilder1, MatrixStack matrixStack2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, int intValue8, int intValue9) {
      float floatValue10 = 0.12F * floatValue7;
      float floatValue11 = 1.52F * floatValue7;
      float floatValue12 = 0.64F * floatValue7;
      float floatValue13 = 1.14F * floatValue7;
      float floatValue14 = 0.39F * floatValue7;
      matrixStack2.push();
      matrixStack2.translate(0.15F * floatValue3, 0.0F, -0.17F);
      matrixStack2.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(floatValue3 * floatValue4));
      matrixStack2.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue5));
      matrixStack2.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue3 * floatValue6));
      Matrix4f matrix4f15 = matrixStack2.peek().getPositionMatrix();
      this.a(
         bufferBuilder1,
         matrix4f15,
         floatValue3 * floatValue10,
         0.02F,
         -0.01F,
         floatValue3 * (floatValue10 + floatValue11 * 0.22F),
         floatValue12 * 0.98F,
         -0.06F,
         floatValue3 * (floatValue10 + floatValue11 * 0.88F),
         floatValue12 * 0.6F,
         -0.13F,
         intValue8,
         intValue9
      );
      this.a(
         bufferBuilder1,
         matrix4f15,
         floatValue3 * floatValue10,
         0.02F,
         -0.01F,
         floatValue3 * (floatValue10 + floatValue11 * 0.88F),
         floatValue12 * 0.6F,
         -0.13F,
         floatValue3 * (floatValue10 + floatValue11),
         floatValue12 * 0.12F,
         -0.17F,
         intValue8,
         intValue9
      );
      this.a(
         bufferBuilder1,
         matrix4f15,
         floatValue3 * floatValue10,
         -0.03F,
         -0.03F,
         floatValue3 * (floatValue10 + floatValue13 * 0.26F),
         -floatValue14 * 0.96F,
         -0.11F,
         floatValue3 * (floatValue10 + floatValue13 * 0.84F),
         -floatValue14 * 0.54F,
         -0.18F,
         intValue9,
         intValue8
      );
      this.a(
         bufferBuilder1,
         matrix4f15,
         floatValue3 * floatValue10,
         -0.03F,
         -0.03F,
         floatValue3 * (floatValue10 + floatValue13 * 0.84F),
         -floatValue14 * 0.54F,
         -0.18F,
         floatValue3 * (floatValue10 + floatValue13),
         -floatValue14 * 0.12F,
         -0.21F,
         intValue9,
         intValue8
      );
      matrixStack2.pop();
   }

   private void a(BufferBuilder bufferBuilder1, MatrixStack matrixStack2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, int intValue8) {
      float floatValue9 = 0.12F * floatValue7;
      float floatValue10 = 1.52F * floatValue7;
      float floatValue11 = 0.64F * floatValue7;
      float floatValue12 = 1.14F * floatValue7;
      float floatValue13 = 0.39F * floatValue7;
      matrixStack2.push();
      matrixStack2.translate(0.15F * floatValue3, 0.0F, -0.17F);
      matrixStack2.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(floatValue3 * floatValue4));
      matrixStack2.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue5));
      matrixStack2.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue3 * floatValue6));
      Matrix4f matrix4f14 = matrixStack2.peek().getPositionMatrix();
      this.a(bufferBuilder1, matrix4f14, floatValue3 * floatValue9, 0.02F, -0.01F, floatValue3 * (floatValue9 + floatValue10 * 0.22F), floatValue11 * 0.98F, -0.06F, intValue8);
      this.a(bufferBuilder1, matrix4f14, floatValue3 * (floatValue9 + floatValue10 * 0.22F), floatValue11 * 0.98F, -0.06F, floatValue3 * (floatValue9 + floatValue10 * 0.88F), floatValue11 * 0.6F, -0.13F, intValue8);
      this.a(bufferBuilder1, matrix4f14, floatValue3 * (floatValue9 + floatValue10 * 0.88F), floatValue11 * 0.6F, -0.13F, floatValue3 * (floatValue9 + floatValue10), floatValue11 * 0.12F, -0.17F, intValue8);
      this.a(bufferBuilder1, matrix4f14, floatValue3 * floatValue9, -0.03F, -0.03F, floatValue3 * (floatValue9 + floatValue12 * 0.26F), -floatValue13 * 0.96F, -0.11F, intValue8);
      this.a(bufferBuilder1, matrix4f14, floatValue3 * (floatValue9 + floatValue12 * 0.26F), -floatValue13 * 0.96F, -0.11F, floatValue3 * (floatValue9 + floatValue12 * 0.84F), -floatValue13 * 0.54F, -0.18F, intValue8);
      this.a(bufferBuilder1, matrix4f14, floatValue3 * (floatValue9 + floatValue12 * 0.84F), -floatValue13 * 0.54F, -0.18F, floatValue3 * (floatValue9 + floatValue12), -floatValue13 * 0.12F, -0.21F, intValue8);
      this.a(bufferBuilder1, matrix4f14, floatValue3 * floatValue9, -0.01F, -0.02F, floatValue3 * (floatValue9 + floatValue10 * 0.6F), 0.08F, -0.08F, intValue8);
      matrixStack2.pop();
   }

   private void a(
      BufferBuilder bufferBuilder1,
      Matrix4f matrix4f2,
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
      float floatValue14,
      int intValue15,
      int intValue16,
      int intValue17,
      int intValue18
   ) {
      this.b(bufferBuilder1, matrix4f2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, floatValue9, floatValue10, floatValue11, floatValue12, floatValue13, floatValue14, intValue15, intValue16, intValue17, intValue18);
      this.b(bufferBuilder1, matrix4f2, floatValue12, floatValue13, floatValue14, floatValue9, floatValue10, floatValue11, floatValue6, floatValue7, floatValue8, floatValue3, floatValue4, floatValue5, intValue15, intValue16, intValue17, intValue18);
   }

   private void a(
      BufferBuilder bufferBuilder1,
      Matrix4f matrix4f2,
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
      float floatValue14,
      int intValue15,
      int intValue16
   ) {
      int intValue17 = intValue15 >> 16 & 0xFF;
      int intValue18 = intValue15 >> 8 & 0xFF;
      int intValue19 = intValue15 & 0xFF;
      int intValue20 = intValue15 >> 24 & 0xFF;
      int intValue21 = intValue16 >> 16 & 0xFF;
      int intValue22 = intValue16 >> 8 & 0xFF;
      int intValue23 = intValue16 & 0xFF;
      int intValue24 = intValue16 >> 24 & 0xFF;
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue5).color(intValue17, intValue18, intValue19, intValue20);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue8).color(intValue21, intValue22, intValue23, intValue24);
      bufferBuilder1.vertex(matrix4f2, floatValue9, floatValue10, floatValue11).color(intValue21, intValue22, intValue23, intValue24);
      bufferBuilder1.vertex(matrix4f2, floatValue12, floatValue13, floatValue14).color(intValue17, intValue18, intValue19, intValue20);
      bufferBuilder1.vertex(matrix4f2, floatValue12, floatValue13, floatValue14).color(intValue17, intValue18, intValue19, intValue20);
      bufferBuilder1.vertex(matrix4f2, floatValue9, floatValue10, floatValue11).color(intValue21, intValue22, intValue23, intValue24);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue8).color(intValue21, intValue22, intValue23, intValue24);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue5).color(intValue17, intValue18, intValue19, intValue20);
   }

   private void a(
      BufferBuilder bufferBuilder1,
      Matrix4f matrix4f2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      float floatValue10,
      float floatValue11,
      int intValue12,
      int intValue13
   ) {
      int intValue14 = intValue12 >> 16 & 0xFF;
      int intValue15 = intValue12 >> 8 & 0xFF;
      int intValue16 = intValue12 & 0xFF;
      int intValue17 = intValue12 >> 24 & 0xFF;
      int intValue18 = intValue13 >> 16 & 0xFF;
      int intValue19 = intValue13 >> 8 & 0xFF;
      int intValue20 = intValue13 & 0xFF;
      int intValue21 = intValue13 >> 24 & 0xFF;
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue5).color(intValue14, intValue15, intValue16, intValue17);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue8).color(intValue18, intValue19, intValue20, intValue21);
      bufferBuilder1.vertex(matrix4f2, floatValue9, floatValue10, floatValue11).color(intValue18, intValue19, intValue20, intValue21);
      bufferBuilder1.vertex(matrix4f2, floatValue9, floatValue10, floatValue11).color(intValue18, intValue19, intValue20, intValue21);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue8).color(intValue18, intValue19, intValue20, intValue21);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue5).color(intValue14, intValue15, intValue16, intValue17);
   }

   private void a(
      BufferBuilder bufferBuilder1,
      Matrix4f matrix4f2,
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
      int intValue13,
      int intValue14
   ) {
      float floatValue15 = floatValue6 - floatValue3;
      float floatValue16 = floatValue7 - floatValue4;
      float floatValue17 = Math.max(1.0E-4F, (float)Math.sqrt((double)(floatValue15 * floatValue15 + floatValue16 * floatValue16)));
      float floatValue18 = -floatValue16 / floatValue17 * floatValue12;
      float floatValue19 = floatValue15 / floatValue17 * floatValue12;
      int intValue20 = intValue13 >> 16 & 0xFF;
      int intValue21 = intValue13 >> 8 & 0xFF;
      int intValue22 = intValue13 & 0xFF;
      int intValue23 = intValue13 >> 24 & 0xFF;
      int intValue24 = intValue14 >> 16 & 0xFF;
      int intValue25 = intValue14 >> 8 & 0xFF;
      int intValue26 = intValue14 & 0xFF;
      int intValue27 = intValue14 >> 24 & 0xFF;
      this.a(
         bufferBuilder1,
         matrix4f2,
         floatValue3 + floatValue18,
         floatValue4 + floatValue19,
         floatValue5,
         floatValue3 - floatValue18,
         floatValue4 - floatValue19,
         floatValue5,
         floatValue6 - floatValue18,
         floatValue7 - floatValue19,
         floatValue8,
         floatValue6 + floatValue18,
         floatValue7 + floatValue19,
         floatValue8,
         intValue20,
         intValue21,
         intValue22,
         intValue23
      );
      float floatValue28 = floatValue9 - floatValue6;
      float floatValue29 = floatValue10 - floatValue7;
      float floatValue30 = Math.max(1.0E-4F, (float)Math.sqrt((double)(floatValue28 * floatValue28 + floatValue29 * floatValue29)));
      float floatValue31 = -floatValue29 / floatValue30 * floatValue12;
      float floatValue32 = floatValue28 / floatValue30 * floatValue12;
      this.a(
         bufferBuilder1,
         matrix4f2,
         floatValue6 + floatValue31,
         floatValue7 + floatValue32,
         floatValue8,
         floatValue6 - floatValue31,
         floatValue7 - floatValue32,
         floatValue8,
         floatValue9 - floatValue31,
         floatValue10 - floatValue32,
         floatValue11,
         floatValue9 + floatValue31,
         floatValue10 + floatValue32,
         floatValue11,
         intValue24,
         intValue25,
         intValue26,
         intValue27
      );
   }

   private void b(
      BufferBuilder bufferBuilder1,
      Matrix4f matrix4f2,
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
      float floatValue14,
      int intValue15,
      int intValue16,
      int intValue17,
      int intValue18
   ) {
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue5).color(intValue15, intValue16, intValue17, intValue18);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue8).color(intValue15, intValue16, intValue17, intValue18);
      bufferBuilder1.vertex(matrix4f2, floatValue9, floatValue10, floatValue11).color(intValue15, intValue16, intValue17, intValue18);
      bufferBuilder1.vertex(matrix4f2, floatValue12, floatValue13, floatValue14).color(intValue15, intValue16, intValue17, intValue18);
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, int intValue9) {
      int intValue10 = intValue9 >> 16 & 0xFF;
      int intValue11 = intValue9 >> 8 & 0xFF;
      int intValue12 = intValue9 & 0xFF;
      int intValue13 = intValue9 >> 24 & 0xFF;
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue5).color(intValue10, intValue11, intValue12, intValue13);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue8).color(intValue10, intValue11, intValue12, intValue13);
   }

   static class a {
      final float a;
      final float b;
      final float c;

      a(float floatValue1, float floatValue2, float floatValue3) {
         this.a = floatValue1;
         this.b = floatValue2;
         this.c = floatValue3;
      }
   }

   static class b {
      final float a;
      final float b;
      final float c;
      final float d;
      final float e;
      final float f;
      final float g;
      final float h;
      private final float i;
      private final float j;
      final float k;
      final float l;
      final float m;
      final float n;
      final float o;
      private final float p;

      b(
         float floatValue1,
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
         float floatValue14,
         float floatValue15,
         float floatValue16
      ) {
         this.a = floatValue1;
         this.b = floatValue2;
         this.c = floatValue3;
         this.d = floatValue4;
         this.e = floatValue5;
         this.f = floatValue6;
         this.g = floatValue7;
         this.h = floatValue8;
         this.i = floatValue9;
         this.j = floatValue10;
         this.k = floatValue11;
         this.l = floatValue12;
         this.m = floatValue13;
         this.n = floatValue14;
         this.o = floatValue15;
         this.p = floatValue16;
      }
   }
}
