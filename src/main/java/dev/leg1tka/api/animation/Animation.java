package dev.leg1tka.api.animation;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import net.minecraft.util.math.MathHelper;

public class Animation {
   private float value;
   private float target;
   private float startValue;
   private float speed;
   private dev.leg1tka.api.animation.EasingFunction easing;
   private long startNanos;
   private double durationSeconds;
   private boolean running;

   public Animation(float floatValue1, float floatValue2, dev.leg1tka.api.animation.EasingFunction value3) {
      this.value = floatValue1;
      this.target = floatValue1;
      this.startValue = floatValue1;
      this.speed = floatValue2;
      this.easing = value3 != null ? value3 : dev.leg1tka.api.animation.Easings.f;
      this.startNanos = 0L;
      this.durationSeconds = 0.0;
      this.running = false;
   }

   public Animation(float floatValue1, float floatValue2) {
      this(floatValue1, floatValue2, dev.leg1tka.api.animation.Easings.f);
   }

   public void animateTo(float floatValue1) {
      if (this.target != floatValue1 || !this.running) {
         this.target = floatValue1;
         this.startValue = this.value;
         this.startNanos = System.nanoTime();
         this.durationSeconds = 1.0 / (double)this.speed * 2.0;
         this.running = true;
      }

      if (this.isFinished()) {
         this.value = this.target;
         this.running = false;
      } else {
         double doubleValue2 = this.progress();
         float floatValue4 = (float)this.easing.ease(doubleValue2);
         this.value = MathHelper.lerp(floatValue4, this.startValue, this.target);
      }
   }

   private double progress() {
      if (!this.running) {
         return 1.0;
      } else {
         long longValue1 = System.nanoTime();
         double doubleValue3 = (double)(longValue1 - this.startNanos) / 1.0E9;
         return MathHelper.clamp(doubleValue3 / this.durationSeconds, 0.0, 1.0);
      }
   }

   public float getValue() {
      return this.value;
   }

   public void resetTo(float floatValue1) {
      this.value = floatValue1;
      this.target = floatValue1;
      this.startValue = floatValue1;
      this.running = false;
   }

   public float getTarget() {
      return this.target;
   }

   public void setSpeed(float floatValue1) {
      this.speed = floatValue1;
      this.durationSeconds = 1.0 / (double)floatValue1;
   }

   public void setEasing(dev.leg1tka.api.animation.EasingFunction value1) {
      this.easing = value1 != null ? value1 : dev.leg1tka.api.animation.Easings.f;
   }

   public boolean isFinished() {
      return this.progress() >= 1.0;
   }

   public boolean isRunning() {
      return !this.isFinished();
   }
}
