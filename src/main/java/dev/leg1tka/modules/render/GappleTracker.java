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
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class GappleTracker extends Module {
   public static GappleTracker c = new GappleTracker();
   private final BooleanSetting d = new BooleanSetting("Золотое яблоко", true);
   private final BooleanSetting e = new BooleanSetting("Зачарованное яблоко", true);
   private final NumberSetting f = new NumberSetting("Размер иконки", 12.0F, 6.0F, 24.0F, 0.5F);
   private final Map g = new HashMap();
   private final Matrix4f h = new Matrix4f();
   private final Quaternionf i = new Quaternionf();
   private Vec3d j = Vec3d.ZERO;
   private float k = 1.0F;
   private int l;
   private int m;
   private boolean n = false;
   private final Vector3f o = new Vector3f();
   private final Vector4f p = new Vector4f();

   public GappleTracker() {
      super(
         "GappleTracker",
         "Показывает иконку яблока над игроком при поедании",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f});
   }

   @Override
   public void onDisable() {
      this.g.clear();
      this.n = false;
      super.onDisable();
   }

   @EventLink(
      priority = 100
   )
   public void onRender3D(Event3DRender event3DRender1) {
      this.n = true;
      this.h.set(event3DRender1.getProjectionMatrix());
      this.j = event3DRender1.getCamera().getPos();
      this.i.set(event3DRender1.getCamera().getRotation());
      this.k = event3DRender1.getTickDelta();
      this.l = minecraftClient.getWindow().getScaledWidth();
      this.m = minecraftClient.getWindow().getScaledHeight();
   }

   @EventLink
   public void onRender2D(EventRender.Default default1) {
      if (this.n && minecraftClient.world != null && minecraftClient.player != null) {
         MatrixStack matrixStack2 = default1.getContext().getMatrices();
         float floatValue3 = this.f.f();
         int intValue4 = dev.leg1tka.api.utils.text.ColorUtils.a();
         int intValue5 = dev.leg1tka.api.utils.text.ColorUtils.a(8, 8, 10, 210);
         int intValue6 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue4, 230);

         for (PlayerEntity playerEntity8 : minecraftClient.world.getPlayers()) {
            if (playerEntity8 != minecraftClient.player && playerEntity8.isAlive()) {
               ItemStack itemStack9 = null;
               if (playerEntity8.isUsingItem()) {
                  ItemStack itemStack10 = playerEntity8.getActiveItem();
                  if (!itemStack10.isEmpty()) {
                     boolean flag11 = itemStack10.getItem() == Items.GOLDEN_APPLE;
                     boolean flag12 = itemStack10.getItem() == Items.ENCHANTED_GOLDEN_APPLE;
                     if (flag11 && this.d.getValue() || flag12 && this.e.getValue()) {
                        itemStack9 = itemStack10;
                     }
                  }
               }

               String string22 = playerEntity8.getUuidAsString();
               if (itemStack9 == null) {
                  this.g.remove(string22);
               } else {
                  this.g.put(string22, itemStack9);
                  double doubleValue23 = MathHelper.lerp((double)this.k, playerEntity8.prevX, playerEntity8.getX());
                  double doubleValue13 = MathHelper.lerp((double)this.k, playerEntity8.prevY, playerEntity8.getY()) + (double)playerEntity8.getHeight() * 0.4;
                  double doubleValue15 = MathHelper.lerp((double)this.k, playerEntity8.prevZ, playerEntity8.getZ());
                  float[] floatValueArray17 = this.a(doubleValue23, doubleValue13, doubleValue15);
                  if (floatValueArray17 != null) {
                     float floatValue18 = floatValueArray17[0];
                     float floatValue19 = floatValueArray17[1];
                     float floatValue20 = floatValue3 * 0.5F + 4.0F;
                     dev.leg1tka.render.RenderPrimitives.d(matrixStack2, floatValue18, floatValue19, floatValue20 + 2.5F, 3.5F, 0.0F, 360.0F, intValue6);
                     dev.leg1tka.render.RenderPrimitives.d(matrixStack2, floatValue18, floatValue19, floatValue20, floatValue20 - 0.5F, 0.0F, 360.0F, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, 200));

                     for (float floatValue21 = 0.0F; floatValue21 < floatValue20 - 1.0F; floatValue21 += 0.5F) {
                        dev.leg1tka.render.RenderPrimitives.d(matrixStack2, floatValue18, floatValue19, floatValue21 + 0.5F, 0.5F, 0.0F, 360.0F, intValue5);
                     }

                     float floatValue24 = floatValue3 / 16.0F;
                     matrixStack2.push();
                     matrixStack2.translate(floatValue18 - floatValue3 * 0.5F, floatValue19 - floatValue3 * 0.5F, 0.0F);
                     matrixStack2.scale(floatValue24, floatValue24, 1.0F);
                     RenderSystem.enableDepthTest();
                     default1.getContext().drawItem(itemStack9, 0, 0);
                     RenderSystem.disableDepthTest();
                     matrixStack2.pop();
                  }
               }
            }
         }
      }
   }

   private float[] a(double doubleValue1, double doubleValue3, double doubleValue5) {
      double doubleValue7 = doubleValue1 - this.j.x;
      double doubleValue9 = doubleValue3 - this.j.y;
      double doubleValue11 = doubleValue5 - this.j.z;
      Quaternionf quaternionf13 = new Quaternionf(this.i).conjugate();
      this.o.set((float)doubleValue7, (float)doubleValue9, (float)doubleValue11);
      quaternionf13.transform(this.o);
      this.p.set(this.o.x, this.o.y, this.o.z, 1.0F);
      this.h.transform(this.p);
      if (this.p.w <= 0.0F) {
         return null;
      } else {
         float floatValue14 = this.p.x / this.p.w;
         float floatValue15 = this.p.y / this.p.w;
         if (!(floatValue14 < -1.0F) && !(floatValue14 > 1.0F) && !(floatValue15 < -1.0F) && !(floatValue15 > 1.0F)) {
            float floatValue16 = (floatValue14 + 1.0F) * 0.5F * (float)this.l;
            float floatValue17 = (1.0F - floatValue15) * 0.5F * (float)this.m;
            return new float[]{floatValue16, floatValue17};
         } else {
            return null;
         }
      }
   }
}
