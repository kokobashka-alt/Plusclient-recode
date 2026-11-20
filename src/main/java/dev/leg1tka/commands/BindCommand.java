package dev.leg1tka.commands;


import dev.leg1tka.modules.Module;
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
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import net.minecraft.command.CommandSource;
import org.lwjgl.glfw.GLFW;

public class BindCommand extends dev.leg1tka.commands.ClientCommand {
   public BindCommand() {
      super("bind");
   }

   @Override
   public void a(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder1) {
      literalArgumentBuilder1.then(
         this.a("add")
            .then(
               this.a("module", StringArgumentType.word())
                  .suggests((commandContext0, suggestionsBuilder1x) -> {
                     String string2 = suggestionsBuilder1x.getRemaining().toLowerCase();
                     ModuleManager.a.a().stream().map(Module::getName).filter(string1xx -> string1xx.toLowerCase().startsWith(string2)).forEach(suggestionsBuilder1x::suggest);
                     return suggestionsBuilder1x.buildFuture();
                  })
                  .then(
                     this.a("key", StringArgumentType.word())
                        .suggests((commandContext0, suggestionsBuilder1x) -> {
                           String string2 = suggestionsBuilder1x.getRemaining().toUpperCase();

                           for (Field field6 : GLFW.class.getDeclaredFields()) {
                              String string7 = field6.getName();
                              if (string7.startsWith("GLFW_KEY_")) {
                                 String string8 = string7.replace("GLFW_KEY_", "");
                                 if (string8.startsWith(string2)) {
                                    suggestionsBuilder1x.suggest(string8);
                                 }
                              }
                           }

                           if ("NONE".startsWith(string2)) {
                              suggestionsBuilder1x.suggest("NONE");
                           }

                           return suggestionsBuilder1x.buildFuture();
                        })
                        .executes(
                           commandContext1x -> {
                              String string2 = (String)commandContext1x.getArgument("module", String.class);
                              Optional optional3 = this.b(string2);
                              if (optional3.isEmpty()) {
                                 dev.leg1tka.api.utils.text.ClientChat.a("Модуль " + string2 + " не найден");
                                 return 1;
                              } else {
                                 Module module4 = (Module)optional3.get();
                                 String string5 = ((String)commandContext1x.getArgument("key", String.class)).toUpperCase();
                                 int intValue6 = this.c(string5);
                                 if (intValue6 == -1) {
                                    dev.leg1tka.api.utils.text.ClientChat.a("Клавиша " + string5 + " не найдена");
                                 } else {
                                    module4.setKeyBind(intValue6);
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "Модуль "
                                          + module4.getName()
                                          + " привязан к клавише "
                                          + string5
                                    );
                                 }

                                 return 1;
                              }
                           }
                        )
                  )
            )
      );
      literalArgumentBuilder1.then(
         this.a("remove")
            .then(
               this.a("module", StringArgumentType.word())
                  .executes(
                     commandContext1x -> {
                        String string2 = (String)commandContext1x.getArgument("module", String.class);
                        Optional optional3 = this.b(string2);
                        if (optional3.isEmpty()) {
                           dev.leg1tka.api.utils.text.ClientChat.a("Модуль " + string2 + " не найден");
                           return 1;
                        } else {
                           Module module4 = (Module)optional3.get();
                           module4.setKeyBind(-1);
                           dev.leg1tka.api.utils.text.ClientChat.a(
                              "Привязка клавиши для модуля "
                                 + module4.getName()
                                 + " удалена"
                           );
                           return 1;
                        }
                     }
                  )
            )
      );
      literalArgumentBuilder1.then(
         this.a("clear")
            .executes(
               commandContext0 -> {
                  ModuleManager.a.a().forEach(module0x -> module0x.setKeyBind(-1));
                  dev.leg1tka.api.utils.text.ClientChat.a(
                     "Все привязки клавиш удалены"
                  );
                  return 1;
               }
            )
      );
      literalArgumentBuilder1.then(
         this.a("list")
            .executes(
               commandContext0 -> {
                  List<Module> list1x = (List)ModuleManager.a.a().stream().filter(module0x -> ((Module)module0x).getKeyBind() != -1).toList();
                  if (list1x.isEmpty()) {
                     dev.leg1tka.api.utils.text.ClientChat.a("Нет привязанных модулей");
                  } else {
                     dev.leg1tka.api.utils.text.ClientChat.a(
                        "Список привязанных модулей:"
                     );

                     for (Module module3 : list1x) {
                        String string4 = dev.leg1tka.gui.components.InputBindings.a(module3.getKeyBind());
                        dev.leg1tka.api.utils.text.ClientChat.a("§7• §f" + module3.getName() + " §7-> §f" + string4);
                     }
                  }

                  return 1;
               }
            )
      );
   }

   private Optional<Module> b(String string1) {
      return ModuleManager.a.a().stream().filter(module1x -> module1x.getName().equalsIgnoreCase(string1)).findFirst();
   }

   private int c(String string1) {
      if ("NONE".equalsIgnoreCase(string1)) {
         return -1;
      } else {
         try {
            return GLFW.class.getField("GLFW_KEY_" + string1).getInt(null);
         } catch (IllegalAccessException | NoSuchFieldException reflectiveOperationException3) {
            return -1;
         }
      }
   }
}
