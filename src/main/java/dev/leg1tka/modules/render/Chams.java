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
import dev.leg1tka.mixin.LivingEntityRendererAccessor;
import java.awt.Color;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class Chams extends Module {
   public static final Chams c = new Chams();
   public static String d;
   public static String e;
   public static String f;
   private static int g;
   private static float h;
   private static float i;
   private static float j;
   private static float k;
   private static float l;
   private static float m;
   private static float n;
   private static final int o = new Color(85, 255, 85, 60).getRGB();
   private static final int p = new Color(100, 255, 100, 255).getRGB();
   private static long q;
   private final MultiSetting r = new MultiSetting(
      "Отображать",
      new BooleanSetting("Игроков", true),
      new BooleanSetting("Друзей", true),
      new BooleanSetting("Себя", false)
   );
   private final BooleanSetting s = new BooleanSetting("Волны", true);
   private final NumberSetting t = new NumberSetting("Скорость X", 0.22F, 0.0F, 1.5F, 0.01F).a(this.s::getValue);
   private final NumberSetting u = new NumberSetting("Скорость Y", 0.15F, 0.0F, 1.5F, 0.01F).a(this.s::getValue);
   private final NumberSetting v = new NumberSetting("Размер волн", 1.35F, 0.2F, 4.0F, 0.05F).a(this.s::getValue);
   private final NumberSetting w = new NumberSetting("Плотность волн", 1.15F, 0.5F, 3.0F, 0.05F).a(this.s::getValue);
   private final NumberSetting x = new NumberSetting("Сила волн", 1.0F, 0.2F, 3.0F, 0.05F).a(this.s::getValue);
   private final BooleanSetting y = new BooleanSetting("Свечение", true);
   private final NumberSetting z = new NumberSetting("Сила свечения", 2.0F, 1.0F, 5.0F, 0.1F).a(this.y::getValue);
   private final NumberSetting A = new NumberSetting("Слои свечения", 3.0F, 1.0F, 6.0F, 1.0F).a(this.y::getValue);
   private final BooleanSetting B = new BooleanSetting("Пульсирование", false);
   private final NumberSetting C = new NumberSetting(
         "Скорость пульсации", 2.0F, 0.5F, 5.0F, 0.1F
      )
      .a(this.B::getValue);
   private final BooleanSetting D = new BooleanSetting("Скрыть оригинал", false);
   private final BooleanSetting E = new BooleanSetting(
      "Скрывать предметы и плащ", false
   );
   private final long F = System.currentTimeMillis();
   private boolean G;
   private long H;

   private Chams() {
      super("Chams", "Чамсы по модели игрока", Category.RENDER);
      this.addSettings(new Setting[]{this.r, this.s, this.t, this.u, this.v, this.w, this.x, this.y, this.z, this.A, this.B, this.C, this.D, this.E});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.G = false;
      this.H = 0L;
      this.r();
   }

   @Override
   public void onDisable() {
      this.G = false;
      this.H = 0L;
      super.onDisable();
   }

   @EventLink(
      priority = 100
   )
   public void onRender3D(Event3DRender event3DRender1) {
      if (this.isEnabled() && minecraftClient.world != null && minecraftClient.player != null) {
         if (this.q() && !this.G && System.currentTimeMillis() >= this.H) {
            this.r();
         }

         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableCull();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);

         for (PlayerEntity playerEntity3 : minecraftClient.world.getPlayers()) {
            if (this.a(playerEntity3) && (playerEntity3 != minecraftClient.player || minecraftClient.options.getPerspective() != Perspective.FIRST_PERSON)) {
               this.a(event3DRender1, playerEntity3);
            }
         }

         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         RenderSystem.lineWidth(1.0F);
      }
   }

   private void a(Event3DRender event3DRender1, PlayerEntity playerEntity2) {
      if (playerEntity2 instanceof AbstractClientPlayerEntity abstractClientPlayerEntity3) {
         if ((Object)minecraftClient.getEntityRenderDispatcher().getRenderer(playerEntity2) instanceof PlayerEntityRenderer playerEntityRenderer5) {
            PlayerEntityRenderState playerEntityRenderState6 = playerEntityRenderer5.createRenderState();
            playerEntityRenderer5.updateRenderState(abstractClientPlayerEntity3, playerEntityRenderState6, event3DRender1.getTickDelta());
            PlayerEntityModel playerEntityModel7 = (PlayerEntityModel)playerEntityRenderer5.getModel();
            playerEntityModel7.setAngles(playerEntityRenderState6);
            MatrixStack matrixStack8 = event3DRender1.getMatrices();
            matrixStack8.push();
            this.a(matrixStack8, playerEntityRenderState6, playerEntityRenderer5, event3DRender1.getCamera().getPos(), playerEntity2, event3DRender1.getTickDelta());
            int intValue9 = this.e(playerEntity2);
            int intValue10 = this.f(playerEntity2);
            this.a(matrixStack8, playerEntityModel7, 0.0F, intValue9);
            this.c(matrixStack8, playerEntityModel7, 0.0F, intValue10);
            matrixStack8.pop();
         }
      }
   }

   private void a(MatrixStack matrixStack1, PlayerEntityRenderState playerEntityRenderState2, PlayerEntityRenderer playerEntityRenderer3, Vec3d vec3d4, PlayerEntity playerEntity5, float floatValue6) {
      Vec3d vec3d7 = playerEntity5.getLerpedPos(floatValue6);
      double doubleValue8 = vec3d7.x - vec3d4.x;
      double doubleValue10 = vec3d7.y - vec3d4.y;
      double doubleValue12 = vec3d7.z - vec3d4.z;
      matrixStack1.translate(doubleValue8, doubleValue10, doubleValue12);
      if (playerEntityRenderState2.sleepingDirection != null) {
         float floatValue14 = playerEntityRenderState2.standingEyeHeight - 0.1F;
         matrixStack1.translate((float)(-playerEntityRenderState2.sleepingDirection.getOffsetX()) * floatValue14, 0.0F, (float)(-playerEntityRenderState2.sleepingDirection.getOffsetZ()) * floatValue14);
      }

      float floatValue16 = playerEntityRenderState2.baseScale;
      matrixStack1.scale(floatValue16, floatValue16, floatValue16);
      LivingEntityRendererAccessor livingEntityRendererAccessor15 = (LivingEntityRendererAccessor)playerEntityRenderer3;
      livingEntityRendererAccessor15.AspectDLC$setupTransforms(playerEntityRenderState2, matrixStack1, playerEntityRenderState2.bodyYaw, floatValue16);
      matrixStack1.scale(-1.0F, -1.0F, 1.0F);
      livingEntityRendererAccessor15.AspectDLC$scale(playerEntityRenderState2, matrixStack1);
      matrixStack1.translate(0.0F, -1.501F, 0.0F);
   }

   private void a(MatrixStack matrixStack1, BipedEntityModel<?> bipedEntityModel2, float floatValue3, int intValue4) {
      if (!this.s.getValue()) {
         this.b(matrixStack1, bipedEntityModel2, floatValue3, intValue4);
      } else {
         ShaderProgram shaderProgram5 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.x);
         if (shaderProgram5 != null) {
            RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.x);
            this.a(shaderProgram5, "time", this.s.getValue() ? (float)(System.currentTimeMillis() - this.F) / 1000.0F : 0.0F);
            this.a(shaderProgram5, "speedX", this.t.f());
            this.a(shaderProgram5, "speedY", this.u.f());
            this.a(shaderProgram5, "scale", this.v.f());
            this.a(shaderProgram5, "density", this.w.f());
            this.a(shaderProgram5, "glowStrength", this.x.f());
            BufferBuilder bufferBuilder6 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            ModelPart modelPart7 = bipedEntityModel2.getRootPart();
            this.b(matrixStack1, bufferBuilder6, modelPart7, bipedEntityModel2.head, -4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, floatValue3, intValue4);
            this.b(matrixStack1, bufferBuilder6, modelPart7, bipedEntityModel2.body, -4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, floatValue3, intValue4);
            this.b(matrixStack1, bufferBuilder6, modelPart7, bipedEntityModel2.rightArm, -3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, floatValue3, intValue4);
            this.b(matrixStack1, bufferBuilder6, modelPart7, bipedEntityModel2.leftArm, -1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, floatValue3, intValue4);
            this.b(matrixStack1, bufferBuilder6, modelPart7, bipedEntityModel2.rightLeg, -2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, floatValue3, intValue4);
            this.b(matrixStack1, bufferBuilder6, modelPart7, bipedEntityModel2.leftLeg, -2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, floatValue3, intValue4);
            BufferRenderer.drawWithGlobalProgram(bufferBuilder6.end());
         }
      }
   }

   private void b(MatrixStack matrixStack1, BipedEntityModel<?> bipedEntityModel2, float floatValue3, int intValue4) {
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      BufferBuilder bufferBuilder5 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
      ModelPart modelPart6 = bipedEntityModel2.getRootPart();
      this.a(matrixStack1, bufferBuilder5, modelPart6, bipedEntityModel2.head, -4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, floatValue3, intValue4);
      this.a(matrixStack1, bufferBuilder5, modelPart6, bipedEntityModel2.body, -4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, floatValue3, intValue4);
      this.a(matrixStack1, bufferBuilder5, modelPart6, bipedEntityModel2.rightArm, -3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, floatValue3, intValue4);
      this.a(matrixStack1, bufferBuilder5, modelPart6, bipedEntityModel2.leftArm, -1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, floatValue3, intValue4);
      this.a(matrixStack1, bufferBuilder5, modelPart6, bipedEntityModel2.rightLeg, -2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, floatValue3, intValue4);
      this.a(matrixStack1, bufferBuilder5, modelPart6, bipedEntityModel2.leftLeg, -2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, floatValue3, intValue4);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder5.end());
   }

   private void a(
      MatrixStack matrixStack1,
      BufferBuilder bufferBuilder2,
      ModelPart modelPart3,
      ModelPart modelPart4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      float floatValue10,
      float floatValue11,
      int intValue12
   ) {
      matrixStack1.push();
      modelPart3.rotate(matrixStack1);
      modelPart4.rotate(matrixStack1);
      Matrix4f matrix4f13 = matrixStack1.peek().getPositionMatrix();
      float floatValue14 = 0.0625F;
      float floatValue15 = floatValue11 * floatValue14;
      float floatValue16 = floatValue5 * floatValue14 - floatValue15;
      float floatValue17 = floatValue6 * floatValue14 - floatValue15;
      float floatValue18 = floatValue7 * floatValue14 - floatValue15;
      float floatValue19 = (floatValue5 + floatValue8) * floatValue14 + floatValue15;
      float floatValue20 = (floatValue6 + floatValue9) * floatValue14 + floatValue15;
      float floatValue21 = (floatValue7 + floatValue10) * floatValue14 + floatValue15;
      this.b(bufferBuilder2, matrix4f13, floatValue16, floatValue20, floatValue18, floatValue16, floatValue20, floatValue21, floatValue19, floatValue20, floatValue21, floatValue19, floatValue20, floatValue18, intValue12);
      this.b(bufferBuilder2, matrix4f13, floatValue16, floatValue17, floatValue21, floatValue16, floatValue17, floatValue18, floatValue19, floatValue17, floatValue18, floatValue19, floatValue17, floatValue21, intValue12);
      this.b(bufferBuilder2, matrix4f13, floatValue16, floatValue17, floatValue18, floatValue16, floatValue20, floatValue18, floatValue19, floatValue20, floatValue18, floatValue19, floatValue17, floatValue18, intValue12);
      this.b(bufferBuilder2, matrix4f13, floatValue19, floatValue17, floatValue21, floatValue19, floatValue20, floatValue21, floatValue16, floatValue20, floatValue21, floatValue16, floatValue17, floatValue21, intValue12);
      this.b(bufferBuilder2, matrix4f13, floatValue16, floatValue17, floatValue21, floatValue16, floatValue20, floatValue21, floatValue16, floatValue20, floatValue18, floatValue16, floatValue17, floatValue18, intValue12);
      this.b(bufferBuilder2, matrix4f13, floatValue19, floatValue17, floatValue18, floatValue19, floatValue20, floatValue18, floatValue19, floatValue20, floatValue21, floatValue19, floatValue17, floatValue21, intValue12);
      matrixStack1.pop();
   }

   private void b(
      MatrixStack matrixStack1,
      BufferBuilder bufferBuilder2,
      ModelPart modelPart3,
      ModelPart modelPart4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      float floatValue10,
      float floatValue11,
      int intValue12
   ) {
      matrixStack1.push();
      modelPart3.rotate(matrixStack1);
      modelPart4.rotate(matrixStack1);
      Matrix4f matrix4f13 = matrixStack1.peek().getPositionMatrix();
      float floatValue14 = 0.0625F;
      float floatValue15 = floatValue11 * floatValue14;
      float floatValue16 = floatValue5 * floatValue14 - floatValue15;
      float floatValue17 = floatValue6 * floatValue14 - floatValue15;
      float floatValue18 = floatValue7 * floatValue14 - floatValue15;
      float floatValue19 = (floatValue5 + floatValue8) * floatValue14 + floatValue15;
      float floatValue20 = (floatValue6 + floatValue9) * floatValue14 + floatValue15;
      float floatValue21 = (floatValue7 + floatValue10) * floatValue14 + floatValue15;
      this.a(bufferBuilder2, matrix4f13, floatValue16, floatValue20, floatValue18, floatValue16, floatValue20, floatValue21, floatValue19, floatValue20, floatValue21, floatValue19, floatValue20, floatValue18, intValue12);
      this.a(bufferBuilder2, matrix4f13, floatValue16, floatValue17, floatValue21, floatValue16, floatValue17, floatValue18, floatValue19, floatValue17, floatValue18, floatValue19, floatValue17, floatValue21, intValue12);
      this.a(bufferBuilder2, matrix4f13, floatValue16, floatValue17, floatValue18, floatValue16, floatValue20, floatValue18, floatValue19, floatValue20, floatValue18, floatValue19, floatValue17, floatValue18, intValue12);
      this.a(bufferBuilder2, matrix4f13, floatValue19, floatValue17, floatValue21, floatValue19, floatValue20, floatValue21, floatValue16, floatValue20, floatValue21, floatValue16, floatValue17, floatValue21, intValue12);
      this.a(bufferBuilder2, matrix4f13, floatValue16, floatValue17, floatValue21, floatValue16, floatValue20, floatValue21, floatValue16, floatValue20, floatValue18, floatValue16, floatValue17, floatValue18, intValue12);
      this.a(bufferBuilder2, matrix4f13, floatValue19, floatValue17, floatValue18, floatValue19, floatValue20, floatValue18, floatValue19, floatValue20, floatValue21, floatValue19, floatValue17, floatValue21, intValue12);
      matrixStack1.pop();
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
      int intValue15
   ) {
      int intValue16 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue15);
      int intValue17 = dev.leg1tka.api.utils.text.ColorUtils.b(intValue15);
      int intValue18 = dev.leg1tka.api.utils.text.ColorUtils.l(intValue15);
      int intValue19 = dev.leg1tka.api.utils.text.ColorUtils.m(intValue15);
      float floatValue20 = this.a(floatValue3, floatValue4, floatValue5);
      float floatValue21 = this.b(floatValue3, floatValue4, floatValue5);
      float floatValue22 = this.a(floatValue6, floatValue7, floatValue8);
      float floatValue23 = this.b(floatValue6, floatValue7, floatValue8);
      float floatValue24 = this.a(floatValue9, floatValue10, floatValue11);
      float floatValue25 = this.b(floatValue9, floatValue10, floatValue11);
      float floatValue26 = this.a(floatValue12, floatValue13, floatValue14);
      float floatValue27 = this.b(floatValue12, floatValue13, floatValue14);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue5).texture(floatValue20, floatValue21).color(intValue16, intValue17, intValue18, intValue19);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue8).texture(floatValue22, floatValue23).color(intValue16, intValue17, intValue18, intValue19);
      bufferBuilder1.vertex(matrix4f2, floatValue9, floatValue10, floatValue11).texture(floatValue24, floatValue25).color(intValue16, intValue17, intValue18, intValue19);
      bufferBuilder1.vertex(matrix4f2, floatValue12, floatValue13, floatValue14).texture(floatValue26, floatValue27).color(intValue16, intValue17, intValue18, intValue19);
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
      int intValue15
   ) {
      int intValue16 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue15);
      int intValue17 = dev.leg1tka.api.utils.text.ColorUtils.b(intValue15);
      int intValue18 = dev.leg1tka.api.utils.text.ColorUtils.l(intValue15);
      int intValue19 = dev.leg1tka.api.utils.text.ColorUtils.m(intValue15);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue5).color(intValue16, intValue17, intValue18, intValue19);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue8).color(intValue16, intValue17, intValue18, intValue19);
      bufferBuilder1.vertex(matrix4f2, floatValue9, floatValue10, floatValue11).color(intValue16, intValue17, intValue18, intValue19);
      bufferBuilder1.vertex(matrix4f2, floatValue12, floatValue13, floatValue14).color(intValue16, intValue17, intValue18, intValue19);
   }

   private float a(float floatValue1, float floatValue2, float floatValue3) {
      return floatValue1 * 1.15F + floatValue3 * 0.72F;
   }

   private float b(float floatValue1, float floatValue2, float floatValue3) {
      return floatValue2 * 1.05F - floatValue3 * 0.38F + floatValue1 * 0.18F;
   }

   private void c(MatrixStack matrixStack1, BipedEntityModel<?> bipedEntityModel2, float floatValue3, int intValue4) {
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      RenderSystem.lineWidth(0.5F);
      if (this.y.getValue()) {
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         int intValue5 = Math.max(1, Math.round(this.A.f()));
         float floatValue6 = Math.max(1.0F, this.z.f());

         for (int intValue7 = intValue5; intValue7 >= 1; intValue7--) {
            float floatValue8 = floatValue3 + (float)intValue7 * 0.5F * floatValue6;
            float floatValue9 = 1.0F / (float)(intValue7 + 1) * 0.7F;
            int intValue10 = Math.max(1, Math.min(255, Math.round((float)dev.leg1tka.api.utils.text.ColorUtils.m(intValue4) * floatValue9)));
            this.d(matrixStack1, bipedEntityModel2, floatValue8, this.a(intValue4, intValue10));
         }
      }

      RenderSystem.defaultBlendFunc();
      this.d(matrixStack1, bipedEntityModel2, floatValue3, intValue4);
      GL11.glDisable(2848);
   }

   private void d(MatrixStack matrixStack1, BipedEntityModel<?> bipedEntityModel2, float floatValue3, int intValue4) {
      BufferBuilder bufferBuilder5 = Tessellator.getInstance().begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
      ModelPart modelPart6 = bipedEntityModel2.getRootPart();
      this.c(matrixStack1, bufferBuilder5, modelPart6, bipedEntityModel2.head, -4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, floatValue3, intValue4);
      this.c(matrixStack1, bufferBuilder5, modelPart6, bipedEntityModel2.body, -4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, floatValue3, intValue4);
      this.c(matrixStack1, bufferBuilder5, modelPart6, bipedEntityModel2.rightArm, -3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, floatValue3, intValue4);
      this.c(matrixStack1, bufferBuilder5, modelPart6, bipedEntityModel2.leftArm, -1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, floatValue3, intValue4);
      this.c(matrixStack1, bufferBuilder5, modelPart6, bipedEntityModel2.rightLeg, -2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, floatValue3, intValue4);
      this.c(matrixStack1, bufferBuilder5, modelPart6, bipedEntityModel2.leftLeg, -2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, floatValue3, intValue4);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder5.end());
   }

   private void c(
      MatrixStack matrixStack1,
      BufferBuilder bufferBuilder2,
      ModelPart modelPart3,
      ModelPart modelPart4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      float floatValue8,
      float floatValue9,
      float floatValue10,
      float floatValue11,
      int intValue12
   ) {
      matrixStack1.push();
      modelPart3.rotate(matrixStack1);
      modelPart4.rotate(matrixStack1);
      float floatValue13 = 0.0625F;
      float floatValue14 = floatValue11 * floatValue13;
      float floatValue15 = floatValue5 * floatValue13 - floatValue14;
      float floatValue16 = floatValue6 * floatValue13 - floatValue14;
      float floatValue17 = floatValue7 * floatValue13 - floatValue14;
      float floatValue18 = (floatValue5 + floatValue8) * floatValue13 + floatValue14;
      float floatValue19 = (floatValue6 + floatValue9) * floatValue13 + floatValue14;
      float floatValue20 = (floatValue7 + floatValue10) * floatValue13 + floatValue14;
      Matrix4f matrix4f21 = matrixStack1.peek().getPositionMatrix();
      this.a(bufferBuilder2, matrix4f21, floatValue15, floatValue16, floatValue17, floatValue18, floatValue16, floatValue17, intValue12);
      this.a(bufferBuilder2, matrix4f21, floatValue18, floatValue16, floatValue17, floatValue18, floatValue16, floatValue20, intValue12);
      this.a(bufferBuilder2, matrix4f21, floatValue18, floatValue16, floatValue20, floatValue15, floatValue16, floatValue20, intValue12);
      this.a(bufferBuilder2, matrix4f21, floatValue15, floatValue16, floatValue20, floatValue15, floatValue16, floatValue17, intValue12);
      this.a(bufferBuilder2, matrix4f21, floatValue15, floatValue19, floatValue17, floatValue18, floatValue19, floatValue17, intValue12);
      this.a(bufferBuilder2, matrix4f21, floatValue18, floatValue19, floatValue17, floatValue18, floatValue19, floatValue20, intValue12);
      this.a(bufferBuilder2, matrix4f21, floatValue18, floatValue19, floatValue20, floatValue15, floatValue19, floatValue20, intValue12);
      this.a(bufferBuilder2, matrix4f21, floatValue15, floatValue19, floatValue20, floatValue15, floatValue19, floatValue17, intValue12);
      this.a(bufferBuilder2, matrix4f21, floatValue15, floatValue16, floatValue17, floatValue15, floatValue19, floatValue17, intValue12);
      this.a(bufferBuilder2, matrix4f21, floatValue18, floatValue16, floatValue17, floatValue18, floatValue19, floatValue17, intValue12);
      this.a(bufferBuilder2, matrix4f21, floatValue18, floatValue16, floatValue20, floatValue18, floatValue19, floatValue20, intValue12);
      this.a(bufferBuilder2, matrix4f21, floatValue15, floatValue16, floatValue20, floatValue15, floatValue19, floatValue20, intValue12);
      matrixStack1.pop();
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, int intValue9) {
      int intValue10 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue9);
      int intValue11 = dev.leg1tka.api.utils.text.ColorUtils.b(intValue9);
      int intValue12 = dev.leg1tka.api.utils.text.ColorUtils.l(intValue9);
      int intValue13 = dev.leg1tka.api.utils.text.ColorUtils.m(intValue9);
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue5).color(intValue10, intValue11, intValue12, intValue13);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue8).color(intValue10, intValue11, intValue12, intValue13);
   }

   private void a(ShaderProgram shaderProgram1, String string2, float floatValue3) {
      GlUniform glUniform4 = shaderProgram1.getUniform(string2);
      if (glUniform4 != null) {
         glUniform4.set(floatValue3);
      }
   }

   public boolean a(PlayerEntity playerEntity1) {
      if (!this.isEnabled() || playerEntity1 == null || !playerEntity1.isAlive()) {
         return false;
      } else if (playerEntity1 != minecraftClient.player) {
         return this.i(playerEntity1) ? this.r.a("Друзей") : this.r.a("Игроков");
      } else {
         return this.r.a("Себя") && minecraftClient.options.getPerspective() != Perspective.FIRST_PERSON;
      }
   }

   public boolean b(PlayerEntity playerEntity1) {
      return this.D.getValue() && this.a(playerEntity1);
   }

   public boolean c(PlayerEntity playerEntity1) {
      return this.E.getValue() && this.a(playerEntity1);
   }

   public boolean d(PlayerEntity playerEntity1) {
      return this.a(playerEntity1);
   }

   public boolean p() {
      return this.isEnabled() && this.q();
   }

   public int e(PlayerEntity playerEntity1) {
      return this.b(this.g(playerEntity1));
   }

   public int f(PlayerEntity playerEntity1) {
      return this.b(this.h(playerEntity1));
   }

   private int g(PlayerEntity playerEntity1) {
      return this.i(playerEntity1) ? o : this.a(dev.leg1tka.api.utils.text.ColorUtils.a(), 1.18F, 1.12F, 130);
   }

   private int h(PlayerEntity playerEntity1) {
      return this.i(playerEntity1) ? p : this.a(dev.leg1tka.api.utils.text.ColorUtils.a(), 1.12F, 1.08F, 255);
   }

   private int b(int intValue1) {
      if (!this.B.getValue()) {
         return intValue1;
      } else {
         float floatValue2 = (float)(System.currentTimeMillis() - this.F) / 1000.0F;
         float floatValue3 = (float)((Math.sin((double)(floatValue2 * this.C.f()) * Math.PI) + 1.0) * 0.5);
         float floatValue4 = 0.65F + 0.35F * floatValue3;
         return dev.leg1tka.api.utils.text.ColorUtils.d(intValue1, floatValue4);
      }
   }

   private int a(int intValue1, float floatValue2, float floatValue3, int intValue4) {
      float[] floatValueArray5 = Color.RGBtoHSB(dev.leg1tka.api.utils.text.ColorUtils.a(intValue1), dev.leg1tka.api.utils.text.ColorUtils.b(intValue1), dev.leg1tka.api.utils.text.ColorUtils.l(intValue1), null);
      float floatValue6 = MathHelper.clamp(floatValueArray5[1] * floatValue2, 0.0F, 1.0F);
      float floatValue7 = MathHelper.clamp(Math.max(floatValueArray5[2], 0.8F) * floatValue3, 0.0F, 1.0F);
      int intValue8 = Color.HSBtoRGB(floatValueArray5[0], floatValue6, floatValue7);
      return dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(intValue8), dev.leg1tka.api.utils.text.ColorUtils.b(intValue8), dev.leg1tka.api.utils.text.ColorUtils.l(intValue8), intValue4);
   }

   private int a(int intValue1, int intValue2) {
      return intValue1 & 16777215 | (intValue2 & 0xFF) << 24;
   }

   private boolean i(PlayerEntity playerEntity1) {
      return AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.friendStorage != null && AspectDLC.INSTANCE.friendStorage.c(playerEntity1.getName().getString());
   }

   private boolean q() {
      if (this.isEnabled() && minecraftClient.world != null && minecraftClient.player != null) {
         for (PlayerEntity playerEntity2 : minecraftClient.world.getPlayers()) {
            if (this.d(playerEntity2)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean r() {
      if (minecraftClient.worldRenderer == null) {
         this.G = false;
         return false;
      } else if (minecraftClient.worldRenderer.getEntityOutlinesFramebuffer() != null) {
         this.G = true;
         return true;
      } else {
         try {
            minecraftClient.worldRenderer.loadEntityOutlinePostProcessor();
         } catch (Exception exception2) {
         }

         this.G = minecraftClient.worldRenderer.getEntityOutlinesFramebuffer() != null;
         if (!this.G) {
            this.H = System.currentTimeMillis() + 3000L;
         }

         return this.G;
      }
   }
}
