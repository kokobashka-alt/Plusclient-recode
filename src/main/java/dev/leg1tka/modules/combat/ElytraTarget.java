package dev.leg1tka.modules.combat;


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
import java.util.function.Supplier;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
public class ElytraTarget extends Module {
   public static final ElytraTarget c = new ElytraTarget();
   private static Identifier o;
   private static float p;
   private static float q;
   private static int r;
   private final dev.leg1tka.render.FramebufferOutline s = new dev.leg1tka.render.FramebufferOutline();
   private Box t;
   private LivingEntity u;
   private int v = -1;
   private int w = -1;
   public final BooleanSetting d = new BooleanSetting("Предикт", true);
   public final NumberSetting e = new NumberSetting("Предикт значение", 3.0F, 1.0F, 5.0F, 0.1F)
      .a(createSupplier106(this));
   public final NumberSetting f = new NumberSetting("Elytra Hit Distance", 4.0F, 2.0F, 8.0F, 0.1F).a(createSupplier107(this));
   public final BooleanSetting g = new BooleanSetting("Smart Elytra Chase", true).visibleWhen(createSupplier108(this));
   public final NumberSetting h = new NumberSetting("Chase Forward", 2.85F, 0.0F, 5.0F, 0.05F).a(createSupplier109(this));
   public final NumberSetting i = new NumberSetting("Chase Lateral", 2.05F, 0.0F, 5.0F, 0.05F).a(createSupplier110(this));
   public final BooleanSetting j = new BooleanSetting(
         "Показать предикт точку", true
      )
      .visibleWhen(createSupplier111(this));
   public final BooleanSetting k = new BooleanSetting("Разворот на элитрах", true)
      .visibleWhen(createSupplier112(this));
   public final BooleanSetting l = new BooleanSetting("Резольвер (Elytra)", true).visibleWhen(createSupplier113(this));
   public final ModeSetting m = new ModeSetting(
      "Бокс", "Шейдер", "Шейдер", "Обычный"
   );
   public final BooleanSetting n = new BooleanSetting("Траектория", true);

   public ElytraTarget() {
      super(
         "ElytraTarget",
         "Таргетит игрока на элитрах",
         Category.COMBAT
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n});
   }

   @EventLink(
      priority = -100
   )
   public void onRender3D(Event3DRender event3DRender1) {
      if (minecraftClient.player == null || minecraftClient.world == null) return;
      LivingEntity target = AttackAura.c.E();
      if (target != null && target.isAlive()) {
         this.q();
         Box box = this.a(target);
         box = this.a(target, box);
         if (this.n.getValue()) {
            this.a(event3DRender1.getMatrices().peek().getPositionMatrix(), event3DRender1.getCamera().getPos(), box, target, event3DRender1.getTickDelta());
         }
         if (this.m.b("Обычный")) {
            this.a(event3DRender1.getMatrices(), box.offset(event3DRender1.getCamera().getPos().negate()));
         }
      } else {
         this.p();
      }
   }

   private void a(MatrixStack matrixStack1, Box box2) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      RenderSystem.lineWidth(1.5F);
      int intValue3 = dev.leg1tka.api.utils.text.ColorUtils.d(-1, 220);
      int intValue4 = intValue3 >> 16 & 0xFF;
      int intValue5 = intValue3 >> 8 & 0xFF;
      int intValue6 = intValue3 & 0xFF;
      int intValue7 = intValue3 >> 24 & 0xFF;
      dev.leg1tka.render.RenderPrimitives.drawOutlinedBox(matrixStack1, box2, intValue4, intValue5, intValue6, intValue7);
      RenderSystem.lineWidth(1.0F);
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
   }



   public void onRender2D(EventRender.Default default1) {
   }

   @Override
   public void onDisable() {
      this.p();
      this.s.c();
      super.onDisable();
   }

   private void p() {
      this.t = null;
      this.u = null;
      this.s.b();
   }

   private void a(Matrix4f matrix4f1, Vec3d vec3d2, Box box3, LivingEntity livingEntity4, float floatValue5) {
      Vec3d vec3d6 = minecraftClient.player.getLerpedPos(floatValue5).add(0.0, (double)minecraftClient.player.getHeight() * 0.5, 0.0);
      Vec3d vec3d7 = box3.getCenter();
      if (!(vec3d6.squaredDistanceTo(vec3d7) < 0.04)) {
         Vec3d[] vec3dArray8 = this.a(vec3d6, vec3d7, livingEntity4);
         if (vec3dArray8.length >= 2) {
            int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.d(this.v, 200);
            int intValue10 = dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.api.utils.text.ColorUtils.d(this.w, -1, 0.3F), 235);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(770, 1);
            RenderSystem.disableCull();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.setShaderTexture(0, o);
            RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
            BufferBuilder bufferBuilder11 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

            for (int intValue12 = 0; intValue12 < vec3dArray8.length - 1; intValue12++) {
               float floatValue13 = (float)intValue12 / (float)Math.max(1, vec3dArray8.length - 2);
               int intValue14 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue9, intValue10, floatValue13);
               this.a(bufferBuilder11, matrix4f1, vec3d2, vec3dArray8[intValue12], vec3dArray8[intValue12 + 1], intValue14, 0.055F);
            }

            BufferRenderer.drawWithGlobalProgram(bufferBuilder11.end());
            RenderSystem.setShaderTexture(0, 0);
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
         }
      }
   }

   private Vec3d[] a(Vec3d vec3d1, Vec3d vec3d2, LivingEntity livingEntity3) {
      Vec3d vec3d4 = vec3d2.subtract(vec3d1);
      double doubleValue5 = vec3d4.length();
      if (doubleValue5 < 0.05) {
         return new Vec3d[]{vec3d1, vec3d2};
      } else {
         Vec3d vec3d7 = minecraftClient.player.getVelocity();
         Vec3d vec3d8 = livingEntity3.getVelocity();
         Vec3d vec3d9 = vec3d1.add(vec3d7.multiply(Math.min(doubleValue5 * 0.42, 3.0))).add(0.0, Math.min(doubleValue5 * 0.14, 4.0), 0.0);
         Vec3d vec3d10 = vec3d2.subtract(vec3d8.multiply(Math.min(doubleValue5 * 0.32, 2.5)));
         Vec3d[] vec3dArray11 = new Vec3d[14];

         for (int intValue12 = 0; intValue12 < 14; intValue12++) {
            float floatValue13 = (float)intValue12 / 13.0F;
            vec3dArray11[intValue12] = this.a(vec3d1, vec3d9, vec3d10, vec3d2, floatValue13);
         }

         return vec3dArray11;
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

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, Vec3d vec3d3, Vec3d vec3d4, Vec3d vec3d5, int intValue6, float floatValue7) {
      this.a(bufferBuilder1, matrix4f2, vec3d4.subtract(vec3d3), vec3d5.subtract(vec3d3), intValue6, floatValue7);
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, Vec3d vec3d3, Vec3d vec3d4, int intValue5, float floatValue6) {
      Vec3d vec3d7 = vec3d4.subtract(vec3d3);
      if (!(vec3d7.lengthSquared() <= 1.0E-6)) {
         Vec3d vec3d8 = vec3d7.normalize();
         double doubleValue9 = (double)floatValue6 * 0.22;
         vec3d3 = vec3d3.subtract(vec3d8.multiply(doubleValue9));
         vec3d4 = vec3d4.add(vec3d8.multiply(doubleValue9));
         vec3d7 = vec3d4.subtract(vec3d3);
         Vec3d vec3d11 = vec3d3.add(vec3d4).multiply(0.5);
         Vec3d vec3d12 = vec3d11.negate();
         if (vec3d12.lengthSquared() <= 1.0E-6) {
            vec3d12 = new Vec3d(0.0, 1.0, 0.0);
         }

         Vec3d vec3d13 = vec3d7.crossProduct(vec3d12);
         if (vec3d13.lengthSquared() <= 1.0E-6) {
            vec3d13 = vec3d7.crossProduct(new Vec3d(0.0, 1.0, 0.0));
            if (vec3d13.lengthSquared() <= 1.0E-6) {
               vec3d13 = vec3d7.crossProduct(new Vec3d(1.0, 0.0, 0.0));
            }
         }

         vec3d13 = vec3d13.normalize().multiply((double)floatValue6 * 0.48);
         Vec3d vec3d14 = vec3d3.add(vec3d13);
         Vec3d vec3d15 = vec3d3.subtract(vec3d13);
         Vec3d vec3d16 = vec3d4.subtract(vec3d13);
         Vec3d vec3d17 = vec3d4.add(vec3d13);
         float[] floatValueArray18 = dev.leg1tka.api.utils.text.ColorUtils.p(intValue5);
         bufferBuilder1.vertex(matrix4f2, (float)vec3d14.x, (float)vec3d14.y, (float)vec3d14.z).texture(0.4F, 0.0F).color(floatValueArray18[0], floatValueArray18[1], floatValueArray18[2], floatValueArray18[3]);
         bufferBuilder1.vertex(matrix4f2, (float)vec3d15.x, (float)vec3d15.y, (float)vec3d15.z).texture(0.4F, 1.0F).color(floatValueArray18[0], floatValueArray18[1], floatValueArray18[2], floatValueArray18[3]);
         bufferBuilder1.vertex(matrix4f2, (float)vec3d16.x, (float)vec3d16.y, (float)vec3d16.z).texture(0.4F, 1.0F).color(floatValueArray18[0], floatValueArray18[1], floatValueArray18[2], floatValueArray18[3]);
         bufferBuilder1.vertex(matrix4f2, (float)vec3d17.x, (float)vec3d17.y, (float)vec3d17.z).texture(0.4F, 0.0F).color(floatValueArray18[0], floatValueArray18[1], floatValueArray18[2], floatValueArray18[3]);
      }
   }

   private Box a(LivingEntity livingEntity1, Box box2) {
      if (this.t != null && this.u == livingEntity1 && !(this.t.getCenter().squaredDistanceTo(box2.getCenter()) > 144.0)) {
         double doubleValue3 = Math.sqrt(this.t.getCenter().squaredDistanceTo(box2.getCenter()));
         double doubleValue5 = MathHelper.clamp(0.08 + doubleValue3 * 0.035, 0.08, 0.18);
         this.t = this.a(this.t, box2, doubleValue5);
         return this.t;
      } else {
         this.t = box2;
         this.u = livingEntity1;
         return box2;
      }
   }

   private Box a(LivingEntity livingEntity1) {
      Box box2 = livingEntity1.getBoundingBox();
      if (!this.d.getValue()) {
         return box2;
      } else {
         float floatValue3 = 1.0F;
         if (minecraftClient.getRenderTickCounter() != null) {
            floatValue3 = minecraftClient.getRenderTickCounter().getTickDelta(true);
         }

         Vec3d vec3d4 = dev.leg1tka.api.utils.player.EntityFilters.a(livingEntity1, floatValue3);
         Vec3d vec3d5 = vec3d4.subtract(box2.getCenter());
         return box2.offset(vec3d5);
      }
   }

   private Box a(Box box1, Box box2, double doubleValue3) {
      return new Box(
         MathHelper.lerp(doubleValue3, box1.minX, box2.minX),
         MathHelper.lerp(doubleValue3, box1.minY, box2.minY),
         MathHelper.lerp(doubleValue3, box1.minZ, box2.minZ),
         MathHelper.lerp(doubleValue3, box1.maxX, box2.maxX),
         MathHelper.lerp(doubleValue3, box1.maxY, box2.maxY),
         MathHelper.lerp(doubleValue3, box1.maxZ, box2.maxZ)
      );
   }

   private void q() {
      if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null && AspectDLC.INSTANCE.themeStorage.b() != null) {
         dev.leg1tka.gui.themes.ThemePalette value1 = AspectDLC.INSTANCE.themeStorage.b().a();
         if (value1 == null) {
            this.v = dev.leg1tka.api.utils.text.ColorUtils.k(0);
            this.w = dev.leg1tka.api.utils.text.ColorUtils.k(180);
         } else {
            if (!"Rainbow".equals(value1.b())) {
               int intValue2 = value1.c != null && value1.c.length > 0 ? value1.c[0] : dev.leg1tka.api.utils.text.ColorUtils.k(0);
               this.v = intValue2;
               this.w = intValue2;
            } else {
               this.v = dev.leg1tka.api.utils.text.ColorUtils.a();
               this.w = dev.leg1tka.api.utils.text.ColorUtils.k(180);
            }
         }
      } else {
         this.v = dev.leg1tka.api.utils.text.ColorUtils.k(0);
         this.w = dev.leg1tka.api.utils.text.ColorUtils.k(180);
      }
   }



   private static Supplier createSupplier106(ElytraTarget elytraTarget0) {
      return () -> elytraTarget0.d.getValue();
   }

   private static Supplier createSupplier107(ElytraTarget elytraTarget0) {
      return () -> elytraTarget0.d.getValue();
   }

   private static Supplier createSupplier108(ElytraTarget elytraTarget0) {
      return () -> elytraTarget0.d.getValue();
   }

   private static Supplier createSupplier109(ElytraTarget elytraTarget0) {
      return () -> elytraTarget0.d.getValue() && elytraTarget0.g.getValue();
   }

   private static Supplier createSupplier110(ElytraTarget elytraTarget0) {
      return () -> elytraTarget0.d.getValue() && elytraTarget0.g.getValue();
   }

   private static Supplier createSupplier111(ElytraTarget elytraTarget0) {
      return () -> elytraTarget0.d.getValue();
   }

   private static Supplier createSupplier112(ElytraTarget elytraTarget0) {
      return () -> elytraTarget0.d.getValue();
   }

   private static Supplier createSupplier113(ElytraTarget elytraTarget0) {
      return () -> elytraTarget0.d.getValue();
   }
   public static void dev_leg1tka_o_gq_initializer() {}
}
