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

public class BlockEspCommand extends dev.leg1tka.commands.ClientCommand {
   public BlockEspCommand() {
      super("blockesp");
   }

   @Override
   public void a(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder1) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder1.then(
                  this.a("add")
                     .then(
                        this.a("block", StringArgumentType.word())
                           .suggests(
                              (commandContext0, suggestionsBuilder1x) -> {
                                 String string2 = suggestionsBuilder1x.getRemaining().toLowerCase();
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
                           .executes(
                              commandContext0 -> {
                                 String string1x = (String)commandContext0.getArgument("block", String.class);
                                 if (BlockESP.c.e(string1x)) {
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "§cБлок §e"
                                          + string1x
                                          + "§c уже отслеживается!"
                                    );
                                    return 1;
                                 } else {
                                    boolean flag2 = Registries.BLOCK.stream().anyMatch(block1xx -> {
                                       String string2x = Registries.BLOCK.getId(block1xx).getPath();
                                       return string2x.equalsIgnoreCase(string1x);
                                    });
                                    if (!flag2) {
                                       dev.leg1tka.api.utils.text.ClientChat.a("§cБлок §e" + string1x + "§c не найден!");
                                       return 1;
                                    } else {
                                       BlockESP.c.c(string1x);
                                       dev.leg1tka.api.utils.text.ClientChat.a(
                                          "§aБлок §e"
                                             + string1x
                                             + "§a добавлен в отслеживание!"
                                       );
                                       return 1;
                                    }
                                 }
                              }
                           )
                     )
               ))
               .then(
                  this.a("remove")
                     .then(
                        this.a("block", StringArgumentType.word())
                           .suggests((commandContext0, suggestionsBuilder1x) -> {
                              BlockESP.c
                                 .q()
                                 .stream()
                                 .sorted(String::compareTo)
                                 .filter(string1xx -> string1xx.startsWith(suggestionsBuilder1x.getRemaining().toLowerCase()))
                                 .forEach(suggestionsBuilder1x::suggest);
                              return suggestionsBuilder1x.buildFuture();
                           })
                           .executes(
                              commandContext0 -> {
                                 String string1x = (String)commandContext0.getArgument("block", String.class);
                                 if (!BlockESP.c.e(string1x)) {
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "§cБлок §e"
                                          + string1x
                                          + "§c не отслеживается!"
                                    );
                                    return 1;
                                 } else {
                                    BlockESP.c.d(string1x);
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "§aБлок §e"
                                          + string1x
                                          + "§a удалён из отслеживания!"
                                    );
                                    return 1;
                                 }
                              }
                           )
                     )
               ))
            .then(
               this.a("list")
                  .executes(
                     commandContext0 -> {
                        Set<String> set1x = BlockESP.c.q();
                        if (set1x.isEmpty()) {
                           dev.leg1tka.api.utils.text.ClientChat.a(
                              "§cСписок отслеживаемых блоков пуст!"
                           );
                           return 1;
                        } else {
                           String string2 = set1x.stream().sorted().collect(Collectors.joining("§7, §e"));
                           dev.leg1tka.api.utils.text.ClientChat.a(
                              "§aОтслеживаемые блоки §7(§e"
                                 + set1x.size()
                                 + "§7)§a: §e"
                                 + string2
                           );
                           return 1;
                        }
                     }
                  )
            ))
         .then(
            this.a("clear")
               .executes(
                  commandContext0 -> {
                     if (BlockESP.c.q().isEmpty()) {
                        dev.leg1tka.api.utils.text.ClientChat.a(
                           "§cСписок отслеживаемых блоков уже пуст!"
                        );
                        return 1;
                     } else {
                        BlockESP.c.p();
                        dev.leg1tka.api.utils.text.ClientChat.a(
                           "§aСписок отслеживаемых блоков очищен!"
                        );
                        return 1;
                     }
                  }
               )
         );
   }
}
