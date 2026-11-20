package dev.leg1tka.settings;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.util.function.Supplier;
import lombok.Generated;
import net.minecraft.util.math.MathHelper;

public class NumberSetting extends Setting {
   private float c;
   private final float f;
   private final float g;
   private final float h;
   private boolean i;

   public NumberSetting(String string1, float floatValue2, float floatValue3, float floatValue4, float floatValue5) {
      super(string1);
      this.c = floatValue2;
      this.f = floatValue3;
      this.g = floatValue4;
      this.h = floatValue5;
   }

   public Number e() {
      return MathHelper.clamp(this.c, this.g(), this.h());
   }

   public void a(float floatValue1) {
      this.c = MathHelper.clamp(floatValue1, this.g(), this.h());
   }

   public float f() {
      return this.e().floatValue();
   }

   public NumberSetting a(Supplier<Boolean> supplier1) {
      this.visibleCondition = supplier1;
      return this;
   }

   @Generated
   public float g() {
      return this.f;
   }

   @Generated
   public float h() {
      return this.g;
   }

   @Generated
   public float i() {
      return this.h;
   }

   @Generated
   public boolean j() {
      return this.i;
   }

   @Generated
   public void a(boolean flag1) {
      this.i = flag1;
   }
}
