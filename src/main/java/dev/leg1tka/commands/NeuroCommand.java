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
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Util;

public class NeuroCommand extends dev.leg1tka.commands.ClientCommand {
   public NeuroCommand() {
      super("neuro");
   }

   @Override
   public void a(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder1) {
      ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder1.executes(
                                       commandContext1x -> this.c()
                                    ))
                                    .then(this.a("help").executes(commandContext1x -> this.b())))
                                 .then(this.a("info").executes(commandContext1x -> this.c())))
                              .then(this.a("reload").executes(commandContext1x -> this.b(dev.leg1tka.rotation.neural.RotationDatasetRecorder.d()))))
                           .then(this.a("list").executes(commandContext1x -> this.d())))
                        .then(this.a("load").then(this.a("name", StringArgumentType.word()).suggests((commandContext0, suggestionsBuilder1x) -> {
                           dev.leg1tka.rotation.neural.RotationDatasetRecorder.e().forEach(suggestionsBuilder1x::suggest);
                           return suggestionsBuilder1x.buildFuture();
                        }).executes(commandContext1x -> this.b(dev.leg1tka.rotation.neural.RotationDatasetRecorder.a((String)commandContext1x.getArgument("name", String.class)))))))
                     .then(
                        this.a("style")
                           .executes(
                              commandContext1x -> {
                                 if (AttackAura.c != null) {
                                    AttackAura.c.aT.b();
                                 }

                                 return this.b(
                                    "§aНовый стиль будет выбран на следующей цели"
                                 );
                              }
                           )
                     ))
                  .then(
                     this.a("debug")
                        .executes(
                           commandContext1x -> {
                              dev.leg1tka.rotation.neural.RotationDatasetRecorder.e = !dev.leg1tka.rotation.neural.RotationDatasetRecorder.e;
                              return this.b(
                                 dev.leg1tka.rotation.neural.RotationDatasetRecorder.e
                                    ? "§aОтладка включена: раз в секунду статистика ведения"
                                    : "§7Отладка выключена"
                              );
                           }
                        )
                  ))
               .then(
                  this.a("rec").executes(commandContext1x -> this.b("§cЗапись отключена"))
               ))
            .then(
               this.a("mode")
                  .executes(
                     commandContext1x -> this.b(
                           "§cРежим ротации «Нейро» отключен"
                        )
                  )
            ))
         .then(this.a("open").executes(commandContext1x -> {
            File file2 = dev.leg1tka.rotation.neural.RotationDatasetRecorder.a();
            Util.getOperatingSystem().open(file2);
            return this.b("§7Папка моделей: §f" + file2.getAbsolutePath());
         }));
   }

   private int b() {
      this.b("§d.neuro §7— состояние модели");
      this.b(
         "§d.neuro reload §7— перезагрузить активную модель"
      );
      this.b("§d.neuro list §7— модели в папке");
      this.b(
         "§d.neuro load <имя> §7— выбрать модель (файл <имя>.json)"
      );
      this.b("§d.neuro style §7— новый стиль движения");
      this.b(
         "§d.neuro debug §7— статистика ведения в чат раз в секунду"
      );
      this.b(
         "§d.neuro mode §7— поставить AttackAura в режим «Нейро»"
      );
      this.b("§d.neuro rec §7— вкл/выкл запись NeuroRecorder");
      this.b(
         "§d.neuro open §7— открыть папку моделей"
      );
      return 1;
   }

   private int c() {
      dev.leg1tka.rotation.neural.RotationDatasetLoader value1 = dev.leg1tka.rotation.neural.RotationDatasetRecorder.c();
      File file2 = dev.leg1tka.rotation.neural.RotationDatasetRecorder.b();
      this.b(
         "§7Активная модель: §f"
            + dev.leg1tka.rotation.neural.RotationDatasetRecorder.f()
            + (file2.exists() ? "" : " §c(файла нет)")
      );
      if (value1 == null) {
         String string3 = dev.leg1tka.rotation.neural.RotationDatasetRecorder.g();
         this.b(
            "§cНе загружена"
               + (string3 != null ? ": " + string3 : "")
               + ". §7Положи model.json в §f"
               + dev.leg1tka.rotation.neural.RotationDatasetRecorder.a().getAbsolutePath()
         );
      } else {
         this.b(
            "§7Эпоха: §f"
               + value1.j
               + " §7, файл от §f"
               + new SimpleDateFormat("dd.MM HH:mm").format(new Date(file2.lastModified()))
         );
         if (!value1.k.isEmpty()) {
            this.b("§7Проверка: §f" + value1.k);
         }
      }

      AttackAura attackAura4 = AttackAura.c;
      if (attackAura4 != null) {
         this.b(
            "§7AttackAura: "
               + (attackAura4.isEnabled() ? "§aвкл" : "§cвыкл")
               + "§7, тип: §f"
               + attackAura4.e.g()
         );
      }

      this.b("§7Отладка: " + (dev.leg1tka.rotation.neural.RotationDatasetRecorder.e ? "§aвкл" : "§7выкл"));
      return 1;
   }

   private int d() {
      List<String> list1 = dev.leg1tka.rotation.neural.RotationDatasetRecorder.e();
      if (list1.isEmpty()) {
         return this.b(
            "§7Моделей нет. Папка: §f" + dev.leg1tka.rotation.neural.RotationDatasetRecorder.a().getAbsolutePath()
         );
      } else {
         for (String string3 : list1) {
            this.b((string3.equals(dev.leg1tka.rotation.neural.RotationDatasetRecorder.f()) ? "§a▶ " : "§7  ") + string3);
         }

         return 1;
      }
   }

   private int b(String string1) {
      dev.leg1tka.api.utils.text.ClientChat.a("[Neuro] " + string1);
      return 1;
   }
}
