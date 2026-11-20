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

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.Event3DRender;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.LightType;
import net.minecraft.world.Heightmap.Type;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL33;

public class Atmosphere extends Module {
   public static Atmosphere c = new Atmosphere();
   private final ModeSetting k = new ModeSetting(
      "Атмосфера",
      "Зима",
      "Нет",
      "Зима",
      "Сакура"
   );
   private final ModeSetting l = new ModeSetting("Стиль", "Выкл", "Выкл", "Complementary", "Sildur's");
   private final BooleanSetting m = new BooleanSetting("Motion blur", true).visibleWhen(() -> !this.l.b("Выкл"));
   private final NumberSetting n = new NumberSetting("Сила размытия", 100.0F, 10.0F, 600.0F, 10.0F)
      .a(() -> !this.l.b("Выкл") && this.m.getValue());
   private final ModeSetting o = new ModeSetting(
         "Качество",
         "Высокое",
         "Низкое",
         "Среднее",
         "Высокое"
      )
      .a(() -> !this.k.b("Нет"));
   private final MultiSetting p = new MultiSetting(
         "Слои",
         new BooleanSetting("Частицы", true),
         new BooleanSetting("Покрытие мира", true),
         new BooleanSetting("Объёмный туман", true),
         new BooleanSetting("Лучи света", true),
         new BooleanSetting("Небо", true),
         new BooleanSetting("Дымка", true),
         new BooleanSetting("Цветокоррекция", true)
      )
      .a(() -> !this.k.b("Нет"));
   private final MultiSetting q = new MultiSetting(
         "Детали",
         new BooleanSetting("Иней на экране", true),
         new BooleanSetting("Северное сияние", true),
         new BooleanSetting("Искры на снегу", true),
         new BooleanSetting("Позёмка", true)
      )
      .a(() -> !this.k.b("Нет"));
   private final NumberSetting r = new NumberSetting("Плотность", 100.0F, 20.0F, 100.0F, 5.0F).a(() -> !this.k.b("Нет"));
   private final NumberSetting s = new NumberSetting("Покрытие", 100.0F, 0.0F, 100.0F, 5.0F)
      .a(() -> !this.k.b("Нет") && this.p.a("Покрытие мира"));
   private final NumberSetting t = new NumberSetting("Интенсивность", 100.0F, 0.0F, 150.0F, 5.0F)
      .a(() -> !this.k.b("Нет"));
   private final NumberSetting u = new NumberSetting("Ветер", 100.0F, 0.0F, 200.0F, 10.0F).a(() -> !this.k.b("Нет"));
   private final dev.leg1tka.api.utils.math.ParticleMotionState v = new dev.leg1tka.api.utils.math.ParticleMotionState();
   private static float w;
   private static final Identifier x = Identifier.of("aspectdlc", "atmosphere_heightmap");
   private static final Identifier y = Identifier.of("aspectdlc", "atmosphere_noise");
   private boolean z = false;
   private VertexBuffer A;
   private int B = -1;
   private NativeImageBackedTexture C;
   private int D = 0;
   private boolean E;
   private SimpleFramebuffer F;
   private SimpleFramebuffer G;
   private SimpleFramebuffer H;
   private SimpleFramebuffer I;
   private final Matrix4f J = new Matrix4f();
   private final Matrix4f K = new Matrix4f();
   private final Matrix4f L = new Matrix4f();
   private Vec3d M = Vec3d.ZERO;
   private Vec3d N = null;
   private boolean O = false;
   private float P = 0.0F;
   public static float d = 0.7F;
   public static float e = 0.8F;
   public static float f = 1.0F;
   private SimpleFramebuffer Q;
   private double R;
   private double S;
   private final Mutable T = new Mutable();
   private final long U = System.nanoTime();
   private long V = 0L;
   private double W;
   private double X;
   private float Y = 1.0F;
   private float Z = 1.0F;
   private float ModuleStorage = 0.0F;
   private float ab = 0.0F;
   public static boolean g = false;
   public static float h = -1.0F;
   private final int[] ac = new int[8];
   private final boolean[] ad = new boolean[8];
   private int ae = 0;
   public static double i = 0.0;
   public static int j = 0;
   private static Boolean af = null;
   private boolean ag = false;

   public Atmosphere() {
      super(
         "Atmosphere",
         "Атмосфера вокруг: зима, сакура",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u});
   }

   public ModeSetting p() {
      return this.k;
   }

   public ModeSetting q() {
      return this.o;
   }

   public MultiSetting r() {
      return this.p;
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.ab = 0.0F;
      this.D = 0;
   }

   @Override
   public void onDisable() {
      super.onDisable();
      RenderSystem.recordRenderCall(this::v);
   }

   private void v() {
      if (this.A != null) {
         this.A.close();
         this.A = null;
         this.B = -1;
      }

      if (this.F != null) {
         this.F.delete();
         this.F = null;
      }

      if (this.G != null) {
         this.G.delete();
         this.G = null;
      }

      if (this.Q != null) {
         this.Q.delete();
         this.Q = null;
      }

      if (this.H != null) {
         this.H.delete();
         this.H = null;
      }

      if (this.I != null) {
         this.I.delete();
         this.I = null;
      }

      this.N = null;
   }

   private int w() {
      String string1 = this.o.g();

      return switch (string1) {
         case "Низкое" -> 3500;
         case "Среднее" -> 7000;
         default -> 12000;
      };
   }

   private void b(int intValue1) {
      if (this.A != null) {
         this.A.close();
      }

      Random random2 = new Random(684805L);
      this.A = VertexBuffer.createAndUpload(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR, vertexConsumer2x -> {
         for (int intValue3 = 0; intValue3 < intValue1; intValue3++) {
            float floatValue4 = random2.nextFloat();
            float floatValue5 = random2.nextFloat();
            float floatValue6 = random2.nextFloat();
            int intValue7 = random2.nextInt(256);
            int intValue8 = random2.nextInt(256);
            int intValue9 = random2.nextInt(256);
            int intValue10 = (int)(((float)intValue3 + 0.5F) / (float)intValue1 * 255.0F);
            vertexConsumer2x.vertex(floatValue4, floatValue5, floatValue6).texture(0.0F, 0.0F).color(intValue7, intValue8, intValue9, intValue10);
            vertexConsumer2x.vertex(floatValue4, floatValue5, floatValue6).texture(0.0F, 1.0F).color(intValue7, intValue8, intValue9, intValue10);
            vertexConsumer2x.vertex(floatValue4, floatValue5, floatValue6).texture(1.0F, 1.0F).color(intValue7, intValue8, intValue9, intValue10);
            vertexConsumer2x.vertex(floatValue4, floatValue5, floatValue6).texture(1.0F, 0.0F).color(intValue7, intValue8, intValue9, intValue10);
         }
      });
      this.B = intValue1;
   }

   private void x() {
      if (this.C == null) {
         this.C = new NativeImageBackedTexture(64, 64, false);
         this.C.setFilter(false, false);
         minecraftClient.getTextureManager().registerTexture(x, this.C);
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      ClientWorld clientWorld2 = minecraftClient.world;
      if (clientWorld2 != null && minecraftClient.player != null) {
         this.x();
         NativeImage nativeImage3 = this.C.getImage();
         if (nativeImage3 != null) {
            int intValue4 = minecraftClient.player.getBlockX();
            int intValue5 = minecraftClient.player.getBlockZ();
            int intValue6 = clientWorld2.getBottomY();

            for (int intValue7 = 0; intValue7 < 16; intValue7++) {
               int intValue8 = intValue5 - 32 + this.D;
               this.D = this.D + 1 & 63;

               for (int intValue9 = intValue4 - 32; intValue9 < intValue4 + 32; intValue9++) {
                  int intValue10 = 0;
                  short value11 = 0;
                  if (clientWorld2.isChunkLoaded(intValue9 >> 4, intValue8 >> 4)) {
                     int intValue12 = clientWorld2.getTopY(Type.MOTION_BLOCKING, intValue9, intValue8);
                     int intValue13 = intValue12 - 1;
                     if (intValue13 >= intValue6) {
                        BlockState blockState14 = clientWorld2.getBlockState(this.T.set(intValue9, intValue13, intValue8));
                        if (blockState14.isIn(BlockTags.LEAVES)) {
                           value11 = 255;
                        } else if (blockState14.getFluidState().isIn(FluidTags.WATER)) {
                           value11 = 128;
                        }

                        if (blockState14.isOf(Blocks.BARRIER)) {
                           for (int intValue15 = 400; intValue13 >= intValue6 && intValue15-- > 0; intValue13--) {
                              blockState14 = clientWorld2.getBlockState(this.T.set(intValue9, intValue13, intValue8));
                              if (!blockState14.isOf(Blocks.BARRIER) && (blockState14.blocksMovement() || !blockState14.getFluidState().isEmpty())) {
                                 break;
                              }
                           }
                        }

                        if (intValue13 >= intValue6) {
                           intValue10 = Math.min(65535, intValue13 + 1 - intValue6 + 1);
                        }
                     }
                  }

                  nativeImage3.setColorArgb(intValue9 & 63, intValue8 & 63, 0xFF000000 | (intValue10 & 0xFF) << 16 | (intValue10 >> 8 & 0xFF) << 8 | value11);
               }
            }

            this.E = true;
            int intValue16 = clientWorld2.getLightLevel(LightType.SKY, BlockPos.ofFloored(minecraftClient.gameRenderer.getCamera().getPos()));
            float floatValue17 = MathHelper.clamp(((float)intValue16 - 3.0F) / 9.0F, 0.0F, 1.0F);
            this.Y = this.Y + (floatValue17 - this.Y) * 0.12F;
         }
      }
   }

   @EventLink
   public void onRender3D(Event3DRender event3DRender1) {
      ClientWorld clientWorld2 = minecraftClient.world;
      if (clientWorld2 != null && minecraftClient.player != null) {
         long longValue3 = System.nanoTime();
         float floatValue5 = this.V == 0L ? 0.016F : Math.min(0.1F, (float)(longValue3 - this.V) / 1.0E9F);
         this.V = longValue3;
         this.ab = Math.min(1.0F, this.ab + floatValue5 / 1.5F);
         float floatValue6 = this.ab * this.ab * (3.0F - 2.0F * this.ab);
         boolean flag7 = this.k.b("Зима");
         boolean flag8 = !this.k.b("Нет");
         this.O = false;
         Camera camera9 = event3DRender1.getCamera();
         Vec3d vec3d10 = camera9.getPos();
         float floatValue11 = (float)((double)(longValue3 - this.U) / 1.0E9 % 20000.0);
         float floatValue12 = event3DRender1.getTickDelta();
         float floatValue13 = clientWorld2.getSkyAngle(floatValue12);
         if (h >= 0.0F) {
            floatValue13 = 0.5F;
         }

         this.v.a(flag7, floatValue13);
         float floatValue14 = 0.3F + 0.7F * this.v.j;
         float floatValue15 = this.u.f() / 100.0F;
         float floatValue16 = 0.55F + 0.45F * MathHelper.sin(floatValue11 * 0.13F) * MathHelper.sin(floatValue11 * 0.29F + 1.3F);
         float floatValue17 = (flag7 ? 1.1F : 0.7F) * floatValue15 * (0.4F + floatValue16);
         float floatValue18 = (flag7 ? 0.45F : 0.35F) * floatValue15 * (0.4F + floatValue16);
         this.W = (this.W + (double)(floatValue17 * floatValue5)) % 40.0;
         this.X = (this.X + (double)(floatValue18 * floatValue5)) % 40.0;
         this.R = (this.R + (double)(floatValue17 * floatValue5) * 0.6) % 4096.0;
         this.S = (this.S + (double)(floatValue18 * floatValue5) * 0.6) % 4096.0;
         float floatValue19 = (float)Math.sqrt((double)(floatValue17 * floatValue17 + floatValue18 * floatValue18));
         this.Z = floatValue19 > 0.001F ? floatValue17 / floatValue19 : 1.0F;
         this.ModuleStorage = floatValue19 > 0.001F ? floatValue18 / floatValue19 : 0.0F;
         Matrix4f matrix4f20 = event3DRender1.getPositionMatrix();
         Matrix4f matrix4f21 = event3DRender1.getProjectionMatrix();
         boolean flag22 = u();
         float floatValue23 = Math.min(1.0F, this.t.f() / 100.0F);
         this.x();
         if (this.E && this.C != null) {
            this.C.upload();
            this.E = false;
         }

         this.B();
         boolean flag24 = this.p.a("Покрытие мира")
            || this.p.a("Небо")
            || this.p.a("Дымка")
            || this.p.a("Цветокоррекция")
            || this.p.a("Объёмный туман")
            || this.p.a("Лучи света")
            || flag7 && this.q.a("Иней на экране");
         if (flag8 && !flag22 && flag24) {
            this.b(matrix4f20, matrix4f21, vec3d10, clientWorld2, floatValue11, flag7, floatValue13, floatValue6 * floatValue23);
         }

         if (flag8 && this.p.a("Частицы")) {
            this.a(matrix4f20, matrix4f21, vec3d10, clientWorld2, floatValue11, flag7, floatValue14, floatValue6);
         }

         this.J.set(matrix4f20);
         this.K.set(matrix4f21);
         this.M = vec3d10;
         this.P = clientWorld2.getSkyAngle(floatValue12);
         if (this.s()) {
            Framebuffer framebuffer25 = minecraftClient.getFramebuffer();
            if (!this.O) {
               int intValue26 = framebuffer25.textureWidth;
               int intValue27 = framebuffer25.textureHeight;
               if (this.F == null || this.F.textureWidth != intValue26 || this.F.textureHeight != intValue27) {
                  if (this.F != null) {
                     this.F.delete();
                  }

                  this.F = new SimpleFramebuffer(intValue26, intValue27, true);
               }

               GlStateManager._glBindFramebuffer(36008, framebuffer25.fbo);
               GlStateManager._glBindFramebuffer(36009, this.F.fbo);
               GlStateManager._glBlitFrameBuffer(0, 0, intValue26, intValue27, 0, 0, intValue26, intValue27, 256, 9728);
               framebuffer25.beginWrite(true);
               this.O = true;
            }
         }

         this.C();
         RenderSystem.setShaderTexture(0, 0);
         RenderSystem.setShaderTexture(1, 0);
         RenderSystem.setShaderTexture(2, 0);
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
         RenderSystem.enableCull();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
      }
   }

   private void a(Matrix4f matrix4f1, Matrix4f matrix4f2, Vec3d vec3d3, ClientWorld clientWorld4, float floatValue5, boolean flag6, float floatValue7, float floatValue8) {
      int intValue9 = this.w();
      if (this.A == null || this.B != intValue9) {
         this.b(intValue9);
      }

      ShaderProgram shaderProgram10 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.T);
      if (shaderProgram10 != null) {
         float floatValue11 = this.r.f() / 100.0F * (flag6 ? 1.0F : 0.32F);
         a(shaderProgram10, "Time", floatValue5);
         a(shaderProgram10, "Box", 40.0F);
         a(shaderProgram10, "Mode", flag6 ? 0.0F : 1.0F);
         a(shaderProgram10, "Light", floatValue7);
         a(shaderProgram10, "Density", floatValue11);
         a(shaderProgram10, "Alpha", floatValue8);
         boolean flag12 = !u() && this.p.a("Цветокоррекция");
         a(shaderProgram10, "PartTint", flag12 ? this.v.g[0] : 1.0F, flag12 ? this.v.g[1] : 1.0F, flag12 ? this.v.g[2] : 1.0F);
         a(shaderProgram10, "CamMod", (float)a(vec3d3.x, 40.0), (float)a(vec3d3.y, 40.0), (float)a(vec3d3.z, 40.0));
         double doubleValue13 = Math.floor(vec3d3.x);
         double doubleValue15 = Math.floor(vec3d3.y);
         double doubleValue17 = Math.floor(vec3d3.z);
         a(shaderProgram10, "CamFrac", (float)(vec3d3.x - doubleValue13), (float)(vec3d3.y - doubleValue15), (float)(vec3d3.z - doubleValue17));
         a(shaderProgram10, "CamBlock", (float)((long)doubleValue13 & 63L), 0.0F, (float)((long)doubleValue17 & 63L));
         a(shaderProgram10, "Wind", (float)this.W, 0.0F, (float)this.X);
         a(shaderProgram10, "HmBase", (float)((double)(clientWorld4.getBottomY() - 1) - vec3d3.y));
         a(shaderProgram10, "HmOn", 1.0F);
         RenderSystem.setShaderTexture(0, x);
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(1, 771);
         RenderSystem.enableDepthTest();
         RenderSystem.depthFunc(515);
         RenderSystem.depthMask(false);
         RenderSystem.disableCull();
         this.A.bind();
         this.A.draw(matrix4f1, matrix4f2, shaderProgram10);
         VertexBuffer.unbind();
      }
   }

   private void b(Matrix4f matrix4f1, Matrix4f matrix4f2, Vec3d vec3d3, ClientWorld clientWorld4, float floatValue5, boolean flag6, float floatValue7, float floatValue8) {
      if (!(floatValue8 < 0.004F)) {
         Framebuffer framebuffer9 = minecraftClient.getFramebuffer();
         int intValue10 = framebuffer9.textureWidth;
         int intValue11 = framebuffer9.textureHeight;
         if (this.F == null || this.F.textureWidth != intValue10 || this.F.textureHeight != intValue11) {
            if (this.F != null) {
               this.F.delete();
            }

            this.F = new SimpleFramebuffer(intValue10, intValue11, true);
         }

         GlStateManager._glBindFramebuffer(36008, framebuffer9.fbo);
         GlStateManager._glBindFramebuffer(36009, this.F.fbo);
         GlStateManager._glBlitFrameBuffer(0, 0, intValue10, intValue11, 0, 0, intValue10, intValue11, 16640, 9728);
         this.O = true;
         Matrix4f matrix4f12 = new Matrix4f(matrix4f2).mul(matrix4f1);
         Matrix4f matrix4f13 = new Matrix4f(matrix4f12).invert();
         float floatValue14 = floatValue7 * (float) (Math.PI * 2);
         float floatValue15 = -MathHelper.sin(floatValue14);
         float floatValue16 = MathHelper.cos(floatValue14);
         double doubleValue17 = Math.floor(vec3d3.x);
         double doubleValue19 = Math.floor(vec3d3.y);
         double doubleValue21 = Math.floor(vec3d3.z);
         float floatValue23 = (float)(vec3d3.x - doubleValue17);
         float floatValue24 = (float)(vec3d3.y - doubleValue19);
         float floatValue25 = (float)(vec3d3.z - doubleValue21);
         float floatValue26 = (float)Math.floorMod((long)doubleValue17, 4096L);
         float floatValue27 = (float)Math.floorMod((long)doubleValue21, 4096L);
         float floatValue28 = (float)((double)(clientWorld4.getBottomY() - 1) - vec3d3.y);
         int intValue29 = Math.max(1, intValue10 / 4);
         int intValue30 = Math.max(1, intValue11 / 4);
         int intValue31 = this.z();
         boolean flag32 = this.p.a("Объёмный туман");
         boolean flag33 = this.p.a("Лучи света");
         float floatValue34 = 0.0F;
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.disableBlend();
         if (flag32) {
            this.G = a(this.G, intValue29, intValue30);
            this.G.beginWrite(true);
            ShaderProgram shaderProgram35 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.V);
            if (shaderProgram35 != null) {
               GlUniform glUniform36 = shaderProgram35.getUniform("InvPV");
               if (glUniform36 != null) {
                  glUniform36.set(matrix4f13);
               }

               a(shaderProgram35, "CamFrac", floatValue23, floatValue24, floatValue25);
               a(shaderProgram35, "CamBlock", floatValue26, 0.0F, floatValue27);
               a(shaderProgram35, "HmBase", floatValue28);
               a(shaderProgram35, "Time", floatValue5);
               a(shaderProgram35, "Mode", flag6 ? 0.0F : 1.0F);
               a(shaderProgram35, "SunDir", floatValue15, floatValue16, 0.0F);
               a(shaderProgram35, "SunCol", this.v.c);
               a(shaderProgram35, "Ambient", this.v.b);
               a(shaderProgram35, "Day", this.v.j);
               a(shaderProgram35, "Night", this.v.k);
               float floatValue37 = flag6 && this.q.a("Позёмка") ? 0.32F * Math.max(0.3F, this.u.f() / 100.0F) : 0.0F;
               if (flag6) {
                  a(shaderProgram35, "Fog", 0.05F, 2.0F, floatValue37, 64.0F);
               } else {
                  a(shaderProgram35, "Fog", 0.035F, 2.6F, 0.0F, 64.0F);
               }

               a(shaderProgram35, "Wind", this.Z, this.ModuleStorage, (float)this.R, (float)this.S);
               a(shaderProgram35, "Steps", intValue31 == 2 ? 12.0F : (intValue31 == 1 ? 8.0F : 6.0F));
               a(shaderProgram35, "Amount", floatValue8 * this.Y);
               this.y();
               RenderSystem.setShaderTexture(0, y);
               RenderSystem.setShaderTexture(1, this.F.getDepthAttachment());
               RenderSystem.setShaderTexture(2, x);
               A();
            }
         }

         if (flag33) {
            float floatValue42 = this.v.k > 0.5F ? -floatValue15 : floatValue15;
            float floatValue44 = this.v.k > 0.5F ? -floatValue16 : floatValue16;
            Vector4f vector4f46 = matrix4f12.transform(new Vector4f(floatValue42 * 100.0F, floatValue44 * 100.0F, 0.0F, 1.0F));
            float floatValue38 = 0.0F;
            float floatValue39 = 0.5F;
            float floatValue40 = 0.5F;
            if (vector4f46.w > 0.001F && floatValue44 > -0.05F) {
               floatValue39 = vector4f46.x / vector4f46.w * 0.5F + 0.5F;
               floatValue40 = vector4f46.y / vector4f46.w * 0.5F + 0.5F;
               float floatValue41 = Math.max(Math.max(-floatValue39, floatValue39 - 1.0F), Math.max(-floatValue40, floatValue40 - 1.0F));
               floatValue38 = MathHelper.clamp(1.0F - Math.max(0.0F, floatValue41) / 0.45F, 0.0F, 1.0F)
                  * MathHelper.clamp(vector4f46.w / 100.0F / 0.3F, 0.0F, 1.0F)
                  * MathHelper.clamp((floatValue44 + 0.05F) / 0.15F, 0.0F, 1.0F);
            }

            if (floatValue38 > 0.004F) {
               this.Q = a(this.Q, intValue29, intValue30);
               this.Q.beginWrite(true);
               ShaderProgram shaderProgram48 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.W);
               if (shaderProgram48 != null) {
                  a(shaderProgram48, "SunUV", floatValue39, floatValue40);
                  a(shaderProgram48, "SunOn", floatValue38);
                  a(shaderProgram48, "Aspect", (float)intValue10 / (float)Math.max(1, intValue11));
                  a(shaderProgram48, "Samples", intValue31 == 2 ? 24.0F : (intValue31 == 1 ? 18.0F : 12.0F));
                  RenderSystem.setShaderTexture(1, this.F.getDepthAttachment());
                  A();
                  floatValue34 = (this.v.k > 0.5F ? 0.14F * this.v.k : (flag6 ? 0.42F : 0.6F) * this.v.j) * floatValue8 * this.Y;
               }
            } else {
               flag33 = false;
            }
         }

         framebuffer9.beginWrite(true);
         ShaderProgram shaderProgram43 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.U);
         if (shaderProgram43 != null) {
            GlUniform glUniform45 = shaderProgram43.getUniform("InvPV");
            if (glUniform45 != null) {
               glUniform45.set(matrix4f13);
            }

            a(shaderProgram43, "Texel", 1.0F / (float)intValue10, 1.0F / (float)intValue11);
            a(shaderProgram43, "PixAng", 2.0F / (Math.max(0.01F, matrix4f2.m11()) * (float)intValue11));
            a(shaderProgram43, "Time", floatValue5);
            a(shaderProgram43, "Mode", flag6 ? 0.0F : 1.0F);
            a(shaderProgram43, "Amount", floatValue8);
            a(shaderProgram43, "Outdoor", this.Y);
            a(
               shaderProgram43,
               "LayersA",
               this.d("Покрытие мира"),
               this.d("Небо"),
               this.d("Дымка"),
               this.d("Цветокоррекция")
            );
            a(
               shaderProgram43,
               "LayersB",
               this.q.a("Иней на экране") ? 1.0F : 0.0F,
               this.q.a("Северное сияние") ? 1.0F : 0.0F,
               this.q.a("Искры на снегу") ? 1.0F : 0.0F,
               0.0F
            );
            a(shaderProgram43, "CamFrac", floatValue23, floatValue24, floatValue25);
            a(shaderProgram43, "CamBlock", floatValue26, 0.0F, floatValue27);
            a(shaderProgram43, "HmBase", floatValue28);
            a(shaderProgram43, "SunDir", floatValue15, floatValue16, 0.0F);
            a(shaderProgram43, "LayersC", flag32 ? 1.0F : 0.0F, flag33 ? 1.0F : 0.0F, 0.0F, 0.0F);
            boolean flag47 = this.v.k > 0.5F;
            a(shaderProgram43, "RayCol", (flag47 ? 0.62F : this.v.c[0]) * floatValue34, (flag47 ? 0.7F : this.v.c[1]) * floatValue34, (flag47 ? 0.95F : this.v.c[2]) * floatValue34);
            a(shaderProgram43, "QTexel", 1.0F / (float)intValue29, 1.0F / (float)intValue30);
            a(shaderProgram43, "WindDir", this.Z, this.ModuleStorage);
            a(shaderProgram43, "Day", this.v.j);
            a(shaderProgram43, "Night", this.v.k);
            a(shaderProgram43, "SkyZen", this.v.a);
            a(shaderProgram43, "SkyHor", this.v.b);
            a(shaderProgram43, "SunCol", this.v.c);
            a(shaderProgram43, "ShadowTint", this.v.d);
            a(shaderProgram43, "HighTint", this.v.e);
            a(shaderProgram43, "Lift", this.v.f);
            a(shaderProgram43, "Grade", this.v.h[0], this.v.h[1], this.v.h[2], this.v.h[3]);
            a(shaderProgram43, "Haze", this.v.i[0], this.v.i[1], this.v.i[2], this.v.i[3]);
            a(shaderProgram43, "Cover", this.s.f() / 100.0F);
            a(shaderProgram43, "Aspect", (float)intValue10 / (float)Math.max(1, intValue11));
            RenderSystem.setShaderTexture(0, this.F.getColorAttachment());
            RenderSystem.setShaderTexture(1, this.F.getDepthAttachment());
            RenderSystem.setShaderTexture(2, x);
            RenderSystem.setShaderTexture(3, flag32 && this.G != null ? this.G.getColorAttachment() : 0);
            RenderSystem.setShaderTexture(4, flag33 && this.Q != null ? this.Q.getColorAttachment() : 0);
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.disableBlend();
            A();
            RenderSystem.setShaderTexture(3, 0);
            RenderSystem.setShaderTexture(4, 0);
         }
      }
   }

   private void y() {
      if (!this.z) {
         this.z = true;
         NativeImage nativeImage1 = new NativeImage(1088, 34, false);
         float[] floatValueArray2 = a(8, 20903L);
         float[] floatValueArray3 = a(16, 40503L);

         for (int intValue4 = 0; intValue4 < 32; intValue4++) {
            for (int intValue5 = -1; intValue5 <= 32; intValue5++) {
               for (int intValue6 = -1; intValue6 <= 32; intValue6++) {
                  int intValue7 = Math.floorMod(intValue6, 32);
                  int intValue8 = Math.floorMod(intValue5, 32);
                  int intValue9 = (int)(a(floatValueArray2, 8, (float)intValue7 / 4.0F, (float)intValue8 / 4.0F, (float)intValue4 / 4.0F) * 255.0F);
                  int intValue10 = (int)(a(floatValueArray3, 16, (float)intValue7 / 2.0F, (float)intValue8 / 2.0F, (float)intValue4 / 2.0F) * 255.0F);
                  nativeImage1.setColorArgb(intValue4 * 34 + 1 + intValue6, 1 + intValue5, 0xFF000000 | intValue9 << 16 | intValue10 << 8);
               }
            }
         }

         NativeImageBackedTexture nativeImageBackedTexture11 = new NativeImageBackedTexture(nativeImage1);
         nativeImageBackedTexture11.setFilter(true, false);
         minecraftClient.getTextureManager().registerTexture(y, nativeImageBackedTexture11);
      }
   }

   private static float[] a(int intValue0, long longValue1) {
      Random random3 = new Random(longValue1);
      float[] floatValueArray4 = new float[intValue0 * intValue0 * intValue0];

      for (int intValue5 = 0; intValue5 < floatValueArray4.length; intValue5++) {
         floatValueArray4[intValue5] = random3.nextFloat();
      }

      return floatValueArray4;
   }

   private static float a(float[] floatValueArray0, int intValue1, float floatValue2, float floatValue3, float floatValue4) {
      int intValue5 = (int)Math.floor((double)floatValue2);
      int intValue6 = (int)Math.floor((double)floatValue3);
      int intValue7 = (int)Math.floor((double)floatValue4);
      float floatValue8 = floatValue2 - (float)intValue5;
      float floatValue9 = floatValue3 - (float)intValue6;
      float floatValue10 = floatValue4 - (float)intValue7;
      floatValue8 = floatValue8 * floatValue8 * (3.0F - 2.0F * floatValue8);
      floatValue9 = floatValue9 * floatValue9 * (3.0F - 2.0F * floatValue9);
      floatValue10 = floatValue10 * floatValue10 * (3.0F - 2.0F * floatValue10);
      float floatValue11 = a(floatValueArray0, intValue1, intValue5, intValue6, intValue7);
      float floatValue12 = a(floatValueArray0, intValue1, intValue5 + 1, intValue6, intValue7);
      float floatValue13 = a(floatValueArray0, intValue1, intValue5, intValue6 + 1, intValue7);
      float floatValue14 = a(floatValueArray0, intValue1, intValue5 + 1, intValue6 + 1, intValue7);
      float floatValue15 = a(floatValueArray0, intValue1, intValue5, intValue6, intValue7 + 1);
      float floatValue16 = a(floatValueArray0, intValue1, intValue5 + 1, intValue6, intValue7 + 1);
      float floatValue17 = a(floatValueArray0, intValue1, intValue5, intValue6 + 1, intValue7 + 1);
      float floatValue18 = a(floatValueArray0, intValue1, intValue5 + 1, intValue6 + 1, intValue7 + 1);
      float floatValue19 = a(a(floatValue11, floatValue12, floatValue8), a(floatValue13, floatValue14, floatValue8), floatValue9);
      float floatValue20 = a(a(floatValue15, floatValue16, floatValue8), a(floatValue17, floatValue18, floatValue8), floatValue9);
      return a(floatValue19, floatValue20, floatValue10);
   }

   private static float a(float[] floatValueArray0, int intValue1, int intValue2, int intValue3, int intValue4) {
      return floatValueArray0[(Math.floorMod(intValue2, intValue1) * intValue1 + Math.floorMod(intValue3, intValue1)) * intValue1 + Math.floorMod(intValue4, intValue1)];
   }

   public boolean s() {
      return this.isEnabled() && !this.l.b("Выкл") && !u();
   }

   public boolean c(String string1) {
      return this.isEnabled() && this.l.b(string1) && !u();
   }

   public void t() {
      if (this.s() && this.O && this.F != null && minecraftClient.world != null) {
         Framebuffer framebuffer1 = minecraftClient.getFramebuffer();
         int intValue2 = framebuffer1.textureWidth;
         int intValue3 = framebuffer1.textureHeight;
         if (this.F.textureWidth == intValue2 && this.F.textureHeight == intValue3) {
            if (this.H == null || this.H.textureWidth != intValue2 || this.H.textureHeight != intValue3) {
               if (this.H != null) {
                  this.H.delete();
               }

               this.H = new SimpleFramebuffer(intValue2, intValue3, true);
            }

            GlStateManager._glBindFramebuffer(36008, framebuffer1.fbo);
            GlStateManager._glBindFramebuffer(36009, this.H.fbo);
            GlStateManager._glBlitFrameBuffer(0, 0, intValue2, intValue3, 0, 0, intValue2, intValue3, 16640, 9728);
            boolean flag4 = this.l.b("Complementary");
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.disableBlend();
            if (flag4) {
               this.I = a(this.I, Math.max(1, intValue2 / 4), Math.max(1, intValue3 / 4));
               this.I.beginWrite(true);
               ShaderProgram shaderProgram5 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.Y);
               if (shaderProgram5 != null) {
                  a(shaderProgram5, "Texel", 1.0F / (float)intValue2, 1.0F / (float)intValue3);
                  RenderSystem.setShaderTexture(0, this.H.getColorAttachment());
                  A();
               }
            }

            framebuffer1.beginWrite(true);
            ShaderProgram shaderProgram15 = RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.X);
            if (shaderProgram15 != null) {
               Matrix4f matrix4f6 = new Matrix4f(this.K).mul(this.J);
               GlUniform glUniform7 = shaderProgram15.getUniform("InvPV");
               if (glUniform7 != null) {
                  glUniform7.set(new Matrix4f(matrix4f6).invert());
               }

               GlUniform glUniform8 = shaderProgram15.getUniform("PrevPV");
               if (glUniform8 != null) {
                  glUniform8.set(this.N != null ? this.L : matrix4f6);
               }

               Vec3d vec3d9 = this.N != null ? this.M.subtract(this.N) : Vec3d.ZERO;
               a(shaderProgram15, "CamDelta", (float)vec3d9.x, (float)vec3d9.y, (float)vec3d9.z);
               a(shaderProgram15, "Texel", 1.0F / (float)intValue2, 1.0F / (float)intValue3);
               a(shaderProgram15, "Style", flag4 ? 1.0F : 2.0F);
               float floatValue10 = this.m.getValue() && this.N != null ? this.n.f() / 100.0F : 0.0F;
               a(shaderProgram15, "Blur", flag4 ? 0.9F * floatValue10 : floatValue10);
               float floatValue11 = this.P * (float) (Math.PI * 2);
               float floatValue12 = MathHelper.cos(floatValue11);
               float floatValue13 = MathHelper.clamp((0.02F - floatValue12) / 0.18F, 0.0F, 1.0F);
               a(shaderProgram15, "Day", MathHelper.clamp(floatValue12 * 4.0F + 0.3F, 0.0F, 1.0F));
               a(shaderProgram15, "Night", floatValue13);
               a(shaderProgram15, "Sunset", Math.max(0.0F, 1.0F - Math.abs(floatValue12 - 0.05F) / 0.3F) * (1.0F - floatValue13));
               a(shaderProgram15, "FogCol", d, e, f);
               a(shaderProgram15, "ViewDist", (float)minecraftClient.options.getClampedViewDistance() * 16.0F);
               a(shaderProgram15, "Bloom", flag4 ? 0.35F : 0.0F);
               a(shaderProgram15, "Sharp", flag4 ? 0.25F : 0.0F);
               a(shaderProgram15, "Outline", flag4 ? 4.0F : 0.0F);
               RenderSystem.setShaderTexture(0, this.H.getColorAttachment());
               RenderSystem.setShaderTexture(1, this.F.getDepthAttachment());
               RenderSystem.setShaderTexture(2, this.H.getDepthAttachment());
               RenderSystem.setShaderTexture(3, flag4 && this.I != null ? this.I.getColorAttachment() : 0);
               A();

               for (int intValue14 = 0; intValue14 < 4; intValue14++) {
                  RenderSystem.setShaderTexture(intValue14, 0);
               }
            }

            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            this.L.set(this.K).mul(this.J);
            this.N = this.M;
            this.O = false;
         }
      } else {
         this.N = null;
      }
   }

   private static SimpleFramebuffer a(SimpleFramebuffer simpleFramebuffer0, int intValue1, int intValue2) {
      if (simpleFramebuffer0 != null && simpleFramebuffer0.textureWidth == intValue1 && simpleFramebuffer0.textureHeight == intValue2) {
         return simpleFramebuffer0;
      } else {
         if (simpleFramebuffer0 != null) {
            simpleFramebuffer0.delete();
         }

         SimpleFramebuffer simpleFramebuffer3 = new SimpleFramebuffer(intValue1, intValue2, false);
         simpleFramebuffer3.setTexFilter(9729);
         return simpleFramebuffer3;
      }
   }

   private int z() {
      String string1 = this.o.g();

      return switch (string1) {
         case "Низкое" -> 0;
         case "Среднее" -> 1;
         default -> 2;
      };
   }

   private float d(String string1) {
      return this.p.a(string1) ? 1.0F : 0.0F;
   }

   private static void A() {
      BufferBuilder bufferBuilder0 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION);
      bufferBuilder0.vertex(-1.0F, -1.0F, 0.0F);
      bufferBuilder0.vertex(1.0F, -1.0F, 0.0F);
      bufferBuilder0.vertex(1.0F, 1.0F, 0.0F);
      bufferBuilder0.vertex(-1.0F, 1.0F, 0.0F);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder0.end());
   }

   public static boolean u() {
      try {
         if (af == Boolean.FALSE) {
            return false;
         } else {
            Class class0 = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
            Object object1 = class0.getMethod("getInstance").invoke(null);
            af = Boolean.TRUE;
            return (Boolean)class0.getMethod("isShaderPackInUse").invoke(object1);
         }
      } catch (Throwable throwable2) {
         af = Boolean.FALSE;
         return false;
      }
   }

   private void B() {
      if (g) {
         if (this.ac[0] == 0) {
            GL15.glGenQueries(this.ac);
         }

         for (byte byteValue1 = 0; byteValue1 < this.ac.length; byteValue1 += 2) {
            if (this.ad[byteValue1] && GL15.glGetQueryObjecti(this.ac[byteValue1 + 1], 34919) != 0) {
               long longValue2 = GL33.glGetQueryObjecti64(this.ac[byteValue1], 34918);
               long longValue4 = GL33.glGetQueryObjecti64(this.ac[byteValue1 + 1], 34918);
               i = i + (double)Math.max(0L, longValue4 - longValue2);
               j++;
               this.ad[byteValue1] = false;
            }
         }

         if (!this.ad[this.ae]) {
            GL33.glQueryCounter(this.ac[this.ae], 36392);
            this.ag = true;
         }
      }
   }

   private void C() {
      if (g && this.ag) {
         GL33.glQueryCounter(this.ac[this.ae + 1], 36392);
         this.ad[this.ae] = true;
         this.ag = false;
         this.ae = (this.ae + 2) % this.ac.length;
      }
   }

   private static double a(double doubleValue0, double doubleValue2) {
      double doubleValue4 = doubleValue0 % doubleValue2;
      return doubleValue4 < 0.0 ? doubleValue4 + doubleValue2 : doubleValue4;
   }

   private static float a(float floatValue0, float floatValue1, float floatValue2) {
      return floatValue0 + (floatValue1 - floatValue0) * floatValue2;
   }

   private static void a(ShaderProgram shaderProgram0, String string1, float floatValue2) {
      GlUniform glUniform3 = shaderProgram0.getUniform(string1);
      if (glUniform3 != null) {
         glUniform3.set(floatValue2);
      }
   }

   private static void a(ShaderProgram shaderProgram0, String string1, float floatValue2, float floatValue3) {
      GlUniform glUniform4 = shaderProgram0.getUniform(string1);
      if (glUniform4 != null) {
         glUniform4.set(floatValue2, floatValue3);
      }
   }

   private static void a(ShaderProgram shaderProgram0, String string1, float[] floatValueArray2) {
      a(shaderProgram0, string1, floatValueArray2[0], floatValueArray2[1], floatValueArray2[2]);
   }

   private static void a(ShaderProgram shaderProgram0, String string1, float floatValue2, float floatValue3, float floatValue4) {
      GlUniform glUniform5 = shaderProgram0.getUniform(string1);
      if (glUniform5 != null) {
         glUniform5.set(floatValue2, floatValue3, floatValue4);
      }
   }

   private static void a(ShaderProgram shaderProgram0, String string1, float floatValue2, float floatValue3, float floatValue4, float floatValue5) {
      GlUniform glUniform6 = shaderProgram0.getUniform(string1);
      if (glUniform6 != null) {
         glUniform6.set(floatValue2, floatValue3, floatValue4, floatValue5);
      }
   }
}
