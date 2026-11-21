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

import com.mojang.brigadier.CommandDispatcher;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.command.CommandSource;

public class CommandStorage {
   private final CommandDispatcher a = new CommandDispatcher();
   private final List b = new ArrayList();
   private String c = ".";

   public CommandStorage() {
      this.e();
   }

   private void e() {
      this.a(new dev.leg1tka.commands.FriendCommand(), new dev.leg1tka.commands.HolyWorldCommand(), new dev.leg1tka.commands.ConfigCommand(), new dev.leg1tka.commands.MacroCommand(), new dev.leg1tka.commands.ConnectCommand(), new dev.leg1tka.commands.WardenCommand(), new dev.leg1tka.commands.BlockEspCommand(), new dev.leg1tka.commands.NukerCommand(), new dev.leg1tka.commands.NukerCommand("nuk"), new dev.leg1tka.commands.WaypointCommand(), new dev.leg1tka.commands.BindCommand(), new dev.leg1tka.commands.StaffCommand(), new dev.leg1tka.commands.VerticalClipCommand(), new dev.leg1tka.commands.ProxyCommand(), new dev.leg1tka.commands.NeuroCommand());
   }

   public CommandSource a() {
      return new ClientCommandSource(null, MinecraftClient.getInstance());
   }

   private void a(dev.leg1tka.commands.ClientCommand... valueArray1) {
      for (dev.leg1tka.commands.ClientCommand value5 : valueArray1) {
         value5.a(this.a);
         this.b.add(value5);
      }
   }

   @Generated
   public CommandDispatcher<CommandSource> b() {
      return this.a;
   }

   @Generated
   public List<dev.leg1tka.commands.ClientCommand> c() {
      return this.b;
   }

   @Generated
   public String d() {
      return this.c;
   }

   @Generated
   public void a(String string1) {
      this.c = string1;
   }
}
