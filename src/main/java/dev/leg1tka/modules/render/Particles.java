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
import dev.leg1tka.api.events.implement.EventAttackEntity;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Particles extends Module {
   public static Particles c = new Particles();
   private static final Identifier d = dev.leg1tka.api.utils.resources.ResourceIdentifiers.a("textures/particle/star.png");
   private static final Identifier e = dev.leg1tka.api.utils.resources.ResourceIdentifiers.a("textures/particle/heart.png");
   private static final Identifier f = dev.leg1tka.api.utils.resources.ResourceIdentifiers.a("textures/particle/dollar.png");
   private static final Identifier g = dev.leg1tka.api.utils.resources.ResourceIdentifiers.a("textures/particle/bloom.png");
   private static final Identifier h = dev.leg1tka.api.utils.resources.ResourceIdentifiers.a("textures/particle/sparkle.png");
   private static final Identifier i = dev.leg1tka.api.utils.resources.ResourceIdentifiers.a("textures/particle/aspectdlc.png");
   private static int j;
   private static double k;
   private static double l;
   private final ModeSetting m = new ModeSetting(
      "Тип частиц",
      "Звездочки",
      "Звездочки",
      "Члены",
      "Доллары",
      "Блум",
      "Сияние"
   );
   private final MultiSetting n = new MultiSetting(
      "Добавлять при",
      new BooleanSetting("Бездействии", false),
      new BooleanSetting("Беге", false),
      new BooleanSetting("Ударе", true),
      new BooleanSetting("Падении перла", false),
      new BooleanSetting("Падении трезубца", false),
      new BooleanSetting("Сносе тотема", true)
   );
   private final NumberSetting o = new NumberSetting("Количество", 10.0F, 2.0F, 40.0F, 1.0F);
   private final BooleanSetting p = new BooleanSetting("Свечение", true);
   private final ArrayList<Particles.Particle> q = new ArrayList<>();
   private final Random r = new Random();
   private final Mutable s = new Mutable();
   private final Quaternionf t = new Quaternionf();
   private final Vector3f u = new Vector3f();
   private final Vector3f v = new Vector3f();

   public Particles() {
      super(
         "Particles",
         "Красивые партиклы при разных действиях",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.m, this.n, this.o, this.p});
   }

   @Override
   public void onDisable() {
      this.q.clear();
      super.onDisable();
   }

   private Identifier p() {
      return switch (this.m.h()) {
         case 1 -> e;
         case 2 -> f;
         case 3 -> g;
         case 4 -> h;
         default -> d;
      };
   }

   boolean a(double doubleValue1, double doubleValue3, double doubleValue5) {
      this.s.set(MathHelper.floor(doubleValue1), MathHelper.floor(doubleValue3), MathHelper.floor(doubleValue5));
      return minecraftClient.world.getBlockState(this.s).isSolidBlock(minecraftClient.world, this.s);
   }

   private boolean b(double doubleValue1, double doubleValue3, double doubleValue5) {
      if (minecraftClient.world == null || minecraftClient.player == null) {
         return true;
      } else if (this.a(doubleValue1, doubleValue3, doubleValue5)) {
         return true;
      } else {
         RaycastContext raycastContext7 = new RaycastContext(minecraftClient.player.getEyePos(), new Vec3d(doubleValue1, doubleValue3, doubleValue5), ShapeType.COLLIDER, FluidHandling.NONE, minecraftClient.player);
         return minecraftClient.world.raycast(raycastContext7).getType() == Type.BLOCK;
      }
   }

   private boolean q() {
      return this.q.size() >= 1500;
   }

   private float a(float floatValue1, float floatValue2) {
      return floatValue1 + this.r.nextFloat() * (floatValue2 - floatValue1);
   }

   private boolean r() {
      return minecraftClient.player != null && (minecraftClient.player.input.movementForward != 0.0F || minecraftClient.player.input.movementSideways != 0.0F);
   }

   @EventLink
   public void onAttack(EventAttackEntity eventAttackEntity1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         if (this.n.a("Ударе")) {
            Entity entity2 = eventAttackEntity1.getTarget();
            if (entity2 != null) {
               for (int intValue3 = 0; intValue3 < 35 && !this.q(); intValue3++) {
                  double doubleValue4 = entity2.getX() + (double)this.a(-0.4F, 0.4F);
                  double doubleValue6 = entity2.getY() + (double)this.a(-0.4F, entity2.getHeight() + 0.4F);
                  double doubleValue8 = entity2.getZ() + (double)this.a(-0.4F, 0.4F);
                  if (!this.b(doubleValue4, doubleValue6, doubleValue8)) {
                     float floatValue10 = this.a(-0.8F, 0.8F) * 2.0F;
                     float floatValue11 = this.a(-0.25F, 1.4F);
                     float floatValue12 = this.a(-0.8F, 0.8F) * 2.0F;
                     long longValue13 = (long)this.a(1000.0F, 1200.0F);
                     this.a(doubleValue4, doubleValue6, doubleValue8, (double)floatValue10 * 0.075, (double)floatValue11 * 0.075, (double)floatValue12 * 0.075, dev.leg1tka.api.utils.text.ColorUtils.a(), 0.3F, longValue13, 0.5F, 7.0E-4F);
                  }
               }
            }
         }
      }
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (minecraftClient.world != null && minecraftClient.player != null) {
         if (this.n.a("Сносе тотема")) {
            if (eventPacket1.getPacket() instanceof EntityStatusS2CPacket entityStatusS2CPacket2 && entityStatusS2CPacket2.getStatus() == 35) {
               minecraftClient.execute(() -> {
                  if (minecraftClient.world != null && minecraftClient.player != null) {
                     Entity entity2x = entityStatusS2CPacket2.getEntity(minecraftClient.world);
                     if (entity2x != null) {
                        this.a(entity2x);
                     }
                  }
               });
            }
         }
      }
   }

   private void a(Entity entity1) {
      double doubleValue2 = entity1.getX();
      double doubleValue4 = entity1.getY() + (double)entity1.getHeight() / 2.0;
      double doubleValue6 = entity1.getZ();

      for (int intValue8 = 0; intValue8 < 50 && !this.q(); intValue8++) {
         double doubleValue9 = this.r.nextDouble() * 2.0 * Math.PI;
         double doubleValue11 = this.r.nextDouble() * Math.PI;
         double doubleValue13 = (this.r.nextDouble() * 0.5 + 0.5) * 0.1;
         double doubleValue15 = Math.sin(doubleValue11) * Math.cos(doubleValue9) * doubleValue13;
         double doubleValue17 = Math.sin(doubleValue11) * Math.sin(doubleValue9) * doubleValue13;
         double doubleValue19 = Math.cos(doubleValue11) * doubleValue13;
         double doubleValue21 = doubleValue2 + (double)this.a(-0.3F, 0.3F);
         double doubleValue23 = doubleValue4 + (double)this.a(-0.3F, 0.3F);
         double doubleValue25 = doubleValue6 + (double)this.a(-0.3F, 0.3F);
         if (!this.b(doubleValue21, doubleValue23, doubleValue25)) {
            int intValue27 = this.r.nextDouble() < 0.7 ? -16711936 : -256;
            long longValue28 = (long)this.a(1500.0F, 2000.0F);
            this.a(doubleValue21, doubleValue23, doubleValue25, doubleValue15, doubleValue17, doubleValue19, intValue27, 0.3F, longValue28, 2.0F, 5.0E-5F);
         }
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         int intValue2 = 0;

         for (int intValue3 = this.q.size(); intValue2 < intValue3; intValue2++) {
            this.q.get(intValue2).a(this);
         }

         this.q.removeIf(Particle::a);
         intValue2 = (int)this.o.f();
         if (this.n.a("Бездействии")) {
            double doubleValue29 = minecraftClient.player.getX();
            double doubleValue5 = minecraftClient.player.getY() + (double)minecraftClient.player.getHeight() / 2.0;
            double doubleValue7 = minecraftClient.player.getZ();

            for (int intValue9 = 0; intValue9 < intValue2 && !this.q(); intValue9++) {
               double doubleValue10 = (double)this.a(7.0F, 35.0F);
               double doubleValue12 = Math.toRadians((double)this.a(0.0F, 360.0F));
               double doubleValue14 = (double)this.a(-7.0F, 25.0F);
               double doubleValue16 = doubleValue29 + Math.cos(doubleValue12) * doubleValue10;
               double doubleValue18 = doubleValue5 + doubleValue14;
               double doubleValue20 = doubleValue7 + Math.sin(doubleValue12) * doubleValue10;
               if (!this.b(doubleValue16, doubleValue18, doubleValue20)) {
                  long longValue22 = (long)this.a(1500.0F, 2000.0F);
                  double doubleValue24 = this.r.nextDouble() < 0.8 ? (double)this.a(0.015F, 0.03F) : 0.125;
                  double doubleValue26 = Math.toRadians((double)this.a(0.0F, 360.0F));
                  this.a(
                     doubleValue16,
                     doubleValue18,
                     doubleValue20,
                     Math.cos(doubleValue26) * doubleValue24,
                     (double)this.a((float)(-doubleValue24 * 0.1F), (float)(doubleValue24 * 0.1F)),
                     Math.sin(doubleValue26) * doubleValue24,
                     dev.leg1tka.api.utils.text.ColorUtils.a(),
                     0.3F,
                     longValue22,
                     3.0F,
                     5.0E-5F
                  );
               }
            }
         }

         if (this.n.a("Беге") && this.r() && !this.q()) {
            this.s();
         }

         boolean flag30 = this.n.a("Падении перла");
         boolean flag4 = this.n.a("Падении трезубца");
         if (flag30 || flag4) {
            double doubleValue31 = minecraftClient.player.getX();
            double doubleValue32 = minecraftClient.player.getY();
            double doubleValue33 = minecraftClient.player.getZ();

            for (Entity entity34 : minecraftClient.world.getEntities()) {
               boolean flag13 = flag30 && entity34 instanceof EnderPearlEntity && !entity34.isOnGround()
                  || flag4 && entity34 instanceof TridentEntity && entity34.getVelocity().lengthSquared() > 0.01;
               if (flag13 && !(Math.abs(entity34.getX() - doubleValue31) > 100.0) && !(Math.abs(entity34.getY() - doubleValue32) > 100.0) && !(Math.abs(entity34.getZ() - doubleValue33) > 100.0)
                  )
                {
                  this.a(entity34.getX(), entity34.getY(), entity34.getZ(), 1);
               }
            }
         }
      }
   }

   private void s() {
      Vec3d vec3d1 = minecraftClient.player.getVelocity();
      double doubleValue2 = Math.sqrt(vec3d1.x * vec3d1.x + vec3d1.z * vec3d1.z);
      Vec3d vec3d4;
      if (doubleValue2 < 0.01) {
         vec3d4 = minecraftClient.player.getRotationVector().multiply(-1.0);
      } else if (minecraftClient.player.isGliding()) {
         vec3d4 = vec3d1.normalize().multiply(-1.0);
      } else {
         vec3d4 = new Vec3d(-vec3d1.x / doubleValue2, 0.0, -vec3d1.z / doubleValue2);
      }

      double doubleValue5 = (minecraftClient.player.isGliding() ? 1.2 : 0.5) + (doubleValue2 > 0.1 ? doubleValue2 * 1.5 : 0.0);
      double doubleValue7 = minecraftClient.player.getX() + vec3d4.x * doubleValue5 + (double)this.a(-0.35F, 0.35F);
      double doubleValue9 = minecraftClient.player.isGliding()
         ? minecraftClient.player.getY() + (double)minecraftClient.player.getHeight() / 2.0 + vec3d4.y * doubleValue5 + (double)this.a(-0.35F, 0.35F)
         : minecraftClient.player.getY() + (double)this.a(0.2F, minecraftClient.player.getHeight() + 0.1F);
      double doubleValue11 = minecraftClient.player.getZ() + vec3d4.z * doubleValue5 + (double)this.a(-0.35F, 0.35F);
      if (!this.b(doubleValue7, doubleValue9, doubleValue11)) {
         double doubleValue13 = 0.075;
         double doubleValue15 = (vec3d4.x * doubleValue13 + (double)this.a(-0.01F, 0.01F)) * 0.1;
         double doubleValue17 = (vec3d4.y * doubleValue13 + (double)this.a(-0.05F, 0.01F)) * 0.1;
         double doubleValue19 = (vec3d4.z * doubleValue13 + (double)this.a(-0.01F, 0.01F)) * 0.1;
         long longValue21 = (long)this.a(1500.0F, 2000.0F);
         this.a(doubleValue7, doubleValue9, doubleValue11, doubleValue15, doubleValue17, doubleValue19, dev.leg1tka.api.utils.text.ColorUtils.a(), 0.3F, longValue21, 3.0F, 5.0E-5F);
      }
   }

   private void a(double doubleValue1, double doubleValue3, double doubleValue5, int intValue7) {
      int intValue8 = dev.leg1tka.api.utils.text.ColorUtils.a();

      for (int intValue9 = 0; (double)intValue9 < (double)intValue7 * 2.5 && !this.q(); intValue9++) {
         double doubleValue10 = doubleValue3 + (double)this.a(0.1F, 0.35F);
         if (!this.b(doubleValue1, doubleValue10, doubleValue5)) {
            float floatValue12 = this.a(0.015F, 0.0375F);
            float floatValue13 = this.a(0.05F, 0.075F);
            double doubleValue14 = (double)this.a(floatValue12, floatValue13);
            double doubleValue16 = doubleValue14 * 0.4;
            double doubleValue18 = Math.toRadians((double)this.a(0.0F, 360.0F));
            long longValue20 = (long)this.a(2400.0F, 2800.0F);
            this.a(
               doubleValue1,
               doubleValue10,
               doubleValue5,
               Math.cos(doubleValue18) * doubleValue14,
               (double)this.a((float)(-doubleValue16), (float)doubleValue16),
               Math.sin(doubleValue18) * doubleValue14,
               intValue8,
               0.25F,
               longValue20,
               2.0F,
               5.0E-5F
            );
         }
      }
   }

   private void a(double doubleValue1, double doubleValue3, double doubleValue5, double doubleValue7, double doubleValue9, double doubleValue11, int intValue13, float floatValue14, long longValue15, float floatValue17, double doubleValue18) {
      if (!this.q()) {
         this.q.add(new Particle(doubleValue1, doubleValue3, doubleValue5, doubleValue7, doubleValue9, doubleValue11, intValue13, floatValue14, longValue15, floatValue17, doubleValue18));
      }
   }

   @EventLink
   public void onRender3D(Event3DRender event3DRender1) {
      if (minecraftClient.player != null && minecraftClient.world != null && !this.q.isEmpty()) {
         Camera camera2 = event3DRender1.getCamera();
         Vec3d vec3d3 = camera2.getPos();
         float floatValue4 = event3DRender1.getTickDelta();
         Matrix4f matrix4f5 = event3DRender1.getMatrices().peek().getPositionMatrix();
         this.t.rotationY((float)Math.toRadians((double)(-camera2.getYaw()))).rotateX((float)Math.toRadians((double)camera2.getPitch()));
         this.t.transform(this.u.set(1.0F, 0.0F, 0.0F));
         this.t.transform(this.v.set(0.0F, 1.0F, 0.0F));
         BufferBuilder bufferBuilder6 = null;
         int intValue7 = 0;

         for (int intValue8 = this.q.size(); intValue7 < intValue8; intValue7++) {
            Particle particle9 = this.q.get(intValue7);
            float floatValue10 = Math.min(1.0F, ((float)particle9.m + floatValue4) / (float)particle9.l);
            int intValue11 = (int)((1.0F - floatValue10) * 255.0F);
            if (intValue11 > 0) {
               if (bufferBuilder6 == null) {
                  bufferBuilder6 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
               }

               float floatValue12 = (float)(MathHelper.lerp((double)floatValue4, particle9.d, particle9.a) - vec3d3.x);
               float floatValue13 = (float)(MathHelper.lerp((double)floatValue4, particle9.e, particle9.b) - vec3d3.y);
               float floatValue14 = (float)(MathHelper.lerp((double)floatValue4, particle9.f, particle9.c) - vec3d3.z);
               float floatValue15 = particle9.k / 2.0F;
               float floatValue16 = this.u.x * floatValue15;
               float floatValue17 = this.u.y * floatValue15;
               float floatValue18 = this.u.z * floatValue15;
               float floatValue19 = this.v.x * floatValue15;
               float floatValue20 = this.v.y * floatValue15;
               float floatValue21 = this.v.z * floatValue15;
               int intValue22 = particle9.j >> 16 & 0xFF;
               int intValue23 = particle9.j >> 8 & 0xFF;
               int intValue24 = particle9.j & 0xFF;
               bufferBuilder6.vertex(matrix4f5, floatValue12 - floatValue16 - floatValue19, floatValue13 - floatValue17 - floatValue20, floatValue14 - floatValue18 - floatValue21).texture(0.0F, 1.0F).color(intValue22, intValue23, intValue24, intValue11);
               bufferBuilder6.vertex(matrix4f5, floatValue12 - floatValue16 + floatValue19, floatValue13 - floatValue17 + floatValue20, floatValue14 - floatValue18 + floatValue21).texture(0.0F, 0.0F).color(intValue22, intValue23, intValue24, intValue11);
               bufferBuilder6.vertex(matrix4f5, floatValue12 + floatValue16 + floatValue19, floatValue13 + floatValue17 + floatValue20, floatValue14 + floatValue18 + floatValue21).texture(1.0F, 0.0F).color(intValue22, intValue23, intValue24, intValue11);
               bufferBuilder6.vertex(matrix4f5, floatValue12 + floatValue16 - floatValue19, floatValue13 + floatValue17 - floatValue20, floatValue14 + floatValue18 - floatValue21).texture(1.0F, 1.0F).color(intValue22, intValue23, intValue24, intValue11);
            }
         }

         if (bufferBuilder6 != null) {
            RenderSystem.enableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.disableCull();
            if (this.p.getValue()) {
               RenderSystem.blendFunc(770, 1);
            } else {
               RenderSystem.defaultBlendFunc();
            }

            RenderSystem.setShaderTexture(0, this.p());
            RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
            BufferRenderer.drawWithGlobalProgram(bufferBuilder6.end());
            RenderSystem.enableCull();
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableBlend();
         }
      }
   }

   static class Particle {
      double a;
      double b;
      double c;
      double d;
      double e;
      double f;
      double g;
      double h;
      double i;
      final int j;
      final float k;
      final int l;
      int m;
      final float n;
      final double o;

      Particle(double doubleValue1, double doubleValue3, double doubleValue5, double doubleValue7, double doubleValue9, double doubleValue11, int intValue13, float floatValue14, long longValue15, float floatValue17, double doubleValue18) {
         this.a = this.d = doubleValue1;
         this.b = this.e = doubleValue3;
         this.c = this.f = doubleValue5;
         this.g = doubleValue7;
         this.h = doubleValue9;
         this.i = doubleValue11;
         this.j = intValue13;
         this.k = floatValue14;
         this.l = Math.max(1, (int)(longValue15 / 50L));
         this.n = floatValue17;
         this.o = doubleValue18;
      }

      boolean a() {
         return this.m >= this.l;
      }

      void a(Particles particles1) {
         this.d = this.a;
         this.e = this.b;
         this.f = this.c;
         this.m++;
         float floatValue2 = Math.min(1.0F, (float)this.m / (float)this.l);
         double doubleValue3 = Math.pow(1.0 - (double)floatValue2, (double)this.n) * 3.0;
         int intValue5 = MathHelper.floor(this.a);
         int intValue6 = MathHelper.floor(this.b);
         int intValue7 = MathHelper.floor(this.c);
         double doubleValue8 = this.a + this.g * doubleValue3;
         if (a(particles1, doubleValue8, this.b, this.c, intValue5, intValue6, intValue7)) {
            this.g = -this.g * 0.8;
            doubleValue8 = this.a;
         }

         double doubleValue10 = this.b + this.h * doubleValue3;
         if (a(particles1, doubleValue8, doubleValue10, this.c, intValue5, intValue6, intValue7)) {
            this.h = -this.h * 1.5;
            doubleValue10 = this.b;
         }

         double doubleValue12 = this.c + this.i * doubleValue3;
         if (a(particles1, doubleValue8, doubleValue10, doubleValue12, intValue5, intValue6, intValue7)) {
            this.i = -this.i * 0.8;
            doubleValue12 = this.c;
         }

         this.a = doubleValue8;
         this.b = doubleValue10;
         this.c = doubleValue12;
         this.g *= 0.9997;
         this.h = this.h * 0.9997 - this.o * 3.0;
         this.i *= 0.9997;
      }

      private static boolean a(Particles particles0, double doubleValue1, double doubleValue3, double doubleValue5, int intValue7, int intValue8, int intValue9) {
         return MathHelper.floor(doubleValue1) == intValue7 && MathHelper.floor(doubleValue3) == intValue8 && MathHelper.floor(doubleValue5) == intValue9 ? false : particles0.a(doubleValue1, doubleValue3, doubleValue5);
      }
   }
}
