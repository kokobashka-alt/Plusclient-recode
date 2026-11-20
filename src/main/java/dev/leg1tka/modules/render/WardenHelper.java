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
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.Map;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class WardenHelper extends Module {
   public static WardenHelper c = new WardenHelper();
   private final BooleanSetting d = new BooleanSetting("AutoBot", false);
   private final ModeSetting e = new ModeSetting("Куда тепаться", "Home", "Home", "Spawn");
   private final dev.leg1tka.settings.StringSetting f = new dev.leg1tka.settings.StringSetting("Команда возврата", "warden", 48);
   private final ModeSetting g = new ModeSetting("Зона", "Авто", "Авто", "Везде");
   private final NumberSetting h = new NumberSetting("Радиус зоны", 80.0F, 16.0F, 160.0F, 4.0F)
      .a(() -> this.g.b("Авто"));
   private final NumberSetting i = new NumberSetting("Задержка клика (мс)", 120.0F, 50.0F, 300.0F, 10.0F);
   private final NumberSetting j = new NumberSetting(
      "Перезарядка сундука (сек)",
      60.0F,
      10.0F,
      300.0F,
      5.0F
   );
   private final BooleanSetting k = new BooleanSetting(
      "Невидимость (Божья аура)", true
   );
   private final BooleanSetting l = new BooleanSetting("Зелье скорости", true);
   private final NumberSetting m = new NumberSetting(
      "Оставлять аур в хотбаре", 3.0F, 0.0F, 16.0F, 1.0F
   );
   private final BooleanSetting n = new BooleanSetting(
      "Авто-еда (золотая морковь)", true
   );
   private String o;
   private boolean p;

   public WardenHelper() {
      super("WardenHelper", "WardenHelper", Category.RENDER);
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n});
      dev.leg1tka.network.bots.BotTaskScheduler.a();
   }

   public boolean p() {
      return this.e.b("Home");
   }

   public String q() {
      String string1 = this.f.e() == null ? "" : this.f.e().trim();

      while (string1.startsWith("/")) {
         string1 = string1.substring(1);
      }

      return string1.isEmpty() ? "warden" : string1;
   }

   public boolean r() {
      return this.g.b("Авто");
   }

   public double s() {
      return (double)this.h.f();
   }

   public long t() {
      return (long)this.i.f();
   }

   public long u() {
      return (long)(this.j.f() * 1000.0F);
   }

   public boolean v() {
      return this.k.getValue();
   }

   public boolean w() {
      return this.l.getValue();
   }

   public int x() {
      return Math.round(this.m.f());
   }

   public boolean y() {
      return this.n.getValue();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      dev.leg1tka.network.bots.BotTaskScheduler.c();
      this.d.setValue(false);
      this.p = false;
      this.o = null;
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.world != null && minecraftClient.player != null) {
         String string2 = dev.leg1tka.network.bots.BotController.c();
         if (!string2.equals(this.o)) {
            this.o = string2;
            this.p = dev.leg1tka.network.bots.BotTaskScheduler.a(string2);
            this.d.setValue(this.p);
         }

         if (this.d.getValue() != this.p) {
            this.p = this.d.getValue();
            if (this.p) {
               String string3 = dev.leg1tka.network.bots.BotTaskScheduler.b(string2);
               if (string3 != null) {
                  dev.leg1tka.api.utils.text.ClientChat.a("§7[WardenBot] §c" + string3);
                  this.p = false;
                  this.d.setValue(false);
               }
            } else {
               dev.leg1tka.network.bots.BotTaskScheduler.c(string2);
            }
         }

         dev.leg1tka.network.bots.BotTaskScheduler.e();
         boolean flag4 = dev.leg1tka.network.bots.BotTaskScheduler.a(string2);
         if (flag4 != this.d.getValue()) {
            this.d.setValue(flag4);
            this.p = flag4;
         }
      }
   }

   @EventLink
   public void onRender3D(Event3DRender event3DRender1) {
      Map<BlockPos, dev.leg1tka.network.bots.BotMessageParser.a> map2 = dev.leg1tka.network.bots.BotMessageParser.a(dev.leg1tka.network.bots.BotTaskScheduler.b());
      if (!map2.isEmpty() && minecraftClient.gameRenderer != null && minecraftClient.gameRenderer.getCamera() != null) {
         long longValue3 = System.currentTimeMillis();
         Vec3d vec3d5 = minecraftClient.gameRenderer.getCamera().getPos();
         MatrixStack matrixStack6 = event3DRender1.getMatrices();
         matrixStack6.push();
         matrixStack6.translate(-vec3d5.x, -vec3d5.y, -vec3d5.z);
         Matrix4f matrix4f7 = matrixStack6.peek().getPositionMatrix();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableCull();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
         Tessellator tessellator8 = Tessellator.getInstance();
         BufferBuilder bufferBuilder9 = tessellator8.begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);

         for (dev.leg1tka.network.bots.BotMessageParser.a value11 : map2.values()) {
            int intValue12 = a(value11, longValue3);
            this.a(bufferBuilder9, matrix4f7, value11.a, b(intValue12), c(intValue12), d(intValue12), 0.18F);
         }

         BufferRenderer.drawWithGlobalProgram(bufferBuilder9.end());
         RenderSystem.lineWidth(2.0F);
         BufferBuilder bufferBuilder15 = tessellator8.begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

         for (dev.leg1tka.network.bots.BotMessageParser.a value18 : map2.values()) {
            int intValue13 = a(value18, longValue3);
            this.b(bufferBuilder15, matrix4f7, value18.a, b(intValue13), c(intValue13), d(intValue13), 1.0F);
         }

         BufferRenderer.drawWithGlobalProgram(bufferBuilder15.end());
         RenderSystem.enableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
         matrixStack6.pop();
         dev.leg1tka.render.font.MsdfTextRenderer value17 = dev.leg1tka.render.font.FontCache.a("sf_regular", 20);
         if (value17 != null) {
            for (dev.leg1tka.network.bots.BotMessageParser.a value20 : map2.values()) {
               if (value20.b != dev.leg1tka.network.bots.BotMessageParser.AnchorState.UNKNOWN) {
                  int intValue14 = value20.b == dev.leg1tka.network.bots.BotMessageParser.AnchorState.TIMER ? -43691 : -11141291;
                  this.a(matrixStack6, value20.a(longValue3), (double)value20.a.getX() + 0.5, (double)value20.a.getY() + 1.2, (double)value20.a.getZ() + 0.5, value17, intValue14);
               }
            }
         }
      }
   }

   private static int a(dev.leg1tka.network.bots.BotMessageParser.a value0, long longValue1) {
      if (value0.b == dev.leg1tka.network.bots.BotMessageParser.AnchorState.TIMER) {
         return 16724787;
      } else if (value0.d > longValue1) {
         return 8421504;
      } else {
         return value0.b == dev.leg1tka.network.bots.BotMessageParser.AnchorState.ACTIVE ? 3407667 : 16776960;
      }
   }

   private static float b(int intValue0) {
      return (float)(intValue0 >> 16 & 0xFF) / 255.0F;
   }

   private static float c(int intValue0) {
      return (float)(intValue0 >> 8 & 0xFF) / 255.0F;
   }

   private static float d(int intValue0) {
      return (float)(intValue0 & 0xFF) / 255.0F;
   }

   private void a(MatrixStack matrixStack1, String string2, double doubleValue3, double doubleValue5, double doubleValue7, dev.leg1tka.render.font.MsdfTextRenderer value9, int intValue10) {
      Camera camera11 = minecraftClient.gameRenderer.getCamera();
      Vec3d vec3d12 = camera11.getPos();
      matrixStack1.push();
      matrixStack1.translate(doubleValue3 - vec3d12.x, doubleValue5 - vec3d12.y, doubleValue7 - vec3d12.z);
      matrixStack1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera11.getYaw()));
      matrixStack1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera11.getPitch()));
      matrixStack1.scale(-0.025F, -0.025F, 0.025F);
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableBlend();
      float floatValue13 = value9.a(string2);
      float floatValue14 = 10.0F;
      float floatValue15 = 4.0F;
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      Tessellator tessellator16 = Tessellator.getInstance();
      BufferBuilder bufferBuilder17 = tessellator16.begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
      Matrix4f matrix4f18 = matrixStack1.peek().getPositionMatrix();
      float floatValue19 = -floatValue13 / 2.0F - floatValue15;
      float floatValue20 = floatValue13 / 2.0F + floatValue15;
      float floatValue21 = -floatValue14 - floatValue15;
      float floatValue23 = 0.5F;
      bufferBuilder17.vertex(matrix4f18, floatValue19, floatValue21, 0.0F).color(0.0F, 0.0F, 0.0F, floatValue23);
      bufferBuilder17.vertex(matrix4f18, floatValue19, floatValue15, 0.0F).color(0.0F, 0.0F, 0.0F, floatValue23);
      bufferBuilder17.vertex(matrix4f18, floatValue20, floatValue15, 0.0F).color(0.0F, 0.0F, 0.0F, floatValue23);
      bufferBuilder17.vertex(matrix4f18, floatValue20, floatValue21, 0.0F).color(0.0F, 0.0F, 0.0F, floatValue23);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder17.end());
      value9.b(matrixStack1, string2, 0.0F, -floatValue14, intValue10);
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      matrixStack1.pop();
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, BlockPos blockPos3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      float floatValue8 = (float)blockPos3.getX() + 0.0625F;
      float floatValue9 = (float)blockPos3.getY();
      float floatValue10 = (float)blockPos3.getZ() + 0.0625F;
      float floatValue11 = (float)blockPos3.getX() + 0.9375F;
      float floatValue12 = (float)blockPos3.getY() + 0.875F;
      float floatValue13 = (float)blockPos3.getZ() + 0.9375F;
      dev.leg1tka.render.RenderPrimitives.drawFilledBox(bufferBuilder1, matrix4f2, floatValue8, floatValue9, floatValue10, floatValue11, floatValue12, floatValue13, floatValue4, floatValue5, floatValue6, floatValue7);
   }

   private void b(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, BlockPos blockPos3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      float floatValue8 = (float)blockPos3.getX() + 0.0625F;
      float floatValue9 = (float)blockPos3.getY();
      float floatValue10 = (float)blockPos3.getZ() + 0.0625F;
      float floatValue11 = (float)blockPos3.getX() + 0.9375F;
      float floatValue12 = (float)blockPos3.getY() + 0.875F;
      float floatValue13 = (float)blockPos3.getZ() + 0.9375F;
      dev.leg1tka.render.RenderPrimitives.drawBoxLines(bufferBuilder1, matrix4f2, floatValue8, floatValue9, floatValue10, floatValue11, floatValue12, floatValue13, floatValue4, floatValue5, floatValue6, floatValue7);
   }
}
