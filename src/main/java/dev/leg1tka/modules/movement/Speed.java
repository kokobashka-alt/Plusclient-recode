package dev.leg1tka.modules.movement;


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
import dev.leg1tka.api.events.implement.EventUpdate;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class Speed extends Module implements dev.leg1tka.api.ClientAccess {
   public static Speed c = new Speed();
   private final ModeSetting d = new ModeSetting("Режим", "Entity: grim", "Entity: grim");
   private final NumberSetting e = new NumberSetting("Скорость", 1.05F, 1.0F, 1.6F, 0.1F);
   private final NumberSetting f = new NumberSetting("Радиус", 1.0F, 0.01F, 3.0F, 0.1F);
   private final NumberSetting g = new NumberSetting("Предикт", 1.0F, 0.0F, 5.0F, 0.1F);
   private final BooleanSetting h = new BooleanSetting("Показать предикт", true);
   private final ModeSetting i = new ModeSetting(
         "Шейдер",
         "Красивый",
         "Красивый",
         "Обычный"
      )
      .a(this.h::getValue);
   private final BooleanSetting j = new BooleanSetting("Только на элитре", false);
   private final dev.leg1tka.render.FramebufferOutline k = new dev.leg1tka.render.FramebufferOutline();
   private Box l;
   private LivingEntity m;
   private int n = -1;
   private int o = -1;

   public Speed() {
      super(
         "Speed",
         "Ускорение при столкновении с целью",
         Category.MOVEMENT
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h, this.i, this.j});
   }

   @EventLink
   private void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         this.p();
      }
   }

   private void p() {
      AttackAura attackAura1 = ModuleManager.i;
      if (attackAura1 != null && attackAura1.isEnabled()) {
         LivingEntity livingEntity2 = attackAura1.E();
         if (livingEntity2 != null && livingEntity2 != minecraftClient.player) {
            if (!this.j.getValue() || minecraftClient.player.isGliding()) {
               Box box3 = minecraftClient.player.getBoundingBox().expand(this.f.e().doubleValue());
               boolean flag4 = false;
               if (minecraftClient.player.isGliding() || livingEntity2.getBoundingBox().intersects(box3)) {
                  if (minecraftClient.player.isGliding()) {
                     Vec3d vec3d5 = this.b(livingEntity2);
                     double doubleValue6 = minecraftClient.player.getEyePos().distanceTo(vec3d5);
                     double doubleValue8 = minecraftClient.player.getEyePos().distanceTo(livingEntity2.getBoundingBox().getCenter());
                     if (doubleValue6 <= 2.5 || doubleValue8 <= 2.5) {
                        flag4 = true;
                     }
                  } else {
                     flag4 = true;
                  }
               }

               if (flag4) {
                  Vec3d vec3d10 = this.a(livingEntity2);
                  minecraftClient.player.setVelocity(vec3d10);
               }
            }
         }
      }
   }

   @NotNull
   private Vec3d a(LivingEntity livingEntity1) {
      Vec3d vec3d2 = this.b(livingEntity1);
      double doubleValue3 = vec3d2.x - minecraftClient.player.getX();
      double doubleValue5 = vec3d2.z - minecraftClient.player.getZ();
      float floatValue7 = (float)(Math.toDegrees(Math.atan2(doubleValue5, doubleValue3)) - 90.0);
      double doubleValue8 = Math.toRadians((double)floatValue7);
      double doubleValue10 = 0.072 * this.e.e().doubleValue();
      Vec3d vec3d12 = minecraftClient.player.getVelocity();
      return new Vec3d(vec3d12.x + -Math.sin(doubleValue8) * doubleValue10, vec3d12.y, vec3d12.z + Math.cos(doubleValue8) * doubleValue10);
   }

   private Vec3d b(LivingEntity livingEntity1) {
      double doubleValue2 = this.g.e().doubleValue();
      if (doubleValue2 <= 0.001) {
         return livingEntity1.getPos();
      } else if (minecraftClient.player != null && minecraftClient.player.isGliding() && livingEntity1.isGliding()) {
         Vec3d vec3d8 = dev.leg1tka.api.utils.player.EntityFilters.a(livingEntity1, livingEntity1.getPos(), (int)Math.round(doubleValue2));
         return new Vec3d(vec3d8.x, minecraftClient.player.getY(), vec3d8.z);
      } else {
         double doubleValue4 = livingEntity1.getX() - livingEntity1.prevX;
         double doubleValue6 = livingEntity1.getZ() - livingEntity1.prevZ;
         return livingEntity1.getPos().add(doubleValue4 * doubleValue2, 0.0, doubleValue6 * doubleValue2);
      }
   }

   private Box c(LivingEntity livingEntity1) {
      Box box2 = livingEntity1.getBoundingBox();
      double doubleValue3 = this.g.e().doubleValue();
      if (doubleValue3 <= 0.001) {
         return box2;
      } else if (minecraftClient.player != null && minecraftClient.player.isGliding() && livingEntity1.isGliding()) {
         float floatValue9 = 1.0F;
         if (minecraftClient.getRenderTickCounter() != null) {
            floatValue9 = minecraftClient.getRenderTickCounter().getTickDelta(true);
         }

         Vec3d vec3d6 = dev.leg1tka.api.utils.player.EntityFilters.a(livingEntity1, floatValue9);
         return box2.offset(vec3d6.subtract(box2.getCenter()));
      } else {
         double doubleValue5 = livingEntity1.getX() - livingEntity1.prevX;
         double doubleValue7 = livingEntity1.getZ() - livingEntity1.prevZ;
         return box2.offset(doubleValue5 * doubleValue3, 0.0, doubleValue7 * doubleValue3);
      }
   }

   private Box a(LivingEntity livingEntity1, Box box2) {
      if (this.l != null && this.m == livingEntity1 && !(this.l.getCenter().squaredDistanceTo(box2.getCenter()) > 144.0)) {
         double doubleValue3 = Math.sqrt(this.l.getCenter().squaredDistanceTo(box2.getCenter()));
         double doubleValue5 = MathHelper.clamp(0.12 + doubleValue3 * 0.04, 0.12, 0.35);
         this.l = this.a(this.l, box2, doubleValue5);
         return this.l;
      } else {
         this.l = box2;
         this.m = livingEntity1;
         return box2;
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

   @EventLink(
      priority = -100
   )
   public void onRender3D(Event3DRender event3DRender1) {
      if (minecraftClient.player != null && minecraftClient.world != null && this.h.getValue()) {
         AttackAura attackAura2 = ModuleManager.i;
         if (attackAura2 != null && attackAura2.isEnabled()) {
            LivingEntity livingEntity3 = attackAura2.E();
            if (livingEntity3 != null && livingEntity3 != minecraftClient.player && livingEntity3.isAlive()) {
               Box box4 = this.c(livingEntity3);
               Box box5 = this.a(livingEntity3, box4);
               Vec3d vec3d6 = event3DRender1.getCamera().getPos();
               Box box7 = box5.offset(-vec3d6.x, -vec3d6.y, -vec3d6.z);
               Matrix4f matrix4f8 = event3DRender1.getMatrices().peek().getPositionMatrix();
               if (this.i.b("Красивый")) {
                  this.q();
                  this.k.a(matrix4f8, box7);
               } else {
                  this.k.b();
                  this.a(matrix4f8, box7);
               }
            } else {
               this.r();
            }
         } else {
            this.r();
         }
      } else {
         this.r();
      }
   }

   @EventLink(
      priority = 200
   )
   public void onRender2D(EventRender.Default default1) {
      if (this.k.a() && this.h.getValue() && this.i.b("Красивый")) {
         int intValue2 = dev.leg1tka.api.utils.text.ColorUtils.d(this.n, -1, 0.38F);
         int intValue3 = dev.leg1tka.api.utils.text.ColorUtils.d(this.o, -1, 0.42F);
         this.k.a(dev.leg1tka.render.FramebufferOutline.BloomConfig.c(intValue2, intValue3));
      }
   }

   private void a(Matrix4f matrix4f1, Box box2) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      RenderSystem.lineWidth(1.5F);
      int intValue3 = -520093697;
      int intValue4 = intValue3 >> 16 & 0xFF;
      int intValue5 = intValue3 >> 8 & 0xFF;
      int intValue6 = intValue3 & 0xFF;
      int intValue7 = intValue3 >> 24 & 0xFF;
      dev.leg1tka.render.RenderPrimitives.drawOutlinedBox(matrix4f1, box2, intValue4, intValue5, intValue6, intValue7);
      RenderSystem.lineWidth(1.0F);
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
   }

   private void q() {
      if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null && AspectDLC.INSTANCE.themeStorage.b() != null) {
         dev.leg1tka.gui.themes.ThemePalette value1 = AspectDLC.INSTANCE.themeStorage.b().a();
         if (value1 == null) {
            this.n = dev.leg1tka.api.utils.text.ColorUtils.k(0);
            this.o = dev.leg1tka.api.utils.text.ColorUtils.k(180);
         } else {
            if (!"Rainbow".equals(value1.b())) {
               int intValue2 = value1.c != null && value1.c.length > 0 ? value1.c[0] : dev.leg1tka.api.utils.text.ColorUtils.k(0);
               this.n = intValue2;
               this.o = intValue2;
            } else {
               this.n = dev.leg1tka.api.utils.text.ColorUtils.a();
               this.o = dev.leg1tka.api.utils.text.ColorUtils.k(180);
            }
         }
      } else {
         this.n = dev.leg1tka.api.utils.text.ColorUtils.k(0);
         this.o = dev.leg1tka.api.utils.text.ColorUtils.k(180);
      }
   }

   private void r() {
      this.l = null;
      this.m = null;
      this.k.b();
   }

   @Override
   public void onDisable() {
      this.r();
      this.k.c();
      super.onDisable();
   }
}
