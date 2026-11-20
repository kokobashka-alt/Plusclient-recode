package dev.leg1tka.render.hud;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.api.events.implement.EventRender;
import dev.leg1tka.api.events.implement.EventUpdate;
import lombok.Generated;
import net.minecraft.client.util.math.MatrixStack;

public class HudRenderer implements dev.leg1tka.api.ClientAccess {
   protected static final dev.leg1tka.render.FramebufferBlur c = new dev.leg1tka.render.FramebufferBlur();
   protected static float d;
   public final dev.leg1tka.render.hud.HudElement e;
   private dev.leg1tka.render.RenderExecutionMode f = dev.leg1tka.render.RenderExecutionMode.SHADER;
   private boolean g = false;
   private boolean h = false;

   public boolean a() {
      return this.h;
   }

   public void a(boolean flag1) {
      this.h = flag1;
   }

   public dev.leg1tka.render.RenderExecutionMode b() {
      return this.f;
   }

   public void a(dev.leg1tka.render.RenderExecutionMode value1) {
      this.f = value1 != null ? value1 : dev.leg1tka.render.RenderExecutionMode.SHADER;
   }

   public boolean c() {
      return this.f == dev.leg1tka.render.RenderExecutionMode.THREADS;
   }

   public boolean d() {
      return this.g;
   }

   public void b(boolean flag1) {
      this.g = flag1;
   }

   public void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      this.a(matrixStack1, floatValue2, floatValue3, floatValue4, floatValue5, 3.0F, intValue6, 1.0F);
   }

   public void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, float floatValue7) {
      this.a(matrixStack1, floatValue2, floatValue3, floatValue4, floatValue5, 3.0F, intValue6, floatValue7);
   }

   public void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, float floatValue8) {
      dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, intValue7, floatValue8);
   }

   public void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7) {
      dev.leg1tka.render.RenderPrimitives.e(matrixStack1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, 16.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 255));
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, intValue7);
   }

   public void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, float floatValue9, int intValue10) {
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, floatValue9, 16.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 255));
      dev.leg1tka.render.RenderPrimitives.c(matrixStack1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, floatValue7, floatValue8, floatValue9, intValue10);
   }

   public void a(EventUpdate eventUpdate1) {
   }

   public void a(EventRender.Default default1) {
   }

   @Generated
   public HudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      this.e = value1;
   }
}
