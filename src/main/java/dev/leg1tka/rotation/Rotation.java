package dev.leg1tka.rotation;

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
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Rotation implements dev.leg1tka.api.ClientAccess {
   private float yaw;
   private float pitch;

   public Rotation(Entity entity1) {
      this.yaw = entity1.getYaw();
      this.pitch = entity1.getPitch();
   }

   public float a(dev.leg1tka.rotation.Rotation value1) {
      float floatValue2 = MathHelper.wrapDegrees(value1.getYaw() - this.yaw);
      float floatValue3 = value1.getPitch() - this.pitch;
      return (float)Math.hypot((double)Math.abs(floatValue2), (double)Math.abs(floatValue3));
   }

   public double b(dev.leg1tka.rotation.Rotation value1) {
      double doubleValue2 = (double)MathHelper.wrapDegrees(value1.getYaw() - this.yaw);
      double doubleValue4 = (double)MathHelper.wrapDegrees(value1.getPitch() - this.pitch);
      return Math.hypot(doubleValue2, doubleValue4);
   }

   public static Vector2f a() {
      return new Vector2f(b(), c());
   }

   public static float b() {
      return MathHelper.wrapDegrees(minecraftClient.gameRenderer.getCamera().getYaw() + (float)(minecraftClient.gameRenderer.getCamera().isThirdPerson() ? 180 : 0));
   }

   public static float c() {
      return (float)(minecraftClient.gameRenderer.getCamera().isThirdPerson() ? -1 : 1) * minecraftClient.gameRenderer.getCamera().getPitch();
   }

   public static dev.leg1tka.rotation.Rotation a(PlayerEntity playerEntity0, Entity entity1) {
      Vec3d vec3d2 = playerEntity0.getCameraPosVec(0.0F);
      Vec3d vec3d3 = entity1.getPos().add(0.0, (double)entity1.getHeight() * 0.5, 0.0);
      double doubleValue4 = vec3d3.x - vec3d2.x;
      double doubleValue6 = vec3d3.y - vec3d2.y;
      double doubleValue8 = vec3d3.z - vec3d2.z;
      double doubleValue10 = Math.sqrt(doubleValue4 * doubleValue4 + doubleValue8 * doubleValue8);
      float floatValue12 = (float)Math.toDegrees(Math.atan2(doubleValue8, doubleValue4)) - 90.0F;
      float floatValue13 = (float)(-Math.toDegrees(Math.atan2(doubleValue6, doubleValue10)));
      return new dev.leg1tka.rotation.Rotation(floatValue12, floatValue13);
   }

   public final Vec3d toDirectionVector() {
      float floatValue1 = this.pitch * (float) (Math.PI / 180.0);
      float floatValue2 = -this.yaw * (float) (Math.PI / 180.0);
      float floatValue3 = MathHelper.cos(floatValue2);
      float floatValue4 = MathHelper.sin(floatValue2);
      float floatValue5 = MathHelper.cos(floatValue1);
      float floatValue6 = MathHelper.sin(floatValue1);
      return new Vec3d((double)(floatValue4 * floatValue5), (double)(-floatValue6), (double)(floatValue3 * floatValue5));
   }

   public final Vec3d asDirectionVector() {
      return this.toDirectionVector();
   }

   @Generated
   public float getYaw() {
      return this.yaw;
   }

   @Generated
   public float getPitch() {
      return this.pitch;
   }

   @Generated
   public void setYaw(float floatValue1) {
      this.yaw = floatValue1;
   }

   @Generated
   public void setPitch(float floatValue1) {
      this.pitch = floatValue1;
   }

   @Generated
   @Override
   public boolean equals(Object object1) {
      if (object1 == this) {
         return true;
      } else if (!(object1 instanceof dev.leg1tka.rotation.Rotation value2)) {
         return false;
      } else if (!value2.canEqual((Object)this)) {
         return false;
      } else {
         return Float.compare(this.getYaw(), value2.getYaw()) != 0 ? false : Float.compare(this.getPitch(), value2.getPitch()) == 0;
      }
   }

   @Generated
   protected boolean canEqual(Object object1) {
      return object1 instanceof dev.leg1tka.rotation.Rotation;
   }

   @Generated
   @Override
   public int hashCode() {
      byte byteValue1 = 59;
      int intValue2 = 1;
      intValue2 = intValue2 * 59 + Float.floatToIntBits(this.getYaw());
      return intValue2 * 59 + Float.floatToIntBits(this.getPitch());
   }

   @Generated
   @Override
   public String toString() {
      return "Rotation(yaw=" + this.getYaw() + ", pitch=" + this.getPitch() + ")";
   }

   @Generated
   public Rotation() {
   }

   @Generated
   public Rotation(float floatValue1, float floatValue2) {
      this.yaw = floatValue1;
      this.pitch = floatValue2;
   }
}
