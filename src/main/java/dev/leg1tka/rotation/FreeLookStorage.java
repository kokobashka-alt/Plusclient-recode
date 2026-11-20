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

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventLook;
import dev.leg1tka.api.events.implement.EventRotation;
import lombok.Generated;
import net.minecraft.util.math.MathHelper;

public class FreeLookStorage implements dev.leg1tka.api.ClientAccess {
   private static boolean c;
   private static float d;
   private static float e;

   public FreeLookStorage() {
      EventInvoker.register(this);
   }

   public static boolean a() {
      return c;
   }

   @EventLink
   public void onLook(EventLook eventLook1) {
      if (c) {
         this.a(eventLook1.getYaw(), eventLook1.getPitch());
         eventLook1.cancel();
      }
   }

   @EventLink
   public void onRotation(EventRotation eventRotation1) {
      if (c) {
         eventRotation1.setYaw(d);
         eventRotation1.setPitch(e);
      } else {
         d = eventRotation1.getYaw();
         e = eventRotation1.getPitch();
      }
   }

   private void a(double doubleValue1, double doubleValue3) {
      e = MathHelper.clamp((float)((double)e + doubleValue3 * 0.15), -90.0F, 90.0F);
      d = (float)((double)d + doubleValue1 * 0.15);
   }

   @Generated
   public static void a(boolean flag0) {
      c = flag0;
   }

   @Generated
   public static float b() {
      return d;
   }

   @Generated
   public static float c() {
      return e;
   }

   @Generated
   public static void a(float floatValue0) {
      d = floatValue0;
   }

   @Generated
   public static void b(float floatValue0) {
      e = floatValue0;
   }
}
