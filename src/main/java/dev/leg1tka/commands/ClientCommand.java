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

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import lombok.Generated;
import net.minecraft.command.CommandSource;

public abstract class ClientCommand implements dev.leg1tka.api.ClientAccess {
   private final String c;

   public ClientCommand(String string1) {
      this.c = string1;
   }

   public abstract void a(LiteralArgumentBuilder<CommandSource> literalArgumentBuilder1);

   public void a(CommandDispatcher<CommandSource> commandDispatcher1) {
      LiteralArgumentBuilder literalArgumentBuilder2 = LiteralArgumentBuilder.literal(this.c);
      this.a(literalArgumentBuilder2);
      commandDispatcher1.register(literalArgumentBuilder2);
   }

   protected <T> RequiredArgumentBuilder<CommandSource, T> a(String string1, ArgumentType<T> argumentType2) {
      return RequiredArgumentBuilder.argument(string1, argumentType2);
   }

   protected LiteralArgumentBuilder<CommandSource> a(String string1) {
      return LiteralArgumentBuilder.literal(string1);
   }

   @Generated
   public String a() {
      return this.c;
   }
}
