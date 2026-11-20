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

public interface InvisibleTargetAccess {
   boolean AspectDLC$isSeeInvisiblesTarget();

   void AspectDLC$setSeeInvisiblesTarget(boolean flag1);
}
