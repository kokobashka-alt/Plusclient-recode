package dev.leg1tka.api.utils.text;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.util.List;
import lombok.Generated;

public class CornerColors {
   protected final dev.leg1tka.api.utils.text.ColorValue a;
   protected final dev.leg1tka.api.utils.text.ColorValue b;
   protected final dev.leg1tka.api.utils.text.ColorValue c;
   protected final dev.leg1tka.api.utils.text.ColorValue d;

   protected CornerColors(dev.leg1tka.api.utils.text.ColorValue value1, dev.leg1tka.api.utils.text.ColorValue value2, dev.leg1tka.api.utils.text.ColorValue value3, dev.leg1tka.api.utils.text.ColorValue value4) {
      this.a = value1;
      this.b = value2;
      this.c = value3;
      this.d = value4;
   }

   public static dev.leg1tka.api.utils.text.CornerColors a(dev.leg1tka.api.utils.text.ColorValue value0, dev.leg1tka.api.utils.text.ColorValue value1, dev.leg1tka.api.utils.text.ColorValue value2, dev.leg1tka.api.utils.text.ColorValue value3) {
      return new dev.leg1tka.api.utils.text.CornerColors(value0, value1, value2, value3);
   }

   public static dev.leg1tka.api.utils.text.CornerColors a(List<dev.leg1tka.api.utils.text.ColorValue> list0) {
      return new dev.leg1tka.api.utils.text.CornerColors((dev.leg1tka.api.utils.text.ColorValue)list0.get(0), (dev.leg1tka.api.utils.text.ColorValue)list0.get(1), (dev.leg1tka.api.utils.text.ColorValue)list0.get(2), (dev.leg1tka.api.utils.text.ColorValue)list0.get(3));
   }

   public dev.leg1tka.api.utils.text.CornerColors a() {
      return this;
   }

   public dev.leg1tka.api.utils.text.CornerColors a(float floatValue1) {
      return new dev.leg1tka.api.utils.text.CornerColors(this.a.b(floatValue1), this.b.b(floatValue1), this.c.b(floatValue1), this.d.b(floatValue1));
   }

   @Generated
   public dev.leg1tka.api.utils.text.ColorValue b() {
      return this.a;
   }

   @Generated
   public dev.leg1tka.api.utils.text.ColorValue c() {
      return this.b;
   }

   @Generated
   public dev.leg1tka.api.utils.text.ColorValue d() {
      return this.c;
   }

   @Generated
   public dev.leg1tka.api.utils.text.ColorValue e() {
      return this.d;
   }
}
