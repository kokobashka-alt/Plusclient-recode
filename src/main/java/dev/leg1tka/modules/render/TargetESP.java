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
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.Event3DRender;
import dev.leg1tka.api.events.implement.EventRender;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class TargetESP extends Module {
   public static TargetESP c = new TargetESP();
   private static float h;
   private static float i;
   private static float j;
   static long d;
   static long e;
   static int f;
   public static final byte[][] g = new byte[][]{
      {-1, -1, -1, 1, -1, -1},
      {1, -1, -1, 1, -1, 1},
      {1, -1, 1, -1, -1, 1},
      {-1, -1, 1, -1, -1, -1},
      {-1, 1, -1, 1, 1, -1},
      {1, 1, -1, 1, 1, 1},
      {1, 1, 1, -1, 1, 1},
      {-1, 1, 1, -1, 1, -1},
      {-1, -1, -1, -1, 1, -1},
      {1, -1, -1, 1, 1, -1},
      {1, -1, 1, 1, 1, 1},
      {-1, -1, 1, -1, 1, 1}
   };
   private final ModeSetting k = new ModeSetting(
      "Режим",
      "Ромб",
      "Ромб",
      "Кубы",
      "Кольцо",
      "Души",
      "Кристаллы",
      "Целестиал",
      "Призраки",
      "Плюс"
   );
   private final NumberSetting l = new NumberSetting("Размер", 1.15F, 0.6F, 2.5F, 0.05F);
   private final NumberSetting m = new NumberSetting("Радиус кольца", 0.5F, 0.3F, 1.5F, 0.05F);
   private final NumberSetting n = new NumberSetting("Скорость кольца", 1.0F, 0.3F, 3.0F, 0.1F);
   private final NumberSetting o = new NumberSetting("Скорость вращения", 1.2F, 0.2F, 4.0F, 0.05F);
   private final BooleanSetting p = new BooleanSetting("Окрашивание при ударе", true);
   private final NumberSetting q = new NumberSetting("Кол-во призраков", 3.0F, 2.0F, 5.0F, 1.0F);
   private final NumberSetting r = new NumberSetting("Время жизни (мс)", 350.0F, 150.0F, 500.0F, 25.0F);
   private final NumberSetting s = new NumberSetting("Цикл XZ", 2000.0F, 1000.0F, 5000.0F, 100.0F);
   private final NumberSetting t = new NumberSetting("Цикл Y", 1700.0F, 1000.0F, 5000.0F, 100.0F);
   private float u = 0.0F;
   private float v = 0.0F;
   private float w = 0.0F;
   private float x = -280.0F;
   private float y = 280.0F;
   private long z = System.currentTimeMillis();
   private LivingEntity A = null;
   private LivingEntity B = null;
   private Vec3d C = null;
   private float D = 1.8F;
   private float E = 0.6F;
   private final CopyOnWriteArrayList<TargetESP.TargetParticle> F = new CopyOnWriteArrayList<>();
   private float G = 0.0F;
   private float H = 0.0F;
   private float I = 0.0F;
   private long J = 0L;
   private final ArrayList<dev.leg1tka.render.WorldMarkerRenderer> K = new ArrayList<>();
   private final ArrayList<dev.leg1tka.render.WorldMarkerRenderer> L = new ArrayList<>();
   private static float M;
   private static int N;
   private double O = 0.0;
   private final Map<LivingEntity, List<dev.leg1tka.render.WorldMarkerRenderer>> P = new WeakHashMap<>();
   private final Map<LivingEntity, List<dev.leg1tka.render.WorldMarkerRenderer>> Q = new WeakHashMap<>();
   private final Map<LivingEntity, List<dev.leg1tka.render.WorldMarkerRenderer>> R = new WeakHashMap<>();
   private int S = 0;
   private long T = 0L;
   private long U = System.currentTimeMillis();
   private final List V = new ArrayList();
   private long W = 0L;
   private long X = 0L;

   public TargetESP() {
      super("TargetESP", "Отображения таргета", Category.RENDER);
      this.l.a(this::p);
      this.o.a(this::p);
      this.q.a(() -> this.k.b("Райдер"));
      this.r.a(() -> this.k.b("Райдер"));
      this.s.a(() -> this.k.b("Райдер"));
      this.t.a(() -> this.k.b("Райдер"));
      this.m.a(() -> this.k.b("Кольцо"));
      this.n.a(() -> this.k.b("Кольцо"));
      this.p.visibleWhen(() -> !this.k.b("Аспект"));
      this.addSettings(new Setting[]{this.k, this.l, this.o, this.p, this.m, this.n, this.q, this.r, this.s, this.t});
   }

   @Override
   public void onDisable() {
      this.u = 0.0F;
      this.v = 0.0F;
      this.A = null;
      this.B = null;
      this.C = null;
      this.w = 0.0F;
      this.x = -280.0F;
      this.y = 280.0F;
      this.F.clear();
      this.G = 0.0F;
      this.H = 0.0F;
      this.I = 0.0F;
      this.J = 0L;
      this.K.clear();
      this.L.clear();
      this.O = 0.0;
      this.P.clear();
      this.Q.clear();
      this.R.clear();
      this.S = 0;
      this.T = 0L;
      this.V.clear();
      this.W = 0L;
      this.X = 0L;
      super.onDisable();
   }

   private boolean p() {
      return this.k.b("Картинка 1") || this.k.b("Ромб");
   }

   private Identifier q() {
      return this.k.b("Ромб") ? dev.leg1tka.api.utils.resources.ResourceIdentifiers.a("textures/targetesp/targetesp_3.png") : dev.leg1tka.api.utils.resources.ResourceIdentifiers.a("textures/targetesp/targetesp_2.png");
   }

   private Identifier r() {
      return dev.leg1tka.api.utils.resources.ResourceIdentifiers.a("textures/targetesp/bloom.png");
   }

   private int s() {
      int intValue1 = dev.leg1tka.api.utils.text.ColorUtils.a();
      if ((intValue1 >> 24 & 0xFF) == 0) {
         intValue1 |= -16777216;
      }

      return intValue1;
   }

   private float a(float floatValue1, float floatValue2, float floatValue3) {
      if (floatValue1 < floatValue2) {
         floatValue1 = Math.min(floatValue1 + floatValue3, floatValue2);
      } else if (floatValue1 > floatValue2) {
         floatValue1 = Math.max(floatValue1 - floatValue3, floatValue2);
      }

      return floatValue1;
   }

   private float a(Vec3d vec3d1, double doubleValue2, double doubleValue4, double doubleValue6) {
      double doubleValue8 = doubleValue2 - vec3d1.x;
      double doubleValue10 = doubleValue4 - vec3d1.y;
      double doubleValue12 = doubleValue6 - vec3d1.z;
      double doubleValue14 = Math.sqrt(doubleValue8 * doubleValue8 + doubleValue10 * doubleValue10 + doubleValue12 * doubleValue12);
      return (float)Math.max(0.1, doubleValue14 * 0.007F);
   }

   @EventLink(
      priority = -100
   )
   public void onRender3D(Event3DRender event3DRender1) {
      if (minecraftClient != null && minecraftClient.player != null && minecraftClient.world != null) {
         AttackAura attackAura2 = ModuleManager.i;
         boolean flag3 = attackAura2 != null && attackAura2.isEnabled();
         LivingEntity livingEntity4 = flag3 ? attackAura2.E() : null;
         boolean flag5 = livingEntity4 != null && livingEntity4.isAlive();
         float floatValue6 = 0.05F;
         this.u = this.a(this.u, flag5 ? 1.0F : 0.0F, floatValue6);
         this.v = this.a(this.v, flag5 ? 1.0F : 0.5F, floatValue6);
         if (flag5) {
            this.A = livingEntity4;
            this.B = livingEntity4;
         }

         if (this.k.b("Кристаллы")) {
            float floatValue7 = flag5 ? 0.07F : 0.045F;
            this.H = this.a(this.H, flag5 ? 1.0F : 0.0F, floatValue7);
            if (flag5) {
               this.G += 0.8F;
            }
         }

         if (!(this.u <= 0.001F) || flag5 || this.k.b("Кристаллы") && !(this.H <= 0.001F)) {
            if (flag5 && livingEntity4 != null) {
               float floatValue8 = event3DRender1.getTickDelta();
               this.C = new Vec3d(
                  MathHelper.lerp((double)floatValue8, livingEntity4.lastRenderX, livingEntity4.getX()),
                  MathHelper.lerp((double)floatValue8, livingEntity4.lastRenderY, livingEntity4.getY()),
                  MathHelper.lerp((double)floatValue8, livingEntity4.lastRenderZ, livingEntity4.getZ())
               );
               this.D = livingEntity4.getHeight();
               this.E = livingEntity4.getWidth();
            }

            if (this.C != null) {
               if (this.k.b("Райдер")) {
                  if (flag5 && livingEntity4 != null) {
                     this.a(
                        livingEntity4, event3DRender1.getTickDelta(), Math.max(1, Math.round(this.q.e().floatValue())), Math.max(1, Math.round(this.r.e().floatValue())), this.s()
                     );
                  }

                  this.F.removeIf(TargetParticle::a);
                  this.g(event3DRender1);
               } else if (this.k.b("Кристаллы")) {
                  LivingEntity livingEntity9 = flag5 ? livingEntity4 : this.A;
                  if ((livingEntity9 != null || this.C != null) && this.H > 0.01F) {
                     this.a(event3DRender1.getMatrices(), livingEntity9, event3DRender1.getTickDelta());
                  }
               } else {
                  if (this.p()) {
                     this.e(event3DRender1);
                  }

                  if (this.k.b("Души")) {
                     this.f(event3DRender1);
                  }

                  if (this.k.b("Кольцо")) {
                     this.a(event3DRender1);
                  }

                  if (this.k.b("Кубы")) {
                     this.a(event3DRender1, livingEntity4, flag5);
                  }

                  if (this.k.b("Целестиал")) {
                     this.O++;
                     this.c(event3DRender1);
                  }

                  if (this.k.b("Призраки")) {
                     this.i(event3DRender1);
                  }

                  if (this.k.b("Аспект")) {
                     this.j(event3DRender1);
                  }
               }
            }
         } else {
            this.A = null;
            this.C = null;
         }
      }
   }

   private void a(Event3DRender event3DRender1, LivingEntity livingEntity2, boolean flag3) {
      long longValue4 = System.currentTimeMillis();
      if (this.J == 0L) {
         this.J = longValue4;
      }

      float floatValue6 = Math.min((float)(longValue4 - this.J) / 1000.0F, 0.1F);
      this.J = longValue4;
      if (Float.isFinite(floatValue6) && minecraftClient.gameRenderer != null && minecraftClient.gameRenderer.getCamera() != null) {
         if (flag3 && livingEntity2 != null) {
            this.A = livingEntity2;
            this.I += floatValue6;

            while (this.I >= 0.022F) {
               this.I -= 0.022F;
               if (this.K.size() >= 72) {
                  break;
               }

               for (int intValue7 = 0; intValue7 < 1; intValue7++) {
                  double doubleValue8 = Math.random() * 360.0;
                  double doubleValue10 = Math.cos(Math.toRadians(doubleValue8)) * 0.7;
                  double doubleValue12 = 0.02 + Math.random() * 0.1;
                  double doubleValue14 = Math.sin(Math.toRadians(doubleValue8)) * 0.7;
                  this.K.add(new dev.leg1tka.render.WorldMarkerRenderer(livingEntity2, doubleValue10, doubleValue12, doubleValue14));
               }
            }
         } else {
            this.I = 0.0F;
         }

         this.L.clear();

         for (int intValue30 = this.K.size() - 1; intValue30 >= 0; intValue30--) {
            dev.leg1tka.render.WorldMarkerRenderer value32 = (dev.leg1tka.render.WorldMarkerRenderer)this.K.get(intValue30);

            try {
               value32.a(floatValue6, longValue4, flag3 ? livingEntity2 : null);
               if (value32.a(longValue4)) {
                  this.K.remove(intValue30);
               } else {
                  this.L.add(value32);
               }
            } catch (Throwable throwable29) {
               this.K.remove(intValue30);
            }
         }

         if (!this.L.isEmpty()) {
            float floatValue31 = event3DRender1.getTickDelta();
            MatrixStack matrixStack33 = event3DRender1.getMatrices();
            Vec3d vec3d9 = minecraftClient.gameRenderer.getCamera().getPos();
            LivingEntity livingEntity34 = flag3 ? livingEntity2 : this.A;
            float floatValue11 = this.a(livingEntity34);
            int intValue35 = this.s();
            int intValue13 = dev.leg1tka.api.utils.text.ColorUtils.b(255, 3, 3);
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.blendFunc(770, 1);
            RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
            BufferBuilder bufferBuilder36 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
            boolean flag15 = false;
            int intValue16 = 0;

            for (int intValue17 = this.L.size(); intValue16 < intValue17; intValue16++) {
               dev.leg1tka.render.WorldMarkerRenderer value18 = (dev.leg1tka.render.WorldMarkerRenderer)this.L.get(intValue16);

               try {
                  int intValue19 = value18.a(intValue35, intValue13, floatValue11, longValue4);
                  if ((intValue19 >> 24 & 0xFF) > 0 && value18.a(bufferBuilder36, matrixStack33, vec3d9, floatValue31, intValue19)) {
                     flag15 = true;
                  }
               } catch (Throwable throwable28) {
               }
            }

            if (flag15) {
               BufferRenderer.drawWithGlobalProgram(bufferBuilder36.end());
            }

            BufferBuilder bufferBuilder37 = Tessellator.getInstance().begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
            boolean flag38 = false;
            int intValue39 = 0;

            for (int intValue41 = this.L.size(); intValue39 < intValue41; intValue39++) {
               dev.leg1tka.render.WorldMarkerRenderer value20 = (dev.leg1tka.render.WorldMarkerRenderer)this.L.get(intValue39);

               try {
                  int intValue21 = value20.a(intValue35, intValue13, floatValue11, longValue4);
                  if ((intValue21 >> 24 & 0xFF) > 0 && value20.b(bufferBuilder37, matrixStack33, vec3d9, floatValue31, intValue21)) {
                     flag38 = true;
                  }
               } catch (Throwable throwable27) {
               }
            }

            if (flag38) {
               BufferRenderer.drawWithGlobalProgram(bufferBuilder37.end());
            }

            RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
            RenderSystem.setShaderTexture(0, this.r());
            BufferBuilder bufferBuilder40 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            boolean flag42 = false;
            float floatValue43 = minecraftClient.gameRenderer.getCamera().getYaw();
            float floatValue44 = minecraftClient.gameRenderer.getCamera().getPitch();
            int intValue22 = 0;

            for (int intValue23 = this.L.size(); intValue22 < intValue23; intValue22++) {
               dev.leg1tka.render.WorldMarkerRenderer value24 = (dev.leg1tka.render.WorldMarkerRenderer)this.L.get(intValue22);

               try {
                  int intValue25 = value24.a(intValue35, intValue13, floatValue11, longValue4);
                  if (value24.a(bufferBuilder40, matrixStack33, vec3d9, floatValue43, floatValue44, floatValue31, intValue25, longValue4)) {
                     flag42 = true;
                  }
               } catch (Throwable throwable26) {
               }
            }

            if (flag42) {
               BufferRenderer.drawWithGlobalProgram(bufferBuilder40.end());
            }

            RenderSystem.depthMask(true);
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
            RenderSystem.enableCull();
            RenderSystem.enableDepthTest();
         }
      }
   }

   private void a(Event3DRender event3DRender1) {
      if (!(this.u <= 0.001F) && this.C != null) {
         float floatValue2 = minecraftClient.getRenderTickCounter().getTickDelta(true);
         LivingEntity livingEntity5 = this.A;
         Vec3d vec3d3;
         float floatValue4;
         if (livingEntity5 != null && livingEntity5.isAlive()) {
            vec3d3 = new Vec3d(
               MathHelper.lerp((double)floatValue2, livingEntity5.lastRenderX, livingEntity5.getX()),
               MathHelper.lerp((double)floatValue2, livingEntity5.lastRenderY, livingEntity5.getY()),
               MathHelper.lerp((double)floatValue2, livingEntity5.lastRenderZ, livingEntity5.getZ())
            );
            floatValue4 = livingEntity5.getHeight();
         } else {
            vec3d3 = this.C;
            floatValue4 = this.D;
         }

         Vec3d vec3d6 = minecraftClient.gameRenderer.getCamera().getPos();
         double doubleValue7 = vec3d3.x - vec3d6.x;
         double doubleValue9 = vec3d3.y - vec3d6.y;
         double doubleValue11 = vec3d3.z - vec3d6.z;
         double doubleValue13 = 2000.0 / (double)this.n.f();
         double doubleValue15 = (double)(System.currentTimeMillis() % (long)doubleValue13);
         boolean flag17 = doubleValue15 > doubleValue13 / 2.0;
         double doubleValue18 = doubleValue15 / (doubleValue13 / 2.0);
         if (flag17) {
            doubleValue18--;
         } else {
            doubleValue18 = 1.0 - doubleValue18;
         }

         doubleValue18 = doubleValue18 < 0.5 ? 2.0 * doubleValue18 * doubleValue18 : 1.0 - Math.pow(-2.0 * doubleValue18 + 2.0, 2.0) / 2.0;
         double doubleValue20 = (double)floatValue4 / 1.2 * (doubleValue18 > 0.5 ? 1.0 - doubleValue18 : doubleValue18) * (double)(flag17 ? -1 : 1);
         int intValue22 = this.s();
         float floatValue23 = this.a(livingEntity5);
         int intValue24 = dev.leg1tka.api.utils.text.ColorUtils.b(255, 3, 3);
         int intValue25 = this.a(intValue22, intValue24, floatValue23);
         int intValue26 = this.a(intValue25, 0.88235295F * this.u);
         int intValue27 = this.a(intValue25, 0.003921569F * this.u);
         int intValue28 = this.a(intValue25, this.u);
         double doubleValue29 = (double)this.m.f();
         MatrixStack matrixStack31 = event3DRender1.getMatrices();
         Matrix4f matrix4f32 = matrixStack31.peek().getPositionMatrix();
         RenderSystem.depthMask(false);
         RenderSystem.disableDepthTest();
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(770, 1);
         RenderSystem.disableCull();
         RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
         BufferBuilder bufferBuilder33 = Tessellator.getInstance().begin(DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

         for (int intValue34 = 0; intValue34 <= 360; intValue34++) {
            double doubleValue35 = Math.toRadians((double)intValue34);
            float floatValue37 = (float)(doubleValue7 + Math.cos(doubleValue35) * doubleValue29);
            float floatValue38 = (float)(doubleValue11 + Math.sin(doubleValue35) * doubleValue29);
            float floatValue39 = (float)(doubleValue9 + (double)floatValue4 * doubleValue18);
            float floatValue40 = (float)(doubleValue9 + (double)floatValue4 * doubleValue18 + doubleValue20);
            bufferBuilder33.vertex(matrix4f32, floatValue37, floatValue39, floatValue38).color(intValue26);
            bufferBuilder33.vertex(matrix4f32, floatValue37, floatValue40, floatValue38).color(intValue27);
         }

         BufferRenderer.drawWithGlobalProgram(bufferBuilder33.end());
         RenderSystem.lineWidth(1.5F);
         BufferBuilder bufferBuilder43 = Tessellator.getInstance().begin(DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);

         for (int intValue44 = 0; intValue44 <= 360; intValue44++) {
            double doubleValue36 = Math.toRadians((double)intValue44);
            float floatValue45 = (float)(doubleValue7 + Math.cos(doubleValue36) * doubleValue29);
            float floatValue46 = (float)(doubleValue11 + Math.sin(doubleValue36) * doubleValue29);
            float floatValue47 = (float)(doubleValue9 + (double)floatValue4 * doubleValue18);
            bufferBuilder43.vertex(matrix4f32, floatValue45, floatValue47, floatValue46).color(intValue28);
         }

         BufferRenderer.drawWithGlobalProgram(bufferBuilder43.end());
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
      }
   }

   private int a(int intValue1, float floatValue2) {
      floatValue2 = Math.max(0.0F, Math.min(1.0F, floatValue2));
      return intValue1 & 16777215 | (int)(floatValue2 * 255.0F) << 24;
   }

   private void b(Event3DRender event3DRender1) {
      if (!(this.u <= 0.001F) && this.C != null) {
         float floatValue2 = minecraftClient.getRenderTickCounter().getTickDelta(true);
         LivingEntity livingEntity5 = this.A;
         Vec3d vec3d3;
         float floatValue4;
         if (livingEntity5 != null && livingEntity5.isAlive()) {
            vec3d3 = new Vec3d(
               MathHelper.lerp((double)floatValue2, livingEntity5.lastRenderX, livingEntity5.getX()),
               MathHelper.lerp((double)floatValue2, livingEntity5.lastRenderY, livingEntity5.getY()),
               MathHelper.lerp((double)floatValue2, livingEntity5.lastRenderZ, livingEntity5.getZ())
            );
            floatValue4 = livingEntity5.getHeight();
         } else {
            vec3d3 = this.C;
            floatValue4 = this.D;
         }

         Vec3d vec3d6 = minecraftClient.gameRenderer.getCamera().getPos();
         double doubleValue7 = vec3d3.x - vec3d6.x;
         double doubleValue9 = vec3d3.y - vec3d6.y + (double)floatValue4 + 0.8;
         double doubleValue11 = vec3d3.z - vec3d6.z;
         int intValue13 = this.s();
         float floatValue14 = this.a(livingEntity5);
         int intValue15 = dev.leg1tka.api.utils.text.ColorUtils.b(255, 3, 3);
         int intValue16 = this.a(intValue13, intValue15, floatValue14);
         float floatValue17 = 0.55F;
         float floatValue18 = 0.75F;
         MatrixStack matrixStack19 = event3DRender1.getMatrices();
         matrixStack19.push();
         matrixStack19.translate(doubleValue7, doubleValue9, doubleValue11);
         matrixStack19.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-minecraftClient.gameRenderer.getCamera().getYaw()));
         matrixStack19.multiply(RotationAxis.POSITIVE_X.rotationDegrees(minecraftClient.gameRenderer.getCamera().getPitch()));
         Matrix4f matrix4f20 = matrixStack19.peek().getPositionMatrix();
         RenderSystem.depthMask(false);
         RenderSystem.disableDepthTest();
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(770, 1);
         RenderSystem.disableCull();
         RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
         int intValue21 = this.a(intValue16, 0.95F * this.u);
         int intValue22 = this.a(intValue16, 0.7F * this.u);
         int intValue23 = this.a(intValue16, 0.3F * this.u);

         for (int intValue24 = 0; intValue24 < 5; intValue24++) {
            float floatValue25 = (float)intValue24 * 0.015F;
            float floatValue26 = 1.0F - (float)intValue24 * 0.15F;
            int intValue27 = this.a(intValue16, 0.95F * this.u * floatValue26);
            int intValue28 = this.a(intValue16, 0.7F * this.u * floatValue26);
            int intValue29 = this.a(intValue16, 0.3F * this.u * floatValue26);
            BufferBuilder bufferBuilder30 = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
            bufferBuilder30.vertex(matrix4f20, -floatValue17, floatValue25, floatValue25).color(intValue27);
            bufferBuilder30.vertex(matrix4f20, floatValue17, floatValue25, floatValue25).color(intValue27);
            bufferBuilder30.vertex(matrix4f20, 0.0F, -floatValue18, floatValue25).color(intValue29);
            bufferBuilder30.vertex(matrix4f20, -floatValue17, floatValue25, -floatValue25).color(intValue27);
            bufferBuilder30.vertex(matrix4f20, 0.0F, -floatValue18, -floatValue25).color(intValue29);
            bufferBuilder30.vertex(matrix4f20, floatValue17, floatValue25, -floatValue25).color(intValue27);
            BufferRenderer.drawWithGlobalProgram(bufferBuilder30.end());
         }

         byte byteValue32 = 6;

         for (int intValue33 = 0; intValue33 < byteValue32; intValue33++) {
            float floatValue35 = (float)intValue33 / (float)(byteValue32 - 1);
            float floatValue37 = floatValue35 * floatValue18;
            float floatValue39 = floatValue17 * (1.0F - floatValue35);
            float floatValue41 = 0.6F * (1.0F - floatValue35 * 0.5F);
            int intValue43 = this.a(intValue16, floatValue41 * this.u);
            RenderSystem.lineWidth(2.5F);
            BufferBuilder bufferBuilder31 = Tessellator.getInstance().begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
            bufferBuilder31.vertex(matrix4f20, -floatValue39, -floatValue37, 0.0F).color(intValue43);
            bufferBuilder31.vertex(matrix4f20, floatValue39, -floatValue37, 0.0F).color(intValue43);
            BufferRenderer.drawWithGlobalProgram(bufferBuilder31.end());
         }

         RenderSystem.lineWidth(2.5F);
         BufferBuilder bufferBuilder34 = Tessellator.getInstance().begin(DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
         int intValue36 = this.a(intValue16, 0.9F * this.u);
         bufferBuilder34.vertex(matrix4f20, -floatValue17, 0.0F, 0.0F).color(intValue36);
         bufferBuilder34.vertex(matrix4f20, floatValue17, 0.0F, 0.0F).color(intValue36);
         bufferBuilder34.vertex(matrix4f20, 0.0F, -floatValue18, 0.0F).color(intValue36);
         bufferBuilder34.vertex(matrix4f20, -floatValue17, 0.0F, 0.0F).color(intValue36);
         BufferRenderer.drawWithGlobalProgram(bufferBuilder34.end());

         for (int intValue38 = 0; intValue38 < 3; intValue38++) {
            float floatValue40 = 1.0F + (float)intValue38 * 0.08F;
            float floatValue42 = 0.25F * (1.0F - (float)intValue38 * 0.3F);
            int intValue44 = this.a(intValue16, floatValue42 * this.u);
            BufferBuilder bufferBuilder45 = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
            bufferBuilder45.vertex(matrix4f20, -floatValue17 * floatValue40, 0.02F * (float)intValue38, 0.0F).color(intValue44);
            bufferBuilder45.vertex(matrix4f20, floatValue17 * floatValue40, 0.02F * (float)intValue38, 0.0F).color(intValue44);
            bufferBuilder45.vertex(matrix4f20, 0.0F, -floatValue18 * floatValue40, 0.0F).color(intValue44);
            BufferRenderer.drawWithGlobalProgram(bufferBuilder45.end());
         }

         matrixStack19.pop();
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
      }
   }

   private void c(Event3DRender event3DRender1) {
      if (!(this.u <= 0.001F) && this.C != null) {
         LivingEntity livingEntity2 = this.A;
         if (livingEntity2 != null && livingEntity2.isAlive()) {
            float floatValue3 = 1.0F - (float)Math.pow((double)(1.0F - Math.min(this.u, 1.0F)), 3.0);
            if (floatValue3 != 0.0F) {
               float floatValue4 = event3DRender1.getTickDelta();
               Vec3d vec3d5 = new Vec3d(
                  MathHelper.lerp((double)floatValue4, livingEntity2.lastRenderX, livingEntity2.getX()),
                  MathHelper.lerp((double)floatValue4, livingEntity2.lastRenderY, livingEntity2.getY()),
                  MathHelper.lerp((double)floatValue4, livingEntity2.lastRenderZ, livingEntity2.getZ())
               );
               Vec3d vec3d6 = minecraftClient.gameRenderer.getCamera().getPos();
               float floatValue7 = minecraftClient.gameRenderer.getCamera().getYaw();
               float floatValue8 = minecraftClient.gameRenderer.getCamera().getPitch();
               double doubleValue9 = vec3d5.x - vec3d6.x;
               double doubleValue11 = vec3d5.y - vec3d6.y;
               double doubleValue13 = vec3d5.z - vec3d6.z;
               double doubleValue15 = this.O - 0.5;
               float floatValue17 = (float)doubleValue15 * 0.6F;
               float floatValue18 = (float)Math.toRadians((double)((float)doubleValue15 * -2.0F));
               float floatValue19 = livingEntity2.getWidth() * 1.0F;
               float floatValue20 = livingEntity2.getHeight() / 1.6F;
               float floatValue21 = livingEntity2.getHeight() / 4.5F;
               byte byteValue22 = 68;
               float floatValue23 = 0.0F;
               float floatValue24 = 0.00615F;
               int intValue25 = this.s();
               float floatValue26 = this.a(livingEntity2);
               int intValue27 = dev.leg1tka.api.utils.text.ColorUtils.b(255, 3, 3);
               RenderSystem.enableBlend();
               RenderSystem.blendFunc(770, 1);
               RenderSystem.disableCull();
               RenderSystem.disableDepthTest();
               RenderSystem.depthMask(false);
               RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
               RenderSystem.setShaderTexture(0, this.r());
               MatrixStack matrixStack28 = event3DRender1.getMatrices();

               for (int intValue29 = 0; intValue29 < 4; intValue29++) {
                  for (int intValue30 = 0; intValue30 < byteValue22; intValue30++) {
                     float floatValue31 = floatValue17 + (float)intValue29 * 90.0F - (float)intValue30;
                     float floatValue32 = (float)Math.toRadians((double)floatValue31);
                     float floatValue33 = (float)Math.sin((double)floatValue32) * floatValue19;
                     float floatValue34 = (float)Math.cos((double)floatValue32) * floatValue19;
                     float floatValue35 = floatValue20 + (float)(Math.cos(Math.toRadians((double)(floatValue31 * 2.0F))) * (double)floatValue21);
                     float floatValue36 = floatValue33 * (float)Math.cos((double)floatValue18) - floatValue34 * (float)Math.sin((double)floatValue18);
                     float floatValue37 = floatValue33 * (float)Math.sin((double)floatValue18) + floatValue34 * (float)Math.cos((double)floatValue18);
                     float floatValue38 = (float)(byteValue22 - intValue30) / (float)byteValue22;
                     float floatValue39 = floatValue23 + floatValue24 * (float)(byteValue22 - intValue30);
                     int intValue40 = this.a(intValue25, intValue27, floatValue26);
                     int intValue41 = this.b(intValue40, floatValue38 * floatValue3 * 0.85F);
                     if ((intValue41 >> 24 & 0xFF) > 0) {
                        double doubleValue42 = vec3d5.x + (double)floatValue36;
                        double doubleValue44 = vec3d5.y + (double)floatValue35;
                        double doubleValue46 = vec3d5.z + (double)floatValue37;
                        this.b(matrixStack28, vec3d6, doubleValue42, doubleValue44, doubleValue46, floatValue39, intValue41, 0.0F);
                        this.b(matrixStack28, vec3d6, doubleValue42, doubleValue44, doubleValue46, floatValue39, intValue41, 0.0F);
                     }
                  }
               }

               RenderSystem.depthMask(true);
               RenderSystem.enableDepthTest();
               RenderSystem.enableCull();
               RenderSystem.defaultBlendFunc();
               RenderSystem.disableBlend();
            }
         }
      }
   }

   private void d(Event3DRender event3DRender1) {
      if (!(this.u <= 0.001F) && this.C != null) {
         LivingEntity livingEntity2 = this.A;
         if (livingEntity2 != null && livingEntity2.isAlive()) {
            float floatValue3 = 1.0F - (float)Math.pow((double)(1.0F - Math.min(this.u, 1.0F)), 3.0);
            if (floatValue3 != 0.0F) {
               float floatValue4 = event3DRender1.getTickDelta();
               Vec3d vec3d5 = new Vec3d(
                  MathHelper.lerp((double)floatValue4, livingEntity2.lastRenderX, livingEntity2.getX()),
                  MathHelper.lerp((double)floatValue4, livingEntity2.lastRenderY, livingEntity2.getY()),
                  MathHelper.lerp((double)floatValue4, livingEntity2.lastRenderZ, livingEntity2.getZ())
               );
               Vec3d vec3d6 = minecraftClient.gameRenderer.getCamera().getPos();
               double doubleValue7 = vec3d5.x;
               double doubleValue9 = vec3d5.y;
               double doubleValue11 = vec3d5.z;
               float floatValue13 = 0.3F;
               float floatValue14 = 0.7F;
               byte byteValue15 = 55;
               double doubleValue16 = (double)System.currentTimeMillis() / 350.0;
               float floatValue18 = MathHelper.clamp((float)livingEntity2.hurtTime / 10.0F, 0.0F, 1.0F);
               float floatValue19 = (float)Math.sin((double)livingEntity2.hurtTime * (Math.PI / 10));
               float[] floatValueArray20 = new float[]{livingEntity2.getHeight() * 0.85F, livingEntity2.getHeight() * 0.55F, livingEntity2.getHeight() * 0.25F};
               double[] doubleValueArray21 = new double[]{Math.toRadians(0.0), Math.toRadians(120.0), Math.toRadians(240.0)};
               List[] listArray22 = new List[]{
                  this.P.computeIfAbsent(livingEntity2, livingEntity0 -> new ArrayList()),
                  this.Q.computeIfAbsent(livingEntity2, livingEntity0 -> new ArrayList()),
                  this.R.computeIfAbsent(livingEntity2, livingEntity0 -> new ArrayList())
               };
               RenderSystem.enableBlend();
               RenderSystem.blendFunc(770, 1);
               RenderSystem.disableCull();
               RenderSystem.disableDepthTest();
               RenderSystem.depthMask(false);
               RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
               RenderSystem.setShaderTexture(0, this.r());
               MatrixStack matrixStack23 = event3DRender1.getMatrices();
               int intValue24 = this.s();

               for (int intValue25 = 0; intValue25 < 3; intValue25++) {
                  float floatValue26 = 0.16F;
                  double doubleValue27 = Math.sin(doubleValue16 * 1.5 + (double)intValue25 * 2.0) * (double)floatValue26;
                  double doubleValue29 = (double)floatValueArray20[intValue25] + doubleValue27;
                  double doubleValue31 = doubleValue16 * 1.3;
                  double doubleValue33 = doubleValueArray21[intValue25] + doubleValue31;
                  double doubleValue35 = Math.cos(doubleValue33) * 0.7F;
                  double doubleValue37 = Math.sin(doubleValue33) * 0.7F;
                  Vec3d vec3d39 = new Vec3d(doubleValue7 + doubleValue35, doubleValue9 + doubleValue29, doubleValue11 + doubleValue37);
                  List list40 = listArray22[intValue25];
                  list40.add(0, vec3d39);

                  while (list40.size() > 55) {
                     list40.remove(list40.size() - 1);
                  }

                  for (int intValue41 = 0; intValue41 < list40.size(); intValue41++) {
                     Vec3d vec3d42 = (Vec3d)list40.get(intValue41);
                     float floatValue43 = (float)intValue41 / 55.0F;
                     float floatValue44 = (float)Math.pow((double)(1.0F - floatValue43), 1.5);
                     float floatValue45 = 0.3F * (1.0F - floatValue43 * 0.3F);
                     int intValue46 = MathHelper.clamp((int)(255.0F * floatValue3 * floatValue44), 0, 255);
                     int intValue47;
                     if (floatValue18 > 0.05F) {
                        short value48 = 255;
                        int intValue49 = 80 + (int)(80.0F * (1.0F - floatValue18));
                        int intValue50 = 80 + (int)(80.0F * (1.0F - floatValue18));
                        intValue47 = dev.leg1tka.api.utils.text.ColorUtils.a(value48, intValue49, intValue50, intValue46);
                     } else {
                        intValue47 = dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(intValue24), dev.leg1tka.api.utils.text.ColorUtils.b(intValue24), dev.leg1tka.api.utils.text.ColorUtils.l(intValue24), intValue46);
                     }

                     int intValue55 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 80, 80, intValue46);
                     float floatValue56 = MathHelper.clamp(floatValue19 * floatValue18, 0.0F, 1.0F);
                     int intValue57 = this.a(intValue47, intValue55, floatValue56);
                     int intValue51 = dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(intValue57), dev.leg1tka.api.utils.text.ColorUtils.b(intValue57), dev.leg1tka.api.utils.text.ColorUtils.l(intValue57), intValue46);
                     float floatValue52 = floatValue45 * 1.3F;
                     int intValue53 = MathHelper.clamp((int)((float)intValue46 * 0.4F), 0, 255);
                     int intValue54 = dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(intValue51), dev.leg1tka.api.utils.text.ColorUtils.b(intValue51), dev.leg1tka.api.utils.text.ColorUtils.l(intValue51), intValue53);
                     this.b(matrixStack23, vec3d6, vec3d42.x, vec3d42.y, vec3d42.z, floatValue52, intValue54, 0.0F);
                     this.b(matrixStack23, vec3d6, vec3d42.x, vec3d42.y, vec3d42.z, floatValue45, intValue51, 0.0F);
                  }
               }

               RenderSystem.depthMask(true);
               RenderSystem.enableDepthTest();
               RenderSystem.enableCull();
               RenderSystem.defaultBlendFunc();
               RenderSystem.disableBlend();
            }
         }
      }
   }

   @EventLink(
      priority = -100
   )
   public void onRender2D(EventRender.Default default1) {
      if (this.k.b("Кристаллы") && !(this.H <= 0.001F) && this.C != null) {
         LivingEntity livingEntity2 = this.A != null && this.A.isAlive() ? this.A : null;
         this.a(default1.getContext().getMatrices(), livingEntity2);
      }
   }

   private int b(int intValue1, float floatValue2) {
      int intValue3 = (int)((float)(intValue1 >> 24 & 0xFF) * floatValue2);
      intValue3 = Math.max(0, Math.min(255, intValue3));
      return intValue3 << 24 | intValue1 & 16777215;
   }

   private int a(int intValue1, int intValue2) {
      intValue2 = Math.max(0, Math.min(255, intValue2));
      return intValue2 << 24 | intValue1 & 16777215;
   }

   public int a(int intValue1, int intValue2, float floatValue3) {
      floatValue3 = Math.max(0.0F, Math.min(1.0F, floatValue3));
      int intValue4 = intValue1 >> 16 & 0xFF;
      int intValue5 = intValue1 >> 8 & 0xFF;
      int intValue6 = intValue1 & 0xFF;
      int intValue7 = intValue1 >> 24 & 0xFF;
      int intValue8 = intValue2 >> 16 & 0xFF;
      int intValue9 = intValue2 >> 8 & 0xFF;
      int intValue10 = intValue2 & 0xFF;
      int intValue11 = intValue2 >> 24 & 0xFF;
      int intValue12 = (int)((float)intValue4 + (float)(intValue8 - intValue4) * floatValue3);
      int intValue13 = (int)((float)intValue5 + (float)(intValue9 - intValue5) * floatValue3);
      int intValue14 = (int)((float)intValue6 + (float)(intValue10 - intValue6) * floatValue3);
      int intValue15 = (int)((float)intValue7 + (float)(intValue11 - intValue7) * floatValue3);
      return intValue15 << 24 | intValue12 << 16 | intValue13 << 8 | intValue14;
   }

   private float a(LivingEntity livingEntity1) {
      if (this.p.getValue() && livingEntity1 != null) {
         float floatValue2 = minecraftClient != null ? minecraftClient.getRenderTickCounter().getTickDelta(true) : 0.0F;
         float floatValue3 = MathHelper.clamp((float)livingEntity1.hurtTime - floatValue2, 0.0F, 10.0F);
         float floatValue4 = floatValue3 / 10.0F;
         return floatValue4 * floatValue4 * (3.0F - 2.0F * floatValue4);
      } else {
         return 0.0F;
      }
   }

   private void a(MatrixStack matrixStack1, Vec3d vec3d2, double doubleValue3, double doubleValue5, double doubleValue7, float floatValue9, int intValue10, float floatValue11) {
      float floatValue12 = this.a(vec3d2, doubleValue3, doubleValue5, doubleValue7);
      float floatValue13 = floatValue9 * floatValue12 * 0.5F;
      this.c(matrixStack1, vec3d2, doubleValue3, doubleValue5, doubleValue7, floatValue13, intValue10, floatValue11);
   }

   private void b(MatrixStack matrixStack1, Vec3d vec3d2, double doubleValue3, double doubleValue5, double doubleValue7, float floatValue9, int intValue10, float floatValue11) {
      float floatValue12 = floatValue9 * 0.5F;
      this.c(matrixStack1, vec3d2, doubleValue3, doubleValue5, doubleValue7, floatValue12, intValue10, floatValue11);
   }

   private void c(MatrixStack matrixStack1, Vec3d vec3d2, double doubleValue3, double doubleValue5, double doubleValue7, float floatValue9, int intValue10, float floatValue11) {
      int intValue12 = intValue10 >> 16 & 0xFF;
      int intValue13 = intValue10 >> 8 & 0xFF;
      int intValue14 = intValue10 & 0xFF;
      int intValue15 = intValue10 >> 24 & 0xFF;
      if (intValue15 > 0) {
         matrixStack1.push();
         matrixStack1.translate(doubleValue3 - vec3d2.x, doubleValue5 - vec3d2.y, doubleValue7 - vec3d2.z);
         matrixStack1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-minecraftClient.gameRenderer.getCamera().getYaw()));
         matrixStack1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(minecraftClient.gameRenderer.getCamera().getPitch()));
         if (floatValue11 != 0.0F) {
            matrixStack1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue11));
         }

         Matrix4f matrix4f16 = matrixStack1.peek().getPositionMatrix();
         BufferBuilder bufferBuilder17 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
         bufferBuilder17.vertex(matrix4f16, -floatValue9, -floatValue9, 0.0F).texture(0.0F, 1.0F).color(intValue12, intValue13, intValue14, intValue15);
         bufferBuilder17.vertex(matrix4f16, -floatValue9, floatValue9, 0.0F).texture(0.0F, 0.0F).color(intValue12, intValue13, intValue14, intValue15);
         bufferBuilder17.vertex(matrix4f16, floatValue9, floatValue9, 0.0F).texture(1.0F, 0.0F).color(intValue12, intValue13, intValue14, intValue15);
         bufferBuilder17.vertex(matrix4f16, floatValue9, -floatValue9, 0.0F).texture(1.0F, 1.0F).color(intValue12, intValue13, intValue14, intValue15);
         BufferRenderer.drawWithGlobalProgram(bufferBuilder17.end());
         matrixStack1.pop();
      }
   }

   private void e(Event3DRender event3DRender1) {
      if (this.C != null && !(this.u <= 0.001F)) {
         Vec3d vec3d2 = minecraftClient.gameRenderer.getCamera().getPos();
         double doubleValue3 = this.C.x;
         double doubleValue5 = this.C.y + (double)((this.D + 0.4F) * 0.5F);
         double doubleValue7 = this.C.z;
         float floatValue9 = this.l.e().floatValue() * 12.0F;
         float floatValue10 = floatValue9 * this.v;
         long longValue11 = System.currentTimeMillis();
         float floatValue13 = Math.max(0.001F, (float)(longValue11 - this.z) / 1000.0F);
         this.z = longValue11;
         float floatValue14 = Math.max(0.35F, 2.2F / this.o.e().floatValue());

         for (this.w += floatValue13 / floatValue14; this.w >= 1.0F; this.y = this.y > 0.0F ? -280.0F : 280.0F) {
            this.w--;
            this.x = this.y;
         }

         float floatValue15 = (float)dev.leg1tka.api.animation.Easings.u.ease((double)this.w);
         float floatValue16 = MathHelper.lerp(floatValue15, this.x, this.y);
         float floatValue17 = this.a(this.A);
         int intValue18 = this.b(this.s(), this.u);
         int intValue19 = this.b(dev.leg1tka.api.utils.text.ColorUtils.b(255, 3, 3), this.u);
         int intValue20 = this.a(intValue18, intValue19, floatValue17);
         RenderSystem.enableBlend();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.disableCull();
         RenderSystem.blendFunc(770, 1);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
         RenderSystem.setShaderTexture(0, this.q());
         this.a(event3DRender1.getMatrices(), vec3d2, doubleValue3, doubleValue5, doubleValue7, floatValue10, intValue20, floatValue16);
         RenderSystem.enableCull();
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
      }
   }

   private void f(Event3DRender event3DRender1) {
      if (!(this.u <= 0.001F) && this.C != null) {
         float floatValue2 = minecraftClient.getRenderTickCounter().getTickDelta(true);
         LivingEntity livingEntity5 = this.A;
         Vec3d vec3d3;
         float floatValue4;
         if (livingEntity5 != null && livingEntity5.isAlive()) {
            vec3d3 = new Vec3d(
               MathHelper.lerp((double)floatValue2, livingEntity5.lastRenderX, livingEntity5.getX()),
               MathHelper.lerp((double)floatValue2, livingEntity5.lastRenderY, livingEntity5.getY()),
               MathHelper.lerp((double)floatValue2, livingEntity5.lastRenderZ, livingEntity5.getZ())
            );
            floatValue4 = livingEntity5.getHeight();
         } else {
            vec3d3 = this.C;
            floatValue4 = this.D;
         }

         Vec3d vec3d6 = minecraftClient.gameRenderer.getCamera().getPos();
         double doubleValue7 = vec3d3.x;
         double doubleValue9 = vec3d3.y + (double)(floatValue4 / 2.0F);
         double doubleValue11 = vec3d3.z;
         double doubleValue13 = 0.7;
         float floatValue15 = 4.0F;
         long longValue16 = System.currentTimeMillis();
         float floatValue18 = this.a(livingEntity5);
         int intValue19 = this.s();
         int intValue20 = dev.leg1tka.api.utils.text.ColorUtils.b(255, 3, 3);
         RenderSystem.disableDepthTest();
         RenderSystem.enableBlend();
         RenderSystem.depthMask(false);
         RenderSystem.disableCull();
         RenderSystem.blendFunc(770, 1);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
         RenderSystem.setShaderTexture(0, this.r());
         MatrixStack matrixStack21 = event3DRender1.getMatrices();

         for (int intValue22 = 0; intValue22 < 20; intValue22++) {
            float floatValue23 = 1.0F - (float)intValue22 / 20.0F * 0.7F;
            double doubleValue24 = 0.15 * ((double)longValue16 - (double)intValue22 * 10.0) / 25.0;
            double doubleValue26 = Math.sin(doubleValue24) * doubleValue13;
            double doubleValue28 = Math.cos(doubleValue24) * doubleValue13;
            double doubleValue30 = doubleValue7 + doubleValue26;
            double doubleValue32 = doubleValue9 + doubleValue28;
            double doubleValue34 = doubleValue11 - doubleValue28;
            float floatValue36 = floatValue15 * floatValue23;
            float floatValue37 = this.u * 0.6F;
            int intValue38 = this.b(intValue19, floatValue37 * this.u);
            int intValue39 = this.b(intValue20, floatValue37 * this.u);
            int intValue40 = this.a(intValue38, intValue39, floatValue18);
            this.b(matrixStack21, vec3d6, doubleValue30, doubleValue32, doubleValue34, floatValue36 * 0.12F, intValue40, 0.0F);
            int intValue41 = this.b(intValue40, 0.45F);
            this.b(matrixStack21, vec3d6, doubleValue30, doubleValue32, doubleValue34, floatValue36 * 0.21F, intValue41, 0.0F);
         }

         for (int intValue42 = 0; intValue42 < 20; intValue42++) {
            float floatValue44 = 1.0F - (float)intValue42 / 20.0F * 0.7F;
            double doubleValue46 = 0.15 * ((double)longValue16 - (double)intValue42 * 10.0) / 25.0;
            double doubleValue48 = Math.sin(doubleValue46) * doubleValue13;
            double doubleValue50 = Math.cos(doubleValue46) * doubleValue13;
            double doubleValue52 = doubleValue7 - doubleValue48;
            double doubleValue54 = doubleValue9 + doubleValue48;
            double doubleValue56 = doubleValue11 - doubleValue50;
            float floatValue58 = floatValue15 * floatValue44;
            float floatValue60 = this.u * 0.6F;
            int intValue62 = this.b(intValue19, floatValue60 * this.u);
            int intValue64 = this.b(dev.leg1tka.api.utils.text.ColorUtils.b(235, 7, 7), floatValue60 * this.u);
            int intValue66 = this.a(intValue62, intValue64, floatValue18);
            this.b(matrixStack21, vec3d6, doubleValue52, doubleValue54, doubleValue56, floatValue58 * 0.12F, intValue66, 0.0F);
            int intValue68 = this.b(intValue66, 0.45F);
            this.b(matrixStack21, vec3d6, doubleValue52, doubleValue54, doubleValue56, floatValue58 * 0.21F, intValue68, 0.0F);
         }

         for (int intValue43 = 0; intValue43 < 20; intValue43++) {
            float floatValue45 = 1.0F - (float)intValue43 / 20.0F * 0.7F;
            double doubleValue47 = 0.15 * ((double)longValue16 - (double)intValue43 * 10.0) / 25.0;
            double doubleValue49 = Math.sin(doubleValue47) * doubleValue13;
            double doubleValue51 = Math.cos(doubleValue47) * doubleValue13;
            double doubleValue53 = doubleValue7 - doubleValue49;
            double doubleValue55 = doubleValue9 - doubleValue49;
            double doubleValue57 = doubleValue11 + doubleValue51;
            float floatValue59 = floatValue15 * floatValue45;
            float floatValue61 = this.u * 0.6F;
            int intValue63 = this.b(intValue19, floatValue61 * this.u);
            int intValue65 = this.b(intValue20, floatValue61 * this.u);
            int intValue67 = this.a(intValue63, intValue65, floatValue18);
            this.b(matrixStack21, vec3d6, doubleValue53, doubleValue55, doubleValue57, floatValue59 * 0.12F, intValue67, 0.0F);
            int intValue69 = this.b(intValue67, 0.45F);
            this.b(matrixStack21, vec3d6, doubleValue53, doubleValue55, doubleValue57, floatValue59 * 0.21F, intValue69, 0.0F);
         }

         RenderSystem.enableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
      }
   }

   private void a(LivingEntity livingEntity1, float floatValue2, int intValue3, int intValue4, int intValue5) {
      float floatValue6 = 0.7F;
      float floatValue7 = livingEntity1.getHeight();
      int intValue8 = (int)this.s.e().floatValue();
      int intValue9 = (int)this.t.e().floatValue();
      long longValue10 = System.currentTimeMillis();
      float floatValue12 = (float)(longValue10 % (long)intValue8) / (float)intValue8;
      float floatValue13 = floatValue12 * 360.0F;
      float floatValue14 = (float)(longValue10 % (long)intValue9) / (float)intValue9;
      float floatValue15 = 0.5F - 0.5F * MathHelper.cos(floatValue14 * (float) (Math.PI * 2));

      for (int intValue16 = 0; intValue16 < intValue3; intValue16++) {
         float floatValue17 = (float)intValue16 / (float)intValue3;
         double doubleValue18 = Math.toRadians((double)MathHelper.wrapDegrees(floatValue17 * 360.0F + floatValue13));
         float floatValue20 = -((float)Math.sin(doubleValue18)) * floatValue6;
         float floatValue21 = floatValue7 * floatValue15;
         float floatValue22 = (float)Math.cos(doubleValue18) * floatValue6;
         this.F.add(new TargetParticle(floatValue20, floatValue21, floatValue22, intValue4, intValue5));
      }
   }

   private void g(Event3DRender event3DRender1) {
      if (!this.F.isEmpty() && !(this.u <= 0.001F)) {
         LivingEntity livingEntity2 = this.A != null ? this.A : this.B;
         if (livingEntity2 != null || this.C != null) {
            float floatValue3 = minecraftClient.getRenderTickCounter().getTickDelta(true);
            Vec3d vec3d4;
            if (livingEntity2 != null && livingEntity2.isAlive()) {
               vec3d4 = new Vec3d(
                  MathHelper.lerp((double)floatValue3, livingEntity2.lastRenderX, livingEntity2.getX()),
                  MathHelper.lerp((double)floatValue3, livingEntity2.lastRenderY, livingEntity2.getY()),
                  MathHelper.lerp((double)floatValue3, livingEntity2.lastRenderZ, livingEntity2.getZ())
               );
            } else {
               vec3d4 = this.C;
            }

            if (vec3d4 != null) {
               Vec3d vec3d5 = minecraftClient.gameRenderer.getCamera().getPos();
               float floatValue6 = this.a(livingEntity2);
               float floatValue7 = 6.0F;
               RenderSystem.disableDepthTest();
               RenderSystem.enableBlend();
               RenderSystem.depthMask(false);
               RenderSystem.disableCull();
               RenderSystem.blendFunc(770, 1);
               RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
               RenderSystem.setShaderTexture(0, this.r());
               MatrixStack matrixStack8 = event3DRender1.getMatrices();

               for (TargetParticle targetParticle10 : this.F) {
                  float floatValue11 = targetParticle10.b();
                  float floatValue12 = 1.0F - floatValue11 * 0.6F;
                  double doubleValue13 = vec3d4.x + (double)targetParticle10.a;
                  double doubleValue15 = vec3d4.y + (double)targetParticle10.b;
                  double doubleValue17 = vec3d4.z + (double)targetParticle10.c;
                  float floatValue19 = floatValue7 * floatValue12;
                  int intValue20 = (int)(255.0F * this.u * floatValue12 * 0.8F);
                  intValue20 = Math.max(0, Math.min(255, intValue20));
                  int intValue21 = this.a(targetParticle10.f, intValue20);
                  int intValue22 = this.a(dev.leg1tka.api.utils.text.ColorUtils.b(255, 3, 3), intValue20);
                  int intValue23 = this.a(intValue21, intValue22, floatValue6);
                  this.a(matrixStack8, vec3d5, doubleValue13, doubleValue15, doubleValue17, floatValue19, intValue23, 0.0F);
               }

               RenderSystem.enableCull();
               RenderSystem.enableDepthTest();
               RenderSystem.defaultBlendFunc();
               RenderSystem.disableBlend();
               RenderSystem.depthMask(true);
            }
         }
      }
   }

   private void h(Event3DRender event3DRender1) {
      if (!(this.u <= 0.001F) && this.C != null) {
         float floatValue2 = minecraftClient.getRenderTickCounter().getTickDelta(true);
         LivingEntity livingEntity5 = this.A;
         Vec3d vec3d3;
         if (livingEntity5 != null && livingEntity5.isAlive()) {
            vec3d3 = new Vec3d(
               MathHelper.lerp((double)floatValue2, livingEntity5.lastRenderX, livingEntity5.getX()),
               MathHelper.lerp((double)floatValue2, livingEntity5.lastRenderY, livingEntity5.getY()),
               MathHelper.lerp((double)floatValue2, livingEntity5.lastRenderZ, livingEntity5.getZ())
            );
            float floatValue49 = livingEntity5.getHeight();
         } else {
            vec3d3 = this.C;
            float floatValue4 = this.D;
         }

         Vec3d vec3d6 = minecraftClient.gameRenderer.getCamera().getPos();
         double doubleValue7 = vec3d3.x;
         double doubleValue9 = vec3d3.y;
         double doubleValue11 = vec3d3.z;
         double doubleValue13 = (double)System.currentTimeMillis() / 384.61539872299335 * 1.2F;
         double doubleValue15 = (double)System.currentTimeMillis() / 666.6666666666666 * 1.2F;
         int intValue17 = this.s();
         float floatValue18 = 4.0F;
         RenderSystem.disableDepthTest();
         RenderSystem.enableBlend();
         RenderSystem.depthMask(false);
         RenderSystem.disableCull();
         RenderSystem.blendFunc(770, 1);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
         RenderSystem.setShaderTexture(0, this.r());
         MatrixStack matrixStack19 = event3DRender1.getMatrices();
         float floatValue20 = 0.65F;

         for (int intValue21 = 0; intValue21 < 4; intValue21++) {
            for (int intValue22 = 0; intValue22 < 20; intValue22++) {
               float floatValue23 = (float)intValue22 / 20.0F;
               float floatValue24 = 1.0F - floatValue23 * 0.55F;
               double doubleValue25 = doubleValue13 - (double)intValue22 * 0.05;
               double doubleValue27 = doubleValue15 - (double)intValue22 * 0.05;
               double doubleValue29 = (Math.sin(doubleValue27) + 1.0) * 0.5;
               double doubleValue31 = Math.toRadians((double)intValue21 * 90.0 + doubleValue25 * 50.0 % 360.0);
               double doubleValue33 = Math.cos(doubleValue31) * (double)floatValue20;
               double doubleValue35 = Math.sin(doubleValue31) * (double)floatValue20;
               double doubleValue37 = intValue21 % 2 == 0 ? 0.1 + 1.7 * doubleValue29 : 1.8 - 1.7 * doubleValue29;
               double doubleValue39 = doubleValue7 + doubleValue33;
               double doubleValue41 = doubleValue9 + doubleValue37;
               double doubleValue43 = doubleValue11 + doubleValue35;
               float floatValue45 = floatValue18 * floatValue24;
               int intValue46 = (int)(255.0F * this.u * 0.6F);
               int intValue47 = this.a(intValue17, intValue46);
               this.a(matrixStack19, vec3d6, doubleValue39, doubleValue41, doubleValue43, floatValue45, intValue47, 0.0F);
               int intValue48 = this.b(intValue47, 0.45F);
               this.a(matrixStack19, vec3d6, doubleValue39, doubleValue41, doubleValue43, floatValue45 * 1.75F, intValue48, 0.0F);
            }

            floatValue20 *= -1.0F;
         }

         RenderSystem.enableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
      }
   }

   private void i(Event3DRender event3DRender1) {
      if (!(this.u <= 0.001F) && this.C != null) {
         LivingEntity livingEntity2 = this.A;
         if (livingEntity2 != null && livingEntity2.isAlive()) {
            MatrixStack matrixStack3 = event3DRender1.getMatrices();
            Vec3d vec3d4 = minecraftClient.gameRenderer.getCamera().getPos();
            long longValue5 = System.currentTimeMillis();
            float floatValue7 = (float)(longValue5 - this.U) / 120.0F;
            RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
            RenderSystem.setShaderTexture(0, this.r());
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 1, 0, 1);
            RenderSystem.disableCull();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            double doubleValue8 = MathHelper.lerp((double)event3DRender1.getTickDelta(), livingEntity2.lastRenderX, livingEntity2.getX()) - vec3d4.x;
            double doubleValue10 = MathHelper.lerp((double)event3DRender1.getTickDelta(), livingEntity2.lastRenderY, livingEntity2.getY()) - vec3d4.y;
            double doubleValue12 = MathHelper.lerp((double)event3DRender1.getTickDelta(), livingEntity2.lastRenderZ, livingEntity2.getZ()) - vec3d4.z;
            byte byteValue14 = 3;
            byte byteValue15 = 12;
            int intValue16 = 3 * byteValue14;
            matrixStack3.push();
            BufferBuilder bufferBuilder17 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            int intValue18 = this.s();
            int intValue19 = intValue18 >> 16 & 0xFF;
            int intValue20 = intValue18 >> 8 & 0xFF;
            int intValue21 = intValue18 & 0xFF;

            for (byte byteValue22 = 0; byteValue22 < intValue16; byteValue22 += byteValue14) {
               for (int intValue23 = 0; intValue23 < byteValue15; intValue23++) {
                  float floatValue24 = floatValue7 + (float)intValue23 * 0.1F;
                  int intValue25 = (int)Math.pow((double)byteValue22, 2.0);
                  matrixStack3.push();
                  matrixStack3.translate(
                     doubleValue8 + (double)(0.8F * MathHelper.sin(floatValue24 + (float)intValue25)),
                     doubleValue10 + 0.5 + (double)(0.3F * MathHelper.sin(floatValue7 + (float)intValue23 * 0.2F)) + (double)(0.2F * (float)byteValue22),
                     doubleValue12 + (double)(0.8F * MathHelper.cos(floatValue24 - (float)intValue25))
                  );
                  float floatValue26 = this.u * (0.005F + (float)intValue23 / 2000.0F);
                  matrixStack3.scale(floatValue26, floatValue26, floatValue26);
                  matrixStack3.multiply(minecraftClient.gameRenderer.getCamera().getRotation());
                  byte byteValue27 = -25;
                  byte byteValue28 = 50;
                  int intValue29 = (int)(this.u * 600.0F);
                  intValue29 = Math.max(0, Math.min(255, intValue29));
                  int intValue30 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue19, intValue20, intValue21, intValue29);
                  Matrix4f matrix4f31 = matrixStack3.peek().getPositionMatrix();
                  bufferBuilder17.vertex(matrix4f31, (float)byteValue27, (float)(byteValue27 + byteValue28), 0.0F).texture(0.0F, 1.0F).color(intValue30);
                  bufferBuilder17.vertex(matrix4f31, (float)(byteValue27 + byteValue28), (float)(byteValue27 + byteValue28), 0.0F).texture(1.0F, 1.0F).color(intValue30);
                  bufferBuilder17.vertex(matrix4f31, (float)(byteValue27 + byteValue28), (float)byteValue27, 0.0F).texture(1.0F, 0.0F).color(intValue30);
                  bufferBuilder17.vertex(matrix4f31, (float)byteValue27, (float)byteValue27, 0.0F).texture(0.0F, 0.0F).color(intValue30);
                  matrixStack3.pop();
               }
            }

            BufferRenderer.drawWithGlobalProgram(bufferBuilder17.end());
            matrixStack3.pop();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.blendFunc(770, 771);
            RenderSystem.enableCull();
         }
      }
   }

   private void a(MatrixStack matrixStack1, LivingEntity livingEntity2, float floatValue3) {
      if (this.C != null && !(this.H <= 0.01F)) {
         Vec3d vec3d4 = minecraftClient.gameRenderer.getCamera().getPos();
         int intValue5 = dev.leg1tka.api.utils.text.ColorUtils.a();
         int intValue6 = this.b(intValue5, this.H);
         int intValue7 = this.b(intValue5, this.H * 0.28F);
         float floatValue8 = this.a(livingEntity2);
         if (floatValue8 > 0.0F) {
            int intValue9 = this.b(dev.leg1tka.api.utils.text.ColorUtils.b(255, 3, 3), this.H);
            intValue6 = this.a(intValue6, intValue9, floatValue8);
            intValue7 = this.a(intValue7, this.b(intValue9, 0.65F), floatValue8);
         }

         float floatValue35 = livingEntity2 != null ? livingEntity2.getWidth() : this.E;
         float floatValue10 = livingEntity2 != null ? livingEntity2.getHeight() : this.D;
         float floatValue11 = floatValue35 * 1.5F;
         Vec3d vec3d12;
         if (livingEntity2 != null && livingEntity2.isAlive()) {
            vec3d12 = new Vec3d(
               MathHelper.lerp((double)floatValue3, livingEntity2.lastRenderX, livingEntity2.getX()),
               MathHelper.lerp((double)floatValue3, livingEntity2.lastRenderY, livingEntity2.getY()),
               MathHelper.lerp((double)floatValue3, livingEntity2.lastRenderZ, livingEntity2.getZ())
            );
         } else {
            vec3d12 = this.C;
         }

         RenderSystem.disableDepthTest();
         RenderSystem.enableBlend();
         RenderSystem.depthMask(false);
         RenderSystem.disableCull();
         float floatValue13 = 1.2F - 0.5F * this.H;
         matrixStack1.push();
         matrixStack1.translate(vec3d12.x - vec3d4.x, vec3d12.y - vec3d4.y, vec3d12.z - vec3d4.z);
         RenderSystem.defaultBlendFunc();
         RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
         BufferBuilder bufferBuilder14 = Tessellator.getInstance().begin(DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);

         for (byte byteValue15 = 0; byteValue15 < 360; byteValue15 += 20) {
            float floatValue16 = (float)Math.toRadians((double)((float)byteValue15 + this.G));
            float floatValue17 = (float)(Math.sin((double)floatValue16) * (double)floatValue11 * (double)floatValue13);
            float floatValue18 = (float)(Math.cos((double)floatValue16) * (double)floatValue11 * (double)floatValue13);
            float floatValue19 = 0.1F;
            float floatValue20 = 0.1F + floatValue10 * Math.abs(MathHelper.sin((float)byteValue15));
            float floatValue24 = floatValue10 / 2.0F;
            float floatValue25 = -floatValue17;
            float floatValue26 = floatValue24 - floatValue20;
            float floatValue27 = -floatValue18;
            float floatValue28 = (float)Math.sqrt((double)(floatValue25 * floatValue25 + floatValue26 * floatValue26 + floatValue27 * floatValue27));
            if (!(floatValue28 < 0.001F)) {
               floatValue25 /= floatValue28;
               floatValue26 /= floatValue28;
               floatValue27 /= floatValue28;
               matrixStack1.push();
               matrixStack1.translate(floatValue17, floatValue20, floatValue18);
               Vector3f vector3f29 = new Vector3f(0.0F, 1.0F, 0.0F);
               Vector3f vector3f30 = new Vector3f(floatValue25, floatValue26, floatValue27);
               Vector3f vector3f31 = new Vector3f();
               vector3f29.cross(vector3f30, vector3f31);
               float floatValue32 = vector3f31.length();
               if (floatValue32 >= 0.001F) {
                  vector3f31.div(floatValue32);
                  float floatValue33 = Math.max(-1.0F, Math.min(1.0F, vector3f29.dot(vector3f30)));
                  float floatValue34 = (float)Math.acos((double)floatValue33);
                  matrixStack1.multiply(new Quaternionf().setAngleAxis(floatValue34, vector3f31.x, vector3f31.y, vector3f31.z));
               }

               this.a(bufferBuilder14, matrixStack1.peek().getPositionMatrix(), floatValue19, intValue6);
               matrixStack1.pop();
            }
         }

         BufferRenderer.drawWithGlobalProgram(bufferBuilder14.end());
         matrixStack1.pop();
         float floatValue36 = 4.5F + floatValue35 * 3.0F;
         float floatValue37 = floatValue36 * 1.28F;
         RenderSystem.blendFunc(770, 1);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
         RenderSystem.setShaderTexture(0, this.r());

         for (byte byteValue38 = 0; byteValue38 < 360; byteValue38 += 20) {
            float floatValue39 = (float)Math.toRadians((double)((float)byteValue38 + this.G));
            float floatValue40 = (float)(Math.sin((double)floatValue39) * (double)floatValue11 * (double)floatValue13);
            float floatValue41 = (float)(Math.cos((double)floatValue39) * (double)floatValue11 * (double)floatValue13);
            float floatValue21 = 0.1F + floatValue10 * Math.abs(MathHelper.sin((float)byteValue38));
            double doubleValue22 = vec3d12.x + (double)floatValue40;
            double doubleValue42 = vec3d12.y + (double)floatValue21;
            double doubleValue45 = vec3d12.z + (double)floatValue41;
            this.a(matrixStack1, vec3d4, doubleValue22, doubleValue42, doubleValue45, floatValue37, this.b(intValue7, 0.24F), this.G + (float)byteValue38);
            this.a(matrixStack1, vec3d4, doubleValue22, doubleValue42, doubleValue45, floatValue36, intValue7, -(this.G + (float)byteValue38 * 0.5F));
         }

         RenderSystem.enableDepthTest();
         RenderSystem.enableCull();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
         RenderSystem.depthMask(true);
      }
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, float floatValue3, int intValue4) {
      int intValue5 = intValue4 >> 16 & 0xFF;
      int intValue6 = intValue4 >> 8 & 0xFF;
      int intValue7 = intValue4 & 0xFF;
      int intValue8 = intValue4 >> 24 & 0xFF;
      float floatValue9 = 0.34F * floatValue3 / 0.1F;
      float floatValue10 = 1.15F * floatValue3 / 0.1F;
      floatValue9 = 0.06F;
      floatValue10 = 0.2F;
      this.a(bufferBuilder1, matrix4f2, 0.0F, floatValue10, 0.0F, floatValue9, 0.0F, 0.0F, 0.0F, 0.0F, floatValue9, intValue5, intValue6, intValue7, intValue8);
      this.a(bufferBuilder1, matrix4f2, 0.0F, floatValue10, 0.0F, 0.0F, 0.0F, floatValue9, -floatValue9, 0.0F, 0.0F, intValue5, intValue6, intValue7, intValue8);
      this.a(bufferBuilder1, matrix4f2, 0.0F, floatValue10, 0.0F, -floatValue9, 0.0F, 0.0F, 0.0F, 0.0F, -floatValue9, intValue5, intValue6, intValue7, intValue8);
      this.a(bufferBuilder1, matrix4f2, 0.0F, floatValue10, 0.0F, 0.0F, 0.0F, -floatValue9, floatValue9, 0.0F, 0.0F, intValue5, intValue6, intValue7, intValue8);
      this.a(bufferBuilder1, matrix4f2, 0.0F, -floatValue10, 0.0F, floatValue9, 0.0F, 0.0F, 0.0F, 0.0F, floatValue9, intValue5, intValue6, intValue7, intValue8);
      this.a(bufferBuilder1, matrix4f2, 0.0F, -floatValue10, 0.0F, 0.0F, 0.0F, floatValue9, -floatValue9, 0.0F, 0.0F, intValue5, intValue6, intValue7, intValue8);
      this.a(bufferBuilder1, matrix4f2, 0.0F, -floatValue10, 0.0F, -floatValue9, 0.0F, 0.0F, 0.0F, 0.0F, -floatValue9, intValue5, intValue6, intValue7, intValue8);
      this.a(bufferBuilder1, matrix4f2, 0.0F, -floatValue10, 0.0F, 0.0F, 0.0F, -floatValue9, floatValue9, 0.0F, 0.0F, intValue5, intValue6, intValue7, intValue8);
   }

   private float[] a(double doubleValue1, double doubleValue3, double doubleValue5) {
      return null;
   }

   private double b(double doubleValue1, double doubleValue3, double doubleValue5) {
      Vec3d vec3d7 = minecraftClient.gameRenderer.getCamera().getPos();
      double doubleValue8 = doubleValue1 - vec3d7.x;
      double doubleValue10 = doubleValue3 - vec3d7.y;
      double doubleValue12 = doubleValue5 - vec3d7.z;
      double doubleValue14 = Math.sqrt(doubleValue8 * doubleValue8 + doubleValue10 * doubleValue10 + doubleValue12 * doubleValue12);
      return Math.max(0.5, 8.0 / Math.max(0.1, doubleValue14));
   }

   private void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      int intValue7 = intValue6 >> 16 & 0xFF;
      int intValue8 = intValue6 >> 8 & 0xFF;
      int intValue9 = intValue6 & 0xFF;
      int intValue10 = intValue6 >> 24 & 0xFF;
      if (intValue10 > 0) {
         Matrix4f matrix4f11 = matrixStack1.peek().getPositionMatrix();
         BufferBuilder bufferBuilder12 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
         bufferBuilder12.vertex(matrix4f11, floatValue2, floatValue3, 0.0F).texture(0.0F, 0.0F).color(intValue7, intValue8, intValue9, intValue10);
         bufferBuilder12.vertex(matrix4f11, floatValue2, floatValue3 + floatValue5, 0.0F).texture(0.0F, 1.0F).color(intValue7, intValue8, intValue9, intValue10);
         bufferBuilder12.vertex(matrix4f11, floatValue2 + floatValue4, floatValue3 + floatValue5, 0.0F).texture(1.0F, 1.0F).color(intValue7, intValue8, intValue9, intValue10);
         bufferBuilder12.vertex(matrix4f11, floatValue2 + floatValue4, floatValue3, 0.0F).texture(1.0F, 0.0F).color(intValue7, intValue8, intValue9, intValue10);
         BufferRenderer.drawWithGlobalProgram(bufferBuilder12.end());
      }
   }

   private void a(MatrixStack matrixStack1, LivingEntity livingEntity2) {
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
      int intValue13,
      int intValue14,
      int intValue15
   ) {
      bufferBuilder1.vertex(matrix4f2, floatValue3, floatValue4, floatValue5).color(intValue12, intValue13, intValue14, intValue15);
      bufferBuilder1.vertex(matrix4f2, floatValue6, floatValue7, floatValue8).color(intValue12, intValue13, intValue14, intValue15);
      bufferBuilder1.vertex(matrix4f2, floatValue9, floatValue10, floatValue11).color(intValue12, intValue13, intValue14, intValue15);
   }

   private void t() {
      this.V.clear();

      for (int intValue1 = 0; intValue1 < 3; intValue1++) {
         TargetESP.a value2 = new TargetESP.a();
         value2.a = (float)((double)intValue1 * (Math.PI * 2.0 / 3.0));
         value2.b = 0.0;
         value2.c = 0.0;
         value2.d = 0.0;
         value2.e = 0;

         for (int intValue3 = 0; intValue3 < 20; intValue3++) {
            value2.f[intValue3] = new TargetESP.c();
         }

         this.V.add(value2);
      }
   }

   private void j(Event3DRender event3DRender1) {
      if (!(this.u <= 0.01F) && this.C != null) {
         LivingEntity livingEntity2 = this.A != null && this.A.isAlive() ? this.A : null;
         if (livingEntity2 != null || this.C != null) {
            if (this.V.isEmpty()) {
               this.t();
            }

            float floatValue3 = event3DRender1.getTickDelta();
            double doubleValue4 = livingEntity2 != null ? MathHelper.lerp((double)floatValue3, livingEntity2.lastRenderX, livingEntity2.getX()) : this.C.x;
            double doubleValue6 = livingEntity2 != null ? MathHelper.lerp((double)floatValue3, livingEntity2.lastRenderY, livingEntity2.getY()) : this.C.y;
            double doubleValue8 = livingEntity2 != null ? MathHelper.lerp((double)floatValue3, livingEntity2.lastRenderZ, livingEntity2.getZ()) : this.C.z;
            float floatValue10 = livingEntity2 != null ? livingEntity2.getHeight() : this.D;
            doubleValue6 += (double)floatValue10 / 2.0;
            long longValue11 = System.currentTimeMillis();
            if (this.W == 0L) {
               this.W = longValue11;
               this.X = longValue11;
            }

            long longValue13 = longValue11 - this.W;
            this.W = longValue11;
            float floatValue15 = (float)longValue13 * 0.003F;
            float floatValue16 = 0.45F;
            float floatValue17 = 0.4F;

            for (int intValue18 = 0; intValue18 < this.V.size(); intValue18++) {
               TargetESP.a value19 = (TargetESP.a)this.V.get(intValue18);
               value19.a += floatValue15;
               float floatValue20 = value19.a + (float)((double)intValue18 * (Math.PI * 2.0 / 3.0));
               double doubleValue21 = Math.sin((double)floatValue20) * (double)floatValue16;
               double doubleValue23 = Math.cos((double)floatValue20) * (double)floatValue16;
               double doubleValue25 = Math.sin((double)floatValue20 * 2.0) * (double)floatValue17;
               value19.b = doubleValue4 + doubleValue21;
               value19.c = doubleValue6 + doubleValue25;
               value19.d = doubleValue8 + doubleValue23;
            }

            if (longValue11 - this.X >= 30L) {
               this.X = longValue11;

               for (int intValue40 = 0; intValue40 < this.V.size(); intValue40++) {
                  TargetESP.a value42 = (TargetESP.a)this.V.get(intValue40);
                  if (value42.e > 0) {
                     double doubleValue44 = value42.b - value42.f[0].a;
                     double doubleValue22 = value42.c - value42.f[0].b;
                     double doubleValue24 = value42.d - value42.f[0].c;
                     if (doubleValue44 * doubleValue44 + doubleValue22 * doubleValue22 + doubleValue24 * doubleValue24 > 10.0) {
                        value42.e = 0;
                     }
                  }

                  int intValue45 = Math.min(value42.e, 19);

                  for (int intValue47 = intValue45; intValue47 > 0; intValue47--) {
                     value42.f[intValue47].a = value42.f[intValue47 - 1].a;
                     value42.f[intValue47].b = value42.f[intValue47 - 1].b;
                     value42.f[intValue47].c = value42.f[intValue47 - 1].c;
                  }

                  value42.f[0].a = value42.b;
                  value42.f[0].b = value42.c;
                  value42.f[0].c = value42.d;
                  if (value42.e < 20) {
                     value42.e++;
                  }
               }
            }

            int intValue41 = this.s();
            float floatValue43 = livingEntity2 != null ? this.a(livingEntity2) : 0.0F;
            float floatValue46 = this.u;
            float floatValue48 = 0.2F;
            Vec3d vec3d49 = minecraftClient.gameRenderer.getCamera().getPos();
            Camera camera50 = minecraftClient.gameRenderer.getCamera();
            float floatValue51 = -camera50.getYaw();
            float floatValue52 = camera50.getPitch();
            MatrixStack matrixStack26 = event3DRender1.getMatrices();
            int intValue27 = intValue41 >> 16 & 0xFF;
            int intValue28 = intValue41 >> 8 & 0xFF;
            int intValue29 = intValue41 & 0xFF;
            float floatValue30 = (float)intValue27 / 255.0F;
            float floatValue31 = (float)intValue28 / 255.0F;
            float floatValue32 = (float)intValue29 / 255.0F;
            floatValue30 = Math.min(1.0F, floatValue30 + floatValue43 * 0.5F);
            floatValue31 *= 1.0F - floatValue43 * 0.3F;
            floatValue32 *= 1.0F - floatValue43 * 0.3F;
            RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
            RenderSystem.setShaderTexture(0, this.r());
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 1, 0, 1);
            RenderSystem.disableCull();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            boolean flag33 = false;
            BufferBuilder bufferBuilder34 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

            for (int intValue35 = 0; intValue35 < this.V.size(); intValue35++) {
               TargetESP.a value36 = (TargetESP.a)this.V.get(intValue35);

               for (int intValue37 = value36.e - 1; intValue37 >= 0; intValue37--) {
                  TargetESP.c value38 = value36.f[intValue37];
                  if (this.a(bufferBuilder34, matrixStack26, floatValue51, floatValue52, vec3d49, value38.a, value38.b, value38.c, intValue37, value36.e, floatValue46, floatValue48, floatValue43, floatValue30, floatValue31, floatValue32)) {
                     flag33 = true;
                  }
               }

               if (this.a(bufferBuilder34, matrixStack26, floatValue51, floatValue52, vec3d49, value36.b, value36.c, value36.d, 0, 1, floatValue46, floatValue48, floatValue43, floatValue30, floatValue31, floatValue32)) {
                  flag33 = true;
               }
            }

            if (flag33) {
               BufferRenderer.drawWithGlobalProgram(bufferBuilder34.end());
            }

            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            RenderSystem.blendFunc(770, 771);
            RenderSystem.enableCull();
         }
      }
   }

   private boolean a(
      BufferBuilder bufferBuilder1,
      MatrixStack matrixStack2,
      float floatValue3,
      float floatValue4,
      Vec3d vec3d5,
      double doubleValue6,
      double doubleValue8,
      double doubleValue10,
      int intValue12,
      int intValue13,
      float floatValue14,
      float floatValue15,
      float floatValue16,
      float floatValue17,
      float floatValue18,
      float floatValue19
   ) {
      double doubleValue20 = doubleValue6 - vec3d5.x;
      double doubleValue22 = doubleValue8 - vec3d5.y;
      double doubleValue24 = doubleValue10 - vec3d5.z;
      float floatValue26 = (float)intValue12 / (float)Math.max(1, intValue13);
      if (intValue13 == 1) {
         floatValue26 = 0.0F;
      }

      float floatValue27 = 1.0F - floatValue26 * 0.7F;
      float floatValue28 = floatValue14 * floatValue15 * floatValue27;
      if (floatValue28 < 0.01F) {
         return false;
      } else {
         float floatValue29 = floatValue14 * (1.0F - floatValue26 * 0.85F);
         if (floatValue29 < 0.01F) {
            return false;
         } else {
            float floatValue31 = floatValue29 * 0.55F;
            float floatValue32 = floatValue29 * 0.28F;
            float floatValue34 = floatValue28 * 1.9F;
            float floatValue35 = floatValue28 * 3.4F;
            float floatValue36 = Math.min(1.0F, floatValue17 * 1.2F);
            float floatValue37 = Math.min(1.0F, floatValue18 * 1.2F);
            float floatValue38 = Math.min(1.0F, floatValue19 * 1.2F);
            matrixStack2.push();
            matrixStack2.translate(doubleValue20, doubleValue22, doubleValue24);
            matrixStack2.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(floatValue3));
            matrixStack2.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue4));
            Matrix4f matrix4f39 = matrixStack2.peek().getPositionMatrix();
            this.a(bufferBuilder1, matrix4f39, floatValue35, floatValue17, floatValue18, floatValue19, floatValue32);
            this.a(bufferBuilder1, matrix4f39, floatValue34, floatValue17, floatValue18, floatValue19, floatValue31);
            this.a(bufferBuilder1, matrix4f39, floatValue28, floatValue36, floatValue37, floatValue38, floatValue29);
            matrixStack2.pop();
            return true;
         }
      }
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      float floatValue8 = floatValue3 * 0.5F;
      int intValue9 = (int)(floatValue4 * 255.0F);
      int intValue10 = (int)(floatValue5 * 255.0F);
      int intValue11 = (int)(floatValue6 * 255.0F);
      int intValue12 = (int)(floatValue7 * 255.0F);
      bufferBuilder1.vertex(matrix4f2, -floatValue8, -floatValue8, 0.0F).texture(0.0F, 1.0F).color(intValue9, intValue10, intValue11, intValue12);
      bufferBuilder1.vertex(matrix4f2, -floatValue8, floatValue8, 0.0F).texture(0.0F, 0.0F).color(intValue9, intValue10, intValue11, intValue12);
      bufferBuilder1.vertex(matrix4f2, floatValue8, floatValue8, 0.0F).texture(1.0F, 0.0F).color(intValue9, intValue10, intValue11, intValue12);
      bufferBuilder1.vertex(matrix4f2, floatValue8, -floatValue8, 0.0F).texture(1.0F, 1.0F).color(intValue9, intValue10, intValue11, intValue12);
   }

   static class a {
      float a;
      double b;
      double c;
      double d;
      int e;
      final TargetESP.c[] f = new TargetESP.c[20];

      a() {
      }
   }

   static class TargetParticle {
      final float a;
      final float b;
      final float c;
      final long d;
      final int e;
      final int f;

      TargetParticle(float floatValue1, float floatValue2, float floatValue3, int intValue4, int intValue5) {
         this.a = floatValue1;
         this.b = floatValue2;
         this.c = floatValue3;
         this.d = System.currentTimeMillis();
         this.e = intValue4;
         this.f = intValue5;
      }

      boolean a() {
         return System.currentTimeMillis() - this.d >= (long)this.e;
      }

      float b() {
         return MathHelper.clamp((float)(System.currentTimeMillis() - this.d) / (float)this.e, 0.0F, 1.0F);
      }

      int a(float floatValue1) {
         int intValue2 = (int)((float)(this.f >> 24 & 0xFF) * (1.0F - floatValue1));
         intValue2 = Math.max(0, Math.min(255, intValue2));
         return intValue2 << 24 | this.f & 16777215;
      }
   }

   static class c {
      double a;
      double b;
      double c;

      c() {
      }
   }
}
