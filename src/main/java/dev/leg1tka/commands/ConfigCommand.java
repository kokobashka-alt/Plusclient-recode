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
import java.io.File;
import java.util.Arrays;
import net.minecraft.command.CommandSource;

public class ConfigCommand extends dev.leg1tka.commands.ClientCommand {
   public ConfigCommand() {
      super("config");
   }

   @Override
   public void a(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder1) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder1.then(
                  this.a("save")
                     .then(
                        this.a("config", StringArgumentType.word())
                           .suggests((commandContext0, suggestionsBuilder1x) -> {
                              if (AspectDLC.INSTANCE.configsDir.exists() && AspectDLC.INSTANCE.configsDir.isDirectory()) {
                                 File[] fileArray2 = AspectDLC.INSTANCE.configsDir.listFiles((file0x, string1xx) -> string1xx.toLowerCase().endsWith(".aspect"));
                                 if (fileArray2 != null) {
                                    Arrays.stream(fileArray2).map(File::getName).map(string0x -> string0x.replace(".aspect", "")).forEach(suggestionsBuilder1x::suggest);
                                 }
                              }

                              return suggestionsBuilder1x.buildFuture();
                           })
                           .executes(
                              commandContext0 -> {
                                 String string1x = (String)commandContext0.getArgument("config", String.class);

                                 try {
                                    AspectDLC.INSTANCE.configStorage.a(string1x);
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "Конфиг "
                                          + string1x
                                          + " успешно сохранён!"
                                    );
                                 } catch (Exception exception3) {
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "Ошибка при сохранении конфига "
                                          + string1x
                                          + "!"
                                    );
                                    exception3.printStackTrace();
                                 }

                                 return 1;
                              }
                           )
                     )
               ))
               .then(
                  this.a("load")
                     .then(
                        this.a("config", StringArgumentType.word())
                           .suggests((commandContext0, suggestionsBuilder1x) -> {
                              if (AspectDLC.INSTANCE.configsDir.exists() && AspectDLC.INSTANCE.configsDir.isDirectory()) {
                                 File[] fileArray2 = AspectDLC.INSTANCE.configsDir.listFiles((file0x, string1xx) -> string1xx.toLowerCase().endsWith(".aspect"));
                                 if (fileArray2 != null) {
                                    Arrays.stream(fileArray2).map(File::getName).map(string0x -> string0x.replace(".aspect", "")).forEach(suggestionsBuilder1x::suggest);
                                 }
                              }

                              return suggestionsBuilder1x.buildFuture();
                           })
                           .executes(
                              commandContext0 -> {
                                 String string1x = (String)commandContext0.getArgument("config", String.class);

                                 try {
                                    AspectDLC.INSTANCE.configStorage.b();
                                    if (!AspectDLC.INSTANCE.configStorage.b(string1x)) {
                                       dev.leg1tka.api.utils.text.ClientChat.a("Конфиг " + string1x + " не найден!");
                                       return 1;
                                    }

                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "Конфиг "
                                          + string1x
                                          + " успешно загружен!"
                                    );
                                 } catch (Exception exception3) {
                                    dev.leg1tka.api.utils.text.ClientChat.a(
                                       "Ошибка при загрузке конфига "
                                          + string1x
                                          + "!"
                                    );
                                    exception3.printStackTrace();
                                 }

                                 return 1;
                              }
                           )
                     )
               ))
            .then(this.a("list").executes(commandContext0 -> {
               File[] fileArray1x = AspectDLC.INSTANCE.configsDir.listFiles((file0x, string1xx) -> string1xx.toLowerCase().endsWith(".aspect"));
               if (fileArray1x != null && fileArray1x.length != 0) {
                  StringBuilder stringBuilder2 = new StringBuilder();

                  for (int intValue3 = 0; intValue3 < fileArray1x.length; intValue3++) {
                     String string4 = fileArray1x[intValue3].getName().replace(".aspect", "");
                     stringBuilder2.append(string4);
                     if (intValue3 < fileArray1x.length - 1) {
                        stringBuilder2.append(", ");
                     }
                  }

                  dev.leg1tka.api.utils.text.ClientChat.a("Конфиги: " + stringBuilder2);
               } else {
                  dev.leg1tka.api.utils.text.ClientChat.a("Список конфигов пуст!");
               }

               return 1;
            })))
         .then(
            this.a("dir")
               .executes(
                  commandContext0 -> {
                     try {
                        File file1x = new File(AspectDLC.INSTANCE.globalsDir, "configs");
                        if (!file1x.exists()) {
                           file1x.mkdirs();
                        }

                        new ProcessBuilder("explorer.exe", file1x.getAbsolutePath()).start();
                        dev.leg1tka.api.utils.text.ClientChat.a(
                           "Папка с конфигами открыта!"
                        );
                     } catch (Exception exception2) {
                        dev.leg1tka.api.utils.text.ClientChat.a(
                           "Ошибка при открытии папки с конфигами!"
                        );
                        exception2.printStackTrace();
                     }

                     return 1;
                  }
               )
         );
   }
}
