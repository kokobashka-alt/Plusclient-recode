package dev.leg1tka.api.utils.resources;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import net.minecraft.util.Identifier;

public class ResourceIdentifiers {
   public ResourceIdentifiers() {
   }

   public static Identifier a(String string0) {
      return Identifier.of(dev.leg1tka.api.utils.resources.NamespaceResolver.a(), string0);
   }
}
