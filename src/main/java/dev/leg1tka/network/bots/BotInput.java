package dev.leg1tka.network.bots;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import net.minecraft.client.input.Input;
import net.minecraft.util.PlayerInput;

public final class BotInput extends Input {
   private static final PlayerInput[] g = new PlayerInput[64];
   boolean a;
   boolean b;
   boolean c;
   boolean d;
   boolean e;
   boolean f;

   public BotInput() {
   }

   public void a() {
      this.a = false;
      this.b = false;
      this.c = false;
      this.d = false;
      this.e = false;
      this.f = false;
   }

   public void a(boolean flag1, boolean flag2, boolean flag3) {
      this.a = flag1;
      this.b = false;
      this.d = false;
      this.e = false;
      this.c = flag2;
      this.f = flag3;
   }

   public boolean b() {
      return this.f;
   }

   public void tick() {
      this.playerInput = g[(this.a ? 1 : 0) | (this.b ? 2 : 0) | (this.c ? 4 : 0) | (this.d ? 8 : 0) | (this.e ? 16 : 0) | (this.f ? 32 : 0)];
      this.movementForward = this.a == this.b ? 0.0F : (this.a ? 1.0F : -1.0F);
      this.movementSideways = this.d == this.e ? 0.0F : (this.d ? 1.0F : -1.0F);
   }

   static {
      for (int intValue0 = 0; intValue0 < g.length; intValue0++) {
         g[intValue0] = new PlayerInput((intValue0 & 1) != 0, (intValue0 & 2) != 0, (intValue0 & 8) != 0, (intValue0 & 16) != 0, (intValue0 & 4) != 0, false, (intValue0 & 32) != 0);
      }
   }
}
