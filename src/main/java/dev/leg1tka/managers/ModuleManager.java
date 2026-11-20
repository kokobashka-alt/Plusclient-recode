package dev.leg1tka.managers;


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

import java.util.List;

public class ModuleManager extends Storage<Module> implements ModuleRegistry {
   public static ModuleManager a = new ModuleManager();
   public static Chams b = Chams.c;

   public ModuleManager() {
   }

   public void b() {
      this.a(
         c,
         d,
         e,
         g,
         h,
         f,
         i,
         n,
         m,
         o,
         u,
         y,
         z,
         A,
         aI,
         j,
         l,
         q,
         r,
         s,
         D,
         E,
         F,
         L,
         B,
         C,
         I,
         J,
         v,
         M,
         N,
         O,
         P,
         ak,
         al,
         am,
         Q,
         R,
         S,
         U,
         V,
         X,
         W,
         w,
         Y,
         Z,
         aa,
         ab,
         ac,
         ad,
         aK,
         ae,
         af,
         ag,
         ah,
         ai,
         aj,
         aL,
         an,
         ao,
         ap,
         aq,
         aZ,
         ar,
         aN,
         as,
         at,
         au,
         av,
         aw,
         ax,
         ay,
         az,
         aC,
         aD,
         aO,
         aP,
         aJ,
         aE,
         aF,
         aG,
         aH,
         aQ,
         aR,
         aS,
         aT,
         aU,
         aV,
         aW,
         aX,
         aY,
         ba
      );
   }

   private void a(Module... moduleArray1) {
      this.a().addAll(List.of(moduleArray1));
   }
}
