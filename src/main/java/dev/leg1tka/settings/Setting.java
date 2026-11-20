package dev.leg1tka.settings;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.AspectDLC;
import java.awt.Color;
import java.util.function.Supplier;
import lombok.Generated;

public abstract class Setting implements dev.leg1tka.api.ClientAccess {
   private final String name;
   public Supplier visibleCondition = () -> true;
   public Color highlightColor = Color.WHITE;

   public Setting(String string1) {
      this.name = string1;
   }

   public Boolean isVisible() {
      return (Boolean)this.visibleCondition.get();
   }

   public String getDisplayName() {
      return AspectDLC.INSTANCE.localizationStorage == null ? this.name : AspectDLC.INSTANCE.localizationStorage.a(this.name);
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public Color getHighlightColor() {
      return this.highlightColor;
   }
}
