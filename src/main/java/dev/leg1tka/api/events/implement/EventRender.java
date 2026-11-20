package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class EventRender extends Event {
   public EventRender() {
   }

   public static class Default extends Event {
      private final DrawContext context;
      private final float partialTicks;

      @Generated
      public DrawContext getContext() {
         return this.context;
      }

      @Generated
      public float getPartialTicks() {
         return this.partialTicks;
      }

      @Generated
      public Default(DrawContext drawContext1, float floatValue2) {
         this.context = drawContext1;
         this.partialTicks = floatValue2;
      }
   }

   public static class Game extends Event {
      private final WorldRenderer context;
      private final MatrixStack matrix;
      private final Matrix4f projectionMatrix;
      private final Camera camera;
      private final float partialTicks;
      private final long finishTimeNano;

      @Generated
      public WorldRenderer getContext() {
         return this.context;
      }

      @Generated
      public MatrixStack getMatrix() {
         return this.matrix;
      }

      @Generated
      public Matrix4f getProjectionMatrix() {
         return this.projectionMatrix;
      }

      @Generated
      public Camera getCamera() {
         return this.camera;
      }

      @Generated
      public float getPartialTicks() {
         return this.partialTicks;
      }

      @Generated
      public long getFinishTimeNano() {
         return this.finishTimeNano;
      }

      @Generated
      public Game(WorldRenderer worldRenderer1, MatrixStack matrixStack2, Matrix4f matrix4f3, Camera camera4, float floatValue5, long longValue6) {
         this.context = worldRenderer1;
         this.matrix = matrixStack2;
         this.projectionMatrix = matrix4f3;
         this.camera = camera4;
         this.partialTicks = floatValue5;
         this.finishTimeNano = longValue6;
      }
   }

   public static class World extends Event {
      private final Window scaledResolution;
      private final float partialTicks;
      private final Matrix4f matrix;
      private final MatrixStack matrixStack;

      @Generated
      public Window getScaledResolution() {
         return this.scaledResolution;
      }

      @Generated
      public float getPartialTicks() {
         return this.partialTicks;
      }

      @Generated
      public Matrix4f getMatrix() {
         return this.matrix;
      }

      @Generated
      public MatrixStack getMatrixStack() {
         return this.matrixStack;
      }

      @Generated
      public World(Window window1, float floatValue2, Matrix4f matrix4f3, MatrixStack matrixStack4) {
         this.scaledResolution = window1;
         this.partialTicks = floatValue2;
         this.matrix = matrix4f3;
         this.matrixStack = matrixStack4;
      }
   }
}
