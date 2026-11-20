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

public class FriendCommand extends dev.leg1tka.commands.ClientCommand {
   public FriendCommand() {
      super("friend");
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
                                 if (!AspectDLC.INSTANCE.friendStorage.c(string1x)) {
                                    AspectDLC.INSTANCE.friendStorage.a(string1x);
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "Игрок "
                                          + string1x
                                          + " добавлен в друзья!"
                                    );
                                 } else {
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "Игрок "
                                          + string1x
                                          + " уже в списке друзей!"
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
                                    .friendStorage
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
                                 if (AspectDLC.INSTANCE.friendStorage.c(string1x)) {
                                    AspectDLC.INSTANCE.friendStorage.b(string1x);
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "Игрок "
                                          + string1x
                                          + " удалён из друзей!"
                                    );
                                 } else {
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "Игрок "
                                          + string1x
                                          + " не найден в списке друзей!"
                                    );
                                 }

                                 return 1;
                              }
                           )
                     )
               ))
            .then(this.a("list").executes(commandContext0 -> {
               if (AspectDLC.INSTANCE.friendStorage.c().isEmpty()) {
                  dev.leg1tka.api.utils.text.ClientChat.a("Список друзей пуст!");
               } else {
                  StringBuilder stringBuilder1x = new StringBuilder();

                  for (int intValue2 = 0; intValue2 < AspectDLC.INSTANCE.friendStorage.c().size(); intValue2++) {
                     stringBuilder1x.append(AspectDLC.INSTANCE.friendStorage.c().get(intValue2));
                     if (intValue2 < AspectDLC.INSTANCE.friendStorage.c().size() - 1) {
                        stringBuilder1x.append(", ");
                     }
                  }

                  dev.leg1tka.api.utils.text.ClientChat.a("Друзья: " + stringBuilder1x);
               }

               return 1;
            })))
         .then(this.a("clear").executes(commandContext0 -> {
            if (!AspectDLC.INSTANCE.friendStorage.b()) {
               AspectDLC.INSTANCE.friendStorage.a();
               dev.leg1tka.api.utils.text.ClientChat.a("Список друзей очищен!");
            } else {
               dev.leg1tka.api.utils.text.ClientChat.a("Список друзей пуст!");
            }

            return 1;
         }));
   }
}
