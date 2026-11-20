package dev.leg1tka.modules.misc;


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
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
public class LeaveTracker extends Module {
   public static final LeaveTracker c = new LeaveTracker();
   private final Map d = new HashMap();
   private ClientWorld e;
   private boolean f;

   public LeaveTracker() {
      super(
         "LeaveTracker",
         "Пишет координаты ливнутых игроков из прогрузки",
         Category.MISC
      );
   }

   @Override
   public void onDisable() {
      this.d.clear();
      this.f = false;
      super.onDisable();
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.world == null) return;
      if (this.e != minecraftClient.world) {
         this.e = minecraftClient.world;
         this.p();
         return;
      }
      this.p();
   }

   private void p() {
      this.d.clear();

      for (PlayerEntity playerEntity2 : minecraftClient.world.getPlayers()) {
         if (playerEntity2 != minecraftClient.player && playerEntity2.isAlive()) {
            this.d.put(playerEntity2.getUuid(), new LeaveTracker.a(playerEntity2.getName().getString(), playerEntity2.getBlockPos()));
         }
      }
   }



   private static String createString172(String string0, String string1, String string2, String string3, int intValue4, int intValue5, int intValue6) {
      return string0 + string1 + string2 + " ливнул на " + string3 + intValue4 + " " + intValue5 + " " + intValue6;
   }

   static record a(String name, BlockPos pos)  {

}
   public static void dev_leg1tka_o_im_initializer() {}
}
