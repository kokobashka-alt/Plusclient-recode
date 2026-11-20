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

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.leg1tka.AspectDLC;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.command.CommandSource;

public class WaypointCommand extends dev.leg1tka.commands.ClientCommand {
   public WaypointCommand() {
      super("gps");
   }

   @Override
   public void a(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder1) {
      ((LiteralArgumentBuilder)literalArgumentBuilder1.then(this.a("X", IntegerArgumentType.integer()).then(this.a("Z", IntegerArgumentType.integer()).executes(commandContext0 -> {
         int intValue1x = (Integer)commandContext0.getArgument("X", Integer.class);
         int intValue2 = (Integer)commandContext0.getArgument("Z", Integer.class);
         dev.leg1tka.api.utils.math.Point2D value3 = new dev.leg1tka.api.utils.math.Point2D((double)intValue1x, (double)intValue2);
         AspectDLC.INSTANCE.waypointStorage.a(value3);
         dev.leg1tka.api.utils.text.ClientChat.a(I18n.translate("Метка поставлена: ", new Object[]{intValue1x, intValue2}));
         return 1;
      })))).then(this.a("remove").executes(commandContext0 -> {
         if (!AspectDLC.INSTANCE.waypointStorage.b()) {
            AspectDLC.INSTANCE.waypointStorage.a();
            dev.leg1tka.api.utils.text.ClientChat.a(I18n.translate("Метка удалена!", new Object[0]));
         } else {
            dev.leg1tka.api.utils.text.ClientChat.a(I18n.translate("Метки не было", new Object[0]));
         }

         return 1;
      }));
   }
}
