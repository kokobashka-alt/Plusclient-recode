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
import java.lang.reflect.Field;
import net.minecraft.command.CommandSource;
import org.lwjgl.glfw.GLFW;

public class MacroCommand extends dev.leg1tka.commands.ClientCommand {
   public MacroCommand() {
      super("macro");
   }

   @Override
   public void a(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder1) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder1.then(
                  this.a("add").then(this.a("name", StringArgumentType.word()).then(this.a("bind", StringArgumentType.word()).suggests((commandContext0, suggestionsBuilder1x) -> {
                     for (Field field5 : GLFW.class.getDeclaredFields()) {
                        String string6 = field5.getName();
                        if (string6.startsWith("GLFW_KEY_")) {
                           String string7 = string6.replace("GLFW_KEY_", "");
                           if (string7.startsWith(suggestionsBuilder1x.getRemaining())) {
                              suggestionsBuilder1x.suggest(string7);
                           }
                        }
                     }

                     if ("NONE".startsWith(suggestionsBuilder1x.getRemaining().toUpperCase())) {
                        suggestionsBuilder1x.suggest("NONE");
                     }

                     return suggestionsBuilder1x.buildFuture();
                  }).then(this.a("command", StringArgumentType.greedyString()).executes(commandContext0 -> {
                     String string1x = (String)commandContext0.getArgument("name", String.class);
                     String string2 = ((String)commandContext0.getArgument("bind", String.class)).toUpperCase();
                     String string3 = (String)commandContext0.getArgument("command", String.class);
                     if (AspectDLC.INSTANCE.macroStorage.a(string1x) != null) {
                        dev.leg1tka.api.utils.text.ClientChat.a(
                           "Макрос "
                              + string1x
                              + " уже существует!"
                        );
                        return 1;
                     } else {
                        try {
                           int intValue4 = "NONE".equals(string2) ? -1 : GLFW.class.getField("GLFW_KEY_" + string2).getInt(null);
                           AspectDLC.INSTANCE.macroStorage.a(new dev.leg1tka.commands.MacroBinding(string1x, string3, new dev.leg1tka.settings.KeybindSetting("bind", intValue4)));
                           dev.leg1tka.api.utils.text.ClientChat.a("Макрос " + string1x + " был добавлен!");
                        } catch (Exception exception5) {
                           dev.leg1tka.api.utils.text.ClientChat.a("Неверный бинд: " + string2);
                        }

                        return 1;
                     }
                  }))))
               ))
               .then(this.a("remove").then(this.a("name", StringArgumentType.word()).suggests((commandContext0, suggestionsBuilder1x) -> {
                  AspectDLC.INSTANCE.macroStorage.d().stream().filter(string1xx -> string1xx.startsWith(suggestionsBuilder1x.getRemaining())).forEach(suggestionsBuilder1x::suggest);
                  return suggestionsBuilder1x.buildFuture();
               }).executes(commandContext0 -> {
                  String string1x = (String)commandContext0.getArgument("name", String.class);
                  if (AspectDLC.INSTANCE.macroStorage.b()) {
                     dev.leg1tka.api.utils.text.ClientChat.a("Список макросов пуст!");
                     return 1;
                  } else {
                     dev.leg1tka.commands.MacroBinding value2 = AspectDLC.INSTANCE.macroStorage.a(string1x);
                     if (value2 == null) {
                        dev.leg1tka.api.utils.text.ClientChat.a("Макрос " + string1x + " не найден!");
                        return 1;
                     } else {
                        AspectDLC.INSTANCE.macroStorage.b(value2);
                        dev.leg1tka.api.utils.text.ClientChat.a("Макрос " + string1x + " был удалён!");
                        return 1;
                     }
                  }
               }))))
            .then(this.a("list").executes(commandContext0 -> {
               StringBuilder stringBuilder1x = new StringBuilder();
               if (AspectDLC.INSTANCE.macroStorage.d().isEmpty()) {
                  dev.leg1tka.api.utils.text.ClientChat.a("Список макросов пуст!");
               } else {
                  for (int intValue2 = 0; intValue2 < AspectDLC.INSTANCE.macroStorage.d().size(); intValue2++) {
                     stringBuilder1x.append(AspectDLC.INSTANCE.macroStorage.d().get(intValue2));
                     if (intValue2 < AspectDLC.INSTANCE.macroStorage.d().size() - 1) {
                        stringBuilder1x.append(", ");
                     }
                  }

                  stringBuilder1x.append(".");
                  dev.leg1tka.api.utils.text.ClientChat.a("Макросы: " + stringBuilder1x);
               }

               return 1;
            })))
         .then(this.a("clear").executes(commandContext0 -> {
            if (!AspectDLC.INSTANCE.macroStorage.b()) {
               AspectDLC.INSTANCE.macroStorage.a();
               dev.leg1tka.api.utils.text.ClientChat.a("Все макросы были удалены!");
            } else {
               dev.leg1tka.api.utils.text.ClientChat.a("Список макросов пуст!");
            }

            return 1;
         }));
   }
}
