package dev.leg1tka.commands;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;

public class ProxyCommand extends dev.leg1tka.commands.ClientCommand {
   public ProxyCommand() {
      super("proxy");
   }

   @Override
   public void a(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder1) {
      literalArgumentBuilder1.then(this.a("start").executes(commandContext0 -> {
         dev.leg1tka.network.ProxySessionBridge.a(25565);
         return 1;
      }));
      literalArgumentBuilder1.then(this.a("stop").executes(commandContext0 -> {
         dev.leg1tka.network.ProxySessionBridge.a();
         return 1;
      }));
      literalArgumentBuilder1.then(((LiteralArgumentBuilder)this.a("swap").then(this.a("force").executes(commandContext0 -> {
         dev.leg1tka.network.ProxySessionBridge.a(true);
         return 1;
      }))).executes(commandContext0 -> {
         dev.leg1tka.network.ProxySessionBridge.a(false);
         return 1;
      }));
      literalArgumentBuilder1.then(this.a("status").executes(commandContext0 -> {
         dev.leg1tka.network.ProxySessionBridge.g();
         return 1;
      }));
      literalArgumentBuilder1.then(this.a("kick").executes(commandContext0 -> {
         dev.leg1tka.network.ProxySessionBridge.h();
         return 1;
      }));
      literalArgumentBuilder1.executes(commandContext0 -> {
         dev.leg1tka.api.utils.text.ClientChat.a("§7Использование: .proxy <start/stop/swap [force]/status/kick>");
         return 1;
      });
   }
}
