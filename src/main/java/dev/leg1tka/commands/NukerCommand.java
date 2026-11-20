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

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.command.CommandSource;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class NukerCommand extends dev.leg1tka.commands.ClientCommand {
   public NukerCommand() {
      super("nuker");
   }

   public NukerCommand(String string1) {
      super(string1);
   }

   @Override
   public void a(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder1) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder1.then(
                  this.a("add")
                     .then(
                        this.a("block", StringArgumentType.word())
                           .suggests(
                              (commandContext0, suggestionsBuilder1x) -> {
                                 String string2 = Nuker.f(suggestionsBuilder1x.getRemaining());
                                 Registries.BLOCK
                                    .stream()
                                    .map(Registries.BLOCK::getId)
                                    .<String>map(Identifier::getPath)
                                    .filter(string1xx -> string1xx.startsWith(string2))
                                    .limit(20L)
                                    .forEach(suggestionsBuilder1x::suggest);
                                 return suggestionsBuilder1x.buildFuture();
                              }
                           )
                           .executes(commandContext1x -> {
                              String string2 = Nuker.f((String)commandContext1x.getArgument("block", String.class));
                              if (Nuker.c.e(string2)) {
                                 dev.leg1tka.api.utils.text.ClientChat.a(
                                    "§cБлок §e"
                                       + string2
                                       + "§c уже в списке Nuker!"
                                 );
                                 return 1;
                              } else if (!this.b(string2)) {
                                 dev.leg1tka.api.utils.text.ClientChat.a("§cБлок §e" + string2 + "§c не найден!");
                                 return 1;
                              } else {
                                 Nuker.c.c(string2);
                                 dev.leg1tka.api.utils.text.ClientChat.a(
                                    "§aБлок §e" + string2 + "§a добавлен в Nuker!"
                                 );
                                 return 1;
                              }
                           })
                     )
               ))
               .then(this.a("remove").then(this.a("block", StringArgumentType.word()).suggests((commandContext0, suggestionsBuilder1x) -> {
                  String string2 = Nuker.f(suggestionsBuilder1x.getRemaining());
                  Nuker.c.q().stream().sorted(String::compareTo).filter(string1xx -> string1xx.startsWith(string2)).forEach(suggestionsBuilder1x::suggest);
                  return suggestionsBuilder1x.buildFuture();
               }).executes(commandContext0 -> {
                  String string1x = Nuker.f((String)commandContext0.getArgument("block", String.class));
                  if (!Nuker.c.e(string1x)) {
                     dev.leg1tka.api.utils.text.ClientChat.a(
                        "§cБлока §e"
                           + string1x
                           + "§c нет в списке Nuker!"
                     );
                     return 1;
                  } else {
                     Nuker.c.d(string1x);
                     dev.leg1tka.api.utils.text.ClientChat.a("§aБлок §e" + string1x + "§a удален из Nuker!");
                     return 1;
                  }
               }))))
            .then(this.a("list").executes(commandContext0 -> {
               Set<String> set1x = Nuker.c.q();
               if (set1x.isEmpty()) {
                  dev.leg1tka.api.utils.text.ClientChat.a("§cСписок Nuker пуст!");
                  return 1;
               } else {
                  String string2 = set1x.stream().sorted().collect(Collectors.joining("§7, §e"));
                  dev.leg1tka.api.utils.text.ClientChat.a("§aБлоки Nuker §7(§e" + set1x.size() + "§7)§a: §e" + string2);
                  return 1;
               }
            })))
         .then(this.a("clear").executes(commandContext0 -> {
            if (Nuker.c.q().isEmpty()) {
               dev.leg1tka.api.utils.text.ClientChat.a("§cСписок Nuker уже пуст!");
               return 1;
            } else {
               Nuker.c.p();
               dev.leg1tka.api.utils.text.ClientChat.a("§aСписок Nuker очищен!");
               return 1;
            }
         }));
   }

   private boolean b(String string1) {
      return Registries.BLOCK.stream().anyMatch(block1x -> Registries.BLOCK.getId(block1x).getPath().equalsIgnoreCase(string1));
   }
}
