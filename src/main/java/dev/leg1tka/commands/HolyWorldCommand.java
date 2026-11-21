package dev.leg1tka.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.leg1tka.api.utils.text.ClientChat;
import dev.leg1tka.modules.combat.AttackAura;
import dev.leg1tka.rotation.holyworld.HolyWorldBrain;
import java.io.File;
import net.minecraft.command.CommandSource;
import net.minecraft.util.Util;

/** Controls the in-game training of the HolyWorld neural rotation: record, train, save, load. */
public class HolyWorldCommand extends dev.leg1tka.commands.ClientCommand {
    private static final int DEFAULT_EPOCHS = 40;

    public HolyWorldCommand() {
        super("holyworld");
        HolyWorldBrain.INSTANCE.init();
    }

    @Override
    public void a(LiteralArgumentBuilder<CommandSource> root) {
        root.executes(context -> status())
                .then(this.a("help").executes(context -> help()))
                .then(this.a("status").executes(context -> status()))
                .then(this.a("record")
                        .then(this.a("on").executes(context -> record(true)))
                        .then(this.a("off").executes(context -> record(false))))
                .then(this.a("train")
                        .executes(context -> train(DEFAULT_EPOCHS))
                        .then(this.a("epochs", IntegerArgumentType.integer(5, 400))
                                .executes(context -> train(IntegerArgumentType.getInteger(context, "epochs")))))
                .then(this.a("stop").executes(context -> stop()))
                .then(this.a("save").executes(context -> save()))
                .then(this.a("load").executes(context -> load()))
                .then(this.a("reset").executes(context -> reset()))
                .then(this.a("clear").executes(context -> clear()))
                .then(this.a("mode").executes(context -> mode()))
                .then(this.a("open").executes(context -> open()));
    }

    private static int say(String text) {
        ClientChat.a("[HolyWorld] " + text);
        return 1;
    }

    private int help() {
        say(".holyworld record on|off — записывать, как ты сам ведёшь цель мышью (Aura выключена)");
        say(".holyworld train [эпохи] — обучить GRU на записи в фоне (по умолчанию 40)");
        say(".holyworld stop — остановить обучение, сохранив лучшую модель");
        say(".holyworld status | save | load | reset | clear | open");
        say(".holyworld mode — поставить тип ротации AttackAura в «HolyWorld»");
        return 1;
    }

    private int status() {
        for (String line : HolyWorldBrain.INSTANCE.status().split("\n")) {
            say(line);
        }
        return 1;
    }

    private int record(boolean on) {
        HolyWorldBrain.INSTANCE.setRecording(on);
        if (on) {
            say("Запись включена. Води мышью по живой цели (игрок или моб рядом, до 7 блоков), Aura должна быть выключена.");
        } else {
            say("Запись выключена. Эпизодов: " + HolyWorldBrain.INSTANCE.dataset().episodeCount()
                    + ", тиков: " + HolyWorldBrain.INSTANCE.dataset().totalTicks());
        }
        return 1;
    }

    private int train(int epochs) {
        String problem = HolyWorldBrain.INSTANCE.startTraining(epochs);
        if (problem != null) {
            say(problem);
        }
        return 1;
    }

    private int stop() {
        if (!HolyWorldBrain.INSTANCE.isTraining()) {
            return say("Обучение не запущено.");
        }
        HolyWorldBrain.INSTANCE.stopTraining();
        return say("Остановка запрошена, лучшая модель будет сохранена после текущей эпохи.");
    }

    private int save() {
        String problem = HolyWorldBrain.INSTANCE.save();
        return say(problem == null ? "Сохранено в " + HolyWorldBrain.INSTANCE.directory().getAbsolutePath() : problem);
    }

    private int load() {
        String problem = HolyWorldBrain.INSTANCE.load();
        return say(problem == null ? "Загружено." : problem);
    }

    private int reset() {
        if (HolyWorldBrain.INSTANCE.isTraining()) {
            return say("Сначала останови обучение: .holyworld stop");
        }
        HolyWorldBrain.INSTANCE.resetModel();
        return say("Модель сброшена, записанные данные оставлены.");
    }

    private int clear() {
        HolyWorldBrain.INSTANCE.clearData();
        return say("Записанные данные удалены.");
    }

    private int mode() {
        AttackAura.c.e.a("HolyWorld");
        return say("AttackAura: тип ротации «HolyWorld».");
    }

    private int open() {
        File dir = HolyWorldBrain.INSTANCE.directory();
        Util.getOperatingSystem().open(dir);
        return say("Папка: " + dir.getAbsolutePath());
    }
}
