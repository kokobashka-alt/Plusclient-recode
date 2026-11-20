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

final class SelectedSlotState {
   private int a = -1;

   SelectedSlotState() {
   }

   void a(int intValue1) {
      this.a = intValue1;
   }

   int a() {
      return this.a;
   }

   boolean b() {
      return this.a >= 0;
   }

   void c() {
      if (this.a > 0) {
         this.a--;
      } else if (this.a == 0) {
         this.a = -1;
      }
   }

   void d() {
      this.a = -1;
   }
}
