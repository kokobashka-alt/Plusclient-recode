package dev.leg1tka.accounts;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.mixin.IMinecraftClientAccessor;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import net.minecraft.client.session.Session.AccountType;

public class AltSessionManager {
   private static final Random a = new Random();
   private static final String[] b = new String[]{
      "Lucky",
      "Cyber",
      "Shadow",
      "Frost",
      "Dark",
      "Ghost",
      "Neo",
      "Pixel",
      "Storm",
      "Silent",
      "Iron",
      "Silver",
      "Golden",
      "Crimson",
      "Cosmic",
      "Astral",
      "Vortex",
      "Blaze",
      "Thunder",
      "Swift",
      "Hyper",
      "Omega",
      "Alpha",
      "Epic",
      "Mystic",
      "Phantom",
      "Viper",
      "Nova",
      "Solar",
      "Lunar",
      "Echo",
      "Rogue",
      "Apex",
      "Zenith",
      "Titan",
      "Pulse",
      "Quantum",
      "Brave",
      "Wild",
      "Toxic"
   };
   private static final String[] c = new String[]{
      "Dev",
      "Knight",
      "Hunter",
      "Warrior",
      "Gamer",
      "Blade",
      "Ninja",
      "Master",
      "Wolf",
      "Hawk",
      "Falcon",
      "Fox",
      "Tiger",
      "Dragon",
      "Phoenix",
      "Reaper",
      "Striker",
      "Walker",
      "Runner",
      "Seeker",
      "Lord",
      "King",
      "Slayer",
      "Samurai",
      "Pilot",
      "Rider",
      "Wizard",
      "Sniper",
      "Agent",
      "Legend",
      "Hero",
      "Scout",
      "Panda",
      "Bear",
      "Pug",
      "Eagle",
      "Cheetah",
      "Raven",
      "Shark",
      "Cobra"
   };
   private static final int[] d = new int[]{18, 77, 99, 42, 21, 13, 88, 7, 3, 9, 69, 10, 24, 0};

   public AltSessionManager() {
   }

   public static void a(String string0) {
      if (string0 != null && !string0.trim().isEmpty()) {
         String string1 = string0.trim();
         UUID uUID2 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + string1).getBytes(StandardCharsets.UTF_8));
         Session session3 = new Session(string1, uUID2, "0", Optional.empty(), Optional.empty(), AccountType.MOJANG);
         ((IMinecraftClientAccessor)MinecraftClient.getInstance()).setSession(session3);
      }
   }

   public static void a(dev.leg1tka.accounts.AltAccount value0) {
      if (value0 != null) {
         UUID uUID1;
         try {
            uUID1 = UUID.fromString(value0.d());
         } catch (Exception exception4) {
            uUID1 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + value0.c()).getBytes(StandardCharsets.UTF_8));
         }

         String string2 = value0.e() != null && !value0.e().isEmpty() ? value0.e() : "0";
         Session session3 = new Session(value0.c(), uUID1, string2, Optional.empty(), Optional.empty(), AccountType.MOJANG);
         ((IMinecraftClientAccessor)MinecraftClient.getInstance()).setSession(session3);
      }
   }

   public static String a() {
      String string0 = b[a.nextInt(b.length)];
      String string1 = c[a.nextInt(c.length)];
      int intValue2 = d[a.nextInt(d.length)];
      return intValue2 > 0 ? string0 + string1 + intValue2 : string0 + string1;
   }
}
