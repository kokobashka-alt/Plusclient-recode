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
import dev.leg1tka.AspectDLC;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.command.CommandSource;

public class StaffCommand extends dev.leg1tka.commands.ClientCommand {
   public StaffCommand() {
      super("staff");
   }

   @Override
   public void a(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder1) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder1.then(
                  this.a("add")
                     .then(
                        this.a("player", StringArgumentType.word())
                           .suggests((commandContext0, suggestionsBuilder1x) -> {
                              for (PlayerListEntry playerListEntry3 : minecraftClient.getNetworkHandler().getPlayerList()) {
                                 String string4 = playerListEntry3.getProfile().getName();
                                 if (string4.toLowerCase().startsWith(suggestionsBuilder1x.getRemaining().toLowerCase())) {
                                    suggestionsBuilder1x.suggest(string4);
                                 }
                              }

                              return suggestionsBuilder1x.buildFuture();
                           })
                           .executes(
                              commandContext0 -> {
                                 String string1x = (String)commandContext0.getArgument("player", String.class);
                                 if (!AspectDLC.INSTANCE.staffStorage.c(string1x)) {
                                    AspectDLC.INSTANCE.staffStorage.a(string1x);
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "Игрок "
                                          + string1x
                                          + " добавлен в список стаффов!"
                                    );
                                 } else {
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "Игрок "
                                          + string1x
                                          + " уже в списке стаффов!"
                                    );
                                 }

                                 return 1;
                              }
                           )
                     )
               ))
               .then(
                  this.a("remove")
                     .then(
                        this.a("player", StringArgumentType.word())
                           .suggests(
                              (commandContext0, suggestionsBuilder1x) -> {
                                 AspectDLC.INSTANCE
                                    .staffStorage
                                    .c()
                                    .stream()
                                    .sorted(String::compareTo)
                                    .filter(string1xx -> string1xx.startsWith(suggestionsBuilder1x.getRemaining()))
                                    .forEach(suggestionsBuilder1x::suggest);
                                 return suggestionsBuilder1x.buildFuture();
                              }
                           )
                           .executes(
                              commandContext0 -> {
                                 String string1x = (String)commandContext0.getArgument("player", String.class);
                                 if (AspectDLC.INSTANCE.staffStorage.c(string1x)) {
                                    AspectDLC.INSTANCE.staffStorage.b(string1x);
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "Игрок "
                                          + string1x
                                          + " удалён из списка стаффов!"
                                    );
                                 } else {
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "Игрок "
                                          + string1x
                                          + " не найден в списке стаффов!"
                                    );
                                 }

                                 return 1;
                              }
                           )
                     )
               ))
            .then(this.a("list").executes(commandContext0 -> {
               StringBuilder stringBuilder1x = new StringBuilder();
               if (AspectDLC.INSTANCE.staffStorage.c().isEmpty()) {
                  dev.leg1tka.api.utils.text.ClientChat.a("Список стаффов пуст!");
               } else {
                  for (int intValue2 = 0; intValue2 < AspectDLC.INSTANCE.staffStorage.c().size(); intValue2++) {
                     stringBuilder1x.append(AspectDLC.INSTANCE.staffStorage.c().get(intValue2));
                     if (intValue2 < AspectDLC.INSTANCE.staffStorage.c().size() - 1) {
                        stringBuilder1x.append(", ");
                     }
                  }

                  stringBuilder1x.append(".");
                  dev.leg1tka.api.utils.text.ClientChat.a("Стаффы: " + stringBuilder1x);
               }

               return 1;
            })))
         .then(this.a("clear").executes(commandContext0 -> {
            if (!AspectDLC.INSTANCE.staffStorage.b()) {
               AspectDLC.INSTANCE.staffStorage.a();
               dev.leg1tka.api.utils.text.ClientChat.a("Список стаффов очищен!");
            } else {
               dev.leg1tka.api.utils.text.ClientChat.a("Список стаффов пуст!");
            }

            return 1;
         }));
   }
}
