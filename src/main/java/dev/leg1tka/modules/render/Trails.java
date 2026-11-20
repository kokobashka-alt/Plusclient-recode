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
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class Trails extends Module {
   public static Trails c = new Trails();
   private final NumberSetting d = new NumberSetting("Длительность", 300.0F, 100.0F, 1000.0F, 10.0F);
   private final List<TrailPoint> e = new ArrayList<>();

   public Trails() {
      super(
         "Trails",
         "Красивый след за игроком",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.d});
   }

   @Override
   public void onDisable() {
      this.e.clear();
      super.onDisable();
   }

   @EventLink
   public void onRender(Event3DRender event3DRender1) {
      if (minecraftClient.options.getPerspective() != Perspective.FIRST_PERSON) {
         if (minecraftClient.player != null && minecraftClient.world != null) {
            long longValue2 = System.currentTimeMillis();
            this.e.removeIf(trailPoint3 -> (float)(longValue2 - trailPoint3.b) > this.d.f());
            Vec3d vec3d4 = this.a(event3DRender1.getTickDelta());
            this.e.add(new TrailPoint(vec3d4));
            this.a(event3DRender1.getMatrices());
         }
      }
   }

   private Vec3d a(float floatValue1) {
      return new Vec3d(
         MathHelper.lerp((double)floatValue1, minecraftClient.player.prevX, minecraftClient.player.getX()),
         MathHelper.lerp((double)floatValue1, minecraftClient.player.prevY, minecraftClient.player.getY()),
         MathHelper.lerp((double)floatValue1, minecraftClient.player.prevZ, minecraftClient.player.getZ())
      );
   }

   private Vec3d a(PlayerEntity playerEntity1, float floatValue2) {
      return new Vec3d(
         MathHelper.lerp((double)floatValue2, playerEntity1.prevX, playerEntity1.getX()),
         MathHelper.lerp((double)floatValue2, playerEntity1.prevY, playerEntity1.getY()),
         MathHelper.lerp((double)floatValue2, playerEntity1.prevZ, playerEntity1.getZ())
      );
   }

   private void a(MatrixStack matrixStack1) {
      if (this.e.size() >= 2) {
         this.p();
         matrixStack1.push();
         Vec3d vec3d2 = minecraftClient.gameRenderer.getCamera().getPos();
         matrixStack1.translate(-vec3d2.x, -vec3d2.y, -vec3d2.z);
         Matrix4f matrix4f3 = matrixStack1.peek().getPositionMatrix();
         int intValue4 = dev.leg1tka.api.utils.text.ColorUtils.a();
         float floatValue5 = dev.leg1tka.api.utils.text.ColorUtils.c(intValue4);
         float floatValue6 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue4);
         float floatValue7 = dev.leg1tka.api.utils.text.ColorUtils.e(intValue4);
         BufferBuilder bufferBuilder8 = Tessellator.getInstance().begin(DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
         int intValue9 = 0;

         for (TrailPoint trailPoint11 : this.e) {
            float floatValue12 = (float)intValue9 / (float)this.e.size() * 0.7F;
            int intValue13 = (int)(floatValue12 * 255.0F);
            bufferBuilder8.vertex(matrix4f3, (float)trailPoint11.a.x, (float)(trailPoint11.a.y + (double)minecraftClient.player.getHeight()), (float)trailPoint11.a.z)
               .color((int)(floatValue5 * 255.0F), (int)(floatValue6 * 255.0F), (int)(floatValue7 * 255.0F), intValue13);
            bufferBuilder8.vertex(matrix4f3, (float)trailPoint11.a.x, (float)trailPoint11.a.y, (float)trailPoint11.a.z)
               .color((int)(floatValue5 * 255.0F), (int)(floatValue6 * 255.0F), (int)(floatValue7 * 255.0F), intValue13);
            intValue9++;
         }

         BufferRenderer.drawWithGlobalProgram(bufferBuilder8.end());
         RenderSystem.lineWidth(2.0F);
         this.a(matrix4f3, this.e, true, floatValue5, floatValue6, floatValue7);
         this.a(matrix4f3, this.e, false, floatValue5, floatValue6, floatValue7);
         matrixStack1.pop();
         this.q();
      }
   }

   private void a(Matrix4f matrix4f1, List<TrailPoint> list2, boolean flag3, float floatValue4, float floatValue5, float floatValue6) {
      BufferBuilder bufferBuilder7 = Tessellator.getInstance().begin(DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
      int intValue8 = 0;

      for (TrailPoint trailPoint10 : list2) {
         float floatValue11 = Math.min((float)intValue8 / (float)list2.size() * 1.5F, 1.0F);
         int intValue12 = (int)(floatValue11 * 255.0F);
         float floatValue13 = flag3 ? (float)(trailPoint10.a.y + (double)minecraftClient.player.getHeight()) : (float)trailPoint10.a.y;
         bufferBuilder7.vertex(matrix4f1, (float)trailPoint10.a.x, floatValue13, (float)trailPoint10.a.z).color((int)(floatValue4 * 255.0F), (int)(floatValue5 * 255.0F), (int)(floatValue6 * 255.0F), intValue12);
         intValue8++;
      }

      BufferRenderer.drawWithGlobalProgram(bufferBuilder7.end());
   }

   private void p() {
      RenderSystem.enableBlend();
      RenderSystem.disableCull();
      RenderSystem.enableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
   }

   private void q() {
      RenderSystem.depthMask(true);
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
   }

   static class TrailPoint {
      public Vec3d a;
      public long b;

      public TrailPoint(Vec3d vec3d1) {
         this.a = vec3d1;
         this.b = System.currentTimeMillis();
      }
   }
}
