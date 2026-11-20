package dev.leg1tka.managers;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.Generated;

public class Storage<T> {
   private final ObjectArrayList a = new ObjectArrayList();

   public Storage() {
   }

   @Generated
   public ObjectArrayList<T> a() {
      return this.a;
   }
}
