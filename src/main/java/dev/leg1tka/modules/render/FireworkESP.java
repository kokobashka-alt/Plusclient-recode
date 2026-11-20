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

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.Event3DRender;
import dev.leg1tka.api.events.implement.EventRender;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class FireworkESP extends Module {
   public static FireworkESP c = new FireworkESP();
   private final NumberSetting d = new NumberSetting("Интервал (мс)", 100.0F, 10.0F, 1000.0F, 10.0F);
   private final NumberSetting e = new NumberSetting("Время жизни (мс)", 1000.0F, 100.0F, 5000.0F, 100.0F);
   private final Matrix4f f = new Matrix4f();
   private final Quaternionf g = new Quaternionf();
   private Vec3d h = Vec3d.ZERO;
   private float i;
   private final Map<Integer, FireworkESP.a> j = new HashMap<>();

   public FireworkESP() {
      super(
         "FireworkESP",
         "Показывает теги и трейлы фейерверков",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.d, this.e});
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.j.clear();
   }

   @EventLink
   public void onRender3D(Event3DRender event3DRender1) {
      this.f.set(event3DRender1.getProjectionMatrix());
      this.h = event3DRender1.getCamera().getPos();
      this.g.set(event3DRender1.getCamera().getRotation());
      this.i = event3DRender1.getTickDelta();
      if (minecraftClient.world != null) {
         long longValue2 = System.currentTimeMillis();
         this.j.entrySet().removeIf(entry3 -> {
            Entity entity4 = minecraftClient.world.getEntityById((Integer)entry3.getKey());
            boolean flag5x = entity4 == null || !entity4.isAlive();
            ((FireworkESP.a)entry3.getValue()).d.removeIf(value3x -> (float)(longValue2 - value3x.b) > this.e.f());
            return flag5x && ((FireworkESP.a)entry3.getValue()).d.isEmpty();
         });

         for (Entity entity5 : minecraftClient.world.getEntities()) {
            if (entity5 instanceof FireworkRocketEntity) {
               FireworkRocketEntity fireworkRocketEntity6 = (FireworkRocketEntity)entity5;
               if (entity5.isAlive()) {
                  FireworkESP.a value7 = this.j.computeIfAbsent(entity5.getId(), integer0 -> new FireworkESP.a());
                  if (!value7.c) {
                     value7.b = this.a(fireworkRocketEntity6);
                     value7.c = true;
                  }

                  if ((float)(longValue2 - value7.a) >= this.d.f()) {
                     Vec3d vec3d8 = new Vec3d(
                        MathHelper.lerp((double)this.i, entity5.lastRenderX, entity5.getX()),
                        MathHelper.lerp((double)this.i, entity5.lastRenderY, entity5.getY()) + 0.5,
                        MathHelper.lerp((double)this.i, entity5.lastRenderZ, entity5.getZ())
                     );
                     float floatValue9 = (float)entity5.age / 20.0F;
                     value7.d.add(new FireworkESP.b(vec3d8, longValue2, floatValue9));
                     value7.a = longValue2;
                  }
               }
            }
         }
      }
   }

   @EventLink
   public void onRender2D(EventRender.Default default1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         MatrixStack matrixStack2 = default1.getContext().getMatrices();
         ItemStack itemStack3 = new ItemStack(Items.FIREWORK_ROCKET);
         long longValue4 = System.currentTimeMillis();
         boolean flag6 = dev.leg1tka.render.RenderPrimitives.b();
         if (flag6) {
            dev.leg1tka.render.FramebufferGlow.c.b();
         }

         try {
            for (Entry<Integer, FireworkESP.a> entry8 : this.j.entrySet()) {
               FireworkESP.a value9 = entry8.getValue();

               for (FireworkESP.b value11 : value9.d) {
                  Vec3d vec3d12 = this.a(value11.a);
                  if (vec3d12 != null) {
                     float floatValue13 = 1.0F - (float)(longValue4 - value11.b) / this.e.f();
                     floatValue13 = MathHelper.clamp(floatValue13, 0.0F, 1.0F);
                     this.a(default1, matrixStack2, itemStack3, vec3d12, floatValue13, value9.b);
                  }
               }

               Entity entity17 = minecraftClient.world.getEntityById((Integer)entry8.getKey());
               if (entity17 instanceof FireworkRocketEntity && entity17.isAlive()) {
                  Vec3d vec3d18 = new Vec3d(
                     MathHelper.lerp((double)this.i, entity17.lastRenderX, entity17.getX()),
                     MathHelper.lerp((double)this.i, entity17.lastRenderY, entity17.getY()) + 0.5,
                     MathHelper.lerp((double)this.i, entity17.lastRenderZ, entity17.getZ())
                  );
                  Vec3d vec3d19 = this.a(vec3d18);
                  if (vec3d19 != null) {
                     this.a(default1, matrixStack2, itemStack3, vec3d19, 1.0F, value9.b);
                  }
               }
            }
         } finally {
            if (flag6) {
               dev.leg1tka.render.FramebufferGlow.c.c();
            }
         }
      }
   }

   private void a(EventRender.Default default1, MatrixStack matrixStack2, ItemStack itemStack3, Vec3d vec3d4, float floatValue5, boolean flag6) {
      float floatValue7 = 0.75F;
      float floatValue8 = 16.0F * floatValue7;
      float floatValue9 = 4.0F;
      float floatValue10 = floatValue8 + floatValue9 * 2.0F;
      float floatValue11 = 4.0F;
      float floatValue12 = 0.45F + 0.55F * floatValue5;
      int intValue13 = (int)(220.0F * floatValue5);
      if (intValue13 > 5) {
         int intValue14 = flag6 ? intValue13 << 24 | 1722906 : intValue13 << 24 | 657930;
         matrixStack2.push();
         matrixStack2.translate(vec3d4.x, vec3d4.y, 0.0);
         matrixStack2.scale(floatValue12, floatValue12, 1.0F);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack2, -floatValue10 * 0.5F, -floatValue10 * 0.5F, floatValue10, floatValue10, floatValue11, intValue14);
         if (flag6) {
            dev.leg1tka.render.RenderPrimitives.a(
               matrixStack2,
               -floatValue10 * 0.5F,
               -floatValue10 * 0.5F,
               floatValue10,
               floatValue10,
               floatValue11,
               floatValue11,
               floatValue11,
               floatValue11,
               1.0F,
               intValue13 << 24 | 5635925,
               intValue13 << 24 | 5635925,
               intValue13 << 24 | 5635925,
               intValue13 << 24 | 5635925
            );
         }

         matrixStack2.push();
         matrixStack2.translate(-floatValue8 * 0.5F, -floatValue8 * 0.5F, 0.0F);
         matrixStack2.scale(floatValue7, floatValue7, 1.0F);
         default1.getContext().drawItem(itemStack3, 0, 0);
         matrixStack2.pop();
         matrixStack2.pop();
      }
   }

   private boolean a(FireworkRocketEntity fireworkRocketEntity1) {
      if (minecraftClient.world != null && AspectDLC.INSTANCE.friendStorage != null) {
         Vec3d vec3d2 = fireworkRocketEntity1.getPos();
         double doubleValue3 = 64.0;

         for (PlayerEntity playerEntity6 : minecraftClient.world.getPlayers()) {
            if (playerEntity6 != minecraftClient.player) {
               double doubleValue7 = playerEntity6.squaredDistanceTo(vec3d2.x, vec3d2.y, vec3d2.z);
               if (doubleValue7 < doubleValue3) {
                  doubleValue3 = doubleValue7;
                  if (AspectDLC.INSTANCE.friendStorage.c(playerEntity6.getName().getString())) {
                     return true;
                  }
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private Vec3d a(Vec3d vec3d1) {
      Vector3f vector3f2 = new Vector3f((float)(vec3d1.x - this.h.x), (float)(vec3d1.y - this.h.y), (float)(vec3d1.z - this.h.z));
      vector3f2.rotate(new Quaternionf(this.g).conjugate());
      Vector4f vector4f3 = new Vector4f(vector3f2.x, vector3f2.y, vector3f2.z, 1.0F);
      this.f.transform(vector4f3);
      float floatValue4 = vector4f3.w;
      if (floatValue4 <= 1.0E-5F) {
         return null;
      } else {
         float floatValue5 = vector4f3.x / floatValue4;
         float floatValue6 = vector4f3.y / floatValue4;
         float floatValue7 = vector4f3.z / floatValue4;
         float floatValue8 = (floatValue5 * 0.5F + 0.5F) * (float)minecraftClient.getWindow().getScaledWidth();
         float floatValue9 = (1.0F - (floatValue6 * 0.5F + 0.5F)) * (float)minecraftClient.getWindow().getScaledHeight();
         return !Float.isNaN(floatValue8) && !Float.isNaN(floatValue9) && !Float.isInfinite(floatValue8) && !Float.isInfinite(floatValue9)
            ? new Vec3d((double)floatValue8, (double)floatValue9, (double)floatValue7)
            : null;
      }
   }

   static class a {
      long a;
      boolean b = false;
      boolean c = false;
      final List<FireworkESP.b> d = new ArrayList<>();

      a() {
      }
   }

   static class b {
      final Vec3d a;
      final long b;
      final float c;

      b(Vec3d vec3d1, long longValue2, float floatValue4) {
         this.a = vec3d1;
         this.b = longValue2;
         this.c = floatValue4;
      }
   }
}
