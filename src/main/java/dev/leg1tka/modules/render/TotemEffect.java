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

import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.Event3DRender;
import dev.leg1tka.api.events.implement.EventAttackEntity;
import dev.leg1tka.api.events.implement.EventPopTotem;
import dev.leg1tka.mixin.LivingEntityRendererAccessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class TotemEffect extends Module {
   public static TotemEffect c = new TotemEffect();
   private static final Identifier d = dev.leg1tka.api.utils.resources.ResourceIdentifiers.a("textures/particle/bloom.png");
   private static long e;
   private static float f;
   private static float g;
   private static float h;
   private static float i;
   private static float j;
   private static int k;
   private static int l;
   private static final int[][] m = new int[][]{{0, 1}, {1, 2}, {2, 3}, {3, 0}, {4, 5}, {5, 6}, {6, 7}, {7, 4}, {0, 4}, {1, 5}, {2, 6}, {3, 7}};
   private static final int[][] n = new int[][]{{-1, -1, -1}, {1, -1, -1}, {1, -1, 1}, {-1, -1, 1}, {-1, 1, -1}, {1, 1, -1}, {1, 1, 1}, {-1, 1, 1}};
   private final BooleanSetting o = new BooleanSetting("Только свои цели", true);
   private final ModeSetting p = new ModeSetting(
      "Плотность",
      "Средняя",
      "Низкая",
      "Средняя",
      "Высокая"
   );
   private final NumberSetting q = new NumberSetting("Длительность", 1.8F, 0.8F, 4.0F, 0.1F);
   private final NumberSetting r = new NumberSetting("Сила разлёта", 1.0F, 0.3F, 2.5F, 0.1F);
   final BooleanSetting s = new BooleanSetting("Отскок от земли", true);
   private final BooleanSetting t = new BooleanSetting("Звук", true);
   private final BooleanSetting u = new BooleanSetting("Чамсы на цели", true);
   private final NumberSetting v = new NumberSetting("Время чамсов", 3.0F, 1.0F, 8.0F, 0.1F).a(this.u::getValue);
   private final Map<Integer, Long> w = new HashMap<>();
   private final Set<Integer> x = Collections.synchronizedSet(new LinkedHashSet<>());
   private final List<TotemEffect.c> y = new ArrayList<>();
   private final List<TotemEffect.a> z = new ArrayList<>();
   private final Random A = new Random();
   private final int[] B = new int[41];
   private final Matrix4f C = new Matrix4f();
   private final Matrix4f D = new Matrix4f();
   private final MatrixStack E = new MatrixStack();
   private final Vector3f F = new Vector3f();
   private final Vector3f G = new Vector3f();
   private static final List H = q();

   public TotemEffect() {
      super(
         "TotemEffect",
         "Цель с тотемом застывает и разлетается на осколки",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.o, this.p, this.q, this.r, this.s, this.t, this.u, this.v});
   }

   @Override
   public void onDisable() {
      this.w.clear();
      this.x.clear();
      this.y.clear();
      this.z.clear();
      super.onDisable();
   }

   @EventLink
   public void onAttack(EventAttackEntity eventAttackEntity1) {
      if (eventAttackEntity1.getTarget() instanceof PlayerEntity playerEntity2 && playerEntity2 != minecraftClient.player) {
         this.w.put(playerEntity2.getId(), System.currentTimeMillis());
      }
   }

   @EventLink
   public void onPopTotem(EventPopTotem eventPopTotem1) {
      PlayerEntity playerEntity2 = eventPopTotem1.getPlayer();
      if (playerEntity2 != null && playerEntity2 != minecraftClient.player) {
         if (this.o.getValue()) {
            Long long3 = (Long)this.w.get(playerEntity2.getId());
            if (long3 == null || System.currentTimeMillis() - long3 > 2500L) {
               return;
            }
         }

         this.x.add(playerEntity2.getId());
      }
   }

   @EventLink
   public void onRender3D(Event3DRender event3DRender1) {
      if (minecraftClient.world != null && minecraftClient.player != null) {
         if (!this.x.isEmpty()) {
            this.a(event3DRender1.getTickDelta());
         }

         if (this.w.size() > 64) {
            long longValue2 = System.currentTimeMillis();
            this.w.values().removeIf(long2x -> longValue2 - long2x > 2500L);
         }

         if (!this.y.isEmpty() || !this.z.isEmpty()) {
            for (int intValue20 = 0; intValue20 < 41; intValue20++) {
               this.B[intValue20] = dev.leg1tka.api.utils.text.ColorUtils.k(intValue20);
            }

            Vec3d vec3d21 = event3DRender1.getCamera().getPos();
            Quaternionf quaternionf3 = event3DRender1.getCamera().getRotation();
            this.F.set(1.0F, 0.0F, 0.0F).rotate(quaternionf3);
            this.G.set(0.0F, 1.0F, 0.0F).rotate(quaternionf3);
            Matrix4f matrix4f4 = event3DRender1.getMatrices().peek().getPositionMatrix();
            long longValue5 = System.nanoTime();
            float floatValue7 = this.q.f();
            RenderSystem.enableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.disableCull();

            try {
               for (int intValue8 = this.y.size() - 1; intValue8 >= 0; intValue8--) {
                  TotemEffect.c value9 = (TotemEffect.c)this.y.get(intValue8);
                  value9.g = value9.g + Math.min(0.1F, (float)(longValue5 - value9.f) / 1.0E9F);
                  value9.f = longValue5;

                  while (value9.g >= 0.008333334F) {
                     value9.g -= 0.008333334F;
                     value9.h += 0.008333334F;
                     value9.a(value9.h, 0.008333334F);
                  }

                  float floatValue10 = (float)(longValue5 - value9.e) / 1.0E9F;
                  if (floatValue10 >= floatValue7) {
                     this.y.remove(intValue8);
                  } else {
                     float floatValue11 = (float)(value9.a.x - vec3d21.x);
                     float floatValue12 = (float)(value9.a.y + (double)value9.c.y - vec3d21.y);
                     float floatValue13 = (float)(value9.a.z - vec3d21.z);
                     float floatValue14 = MathHelper.clamp((MathHelper.sqrt(floatValue11 * floatValue11 + floatValue12 * floatValue12 + floatValue13 * floatValue13) - 0.8F) / 1.4F, 0.3F, 1.0F);
                     this.C.set(matrix4f4).translate((float)(value9.a.x - vec3d21.x), (float)(value9.a.y - vec3d21.y), (float)(value9.a.z - vec3d21.z));
                     this.a(floatValue10, floatValue14);
                     this.a(value9, floatValue10, floatValue7, floatValue14);
                     this.a(value9, floatValue10, floatValue14);
                  }
               }

               for (int intValue22 = this.z.size() - 1; intValue22 >= 0; intValue22--) {
                  TotemEffect.a value23 = (TotemEffect.a)this.z.get(intValue22);
                  float floatValue24 = (float)(longValue5 - value23.b) / 1.0E9F - 0.24F;
                  if (floatValue24 >= this.v.f() || !this.u.getValue() || !(minecraftClient.world.getEntityById(value23.a) instanceof AbstractClientPlayerEntity abstractClientPlayerEntity25) || !abstractClientPlayerEntity25.isAlive()) {
                     this.z.remove(intValue22);
                  } else if (!(floatValue24 < 0.0F) && !abstractClientPlayerEntity25.isInvisible()) {
                     this.a(value23, abstractClientPlayerEntity25, matrix4f4, vec3d21, event3DRender1.getTickDelta(), floatValue24);
                  }
               }
            } catch (RuntimeException runtimeException18) {
               this.y.clear();
               this.z.clear();
            } finally {
               RenderSystem.enableCull();
               RenderSystem.depthMask(true);
               RenderSystem.enableDepthTest();
               RenderSystem.defaultBlendFunc();
               RenderSystem.disableBlend();
            }
         }
      } else {
         this.y.clear();
         this.z.clear();
      }
   }

   private void a(float floatValue1) {
      Integer[] integerArray2;
      synchronized (this.x) {
         integerArray2 = this.x.toArray(new Integer[0]);
         this.x.clear();
      }

      Integer[] integerArray12 = integerArray2;
      int intValue4 = integerArray2.length;

      for (int intValue5 = 0; intValue5 < intValue4; intValue5++) {
         int intValue6 = integerArray12[intValue5];
         Entity entity8 = minecraftClient.world.getEntityById(intValue6);
         if (entity8 instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity abstractClientPlayerEntity7 = (AbstractClientPlayerEntity)entity8;
            TotemEffect.c value13;
            try {
               value13 = this.a(abstractClientPlayerEntity7, floatValue1);
            } catch (RuntimeException runtimeException11) {
               continue;
            }

            if (value13 != null && !value13.d.isEmpty()) {
               this.y.add(value13);
               if (this.u.getValue()) {
                  this.b(intValue6);
               }

               if (this.t.getValue()) {
                  minecraftClient.world
                     .playSound(
                        abstractClientPlayerEntity7.getX(),
                        abstractClientPlayerEntity7.getY(),
                        abstractClientPlayerEntity7.getZ(),
                        SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK,
                        SoundCategory.PLAYERS,
                        1.0F,
                        0.75F + this.A.nextFloat() * 0.2F,
                        false
                     );
                  minecraftClient.world.playSound(abstractClientPlayerEntity7.getX(), abstractClientPlayerEntity7.getY(), abstractClientPlayerEntity7.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 0.6F, 1.4F, false);
               }
            }
         }
      }
   }

   private float p() {
      if (this.p.b("Низкая")) {
         return 4.0F;
      } else {
         return this.p.b("Высокая") ? 2.0F : 2.6666667F;
      }
   }

   private TotemEffect.c a(AbstractClientPlayerEntity abstractClientPlayerEntity1, float floatValue2) {
      if (minecraftClient.getEntityRenderDispatcher().getRenderer(abstractClientPlayerEntity1) instanceof PlayerEntityRenderer playerEntityRenderer4) {
         PlayerEntityRenderState playerEntityRenderState5 = playerEntityRenderer4.createRenderState();
         playerEntityRenderer4.updateRenderState(abstractClientPlayerEntity1, playerEntityRenderState5, floatValue2);
         PlayerEntityModel playerEntityModel6 = (PlayerEntityModel)playerEntityRenderer4.getModel();
         playerEntityModel6.setAngles(playerEntityRenderState5);
         MatrixStack matrixStack7 = new MatrixStack();
         float floatValue8 = playerEntityRenderState5.baseScale;
         matrixStack7.scale(floatValue8, floatValue8, floatValue8);
         LivingEntityRendererAccessor livingEntityRendererAccessor9 = (LivingEntityRendererAccessor)playerEntityRenderer4;
         livingEntityRendererAccessor9.AspectDLC$setupTransforms(playerEntityRenderState5, matrixStack7, playerEntityRenderState5.bodyYaw, floatValue8);
         matrixStack7.scale(-1.0F, -1.0F, 1.0F);
         livingEntityRendererAccessor9.AspectDLC$scale(playerEntityRenderState5, matrixStack7);
         matrixStack7.translate(0.0F, -1.501F, 0.0F);
         TotemEffect.c value10 = new TotemEffect.c(abstractClientPlayerEntity1.getLerpedPos(floatValue2), abstractClientPlayerEntity1.isOnGround(), abstractClientPlayerEntity1.getHeight());
         ModelPart modelPart11 = playerEntityModel6.getRootPart();
         float floatValue12 = this.p();
         this.a(value10, matrixStack7, modelPart11, playerEntityModel6.head, floatValue12, -4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F);
         this.a(value10, matrixStack7, modelPart11, playerEntityModel6.body, floatValue12, -4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F);
         this.a(value10, matrixStack7, modelPart11, playerEntityModel6.rightArm, floatValue12, -3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F);
         this.a(value10, matrixStack7, modelPart11, playerEntityModel6.leftArm, floatValue12, -1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F);
         this.a(value10, matrixStack7, modelPart11, playerEntityModel6.rightLeg, floatValue12, -2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F);
         this.a(value10, matrixStack7, modelPart11, playerEntityModel6.leftLeg, floatValue12, -2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F);
         value10.a(this.A, this.r.f());
         return value10;
      } else {
         return null;
      }
   }

   private void a(
      TotemEffect.c value1, MatrixStack matrixStack2, ModelPart modelPart3, ModelPart modelPart4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, float floatValue9, float floatValue10, float floatValue11
   ) {
      if (modelPart4.visible) {
         matrixStack2.push();
         modelPart3.rotate(matrixStack2);
         modelPart4.rotate(matrixStack2);
         Matrix4f matrix4f12 = matrixStack2.peek().getPositionMatrix();
         int intValue13 = Math.max(1, Math.round(floatValue9 / floatValue5));
         int intValue14 = Math.max(1, Math.round(floatValue10 / floatValue5));
         int intValue15 = Math.max(1, Math.round(floatValue11 / floatValue5));
         float floatValue16 = floatValue9 / (float)intValue13;
         float floatValue17 = floatValue10 / (float)intValue14;
         float floatValue18 = floatValue11 / (float)intValue15;
         boolean[] flagArray19 = new boolean[intValue13 * intValue14 * intValue15];

         for (int intValue20 = 0; intValue20 < intValue13; intValue20++) {
            for (int intValue21 = 0; intValue21 < intValue14; intValue21++) {
               for (int intValue22 = 0; intValue22 < intValue15; intValue22++) {
                  if (a(intValue20, intValue21, intValue22, intValue13, intValue14, intValue15) && !flagArray19[(intValue20 * intValue14 + intValue21) * intValue15 + intValue22]) {
                     int intValue23 = this.A.nextFloat() < 0.4F ? 2 : 1;
                     int intValue24 = this.A.nextFloat() < 0.5F ? 2 : 1;
                     int intValue25 = this.A.nextFloat() < 0.4F ? 2 : 1;
                     if (!a(flagArray19, intValue20, intValue21, intValue22, intValue23, intValue24, intValue25, intValue13, intValue14, intValue15)) {
                        intValue25 = 1;
                     }

                     if (!a(flagArray19, intValue20, intValue21, intValue22, intValue23, intValue24, intValue25, intValue13, intValue14, intValue15)) {
                        intValue23 = 1;
                     }

                     if (!a(flagArray19, intValue20, intValue21, intValue22, intValue23, intValue24, intValue25, intValue13, intValue14, intValue15)) {
                        intValue24 = 1;
                     }

                     for (int intValue26 = intValue20; intValue26 < intValue20 + intValue23; intValue26++) {
                        for (int intValue27 = intValue21; intValue27 < intValue21 + intValue24; intValue27++) {
                           for (int intValue28 = intValue22; intValue28 < intValue22 + intValue25; intValue28++) {
                              flagArray19[(intValue26 * intValue14 + intValue27) * intValue15 + intValue28] = true;
                           }
                        }
                     }

                     Matrix4f matrix4f29 = new Matrix4f(matrix4f12)
                        .translate(
                           (floatValue6 + ((float)intValue20 + (float)intValue23 * 0.5F) * floatValue16) / 16.0F,
                           (floatValue7 + ((float)intValue21 + (float)intValue24 * 0.5F) * floatValue17) / 16.0F,
                           (floatValue8 + ((float)intValue22 + (float)intValue25 * 0.5F) * floatValue18) / 16.0F
                        );
                     Vector3f vector3f30 = matrix4f29.getTranslation(new Vector3f());
                     matrix4f29.setTranslation(0.0F, 0.0F, 0.0F);
                     value1.d
                        .add(
                           new TotemEffect.d(
                              vector3f30,
                              matrix4f29,
                              (float)intValue23 * floatValue16 / 32.0F,
                              (float)intValue24 * floatValue17 / 32.0F,
                              (float)intValue25 * floatValue18 / 32.0F,
                              intValue23 * intValue24 * intValue25 >= 2
                           )
                        );
                  }
               }
            }
         }

         matrixStack2.pop();
      }
   }

   private static boolean a(int intValue0, int intValue1, int intValue2, int intValue3, int intValue4, int intValue5) {
      return intValue0 == 0 || intValue1 == 0 || intValue2 == 0 || intValue0 == intValue3 - 1 || intValue1 == intValue4 - 1 || intValue2 == intValue5 - 1;
   }

   private static boolean a(boolean[] flagArray0, int intValue1, int intValue2, int intValue3, int intValue4, int intValue5, int intValue6, int intValue7, int intValue8, int intValue9) {
      if (intValue1 + intValue4 <= intValue7 && intValue2 + intValue5 <= intValue8 && intValue3 + intValue6 <= intValue9) {
         for (int intValue10 = intValue1; intValue10 < intValue1 + intValue4; intValue10++) {
            for (int intValue11 = intValue2; intValue11 < intValue2 + intValue5; intValue11++) {
               for (int intValue12 = intValue3; intValue12 < intValue3 + intValue6; intValue12++) {
                  if (flagArray0[(intValue10 * intValue8 + intValue11) * intValue9 + intValue12] || !a(intValue10, intValue11, intValue12, intValue7, intValue8, intValue9)) {
                     return false;
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void a(TotemEffect.c value1, float floatValue2, float floatValue3, float floatValue4) {
      float floatValue5 = MathHelper.clamp(floatValue2 / 0.08F, 0.0F, 1.0F);
      float floatValue6 = 1.0F - b(MathHelper.clamp(floatValue2 / 0.35F, 0.0F, 1.0F));
      float floatValue7 = MathHelper.clamp((floatValue2 - 0.08F) / 0.16F, 0.0F, 1.0F);
      float floatValue8 = floatValue3 * 0.55F;
      float floatValue9 = b(MathHelper.clamp((floatValue2 - floatValue8) / (floatValue3 - floatValue8), 0.0F, 1.0F));
      float floatValue10 = floatValue5 * (1.0F - floatValue9) * floatValue4;
      if (!(floatValue10 <= 0.01F)) {
         RenderSystem.blendFuncSeparate(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA, SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
         BufferBuilder bufferBuilder11 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
         float floatValue12 = floatValue7 * 0.006F;
         float floatValue13 = MathHelper.clamp(1.0F - floatValue9 * 0.9F, 0.05F, 1.0F);

         for (TotemEffect.d value15 : value1.d) {
            Matrix4f matrix4f16 = value15.c.set(this.C);
            if (floatValue2 < value15.m) {
               matrix4f16.translate(
                  value15.a.x + MathHelper.sin(floatValue2 * 70.0F + value15.n) * floatValue12,
                  value15.a.y + MathHelper.sin(floatValue2 * 83.0F + value15.n * 1.3F) * floatValue12,
                  value15.a.z + MathHelper.sin(floatValue2 * 77.0F + value15.n * 1.7F) * floatValue12
               );
               matrix4f16.mul(value15.b);
            } else {
               matrix4f16.translate(value15.a.x + value15.h.x, value15.a.y + value15.h.y, value15.a.z + value15.h.z);
               matrix4f16.rotate(value15.k, value15.j);
               matrix4f16.scale(floatValue13);
               matrix4f16.mul(value15.b);
            }

            int intValue17 = dev.leg1tka.api.utils.text.ColorUtils.d(this.B[value15.o], -1, floatValue6 * 0.4F);
            a(bufferBuilder11, matrix4f16, value15.d, value15.e, value15.f, intValue17, floatValue10 * 0.55F);
         }

         a(bufferBuilder11);
         RenderSystem.blendFuncSeparate(SrcFactor.SRC_ALPHA, DstFactor.ONE, SrcFactor.ZERO, DstFactor.ONE);
         BufferBuilder bufferBuilder20 = Tessellator.getInstance().begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

         for (TotemEffect.d value22 : value1.d) {
            float floatValue23 = floatValue2 - value22.m;
            float floatValue18;
            if (floatValue23 <= 0.0F) {
               floatValue18 = floatValue7 * 0.5F;
            } else {
               if (!value22.g) {
                  continue;
               }

               floatValue18 = 1.0F - floatValue23 / 0.35F;
            }

            if (!(floatValue18 <= 0.02F)) {
               int intValue19 = dev.leg1tka.api.utils.text.ColorUtils.d(this.B[value22.o], -1, floatValue18 * 0.35F);
               b(bufferBuilder20, value22.c, value22.d, value22.e, value22.f, intValue19, floatValue10 * floatValue18 * 0.8F);
            }
         }

         a(bufferBuilder20);
      }
   }

   private void a(float floatValue1, float floatValue2) {
      float floatValue3 = (floatValue1 - 0.192F) / 0.7F;
      if (!(floatValue3 <= 0.0F) && !(floatValue3 >= 1.0F)) {
         float floatValue4 = 1.0F - (1.0F - floatValue3) * (1.0F - floatValue3) * (1.0F - floatValue3);
         float floatValue5 = 0.3F + floatValue4 * 2.0F;
         float floatValue6 = 0.04F + (1.0F - floatValue3) * 0.1F;
         float floatValue7 = (1.0F - floatValue3) * (1.0F - floatValue3) * floatValue2;
         int intValue8 = dev.leg1tka.api.utils.text.ColorUtils.a();
         int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue8);
         int intValue10 = dev.leg1tka.api.utils.text.ColorUtils.b(intValue8);
         int intValue11 = dev.leg1tka.api.utils.text.ColorUtils.l(intValue8);
         float floatValue12 = 0.03F;
         RenderSystem.blendFuncSeparate(SrcFactor.SRC_ALPHA, DstFactor.ONE, SrcFactor.ZERO, DstFactor.ONE);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
         BufferBuilder bufferBuilder13 = Tessellator.getInstance().begin(DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
         int intValue14 = Math.max(1, (int)(floatValue7 * 140.0F));

         for (int intValue15 = 0; intValue15 <= 64; intValue15++) {
            float floatValue16 = (float)((double)intValue15 * Math.PI * 2.0 / 64.0);
            float floatValue17 = MathHelper.cos(floatValue16);
            float floatValue18 = MathHelper.sin(floatValue16);
            bufferBuilder13.vertex(this.C, floatValue17 * (floatValue5 - floatValue6), floatValue12, floatValue18 * (floatValue5 - floatValue6)).color(intValue9, intValue10, intValue11, 0);
            bufferBuilder13.vertex(this.C, floatValue17 * floatValue5, floatValue12, floatValue18 * floatValue5).color(intValue9, intValue10, intValue11, intValue14);
         }

         a(bufferBuilder13);
         bufferBuilder13 = Tessellator.getInstance().begin(DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
         int intValue22 = Math.max(1, (int)(floatValue7 * 90.0F));
         float floatValue23 = floatValue5 + floatValue6 * 0.5F;

         for (int intValue24 = 0; intValue24 <= 64; intValue24++) {
            float floatValue25 = (float)((double)intValue24 * Math.PI * 2.0 / 64.0);
            float floatValue19 = MathHelper.cos(floatValue25);
            float floatValue20 = MathHelper.sin(floatValue25);
            bufferBuilder13.vertex(this.C, floatValue19 * floatValue5, floatValue12, floatValue20 * floatValue5).color(255, 255, 255, intValue22);
            bufferBuilder13.vertex(this.C, floatValue19 * floatValue23, floatValue12, floatValue20 * floatValue23).color(intValue9, intValue10, intValue11, 0);
         }

         a(bufferBuilder13);
      }
   }

   private void a(TotemEffect.c value1, float floatValue2, float floatValue3) {
      float floatValue4 = a(floatValue2, 0.204F, 0.4F);
      if (!(floatValue4 <= 0.01F)) {
         RenderSystem.blendFuncSeparate(SrcFactor.SRC_ALPHA, DstFactor.ONE, SrcFactor.ZERO, DstFactor.ONE);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
         RenderSystem.setShaderTexture(0, d);
         BufferBuilder bufferBuilder5 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
         Vector3f vector3f6 = value1.c;
         this.a(bufferBuilder5, vector3f6.x, vector3f6.y, vector3f6.z, 0.6F + floatValue4 * 0.8F, -1, floatValue4 * 0.22F * floatValue3);
         this.a(bufferBuilder5, vector3f6.x, vector3f6.y, vector3f6.z, 1.0F + floatValue4 * 1.2F, this.B[0], floatValue4 * 0.3F * floatValue3);
         a(bufferBuilder5);
      }
   }

   private void b(int intValue1) {
      for (TotemEffect.a value3 : this.z) {
         if (value3.a == intValue1) {
            value3.b = System.nanoTime();
            return;
         }
      }

      this.z.add(new TotemEffect.a(intValue1));
   }

   private void a(TotemEffect.a value1, AbstractClientPlayerEntity abstractClientPlayerEntity2, Matrix4f matrix4f3, Vec3d vec3d4, float floatValue5, float floatValue6) {
      if (minecraftClient.getEntityRenderDispatcher().getRenderer(abstractClientPlayerEntity2) instanceof PlayerEntityRenderer playerEntityRenderer8) {
         if (value1.c != playerEntityRenderer8) {
            value1.c = playerEntityRenderer8;
            value1.d = playerEntityRenderer8.createRenderState();
         }

         PlayerEntityRenderState playerEntityRenderState9 = value1.d;
         playerEntityRenderer8.updateRenderState(abstractClientPlayerEntity2, playerEntityRenderState9, floatValue5);
         PlayerEntityModel playerEntityModel10 = (PlayerEntityModel)playerEntityRenderer8.getModel();
         playerEntityModel10.setAngles(playerEntityRenderState9);
         this.E.push();
         this.E.peek().getPositionMatrix().identity();
         this.E.peek().getNormalMatrix().identity();
         float floatValue11 = playerEntityRenderState9.baseScale;
         this.E.scale(floatValue11, floatValue11, floatValue11);
         LivingEntityRendererAccessor livingEntityRendererAccessor12 = (LivingEntityRendererAccessor)playerEntityRenderer8;
         livingEntityRendererAccessor12.AspectDLC$setupTransforms(playerEntityRenderState9, this.E, playerEntityRenderState9.bodyYaw, floatValue11);
         this.E.scale(-1.0F, -1.0F, 1.0F);
         livingEntityRendererAccessor12.AspectDLC$scale(playerEntityRenderState9, this.E);
         this.E.translate(0.0F, -1.501F, 0.0F);
         ModelPart modelPart13 = playerEntityModel10.getRootPart();

         for (int intValue14 = 0; intValue14 < 6; intValue14++) {
            ModelPart modelPart15 = switch (intValue14) {
               case 0 -> playerEntityModel10.head;
               case 1 -> playerEntityModel10.body;
               case 2 -> playerEntityModel10.rightArm;
               case 3 -> playerEntityModel10.leftArm;
               case 4 -> playerEntityModel10.rightLeg;
               default -> playerEntityModel10.leftLeg;
            };
            value1.f[intValue14] = modelPart15.visible;
            this.E.push();
            modelPart13.rotate(this.E);
            modelPart15.rotate(this.E);
            value1.e[intValue14].set(this.E.peek().getPositionMatrix());
            this.E.pop();
         }

         this.E.pop();
         double doubleValue33 = MathHelper.lerp((double)floatValue5, abstractClientPlayerEntity2.prevX, abstractClientPlayerEntity2.getX());
         double doubleValue16 = MathHelper.lerp((double)floatValue5, abstractClientPlayerEntity2.prevY, abstractClientPlayerEntity2.getY());
         double doubleValue18 = MathHelper.lerp((double)floatValue5, abstractClientPlayerEntity2.prevZ, abstractClientPlayerEntity2.getZ());
         this.C.set(matrix4f3).translate((float)(doubleValue33 - vec3d4.x), (float)(doubleValue16 - vec3d4.y), (float)(doubleValue18 - vec3d4.z));
         float floatValue20 = this.v.f();
         float floatValue21 = MathHelper.clamp(floatValue6 / 0.45F, 0.0F, 1.0F);
         float floatValue22 = Math.min(1.4F, floatValue20 * 0.45F);
         float floatValue23 = MathHelper.clamp((floatValue6 - (floatValue20 - floatValue22)) / floatValue22, 0.0F, 1.0F);
         float floatValue24 = 1.0F - b(MathHelper.clamp(floatValue6 / 0.5F, 0.0F, 1.0F));
         float floatValue25 = floatValue6 * 0.8F % 1.4F - 0.2F;
         RenderSystem.blendFuncSeparate(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA, SrcFactor.ONE, DstFactor.ONE_MINUS_SRC_ALPHA);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
         BufferBuilder bufferBuilder26 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);

         for (int intValue27 = 0; intValue27 < H.size(); intValue27++) {
            TotemEffect.b value28 = (TotemEffect.b)H.get(intValue27);
            float floatValue29 = this.a(value1, value28, floatValue6, floatValue21, floatValue23);
            if (!(floatValue29 <= 0.01F)) {
               float floatValue30 = Math.max(0.0F, 1.0F - Math.abs(value28.h01 - floatValue25) * 5.0F);
               float floatValue31 = 0.85F + 0.15F * MathHelper.sin(floatValue6 * 4.0F + value28.h01 * 6.0F);
               int intValue32 = dev.leg1tka.api.utils.text.ColorUtils.d(this.B[value28.colorIndex], -1, Math.max(floatValue24 * 0.4F, floatValue30 * 0.3F));
               a(bufferBuilder26, this.D, value28.hx, value28.hy, value28.hz, intValue32, floatValue29 * 0.38F * floatValue31);
            }
         }

         a(bufferBuilder26);
         RenderSystem.blendFuncSeparate(SrcFactor.SRC_ALPHA, DstFactor.ONE, SrcFactor.ZERO, DstFactor.ONE);
         BufferBuilder bufferBuilder34 = Tessellator.getInstance().begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

         for (int intValue35 = 0; intValue35 < H.size(); intValue35++) {
            TotemEffect.b value36 = (TotemEffect.b)H.get(intValue35);
            float floatValue37 = this.a(value1, value36, floatValue6, floatValue21, floatValue23);
            if (!(floatValue37 <= 0.01F)) {
               float floatValue38 = Math.max(0.0F, 1.0F - Math.abs(value36.h01 - floatValue25) * 5.0F);
               int intValue39 = dev.leg1tka.api.utils.text.ColorUtils.d(this.B[value36.colorIndex], -1, Math.max(floatValue24, floatValue38) * 0.35F);
               b(bufferBuilder34, this.D, value36.hx, value36.hy, value36.hz, intValue39, floatValue37 * (0.35F + floatValue38 * 0.35F));
            }
         }

         a(bufferBuilder34);
      }
   }

   private float a(TotemEffect.a value1, TotemEffect.b value2, float floatValue3, float floatValue4, float floatValue5) {
      if (!value1.f[value2.part]) {
         return 0.0F;
      } else {
         float floatValue6 = b(MathHelper.clamp(floatValue4 * 1.4F - value2.h01 * 0.4F, 0.0F, 1.0F));
         float floatValue7 = b(MathHelper.clamp(floatValue5 * 1.4F - (1.0F - value2.h01) * 0.4F, 0.0F, 1.0F));
         float floatValue8 = floatValue6 * (1.0F - floatValue7);
         if (floatValue8 <= 0.01F) {
            return 0.0F;
         } else {
            float floatValue9 = (1.06F + (1.0F - floatValue6) * 0.35F) * (1.0F - floatValue7 * 0.5F);
            this.D.set(this.C).translate(0.0F, floatValue7 * 0.25F, 0.0F).mul(value1.e[value2.part]).translate(value2.cx, value2.cy, value2.cz).scale(floatValue9);
            return floatValue8;
         }
      }
   }

   private static float b(float floatValue0) {
      return floatValue0 * floatValue0 * (3.0F - 2.0F * floatValue0);
   }

   private static void a(BufferBuilder bufferBuilder0) {
      BuiltBuffer builtBuffer1 = bufferBuilder0.endNullable();
      if (builtBuffer1 != null) {
         BufferRenderer.drawWithGlobalProgram(builtBuffer1);
      }
   }

   private static float a(float floatValue0, float floatValue1, float floatValue2) {
      float floatValue3 = (floatValue0 - floatValue1) / floatValue2;
      if (!(floatValue3 < 0.0F) && !(floatValue3 > 1.0F)) {
         return floatValue3 < 0.15F ? floatValue3 / 0.15F : (1.0F - floatValue3) / 0.85F;
      } else {
         return 0.0F;
      }
   }

   private void a(BufferBuilder bufferBuilder1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, float floatValue7) {
      float floatValue8 = floatValue5 * 0.5F;
      float floatValue9 = this.F.x * floatValue8;
      float floatValue10 = this.F.y * floatValue8;
      float floatValue11 = this.F.z * floatValue8;
      float floatValue12 = this.G.x * floatValue8;
      float floatValue13 = this.G.y * floatValue8;
      float floatValue14 = this.G.z * floatValue8;
      int intValue15 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue6);
      int intValue16 = dev.leg1tka.api.utils.text.ColorUtils.b(intValue6);
      int intValue17 = dev.leg1tka.api.utils.text.ColorUtils.l(intValue6);
      int intValue18 = Math.max(1, Math.min(255, (int)(floatValue7 * 255.0F)));
      bufferBuilder1.vertex(this.C, floatValue2 - floatValue9 - floatValue12, floatValue3 - floatValue10 - floatValue13, floatValue4 - floatValue11 - floatValue14).texture(0.0F, 1.0F).color(intValue15, intValue16, intValue17, intValue18);
      bufferBuilder1.vertex(this.C, floatValue2 - floatValue9 + floatValue12, floatValue3 - floatValue10 + floatValue13, floatValue4 - floatValue11 + floatValue14).texture(0.0F, 0.0F).color(intValue15, intValue16, intValue17, intValue18);
      bufferBuilder1.vertex(this.C, floatValue2 + floatValue9 + floatValue12, floatValue3 + floatValue10 + floatValue13, floatValue4 + floatValue11 + floatValue14).texture(1.0F, 0.0F).color(intValue15, intValue16, intValue17, intValue18);
      bufferBuilder1.vertex(this.C, floatValue2 + floatValue9 - floatValue12, floatValue3 + floatValue10 - floatValue13, floatValue4 + floatValue11 - floatValue14).texture(1.0F, 1.0F).color(intValue15, intValue16, intValue17, intValue18);
   }

   private static void a(BufferBuilder bufferBuilder0, Matrix4f matrix4f1, float floatValue2, float floatValue3, float floatValue4, int intValue5, float floatValue6) {
      int intValue7 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue5);
      int intValue8 = dev.leg1tka.api.utils.text.ColorUtils.b(intValue5);
      int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.l(intValue5);
      int intValue10 = Math.max(1, Math.min(255, (int)(floatValue6 * 255.0F)));
      a(bufferBuilder0, matrix4f1, -floatValue2, -floatValue3, -floatValue4, floatValue2, -floatValue3, -floatValue4, floatValue2, -floatValue3, floatValue4, -floatValue2, -floatValue3, floatValue4, intValue7, intValue8, intValue9, intValue10, 1.0F);
      a(bufferBuilder0, matrix4f1, -floatValue2, floatValue3, -floatValue4, -floatValue2, floatValue3, floatValue4, floatValue2, floatValue3, floatValue4, floatValue2, floatValue3, -floatValue4, intValue7, intValue8, intValue9, intValue10, 0.55F);
      a(bufferBuilder0, matrix4f1, -floatValue2, -floatValue3, -floatValue4, -floatValue2, floatValue3, -floatValue4, floatValue2, floatValue3, -floatValue4, floatValue2, -floatValue3, -floatValue4, intValue7, intValue8, intValue9, intValue10, 0.8F);
      a(bufferBuilder0, matrix4f1, -floatValue2, -floatValue3, floatValue4, floatValue2, -floatValue3, floatValue4, floatValue2, floatValue3, floatValue4, -floatValue2, floatValue3, floatValue4, intValue7, intValue8, intValue9, intValue10, 0.8F);
      a(bufferBuilder0, matrix4f1, -floatValue2, -floatValue3, -floatValue4, -floatValue2, -floatValue3, floatValue4, -floatValue2, floatValue3, floatValue4, -floatValue2, floatValue3, -floatValue4, intValue7, intValue8, intValue9, intValue10, 0.68F);
      a(bufferBuilder0, matrix4f1, floatValue2, -floatValue3, -floatValue4, floatValue2, floatValue3, -floatValue4, floatValue2, floatValue3, floatValue4, floatValue2, -floatValue3, floatValue4, intValue7, intValue8, intValue9, intValue10, 0.68F);
   }

   private static void a(
      BufferBuilder bufferBuilder0,
      Matrix4f matrix4f1,
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
      int intValue14,
      int intValue15,
      int intValue16,
      int intValue17,
      float floatValue18
   ) {
      int intValue19 = (int)((float)intValue14 * floatValue18);
      int intValue20 = (int)((float)intValue15 * floatValue18);
      int intValue21 = (int)((float)intValue16 * floatValue18);
      bufferBuilder0.vertex(matrix4f1, floatValue2, floatValue3, floatValue4).color(intValue19, intValue20, intValue21, intValue17);
      bufferBuilder0.vertex(matrix4f1, floatValue5, floatValue6, floatValue7).color(intValue19, intValue20, intValue21, intValue17);
      bufferBuilder0.vertex(matrix4f1, floatValue8, floatValue9, floatValue10).color(intValue19, intValue20, intValue21, intValue17);
      bufferBuilder0.vertex(matrix4f1, floatValue11, floatValue12, floatValue13).color(intValue19, intValue20, intValue21, intValue17);
   }

   private static void b(BufferBuilder bufferBuilder0, Matrix4f matrix4f1, float floatValue2, float floatValue3, float floatValue4, int intValue5, float floatValue6) {
      int intValue7 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue5);
      int intValue8 = dev.leg1tka.api.utils.text.ColorUtils.b(intValue5);
      int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.l(intValue5);
      int intValue10 = Math.max(1, Math.min(255, (int)(floatValue6 * 255.0F)));

      for (int[] intValueArray14 : m) {
         int[] intValueArray15 = n[intValueArray14[0]];
         int[] intValueArray16 = n[intValueArray14[1]];
         bufferBuilder0.vertex(matrix4f1, (float)intValueArray15[0] * floatValue2, (float)intValueArray15[1] * floatValue3, (float)intValueArray15[2] * floatValue4).color(intValue7, intValue8, intValue9, intValue10);
         bufferBuilder0.vertex(matrix4f1, (float)intValueArray16[0] * floatValue2, (float)intValueArray16[1] * floatValue3, (float)intValueArray16[2] * floatValue4).color(intValue7, intValue8, intValue9, intValue10);
      }
   }

   private static List<TotemEffect.b> q() {
      float[][] floatValueArrayArray0 = new float[][]{
         {-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F},
         {-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 8.0F},
         {-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 8.0F},
         {-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 8.0F},
         {-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 20.0F},
         {-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 20.0F}
      };
      ArrayList arrayList1 = new ArrayList();

      for (int intValue2 = 0; intValue2 < floatValueArrayArray0.length; intValue2++) {
         float[] floatValueArray3 = floatValueArrayArray0[intValue2];
         int intValue4 = Math.max(1, Math.round(floatValueArray3[3] / 2.0F));
         int intValue5 = Math.max(1, Math.round(floatValueArray3[4] / 2.0F));
         int intValue6 = Math.max(1, Math.round(floatValueArray3[5] / 2.0F));
         float floatValue7 = floatValueArray3[3] / (float)intValue4;
         float floatValue8 = floatValueArray3[4] / (float)intValue5;
         float floatValue9 = floatValueArray3[5] / (float)intValue6;

         for (int intValue10 = 0; intValue10 < intValue4; intValue10++) {
            for (int intValue11 = 0; intValue11 < intValue5; intValue11++) {
               for (int intValue12 = 0; intValue12 < intValue6; intValue12++) {
                  if (a(intValue10, intValue11, intValue12, intValue4, intValue5, intValue6)) {
                     float floatValue13 = MathHelper.clamp(1.0F - (floatValueArray3[6] + ((float)intValue11 + 0.5F) * floatValue8) / 32.0F, 0.0F, 1.0F);
                     arrayList1.add(
                        new TotemEffect.b(
                           intValue2,
                           (floatValueArray3[0] + ((float)intValue10 + 0.5F) * floatValue7) / 16.0F,
                           (floatValueArray3[1] + ((float)intValue11 + 0.5F) * floatValue8) / 16.0F,
                           (floatValueArray3[2] + ((float)intValue12 + 0.5F) * floatValue9) / 16.0F,
                           floatValue7 / 32.0F,
                           floatValue8 / 32.0F,
                           floatValue9 / 32.0F,
                           floatValue13,
                           (int)((1.0F - floatValue13) * 40.0F)
                        )
                     );
                  }
               }
            }
         }
      }

      return arrayList1;
   }

   static class a {
      final int a;
      long b = System.nanoTime();
      PlayerEntityRenderer c;
      PlayerEntityRenderState d;
      final Matrix4f[] e = new Matrix4f[]{new Matrix4f(), new Matrix4f(), new Matrix4f(), new Matrix4f(), new Matrix4f(), new Matrix4f()};
      final boolean[] f = new boolean[6];

      a(int intValue1) {
         this.a = intValue1;
      }
   }

   static record b(int part, float cx, float cy, float cz, float hx, float hy, float hz, float h01, int colorIndex)  {








}

   class c {
      final Vec3d a;
      final boolean b;
      final Vector3f c;
      final List<TotemEffect.d> d = new ArrayList<>();
      final long e = System.nanoTime();
      long f = this.e;
      float g;
      float h;

      c(Vec3d vec3d2, boolean flag3, float floatValue4) {
         this.a = vec3d2;
         this.b = flag3;
         this.c = new Vector3f(0.0F, floatValue4 * 0.62F, 0.0F);
      }

      void a(Random random1, float floatValue2) {
         float floatValue3 = 0.01F;

         for (TotemEffect.d value5 : this.d) {
            floatValue3 = Math.max(floatValue3, value5.a.y);
         }

         Vector3f vector3f8 = new Vector3f();

         for (TotemEffect.d value6 : this.d) {
            vector3f8.set(value6.a).sub(this.c);
            value6.m = 0.24F + vector3f8.length() * 0.14F + random1.nextFloat() * 0.03F;
            vector3f8.y *= 0.35F;
            if (vector3f8.lengthSquared() < 1.0E-4F) {
               vector3f8.set(random1.nextFloat() - 0.5F, 0.0F, random1.nextFloat() - 0.5F);
            }

            vector3f8.normalize();
            float floatValue7 = (1.2F + random1.nextFloat() * 1.4F) * floatValue2;
            value6.i.set(vector3f8.x * floatValue7, (0.6F + random1.nextFloat() * 1.4F) * floatValue2, vector3f8.z * floatValue7);
            value6.j.set(random1.nextFloat() - 0.5F, random1.nextFloat() - 0.5F, random1.nextFloat() - 0.5F).normalize();
            value6.l = (3.0F + random1.nextFloat() * 7.0F) * (float)(random1.nextBoolean() ? 1 : -1);
            value6.n = random1.nextFloat() * (float) (Math.PI * 2);
            value6.o = MathHelper.clamp((int)((1.0F - value6.a.y / floatValue3) * 40.0F), 0, 40);
         }
      }

      void a(float floatValue1, float floatValue2) {
         boolean flag3 = TotemEffect.this.s.getValue() && this.b;

         for (TotemEffect.d value5 : this.d) {
            if (!(floatValue1 < value5.m) && !value5.q) {
               value5.i.y -= 9.5F * floatValue2;
               value5.i.mul(1.0F - 0.6F * floatValue2);
               value5.h.add(value5.i.x * floatValue2, value5.i.y * floatValue2, value5.i.z * floatValue2);
               value5.k = value5.k + value5.l * floatValue2;
               float floatValue6 = value5.e - value5.a.y;
               if (flag3 && value5.h.y < floatValue6) {
                  value5.h.y = floatValue6;
                  if (value5.i.y < 0.0F) {
                     if (value5.p++ == 0 && value5.i.y < -1.2F) {
                        value5.i.y *= -0.32F;
                        value5.i.x *= 0.55F;
                        value5.i.z *= 0.55F;
                        value5.l *= 0.4F;
                     } else {
                        value5.i.zero();
                        value5.l = 0.0F;
                        value5.q = true;
                     }
                  }
               }
            }
         }
      }
   }

   static class d {
      final Vector3f a;
      final Matrix4f b;
      final Matrix4f c = new Matrix4f();
      final float d;
      final float e;
      final float f;
      final boolean g;
      final Vector3f h = new Vector3f();
      final Vector3f i = new Vector3f();
      final Vector3f j = new Vector3f(0.0F, 1.0F, 0.0F);
      float k;
      float l;
      float m;
      float n;
      int o;
      int p;
      boolean q;

      d(Vector3f vector3f1, Matrix4f matrix4f2, float floatValue3, float floatValue4, float floatValue5, boolean flag6) {
         this.a = vector3f1;
         this.b = matrix4f2;
         this.d = floatValue3;
         this.e = floatValue4;
         this.f = floatValue5;
         this.g = flag6;
      }
   }
}
