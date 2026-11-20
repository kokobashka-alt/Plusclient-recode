package dev.leg1tka.modules.movement;


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

import com.mojang.blaze3d.systems.RenderSystem;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.Event3DRender;
import dev.leg1tka.api.events.implement.EventAttackEntity;
import dev.leg1tka.api.events.implement.EventMove;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventRender;
import dev.leg1tka.api.events.implement.EventUpdate;
import dev.leg1tka.mixin.EntityInvoker;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.LookAndOnGround;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AirStuck extends Module {
   public static AirStuck c = new AirStuck();
   private static long d;
   private static double e;
   private static float f;
   private static final String[] g = new String[101];
   private final ModeSetting h = new ModeSetting("Мод", "Grim", "Grim", "SkyTime", "SpookyTime", "SpaceTime");
   private final NumberSetting i = new NumberSetting("Отпускать сек.", 4.4F, 1.0F, 10.0F, 0.1F)
      .a(() -> this.h.b("SpookyTime"));
   private final NumberSetting j = new NumberSetting("Отпускать сек.", 1.2F, 0.5F, 10.0F, 0.1F)
      .a(() -> this.h.b("SpaceTime"));
   private final BooleanSetting k = new BooleanSetting("Повороты", true).visibleWhen(() -> this.h.b("SkyTime"));
   private final BooleanSetting l = new BooleanSetting("Улучшить криты", true).visibleWhen(() -> !this.h.b("SkyTime"));
   private final BooleanSetting m = new BooleanSetting("Булава (Анти-Флаг)", true);
   private final BooleanSetting n = new BooleanSetting("Старая позиция", false);
   private final dev.leg1tka.settings.KeybindSetting o = new dev.leg1tka.settings.KeybindSetting("До земли", -1);
   private Vec3d p = Vec3d.ZERO;
   private Vec3d q = Vec3d.ZERO;
   private float r = 0.0F;
   private float s = 0.0F;
   private float t = 0.0F;
   private boolean u = false;
   private boolean v = false;
   private boolean w;
   private long x;
   private Vec3d y = Vec3d.ZERO;
   private boolean z;
   private Entity A;
   private Object B;
   private boolean C;
   private boolean D;
   private boolean E;
   private long F;
   private LivingEntity G;
   private LivingEntity H;
   private final dev.leg1tka.render.TargetBoxRenderer I = new dev.leg1tka.render.TargetBoxRenderer();
   private final float[] J = new float[]{60.0F, 224.0F, 90.0F};
   private long K;
   private long L;

   public boolean p() {
      return this.v;
   }

   public boolean q() {
      return this.isEnabled() && this.v && (this.h.b("SkyTime") || this.l.getValue()) && this.r > 0.0F;
   }

   private boolean s() {
      return this.h.b("SkyTime") && this.k.getValue();
   }

   private boolean t() {
      return this.h.b("SpookyTime") || this.h.b("SpaceTime");
   }

   private long u() {
      return (long)((this.h.b("SpaceTime") ? this.j.f() : this.i.f()) * 1000.0F);
   }

   public void b(int intValue1) {
      if (intValue1 != -1 && intValue1 == this.o.getKey() && minecraftClient.player != null) {
         if (this.isEnabled() && this.z) {
            this.setEnabled(false);
         } else if (this.isEnabled()) {
            this.v();
            this.v = false;
            this.w = false;
            this.z = true;
         } else {
            this.C = true;
            this.setEnabled(true);
         }
      }
   }

   public AirStuck() {
      super("AirStuck", "Зависает в воздухе", Category.MOVEMENT);
      this.addSettings(new Setting[]{this.h, this.i, this.j, this.k, this.l, this.m, this.n, this.o});
      this.I.d = true;
      this.I.e = 20.0F;
      this.I.f = 16.0F;
   }

   public boolean r() {
      return this.D;
   }

   @Override
   public void onEnable() {
      this.v = false;
      this.w = false;
      this.u = false;
      this.p = Vec3d.ZERO;
      this.z = this.C;
      this.C = false;
      this.A = minecraftClient.player;
      this.B = minecraftClient.world;
      if (minecraftClient.player != null) {
         this.w();
         this.q = minecraftClient.player.getVelocity();
         this.r = minecraftClient.player.fallDistance;
      }

      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.v();
      this.v = false;
      this.w = false;
      this.u = false;
      this.z = false;
      this.C = false;
      this.A = null;
      this.B = null;
      this.B();
      super.onDisable();
   }

   private void v() {
      if (minecraftClient.player != null && this.v) {
         if (this.q != null) {
            minecraftClient.player.setVelocity(this.q);
         }

         minecraftClient.player.fallDistance = this.r;
         if (this.p != null && this.p != Vec3d.ZERO) {
            minecraftClient.player.refreshPositionAfterTeleport(this.p);
         }
      }
   }

   private void w() {
      ItemStack itemStack1 = minecraftClient.player.getEquippedStack(EquipmentSlot.CHEST);
      if (itemStack1.isOf(Items.ELYTRA)) {
         int intValue2 = dev.leg1tka.api.utils.player.InventorySearch.b();
         if (intValue2 != -1) {
            this.c(intValue2);
         }
      }
   }

   private void c(int intValue1) {
      if (intValue1 >= 0 && intValue1 < 9) {
         minecraftClient.interactionManager.clickSlot(0, 6, intValue1, SlotActionType.SWAP, minecraftClient.player);
      } else {
         minecraftClient.interactionManager.clickSlot(0, intValue1, 0, SlotActionType.SWAP, minecraftClient.player);
         minecraftClient.interactionManager.clickSlot(0, 6, 0, SlotActionType.SWAP, minecraftClient.player);
         minecraftClient.interactionManager.clickSlot(0, intValue1, 0, SlotActionType.SWAP, minecraftClient.player);
      }

      minecraftClient.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(0));
   }

   @EventLink
   public void onAttack(EventAttackEntity eventAttackEntity1) {
      if (this.m.getValue() && this.isEnabled() && minecraftClient.player != null) {
         if (eventAttackEntity1.getPlayer() == minecraftClient.player) {
            if (minecraftClient.player.getMainHandStack().getItem() == Items.MACE) {
               this.setEnabled(false);
            }
         }
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null) {
         if (this.A == null) {
            this.A = minecraftClient.player;
            this.B = minecraftClient.world;
         }

         boolean flag2 = minecraftClient.player != this.A || minecraftClient.world != this.B;
         if (!flag2 && !minecraftClient.player.isDead()) {
            if (this.w) {
               this.y();
            } else if (!this.v) {
               this.y = minecraftClient.player.getVelocity();
            }

            if (this.v && !this.z && this.t() && System.currentTimeMillis() - this.x >= this.u()) {
               this.x();
            }

            this.z();
            if (this.v) {
               minecraftClient.player.setVelocity(0.0, 0.0, 0.0);
               if (this.p != null && this.p != Vec3d.ZERO) {
                  minecraftClient.player.setPosition(this.p.x, this.p.y, this.p.z);
               }

               if (this.q()) {
                  minecraftClient.player.fallDistance = Math.max(minecraftClient.player.fallDistance, this.r);
               }
            }
         } else {
            if (flag2) {
               this.v = false;
            }

            this.w = false;
            this.setEnabled(false);
         }
      }
   }

   @EventLink
   public void onMove(EventMove eventMove1) {
      if (minecraftClient.player != null) {
         if (this.z && !this.v && !this.w && this.a(eventMove1.getMovePos())) {
            this.q = minecraftClient.player.getVelocity();
            this.r = minecraftClient.player.fallDistance;
            this.p = minecraftClient.player.getPos();
            this.v = true;
            this.x = System.currentTimeMillis();
         }

         if (!this.z
            && !this.v
            && !this.w
            && minecraftClient.player.fallDistance > 0.0F
            && minecraftClient.player.getVelocity().y < 0.0
            && (minecraftClient.player.fallDistance >= 0.45F || this.a(eventMove1.getMovePos()))) {
            this.q = this.t() ? this.y : minecraftClient.player.getVelocity();
            this.r = minecraftClient.player.fallDistance;
            this.p = minecraftClient.player.getPos();
            this.v = true;
            this.x = System.currentTimeMillis();
         }

         if (this.v) {
            eventMove1.setMovePos(Vec3d.ZERO);
            minecraftClient.player.setPosition(this.p.x, this.p.y, this.p.z);
            minecraftClient.player.setVelocity(0.0, 0.0, 0.0);
            if (this.q()) {
               minecraftClient.player.fallDistance = Math.max(minecraftClient.player.fallDistance, this.r);
            }
         }
      }
   }

   private boolean a(Vec3d vec3d1) {
      if (vec3d1 == null || vec3d1.y >= 0.0 || minecraftClient.player.isOnGround() || minecraftClient.player.fallDistance <= 0.0F) {
         return false;
      } else if (!minecraftClient.player.isTouchingWater()
         && !minecraftClient.player.isInLava()
         && !minecraftClient.player.isClimbing()
         && !minecraftClient.player.isGliding()
         && !minecraftClient.player.getAbilities().flying
         && !minecraftClient.player.hasVehicle()) {
         Vec3d vec3d2 = ((EntityInvoker)minecraftClient.player).invokeAdjustMovementForCollisions(vec3d1);
         return vec3d2.y > vec3d1.y + 1.0E-7;
      } else {
         return false;
      }
   }

   private void x() {
      this.v = false;
      this.w = true;
      minecraftClient.player.setPosition(this.p.x, this.p.y, this.p.z);
      minecraftClient.player.setVelocity(this.q);
      minecraftClient.player.fallDistance = this.r;
   }

   private void y() {
      this.w = false;
      if (!minecraftClient.player.isOnGround()) {
         this.p = minecraftClient.player.getPos();
         this.q = minecraftClient.player.getVelocity();
         this.r = minecraftClient.player.fallDistance;
         this.x = System.currentTimeMillis();
         this.v = true;
      }
   }

   private void z() {
      ClientPlayNetworkHandler clientPlayNetworkHandler1 = minecraftClient.getNetworkHandler();
      boolean flag2 = this.isEnabled() && this.n.getValue() && (this.v || this.w) && clientPlayNetworkHandler1 != null;
      if (!flag2) {
         this.B();
      } else {
         if (!this.D) {
            dev.leg1tka.network.InboundPacketReplay.a(clientPlayNetworkHandler1.getConnection(), minecraftClient.player.getId());
            if (BackTrackOld.c.isEnabled()) {
               BackTrackOld.c.setEnabled(false);
            }

            dev.leg1tka.network.InboundPacketReplay.e();
            dev.leg1tka.network.InboundPacketReplay.i();
            this.D = true;
            this.E = false;
            this.G = null;
         }

         dev.leg1tka.network.InboundPacketReplay.b = false;
         if (dev.leg1tka.network.InboundPacketReplay.i()) {
            this.E = false;
         }

         LivingEntity livingEntity3 = AttackAura.c != null && AttackAura.c.isEnabled() ? AttackAura.c.E() : null;
         if (livingEntity3 != null && (!livingEntity3.isAlive() || livingEntity3.isRemoved())) {
            livingEntity3 = null;
         }

         if (livingEntity3 != this.G) {
            this.A();
            this.G = livingEntity3;
            if (livingEntity3 == null) {
               dev.leg1tka.network.InboundPacketReplay.a(-1, null);
            } else {
               dev.leg1tka.network.InboundPacketReplay.a(livingEntity3.getId(), livingEntity3.getTrackedPosition().getPos());
            }
         } else if (livingEntity3 != null) {
            this.H = livingEntity3;
            if (!dev.leg1tka.network.InboundPacketReplay.h()) {
               dev.leg1tka.network.InboundPacketReplay.b(livingEntity3.getId(), livingEntity3.getTrackedPosition().getPos());
            }

            long longValue4 = System.currentTimeMillis();
            if (!this.E) {
               if (!dev.leg1tka.network.InboundPacketReplay.h()) {
                  if (livingEntity3.getBoundingBox().squaredMagnitude(minecraftClient.player.getEyePos()) <= 7.290000000000001) {
                     dev.leg1tka.network.InboundPacketReplay.b();
                     this.E = true;
                     this.F = longValue4;
                  }
               }
            } else {
               if (!dev.leg1tka.network.InboundPacketReplay.f() || longValue4 - this.F >= 55000L) {
                  this.A();
               }
            }
         }
      }
   }

   private void A() {
      if (this.E) {
         this.E = false;
         dev.leg1tka.network.InboundPacketReplay.e();
      }
   }

   private void B() {
      if (this.D) {
         this.A();
         this.D = false;
         this.G = null;
         dev.leg1tka.network.InboundPacketReplay.a(-1, null);
      }
   }

   @EventLink
   public void onRender3D(Event3DRender event3DRender1) {
      if (minecraftClient.player != null) {
         long longValue2 = System.nanoTime();
         float floatValue4 = this.K == 0L ? 0.0F : Math.min(0.1F, (float)(longValue2 - this.K) / 1.0E9F);
         this.K = longValue2;
         LivingEntity livingEntity5 = this.H;
         Vec3d vec3d6 = dev.leg1tka.network.InboundPacketReplay.k();
         if (this.E && dev.leg1tka.network.InboundPacketReplay.j() > 0) {
            this.L = longValue2;
         }

         boolean flag7 = livingEntity5 != null && vec3d6 != null && longValue2 - this.L < 120000000L;
         if (livingEntity5 != null) {
            BackTrack.a(this.J, Math.sqrt(livingEntity5.getBoundingBox().squaredMagnitude(minecraftClient.player.getEyePos())), floatValue4);
         }

         this.I.c = BackTrack.a(this.J);
         Vec3d vec3d8 = flag7 ? vec3d6.subtract(livingEntity5.getPos()) : Vec3d.ZERO;
         this.I.a(event3DRender1.getMatrices().peek().getPositionMatrix(), event3DRender1.getCamera().getPos(), event3DRender1.getTickDelta(), livingEntity5, flag7, vec3d8, false);
      }
   }

   @EventLink
   public void onRender2D(EventRender.Default default1) {
      if (this.v && !this.z && this.t() && minecraftClient.player != null && !minecraftClient.options.hudHidden) {
         dev.leg1tka.render.font.MsdfTextRenderer value2 = dev.leg1tka.render.font.FontCache.a("sf_regular", 13);
         if (value2 != null) {
            long longValue3 = Math.max(0L, this.u() - (System.currentTimeMillis() - this.x));
            String string5 = g[(int)Math.min((long)(g.length - 1), (longValue3 + 99L) / 100L)];
            float floatValue6 = (float)minecraftClient.getWindow().getScaledWidth() / 2.0F - value2.a(string5) / 2.0F;
            float floatValue7 = (float)minecraftClient.getWindow().getScaledHeight() / 2.0F + (minecraftClient.player.getMainHandStack().isOf(Items.MACE) ? 24.0F : 12.0F);
            RenderSystem.enableBlend();
            value2.a(default1.getContext().getMatrices(), string5, floatValue6, floatValue7, -1);
            RenderSystem.disableBlend();
         }
      }
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (this.v && eventPacket1.getType() == EventPacket.Type.SEND && minecraftClient.player != null) {
         if (eventPacket1.getPacket() instanceof PlayerMoveC2SPacket playerMoveC2SPacket2) {
            eventPacket1.cancel();
            if (this.s() && playerMoveC2SPacket2.changesLook()) {
               float floatValue5 = playerMoveC2SPacket2.getYaw(minecraftClient.player.getYaw());
               float floatValue4 = MathHelper.clamp(playerMoveC2SPacket2.getPitch(minecraftClient.player.getPitch()), -90.0F, 90.0F);
               if (!this.u || Math.abs(floatValue5 - this.s) > 0.01F || Math.abs(floatValue4 - this.t) > 0.01F) {
                  this.s = floatValue5;
                  this.t = floatValue4;
                  this.u = true;
                  dev.leg1tka.network.PacketSender.a(new LookAndOnGround(floatValue5, floatValue4, minecraftClient.player.isOnGround(), playerMoveC2SPacket2.horizontalCollision()));
               }
            }
         }
      }
   }

   static {
      for (int intValue0 = 0; intValue0 < g.length; intValue0++) {
         g[intValue0] = "AirStuck " + intValue0 / 10 + "." + intValue0 % 10 + "s";
      }
   }
}
