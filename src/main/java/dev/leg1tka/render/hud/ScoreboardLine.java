package dev.leg1tka.render.hud;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import net.minecraft.text.Text;

public class ScoreboardLine {
   public final Text a;
   public final Text b;
   public final int c;

   public ScoreboardLine(Text text1, Text text2, int intValue3) {
      this.a = text1;
      this.b = text2;
      this.c = intValue3;
   }

   public Text a() {
      return this.a;
   }

   public Text b() {
      return this.b;
   }

   public int c() {
      return this.c;
   }
}
