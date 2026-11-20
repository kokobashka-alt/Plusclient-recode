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
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

public class Projectile extends Module {
   private final dev.leg1tka.render.font.MsdfTextRenderer d = dev.leg1tka.render.font.FontCache.a("sf_regular", 14);
   private static final ItemStack e = new ItemStack(Items.ENDER_PEARL);
   public static Projectile c = new Projectile();
   private final NumberSetting f = new NumberSetting("Размер", 1.2F, 0.6F, 2.4F, 0.1F);
   private final List<Projectile.a> g = new ArrayList<>();
   private final Matrix4f h = new Matrix4f();
   private final Quaternionf i = new Quaternionf();
   private Vec3d j = Vec3d.ZERO;
   private boolean k;

   public Projectile() {
      super(
         "Projectile",
         "Траектория жемчуга эндера",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.f});
   }

   private List<Vec3d> a(EnderPearlEntity enderPearlEntity1, float floatValue2) {
      ArrayList arrayList3 = new ArrayList();
      if (minecraftClient.world != null && minecraftClient.player != null) {
         Vec3d vec3d4 = enderPearlEntity1.getPos();
         Vec3d vec3d5 = enderPearlEntity1.getVelocity();
         arrayList3.add(vec3d4);
         double doubleValue6 = 0.03;
         double doubleValue8 = 0.99;
         short value10 = 400;
         Vec3d vec3d11 = vec3d4;
         Vec3d vec3d12 = vec3d5;

         for (int intValue13 = 0; intValue13 < value10; intValue13++) {
            Vec3d vec3d14 = vec3d11.add(vec3d12);
            RaycastContext raycastContext15 = new RaycastContext(vec3d11, vec3d14, ShapeType.COLLIDER, FluidHandling.NONE, minecraftClient.player);
            BlockHitResult blockHitResult16 = minecraftClient.world.raycast(raycastContext15);
            if (blockHitResult16.getType() != Type.MISS) {
               arrayList3.add(blockHitResult16.getPos());
               break;
            }

            arrayList3.add(vec3d14);
            vec3d12 = new Vec3d(vec3d12.x * doubleValue8, vec3d12.y * doubleValue8 - doubleValue6, vec3d12.z * doubleValue8);
            vec3d11 = vec3d14;
            if (vec3d12.length() < 0.01 && intValue13 > 10) {
               break;
            }
         }

         return arrayList3;
      } else {
         return arrayList3;
      }
   }

   @EventLink
   public void onRender3D(Event3DRender event3DRender1) {
      if (minecraftClient.world != null && minecraftClient.player != null) {
         this.g.clear();
         this.k = true;
         this.h.set(event3DRender1.getProjectionMatrix());
         this.j = event3DRender1.getCamera().getPos();
         this.i.set(event3DRender1.getCamera().getRotation());
         MatrixStack matrixStack2 = event3DRender1.getMatrices();
         Camera camera3 = event3DRender1.getCamera();
         Vec3d vec3d4 = camera3.getPos();
         float floatValue5 = event3DRender1.getTickDelta();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableCull();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.setShader(ShaderProgramKeys.RENDERTYPE_LINES);
         RenderSystem.lineWidth(Math.max(1.0F, this.f.f() * 2.0F));
         GL11.glEnable(2848);
         Box box6 = minecraftClient.player.getBoundingBox().expand(128.0);

         for (EnderPearlEntity enderPearlEntity8 : minecraftClient.world.getEntitiesByClass(EnderPearlEntity.class, box6, Entity::isAlive)) {
            List list9 = this.a(enderPearlEntity8, floatValue5);
            if (list9.size() >= 2) {
               float floatValue10 = (float)(list9.size() - 1) / 20.0F;
               Vec3d vec3d11 = (Vec3d)list9.get(list9.size() - 1);
               this.g.add(new Projectile.a(vec3d11, floatValue10, e));
               matrixStack2.push();
               matrixStack2.translate(-vec3d4.x, -vec3d4.y, -vec3d4.z);
               BufferBuilder bufferBuilder12 = Tessellator.getInstance().begin(DrawMode.LINES, VertexFormats.LINES);
               int intValue13 = dev.leg1tka.api.utils.text.ColorUtils.a();

               for (int intValue14 = 1; intValue14 < list9.size(); intValue14++) {
                  int intValue15 = MathHelper.clamp((int)(MathHelper.clamp((float)intValue14 / 25.0F, 0.0F, 1.0F) * 255.0F), 0, 255);
                  int intValue16 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue13, intValue15);
                  this.a(matrixStack2, bufferBuilder12, (Vec3d)list9.get(intValue14 - 1), (Vec3d)list9.get(intValue14), intValue16);
               }

               BufferRenderer.drawWithGlobalProgram(bufferBuilder12.end());
               matrixStack2.pop();
            }
         }

         RenderSystem.lineWidth(1.0F);
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
         RenderSystem.enableCull();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
         GL11.glDisable(2848);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
      }
   }

   @EventLink
   public void onRender2D(EventRender.Default default1) {
      if (this.k && !this.g.isEmpty() && minecraftClient.player != null) {
         DrawContext drawContext2 = default1.getContext();
         MatrixStack matrixStack3 = drawContext2.getMatrices();
         dev.leg1tka.render.font.MsdfTextRenderer value4 = this.d;
         if (value4 != null) {
            for (Projectile.a value6 : this.g) {
               Vec3d vec3d7 = this.a(value6.pos);
               if (vec3d7 != null && vec3d7.z < 1.0) {
                  String string8 = String.format("%.1f", value6.seconds).replace(',', '.') + " сек";
                  float floatValue9 = value4.b(string8);
                  float floatValue10 = floatValue9 + 17.0F;
                  float floatValue11 = 10.75F;
                  float floatValue12 = (float)vec3d7.x - floatValue10 / 2.0F;
                  float floatValue13 = (float)vec3d7.y - floatValue11 - 4.0F;
                  dev.leg1tka.render.RenderPrimitives.b(matrixStack3, floatValue12, floatValue13, floatValue10, floatValue11, 1.5F, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, 120));
                  dev.leg1tka.render.RenderPrimitives.a(drawContext2, value6.stack(), floatValue12 + 2.0F, floatValue13 + 1.4F, 0.5F, 100.0F);
                  value4.a(matrixStack3, string8, floatValue12 + 13.0F, floatValue13 + 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 255));
               }
            }
         }
      }
   }

   private void a(MatrixStack matrixStack1, VertexConsumer vertexConsumer2, Vec3d vec3d3, Vec3d vec3d4, int intValue5) {
      Entry entry6 = matrixStack1.peek();
      Vector3f vector3f7 = this.a(vec3d3, vec3d4);
      vertexConsumer2.vertex(entry6, vec3d3.toVector3f()).color(intValue5).normal(entry6, vector3f7.x(), vector3f7.y(), vector3f7.z());
      vertexConsumer2.vertex(entry6, vec3d4.toVector3f()).color(intValue5).normal(entry6, vector3f7.x(), vector3f7.y(), vector3f7.z());
   }

   private Vector3f a(Vec3d vec3d1, Vec3d vec3d2) {
      float floatValue3 = (float)(vec3d2.x - vec3d1.x);
      float floatValue4 = (float)(vec3d2.y - vec3d1.y);
      float floatValue5 = (float)(vec3d2.z - vec3d1.z);
      float floatValue6 = MathHelper.sqrt(floatValue3 * floatValue3 + floatValue4 * floatValue4 + floatValue5 * floatValue5);
      return floatValue6 <= 0.0F ? new Vector3f(0.0F, 1.0F, 0.0F) : new Vector3f(floatValue3 / floatValue6, floatValue4 / floatValue6, floatValue5 / floatValue6);
   }

   private Vec3d a(Vec3d vec3d1) {
      if (minecraftClient.player == null) {
         return null;
      } else {
         Vector3f vector3f2 = new Vector3f((float)(vec3d1.x - this.j.x), (float)(vec3d1.y - this.j.y), (float)(vec3d1.z - this.j.z));
         vector3f2.rotate(new Quaternionf(this.i).conjugate());
         Vector4f vector4f3 = new Vector4f(vector3f2.x, vector3f2.y, vector3f2.z, 1.0F);
         this.h.transform(vector4f3);
         if (vector4f3.w() <= 1.0E-5F) {
            return null;
         } else {
            float floatValue4 = 1.0F / vector4f3.w();
            float floatValue5 = (vector4f3.x() * floatValue4 + 1.0F) * (float)minecraftClient.getWindow().getScaledWidth() / 2.0F;
            float floatValue6 = (1.0F - vector4f3.y() * floatValue4) * (float)minecraftClient.getWindow().getScaledHeight() / 2.0F;
            float floatValue7 = vector4f3.z() * floatValue4;
            return !Float.isNaN(floatValue5) && !Float.isNaN(floatValue6) && !Float.isInfinite(floatValue5) && !Float.isInfinite(floatValue6)
               ? new Vec3d((double)floatValue5, (double)floatValue6, (double)floatValue7)
               : null;
         }
      }
   }

   static record a(Vec3d pos, float seconds, ItemStack stack)  {


}
}
