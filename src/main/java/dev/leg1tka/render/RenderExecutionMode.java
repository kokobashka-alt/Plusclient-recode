package dev.leg1tka.render;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

public enum RenderExecutionMode {
   SHADER,
   THREADS;

   private RenderExecutionMode() {
   }

   public static dev.leg1tka.render.RenderExecutionMode a(boolean flag0) {
      return flag0 ? THREADS : SHADER;
   }
}
