package dev.leg1tka.render.hud;


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

import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

public class HudElement implements dev.leg1tka.api.ClientAccess {
   private float xPos;
   private float yPos;
   public float initialXVal;
   public float initialYVal;
   private float startX;
   private float startY;
   private boolean dragging;
   private float width;
   private float height;
   private String name;
   private final Module module;
   private float targetXPos;
   private float targetYPos;
   private static final float CENTER_LINE_WIDTH = 1.0F;
   private static final float SNAP_THRESHOLD = 10.0F;
   private float lineAlpha = 0.0F;
   private long lastUpdateTime;
   private boolean snapToCenter;
   private boolean snapToCenterx;
   private boolean snapToCenter2x;
   private boolean snapToCenter3x;
   private boolean snapToCenter4x;
   private boolean snapToCenter5x;
   private boolean snapToCenter2;
   private boolean snapToCenter3;
   private boolean snapToCenter4;
   private boolean snapToCenter5;
   private static final float LERP_SPEED = 0.19F;
   private static final float MAX_TILT_DEGREES = 25.0F;
   private static final float TILT_FROM_MOUSE_DELTA = 4.0F;
   private static final float DRAG_TILT_LERP = 0.14F;
   private static final float RELEASE_TILT_LERP = 0.1F;
   private static final float TILT_DELTA_SMOOTHING = 0.18F;
   private static final float TILT_TARGET_SMOOTHING = 0.22F;
   private static final float TILT_DEADZONE = 0.002F;
   private static final float DRAG_SCALE_MULTIPLIER = 1.01F;
   private static final float DRAG_SCALE_LERP = 0.1F;
   private static final float RELEASE_SCALE_LERP = 0.02F;
   private float dragTiltDegrees;
   private float targetTiltDegrees;
   private float smoothedMouseDeltaX;
   private float lastDragMouseX;
   private boolean hasLastDragMouseX;
   private boolean tiltMatrixPushed;
   private float dragScale = 1.0F;
   private float targetScale = 1.0F;

   public HudElement(Module module1, String string2, float floatValue3, float floatValue4) {
      this.module = module1;
      this.name = string2;
      this.xPos = floatValue3;
      this.yPos = floatValue4;
      this.initialXVal = floatValue3;
      this.initialYVal = floatValue4;
   }

   public float a() {
      return this.xPos;
   }

   public void a(float floatValue1) {
      this.xPos = floatValue1;
   }

   public float b() {
      return this.yPos;
   }

   public void b(float floatValue1) {
      this.yPos = floatValue1;
   }

   private dev.leg1tka.render.hud.HudDimensions a(int intValue1, int intValue2) {
      MinecraftClient minecraftClient3 = MinecraftClient.getInstance();
      Window window4 = minecraftClient3 == null ? null : minecraftClient3.getWindow();
      double doubleValue5 = window4 == null ? 1.0 : window4.getScaleFactor();
      return new dev.leg1tka.render.hud.HudDimensions((int)((double)intValue1 * doubleValue5 / 2.0), (int)((double)intValue2 * doubleValue5 / 2.0));
   }

   public final void a(int intValue1, int intValue2, Window window3, MatrixStack matrixStack4) {
      dev.leg1tka.render.hud.HudDimensions value5 = this.a(intValue1, intValue2);
      intValue1 = value5.a();
      intValue2 = value5.b();
      float floatValue6 = (float)window3.getScaledWidth() / 2.0F;
      float floatValue7 = (float)window3.getScaledHeight() / 2.0F;
      float floatValue8 = (float)window3.getScaledWidth() / 4.0F;
      float floatValue9 = (float)window3.getScaledHeight() / 4.0F;
      float floatValue10 = (float)window3.getScaledWidth() / 8.0F;
      float floatValue11 = (float)window3.getScaledHeight() / 8.0F;
      float floatValue12 = (float)window3.getScaledWidth() / 1.15F;
      float floatValue13 = (float)window3.getScaledHeight() / 1.15F;
      float floatValue14 = (float)window3.getScaledWidth() / 1.35F;
      float floatValue15 = (float)window3.getScaledHeight() / 1.35F;
      this.snapToCenter = this.snapToCenterx = this.snapToCenter2x = this.snapToCenter3x = this.snapToCenter4x = this.snapToCenter5x = this.snapToCenter2 = this.snapToCenter3 = this.snapToCenter4 = this.snapToCenter5 = false;
      if (this.dragging) {
         this.targetScale = 1.01F;
         if (this.hasLastDragMouseX) {
            float floatValue16 = (float)intValue1 - this.lastDragMouseX;
            if (Math.abs(floatValue16) < 0.18F) {
               floatValue16 = 0.0F;
            }

            this.smoothedMouseDeltaX = dev.leg1tka.api.utils.math.MathOperations.b(this.smoothedMouseDeltaX, floatValue16, 0.18F);
            float floatValue17 = Math.max(-25.0F, Math.min(25.0F, this.smoothedMouseDeltaX * 4.0F));
            this.targetTiltDegrees = dev.leg1tka.api.utils.math.MathOperations.b(this.targetTiltDegrees, floatValue17, 0.22F);
         }

         this.lastDragMouseX = (float)intValue1;
         this.hasLastDragMouseX = true;
         this.targetXPos = (float)intValue1 - this.startX;
         this.targetYPos = (float)intValue2 - this.startY;
         boolean flag20 = false;
         if (Math.abs(this.targetXPos + this.width / 2.0F - floatValue6) < 10.0F) {
            this.targetXPos = floatValue6 - this.width / 2.0F;
            this.snapToCenterx = true;
            flag20 = true;
         }

         if (Math.abs(this.targetYPos + this.height / 2.0F - floatValue7) < 10.0F) {
            this.targetYPos = floatValue7 - this.height / 2.0F;
            this.snapToCenter = true;
            flag20 = true;
         }

         if (Math.abs(this.targetXPos + this.width / 2.0F - floatValue8) < 10.0F) {
            this.targetXPos = floatValue8 - this.width / 2.0F;
            this.snapToCenter2x = true;
            flag20 = true;
         }

         if (Math.abs(this.targetYPos + this.height / 2.0F - floatValue9) < 10.0F) {
            this.targetYPos = floatValue9 - this.height / 2.0F;
            this.snapToCenter2 = true;
            flag20 = true;
         }

         if (Math.abs(this.targetXPos + this.width / 2.0F - floatValue10) < 10.0F) {
            this.targetXPos = floatValue10 - this.width / 2.0F;
            this.snapToCenter3x = true;
            flag20 = true;
         }

         if (Math.abs(this.targetYPos + this.height / 2.0F - floatValue11) < 10.0F) {
            this.targetYPos = floatValue11 - this.height / 2.0F;
            this.snapToCenter3 = true;
            flag20 = true;
         }

         if (Math.abs(this.targetXPos + this.width / 2.0F - floatValue12) < 10.0F) {
            this.targetXPos = floatValue12 - this.width / 2.0F;
            this.snapToCenter4x = true;
            flag20 = true;
         }

         if (Math.abs(this.targetYPos + this.height / 2.0F - floatValue13) < 10.0F) {
            this.targetYPos = floatValue13 - this.height / 2.0F;
            this.snapToCenter4 = true;
            flag20 = true;
         }

         if (Math.abs(this.targetXPos + this.width / 2.0F - floatValue14) < 10.0F) {
            this.targetXPos = floatValue14 - this.width / 2.0F;
            this.snapToCenter5x = true;
            flag20 = true;
         }

         if (Math.abs(this.targetYPos + this.height / 2.0F - floatValue15) < 10.0F) {
            this.targetYPos = floatValue15 - this.height / 2.0F;
            this.snapToCenter5 = true;
            flag20 = true;
         }

         if (this.targetXPos + this.width > (float)window3.getScaledWidth()) {
            this.targetXPos = (float)window3.getScaledWidth() - this.width;
         }

         if (this.targetYPos + this.height > (float)window3.getScaledHeight()) {
            this.targetYPos = (float)window3.getScaledHeight() - this.height;
         }

         if (this.targetXPos < 0.0F) {
            this.targetXPos = 0.0F;
         }

         if (this.targetYPos < 0.0F) {
            this.targetYPos = 0.0F;
         }

         this.xPos = dev.leg1tka.api.utils.math.MathOperations.b(this.xPos, this.targetXPos, 0.19F);
         this.yPos = dev.leg1tka.api.utils.math.MathOperations.b(this.yPos, this.targetYPos, 0.19F);
         this.a(flag20);
      } else {
         this.targetScale = 1.0F;
         this.targetTiltDegrees = 0.0F;
         this.smoothedMouseDeltaX = dev.leg1tka.api.utils.math.MathOperations.b(this.smoothedMouseDeltaX, 0.0F, 0.18F);
         this.hasLastDragMouseX = false;
         this.a(false);
      }

      this.g();
      this.a(matrixStack4, window3);
   }

   private void g() {
      float floatValue1 = this.dragging ? 0.14F : 0.1F;
      this.dragTiltDegrees = dev.leg1tka.api.utils.math.MathOperations.b(this.dragTiltDegrees, this.targetTiltDegrees, floatValue1);
      if (!this.dragging && Math.abs(this.dragTiltDegrees) < 0.02F) {
         this.dragTiltDegrees = 0.0F;
      }

      float floatValue2 = this.dragging ? 0.1F : 0.02F;
      this.dragScale = dev.leg1tka.api.utils.math.MathOperations.b(this.dragScale, this.targetScale, floatValue2);
      if (!this.dragging && Math.abs(this.dragScale - 1.0F) < 0.002F) {
         this.dragScale = 1.0F;
      }
   }

   public void a(MatrixStack matrixStack1) {
      this.tiltMatrixPushed = false;
   }

   public void b(MatrixStack matrixStack1) {
      this.tiltMatrixPushed = false;
   }

   private void a(boolean flag1) {
      long longValue2 = System.currentTimeMillis();
      float floatValue4 = (float)(longValue2 - this.lastUpdateTime) / 1000.0F;
      this.lastUpdateTime = longValue2;
      float floatValue5 = 2.0F;
      float floatValue6 = 2.0F;
      if (flag1) {
         this.lineAlpha += floatValue4 * floatValue5;
         if (this.lineAlpha > 1.0F) {
            this.lineAlpha = 1.0F;
         }
      } else {
         this.lineAlpha -= floatValue4 * floatValue6;
         if (this.lineAlpha < 0.0F) {
            this.lineAlpha = 0.0F;
         }
      }
   }

   private void a(MatrixStack matrixStack1, Window window2) {
      if (this.lineAlpha > 0.0F) {
         float floatValue3 = (float)window2.getScaledWidth() / 2.0F;
         float floatValue4 = (float)window2.getScaledHeight() / 2.0F;
         float floatValue5 = (float)window2.getScaledWidth() / 4.0F;
         float floatValue6 = (float)window2.getScaledHeight() / 4.0F;
         float floatValue7 = (float)window2.getScaledWidth() / 8.0F;
         float floatValue8 = (float)window2.getScaledHeight() / 8.0F;
         float floatValue9 = (float)window2.getScaledWidth() / 1.15F;
         float floatValue10 = (float)window2.getScaledHeight() / 1.15F;
         float floatValue11 = (float)window2.getScaledWidth() / 1.35F;
         float floatValue12 = (float)window2.getScaledHeight() / 1.35F;
         int intValue13 = (int)(this.lineAlpha * 255.0F) << 24 | 16777215;
         if (this.snapToCenterx) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue3 - 0.33333334F, 0.0F, 1.0F, (float)window2.getScaledHeight(), 1.0F, intValue13);
         }

         if (this.snapToCenter) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, 0.0F, floatValue4 - 0.33333334F, (float)window2.getScaledWidth(), 1.0F, 1.0F, intValue13);
         }

         if (this.snapToCenter2x) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue5 - 0.33333334F, 0.0F, 1.0F, (float)window2.getScaledHeight(), 1.0F, intValue13);
         }

         if (this.snapToCenter2) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, 0.0F, floatValue6 - 0.33333334F, (float)window2.getScaledWidth(), 1.0F, 1.0F, intValue13);
         }

         if (this.snapToCenter3x) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue7 - 0.33333334F, 0.0F, 1.0F, (float)window2.getScaledHeight(), 1.0F, intValue13);
         }

         if (this.snapToCenter3) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, 0.0F, floatValue8 - 0.33333334F, (float)window2.getScaledWidth(), 1.0F, 1.0F, intValue13);
         }

         if (this.snapToCenter4x) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue9 - 0.33333334F, 0.0F, 1.0F, (float)window2.getScaledHeight(), 1.0F, intValue13);
         }

         if (this.snapToCenter4) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, 0.0F, floatValue10 - 0.33333334F, (float)window2.getScaledWidth(), 1.0F, 1.0F, intValue13);
         }

         if (this.snapToCenter5x) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue11 - 0.33333334F, 0.0F, 1.0F, (float)window2.getScaledHeight(), 1.0F, intValue13);
         }

         if (this.snapToCenter5) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, 0.0F, floatValue12 - 0.33333334F, (float)window2.getScaledWidth(), 1.0F, 1.0F, intValue13);
         }
      }
   }

   public final boolean a(double doubleValue1, double doubleValue3, int intValue5) {
      if (intValue5 == 0 && dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, this.xPos, this.yPos, this.width, this.height)) {
         this.dragging = true;
         this.targetScale = 1.01F;
         this.startX = (float)((int)(doubleValue1 - (double)this.xPos));
         this.startY = (float)((int)(doubleValue3 - (double)this.yPos));
         this.smoothedMouseDeltaX = 0.0F;
         this.hasLastDragMouseX = false;
         this.lastUpdateTime = System.currentTimeMillis();
         return true;
      } else {
         return false;
      }
   }

   public final void a(int intValue1) {
      if (intValue1 == 0) {
         this.dragging = false;
         this.targetScale = 1.0F;
         this.targetTiltDegrees = 0.0F;
         this.smoothedMouseDeltaX = 0.0F;
         this.hasLastDragMouseX = false;
      }
   }

   @Generated
   public void c(float floatValue1) {
      this.width = floatValue1;
   }

   @Generated
   public float c() {
      return this.width;
   }

   @Generated
   public void d(float floatValue1) {
      this.height = floatValue1;
   }

   @Generated
   public float d() {
      return this.height;
   }

   @Generated
   public String e() {
      return this.name;
   }

   @Generated
   public Module f() {
      return this.module;
   }
}
