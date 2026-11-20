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

import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventTickPre;
import java.util.Random;
import java.util.function.BooleanSupplier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.Packet;
public final class NeuralRotationController implements dev.leg1tka.api.ClientAccess {
   public static int c;
   public static int d;
   private static double j;
   private dev.leg1tka.rotation.neural.RotationTrainingContext k;
   private final dev.leg1tka.rotation.neural.RotationSequenceModel l = new dev.leg1tka.rotation.neural.RotationSequenceModel(40);
   private final int[] m = new int[16];
   private final double[] n = new double[48];
   private final Random o = new Random();
   private int p = -1;
   private int q = 40;
   private Object r;
   private float[] s;
   private float[] t;
   private float[] u;
   private float[] v;
   private float[] w;
   private double x;
   private double y;
   private double z;
   private double A;
   private final BooleanSupplier B;
   public float e;
   public float f;
   public float g;
   public boolean h;
   public long i;

   public NeuralRotationController(BooleanSupplier booleanSupplier1) {
      this.B = booleanSupplier1;
   }

   public boolean a() {
      return this.k != null;
   }

   public void b() {
      this.k = new dev.leg1tka.rotation.neural.RotationTrainingContext();
      this.k.e();
      this.l.a();
      this.p = -1;
      this.q = 40;
      this.z = this.A = 0.0;
   }

   public void c() {
      this.k = null;
      this.l.a();
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      dev.leg1tka.rotation.neural.RotationTrainingContext value2 = this.k;
      if (value2 != null) {
         Packet packet3 = eventPacket1.getPacket();
         if (eventPacket1.getType() == EventPacket.Type.RECEIVE) {
            value2.a(packet3);
         } else if (minecraftClient.isOnThread() && value2.b(packet3)) {
            int intValue4 = value2.a(this.m, this.n, 144.0);
            this.l.a(value2.g.bH, this.m, this.n, intValue4);
         }
      }
   }

   @EventLink
   public void onTickPre(EventTickPre eventTickPre1) {
      boolean flag2 = minecraftClient.player != null && minecraftClient.world != null && this.B.getAsBoolean();
      if (flag2 && this.k == null) {
         this.b();
      } else if (!flag2 && this.k != null) {
         this.c();
      }

      dev.leg1tka.rotation.neural.RotationTrainingContext value3 = this.k;
      if (value3 != null) {
         value3.f();
      }
   }

   public void a(boolean flag1) {
      this.q = flag1 ? 0 : Math.min(this.q + 1, 40);
   }

   public boolean a(LivingEntity livingEntity1) {
      return this.k != null && livingEntity1 != null && this.l.b() && this.l.a(livingEntity1.getId());
   }

   public String b(LivingEntity livingEntity1) {
      if (this.k == null) {
         return "no_brain";
      } else if (!this.l.b()) {
         return "warmup";
      } else {
         return livingEntity1 != null && this.l.a(livingEntity1.getId()) ? "model" : "not_seen";
      }
   }

   private void a(dev.leg1tka.rotation.neural.RotationModelWeights value1) {
      if (this.s == null || this.s.length != value1.f || this.t.length != value1.e || this.v.length != value1.h) {
         this.s = new float[value1.f];
         this.t = new float[value1.e];
         this.u = new float[value1.e];
         this.v = new float[value1.h];
         this.w = new float[value1.h];
      }
   }

   public boolean step3(dev.leg1tka.rotation.neural.RotationModelWeights value1, LivingEntity livingEntity2, float floatValue3, float floatValue4, float floatValue5, int intValue6, dev.leg1tka.rotation.NeuralRotationController.a value7) {
      return false;
   }

   private static double a(double doubleValue0, double doubleValue2) {
      return (double)Math.round(doubleValue0 / doubleValue2) * doubleValue2;
   }

   private static double a(double doubleValue0) {
      return Math.max(1.0E-9, Math.min(0.999999999, doubleValue0));
   }

   public boolean d() {
      return this.l.i && Math.abs(this.l.f) <= 1.0 && Math.abs(this.l.g) <= 1.0;
   }

   private static double e() {
      double doubleValue0 = (Double)minecraftClient.options.getMouseSensitivity().getValue() * 0.6 + 0.2;
      return doubleValue0 * doubleValue0 * doubleValue0 * 8.0 * 0.15;
   }



   public interface a {
      double err(float floatValue1, float floatValue2);
   }
   public static void dev_leg1tka_o_hp_initializer() {}
}
