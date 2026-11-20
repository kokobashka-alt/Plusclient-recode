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

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventMoveInput;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.function.Predicate;
import lombok.Generated;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.CodEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.AxeItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
public class TriggerBot extends Module {
   public static final TriggerBot c = new TriggerBot();
   private final NumberSetting d = new NumberSetting("Дистанция атаки", 3.0F, 0.0F, 6.0F, 0.05F);
   private final MultiSetting e = new MultiSetting(
      "Опции",
      new BooleanSetting("Умные криты", true),
      new BooleanSetting("Сброс спринта", true),
      new BooleanSetting("Бить через стены", false),
      new BooleanSetting("Проверка на наведение", true),
      new BooleanSetting("Отжимать щит", false),
      new BooleanSetting("Ломать щит", true)
   );
   private final MultiSetting f = new MultiSetting(
      "Таргеты",
      new BooleanSetting("Игроки", true),
      new BooleanSetting("Невидимки", true),
      new BooleanSetting("Мирные", false),
      new BooleanSetting("Мобы", true)
   );
   private LivingEntity g;
   private final dev.leg1tka.api.utils.time.Stopwatch h = new dev.leg1tka.api.utils.time.Stopwatch();
   private boolean i = false;
   private boolean j = false;
   private int k = 0;

   public TriggerBot() {
      super(
         "TriggerBot",
         "Автоматически атакует при наведении на цель",
         Category.COMBAT
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f});
   }

   @EventLink
   public void onMoveInput(EventMoveInput eventMoveInput1) {
      if (this.i) {
         eventMoveInput1.setForward(0.0F);
         this.i = false;
         this.j = true;
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player == null) return;
      this.g = this.r();
      if (this.g != null) {
         this.q();
      }
   }

   private void q() {
      if (this.u()) {
         if (this.e.a("Сброс спринта") && minecraftClient.player.isSprinting() && !this.j && !this.w()) {
            this.i = true;
         } else if (!this.e.a("Сброс спринта") || !this.j || this.k >= 1) {
            this.s();
            this.j = false;
            this.k = 0;
         }
      }
   }

   private LivingEntity r() {
      Vec3d vec3d1 = minecraftClient.player.getCameraPosVec(1.0F);
      Vec3d vec3d2 = minecraftClient.player.getRotationVec(1.0F);
      float floatValue3 = this.d.e().floatValue();
      Vec3d vec3d4 = vec3d1.add(vec3d2.multiply((double)floatValue3));
      EntityHitResult entityHitResult5 = ProjectileUtil.raycast(
         minecraftClient.player, vec3d1, vec3d4, minecraftClient.player.getBoundingBox().expand((double)floatValue3), createPredicate153(), (double)(floatValue3 * floatValue3)
      );
      return entityHitResult5 != null && entityHitResult5.getEntity() instanceof LivingEntity livingEntity6 && this.a(livingEntity6) ? livingEntity6 : null;
   }

   private void s() {
      if (this.e.a("Отжимать щит") && minecraftClient.player.isBlocking()) {
         minecraftClient.interactionManager.stopUsingItem(minecraftClient.player);
      }

      label19: {
         if (this.g instanceof PlayerEntity playerEntity1 && playerEntity1.isBlocking() && this.e.a("Ломать щит")) {
            this.a(playerEntity1);
            break label19;
         }

         minecraftClient.interactionManager.attackEntity(minecraftClient.player, this.g);
      }

      minecraftClient.player.swingHand(Hand.MAIN_HAND);
      this.h.reset();
   }

   private void a(PlayerEntity playerEntity1) {
      int intValue2 = this.t();
      if (intValue2 != -1) {
         int intValue3 = minecraftClient.player.getInventory().selectedSlot;
         minecraftClient.player.getInventory().selectedSlot = intValue2;
         minecraftClient.interactionManager.attackEntity(minecraftClient.player, playerEntity1);
         minecraftClient.player.swingHand(Hand.MAIN_HAND);
         minecraftClient.player.getInventory().selectedSlot = intValue3;
      } else {
         minecraftClient.interactionManager.attackEntity(minecraftClient.player, playerEntity1);
      }
   }

   private int t() {
      for (int intValue1 = 0; intValue1 < 9; intValue1++) {
         if (minecraftClient.player.getInventory().getStack(intValue1).getItem() instanceof AxeItem) {
            return intValue1;
         }
      }

      return -1;
   }

   private boolean a(LivingEntity livingEntity1) {
      if (livingEntity1 != null && livingEntity1 != minecraftClient.player) {
         if (!livingEntity1.isAlive() || livingEntity1.getHealth() <= 0.0F) {
            return false;
         } else if (livingEntity1 instanceof ArmorStandEntity) {
            return false;
         } else {
            if (livingEntity1 instanceof PlayerEntity playerEntity2) {
               if (!this.f.a("Игроки")) {
                  return false;
               }

               if (playerEntity2.hasStatusEffect(StatusEffects.INVISIBILITY) && !this.f.a("Невидимки")) {
                  return false;
               }

               if (AspectDLC.INSTANCE.friendStorage.c(livingEntity1.getName().getString())) {
                  return false;
               }
            } else if (!(livingEntity1 instanceof PassiveEntity) && !(livingEntity1 instanceof CodEntity)) {
               if (livingEntity1 instanceof HostileEntity && !this.f.a("Мобы")) {
                  return false;
               }
            } else if (!this.f.a("Мирные")) {
               return false;
            }

            return minecraftClient.player.getEyePos().distanceTo(livingEntity1.getBoundingBox().getCenter()) > (double)this.d.e().floatValue()
               ? false
               : this.e.a("Бить через стены") || minecraftClient.player.canSee(livingEntity1);
         }
      } else {
         return false;
      }
   }

   private boolean u() {
      if (minecraftClient.player.getAttackCooldownProgress(1.5F) < dev.leg1tka.api.utils.player.PlayerEnvironment.a()) {
         return false;
      } else {
         if (this.e.a("Проверка на наведение")) {
            Vec3d vec3d1 = minecraftClient.player.getCameraPosVec(1.0F);
            Vec3d vec3d2 = minecraftClient.player.getRotationVec(1.0F);
            float floatValue3 = this.d.e().floatValue();
            Vec3d vec3d4 = vec3d1.add(vec3d2.multiply((double)floatValue3));
            EntityHitResult entityHitResult5 = ProjectileUtil.raycast(
               minecraftClient.player, vec3d1, vec3d4, minecraftClient.player.getBoundingBox().expand((double)floatValue3), createPredicate154(), (double)(floatValue3 * floatValue3)
            );
            if (entityHitResult5 == null || entityHitResult5.getEntity() != this.g) {
               return false;
            }
         }

         return !this.e.a("Умные криты") || dev.leg1tka.api.utils.player.PlayerEnvironment.a(this.g);
      }
   }

   private void v() {
      this.j = false;
      this.k = 0;
   }

   private boolean w() {
      return minecraftClient.player != null && (minecraftClient.player.isTouchingWater() || minecraftClient.player.isSubmergedInWater()) && Sprint.c != null && Sprint.c.r();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.g = null;
      this.i = false;
      this.j = false;
      this.k = 0;
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.i = false;
      this.j = false;
      this.k = 0;
   }

   @Generated
   public LivingEntity p() {
      return this.g;
   }



   private static Predicate<Entity> createPredicate153() {
      return (Entity entity0) -> entity0 != minecraftClient.player && entity0.isAlive() && entity0 instanceof LivingEntity;
   }

   private static Predicate<Entity> createPredicate154() {
      return (Entity entity0) -> entity0 != minecraftClient.player && entity0.isAlive();
   }
   public static void dev_leg1tka_o_hc_initializer() {}
}
