package dev.leg1tka.modules.combat;


import dev.leg1tka.modules.Module;
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
import dev.leg1tka.api.events.implement.Event3DRender;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventUpdate;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket.Handler;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class BackTrack extends Module {
   public static BackTrack c = new BackTrack();
   static int d;
   static int e;
   static int f;
   private static double g;
   private static long h;
   private final ModeSetting i = new ModeSetting("Режим", "Smooth", "Smooth", "Stand");
   private final NumberSetting j = new NumberSetting("Дистанция модели", 2.7F, 2.0F, 2.7F, 0.05F)
      .a(() -> this.i.b("Smooth"));
   private final NumberSetting k = new NumberSetting("Макс. реальная", 5.5F, 4.0F, 5.9F, 0.05F)
      .a(() -> this.i.b("Smooth"));
   private final NumberSetting l = new NumberSetting("Скорость догонки", 2.5F, 1.5F, 4.0F, 0.1F)
      .a(() -> this.i.b("Smooth"));
   private final BooleanSetting m = new BooleanSetting("Компенсация пинга", true)
      .visibleWhen(() -> this.i.b("Smooth"));
   private final NumberSetting n = new NumberSetting("Дистанция отпуска", 5.5F, 3.5F, 5.9F, 0.1F)
      .a(() -> this.i.b("Stand"));
   private final NumberSetting o = new NumberSetting("Дистанция старта", 2.7F, 2.0F, 2.7F, 0.05F)
      .a(() -> this.i.b("Stand"));
   private final NumberSetting p = new NumberSetting("Макс. удержание (сек)", 1.5F, 0.5F, 20.0F, 0.1F);
   private final BooleanSetting q = new BooleanSetting("Отпуск при отдаче", true);
   private final BooleanSetting r = new BooleanSetting("Отпуск при HP ниже", false);
   private final NumberSetting s = new NumberSetting("Порог HP", 8.0F, 1.0F, 20.0F, 0.5F).a(this.r::getValue);
   private final BooleanSetting t = new BooleanSetting("Реальная позиция", true);
   private final dev.leg1tka.render.TargetBoxRenderer u = new dev.leg1tka.render.TargetBoxRenderer();
   private LivingEntity v;
   private double w = -1.0;
   private long x;
   private boolean y;
   private long z;
   private long A;
   private long B;
   private boolean C = true;
   private long D;
   private int E;
   private final Vec3d[] F = new Vec3d[5];
   private int G;
   private int H;
   private final float[] I = new float[]{60.0F, 224.0F, 90.0F};

   public BackTrack() {
      super(
         "BackTrack",
         "Задерживает входящие пакеты, модель цели отстаёт от реальной позиции",
         Category.COMBAT
      );
      this.addSettings(new Setting[]{this.i, this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t});
      this.u.d = true;
      this.u.e = 20.0F;
      this.u.f = 16.0F;
   }

   @Override
   public void onEnable() {
      if (BackTrackOld.c.isEnabled()) {
         BackTrackOld.c.setEnabled(false);
      }

      this.p();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      if (AirStuck.c == null || !AirStuck.c.r()) {
         dev.leg1tka.network.InboundPacketReplay.e();
      }

      this.p();
      super.onDisable();
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (this.isEnabled() && minecraftClient.player != null && minecraftClient.world != null) {
         ClientPlayNetworkHandler clientPlayNetworkHandler2 = minecraftClient.getNetworkHandler();
         if (clientPlayNetworkHandler2 != null) {
            if (AirStuck.c != null && AirStuck.c.r()) {
               this.v = null;
            } else {
               dev.leg1tka.network.InboundPacketReplay.a(clientPlayNetworkHandler2.getConnection(), minecraftClient.player.getId());
               dev.leg1tka.network.InboundPacketReplay.b = this.q.getValue();
               if (BackTrackOld.c.isEnabled()) {
                  BackTrackOld.c.setEnabled(false);
               }

               boolean flag3 = this.i.b("Smooth");
               if (flag3 != this.y) {
                  dev.leg1tka.network.InboundPacketReplay.e();
                  this.y = flag3;
               } else {
                  if (dev.leg1tka.network.InboundPacketReplay.i()) {
                     this.E = 0;
                  }

                  LivingEntity livingEntity4 = AttackAura.c != null && AttackAura.c.isEnabled() ? AttackAura.c.E() : null;
                  if (livingEntity4 != null && livingEntity4.isAlive() && !livingEntity4.isRemoved()) {
                     if (livingEntity4 != this.v) {
                        if (!dev.leg1tka.network.InboundPacketReplay.h() && !dev.leg1tka.network.InboundPacketReplay.g()) {
                           this.v = livingEntity4;
                           dev.leg1tka.network.InboundPacketReplay.a(livingEntity4.getId(), livingEntity4.getTrackedPosition().getPos());
                           this.w = -1.0;
                           this.H = 0;
                           this.C = true;
                           this.E = 0;
                        } else {
                           dev.leg1tka.network.InboundPacketReplay.e();
                        }
                     } else {
                        if (!dev.leg1tka.network.InboundPacketReplay.h()) {
                           dev.leg1tka.network.InboundPacketReplay.b(livingEntity4.getId(), livingEntity4.getTrackedPosition().getPos());
                        }

                        Vec3d vec3d5 = dev.leg1tka.network.InboundPacketReplay.k();
                        if (vec3d5 != null) {
                           Vec3d vec3d6 = minecraftClient.player.getEyePos();
                           EntityDimensions entityDimensions7 = livingEntity4.getDimensions(livingEntity4.getPose());
                           double doubleValue8 = Math.sqrt(entityDimensions7.getBoxAt(vec3d5).squaredMagnitude(vec3d6));
                           if (this.y) {
                              this.a(clientPlayNetworkHandler2, vec3d6, vec3d5, entityDimensions7, doubleValue8);
                           } else {
                              this.a(clientPlayNetworkHandler2, livingEntity4, vec3d6, doubleValue8);
                           }
                        }
                     }
                  } else {
                     if (this.v != null) {
                        dev.leg1tka.network.InboundPacketReplay.e();
                     }

                     this.v = null;
                     dev.leg1tka.network.InboundPacketReplay.a(-1, null);
                     this.w = -1.0;
                  }
               }
            }
         }
      }
   }

   private void a(ClientPlayNetworkHandler clientPlayNetworkHandler1, Vec3d vec3d2, Vec3d vec3d3, EntityDimensions entityDimensions4, double doubleValue5) {
      this.F[this.G] = vec3d3;
      this.G = (this.G + 1) % this.F.length;
      if (this.H < this.F.length) {
         this.H++;
      }

      double doubleValue7 = doubleValue5 + (this.m.getValue() ? this.a(clientPlayNetworkHandler1, vec3d2, vec3d3) : 0.0);
      float floatValue9 = this.j.f();
      LivingEntity livingEntity10 = this.v;
      double doubleValue11 = livingEntity10 == null ? doubleValue5 : Math.sqrt(livingEntity10.getBoundingBox().squaredMagnitude(vec3d2));
      int intValue13 = dev.leg1tka.network.InboundPacketReplay.j();
      long longValue14 = System.currentTimeMillis();
      if (this.r.getValue() && minecraftClient.player.getHealth() < this.s.f()) {
         dev.leg1tka.network.InboundPacketReplay.e();
      } else {
         if (!this.C && (doubleValue5 <= (double)floatValue9 || longValue14 >= this.D)) {
            this.C = true;
         }

         this.E = intValue13 > 0 && doubleValue5 <= Math.max((double)floatValue9, doubleValue11) - 0.3 ? this.E + 1 : 0;
         boolean flag16 = doubleValue7 > (double)this.k.f() || !this.C || this.E >= 2;
         if (!flag16 && (float)dev.leg1tka.network.InboundPacketReplay.d() >= this.p.f() * 1000.0F) {
            this.C = false;
            this.D = longValue14 + 2000L;
            flag16 = true;
         }

         if (flag16) {
            dev.leg1tka.network.InboundPacketReplay.c();
         } else {
            dev.leg1tka.network.InboundPacketReplay.a(vec3d2, (double)entityDimensions4.width(), (double)entityDimensions4.height(), (double)floatValue9, (double)this.l.f());
         }
      }
   }

   private double a(ClientPlayNetworkHandler clientPlayNetworkHandler1, Vec3d vec3d2, Vec3d vec3d3) {
      double doubleValue4 = System.currentTimeMillis() - this.B < 300L ? 0.5 : 0.0;
      if (this.H < 2) {
         return doubleValue4;
      } else {
         int intValue6 = this.H - 1;
         Vec3d vec3d7 = this.F[(this.G - this.H + this.F.length) % this.F.length];
         Vec3d vec3d8 = vec3d3.subtract(vec3d7).multiply(1.0 / (double)intValue6);
         Vec3d vec3d9 = new Vec3d(vec3d3.x - vec3d2.x, 0.0, vec3d3.z - vec3d2.z);
         double doubleValue10 = vec3d9.length();
         double doubleValue12 = doubleValue10 < 1.0E-4 ? 0.0 : Math.max(0.0, (vec3d8.x * vec3d9.x + vec3d8.z * vec3d9.z) / doubleValue10);
         PlayerListEntry playerListEntry14 = clientPlayNetworkHandler1.getPlayerListEntry(minecraftClient.player.getUuid());
         int intValue15 = playerListEntry14 == null ? 0 : Math.max(0, playerListEntry14.getLatency());
         return doubleValue12 * ((double)intValue15 / 50.0 + 1.0) + doubleValue4;
      }
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (this.isEnabled() && eventPacket1.getType() == EventPacket.Type.SEND && minecraftClient.player != null) {
         if (eventPacket1.getPacket() instanceof PlayerInteractEntityC2SPacket playerInteractEntityC2SPacket2 && a(playerInteractEntityC2SPacket2)) {
            LivingEntity livingEntity4 = this.v;
            if (livingEntity4 != null && playerInteractEntityC2SPacket2.entityId == livingEntity4.getId()) {
               this.B = System.currentTimeMillis();
            }

            return;
         }
      }
   }

   private static boolean a(PlayerInteractEntityC2SPacket playerInteractEntityC2SPacket0) {
      final boolean[] flagArray1 = new boolean[1];
      playerInteractEntityC2SPacket0.handle(new Handler() {
         public void interact(Hand hand) {
         }

         public void interactAt(Hand hand, Vec3d pos) {
         }

         public void attack() {
            flagArray1[0] = true;
         }
      });
      return flagArray1[0];
   }

   private void a(ClientPlayNetworkHandler clientPlayNetworkHandler1, LivingEntity livingEntity2, Vec3d vec3d3, double doubleValue4) {
      double doubleValue6 = Math.sqrt(livingEntity2.getBoundingBox().squaredMagnitude(vec3d3));
      double doubleValue8 = doubleValue4 + dev.leg1tka.network.InboundPacketReplay.l() * a(clientPlayNetworkHandler1);
      boolean flag10 = this.w >= 0.0 && doubleValue4 > this.w + 0.02;
      this.w = doubleValue4;
      if (!dev.leg1tka.network.InboundPacketReplay.f()) {
         if (!dev.leg1tka.network.InboundPacketReplay.h()) {
            if (flag10 && doubleValue6 <= (double)this.o.f() && doubleValue8 < (double)this.n.f()) {
               this.x = System.currentTimeMillis();
               dev.leg1tka.network.InboundPacketReplay.b();
            }
         }
      } else {
         long longValue11 = System.currentTimeMillis() - this.x;
         if (doubleValue8 >= (double)this.n.f() || doubleValue4 + 0.2 < doubleValue6 || (float)longValue11 >= this.p.f() * 1000.0F) {
            dev.leg1tka.network.InboundPacketReplay.e();
         }
      }
   }

   @EventLink
   public void onRender3D(Event3DRender event3DRender1) {
      if (this.isEnabled() && minecraftClient.player != null) {
         long longValue2 = System.nanoTime();
         float floatValue4 = this.A == 0L ? 0.0F : MathHelper.clamp((float)(longValue2 - this.A) / 1.0E9F, 0.0F, 0.1F);
         this.A = longValue2;
         LivingEntity livingEntity5 = this.v;
         Vec3d vec3d6 = dev.leg1tka.network.InboundPacketReplay.k();
         if (dev.leg1tka.network.InboundPacketReplay.j() > 0) {
            this.z = longValue2;
         }

         boolean flag7 = this.t.getValue() && livingEntity5 != null && vec3d6 != null && longValue2 - this.z < 120000000L;
         if (livingEntity5 != null) {
            a(this.I, Math.sqrt(livingEntity5.getBoundingBox().squaredMagnitude(minecraftClient.player.getEyePos())), floatValue4);
         }

         this.u.c = a(this.I);
         Vec3d vec3d8 = flag7 ? vec3d6.subtract(livingEntity5.getPos()) : Vec3d.ZERO;
         this.u.a(event3DRender1.getMatrices().peek().getPositionMatrix(), event3DRender1.getCamera().getPos(), event3DRender1.getTickDelta(), livingEntity5, flag7, vec3d8, false);
      }
   }

   public static void a(float[] floatValueArray0, double doubleValue1, float floatValue3) {
      float floatValue4 = (float)MathHelper.clamp(doubleValue1 - 1.0, 0.0, 2.0);
      int intValue5 = floatValue4 < 1.0F ? 3989594 : 16765500;
      int intValue6 = floatValue4 < 1.0F ? 16765500 : 16728128;
      float floatValue7 = floatValue4 < 1.0F ? floatValue4 : floatValue4 - 1.0F;
      float floatValue8 = 1.0F - (float)Math.exp((double)(-floatValue3 * 18.0F));
      floatValueArray0[0] += ((float)MathHelper.lerp(floatValue7, intValue5 >> 16 & 0xFF, intValue6 >> 16 & 0xFF) - floatValueArray0[0]) * floatValue8;
      floatValueArray0[1] += ((float)MathHelper.lerp(floatValue7, intValue5 >> 8 & 0xFF, intValue6 >> 8 & 0xFF) - floatValueArray0[1]) * floatValue8;
      floatValueArray0[2] += ((float)MathHelper.lerp(floatValue7, intValue5 & 0xFF, intValue6 & 0xFF) - floatValueArray0[2]) * floatValue8;
   }

   public static int a(float[] floatValueArray0) {
      return 0xFF000000 | Math.round(floatValueArray0[0]) << 16 | Math.round(floatValueArray0[1]) << 8 | Math.round(floatValueArray0[2]);
   }

   private static double a(ClientPlayNetworkHandler clientPlayNetworkHandler0) {
      PlayerListEntry playerListEntry1 = clientPlayNetworkHandler0.getPlayerListEntry(minecraftClient.player.getUuid());
      int intValue2 = playerListEntry1 == null ? 0 : Math.max(0, playerListEntry1.getLatency());
      return (double)intValue2 / 100.0 + 1.0;
   }

   private void p() {
      this.v = null;
      this.w = -1.0;
      this.H = 0;
      this.C = true;
      this.E = 0;
      this.y = this.i.b("Smooth");
      dev.leg1tka.network.InboundPacketReplay.a(-1, null);
   }
}
