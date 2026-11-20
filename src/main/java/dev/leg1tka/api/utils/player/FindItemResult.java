package dev.leg1tka.api.utils.player;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public record FindItemResult(int slot, boolean found, ItemStack stack) implements dev.leg1tka.api.ClientAccess {





   private static final dev.leg1tka.api.utils.player.FindItemResult f = new dev.leg1tka.api.utils.player.FindItemResult(-1, false, ItemStack.EMPTY);

   public static dev.leg1tka.api.utils.player.FindItemResult a() {
      return f;
   }

   @NotNull
   public static dev.leg1tka.api.utils.player.FindItemResult a(ItemStack itemStack0) {
      return new dev.leg1tka.api.utils.player.FindItemResult(999, true, itemStack0);
   }

   public boolean b() {
      return minecraftClient.player == null ? false : this.c() || minecraftClient.player.getInventory().selectedSlot == this.slot;
   }

   public boolean c() {
      return this.slot == 999;
   }

   public boolean d() {
      return this.slot >= 0 && this.slot < 9;
   }

   public void e() {
      if (this.found && this.d()) {
         dev.leg1tka.api.utils.player.ItemNames.b(this.slot);
      }
   }

   public void f() {
      if (this.found && this.d()) {
         dev.leg1tka.api.utils.player.ItemNames.c(this.slot);
      }
   }

   public int g() {
      return this.slot;
   }

   public boolean h() {
      return this.found;
   }

   public ItemStack i() {
      return this.stack;
   }
}
