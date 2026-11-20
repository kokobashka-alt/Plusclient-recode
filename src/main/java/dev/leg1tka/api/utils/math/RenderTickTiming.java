package dev.leg1tka.api.utils.math;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import java.lang.reflect.Field;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderTickCounter;

public class RenderTickTiming {
   private static Field a;
   private static Field b;
   private static Object c;
   private static boolean d;

   public RenderTickTiming() {
   }

   public static void a(float floatValue0) {
      MinecraftClient minecraftClient1 = MinecraftClient.getInstance();
      if (minecraftClient1 != null) {
         RenderTickCounter renderTickCounter2 = minecraftClient1.getRenderTickCounter();
         if (renderTickCounter2 != null) {
            try {
               a(renderTickCounter2);
               if (a != null) {
                  a.setFloat(renderTickCounter2, 50.0F / Math.max(0.05F, floatValue0));
               }

               if (b != null) {
                  if (c == null) {
                     c = b.get(renderTickCounter2);
                  }

                  FloatUnaryOperator floatUnaryOperator3 = (FloatUnaryOperator)c;
                  FloatUnaryOperator floatUnaryOperator4 = floatValue2x -> {
                     float floatValue3x = floatUnaryOperator3.apply(floatValue2x);
                     return floatValue3x / Math.max(0.05F, floatValue0);
                  };
                  b.set(renderTickCounter2, floatUnaryOperator4);
               }
            } catch (IllegalAccessException illegalAccessException5) {
            }
         }
      }
   }

   public static void a() {
      a(1.0F);
   }

   private static void a(Object object0) {
      if (!d) {
         for (Class class1 = object0.getClass(); class1 != null; class1 = class1.getSuperclass()) {
            for (Field field5 : class1.getDeclaredFields()) {
               if (field5.getType() == float.class) {
                  if (field5.getName().equals("field_51964") || field5.getName().equals("tickTime") || field5.getName().equals("msPerTick")) {
                     field5.setAccessible(true);
                     a = field5;
                  }
               } else if (field5.getType().getName().contains("FloatUnaryOperator")
                  && (field5.getName().equals("field_51965") || field5.getName().equals("targetMillisPerTick"))) {
                  field5.setAccessible(true);
                  b = field5;
               }
            }
         }

         d = true;
      }
   }
}
