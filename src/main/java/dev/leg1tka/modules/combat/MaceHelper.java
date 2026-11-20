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

import com.mojang.blaze3d.systems.RenderSystem;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventAttackEntity;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventRender;
import dev.leg1tka.api.events.implement.EventUpdate;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.CooldownUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.Box;

public class MaceHelper extends Module {
   public static MaceHelper c = new MaceHelper();
   private static int d;
   private static int e;
   private static int f;
   private static float g;
   private final BooleanSetting h = new BooleanSetting("Индикатор", true);
   private volatile int i = 20;
   private volatile int j = Integer.MIN_VALUE;
   private volatile boolean k;
   private boolean l;
   private int m = -1;
   private int n = -1;
   private int o = -1;
   private int p = -1;
   private float q;
   private int r;

   public MaceHelper() {
      super(
         "MaceHelper",
         "Заранее берёт булаву при падении и вихре, не бьёт в КД",
         Category.COMBAT
      );
      this.addSettings(new Setting[]{this.h});
   }

   @Override
   public void onDisable() {
      this.k = false;
      this.r = 0;
      this.l = false;
      this.m = -1;
      this.n = -1;
      this.p = -1;
      super.onDisable();
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (minecraftClient.player != null) {
         if (eventPacket1.getType() == EventPacket.Type.SEND) {
            if (eventPacket1.getPacket() instanceof UpdateSelectedSlotC2SPacket updateSelectedSlotC2SPacket3) {
               int intValue7 = updateSelectedSlotC2SPacket3.getSelectedSlot();
               if (intValue7 != this.o && intValue7 >= 0 && intValue7 < 9 && minecraftClient.player.getInventory().getStack(intValue7).isOf(Items.MACE)) {
                  this.j = minecraftClient.player.age;
               }

               this.o = intValue7;
            }

            if (eventPacket1.getPacket() instanceof PlayerInteractItemC2SPacket playerInteractItemC2SPacket5 && minecraftClient.player.getStackInHand(playerInteractItemC2SPacket5.getHand()).isOf(Items.WIND_CHARGE)) {
               this.k = true;
            }
         } else if (eventPacket1.getPacket() instanceof CooldownUpdateS2CPacket cooldownUpdateS2CPacket2
            && cooldownUpdateS2CPacket2.cooldown() > 0
            && Registries.ITEM.get(cooldownUpdateS2CPacket2.cooldownGroup()) == Items.MACE
            && minecraftClient.player.age - this.j <= 10) {
            this.i = cooldownUpdateS2CPacket2.cooldown();
         }
      }
   }

   @EventLink(
      priority = -200
   )
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         ClientPlayerEntity clientPlayerEntity2 = minecraftClient.player;
         if (this.k) {
            this.k = false;
            this.r = 12;
            this.l = false;
         }

         if (this.r > 0) {
            this.r--;
            if (!clientPlayerEntity2.getMainHandStack().isOf(Items.MACE)) {
               this.a(clientPlayerEntity2);
            }

            if (!clientPlayerEntity2.isOnGround()) {
               this.d(clientPlayerEntity2);
            }
         } else {
            int intValue3 = clientPlayerEntity2.getInventory().selectedSlot;
            boolean flag4 = clientPlayerEntity2.getMainHandStack().isOf(Items.MACE);
            if (this.m != -1 && intValue3 != this.m && !clientPlayerEntity2.isOnGround()) {
               this.l = true;
            }

            if (clientPlayerEntity2.isOnGround()) {
               this.p = -1;
               this.q = 0.0F;
               this.m = -1;
               this.n = -1;
               this.l = false;
            } else {
               this.d(clientPlayerEntity2);
               if (!flag4 && !this.l && this.c(clientPlayerEntity2)) {
                  if (!(this.q < 3.5F)) {
                     this.a(clientPlayerEntity2);
                  }
               }
            }
         }
      }
   }

   @EventLink(
      priority = 100
   )
   public void onAttack(EventAttackEntity eventAttackEntity1) {
      if (this.q()) {
         eventAttackEntity1.cancel();
      }
   }

   public boolean p() {
      return this.isEnabled() && this.q();
   }

   private boolean q() {
      if (minecraftClient.player == null) {
         return false;
      } else {
         ItemStack itemStack1 = minecraftClient.player.getMainHandStack();
         return !itemStack1.isOf(Items.MACE) ? false : minecraftClient.player.getItemCooldownManager().isCoolingDown(itemStack1);
      }
   }

   private void a(PlayerEntity playerEntity1) {
      int intValue2 = this.b(playerEntity1);
      if (intValue2 != -1) {
         int intValue3 = playerEntity1.getInventory().selectedSlot;
         this.m = intValue2;
         if (intValue2 != intValue3) {
            if (this.n == -1 && !playerEntity1.getInventory().getStack(intValue3).isOf(Items.WIND_CHARGE)) {
               this.n = intValue3;
            }

            playerEntity1.getInventory().selectedSlot = intValue2;
         }
      }
   }

   private int b(PlayerEntity playerEntity1) {
      for (int intValue2 = 0; intValue2 < 9; intValue2++) {
         if (playerEntity1.getInventory().getStack(intValue2).isOf(Items.MACE)) {
            return intValue2;
         }
      }

      return -1;
   }

   private boolean c(PlayerEntity playerEntity1) {
      return !playerEntity1.isGliding()
         && !playerEntity1.isTouchingWater()
         && !playerEntity1.isInLava()
         && !playerEntity1.isClimbing()
         && !playerEntity1.getAbilities().flying
         && !playerEntity1.isUsingItem()
         && !playerEntity1.hasStatusEffect(StatusEffects.LEVITATION)
         && !playerEntity1.hasStatusEffect(StatusEffects.SLOW_FALLING);
   }

   private void d(PlayerEntity playerEntity1) {
      double doubleValue2 = playerEntity1.hasNoGravity() ? 0.0 : playerEntity1.getFinalGravity();
      double doubleValue4 = playerEntity1.getVelocity().y;
      double doubleValue6 = 0.0;
      double doubleValue8 = 0.0;
      Box box10 = playerEntity1.getBoundingBox();
      this.p = -1;
      this.q = 0.0F;

      for (int intValue11 = 1; intValue11 <= 100; intValue11++) {
         if (!minecraftClient.world.isSpaceEmpty(playerEntity1, box10.offset(0.0, doubleValue6 + doubleValue4, 0.0))) {
            if (!(doubleValue4 > 0.0)) {
               this.p = intValue11;
               float floatValue12 = playerEntity1.getVelocity().y < 0.0 ? playerEntity1.fallDistance : 0.0F;
               this.q = floatValue12 + (float)(doubleValue8 - doubleValue6);
               return;
            }

            doubleValue4 = (0.0 - doubleValue2) * 0.98;
         } else {
            doubleValue6 += doubleValue4;
            if (doubleValue6 > doubleValue8) {
               doubleValue8 = doubleValue6;
            }

            doubleValue4 = (doubleValue4 - doubleValue2) * 0.98;
            if (playerEntity1.getY() + doubleValue6 < (double)minecraftClient.world.getBottomY()) {
               return;
            }
         }
      }
   }

   private float a(float floatValue1, int intValue2) {
      float floatValue3 = floatValue1 <= 3.0F ? 4.0F * floatValue1 : (floatValue1 <= 8.0F ? 12.0F + 2.0F * (floatValue1 - 3.0F) : 22.0F + (floatValue1 - 8.0F));
      return floatValue3 + 0.5F * (float)intValue2 * floatValue1;
   }

   @EventLink(
      priority = 100
   )
   public void onRender2D(EventRender.Default default1) {
      if (this.h.getValue() && minecraftClient.player != null && !minecraftClient.options.hudHidden) {
         ItemStack itemStack2 = minecraftClient.player.getMainHandStack();
         if (itemStack2.isOf(Items.MACE)) {
            String string3;
            if (this.q()) {
               float floatValue4 = minecraftClient.player.getItemCooldownManager().getCooldownProgress(itemStack2, 0.0F) * (float)this.i / 20.0F;
               string3 = "КД " + a(Math.max(0.0F, floatValue4)) + "с";
            } else {
               if (minecraftClient.player.isOnGround()) {
                  return;
               }

               float floatValue7 = this.p >= 0 ? this.q : minecraftClient.player.fallDistance;
               if (floatValue7 <= 1.5F) {
                  return;
               }

               string3 = "+" + a(this.a(floatValue7, dev.leg1tka.api.utils.player.ItemAttributes.a(Enchantments.DENSITY, itemStack2)));
            }

            dev.leg1tka.render.font.MsdfTextRenderer value8 = dev.leg1tka.render.font.FontCache.a("sf_regular", 13);
            if (value8 != null) {
               float floatValue5 = (float)minecraftClient.getWindow().getScaledWidth() / 2.0F - value8.a(string3) / 2.0F;
               float floatValue6 = (float)minecraftClient.getWindow().getScaledHeight() / 2.0F + 12.0F;
               RenderSystem.enableBlend();
               value8.a(default1.getContext().getMatrices(), string3, floatValue5, floatValue6, -1);
               RenderSystem.disableBlend();
            }
         }
      }
   }

   private static String a(float floatValue0) {
      int intValue1 = Math.round(floatValue0 * 10.0F);
      return intValue1 / 10 + "." + Math.abs(intValue1 % 10);
   }
}
