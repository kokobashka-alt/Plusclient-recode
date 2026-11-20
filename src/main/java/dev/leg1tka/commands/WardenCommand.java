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
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.CommandSource;

public class WardenCommand extends dev.leg1tka.commands.ClientCommand {
   private static final SuggestionProvider c = (commandContext0, suggestionsBuilder1) -> {
      suggestionsBuilder1.suggest("all");
      dev.leg1tka.network.bots.BotController.a(true).forEach(suggestionsBuilder1::suggest);
      return suggestionsBuilder1.buildFuture();
   };

   public WardenCommand() {
      super("warden");
   }

   @Override
   public void a(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder1) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder1.then(
                  this.a("start").then(this.a("name", StringArgumentType.string()).suggests(c).executes(commandContext0 -> {
                     if (b()) {
                        b(StringArgumentType.getString(commandContext0, "name"));
                     }

                     return 1;
                  }))
               ))
               .then(this.a("stop").then(this.a("name", StringArgumentType.string()).suggests(c).executes(commandContext0 -> {
                  c(StringArgumentType.getString(commandContext0, "name"));
                  return 1;
               }))))
            .then(this.a("list").executes(commandContext0 -> {
               c();
               return 1;
            })))
         .executes(
            commandContext0 -> {
               d(
                  "§f.warden start <ник|all> §7— запустить AutoBot на аккаунте"
               );
               d("§f.warden stop <ник|all> §7— остановить");
               d(
                  "§f.warden list §7— состояние всех аккаунтов"
               );
               return 1;
            }
         );
   }

   private static boolean b() {
      if (WardenHelper.c.isEnabled()) {
         return true;
      } else {
         d("§cВключи модуль WardenHelper");
         return false;
      }
   }

   private static void b(String string0) {
      List<String> list1 = string0.equalsIgnoreCase("all") ? dev.leg1tka.network.bots.BotController.a(true) : List.of(string0);
      if (list1.isEmpty()) {
         d("§fНет аккаунтов");
      } else {
         for (String string3 : list1) {
            String string4 = dev.leg1tka.network.bots.BotTaskScheduler.b(string3);
            if (string4 != null) {
               d("§f" + string3 + "§7: §c" + string4);
            }
         }
      }
   }

   private static void c(String string0) {
      List<String> list1 = string0.equalsIgnoreCase("all") ? dev.leg1tka.network.bots.BotTaskScheduler.d() : List.of(string0);
      int intValue2 = 0;

      for (String string4 : new ArrayList<>(list1)) {
         if (dev.leg1tka.network.bots.BotTaskScheduler.c(string4)) {
            intValue2++;
         }
      }

      if (intValue2 == 0) {
         d("§fНичего не запущено");
      }
   }

   private static void c() {
      MinecraftClient minecraftClient0 = MinecraftClient.getInstance();
      List<String> list1 = dev.leg1tka.network.bots.BotController.a(true);
      if (list1.isEmpty()) {
         d("§fНет аккаунтов");
      } else {
         String string2 = dev.leg1tka.network.bots.BotController.c();

         for (String string4 : list1) {
            boolean flag5 = minecraftClient0.player != null && string4.equalsIgnoreCase(string2);
            ClientPlayerEntity clientPlayerEntity6 = flag5 ? minecraftClient0.player : null;
            if (!flag5) {
               dev.leg1tka.network.bots.BotSession value7 = dev.leg1tka.network.bots.BotController.d(string4);
               clientPlayerEntity6 = value7 != null ? value7.c() : null;
            }

            dev.leg1tka.network.bots.BotLootTracker value9 = dev.leg1tka.network.bots.BotTaskScheduler.d(string4);
            String string8 = flag5 ? "§a▸ " : "§7• ";
            d(
               string8
                  + "§f"
                  + string4
                  + " §8| "
                  + (value9 == null ? "§7не запущен" : "§a" + value9.a(clientPlayerEntity6))
            );
         }
      }
   }

   private static void d(String string0) {
      dev.leg1tka.api.utils.text.ClientChat.a("§7[WardenBot] " + string0);
   }
}
