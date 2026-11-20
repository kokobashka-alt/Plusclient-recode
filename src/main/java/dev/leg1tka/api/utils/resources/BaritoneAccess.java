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

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.event.events.WorldEvent;
import baritone.api.event.events.type.EventState;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.world.ClientWorld;

public final class BaritoneAccess {
   private static final boolean a = FabricLoader.getInstance().isModLoaded("baritone");

   private BaritoneAccess() {
   }

   public static boolean a() {
      return a;
   }

   public static IBaritone b() {
      if (!a) {
         return null;
      } else {
         try {
            return BaritoneAPI.getProvider().getPrimaryBaritone();
         } catch (Throwable throwable1) {
            return null;
         }
      }
   }

   public static void c() {
      IBaritone iBaritone0 = b();
      if (iBaritone0 != null) {
         try {
            iBaritone0.getPathingBehavior().cancelEverything();
         } catch (Throwable throwable2) {
         }
      }
   }

   public static boolean d() {
      IBaritone iBaritone0 = b();
      if (iBaritone0 == null) {
         return false;
      } else {
         try {
            return iBaritone0.getPathingBehavior().isPathing() || iBaritone0.getPathingControlManager().mostRecentInControl().isPresent();
         } catch (Throwable throwable2) {
            return false;
         }
      }
   }

   public static void a(ClientWorld clientWorld0) {
      IBaritone iBaritone1 = b();
      if (iBaritone1 != null) {
         try {
            iBaritone1.getPathingBehavior().cancelEverything();
            iBaritone1.getGameEventHandler().onWorldEvent(new WorldEvent(clientWorld0, EventState.PRE));
            iBaritone1.getGameEventHandler().onWorldEvent(new WorldEvent(clientWorld0, EventState.POST));
         } catch (Throwable throwable3) {
         }
      }
   }
}
