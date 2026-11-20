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
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.command.CommandSource;

public class ConnectCommand extends dev.leg1tka.commands.ClientCommand {
   private static final SuggestionProvider c = (commandContext0, suggestionsBuilder1) -> {
      dev.leg1tka.network.bots.BotController.a(false).forEach(suggestionsBuilder1::suggest);
      return suggestionsBuilder1.buildFuture();
   };
   private static final SuggestionProvider d = (commandContext0, suggestionsBuilder1) -> {
      suggestionsBuilder1.suggest("all");
      dev.leg1tka.network.bots.BotController.a(false).forEach(suggestionsBuilder1::suggest);
      return suggestionsBuilder1.buildFuture();
   };

   public ConnectCommand() {
      super("bot");
   }

   @Override
   public void a(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder1) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder1.then(
                           this.a("connect").then(((RequiredArgumentBuilder)this.a("name", StringArgumentType.string()).executes(commandContext0 -> {
                              a(StringArgumentType.getString(commandContext0, "name"), (String)null);
                              return 1;
                           })).then(this.a("PotionTracker", StringArgumentType.string()).executes(commandContext0 -> {
                              a(StringArgumentType.getString(commandContext0, "name"), StringArgumentType.getString(commandContext0, "PotionTracker"));
                              return 1;
                           })))
                        ))
                        .then(this.a("control").then(this.a("name", StringArgumentType.string()).suggests(c).executes(commandContext0 -> {
                           if (b()) {
                              c(StringArgumentType.getString(commandContext0, "name"));
                           }

                           return 1;
                        }))))
                     .then(
                        this.a("return")
                           .executes(
                              commandContext0 -> {
                                 if (!b()) {
                                    return 1;
                                 } else {
                                    if (dev.leg1tka.network.bots.BotController.e()) {
                                       b(
                                          "§fДождись окончания подключения"
                                       );
                                    } else if (!dev.leg1tka.network.bots.BotController.a()) {
                                       b("§fНет фоновых сессий");
                                    }

                                    return 1;
                                 }
                              }
                           )
                     ))
                  .then(this.a("remove").then(this.a("name", StringArgumentType.string()).suggests(d).executes(commandContext0 -> {
                     if (!b()) {
                        return 1;
                     } else {
                        String string1x = StringArgumentType.getString(commandContext0, "name");
                        if (string1x.equalsIgnoreCase("all")) {
                           b("§fОтключаю всех: §a" + dev.leg1tka.network.bots.BotController.b());
                        } else if (dev.leg1tka.network.bots.BotController.b(string1x)) {
                           b("§fОтключаю: §a" + string1x);
                        } else {
                           b("§fНет фоновой сессии: " + string1x);
                        }

                        return 1;
                     }
                  }))))
               .then(
                  this.a("say")
                     .then(
                        this.a("name", StringArgumentType.string())
                           .suggests(d)
                           .then(
                              this.a("message", StringArgumentType.greedyString())
                                 .executes(
                                    commandContext0 -> {
                                       if (!b()) {
                                          return 1;
                                       } else {
                                          String string1x = StringArgumentType.getString(commandContext0, "name");
                                          String string2 = StringArgumentType.getString(commandContext0, "message");
                                          if (string1x.equalsIgnoreCase("all")) {
                                             b(
                                                "§fОтправлено от ботов: §a"
                                                   + dev.leg1tka.network.bots.BotController.c(string2)
                                             );
                                          } else if (!dev.leg1tka.network.bots.BotController.b(string1x, string2)) {
                                             b(
                                                "§fНет готовой фоновой сессии: "
                                                   + string1x
                                             );
                                          }

                                          return 1;
                                       }
                                    }
                                 )
                           )
                     )
               ))
            .then(this.a("list").executes(commandContext0 -> {
               if (b()) {
                  c();
               }

               return 1;
            })))
         .executes(
            commandContext0 -> {
               b(
                  "§f.bot connect <ник> [PotionTracker] §7— без PotionTracker на текущий сервер"
               );
               b("§f.bot control <ник> §7— переключиться");
               b("§f.bot return §7— на предыдущий ник");
               b("§f.bot remove <ник|all> §7— отключить");
               b(
                  "§f.bot say <ник|all> <текст> §7— написать от бота"
               );
               b("§f.bot list §7— список ботов");
               return 1;
            }
         );
   }

   private static boolean b() {
      if (BotManager.p()) {
         return true;
      } else {
         b("§cВключи модуль BotManager (Misc)");
         return false;
      }
   }

   private static void b(String string0) {
      dev.leg1tka.api.utils.text.ClientChat.a("§7[Bot] " + string0);
   }

   private static void a(String string0, String string1) {
      if (b()) {
         if (string1 == null) {
            ServerInfo serverInfo2 = MinecraftClient.getInstance().getCurrentServerEntry();
            if (serverInfo2 == null || serverInfo2.address == null || serverInfo2.address.isBlank()) {
               b(
                  "§cТы не на сервере — укажи PotionTracker: .bot connect <ник> <PotionTracker>"
               );
               return;
            }

            string1 = serverInfo2.address;
         }

         b("§fПодключаю §a" + string0 + " §7-> " + string1);
         dev.leg1tka.network.bots.BotController.a(string0, string1);
      }
   }

   private static void c(String string0) {
      if (string0.equalsIgnoreCase(dev.leg1tka.network.bots.BotController.c()) && MinecraftClient.getInstance().player != null) {
         b("§fТы уже управляешь: " + string0);
      } else if (dev.leg1tka.network.bots.BotController.e()) {
         b(
            "§fДождись окончания подключения"
         );
      } else {
         if (!dev.leg1tka.network.bots.BotController.a(string0)) {
            b("§fНет фоновой сессии: " + string0);
         }
      }
   }

   private static void c() {
      MinecraftClient minecraftClient0 = MinecraftClient.getInstance();
      if (minecraftClient0.player != null) {
         ServerInfo serverInfo1 = minecraftClient0.getCurrentServerEntry();
         a("§a▸ ", dev.leg1tka.network.bots.BotController.c(), serverInfo1 != null ? serverInfo1.address : "-", dev.leg1tka.network.bots.BotController.a(minecraftClient0.world, minecraftClient0.player));
      }

      List<dev.leg1tka.network.bots.BotSession> list5 = dev.leg1tka.network.bots.BotController.d();
      if (list5.isEmpty()) {
         b("§fФоновых ботов нет");
      } else {
         for (dev.leg1tka.network.bots.BotSession value3 : list5) {
            String string4 = value3.f() ? dev.leg1tka.network.bots.BotController.a(value3.d(), value3.c()) : null;
            a("§7• ", value3.a(), value3.b(), value3.f() ? string4 : "§eзаходит...");
         }
      }
   }

   private static void a(String string0, String string1, String string2, String string3) {
      dev.leg1tka.api.utils.text.ClientChat.a(
         "§7[Bot] "
            + string0
            + "§f"
            + string1
            + " §8| §7"
            + string2
            + " §8| §b"
            + (string3 != null ? string3 : "Неизвестно")
      );
   }
}
