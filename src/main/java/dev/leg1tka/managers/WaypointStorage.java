package dev.leg1tka.managers;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventRender;
import lombok.Generated;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class WaypointStorage implements dev.leg1tka.api.ClientAccess {
   private static final Identifier c = dev.leg1tka.api.utils.resources.ResourceIdentifiers.a("textures/arrows/gps.png");
   private final dev.leg1tka.api.animation.Animation d = new dev.leg1tka.api.animation.Animation(0.0F, 8.5F, dev.leg1tka.api.animation.Easings.k);
   private float e;
   private dev.leg1tka.api.utils.math.Point2D f = null;

   public WaypointStorage() {
      EventInvoker.register(this);
   }

   public void a(dev.leg1tka.api.utils.math.Point2D value1) {
      this.f = value1;
   }

   public void b(dev.leg1tka.api.utils.math.Point2D value1) {
      if (this.f != null && this.f.equals(value1)) {
         this.f = null;
      }
   }

   public void a() {
      this.f = null;
   }

   public boolean b() {
      return this.f == null;
   }

   @EventLink
   public void onRender2D(EventRender.Default default1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         this.d.animateTo(this.f == null ? 0.0F : 1.0F);
         float floatValue2 = MathHelper.clamp(this.d.getValue(), 0.0F, 1.0F);
         if (this.f != null && !(floatValue2 <= 0.02F)) {
            float floatValue3 = (float)minecraftClient.getWindow().getScaledWidth() * 0.5F;
            float floatValue4 = (float)minecraftClient.getWindow().getScaledHeight() * 0.25F;
            float floatValue5 = 40.0F;
            double doubleValue6 = this.f.a() - minecraftClient.player.getX();
            double doubleValue8 = this.f.b() - minecraftClient.player.getZ();
            int intValue10 = (int)dev.leg1tka.api.utils.math.MathOperations.a(MathHelper.sqrt((float)(doubleValue6 * doubleValue6 + doubleValue8 * doubleValue8)));
            float floatValue11 = (float)(-Math.toDegrees(Math.atan2(doubleValue6, doubleValue8))) - minecraftClient.gameRenderer.getCamera().getYaw();
            this.e = this.a(this.e, floatValue11, 0.18F);
            int intValue12 = dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(), floatValue2);
            dev.leg1tka.render.font.MsdfTextRenderer value13 = dev.leg1tka.render.font.FontCache.a("sf_regular", 12);
            if (value13 != null) {
               String string14 = intValue10 + "m.";
               value13.d(default1.getContext().getMatrices(), string14, floatValue3 - value13.b(string14) * 0.5F + 1.5F, floatValue4 + 7.5F, dev.leg1tka.api.utils.text.ColorUtils.a(-1, floatValue2));
            }

            default1.getContext().getMatrices().push();
            default1.getContext().getMatrices().translate(floatValue3, floatValue4, 0.0F);
            default1.getContext().getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(this.e));
            default1.getContext().getMatrices().translate(-floatValue3, -floatValue4, 0.0F);
            float floatValue16 = floatValue3 - floatValue5 * 0.5F;
            float floatValue15 = floatValue4 - floatValue5 * 0.5F;
            dev.leg1tka.render.RenderPrimitives.a(default1.getContext().getMatrices(), c, floatValue16, floatValue15, floatValue5, floatValue5, intValue12);
            default1.getContext().getMatrices().pop();
         }
      }
   }

   private float a(float floatValue1, float floatValue2, float floatValue3) {
      float floatValue4 = MathHelper.wrapDegrees(floatValue2 - floatValue1);
      return floatValue1 + floatValue4 * floatValue3;
   }

   @Generated
   public dev.leg1tka.api.utils.math.Point2D c() {
      return this.f;
   }
}
