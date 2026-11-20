package dev.leg1tka.managers;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventPacket;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.util.math.MathHelper;

public class TpsCalculator {
   private float a = 20.0F;
   private float b = 0.0F;
   private long c;
   private long d;
   private static final int e = 20;
   private final float[] f = new float[20];
   private int g = 0;

   public TpsCalculator() {
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (eventPacket1.getType() == EventPacket.Type.RECEIVE && eventPacket1.getPacket() instanceof WorldTimeUpdateS2CPacket) {
         this.g();
      }
   }

   public float a() {
      if (this.d == 0L) {
         return this.a;
      } else {
         MinecraftClient minecraftClient1 = MinecraftClient.getInstance();
         return minecraftClient1 != null && minecraftClient1.getNetworkHandler() != null && System.currentTimeMillis() - this.d <= 3500L ? this.a : 20.0F;
      }
   }

   private void g() {
      long longValue1 = System.nanoTime();
      this.d = System.currentTimeMillis();
      if (this.c == 0L) {
         this.c = longValue1;
      } else {
         long longValue3 = longValue1 - this.c;
         this.c = longValue1;
         if (longValue3 > 0L) {
            float floatValue5 = 20.0F;
            float floatValue6 = floatValue5 * (1.0E9F / (float)longValue3);
            float floatValue7 = MathHelper.clamp(floatValue6, 0.0F, floatValue5);
            this.f[this.g % 20] = floatValue7;
            this.g++;
            int intValue8 = Math.min(this.g, 20);
            float floatValue9 = 0.0F;

            for (int intValue10 = 0; intValue10 < intValue8; intValue10++) {
               float floatValue11 = this.f[intValue10];
               floatValue9 += floatValue11;
            }

            this.a = (float)this.a((double)(floatValue9 / (float)intValue8));
            this.b = this.a - floatValue5;
         }
      }
   }

   public double a(double doubleValue1) {
      return (double)Math.round(doubleValue1 * 10.0) / 10.0;
   }

   @Generated
   public float b() {
      return this.b;
   }

   @Generated
   public long c() {
      return this.c;
   }

   @Generated
   public long d() {
      return this.d;
   }

   @Generated
   public float[] e() {
      return this.f;
   }

   @Generated
   public int f() {
      return this.g;
   }
}
