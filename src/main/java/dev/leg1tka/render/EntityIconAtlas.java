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

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;
import net.minecraft.util.TriState;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.lwjgl.opengl.GL30;

public final class EntityIconAtlas {
   public static final Identifier a = Identifier.of("aspectdlc", "esp_icon_atlas");
   public static int b;
   private static int c;
   private static int d;
   private static int e;
   private static int f;
   private static int g;
   private static float h;
   private SimpleFramebuffer i;
   private final ItemStack[] j = new ItemStack[256];
   private final int[] k = new int[256];
   private final int[] l = new int[256];
   private final long[] m = new long[256];
   private final long[] n = new long[256];
   private final boolean[] o = new boolean[256];
   private long p = 2L;
   private int[] q = new int[16];
   private ItemStack[] r = new ItemStack[16];
   private LivingEntity[] s = new LivingEntity[16];
   private int t;
   private final ItemRenderState u = new ItemRenderState();
   private final MatrixStack v = new MatrixStack();
   private final Matrix4f w = new Matrix4f().setOrtho(0.0F, 16.0F, 16.0F, 0.0F, -1000.0F, 1000.0F);

   public EntityIconAtlas() {
   }

   public void a() {
      this.p++;
   }

   public int a(ItemStack itemStack1, LivingEntity livingEntity2) {
      if (itemStack1 != null && !itemStack1.isEmpty()) {
         int intValue3 = ItemStack.hashCode(itemStack1);

         for (int intValue4 = 0; intValue4 < 256; intValue4++) {
            ItemStack itemStack5 = this.j[intValue4];
            if (itemStack5 != null && !this.o[intValue4] && this.k[intValue4] == intValue3 && ItemStack.areItemsAndComponentsEqual(itemStack5, itemStack1)) {
               this.m[intValue4] = this.p;
               return this.f(intValue4);
            }
         }

         int intValue6 = this.c();
         if (intValue6 < 0) {
            return -1;
         } else {
            ItemStack itemStack7 = itemStack1.copyWithCount(1);
            this.j[intValue6] = itemStack7;
            this.k[intValue6] = intValue3;
            this.o[intValue6] = false;
            this.a(intValue6, itemStack7, livingEntity2);
            return this.f(intValue6);
         }
      } else {
         return -1;
      }
   }

   public int a(ItemStack itemStack1, LivingEntity livingEntity2, long longValue3) {
      if (itemStack1 != null && !itemStack1.isEmpty()) {
         int intValue5 = -1;

         for (int intValue6 = 0; intValue6 < 256; intValue6++) {
            if (this.j[intValue6] != null && this.o[intValue6] && this.n[intValue6] == longValue3) {
               intValue5 = intValue6;
               break;
            }
         }

         if (intValue5 < 0) {
            intValue5 = this.c();
            if (intValue5 < 0) {
               return -1;
            }

            this.o[intValue5] = true;
            this.n[intValue5] = longValue3;
         }

         this.j[intValue5] = itemStack1;
         this.m[intValue5] = this.p;
         this.a(intValue5, itemStack1, livingEntity2);
         return this.f(intValue5);
      } else {
         return -1;
      }
   }

   public boolean a(int intValue1) {
      if (intValue1 == -1) {
         return false;
      } else {
         int intValue2 = intValue1 & 0xFF;
         if (this.l[intValue2] == intValue1 >>> 8 && this.j[intValue2] != null) {
            this.m[intValue2] = this.p;
            return true;
         } else {
            return false;
         }
      }
   }

   public float b(int intValue1) {
      return (float)((intValue1 & 0xFF) % 16) * 64.0F / 1024.0F + 4.8828125E-4F;
   }

   public float c(int intValue1) {
      return (float)((intValue1 & 0xFF) % 16 + 1) * 64.0F / 1024.0F - 4.8828125E-4F;
   }

   public float d(int intValue1) {
      return (float)((intValue1 & 0xFF) / 16 + 1) * 64.0F / 1024.0F - 4.8828125E-4F;
   }

   public float e(int intValue1) {
      return (float)((intValue1 & 0xFF) / 16) * 64.0F / 1024.0F + 4.8828125E-4F;
   }

   public boolean b() {
      return this.i != null;
   }

   public void a(MinecraftClient minecraftClient1) {
      if (this.t != 0) {
         this.d(minecraftClient1);
         Immediate immediate2 = minecraftClient1.getBufferBuilders().getEntityVertexConsumers();
         immediate2.draw();
         RenderSystem.backupProjectionMatrix();
         RenderSystem.setProjectionMatrix(this.w, ProjectionType.ORTHOGRAPHIC);
         Matrix4fStack matrix4fStack3 = RenderSystem.getModelViewStack();
         matrix4fStack3.pushMatrix();
         matrix4fStack3.identity();
         Framebuffer framebuffer4 = minecraftClient1.getFramebuffer();
         int intValue5 = framebuffer4.fbo;
         framebuffer4.fbo = this.i.fbo;

         try {
            this.a(minecraftClient1, immediate2);
         } finally {
            framebuffer4.fbo = intValue5;
         }

         this.u.clear();
         Arrays.fill(this.r, 0, this.t, null);
         Arrays.fill(this.s, 0, this.t, null);
         this.t = 0;
         GlStateManager._activeTexture(33984);
         GlStateManager._bindTexture(this.i.getColorAttachment());
         GL30.glGenerateMipmap(3553);
         GlStateManager._bindTexture(0);
         matrix4fStack3.popMatrix();
         RenderSystem.restoreProjectionMatrix();
         framebuffer4.beginWrite(true);
         c(minecraftClient1);
      }
   }

   private void a(MinecraftClient minecraftClient1, Immediate immediate2) {
      this.i.beginWrite(false);
      RenderSystem.depthMask(true);
      RenderSystem.clearColor(0.0F, 0.0F, 0.0F, 0.0F);

      for (int intValue3 = 0; intValue3 < this.t; intValue3++) {
         int intValue4 = this.q[intValue3];
         int intValue5 = intValue4 % 16 * 64;
         int intValue6 = intValue4 / 16 * 64;
         RenderSystem.viewport(intValue5, intValue6, 64, 64);
         RenderSystem.enableScissor(intValue5, intValue6, 64, 64);
         RenderSystem.clear(16640);
         RenderSystem.disableScissor();
         ItemStack itemStack7 = this.r[intValue3].copy();
         itemStack7.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
         minecraftClient1.getItemModelManager().update(this.u, itemStack7, ModelTransformationMode.GUI, false, minecraftClient1.world, this.s[intValue3], 0);
         if (!this.u.isEmpty()) {
            if (this.u.isSideLit()) {
               DiffuseLighting.enableGuiDepthLighting();
            } else {
               DiffuseLighting.disableGuiDepthLighting();
            }

            this.v.push();
            this.v.translate(8.0F, 8.0F, 0.0F);
            this.v.scale(16.0F, -16.0F, 16.0F);
            this.u.render(this.v, immediate2, 15728880, OverlayTexture.DEFAULT_UV);
            immediate2.draw();
            this.v.pop();
         }
      }
   }

   public void b(MinecraftClient minecraftClient1) {
      if (this.i != null) {
         minecraftClient1.getTextureManager().destroyTexture(a);
         this.i.delete();
         this.i = null;
      }

      Arrays.fill(this.j, null);
      Arrays.fill(this.r, null);
      Arrays.fill(this.s, null);
      this.t = 0;

      for (int intValue2 = 0; intValue2 < 256; intValue2++) {
         this.l[intValue2]++;
      }
   }

   static void c(MinecraftClient minecraftClient0) {
      if (minecraftClient0.world != null && minecraftClient0.world.getDimensionEffects().isDarkened()) {
         DiffuseLighting.enableForLevel();
      } else {
         DiffuseLighting.disableForLevel();
      }
   }

   private int f(int intValue1) {
      return intValue1 | this.l[intValue1] << 8;
   }

   private int c() {
      int intValue1 = -1;
      long longValue2 = Long.MAX_VALUE;

      for (int intValue4 = 0; intValue4 < 256; intValue4++) {
         if (this.j[intValue4] == null) {
            intValue1 = intValue4;
            break;
         }

         if (this.m[intValue4] < this.p - 1L && this.m[intValue4] < longValue2) {
            longValue2 = this.m[intValue4];
            intValue1 = intValue4;
         }
      }

      if (intValue1 < 0) {
         return -1;
      } else {
         this.j[intValue1] = null;
         this.l[intValue1] = this.l[intValue1] + 1 & 8388607;
         this.m[intValue1] = this.p;
         return intValue1;
      }
   }

   private void a(int intValue1, ItemStack itemStack2, LivingEntity livingEntity3) {
      for (int intValue4 = 0; intValue4 < this.t; intValue4++) {
         if (this.q[intValue4] == intValue1) {
            this.r[intValue4] = itemStack2;
            this.s[intValue4] = livingEntity3;
            return;
         }
      }

      if (this.t == this.q.length) {
         int intValue5 = this.t * 2;
         this.q = Arrays.copyOf(this.q, intValue5);
         this.r = Arrays.copyOf(this.r, intValue5);
         this.s = Arrays.copyOf(this.s, intValue5);
      }

      this.q[this.t] = intValue1;
      this.r[this.t] = itemStack2;
      this.s[this.t] = livingEntity3;
      this.t++;
   }

   private void d(MinecraftClient minecraftClient1) {
      if (this.i == null) {
         this.i = new SimpleFramebuffer(1024, 1024, true);
         int intValue2 = this.i.getColorAttachment();
         GlStateManager._activeTexture(33984);
         GlStateManager._bindTexture(intValue2);
         GlStateManager._texParameter(3553, 10241, 9987);
         GlStateManager._texParameter(3553, 10240, 9728);
         GlStateManager._texParameter(3553, 33085, 6);
         GlStateManager._texParameter(3553, 10242, 33071);
         GlStateManager._texParameter(3553, 10243, 33071);
         GL30.glGenerateMipmap(3553);
         GlStateManager._bindTexture(0);
         this.i.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
         this.i.clear();
         minecraftClient1.getTextureManager().registerTexture(a, new dev.leg1tka.render.EntityIconAtlas.a(intValue2));
         minecraftClient1.getFramebuffer().beginWrite(true);
      }
   }

   static final class a extends AbstractTexture {
      a(int intValue1) {
         this.glId = intValue1;
      }

      public void setFilter(TriState bilinear, boolean mipmap) {
      }

      public void setFilter(boolean bilinear, boolean mipmap) {
      }

      public void clearGlId() {
         this.glId = -1;
      }

      public void close() {
         this.glId = -1;
      }
   }
}
