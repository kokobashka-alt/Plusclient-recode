package dev.leg1tka.api.events.implement;

import dev.leg1tka.api.events.Event;
import lombok.Generated;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class Event3DRender extends Event {
   private final MatrixStack matrices;
   private final Matrix4f positionMatrix;
   private final Matrix4f projectionMatrix;
   private final Camera camera;
   private final float tickDelta;

   @Generated
   public MatrixStack getMatrices() {
      return this.matrices;
   }

   @Generated
   public Matrix4f getPositionMatrix() {
      return this.positionMatrix;
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
   public float getTickDelta() {
      return this.tickDelta;
   }

   @Generated
   public Event3DRender(MatrixStack matrixStack1, Matrix4f matrix4f2, Matrix4f matrix4f3, Camera camera4, float floatValue5) {
      this.matrices = matrixStack1;
      this.positionMatrix = matrix4f2;
      this.projectionMatrix = matrix4f3;
      this.camera = camera4;
      this.tickDelta = floatValue5;
   }
}
