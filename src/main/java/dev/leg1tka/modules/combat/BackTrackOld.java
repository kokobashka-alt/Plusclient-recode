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
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
public class BackTrackOld extends Module {
   public static final BackTrackOld c = new BackTrackOld();
   private final NumberSetting d = new NumberSetting("Макс. дальность", 4.2F, 3.0F, 4.2F, 0.05F);
   private final NumberSetting e = new NumberSetting("Макс. окно (мс)", 200.0F, 50.0F, 200.0F, 10.0F);
   private final NumberSetting f = new NumberSetting("Дистанция аима", 8.0F, 3.0F, 8.0F, 0.5F);
   private final BooleanSetting g = new BooleanSetting("Отрисовка", true);
   private final ModeSetting h;
   private final ModeSetting i;
   private final ColorSetting j;
   private final dev.leg1tka.rotation.TargetMotionTracker k;
   private final dev.leg1tka.render.TargetBoxRenderer l;

   public BackTrackOld() {
      super(
         "BackTrack",
         "Бьёт по прошлой позиции цели",
         Category.COMBAT
      );
      ModeSetting modeSetting10001 = new ModeSetting(
         "Стиль отрисовки",
         "Скелетный",
         "Обычный",
         "Скелетный"
      );
      BooleanSetting booleanSetting10002 = this.g;
      Objects.requireNonNull(this.g);
      this.h = modeSetting10001.a(createSupplier103(booleanSetting10002));
      modeSetting10001 = new ModeSetting("Цвет", "Тема", "Тема", "Свой");
      booleanSetting10002 = this.g;
      Objects.requireNonNull(this.g);
      this.i = modeSetting10001.a(createSupplier104(booleanSetting10002));
      this.j = new ColorSetting("Свой цвет", -49056).visibleWhen(createSupplier105(this));
      this.k = new dev.leg1tka.rotation.TargetMotionTracker();
      this.l = new dev.leg1tka.render.TargetBoxRenderer();
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h, this.i, this.j});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.k.b();
      this.l.a();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.k.b();
      this.l.a();
   }

   public boolean p() {
      return this.isEnabled() && AttackAura.c != null && AttackAura.c.isEnabled();
   }

   public void a(LivingEntity livingEntity1) {
      if (this.p() && livingEntity1 != null && minecraftClient.player != null) {
         this.k.a(livingEntity1, minecraftClient.player.getYaw(), minecraftClient.player.getPitch(), this.d.f(), (long)this.e.f());
      } else {
         this.k.a();
      }
   }

   public Box a(Entity entity1) {
      if (entity1 == null) return null;
      Box box = this.p() ? this.k.b(entity1) : null;
      return box != null ? box : entity1.getBoundingBox();
   }

   public Vec3d b(Entity entity1) {
      return this.p() ? this.k.c(entity1) : Vec3d.ZERO;
   }

   public float a(Entity entity1, float floatValue2) {
      return this.p() ? this.k.e(entity1) : floatValue2;
   }

   public boolean q() {
      return this.p();
   }

   public float r() {
      return this.f.f();
   }

   public void s() {
      this.k.b();
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (eventPacket1.getPacket() != null) {
         this.k.a(eventPacket1.getPacket());
      }
   }

   @EventLink
   public void onRender3D(Event3DRender event3DRender1) {
      if (minecraftClient.player != null && minecraftClient.world != null && this.g.getValue()) {
         LivingEntity livingEntity2 = this.p() ? AttackAura.c.E() : null;
         boolean flag3 = livingEntity2 != null && this.k.a(livingEntity2);
         this.l.d = this.i.b("Свой");
         this.l.c = this.j.getColor();
         this.l
            .a(
               event3DRender1.getMatrices().peek().getPositionMatrix(),
               event3DRender1.getCamera().getPos(),
               event3DRender1.getTickDelta(),
               livingEntity2,
               flag3,
               flag3 ? this.k.d(livingEntity2) : Vec3d.ZERO,
               this.h.b("Скелетный")
            );
      }
   }



   private static Supplier createSupplier103(BooleanSetting booleanSetting0) {
      return booleanSetting0::getValue;
   }

   private static Supplier createSupplier104(BooleanSetting booleanSetting0) {
      return booleanSetting0::getValue;
   }

   private static Supplier createSupplier105(BackTrackOld backTrackOld0) {
      return () -> backTrackOld0.g.getValue() && backTrackOld0.i.b("Свой");
   }
   public static void dev_leg1tka_o_go_initializer() {}
}
