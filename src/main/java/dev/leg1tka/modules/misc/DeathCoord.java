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
import net.minecraft.util.math.BlockPos;
public class DeathCoord extends Module {
   public static final DeathCoord c = new DeathCoord();
   private final BooleanSetting d = new BooleanSetting("Копировать в буфер", true);
   private BlockPos e = null;
   private boolean f = false;

   public DeathCoord() {
      super(
         "DeathCoord",
         "Показывает координаты смерти",
         Category.MISC
      );
      this.addSettings(new Setting[]{this.d});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.f = false;
      this.e = null;
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player == null) return;
      if (minecraftClient.player.isDead() || minecraftClient.player.getHealth() <= 0.0F) {
         if (!this.f) {
            this.f = true;
            this.e = minecraftClient.player.getBlockPos();
            String coords = createString156(this.e.getX(), this.e.getY(), this.e.getZ());
            String dim = this.p();
            dev.leg1tka.api.utils.text.ClientChat.a(createString157(coords, dim));
            if (this.d.getValue()) {
               minecraftClient.keyboard.setClipboard(createString158(this.e.getX(), this.e.getY(), this.e.getZ()));
            }
         }
      } else {
         this.f = false;
      }
   }

   private String p() {
      if (minecraftClient.world == null) {
         return "Unknown";
      } else {
         String string1 = minecraftClient.world.getRegistryKey().getValue().toString();
         if (string1.contains("overworld")) {
            return "Overworld";
         } else if (string1.contains("nether")) {
            return "Nether";
         } else {
            return string1.contains("end") ? "End" : string1;
         }
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.f = false;
      this.e = null;
   }



   private static String createString156(int intValue0, int intValue1, int intValue2) {
      return "X: " + intValue0 + " Y: " + intValue1 + " Z: " + intValue2;
   }

   private static String createString157(String string0, String string1) {
      return "§cВы умерли! §f" + string0 + " §7(" + string1 + ")";
   }

   private static String createString158(int intValue0, int intValue1, int intValue2) {
      return intValue0 + " " + intValue1 + " " + intValue2;
   }
   public static void dev_leg1tka_o_ii_initializer() {}
}
