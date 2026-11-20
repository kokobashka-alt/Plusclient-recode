package dev.leg1tka.modules.player;


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
import dev.leg1tka.api.events.implement.EventBinding;
import dev.leg1tka.api.events.implement.EventMoveInput;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventUpdate;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;

public class WindHop extends Module {
   public static final WindHop c = new WindHop();
   private final BooleanSetting d = new BooleanSetting(
      "Поворачивать голову вниз", true
   );
   private int e = -1;

   public WindHop() {
      super(
         "WindHop",
         "Автоматически прыгает после использования заряда ветра",
         Category.PLAYER
      );
      this.addSettings(new Setting[]{this.d});
   }

   public BooleanSetting p() {
      return this.d;
   }

   @Override
   public void onDisable() {
      this.e = -1;
      super.onDisable();
   }

   @EventLink
   public void onBinding(EventBinding eventBinding1) {
      if (minecraftClient.player != null && this.d.getValue()) {
         FTHelper fTHelper2 = FTHelper.c;
         if (fTHelper2 != null && fTHelper2.isEnabled() && fTHelper2.r() != null && eventBinding1.getKey() == fTHelper2.r().getKey()) {
            this.q();
         }
      }
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (minecraftClient.player != null && eventPacket1.getType() == EventPacket.Type.SEND) {
         if (eventPacket1.getPacket() instanceof PlayerInteractItemC2SPacket playerInteractItemC2SPacket2 && minecraftClient.player.getStackInHand(playerInteractItemC2SPacket2.getHand()).isOf(Items.WIND_CHARGE)) {
            if (this.d.getValue()) {
               this.q();
            }

            this.e = 2;
         }
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (this.e > 0) {
         this.e--;
      }
   }

   @EventLink
   public void onMoveInput(EventMoveInput eventMoveInput1) {
      if (this.e == 0) {
         eventMoveInput1.setJump(true);
         this.e = -1;
      }
   }

   public void q() {
      if (minecraftClient.player != null) {
         float floatValue1 = (float)minecraftClient.player.age + (minecraftClient.getRenderTickCounter() != null ? minecraftClient.getRenderTickCounter().getTickDelta(false) : 0.0F);
         float floatValue2 = (float)(Math.sin((double)(floatValue1 * 0.31F)) * 6.600001001477404 + Math.sin((double)(floatValue1 * 0.73F + 1.1F)) * 0.3000001491338646);
         float floatValue3 = minecraftClient.player.getYaw() + floatValue2;
         float floatValue4 = 90.0F + floatValue2 / 2.0F;
         dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(floatValue3, floatValue4), 180.0F, 180.0F, 1, 3);
      }
   }
}
