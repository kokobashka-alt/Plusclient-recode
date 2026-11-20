package dev.leg1tka.managers;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventBinding;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import net.minecraft.util.Formatting;

public class MacroStorage implements dev.leg1tka.api.ClientAccess {
   private final List<dev.leg1tka.commands.MacroBinding> c = new ArrayList<>();
   private final List<String> d = new ArrayList<>();

   public MacroStorage() {
      EventInvoker.register(this);
   }

   public void a(dev.leg1tka.commands.MacroBinding value1) {
      if (value1 != null && value1.a() != null && !value1.a().isBlank() && this.a(value1.a()) == null) {
         this.c.add(value1);
         this.d.add(value1.a());
      }
   }

   public void b(dev.leg1tka.commands.MacroBinding value1) {
      if (value1 != null) {
         this.c.remove(value1);
         this.d.remove(value1.a());
      }
   }

   public void a() {
      if (!this.c.isEmpty()) {
         this.c.clear();
      }

      if (!this.d.isEmpty()) {
         this.d.clear();
      }
   }

   public boolean b() {
      return this.c.isEmpty();
   }

   public dev.leg1tka.commands.MacroBinding a(String string1) {
      for (dev.leg1tka.commands.MacroBinding value3 : this.c) {
         if (value3.a().equalsIgnoreCase(string1)) {
            return value3;
         }
      }

      return null;
   }

   @EventLink
   public void onKey(EventBinding eventBinding1) {
      if (minecraftClient.player != null && minecraftClient.world != null && minecraftClient.currentScreen == null && minecraftClient.player.networkHandler != null && !this.c.isEmpty()) {
         for (dev.leg1tka.commands.MacroBinding value3 : this.c) {
            if (value3 != null && value3.c() != null && value3.c().getKey() == eventBinding1.getKey()) {
               this.c(value3);
            }
         }
      }
   }

   private void c(dev.leg1tka.commands.MacroBinding value1) {
      String string2 = value1.b();
      if (string2 != null && !string2.isBlank()) {
         if (string2.startsWith("/")) {
            minecraftClient.player.networkHandler.sendChatCommand(string2.substring(1));
         } else {
            String string3 = AspectDLC.INSTANCE.commandStorage.d();
            if (string3 != null && !string3.isEmpty() && string2.startsWith(string3)) {
               try {
                  AspectDLC.INSTANCE.commandStorage.b().execute(string2.substring(string3.length()), AspectDLC.INSTANCE.commandStorage.a());
               } catch (CommandSyntaxException commandSyntaxException5) {
                  dev.leg1tka.api.utils.text.ClientChat.a(
                     Formatting.RED
                        + "Ошибка в использовании макроса "
                        + value1.a()
                        + "!"
                  );
               }
            } else {
               minecraftClient.player.networkHandler.sendChatMessage(string2);
            }
         }
      }
   }

   @Generated
   public List<dev.leg1tka.commands.MacroBinding> c() {
      return this.c;
   }

   @Generated
   public List<String> d() {
      return this.d;
   }
}
