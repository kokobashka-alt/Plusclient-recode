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

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventInvoker;

public class StorageManager implements dev.leg1tka.api.ClientAccess {
   public StorageManager() {
   }

   public void a() {
      EventInvoker.register(this);
      this.b();
   }

   public void b() {
      AspectDLC.INSTANCE.moduleStorage = new ModuleStorage();
      AspectDLC.INSTANCE.themeStorage = new dev.leg1tka.managers.ThemeStorage();
      AspectDLC.INSTANCE.tpsCalc = new dev.leg1tka.managers.TpsCalculator();
      EventInvoker.register(AspectDLC.INSTANCE.tpsCalc);
      AspectDLC.INSTANCE.localizationStorage = new dev.leg1tka.managers.LocalizationStorage();
      AspectDLC.INSTANCE.freeLookStorage = new dev.leg1tka.rotation.FreeLookStorage();
      AspectDLC.INSTANCE.rotationStorage = new dev.leg1tka.rotation.RotationStorage();
      AspectDLC.INSTANCE.serverStorage = new dev.leg1tka.managers.ServerStorage();
      AspectDLC.INSTANCE.friendStorage = new dev.leg1tka.managers.FriendStorage();
      AspectDLC.INSTANCE.macroStorage = new dev.leg1tka.managers.MacroStorage();
      AspectDLC.INSTANCE.staffStorage = new dev.leg1tka.managers.StaffStorage();
      AspectDLC.INSTANCE.waypointStorage = new dev.leg1tka.managers.WaypointStorage();
      AspectDLC.INSTANCE.commandStorage = new dev.leg1tka.managers.CommandStorage();
      AspectDLC.INSTANCE.configStorage = new dev.leg1tka.managers.ConfigStorage();
      AspectDLC.INSTANCE.altStorage = new dev.leg1tka.managers.AltStorage();
   }
}
