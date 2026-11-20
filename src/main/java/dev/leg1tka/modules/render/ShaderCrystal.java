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
import dev.leg1tka.api.events.implement.EventRender;
import dev.leg1tka.api.events.implement.EventUpdate;
import dev.leg1tka.mixin.WorldRendererAccessor;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class ShaderCrystal extends Module {
   public static ShaderCrystal c = new ShaderCrystal();
   private static float d;
   private static long e;
   private static double f;
   private static float g;
   private static int h;
   private static float i;
   private static float j;
   private static boolean k;
   private final List<Framebuffer> l = new ArrayList<>();
   private Framebuffer m;
   private int n = -1;
   private int o = -1;
   private boolean p;
   private boolean q;
   private long r;

   public ShaderCrystal() {
      super(
         "ShaderCrystal",
         "Красивая обводка кристаллов",
         Category.RENDER
      );
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.p = false;
      this.r = 0L;
      this.q();
   }

   @Override
   public void onDisable() {
      for (Framebuffer framebuffer2 : this.l) {
         framebuffer2.delete();
      }

      this.l.clear();
      if (this.m != null) {
         this.m.delete();
         this.m = null;
      }

      this.n = -1;
      this.o = -1;
      this.p = false;
      this.q = false;
      this.r = 0L;
      super.onDisable();
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (this.isEnabled()) {
         if (minecraftClient.world != null && minecraftClient.worldRenderer != null) {
            this.q = this.s();
            if (!this.q) {
               this.p = false;
            } else {
               if (!this.p && System.currentTimeMillis() >= this.r) {
                  this.q();
               }
            }
         } else {
            this.p = false;
            this.q = false;
         }
      }
   }

   @EventLink(
      priority = 200
   )
   public void onRender2D(EventRender.Default default1) {
      if (this.isEnabled() && minecraftClient.world != null && minecraftClient.player != null && minecraftClient.worldRenderer != null) {
         boolean flag2 = true;
         boolean flag3 = true;
         if (flag2 || flag3) {
            if (this.q) {
               if (this.q()) {
                  Framebuffer framebuffer4 = this.r();
                  if (framebuffer4 != null && framebuffer4.getColorAttachment() != 0) {
                     Framebuffer framebuffer5 = minecraftClient.getFramebuffer();
                     this.a(framebuffer5.textureWidth, framebuffer5.textureHeight);
                     int intValue6 = Math.max(1, Math.min(8, (int)Math.ceil(1.125)));
                     int intValue7 = 0;
                     if (flag3) {
                        int intValue8 = Math.max(2, Math.min(6, intValue6 + 1));
                        intValue7 = this.b(framebuffer4.getColorAttachment(), intValue8);
                     }

                     int intValue11 = flag2 ? this.b(framebuffer4.getColorAttachment(), intValue6) : intValue7;
                     int intValue9 = this.p();
                     framebuffer5.beginWrite(false);
                     RenderSystem.enableBlend();
                     RenderSystem.disableDepthTest();
                     RenderSystem.colorMask(true, true, true, false);
                     if (flag3) {
                        ShaderProgram shaderProgram10 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.E);
                        if (shaderProgram10 != null) {
                           RenderSystem.blendFuncSeparate(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA, SrcFactor.ZERO, DstFactor.ONE);
                           RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.E);
                           RenderSystem.setShaderTexture(0, framebuffer4.getColorAttachment());
                           RenderSystem.setShaderTexture(1, intValue7 == 0 ? intValue11 : intValue7);
                           this.a(shaderProgram10, "color", dev.leg1tka.api.utils.text.ColorUtils.c(intValue9), dev.leg1tka.api.utils.text.ColorUtils.d(intValue9), dev.leg1tka.api.utils.text.ColorUtils.e(intValue9));
                           this.a(shaderProgram10, "alpha", 0.7F);
                           this.a(shaderProgram10, "time", (float)(System.currentTimeMillis() % 100000L) / 1000.0F);
                           this.t();
                        }
                     }

                     if (flag2) {
                        ShaderProgram shaderProgram12 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.D);
                        if (shaderProgram12 != null) {
                           RenderSystem.blendFuncSeparate(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA, SrcFactor.ZERO, DstFactor.ONE);
                           RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.D);
                           RenderSystem.setShaderTexture(0, intValue11);
                           RenderSystem.setShaderTexture(1, framebuffer4.getColorAttachment());
                           this.a(shaderProgram12, "color", dev.leg1tka.api.utils.text.ColorUtils.c(intValue9), dev.leg1tka.api.utils.text.ColorUtils.d(intValue9), dev.leg1tka.api.utils.text.ColorUtils.e(intValue9));
                           this.a(shaderProgram12, "color2", dev.leg1tka.api.utils.text.ColorUtils.c(intValue9), dev.leg1tka.api.utils.text.ColorUtils.d(intValue9), dev.leg1tka.api.utils.text.ColorUtils.e(intValue9));
                           this.a(shaderProgram12, "exposure", 0.05075F);
                           this.a(shaderProgram12, "time", (float)(System.currentTimeMillis() % 100000L) / 1000.0F);
                           this.a(shaderProgram12, "animate", 1.0F);
                           this.a(framebuffer5, framebuffer4);
                        }
                     }

                     RenderSystem.colorMask(true, true, true, true);
                     RenderSystem.disableDepthTest();
                     RenderSystem.disableBlend();
                     RenderSystem.defaultBlendFunc();
                     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                     RenderSystem.setShaderTexture(0, 0);
                     RenderSystem.setShaderTexture(1, 0);
                     framebuffer5.beginWrite(true);
                  }
               }
            }
         }
      }
   }

   private void a(Framebuffer framebuffer1, Framebuffer framebuffer2) {
      if (this.m == null) {
         this.t();
      } else {
         GL30.glBindFramebuffer(36008, framebuffer1.fbo);
         GL30.glBindFramebuffer(36009, this.m.fbo);
         GL30.glBlitFramebuffer(0, 0, framebuffer1.textureWidth, framebuffer1.textureHeight, 0, 0, this.m.textureWidth, this.m.textureHeight, 256, 9728);
         GL30.glBindFramebuffer(36008, framebuffer2.fbo);
         GL30.glBindFramebuffer(36009, framebuffer1.fbo);
         GL30.glBlitFramebuffer(0, 0, framebuffer2.textureWidth, framebuffer2.textureHeight, 0, 0, framebuffer1.textureWidth, framebuffer1.textureHeight, 256, 9728);
         framebuffer1.beginWrite(false);
         RenderSystem.enableDepthTest();
         RenderSystem.depthFunc(515);
         RenderSystem.depthMask(false);
         this.t();
         RenderSystem.depthMask(true);
         RenderSystem.disableDepthTest();
         GL30.glBindFramebuffer(36008, this.m.fbo);
         GL30.glBindFramebuffer(36009, framebuffer1.fbo);
         GL30.glBlitFramebuffer(0, 0, this.m.textureWidth, this.m.textureHeight, 0, 0, framebuffer1.textureWidth, framebuffer1.textureHeight, 256, 9728);
         framebuffer1.beginWrite(false);
      }
   }

   private void a(int intValue1, int intValue2) {
      if (this.m != null && (this.m.textureWidth != intValue1 || this.m.textureHeight != intValue2)) {
         this.m.delete();
         this.m = null;
      }

      if (this.m == null) {
         this.m = new SimpleFramebuffer(intValue1, intValue2, true);
      }
   }

   private boolean q() {
      if (minecraftClient.world != null && minecraftClient.worldRenderer != null) {
         Framebuffer framebuffer1 = this.r();
         if (framebuffer1 != null && framebuffer1.getColorAttachment() != 0) {
            this.p = true;
            return true;
         } else {
            if (this.p) {
               this.p = false;
            }

            if (System.currentTimeMillis() < this.r) {
               return false;
            } else {
               try {
                  minecraftClient.worldRenderer.loadEntityOutlinePostProcessor();
                  framebuffer1 = this.r();
                  this.p = framebuffer1 != null && framebuffer1.getColorAttachment() != 0;
                  if (!this.p) {
                     this.r = System.currentTimeMillis() + 3000L;
                  }

                  return this.p;
               } catch (Throwable throwable3) {
                  this.p = false;
                  this.r = System.currentTimeMillis() + 3000L;
                  return false;
               }
            }
         }
      } else {
         this.p = false;
         return false;
      }
   }

   private Framebuffer r() {
      if (minecraftClient.worldRenderer instanceof WorldRendererAccessor worldRendererAccessor1) {
         Framebuffer framebuffer3 = worldRendererAccessor1.AspectDLC$getEntityOutlineFramebufferRaw();
         if (framebuffer3 != null && framebuffer3.getColorAttachment() != 0) {
            return framebuffer3;
         }
      }

      return minecraftClient.worldRenderer.getEntityOutlinesFramebuffer();
   }

   public boolean a(Entity entity1) {
      if (!this.isEnabled() || entity1 == null || minecraftClient.player == null || minecraftClient.world == null) {
         return false;
      } else if (!entity1.isAlive()) {
         return false;
      } else if (entity1.isRemoved()) {
         return false;
      } else {
         return entity1.squaredDistanceTo(minecraftClient.player) > 65536.0 ? false : entity1 instanceof EndCrystalEntity;
      }
   }

   private boolean s() {
      if (minecraftClient.world != null && minecraftClient.player != null) {
         for (Entity entity2 : minecraftClient.world.getEntities()) {
            if (this.a(entity2)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public int p() {
      return dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.api.utils.text.ColorUtils.a(), 255) & 16777215;
   }

   private int b(int intValue1, int intValue2) {
      this.b(intValue2);
      if (this.l.isEmpty()) {
         return intValue1;
      } else {
         int intValue3 = intValue1;
         ShaderProgram shaderProgram4 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.B);
         ShaderProgram shaderProgram5 = minecraftClient.getShaderLoader().getOrCreateProgram(dev.leg1tka.render.ShaderRegistry.C);
         if (shaderProgram4 != null && shaderProgram5 != null) {
            for (int intValue6 = 0; intValue6 < intValue2; intValue6++) {
               Framebuffer framebuffer7 = (Framebuffer)this.l.get(intValue6);
               framebuffer7.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
               framebuffer7.clear();
               framebuffer7.beginWrite(true);
               RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.B);
               RenderSystem.setShaderTexture(0, intValue3);
               this.a(shaderProgram4, framebuffer7.textureWidth, framebuffer7.textureHeight, 1.0F + (float)intValue6);
               this.t();
               intValue3 = framebuffer7.getColorAttachment();
            }

            for (int intValue8 = intValue2 - 1; intValue8 >= 1; intValue8--) {
               Framebuffer framebuffer9 = (Framebuffer)this.l.get(intValue8 - 1);
               framebuffer9.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
               framebuffer9.clear();
               framebuffer9.beginWrite(true);
               RenderSystem.setShader(dev.leg1tka.render.ShaderRegistry.C);
               RenderSystem.setShaderTexture(0, intValue3);
               this.a(shaderProgram5, framebuffer9.textureWidth, framebuffer9.textureHeight, 1.0F + (float)intValue8);
               this.a(shaderProgram5, "color", 1.0F, 1.0F, 1.0F);
               this.t();
               intValue3 = framebuffer9.getColorAttachment();
            }

            minecraftClient.getFramebuffer().beginWrite(true);
            return intValue3;
         } else {
            return intValue1;
         }
      }
   }

   private void b(int intValue1) {
      int intValue2 = minecraftClient.getWindow().getFramebufferWidth();
      int intValue3 = minecraftClient.getWindow().getFramebufferHeight();
      if (this.n != intValue2 || this.o != intValue3) {
         for (Framebuffer framebuffer5 : this.l) {
            framebuffer5.delete();
         }

         this.l.clear();
         this.n = intValue2;
         this.o = intValue3;
      }

      while (this.l.size() > intValue1) {
         int intValue8 = this.l.size() - 1;
         ((Framebuffer)this.l.get(intValue8)).delete();
         this.l.remove(intValue8);
      }

      for (int intValue9 = 0; intValue9 < intValue1; intValue9++) {
         int intValue10 = Math.max(2, intValue2 >> intValue9 + 1);
         int intValue6 = Math.max(2, intValue3 >> intValue9 + 1);
         if (intValue9 >= this.l.size()) {
            SimpleFramebuffer simpleFramebuffer7 = new SimpleFramebuffer(intValue10, intValue6, false);
            this.a(simpleFramebuffer7);
            this.l.add(simpleFramebuffer7);
         } else {
            Framebuffer framebuffer11 = (Framebuffer)this.l.get(intValue9);
            if (framebuffer11.textureWidth != intValue10 || framebuffer11.textureHeight != intValue6) {
               framebuffer11.delete();
               SimpleFramebuffer simpleFramebuffer12 = new SimpleFramebuffer(intValue10, intValue6, false);
               this.a(simpleFramebuffer12);
               this.l.set(intValue9, simpleFramebuffer12);
            }
         }
      }
   }

   private void a(Framebuffer framebuffer1) {
      RenderSystem.bindTexture(framebuffer1.getColorAttachment());
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      RenderSystem.bindTexture(0);
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

   private void a(ShaderProgram shaderProgram1, int intValue2, int intValue3, float floatValue4) {
      this.a(shaderProgram1, "uSize", (float)Math.max(1, intValue2), (float)Math.max(1, intValue3));
      this.a(shaderProgram1, "uOffset", floatValue4, floatValue4);
      this.a(shaderProgram1, "uHalfPixel", 0.5F / (float)Math.max(1, intValue2), 0.5F / (float)Math.max(1, intValue3));
   }

   private void t() {
      float floatValue1 = (float)Math.max(minecraftClient.getWindow().getScaledWidth(), 1);
      float floatValue2 = (float)Math.max(minecraftClient.getWindow().getScaledHeight(), 1);
      BufferBuilder bufferBuilder3 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      bufferBuilder3.vertex(0.0F, 0.0F, 0.0F).texture(0.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder3.vertex(0.0F, floatValue2, 0.0F).texture(0.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder3.vertex(floatValue1, floatValue2, 0.0F).texture(1.0F, 0.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      bufferBuilder3.vertex(floatValue1, 0.0F, 0.0F).texture(1.0F, 1.0F).color(1.0F, 1.0F, 1.0F, 1.0F);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder3.end());
   }
}
